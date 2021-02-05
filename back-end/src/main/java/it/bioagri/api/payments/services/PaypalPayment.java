/*
 * MIT License
 *
 * Copyright (c) 2020 BioAgri S.r.l.s.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.bioagri.api.payments.services;

import ch.qos.logback.classic.Logger;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import it.bioagri.api.payments.PaymentRequest;
import it.bioagri.api.payments.PaymentServiceFailed;
import it.bioagri.models.Product;
import it.bioagri.models.TransactionType;
import it.bioagri.persistence.DataSource;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaypalPayment implements PaymentExternalService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(PaypalPayment.class);

    private final String clientId;
    private final String clientSecret;
    private final PayPalHttpClient client;

    public PaypalPayment(String clientId, String clientSecret) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;

        this.client = new PayPalHttpClient(
                new PayPalEnvironment.Sandbox(clientId, clientSecret)
        );

    }


    public PayPalHttpClient getClient() {
        return client;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }



//    @Override
//    public Transaction authorize(DataSource dataSource, PaymentRequest request, Transaction.Builder builder) {
//
//        try {
//
//            HttpResponse<Order> response = getClient().execute(new OrdersCaptureRequest(request.getId()) {{
//                requestBody(new OrderRequest());
//            }});
//
//
//            if(logger.isDebugEnabled()) {
//
//                logger.debug("===== PAYPAL AUTHORIZATION =====");
//                logger.debug("Status Code: %d".formatted(response.statusCode()));
//                logger.debug("OrderId    : %s".formatted(response.result().id()));
//                logger.debug("Status     : %s".formatted(response.result().status()));
//                logger.debug("CreateTime : %s".formatted(response.result().createTime()));
//                logger.debug("UpdateTime : %s".formatted(response.result().updateTime()));
//                logger.debug("Expiration : %s".formatted(response.result().expirationTime()));
//                logger.debug("Units      : %d".formatted(response.result().purchaseUnits().size()));
//                logger.debug("Items      : %d".formatted(request.getItems().size()));
//
//                for(var i : response.result().links())
//                    logger.debug("Links      : %s => %s".formatted(i.rel(), i.href()));
//
//                for(var i : response.result().purchaseUnits()) {
//
//                    for (var j : i.payments().captures())
//                        logger.debug("Captures   : %s".formatted(j.id()));
//
////                    logger.debug("Price      : %s".formatted(i.amountWithBreakdown()
////                            .amountBreakdown()
////                            .itemTotal()
////                            .value()));
//
//                }
//
//                logger.debug("BuyerMail  : %s".formatted(response.result().payer().email()));
//                logger.debug("BuyerName  : %s".formatted(response.result().payer().name().fullName()));
//
//                logger.debug("=====  END AUTHORIZATION   =====");
//
//            }
//
//
//
//
//            if(response.result().purchaseUnits().size() != 1)
//                throw new PaymentServiceFailed(request, "PurchaseUnits must be equal to 1");
//
//
////            for(var i : request.getItems()) {
////
////                var j = dataSource.getProductDao()
////                        .findByPrimaryKey(i.getKey())
////                        .orElseThrow(() -> new PaymentServiceFailed(request, "Invalid Product ID %s".formatted(i.getKey())));
////
////                if(i.getValue() > j.getStock())
////                    throw new PaymentServiceFailed(request, "Stock < Quantity for Product ID %s".formatted(i.getKey()));
////
//////                if(Double.parseDouble(i.unitAmount().value()) != (j.getPrice() - (j.getPrice() * j.getDiscount()) / 100))
//////                    throw new PaymentServiceFailed(request, "UnitAmount != Price for Product ID %s".formatted(i.sku()));
////
////                // TODO: Edit Quantity
////
////            }
//
//
//
//            builder
//                    .withStatus(TransactionStatus.PROCESSING)
//                    .withType(TransactionType.PAYPAL)
//                    .withTransactionCode(response.result().id())
//                    .withAddress("%s, %s, %s, %s, %s, %s, %s".formatted(
//                            response.result().payer().addressPortable().addressLine1(),
//                            response.result().payer().addressPortable().addressLine2(),
//                            response.result().payer().addressPortable().addressLine3(),
//                            response.result().payer().addressPortable().adminArea1(),
//                            response.result().payer().addressPortable().adminArea2(),
//                            response.result().payer().addressPortable().adminArea3(),
//                            response.result().payer().addressPortable().adminArea4()))
//                    .withCity(response.result().payer().addressPortable().countryCode());
////                    .withTotal(response.result().purchaseUnits().get(0)
////                            .items()
////                            .stream()
////                            .map(i -> Double.parseDouble(i.unitAmount().value()) * Double.parseDouble(i.quantity()))
////                            .reduce(0.0, Double::sum)
////                    );
//
//
//
//            if(HttpStatus.resolve(response.statusCode()) != null
//                    && HttpStatus.valueOf(response.statusCode()).is2xxSuccessful()) {
//
//                return builder
//                        .withStatus(TransactionStatus.OK)
//                        .build();
//
//            }
//
//
//        } catch (IOException | PaymentServiceFailed e) {
//
//            logger.error("===== PAYPAL AUTHORIZATION =====");
//            logger.error(e.getMessage(), e);
//            logger.error("=====  END AUTHORIZATION   =====");
//
//        }
//
//        return builder
//                .withStatus(TransactionStatus.FAILED)
//                .build();
//
//    }

    @Override
    public String create(DataSource dataSource, PaymentRequest request, double shippingPrice) throws PaymentServiceFailed {


        if (request.getItems().size() == 0)
            throw new PaymentServiceFailed(request, "EMPTY");


        List<Map.Entry<Product, Integer>> items;

        try {

            items = request.getItems()
                    .stream()
                    .filter(i -> i.getValue() > 0)
                    .map(i -> Map.entry(
                            dataSource.getProductDao()
                                    .findByPrimaryKey(i.getKey())
                                    .orElseThrow(() -> new RuntimeException("PRODUCT_NOT_FOUND")), i.getValue()
                    ))
                    .peek(i -> {
                        if (i.getKey().getStock() < i.getValue())
                            throw new RuntimeException("PRODUCT_NOT_AVAILABLE");
                    })
                    .collect(Collectors.toList());

        } catch (RuntimeException e) {
            throw new PaymentServiceFailed(request, e.getMessage());
        }


        try {

            double priceTotal = items
                    .stream()
                    .mapToDouble(i -> BigDecimal
                            .valueOf(i.getKey().getPrice() - (i.getKey().getPrice() * i.getKey().getDiscount() / 100))
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue() * i.getValue())
                    .reduce(0.0, Double::sum);


            OrderRequest order = new OrderRequest()
                    .checkoutPaymentIntent("CAPTURE")
                    .purchaseUnits(List.of(
                            new PurchaseUnitRequest()
                                    .description("Acquisto su Bioagri Shop")
                                    .amountWithBreakdown(new AmountWithBreakdown()
                                            .currencyCode("EUR")
                                            .value(BigDecimal.valueOf(priceTotal + shippingPrice)
                                                    .setScale(2, RoundingMode.HALF_UP)
                                                    .toPlainString())
                                            .amountBreakdown(new AmountBreakdown()
                                                    .itemTotal(new Money()
                                                            .currencyCode("EUR")
                                                            .value(BigDecimal.valueOf(priceTotal)
                                                                    .setScale(2, RoundingMode.HALF_UP)
                                                                    .toPlainString()))
                                                    .shipping(new Money()
                                                            .currencyCode("EUR")
                                                            .value(BigDecimal.valueOf(shippingPrice)
                                                                    .setScale(2, RoundingMode.HALF_UP)
                                                                    .toPlainString()))
                                                    .handling(new Money()
                                                            .currencyCode("EUR")
                                                            .value("0.00"))
                                                    .taxTotal(new Money()
                                                            .currencyCode("EUR")
                                                            .value("0.00"))
                                                    .shippingDiscount(new Money()
                                                            .currencyCode("EUR")
                                                            .value("0.00"))))
                                    .items(items.stream()
                                            .map(i -> new Item()
                                                    .name(i.getKey().getName())
                                                    .sku(i.getKey().getId().toString())
                                                    .quantity(i.getValue().toString())
                                                    .unitAmount(new Money()
                                                            .currencyCode("EUR")
                                                            .value(BigDecimal
                                                                    .valueOf(i.getKey().getPrice() - (i.getKey().getPrice() * i.getKey().getDiscount() / 100))
                                                                    .setScale(2, RoundingMode.HALF_UP)
                                                                    .toPlainString()))
                                                    .tax(new Money()
                                                            .currencyCode("EUR")
                                                            .value("0.00")))
                                            .collect(Collectors.toList()))

                    ));


            HttpResponse<Order> response = getClient().execute(new OrdersCreateRequest() {{
                requestBody(order);
            }});


            if(response.statusCode() != 201)
                throw new PaymentServiceFailed(request, "BAD_REQUEST");

            return response.result().id();


        } catch (IOException e) {
            throw new PaymentServiceFailed(request, "INTERNAL_ERROR");
        }

    }

    @Override
    public boolean authorize(DataSource dataSource, PaymentRequest request, it.bioagri.models.Order.Builder builder) {
        return false;
    }
}

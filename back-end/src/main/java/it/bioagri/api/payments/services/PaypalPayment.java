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
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import it.bioagri.api.payments.PaymentRequest;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

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


    @Override
    public boolean authorize(PaymentRequest request) {

        try {

            HttpResponse<Order> response = getClient().execute(new OrdersCaptureRequest(request.getId()) {{
                requestBody(new OrderRequest());
            }});


            if(logger.isDebugEnabled()) {

                logger.debug("===== PAYPAL AUTHORIZATION =====");
                logger.debug("Status Code: %d".formatted(response.statusCode()));
                logger.debug("Status     : %s".formatted(response.result().status()));
                logger.debug("OrderId    : %s".formatted(response.result().id()));

                for(var i : response.result().links())
                    logger.debug("Links      : %s => %s".formatted(i.rel(), i.href()));

                for(var i : response.result().purchaseUnits())
                    for(var j : i.payments().captures())
                        logger.debug("Captures   : %s".formatted(j.id()));

                logger.debug("BuyerMail  : %s".formatted(response.result().payer().email()));
                logger.debug("BuyerName  : %s".formatted(response.result().payer().name().fullName()));

                logger.debug("=====  END AUTHORIZATION   =====");

            }


            if(HttpStatus.resolve(response.statusCode()) != null)
                return HttpStatus.valueOf(response.statusCode()).is2xxSuccessful();

            return false;

        } catch (IOException e) {

            logger.error("===== PAYPAL AUTHORIZATION =====");
            logger.error(e.getMessage(), e);
            logger.error("=====  END AUTHORIZATION   =====");

            return false;

        }

    }

}

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

package it.bioagri.api.orders;

import io.restassured.RestAssured;
import it.bioagri.api.auth.AuthTest;
import it.bioagri.models.OrderStatus;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class OrdersTest {


    private String createAs(String username, int expectedCode) {
        return RestAssured.given()
                .header("X-Auth-Token", AuthTest.authenticate(username, "123"))
                .spec(AuthTest.getSpecs())
                .body(
                        """
                        {
                        "id"        : "0",
                        "status"    : "1",
                        "createdAt" : "2014-01-01T23:28:56.782Z",
                        "updatedAt" : "2015-01-01T23:28:56.782Z"
                        }
                        """
                )
                .post("/orders")
                .then()
                .statusCode(expectedCode)
                .extract()
                .header("Location");

    }

    @Test
    public void createOrders() {

        createAs("user@test.com",201);
        createAs("admin@test.com",201);

    }


    @Test
    void findAll() {

        RestAssured.given()
                .header("X-Auth-Token", AuthTest.authenticate("admin@test.com", "123"))
                .spec(AuthTest.getSpecs())
                .get("/orders")
                .then()
                .statusCode(200);



    }

    @Test
    void findById() {
    }


    @Test
    void update() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void delete() {
    }
}
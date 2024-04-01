package io.arrogantprogrammer.coffeeshop.web;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CoffeeshopApiResourceTest {

    @Test
    public void testPlaceOrder() {

        String json = """
                {
                    "uuid": "1e08c459-7e9e-463d-9c19-608688d1a63e",
                    "items": ["CAPPUCCINO"],
                    "name": "Ashley"
                }
                """;

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .when()
                .post("/api/order")
                .then()
                .assertThat()
                .statusCode(202);
    }
}

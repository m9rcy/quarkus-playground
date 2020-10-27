package io.m9rcy.playground;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AzureVaultResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/azure-vault/demo")
          .then()
             .statusCode(200)
             .body(containsString("demo-key"));
    }

}
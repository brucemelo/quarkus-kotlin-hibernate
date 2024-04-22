package com.github.brucemelo

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.jboss.resteasy.reactive.RestResponse.StatusCode
import org.junit.jupiter.api.Test

@QuarkusTest
class IndexResourceTest {

    @Test
    fun index() {
        given()
            .`when`().get("/")
            .then()
            .statusCode(StatusCode.OK)
            .body(`is`("Hello from Quarkus REST - Kotlin - Hibernate"))
    }

}
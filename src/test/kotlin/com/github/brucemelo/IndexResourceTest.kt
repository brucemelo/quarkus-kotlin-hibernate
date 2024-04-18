package com.github.brucemelo

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class IndexResourceTest {

    @Test
    fun index() {
        given()
            .`when`().get("/")
            .then()
            .statusCode(200)
            .body(`is`("Hello from Quarkus REST - Kotlin - Hibernate"))
    }

}
package com.github.brucemelo

import com.github.brucemelo.domain.Course
import com.github.brucemelo.domain.CourseService
import com.github.brucemelo.web.CourseDTO
import com.github.brucemelo.web.CourseResource
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.common.http.TestHTTPResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import jakarta.inject.Inject
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@QuarkusTest
@TestHTTPEndpoint(CourseResource::class)
class CourseResourceTest : ResourceTest() {

    @Inject
    private lateinit var courseService: CourseService

    @Test
    fun listAll() {
        val result1: List<CourseDTO> = given()
            .`when`().get()
            .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(object : TypeRef<List<CourseDTO>>() {})

        assertTrue(result1.isEmpty())

        courseService.save(Course().apply { name = "Test1" })

        val result2: List<CourseDTO> = given()
            .`when`().get()
            .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(object : TypeRef<List<CourseDTO>>() {})

        assertTrue(result2.isNotEmpty())
    }

    @Test
    fun save() {
        val course = CourseDTO(name = "Test2")

        val response: CourseDTO = given()
            .body(course)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post()
            .then()
            .statusCode(201)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(CourseDTO::class.java)

        assertEquals(course.name, response.name)
    }

}
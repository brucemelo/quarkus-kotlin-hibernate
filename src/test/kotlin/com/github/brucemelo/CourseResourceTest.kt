package com.github.brucemelo

import com.github.brucemelo.service.CourseService
import com.github.brucemelo.web.CourseResource
import com.github.brucemelo.web.CourseWithStudents
import com.github.brucemelo.web.NewCourse
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import jakarta.inject.Inject
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import org.hamcrest.CoreMatchers.notNullValue
import org.jboss.resteasy.reactive.RestResponse.StatusCode
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
        val result1: List<CourseWithStudents> = given()
            .`when`().get()
            .then()
            .statusCode(StatusCode.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(object : TypeRef<List<CourseWithStudents>>() {})

        assertTrue(result1.isEmpty())

        courseService.save(NewCourse(name = "course2"))

        val result2: List<CourseWithStudents> = given()
            .`when`().get()
            .then()
            .statusCode(StatusCode.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(object : TypeRef<List<CourseWithStudents>>() {})

        assertTrue(result2.isNotEmpty())
    }

    @Test
    fun save() {
        val course = NewCourse(name = "Test2")

        val response: NewCourse = given()
            .body(course)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post()
            .then()
            .statusCode(StatusCode.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(NewCourse::class.java)

        assertEquals(course.name, response.name)
    }

}
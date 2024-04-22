package com.github.brucemelo

import com.github.brucemelo.domain.Student
import com.github.brucemelo.service.StudentService
import com.github.brucemelo.web.NewStudent
import com.github.brucemelo.web.StudentResource
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
@TestHTTPEndpoint(StudentResource::class)
class StudentResourceTest : ResourceTest() {

    @Inject
    private lateinit var studentService: StudentService

    @Test
    fun listAll() {
        val students1: List<NewStudent> = given()
            .`when`().get()
            .then()
            .statusCode(StatusCode.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(object : TypeRef<List<NewStudent>>() {})

        assertTrue(students1.isEmpty())

        studentService.save(Student().apply { name = "Test User" })

        val students2: List<NewStudent> = given()
            .`when`().get()
            .then()
            .statusCode(StatusCode.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(object : TypeRef<List<NewStudent>>() {})

        assertTrue(students2.isNotEmpty())
    }

    @Test
    fun save() {
        val student = NewStudent(name = "Bruce")

        val responseStudent: NewStudent = given()
            .body(student)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post()
            .then()
            .statusCode(StatusCode.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(notNullValue())
            .extract().`as`(NewStudent::class.java)

        assertEquals(student.name, responseStudent.name)
    }

}
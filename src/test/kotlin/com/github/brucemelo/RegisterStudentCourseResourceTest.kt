package com.github.brucemelo

import com.github.brucemelo.domain.Student
import com.github.brucemelo.service.CourseService
import com.github.brucemelo.service.StudentService
import com.github.brucemelo.web.NewCourse
import com.github.brucemelo.web.NewRegisterStudentCourse
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.RestResponse.StatusCode
import org.junit.jupiter.api.Test

@QuarkusTest
class RegisterStudentCourseResourceTest : ResourceTest() {

    @Inject
    private lateinit var studentService: StudentService

    @Inject
    private lateinit var courseService: CourseService

    @Test
    fun `register student`() {
        val course = courseService.save(NewCourse(name = "course1"))
        val student: Student = studentService.save(Student().apply { name = "name1" })

        val newRegisterStudentCourse = NewRegisterStudentCourse(
            idStudent = student.id!!,
            idCourse = course.id!!)

        given()
            .body(newRegisterStudentCourse)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post("/register-students")
            .then()
            .statusCode(StatusCode.CREATED)
    }

    @Test
    fun `register student - throws Course not found exception`() {
        studentService.save(Student().apply { name = "name1" })

        val newRegisterStudentCourse = NewRegisterStudentCourse(
            idStudent = 1,
            idCourse = 1)

        given()
            .body(newRegisterStudentCourse)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post("/register-students")
            .then()
            .statusCode(StatusCode.NOT_FOUND)
    }

    @Test
    fun `register student - throws Student not found exception`() {
        courseService.save(NewCourse(name = "course1"))

        val newRegisterStudentCourse = NewRegisterStudentCourse(
            idStudent = 1,
            idCourse = 1)

        given()
            .body(newRegisterStudentCourse)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post("/register-students")
            .then()
            .statusCode(StatusCode.NOT_FOUND)
    }

}
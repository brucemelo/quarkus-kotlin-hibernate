package com.github.brucemelo

import com.github.brucemelo.domain.Course
import com.github.brucemelo.domain.CourseService
import com.github.brucemelo.domain.Student
import com.github.brucemelo.domain.StudentService
import com.github.brucemelo.web.RegisterStudentCourseDTO
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import org.junit.jupiter.api.Test

@QuarkusTest
class RegisterStudentCourseResourceTest : ResourceTest() {

    @Inject
    private lateinit var studentService: StudentService

    @Inject
    private lateinit var courseService: CourseService

    @Test
    fun `register student`() {
        val course = courseService.save(Course().apply { name = "caa1" })
        val student: Student = studentService.save(Student().apply { name = "s24" })

        val registerStudentCourseDTO = RegisterStudentCourseDTO(
            idStudent = student.id!!,
            idCourse = course.id!!)

        given()
            .body(registerStudentCourseDTO)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post("/register-students")
            .then()
            .statusCode(201)
    }

    @Test
    fun `register student - throws Course not found exception`() {
        studentService.save(Student().apply { name = "s2333" })

        val registerStudentCourseDTO = RegisterStudentCourseDTO(
            idStudent = 1,
            idCourse = 1)

        given()
            .body(registerStudentCourseDTO)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post("/register-students")
            .then()
            .statusCode(404)
    }

    @Test
    fun `register student - throws Student not found exception`() {
        courseService.save(Course().apply { name = "course123" })

        val registerStudentCourseDTO = RegisterStudentCourseDTO(
            idStudent = 1,
            idCourse = 1)

        given()
            .body(registerStudentCourseDTO)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .`when`().post("/register-students")
            .then()
            .statusCode(404)
    }

}
package com.github.brucemelo.web

import com.github.brucemelo.service.RegisterStudentCourseService
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response

data class NewRegisterStudentCourse(val idStudent: Long, val idCourse: Long)

@Path("/register-students")
class RegisterStudentCourseResource(private val registerStudentCourseService: RegisterStudentCourseService) {

    @POST
    fun register(newRegisterStudentCourse: NewRegisterStudentCourse): Response =
        registerStudentCourseService.register(newRegisterStudentCourse)
            .let { Response.status(Response.Status.CREATED).build() }

}

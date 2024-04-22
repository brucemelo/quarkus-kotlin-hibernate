package com.github.brucemelo.web

import com.github.brucemelo.service.CourseService
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

data class CourseWithStudents(var name: String, val students: List<NewStudent> = mutableListOf())
data class NewCourse(var name: String)

@Path("/courses")
class CourseResource(private val courseService: CourseService) {

    @GET
    fun listAll(): List<CourseWithStudents> = courseService.listAll()
        .map { CourseWithStudents(it.name, students = it.studentCourse.map { v -> NewStudent(name = v.student.name) }) }

    @POST
    fun save(newCourse: NewCourse): RestResponse<NewCourse> {
        if (newCourse.name.isBlank()) throw BadRequestException("Course name cannot be blank.")
        courseService.save(newCourse)
        return ResponseBuilder.create(RestResponse.Status.CREATED, NewCourse(name = newCourse.name)).build()
    }

}
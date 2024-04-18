package com.github.brucemelo.web

import com.github.brucemelo.domain.Course
import com.github.brucemelo.domain.CourseService
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response

data class CourseDTO(var name: String, val students: List<StudentDTO> = mutableListOf())

@Path("/courses")
class CourseResource(private val courseService: CourseService) {

    @GET
    fun listAll(): List<CourseDTO> = courseService.listAll()
        .map { CourseDTO(it.name, students = it.studentCourse.map { v -> StudentDTO(name = v.student.name) }) }

    @POST
    fun save(dto: CourseDTO): Response {
        val course = Course().apply { name = dto.name }
        courseService.save(course)
        return Response.status(Response.Status.CREATED).entity(CourseDTO(course.name)).build()
    }

}
package com.github.brucemelo.web

import com.github.brucemelo.domain.Student
import com.github.brucemelo.domain.StudentService
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response

data class StudentDTO(var name: String)

@Path("/students")
class StudentResource(private val studentService: StudentService) {

    @GET
    fun listAll(): List<StudentDTO> =
        studentService.listAll().map { StudentDTO(it.name) }

    @POST
    fun save(dto: StudentDTO): Response =
        Student().apply { name = dto.name }
            .also { studentService.save(it) }
            .let { StudentDTO(name = it.name) }
            .let { Response.status(Response.Status.CREATED).entity(it).build() }

}

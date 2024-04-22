package com.github.brucemelo.web

import com.github.brucemelo.domain.Student
import com.github.brucemelo.service.StudentService
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestResponse

data class NewStudent(var name: String)
data class StudentResult(var name: String)

@Path("/students")
class StudentResource(private val studentService: StudentService) {

    @GET
    fun listAll(): List<StudentResult> =
        studentService.listAll().map { StudentResult(it.name) }

    @ResponseStatus(RestResponse.StatusCode.CREATED)
    @POST
    fun save(newStudent: NewStudent): NewStudent =
        Student().apply { name = newStudent.name }
            .also { studentService.save(it) }
            .let { NewStudent(name = it.name) }

}

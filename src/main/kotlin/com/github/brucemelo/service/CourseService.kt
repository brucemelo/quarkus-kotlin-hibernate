package com.github.brucemelo.service

import com.github.brucemelo.domain.Course
import com.github.brucemelo.domain.CourseRepository
import com.github.brucemelo.infrastructure.NotFoundException
import com.github.brucemelo.web.NewCourse
import io.quarkus.narayana.jta.QuarkusTransaction
import jakarta.enterprise.context.ApplicationScoped

class CourseNotFoundException : NotFoundException()

@ApplicationScoped
class CourseService(private val courseRepository: CourseRepository) {

    fun save(newCourse: NewCourse): Course {
        val course = Course().apply { name = newCourse.name }
        QuarkusTransaction.joiningExisting().run {
            courseRepository.persist(course)
        }
        return course
    }


    fun listAll(): List<Course> =
        courseRepository.listAll()

    fun findById(id: Long) =
        courseRepository.findById(id) ?: throw CourseNotFoundException()

}
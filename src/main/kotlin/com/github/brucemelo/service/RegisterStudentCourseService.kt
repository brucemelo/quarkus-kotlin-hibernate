package com.github.brucemelo.service

import com.github.brucemelo.domain.CourseNotFoundException
import com.github.brucemelo.domain.CourseRepository
import com.github.brucemelo.domain.StudentCourse
import com.github.brucemelo.domain.StudentCourseRepository
import com.github.brucemelo.domain.StudentNotFoundException
import com.github.brucemelo.domain.StudentRepository
import com.github.brucemelo.web.RegisterStudentCourseDTO
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class RegisterStudentCourseService(
    private val studentCourseRepository: StudentCourseRepository,
    private val studentRepository: StudentRepository,
    private val courseRepository: CourseRepository
) {

    @Transactional
    fun register(registerStudentCourseDTO: RegisterStudentCourseDTO) {
        val student = studentRepository.findById(registerStudentCourseDTO.idStudent) ?: throw StudentNotFoundException()
        val course = courseRepository.findById(registerStudentCourseDTO.idCourse) ?: throw CourseNotFoundException()

        val studentCourse = StudentCourse().apply {
            this.student = student
            this.course = course
        }

        studentCourseRepository.persist(studentCourse)
    }

}

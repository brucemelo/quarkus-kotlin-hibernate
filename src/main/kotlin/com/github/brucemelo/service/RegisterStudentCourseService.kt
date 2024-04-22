package com.github.brucemelo.service

import com.github.brucemelo.domain.StudentCourse
import com.github.brucemelo.domain.StudentCourseRepository
import com.github.brucemelo.web.NewRegisterStudentCourse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class RegisterStudentCourseService(
    private val studentCourseRepository: StudentCourseRepository,
    private val studentService: StudentService,
    private val courseService: CourseService
) {

    @Transactional
    fun register(newRegisterStudentCourse: NewRegisterStudentCourse) {
        val student = studentService.findById(newRegisterStudentCourse.idStudent)
        val course = courseService.findById(newRegisterStudentCourse.idCourse)

        val studentCourse = StudentCourse().apply {
            this.student = student
            this.course = course
        }

        studentCourseRepository.persist(studentCourse)
    }

}

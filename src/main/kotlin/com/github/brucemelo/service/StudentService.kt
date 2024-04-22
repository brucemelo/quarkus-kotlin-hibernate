package com.github.brucemelo.service

import com.github.brucemelo.domain.Student
import com.github.brucemelo.domain.StudentRepository
import com.github.brucemelo.infrastructure.NotFoundException
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

class StudentNotFoundException : NotFoundException()

@ApplicationScoped
class StudentService(private val studentRepository: StudentRepository) {

    @Transactional
    fun save(student: Student) =
        studentRepository.persist(student).let { student }

    fun listAll(): List<Student> =
        studentRepository.listAll()

    fun findById(id: Long) =
        studentRepository.findById(id) ?: throw StudentNotFoundException()

}
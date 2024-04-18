package com.github.brucemelo

import com.github.brucemelo.domain.CourseRepository
import com.github.brucemelo.domain.StudentCourseRepository
import com.github.brucemelo.domain.StudentRepository
import io.quarkus.narayana.jta.QuarkusTransaction
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach

abstract class ResourceTest {

    @Inject
    private lateinit var studentRepository: StudentRepository
    @Inject
    private lateinit var courseRepository: CourseRepository
    @Inject
    private lateinit var studentCourseRepository: StudentCourseRepository

    @AfterEach
    fun after() {
        QuarkusTransaction.joiningExisting().run {
            studentCourseRepository.deleteAll()
            studentRepository.deleteAll()
            courseRepository.deleteAll()
        }
    }

}
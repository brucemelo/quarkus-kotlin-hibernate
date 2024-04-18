package com.github.brucemelo.domain

import com.github.brucemelo.infrastructure.NotFoundException
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.narayana.jta.QuarkusTransaction
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "course")
class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    var studentCourse: MutableSet<StudentCourse> = mutableSetOf()

}

@ApplicationScoped
class CourseRepository : PanacheRepository<Course>

class CourseNotFoundException : NotFoundException()

@ApplicationScoped
class CourseService(private val courseRepository: CourseRepository) {

    fun save(course: Course) =
        QuarkusTransaction.joiningExisting().run {
            courseRepository.persist(course)
        }.let { course }

    fun listAll(): List<Course> =
        courseRepository.listAll()

}




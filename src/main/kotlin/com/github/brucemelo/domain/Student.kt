package com.github.brucemelo.domain

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
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
@Table(name = "student")
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    var studentCourse: MutableSet<StudentCourse> = mutableSetOf()

}

@ApplicationScoped
class StudentRepository : PanacheRepository<Student>


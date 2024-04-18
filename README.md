# Java 21 - Quarkus 3.9 - Kotlin - Hibernate

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Test

```
###
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "name": "student1"
}

###
GET http://localhost:8080/api/students

###
POST http://localhost:8080/api/courses
Content-Type: application/json

{
  "name": "course1"
}

###
GET http://localhost:8080/api/courses

###
POST http://localhost:8080/api/register-students
Content-Type: application/json

{
  "idStudent": 1,
  "idCourse": 1
}


```
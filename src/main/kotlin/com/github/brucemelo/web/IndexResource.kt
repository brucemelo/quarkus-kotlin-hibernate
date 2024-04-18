package com.github.brucemelo.web

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path


@Path("/")
class IndexResource {

    @GET
    fun index() = "Hello from Quarkus REST - Kotlin - Hibernate"

}

package com.github.brucemelo.infrastructure

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class NotFoundExceptionMapper : ExceptionMapper<NotFoundException> {

    override fun toResponse(ex: NotFoundException): Response {
        return Response.status(Response.Status.NOT_FOUND).build()
    }

}

open class NotFoundException : RuntimeException()
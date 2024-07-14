package io.github.cares0.restdocskdsl.core

abstract class HeaderElement : HandlerElement

data class RequestHeaderElement(
    override val name: String,
) : HeaderElement()

data class ResponseHeaderElement(
    override val name: String,
) : HeaderElement()
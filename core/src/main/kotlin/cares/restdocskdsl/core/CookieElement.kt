package cares.restdocskdsl.core

abstract class CookieElement : HandlerElement

data class RequestCookieElement(
    override val name: String,
) : CookieElement()

data class ResponseCookieElement(
    override val name: String,
) : CookieElement()
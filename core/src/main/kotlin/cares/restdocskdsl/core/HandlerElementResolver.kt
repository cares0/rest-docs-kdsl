package cares.restdocskdsl.core

interface HandlerElementResolver<T> {

    fun isSupport(node: T): Boolean

    fun resolve(node: T): List<HandlerElement>

}
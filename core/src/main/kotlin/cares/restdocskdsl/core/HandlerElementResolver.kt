package cares.restdocskdsl.core

/**
 * Resolve [HandlerElement] from a Handler.
 *
 * @author YoungJun Kim
 * @see HandlerElement
 */
interface HandlerElementResolver<T> {

    fun isSupport(node: T): Boolean

    fun resolve(node: T): List<HandlerElement>

}
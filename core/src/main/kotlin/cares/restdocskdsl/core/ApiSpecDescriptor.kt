package cares.restdocskdsl.core

import cares.restdocskdsl.dsl.*

/**
 * Describes the API specifications.
 *
 * A list of [HandlerElement] is resolved from the handler by the [HandlerElementResolver].
 * The resolved [HandlerElement] list is then converted into the [ApiValue] type properties
 * of an [ApiComponent] implementation by the [HandlerElementWriter].
 * Finally, the implementation of the [ApiSpec] for each API is built,
 * and the built [ApiComponent] and [ApiSpec] implementations are written to a single file.
 *
 * @author YoungJun Kim
 * @see HandlerElement
 * @see HandlerElementResolver
 * @see HandlerElementWriter
 */
interface ApiSpecDescriptor {

    val handlerName: String
    val packageName: String
    val fileName: String
    val handlerElements: List<HandlerElement>

    fun writeApiSpecFile()

}
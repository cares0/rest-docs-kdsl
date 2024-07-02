package cares.restdocskdsl.core

import cares.restdocskdsl.dsl.*

/**
 * Writes the build process of DSL-related classes such as [ApiSpec], [ApiComponent], and [ApiValue]
 * to the [ApiSpecDescriptor] using [HandlerElement].
 *
 * @author YoungJun Kim
 * @see HandlerElement
 * @see ApiSpecDescriptor
 */
interface HandlerElementWriter {

    fun write(descriptor: ApiSpecDescriptor)

}
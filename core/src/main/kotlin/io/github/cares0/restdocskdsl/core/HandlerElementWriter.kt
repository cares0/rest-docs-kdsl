package io.github.cares0.restdocskdsl.core

import io.github.cares0.restdocskdsl.dsl.*

/**
 * Writes the build process of DSL-related classes such as [ApiSpec], [ApiComponent], and [ApiField]
 * to the [ApiSpecDescriptor] using [HandlerElement].
 *
 * @author YoungJun Kim
 * @see HandlerElement
 * @see ApiSpecDescriptor
 */
interface HandlerElementWriter {

    fun write(descriptor: ApiSpecDescriptor)

}
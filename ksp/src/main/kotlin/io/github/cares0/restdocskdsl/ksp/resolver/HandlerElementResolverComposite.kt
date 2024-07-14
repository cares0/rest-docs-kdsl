package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.HandlerElement
import io.github.cares0.restdocskdsl.core.HandlerElementResolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

abstract class HandlerElementResolverComposite<T>(
    val environment: SymbolProcessorEnvironment
) : HandlerElementResolver<T> {

    abstract val delegates: List<HandlerElementResolver<T>>

    private val resolverCache: MutableMap<T, List<HandlerElementResolver<T>>> = mutableMapOf()

    override fun isSupport(node: T): Boolean {
        return getSupportingResolvers(node).isNotEmpty()
    }

    override fun resolve(node: T): List<HandlerElement> {
        return if (isSupport(node)) {
            getSupportingResolvers(node).flatMap { it.resolve(node) }
        } else emptyList()
    }

    private fun getSupportingResolvers(ksNode: T): List<HandlerElementResolver<T>> {
        val cachedResolver = resolverCache[ksNode]

        if (cachedResolver == null) {
            val supportingResolvers = delegates.filter { it.isSupport(ksNode) }
            resolverCache[ksNode] = supportingResolvers
        }

        return resolverCache[ksNode]!!
    }

}
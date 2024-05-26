package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.HandlerElementResolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSValueParameter

class ValueParameterResolverComposite(
    environment: SymbolProcessorEnvironment,
    vararg resolvers: HandlerElementResolver<KSValueParameter>,
) : HandlerElementResolverComposite<KSValueParameter>(environment) {
    override val delegates: List<HandlerElementResolver<KSValueParameter>> = listOf(*resolvers)
}

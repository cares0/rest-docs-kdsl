package cares.restdocskdsl.ksp.resolver

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

class FunctionResolverComposite(
    environment: SymbolProcessorEnvironment,
    vararg delegates: FunctionResolver
) : HandlerElementResolverComposite<KSFunctionDeclaration>(environment) {

    override val delegates: List<FunctionResolver> = listOf(*delegates)

}
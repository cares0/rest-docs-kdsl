package cares.restdocskdsl.ksp.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.*

class HandlerDeclarationVisitor(
    environment: SymbolProcessorEnvironment,
) : SymbolEnvironmentDefaultVisitor<Resolver, Unit>(environment) {

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Resolver) {
        logger.info("Generate DSL class: ${function.qualifiedName?.asString()}")

        logger.info("Complete DSL class generation: ${function.qualifiedName?.asString()}")
    }

    override fun defaultHandler(node: KSNode, data: Resolver) {
        logger.info("node not handled", node)
    }

}
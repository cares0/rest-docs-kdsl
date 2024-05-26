package cares.restdocskdsl.ksp.processor

import cares.restdocskdsl.ksp.KspApiSpecDescriptor
import cares.restdocskdsl.ksp.resolver.KspHandlerElementResolver
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.*

class HandlerDeclarationVisitor(
    environment: SymbolProcessorEnvironment,
) : SymbolEnvironmentDefaultVisitor<Resolver, Unit>(environment) {

    private val handlerElementResolver = KspHandlerElementResolver(environment)

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Resolver) {
        logger.info("Generate DSL class: ${function.qualifiedName?.asString()}")

        val apiSpecDescriptor = KspApiSpecDescriptor(
            environment = environment,
            handlerDeclaration = function,
        )

        handlerElementResolver.resolve(apiSpecDescriptor)

        logger.info("Complete DSL class generation: ${function.qualifiedName?.asString()}")
    }

    override fun defaultHandler(node: KSNode, data: Resolver) {
        logger.info("node not handled", node)
    }

}
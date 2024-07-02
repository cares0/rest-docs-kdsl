package cares.restdocskdsl.ksp.processor

import cares.restdocskdsl.core.*
import cares.restdocskdsl.ksp.KspApiSpecDescriptor
import cares.restdocskdsl.ksp.resolver.KspHandlerElementResolver
import cares.restdocskdsl.ksp.writer.*
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.*

/**
 * A visitor class that processes handler functions
 *
 * This class generates an [KspApiSpecDescriptor] for each handler function
 * and creates the corresponding API specification file.
 *
 * The process involves:
 * 1. Creating a [KspApiSpecDescriptor] that contains the overall API specification information from the handler function.
 * 2. Using [KspHandlerElementResolver] to analyze the handler function's parameters, return type, and annotations.
 *  These elements are then converted into appropriate [HandlerElement] implementations.
 * 3. The resolved [HandlerElement] implementations are used in [KotlinPoetHandlerElementWriter]
 * to generate the final API specification descriptor.
 * 4. After the API specification is fully described, an `[cares.restdocskdsl.dsl.ApiSpec] implementation is built
 * and the corresponding file is created.
 *
 * @author YoungJun Kim
 * @see KspApiSpecDescriptor
 * @see KspHandlerElementResolver
 * @see KotlinPoetHandlerElementWriter
 */
class HandlerDeclarationVisitor(
    environment: SymbolProcessorEnvironment,
) : SymbolEnvironmentDefaultVisitor<Resolver, Unit>(environment) {

    private val handlerElementResolver: HandlerElementResolver<ApiSpecDescriptor> =
        KspHandlerElementResolver(environment)

    private val apiComponentWriter: HandlerElementWriter = KotlinPoetHandlerElementWriterComposite(environment)

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Resolver) {
        logger.info("Generate DSL class: ${function.qualifiedName?.asString()}")

        val apiSpecDescriptor = KspApiSpecDescriptor(
            environment = environment,
            handlerDeclaration = function,
        )

        handlerElementResolver.resolve(apiSpecDescriptor)

        apiComponentWriter.write(apiSpecDescriptor)

        apiSpecDescriptor.writeApiSpecFile()

        logger.info("Complete DSL class generation: ${function.qualifiedName?.asString()}")
    }

    override fun defaultHandler(node: KSNode, data: Resolver) {
        logger.info("node not handled", node)
    }

}
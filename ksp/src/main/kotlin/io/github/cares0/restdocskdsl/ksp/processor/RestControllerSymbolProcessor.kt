package io.github.cares0.restdocskdsl.ksp.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import org.springframework.web.bind.annotation.RestController

/**
 * A custom implementation of [SymbolProcessor] for processing [RestController] annotation.
 *
 * It identifies and processes all classes annotated with `@RestController` in the source code
 * and passes them to a [HandlerFilterVisitor] for further processing.
 *
 * @author YoungJun Kim
 * @see HandlerFilterVisitor
 */
class RestControllerSymbolProcessor(
    val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val visitor = HandlerFilterVisitor(environment)

        resolver
            .getSymbolsWithAnnotation(RestController::class.qualifiedName!!)
            .forEach { it.accept(visitor, resolver) }

        return emptyList()
    }

}
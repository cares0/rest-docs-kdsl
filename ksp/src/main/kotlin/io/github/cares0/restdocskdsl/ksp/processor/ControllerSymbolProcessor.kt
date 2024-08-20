package io.github.cares0.restdocskdsl.ksp.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import org.springframework.stereotype.Controller

/**
 * A custom implementation of [SymbolProcessor] for processing [Controller] annotation.
 *
 * It identifies and processes all classes annotated with `@Controller` in the source code
 * and passes them to a [RestApiFilterVisitor] for further processing.
 *
 * @author YoungJun Kim
 * @see RestApiFilterVisitor
 */
class ControllerSymbolProcessor(
    val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val visitor = RestApiFilterVisitor(environment)

        resolver
            .getSymbolsWithAnnotation(Controller::class.qualifiedName!!)
            .forEach { it.accept(visitor, resolver) }

        return emptyList()
    }

}
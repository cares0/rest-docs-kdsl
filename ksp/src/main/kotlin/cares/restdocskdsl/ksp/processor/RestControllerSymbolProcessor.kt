package cares.restdocskdsl.ksp.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import org.springframework.web.bind.annotation.RestController

class RestControllerSymbolProcessor(
    val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val visitor = RestControllerSymbolVisitor(environment)

        resolver
            .getSymbolsWithAnnotation(RestController::class.qualifiedName!!)
            .forEach { classDeclaration -> classDeclaration.accept(visitor, resolver) }

        return emptyList()
    }

}
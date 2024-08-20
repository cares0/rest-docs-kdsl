package io.github.cares0.restdocskdsl.ksp.processor

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import io.github.cares0.restdocskdsl.ksp.getQualifiedName
import org.springframework.web.bind.annotation.*

/**
 * Receives class symbols resolved by [ControllerSymbolProcessor]
 * and filters functions that are REST APIs within the class.
 * These filtered functions are then passed to the [HandlerFilterVisitor].
 *
 * @author YoungJun Kim
 * @see HandlerFilterVisitor
 */
class RestApiFilterVisitor(
    environment: SymbolProcessorEnvironment,
) : SymbolEnvironmentDefaultVisitor<Resolver, Unit>(environment) {
    private val handlerFilterVisitor = HandlerFilterVisitor(environment)

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Resolver) {
        classDeclaration.getDeclaredFunctions()
            .filter(::isRestApi)
            .forEach { it.accept(handlerFilterVisitor, data) }
    }

    private fun isRestApi(function: KSFunctionDeclaration) = function.annotations
        .filter { ResponseBody::class.qualifiedName == it.annotationType.getQualifiedName() }
        .toList()
        .isNotEmpty()

    override fun defaultHandler(node: KSNode, data: Resolver) {
        logger.info("node not handled", node)
    }
    
}
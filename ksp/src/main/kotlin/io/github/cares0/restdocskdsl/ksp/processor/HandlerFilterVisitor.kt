package io.github.cares0.restdocskdsl.ksp.processor

import io.github.cares0.restdocskdsl.ksp.getQualifiedName
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import org.springframework.web.bind.annotation.*

/**
 * Filters handler functions and passes them to the `HandlerDeclarationVisitor`.
 *
 * If the provided symbol is a `ClassDeclaration`, it filters all functions declared within that class.
 * If the provided symbol is a `FunctionDeclaration`, it filters whether the function is a handler.
 * The filtering process is based on Spring's request mapping annotations.
 *
 * @author YoungJun Kim
 * @see HandlerDeclarationVisitor
 */
class HandlerFilterVisitor(
    environment: SymbolProcessorEnvironment,
) : SymbolEnvironmentDefaultVisitor<Resolver, Unit>(environment) {

    private val handlerDeclarationVisitor = HandlerDeclarationVisitor(environment)

    private val urlMappingAnnotations = listOf(
        RequestMapping::class.qualifiedName,
        GetMapping::class.qualifiedName,
        PostMapping::class.qualifiedName,
        PatchMapping::class.qualifiedName,
        PutMapping::class.qualifiedName,
        DeleteMapping::class.qualifiedName,
    )

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Resolver) {
        classDeclaration.getDeclaredFunctions()
            .filter(::isHandler)
            .forEach { it.accept(handlerDeclarationVisitor, data) }
    }

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Resolver) {
        if (isHandler(function)) function.accept(handlerDeclarationVisitor, data)
    }

    private fun isHandler(function: KSFunctionDeclaration) = function.annotations
        .filter { urlMappingAnnotations.contains(it.annotationType.getQualifiedName()) }
        .toList()
        .isNotEmpty()

    override fun defaultHandler(node: KSNode, data: Resolver) {
        logger.info("node not handled", node)
    }

}
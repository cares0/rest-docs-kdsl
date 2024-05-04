package cares.restdocskdsl.ksp.processor

import cares.restdocskdsl.ksp.getQualifiedName
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import org.springframework.web.bind.annotation.*

class RestControllerSymbolVisitor(
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
            .forEach { handlerDeclaration -> handlerDeclaration.accept(handlerDeclarationVisitor, data) }
    }

    private fun isHandler(function: KSFunctionDeclaration) = function.annotations
        .filter { urlMappingAnnotations.contains(it.annotationType.getQualifiedName()) }
        .toList()
        .isNotEmpty()

    override fun defaultHandler(node: KSNode, data: Resolver) {
        logger.info("node not handled", node)
    }

}
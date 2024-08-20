package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.annotation.RequestHeaderDocs
import io.github.cares0.restdocskdsl.ksp.containsAnnotation
import io.github.cares0.restdocskdsl.ksp.getArgumentValue
import io.github.cares0.restdocskdsl.ksp.getQualifiedName
import io.github.cares0.restdocskdsl.core.HandlerElement
import io.github.cares0.restdocskdsl.core.RequestHeaderElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class RequestHeaderDocsAnnotationElementResolver(
    private val logger: KSPLogger,
) : io.github.cares0.restdocskdsl.ksp.resolver.FunctionResolver {

    override fun isSupport(kSFunction: KSFunctionDeclaration): Boolean {
        return kSFunction.containsAnnotation(RequestHeaderDocs::class) ?: false
    }

    override fun resolve(kSFunction: KSFunctionDeclaration): List<HandlerElement> {
        val extractedElements = mutableListOf<RequestHeaderElement>()

        kSFunction.annotations.forEach { annotation ->
            if (annotation.annotationType.getQualifiedName() != RequestHeaderDocs::class.qualifiedName) return@forEach

            val names = (annotation.getArgumentValue("name") as? ArrayList<*>)
                ?.mapNotNull { it as? String } ?: emptyList()

            val values = (annotation.getArgumentValue("value") as? ArrayList<*>)
                ?.mapNotNull { it as? String } ?: emptyList()

            (names + values).forEach { extractedElements.add(RequestHeaderElement(it)) }
        }

        return extractedElements
    }

}
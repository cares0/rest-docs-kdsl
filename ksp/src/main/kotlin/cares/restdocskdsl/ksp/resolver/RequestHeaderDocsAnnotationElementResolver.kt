package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.annotation.RequestHeaderDocs
import cares.restdocskdsl.ksp.containsAnnotation
import cares.restdocskdsl.ksp.getArgumentValue
import cares.restdocskdsl.ksp.getQualifiedName
import cares.restdocskdsl.core.HandlerElement
import cares.restdocskdsl.core.RequestHeaderElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class RequestHeaderDocsAnnotationElementResolver(
    private val logger: KSPLogger,
) : FunctionResolver {

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
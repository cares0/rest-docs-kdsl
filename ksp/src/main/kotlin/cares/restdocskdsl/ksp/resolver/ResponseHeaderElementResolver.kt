package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.annotation.ResponseHeaderDocs
import cares.restdocskdsl.ksp.containsAnnotation
import cares.restdocskdsl.ksp.getArgumentValue
import cares.restdocskdsl.ksp.getQualifiedName
import cares.restdocskdsl.core.HandlerElement
import cares.restdocskdsl.core.ResponseHeaderElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class ResponseHeaderElementResolver(
    val logger: KSPLogger,
) : FunctionResolver {

    override fun isSupport(kSFunction: KSFunctionDeclaration): Boolean {
        return kSFunction.containsAnnotation(ResponseHeaderDocs::class)
    }

    override fun resolve(kSFunction: KSFunctionDeclaration): List<HandlerElement> {
        val extractedElements = mutableListOf<ResponseHeaderElement>()

        kSFunction.annotations.forEach { annotation ->
            if (annotation.annotationType.getQualifiedName() != ResponseHeaderDocs::class.qualifiedName) return@forEach

            val names = (annotation.getArgumentValue("name") as? ArrayList<*>)
                ?.mapNotNull { it as? String } ?: emptyList()

            val values = (annotation.getArgumentValue("value") as? ArrayList<*>)
                ?.mapNotNull { it as? String } ?: emptyList()

            (names + values).forEach { extractedElements.add(ResponseHeaderElement(it)) }
        }

        return extractedElements
    }

}
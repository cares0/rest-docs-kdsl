package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.HandlerElement
import io.github.cares0.restdocskdsl.core.RequestPartElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import io.github.cares0.restdocskdsl.ksp.*
import org.springframework.web.bind.annotation.RequestPart

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class RequestPartElementResolver(
    private val logger: KSPLogger,
) : ValueParameterResolver {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val parameterTypeReference = kSValueParameter.type
        return kSValueParameter.containsAnnotation(RequestPart::class)
                && (parameterTypeReference.isJavaApi()
                || parameterTypeReference.isKotlinApi()
                || parameterTypeReference.isEnumType()
                || parameterTypeReference.isSpringApi())
    }

    override fun resolve(kSValueParameter: KSValueParameter): List<HandlerElement> {
        val extractedElements = mutableListOf<RequestPartElement>()

        kSValueParameter.annotations.forEach { annotation ->
            if (annotation.annotationType.getQualifiedName() != RequestPart::class.qualifiedName) return@forEach
            val name = annotation.getArgumentValue("name") as? String
            val value = annotation.getArgumentValue("value") as? String

            extractedElements.add(
                RequestPartElement(
                    if (!name.isNullOrBlank()) name
                    else if (!value.isNullOrBlank()) value
                    else kSValueParameter.name!!.asString()
                )
            )
        }

        return extractedElements
    }

}
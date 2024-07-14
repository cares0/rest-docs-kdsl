package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.HandlerElement
import io.github.cares0.restdocskdsl.core.QueryParameterElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import io.github.cares0.restdocskdsl.ksp.*
import org.springframework.web.bind.annotation.RequestParam

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class RequestParamAnnotationResolver(
    override val logger: KSPLogger,
) : ValueParameterResolver, QueryParameterElementExtractor {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val parameterTypeReference = kSValueParameter.type
        return kSValueParameter.containsAnnotation(RequestParam::class)
                && (parameterTypeReference.isJavaApi()
                || parameterTypeReference.isKotlinApi()
                || parameterTypeReference.isEnumType())

    }

    override fun resolve(kSValueParameter: KSValueParameter): List<HandlerElement> {
        val extractedElements = mutableListOf<QueryParameterElement>()

        kSValueParameter.annotations.forEach { annotation ->
            if (annotation.annotationType.getQualifiedName() != RequestParam::class.qualifiedName) return@forEach
            val name = annotation.getArgumentValue("name") as? String
            val value = annotation.getArgumentValue("value") as? String

            extractedElements.addAll(
                extractElement(
                    elementTypeReference = kSValueParameter.type,
                    elementName = if (!name.isNullOrBlank()) name
                    else if (!value.isNullOrBlank()) value
                    else kSValueParameter.name!!.asString()
                )
            )
        }

        return extractedElements
    }

}
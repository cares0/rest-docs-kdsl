package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.HandlerElement
import cares.restdocskdsl.core.RequestHeaderElement
import cares.restdocskdsl.ksp.*
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import org.springframework.web.bind.annotation.RequestHeader

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class RequestHeaderElementResolver(
    private val logger: KSPLogger,
) : ValueParameterResolver {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val parameterTypeReference = kSValueParameter.type
        return kSValueParameter.containsAnnotation(RequestHeader::class)
                && (parameterTypeReference.isJavaApi()
                || parameterTypeReference.isKotlinApi()
                || parameterTypeReference.isEnumType())
    }

    override fun resolve(kSValueParameter: KSValueParameter): List<HandlerElement> {
        val extractedElements = mutableListOf<RequestHeaderElement>()

        kSValueParameter.annotations.forEach { annotation ->
            if (annotation.annotationType.getQualifiedName() != RequestHeader::class.qualifiedName) return@forEach
            val name = annotation.getArgumentValue("name") as? String
            val value = annotation.getArgumentValue("value") as? String

            extractedElements.add(
                RequestHeaderElement(
                    if (!name.isNullOrBlank()) name
                    else if (!value.isNullOrBlank()) value
                    else kSValueParameter.name!!.asString()
                )
            )
        }

        return extractedElements
    }

}
package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.HandlerElement
import cares.restdocskdsl.ksp.*
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import org.springframework.stereotype.Component

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class EmptyAnnotationResolver(
    override val logger: KSPLogger,
) : ValueParameterResolver, QueryParameterElementExtractor {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val parameterTypeReference = kSValueParameter.type

        return parameterTypeReference.getQualifiedName() != null
                && kSValueParameter.annotations.toList().isEmpty()
                && !parameterTypeReference.isSpringApi()
                && !parameterTypeReference.isServletApi()
    }

    override fun resolve(kSValueParameter: KSValueParameter): List<HandlerElement> {
        return extractElement(kSValueParameter.type, kSValueParameter.name!!.asString())
    }
}
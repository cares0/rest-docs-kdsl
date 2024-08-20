package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.HandlerElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import io.github.cares0.restdocskdsl.ksp.containsAnnotation
import io.github.cares0.restdocskdsl.ksp.isEnumType
import io.github.cares0.restdocskdsl.ksp.isJavaApi
import io.github.cares0.restdocskdsl.ksp.isKotlinApi
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ModelAttribute

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class ModelAttributeAnnotationResolver(
    override val logger: KSPLogger,
) : ValueParameterResolver, QueryParameterElementExtractor {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val parameterTypeReference = kSValueParameter.type
        return kSValueParameter.containsAnnotation(ModelAttribute::class)
                && (!parameterTypeReference.isJavaApi()
                && !parameterTypeReference.isKotlinApi()
                && !parameterTypeReference.isEnumType())
    }

    override fun resolve(kSValueParameter: KSValueParameter): List<HandlerElement> {
        return extractElementWhenCustomObject(kSValueParameter.type)
    }

}
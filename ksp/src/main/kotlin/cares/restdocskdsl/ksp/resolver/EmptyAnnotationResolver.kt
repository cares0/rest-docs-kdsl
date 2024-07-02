package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.ksp.getQualifiedName
import cares.restdocskdsl.core.HandlerElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import org.springframework.stereotype.Component

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class EmptyAnnotationResolver(
    override val logger: KSPLogger,
) : ValueParameterResolver, QueryParameterElementExtractor {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val parameterTypeName = kSValueParameter.type.getQualifiedName()

        return parameterTypeName != null
                && kSValueParameter.annotations.toList().isEmpty()
                && !parameterTypeName.contains("org.springframework")
                && !parameterTypeName.contains("jakarta.servlet")
    }

    override fun resolve(kSValueParameter: KSValueParameter): List<HandlerElement> {
        return extractElement(kSValueParameter.type, kSValueParameter.name!!.asString())
    }
}
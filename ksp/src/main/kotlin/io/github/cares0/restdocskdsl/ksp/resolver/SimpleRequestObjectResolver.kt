package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.ksp.KotlinBuiltinName
import io.github.cares0.restdocskdsl.ksp.containsAnnotation
import io.github.cares0.restdocskdsl.ksp.getQualifiedName
import io.github.cares0.restdocskdsl.ksp.getSimpleName
import io.github.cares0.restdocskdsl.core.HandlerElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Component
class SimpleRequestObjectResolver(
    override val logger: KSPLogger,
) : ValueParameterResolver, RequestBodyElementExtractor {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val typeName = kSValueParameter.type.getQualifiedName()!!

        return kSValueParameter.containsAnnotation(RequestBody::class)
                && !KotlinBuiltinName.isBuiltinType(typeName)
                && !typeName.contains("org.springframework")
                && !typeName.contains("jakarta.servlet")
    }

    override fun resolve(kSValueParameter: KSValueParameter): List<HandlerElement> {
        return listOf(
            createBodyElement(
                name = kSValueParameter.name!!.getShortName(),
                nestedElementName = kSValueParameter.type.getSimpleName(),
                nestedElements = extractElements(kSValueParameter.type),
                isArrayBasedType = false,
                isRootElement = true,
            )
        )
    }

}
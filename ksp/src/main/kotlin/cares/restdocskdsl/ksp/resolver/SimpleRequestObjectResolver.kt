package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.ksp.KotlinBuiltinName
import cares.restdocskdsl.ksp.containsAnnotation
import cares.restdocskdsl.ksp.getQualifiedName
import cares.restdocskdsl.ksp.getSimpleName
import cares.restdocskdsl.core.HandlerElement
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
        val element = createBodyElement(
            name = kSValueParameter.name!!.getShortName(),
            nestedElementName = kSValueParameter.type.getSimpleName(),
            nestedElements = extractElements(kSValueParameter.type),
            isArrayBasedType = false,
            isRootElement = true,
        )

        return listOf(element)
    }

}
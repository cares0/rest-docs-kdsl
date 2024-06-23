package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.HandlerElement
import cares.restdocskdsl.ksp.*
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import org.springframework.web.bind.annotation.RequestBody

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class ArrayBasedRequestObjectResolver(
    override val logger: KSPLogger,
) : ValueParameterResolver, RequestBodyElementExtractor {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val typeName = kSValueParameter.type.getQualifiedName()!!

        return kSValueParameter.containsAnnotation(RequestBody::class)
                && KotlinBuiltinName.isArrayBasedType(typeName)
    }

    override fun resolve(kSValueParameter: KSValueParameter): List<HandlerElement> {
        val arrayTypeArgumentType = kSValueParameter.type.getTypeArguments().first().type!!

        return listOf(
            createBodyElement(
                name = kSValueParameter.name!!.getShortName(),
                nestedElementName = arrayTypeArgumentType.getSimpleName(),
                nestedElements = extractElements(arrayTypeArgumentType),
                isArrayBasedType = true,
                isRootElement = true,
            )
        )
    }

}
package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.HandlerElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSValueParameter
import io.github.cares0.restdocskdsl.ksp.*
import org.springframework.web.bind.annotation.RequestBody

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class ArrayBasedRequestObjectResolver(
    override val logger: KSPLogger,
) : ValueParameterResolver, RequestBodyElementExtractor {

    override fun isSupport(kSValueParameter: KSValueParameter): Boolean {
        val typeName = kSValueParameter.type.getQualifiedName()!!
        val isArrayBasedType = KotlinBuiltinName.isArrayBasedType(typeName)

        return if (!isArrayBasedType) false
        else {
            val typeArgumentTypeName =
                kSValueParameter.type.getTypeArguments().first().type!!.getQualifiedName()!!

            kSValueParameter.containsAnnotation(RequestBody::class)
                    && !KotlinBuiltinName.isBuiltinType(typeArgumentTypeName)
                    && !typeArgumentTypeName.contains("org.springframework")
                    && !typeArgumentTypeName.contains("jakarta.servlet")
        }
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
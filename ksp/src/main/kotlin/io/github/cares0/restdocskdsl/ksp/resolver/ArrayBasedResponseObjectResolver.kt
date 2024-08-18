package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.HandlerElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import io.github.cares0.restdocskdsl.ksp.*
import org.springframework.web.bind.annotation.RequestBody

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class ArrayBasedResponseObjectResolver(
    override val logger: KSPLogger,
) : FunctionResolver, ResponseBodyElementExtractor {

    override fun isSupport(kSFunction: KSFunctionDeclaration): Boolean {
        val returnType = kSFunction.returnType ?: return false
        val typeName = returnType.getQualifiedName()!!
        val isArrayBasedType = KotlinBuiltinName.isArrayBasedType(typeName)

        return if (!isArrayBasedType) false
        else {
            val typeArgumentTypeName =
                returnType.getTypeArguments().first().type!!.getQualifiedName()!!

            !KotlinBuiltinName.isBuiltinType(typeArgumentTypeName)
                    && !typeArgumentTypeName.contains("org.springframework")
                    && !typeArgumentTypeName.contains("jakarta.servlet")
        }
    }

    override fun resolve(kSFunction: KSFunctionDeclaration): List<HandlerElement> {
        val arrayTypeArgumentType = kSFunction.returnType!!.getTypeArguments().first().type!!

        return listOf(
            createBodyElement(
                name = kSFunction.simpleName.getShortName(),
                nestedElementName = arrayTypeArgumentType.getSimpleName(),
                nestedElements = extractElements(arrayTypeArgumentType),
                isArrayBasedType = true,
                isRootElement = true,
            )
        )
    }

}
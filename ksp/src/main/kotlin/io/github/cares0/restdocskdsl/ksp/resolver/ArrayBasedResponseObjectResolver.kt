package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.ksp.KotlinBuiltinName
import io.github.cares0.restdocskdsl.ksp.getQualifiedName
import io.github.cares0.restdocskdsl.ksp.getSimpleName
import io.github.cares0.restdocskdsl.ksp.getTypeArguments
import io.github.cares0.restdocskdsl.core.HandlerElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class ArrayBasedResponseObjectResolver(
    override val logger: KSPLogger,
) : FunctionResolver, ResponseBodyElementExtractor {

    override fun isSupport(kSFunction: KSFunctionDeclaration): Boolean {
        val returnType = kSFunction.returnType ?: return false
        val typeName = returnType.getQualifiedName()!!

        return KotlinBuiltinName.isArrayBasedType(typeName)
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
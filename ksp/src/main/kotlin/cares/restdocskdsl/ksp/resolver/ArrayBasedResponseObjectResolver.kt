package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.ksp.KotlinBuiltinName
import cares.restdocskdsl.ksp.getQualifiedName
import cares.restdocskdsl.ksp.getSimpleName
import cares.restdocskdsl.ksp.getTypeArguments
import cares.restdocskdsl.core.HandlerElement
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
                startWithArray = true,
                isRootElement = true,
            )
        )
    }

}
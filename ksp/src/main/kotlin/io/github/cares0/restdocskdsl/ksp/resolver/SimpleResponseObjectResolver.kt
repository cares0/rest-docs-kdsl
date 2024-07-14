package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.ksp.KotlinBuiltinName
import io.github.cares0.restdocskdsl.ksp.getQualifiedName
import io.github.cares0.restdocskdsl.ksp.getSimpleName
import io.github.cares0.restdocskdsl.core.HandlerElement
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class SimpleResponseObjectResolver(
    override val logger: KSPLogger,
) : FunctionResolver, ResponseBodyElementExtractor {

    override fun isSupport(kSFunction: KSFunctionDeclaration): Boolean {
        val returnType = kSFunction.returnType ?: return false
        val typeName = returnType.getQualifiedName()!!

        return !KotlinBuiltinName.isBuiltinType(typeName)
                && !typeName.contains("org.springframework")
                && !typeName.contains("jakarta.servlet")
    }

    override fun resolve(kSFunction: KSFunctionDeclaration): List<HandlerElement> {
        return listOf(
            createBodyElement(
                name = kSFunction.returnType!!.getSimpleName(),
                nestedElementName = kSFunction.returnType!!.getSimpleName(),
                nestedElements = extractElements(kSFunction.returnType!!),
                isArrayBasedType = false,
                isRootElement = true,
            )
        )
    }

}
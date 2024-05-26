package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.ksp.KotlinBuiltinName
import cares.restdocskdsl.ksp.getQualifiedName
import cares.restdocskdsl.ksp.getSimpleName
import cares.restdocskdsl.core.HandlerElement
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
                startWithArray = false,
                isRootElement = true,
            )
        )
    }

}
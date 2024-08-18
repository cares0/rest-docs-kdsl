package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.ksp.KotlinBuiltinName
import io.github.cares0.restdocskdsl.ksp.getQualifiedName
import io.github.cares0.restdocskdsl.ksp.getSimpleName
import io.github.cares0.restdocskdsl.core.HandlerElement
import io.github.cares0.restdocskdsl.ksp.getTypeArguments
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import org.springframework.http.ResponseEntity

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class ResponseEntityObjectResolver(
    override val logger: KSPLogger,
) : FunctionResolver, ResponseBodyElementExtractor {

    override fun isSupport(kSFunction: KSFunctionDeclaration): Boolean {
        val returnType = kSFunction.returnType ?: return false
        val typeName = returnType.getQualifiedName()!!

        return if (!KotlinBuiltinName.isBuiltinType(typeName)
            && typeName == ResponseEntity::class.qualifiedName
        ) {
            val typeArgumentTypeName =
                returnType.getTypeArguments().first().type!!.getQualifiedName()!!

            !KotlinBuiltinName.isBuiltinType(typeArgumentTypeName)
                    && !typeArgumentTypeName.contains("org.springframework")
                    && !typeArgumentTypeName.contains("jakarta.servlet")
        } else false
    }

    override fun resolve(kSFunction: KSFunctionDeclaration): List<HandlerElement> {
        val bodyType = kSFunction.returnType!!.getTypeArguments()[0].type!!

        return listOf(
            createBodyElement(
                name = bodyType.getSimpleName(),
                nestedElementName = bodyType.getSimpleName(),
                nestedElements = extractElements(bodyType),
                isArrayBasedType = false,
                isRootElement = true,
            )
        )
    }

}
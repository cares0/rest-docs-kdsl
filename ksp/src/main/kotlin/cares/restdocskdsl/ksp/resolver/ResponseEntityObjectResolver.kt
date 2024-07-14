package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.ksp.KotlinBuiltinName
import cares.restdocskdsl.ksp.getQualifiedName
import cares.restdocskdsl.ksp.getSimpleName
import cares.restdocskdsl.core.HandlerElement
import cares.restdocskdsl.ksp.getTypeArguments
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

        return !KotlinBuiltinName.isBuiltinType(typeName)
                && typeName == ResponseEntity::class.qualifiedName
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
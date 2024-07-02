package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.BodyElement
import cares.restdocskdsl.ksp.*
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.*

interface BodyElementExtractor {

    val logger: KSPLogger

    fun createBodyElement(
        name: String,
        nestedElementName: String? = null,
        nestedElements: List<BodyElement>? = null,
        isArrayBasedType: Boolean = false,
        isRootElement: Boolean = false,
    ): BodyElement

    fun extractElements(ksTypeReference: KSTypeReference): List<BodyElement> {

        return ksTypeReference
            .getAsKsClassDeclaration()
            .getDeclaredProperties()
            .mapNotNull { property -> handleProperty(ksTypeReference, property) }
            .toList()
    }

    fun handleProperty(
        parentTypeReference: KSTypeReference,
        property: KSPropertyDeclaration,
    ): BodyElement? {
        val propertyTypeName = property.type.getQualifiedName()!!

        return when {
            KotlinBuiltinName.isPrimitiveType(propertyTypeName)
                    || propertyTypeName == KotlinBuiltinName.MAP -> createBodyElement(property.simpleName.asString())
            KotlinBuiltinName.isArrayBasedType(propertyTypeName) -> handleArrayBasedType(property)
            property.isGenericType() -> handleGenericType(parentTypeReference, property)
            else -> handleCustomObjectType(property)
        }

    }

    fun handleArrayBasedType(
        property: KSPropertyDeclaration,
    ): BodyElement {
        val typeArgumentReference = property.type.getTypeArguments().first().type!!
        val nestedElements = extractElements(typeArgumentReference)

        return createBodyElement(
            name = property.simpleName.asString(),
            nestedElementName = if (nestedElements.isEmpty()) null else typeArgumentReference.getSimpleName(),
            nestedElements = nestedElements.ifEmpty { null },
            isArrayBasedType = true,
        )
    }

    fun handleGenericType(
        parentTypeReference: KSTypeReference,
        property: KSPropertyDeclaration,
    ): BodyElement {
        val typeArgumentReference = property.getActualTypeOfTypeArgument(parentTypeReference)!!
        return createBodyElement(
            name = property.simpleName.asString(),
            nestedElementName = typeArgumentReference.getSimpleName(),
            nestedElements = extractElements(typeArgumentReference)
        )
    }

    fun handleCustomObjectType(
        property: KSPropertyDeclaration,
    ): BodyElement? {
        val ksClassDeclaration = property.type.getAsIfKsClassDeclaration()
        if (ksClassDeclaration != null) {
            return when (ksClassDeclaration.classKind) {
                ClassKind.ENUM_CLASS -> createBodyElement(property.simpleName.asString())
                ClassKind.CLASS -> {
                    createBodyElement(
                        name = property.simpleName.asString(),
                        nestedElementName = property.type.getSimpleName(),
                        nestedElements = extractElements(property.type)
                    )
                }
                else -> return null
            }
        }
        return null
    }

}
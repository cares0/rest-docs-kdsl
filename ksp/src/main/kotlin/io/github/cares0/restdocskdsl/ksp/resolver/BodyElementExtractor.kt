package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.BodyElement
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.*
import io.github.cares0.restdocskdsl.ksp.*

interface BodyElementExtractor {

    val logger: KSPLogger

    fun createBodyElement(
        name: String,
        nestedElementName: String? = null,
        nestedElements: List<BodyElement>? = null,
        isArrayBasedType: Boolean = false,
        isRootElement: Boolean = false,
    ): BodyElement

    fun extractElements(
        ksTypeReference: KSTypeReference,
        visitedElements: MutableSet<String> = mutableSetOf(),
    ): List<BodyElement> {
        val typeName = ksTypeReference.getQualifiedName() ?: return emptyList()

        if (!visitedElements.add(typeName)) return emptyList()

        val elements = ksTypeReference
            .getAsKsClassDeclaration()
            .getDeclaredProperties()
            .mapNotNull { property ->
                handleProperty(
                    parentType = ksTypeReference,
                    property = property,
                    visitedElements = visitedElements,
                )
            }
            .toList()

        visitedElements.remove(typeName)

        return elements
    }

    fun handleProperty(
        parentType: KSTypeReference,
        property: KSPropertyDeclaration,
        visitedElements: MutableSet<String>,
    ): BodyElement? {
        var isArrayBasedType = false
        var baseType = unwrapIfGenericType(parentType, property)

        if (baseType.isArrayBasedType()) {
            baseType = baseType.getTypeArguments().first().type!!
            isArrayBasedType = true
        }

        return resolveBodyElement(
            type = baseType,
            elementName = property.simpleName.asString(),
            isArrayBasedType = isArrayBasedType,
            visitedElements = visitedElements,
        )
    }

    private fun unwrapIfGenericType(
        parentType: KSTypeReference,
        property: KSPropertyDeclaration,
    ): KSTypeReference {
        return if (property.isGenericType()) {
            property.getActualTypeOfTypeArgument(parentType)!!
        } else {
            property.type
        }
    }

    fun resolveBodyElement(
        type: KSTypeReference,
        elementName: String,
        isArrayBasedType: Boolean = false,
        visitedElements: MutableSet<String>,
    ): BodyElement? {
        return if (isNotNestedType(type)) {
            createBodyElement(
                name = elementName,
                isArrayBasedType = isArrayBasedType,
            )
        } else {
            resolveNestedType(
                propertyTypeReference = type,
                propertyName = elementName,
                isArrayBasedType = isArrayBasedType,
                visitedElements = visitedElements,
            )
        }
    }

    private fun isNotNestedType(type: KSTypeReference): Boolean {
        val typeName = type.getQualifiedName()!!
        return (KotlinBuiltinName.isPrimitiveType(typeName)
                || typeName == KotlinBuiltinName.MAP
                || type.isJavaTimeApi())
    }

    fun resolveNestedType(
        propertyTypeReference: KSTypeReference,
        propertyName: String,
        isArrayBasedType: Boolean = false,
        visitedElements: MutableSet<String>,
    ): BodyElement? {
        val ksClassDeclaration = propertyTypeReference.getAsIfKsClassDeclaration() ?: return null

        return when (ksClassDeclaration.classKind) {
            ClassKind.ENUM_CLASS -> createBodyElement(propertyName)
            ClassKind.CLASS -> {
                val nestedElements = extractElements(propertyTypeReference, visitedElements)
                createBodyElement(
                    name = propertyName,
                    nestedElementName = propertyTypeReference.getSimpleName(),
                    nestedElements = nestedElements.ifEmpty { null },
                    isArrayBasedType = isArrayBasedType,
                )
            }
            else -> null
        }
    }
}
package cares.restdocskdsl.ksp.writer

import cares.restdocskdsl.core.BodyElement
import cares.restdocskdsl.core.RequestBodyElement
import cares.restdocskdsl.core.ResponseBodyElement
import cares.restdocskdsl.dsl.*
import cares.restdocskdsl.ksp.KspApiSpecDescriptor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlin.reflect.KClass

abstract class BodyElementWriter(
    environment: SymbolProcessorEnvironment
) : KotlinPoetHandlerElementWriter<BodyElement>(environment) {

    override fun createApiComponentTypeSpec(
        descriptor: KspApiSpecDescriptor,
        filteredElements: List<BodyElement>,
    ): TypeSpec {
        val rootElement = filteredElements.first { it.isRootElement }
        val elementNameResolver = BodyElementNameResolver()

        return createBodyElementTypeSpec(
            descriptor = descriptor,
            element = rootElement,
            previousArrayCount = 0,
            elementNameResolver = elementNameResolver,
        )!!
    }

    private fun createBodyElementTypeSpec(
        descriptor: KspApiSpecDescriptor,
        element: BodyElement,
        previousArrayCount: Int,
        elementNameResolver: BodyElementNameResolver,
    ): TypeSpec? {
        return if (element.nestedElements != null) {
            val isRootStartWithArray: Boolean = element.isRootElement && element.isArrayBasedType

            val arrayCount = if (element.isArrayBasedType) previousArrayCount + 1 else previousArrayCount

            TypeSpec.objectBuilder(
                ClassName(
                    packageName = descriptor.packageName,
                    simpleNames = listOf(
                        if (element.isRootElement) getApiComponentObjectName(descriptor)
                        else elementNameResolver.getNextName(element.nestedElementName!!)
                    )
                )
            )
                .superclass(FieldComponent::class)
                .addSuperclassConstructorParameter("$isRootStartWithArray")
                .addTypes(resolveNestedClassesSpec(descriptor, element, arrayCount, elementNameResolver))
                .addProperties(
                    element.nestedElements!!.map {
                        generatePropertySpec(
                            bodyElement = it,
                            isFieldOfArray = element.isArrayBasedType,
                            previousArrayCount = arrayCount,
                            elementNameResolver = elementNameResolver
                        )
                    }
                )
                .addApiComponentInitializer(element.nestedElements!!)
                .build()
        } else null
    }

    private fun generatePropertySpec(
        bodyElement: BodyElement,
        isFieldOfArray: Boolean,
        previousArrayCount: Int,
        elementNameResolver: BodyElementNameResolver,
    ): PropertySpec {
        val valueKClass: KClass<*>
        val valueClassName: String
        val valueClassTypeParameterName: String?
        val defaultValue: CodeBlock
        if (!bodyElement.nestedElements.isNullOrEmpty()) {
            val elementName = elementNameResolver.getCurrentName(bodyElement.nestedElementName!!)

            valueKClass = if (bodyElement.isArrayBasedType) NestedArrayFieldValue::class
            else NestedFieldValue::class

            valueClassName = valueKClass.simpleName!!
            valueClassTypeParameterName = elementName
            defaultValue = CodeBlock.builder()
                .add(valueClassName)
                .add("(\"${bodyElement.name}\", ")
                .add("$elementName, ")
                .add("$isFieldOfArray, ")
                .add("$previousArrayCount)")
                .build()
        } else {
            valueKClass = FieldValue::class
            valueClassName = valueKClass.simpleName!!
            valueClassTypeParameterName = null
            defaultValue = CodeBlock.builder()
                .add(valueClassName)
                .add("(\"${bodyElement.name}\", ")
                .add("$isFieldOfArray, ")
                .add("$previousArrayCount)")
                .build()
        }

        val propertyTypeName = valueKClass.asTypeName()

        return PropertySpec.builder(
            name = bodyElement.name,
            type = if (valueClassTypeParameterName != null) {
                propertyTypeName.parameterizedBy(
                    ClassName(
                        packageName = "",
                        simpleNames = listOf(valueClassTypeParameterName)
                    )
                )
            } else propertyTypeName
        )
            .initializer(defaultValue)
            .build()
    }

    private fun resolveNestedClassesSpec(
        descriptor: KspApiSpecDescriptor,
        element: BodyElement,
        previousArrayCount: Int,
        elementNameResolver: BodyElementNameResolver,
    ): List<TypeSpec> {
        return element.nestedElements!!
            .mapNotNull { nestedElement ->
                if (nestedElement.nestedElements != null) {
                    createBodyElementTypeSpec(descriptor, nestedElement, previousArrayCount, elementNameResolver)
                } else null
            }
    }

}

class RequestBodyElementWriter(
    environment: SymbolProcessorEnvironment,
) : BodyElementWriter(environment) {
    override fun filterElements(descriptor: KspApiSpecDescriptor): List<BodyElement> {
        return descriptor.handlerElements.filterIsInstance<RequestBodyElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<RequestBodySnippetGenerator<*>> {
        return RequestBodySnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName.replaceFirstChar(Char::uppercase)}ApiRequestBody"
    }
}

class ResponseBodyElementWriter(
    environment: SymbolProcessorEnvironment
) : BodyElementWriter(environment) {
    override fun filterElements(descriptor: KspApiSpecDescriptor): List<BodyElement> {
        return descriptor.handlerElements.filterIsInstance<ResponseBodyElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<ResponseBodySnippetGenerator<*>> {
        return ResponseBodySnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName.replaceFirstChar(Char::uppercase)}ApiResponseBody"
    }
}
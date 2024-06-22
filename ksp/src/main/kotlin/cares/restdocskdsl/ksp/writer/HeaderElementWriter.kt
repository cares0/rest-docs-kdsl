package cares.restdocskdsl.ksp.writer

import cares.restdocskdsl.core.*
import cares.restdocskdsl.dsl.*
import cares.restdocskdsl.ksp.KspApiSpecDescriptor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.springframework.restdocs.headers.HeaderDescriptor
import kotlin.reflect.KClass

abstract class HeaderElementWriter(
    environment: SymbolProcessorEnvironment,
) : KspHandlerElementWriter<HeaderElement>(environment) {

    override fun createApiComponentTypeSpec(
        descriptor: KspApiSpecDescriptor,
        filteredElements: List<HeaderElement>,
    ): TypeSpec {
        return TypeSpec.objectBuilder(
            ClassName(
                packageName = descriptor.packageName,
                simpleNames = listOf(getApiComponentObjectName(descriptor))
            )
        )
            .superclass(ApiComponent::class.parameterizedBy(HeaderDescriptor::class))
            .addProperties(
                filteredElements.map { element ->
                    PropertySpec.builder(element.name, HeaderValue::class)
                        .initializer("${HeaderValue::class.simpleName}(\"${element.name}\")")
                        .build()
                }
            )
            .addApiComponentInitializer(filteredElements)
            .build()
    }
}

class RequestHeaderElementWriter(
    environment: SymbolProcessorEnvironment,
) : HeaderElementWriter(environment) {
    override fun filterElements(descriptor: KspApiSpecDescriptor): List<HeaderElement> {
        return descriptor.handlerElements.filterIsInstance<RequestHeaderElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<out SnippetGenerator> {
        return RequestHeaderSnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName.replaceFirstChar(Char::uppercase)}ApiRequestHeader"
    }

}

class ResponseHeaderElementWriter(
    environment: SymbolProcessorEnvironment,
) : HeaderElementWriter(environment) {
    override fun filterElements(descriptor: KspApiSpecDescriptor): List<HeaderElement> {
        return descriptor.handlerElements.filterIsInstance<ResponseHeaderElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<out SnippetGenerator> {
        return ResponseHeaderSnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName.replaceFirstChar(Char::uppercase)}ApiResponseHeader"
    }

}
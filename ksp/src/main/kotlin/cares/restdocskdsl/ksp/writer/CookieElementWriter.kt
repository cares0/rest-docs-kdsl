package cares.restdocskdsl.ksp.writer

import cares.restdocskdsl.core.*
import cares.restdocskdsl.dsl.*
import cares.restdocskdsl.ksp.KspApiSpecDescriptor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.springframework.restdocs.cookies.CookieDescriptor
import kotlin.reflect.KClass

abstract class CookieElementWriter(
    environment: SymbolProcessorEnvironment,
) : KspHandlerElementWriter<CookieElement>(environment) {

    override fun createApiComponentTypeSpec(
        descriptor: KspApiSpecDescriptor,
        filteredElements: List<CookieElement>,
    ): TypeSpec {
        return TypeSpec.objectBuilder(
            ClassName(
                packageName = descriptor.packageName,
                simpleNames = listOf(getApiComponentObjectName(descriptor))
            )
        )
            .superclass(ApiComponent::class.parameterizedBy(CookieDescriptor::class))
            .addProperties(
                filteredElements.map { element ->
                    PropertySpec.builder(element.name, CookieValue::class)
                        .initializer("${CookieValue::class.simpleName}(\"${element.name}\")")
                        .build()
                }
            )
            .addApiComponentInitializer(filteredElements)
            .build()
    }
}

class RequestCookieElementWriter(
    environment: SymbolProcessorEnvironment,
) : CookieElementWriter(environment) {
    override fun filterElements(descriptor: KspApiSpecDescriptor): List<CookieElement> {
        return descriptor.handlerElements.filterIsInstance<RequestCookieElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<out SnippetGenerator> {
        return RequestCookieSnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName.replaceFirstChar(Char::uppercase)}ApiRequestCookie"
    }

}

class ResponseCookieElementWriter(
    environment: SymbolProcessorEnvironment,
) : CookieElementWriter(environment) {
    override fun filterElements(descriptor: KspApiSpecDescriptor): List<CookieElement> {
        return descriptor.handlerElements.filterIsInstance<ResponseCookieElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<out SnippetGenerator> {
        return ResponseCookieSnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName.replaceFirstChar(Char::uppercase)}ApiResponseCookie"
    }

}
package io.github.cares0.restdocskdsl.ksp.writer

import io.github.cares0.restdocskdsl.core.RequestPartElement
import io.github.cares0.restdocskdsl.dsl.*
import io.github.cares0.restdocskdsl.ksp.KspApiSpecDescriptor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.springframework.restdocs.request.RequestPartDescriptor
import kotlin.reflect.KClass

class RequestPartElementWriter(
    environment: SymbolProcessorEnvironment,
) : KotlinPoetHandlerElementWriter<RequestPartElement>(environment) {

    override fun createApiComponentTypeSpec(
        descriptor: KspApiSpecDescriptor,
        filteredElements: List<RequestPartElement>,
    ): TypeSpec {
        return TypeSpec.objectBuilder(
            ClassName(
                packageName = descriptor.packageName,
                simpleNames = listOf(getApiComponentObjectName(descriptor))
            )
        )
            .superclass(ApiComponent::class.parameterizedBy(RequestPartDescriptor::class))
            .addProperties(
                filteredElements.map { element ->
                    PropertySpec.builder(element.name, RequestPartField::class)
                        .initializer("${RequestPartField::class.simpleName}(\"${element.name}\")")
                        .build()
                }
            )
            .addApiComponentInitializer(filteredElements)
            .build()
    }

    override fun filterElements(descriptor: KspApiSpecDescriptor): List<RequestPartElement> {
        return descriptor.handlerElements.filterIsInstance<RequestPartElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<out SnippetGenerator> {
        return RequestPartSnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName}ApiRequestPart"
    }

}
package io.github.cares0.restdocskdsl.ksp.writer

import io.github.cares0.restdocskdsl.core.QueryParameterElement
import io.github.cares0.restdocskdsl.dsl.*
import io.github.cares0.restdocskdsl.ksp.KspApiSpecDescriptor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.springframework.restdocs.request.ParameterDescriptor
import kotlin.reflect.KClass

class QueryParameterElementWriter(
    environment: SymbolProcessorEnvironment,
) : KotlinPoetHandlerElementWriter<QueryParameterElement>(environment) {

    override fun createApiComponentTypeSpec(
        descriptor: KspApiSpecDescriptor,
        filteredElements: List<QueryParameterElement>,
    ): TypeSpec {
        return TypeSpec.objectBuilder(
            ClassName(
                packageName = descriptor.packageName,
                simpleNames = listOf(getApiComponentObjectName(descriptor))
            )
        )
            .superclass(ApiComponent::class.parameterizedBy(ParameterDescriptor::class))
            .addProperties(
                filteredElements.map { element ->
                    PropertySpec.builder(element.name, QueryParameterField::class)
                        .initializer("${QueryParameterField::class.simpleName}(\"${element.name}\")")
                        .build()
                }
            )
            .addApiComponentInitializer(filteredElements)
            .build()
    }

    override fun filterElements(descriptor: KspApiSpecDescriptor): List<QueryParameterElement> {
        return descriptor.handlerElements.filterIsInstance<QueryParameterElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<out SnippetGenerator> {
        return QueryParameterSnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName}ApiQueryParameter"
    }

}
package cares.restdocskdsl.ksp.writer

import cares.restdocskdsl.core.PathVariableElement
import cares.restdocskdsl.dsl.*
import cares.restdocskdsl.ksp.KspApiSpecDescriptor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.springframework.restdocs.request.ParameterDescriptor
import kotlin.reflect.KClass

class PathVariableElementWriter(
    environment: SymbolProcessorEnvironment,
) : KotlinPoetHandlerElementWriter<PathVariableElement>(environment) {

    override fun createApiComponentTypeSpec(
        descriptor: KspApiSpecDescriptor,
        filteredElements: List<PathVariableElement>,
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
                    PropertySpec.builder(element.name, PathVariableValue::class)
                        .initializer("${PathVariableValue::class.simpleName}(\"${element.name}\")")
                        .build()
                }
            )
            .addApiComponentInitializer(filteredElements)
            .build()
    }

    override fun filterElements(descriptor: KspApiSpecDescriptor): List<PathVariableElement> {
        return descriptor.handlerElements.filterIsInstance<PathVariableElement>()
    }

    override fun getSnippetGeneratorClass(): KClass<out SnippetGenerator> {
        return PathVariableSnippetGenerator::class
    }

    override fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String {
        return "${descriptor.handlerName}ApiPathVariable"
    }

}
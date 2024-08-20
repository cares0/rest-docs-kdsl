package io.github.cares0.restdocskdsl.ksp.writer

import io.github.cares0.restdocskdsl.core.ApiSpecDescriptor
import io.github.cares0.restdocskdsl.core.HandlerElement
import io.github.cares0.restdocskdsl.core.HandlerElementWriter
import io.github.cares0.restdocskdsl.dsl.SnippetGenerator
import io.github.cares0.restdocskdsl.ksp.KspApiSpecDescriptor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions

/**
 * Build implementation of [io.github.cares0.restdocskdsl.dsl.ApiComponent] and properties of type [io.github.cares0.restdocskdsl.dsl.ApiValue] in the [KspApiSpecDescriptor] using Kotlin Poet.
 *
 * This code uses KotlinPoet to add properties of type `ApiValue` for each implementation
 * of `ApiComponent` from the list of [HandlerElement] described in [ApiSpecDescriptor].
 * Depending on the generated `ApiComponent` implementations,
 * it also implements the appropriate [SnippetGenerator] interface in the [io.github.cares0.restdocskdsl.dsl.ApiSpec] implementation to enable DSL usage.
 *
 * @author YoungJun Kim
 * @see KspApiSpecDescriptor
 * @see HandlerElement
*/
abstract class KotlinPoetHandlerElementWriter<T: HandlerElement>(
    environment: SymbolProcessorEnvironment
) : HandlerElementWriter {

    val logger = environment.logger

    override fun write(descriptor: ApiSpecDescriptor) {
        val descriptorKspImpl = descriptor as? KspApiSpecDescriptor
        if (descriptorKspImpl != null) write(descriptorKspImpl)
    }

    private fun write(descriptor: KspApiSpecDescriptor) {
        val filteredElements = filterElements(descriptor)

        if (filteredElements.isNotEmpty()) {
            addApiComponentTypeSpec(descriptor, filteredElements)
            addSnippetGeneratorImplementation(descriptor)
        }
    }

    abstract fun filterElements(descriptor: KspApiSpecDescriptor): List<T>

    private fun addApiComponentTypeSpec(descriptor: KspApiSpecDescriptor, filteredElements: List<T>) {
        descriptor.addApiComponentSpec(
            createApiComponentTypeSpec(
                descriptor = descriptor,
                filteredElements = filteredElements
            )
        )
    }

    fun TypeSpec.Builder.addApiComponentInitializer(
        elements: List<HandlerElement>
    ): TypeSpec.Builder {
        this.addInitializerBlock(
            CodeBlock.builder()
                .add("addValues(\n")
                .indent()
                .add(elements.joinToString(separator = ",\n") { "`${it.name}`" })
                .unindent()
                .add("\n)\n")
                .build()
        )
        return this
    }

    abstract fun createApiComponentTypeSpec(
        descriptor: KspApiSpecDescriptor,
        filteredElements: List<T>
    ): TypeSpec

    private fun addSnippetGeneratorImplementation(descriptor: KspApiSpecDescriptor) {
        val snippetGeneratorClass = getSnippetGeneratorClass()
        val abstractFunctionNames = snippetGeneratorClass.declaredFunctions
            .filter { it.isAbstract }
            .map { it.name }

        descriptor.apiSpecTypeBuilder
            .addSuperinterface(
                snippetGeneratorClass
                    .asTypeName()
                    .parameterizedBy(
                        ClassName(
                            packageName = descriptor.packageName,
                            simpleNames = listOf(getApiComponentObjectName(descriptor))
                        )
                    )
            )
            .addFunction(
                FunSpec.Companion.builder(abstractFunctionNames.first())
                    .addModifiers(KModifier.OVERRIDE)
                    .returns(
                        ClassName(
                            packageName = "",
                            simpleNames = listOf(getApiComponentObjectName(descriptor))
                        )
                    )
                    .addStatement("return ${getApiComponentObjectName(descriptor)}")
                    .build()
            )
    }

    abstract fun getSnippetGeneratorClass(): KClass<out SnippetGenerator>

    abstract fun getApiComponentObjectName(descriptor: KspApiSpecDescriptor): String

}
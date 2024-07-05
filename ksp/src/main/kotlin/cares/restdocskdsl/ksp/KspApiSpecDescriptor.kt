package cares.restdocskdsl.ksp

import cares.restdocskdsl.core.ApiSpecDescriptor
import cares.restdocskdsl.core.HandlerElement
import cares.restdocskdsl.dsl.*
import cares.restdocskdsl.ksp.resolver.*
import cares.restdocskdsl.ksp.writer.*
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.writeTo
import org.springframework.restdocs.snippet.Snippet

/**
 * A class that describes metadata for the API specification through KSP.
 * The [ApiSpec] implementation file is generated using KotlinPoet.
 *
 * @property [handlerDeclaration] The [KSFunctionDeclaration] of the handler resolved through KSP.
 * @property [handlerElements] A list of [HandlerElement] resolved from the [KSFunctionDeclaration] by [KspHandlerElementResolver].
 *  This list is used by [KotlinPoetHandlerElementWriter] to build [ApiValue] and [ApiComponent] implementations for each specific type.
 *
 * @author YoungJun Kim
 * @see KspHandlerElementResolver
 * @see KotlinPoetHandlerElementWriter
 */
class KspApiSpecDescriptor(
    val environment: SymbolProcessorEnvironment,
    val handlerDeclaration: KSFunctionDeclaration,
) : ApiSpecDescriptor {

    override val packageName: String = "${handlerDeclaration.packageName.asString()}.dsl"
    override val handlerName: String
    override val fileName: String
    override val handlerElements: MutableList<HandlerElement> = mutableListOf()
    private val codeGenerator = environment.codeGenerator
    private val apiSpecFileBuilder: FileSpec.Builder
    val apiSpecTypeBuilder: TypeSpec.Builder

    init {
        val duplicatedCount = generateSequence(0) { it + 1 }
            .filterNot(::checkFileAlreadyExist)
            .first()

        handlerName = if (duplicatedCount == 0) getOriginalHandlerName()
        else "${getOriginalHandlerName()}$duplicatedCount"

        fileName = "${handlerName}ApiSpec"

        apiSpecFileBuilder = FileSpec.builder(packageName, fileName)
        apiSpecTypeBuilder = TypeSpec.classBuilder(fileName)
            .addModifiers(KModifier.DATA)
            .addSuperinterface(ApiSpec::class)
    }

    private fun checkFileAlreadyExist(index: Int): Boolean {
        val tempFileName = buildString {
            append(getOriginalHandlerName())
            if (index > 0) append(index)
            append("ApiSpec")
        }

        val filePath = "${packageName.replace(".", "/")}/$tempFileName"

        return codeGenerator.generatedFile.any { it.canonicalPath.contains(filePath) }
    }

    private fun getOriginalHandlerName() =
        handlerDeclaration.simpleName.asString().replaceFirstChar(Char::uppercase)

    fun addHandlerElements(handlerElements: List<HandlerElement>) {
        this.handlerElements.addAll(handlerElements.distinct())
    }

    fun addApiComponentSpec(apiComponentTypeSpec: TypeSpec) {
        apiSpecFileBuilder.addType(apiComponentTypeSpec)
    }

    override fun writeApiSpecFile() {
        implementApiSpec()

        apiSpecFileBuilder.addType(apiSpecTypeBuilder.build())

        apiSpecFileBuilder.build().writeTo(codeGenerator, Dependencies.ALL_FILES)
    }

    private fun implementApiSpec() {
        overrideIdentifierProperty()
        overrideSnippetsProperty()
        overrideSnippetsFunctions()
    }

    private fun overrideIdentifierProperty() {
        apiSpecTypeBuilder
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(ParameterSpec.builder("identifier", String::class).build())
                    .build()
            )
            .addProperty(
                PropertySpec.builder("identifier", String::class)
                    .initializer("identifier")
                    .addModifiers(KModifier.OVERRIDE)
                    .build()
            )
    }

    private fun overrideSnippetsProperty() {
        apiSpecTypeBuilder
            .addProperty(
                PropertySpec.builder(
                    "snippets",
                    ClassName("kotlin.collections", "MutableList")
                        .parameterizedBy(Snippet::class.asTypeName())
                )
                    .addModifiers(KModifier.OVERRIDE)
                    .initializer("mutableListOf()")
                    .build()
            )
    }

    private fun overrideSnippetsFunctions() {
        apiSpecTypeBuilder
            .addFunction(
                FunSpec
                    .builder("addSnippet")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter(ParameterSpec.builder("generatedSnippet", Snippet::class).build())
                    .addStatement("this.snippets.add(generatedSnippet)")
                    .build()
            )
            .addFunction(
                FunSpec
                    .builder("addSnippets")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter(
                        ParameterSpec
                            .builder("generatedSnippets", List::class.parameterizedBy(Snippet::class))
                            .build()
                    )
                    .addStatement("this.snippets.addAll(generatedSnippets)")
                    .build()
            )
    }
}
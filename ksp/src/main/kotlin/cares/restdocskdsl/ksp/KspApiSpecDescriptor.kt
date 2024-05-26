package cares.restdocskdsl.ksp

import cares.restdocskdsl.core.ApiSpecDescriptor
import cares.restdocskdsl.core.HandlerElement
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

class KspApiSpecDescriptor(
    val environment: SymbolProcessorEnvironment,
    val handlerDeclaration: KSFunctionDeclaration,
) : ApiSpecDescriptor {

    override val handlerName: String = handlerDeclaration.simpleName.asString()
    override val packageName: String = "${handlerDeclaration.packageName.asString()}.kdsl"
    override val fileName: String = "${handlerName.replaceFirstChar(Char::uppercase)}Spec"
    override val handlerElements: MutableList<HandlerElement> = mutableListOf()

    fun addHandlerElements(handlerElements: List<HandlerElement>) {
        this.handlerElements.addAll(handlerElements.distinct())
    }

    override fun writeApiSpecFile() {
        TODO("After implementing HandlerElementWriter to write HandlerElement into the Descriptor, generate the corresponding file.")
    }

}
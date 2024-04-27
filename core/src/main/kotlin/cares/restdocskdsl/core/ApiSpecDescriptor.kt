package cares.restdocskdsl.core

interface ApiSpecDescriptor {

    val handlerName: String
    val packageName: String
    val fileName: String
    val handlerElements: List<HandlerElement>

    fun writeApiSpecFile()

}
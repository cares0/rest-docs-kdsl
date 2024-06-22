package cares.restdocskdsl.ksp.writer

import cares.restdocskdsl.core.ApiSpecDescriptor
import cares.restdocskdsl.core.HandlerElementWriter
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

class KspHandlerElementWriterComposite(
    environment: SymbolProcessorEnvironment
): HandlerElementWriter {

    private val delegates: List<HandlerElementWriter> = listOf(
        RequestBodyElementWriter(environment),
        ResponseBodyElementWriter(environment),
        RequestCookieElementWriter(environment),
        ResponseCookieElementWriter(environment),
        RequestHeaderElementWriter(environment),
        ResponseHeaderElementWriter(environment),
        RequestPartElementWriter(environment),
        QueryParameterElementWriter(environment),
        PathVariableElementWriter(environment)
    )

    override fun write(descriptor: ApiSpecDescriptor) {
        delegates.forEach { delegate -> delegate.write(descriptor) }
    }

}
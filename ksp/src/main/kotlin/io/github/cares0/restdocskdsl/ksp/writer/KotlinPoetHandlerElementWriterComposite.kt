package io.github.cares0.restdocskdsl.ksp.writer

import io.github.cares0.restdocskdsl.core.ApiSpecDescriptor
import io.github.cares0.restdocskdsl.core.HandlerElementWriter
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

class KotlinPoetHandlerElementWriterComposite(
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
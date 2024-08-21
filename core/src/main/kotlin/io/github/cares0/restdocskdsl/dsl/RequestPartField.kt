package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestPartDescriptor

class RequestPartField(
    override val name: String
) : ApiField<RequestPartDescriptor> {
    override val descriptor: RequestPartDescriptor = RequestDocumentation.partWithName(name)

    override fun means(description: String): ApiField<RequestPartDescriptor> {
        descriptor.description(description)
        return this
    }

    override fun typeOf(type: ApiFieldType): ApiField<RequestPartDescriptor> {
        descriptor.format(type::class.simpleName!!)
        return this
    }

    override fun formattedAs(format: String): ApiField<RequestPartDescriptor> {
        descriptor.format(format)
        return this
    }

    override fun isIgnored(isIgnored: Boolean): ApiField<RequestPartDescriptor> {
        if (isIgnored) descriptor.ignored()
        return this
    }

    override fun isOptional(isOptional: Boolean): ApiField<RequestPartDescriptor> {
        if (isOptional) descriptor.optional()
        return this
    }
}
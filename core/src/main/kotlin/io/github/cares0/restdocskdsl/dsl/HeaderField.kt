package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation

class HeaderField(
    override val name: String
) : ApiField<HeaderDescriptor> {
    override val descriptor: HeaderDescriptor = HeaderDocumentation.headerWithName(name)

    override fun means(description: String): ApiField<HeaderDescriptor> {
        descriptor.description(description)
        return this
    }

    override fun typeOf(type: ApiFieldType): ApiField<HeaderDescriptor> {
        descriptor.format(type::class.simpleName!!)
        return this
    }

    override fun formattedAs(format: String): ApiField<HeaderDescriptor> {
        descriptor.format(format)
        return this
    }

    override fun isIgnored(isIgnored: Boolean): ApiField<HeaderDescriptor> {
        if (isIgnored) descriptor.optional()
        return this
    }

    override fun isOptional(isOptional: Boolean): ApiField<HeaderDescriptor> {
        if (isOptional) descriptor.optional()
        return this
    }
}
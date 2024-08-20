package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation

class HeaderValue(
    override val name: String
) : ApiValue<HeaderDescriptor> {
    override val descriptor: HeaderDescriptor = HeaderDocumentation.headerWithName(name)

    override fun means(description: String): ApiValue<HeaderDescriptor> {
        descriptor.description(description)
        return this
    }

    override fun typeOf(type: ApiValueType): ApiValue<HeaderDescriptor> {
        descriptor.format(type::class.simpleName!!)
        return this
    }

    override fun formattedAs(format: String): ApiValue<HeaderDescriptor> {
        descriptor.format(format)
        return this
    }

    override fun isIgnored(isIgnored: Boolean): ApiValue<HeaderDescriptor> {
        if (isIgnored) descriptor.optional()
        return this
    }

    override fun isOptional(isOptional: Boolean): ApiValue<HeaderDescriptor> {
        if (isOptional) descriptor.optional()
        return this
    }
}
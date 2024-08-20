package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestPartDescriptor

class RequestPartValue(
    override val name: String
) : ApiValue<RequestPartDescriptor> {
    override val descriptor: RequestPartDescriptor = RequestDocumentation.partWithName(name)

    override fun means(description: String): ApiValue<RequestPartDescriptor> {
        descriptor.description(description)
        return this
    }

    override fun typeOf(type: ApiValueType): ApiValue<RequestPartDescriptor> {
        descriptor.format(type::class.simpleName!!)
        return this
    }

    override fun formattedAs(format: String): ApiValue<RequestPartDescriptor> {
        descriptor.format(format)
        return this
    }

    override fun isIgnored(isIgnored: Boolean): ApiValue<RequestPartDescriptor> {
        if (isIgnored) descriptor.ignored()
        return this
    }

    override fun isOptional(isOptional: Boolean): ApiValue<RequestPartDescriptor> {
        if (isOptional) descriptor.optional()
        return this
    }
}
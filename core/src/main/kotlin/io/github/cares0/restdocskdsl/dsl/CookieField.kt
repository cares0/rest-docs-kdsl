package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.cookies.CookieDescriptor
import org.springframework.restdocs.cookies.CookieDocumentation

class CookieField(
    override val name: String
) : ApiField<CookieDescriptor> {
    override val descriptor: CookieDescriptor = CookieDocumentation.cookieWithName(name)

    override fun means(description: String): ApiField<CookieDescriptor> {
        descriptor.description(description)
        return this
    }

    override fun typeOf(type: ApiFieldType): ApiField<CookieDescriptor> {
        descriptor.format(type::class.simpleName!!)
        return this
    }

    override fun formattedAs(format: String): ApiField<CookieDescriptor> {
        descriptor.format(format)
        return this
    }

    override fun isIgnored(isIgnored: Boolean): ApiField<CookieDescriptor> {
        if (isIgnored) descriptor.ignored()
        return this
    }

    override fun isOptional(isOptional: Boolean): ApiField<CookieDescriptor> {
        if (isOptional) descriptor.optional()
        return this
    }
}
package cares.restdocskdsl.dsl

import org.springframework.restdocs.cookies.CookieDescriptor
import org.springframework.restdocs.cookies.CookieDocumentation

class CookieValue(
    override val name: String
) : ApiValue<CookieDescriptor> {
    override val descriptor: CookieDescriptor = CookieDocumentation.cookieWithName(name)

    override fun means(description: String): ApiValue<CookieDescriptor> {
        descriptor.description(description)
        return this
    }

    override fun typeOf(type: ApiValueType): ApiValue<CookieDescriptor> {
        descriptor.format(type::class.simpleName!!)
        return this
    }

    override fun formattedAs(format: String): ApiValue<CookieDescriptor> {
        descriptor.format(format)
        return this
    }

    override fun isIgnored(isIgnored: Boolean): ApiValue<CookieDescriptor> {
        if (isIgnored) descriptor.ignored()
        return this
    }

    override fun isOptional(isOptional: Boolean): ApiValue<CookieDescriptor> {
        if (isOptional) descriptor.optional()
        return this
    }
}
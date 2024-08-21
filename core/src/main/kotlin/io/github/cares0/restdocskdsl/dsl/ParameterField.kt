package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation

abstract class ParameterField(
    final override val name: String
): ApiField<ParameterDescriptor> {

    override val descriptor: ParameterDescriptor = RequestDocumentation.parameterWithName(name)

    override infix fun means(description: String): ParameterField {
        descriptor.description(description)
        return this
    }

    override infix fun typeOf(type: ApiFieldType): ParameterField {
        descriptor.format(type::class.simpleName!!)
        return this
    }

    override infix fun formattedAs(format: String): ParameterField {
        descriptor.format(format)
        return this
    }

    override infix fun isIgnored(isIgnored: Boolean): ParameterField {
        if (isIgnored) descriptor.ignored()
        return this
    }

    override infix fun isOptional(isOptional: Boolean): ParameterField {
        if (isOptional) descriptor.optional()
        return this
    }

}

class QueryParameterField(
    name: String,
) : ParameterField(name)

class PathVariableField(
    name: String,
) : ParameterField(name)
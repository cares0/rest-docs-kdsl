package cares.restdocskdsl.dsl

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation

abstract class ParameterValue(
    final override val name: String
): ApiValue<ParameterDescriptor> {

    override val descriptor: ParameterDescriptor = RequestDocumentation.parameterWithName(name)

    override infix fun means(description: String): ParameterValue {
        descriptor.description(description)
        return this
    }

    override infix fun typeOf(type: ApiValueType): ParameterValue {
        descriptor.format(type::class.simpleName!!)
        return this
    }

    override infix fun formattedAs(format: String): ParameterValue {
        descriptor.format(format)
        return this
    }

    override infix fun isIgnored(isIgnored: Boolean): ParameterValue {
        if (isIgnored) descriptor.ignored()
        return this
    }

    override infix fun isOptional(isOptional: Boolean): ParameterValue {
        if (isOptional) descriptor.optional()
        return this
    }

}

class QueryParameterValue(
    name: String,
) : ParameterValue(name)

class PathVariableValue(
    name: String,
) : ParameterValue(name)
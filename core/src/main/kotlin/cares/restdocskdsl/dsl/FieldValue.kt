package cares.restdocskdsl.dsl

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation

open class FieldValue(
    final override val name: String,
    isFieldOfArray: Boolean,
    previousArrayCount: Int,
): ApiValue<FieldDescriptor> {
    override val descriptor: FieldDescriptor = if (isFieldOfArray && previousArrayCount > 1) {
        PayloadDocumentation.fieldWithPath("[].$name")
    } else PayloadDocumentation.fieldWithPath(name)

    override infix fun means(description: String): FieldValue {
        descriptor.description(description)
        return this
    }

    override infix fun typeOf(type: ApiValueType): FieldValue {
        descriptor.type(type.fieldType)
        if (type.customFormat != null) descriptor.format(type.customFormat!!)
        return this
    }

    override infix fun formattedAs(format: String): FieldValue {
        descriptor.format(format)
        return this
    }

    override infix fun isIgnored(isIgnored: Boolean): FieldValue {
        if (isIgnored) descriptor.ignored()
        return this
    }

    override infix fun isOptional(isOptional: Boolean): FieldValue {
        if (isOptional) descriptor.optional()
        return this
    }
}

open class NestedFieldValue<E: ApiComponent<FieldDescriptor>>(
    name: String,
    val nestedElement: E,
    isFieldOfArray: Boolean,
    previousArrayCount: Int,
): FieldValue(name, isFieldOfArray, previousArrayCount) {
    override val descriptor: FieldDescriptor = if (isFieldOfArray && previousArrayCount > 1) {
        PayloadDocumentation.subsectionWithPath("[].$name")
    } else PayloadDocumentation.subsectionWithPath(name)

    open val beneathPathName: String = name

    override infix fun means(description: String): NestedFieldValue<E> {
        super.means(description)
        return this
    }

    override infix fun typeOf(type: ApiValueType): NestedFieldValue<E> {
        super.typeOf(type)
        return this
    }

    override infix fun formattedAs(format: String): NestedFieldValue<E> {
        super.formattedAs(format)
        return this
    }

    override infix fun isIgnored(isIgnored: Boolean): NestedFieldValue<E> {
        super.isIgnored(isIgnored)
        return this
    }

    override infix fun isOptional(isOptional: Boolean): NestedFieldValue<E> {
        super.isOptional(isOptional)
        return this
    }

    infix fun of(nestedFieldDetailDsl: E.() -> Unit) {
        nestedElement.nestedFieldDetailDsl()
    }
}

class NestedArrayFieldValue<E: ApiComponent<FieldDescriptor>>(
    name: String,
    nestedElement: E,
    isFieldOfArray: Boolean,
    previousArrayCount: Int,
): NestedFieldValue<E>(name, nestedElement, isFieldOfArray, previousArrayCount) {
    override val beneathPathName: String = "$name.[]"
}

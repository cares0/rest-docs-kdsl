package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation

open class JsonField(
    final override val name: String,
    isFieldOfArray: Boolean,
    previousArrayCount: Int,
): ApiField<FieldDescriptor> {
    override val descriptor: FieldDescriptor = if (isFieldOfArray && previousArrayCount > 1) {
        PayloadDocumentation.fieldWithPath("[].$name")
    } else PayloadDocumentation.fieldWithPath(name)

    override infix fun means(description: String): JsonField {
        descriptor.description(description)
        return this
    }

    override infix fun typeOf(type: ApiFieldType): JsonField {
        descriptor.type(type.fieldType)
        if (type.customFormat != null) descriptor.format(type.customFormat!!)
        return this
    }

    override infix fun formattedAs(format: String): JsonField {
        descriptor.format(format)
        return this
    }

    override infix fun isIgnored(isIgnored: Boolean): JsonField {
        if (isIgnored) descriptor.ignored()
        return this
    }

    override infix fun isOptional(isOptional: Boolean): JsonField {
        if (isOptional) descriptor.optional()
        return this
    }
}

open class NestedJsonField<E: ApiComponent<FieldDescriptor>>(
    name: String,
    val nestedElement: E,
    isFieldOfArray: Boolean,
    previousArrayCount: Int,
): JsonField(name, isFieldOfArray, previousArrayCount) {
    override val descriptor: FieldDescriptor = if (isFieldOfArray && previousArrayCount > 1) {
        PayloadDocumentation.subsectionWithPath("[].$name")
    } else PayloadDocumentation.subsectionWithPath(name)

    open val beneathPathName: String = name

    override infix fun means(description: String): NestedJsonField<E> {
        super.means(description)
        return this
    }

    override infix fun typeOf(type: ApiFieldType): NestedJsonField<E> {
        super.typeOf(type)
        return this
    }

    override infix fun formattedAs(format: String): NestedJsonField<E> {
        super.formattedAs(format)
        return this
    }

    override infix fun isIgnored(isIgnored: Boolean): NestedJsonField<E> {
        super.isIgnored(isIgnored)
        return this
    }

    override infix fun isOptional(isOptional: Boolean): NestedJsonField<E> {
        super.isOptional(isOptional)
        return this
    }

    infix fun of(nestedFieldDetailDsl: E.() -> Unit) {
        nestedElement.nestedFieldDetailDsl()
    }
}

class NestedArrayJsonField<E: ApiComponent<FieldDescriptor>>(
    name: String,
    nestedElement: E,
    isFieldOfArray: Boolean,
    previousArrayCount: Int,
): NestedJsonField<E>(name, nestedElement, isFieldOfArray, previousArrayCount) {
    override val beneathPathName: String = "$name.[]"
}

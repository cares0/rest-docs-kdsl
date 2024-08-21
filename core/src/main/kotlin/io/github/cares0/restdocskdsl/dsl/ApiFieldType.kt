package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.payload.JsonFieldType
import kotlin.reflect.KClass

/**
 * This is a wrapper class for [JsonFieldType], which wraps frequently used types for convenience.
 * It is used as an argument in the [ApiField.typeOf] function.
 *
 * @param customFormat specifies commonly used formats for each type.
 * @author YoungJun Kim
 */
open class ApiFieldType(
    val fieldType: JsonFieldType,
    open val customFormat: String? = null
)

data object ARRAY: ApiFieldType(JsonFieldType.ARRAY)
data object BOOLEAN: ApiFieldType(JsonFieldType.BOOLEAN)
data object OBJECT: ApiFieldType(JsonFieldType.OBJECT)
data object NUMBER: ApiFieldType(JsonFieldType.NUMBER)
data object NULL: ApiFieldType(JsonFieldType.NULL)
data object STRING: ApiFieldType(JsonFieldType.STRING)
data object ANY: ApiFieldType(JsonFieldType.VARIES)
data object DATE: ApiFieldType(JsonFieldType.STRING, "yyyy-MM-dd")
data object DATETIME: ApiFieldType(JsonFieldType.STRING, "yyyy-MM-ddTHH:mm:ss")

/**
 * Used for documenting Enum types.
 * @author YoungJun Kim
 */
data class ENUM<T : Enum<T>>(
    val enums: Collection<T>,
    override val customFormat: String? = enums.joinToString(", "),
) : ApiFieldType(JsonFieldType.STRING) {

    /**
     * This constructor is used when filtering specific values from the Enum
     * and include them in the format.
     */
    constructor(
        clazz: KClass<T>,
        filter: (T.() -> Boolean)? = null
    ) : this(
        enums = clazz.java.enumConstants.asList().let {
            if (filter != null) it.filter(filter) else it
        },
    )

}
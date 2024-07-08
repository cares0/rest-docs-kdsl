package cares.restdocskdsl.dsl

import org.springframework.restdocs.payload.JsonFieldType
import kotlin.reflect.KClass

/**
 * This is a wrapper class for [JsonFieldType], which wraps frequently used types for convenience.
 * It is used as an argument in the [ApiValue.typeOf] function.
 *
 * @param customFormat specifies commonly used formats for each type.
 * @author YoungJun Kim
 */
open class ApiValueType(
    val fieldType: JsonFieldType,
    open val customFormat: String? = null
)

data object ARRAY: ApiValueType(JsonFieldType.ARRAY)
data object BOOLEAN: ApiValueType(JsonFieldType.BOOLEAN)
data object OBJECT: ApiValueType(JsonFieldType.OBJECT)
data object NUMBER: ApiValueType(JsonFieldType.NUMBER)
data object NULL: ApiValueType(JsonFieldType.NULL)
data object STRING: ApiValueType(JsonFieldType.STRING)
data object ANY: ApiValueType(JsonFieldType.VARIES)
data object DATE: ApiValueType(JsonFieldType.STRING, "yyyy-MM-dd")
data object DATETIME: ApiValueType(JsonFieldType.STRING, "yyyy-MM-ddTHH:mm:ss")

/**
 * Used for documenting Enum types.
 * @author YoungJun Kim
 */
data class ENUM<T : Enum<T>>(
    val enums: Collection<T>,
    override val customFormat: String? = enums.joinToString(", "),
) : ApiValueType(JsonFieldType.STRING) {

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
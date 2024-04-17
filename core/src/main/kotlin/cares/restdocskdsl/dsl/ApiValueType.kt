package cares.restdocskdsl.dsl

import org.springframework.restdocs.payload.JsonFieldType
import kotlin.reflect.KClass

sealed class ApiValueType(
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

data class ENUM<T : Enum<T>>(
    val enums: Collection<T>,
    override val customFormat: String? = enums.joinToString(", "),
) : ApiValueType(JsonFieldType.STRING) {

    constructor(clazz:KClass<T>) : this(
        enums = clazz.java.enumConstants.asList(),
        customFormat = createPopupLink(camelToKebabCase(clazz.simpleName!!))
    )

}

fun camelToKebabCase(input: String): String {
    if (input.isEmpty()) return input

    val result = StringBuilder()
    input.forEachIndexed { index, char ->
        if (char.isUpperCase()) {
            if (index != 0) {
                result.append('-')
            }
            result.append(char.lowercaseChar())
        } else {
            result.append(char)
        }
    }
    return result.toString()
}

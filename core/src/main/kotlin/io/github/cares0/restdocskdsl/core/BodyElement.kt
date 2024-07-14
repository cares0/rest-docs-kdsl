package io.github.cares0.restdocskdsl.core

/**
 * Represents a single field in the JSON type request and response body.
 *
 * ## Example
 *```
 * data class Body {
 * 	val a: Nested
 *
 * 	data class Nested {
 * 		val b: List<Int>,
 * 		val c: String
 * 	}
 * }
 *```
 *
 * When serialized to JSON, the above object would look like this:
 *
 *```
 * {
 * 	"a": {
 * 		"b": [1, 2, 3],
 * 	    "c": "value"
 *     }
 * }
 *```
 * In this example, `BodyElement` instances can be created for the root JSON object `Body`,
 * as well as for the elements `a`, `b`, and `c` beneath it.
 * The `Body` class represents the object where the JSON starts.
 * Therefore, `a` is an element nested within the root object in the JSON, and `b` and `c` are nested elements within `a`.
 *
 * @author YoungJun Kim
 */
abstract class BodyElement: HandlerElement {

    /**
     * The name of the nested element. In the example above,
     * if the `BodyElement` represents `a`, `nestedElementName` would be "Nested",
     * which is the name of the object where `b` and `c` are declared.
     */
    abstract val nestedElementName: String?

    /**
     * A list of all `BodyElement` types representing the nested elements.
     * In the example above, if the `BodyElement` represents `a`, `nestedElements` would contain `BodyElement` instances
     * representing `b` and `c`.
     */
    abstract val nestedElements: List<BodyElement>?

    /**
     * Indicates whether the element is of an array-based type (e.g., List, Array, Set) when serialized to JSON.
     * In the example, this is true only for the `BodyElement` representing `b`.
     */
    abstract val isArrayBasedType: Boolean

    /**
     * Indicates whether the element is a top-level field.
     * In the example, if the `BodyElement` represents the `Body` class, this would be true,
     * as it represents the object where the JSON starts.
     */
    abstract val isRootElement: Boolean
}

data class RequestBodyElement(
    override val name: String,
    override val nestedElementName: String? = null,
    override val nestedElements: List<BodyElement>? = null,
    override val isArrayBasedType: Boolean = false,
    override val isRootElement: Boolean = false,
) : BodyElement()

data class ResponseBodyElement(
    override val name: String,
    override val nestedElementName: String? = null,
    override val nestedElements: List<BodyElement>? = null,
    override val isArrayBasedType: Boolean = false,
    override val isRootElement: Boolean = false,
) : BodyElement()
package cares.restdocskdsl.core

/**
 * An element that are bound to each request and response, as specified in the parameter and return type of handler.
 * ```
 * @GetMapping
 * fun someApi(
 * 	@RequestParam param1: String,
 * ) {
 * 	... do something
 * }
 * ```
 * In a handler function like the one above, `param1`,
 * which is bound to the request parameter, is converted into a `HandlerElement`.
 * It is then used when declaring a DSL object of type `ApiValue`.
 *
 * @property name The name of the element. In the example above,
 * the `HandlerElement` corresponding to `param1` will have its name set to "param1".
 * @author YoungJun Kim
 */
interface HandlerElement {
    val name: String
}
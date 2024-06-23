package cares.restdocskdsl.core

abstract class BodyElement: HandlerElement {
    abstract val nestedElementName: String?
    abstract val nestedElements: List<BodyElement>?
    abstract val isArrayBasedType: Boolean
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
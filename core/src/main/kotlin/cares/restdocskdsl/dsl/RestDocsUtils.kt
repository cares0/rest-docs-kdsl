package cares.restdocskdsl.dsl

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.snippet.AbstractDescriptor
import org.springframework.restdocs.snippet.Attributes
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl

fun AbstractDescriptor<*>.format(value: Any): AbstractDescriptor<*> =
    this.attributes(Attributes.key("format").value(value))

fun createPopupLink(popupFileName: String, popupDescription: String? = null): String {
    return "link:popup/$popupFileName.html[${popupDescription ?: "Available Value"},role=\"popup\"]"
}

fun <T: ApiSpec> MockMvcResultHandlersDsl.document(apiSpec: T, dsl: T.() -> Unit) {
    apiSpec.dsl()
    this.handle(
        MockMvcRestDocumentation.document(
            apiSpec.identifier,
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            *apiSpec.snippets.toTypedArray()
        )
    )
}
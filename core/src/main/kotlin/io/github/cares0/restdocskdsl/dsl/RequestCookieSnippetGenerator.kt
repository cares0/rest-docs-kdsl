package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.cookies.CookieDescriptor
import org.springframework.restdocs.cookies.CookieDocumentation
import org.springframework.restdocs.snippet.Snippet

interface RequestCookieSnippetGenerator<C: ApiComponent<CookieDescriptor>> : SnippetGenerator {

    fun requestCookies(dsl: C.() -> Unit) {
        val requestCookieComponent = getRequestCookieApiComponent()
        requestCookieComponent.dsl()
        addSnippet(generateRequestCookieSnippet(requestCookieComponent))
    }

    fun generateRequestCookieSnippet(
        element: ApiComponent<CookieDescriptor>
    ): Snippet {
        return CookieDocumentation.requestCookies(
            element.apiFields.map { it.descriptor }
        )
    }

    fun getRequestCookieApiComponent(): C


}
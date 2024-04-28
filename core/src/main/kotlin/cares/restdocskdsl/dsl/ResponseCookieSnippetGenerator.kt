package cares.restdocskdsl.dsl

import org.springframework.restdocs.cookies.CookieDescriptor
import org.springframework.restdocs.cookies.CookieDocumentation
import org.springframework.restdocs.snippet.Snippet

interface ResponseCookieSnippetGenerator<C: ApiComponent<CookieDescriptor>> : SnippetGenerator {

    fun responseCookie(dsl: C.() -> Unit) {
        val responseCookieComponent = getResponseCookieApiComponent()
        responseCookieComponent.dsl()
        addSnippet(generateResponseCookieSnippet(responseCookieComponent))
    }

    fun generateResponseCookieSnippet(
        element: ApiComponent<CookieDescriptor>
    ): Snippet {
        return CookieDocumentation.responseCookies(
            element.apiValues.map { it.descriptor }
        )
    }

    fun getResponseCookieApiComponent(): C


}
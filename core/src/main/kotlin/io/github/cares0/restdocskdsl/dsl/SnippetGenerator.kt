package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.snippet.Snippet

/**
 * Converts ApiComponent into a Snippet.
 *
 * Each implementation transforms [ApiComponent] into a suitable [Snippet] implementation based on the HTTP component being documented.
 * The converted `Snippet` is then added to the [ApiSpec.snippets], allowing it to be utilized when calling the DSL in the [document] function.
 *
 * @see ApiComponent
 * @see ApiSpec
 * @author YoungJun Kim
 */
interface SnippetGenerator {

    fun addSnippet(generatedSnippet: Snippet)

    fun addSnippets(generatedSnippets: List<Snippet>)

}
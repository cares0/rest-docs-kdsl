package cares.restdocskdsl.dsl

import org.springframework.restdocs.snippet.Snippet

interface SnippetGenerator {

    fun addSnippet(generatedSnippet: Snippet)

    fun addSnippets(generatedSnippets: List<Snippet>)

}
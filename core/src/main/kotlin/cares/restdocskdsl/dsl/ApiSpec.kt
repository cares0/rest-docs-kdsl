package cares.restdocskdsl.dsl

import org.springframework.restdocs.snippet.Snippet

interface ApiSpec {
    val identifier: String
    val snippets: MutableList<Snippet>
}
package io.github.cares0.restdocskdsl.annotation

import org.springframework.core.annotation.AliasFor

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResponseCookieDocs(

    @get: AliasFor("name")
    val value: Array<String> = [],

    @get: AliasFor("value")
    val name: Array<String> = [],

)
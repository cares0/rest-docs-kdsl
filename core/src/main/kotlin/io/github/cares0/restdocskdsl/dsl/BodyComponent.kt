package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.payload.FieldDescriptor

abstract class BodyComponent(
    val isParentStartWithArray: Boolean = false
) : ApiComponent<FieldDescriptor>()
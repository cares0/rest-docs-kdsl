package cares.restdocskdsl.dsl

import org.springframework.restdocs.payload.FieldDescriptor

abstract class FieldComponent(
    val isParentStartWithArray: Boolean = false
) : ApiComponent<FieldDescriptor>()
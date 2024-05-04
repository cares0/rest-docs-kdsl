package cares.restdocskdsl.ksp.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.visitor.KSDefaultVisitor

abstract class SymbolEnvironmentDefaultVisitor<D, R>(
    val environment: SymbolProcessorEnvironment
) : KSDefaultVisitor<D, R>() {

    val logger = environment.logger

}
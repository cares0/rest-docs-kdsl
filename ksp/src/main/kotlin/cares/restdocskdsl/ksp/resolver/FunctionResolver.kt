package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.HandlerElementResolver
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

interface FunctionResolver : HandlerElementResolver<KSFunctionDeclaration>
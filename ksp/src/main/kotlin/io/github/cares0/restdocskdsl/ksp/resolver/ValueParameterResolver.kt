package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.HandlerElementResolver
import com.google.devtools.ksp.symbol.KSValueParameter

interface ValueParameterResolver : HandlerElementResolver<KSValueParameter>
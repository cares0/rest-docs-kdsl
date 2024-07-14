package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.ApiSpecDescriptor
import io.github.cares0.restdocskdsl.core.HandlerElement
import io.github.cares0.restdocskdsl.core.HandlerElementResolver
import io.github.cares0.restdocskdsl.ksp.KspApiSpecDescriptor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

/**
 * Resolves [HandlerElement] from the symbol of a handler function using the KSP.
 *
 * @property valueParameterResolver Resolves [HandlerElement] through the parameter declarations of the handler function.
 * This typically resolves elements like request parameters, parts, body, etc.
 * @property functionResolver Resolves [HandlerElement] through the declarations of the handler function.
 * This mainly involves resolving elements based on annotations and the return type declared in the function.
 * This typically resolves response body, request and response headers, etc.
 *
 * @author YoungJun Kim
 * @see HandlerElement
 */
class KspHandlerElementResolver(
    environment: SymbolProcessorEnvironment,
) : HandlerElementResolver<ApiSpecDescriptor> {

    val logger = environment.logger

    private val valueParameterResolver = ValueParameterResolverComposite(
        environment,
        EmptyAnnotationResolver(logger),
        ModelAttributeAnnotationResolver(logger),
        RequestParamAnnotationResolver(logger),
        PathVariableAnnotationResolver(logger),
        RequestCookieElementResolver(logger),
        RequestHeaderElementResolver(logger),
        RequestPartElementResolver(logger),
        SimpleRequestObjectResolver(logger),
        ArrayBasedRequestObjectResolver(logger),
    )

    private val functionResolver = FunctionResolverComposite(
        environment,
        RequestCookieDocsAnnotationElementResolver(logger),
        io.github.cares0.restdocskdsl.ksp.resolver.RequestHeaderDocsAnnotationElementResolver(logger),
        SimpleResponseObjectResolver(logger),
        ArrayBasedResponseObjectResolver(logger),
        ResponseCookieElementResolver(logger),
        ResponseHeaderElementResolver(logger),
        ResponseEntityObjectResolver(logger),
    )

    override fun isSupport(node: ApiSpecDescriptor): Boolean {
        return node is KspApiSpecDescriptor
    }

    override fun resolve(node: ApiSpecDescriptor): List<HandlerElement> {
        return if (isSupport(node)) {
            val descriptor = node as KspApiSpecDescriptor

            val resolvedByParameter = descriptor.handlerDeclaration.parameters.flatMap(valueParameterResolver::resolve)
            val resolvedByFunction = functionResolver.resolve(descriptor.handlerDeclaration)

            descriptor.addHandlerElements(resolvedByParameter + resolvedByFunction)

            descriptor.handlerElements
        } else emptyList()
    }

}
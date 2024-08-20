package io.github.cares0.restdocskdsl.ksp

import com.google.devtools.ksp.symbol.*
import kotlin.reflect.KClass

fun KSTypeReference.getQualifiedName(): String? {
    return this.resolve().declaration.qualifiedName?.asString()
}

fun KSTypeReference.getSimpleName(): String {
    return this.resolve().declaration.simpleName.asString()
}

fun KSTypeReference.getTypeArguments(): List<KSTypeArgument> {
    return this.element?.typeArguments ?: emptyList()
}

fun KSTypeReference.getAsKsClassDeclaration(): KSClassDeclaration {
    return this.resolve().declaration as KSClassDeclaration
}

fun KSTypeReference.getAsIfKsClassDeclaration(): KSClassDeclaration? {
    return this.resolve().declaration as? KSClassDeclaration
}

fun KSTypeReference.isJavaApi(): Boolean {
    return this.getQualifiedName()?.startsWith("java") ?: false
}

fun KSTypeReference.isJavaTimeApi(): Boolean {
    return this.getQualifiedName()?.startsWith("java.time") ?: false
}

fun KSTypeReference.isKotlinApi(): Boolean {
    return this.getQualifiedName()?.startsWith("kotlin") ?: false
}

fun KSTypeReference.isSpringApi(): Boolean {
    return this.getQualifiedName()?.startsWith("org.springframework") ?: false
}

fun KSTypeReference.isServletApi(): Boolean {
    return this.getQualifiedName()?.startsWith("jakarta.servlet") ?: false
}

fun KSTypeReference.isEnumType(): Boolean {
    return this.getAsIfKsClassDeclaration()?.classKind == ClassKind.ENUM_CLASS
}

fun KSAnnotation.getArgumentValue(name: String): Any? {
    return this.arguments.firstOrNull { argument -> argument.name?.asString() == name}?.value
}

fun <T: Annotation> KSAnnotated.containsAnnotation(annotationClass: KClass<T>): Boolean {
    return this.annotations.firstOrNull {
        it.annotationType.getQualifiedName() == annotationClass.qualifiedName
    } != null
}

fun KSPropertyDeclaration.isGenericType(): Boolean {
    return this.type.resolve().declaration is KSTypeParameter
}

fun KSPropertyDeclaration.getActualTypeOfTypeArgument(
    typeReference: KSTypeReference
): KSTypeReference? {
    val propertyParentDeclaration = this.parentDeclaration ?: throw IllegalArgumentException()

    val firstIndex = propertyParentDeclaration.typeParameters.indexOfFirst {
        it.name.asString() == this.type.getSimpleName()
    }

    return if (firstIndex != -1) {
        typeReference.getTypeArguments()[firstIndex].type
    } else null
}

object KotlinBuiltinName {

    val ANY = "kotlin.Any"
    val NOTHING = "kotlin.Nothing"
    val UNIT = "kotlin.Unit"
    val NUMBER = "kotlin.Number"
    val BYTE = "kotlin.Byte"
    val SHORT = "kotlin.Short"
    val INT = "kotlin.Int"
    val LONG = "kotlin.Long"
    val FLOAT = "kotlin.Float"
    val DOUBLE = "kotlin.Double"
    val CHAR = "kotlin.Char"
    val BOOLEAN = "kotlin.Boolean"
    val STRING = "kotlin.String"
    val ITERABLE = "kotlin.collections.Iterable"
    val LIST = "kotlin.collections.List"
    val MAP = "kotlin.collections.Map"
    val SET = "kotlin.collections.Set"
    val ANNOTATION = "kotlin.Annotation"
    val ARRAY = "kotlin.Array"

    val ALL = listOf(
        ANY,
        NOTHING,
        UNIT,
        NUMBER,
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        CHAR,
        BOOLEAN,
        STRING,
        ITERABLE,
        LIST,
        MAP,
        SET,
        ANNOTATION,
        ARRAY,
    )

    val ARRAY_BASED_TYPES = listOf(
        ARRAY,
        LIST,
        SET,
    )

    val PRIMITIVES = listOf(
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        CHAR,
        BOOLEAN,
        STRING,
    )

    fun isBuiltinType(qualifiedName: String): Boolean {
        return ALL.contains(qualifiedName)
    }

    fun isPrimitiveType(qualifiedName: String): Boolean {
        return PRIMITIVES.contains(qualifiedName)
    }

    fun isArrayBasedType(qualifiedName: String): Boolean {
        return ARRAY_BASED_TYPES.contains(qualifiedName)
    }

}

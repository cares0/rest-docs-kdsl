package cares.restdocskdsl.example.controller

import cares.restdocskdsl.annotation.RequestCookieDocs
import cares.restdocskdsl.annotation.RequestHeaderDocs
import cares.restdocskdsl.annotation.ResponseCookieDocs
import cares.restdocskdsl.annotation.ResponseHeaderDocs
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class ExampleController {

    @PostMapping("/simple-usage")
    fun simpleUsage(
        @RequestHeader("X-Csrf-Token") csrfToken: String,
        @RequestBody request: SimpleRequest,
    ): SimpleResponse {
        return SimpleResponse.FIXTURE
    }

    data class SimpleRequest(
        val id: String,
        val password: Int,
    ) {
        companion object {
            val FIXTURE = SimpleRequest(
                id = "id",
                password = 1
            )
        }
    }

    data class SimpleResponse(
        val result: String,
        val status: UserStatus,
        val detail: SimpleDetail,
    ) {

        enum class UserStatus {
            ACTIVATED,
            DEACTIVATED,
        }

        data class SimpleDetail(
            val userId: String,
            val password: Int?,
            val profileMessage: String?,
        ) {
            companion object {
                val FIXTURE = SimpleDetail(
                    userId = "userId",
                    password = 1,
                    profileMessage = null,
                )
            }
        }
        companion object {
            val FIXTURE = SimpleResponse(
                result = "Success",
                status = UserStatus.ACTIVATED,
                detail = SimpleDetail.FIXTURE,
            )
        }
    }

    @PostMapping("/full-usage/{exampleId}")
    @RequestCookieDocs(["requestCookieAnnotation1", "requestCookieAnnotation2"])
    @RequestHeaderDocs(["requestHeaderAnnotation1", "requestHeaderAnnotation2"])
    @ResponseCookieDocs(["responseCookieAnnotation1", "responseCookieAnnotation2"])
    @ResponseHeaderDocs(["responseHeaderAnnotation1", "responseHeaderAnnotation2"])
    fun fullUsage(
        @PathVariable exampleId: String,
        @RequestParam intRequestParam: Int,
        @RequestParam("stringRequestParam") stringRequestParam: String,
        @RequestParam(name = "arrayRequestParam") arrayRequestParam: Array<String>,
        @ModelAttribute modelExample: ModelExample,
        emptyAnnotationObject: EmptyAnnotationModelExample,
        emptyAnnotationPrimitive: String,
        @CookieValue intCookieValue: Int,
        @CookieValue("stringCookieValue") stringCookieValueName: String,
        @RequestHeader("intHeaderValue") intHeader: Int,
        @RequestHeader("stringHeaderValue") stringHeader: String,
        @RequestBody requestBody: BodyExample,
    ): ResponseEntity<BodyExample> {
        val responseCookie1 = ResponseCookie.from("responseCookieAnnotation1").value("value").build().toString()
        val responseCookie2 = ResponseCookie.from("responseCookieAnnotation2").value("value").build().toString()

        return ResponseEntity.ok()
            .header("responseHeaderAnnotation1", "responseHeaderAnnotation1")
            .header("responseHeaderAnnotation2", "responseHeaderAnnotation2")
            .header("Set-Cookie", responseCookie1, responseCookie2)
            .body(BodyExample.FIXTURE)
    }

    data class ModelExample(
        val modelIntProperty: Int,
        val modelStringProperty: String,
        val modelDateTimeProperty: LocalDateTime,
        val modelEnumProperty: EnumExample,
        val modelListProperty: List<Int>,
        val modelArrayProperty: Array<Int>,
        val modelSetProperty: Set<Int>,
    )

    data class BodyExample(
        val bodyIntProperty: Int,
        val bodyStringProperty: String,
        val bodyDateTimeProperty: LocalDateTime,
        val bodyEnumProperty: EnumExample,
        val bodyObjectProperty: BodyObjectExample,
        val bodyIntListProperty: List<Int>,
        val bodyIntArrayProperty: Array<Int>,
        val bodyIntSetProperty: Set<Int>,
        val bodyObjectListProperty: List<BodyObjectExample>,
    ) {
        data class BodyObjectExample(
            val bodyObjectIntProperty: Int,
            val bodyObjectStringProperty: String,
            val bodyObjectIntListProperty: List<Int>,
            val bodyObjectNestedObjectProperty: BodyNestedObjectExample,
            val bodyObjectNestedObjectListProperty: List<BodyNestedObjectExample>,
        ) {
            data class BodyNestedObjectExample(
                val bodyNestedObjectIntProperty: Int,
                val bodyNestedObjectStringProperty: String,
                val bodyNestedObjectIntListProperty: List<Int>,
                val bodyNestedObjectMultiNestedObjectProperty: BodyMultiNestedObjectExample,
                val bodyNestedObjectMultiNestedObjectListProperty: List<BodyMultiNestedObjectExample>,
            ) {
                data class BodyMultiNestedObjectExample(
                    val bodyMultiNestedObjectIntProperty: Int,
                    val bodyMultiNestedObjectStringProperty: String,
                    val bodyMultiNestedObjectIntListProperty: List<Int>,
                ) {
                    companion object {
                        val FIXTURE = BodyMultiNestedObjectExample(
                            bodyMultiNestedObjectIntProperty = 0,
                            bodyMultiNestedObjectStringProperty = "",
                            bodyMultiNestedObjectIntListProperty = emptyList(),
                        )
                    }
                }
                companion object {
                    val FIXTURE = BodyNestedObjectExample(
                        bodyNestedObjectIntProperty = 0,
                        bodyNestedObjectStringProperty = "",
                        bodyNestedObjectIntListProperty = emptyList(),
                        bodyNestedObjectMultiNestedObjectProperty = BodyMultiNestedObjectExample.FIXTURE,
                        bodyNestedObjectMultiNestedObjectListProperty = listOf(
                            BodyMultiNestedObjectExample.FIXTURE,
                            BodyMultiNestedObjectExample.FIXTURE
                        ),
                    )
                }
            }
            companion object {
                val FIXTURE = BodyObjectExample(
                    bodyObjectIntProperty = 0,
                    bodyObjectStringProperty = "",
                    bodyObjectIntListProperty = emptyList(),
                    bodyObjectNestedObjectProperty = BodyNestedObjectExample.FIXTURE,
                    bodyObjectNestedObjectListProperty = listOf(
                        BodyNestedObjectExample.FIXTURE,
                        BodyNestedObjectExample.FIXTURE,
                        BodyNestedObjectExample.FIXTURE,
                    ),
                )
            }
        }
        companion object {
            val FIXTURE = BodyExample(
                bodyIntProperty = 0,
                bodyStringProperty = "",
                bodyDateTimeProperty = LocalDateTime.now(),
                bodyEnumProperty = EnumExample.A,
                bodyObjectProperty = BodyExample.BodyObjectExample.FIXTURE,
                bodyIntListProperty = emptyList(),
                bodyIntArrayProperty = emptyArray(),
                bodyIntSetProperty = emptySet(),
                bodyObjectListProperty = listOf(
                    BodyExample.BodyObjectExample.FIXTURE,
                    BodyExample.BodyObjectExample.FIXTURE,
                    BodyExample.BodyObjectExample.FIXTURE,
                ),
            )
        }
    }


    enum class EnumExample {
        A,
        B,
        C

        ;

        fun isB(): Boolean {
            return this == B
        }
    }


    data class EmptyAnnotationModelExample(
        val modelIntProperty: Int,
        val modelStringProperty: String,
    )

}
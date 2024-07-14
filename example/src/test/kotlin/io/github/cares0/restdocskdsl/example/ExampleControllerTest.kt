package io.github.cares0.restdocskdsl.example

import io.github.cares0.restdocskdsl.dsl.*
import io.github.cares0.restdocskdsl.example.controller.ExampleController
import io.github.cares0.restdocskdsl.example.controller.dsl.FullUsageApiSpec
import io.github.cares0.restdocskdsl.example.controller.dsl.SimpleUsageApiSpec
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.generate.RestDocumentationGenerator
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
class ExampleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    fun <T> createJson(request: T): String {
        return objectMapper.writeValueAsString(request)
    }

    @Test
    fun simpleUsage() {
        mockMvc.post("/simple-usage") {
            requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, "/simple-usage")
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = StandardCharsets.UTF_8.name()
            content = createJson(ExampleController.SimpleRequest.FIXTURE)
            header("X-Csrf-Token", "token")
        }.andExpectAll {
            status { isOk() }
        }.andDo {
            print()
            document(SimpleUsageApiSpec("simple-usage")) {
                requestHeader {
                    `X-Csrf-Token` means "CSRF Token" typeOf STRING
                }
                requestBody {
                    id means "User's id" typeOf STRING
                    password means "User's password" typeOf NUMBER
                }
                responseBody {
                    result means "Whether the login was successful" typeOf STRING formattedAs "Success or Failure"
                    status means "User's status" typeOf ENUM(ExampleController.SimpleResponse.UserStatus::class)
                    detail means "User's detail information" typeOf OBJECT of {
                        userId means "User's id" typeOf STRING
                        password means "User's password" typeOf STRING isIgnored true
                        profileMessage means "User's profile message" typeOf STRING isOptional true
                    }
                }
            }
        }
    }

    @Test
    fun fullUsage() {
        mockMvc.post("/full-usage/{exampleId}", "exampleId") {
            requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, "/full-usage/{exampleId}")
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = StandardCharsets.UTF_8.name()
            content = createJson(ExampleController.BodyExample.FIXTURE)
            param("intRequestParam", "0")
            param("stringRequestParam", "stringRequestParam")
            param("arrayRequestParam", "arrayRequestParam1")
            param("arrayRequestParam", "arrayRequestParam2")
            param("modelIntProperty", "0")
            param("modelStringProperty", "modelStringProperty")
            param("modelDateTimeProperty", LocalDateTime.now().toString())
            param("modelEnumProperty", ExampleController.EnumExample.A.toString())
            param("modelListProperty", "1")
            param("modelListProperty", "2")
            param("modelArrayProperty", "1")
            param("modelSetProperty", "2")
            param("emptyAnnotationPrimitive", "emptyAnnotationPrimitive")
            cookie(Cookie("intCookieValue", "1"))
            cookie(Cookie("stringCookieValue", "stringCookieValue"))
            cookie(Cookie("requestCookieAnnotation1", "requestCookieAnnotation1"))
            cookie(Cookie("requestCookieAnnotation2", "requestCookieAnnotation2"))
            header("intHeaderValue", "1")
            header("stringHeaderValue", "stringHeaderValue")
            header("requestHeaderAnnotation1", "requestHeaderAnnotation1")
            header("requestHeaderAnnotation2", "requestHeaderAnnotation2")
        }.andExpectAll {
            status { isOk() }
        }.andDo {
            print()
            document(FullUsageApiSpec("full-usage")) {
                pathVariable {
                    exampleId means "path-variable" typeOf STRING
                }
                requestCookie {
                    intCookieValue means "int-cookie-value" typeOf NUMBER
                    stringCookieValue means "string-cookie-value" typeOf STRING
                    requestCookieAnnotation1 means "request-cookie-annotation-1" typeOf STRING
                    requestCookieAnnotation2 means "request-cookie-annotation-2" typeOf STRING
                }
                requestHeader {
                    intHeaderValue means "intHeaderValue" typeOf NUMBER
                    stringHeaderValue means "stringHeaderValue" typeOf STRING
                    requestHeaderAnnotation1 means "requestHeaderAnnotation1" typeOf STRING
                    requestHeaderAnnotation2 means "requestHeaderAnnotation2" typeOf STRING
                }
                queryParameter {
                    intRequestParam means "intRequestParam" typeOf NUMBER
                    stringRequestParam means "stringRequestParam" typeOf STRING
                    arrayRequestParam means "arrayRequestParam" typeOf ARRAY
                    modelIntProperty means "modelIntProperty" typeOf NUMBER
                    modelStringProperty means "modelStringProperty" typeOf STRING
                    modelEnumProperty means "modelEnumProperty" typeOf ENUM(ExampleController.EnumExample::class)
                    modelDateTimeProperty means "modelStringProperty" typeOf DATETIME
                    modelListProperty means "modelListProperty" typeOf ARRAY
                    modelArrayProperty means "modelArrayProperty" typeOf ARRAY
                    modelSetProperty means "modelSetProperty" typeOf ARRAY
                    emptyAnnotationPrimitive means "emptyAnnotationPrimitive" typeOf STRING
                }
                requestBody {
                    bodyIntProperty means "bodyIntProperty" typeOf NUMBER
                    bodyStringProperty means "bodyStringProperty" typeOf STRING
                    bodyDateTimeProperty means "bodyDateTimeProperty" typeOf DATETIME
                    bodyEnumProperty means "bodyEnumProperty" typeOf ENUM(ExampleController.EnumExample::class) { !isB() }
                    bodyObjectProperty means "bodyObjectProperty" typeOf OBJECT of {
                        bodyObjectIntProperty means "bodyObjectIntProperty" typeOf NUMBER
                        bodyObjectStringProperty means "bodyObjectStringProperty" typeOf STRING
                        bodyObjectIntListProperty means "bodyObjectIntListProperty" typeOf ARRAY
                        bodyObjectNestedObjectProperty means "bodyObjectNestedObjectProperty" typeOf OBJECT of {
                            bodyNestedObjectIntProperty means "bodyNestedObjectIntProperty" typeOf NUMBER
                            bodyNestedObjectStringProperty means "bodyNestedObjectStringProperty" typeOf STRING
                            bodyNestedObjectIntListProperty means "bodyNestedObjectIntListProperty" typeOf ARRAY
                            bodyNestedObjectMultiNestedObjectProperty means "bodyNestedObjectMultiNestedObjectProperty" typeOf OBJECT of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                            bodyNestedObjectMultiNestedObjectListProperty means "bodyNestedObjectMultiNestedObjectListProperty" typeOf ARRAY of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                        }
                        bodyObjectNestedObjectListProperty means "bodyObjectNestedObjectListProperty" typeOf ARRAY of {
                            bodyNestedObjectIntProperty means "bodyNestedObjectIntProperty" typeOf NUMBER
                            bodyNestedObjectStringProperty means "bodyNestedObjectStringProperty" typeOf STRING
                            bodyNestedObjectIntListProperty means "bodyNestedObjectIntListProperty" typeOf ARRAY
                            bodyNestedObjectMultiNestedObjectProperty means "bodyNestedObjectMultiNestedObjectProperty" typeOf OBJECT of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                            bodyNestedObjectMultiNestedObjectListProperty means "bodyNestedObjectMultiNestedObjectListProperty" typeOf ARRAY of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                        }
                    }
                    bodyIntListProperty means "bodyIntListProperty" typeOf ARRAY
                    bodyIntArrayProperty means "bodyIntArrayProperty" typeOf ARRAY
                    bodyIntSetProperty means "bodyIntSetProperty" typeOf ARRAY
                    bodyObjectListProperty means "bodyObjectListProperty" typeOf ARRAY of {
                        bodyObjectIntProperty means "bodyObjectIntProperty" typeOf NUMBER
                        bodyObjectStringProperty means "bodyObjectStringProperty" typeOf STRING
                        bodyObjectIntListProperty means "bodyObjectIntListProperty" typeOf ARRAY
                        bodyObjectNestedObjectProperty means "bodyObjectNestedObjectProperty" typeOf OBJECT of {
                            bodyNestedObjectIntProperty means "bodyNestedObjectIntProperty" typeOf NUMBER
                            bodyNestedObjectStringProperty means "bodyNestedObjectStringProperty" typeOf STRING
                            bodyNestedObjectIntListProperty means "bodyNestedObjectIntListProperty" typeOf ARRAY
                            bodyNestedObjectMultiNestedObjectProperty means "bodyNestedObjectMultiNestedObjectProperty" typeOf OBJECT of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                            bodyNestedObjectMultiNestedObjectListProperty means "bodyNestedObjectMultiNestedObjectListProperty" typeOf ARRAY of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                        }
                        bodyObjectNestedObjectListProperty means "bodyObjectNestedObjectListProperty" typeOf ARRAY of {
                            bodyNestedObjectIntProperty means "bodyNestedObjectIntProperty" typeOf NUMBER
                            bodyNestedObjectStringProperty means "bodyNestedObjectStringProperty" typeOf STRING
                            bodyNestedObjectIntListProperty means "bodyNestedObjectIntListProperty" typeOf ARRAY
                            bodyNestedObjectMultiNestedObjectProperty means "bodyNestedObjectMultiNestedObjectProperty" typeOf OBJECT of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                            bodyNestedObjectMultiNestedObjectListProperty means "bodyNestedObjectMultiNestedObjectListProperty" typeOf ARRAY of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                        }
                    }
                }
                responseBody {
                    bodyIntProperty means "bodyIntProperty" typeOf NUMBER
                    bodyStringProperty means "bodyStringProperty" typeOf STRING
                    bodyDateTimeProperty means "bodyDateTimeProperty" typeOf DATETIME
                    bodyEnumProperty means "bodyEnumProperty" typeOf ENUM(ExampleController.EnumExample::class) { isB() }
                    bodyObjectProperty means "bodyObjectProperty" typeOf OBJECT of {
                        bodyObjectIntProperty means "bodyObjectIntProperty" typeOf NUMBER
                        bodyObjectStringProperty means "bodyObjectStringProperty" typeOf STRING
                        bodyObjectIntListProperty means "bodyObjectIntListProperty" typeOf ARRAY
                        bodyObjectNestedObjectProperty means "bodyObjectNestedObjectProperty" typeOf OBJECT of {
                            bodyNestedObjectIntProperty means "bodyNestedObjectIntProperty" typeOf NUMBER
                            bodyNestedObjectStringProperty means "bodyNestedObjectStringProperty" typeOf STRING
                            bodyNestedObjectIntListProperty means "bodyNestedObjectIntListProperty" typeOf ARRAY
                            bodyNestedObjectMultiNestedObjectProperty means "bodyNestedObjectMultiNestedObjectProperty" typeOf OBJECT of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                            bodyNestedObjectMultiNestedObjectListProperty means "bodyNestedObjectMultiNestedObjectListProperty" typeOf ARRAY of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                        }
                        bodyObjectNestedObjectListProperty means "bodyObjectNestedObjectListProperty" typeOf ARRAY of {
                            bodyNestedObjectIntProperty means "bodyNestedObjectIntProperty" typeOf NUMBER
                            bodyNestedObjectStringProperty means "bodyNestedObjectStringProperty" typeOf STRING
                            bodyNestedObjectIntListProperty means "bodyNestedObjectIntListProperty" typeOf ARRAY
                            bodyNestedObjectMultiNestedObjectProperty means "bodyNestedObjectMultiNestedObjectProperty" typeOf OBJECT of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                            bodyNestedObjectMultiNestedObjectListProperty means "bodyNestedObjectMultiNestedObjectListProperty" typeOf ARRAY of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                        }
                    }
                    bodyIntListProperty means "bodyIntListProperty" typeOf ARRAY
                    bodyIntArrayProperty means "bodyIntArrayProperty" typeOf ARRAY
                    bodyIntSetProperty means "bodyIntSetProperty" typeOf ARRAY
                    bodyObjectListProperty means "bodyObjectListProperty" typeOf ARRAY of {
                        bodyObjectIntProperty means "bodyObjectIntProperty" typeOf NUMBER
                        bodyObjectStringProperty means "bodyObjectStringProperty" typeOf STRING
                        bodyObjectIntListProperty means "bodyObjectIntListProperty" typeOf ARRAY
                        bodyObjectNestedObjectProperty means "bodyObjectNestedObjectProperty" typeOf OBJECT of {
                            bodyNestedObjectIntProperty means "bodyNestedObjectIntProperty" typeOf NUMBER
                            bodyNestedObjectStringProperty means "bodyNestedObjectStringProperty" typeOf STRING
                            bodyNestedObjectIntListProperty means "bodyNestedObjectIntListProperty" typeOf ARRAY
                            bodyNestedObjectMultiNestedObjectProperty means "bodyNestedObjectMultiNestedObjectProperty" typeOf OBJECT of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                            bodyNestedObjectMultiNestedObjectListProperty means "bodyNestedObjectMultiNestedObjectListProperty" typeOf ARRAY of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                        }
                        bodyObjectNestedObjectListProperty means "bodyObjectNestedObjectListProperty" typeOf ARRAY of {
                            bodyNestedObjectIntProperty means "bodyNestedObjectIntProperty" typeOf NUMBER
                            bodyNestedObjectStringProperty means "bodyNestedObjectStringProperty" typeOf STRING
                            bodyNestedObjectIntListProperty means "bodyNestedObjectIntListProperty" typeOf ARRAY
                            bodyNestedObjectMultiNestedObjectProperty means "bodyNestedObjectMultiNestedObjectProperty" typeOf OBJECT of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                            bodyNestedObjectMultiNestedObjectListProperty means "bodyNestedObjectMultiNestedObjectListProperty" typeOf ARRAY of {
                                bodyMultiNestedObjectIntProperty means "bodyMultiNestedObjectIntProperty" typeOf NUMBER
                                bodyMultiNestedObjectStringProperty means "bodyMultiNestedObjectStringProperty" typeOf STRING
                                bodyMultiNestedObjectIntListProperty means "bodyMultiNestedObjectIntListProperty" typeOf ARRAY
                            }
                        }
                    }
                }
                responseHeader {
                    responseHeaderAnnotation1 means "responseHeaderAnnotation1" typeOf STRING
                    responseHeaderAnnotation2 means "responseHeaderAnnotation2" typeOf STRING
                }
                responseCookie {
                    responseCookieAnnotation1 means "responseCookieAnnotation1" typeOf STRING
                    responseCookieAnnotation2 means "responseCookieAnnotation2" typeOf STRING
                }
            }
        }
    }

}
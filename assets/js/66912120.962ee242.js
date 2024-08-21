"use strict";(self.webpackChunkdocs=self.webpackChunkdocs||[]).push([[32],{9358:(e,n,s)=>{s.r(n),s.d(n,{assets:()=>l,contentTitle:()=>d,default:()=>p,frontMatter:()=>r,metadata:()=>o,toc:()=>c});var t=s(4848),i=s(8453);const r={sidebar_position:1},d="DSL Interface",o={id:"guides/dsl-interface",title:"DSL Interface",description:"This section describes the interfaces designed to provide DSL capabilities.",source:"@site/docs/guides/dsl-interface.md",sourceDirName:"guides",slug:"/guides/dsl-interface",permalink:"/rest-docs-kdsl/docs/guides/dsl-interface",draft:!1,unlisted:!1,tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1},sidebar:"tutorialSidebar",previous:{title:"Guides",permalink:"/rest-docs-kdsl/docs/category/guides"},next:{title:"DSL Generation Conditions",permalink:"/rest-docs-kdsl/docs/guides/dsl-generation-condition"}},l={},c=[{value:"Overall Structure",id:"overall-structure",level:2},{value:"HTTP Component DSL",id:"http-component-dsl",level:2},{value:"Component DSL",id:"component-dsl",level:2},{value:"Available Infix Functions",id:"available-infix-functions",level:3},{value:"Nested JSON Fields",id:"nested-json-fields",level:3}];function a(e){const n={a:"a",code:"code",h1:"h1",h2:"h2",h3:"h3",header:"header",img:"img",li:"li",p:"p",pre:"pre",table:"table",tbody:"tbody",td:"td",th:"th",thead:"thead",tr:"tr",ul:"ul",...(0,i.R)(),...e.components};return(0,t.jsxs)(t.Fragment,{children:[(0,t.jsx)(n.header,{children:(0,t.jsx)(n.h1,{id:"dsl-interface",children:"DSL Interface"})}),"\n",(0,t.jsx)(n.p,{children:"This section describes the interfaces designed to provide DSL capabilities."}),"\n",(0,t.jsx)(n.h2,{id:"overall-structure",children:"Overall Structure"}),"\n",(0,t.jsx)(n.p,{children:(0,t.jsx)(n.img,{alt:"structure",src:s(4913).A+"",width:"2438",height:"1200"})}),"\n",(0,t.jsx)(n.p,{children:"In Spring MVC, HTTP requests and responses are mapped via handlers.\nThe components that constitute HTTP requests and responses (e.g., query parameters, path variables, body)\nare mapped to the handler's annotations, parameters, and return types.\nSpring REST Docs helps document these components by describing their elements\nand integrating them into comprehensive documentation."}),"\n",(0,t.jsxs)(n.p,{children:["In Spring REST Docs, the elements of each component are represented as ",(0,t.jsx)(n.code,{children:"AbstractDescriptor"})," objects,\nwhich are then compiled into ",(0,t.jsx)(n.code,{children:"Snippet"}),"s.\nThe integrated ",(0,t.jsx)(n.code,{children:"Snippet"}),"s are handled by the ",(0,t.jsx)(n.code,{children:"document"})," function, which, together with the document's identifier,\nprocesses the API call results to finally generate snippets in formats like Asciidoc."]}),"\n",(0,t.jsx)(n.p,{children:"This library offers DSL capabilities while aligning with Spring REST Docs' structure\nby providing four main interfaces:"}),"\n",(0,t.jsxs)(n.ul,{children:["\n",(0,t.jsxs)(n.li,{children:[(0,t.jsx)(n.code,{children:"ApiValue"}),", corresponding to ",(0,t.jsx)(n.code,{children:"AbstractDescriptor"})]}),"\n",(0,t.jsxs)(n.li,{children:[(0,t.jsx)(n.code,{children:"ApiComponent"}),", corresponding to ",(0,t.jsx)(n.code,{children:"Snippet"})]}),"\n",(0,t.jsxs)(n.li,{children:[(0,t.jsx)(n.code,{children:"ApiSpec"}),", which integrates all ",(0,t.jsx)(n.code,{children:"Snippet"}),"s for a single API"]}),"\n",(0,t.jsxs)(n.li,{children:[(0,t.jsx)(n.code,{children:"SnippetGenerator"}),", which converts the written DSL into ",(0,t.jsx)(n.code,{children:"Snippet"}),"s."]}),"\n"]}),"\n",(0,t.jsxs)(n.p,{children:["Additionally, there's an interface called ",(0,t.jsx)(n.code,{children:"HandlerElement"})," that doesn\u2019t provide a DSL\nbut captures the necessary information from the handler\u2019s elements and converts them into ",(0,t.jsx)(n.code,{children:"ApiValue"}),"."]}),"\n",(0,t.jsx)(n.p,{children:"Let\u2019s look at these four interfaces using a simple HTTP API example:"}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{children:'HTTP Message\n\nRequest\nGET /some-api?id=abc&num=5 HTTP/1.1\nContent-Type: application/json\n\nResponse \nHTTP/1.1 200 OK\nContent-Type: application/json\nContent-Length: 85\n{\n    "data": "some data"\n}\n'})}),"\n",(0,t.jsx)(n.p,{children:"Given an API communicating with messages like the one above,\nthe components and elements extracted are as follows:"}),"\n",(0,t.jsxs)(n.table,{children:[(0,t.jsx)(n.thead,{children:(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.th,{children:"Component"}),(0,t.jsx)(n.th,{children:"Elements"})]})}),(0,t.jsxs)(n.tbody,{children:[(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Query Parameter"}),(0,t.jsx)(n.td,{children:"id, num"})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Response Body"}),(0,t.jsx)(n.td,{children:"data"})]})]})]}),"\n",(0,t.jsxs)(n.p,{children:[(0,t.jsx)(n.code,{children:"ApiValue"})," is the DSL interface that allows you to document the elements listed above."]}),"\n",(0,t.jsxs)(n.p,{children:["The ",(0,t.jsx)(n.code,{children:"ApiComponent"})," interface integrates these ",(0,t.jsx)(n.code,{children:"ApiValue"})," properties by component."]}),"\n",(0,t.jsxs)(n.p,{children:["The ",(0,t.jsx)(n.code,{children:"ApiComponent"})," generated is then handled by a suitable ",(0,t.jsx)(n.code,{children:"SnippetGenerator"})," in the ",(0,t.jsx)(n.code,{children:"ApiSpec"})," implementation,\nallowing the DSL functions to be invoked."]}),"\n",(0,t.jsxs)(n.p,{children:["Let\u2019s examine the ",(0,t.jsx)(n.code,{children:"ApiComponent"})," implementations and ",(0,t.jsx)(n.code,{children:"ApiValue"})," properties generated after mapping the API to a handler:"]}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{className:"language-kotlin",children:'public object ExampleApiResponseBody : FieldComponent(false) {  \n  public val `data`: FieldValue = FieldValue("data", false, 0)  \n  \n  init {  \n    addValues(  \n      `data`  \n    )  \n  }  \n}  \n  \npublic object ExampleApiQueryParameter : ApiComponent<ParameterDescriptor>() {  \n  public val id: QueryParameterValue = QueryParameterValue("id")  \n  \n  public val num: QueryParameterValue = QueryParameterValue("num")  \n  \n  init {  \n    addValues(  \n      `id`,  \n      `num`  \n    )  \n  }  \n}\n'})}),"\n",(0,t.jsxs)(n.p,{children:["Two ",(0,t.jsx)(n.code,{children:"ApiComponent"})," implementations, ",(0,t.jsx)(n.code,{children:"ExampleApiResponseBody"})," and ",(0,t.jsx)(n.code,{children:"ExampleApiQueryParameter"}),", were generated.\nEach holds the elements as ",(0,t.jsx)(n.code,{children:"ApiValue"}),"-typed properties corresponding to their respective components."]}),"\n",(0,t.jsxs)(n.p,{children:["These generated ",(0,t.jsx)(n.code,{children:"ApiComponent"}),"s are then declared as type parameters for ",(0,t.jsx)(n.code,{children:"SnippetGenerator"})," implementations,\nwhich are invoked in the ",(0,t.jsx)(n.code,{children:"ApiSpec"})," implementation to call the appropriate ",(0,t.jsx)(n.a,{href:"#http-component-dsl",children:"DSL functions"}),"."]}),"\n",(0,t.jsxs)(n.p,{children:["Let's now look at the ",(0,t.jsx)(n.code,{children:"ApiSpec"})," generated:"]}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{className:"language-kotlin",children:"public data class ExampleApiSpec(  \n  override val identifier: String,  \n) : ApiSpec,  \n    ResponseBodySnippetGenerator<ExampleApiResponseBody>,  \n    QueryParameterSnippetGenerator<ExampleApiQueryParameter> {  \n  override val snippets: MutableList<Snippet> = mutableListOf()  \n  \n  override fun getResponseBodyApiComponent(): ExampleApiResponseBody = ExampleApiResponseBody  \n  \n  override fun getQueryParameterApiComponent(): ExampleApiQueryParameter = ExampleApiQueryParameter  \n  \n  override fun addSnippet(generatedSnippet: Snippet) {  \n    this.snippets.add(generatedSnippet)  \n  }  \n  \n  override fun addSnippets(generatedSnippets: List<Snippet>) {  \n    this.snippets.addAll(generatedSnippets)  \n  }  \n}\n"})}),"\n",(0,t.jsxs)(n.p,{children:["This HTTP API only includes query parameters and a response body, so the ",(0,t.jsx)(n.code,{children:"ApiSpec"})," implementation,\n",(0,t.jsx)(n.code,{children:"ExampleApiSpec"}),", implements the corresponding ",(0,t.jsx)(n.code,{children:"SnippetGenerator"})," interfaces."]}),"\n",(0,t.jsxs)(n.p,{children:["Each ",(0,t.jsx)(n.code,{children:"SnippetGenerator"})," implementation has appropriately named functions.\nFor example, ",(0,t.jsx)(n.code,{children:"ResponseBodySnippetGenerator"})," has the ",(0,t.jsx)(n.code,{children:"responseBody"})," function,\nand ",(0,t.jsx)(n.code,{children:"QueryParameterSnippetGenerator"})," has the ",(0,t.jsx)(n.code,{children:"queryParameters"})," function:"]}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{className:"language-kotlin",children:"interface QueryParameterSnippetGenerator<C: ApiComponent<ParameterDescriptor>> : SnippetGenerator {\n\n    fun queryParameters(dsl: C.() -> Unit) {\n        ...\n    }\n    ...\n}\n\ninterface ResponseBodySnippetGenerator<C: FieldComponent> : SnippetGenerator {\n\n    fun responseBody(dsl: C.(element: C) -> Unit) {\n        ...\n    }\n    ...\n}\n"})}),"\n",(0,t.jsxs)(n.p,{children:["These functions take as parameters functions that have the type declared in the type parameter as the receiver.\nSince the type parameter is of ",(0,t.jsx)(n.code,{children:"ApiComponent"})," type and ",(0,t.jsx)(n.code,{children:"ApiComponent"})," contains ",(0,t.jsx)(n.code,{children:"ApiValue"})," properties,\nyou can call ",(0,t.jsx)(n.a,{href:"#component-dsl",children:"Component DSL"})," functions using those properties within the function block."]}),"\n",(0,t.jsx)(n.h2,{id:"http-component-dsl",children:"HTTP Component DSL"}),"\n",(0,t.jsxs)(n.p,{children:["As mentioned earlier, the HTTP components constituting requests and responses can be documented\nusing DSL through the functions of ",(0,t.jsx)(n.code,{children:"SnippetGenerator"})," implementations."]}),"\n",(0,t.jsxs)(n.table,{children:[(0,t.jsx)(n.thead,{children:(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.th,{children:"HTTP Component"}),(0,t.jsx)(n.th,{children:"Implementation"}),(0,t.jsx)(n.th,{children:"Function Name"})]})}),(0,t.jsxs)(n.tbody,{children:[(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Request Path Variables"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"PathVariableSnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"pathVariables"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Request Parameters"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"QueryParameterSnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"queryParameters"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Request Headers"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"RequestHeaderSnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"requestHeaders"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Request Cookies"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"RequestCookieSnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"requestCookies"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Request Parts"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"RequestPartSnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"requestParts"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Request Body"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"RequestBodySnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"requestBody"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Response Headers"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"ResponseHeaderSnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"responseHeaders"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Response Cookies"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"RequestCookieSnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"responseCookies"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:"Response Body"}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"ResponseBodySnippetGenerator"})}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"responseBody"})})]})]})]}),"\n",(0,t.jsxs)(n.p,{children:["If an API includes request headers and both request and response bodies,\nthe ",(0,t.jsx)(n.code,{children:"ApiSpec"})," implementation will implement ",(0,t.jsx)(n.code,{children:"RequestHeaderSnippetGenerator"}),", ",(0,t.jsx)(n.code,{children:"RequestBodySnippetGenerator"}),",\nand ",(0,t.jsx)(n.code,{children:"ResponseBodySnippetGenerator"}),". Consequently, you can call the DSL functions as follows:"]}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{className:"language-kotlin",children:'document(SimpleUsageApiSpec("simple-usage")) {\n    requestHeaders { ... }\n    requestBody { ... }\n    responseBody { ... }\n}\n'})}),"\n",(0,t.jsx)(n.h2,{id:"component-dsl",children:"Component DSL"}),"\n",(0,t.jsxs)(n.p,{children:["As mentioned, the elements constituting an HTTP component are handled as ",(0,t.jsx)(n.code,{children:"ApiValue"})," properties,\ncorresponding to Spring REST Docs' ",(0,t.jsx)(n.code,{children:"AbstractDescriptor"}),"."]}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{className:"language-kotlin",children:'responseBody { \n    result means "Whether the login was successful" typeOf STRING formattedAs "Success or Failure"\n    status means "User\'s status" typeOf ENUM(UserStatus::class)\n}\n'})}),"\n",(0,t.jsxs)(n.p,{children:["This example demonstrates how to document the ",(0,t.jsx)(n.code,{children:"result"})," and ",(0,t.jsx)(n.code,{children:"status"})," elements using the DSL.\nThe DSL uses infix functions, and all functions return ",(0,t.jsx)(n.code,{children:"ApiValue"})," itself, enabling chaining."]}),"\n",(0,t.jsx)(n.h3,{id:"available-infix-functions",children:"Available Infix Functions"}),"\n",(0,t.jsxs)(n.table,{children:[(0,t.jsx)(n.thead,{children:(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.th,{children:"Function Name"}),(0,t.jsx)(n.th,{children:"Description"}),(0,t.jsx)(n.th,{children:"Parameter Type"})]})}),(0,t.jsxs)(n.tbody,{children:[(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"means"})}),(0,t.jsxs)(n.td,{children:["Provides a description for the element. Corresponds to ",(0,t.jsx)(n.code,{children:"AbstractDescriptor.description"}),"."]}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"String"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"typeOf"})}),(0,t.jsxs)(n.td,{children:["Specifies the type of the element. Corresponds to ",(0,t.jsx)(n.code,{children:"org.springframework.restdocs.payload.FieldDescriptor.type"}),". If the type isn't ",(0,t.jsx)(n.code,{children:"FieldDescriptor"}),", it is passed to the ",(0,t.jsx)(n.code,{children:"formattedAs"})," function."]}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"ApiValueType"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"formattedAs"})}),(0,t.jsxs)(n.td,{children:["Adds the given ",(0,t.jsx)(n.code,{children:"format"})," string to the ",(0,t.jsx)(n.code,{children:"AbstractDescriptor.attributes"})," with the ",(0,t.jsx)(n.code,{children:"format"})," key."]}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"String"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"isIgnored"})}),(0,t.jsxs)(n.td,{children:["Ignores the element in the documentation. Corresponds to ",(0,t.jsx)(n.code,{children:"org.springframework.restdocs.snippet.IgnorableDescriptor.ignored"}),"."]}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"Boolean"})})]}),(0,t.jsxs)(n.tr,{children:[(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"isOptional"})}),(0,t.jsxs)(n.td,{children:["Marks the element as optional. Corresponds to ",(0,t.jsx)(n.code,{children:"optional()"})," in each ",(0,t.jsx)(n.code,{children:"AbstractDescriptor"})," implementation."]}),(0,t.jsx)(n.td,{children:(0,t.jsx)(n.code,{children:"Boolean"})})]})]})]}),"\n",(0,t.jsx)(n.h3,{id:"nested-json-fields",children:"Nested JSON Fields"}),"\n",(0,t.jsx)(n.p,{children:"JSON fields can be simple values like strings or numbers,\nbut they can also represent nested objects or arrays of objects."}),"\n",(0,t.jsxs)(n.p,{children:["Spring REST Docs allows you to document these nested fields using ",(0,t.jsx)(n.code,{children:"subsectionWithPath"})," or ",(0,t.jsx)(n.code,{children:"beneathPath"}),".\nHowever, this library provides a more elegant way to document nested fields."]}),"\n",(0,t.jsxs)(n.p,{children:["If a field is nested, it will be declared as a ",(0,t.jsx)(n.code,{children:"NestedFieldValue"})," or ",(0,t.jsx)(n.code,{children:"NestedArrayFieldValue"})," property.\n",(0,t.jsx)(n.code,{children:"NestedArrayFieldValue"})," extends ",(0,t.jsx)(n.code,{children:"NestedFieldValue"}),", and this class supports nested object DSL through the ",(0,t.jsx)(n.code,{children:"of"})," function:"]}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{className:"language-kotlin",children:'responseBody { \n    result means "Whether the login was successful" typeOf STRING formattedAs "Success or Failure"\n    status means "User\'s status" typeOf ENUM(ExampleController.SimpleResponse.UserStatus::class)\n    detail means "User\'s detail information" typeOf OBJECT of {\n        userId means "User\'s id" typeOf STRING\n        password means "User\'s password" typeOf STRING isIgnored true\n        profileMessage means "User\'s profile message" typeOf STRING isOptional true\n    }\n}\n'})}),"\n",(0,t.jsxs)(n.p,{children:["In the example above, the ",(0,t.jsx)(n.code,{children:"detail"})," field is a nested object field.\nYou can document its fields using the ",(0,t.jsx)(n.code,{children:"of"})," function, as shown."]}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{className:"language-kotlin",children:"infix fun of(nestedFieldDetailDsl: E.() -> Unit) {\n    nestedElement.nestedFieldDetailDsl()\n}\n"})}),"\n",(0,t.jsxs)(n.p,{children:["The ",(0,t.jsx)(n.code,{children:"of"})," function takes a function as a parameter,\nwhere the type parameter ",(0,t.jsx)(n.code,{children:"E"})," is an ",(0,t.jsx)(n.code,{children:"ApiComponent"})," implementation\nthat holds the nested object's elements as ",(0,t.jsx)(n.code,{children:"FieldValue"})," properties."]}),"\n",(0,t.jsxs)(n.p,{children:["Let\u2019s look at the generated ",(0,t.jsx)(n.code,{children:"ApiComponent"})," implementation:"]}),"\n",(0,t.jsx)(n.pre,{children:(0,t.jsx)(n.code,{className:"language-kotlin",children:'public object SimpleUsageApiResponseBody : FieldComponent(false) {\n\n  ...\n  \n  public val detail: NestedFieldValue<SimpleDetail_0> = NestedFieldValue("detail", SimpleDetail_0, false, 0)\n\n  ...\n  \n  public object SimpleDetail_0 : FieldComponent(false) {\n    public val userId: FieldValue = FieldValue("userId", false, 0)\n\n    public val password: FieldValue = FieldValue("password", false, 0)\n\n    public val profileMessage: FieldValue = FieldValue("profileMessage", false, 0)\n\n    ...\n    \n  }\n}\n'})}),"\n",(0,t.jsxs)(n.p,{children:["The ",(0,t.jsx)(n.code,{children:"detail"})," property is declared as a ",(0,t.jsx)(n.code,{children:"NestedFieldValue"}),", with ",(0,t.jsx)(n.code,{children:"SimpleDetail_0"})," passed as the type parameter."]}),"\n",(0,t.jsxs)(n.p,{children:[(0,t.jsx)(n.code,{children:"SimpleDetail_0"})," is an implementation of ",(0,t.jsx)(n.code,{children:"FieldComponent"}),", holding the nested fields as properties.\nAs with other JSON data, you can use DSL to document these fields."]})]})}function p(e={}){const{wrapper:n}={...(0,i.R)(),...e.components};return n?(0,t.jsx)(n,{...e,children:(0,t.jsx)(a,{...e})}):a(e)}},4913:(e,n,s)=>{s.d(n,{A:()=>t});const t=s.p+"assets/images/structure-57875cc48bacbec95884cb0c20e7e8f7.png"},8453:(e,n,s)=>{s.d(n,{R:()=>d,x:()=>o});var t=s(6540);const i={},r=t.createContext(i);function d(e){const n=t.useContext(r);return t.useMemo((function(){return"function"==typeof e?e(n):{...n,...e}}),[n,e])}function o(e){let n;return n=e.disableParentContext?"function"==typeof e.components?e.components(i):e.components||i:d(e.components),t.createElement(r.Provider,{value:n},e.children)}}}]);
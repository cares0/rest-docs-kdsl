"use strict";(self.webpackChunkdocs=self.webpackChunkdocs||[]).push([[873],{471:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>o,contentTitle:()=>i,default:()=>h,frontMatter:()=>r,metadata:()=>l,toc:()=>c});var s=n(4848),d=n(8453);const r={sidebar_position:2},i="\uc124\uc815",l={id:"getting-started/setup",title:"\uc124\uc815",description:"\uc774 \ub77c\uc774\ube0c\ub7ec\ub9ac\ub97c \uc0ac\uc6a9\ud558\uae30 \uc704\ud574 \uc120\ud589\ub418\uc5b4\uc57c \ud558\ub294 \uc124\uc815\uc744 \uc9c4\ud589\ud569\ub2c8\ub2e4.",source:"@site/i18n/ko/docusaurus-plugin-content-docs/version-1.0.4/getting-started/setup.md",sourceDirName:"getting-started",slug:"/getting-started/setup",permalink:"/rest-docs-kdsl/ko/docs/getting-started/setup",draft:!1,unlisted:!1,tags:[],version:"1.0.4",sidebarPosition:2,frontMatter:{sidebar_position:2},sidebar:"tutorialSidebar",previous:{title:"Getting Started",permalink:"/rest-docs-kdsl/ko/docs/category/getting-started"},next:{title:"\ud29c\ud1a0\ub9ac\uc5bc",permalink:"/rest-docs-kdsl/ko/docs/getting-started/tutorial"}},o={},c=[{value:"build.gradle",id:"buildgradle",level:2},{value:"1. KSP \ud50c\ub7ec\uadf8\uc778 \ucd94\uac00",id:"1-ksp-\ud50c\ub7ec\uadf8\uc778-\ucd94\uac00",level:3},{value:"2. \ub77c\uc774\ube0c\ub7ec\ub9ac \uc758\uc874\uc131 \ucd94\uac00",id:"2-\ub77c\uc774\ube0c\ub7ec\ub9ac-\uc758\uc874\uc131-\ucd94\uac00",level:3},{value:"\ud638\ud658\uc131 \ub9e4\ud2b8\ub9ad\uc2a4",id:"\ud638\ud658\uc131-\ub9e4\ud2b8\ub9ad\uc2a4",level:2}];function a(e){const t={code:"code",h1:"h1",h2:"h2",h3:"h3",header:"header",p:"p",pre:"pre",table:"table",tbody:"tbody",td:"td",th:"th",thead:"thead",tr:"tr",...(0,d.R)(),...e.components};return(0,s.jsxs)(s.Fragment,{children:[(0,s.jsx)(t.header,{children:(0,s.jsx)(t.h1,{id:"\uc124\uc815",children:"\uc124\uc815"})}),"\n",(0,s.jsx)(t.p,{children:"\uc774 \ub77c\uc774\ube0c\ub7ec\ub9ac\ub97c \uc0ac\uc6a9\ud558\uae30 \uc704\ud574 \uc120\ud589\ub418\uc5b4\uc57c \ud558\ub294 \uc124\uc815\uc744 \uc9c4\ud589\ud569\ub2c8\ub2e4."}),"\n",(0,s.jsx)(t.h2,{id:"buildgradle",children:"build.gradle"}),"\n",(0,s.jsx)(t.h3,{id:"1-ksp-\ud50c\ub7ec\uadf8\uc778-\ucd94\uac00",children:"1. KSP \ud50c\ub7ec\uadf8\uc778 \ucd94\uac00"}),"\n",(0,s.jsx)(t.pre,{children:(0,s.jsx)(t.code,{className:"language-kotlin",children:'plugins {\n    id("com.google.devtools.ksp") version "2.0.0-1.0.21"\n}\n'})}),"\n",(0,s.jsx)(t.h3,{id:"2-\ub77c\uc774\ube0c\ub7ec\ub9ac-\uc758\uc874\uc131-\ucd94\uac00",children:"2. \ub77c\uc774\ube0c\ub7ec\ub9ac \uc758\uc874\uc131 \ucd94\uac00"}),"\n",(0,s.jsx)(t.pre,{children:(0,s.jsx)(t.code,{className:"language-kotlin",children:'val kdslVersion = "1.x.x" // \uc0ac\uc6a9\ud560 \ub77c\uc774\ube0c\ub7ec\ub9ac \ubc84\uc804\uc744 \uc791\uc131\ud558\uc138\uc694.\n\ndependencies {\n    implementation("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")\n    ksp("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")\n}\n'})}),"\n",(0,s.jsx)(t.h2,{id:"\ud638\ud658\uc131-\ub9e4\ud2b8\ub9ad\uc2a4",children:"\ud638\ud658\uc131 \ub9e4\ud2b8\ub9ad\uc2a4"}),"\n",(0,s.jsxs)(t.table,{children:[(0,s.jsx)(t.thead,{children:(0,s.jsxs)(t.tr,{children:[(0,s.jsx)(t.th,{children:"\ub77c\uc774\ube0c\ub7ec\ub9ac \ubc84\uc804"}),(0,s.jsx)(t.th,{children:"Kotlin \ubc84\uc804"}),(0,s.jsx)(t.th,{children:"KSP \ubc84\uc804"}),(0,s.jsx)(t.th,{children:"Spring REST Docs \ubc84\uc804"})]})}),(0,s.jsx)(t.tbody,{children:(0,s.jsxs)(t.tr,{children:[(0,s.jsx)(t.td,{children:(0,s.jsx)(t.code,{children:"1.0.4"})}),(0,s.jsx)(t.td,{children:(0,s.jsx)(t.code,{children:"2.0.0"})}),(0,s.jsx)(t.td,{children:(0,s.jsx)(t.code,{children:"2.0.0-1.0.21"})}),(0,s.jsx)(t.td,{children:(0,s.jsx)(t.code,{children:"3.0.1"})})]})})]}),"\n",(0,s.jsx)(t.p,{children:"1.0.4 \ubc84\uc804\uc774 \uc774 \ub77c\uc774\ube0c\ub7ec\ub9ac\uc758 \ucd5c\uc18c \ubc84\uc804\uc785\ub2c8\ub2e4."})]})}function h(e={}){const{wrapper:t}={...(0,d.R)(),...e.components};return t?(0,s.jsx)(t,{...e,children:(0,s.jsx)(a,{...e})}):a(e)}},8453:(e,t,n)=>{n.d(t,{R:()=>i,x:()=>l});var s=n(6540);const d={},r=s.createContext(d);function i(e){const t=s.useContext(r);return s.useMemo((function(){return"function"==typeof e?e(t):{...t,...e}}),[t,e])}function l(e){let t;return t=e.disableParentContext?"function"==typeof e.components?e.components(d):e.components||d:i(e.components),s.createElement(r.Provider,{value:t},e.children)}}}]);
"use strict";(self["webpackChunk_coreui_coreui_free_vue_admin_template"]=self["webpackChunk_coreui_coreui_free_vue_admin_template"]||[]).push([[758],{1758:function(e,l,s){s.r(l),s.d(l,{default:function(){return k}});var a=s(3396),t=s(9242),o=s(7139);const i={class:"bg-light min-vh-100 d-flex flex-row align-items-center"},u=(0,a._)("h1",{class:"mb-5 text-center"},"Reset Password",-1),n={class:"text-medium-emphasis"},d={class:"text-muted"},r={class:"text-danger float-end"},m=(0,a.Uk)(" ::: "),c=(0,a.Uk)("@"),p=(0,a._)("br",null,null,-1),w={class:"d-grid mt-4"},_=(0,a.Uk)("Change passwrod");function f(e,l,s,f,h,g){const C=(0,a.up)("CInputGroupText"),W=(0,a.up)("CFormInput"),k=(0,a.up)("CInputGroup"),v=(0,a.up)("CIcon"),b=(0,a.up)("CButton"),V=(0,a.up)("CForm"),x=(0,a.up)("CCardBody"),y=(0,a.up)("CCard"),L=(0,a.up)("CCol"),P=(0,a.up)("CRow"),U=(0,a.up)("CContainer"),q=(0,a.up)("Loading");return(0,a.wg)(),(0,a.iD)(a.HY,null,[(0,a._)("div",i,[(0,a.Wm)(U,null,{default:(0,a.w5)((()=>[(0,a.Wm)(P,{class:"justify-content-center"},{default:(0,a.w5)((()=>[(0,a.Wm)(L,{md:12,lg:10,xl:6},{default:(0,a.w5)((()=>[(0,a.Wm)(y,{class:"mx-4 mx-sm-0"},{default:(0,a.w5)((()=>[(0,a.Wm)(x,{class:"p-4"},{default:(0,a.w5)((()=>[(0,a.Wm)(V,{onSubmit:(0,t.iM)(g.submit,["prevent"])},{default:(0,a.w5)((()=>[u,(0,a._)("p",n,[(0,a._)("small",d,[(0,a._)("i",r,(0,o.zw)(h.message),1)])]),(0,a._)("div",null,[(0,a.Wm)(k,{class:"mb-3"},{default:(0,a.w5)((()=>[(0,a.Wm)(C,null,{default:(0,a.w5)((()=>[m])),_:1}),(0,a.Wm)(W,{type:"text",placeholder:"Token...",autocomplete:"password",modelValue:h.token,"onUpdate:modelValue":l[0]||(l[0]=e=>h.token=e),required:""},null,8,["modelValue"])])),_:1})]),(0,a._)("div",null,[(0,a.Wm)(k,{class:"mb-3"},{default:(0,a.w5)((()=>[(0,a.Wm)(C,null,{default:(0,a.w5)((()=>[c])),_:1}),(0,a.Wm)(W,{placeholder:"Email",autocomplete:"email",modelValue:h.email,"onUpdate:modelValue":l[1]||(l[1]=e=>h.email=e),required:"",type:"email"},null,8,["modelValue"])])),_:1})]),(0,a._)("div",null,[(0,a.Wm)(k,{class:"mb-3"},{default:(0,a.w5)((()=>[(0,a.Wm)(C,null,{default:(0,a.w5)((()=>[(0,a.Wm)(v,{icon:"cil-lock-locked"})])),_:1}),(0,a.Wm)(W,{type:"password",placeholder:"Password",autocomplete:"password",modelValue:h.password,"onUpdate:modelValue":l[2]||(l[2]=e=>h.password=e),required:""},null,8,["modelValue"])])),_:1})]),(0,a._)("div",null,[(0,a.Wm)(k,{class:"mb-3"},{default:(0,a.w5)((()=>[(0,a.Wm)(C,null,{default:(0,a.w5)((()=>[(0,a.Wm)(v,{icon:"cil-lock-locked"})])),_:1}),(0,a.Wm)(W,{type:"password",placeholder:"Confirm Password",autocomplete:"new-password",modelValue:h.confirm,"onUpdate:modelValue":l[3]||(l[3]=e=>h.confirm=e),required:""},null,8,["modelValue"])])),_:1})]),(0,a._)("a",{color:"link",class:"ms-2",onClick:l[4]||(l[4]=e=>g.loginPage()),style:{cursor:"pointer"}}," Back to Login "),p,(0,a._)("div",w,[(0,a.Wm)(b,{color:"success",type:"submit"},{default:(0,a.w5)((()=>[_])),_:1})])])),_:1},8,["onSubmit"])])),_:1})])),_:1})])),_:1})])),_:1})])),_:1})]),(0,a.Wm)(q,{active:h.isLoading,"onUpdate:active":l[5]||(l[5]=e=>h.isLoading=e)},null,8,["active"])],64)}s(7658);var h=s(7726),g={name:"Register",data(){return{email:"",password:"",confirm:"",token:"",message:"",isLoading:!1}},methods:{async submit(){this.isLoading=!0,this.password!=this.confirm?this.message="Confirm password & Password need in the same!":await h.Z.resetPassword({token:this.token,email:this.email,password:this.password,confirm:this.confirm}).then((e=>{200===e.status&&(this.isLoading=!1,this.$swal.fire("Change successly!","Now go to login!","success"),this.message="",this.$router.push("/pages/login"))})).catch((e=>{this.isLoading=!1,this.$swal.fire("Change failed!","Please check again!","error"),this.message=e.response.this.error}))},loginPage(){this.$router.push("/pages/login")}}},C=s(89);const W=(0,C.Z)(g,[["render",f]]);var k=W}}]);
//# sourceMappingURL=758.e390ca69.js.map
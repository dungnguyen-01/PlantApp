"use strict";(self["webpackChunk_coreui_coreui_free_vue_admin_template"]=self["webpackChunk_coreui_coreui_free_vue_admin_template"]||[]).push([[662],{2344:function(e,s,t){t.r(s),t.d(s,{default:function(){return v}});var a=t(3396),i=t(9242),l=t(7139);const n={class:"bg-light min-vh-100 d-flex flex-row align-items-center"},o=(0,a._)("h1",{class:"mb-5"},"Forgot Password",-1),r={class:"text-medium-emphasis"},u={class:"text-muted"},c={class:"text-danger float-end"},m={class:"text-danger text"},d=(0,a._)("br",null,null,-1),g={class:"d-grid mt-4"},p=(0,a.Uk)("Send Mail");function h(e,s,t,h,_,f){const w=(0,a.up)("CButton"),C=(0,a.up)("CForm"),v=(0,a.up)("CCardBody"),k=(0,a.up)("CCard"),b=(0,a.up)("CCol"),x=(0,a.up)("CRow"),y=(0,a.up)("CContainer"),L=(0,a.up)("Loading");return(0,a.wg)(),(0,a.iD)(a.HY,null,[(0,a._)("div",n,[(0,a.Wm)(y,null,{default:(0,a.w5)((()=>[(0,a.Wm)(x,{class:"justify-content-center"},{default:(0,a.w5)((()=>[(0,a.Wm)(b,{md:12,lg:10,xl:6},{default:(0,a.w5)((()=>[(0,a.Wm)(k,{class:"mx-4 mx-sm-0"},{default:(0,a.w5)((()=>[(0,a.Wm)(v,{class:"p-4"},{default:(0,a.w5)((()=>[(0,a.Wm)(C,{onSubmit:(0,i.iM)(f.submit,["prevent"])},{default:(0,a.w5)((()=>[o,(0,a._)("p",r,[(0,a._)("small",u,[(0,a._)("i",c,(0,l.zw)(_.message),1)])]),(0,a._)("i",m,(0,l.zw)(e.emailError),1),(0,a.wy)((0,a._)("input",{class:"form-control",placeholder:"Email",autocomplete:"email","onUpdate:modelValue":s[0]||(s[0]=e=>_.email=e),required:"",type:"email"},null,512),[[i.nr,_.email]]),(0,a._)("a",{color:"link",class:"ms-2",onClick:s[1]||(s[1]=e=>f.loginPage()),style:{cursor:"pointer"}}," Back to Login "),d,(0,a._)("div",g,[(0,a.Wm)(w,{color:"success",type:"submit"},{default:(0,a.w5)((()=>[p])),_:1})])])),_:1},8,["onSubmit"])])),_:1})])),_:1})])),_:1})])),_:1})])),_:1})]),(0,a.Wm)(L,{active:_.isLoading,"onUpdate:active":s[2]||(s[2]=e=>_.isLoading=e)},null,8,["active"])],64)}t(7658);var _=t(7726),f={name:"Register",data(){return{email:"",message:"",isLoading:!1}},methods:{async submit(){this.isLoading=!0,await _.Z.forgotPassword({email:this.email,password:""}).then((e=>{200==e.status&&(this.isLoading=!1,this.$swal.fire("Success","Please check email get token & follow the instructions!","success"),this.message="")})).catch((e=>{this.isLoading=!1,this.$swal.fire("Failed!","Please check again!","error"),e.response.this.error==this.email+" not found!"?this.message=e.response.this.error:this.message="The system is crashing, please try again later"}))},loginPage(){this.$router.push("/pages/login")}}},w=t(89);const C=(0,w.Z)(f,[["render",h]]);var v=C}}]);
//# sourceMappingURL=662.00b83bf5.js.map
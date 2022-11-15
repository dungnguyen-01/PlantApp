"use strict";(self["webpackChunk_coreui_coreui_free_vue_admin_template"]=self["webpackChunk_coreui_coreui_free_vue_admin_template"]||[]).push([[50],{4303:function(t,e,a){var o=a(6265),i=a.n(o),n=a(8);const l="https://api.kintovn.com/api/v1/location/";class r{getAllLocations(t){return i().get(l+"list"+t,{headers:(0,n.Z)()})}getLocations(t){return i().get(l+t,{headers:(0,n.Z)()})}getLocationById(t){return i().get(l+t,{headers:(0,n.Z)()})}createLocation(t){return i().post(l,t,{headers:(0,n.Z)()})}updateLocation(t,e){return i().put(l+t,e,{headers:(0,n.Z)()})}deleteLocationById(t){return i().get(l+"delete/"+t,{headers:(0,n.Z)()})}}e["Z"]=new r},9050:function(t,e,a){a.r(e),a.d(e,{default:function(){return _}});var o=a(3396),i=a(7139),n=a(9242);const l=(0,o.Uk)(" Detail Location "),r=(0,o._)("hr",null,null,-1),d=(0,o.Uk)(" Back to list "),s=(0,o.Uk)(" Edit ");function u(t,e,a,u,c,m){const p=(0,o.up)("CIcon"),h=(0,o.up)("CCardHeader"),f=(0,o.up)("labe"),_=(0,o.up)("CRow"),g=(0,o.up)("CCardBody"),w=(0,o.up)("CCardFooter"),L=(0,o.up)("CCard"),b=(0,o.up)("CCol"),k=(0,o.up)("Loading");return(0,o.wg)(),(0,o.iD)(o.HY,null,[(0,o.Wm)(_,null,{default:(0,o.w5)((()=>[(0,o.Wm)(b,null,{default:(0,o.w5)((()=>[(0,o.Wm)(L,null,{default:(0,o.w5)((()=>[(0,o.Wm)(h,null,{default:(0,o.w5)((()=>[(0,o.Wm)(p,{icon:"cil-drop"}),l])),_:1}),(0,o.Wm)(g,null,{default:(0,o.w5)((()=>[(0,o.Wm)(_,null,{default:(0,o.w5)((()=>[(0,o.Wm)(f,{class:"form-label text"},{default:(0,o.w5)((()=>[(0,o.Uk)("- Id: "+(0,i.zw)(c.locationId),1)])),_:1}),(0,o.Wm)(f,{class:"form-label text"},{default:(0,o.w5)((()=>[(0,o.Uk)("- Code: "+(0,i.zw)(c.code),1)])),_:1}),(0,o.Wm)(f,{class:"form-label text"},{default:(0,o.w5)((()=>[(0,o.Uk)("- Address: "+(0,i.zw)(c.address),1)])),_:1}),(0,o.Wm)(f,{class:"form-label text"},{default:(0,o.w5)((()=>[(0,o.Uk)("- Date create: "+(0,i.zw)(this.$formatDate.formatDate(c.createAt)),1)])),_:1}),(0,o.Wm)(f,{class:"form-label text"},{default:(0,o.w5)((()=>[(0,o.Uk)("- Date update: "+(0,i.zw)(this.$formatDate.formatDate(c.updateAt)),1)])),_:1}),r,(0,o._)("label",null,"- Organization: "+(0,i.zw)(c.org),1)])),_:1})])),_:1}),(0,o.Wm)(w,null,{default:(0,o.w5)((()=>[(0,o._)("div",null,[(0,o._)("button",{type:"button",class:"btn btn-info rounded-pill mx-2 px-4",title:"detail",onClick:e[0]||(e[0]=t=>m.backToList())},[(0,o.Wm)(p,{icon:u.icon.cilMenu,size:"m"},null,8,["icon"]),d]),(0,o.wy)((0,o._)("button",{type:"button",class:"btn btn-warning rounded-pill mx-2 px-4",title:"edit",onClick:e[1]||(e[1]=t=>m.editLocation(c.locationId))},[(0,o.Wm)(p,{icon:u.icon.cilPen,size:"m"},null,8,["icon"]),s],512),[[n.F8,"Admin"==c.role.roleName||"Editor"==c.role.roleName]])])])),_:1})])),_:1})])),_:1})])),_:1}),(0,o.Wm)(k,{active:c.isLoading,"onUpdate:active":e[2]||(e[2]=t=>c.isLoading=t)},null,8,["active"])],64)}a(7658);var c=a(4303),m=a(6867),p={name:"DetailLocation",data(){return{locationId:"",role:this.$store.state.auth.user.user.role,address:"",code:"",createAt:"",updateAt:"",org:"",isLoading:!0}},computed:{id(){return this.$route.params.id}},methods:{getDetailLocation(){c.Z.getLocationById(this.id).then((t=>{200===t.status&&(this.isLoading=!1,this.locationId=t.data.id,this.address=t.data.address,this.code=t.data.code,this.org=t.data.organization.name,this.createAt=t.data.createAt,this.updateAt=t.data.updateAt)})).catch((()=>{this.isLoading=!1,this.$swal.fire("Warning!","The system is experiencing an error, please reload the page","warning")}))},backToList(){this.$router.push("/location/list-location")},editLocation(t){this.$router.push(`/location/form-location/${t}`)}},created(){this.getDetailLocation()},setup(){return{icon:m}}},h=a(89);const f=(0,h.Z)(p,[["render",u]]);var _=f}}]);
//# sourceMappingURL=50.be740274.js.map
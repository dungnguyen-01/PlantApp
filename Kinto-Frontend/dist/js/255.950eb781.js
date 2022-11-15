"use strict";(self["webpackChunk_coreui_coreui_free_vue_admin_template"]=self["webpackChunk_coreui_coreui_free_vue_admin_template"]||[]).push([[255],{2319:function(e,t,a){var l=a(6265),i=a.n(l),r=a(8);const s="https://api.kintovn.com/api/v1/farm-diary/";class n{getFarmDiaryById(e){return i().get(s+e,{headers:(0,r.Z)()})}createFarmDiary(e){return i().post(s,e,{headers:(0,r.Z)()})}updateFarmDiary(e,t){return i().put(s+e,t,{headers:(0,r.Z)()})}deleteFarmDiaryById(e){return i()["delete"](s+e,{headers:(0,r.Z)()})}deleteFarmDiaryByPlantId(e){return i()["delete"](s+"plant/"+e,{headers:(0,r.Z)()})}}t["Z"]=new n},8618:function(e,t,a){var l=a(6265),i=a.n(l),r=a(8);const s="https://api.kintovn.com/api/v1/image/";class n{createImage(e){return i().post(s,e,{headers:(0,r.Z)()})}deleteImageById(e){return i()["delete"](s+e,{headers:(0,r.Z)()})}deleteImageByPlantId(e){return i()["delete"](s+"plant/"+e,{headers:(0,r.Z)()})}}t["Z"]=new n},4303:function(e,t,a){var l=a(6265),i=a.n(l),r=a(8);const s="https://api.kintovn.com/api/v1/location/";class n{getAllLocations(e){return i().get(s+"list"+e,{headers:(0,r.Z)()})}getLocations(e){return i().get(s+e,{headers:(0,r.Z)()})}getLocationById(e){return i().get(s+e,{headers:(0,r.Z)()})}createLocation(e){return i().post(s,e,{headers:(0,r.Z)()})}updateLocation(e,t){return i().put(s+e,t,{headers:(0,r.Z)()})}deleteLocationById(e){return i().get(s+"delete/"+e,{headers:(0,r.Z)()})}}t["Z"]=new n},2556:function(e,t,a){var l=a(6265),i=a.n(l),r=a(8);const s="https://api.kintovn.com/api/v1/organization/";class n{getAllOrganizations(){return i().get(s+"list",{headers:(0,r.Z)()})}getAllSelectByUserOrganizations(){return i().get(s+"list-user",{headers:(0,r.Z)()})}getOrganizations(e){return i().get(s+e,{headers:(0,r.Z)()})}getOrganizationById(e){return i().get(s+e,{headers:(0,r.Z)()})}createOrganization(e){return i().post(s,e,{headers:(0,r.Z)()})}updateOrganization(e,t){return i().put(s+e,t,{headers:(0,r.Z)()})}deleteOrganizationById(e){return i().get(s+"delete/"+e,{headers:(0,r.Z)()})}}t["Z"]=new n},1919:function(e,t,a){var l=a(6265),i=a.n(l),r=a(8);const s="https://api.kintovn.com/api/v1/plant/";class n{getPlants(e){return i().get(s+e,{headers:(0,r.Z)()})}getPlantById(e){return i().get(s+e,{headers:(0,r.Z)()})}createPlant(e){return i().post(s,e,{headers:(0,r.Z)()})}updatePlant(e,t){return i().put(s+e,t,{headers:(0,r.Z)()})}deletePlantById(e){return i()["delete"](s+e,{headers:(0,r.Z)()})}}t["Z"]=new n},2872:function(e,t,a){var l=a(6265),i=a.n(l),r=a(8);const s="https://api.kintovn.com/api/v1/plant-type/";class n{getAllPlantTypes(e){return i().get(s+"list"+e,{headers:(0,r.Z)()})}getPlantTypes(e){return i().get(s+e,{headers:(0,r.Z)()})}getPlantTypeById(e){return i().get(s+e,{headers:(0,r.Z)()})}createPlantType(e){return i().post(s,e,{headers:(0,r.Z)()})}updatePlantType(e,t){return i().put(s+e,t,{headers:(0,r.Z)()})}deletePlantTypeById(e){return i().get(s+"delete/"+e,{headers:(0,r.Z)()})}}t["Z"]=new n},255:function(e,t,a){a.r(t),a.d(t,{default:function(){return Ye}});var l=a(3396),i=a(9242),r=a(7139);const s=(0,l.Uk)(" Plant "),n={class:"float-end text-danger"},o=(0,l._)("div",{class:"alert alert-success"},"FORM PLANT.",-1),d=(0,l._)("hr",null,null,-1),u={class:"row"},c={class:"col-lg-5"},p=(0,l._)("label",{for:"plantId",class:"form-label"},"Id:",-1),m={class:"col-lg-7"},h=(0,l._)("label",{for:"comment",class:"form-label text"},"Person In Charge:",-1),g={class:"text-danger"},y={class:"row mt-3"},f={class:"col-lg-5"},_=(0,l._)("label",{for:"id",class:"form-label"},"Plant type: ",-1),w={class:"text-danger"},D={class:"col-lg-7"},v=(0,l._)("label",{for:"location",class:"form-label text"},"Location:",-1),b={class:"text-danger"},I={class:"mt-3 row"},C={class:"col-lg-6"},Z=(0,l._)("label",{for:"cultureMedium",class:"form-label text"},"Culture Medium:",-1),T={class:"text-danger"},k={class:"col-lg-6"},z=(0,l._)("label",{for:"comment",class:"form-label text"},"Farm Diary:",-1),P=(0,l.Uk)(" Add Farm Diary "),x={class:"mt-3"},F=(0,l._)("h5",null,"Add Image",-1),B={class:"form-group"},L={class:"border p-2 mt-3"},S=(0,l._)("p",null,"Preview Here:",-1),W={key:0},U={class:"table table-striped"},M=(0,l._)("thead",null,[(0,l._)("th",null,"No."),(0,l._)("th",null,"Image"),(0,l._)("th",null,"Description")],-1),$=["src"],E={class:"mb-0"},A=(0,l._)("hr",null,null,-1),V={class:"text-center my-1"},R=(0,l.Uk)(" Back "),O={class:"btn btn-outline-primary rounded-pill ms-2 px-4",title:"create",type:"submit"},N=(0,l.Uk)(" Create "),Y={class:"btn btn-outline-success rounded-pill ms-2 px-4",title:"update",type:"submit"},H=(0,l.Uk)(" Update "),j=(0,l.Uk)(" Reset ");function q(e,t,a,q,K,G){const J=(0,l.up)("CIcon"),Q=(0,l.up)("CCardHeader"),X=(0,l.up)("CRow"),ee=(0,l.up)("v-select"),te=(0,l.up)("CButton"),ae=(0,l.up)("FarmDiary"),le=(0,l.up)("Image"),ie=(0,l.up)("CCardBody"),re=(0,l.up)("Loading"),se=(0,l.up)("CCardFooter"),ne=(0,l.up)("CCard"),oe=(0,l.up)("CCol");return(0,l.wg)(),(0,l.j4)(X,null,{default:(0,l.w5)((()=>[(0,l.Wm)(oe,null,{default:(0,l.w5)((()=>[(0,l.Wm)(ne,null,{default:(0,l.w5)((()=>[(0,l._)("form",{onSubmit:t[11]||(t[11]=(0,i.iM)((e=>"null"==this.$route.params.id?G.createPlant():G.updatePlant()),["prevent"]))},[(0,l.Wm)(Q,null,{default:(0,l.w5)((()=>[(0,l.Wm)(J,{icon:"cil-drop"}),s,(0,l._)("span",n,(0,r.zw)(K.message),1)])),_:1}),(0,l.Wm)(ie,null,{default:(0,l.w5)((()=>[(0,l.Wm)(X,null,{default:(0,l.w5)((()=>[o])),_:1}),d,(0,l.Wm)(X,null,{default:(0,l.w5)((()=>[(0,l.Wm)(X,null,{default:(0,l.w5)((()=>[(0,l._)("div",u,[(0,l._)("div",c,[p,(0,l.wy)((0,l._)("input",{type:"text",class:"form-control",placeholder:"Plant code...",readonly:"",name:"plantId","onUpdate:modelValue":t[0]||(t[0]=e=>K.plantId=e)},null,512),[[i.nr,K.plantId]])]),(0,l._)("div",m,[h,(0,l._)("i",g,(0,r.zw)(K.userError),1),(0,l.Wm)(ee,{modelValue:K.userSelected,"onUpdate:modelValue":t[1]||(t[1]=e=>K.userSelected=e),options:K.optionsUser,label:"data_item",reduce:e=>e.id,required:""},null,8,["modelValue","options","reduce"])])]),(0,l._)("div",y,[(0,l._)("div",f,[_,(0,l._)("i",w,(0,r.zw)(K.plantTypeError),1),(0,l.Wm)(ee,{modelValue:K.plantTypeSelected,"onUpdate:modelValue":t[2]||(t[2]=e=>K.plantTypeSelected=e),options:K.optionsPlantType,label:"namePlantType",reduce:e=>e.id,required:""},null,8,["modelValue","options","reduce"])]),(0,l._)("div",D,[v,(0,l._)("i",b,(0,r.zw)(K.locationError),1),(0,l.Wm)(ee,{modelValue:K.locationSelected,"onUpdate:modelValue":t[3]||(t[3]=e=>K.locationSelected=e),options:K.optionsLocation,label:"address",reduce:e=>e.id,required:""},null,8,["modelValue","options","reduce"])])]),(0,l._)("div",I,[(0,l._)("div",C,[Z,(0,l._)("i",T,(0,r.zw)(K.cultureMediumError),1),(0,l.wy)((0,l._)("textarea",{class:"form-control",rows:"5",placeholder:"Culture Medium...",name:"cultureMedium","onUpdate:modelValue":t[4]||(t[4]=e=>K.cultureMedium=e),required:""},null,512),[[i.nr,K.cultureMedium]])]),(0,l._)("div",k,[z,(0,l.wy)((0,l._)("textarea",{class:"form-control",rows:"5",placeholder:"...",name:"farmDiary","onUpdate:modelValue":t[5]||(t[5]=e=>K.farmDiary=e)},null,512),[[i.nr,K.farmDiary]]),(0,l.wy)((0,l.Wm)(te,{class:"btn btn-outline-info rounded-pill ms-2 px-3 m-3",title:"create",onClick:t[6]||(t[6]=e=>G.addFarmDiary())},{default:(0,l.w5)((()=>[(0,l.Wm)(J,{icon:q.icon.cilPen,size:"m"},null,8,["icon"]),P])),_:1},512),[[i.F8,""!=K.farmDiary]])])])])),_:1}),(0,l.Wm)(ae,{farmDiaries:K.farmDiaries},null,8,["farmDiaries"]),(0,l.Wm)(le,{images:K.images},null,8,["images"]),(0,l.Wm)(X,null,{default:(0,l.w5)((()=>[(0,l._)("div",x,[F,(0,l._)("form",null,[(0,l._)("div",B,[(0,l._)("input",{type:"file",accept:"image/*",multiple:"multiple",onChange:t[7]||(t[7]=e=>G.previewMultiImage(e)),class:"form-control-file border border-warning",id:"my-file",ref:"uploadImage"},null,544),(0,l._)("div",L,[S,K.preview_list.length?((0,l.wg)(),(0,l.iD)("div",W,[(0,l._)("table",U,[M,(0,l._)("tbody",null,[((0,l.wg)(!0),(0,l.iD)(l.HY,null,(0,l.Ko)(K.preview_list,((e,t)=>((0,l.wg)(),(0,l.iD)("tr",{key:t},[(0,l._)("td",null,(0,r.zw)(t+1),1),(0,l._)("td",null,[(0,l._)("img",{src:e,class:"img-fluid border border-dark",style:{width:"100px",height:"100px"}},null,8,$)]),(0,l._)("td",null,[(0,l._)("p",E," file name: "+(0,r.zw)(K.image_list[t].name),1)])])))),128))])])])):(0,l.kq)("",!0)])])])])])),_:1})])),_:1}),A])),_:1}),(0,l.Wm)(se,null,{default:(0,l.w5)((()=>[(0,l._)("div",V,[(0,l._)("button",{class:"btn btn-outline-info rounded-pill px-4",title:"back to list",onClick:t[8]||(t[8]=e=>G.backToList())},[(0,l.Wm)(J,{icon:q.icon.cilArrowLeft,size:"m"},null,8,["icon"]),R]),(0,l.wy)((0,l._)("button",O,[(0,l.Wm)(J,{icon:q.icon.cilPen,size:"m"},null,8,["icon"]),N],512),[[i.F8,"null"==this.$route.params.id]]),(0,l.wy)((0,l._)("button",Y,[(0,l.Wm)(J,{icon:q.icon.cilTrash,size:"m"},null,8,["icon"]),H],512),[[i.F8,"null"!=this.$route.params.id]]),(0,l._)("button",{class:"btn btn-outline-warning rounded-pill ms-2 px-4",title:"reset",onClick:t[9]||(t[9]=e=>G.resetForm())},[(0,l.Wm)(J,{icon:q.icon.cilReload,size:"m"},null,8,["icon"]),j])]),(0,l.Wm)(re,{active:K.isLoading,"onUpdate:active":t[10]||(t[10]=e=>K.isLoading=e)},null,8,["active"])])),_:1})],32)])),_:1})])),_:1})])),_:1})}a(7658);var K=a(4303),G=a(2872),J=a(2556),Q=a(1919),X=a(2319),ee=a(8618),te=a(7726),ae=a(6867);const le={class:"mt-3"},ie=(0,l._)("h5",null,"3. Image",-1),re={class:"form-group"},se={class:"border p-2 mt-3"},ne={class:"table-responsive-lg"},oe={class:"table table-striped"},de=(0,l._)("thead",{class:"table-dark"},[(0,l._)("tr",null,[(0,l._)("th",null,"ID."),(0,l._)("th",null,"Image"),(0,l._)("th",null,"Name"),(0,l._)("th",null,"Date create"),(0,l._)("th",null,"Action")])],-1),ue=["refs"],ce={style:{width:"200px"}},pe=["src","onClick"],me={class:"mb-0"},he=["onClick"],ge=(0,l.Uk)("Show image"),ye=["src"];function fe(e,t,a,i,s,n){const o=(0,l.up)("CIcon"),d=(0,l.up)("CModalTitle"),u=(0,l.up)("CModalHeader"),c=(0,l.up)("CModalBody"),p=(0,l.up)("CModal"),m=(0,l.up)("CRow");return(0,l.wg)(),(0,l.j4)(m,null,{default:(0,l.w5)((()=>[(0,l._)("div",le,[ie,(0,l._)("form",null,[(0,l._)("div",re,[(0,l._)("div",se,[(0,l._)("div",null,[(0,l._)("div",ne,[(0,l._)("table",oe,[de,(0,l._)("tbody",null,[((0,l.wg)(!0),(0,l.iD)(l.HY,null,(0,l.Ko)(a.images,((e,t)=>((0,l.wg)(),(0,l.iD)("tr",{key:t},[(0,l._)("td",{refs:"title"+e.id},(0,r.zw)(t+1),9,ue),(0,l._)("td",ce,[(0,l._)("img",{src:e.objectName,style:{width:"100%"},class:"img-fluid border border-dark h-25",onClick:t=>n.showModal(e.objectName)},null,8,pe)]),(0,l._)("td",null,(0,r.zw)(e.name),1),(0,l._)("td",null,[(0,l._)("p",me,(0,r.zw)(this.$formatDate.formatDate(e.createAt)),1)]),(0,l._)("td",null,[(0,l._)("div",{class:"btn btn-outline-danger rounded-pill p-0 px-4",title:"delete",onClick:a=>n.deleteImage(e.id,t)},[(0,l.Wm)(o,{icon:i.icon.cilTrash,size:"m"},null,8,["icon"])],8,he)])])))),128))])])])])])])]),(0,l.Wm)(p,{ref:"my-modal",size:"xl",visible:s.xlDemo,onClose:t[0]||(t[0]=()=>{s.xlDemo=!1})},{default:(0,l.w5)((()=>[(0,l.Wm)(u,null,{default:(0,l.w5)((()=>[(0,l.Wm)(d,null,{default:(0,l.w5)((()=>[ge])),_:1})])),_:1}),(0,l.Wm)(c,null,{default:(0,l.w5)((()=>[(0,l._)("div",null,[(0,l._)("img",{src:s.linkImg,alt:"wait",class:"w-75 rounded mx-auto d-block"},null,8,ye)])])),_:1})])),_:1},8,["visible"])])])),_:1})}var _e={name:"image",props:["images"],data(){return{linkImg:"",xlDemo:!1,organization:this.$store.state.auth.user.user.organization}},methods:{deleteImage(e,t){this.$swal({title:"Are you sure?",text:"You won't be able to revert this!",icon:"warning",showCancelButton:!0,confirmButtonText:"Yes, delete it!",cancelButtonText:"No, cancel!",confirmButtonColor:"#55c663",reverseButtons:!0}).then((a=>{a.isConfirmed&&(ee.Z.deleteImageById(e).then((()=>{this.images.splice(t,1)})),this.$swal.fire("Deleted!","You have successfully deleted.","success"))}))},showModal(e){this.linkImg=e,this.xlDemo=!0}},setup(){return{icon:ae}}},we=a(89);const De=(0,we.Z)(_e,[["render",fe]]);var ve=De;const be={class:"mt-3"},Ie=(0,l._)("h5",null,"2. Farm Diaries:",-1),Ce={class:"form-group"},Ze={class:"border p-2 mt-3"},Te={class:"table-responsive-lg"},ke={class:"table table-striped"},ze=(0,l._)("thead",{class:"table-dark"},[(0,l._)("tr",null,[(0,l._)("th",null,"ID."),(0,l._)("th",null,"Farm Diary"),(0,l._)("th",null,"Date create"),(0,l._)("th",null,"Date update"),(0,l._)("th"),(0,l._)("th")])],-1),Pe={class:"mb-0"},xe={class:"mb-0"},Fe=["onClick"],Be=["onClick"],Le=(0,l.Uk)("Edit Farm Diary"),Se=(0,l._)("label",{for:"id",class:"form-label text"},"Id:",-1),We=(0,l._)("label",{for:"name",class:"form-label text"},"Name:",-1),Ue=(0,l.Uk)(" Close "),Me=(0,l.Uk)("Save");function $e(e,t,a,s,n,o){const d=(0,l.up)("CIcon"),u=(0,l.up)("CModalTitle"),c=(0,l.up)("CModalHeader"),p=(0,l.up)("CModalBody"),m=(0,l.up)("CButton"),h=(0,l.up)("CModalFooter"),g=(0,l.up)("CModal"),y=(0,l.up)("CRow");return(0,l.wg)(),(0,l.j4)(y,null,{default:(0,l.w5)((()=>[(0,l._)("div",be,[Ie,(0,l._)("div",Ce,[(0,l._)("div",Ze,[(0,l._)("div",null,[(0,l._)("div",Te,[(0,l._)("table",ke,[ze,(0,l._)("tbody",null,[((0,l.wg)(!0),(0,l.iD)(l.HY,null,(0,l.Ko)(a.farmDiaries,((e,t)=>((0,l.wg)(),(0,l.iD)("tr",{key:t},[(0,l._)("td",null,(0,r.zw)(t+1),1),(0,l._)("td",null,(0,r.zw)(e.name),1),(0,l._)("td",null,[(0,l._)("p",Pe,(0,r.zw)(this.$formatDate.formatDate(e.createAt)),1)]),(0,l._)("td",null,[(0,l._)("p",xe,(0,r.zw)(this.$formatDate.formatDate(e.updateAt)),1)]),(0,l._)("td",null,[(0,l.wy)((0,l._)("div",{id:"show-btn",class:"btn btn-outline-warning rounded-pill p-0 px-4 mx-2",title:"edit",onClick:t=>o.editFarmDiary(e.id)},[(0,l.Wm)(d,{icon:s.icon.cilPen,size:"m"},null,8,["icon"])],8,Fe),[[i.F8,null!=e.id]])]),(0,l._)("td",null,[(0,l._)("div",{class:"btn btn-outline-danger rounded-pill p-0 px-4",title:"delete",onClick:a=>o.deleteFarmDiary(e.id,t)},[(0,l.Wm)(d,{icon:s.icon.cilTrash,size:"m"},null,8,["icon"])],8,Be)])])))),128))])])])])])])]),(0,l.Wm)(g,{visible:s.visibleLiveDemo,onClose:t[5]||(t[5]=()=>{s.visibleLiveDemo=!1})},{default:(0,l.w5)((()=>[(0,l.Wm)(c,{dismiss:"",onClose:t[0]||(t[0]=()=>{s.visibleLiveDemo=!1})},{default:(0,l.w5)((()=>[(0,l.Wm)(u,null,{default:(0,l.w5)((()=>[Le])),_:1})])),_:1}),(0,l.Wm)(p,null,{default:(0,l.w5)((()=>[(0,l._)("div",null,[Se,(0,l.wy)((0,l._)("input",{type:"text",class:"form-control",placeholder:"Auto increment code...",name:"id","onUpdate:modelValue":t[1]||(t[1]=e=>n.farmDiary.id=e),readonly:""},null,512),[[i.nr,n.farmDiary.id]]),We,(0,l.wy)((0,l._)("textarea",{type:"text",class:"form-control",placeholder:"Name Farm Diary...",name:"name",rows:"5","onUpdate:modelValue":t[2]||(t[2]=e=>n.farmDiary.name=e)},null,512),[[i.nr,n.farmDiary.name]])])])),_:1}),(0,l.Wm)(h,null,{default:(0,l.w5)((()=>[(0,l.Wm)(m,{color:"secondary",onClick:t[3]||(t[3]=()=>{s.visibleLiveDemo=!1})},{default:(0,l.w5)((()=>[Ue])),_:1}),(0,l.Wm)(m,{color:"primary",onClick:t[4]||(t[4]=e=>o.updateFarmDiary())},{default:(0,l.w5)((()=>[Me])),_:1})])),_:1})])),_:1},8,["visible"])])),_:1})}var Ee=a(4870),Ae={name:"FarmDiary",props:["farmDiaries"],data(){return{farmDiary:{},organization:this.$store.state.auth.user.user.organization}},methods:{editFarmDiary(e){this.visibleLiveDemo=!0,this.getFarmDiaryById(e)},getFarmDiaryById(e){X.Z.getFarmDiaryById(e).then((e=>{this.farmDiary=e.data}))},updateFarmDiary(){X.Z.updateFarmDiary(this.farmDiary.id,{id:this.farmDiary.id,name:this.farmDiary.name,organization:this.organization}).then((()=>{this.visibleLiveDemo=!1,window.location.reload(),this.$forceUpdate()})).catch((e=>{console.log(e)}))},deleteFarmDiary(e,t){null==e?this.farmDiaries.splice(this.farmDiaries.indexOf(e),1):this.$swal({title:"Are you sure?",text:"You won't be able to revert this!",icon:"warning",showCancelButton:!0,confirmButtonText:"Yes, delete it!",cancelButtonText:"No, cancel!",confirmButtonColor:"#55c663",reverseButtons:!0}).then((a=>{a.isConfirmed&&(this.$swal.fire("Deleted!","You have successfully deleted.","success"),X.Z.deleteFarmDiaryById(e).then((()=>{this.farmDiaries.splice(t,1)})))}))}},setup(){const e=(0,Ee.iH)(!1);return{icon:ae,visibleLiveDemo:e}}};const Ve=(0,we.Z)(Ae,[["render",$e]]);var Re=Ve,Oe={name:"form-plant",data(){return{plantId:"",isLoading:!1,farmDiary:"",message:"",cultureMedium:"",location:{},plantType:{},organization:{},images:[],user:{},org:this.$store.state.auth.user.user.organization,userSelected:"Chose person in change...",locationSelected:"Chose location...",plantTypeSelected:"Chose plant type...",plantTypeError:"",locationError:"",cultureMediumError:"",userError:"",preview_list:[],image_list:[],farmDiaries:[],optionsUser:[],optionsLocation:[],optionsPlantType:[]}},computed:{id(){return this.$route.params.id}},methods:{getPlantById(){"null"!==this.id&&(this.isLoading=!0,Q.Z.getPlantById(this.id).then((e=>{200==e.status&&(this.isLoading=!1,this.plantId=e.data.id,this.userSelected=e.data.user.id,this.locationSelected=e.data.location.id,this.plantTypeSelected=e.data.plantType.id,this.cultureMedium=e.data.cultureMedium,this.images=e.data.imageDtos,this.farmDiaries=e.data.farmDiaryDtos,this.location=e.data.location,this.plantType=e.data.plantType,this.organization=e.data.organization,this.user=e.data.user)})).catch((()=>{this.isLoading=!1,this.$swal.fire("Warning!","The system is experiencing an error, please reload the page","warning")})))},createPlant(){"Chose person in change..."==this.userSelected||"Chose location..."==this.locationSelected||"Chose plant type..."==this.plantTypeSelected?("Chose plant type..."==this.plantTypeSelected?this.plantTypeError=" Please select a plant type":this.plantTypeError="","Chose location..."==this.locationSelected?this.locationError=" Please select a location":this.locationError="","Chose person in change..."==this.userSelected?this.userError="Please select person in change":this.userError=""):(this.isLoading=!0,Q.Z.createPlant({cultureMedium:this.cultureMedium,location:this.location,plantType:this.plantType,orgId:this.org.id,user:this.user}).then((e=>{if(200==e.status){this.isLoading=!1,this.plantId=e.data.id;let t=this.$refs.uploadImage.files;t.length>0&&this.createImage(this.plantId),this.farmDiaries.forEach((e=>{null==e.id&&this.createFarmDiary(this.plantId,e.name)})),this.$swal.fire("Success!","Create successfully!","success"),this.$router.push("/plant/list-plant")}})).catch((e=>{this.isLoading=!1,this.$swal.fire("Failed!","Please check again!","error"),this.message=e.response.data.error})))},updatePlant(){"Chose person in change..."==this.userSelected||"Chose location..."==this.locationSelected||"Chose plant type..."==this.plantTypeSelected?("Chose plant type..."==this.plantTypeSelected?this.plantTypeError=" Please select a plant type":this.plantTypeError="","Chose location..."==this.locationSelected?this.locationError=" Please select a location":this.locationError="","Chose person in change..."==this.userSelected?this.userError="Please select person in change":this.userError=""):(this.isLoading=!0,console.log(this.org),Q.Z.updatePlant(this.id,{cultureMedium:this.cultureMedium,location:this.location,plantType:this.plantType,orgId:this.org.id,user:this.user}).then((e=>{if(200==e.status){this.isLoading=!1;let e=this.$refs.uploadImage.files;e.length>0&&this.createImage(this.id),this.farmDiaries.forEach((e=>{null==e.id&&this.createFarmDiary(this.plantId,e.name)})),this.$swal.fire("Success!","Update successfully!","success"),this.$router.push("/plant/list-plant")}})).catch((e=>{this.isLoading=!1,e.response.data.error==this.email+" already exists"?this.message=e.response.data.error:this.message="The system is crashing, please try again later"})))},getLocationRepository(){K.Z.getAllLocations(`?orgId=${this.org.id}`).then((e=>{this.optionsLocation=e.data}))},getValueLocation(e){K.Z.getLocationById(e).then((e=>{this.location=e.data}))},getUserRepository(){te.Z.getAllUsers(`?orgId=${this.org.id}`).then((e=>{this.optionsUser=e.data,this.optionsUser.map((function(e){return e.data_item=e.fullname+" -  - ("+e.role.roleName+")"}))}))},getValueUser(e){te.Z.getUserById(e).then((e=>{this.user=e.data}))},getPlantTypeRepository(){G.Z.getAllPlantTypes(`?orgId=${this.org.id}`).then((e=>{this.optionsPlantType=e.data}))},getValuePlantType(e){G.Z.getPlantTypeById(e).then((e=>{this.plantType=e.data}))},getOrganizationRepository(){J.Z.getAllOrganizations().then((e=>{this.organizations=e.data}))},getValueOrganization(e){J.Z.getOrganizationById(e.target.value).then((e=>{this.organization=e.data}))},previewMultiImage(e){var t=e.target,a=t.files.length,l=0;if(t.files)while(a--){var i=new FileReader;i.onload=e=>{this.preview_list.push(e.target.result)},this.image_list.push(t.files[l]),i.readAsDataURL(t.files[l]),l++}},createImage(e){this.formData=new FormData;let t=this.$refs.uploadImage.files;for(let a=0;a<t.length;a++)this.formData.append("files",t[a]);this.formData.append("plantId",e),this.formData.append("orgId",this.org.id),ee.Z.createImage(this.formData).then((()=>{}))},createFarmDiary(e,t){X.Z.createFarmDiary({name:t,plantId:e,organization:this.org}).then((()=>{}))},addFarmDiary(){var e=new Date;let t={id:null,name:this.farmDiary,plantId:this.id,createAt:e.toISOString(),updateAt:""};this.farmDiaries.push({...t}),this.farmDiary=""},resetForm(){this.$router.push("/plant/form-plant/null"),this.plantId="",this.cultureMedium="",this.userSelected="Chose person in change...",this.locationSelected="Chose location...",this.plantTypeSelected="Chose plant type...",this.preview_list=[],this.image_list=[],this.farmDiaries=[],this.plantTypeError="",this.organizationError="",this.locationError="",this.cultureMediumError="",this.userError=""},backToList(){this.$router.push("/plant/list-plant")}},created(){this.getLocationRepository(),this.getPlantTypeRepository(),this.getOrganizationRepository(),this.getUserRepository(),this.getPlantById()},setup(){return{icon:ae}},components:{Image:ve,FarmDiary:Re},watch:{userSelected(e,t){t!=e&&this.getValueUser(e)},locationSelected(e,t){t!=e&&this.getValueLocation(e)},plantTypeSelected(e,t){t!=e&&this.getValuePlantType(e)}}};const Ne=(0,we.Z)(Oe,[["render",q]]);var Ye=Ne}}]);
//# sourceMappingURL=255.950eb781.js.map
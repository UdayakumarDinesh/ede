<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.*" %>
 <%@page import="org.apache.commons.io.FilenameUtils"%>
 <%@page import="com.vts.ems.pis.model.*" %>
 <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Configurable Report</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">

.pic:hover img {
    -webkit-filter: opacity(50%)brightness(60%);
    -webkit-transform: scale3d(1.2, 1.2, 1);
    transform: scale3d(1.2, 1.2, 1);
}
.pic{
border: 8px solid rgba(255, 255, 255, 0.7);
}
.p-image1{
  color: #F0F5F6;
  opacity:0.8;
  font-size: 20px;
  position: absolute;
  top:80%;
  left: 80%;

  
}
.pic1:hover img {
    -webkit-filter: opacity(50%)brightness(60%);
    -webkit-transform: scale3d(1.2, 1.2, 1);
    transform: scale3d(1.2, 1.2, 1);
}
.pic1{
border: 8px solid rgba(255, 255, 255, 0.7);
}
.p-image{
  color: #A49F9F ;
  opacity:0.8;
  font-size: 20px;
  position: absolute;
  top:80%;
  left: 80%;


}


.card .card-media {
    padding: 0 20px;
    margin: 0 -14px;
}

.card .card-media img {
    max-width: 100%;
    max-height: 100%;
}

.card.hovercard {
    position: relative;
    padding-top: 0;
    overflow: hidden;
    text-align: center;
    background-color: rgba(214, 224, 226, 0.2);
}

.card.hovercard .cardheader {
    background-color:#0e6fb6;
    background-size: cover;
    height: 135px;
}

.card.hovercard .avatar {
    position: relative;
    top: 10px;
    margin-bottom: -50px;
}

.card.hovercard .avatar img {
    width: 100px;
    height: 100px;
    max-width: 144px;
    max-height: 110px;
    -webkit-border-radius: 50%;
    -moz-border-radius: 50%;
    border-radius: 50%;
    border: 5px solid rgba(255,255,255,0.5);
}

.card.hovercard .info {
    padding: 4px 8px 10px;
}

.card.hovercard .info .title {
    margin-bottom: 4px;
    font-size: 24px;
    line-height: 1;
    color: #262626;
    vertical-align: middle;
}

.card.hovercard .info .desc {
    overflow: hidden;
    font-size: 12px;
    line-height: 20px;
    color: #737373;
    text-overflow: ellipsis;
}

.card.hovercard .bottom {
    padding: 0 20px;
    margin-bottom: 17px;
}


</style>
<spring:url value="/webresources/js/dataTables.buttons.min.js" var="datatablejsbuttons" />
<script src="${datatablejsbuttons}"></script>

<spring:url value="/webresources/js/buttons.flash.min.js" var="datatablejsflash" />
<script src="${datatablejsflash}"></script>

<spring:url value="/webresources/js/jszip.min.js" 	var="datatablejszip" />
<script src="${datatablejszip}"></script>

<spring:url value="/webresources/js/pdfmake.min.js" var="datatablejspdfmake" />
<script src="${datatablejspdfmake}"></script>

<spring:url value="/webresources/js/vfs_fonts.js" var="datatablejsvfs" />
<script src="${datatablejsvfs}"></script>

<spring:url value="/webresources/js/buttons.html5.min.js" 	var="datatablejshtml5" />
<script src="${datatablejshtml5}"></script>

<spring:url value="/webresources/js/buttons.print.min.js" 	var="datatablejsprint" />
<script src="${datatablejsprint}"></script>

</head>
<body>
<%
List<PisCadre> cadreNames=(List<PisCadre>)request.getAttribute("piscaderlist");
List<PisCatClass> catclass=(List<PisCatClass>)request.getAttribute("piscatclasslist"); 
List<DivisionMaster> divisionlist=(List<DivisionMaster>)request.getAttribute("divisionlist");
List<PisCategory> Category=(List<PisCategory>)request.getAttribute("piscategorylist");
List<EmployeeDesig> Designation=(List<EmployeeDesig>)request.getAttribute("desiglist");
List<Object[]> modeOfRecruit=(List<Object[]>)request.getAttribute("modeOfRecruit");
List<Object[]> ConfigurableReportList=(List<Object[]>)request.getAttribute("ConfigurableReportList");

List<Object[]> AwardNameList=(List<Object[]>)request.getAttribute("AwardsNamelist");
Map<String,String> map=(Map)request.getAttribute("map");



Object[]  employeedetails=(Object[])request.getAttribute("employeedetails");
String  PhotoUrl=(String)request.getAttribute("basevalue");
Object[]  perAddressDetails=(Object[])request.getAttribute("peraddressdetails"); 
List<Object[]>  resAddressDetails =(List<Object[]>)request.getAttribute("resaddressdetails");
List<Object[]>  appointmentDetailList =(List<Object[]>)request.getAttribute("Appointmentlist");
List<Object[]>  awardsDetailList = (List<Object[]>)request.getAttribute("Awardslist");
List<Object[]>	familyDetailList =(List<Object[]>)request.getAttribute("familydetails");
List<Object[]>	propertyDetailList = (List<Object[]>)request.getAttribute("Propertylist");
List<Object[]>	publicationDetailList =(List<Object[]>)request.getAttribute("Publicationlist");
List<Object[]>	qualificationDetailList =(List<Object[]>)request.getAttribute("Educationlist");
Object[]   PassportDetails=(Object[])request.getAttribute("PassportList"); 
List<Object[]>  foreignVisitDetails =(List<Object[]>)request.getAttribute("PassportVisitList");

%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-6">
					<h5>Employee Wise Complete Configurable Report</h5>
				</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PIS.htm">PIS</a></li>
						<li class="breadcrumb-item active " aria-current="page">Employee Wise Complete Configurable Report</li>
					</ol>
				</div>
			</div>
</div>


 <div class=" page card dashboard-card">
 <div class="card">
	 <div class="card-header"><b>Search By Following Fields</b></div>	
	<div class="card-body" >
	
	 <form action="ConfigurableReport.htm" method="post"  >
		<div class="card" >
			<div class="card-body " >
                        <!--rank,name ,designation ,group name, cat class-->
	             <div class="form-group">
	              <div class="row">
	              
	                <div class="col-md-4">
	                <label>Name</label>
                      <input  name="name"     maxlength="75" placeholder="Search By Name"   class="form-control input-sm" value="<%if(map!=null){%><%=map.get("name")%><%}%>"  onclick="return trim(this)" onchange="return trim(this)">
                   </div>
	               
	               <div class="col-md-2">
                        <label>Designation</label>
                         <select name="Designation" class="form-control input-sm selectpicker" required="required"  data-live-search="true">  
                      <option value="Select#Select">Select</option> 
                      <%if(Designation!=null&&Designation.size()>0){
                         for(EmployeeDesig details:Designation){ %>
                             <option value="<%= details.getDesigId()%>#<%= details.getDesignation()%>" <%if(map!=null&&map.get("DesigId").toString().equals(String.valueOf(details.getDesigId()))){%> selected="selected" <%}%>><%= details.getDesignation()%></option>
                       <%}} %>  
                      
                       </select>
                    </div>
	              
	               
	              <div class="col-md-2">
	                    <label>Group/Division</label>
                        <select  name="GroupDivision" class="form-control input-sm selectpicker"  required data-live-search="true">
                        
                        <option value="Select#Select">Select</option> 
                        <%if(divisionlist!=null&&divisionlist.size()>0){
                        for(DivisionMaster details:divisionlist){ %>
                          <option value="<%= details.getDivisionId()%>#<%= details.getDivisionName()%>" <%if(map!=null&&map.get("GroupId").toString().equals(String.valueOf(details.getDivisionId()))){%> selected="selected" <%}%>><%= details.getDivisionName()%></option>
                       <%}} %> 
                        
                        </select>
                 </div>
	              
	              <div class="col-md-2">
	                  <label>Category Class</label>
                       <select name="CatClass" class="form-control input-sm selectpicker" required >

                         <option value="Select#Select">Select</option> 
                         <%if(catclass!=null&&catclass.size()>0){
                          for(PisCatClass details1:catclass){%>
                          <option value="<%=details1.getCat_id()%>#<%= details1.getCat_name()%>" <%if(map!=null&&map.get("CatId").toString().equals(String.valueOf(details1.getCat_class_id()))){%> selected="selected" <%}%>><%= details1.getCat_name()%> </option>
                          <%} }%>
                       </select>
                 </div>
	              
	              <div class="col-md-2">
	                  <label>Gender Wise</label>
                    <select name="Gender" class="form-control input-sm selectpicker" required>  
                      <option value="Select#Select">Select</option> 
                      <option value="M#M" <%if(map!=null&&map.get("Gender").equals("M")){%> selected="selected" <%}%>>Male</option>
                      <option value="F#F" <%if(map!=null&&map.get("Gender").equals("F")){%> selected="selected" <%}%>>Female</option> 
                    </select>
	            </div>
	             
	              </div>
	              </div>
	              <!--//rank,name ,designation ,group name,catclass  -->
	              
	              
	             <!--rank,name ,designation ,group name, cat class-->
	             <div class="form-group">
	              <div class="row">
	              
	                 <div class="col-md-2">
                        <label>Cadre</label>
                        <select name="Cadre" class="form-control input-sm selectpicker " required="required" >  

                         <option value="Select#Select">Select</option>      
                         <%if(cadreNames!=null&&cadreNames.size()>0){
                           for(PisCadre details:cadreNames){ %>
                           <option value="<%= details.getCadreId()%>#<%= details.getCadre()%>"<%if(map!=null&&map.get("CadreId").toString().equals(String.valueOf(details.getCadreId()))){%> selected="selected" <%}%>  ><%= details.getCadre()%></option>
                          <%}}%> 
                       </select>
                    </div>
                    
                     <div class="col-md-2">
                        <label>Service Status</label>
                        <select name="ServiceStatus" class="form-control input-sm selectpicker" required="required">  
                        <option value="Select">Select</option> 
                        <option value="Confirmed" <%if(map!=null&&map.get("ServiceStatus").toString().equals("Confirmed")){%> selected="selected" <%}%> >Confirmed</option>
                        <option value="Probation" <%if(map!=null&&map.get("ServiceStatus").toString().equals("Probation")){%> selected="selected" <%}%>>Probation</option>
                        <option value="Adhoc" <%if(map!=null&&map.get("ServiceStatus").toString().equals("Adhoc")){%> selected="selected" <%}%>>Adhoc</option>
                        <option value="Temporary" <%if(map!=null&&map.get("ServiceStatus").toString().equals("Temporary")){%> selected="selected" <%}%>>Temporary</option>  
                        <option value="Contract" <%if(map!=null&&map.get("ServiceStatus").toString().equals("Contract")){%> selected="selected" <%}%>>Contract</option>
                        
                       </select>
                    </div>
	               
	               <div class="col-md-2">
                        <label>Category</label>
                        <select name="Category" class="form-control input-sm selectpicker" required="required" >  
                       <option value="Select#Select">Select</option> 
                       <%if(Category!=null&&Category.size()>0){
                       for(PisCategory details:Category){ %>
                       <option value="<%= details.getCategory_id()%>#<%= details.getCategory_desc()%>" <%if(map!=null&&map.get("CategoryId").toString().equals(String.valueOf(details.getCategory_id()))){%> selected="selected" <%}%>><%= details.getCategory_desc()%></option>
                       <%}}%>  
                       </select>
                    </div>
	              
	             <div class="col-md-2">
	                  <label>Awards</label>                  
	               <select name="Awards" class="form-control input-sm selectpicker" required >
                         <option value="Select#Select">Select</option> 
                       <%if(AwardNameList!=null && AwardNameList.size()>0){ for (Object[] a : AwardNameList) {%>
					    <option value="<%=a[0]%>#<%=a[1]%>" <%if(map!=null&&map.get("AwardId").toString().equals(a[0].toString())){%> selected="selected" <%}%>><%=a[1]%></option>
					   <%}}%>
                       </select>
	              </div>          
	              
	              <div class="col-md-2">
	                  <label>Appointment</label>
                       <select name="Appointment" class="form-control input-sm selectpicker" required data-live-search="true">
                        <option value="Select#Select">Select</option> 
                       <%if(modeOfRecruit!=null && modeOfRecruit.size()>0){ for (Object[] m : modeOfRecruit) {%>
					    <option value="<%=m[0]%>#<%=m[1]%>"<%if(map!=null&&map.get("modeOfRecruitId").toString().equals(m[0].toString())){%> selected="selected" <%}%> ><%=m[1]%></option>
					   <%}}%>
                       </select>
                 </div>
	              
	              
	               <div class="col-md-2">
	                    <label>Blood Group</label>
                        <select  name="BG" class="form-control input-sm selectpicker">
                             
                            <option value="Select">Select</option> 
                            <option value="A-" <%if(map!=null&&map.get("BG").toString().equals("A-")){%> selected="selected" <%}%>>A-</option>
                            <option value="A+" <%if(map!=null&&map.get("BG").toString().equals("A+")){%> selected="selected" <%}%>>A+</option>
                            <option value="B-"<%if(map!=null&&map.get("BG").toString().equals("B-")){%> selected="selected" <%}%>>B-</option>
                            <option value="B+" <%if(map!=null&&map.get("BG").toString().equals("B+-")){%> selected="selected" <%}%>>B+</option>
                            <option value="AB-" <%if(map!=null&&map.get("BG").toString().equals("AB-")){%> selected="selected" <%}%>>AB-</option>
                            <option value="AB+" <%if(map!=null&&map.get("BG").toString().equals("AB+")){%> selected="selected" <%}%>>AB+</option>
                            <option value="O-" <%if(map!=null&&map.get("BG").toString().equals("O-")){%> selected="selected" <%}%>>O-</option>
                            <option value="O+" <%if(map!=null&&map.get("BG").toString().equals("O+")){%> selected="selected" <%}%>>O+</option>
                            <option value="Not Available" <%if(map!=null&&map.get("BG").toString().equals("Not Available")){%> selected="selected" <%}%>>Not Available</option>
                        </select>
                 </div>

	              </div>
	              </div>
	              <!--//rank,name ,designation ,group name,catclass  -->

						<div  align="center">
							<button class="btn btn-sm submit-btn" name="submit"
								value="submit">Submit</button>
						</div>
					</div>	
		</div>
		<br>
		<%if(ConfigurableReportList!=null&&ConfigurableReportList.size()>0){ %>
		<table class="table table-bordered table-hover table-striped table-condensed DataTableWithPdfandExcel"  > 
							<thead> 
                                  <tr>       
                                     <th>Name</th>
                                     <th>Designation</th>
                                     <th>Group/Div</th>
                                     <th>Category Class</th>
                                     <th>Category</th>
                                     <th>Service Status</th>
                                     <th>Cadre</th>
                                     <th>Details</th>   
                                  </tr>
                             </thead>
                             <tbody>
                             <%
                               for(Object[] ls:ConfigurableReportList){
                             %>
                             <tr>
		                             <td><%if(ls!=null && ls[1]!=null){%> <%=ls[1]%> <%}else{%> -- <%}%></td>
		                             <td><%if(ls!=null && ls[2]!=null){%> <%=ls[2]%> <%}else{%> -- <%}%></td> 
		                             <td><%if(ls!=null && ls[7]!=null){%> <%=ls[7]%> <%}else{%> -- <%}%></td>
		                             <td><%if(ls!=null && ls[8]!=null){%> <%=ls[8]%> <%}else{%> -- <%}%></td>
		                             <td><%if(ls!=null && ls[10]!=null){%><%=ls[10]%> <%}else{%> -- <%}%></td> 
		                             <td><%if(ls!=null && ls[5]!=null){%> <%=ls[5]%> <%}else{%> -- <%}%></td>
		                             <td><%if(ls!=null && ls[9]!=null){%> <%=ls[9]%> <%}else{%> -- <%}%></td>
								     <td align="center"><button class="btn btn-sm "style="color: white; background-color: #3275a8;"name="Details" value="<%=ls[0]%>">Details</button></td>
							</tr>
                            <%}%>
                           </tbody> 
							</table>
							<%}%>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />	
		</form>
		<br>
		<!-- --------------------------user profile-------------------- -->

<div class="row">
<%if(employeedetails!=null){ %>
<div class="col-3">

            <div class="card hovercard">
                <div class="cardheader">
                <%if(employeedetails!=null){%>
               <div class="avatar">
              
                    <%if(PhotoUrl!=null){%>
                   <img src="data:image/<%=FilenameUtils.getExtension(employeedetails[26].toString())%>;base64,<%=PhotoUrl%>" class="img-responsive img-rounded" style=" width: 100%; height:124px; cursor: pointer;" id="profileImage1" >
                    <%}else if("M".equalsIgnoreCase(employeedetails[9].toString())){%>
                    <img alt="" src="./view/images/user.png">
                    <%}else if("F".equalsIgnoreCase(employeedetails[9].toString())){%>
                    <img alt="" src="./view/images/user.png">
                    <%}else{%>
                     <img src="data:image/<%=FilenameUtils.getExtension(employeedetails[26].toString())%>;base64,<%=PhotoUrl%>" class="img-responsive img-rounded" style=" width: 100%; height:124px; cursor: pointer;" id="profileImage1" >
                    <%}%>
                </div>
                </div>
               
                <div class="info">
                   <div class="title">
                        <h5><%=employeedetails[3]%></h5>
                    </div>
                      	<table class="table table-striped table-bordered" >
	                    <tbody>
	                         
	                         <tr><th>Designation:</th><td  style=" text-align: left;"><%=employeedetails[19]%></td></tr>
	                         <tr><th>DOB:</th><td  style=" text-align: left;"><%if(employeedetails[5]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(employeedetails[7].toString())%><%}%> </td></tr>
	                         <tr><th>Blood Group</th><td  style=" text-align: left;"><%=employeedetails[10]%> </td></tr> 
	                         <tr><th>PAN:</th><td  style=" text-align: left;"><%=employeedetails[13]%> </td></tr>
	                         <tr><th>Category:</th><td  style=" text-align: left;"><%=employeedetails[18]%> </td></tr>  
	                         <tr><th>UID:</th><td  style=" text-align: left;"><%=employeedetails[15]%> </td></tr>
	                      </tbody>
	            </table>
	            
              </div> 
           <%}%>
         </div>
        </div><!-- //col-md3 -->

          
           <!-- col9 -->
   <div class="col-md-9" >
			<ul class="nav nav-tabs" role="tablist">
				<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#tabs-9" role="tab" >Basic Details</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-1" role="tab">Address</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab">Appointment</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-3" role="tab">Awards</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-4" role="tab">Family</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-5" role="tab">Property</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-6" role="tab">Publications</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-7" role="tab">Qualifications</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-8" role="tab">Passport</a></li>
				
			</ul>
       
        <div id="tabs" class="" style="margin-top: 60px;">
		<div class="tab-content">

			<!-- basic detail -->
			<div id="tabs-9" class="tab-pane  active" role="tabpanel">
             	<table class="table table-striped table-bordered" >

					<tbody>
						
				    	<tr>
						<td ><b>Division Name</b></td>
						<td ><%if(employeedetails!=null){%><%= employeedetails[20]%><%}%></td>
						<td ><b>Cat Class</b></td>
						<td ><%if(employeedetails!=null && employeedetails[32]!=null){%><%= employeedetails[32]%><%}else{%> -- <%}%></td>
						<td ><b>Service Status</b></td>
						<td  ><%if(employeedetails!=null && employeedetails[36]!=null){%><%= employeedetails[36]%> <%}else{%> -- <%}%></td>
					  </tr>
					 
					  <tr>
						<td ><b>Employee Status</b></td>
						<td  ><%if(employeedetails!=null && employeedetails[37]!=null){%><%= employeedetails[37]%><%}else{%> -- <%}%></td>
						<td ><b>PunchCard No</b></td>
						<td ><%if(employeedetails!=null && employeedetails[14]!=null){%><%=employeedetails[14]%><%}else{%>--<%}%></td>
						<td ><b>Gender</b></td>
						<td  ><%if(employeedetails!=null && employeedetails[9]!=null){%><%if("M".equalsIgnoreCase(employeedetails[9].toString())){out.println("Male");}else{out.println("FeMale");%><%}}%></td>
					  </tr>
					 
					 <tr>
						<td ><b>DOJ</b></td>
						<td  ><%if(employeedetails!=null){%><%= DateTimeFormatUtil.SqlToRegularDate(employeedetails[6].toString())%><%}%></td>
						<td ><b>DOA</b></td>
						<td ><%if(employeedetails!=null){%><%= DateTimeFormatUtil.SqlToRegularDate(employeedetails[7].toString())%><%}%></td>
						<td ><b>DOR</b></td>
						<td  ><%if(employeedetails!=null){%><%= DateTimeFormatUtil.SqlToRegularDate(employeedetails[8].toString())%><%}%></td>
					  </tr>
					 
					 <tr>
						<td ><b>GPF/PRAN</b></td>
						<td  ><%if(employeedetails!=null && employeedetails[22]!=null){%><%= employeedetails[22]%><%}else{%>--<%}%></td>
						<td ><b>PIN DRONA</b></td>
						<td align="center"><%if(employeedetails!=null){%>--<%}else{%>--<%}%></td>
						<td ><b>Cadre Name</b></td>
						<td ><%if(employeedetails!=null){%><%= employeedetails[33]%><%}%></td>
						
					  </tr>
					 
					  <tr>
						<td ><b>Pay Level</b></td>
						<td  ><%if(employeedetails!=null && employeedetails[28]!=null){%><%= employeedetails[28]%><%}else{%>--<%}%></td>
						<td ><b>SBI Acc</b></td>
						<td  ><%if(employeedetails!=null && employeedetails[17]!=null){%><%= employeedetails[17]%><%}else{%> -- <%}%></td>
						<td ><b>Religion</b></td>
						<td ><%if(employeedetails!=null && employeedetails[12]!=null){%><%= employeedetails[12]%><%}else{%> -- <%}%></td>
					  </tr>
					 
					 <tr>
						<td ><b>Quarter Available</b></td>
						<td  ><%if(employeedetails!=null && employeedetails[25]!=null){%><%if(employeedetails[25]!=null&&"Y".equalsIgnoreCase(employeedetails[25].toString())){%><%="Yes"%><%}else{%><%="No"%><%}}else{%> -- <%}%></td>
						<td ><b>Physical Handicap</b></td>
						<td ><%if(employeedetails!=null && employeedetails[38]!=null){%><%if(employeedetails[38]!=null&&"Y".equalsIgnoreCase(employeedetails[38].toString())){%><%="Yes"%><%}else{%><%="No"%><%}}else{%> --<%}%></td>
						<td ><b>Hometown</b></td>
						<td ><%if(employeedetails!=null && employeedetails[24]!=null){%><%= employeedetails[24]%><%}else{%>--<%}%></td>
					</tr>
				 	<tr>
					 <%if(employeedetails!=null){%>
					    <td ><b>Permanent PassNO</b></td>
						<td ><%if(employeedetails!=null && employeedetails[34]!=null){%><%= employeedetails[34]%><%}else{%> -- <%}%></td>
					    <td ><b>Ex ServiceMan</b></td>
						<td ><%if(employeedetails[35]!=null && "Y".equalsIgnoreCase(employeedetails[35].toString())){%><%="Yes"%><%}else if(employeedetails[35]!=null && "N".equalsIgnoreCase(employeedetails[37].toString())){%><%="No"%><%}else{%>--<%}%></td>
					 <%} %>
					 </tr> 
					</tbody>
                </table>
         	</div>
			<!-- //basic detail -->	
			

			<!-- Address -->
			<div id="tabs-1" class="tab-pane fade ">
				
				
				<table class="table table-striped table-bordered" >

				<thead>
					<tr>	
						<th>Permanent Address</th>
						<th>City</th>
						<th>Mobile</th>
					    <th>Alt Mobile</th>
				   </tr>	
				   </thead>
				   <tbody>
				     <%if(perAddressDetails!=null){%>
					<tr>
						<td><%=perAddressDetails[6]%></td>
						<td><%=perAddressDetails[1]%></td>
						<td align="center"><%=perAddressDetails[5]%></td>
						<td align="center"><%=perAddressDetails[0]%></td>
					</tr>
					<%}else{%>
					<tr>
						<td colspan="4" align="center"> <h5>Permanent Address Not Added!</h5></td>
					</tr>
					<%}%>
				</tbody>
			</table>

				<table class="table table-striped table-bordered" >

				<thead>
					<tr>	
						<th>Residential Address</th>
						<th>Mobile</th>
						<th>Ext no.</th>
						<th>Quarter No.</th>
						<th>Quarter Type</th>
					    <th>Email Official</th>
				   </tr>	
				   </thead>
				   <tbody>
					<%if(resAddressDetails!=null&&resAddressDetails.size()>0){
					for(Object[] ls:resAddressDetails){ %>
					<tr>
						<td><%=ls[5]%></td>
						<td align="center"><%=ls[4]%></td>
						<td><%=ls[8]%></td>
						<td><%=ls[9]%></td>
						<td><%=ls[10]%></td>
						<td><%=ls[11]%></td>
					</tr>
					<%}}else{%>
					<tr>
					    <td colspan="6" align="center"> <h5>Residential Address Not Added!</h5></td>
					</tr>
					<%}%>
				</tbody>
			</table>
		</div>
		<!--// Address -->	
		
		
		<!-- Appointment -->
			<div id="tabs-2" class="tab-pane fade">
				<table class="table table-striped table-bordered" >
					<thead>
						<tr>
						 <th>Designation</th>
						 <th>Org/Lab</th>
						 <th>DRDO/Others</th>
						 <th>From Date</th>
						 <th>To Date</th>
						</tr>
					</thead>
					<tbody>
					<%if(appointmentDetailList!=null&&appointmentDetailList.size()>0){
					for(Object[] obj:appointmentDetailList){ %>
					<tr>
			                                 <td> <%=obj[7]%> </td>
			                                 <td> <%=obj[2]%> </td>
			                                 <td> <%=obj[5]%> </td>
			                                 <td align="center"> <%if(obj[8]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[8].toString())%><%} %> </td>
			                                 <td align="center"> <%if(obj[9]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[9].toString())%><%} %> </td>
					</tr>
					<%}}else{%>
					<tr>
					    <td colspan="5" align="center"> <h5>Appointment Details Not Added!</h5></td>
					</tr>
					<%}%>
					</tbody>

				</table>
			</div>
		<!-- //Appointment -->
			
		<!-- Awards -->
			<div id="tabs-3" class="tab-pane fade">
				<table class="table table-striped table-bordered" >
					<thead>
					<tr>
						<th>Award Name</th>
						<th>Award By</th>
						<th>Details</th>
						<th>Award Date</th>
						<th>Certificate</th>
						<th>Citation</th>
						<th>Medallian</th>
						<th>Award Category</th>
						<th>Cash Ammount</th>
						</tr>
					</thead>
				
					<tbody>
					<%if(awardsDetailList!=null&&awardsDetailList.size()>0){
					  for(Object[] ls:awardsDetailList){%>
					  <tr>
						<td><%=ls[2]%><%if("7".equalsIgnoreCase(ls[9].toString())){%><%=" / "+ls[0]%><%}%></td>
						<td><%=ls[3]%></td>
						<td><%=ls[4]%></td>
						<td><%if(ls[5]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[5].toString()));}%></td>
						<td><%=ls[7]%></td>
						<td><%=ls[9]%></td>
						<td><%=ls[11]%></td>
						<td><%=ls[12]%></td>
						<td><%=ls[15]%></td>
					  </tr>
					<%}}else{%>
					<tr>
						<td colspan="9" align="center"> <h5>Awards Details Not Added!</h5> </td>
					</tr>
					<%}%>	
					</tbody>
              </table>
           </div>
		<!-- //Awards -->
			
			<!-- Family -->
			<div id="tabs-4" class="tab-pane fade">
				<table class="table table-striped table-bordered" >
					<thead><tr>
						<tr>
						<th>Member Name</th>
						<th>Relation</th>
						<th>Date Of Birth</th>
						<th>Med Dep</th>
						<th>Blood Group</th>
						</tr>
					</thead>
					
					<tbody>
					<%if(familyDetailList!=null&&familyDetailList.size()>0){
					  for(Object[] ls:familyDetailList){%>
					  <tr>
						<td><%=ls[0]%></td>
						<td><%=ls[1]%></td>
						<td align="center"><%if(ls[2]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[2].toString()));}%></td>
						<td><%if(ls[3]!=null && "Y".equalsIgnoreCase(ls[3].toString())){%> Yes <%}else{%>  No<%}%></td>
						<td align="center"><%=ls[4]%></td>
						
					  </tr>
					<%}}else{%>	
					<tr>
						<td colspan="5" align="center"> <h5>Family Details Not Added!</h5> </td>
					</tr>
					<%}%>
					</tbody>
				</table>
           </div>
				<!-- //Family -->
			
			
				<!-- Property -->
			<div id="tabs-5" class="tab-pane fade">
				<table class="table table-striped table-bordered" >
					<thead><tr>
						<th>Movable</th>
						<th>value</th>
						<th>Details</th>
						<th>DOP</th>
						<th>Acquired Type</th>
						<th>Noting On</th>
						<th>Remarks</th>
					</tr></thead>
					<tbody>
					  <%if(propertyDetailList!=null&&propertyDetailList.size()>0){
					   for(Object[] ls:propertyDetailList){%>
					   <tr>
						<td><%if("N".equalsIgnoreCase(ls[2].toString())){%><a class="btn btn-primary btn-xs btn-outline" href="print?PropertyID=<%=ls[8]%>" target="blank">Immovable</a><%}else{%>Movable<%}%></td>
						<td><%=ls[3]%></td>
						<td><%=ls[4]%></td>
						<td align="center"><%if(ls[5]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[5].toString()));}%></td>
						<td><%=ls[6]%></td>
						<td align="center"><%if(ls[7]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[7].toString()));}%></td>
						<td><%=ls[8]%></td>

					  </tr>
					<%}}else{%>
					<tr>
						<td colspan="7" align="center"> <h5>Property Details Not Added!</h5> </td>
					</tr>
					<%}%>	
					</tbody>
              </table>
          </div>
			<!--// Property -->
			
			<!-- Publication -->
			<div id="tabs-6" class="tab-pane fade">
                 <table class="table table-striped table-bordered" >
					<thead>
					<tr>
						<th>Publication Type</th>
						<th>Authors</th>
						<th>Discipline</th>
						<th>Title</th>
						<th>Publication Name</th>
						<th>Publication Date</th>
						<th>Patent No</th>
                     </tr>
					</thead>
					<tbody>
						<%if(publicationDetailList!=null&&publicationDetailList.size()>0){
						for(Object[] ls:publicationDetailList){%>
					  <tr>
						<td><%=ls[0]%></td>
						<td><%=ls[1]%></td>
						<td><%=ls[2]%></td>
						<td><%=ls[3]%></td>
						<td><%=ls[8]%></td>
						<td align="center"><%if(ls[7]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[7].toString()));}%></td>
						<td><%=ls[9]%></td>
					  </tr>
					<%}}else{%>
					<tr>
						<td colspan="7" align="center"> <h5>Publication Details Not Added!</h5> </td>
					</tr>
					<%}%>		
					</tbody>
                 </table>
         	</div>
			<!--// Publication -->
			
			<!-- Qualification -->
			<div id="tabs-7" class="tab-pane fade">
             	<table class="table table-striped table-bordered" >
					<thead>
						<tr>
						<th>Quali Title</th>
						<th>Disci Title</th>
						<th>University</th>
						<th>Year Of Passing</th>
						<th>CGPA</th>
						<th>Specialization</th>
						<th>Sponsored</th>
						<th>Acq_Bef_Aft</th>
						
						</tr>
					</thead>
					<tbody>
						<%if(qualificationDetailList!=null&&qualificationDetailList.size()>0){
						for(Object[] ls:qualificationDetailList){%>
					  <tr>
							<td><%=ls[2]%></td>
							<td><%=ls[3]%></td>
							<td><%=ls[4]%></td>
							<td><%=ls[5]%></td>
							<td><%=ls[6]%></td>
							<td><%=ls[8]%></td>
							<td><%=ls[7]%></td>
							<td><%if(ls[9]!=null && "Y".equalsIgnoreCase(ls[9].toString())){%> Yes <%}else{%>  No <%}%></td>
						  
					  </tr>
					<%}}else{%>
					<tr>
						<td colspan="8" align="center"> <h5>Qualification Details Not Added!</h5> </td>
					</tr>
					<%}%>	
					</tbody>
                </table>
         	</div>
			<!-- //Qualification -->	
				
				<!-- Passport  and foreign visit-->
			<div id="tabs-8" class="tab-pane fade ">
				<table class="table table-striped table-bordered" >

				<thead>
					 <tr>	
						<th>Passport Type</th>
						<th>Valid From</th>
						<th>Valid To</th>
					    <th>Passport No</th>
					 </tr>	
				   </thead>
				   <tbody>
				    <%if(PassportDetails!=null){%>
				    <tr>
						<td> <%=PassportDetails[1]%></td>
						<td align="center"> <%if(PassportDetails[2]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(PassportDetails[2].toString()));}%></td>
					    <td align="center"> <%if(PassportDetails[3]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(PassportDetails[3].toString()));}%></td>
						<td align="center"> <%=PassportDetails[4]%></td>
					</tr>
					<%}else{%>
					<tr>
						<td colspan="4" align="center"> <h5>Passport Details Not Added!</h5> </td>
					</tr>
					<%}%>	
				</tbody>
			</table>
				
				<table class="table table-striped table-bordered" >

				<thead>
					<tr>	
						<th>Country Name</th>
						<th>Visit From</th>
						<th>Visit To</th>
						<th>Purpose</th>
						<th>NOC Letter No</th>
				   </tr>	
				   </thead>
				   <tbody>
					<%if(foreignVisitDetails!=null&&foreignVisitDetails.size()>0){
					for(Object[] ls:foreignVisitDetails){%>
					<tr>
						<td><%=ls[2]%></td>
						<td align="center"><%if(ls[6]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[6].toString()));}%></td>
						<td align="center"><%if(ls[7]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[7].toString()));}%></td>
						<td><%=ls[5]%></td>
						<td align="center"><%=ls[3]%></td>
					</tr>
				  <%}}else{%>
				    <tr>
						<td colspan="5" align="center"> <h5>Foreign Visit Details Not Added!</h5> </td>
					</tr>
					<%}%>	
				</tbody>
			</table>
		</div>
		<!-- //Passport  and foreign visit-->	
				   </div><!-- tab-content -->
			    </div>
              </div>  <!-- //col9 -->
<%} %>
</div>
	</div>
</div>
</div>

	
<script type="text/javascript">

$(document).ready(function() {
	
    $('.DataTableWithPdfandExcel').dataTable( 
    {    	
    	dom: 'Brt',
    	lengthMenu: [ 50, 75, 100],
    	buttons: [
         	{
         		
         		extend: "pdfHtml5",
         		className: "btn-sm btn1  ",
         		title: ' Report', 
         		orientation: 'portrait',
         		pageSize: 'A4',
         		titleAttr: 'Print PDF',
         		text: '<i class="fa-solid fa-file-pdf" style="font-size:24px; color: red;" ></i>',
         		
         		download:'open',
         		
         		
         	},
         	{
         		extend: "excel",
         		className: "btn-sm btn1 ",
         		title: ' Report',
         		titleAttr: 'Print excel',
         		text: '<i class="fa-solid fa-file-excel" style="font-size:24px ; color: green;" ></i>'
         	}
         	/* ,{
                extend:    'csvHtml5',
                className: "btn-sm btn1 ",
                text:      '<i class="fa fa-file-text-o" style="font-size:25px" aria-hidden="true" ></i>',
                titleAttr: 'CSV'
            },{
                extend: 'copyHtml5',
                className: "btn-sm btn1 ",
                text:   '<i class="fa fa-clipboard" style="font-size:25px" aria-hidden="true"></i>',
                titleAttr: 'Copy',
                title: 'Milestone Report'
            } */
    	]
    	
    } );
} );


</script>

</body>
</html>
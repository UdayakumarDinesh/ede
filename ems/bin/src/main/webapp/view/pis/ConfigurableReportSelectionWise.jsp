<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Objects"%>
<%@page import="org.springframework.util.CollectionUtils"%>
<%@page import="java.util.*"%>
<%@page import="com.vts.ems.pis.model.*"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Configurable Selection Wise Report</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<style type="text/css">

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
<body >
<%
List<PisCadre> cadreNames=(List<PisCadre>)request.getAttribute("CadreNames");
List<PisCatClass> catclass=(List<PisCatClass>)request.getAttribute("Catclass"); 
List<Object[]> GroupName=(List<Object[]>)request.getAttribute("GroupName");
List<PisCategory> Category=(List<PisCategory>)request.getAttribute("Category");
List<Object[]> Designation=(List<Object[]>)request.getAttribute("Designation");
List<Object[]> modeOfRecruit=(List<Object[]>)request.getAttribute("ModeOfRecruit");
List<Object[]> ConfigurableReportList=(List<Object[]>)request.getAttribute("ConfigurableReportList");
List<Object[]> AwardType=(List<Object[]>)request.getAttribute("awardslist");
List<PisPayLevel> PayLevel=(List<PisPayLevel>)request.getAttribute("PayLevel");
List<Object[]> qualification=(List<Object[]>)request.getAttribute("qualification");

List<Object[]> confreports=(List<Object[]>)request.getAttribute("confreports");
List<String> tblheadings=(List<String>)request.getAttribute("tblheadings");


String Genderlist=(String)request.getAttribute("Genderlist");
String ServiceStatuslist=(String)request.getAttribute("ServiceStatuslist");
String BGlist=(String)request.getAttribute("BGlist");
String Religionlist=(String)request.getAttribute("Religionlist");
String Propertylist=(String)request.getAttribute("Propertylist");
String Publicationlist=(String)request.getAttribute("Publicationlist");
String QuarterAvailable=(String)request.getAttribute("QuarterAvailable");
String Handicap=(String)request.getAttribute("Handicap");



Map<String,String> map=(Map)request.getAttribute("map");
Map<String,String> selects=(Map)request.getAttribute("selects");

String msg="No Data Available";
String norecordsmsg=(String)request.getAttribute("norecords");

%>

<div class="card-header page-top">
			<div class="row">
				<div class="col-6">
					<h5>Selection Wise Configurable Report</h5>
				</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PIS.htm">PIS</a></li>
						<li class="breadcrumb-item active " aria-current="page">Selection Wise Configurable Report</li>
					</ol>
				</div>
			</div>
</div>
<%if("norecords".equals(norecordsmsg)){%>
		<div align="center">
				<div class="alert alert-success" role="alert">No Data Found</div> 
		</div>
<%}%>

 <div class=" page card dashboard-card">
    <div class="card">
	     <div class="card-header"><h5 style="color:#2e3331;">Search By Following Fields  <button type="button" class="btn btnclr pull-right btn-sm" data-toggle="modal"  data-target="#Selection" style="background-color:#118c5f; color:white; float: right;">Please Select One Option</button> </h5></div>
			<div class="card-body">
			 <form  action="getconfigurablereportselectionwise.htm" method="post">
				        <!--rank,name ,designation ,group name, cat class-->
	             <div class="form-group">
	              <div class="row">
	              
	                <div class="col-md-4">
	                <label>Name</label>
                      <input  name="name"  maxlength="75" placeholder="Search By Name"   class="form-control input-sm" value="<%if(!CollectionUtils.isEmpty(selects) && selects.get("name")!=null){%><%=selects.get("name")%><%}%>"/>
                   </div><br><br><br>
	                <%if(Designation!=null&&Designation.size()>0){%>
	                <div class="col-md-2">
                        <label>Designation</label>
                         <select name="Designation" class="form-control input-sm selectpicker" required="required"  data-live-search="true">  
	                      <option value="">Select</option> 
                          <%for(Object[] details:Designation){ %>
                             <option value="<%= details[0]%>#<%= details[1]%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("desigId").toString().equals(details[0].toString())){%> selected="selected" <%}%>><%= details[1]%></option>
                       <%} %>  
                       </select>
                    </div>
	              <%}%>
	               
	               <%if(GroupName!=null&&GroupName.size()>0){%>
	              <div class="col-md-2">
	                    <label>Group/Division</label>
                        <select  name="GroupDivision" class="form-control input-sm select2"  required data-live-search="true">
                      
                         <option value="">Select</option> 
                       <% for(Object[] details:GroupName){ %>
                          <option value="<%= details[0]%>#<%= details[1]%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("groupId").toString().equals(details[0].toString())){%> selected="selected" <%}%>><%= details[1]%></option>
                       <%}%> 
                        </select>
                 </div>
	              <%} %>
	              
	              
	               <%if(catclass!=null&&catclass.size()>0){%>
	              <div class="col-md-2">
	                  <label>Category Class</label>
                       <select name="CatClass" class="form-control input-sm selectpicker" data-live-search="true" required >
                         <option value="">Select</option> 
                        <%for(PisCatClass obj:catclass){%>
                          <option value="<%=obj.getCat_id()%>#<%= obj.getCat_name()%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("CatId").toString().equals(obj.getCat_id())){%> selected="selected" <%}%>><%= obj.getCat_name()%> </option>
                          <%} %>
                       </select>
                 </div>
	              <%}%>
	              
	              
	            <%if(Genderlist!=null){%>
	              <div class="col-md-2">
	                  <label>Gender Wise</label>
                    <select name="Gender" class="form-control input-sm selectpicker" required>
                      
                      <option value="">Select</option> 
                      <option value="M#M" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("genderName").equals("M")){%> selected="selected" <%}%>>Male</option>
                      <option value="F#F" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("genderName").equals("F")){%> selected="selected" <%}%>>Female</option> 
                    </select>
	            </div>
	             <%}%>
	             
	              <%if(cadreNames!=null&&cadreNames.size()>0){%>
	                 <div class="col-md-2">
                        <label>Cadre</label>
                        <select name="Cadre" class="form-control input-sm selectpicker" required="required" data-live-search="true">  
                          <option value="">Select</option> 
                          <%for(PisCadre obj:cadreNames){ %>
                           <option value="<%= obj.getCadreId()%>#<%= obj.getCadre()%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("cadreId").toString().equalsIgnoreCase(String.valueOf(obj.getCadreId()))){%> selected="selected" <%}%>><%=obj.getCadre()%></option>
                          <%}%> 
                       </select>
                    </div>
                    <%}%>
                    
                    <%if(ServiceStatuslist!=null){%>
                     <div class="col-md-2">
                        <label>Service Status</label>
                        <select name="ServiceStatus" class="form-control input-sm selectpicker" data-live-search="true" required="required">  
                        <option value="">Select</option> 
                        <option value="Confirmed" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("serviceStatus").toString().equals("Confirmed")){%> selected="selected" <%}%>>Confirmed</option>
                        <option value="Probation" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("serviceStatus").toString().equals("Probation")){%> selected="selected" <%}%>>Probation</option>
                        <option value="Adhoc" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("serviceStatus").toString().equals("Adhoc")){%> selected="selected" <%}%>>Adhoc</option>
                        <option value="Contract" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("serviceStatus").toString().equals("Contract")){%> selected="selected" <%}%>>Contract</option>
                       </select>
                    </div>
	               <%}%>
	               
	                <%if(Category!=null&&Category.size()>0){ %> 
	               <div class="col-md-2">
                        <label>Category</label>
                        <select name="Category" class="form-control input-sm selectpicker" data-live-search="true" required="required">  
	                       <option value="">Select</option> 
	                       <%for(PisCategory obj:Category){%>
	                       <option value="<%=obj.getCategory_id()%>#<%= obj.getCategory_desc()%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("categoryId").toString().equals(String.valueOf(obj.getCategory_id()))){%> selected="selected" <%}%>><%= obj.getCategory_desc()%></option>
	                       <%}%>  
                       </select>
                    </div>
	              <%}%>
	              
	              <%if(BGlist!=null){%>
	              <div class="col-md-2">
	                    <label>Blood Group</label>
                        <select  name="BG" class="form-control input-sm selectpicker" data-live-search="true" required>
                           
                            <option value="">Select</option> 
                            <option value="A-" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("A-")){%> selected="selected" <%}%>>A-</option>
                            <option value="A+" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("A+")){%> selected="selected" <%}%>>A+</option>
                            <option value="B-" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("B-")){%> selected="selected" <%}%>>B-</option>
                            <option value="B+" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("B+")){%> selected="selected" <%}%>>B+</option>
                            <option value="AB-" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("AB-")){%> selected="selected" <%}%>>AB-</option>
                            <option value="AB+" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("AB+")){%> selected="selected" <%}%>>AB+</option>
                            <option value="O-" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("O-")){%> selected="selected" <%}%>>O-</option>
                            <option value="O+" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("O+")){%> selected="selected" <%}%>>O+</option>
                            
                            <option value="Not Available" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("BG").toString().equals("Not Available")){%> selected="selected" <%}%>>Not Available</option>
                        </select>
                 </div>
	             <%}%> 
	             
	              <%if(Religionlist!=null){%>
	              <div class="col-md-2">
	              <label>Religion</label>
                 <select name="religion" class="form-control input-sm selectpicker" data-live-search="true" required>
               
                 <option value="">Select</option> 
                 <option value="Christian" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("religion").toString().equals("Christian")){%> selected="selected" <%}%>>Christian</option>
                 <option value="Hindu" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("religion").toString().equals("Hindu")){%> selected="selected" <%}%>>Hindu</option>
                 <option value="Islam" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("religion").toString().equals("Islam")){%> selected="selected" <%}%>>Islam</option>
                 <option value="Jain" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("religion").toString().equals("Jain")){%> selected="selected" <%}%>>Jain</option>
                 <option value="Parsi" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("religion").toString().equals("Parsi")){%> selected="selected" <%}%>>Parsi</option>
                 <option value="Sikh" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("religion").toString().equals("Sikh")){%> selected="selected" <%}%>>Sikh</option>
                 <option value="Others" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("religion").toString().equals("Others")){%> selected="selected" <%}%>>Others</option>
                 
                 </select>
                 </div>
	             <%} %>
	             
	             
	              <%if(QuarterAvailable!=null){%>
	              <div class="col-md-2">
	                   <label>Quarter Available</label>
                        <select  name="Quarter" class="form-control input-sm selectpicker" data-live-search="true" required>
                               <option value="">Select</option> 
                               <option value="Y#Yes" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("quarterId").toString().equals("Y")){%> selected="selected" <%}%>>Yes</option> 
                               <option value="N#No" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("quarterId").toString().equals("N")){%> selected="selected" <%}%>>No</option> 
	                   </select>
	              </div>
	              <%}%>
	              
	               <%if(Handicap!=null){%>
	              <div class="col-md-2">
	                   <label>Physical Handicap</label>
                        <select  name="PhysicalHandicap" class="form-control input-sm selectpicker" data-live-search="true"  required>
                               <option value="">Select</option> 
                               <option value="Y#Yes" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("physicalHandicapId").toString().equals("Y")){%> selected="selected" <%}%>>Yes</option> 
                               <option value="N#No" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("physicalHandicapId").toString().equals("N")){%> selected="selected" <%}%>>No</option>  
	              </select>
	              </div>
	              <%}%>
	               
	              <%if(PayLevel!=null&&PayLevel.size()>0){%>
	                     <div class="col-md-2">
									<div class="form-group">
										<label>Pay Level</label>
									<select class="form-control input-sm selectpicker"  required="required" 
									name="PayLevel" data-live-search="true">
									<option value="">Select</option>
									<%for (PisPayLevel obj: PayLevel) {
									%>
									<option value="<%=obj.getPayLevelId()%>#<%=obj.getPayLevel()%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("pay_LevelId").toString().equals(String.valueOf(obj.getPayLevelId()))){%> selected="selected" <%}%>><%=obj.getPayLevel()%></option>
									<%}%>
								</select>
							</div>
						</div>
	                 <%} %>     
	               
	               <%if(qualification!=null&&qualification.size()>0){%>
	                     <div class="col-md-2">
									<div class="form-group">
										<label>Qualification</label> 
								<select	class="form-control input-sm selectpicker" required="required" 
									name="qualification" data-live-search="true">
									
									<option value="">Select</option>
									<%for (Object[] q : qualification) {
									%>
									<option value="<%=q[0]%>#<%=q[1]%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("qualificationId").toString().equals(q[0].toString())){%> selected="selected" <%}%>><%=q[1]%></option>
									<%}%>
								</select>
							</div>
						</div>
	                 <%} %>
	               
	               <%if(Publicationlist!=null){%>
	               <div class="col-md-2">
	               <div class="form-group">
		                    <label>Publication Type</label>
		                    <select  class="form-control input-sm selectpicker" data-live-search="true" name="pubType" required="required" >
		                      	
		                       <option value="">Select</option>
		                       <option value="JOURNAL" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("pubType").toString().equals("JOURNAL")){%> selected="selected" <%}%>>JOURNAL</option>
		                       <option value="BOOK" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("pubType").toString().equals("BOOK")){%> selected="selected" <%}%>>BOOK</option>
		                       <option value="PAPER" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("pubType").toString().equals("PAPER")){%> selected="selected" <%}%>>PAPER</option>
		                       <option value="POSTER" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("pubType").toString().equals("POSTER")){%> selected="selected" <%}%>>POSTER</option>
		                       <option value="ARTICLE" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("pubType").toString().equals("ARTICLE")){%> selected="selected" <%}%>>ARTICLE</option>
		                       <option value="PATENT" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("pubType").toString().equals("PATENT")){%> selected="selected" <%}%>>PATENT</option>
		                   </select>
		                  
		                </div>
	              </div>
	             <%}%>  
	              
	              <%if(Propertylist!=null){%>
	              <div class="col-md-2">
                        <div class="form-group">
                            <label>Property Type</label>
                            <select class="form-control input-sm selectpicker" data-live-search="true" name="PropertyAcquiredType"  required="required" >
                        
                           <option value="">Select</option>
                           <option value="PURCHASE" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("propertyType").toString().equals("PURCHASE")){%> selected="selected" <%}%> >PURCHASE</option>
                           <option value="GIFT" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("propertyType").toString().equals("GIFT")){%> selected="selected" <%}%>>GIFT</option>
                           <option value="INHERITANCE" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("propertyType").toString().equals("INHERITANCE")){%> selected="selected" <%}%>>INHERITANCE</option>
                           
                           </select>
                        </div>
                    </div>
	              <%}%>
	              
	               
	               <%if(modeOfRecruit!=null&&modeOfRecruit.size()>0){%>
	              <div class="col-md-2">
	                  <label>Appointment</label>
                       <select name="Appointment" class="form-control input-sm selectpicker"  data-live-search="true" required>
                       
                          <option value="">Select</option> 
                        <%for (Object[] m : modeOfRecruit) {%>
					    <option value="<%=m[0]%>#<%=m[1]%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("appointmentId").toString().equals(m[0].toString())){%> selected="selected" <%}%>><%=m[1]%></option>
					   <%}%>
                       </select>
                 </div>
	             <%}%> 
	                       
	              
	              <%if(AwardType!=null && AwardType.size()>0){%>
	              <div class="col-md-2">
	                  <label>Awards</label>
	           		<select name="Awards" class="form-control input-sm selectpicker"  data-live-search="true" required>
                        
                          <option value="">Select</option> 
                        <%for (Object[] m : AwardType) {%>
					    <option value="<%=m[0]%>#<%=m[1]%>" <%if((!CollectionUtils.isEmpty(selects))&&selects.get("awardsId").toString().equals(m[0].toString())){%> selected="selected" <%}%>><%=m[1]%></option>
					   <%}%>
                       </select>
	            </div>
	             <%}%>
	              </div>
	              </div>
	              <div class="row">
                        <div class="col-sm-12 text-center">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-sm submit-btn" id="btnSubmit" onclick="return confirm('Are You Sure To Submit?');" name="submit" value="submit">SUBMIT</button> 
                            <a href="Configurableselectionwise.htm" id="btnReset" class="btn btn-sm" style="background-color: #d6854b; color: white;" role="button">Reset</a>
                       		
                       </div>
                       </div>
	              <!--//rank,name ,designation ,group name,catclass  -->
	              </form>
	              
	              
	              
	              
	<table class="table table-bordered table-hover table-striped table-condensed DataTableWithPdfandExcel"  > 
		<%
		if(!CollectionUtils.isEmpty(confreports)){%>
		<thead>
			<tr>
			<%for(String heading:tblheadings){%>
				<th><%=heading %></th>
			<%}%>
			</tr></thead>
			<tbody>
			<%for(Object[] objsarr:confreports){%>
			
			<tr>
				<%for(Object obj:objsarr){
					if(Objects.nonNull(obj)){%>
						<td style="text-align:left;"><%= obj %></td>
				<%}}%>
			</tr>
			<%}}%> 
			</tbody>
			    
		</table>

			</div>
	</div>
</div>

<div class="row">
 <div class="container-fluid">     
    <!--Modal To Select Options  -->
 <form id="checkboxval" action="configurablereportselectionwise.htm" method="POST">  
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
     <div class="modal fade" id="Selection" role="dialog" >
           <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                      
                      <h4 class="modal-litle">Please Select  Options From The Following</h4>
                    </div>
                     
             <div class="modal-body">
             <div class="row">
              
          <div class="col-md-4" >
              <table id="popuptable" class="table table-hover  table-striped table-condensed table-bordered">
            <thead>
              <tr ><th>Select</th><th>Parameter</th></tr>
            </thead>
           <tbody>
              <tr><td><input type="checkbox"  disabled checked></td><td>Name</td></tr>
              <tr><td><input type="checkbox" class="check" name="DesignationModal" value="DesignationModal"></td><td>Designation</td></tr>
              <tr><td><input type="checkbox" class="check" name="GroupModal" value="GroupModal"></td><td>Group/Division</td></tr>
              <tr><td><input type="checkbox" class="check" name="CatClassModal" value="CatClassModal"></td><td>Category Class</td></tr>
              <tr><td><input type="checkbox" class="check" name="GenderModal" value="GenderModal"></td> <td>Gender Wise</td></tr>
              <tr><td><input type="checkbox" class="check" name="CadreModal" value="CadreModal"></td><td>Cadre</td></tr>
            </tbody>
          </table>
             </div>
             
          <div class="col-md-4" >
              <table class="table table-hover  table-striped table-condensed table-bordered">
            <thead>
              <tr ><th>Select</th><th>Parameter</th></tr>
            </thead>
           <tbody>
              <tr><td><input type="checkbox" class="check" name="ServiceStatusModal" value="ServiceStatusModal"></td><td>Service Status</td></tr>
              <tr><td><input type="checkbox" class="check" name="CategoryModal" value="CategoryModal"></td><td>Category</td></tr>
              <tr><td><input type="checkbox" class="check" name="BloodModal" value="BloodModal"></td><td>Blood Group</td></tr>
              <tr><td><input type="checkbox" class="check" name="ReligionModal" value="ReligionModal"></td><td>Religion</td></tr>
              <tr><td><input type="checkbox" class="check" name="QuarterModal" value="QuarterModal"></td><td>Govt. Quarter</td></tr>
              <tr><td><input type="checkbox" class="check" name="HandicapModal" value="HandicapModal"></td><td>Physical Handicap</td></tr>
            </tbody>
          </table>
           </div>
          
           <div class="col-md-4" >
              <table class="table table-bordered table-hover table-striped table-condensed">
            <thead>
              <tr ><th>Select</th><th>Parameter</th></tr>
            </thead>
           <tbody >
              <tr><td><input class="check" type="checkbox" name="PayLevelModal" value="PayLevelModal"></td><td>Pay Level</td></tr>
              <tr><td><input class="check" type="checkbox" name="QualificationModal" value="QualificationModal"></td><td>Qualification</td></tr>
              <tr><td><input class="check"  type="checkbox" name="PublicationModal" value="PublicationModal"></td><td>Publication</td></tr>
              <tr><td><input class="check"  type="checkbox"  name="PropertyModal" value="PropertyModal"></td><td>Property</td></tr>
              <tr><td><input class="check"  type="checkbox" name="AppointmentModal" value="AppointmentModal"></td><td>Appointment</td></tr>
              <tr><td><input class="check"  type="checkbox"  name="AwardsModal" value="AwardsModal"></td><td>Awards</td></tr>
            </tbody>
          </table>
           </div>
          </div><!-- //row -->
          </div><!-- //modal-body -->
             
             <div class="modal-footer">
                <span class="pull-left"><b style="color:brown;" >Note:You Can Select Maximum Five Parameters </b></span>
                <button type="submit" class="btn btn-sm submit-btn" name="ModalData" value="ModalData" >Submit</button>
               <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">close</button> 
               
             </div>
            </div>
        </div>
     </div>
	</form>
     <!-- //Modal To Select Options -->          
</div>
</div>


<script type="text/javascript">

$(document).ready(function(){
    $(".check").change(function(){
        var maxAllowed = 5;
         var cnt = $(".check:checked").length;
        if (cnt > maxAllowed) {
            $(this).prop("checked", "");
            alert('You can select maximum ' + maxAllowed + ' Parameters!!');
            }
        if(cnt==0){
        	$(this).prop("checked", "");
        	alert('You can select minimum 1 Parameters!!');
        }
    });
});

$( '#checkboxval' ).on('submit', function() {
	   if($( 'input[class^="check"]:checked' ).length === 0) {
	      alert( 'Please Choose atleast one' );
	      return false;
	   }
	});

</script>

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
         		text: '<i class="fa-solid fa-file-excel" style="font-size:24px ; color: red;" ></i>',
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

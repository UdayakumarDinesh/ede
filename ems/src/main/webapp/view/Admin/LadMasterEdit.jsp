<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.vts.ems.Admin.model.*" %>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lab Details</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<style type="text/css">

label
{
  font-weight: bold;
  font-size: 13px;
}

.table thead tr th 
{
	align-items:center;
	text-align: left;
	width:30%;
}

.table thead tr td 
{

	text-align: center;
}

label
{
	font-size: 15px;
}

table
{
	box-shadow: 0 4px 6px -2px gray;
}

</style>
</head>
<body>
<%
LabMaster labdetail     = (LabMaster)request.getAttribute("labdetails");
List<Object[]> emplist  = (List<Object[]>)request.getAttribute("emplist");
List<Object[]> labslist = (List<Object[]>)request.getAttribute("labslist");

%>

<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Lab Details Edit</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>						
						<li class="breadcrumb-item active " aria-current="page">Lab Details Edit</li>
				</ol>
			   </div>
		</div>
	</div> 
	<div class="card-body">
	<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
		if(ses1!=null){%>
			<div class="alert alert-danger" role="alert">
				<%=ses1%>
			</div>
			
		<%}if(ses!=null){%>
			
			<div class="alert alert-success" role="alert">
				<%=ses%>
			</div>
		<%}%>
	     </div>
	   <div class="card" >
	   		<div class="card-body" >
	   		
<form name="myfrm" action="LabMaster.htm" method="POST" >

  <div class="form-group">
  <div class="table-responsive">
	  <table class="table table-bordered table-hover table-striped table-condensed "  style="width: 70%;" align="center" >
	<!--   <thead><tr align="center"><th colspan="6" align="center"> Lab Details</th></tr></thead> -->
        <thead>
				<tr>
					<th><label>Lab Code: <span class="mandatory"style="color: red;">*</span></label></th>
					<td colspan="4"><input class="form-control" type="text"name="LabCode" required="required" maxlength="255"	style="font-size: 15px; " id="LabCode" value="<%if(labdetail!=null){%><%=labdetail.getLabCode()%><%}%>" disabled ></td>
				</tr>
		</thead>

       <thead>
                <tr>
                    <th><label >Lab Name:<span class="mandatory" style="color: red;">*</span></label></th>
                     <td colspan="4"><input  class="form-control form-control" type="text" name="LabName" id="LabName" required="required" maxlength="255" style="font-size: 15px;text-transform:capitalize;" value="<%if(labdetail!=null){%><%=labdetail.getLabName()%><%}%>" disabled></td>
                 </tr>
       </thead>
       
       <thead>
				<tr>
					<th><label >Lab Address:<span class="mandatory" style="color: red;">*</span></label></th>
                    <td colspan="4"><input  class="form-control form-control" type="text" name="LabAddress" id="LabAddress" required="required" maxlength="255" style="font-size: 15px;text-transform:capitalize;" value="<%if(labdetail!=null){%><%=labdetail.getLabAddress()%><%}%>"  disabled></td>
                </tr>
       </thead>
       
       <thead>
               <tr>
               <th><label >Lab City:<span class="mandatory" style="color: red;">*</span></label></th>
               <td><input  class="form-control form-control" type="text" name="LabCity"  id="LabCity" required="required" maxlength="255" style="font-size: 15px;text-transform:capitalize;" value="<%if(labdetail!=null){%><%=labdetail.getLabCity()%><%}%>" disabled></td>
               <th><label >Lab Email:<span class="mandatory" style="color: red;">*</span></label></th>
               <td ><input  class="form-control form-control" type="text" name="LabEmail"  id="LabEmail" required="required" maxlength="30" style="font-size: 15px;" value="<%if(labdetail!=null){%><%=labdetail.getLabEmail()%><%}%>" disabled></td>
               </tr>
      </thead>
      
      <thead>
              <tr>
                 <th><label >Lab Pin:<span class="mandatory" style="color: red;">*</span></label></th>
                 <td><input  class="form-control form-control" type="text" name="LabPin" id="LabPin" required="required" maxlength="6" style="font-size: 15px;"  value="<%if(labdetail!=null){%><%=labdetail.getLabPin()%><%}%>" disabled></td>
                 <th><label >Lab Unit Code:<span class="mandatory" style="color: red;">*</span></label></th>
                 <td><input  class="form-control form-control" type="number" name="LabUnitCode" id="LabUnitCode" required="required" maxlength="255" style="font-size: 15px;"    value="<%if(labdetail!=null){%><%=labdetail.getLabUnitCode()%><%}%>"  disabled></td>
			  </tr>
      </thead>
      
      <thead>
             <tr>
                <th><label >Lab Telephone:<span class="mandatory" style="color: red;">*</span></label></th>
                <td><input  class="form-control form-control" type="text" name="LabTelephone" id="LabTelephone" required="required" maxlength="15" style="font-size: 15px;" value="<%if(labdetail!=null){%><%=labdetail.getLabTelNo()%><%}%>"  disabled></td>
                <th><label >Lab Fax No:<span class="mandatory" style="color: red;">*</span></label></th>
                <td><input  class="form-control form-control" type="number" name="LabFaxNo" id="LabFaxNo" required="required" maxlength="255" style="font-size: 15px;"  value="<%if(labdetail!=null){%><%=labdetail.getLabFaxNo()%><%}%>" disabled ></td>
            </tr>
      </thead> 
      
      <thead>
             <tr>
                 <th><label >Lab Authority:<span class="mandatory" style="color: red;">*</span></label></th>
 				 <td style="width:20%"><input  class="form-control form-control" type="text" name="LabAuthority" id="LabAuthority" required="required" maxlength="30" style="font-size: 15px;" value="<%if(labdetail!=null){%><%=labdetail.getLabAuthority()%><%}%>" disabled></td>
                 <th><label >Lab Authority Name:<span class="mandatory" style="color: red;">*</span></label></th>
                 <td><select class="form-control select2" name="LabAuthorityName" id="LabAuthorityName" data-container="body" data-live-search="true"  required="required" style="width: 100%"  disabled>
				     <option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
				     <%if(emplist!=null){  for(Object[] obj:emplist){ %>
				      <option value="<%=obj[0]%>" <%if(labdetail.getLabAuthorityId().toString().equalsIgnoreCase(obj[0].toString())){%> selected<%}%> ><%=obj[1] %> </option>
				      <%} }%>
			     </select> </td>
            </tr>
     </thead> 
       
     <thead>
         <tr>
            <th style="width:20%"><label >Lab :<span class="mandatory" style="color: red;">*</span></label></th>
            <td colspan="4"><select class="form-control select2" name="labid"  id="labid"  data-container="body" data-live-search="true"  required="required" style="width: 100%" disabled>
				<option value=""  selected="selected"hidden="true">--Select--</option>
			   <%if(labslist!=null){  for(Object[] obj:labslist){ %>
				      <option value="<%=obj[0]%>" <%if(labdetail.getLabId().toString().equalsIgnoreCase(obj[0].toString())){%> selected<%}%> ><%=obj[1] %> </option>
				      <%} }%>
		        </select> 
			</td>
        </tr>
     </thead> 
     
    <thead>
          <tr>
             <th style="width:20%"><label >Lab RFP Website:<span class="mandatory" style="color: red;">*</span></label></th>
             <td colspan="4"><input  class="form-control form-control" type="text" name="LabRFPEmail"  id="LabRFPEmail" required="required" maxlength="30" style="font-size: 15px;" value="<%if(labdetail!=null){%><%=labdetail.getLabRfpEmail()%><%}%>" disabled></td>
          </tr>
    </thead> 
    
</table>
</div>
</div>

	  <div id="Edit" align="center">
	  <button type="button" class="btn btn-sm submit-btn" onclick="EditlabMaster()"  >EDIT</button>
	  </div>
	   <div id="Update" align="center" hidden>
	  <button type="submit"  class="btn btn-sm submit-btn"  onclick="return confirm('Are You Sure To Update');" name="Action" value="EDITLAB">UPDATE</button>
	  </div>
	    <input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}"  />
		<input type="hidden" name="LabMasterId" value="<%if(labdetail!=null){%><%=labdetail.getLabMasterId()%><%}%>">
	 
	 
	  </form>
	   		
	   	</div>
	   </div>
	</div>
</div>
</body>
<script type="text/javascript">

function EditlabMaster()
{
	
	document.getElementById("LabCode").disabled = false;
	document.getElementById("LabName").disabled = false;
    document.getElementById("LabAddress").disabled = false;
    document.getElementById("LabCity").disabled = false;
    document.getElementById("LabEmail").disabled = false;
    document.getElementById("LabPin").disabled = false;
    document.getElementById("LabUnitCode").disabled = false;
    document.getElementById("LabTelephone").disabled = false;
    document.getElementById("LabFaxNo").disabled = false;
    document.getElementById("LabAuthority").disabled = false;
    document.getElementById("LabAuthorityName").disabled = false;
    document.getElementById("labid").disabled = false;
    document.getElementById("LabRFPEmail").disabled = false;
    document.getElementById("LabUnitCode").disabled = false;
    document.getElementById("LabUnitCode").disabled = false;
    document.getElementById("Edit").hidden = true;
    document.getElementById("Update").hidden = false;
}

</script>
</html>
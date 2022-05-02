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
	   <div class="card" >
	   		<div class="card-body" >
	   		
<form name="myfrm" action="LabMaster.htm" method="POST" >

  <div class="form-group">
  <div class="table-responsive">
	  <table class="table table-bordered table-hover table-striped table-condensed "  style="width: 70%;"  >
	<!--  <tr align="center"><th colspan="6" align="center"> Lab Details</th></tr></thead> -->
       
				<tr>
					<th><label>Lab Code: <span class="mandatory"style="color: red;">*</span></label></th>
					<td colspan="4"><input class="form-control" type="text"name="LabCode" required="required" maxlength="255"	style="font-size: 15px; " id="LabCode" value="<%if(labdetail!=null){%><%=labdetail.getLabCode()%><%}%>" readonly="readonly"></td>
				</tr>

      
                <tr>
                    <th><label >Lab Name:<span class="mandatory" style="color: red;">*</span></label></th>
                     <td colspan="4"><input  class="form-control form-control" type="text" name="LabName" required="required" maxlength="255" style="font-size: 15px;text-transform:capitalize;" value="<%if(labdetail!=null){%><%=labdetail.getLabName()%><%}%>" readonly="readonly"></td>
                 </tr>
      
       
      
				<tr>
					<th><label >Lab Address:<span class="mandatory" style="color: red;">*</span></label></th>
                    <td colspan="4"><input  class="form-control form-control" type="text" name="LabAddress" required="required" maxlength="255" style="font-size: 15px;text-transform:capitalize;" value="<%if(labdetail!=null){%><%=labdetail.getLabAddress()%><%}%>" readonly="readonly" ></td>
                </tr>
      
       
      
               <tr>
               <th><label >Lab City:<span class="mandatory" style="color: red;">*</span></label></th>
               <td><input  class="form-control form-control" type="text" name="LabCity" required="required" maxlength="255" style="font-size: 15px;text-transform:capitalize;" value="<%if(labdetail!=null){%><%=labdetail.getLabCity()%><%}%>" readonly="readonly"></td>
               <th><label >Lab Email:<span class="mandatory" style="color: red;">*</span></label></th>
               <td ><input  class="form-control form-control" type="text" name="LabEmail" required="required" maxlength="30" style="font-size: 15px;" value="<%if(labdetail!=null){%><%=labdetail.getLabEmail()%><%}%>" readonly="readonly" ></td>
               </tr>
     
      
     
              <tr>
                 <th><label >Lab Pin:<span class="mandatory" style="color: red;">*</span></label></th>
                 <td><input  class="form-control form-control" type="text" name="LabPin" required="required" maxlength="6" style="font-size: 15px;"  value="<%if(labdetail!=null){%><%=labdetail.getLabPin()%><%}%>" readonly="readonly"></td>
                 <th><label >Lab Unit Code:<span class="mandatory" style="color: red;">*</span></label></th>
                 <td><input  class="form-control form-control" type="number" name="LabUnitCode" required="required" maxlength="255" style="font-size: 15px;"    value="<%if(labdetail!=null){%><%=labdetail.getLabUnitCode()%><%}%>" readonly="readonly" ></td>
			  </tr>
     
      
     
             <tr>
                <th><label >Lab Telephone:<span class="mandatory" style="color: red;">*</span></label></th>
                <td><input  class="form-control form-control" type="text" name="LabTelephone" required="required" maxlength="15" style="font-size: 15px;" value="<%if(labdetail!=null){%><%=labdetail.getLabTelNo()%><%}%>" readonly="readonly" ></td>
                <th><label >Lab Fax No:<span class="mandatory" style="color: red;">*</span></label></th>
                <td><input  class="form-control form-control" type="number" name="LabFaxNo" required="required" maxlength="255" style="font-size: 15px;"  value="<%if(labdetail!=null){%><%=labdetail.getLabFaxNo()%><%}%>" readonly="readonly" ></td>
            </tr>
      
      
     
             <tr>
                 <th><label >Lab Authority:<span class="mandatory" style="color: red;">*</span></label></th>
 				 <td style="width:20%"><input  class="form-control form-control" type="text" name="LabAuthority" required="required" maxlength="30" style="font-size: 15px;" value="<%if(labdetail!=null){%><%=labdetail.getLabAuthority()%><%}%>" readonly="readonly"></td>
                 <th><label >Lab Authority Name:<span class="mandatory" style="color: red;">*</span></label></th>
                 <td><select class="form-control select2" name="LabAuthorityName" data-container="body" data-live-search="true"  required="required" style="width: 100%"  >
				     <option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
				     <%if(emplist!=null){  for(Object[] obj:emplist){ %>
				      <option value="<%=obj[0]%>" <%if(labdetail.getLabAuthorityId().toString().equalsIgnoreCase(obj[0].toString())){%> selected<%}%> disabled="disabled"><%=obj[1] %> </option>
				      <%} }%>
			     </select> </td>
            </tr>
     
       
    
         <tr>
            <th style="width:20%"><label >Lab :<span class="mandatory" style="color: red;">*</span></label></th>
            <td colspan="4"><select class="form-control select2" name="labid" data-container="body" data-live-search="true"  required="required" style="width: 100%">
				<option value="" disabled="disabled" selected="selected"hidden="true">--Select--</option>
			   <%if(labslist!=null){  for(Object[] obj:labslist){ %>
				      <option value="<%=obj[0]%>" <%if(labdetail.getLabId().toString().equalsIgnoreCase(obj[0].toString())){%> selected<%}%> disabled="disabled"><%=obj[1] %> </option>
				      <%} }%>
		        </select> 
			</td>
        </tr>
     
     
   
          <tr>
             <th style="width:20%"><label >Lab RFP Website:<span class="mandatory" style="color: red;">*</span></label></th>
             <td colspan="4"><input  class="form-control form-control" type="text" name="LabRFPEmail" required="required" maxlength="30" style="font-size: 15px;" value="<%if(labdetail!=null){%><%=labdetail.getLabRfpEmail()%><%}%>" readonly="readonly"></td>
          </tr>
    
    
</table>
</div>
</div>

	  <div id="LabAddSubmit" align="center">
	  <button type="button" class="btn btn-sm submit-btn" onclick="EditlabMaster();">EDIT</button>
	  <input type="submit"  class="btn btn-sm submit-btn"  onclick="return confirm('Are You Sure To Update');" name="Action" value="UPDATE"/>
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
	
	
}

</script>
</html>
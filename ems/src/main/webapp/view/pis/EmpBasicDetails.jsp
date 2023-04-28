<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

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
</style>
</head>
<body>

<%
	String ThisEmpId = String.valueOf((Long)session.getAttribute("EmpId"));
	String empid = (String) request.getAttribute("empid");
	Object[] employeedetails = (Object[]) request.getAttribute("employeedetails");
	Object[] empdata = (Object[]) request.getAttribute("empdata");
	Object[] Emecdetails = (Object[]) request.getAttribute("emeaddressdetails");
	Object[] Nextdetails = (Object[]) request.getAttribute("nextaddressdetails");
	List<Object[]> Resdetails  = (List<Object[]>) request.getAttribute("resaddressdetails");
	Object[] Perdetails  = (Object[]) request.getAttribute("peraddressdetails");
	String path=(String)request.getAttribute("basevalue");
	List<Object[]> familydetails = (List<Object[]>) request.getAttribute("familydetails");
	
	List<Object[]> DropDownEmployeeList = (List<Object[]>) request.getAttribute("DropDownEmployeeList");
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	String LoginType = (String) session.getAttribute("LoginType");
%>
 
 <%-- <%if(LoginType.equalsIgnoreCase("A")){ %> --%>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 
 <%-- <%} %> --%>
 
  <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Employee View </h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
					<%-- <%if("A".equalsIgnoreCase(LoginType)){ %>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item "><a href="PisAdminEmpList.htm">Employee List</a></li>
					<%}%> --%>
					<li class="breadcrumb-item active " aria-current="page">Employee info</li>
				</ol>
			</div>	
		</div>
	</div>	                 
                    
 <div class=" page card dashboard-card">
	
	<div align="center"  style="margin-top: 15px;">
				<%String ses=(String)request.getParameter("result"); 
				String ses1=(String)request.getParameter("resultfail");
				if(ses1!=null){ %>
					<div class="alert alert-danger" role="alert">
						<%=ses1 %>
					</div>
					
				<%}if(ses!=null){ %>
					
					<div class="alert alert-success" role="alert">
						<%=ses %>
					</div>
				<%} %>
			</div> 
	
	
	<div class="card-body"  >
			
			<div class="row" style="margin-top: -25px;">
				<div class="col-md-12">
					<form action="EmployeeDetails.htm" method="post" style="float: right;">
						<div class="form-inline">
							<b>Employee : &nbsp;</b> 
							<select class="form-control select2" name="empid" onchange="this.form.submit();" style="margin-top: -5px;width: 200px;align:right;">
								<%for(Object[] employee : DropDownEmployeeList){ %>
									<option value="<%=employee[0]%>" <%if(employee[0].toString().equalsIgnoreCase(empid)){ %>selected <%} %>><%=employee[2]%></option>
								<%} %>
							</select>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</div>
					</form> 
				</div>
			</div>
			
			
		<div class="card" >
			<div class="card-body " >
			
			
			<br>
			<div class="row">
			<div class="col-md-2" style="padding-bottom: 10px;" >
			<div class="card" style="background-color: #ebf0f2;">
			<div class="card-body text-center"  >
			<form action="PisEmployeeImageUpload.htm" method="POST" enctype="multipart/form-data" id="myForm" > 
			<%if(employeedetails[29]!=null){ %>			
			
			 <div class="pic1" >
			 <img src="data:image/<%=FilenameUtils.getExtension(employeedetails[29].toString())%>;base64,<%=path%>" class="img-responsive img-rounded" style=" width: 100%; height:124px; cursor: pointer;" id="profileImage1" >
			 </div>
			 <div class="p-image1">
             <i class="fa fa-camera upload-button"></i>
			 <input id="imageUpload1" type="file" name="photo1" accept=".png, .jpg, .jpeg"   style="display: none;" onchange="return checkPic1()">
			 			 
			
			 <%}else{%>
		
			 
			  <div class="pic">
			  <img src="./view/images/user.png" class="img-responsive img-rounded" style=" width: 100%; height:124px; cursor: pointer;  " id="profileImage">
			  </div>
			  <div class="p-image">
              <i class="fa fa-camera upload-button"></i>
			  <input id="imageUpload" type="file" name="photo1" accept=".png, .jpg, .jpeg"    style="display: none;" onchange="return checkPic2()">
			  <%}%>
			  </div>
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			   <input type="hidden" name="empid" value="<%=employeedetails[0]%>"/>
			  </form>
			 
			 
			 </div>
			 </div>
			 </div>
			 
			 <div class="col-md-10"  >
			 <table class="table table-striped table-bordered" >
					<tbody>
					<tr ><td colspan="6" style="background-color: #0e6fb6; color: white;"><h3 style="text-align: center;margin: 0px;"> <%=employeedetails[3]%> (<%=employeedetails[22]%>)</h3></td></tr>
						<tr>
						     <th>Designation</th>
	                         <th>Department</th>
	                         <th>DOB</th>
	                         <th>Employee No</th>
	                         <th>Pay Grade</th>
	                         <th>Basic Pay</th>
						</tr>
						<tr>
							<td><%if(employeedetails[22]!=null){%><%=employeedetails[22]%><%}else{%>--<%}%></td>						
					     	<td><%if(employeedetails[23]!=null && employeedetails[24]!=null){%> <%=employeedetails[23]%>(<%=employeedetails[24] %>) <%}else{%>--<%}%></td>
							<td><%if(employeedetails[5]!=null){%> <%=DateTimeFormatUtil.SqlToRegularDate(employeedetails[5].toString())%> <%}else{%>--<%}%></td>
						    <td><%if(employeedetails[2]!=null){%><%=employeedetails[2]%><%}else{%>--<%}%></td>
						    <td><%if(employeedetails[33]!=null){%><%=employeedetails[33]%><%}else{%>--<%}%></td>
							<td><%if(employeedetails[34]!=null){%><%=employeedetails[34]%><%}else{%>--<%}%></td>
						</tr>
						
						<tr>
							<td colspan="6"  style="background-color: white; " > 
								<%if(!"P".equalsIgnoreCase(LoginType) && ThisEmpId.equalsIgnoreCase(empid)){ %>
									<form action="EmpRequestMsg.htm" method="GET" style="float: right;">
								 		<span><b style="color: red;">Note :</b> Any changes in profile request to Admin  &nbsp;&nbsp;&nbsp;&nbsp;
											<button type="submit" class="btn btn-sm misc-btn"  name="action" value="msg" >REQUEST MESSAGE</button>
								    	</span>
							    	</form>
							    <%}%>
						    </td>
					    </tr> 
					    
					</tbody>
				</table>
			 </div>
			</div>
			
			<div class="col-12">
			
		<ul class="nav nav-tabs" role="tablist">
			<li class="nav-item">
				<a class="nav-link active" data-toggle="tab" href="#tabs-1" role="tab" >Basic Details</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab">Family</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" data-toggle="tab" href="#tabs-3" role="tab">Address</a>
			</li>
		</ul>

<!-- Tab panes -->
<div class="tab-content">

			
	<div class="tab-pane active" id="tabs-1" role="tabpanel">
		
		<!-- -----------------------------------Basic Details--------------------------------------------- -->
			 <table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Basic Details</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						
						<tr>
							<td> <b>Gender</b> </td>
							<td>
								<%if(employeedetails[9]!=null && employeedetails[9].toString().equalsIgnoreCase("M")){ %>
									Male
								<%}else if(employeedetails[9]!=null && employeedetails[9].toString().equalsIgnoreCase("F")){ %>
									Female
								<%}else{ %>--<% }%> 
							</td>
							<td> <b>Group</b> </td>
							<td><%if(employeedetails[25]!=null && employeedetails[26]!=null){%><%=employeedetails[25] %>(<%=employeedetails[26] %>) <%}else{%>--<%}%></td>
							<td> <b>PAN</b> </td>
							<td><%if(employeedetails[13]!=null){ %><%=employeedetails[13].toString().toUpperCase()%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>DOJ</b> </td>
							<td><%if(employeedetails[6]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(employeedetails[6].toString())%><%}else{%>--<%}%></td>
							<td> <b>DOR</b> </td>
							<td><%if(employeedetails[8]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(employeedetails[8].toString())%><%}else{%>--<%}%></td>
							<td> <b>Mobile No</b> </td>
							<td><%if(employeedetails[30]!=null){%><%=employeedetails[30].toString()%><%}else{%>--<%}%></td>
						</tr>
						
						<tr>
							<td> <b>Internal Email</b> </td>
							<td><%if(employeedetails[16]!=null){%><%=employeedetails[16]%><%}else{%>--<%}%></td>
							<td> <b>Extension No</b> </td>
							<td><%if(employeedetails[32]!=null){%><%=employeedetails[32]%><%}else{%>--<%}%></td>
							<td> <b>Marital Status</b> </td>
							<td>
								<%if(employeedetails[11]!=null && (employeedetails[11]+"").equalsIgnoreCase("M")){ %>
									Married
								<%}else if(employeedetails[11]!=null && (employeedetails[11]+"").equalsIgnoreCase("U")){ %>
									UnMarried
								<%}else{%>--<%}%> 
							</td>
						</tr>
						
						<tr>
							<td> <b>SBI AccNo.</b> </td>
							<td><%if(employeedetails[20]!=null){%><%=employeedetails[20]%><%}else{%>--<%}%></td>
							<td> <b>Blood Group</b> </td>
							<td style="max-width: 30%;">
								<form action="UserBloodGropuEdit.htm" method="post"> 
									<table style="width: 100%; border: 0px;margin: -5px 0px 0px 0px;" >
										<tr>
											<td  style="width: 75%; border: 0px;padding: 0px;">
												 <select name="bloodgroup" class="form-control selectpicker" style="max-width: 50% !important;" required data-live-search="true">
												 	<option value="NOT" <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("NOT")){%>selected<%}%>  >Not Available</option>			
								                    <option value="A-"  <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("A-")){%>selected<%}%>   >A-</option>
								                    <option value="A+"  <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("A+")){%>selected<%}%>   >A+</option>
								                    <option value="B-"  <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("B-")){%>selected<%}%>   >B-</option>
								                    <option value="B+"  <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("B+")){%>selected<%}%>   >B+</option>
								                    <option value="AB-" <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("AB-")){%>selected<%}%>  >AB-</option>
								                    <option value="AB+" <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("AB+")){%>selected<%}%>  >AB+</option>
								                    <option value="O-"  <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("O-")){%>selected<%}%>   >O-</option>
								                    <option value="O+"  <%if(employeedetails[10]!=null  && employeedetails[10].toString().equalsIgnoreCase("O+")){%>selected<%}%>   >O+</option>
								                </select>
							                </td>
							                <td style="width:25%; border: 0px;padding: 0px;">
							                	<%if(!"P".equalsIgnoreCase(LoginType) && ThisEmpId.equalsIgnoreCase(empid)){ %>
							                	<button type="submit" class="btn btn-sm update-btn" style="float: right;" name="" value="" onclick="return confirm('Are You Sure to Update?');">Update</button>
							                	<%} %>
							                </td>
						                </tr>
						           	</table>
					                <input type="hidden" name="empid" value="<%=employeedetails[0]%>">
					                <input type="hidden" name="empno" value="<%=employeedetails[2]%>">
					                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form> 
							</td>
							<td> <b>Home Town</b> </td>
							<td><%if(employeedetails[27]!=null){%><%=employeedetails[27]%><%}else{%>--<%}%></td>
						</tr>
					</tbody>
				</table>
				
	</div>
	<!-------------------------------------   Basic Details   ---------------------------------------------->
	
	
	<!------------------------------------- Permanent Address ---------------------------------------------->
	<div class="tab-pane" id="tabs-3" role="tabpanel">
	<%if(Perdetails!=null){ %> 
		 <table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Permanent Address</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr>
							<td > <b>Per Address</b> </td>
							<td colspan="3"><%if(Perdetails[6]!=null){%><%=Perdetails[6]%><%}else{%>--<%}%></td>
							<td><b>City</b> </td>
							<td><%if(Perdetails[1]!=null){%><%=Perdetails[1]%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>Mobile</b> </td>
						    <td><%if(Perdetails[5]!=null){%><%=Perdetails[5]%><%}else{%>--<%}%></td>
							<td> <b>State</b> </td>
					    	<td><%if(Perdetails[8]!=null){%><%=Perdetails[8]%><%}else{%>--<%}%></td>
							<td> <b>From_Per_Address</b> </td>
							<td><%if(Perdetails[2]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(Perdetails[2].toString())%><%}else{%>--<%}%></td>
							
						</tr>
						
						<tr>
							<td> <b>Alt_Mobile</b> </td>
							<td><%if(Perdetails[0]!=null){%><%=Perdetails[0]%><%}else{%>--<%}%></td>
							<td> <b>Landline</b> </td>
							<td><%if(Perdetails[4]!=null){%><%=Perdetails[4]%><%}else{%>--<%}%></td>
							<td> <b>Pin</b> </td>
							<td><%if(Perdetails[7]!=null){%><%=Perdetails[7]%><%}else{%>--<%}%></td>
						</tr>
						
					
					</tbody>
				</table>
				
				<%}else{%>
				<table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;" > <b>Permanent Address</b></td>
						</tr>
			        	<tr></tr>
						<tr></tr>
						<tr></tr>
			    <tr><td align="center"> <h5>Permanent Address Not Added!</h5> </td></tr>
				</tbody>
				</table>
				<%} %>
	       <!------------------------------ Permanent Address ------------------------------------------------>			
				
				
				
				
            <!---------------------------------- Residential Address  ---------------------------------------->
				<%if(Resdetails!=null&&Resdetails.size()!=0 ){ %>
				 <table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Residential  Address</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<%for(Object[] O:Resdetails){ %>
						<tr ><td colspan="4"></td><td></td><td></td>
						</tr>
						<tr>
						<td > <b>Res Address</b> </td>
							<td colspan="3"><%if(O[5]!=null){%><%=O[5]%><%}else{%>--<%}%></td>
							<td><b>City</b> </td>
							<td><%if(O[1]!=null){%><%=O[1]%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>Mobile</b> </td>
							<td><%if(O[4]!=null){%><%=O[4]%><%}else{%>--<%}%></td>
							<td> <b>State</b> </td>
							<td><%if(O[7]!=null){%><%=O[7]%><%}else{%>--<%}%></td>
							<td> <b>From_Res_Address</b> </td>
							<td><%if(O[2]!=null){%> <%=DateTimeFormatUtil.SqlToRegularDate(O[2].toString())%><%}else{%>--<%}%></td>
						</tr>
						
						<tr>
							<td><b>Alt_Mobile</b></td>
							<td><%if(O[0]!=null){%><%=O[0]%><%}else{%>--<%}%></td>
							<td><b>Landline</b></td>
							<td><%if(O[3]!=null){%><%=O[3]%><%}else{%>--<%}%></td>
							<td><b>Pin</b></td>
							<td><%if(O[6]!=null){%><%=O[6]%><%}else{%>--<%}%></td>
						</tr>
						
					<%}%>
					</tbody>
				</table>
				<%}else{ %>
				 <table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Residential  Address</b></td>
						</tr>
			        	<tr></tr>
						<tr></tr>
						<tr></tr>
			    <tr>	<td align="center"> <h5>Residential  Address Not Added!</h5> </td></tr>
				</tbody>
				</table>
				<%}%>
		<!---------------------------------- Residential Address  ------------------------------------->
				
				
		<!---------------------------------- Next kin Address  ---------------------------------------->	
				<%if(Nextdetails!=null){ %>
				 <table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Next kin Address</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						
						<tr>
						<td > <b>Next kin Address</b> </td>
							<td colspan="3"><%if(Nextdetails[6]!=null){%><%=Nextdetails[6]%><%}else{%>--<%}%></td>
							<td><b>City</b> </td>
							<td><%if(Nextdetails[1]!=null){%><%=Nextdetails[1]%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>Mobile</b> </td>
							<td><%if(Nextdetails[5]!=null){%><%=Nextdetails[5]%><%}else{%>--<%}%></td>
							<td> <b>State</b> </td>
							<td><%if(Nextdetails[8]!=null){%><%=Nextdetails[8]%><%}else{%>--<%}%></td>
							<td> <b>From_Res_Address</b> </td>
							<td><%if(Nextdetails[8]!=null){%><%=Nextdetails[8].toString()%><%}else{%>--<%}%></td>
						</tr>
						
						<tr>
							<td> <b>Alt_Mobile</b> </td>
							<td><%if(Nextdetails[0]!=null){%><%=Nextdetails[0]%><%}else{%>--<%}%></td>
							<td> <b>Landline</b> </td>
							<td><%if(Nextdetails[4]!=null){%><%=Nextdetails[4]%><%}else{%>--<%}%></td>
							<td> <b>Pin</b> </td>
							<td><%if(Nextdetails[7]!=null){%><%=Nextdetails[7]%><%}else{%>--<%}%></td>
						</tr>		
					</tbody>
				</table>
				<%}else{%>		
				<table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Next kin  Address</b></td>
						</tr>
			        	<tr></tr>
						<tr></tr>
						<tr></tr>
			    <tr>	<td align="center"> <h5>Next kin  Address Not Added!</h5> </td></tr>
				</tbody>
				</table>				
				<%}%>
			<!---------------------------------- Next kin Address  ---------------------------------------->	
			
			
			<!---------------------------------- Emergency Address  ---------------------------------------->	
				<%if(Emecdetails!=null){ %>
				<table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Emergency Address</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						
							<tr>
						<td > <b>Emergency Address</b> </td>
							<td colspan="3"><%if(Emecdetails[6]!=null){%><%=Emecdetails[6]%><%}else{%>--<%}%></td>
							<td><b>City</b> </td>
							<td><%if(Emecdetails[1]!=null){%><%=Emecdetails[1]%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>Mobile</b> </td>
							<td><%if(Emecdetails[5]!=null){%><%=Emecdetails[5]%><%}else{%>--<%}%></td>
							<td> <b>State</b> </td>
							<td><%if(Emecdetails[8]!=null){%><%=Emecdetails[8]%><%}else{%>--<%}%></td>
							<td> <b>From_Emec_Address</b> </td>
							<td><%if(Emecdetails[2]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(Emecdetails[2].toString())%><%}else{%>--<%}%></td>
						</tr>
						
						<tr>
							<td> <b>Alt_Mobile</b> </td>
							<td><%if(Emecdetails[0]!=null){%><%=Emecdetails[0]%><%}else{%>--<%}%></td>
							<td> <b>Landline</b> </td>
						    <td><%if(Emecdetails[4]!=null){%><%=Emecdetails[4]%><%}else{%>--<%}%></td>
							<td> <b>Pin</b> </td>
							<td><%if(Emecdetails[7]!=null){%><%=Emecdetails[7]%><%}else{%>--<%}%></td>
						</tr>	
						
					
					</tbody>
				</table>
				<%}else{ %>				
				<table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Emergency Address</b></td>
						</tr>
			        	<tr></tr>
						<tr></tr>
						<tr></tr>
			    <tr><td align="center"> <h5>Emergency Address Not Added!</h5> </td></tr>
				</tbody>
				</table>
				<%}%>
	<!---------------------------------- Emergency Address  ---------------------------------------->			
				
	</div>
	
		
	<div class="tab-pane" id="tabs-2" role="tabpanel">
		<!---------------------------------- Family Details  ---------------------------------------->	
				
				<table class="table table-striped table-bordered" >
				
					<tbody>
						<tr>
							<td colspan="3"  style="background-color: #0e6fb6; color: white;">
								<b>Family Details</b> 
								<!-- <span style="float: right;"><a class="btn btn-sm submit-btn" href="FamIncExcFwdList.htm"> Include / Exclude </a></span> -->
							</td>
						</tr>
                        
						<tr>
							<td><b>Member Name</b></td>
							<td><b>Relation</b></td>
							<td><b>Date Of Birth</b></td>														
																			
						</tr>
							
						<%if(familydetails!=null){for(Object[] O:familydetails){ %>
						<tr>
							<td><%if(O[0]!=null){%><%=O[0]%><%}else{%>--<%}%></td>
							<td><%if(O[1]!=null){%><%=O[1]%><%}else{%>--<%}%></td>
							<td><%if(O[2]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(O[2].toString())%><%}else{%>--<%}%></td>																			
						</tr>						
						<%}} %>
						
					</tbody>
				</table>
			
	<!---------------------------------- Family Details  ---------------------------------------->	
	</div>
	
	<!-- <div class="tab-pane" id="tabs-6" role="tabpanel">
		<p>Six Panel</p>
	</div>
	
	<div class="tab-pane" id="tabs-7" role="tabpanel">  
		<p>Seven Panel</p>
	</div>
	
	<div class="tab-pane" id="tabs-8" role="tabpanel">
		<p>Eight Panel</p>
	</div>
	
	<div class="tab-pane" id="tabs-9" role="tabpanel">
		<p>Nine Panel</p>
	</div> -->
	
	
</div>
				
		</div>		
				
			</div>
		</div>
	</div>
 </div>

</body>
<script src="webresources/js/master.js" type="text/javascript"></script>
<script type="text/javascript">

$("#profileImage").click(function(e) {
    $("#imageUpload").click();
});
$("#profileImage1").click(function(e) {
    $("#imageUpload1").click();
});

</script>

<script type="text/javascript">
function checkPic1(){
	
    var imgpath=document.getElementById('imageUpload1');
    var value  = (imgpath.files[0].size/1024)/1024; 
    if(value>5){
    	 alert("Image Size Should Be less than 5MB!");
    	 event.preventDefault();
    	 return false;
    }else{   	
    	document.getElementById("myForm").submit();
    	return true;
    }  
  }

  
function checkPic2(){

    var imgpath1=document.getElementById('imageUpload');
    var value1 = (imgpath1.files[0].size/1024)/1024;   
    if(value1>5){
    	 alert("Image Size Should Be less than 5MB!");
    	 event.preventDefault();
    	 return false;
    }else{   	
    	document.getElementById("myForm").submit();
    	return true;
    } 
  }
</script>
</html>


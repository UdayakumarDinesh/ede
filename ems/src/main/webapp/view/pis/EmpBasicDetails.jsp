<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.DateTimeFormatUtil" %>
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
	String empid = (String) request.getAttribute("empid");
	Object[] employeedetails = (Object[]) request.getAttribute("employeedetails");
	String path=(String)request.getAttribute("basevalue");

	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	DateTimeFormatUtil rd = new DateTimeFormatUtil();
%>
 
                   
                    
 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>ADMIN DASHBOARD </h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
					<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
					<li class="breadcrumb-item "><a href="PisAdminEmpList.htm">Employee List</a></li>
					<li class="breadcrumb-item active " aria-current="page">Employee info</li>
				</ol>
			</div>	
		</div>
	</div>	
	
	
<% String ses=(String)request.getAttribute("result"); 
   String ses1=(String)request.getAttribute("resultfail");
 %><%if(ses1!=null){
	%>
	
	<div align="center">
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></div>
	<%}  if(ses!=null){ %>
	<div align="center">
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div> </div>
                    <%} %>
	
	
	<div class="card-body" >
		<div class="card" >
			<div class="card-body " >
				<div class="row">
<div class="col-md-2" style="padding-bottom: 10px;" >
<div class="card" style="background-color: #ebf0f2;">
<div class="card-body text-center"  >
             <form action="PisImageUpload.htm" method="POST" enctype="multipart/form-data"> 
			<%if(employeedetails[29]!=null){ %>
			
			
			 <div class="pic1" >
			 <img src="data:image/<%=FilenameUtils.getExtension(employeedetails[29].toString())%>;base64,<%=path%>" class="img-responsive img-rounded" style=" width: 100%; height:124px; cursor: pointer;" id="profileImage1" >
			 </div>
			 <div class="p-image1">
             <i class="fa fa-camera upload-button"></i>
			 <input id="imageUpload1" type="file" name="photo1" accept=".png, .jpg, .jpeg"   capture style="display: none;" onchange="this.form.submit()">
			 			 
			 
			 <%}else{%>
		
			 
			  <div class="pic">
			  <img src="./view/images/<%if(employeedetails[9].toString().equals("M")){ %>avatarmale.png"<%}else{ %>avatar.png"<%} %> class="img-responsive img-rounded" style=" width: 100%; height:124px; cursor: pointer;  " id="profileImage">
			  </div>
			  <div class="p-image">
              <i class="fa fa-camera upload-button"></i>
			  <input id="imageUpload" type="file" name="photo1" accept=".png, .jpg, .jpeg"   capture style="display: none;" onchange="this.form.submit()">
			  <%}%>
			  </div>
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			   <input type="hidden" name="employeeid" value="<%=employeedetails[0]%>"/>
			  </form>
			 
			 
			 </div>
			 </div>
			 </div>
			 
			 <div class="col-md-10"  >
			 <table class="table table-striped table-bordered" >
					<tbody>
					<tr ><td colspan="5" rowspan="2"><h3 style="text-align: center;"> <%=employeedetails[3]%> (<%=employeedetails[22]%>)</h3></td></tr>
					<tr></tr>
						<tr>
						     <th>Designation</th>
	                         <th>Division</th>
	                         <th>DOB</th>
	                         <th>PunchCard No</th>
	                         <th>UID</th>
						</tr>
						<tr>
						<td><%=employeedetails[22]%></td>
				     	<td><%=employeedetails[23]%>(<%=employeedetails[24] %>)</td>
						<td><%=rd.SqlToRegularDate(employeedetails[5].toString())%></td>
					    <td><%=employeedetails[14]%></td>
						<td><%=employeedetails[15]%></td>
						</tr>
					</tbody>
				</table>
			 </div>
			</div>
			
			<div class="col-1"></div>
			
			<div class="col-12"  >
			 <table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3"> <b>Basic Details</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						
						<tr>
							<td> <b>Gender</b> </td>
							<td>
								<%if(employeedetails[9].toString().equalsIgnoreCase("M")){ %>
									Male
								<%}else if(employeedetails[9].toString().equalsIgnoreCase("F")){ %>
									Female
								<%} %> 
							</td>
							<td> <b>Group</b> </td>
							<td><%=employeedetails[25] %>(<%=employeedetails[26] %>)</td>
							<td> <b>PAN</b> </td>
							<td><%=employeedetails[13] %></td>
						</tr>
						
						
						<tr>
							<td> <b>DOJ</b> </td>
							<td><%=rd.SqlToRegularDate(employeedetails[6].toString())%></td>
							<td> <b>DOA</b> </td>
							<td><%=rd.SqlToRegularDate(employeedetails[7].toString())%></td>
							<td> <b>DOR</b> </td>
							<td><%=rd.SqlToRegularDate(employeedetails[8].toString())%></td>
						</tr>
						
						<tr>
							<td> <b>Email</b> </td>
							<td><%=employeedetails[16] %></td>
							<td> <b>Religion</b> </td>
							<td><%=employeedetails[12] %></td>
							<td> <b>Marital Status</b> </td>
							<td>
								<%if(employeedetails[11].toString().equalsIgnoreCase("M")){ %>
									Married
								<%}else if(employeedetails[11].toString().equalsIgnoreCase("U")){ %>
									UnMarried
								<%} %> 
							</td>
						</tr>
						
						<tr>
							<td> <b>SBI AccNo.</b> </td>
							<td><%=employeedetails[20] %></td>
							<td> <b>Blood Group</b> </td>
							<td><%=employeedetails[10] %></td>
							<td> <b>Home Town</b> </td>
							<td><%=employeedetails[27] %></td>
						</tr>
					</tbody>
				</table>
				</div>
				
			</div>
		</div>
	</div>
 </div>

</body>
<script src="webresources/js/alertDisappear.js" type="text/javascript"></script>
<script type="text/javascript">

$("#profileImage").click(function(e) {
    $("#imageUpload").click();
});
$("#profileImage1").click(function(e) {
    $("#imageUpload1").click();
});

</script>
</html>
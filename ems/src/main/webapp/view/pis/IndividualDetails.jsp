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
</style>
</head>
<body>

<%
	String empid = (String) request.getAttribute("empid");
    Object[] employeedetails = (Object[]) request.getAttribute("employeedetails");
	Object[] empdata = (Object[]) request.getAttribute("empdata");
	Object[] Emecdetails = (Object[]) request.getAttribute("emeaddressdetails");
	Object[] Nextdetails = (Object[]) request.getAttribute("nextaddressdetails");
	List<Object[]> Resdetails  = (List<Object[]>) request.getAttribute("resaddressdetails");
	Object[] Perdetails  = (Object[]) request.getAttribute("peraddressdetails");
	String path=(String)request.getAttribute("basevalue");
	List<Object[]> familydetails = (List<Object[]>) request.getAttribute("familydetails");
	List<Object[]>  appointmentDetailList =(List<Object[]>)request.getAttribute("Appointmentlist");
	List<Object[]>  awardsDetailList = (List<Object[]>)request.getAttribute("Awardslist");
	List<Object[]>	propertyDetailList = (List<Object[]>)request.getAttribute("Propertylist");
	List<Object[]>	publicationDetailList =(List<Object[]>)request.getAttribute("Publicationlist");
	List<Object[]>	qualificationDetailList =(List<Object[]>)request.getAttribute("Educationlist");
	Object[]   PassportDetails=(Object[])request.getAttribute("PassportList"); 
	List<Object[]>  foreignVisitDetails =(List<Object[]>)request.getAttribute("PassportVisitList");
	List<Object[]> EmployeeList = (List<Object[]>)request.getAttribute("EmployeeList");
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	String LoginType = (String) session.getAttribute("LoginType");
	
	String employeedetailsYesOrNo;
	String perAddressDetailsYesOrNo;
	String PassportDetailsYesOrNo;
	String emerAddressDetailsYesOrNo;
	if(employeedetails!=null){employeedetailsYesOrNo="Yes";}else{employeedetailsYesOrNo="No";}
	if(Perdetails!=null){perAddressDetailsYesOrNo="Yes";}else{perAddressDetailsYesOrNo="No";}
	if(Emecdetails!=null){emerAddressDetailsYesOrNo="Yes";}else{emerAddressDetailsYesOrNo="No";}
	if(PassportDetails!=null){PassportDetailsYesOrNo="Yes";}else{PassportDetailsYesOrNo="No";}
%>


 
  <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Individual Details </h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
					<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">PIS</a></li>
					<li class="breadcrumb-item active " aria-current="page">Individual Details</li>
				</ol>
			</div>	
		</div>
	</div>	          
	                    
 <div class=" page card dashboard-card" >
	<div class="container-fluid" style="margin-top: 10px;margin-bottom: -30px;">	

<div class="nav navbar bg-light dashboard-margin custom-navbar"  >
<div class="col-md-2"></div>
   	<div class="col-md-2"  style="margin-right: -345px; margin-top: 23px;">
   		<form action="##" method="post" target="blank" class="pull-right" style="margin-top:-30px;">
          			  
          	  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <button type="submit" class="btn btn-danger btn-xs " name="PrintPdfIndividualReport" value="PrintPdfIndividualReport" data-toggle="tooltip" data-placement="bottom" title="Print Employee Details" formaction="PrintEmployeeReportDownload.htm" formmethod="POST" formnovalidate="formnovalidate"><i class="fa-solid fa-file-pdf " style="font-size:24px"></i></button>
              <button type="submit" class="btn btn-success btn-xs" name="ExportIndividualReport" value="ExportIndividualReport" data-toggle="tooltip" data-placement="bottom" title="Export To Excel" formaction="DownloadEmpdetailsExcel.htm" formmethod="POST" formnovalidate="formnovalidate"><i class="fa-solid fa-file-excel" style="font-size:24px" ></i></button>
              <input type="hidden" name="Empid" value="<%=empid%>">
              
             
             <!--  <span  onclick="alert('Please Enter the Ex. ServiceMan Inside Employee Details.')">
              <button type="submit" id="ajay" disabled="disabled" class="btn btn-danger btn-xs " name="PrintPdfIndividualReport" value="PrintPdfIndividualReport" data-toggle="tooltip" data-placement="bottom" title="Print Employee Details"><i class="fa fa-file-pdf-o " style="font-size:24px"></i></button>
              <button type="submit" id="ajay" disabled="disabled" class="btn btn-success btn-xs" name="ExportIndividualReport" value="ExportIndividualReport" data-toggle="tooltip" data-placement="bottom" title="Export To Excel"><i class="fa fa-file-excel-o" style="font-size:24px"></i></button>
               </span> -->
           </form>
				
  </div>
						<!-- Select Employee Name -->
						<form action="IndividualDetails.htm" method="post" id="form1">
		  		         <div class="form-group"  style="margin-top: 10px;">
		  		         <label ><b>Select Employee  :</b> &nbsp; &nbsp; &nbsp;</label>
		  		        	<select class="form-control input-sm select2" name="empid" data-live-search="true" onchange="this.form.submit()">
			 					  <%if(EmployeeList!=null&&EmployeeList.size()>0){
			 					  for(Object[] ls:EmployeeList){ %>
			 					 <option value="<%=ls[0]%>" <%if(empid!=null && empid.equalsIgnoreCase(ls[0].toString())){%> selected="selected" <%}%> ><%= ls[1]%> (<%=ls[3]%>)-<%= ls[2]%></option>
					             <%} }%>
			 				</select>
						  </div>
      				   <div class="col-md-2"></div>
      				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      				</form>
      				 </div>
</div>       

	
	<div class="card-body" >
		
			<div align="center" style="margin-top: 15px;">
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
	
		<div class="card" >
			<div class="card-body " >
				<div class="row">
			<div class="col-md-2" style="padding-bottom: 10px;" >
			<div class="card" style="background-color: #ebf0f2;">
			<div class="card-body text-center"  >
			<form action="PisImageUpload.htm" method="POST" enctype="multipart/form-data" id="myForm" > 
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
					     	<td><%if(employeedetails[23]!=null&&employeedetails[24]!=null){%> <%=employeedetails[23]%>(<%=employeedetails[24] %>) <%}else{%>--<%}%></td>
							<td><%if(employeedetails[5]!=null){%> <%=DateTimeFormatUtil.SqlToRegularDate(employeedetails[5].toString())%> <%}else{%>--<%}%></td>
						    <td><%if(employeedetails[2]!=null){%><%=employeedetails[2]%><%}else{%>--<%}%></td>
						    <td><%if(employeedetails[33]!=null){%><%=employeedetails[33]%><%}else{%>--<%}%></td>
							<td><%if(employeedetails[34]!=null){%><%=employeedetails[34]%><%}else{%>--<%}%></td>
						</tr>
						<%if(!"P".equalsIgnoreCase(LoginType)){ %>
							<tr>
								<td colspan="6"  style="background-color: white; " > 
									<form action="EmpRequestMsg.htm" method="GET" style="float: right;">
								 		<span><b style="color: red;">Note :</b> Any changes in profile request to Admin  &nbsp;&nbsp;&nbsp;&nbsp;
											<button type="submit" class="btn btn-sm misc-btn"  name="action" value="msg" >REQUEST MESSAGE</button>
								    	</span>
							    	</form>
						       </td>
					       </tr> 
					    <%}%>
					</tbody>
				</table>
			 </div>
			</div>
			
			<div class="col-12">
			
		<ul class="nav nav-tabs" role="tablist">
				<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#tabs-1" role="tab" >Basic Details</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab">Family</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-3" role="tab">Address</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-4" role="tab">Appointment</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-5" role="tab">Awards</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-6" role="tab">Property</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-7" role="tab">Publications</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-8" role="tab">Qualifications</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tabs-9" role="tab">Passport</a></li>
				
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
								<%}else{%>--<%}%> 
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
								<form action="EmpBloodGropuEdit.htm" method="post"> 
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
							                	<button type="submit" class="btn btn-sm update-btn" style="float: right;" name="" value="" onclick="return confirm('Are You Sure to Update?');">Update</button>
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
	
	 <div class="tab-pane" id="tabs-4" role="tabpanel">
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
	
	<div class="tab-pane" id="tabs-5" role="tabpanel">  
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
	
	<div class="tab-pane" id="tabs-6" role="tabpanel">
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
	
	<div class="tab-pane" id="tabs-7" role="tabpanel">
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
	<div class="tab-pane" id="tabs-8" role="tabpanel">
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
	
	<div class="tab-pane" id="tabs-9" role="tabpanel">
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


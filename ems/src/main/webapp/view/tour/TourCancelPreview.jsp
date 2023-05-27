<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
body{
 	overflow-x: hidden;
    overflow-y: hidden;
}
</style>
<title>Tour Cancel Form</title>

</head>
<body>

	<%
	String LabLogo = (String) request.getAttribute("LabLogo");
	String empno = (String) session.getAttribute("EmpNo");
	Object[] tourdetails = (Object[]) request.getAttribute("tourdetails");
	Object[] touradvancedetails = (Object[])request.getAttribute("touradvancedetails");
	List<Object[]> list = (List<Object[]>)request.getAttribute("Touronwarddetails");
	List<Object[]> statusdetails = (List<Object[]>)request.getAttribute("cancelstatustrack");
	Object[] ApprovalEmp=(Object[])request.getAttribute("ApprovalEmp"); 
	SimpleDateFormat time = new SimpleDateFormat("HH:mm");
	
	%>
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Tour Cancel Form</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TourCancelledlist.htm">Tour Cancel List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Tour Cancel Form</li>
					</ol>
			</div>
		</div>
	</div>


	<div class="page card dashboard-card">
		<div class="card-body" align="center">
			<div class="row">



				<div class="card-body">
					<div class="card"
						style="padding-top: 0px;  width: 85%;">
						<form action="TourApplyList.htm" method="post">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="card-body main-card" style="padding-top: 0px; " align="center">
								<table style="border: 0px; width: 100%">
									<tr>
										<td
											style="width: 20%; height: 75px; border: 0; margin-bottom: 10px;"><img
											style="width: 80px; height: 90px; margin: 5px;" align="left"
											src="data:image/png;base64,<%=LabLogo%>"></td>
										<td
											style="width: 60%; height: 75px; border: 0; text-align: center; "><h4>
												FORM FOR CANCELLATION OF TOUR PROGRAMME <br>
											</h4></td>
										<td
											style="width: 20%; height: 75px; border: 0; vertical-align: bottom;">
											<span style="float: right;"> &nbsp;<span
												class="text-blue"></span></span>
										</td>
									</tr>
								</table>
								
								<br>
								
								<table style="border: 0px; width: 100%">
									<tr>
										<td> <b>Department : </b>&nbsp; <%=tourdetails[3]%></td>
										<td><b> Phone No : </b> &nbsp; <%=tourdetails[6]%></td>
										<td><b> Date : </b></td>
										
									</tr>
								</table>
								<br>
								<div align="left">
								<p> A tour which was approved / planed to visit <span style="color: blue; font-weight: 600; text-decoration: underline;"> <%=tourdetails[12]%> </span> 
								by the following official for the purpose of <span style="color: blue; font-weight: 600; text-decoration: underline;"> <%=tourdetails[9]%> </span>
								 may be cancelled / has been re-scheduled due to <span style="color: blue; font-weight: 600; text-decoration: underline;"> <%=tourdetails[25]%> </span>
								 </p>
								 <p> Movement Order Ref. no ________________ dated _______________(if issued)</p>
								<table style="width: 100%; margin-top: 10px; ">
									<thead>
										<tr>
											<th style="border: 1px solid black; border-collapse: collapse;"> Name </th>
											<th style="border: 1px solid black; border-collapse: collapse;">Emp No</th>
											<th style="border: 1px solid black; border-collapse: collapse;"> DOB</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Pay Level</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Mobile No</th>
											<th style="border: 1px solid black; border-collapse: collapse;">Email</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;color: blue;"><%=tourdetails[4]%>(<%=tourdetails[3]%>)</td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;color: blue;"><%=tourdetails[2]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;color: blue;"><%=DateTimeFormatUtil.fromDatabaseToActual( tourdetails[5].toString())%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;color: blue;"><%=tourdetails[8]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;color: blue;"><%=tourdetails[6]%></td>
											<td style="border: 1px solid black; border-collapse: collapse; text-align: center;color: blue;"><%=tourdetails[7]%></td>
										</tr>
									</tbody>
								</table>
								<p> The above is put up for incurring cancellation charges of Rs. __________ / reimbursement of 
								Rs.___________ to the undersigned </p>
								<p> The Invoice / cancelled ticket is enclosed  </p>
								<p> Put up for Expenditure Sanction.</p>
								<b style="float: right;"> Signature of employee <br> Name : &nbsp;&nbsp;<%=tourdetails[4]%>(<%=tourdetails[3]%>)</b><br><br>
								<b>Dept. Incharge.</b><br><br><b style="float: right;">Incharge-F & A</b>  <br> <b>DGM</b><br><br>
								
								<b style="margin-left: 370px;"> SANCTIONED / NOT SANCTIONED </b>
								<b style="margin-left: 470px;">CEO</b><br><br><br>
								
								<b>P&A Dept. for issue of cancellation of movement orders and onward transmission to F&A Department for necessary action.</b>	
													
								</div>
							</div>
						</form>
					</div>
				
				
				<div class="card" style="width: 85%;">
						   <div class="card-body">  
							<div align="center" style="margin: 10px;">
								<div class="row">
									<div class="col-md-6">
										<table>
										<tr> <th>Name </th><th> Remarks</th></tr>
										<%if(statusdetails!=null && statusdetails.size()>0){
											for(Object[] obj : statusdetails){%>
										
											<tr> 
												<td style="text-align: left; color: blue;"> <%=obj[2]%>(<%=obj[3]%>):</td> 
												<td style="text-align: left;"><%if(obj[6]!=null){%> <%=obj[6]%> <%}else{%> -- <%}%></td>
											</tr>											
										
										<% }}%>
										</table>
									</div>
						<div class="col-md-6" style="margin-top: -10px;">
						<%if(tourdetails[19]!=null && tourdetails[19].toString().equalsIgnoreCase("CAD") || tourdetails[19].toString().equalsIgnoreCase("CDG") || tourdetails[19].toString().equalsIgnoreCase("CDF") || tourdetails[19].toString().equalsIgnoreCase("CDC") || tourdetails[19].toString().equalsIgnoreCase("CDP")){%>
							<form action="TourCancelForward.htm" method="post">
								    <div class="row" style="margin-top: 10px;">
										<div class="col-md-4">
											    <label style="font-weight: 600;"> Remarks  :</label>
										</div>
										<div class="col-md-6">
												<textarea   name="remarks"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>												                         
										</div>
									  </div>
									  <div class="row" style="margin-top: 15px;">
											<div class="col-md-5"></div>
											 <div class="col-md-3">
											 	<input type="hidden" name="tourapplyId" value="<%=tourdetails[0]%>">
												<button style="margin-bottom: -10px; " type="submit" class="btn btn-sm submit-btn"  name="Action" onclick="return confirm('Are you sure to Submit?')"  data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>										
											 </div>
									   </div>						
							          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>								
							</form>
						<%}%>			
						<%if( !tourdetails[2].toString().equalsIgnoreCase(empno) && tourdetails[19]!=null && (tourdetails[19].toString().equalsIgnoreCase("CAA") || tourdetails[19].toString().equalsIgnoreCase("CAG") || tourdetails[19].toString().equalsIgnoreCase("CAF") || tourdetails[19].toString().equalsIgnoreCase("CAC") || tourdetails[19].toString().equalsIgnoreCase("CBU"))){%>			
						  <form action="CancelApproval.htm" method="post">
						  			 <div class="row" style="margin-top: 10px;">
										<div class="col-md-4">
											    <label style="font-weight: 600;"> Remarks  :</label>
										</div>
										<div class="col-md-6">
												<textarea   name="<%=tourdetails[0]%>"  id="remarks" class=" form-control input-sm " placeholder="Enter Remarks......."     maxlength="250" required="required"></textarea>												                         
										</div>
									  </div>
						  			 <div class="row" style="margin-top: 15px;">
											<div class="col-md-4"></div>
											
												<%if(tourdetails[19].toString().equalsIgnoreCase("ABP")){%>
												<div class="col-md-2">
													<button style="margin-bottom: -10px; " type="submit" class="btn btn-sm submit-btn"  name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  onclick="return confirm('Are you sure to Submit?')" data-toggle="tooltip" data-placement="top" title="Forward"> Verify</button>
												</div>
												<div class="col-md-2">
													<button style="margin-bottom: -10px; background-color: maroon;" type="submit" class="btn btn-sm submit-btn"  name="reject" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>" onclick="return confirm('Are you sure to Return?')" data-toggle="tooltip" data-placement="top" title="Return"> DisApprove </button>																								
												</div>
												<%}else{%>
												<div class="col-md-2">
													<button style="margin-bottom: -10px; " type="submit" class="btn btn-sm submit-btn"  name="approve" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>" onclick="return confirm('Are you sure to Forward?')"  data-toggle="tooltip" data-placement="top" title="Forward"> Forward</button>										
												</div>
												<div class="col-md-2">
													<button style="margin-bottom: -10px; background-color: maroon;" type="submit" class="btn btn-sm submit-btn"  name="reject" value="<%=tourdetails[0]%>_<%=tourdetails[19]%>"  onclick="return confirm('Are you sure to Return?')" data-toggle="tooltip" data-placement="top" title="Return"> Return </button>																					
												</div>
												<%}%>
									   </div>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>										
									</form>			
								<%}%>
								</div>
				   </div>
				  </div>
				 </div>
				</div>
			</div>			
			</div>
		</div>
	</div>
</body>
</html>
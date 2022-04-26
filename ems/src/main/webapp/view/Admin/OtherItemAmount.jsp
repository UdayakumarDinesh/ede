<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Other Item Amount</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>

<body>

<%
List<Object[]> itemlist = (List<Object[]>)request.getAttribute("itemlist");
%>
<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Medicine List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>	
						<li class="breadcrumb-item active " aria-current="page">CHSS Medicine List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 <div class="card-body">
			 <div class="card">
				 <div class="card-body">
						<div class="row">
						<div class="col-4"></div>
						<div class="col-2" align="left" style="margin-right: -8%;"> <h5>Item Name :</h5></div>
							<div class="col-2" align="left">
							
								<select class="form-control select2" name="tratementname" data-container="body" data-live-search="true"  onchange="this.form.submit();" style="width: 200px; align-items: center; font-size: 5px;">
							          
									<%if(itemlist!=null&&itemlist.size()>0){for(Object[] Obj:itemlist){%>
										<option value="<%=Obj[0]%>" > <%=Obj[1]%></option>
												<%}}%>
						         </select>
							</div>
						</div>
						<br>
							<div class="row">					
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;"  >SNo.          </th>
											<th style="width:35%;" >Basic From 	  </th>
											<th style="width:25%;" >Basic To      </th>
											<th style="width:10%;" >Permit Amount </th>
											<th style="width:5%;"  ><button type="button" class="btn tbl-row-add" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone" >
											<td><span class="sno" id="sno">1</span></td>
											<td><input type="text" class="form-control items" name="basicfrom" id="basicfrom" value=""   maxlength="100" required="required"></td>
											<td><input type="text" class="form-control items" name="basicto" id="basicto" value=""    maxlength="100" required="required"></td>
											<td><input type="text" class="form-control billdate" name="billdate" id="billdate" value=""     maxlength="100"  required="required"></td>
											<td><button type="button" class="btn tbl-row-rem"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button></td>
										</tr>
									</tbody>															
								</table>
						</div>						
				 </div>
			 </div>
		 </div>
</div>
</body>
</html>
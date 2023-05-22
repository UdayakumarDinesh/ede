<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date" %>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
	
		
</head>

<style>
body {
  overflow-x: hidden;
  overflow-y: hidden;
}

</style>
<body>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>IT Inventory</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
							<li class="breadcrumb-item "><a href="ITInventoryDashboard.htm">Dashboard</a></li>
					<li class="breadcrumb-item active" aria-current="page">Inventory
						</li>
				</ol>
			</div>

		</div>
	</div>
	
	<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
		<div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
		</div>
		<%}if(ses!=null){ %>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
	<%} %>
		
	<%
		 
	     /* String FromDate=(String)request.getAttribute("fromdate");
	     String ToDate=(String)request.getAttribute("todate");
	     String currentdate=(String)request.getAttribute("currentdate");
	     
		 Date Fd=DateTimeFormatUtil.dateConversionSql(FromDate);
		 Date To=DateTimeFormatUtil.dateConversionSql(ToDate);
		 Date cd=DateTimeFormatUtil.dateConversionSql(currentdate);
		 System.out.println("Fd--"+Fd); 
		 System.out.println("To--"+To); 
		 System.out.println("cd--"+cd);   */
		 
		 List<Object[]> InventoryQuantityList =(List<Object[]>)request.getAttribute("InventoryQuantityList");
		 String Status="";
		 for(Object[] QtyList:InventoryQuantityList)
		 {
		    	  Status = QtyList[29].toString();
		  }
		    
	%>
		
	<div  class="text-center" style="color:red;text-align: center; font-size:15px;"><marquee><b>* Inventory Details must be declared within January of every year *</b> </marquee></div>
	
	<% if(InventoryQuantityList.size()>0) {%>
			<div class="card"  style="overflow-y: hidden;" >
			<div class="card-body">	
			<form action="##">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<table class="table table-bordered table-hover table-striped table-condensed"   > 
			<thead>
				<tr>
					<th style="width: 1%">SN</th>
					<th style="width: 8%">Items</th>
					<th style="width: 1%">Quantity</th>
					<th style="width: 1%">Intended By</th>
					<th style="width: 3%">Remarks</th>
				<% if(Status.equals("I") || Status.equals("R") || Status.equals("A")) { %>
				    <th style="width: 2%">Action</th>
				<%} %>
		
				</tr>
			</thead>
			<tbody>
									
		  <% int count=0; %>
		    <% for(Object[] QtyList:InventoryQuantityList)
		    	
		    { %>
		    
		    <tr>
		       
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Desktop/Computer</td>
			<td  style="width: 2%" >
			
				<input type="number" class="form-control "  style="width:50%;margin-left:40px;"   value="<%=QtyList[1] %>"  min="0" max="99" name="Desktop"> 
			</td>
			<td>
				<select class="form-control" name="DesktopIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF" <%if(QtyList[2]!=null){ if(QtyList[2].toString().equals("SELF")){%> selected <%}} %>>Self</option>
									<option value="IT"  <%if(QtyList[2]!=null){ if(QtyList[2].toString().equals("IT")){%> selected <%}} %> >IT</option>
				</select>
			</td>
			<td style="width: 2%">
			
				 <input type="text" class="form-control"  value="<%=QtyList[3]%>"  name="DesktopRemarks">
			</td>
			
			 <%if(Status.equals("I") || Status.equals("R")  || Status.equals("A")){ %>	
			<td>
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>" formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>&emsp;
				<% if(!QtyList[1].toString().equals("0")){%><button type="submit" class ="btn btn-sm add-btn " name="Itinventoryid" value="<%=QtyList[0]%>/D/<%=QtyList[28] %>" formaction="InventoryConfigure.htm" formmethod="POST" >configure</button> <%} else{ %><%} %>
			</td>
			<%} %> 
			</tr>
									 
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Laptop</td>
			 <td style="width: 2%">
			 
				<input type="number" class="form-control"    style="width:50%;margin-left:40px;" value="<%=QtyList[4] %>"  min="0" max="99" name="Laptop"> 
			</td>
			<td>
				<select class="form-control" name="LaptopIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF" <%if(QtyList[5]!=null){ if(QtyList[5].toString().equals("SELF")){%> selected <%}} %>>Self</option>
									<option value="IT" <%if(QtyList[5]!=null){ if(QtyList[5].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
			</td>
			<td style="width: 2%">
			
				 <input type="text" class="form-control"  value="<%=QtyList[6] %>"  name="LaptopRemarks" maxlength="500" >
			</td>
			 <%if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>	
			<td>
			    <button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>" formaction="QuantityEditSubmit.htm" formmethod="POST"  onclick="return confirm('Are You Sure To Update ?');">update</button>&emsp;
				<% if(!QtyList[4].toString().equals("0")){ %><button type="submit" class ="btn btn-sm add-btn " name="Itinventoryid" value="<%=QtyList[0]%>/L/<%=QtyList[28] %>" formaction="InventoryConfigure.htm" formmethod="POST" >configure</button> <%} else{ %><%} %>
			</td>
			 <%} %> 
			</tr>
							  
		    <tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >USB Pendrive</td>
			 <td style="width: 2%;text-align: center;">
			 
				<input type="number" class="form-control"   style="width:50%;margin-left:40px;" value="<%=QtyList[7] %>" min="0"  max="99"name="USBPendrive"  >
			</td>
			<td>
				<select class="form-control" name="USBPendriveIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF"  <%if(QtyList[8]!=null){ if(QtyList[8].toString().equals("SELF")){%> selected <%}} %> >Self</option>
									<option value="IT"  <%if(QtyList[8]!=null){ if(QtyList[8].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
			</td>
				<td style="width: 2%">
				
				 <input type="text" class="form-control"  value="<%=QtyList[9] %>"  name="USBPendriveRemarks" maxlength="500" >
			</td>
			 <%if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>	
			<td>
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>" formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>
			</td>
			<%} %> 
			</tr>
								
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Printer</td>
			<td style="width: 2%;text-align: center;">
			
				<input type="number" class="form-control "   style="width:50%;margin-left:40px;" value="<%=QtyList[10] %>"  min="0"  max="99" name="Printer"> 
			</td>
			<td>
				<select class="form-control" name="PrinterIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF"  <%if(QtyList[11]!=null){ if(QtyList[11].toString().equals("SELF")){%> selected <%}} %> >Self</option>
									<option value="IT"  <%if(QtyList[11]!=null){ if(QtyList[11].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
			</td>
			<td style="width: 2%">
				
				  <input type="text" class="form-control" value="<%=QtyList[12] %>" name="PrinterRemarks" maxlength="500" >
			</td>
			 <%if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>	
			<td>
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>
			</td>
			<%} %> 
			</tr>
			
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				
			    <td style="width: 2%" >Telephone</td>
			<td style="width: 2%;text-align: center;">
			
				<input type="number" class="form-control"  style="width:50%;margin-left:40px;"  value="<%=QtyList[13] %>" min="0"  max="99" name="Telephone"> 
		   </td>
			<td>
				<select class="form-control" name="TelephoneIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF"   <%if(QtyList[14]!=null){ if(QtyList[14].toString().equals("SELF")){%> selected <%}} %>>Self</option>
									<option value="IT"  <%if(QtyList[14]!=null){ if(QtyList[14].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
		   </td>
			<td style="width: 2%">
			
				 <input type="text" class="form-control" value="<%=QtyList[15] %>" name="TelephoneRemarks" maxlength="500" >
			</td>
			 <% if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>	
			<td>
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>
			</td>
			<%} %> 
			</tr>
								
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Fax Machine</td>
			<td style="width: 2%;text-align: center;">
			
				<input type="number" class="form-control" style="width:50%;margin-left:40px;" value="<%=QtyList[16] %>"  min="0"  max="99" name="FaxMachine"> 
		   </td>
		   
			<td>
				<select class="form-control" name="FaxMachineIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF" <%if(QtyList[17]!=null){ if(QtyList[17].toString().equals("SELF")){%> selected <%}} %> >Self</option>
									<option value="IT" <%if(QtyList[17]!=null){ if(QtyList[17].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
		   </td>
			<td style="width: 2%">
			
				<input type="text" class="form-control" value="<%=QtyList[18] %>" name="FaxMachineRemarks" maxlength="500" >
			</td>
			 <%if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>	
			<td>
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>
			</td>
			<%} %> 
			</tr>
								
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Scanner</td>
			 <td style="width: 2%;text-align: center;">
			 
				<input type="number" class="form-control"  style="width:50%;margin-left:40px;"  value="<%=QtyList[19] %>" min="0"  max="99"name="Scanner"> 
			</td>
			<td>
				<select class="form-control" name="ScannerIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF" <%if(QtyList[20]!=null){ if(QtyList[20].toString().equals("SELF")){%> selected <%}} %> >Self</option>
									<option value="IT" <%if(QtyList[20]!=null){ if(QtyList[20].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
			</td>
			
			<td style="width: 2%">
			
				<input type="text" class="form-control" value="<%=QtyList[21] %>" name="ScannerRemarks" maxlength="500" >
			</td>
			 <%if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>	
			<td>
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>
			</td>
			<%} %>
			</tr>
							
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Xerox Machine</td>
			<td style="width: 2%;text-align: center;">
			
				<input type="number" class="form-control "   style="width:50%;margin-left:40px;"  value="<%=QtyList[22] %>"  min="0"  max="99" name="XeroxMachine" required="required"> 
			</td>
			<td>
				<select class="form-control" name="XeroxMachineIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF"  <%if(QtyList[23]!=null){ if(QtyList[23].toString().equals("SELF")){%> selected <%}} %>>Self</option>
									<option value="IT" <%if(QtyList[23]!=null){ if(QtyList[23].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
			</td>
			<td style="width: 2%">
			
				<input type="text" class="form-control" value="<%=QtyList[24] %>" name="XeroxMachineremarks" maxlength="500" >
				
			</td>
			 <%if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>	
			<td>
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>
			</td>
			<%} %>
			</tr>
								
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Miscellaneous</td>
			 <td style="width: 2%;text-align: center;">
			 
				<input type="number" class="form-control"   style="width:50%;margin-left:40px;"  value="<%=QtyList[25] %>"  min="0"  max="99" name="Miscellaneous"> 
			</td>
			<td>
				<select class="form-control" name="MiscellaneousIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF" <%if(QtyList[26]!=null){ if(QtyList[26].toString().equals("SELF")){%> selected <%}} %>>Self</option>
									<option value="IT" <%if(QtyList[26]!=null){ if(QtyList[26].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
			</td>
			<td style="width: 2%">
			
				<input type="text" class="form-control" value="<%=QtyList[27] %>" name="Miscellaneousremarks" maxlength="500" >
				
				<input type="hidden" name="DeclarationYear" value="<%=QtyList[28] %>">
			
			 <% if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>	
			<td>
				
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>
			</td>
			<%} %>	
			</tr>
		
								
			</table>
			<% if(Status.equals("I") || Status.equals("R") ||  Status.equals("A")){ %>
			 <div class="row" align="center">
			    <div class="col-sm-12">
			       <input type="hidden" name ="declarationyear" value="<%=QtyList[28] %>">
			       <button type="submit" class="btn btn-sm preview-btn" name="" value=""  formaction="InventoryView.htm"  formmethod="POST"  >Preview</button> 
		
			   </div>
			 </div> 
			 <% }else{ %>
			  <div class="row" align="center">
			    <div class="col-sm-12">
			
			       <button type="submit" class="btn btn-sm preview-btn" name="" value=""  formaction="InventoryFormPreview.htm"   formtarget="blank" formmethod="GET"  >Preview</button> 
		
			   </div>
			 </div> 
			 <%} %>
			 
		   <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
<%} %>
	</form>		
	 </div>
</div>
 <%} %>
	
		
		<!----------------------------------------------------------------------------------------------------------  -->
	<%  if (InventoryQuantityList.size()==0) {%> 
			<div class="card"  >
			<div class="card-body " >	
			<form action="ITInventoryQuantityAddSubmit.htm" method="POST"  >
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<table class="table table-bordered table-hover table-striped table-condensed" style="width:100%;"  > 
								<thead>
									<tr>
										<th style="width: 1%">SN</th>
										<th style="width: 16%">Items</th>
										<th style="width: 1%">Quantity</th>
										<th style="width: 1%">Intended By</th>
										<th style="width: 5%">Remarks</th>
										
									</tr>
								</thead>
								<tbody>
								<%int count=0; %>
							    
							    <tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >Desktop/Computer</td>
								    <td style="width: 1%;text-align: center;" >
									  <input type="number" class="form-control " style="width:50%;margin-left:30px;" value="0"  id="qty" min="0" max="99" name="Desktop">
									</td>
									<td>
									<select class="form-control" name="DesktopIntendedBy" id="intndby" >
									 <option value="" selected="selected" disabled="disabled">Select</option>
									 <option value="SELF">Self</option>
									 <option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1"  style="display:block; " class="form-control"  name="DesktopRemarks"  maxlength="500" ></textarea>
									</td>
									
								</tr>
							 
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >Laptop</td>
								    <td style="width: 2%">
									<input type="number" class="form-control"   style="width:50%;margin-left:30px;"  value="0"  id="qty1" min="0"  max="99" name="Laptop"> 
									</td>
									<td><select class="form-control" name="LaptopIntendedBy"  id="intndby1">
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="LaptopRemarks" maxlength="500"  ></textarea>
									</td>
								</tr>
							    
							    <tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >USB Pendrive</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control"  style="width:50%;margin-left:30px;"  value="0"  id="qty2" min="0"  max="99" name="USBPendrive" >
									</td>
									<td><select class="form-control" name="USBPendriveIntendedBy" id="intndby2">
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="USBPendriveRemarks"  maxlength="500" ></textarea>
									</td>
								</tr>
									
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									
								    <td style="width: 2%" >Printer</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control"  style="width:50%;margin-left:30px;"  value="0"   id="qty3" min="0"   max="99" name="Printer"> 
									</td>
									<td><select class="form-control" name="PrinterIntendedBy" id="intndby3">
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="PrinterRemarks"  maxlength="500" ></textarea>
									</td>
								</tr>
									 
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									
								    <td style="width: 2%" >Telephone</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control" style="width:50%;margin-left:30px;" value="0"  id="qty4" min="0" max="99" name="Telephone"> 
									</td>
									<td><select class="form-control" name="TelephoneIntendedBy" id="intndby4" >
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="TelephoneRemarks"  maxlength="500" ></textarea>
									</td>
								</tr>
								
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >Fax Machine</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control"   style="width:50%;margin-left:30px;"  value="0"  id="qty5" min="0" max="99" name="FaxMachine"> 
									</td>
									<td><select class="form-control" name="FaxMachineIntendedBy" id="intndby5">
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="FaxMachineRemarks"  maxlength="500" ></textarea>
									</td>
								</tr>
								
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >Scanner</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control "  style="width:50%;margin-left:30px;"  value="0"  id="qty6" min="0"   max="99" name="Scanner"> 
									</td>
									<td><select class="form-control" name="ScannerIntendedBy" id="intndby6">
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1"  style="display:block; " class="form-control"  name="ScannerRemarks"  maxlength="500" ></textarea>
									</td>
								</tr>
								
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >Xerox Machine</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control "    style="width:50%;margin-left:30px;" value="0" min="0"   id="qty7"  max="99" name="XeroxMachine"> 
									</td>
									<td><select class="form-control" name="XeroxMachineIntendedBy" id="intndby7">
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="XeroxMachineRemarks" maxlength="500" ></textarea>
									</td>
								</tr>
								
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >Miscellaneous</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control "  style="width:50%;margin-left:30px;" value="0" min="0"  id="qty8"  max="99" name="Miscellaneous"> 
									</td>
									<td><select class="form-control" name="MiscellaneousIntendedBy" id="intndby8">
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="MiscellaneousRemarks"  maxlength="500" ></textarea>
									</td>
								</tr>
							</table>
							
						<div class="row justify-content-center">
							 <button type="submit" style="margin-left:0px;" class="btn btn-sm submit-btn " name="action" value="submit" onclick="return Add();" >Submit</button>
						</div>
						   <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
				
				</form>		
		 </div>
		 </div>
	 
	
   <%}%> 
		 
			
</body>

<script>

function Add(){
	
	console.log("qty---"+$('#qty').val());
	console.log("qty1---"+$('#qty1').val());
	console.log("qty2---"+$('#qty2').val());
	console.log("qty3---"+$('#qty3').val());
	console.log("qty4---"+$('#qty4').val());
	console.log("qty5---"+$('#qty5').val());
	console.log("qty6---"+$('#qty6').val());
	console.log("qty7---"+$('#qty7').val());
	console.log("qty8---"+$('#qty8').val());
	
	console.log("intndby---"+$('#intndby').val());
	if($('#qty').val()==='0' && $('#qty1').val()==='0' && $('#qty2').val()==='0' &&  $('#qty3').val()==='0' &&  $('#qty4').val()==='0'  &&  $('#qty5').val()==='0' &&  $('#qty6').val()==='0' &&  $('#qty7').val()==='0' && $('#qty8').val()==='0'){
		alert('Please Enter the Quantity! ');
		return false;
	}else if ($('#intndby').val()==null && $('#intndby1').val()==null && $('#intndby2').val()==null && $('#intndby3').val()==null && $('#intndby4').val()==null && $('#intndby5').val()==null &&  $('#intndby6').val()==null &&  $('#intndby7').val()==null &&  $('#intndby8').val()==null){
		alert('Please Enter Intended By! ');
		return false;
	}else{
			return confirm('Are You Sure To Submit?');
	}
	
}
</script>
</html>
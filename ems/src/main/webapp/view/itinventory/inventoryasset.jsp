<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
		
</head>
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
							<li class="breadcrumb-item "><a href="InventoryList.htm">Inventory List</a></li>
					<li class="breadcrumb-item active" aria-current="page">Inventory Details
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
		  int currentyear= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		  List<Object[]> InventoryQuantityList =(List<Object[]>)request.getAttribute("InventoryQuantityList");
	%>
		
	<%--  <% for(Object[] Qtylist:InventoryQuantityList){
	 if(Qtylist[19]!=null && Qtylist[20].toString().substring(0,4)==Integer.toString(currentyear)){%> 
	 <h3>Declaration already forwarded for this year</h3>
	 
	 <%} 
	 else{ %>  --%>
		
		
	<%if(InventoryQuantityList.size()>0) {%>
			<div class="card"  style="max-height: 40rem; overflow-y:auto;">
			<div class="card-body " >	
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
					<th style="width: 2%">Action</th>
				</tr>
			</thead>
			<tbody>
									
		    <%int count=0; %>
		    <% for(Object[] QtyList:InventoryQuantityList){%>
		    <tr>
		       
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Desktop/Computer</td>
			    <td  style="width: 2%" >
				<input type="number" class="form-control "  style="width:50%;margin-left:40px;"  value="<%=QtyList[1] %>"  min="0" max="99" name="Desktop"> 
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
				<td>
				<button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>" formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button>&emsp;
				<% if(!QtyList[1].toString().equals("0")){%><button type="submit" class ="btn btn-sm add-btn " name="Itinventoryid" value="<%=QtyList[0]%>/C" formaction="InventoryConfigure.htm" formmethod="POST" >configure</button> <%} else{ %><%} %>
				</td>
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
				 <input type="text" class="form-control"  value="<%=QtyList[6] %>"  name="LaptopRemarks" maxlength="500">
				</td>
				<td><button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>" formaction="QuantityEditSubmit.htm" formmethod="POST"  onclick="return confirm('Are You Sure To Update ?');">update</button>&emsp;
				<% if(!QtyList[4].toString().equals("0")){ %><button type="submit" class ="btn btn-sm add-btn " name="Itinventoryid" value="<%=QtyList[0]%>/L" formaction="InventoryConfigure.htm" formmethod="POST" >configure</button> <%} else{ %><%} %>
				</td>
			</tr>
							  
		    <tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >USB Pendrive</td>
			    <td style="width: 2%;text-align: center;">
				<input type="number" class="form-control"   style="width:50%;margin-left:40px;" value="<%=QtyList[7] %>" min="0"  max="99"name="USBPendrive" >
				</td>
				<td>
				<select class="form-control" name="USBPendriveIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF"  <%if(QtyList[8]!=null){ if(QtyList[8].toString().equals("SELF")){%> selected <%}} %> >Self</option>
									<option value="IT"  <%if(QtyList[8]!=null){ if(QtyList[8].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
				</td>
				<td style="width: 2%">
				 <input type="text" class="form-control"  value="<%=QtyList[9] %>"  name="USBPendriveRemarks" maxlength="500">
				</td>
				<td><button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>" formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button></td>
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
				  <input type="text" class="form-control" value="<%=QtyList[12] %>" name="PrinterRemarks" maxlength="500">
				</td>
				<td><button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button></td>
			</tr>
			
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				
			    <td style="width: 2%" >Telephone</td>
			    <td style="width: 2%;text-align: center;">
				<input type="number" class="form-control "    style="width:50%;margin-left:40px;"  value="<%=QtyList[13] %>" min="0"  max="99" name="Telephone"> 
				</td>
				<td>
				<select class="form-control" name="TelephoneIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF"   <%if(QtyList[14]!=null){ if(QtyList[14].toString().equals("SELF")){%> selected <%}} %>>Self</option>
									<option value="IT"  <%if(QtyList[14]!=null){ if(QtyList[14].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
				</td>
				<td style="width: 2%">
				 <input type="text" class="form-control" value="<%=QtyList[15] %>" name="TelephoneRemarks" maxlength="500">
				</td>
				<td><button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button></td>
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
				<input type="text" class="form-control" value="<%=QtyList[18] %>" name="FaxMachineRemarks" maxlength="500">
				</td>
				<td><button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button></td>
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
				<input type="text" class="form-control" value="<%=QtyList[21] %>" name="ScannerRemarks" maxlength="500">
				</td>
				<td><button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button></td>
			</tr>
							
			<tr>
				<td style="width: 2%; text-align: center;"><%=++count %></td>
				<td style="width: 2%" >Xerox Machine</td>
			    <td style="width: 2%;text-align: center;">
				<input type="number" class="form-control "   style="width:50%;margin-left:40px;"  value="<%=QtyList[22] %>"  min="0"  max="99" name="XeroxMachine"> 
				</td>
				<td>
				<select class="form-control" name="XeroxMachineIntendedBy" >
					                <option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF"  <%if(QtyList[23]!=null){ if(QtyList[23].toString().equals("SELF")){%> selected <%}} %>>Self</option>
									<option value="IT" <%if(QtyList[23]!=null){ if(QtyList[23].toString().equals("IT")){%> selected <%}} %>>IT</option>
				</select>
				</td>
				<td style="width: 2%">
				<input type="text" class="form-control" value="<%=QtyList[24] %>" name="XeroxMachineremarks" maxlength="500">
				</td>
				<td><button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button></td>
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
				<input type="text" class="form-control" value="<%=QtyList[27] %>" name="Miscellaneousremarks" maxlength="500">
				<td><button type="submit" class="btn btn-sm update-btn" name="Itinventoryid" value="<%=QtyList[0] %>"  formaction="QuantityEditSubmit.htm" formmethod="POST" onclick="return confirm('Are You Sure To Update ?');">update</button></td>
				
			</tr>
		
								
			</table>
			<%-- <div class="row" align="center"> 
			<div class="col-sm-12">
			
			 <button type="submit" class="btn btn-sm submit-btn"  name="inventoryid"   value="<%=QtyList[0]%>"  formaction="InventoryDetailsForward.htm"  formmethod="POST" onclick="return confirm('Are You Sure To Forward ?');">Forward</button>
		    <button type="submit" class="btn btn-sm preview-btn" name="" value=""  formaction="InventoryPreview.htm"  formmethod="POST"  >Preview</button> 
		
			</div>
			 </div>  --%>
		   <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
<%} %>
	</form>		
	 </div>
	 </div>
 <%} %>
	
		
		<!----------------------------------------------------------------------------------------------------------  -->
			
			<%if(InventoryQuantityList.size()==0) {%>
			<div class="card"  style="max-height: 40rem; overflow-y:auto;">
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
									<input type="number" class="form-control " style="width:50%;margin-left:30px;" value="0" min="0" max="99" name="Desktop">
									</td>
									<td ><select class="form-control" name="DesktopIntendedBy" >
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1"  style="display:block; " class="form-control"  name="DesktopRemarks"  maxlength="500"></textarea>
									</td>
									
								</tr>
							 
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >Laptop</td>
								    <td style="width: 2%">
									<input type="number" class="form-control"   style="width:50%;margin-left:30px;"  value="0" min="0"  max="99" name="Laptop"> 
									</td>
									<td><select class="form-control" name="LaptopIntendedBy" >
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="LaptopRemarks" maxlength="500" ></textarea>
									</td>
								</tr>
							    
							    <tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >USB Pendrive</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control"  style="width:50%;margin-left:30px;"  value="0" min="0"  max="99" name="USBPendrive" >
									</td>
									<td><select class="form-control" name="USBPendriveIntendedBy" >
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="USBPendriveRemarks"  maxlength="500"></textarea>
									</td>
								</tr>
									
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									
								    <td style="width: 2%" >Printer</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control"  style="width:50%;margin-left:30px;"  value="0" min="0"   max="99" name="Printer"> 
									</td>
									<td><select class="form-control" name="PrinterIntendedBy" >
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="PrinterRemarks"  maxlength="500"></textarea>
									</td>
								</tr>
									 
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									
								    <td style="width: 2%" >Telephone</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control "   style="width:50%;margin-left:30px;" value="0" min="0"   max="99" name="Telephone"> 
									</td>
									<td><select class="form-control" name="TelephoneIntendedBy" >
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="TelephoneRemarks"  maxlength="500"></textarea>
									</td>
								</tr>
								
								<tr>
									<td style="width: 2%; text-align: center;"><%=++count %></td>
									<td style="width: 2%" >Fax Machine</td>
								    <td style="width: 2%;text-align: center;">
									<input type="number" class="form-control"   style="width:50%;margin-left:30px;"  value="0" min="0"   max="99" name="FaxMachine"> 
									</td>
									<td><select class="form-control" name="FaxMachineIntendedBy" >
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
									<input type="number" class="form-control "  style="width:50%;margin-left:30px;"  value="0" min="0"   max="99" name="Scanner"> 
									</td>
									<td><select class="form-control" name="ScannerIntendedBy" >
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
									<input type="number" class="form-control "    style="width:50%;margin-left:30px;" value="0" min="0"   max="99" name="XeroxMachine"> 
									</td>
									<td><select class="form-control" name="XeroxMachineIntendedBy" >
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
									<input type="number" class="form-control "  style="width:50%;margin-left:30px;" value="0" min="0"   max="99" name="Miscellaneous"> 
									</td>
									<td><select class="form-control" name="MiscellaneousIntendedBy" >
									<option value="" selected="selected" disabled="disabled">Select</option>
									<option value="SELF">Self</option>
									<option value="IT">IT</option>
									</select>
									</td>
									<td style="width: 2%">
									 <textarea  rows="1" style="display:block; " class="form-control"  name="MiscellaneousRemarks"  maxlength="500"></textarea>
									</td>
								</tr>
							</table>
							
							<div class="row justify-content-center">
							 <button type="submit" style="margin-left:0px;" class="btn btn-sm submit-btn " name="action" value="submit" onclick="return confirm('Are You Sure To Add ?')" >Submit</button>
							 </div>
						   <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
				
				</form>		
		 </div>
		 </div>
	<%} %>
	<%-- <%}} %> --%>

		 
			
</body>

<script>

</script>
</html>
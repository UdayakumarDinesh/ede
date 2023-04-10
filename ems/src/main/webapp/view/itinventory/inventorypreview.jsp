<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%> 
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Inventory Details</title>
<style type="text/css">



table{
	align: left;
	width: 100% !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	margin-left:10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
	
}
tr.noBorder td {
  border: 0;
}

.center{

	text-align: center;
}

.right
{
	text-align: right;
}
.btn-history
{
	float: right;
	background-color: #FFD24C;
}
.text-blue
{
	color: blue;
	font-weight:400px;
}


 
 .float{
	position:fixed;
	width:50px;
	height:50px;
	bottom:40px;
	right:40px;
	color:#FFF;
	border-radius:50px;
	text-align:center;
	box-shadow: 2px 2px 3px #999;
}



</style>


</head>
<body>

<%	
    SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
    String LabLogo = (String)request.getAttribute("LabLogo");
    List<Object[]> inventoryquantity=( List<Object[]>)request.getAttribute("inventoryqty");
    List<Object[]> inventoryconfigure=(List<Object[]>)request.getAttribute("inventoryconfigure");
    String LoginType=(String)request.getAttribute("LoginType");

%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>Inventory Preview</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
							<%if(LoginType.toString().equals("U")){ %>
							<li class="breadcrumb-item "><a href="InventoryList.htm">Inventory </a></li>
							<%}else{ %>
							<li class="breadcrumb-item "><a href="InventoryDetailsForwarded.htm">Inventory Declared </a></li>
							<%} %>
					        <li class="breadcrumb-item active" aria-current="page">Inventory Preview
						</li>
				</ol>
			</div>

		</div>
	</div>
	


	 <div class="page card dashboard-card"> 
		<div class="card-body"  >
			<div class="card" style="padding-top:0px;margin-top: -15px;">
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"  align="center">
				<form action="##" id="forward">
				<div class="" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
				
								<div align="center">
								<div style="width: 100%;">
									<div style="width: 100%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
									<div ><h3 ><span style="margin-left: -80px;font-weight: 600; ">INFORMATION TECHNOLOGY(IT) DEPARTMENT</span></h3></div>
									<div ><span style="margin-left: -90px;font-weight: 600">INVENTORY DECLARATION FORM</span></div>
									<div ><span style="margin-left: -88px;">(To be submitted to IT Department)</span></div>
								</div>
								</div>
								<%for(Object[] inventoryqty:inventoryquantity ){ %>
								<%if(inventoryqty[32]!=null){ %>
								 <div><span style="border: 0px;margin-right:-900px;font-weight: 600">Date :&nbsp;<%=rdf.format(sdf.parse(inventoryqty[32].toString())) %> </span>
								</div>
								<%} %>
								 
								
								<table style="margin-top: 5px;">	
									<tbody>
										
										<tr>
											<th>Name</th>
											<th>Emp No</th>
											<th>Dept</th>
											<th>Designation</th>
										</tr>
										<tr>
										
											<td class="text-blue" style="text-transform: uppercase;"><%=inventoryqty[1]%></td>
											<td class="text-blue" ><%=inventoryqty[2]%></td>
											<td class="text-blue" ><%=inventoryqty[3]%></td>
											<td class="text-blue" ><%=inventoryqty[4]%></td>
										</tr>
										
									</tbody>
								</table>
								
						<div class="row">
								<div class="col-md-12" align="center">
								<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;">Inventory Details</span>
							</div>
						
							
				<table>
						<tr>
							<th style="width:5%;text-align:center;">SN</th>
							<th colspan="5" style="width:20%;" >Items</th>
							<th style="width:5%;text-align:center;">Qty</th>
							<th style="width:7%;">Intended By</th>
							<th style="width:50%;" >Remark</th>
										
						</tr>
						
						<%int count=0; %>
						<%if(!inventoryqty[5].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Desktop/Computer</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[5]%></td>
								    <td class="text-blue" ><%if(inventoryqty[6]!=null){%><%=inventoryqty[6]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[7].toString().equalsIgnoreCase("")){%><%=inventoryqty[7]%><%}  else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[8].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Laptop</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[8]%></td>
								    <td class="text-blue" ><%if(inventoryqty[9]!=null){%><%=inventoryqty[9]%><%} else{ %>-<%} %></td>
									<td class="text-blue"style="width: 2%"><%if(!inventoryqty[10].toString().equalsIgnoreCase("")){%><%=inventoryqty[10]%><%}  else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[11].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >USB Pendrive</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[11]%></td>
								    <td  class="text-blue" ><%if(inventoryqty[12]!=null){%><%=inventoryqty[12]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[13].toString().equalsIgnoreCase("")){%><%=inventoryqty[13]%><%}  else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[14].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Printer</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[14]%></td>
								    <td class="text-blue" ><%if(inventoryqty[15]!=null){%><%=inventoryqty[15]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[16].toString().equalsIgnoreCase("")){%><%=inventoryqty[16]%><%}  else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[17].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Telephone</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[17]%></td>
								    <td class="text-blue" ><%if(inventoryqty[18]!=null){%><%=inventoryqty[18]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[19].toString().equalsIgnoreCase("")){%><%=inventoryqty[19]%><%} else{ %>--<%} %></td>
						
						</tr>
						<%} %>
						<%if(!inventoryqty[20].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Fax Machine</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[20]%></td>
								    <td class="text-blue" ><%if(inventoryqty[21]!=null){%><%=inventoryqty[21]%><%}  else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[22].toString().equalsIgnoreCase("")){%><%=inventoryqty[22]%><%} else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[23].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Scanner</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[23]%></td>
								    <td class="text-blue" ><%if(inventoryqty[24]!=null){%><%=inventoryqty[24]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[25].toString().equalsIgnoreCase("")){%><%=inventoryqty[25]%><%} else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[26].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Xerox machine</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[26]%></td>
								    <td class="text-blue" ><%if(inventoryqty[27]!=null){%><%=inventoryqty[27]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[28].toString().equalsIgnoreCase("")){%><%=inventoryqty[28]%><%} else{ %>--<%} %></td>
						</tr>
						<%} %>
						<%if(!inventoryqty[29].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Miscellaneous </td>
									<td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[29]%></td>
									<td class="text-blue" ><%=inventoryqty[30]%></td>
								    <td class="text-blue" style="width: 2%"><%if(!inventoryqty[31].toString().equalsIgnoreCase("")){%><%=inventoryqty[31]%><%} else{ %>--<%} %></td>
						</tr>
						<%} %>
					</table>
					</div>
					       <div class="row">
							<%if(inventoryconfigure.size()>0) {%>
								<div class="col-md-12" align="center">
								<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;">Inventory Configuration </span>
								<%} %>
							</div>
							
								<table>
								
								<%  String LAN="LAN";
						            String Internet="INTERNET";
						            String StandAlone="STAND ALONE";
						            String Windows="Windows";
						            String Linux="Linux";
				                    String Computer="Desktop";
						            String Laptop="Laptop";
						            String Yes="YES";
						            String No="NO";
						         %>
						        
								<tr>
									 <%for(Object[] Config:inventoryconfigure) {%>
									<td colspan="2">
										<b >Connection Type :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;" ><%if(Config[2].toString().equals("L")){%><%=LAN %><%}else if(Config[2].toString().equals("I")){%><%=Internet %><%} else{%><%=StandAlone%><%} %></span></b>
									</td>
									<td colspan="3">
										<b>CPU :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;" ><%=Config[3] %></span></b>
								</tr>
								 
								<tr>
								
								  <td colspan="2" >
										<b>Monitor :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;" ><%=Config[4] %></span></b>
									</td>
									 <td colspan="2" >
										<b>Keyboard  :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;" ><%=Config[7] %></span></b>
									</td>
									  <td colspan="2" >
										<b>RAM :&nbsp;&nbsp;<span class="text-blue" style="text-transform: uppercase;font-weight: 400;" ><%=Config[5] %></span></b>
									</td>	 
								</tr>
								
								<tr>
								     <td colspan="0">
										<b>Additional RAM :&nbsp;&nbsp;<span class="text-blue" style="text-transform: uppercase;font-weight: 400;"><%=Config[6] %></span></b>
									</td> 
									 <td colspan="3" >
										<b>Mouse :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;"  ><%=Config[8] %></span></b>
									</td> 
									
									<td colspan="3">
										<b>External harddisk  :&nbsp;&nbsp;<span class="text-blue" style="text-transform: uppercase;font-weight: 400;"><%=Config[9] %></span></b>
									</td>
									
								</tr>
								<tr>
									 <td >
										<b>Extra Internal harddisk :&nbsp;&nbsp;<span class="text-blue" style="text-transform: uppercase;font-weight: 400;" ><%=Config[10] %></span></b>
									</td>
									<td colspan="3">
										<b >Office :&nbsp;&nbsp; <span class="text-blue" style="text-transform: uppercase;font-weight: 400;" ><%=Config[11] %></span></b>
										
									</td> 
									<td colspan="1">
										<b>OS:&nbsp;&nbsp;<span class="text-blue" style="text-transform: uppercase;font-weight: 400;" ><%if(Config[12].toString().equals("W")){%><%=Windows %><%}else{ %><%=Linux %><%} %></span></b>
									</td>
								</tr>
								
								<tr>
									 <td colspan="1">
										<b>Browser:&nbsp;&nbsp; <span class="text-blue" style="font-weight: 400;" ><%=Config[14] %></span></b>
										
									</td>
									<td  colspan="3">
										<b>PDF:&nbsp;&nbsp; <span class="text-blue" style="font-weight: 400;" ><%if(Config[13].toString().equals("Y")){ %><%=Yes%><%}else{ %><%=No %><%} %></span></b>
										 
									</td>
									<td  colspan="1">
										<b>Kavach:&nbsp;&nbsp; <span class="text-blue" style="font-weight: 400;"  ><%if(Config[15].toString().equals("Y")){ %><%=Yes%><%}else{ %><%=No %><%} %></span></b>
										 
									</td> 
									
								</tr>
								<tr class="noBorder"><td colspan="5">&nbsp;</td></tr>
								
								<%} %>	
						</table>
					</div>					
					 <%-- <div style="margin-left:600px;margin-top:0px;" >
								<span style="font-weight: 600; font-size: 18px;">Signature </span><br>
								<span  style="font-weight: 600; font-size: 18px; ">Name:&nbsp;<span class="text-blue" ><%=inventoryqty[1]%></span> </span><br>
								<span  style="font-weight: 600; font-size: 18px;">EmpNo:&nbsp;<span class="text-blue" ><%=inventoryqty[2]%></span> </span>
							</div>   --%>
						
				<%-- <div class="col-sm-12">
				<%if((inventoryqty[33].toString().equals("I") || inventoryqty[33].toString().equals("R")) && LoginType.toString().equals("U")){ %>
				 <button type="submit" class="btn btn-sm submit-btn"   name="inventoryid01"  id="ITinventoryid"   formaction="InventoryDetailsForward.htm" formmethod="get"  onclick="message(<%=inventoryqty[0]%>)">Forward</button>
				 <%} %> --%>
				 
				</div>
				<%if(LoginType.toString().equals("U") && inventoryqty[33].toString().equals("R")) {%>
				 <div class="col-md-5" align="center" style="margin: -1% 33% -7% -24%; padding:0px;border: 1px solid black;border-radius: 5px;">
				<table style="margin: 3px;padding: 0px">
					<tr>
						<td style="border:none;padding: 0px">
							<h6 style="text-decoration: underline;">Remarks :</h6> 
						</td>											
					</tr>
					<tr>
						<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
							<span style="border:none;" class="text-blue" ><%=inventoryqty[35] %></span>
						</td>
					</tr>
				</table>
				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />			
			</div> 
				<%} %>
				<%if(LoginType.toString().equals("A") && inventoryqty[33].toString().equals("F") && !inventoryqty[35].toString().equals("")) {%>
				<div class="col-md-5" align="center" style="margin: -1% 33% -7% -24%; padding:0px;border: 1px solid black;border-radius: 5px;">
				<table style="margin: 3px;padding: 0px">
					<tr>
						<td style="border:none;padding: 0px">
							<h6 style="text-decoration: underline;">Remarks :</h6> 
						</td>											
					</tr>
					<tr>
						<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
							<span style="border:none;" class="text-blue" ><%=inventoryqty[35] %></span>
						</td>
					</tr>
				</table>
				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />			
			</div> 
			<%} %>
			
				<%if(LoginType.toString().equals("A") && inventoryqty[33].toString().equals("F")) {%>
				<div class="col-md-6" align="center" style="margin-top: 3%;margin-left:40%;">
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100" id="remarks" name="remarks" maxlength="500"></textarea>
				  </div>
                 <button type="submit" class="btn btn-sm submit-btn"  name="inventoryid"  value="<%=inventoryqty[0]%>" formaction="InventoryDetailsApprove.htm"  formmethod="GET"  onclick="return confirm('Are You Sure To Approve ?');">Approve</button>
				<button type="submit" class="btn btn-sm delete-btn" name="inventoryid"  value="<%=inventoryqty[0]%>" formaction="InventoryDetailsReturn.htm"  formmethod="GET"  onclick="return remarkRequired('R');" formnovalidate="formnovalidate">Return</button>
				</div>
				<%} %>
				
				<%if((inventoryqty[33].toString().equals("I") || inventoryqty[33].toString().equals("R")) && LoginType.toString().equals("U")){ %>
				<div class="col-md-6" align="center" style="margin-top: 3%;margin-left:44%;">
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100"  name="Remarks" <% if(inventoryqty[33].toString().equals("R")){ %> required="required"<%} %> maxlength="500"></textarea>
				  </div>
                <button type="submit" class="btn btn-sm submit-btn"   name="inventoryid01"  id="ITinventoryid"   formaction="InventoryDetailsForward.htm" formmethod="get"  onclick="message(<%=inventoryqty[0]%>)">Forward</button>
				 </div>
				 <%} %>
				 <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
				</form>
			    </div>
			<%} %>
			
			</div>		
		</div>
	</div>
	

	
</body>

<script>
function message(inventoryid){
	
	console.log(inventoryid);
	 let text = "Are You Sure To Submit\nOnce submitted, data can't be changed";
	  if (confirm(text) == true) {
		  $("#ITinventoryid").val(inventoryid);
		  $('#forward').submit();
		 
	  }else {
		  event.preventDefault();
	      return false;
	  }
	  
}
function remarkRequired(action)
{
	if(action === 'R'){
		$('#remarks').attr('required', true);
		if($('#remarks').val().trim()===''){
			alert('Please Fill Remarks to Return! ');
			return false;
		}else{
				return confirm('Are You Sure To Return?');
		}
		
	}
}

</script>

</html>
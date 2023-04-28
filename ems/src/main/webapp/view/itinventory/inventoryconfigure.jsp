<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.itinventory.model.ITInventoryConfigured"%> 
<%-- <%@page import="com.vts.ems.itinventory.model.ITInventory"%>  --%>
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

  <%
  

       String Itinventoryid=(String)request.getAttribute("inventoryid");
       ITInventoryConfigured configure=(ITInventoryConfigured)request.getAttribute("configure");
       String ItemType=(String)request.getAttribute("itemtype");
       List<Object[]> inventoryconfig=(List<Object[]>)request.getAttribute("inventoryconfig");
  %>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-4">
			<%String Desktop="[Desktop]";
			String laptop="[Laptop]";
			%>
			
				<h5>Inventory Configuration  <%if(ItemType.toString().equals("D")){%><%=Desktop%><%}else{%><%=laptop%><%} %></h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="Inventory.htm">Inventory</a></li>
					<% if(configure!=null) {%>
					<li class="breadcrumb-item active " aria-current="page">Configuration Edit</li>
					<%} else{ %>
					<li class="breadcrumb-item active " aria-current="page">Configuration Add</li>
					<%} %>
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
	
	
	<div class=" page card dashboard-card">
   <!--  <div class="card-body" > -->
		<div class="card" >
			<div class="card-body" >
			<%if(configure!=null){%>
				<form action="InventoryConfigureEditSubmit.htm" method="post" autocomplete="off" >
				<%}else{ %>
				<form action="InventoryConfigureAddSubmit.htm" method="post" autocomplete="off" >
				<%} %>
				
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			         <div class="col-md-2">
			                <label>Connection Type</label><br>
			                 <select class=" form-control select2" name="ConnectionType" required="required">
			                  <option value="" selected="selected" disabled="disabled">Select</option>
								<option  value="L" <%if(configure!=null){ if(configure.getConnectionType().toString().equals("L")){%> selected  <% }}%>>LAN</option>
								<option value="I" <%if(configure!=null){ if(configure.getConnectionType().toString().equals("I")){%> selected  <% }}%>>INTERNET</option>
								<option value="S" <%if(configure!=null){ if(configure.getConnectionType().toString().equals("S")){%> selected  <% }}%>>STAND ALONE</option>
							</select>
			            </div>
			
			
			            <div class="col-md-4">
			                <label>CPU</label> 
			            	<input type="text" name="CPU" required="required"  maxlength="100" value="<%if(configure!=null){ %><%=configure.getCPU() %><%} %>" class="form-control input-sm"   placeholder="CPU" >
			            </div>			
			            	
			            <div class="col-md-2">
			                <label>Monitor </label>
			                <input type="text" id="" name="Monitor"   maxlength="100" value="<%if(configure!=null){ %><%=configure.getMonitor() %><%} %>"
			                    class=" form-control input-sm " placeholder="Monitor" required="required"
			                    >
			            </div>
			            <div class="col-md-2">
			                <label>RAM </label>
			                <input type="text" id="" name="RAM"   maxlength="100" value="<%if(configure!=null){ %><%=configure.getRAM()%><%} %>"
			                    class=" form-control input-sm " placeholder=" RAM " required="required"
			                     >
			            </div>
			             <div class="col-md-2">
			                <label>Additional RAM </label>
			                <input type="text" id="" name="AdditionalRAM"    maxlength="100" value="<%if(configure!=null){ %><%=configure.getAdditionalRAM()%><%} %>"
			                    class=" form-control input-sm " placeholder=" Additional RAM " required="required"
			                    >
			            </div>
				  </div>
			    </div>
			
			    <div class="form-group">
			        <div class="row">
			          <div class="col-md-2">
			                <label>Keyboard </label>
			                <input type="text" id="" name="Keyboard"   maxlength="100" value="<%if(configure!=null){ %><%=configure.getKeyboard()%><%} %>"
			                    class=" form-control input-sm " placeholder="Keyboard "  required="required"
			                    >
			            </div>
			            
			             <div class="col-md-2">
			                <label>Mouse </label>
			                <input type="text" id="" name="Mouse"    maxlength="100" value="<%if(configure!=null){ %><%=configure.getMouse()%><%} %>"
			                   class=" form-control input-sm " placeholder="Mouse "  required="required"
			                    >
			            </div>
			            <div class="col-md-2">
			                <label>External harddisk </label>
			                <input type="text" id="" name="Externalharddisk"   maxlength="100" value="<%if(configure!=null){ %><%=configure.getExternalharddisk()%><%} %>"
			                    class=" form-control input-sm " placeholder="External harddisk "  required="required"
			                   >
			            </div>
			            
			             <div class="col-md-2">
			                <label>Extra Internal harddisk </label>
			                <input type="text" id="" name="ExtraInternalhardisk"   maxlength="100"  value="<%if(configure!=null){ %><%=configure.getExtraInternalharddisk()%><%} %>"  required="required"
			                    class=" form-control input-sm " placeholder="External harddisk "  required="required"
			                    >
			            </div>
			            
			            <div class="col-md-2">
			                <label>Office </label>
			                <input type="text" id="" name="Office"    maxlength="100" value="<%if(configure!=null){ %><%=configure.getOffice()%><%} %>"
			                   class=" form-control input-sm " placeholder="Office "  required="required"
			                   >
			            </div>
			           <div class="col-md-2">
			                <label>OS </label>
			                <select name="OS" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="W" <%if(configure!=null){ if(configure.getOS().toString().equals("W")){%> selected  <% }}%>>Windows</option>
			                    <option value="L" <%if(configure!=null){ if(configure.getOS().toString().equals("L")){%> selected  <% }}%>>Linux</option>
			                </select>
			            </div>
			       </div>
			     </div>
			   
			    <div class="form-group">
			        <div class="row">
			            <div class="col-md-2">
			                <label class="text-nowrap">PDF</label>
			                <select name="PDF" class="form-control input-sm select2" required="required">
			                    <option value="N" <%if(configure!=null){ if(configure.getPDF().toString().equals("N")){%> selected  <% }}%>>No</option>
			                    <option value="Y" <%if(configure!=null){ if(configure.getPDF().toString().equals("Y")){%> selected  <% }}%>>Yes</option>
			                   
			                    
			                </select>
			            </div>
			
			             <div class="col-md-2">
			                <label>Browser</label>
			                <input type="text" name="Browser"    maxlength="100" value="<%if(configure!=null){ %><%=configure.getBrowser()%><%} %>"
			                    class=" form-control input-sm " placeholder="Browser" required="required"
			                    >
			              </div>
			              
			              <div class="col-md-2">
			                <label>Kavach</label>
			               <select name="Kavach" class="form-control input-sm select2"   required="required">
			                     <option value="N"  <%if(configure!=null){ if(configure.getKavach().toString().equals("N")){%> selected  <% }}%>>No</option>
			                    <option value="Y" <%if(configure!=null){ if(configure.getKavach().toString().equals("Y")){%> selected  <% }}%>>Yes</option>
			                   
			                </select>
			              </div>
			          </div>
			    </div> 
			    
			<div class="col-12" align="center">
			        <%if(configure!=null){%>
			    	<button type="submit" class="btn btn-sm update-btn"  name="action" value="submit" onclick="return confirm('Are You Sure To Update')">Update</button>
			    	<input type="hidden" name="InventoryConfigureId" value="<%=configure.getInventoryConfigureId()%>">
			    	<%}else{ %>
			    	<button type="submit" class="btn btn-sm submit-btn"  name="action" value="submit" onclick="return confirm('Are You Sure To Submit')">SUBMIT</button>
			    	 <input type="hidden" name="Itinventoryid"	value="<%=Itinventoryid%>" >
			    	<%} %>
			    	</div>
			    
			    <input type="hidden" name="ItemType"	value="<%=ItemType%>" >
			    
			 </form>
			</div>
		</div>
	<!-- </div> -->
	
	<div class="card-body main-card"  style="min-height: 0rem; overflow-y:auto;" >
      <form action="#" method="POST" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="table-responsive"      >
					<table
						class="table table-hover  table-striped table-condensed table-bordered table-fixed"
						id="myTable" style="width:100% ;" >
						<thead>
							<tr>
							    <th style="width: 2%">SN</th>
							    <th style="width: 4%">Item Type</th>
								<th style="width: 4%">Connection Type</th>
								<th style="width: 10%">CPU</th>
								<th style="width: 7%">OS</th>
								<th style="width: 10%">Action</th>
								
						   </tr>
						</thead>
						<tbody>
						 <%  String LAN="LAN";
						     String Internet="INTERNET";
						     String StandAlone="STAND ALONE";
						     String Windows="Windows";
						     String Linux="Linux";
				             String Computer="Desktop";
						     String Laptop="Laptop";
						  %>
						<% int count=0;
						for(Object[] Config:inventoryconfig){%>
							<tr>
							  
							    <td style="text-align: center;"><%=++count %></td>
							    <td style="text-align: left;"><%if(Config[1].toString().equals("D")){ %><%=Computer %><%} else  { %><%=Laptop %><%} %></td>
							    <td style="text-align: left;"><%if(Config[2].toString().equals("L")){%><%=LAN %><%}else if(Config[2].toString().equals("I")){%><%=Internet %><%} else{%><%=StandAlone%><%} %></td>
								<td style="text-align: left;"><%=Config[3] %></td>
								<td style="text-align: left;"><%if(Config[12].toString().equals("W")){%><%=Windows %><%} else{%><%=Linux %><%} %> </td>
								<td style="text-align: center;">
								<button type="submit" class="btn btn-sm " name="InventoryConfigureId" value="<%=Config[0] %>/<%=Config[1] %>"  formaction="InventoryConfigureEdit.htm" formmethod="POST" data-toggle="tooltip" data-placement="top" data-original-title="Edit">
								<i class="fa fa-edit" style="color: #333C83"></i>
								</button>
								
								 <input type="hidden" name="ItemType<%=Config[0]%>"	id="ItemType<%=Config[0]%>" value="<%if(Config[1].toString().equals("D")){ %><%=Computer  %><%} else { %><%=Laptop %><%} %>" >
								<input type="hidden" name="ConnectionType<%=Config[0]%>"  id="ConnectionType<%=Config[0]%>"   value="<%if(Config[2].toString().equals("L")){%><%=LAN %><%}else if(Config[2].toString().equals("I")){%><%=Internet %><%} else{%><%=StandAlone%><%} %>" >
								<input type="hidden" name="CPU<%=Config[0]%>"  id="CPU<%=Config[0]%>" value="<%=Config[3] %>">
								<input type="hidden" name="Monitor<%=Config[0]%>"  id="Monitor<%=Config[0]%>" value="<%=Config[4] %>">
								<input type="hidden" name="RAM<%=Config[0]%>"  id="RAM<%=Config[0]%>" value="<%=Config[5] %>">
								<input type="hidden" name="AdditionalRAM<%=Config[0]%>"  id="AdditionalRAM<%=Config[0]%>" value="<%=Config[6] %>">
								<input type="hidden" name="Keyboard<%=Config[0]%>"  id="Keyboard<%=Config[0]%>" value="<%=Config[7] %>">
								<input type="hidden" name="Mouse<%=Config[0]%>"  id="Mouse<%=Config[0]%>" value="<%=Config[8] %>">
								<input type="hidden" name="ExternalHarddisk<%=Config[0]%>"  id="ExternalHarddisk<%=Config[0]%>" value="<%=Config[9] %>">
								<input type="hidden" name="ExtraInternalHarddisk<%=Config[0]%>"  id="ExtraInternalHarddisk<%=Config[0]%>" value="<%=Config[10] %>">
								<input type="hidden" name="Office<%=Config[0]%>"  id="Office<%=Config[0]%>" value="<%=Config[11] %>">
								<input type="hidden" name="OS<%=Config[0]%>"  id="OS<%=Config[0]%>" value="<%if(Config[12].toString().equals("W")){%><%=Windows %><%} else{%><%=Linux %><%} %>">
								<input type="hidden" name="PDF<%=Config[0]%>"  id="PDF<%=Config[0]%>" value="<%=Config[13]%>">
								<input type="hidden" name="Browser<%=Config[0]%>"  id="Browser<%=Config[0]%>" value="<%=Config[14] %>">
								<input type="hidden" name="Kavach<%=Config[0]%>"  id="Kavach<%=Config[0]%>" value="<%=Config[15] %>">
								<button type="button" class="btn btn-sm " name="InventoryConfigureId" id="ConfigureId" value="<%=Config[0]%>"  onclick="openModal('<%=Config[0]%>')"  data-toggle="tooltip" title="" data-original-title="View Details">
								<i class="fa fa-eye " style="color: black;"></i>
							    </button> 
								
								</td>
							</tr>
							<%} %>
						</tbody>
					</table>
                       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</div>
          </form>
      </div>
      </div>


<div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="my-configure-modal">
		   <div class="modal-dialog modal-lg" role="document" style="width: 45% ;height: 60% ;">
			<div class="modal-content">
					<div class="modal-header" style="background-color:#0e6fb6;">
				<div class="row w-100">
					<div class="col-md-12" >
				    	<h3 class="modal-title" id="itemtype" style="color:#FFFFFF;text-align:center;"></h3>
				    </div>
				   </div>
				        <button type="button" class="close" style="color: #F9F9F9" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div> 
				<div class="modal-body" align="center" style="margin-top:-4px;background-color:#EEEEEE">
					<form action="#" method="post" autocomplete="off"  >
						<table style="width: 100%;">
							<tr>
								<th style="padding: 5px;" >Connection Type :</th>
								<td style="padding: 5px;" id="connectiontype"></td>
								<th style="padding: 5px;" >CPU :</th>
								<td style="padding: 5px;" id="cpu"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Monitor:</th>
								<td style="padding: 5px;text-transform: uppercase;" class="tabledata" id="monitor"></td>
								<th style="padding: 5px;width:23%;" >RAM  :</th>
								<td style="padding: 5px;text-transform: uppercase;" class="tabledata" id="ram"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Additional RAM :</th>
								<td style="padding: 5px;text-transform: uppercase;" class="tabledata" id="adram"></td>
								<th style="padding: 5px" >Keyboard :</th>
								<td style="padding: 5px;" class="tabledata" id="keyboard"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Mouse:</th>
								<td style="padding: 5px;" class="tabledata" id="mouse"></td>
								<th style="padding: 5px;width:20%;" >External harddisk :</th>
								<td style="padding: 5px;text-transform: uppercase;" class="tabledata" id="Extharddisk"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:28%;" >Extra Internal harddisk:</th>
								<td style="padding: 5px;text-transform: uppercase;" class="tabledata" id="ExtIharddisk"></td>
								<th style="padding: 5px;width:20%;" >Office:</th>
								<td style="padding: 5px;text-transform: uppercase;" class="tabledata" id="office"></td>
							</tr>
							
					        <tr>
								<th style="padding: 5px;width:17%;" >OS:</th>
								<td style="padding: 5px;text-transform: uppercase;" class="tabledata" id="os"></td>
								<th style="padding: 5px;width:20%;" >PDF:</th>
								<td style="padding: 5px;" class="tabledata" id="pdf"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Browser:</th>
								<td style="padding: 5px;" class="tabledata" id="browser"></td>
								<th style="padding: 5px;width:17%;" >Kavach:</th>
								<td style="padding: 5px;" class="tabledata" id="kavach"></td>
							</tr>
							
							
						</table>
						
					         <input type="hidden"  name="" id="" value=""> 
						     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
				</div>
				
			</div>
		</div>
	</div>
	
<script>
function openModal(ConfigureId){
	
	$("#ConfigureId").val(ConfigureId);
	$('#itemtype').html($('#ItemType'+ConfigureId).val())
	$('#connectiontype').html($('#ConnectionType'+ConfigureId).val())
	$('#cpu').html($('#CPU'+ConfigureId).val())
	$('#monitor').html($('#Monitor'+ConfigureId).val())
	$('#ram').html($('#RAM'+ConfigureId).val())
	$('#adram').html($('#AdditionalRAM'+ConfigureId).val())
	$('#keyboard').html($('#Keyboard'+ConfigureId).val())
	$('#mouse').html($('#Mouse'+ConfigureId).val())
	$('#Extharddisk').html($('#ExternalHarddisk'+ConfigureId).val())
	$('#ExtIharddisk').html($('#ExtraInternalHarddisk'+ConfigureId).val())
	$('#office').html($('#Office'+ConfigureId).val())
	$('#os').html($('#OS'+ConfigureId).val())
	$('#pdf').html($('#PDF'+ConfigureId).val())
	$('#browser').html($('#Browser'+ConfigureId).val())
	
	/*  var itemtype=$('#ItemType'+ConfigureId).val())
	if(itemtype=="D"){
		D="Desktop"
		$('#itemtype').html(D);
		
	}else{
		L="Laptop"
			$('#itemtype').html(L);
	}  */
	
	var k=$('#Kavach'+ConfigureId).val()
	if(k=="N"){
		 N="NO"
			   $('#kavach').html(N).css('color','#ff0000');
	}
	else{
		Y="YES"
			 $('#kavach').html(Y).css('color','blue');
	}
	
	var pdf=$('#PDF'+ConfigureId).val()
	if(pdf=="N"){
		 N="NO"
			 $('#pdf').html(N).css('color','#ff0000');
	}
	else{
		Y="YES"
			 $('#pdf').html(Y).css('color','blue');
	}
	
	$('#my-configure-modal').modal('toggle'); 
}



</script>


</body>
</html>
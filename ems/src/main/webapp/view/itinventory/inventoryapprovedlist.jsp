<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
     
     
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<body>
<%  
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	List<Object[]> InventoryApprovedList=(List<Object[]>)request.getAttribute("InventoryApprovedList");
	String DeclarationYear=(String)request.getAttribute("DeclarationYear");
	String LoginType=(String)request.getAttribute("LoginType");
	/* String Todate=(String)request.getAttribute("Todate");  */
   %>

 <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Inventory Approved List</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="ITInventoryDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active " aria-current="page">Approved List</li>
				</ol>
			</div>
		</div>
	</div>

	<div class="page card dashboard-card">
	<div class="card-body" >		
	<div align="center">
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
	
	<%if(LoginType.toString().equals("A")){ %>
	   <div class="card">
			<div class="card-header" style="height: 4rem">
		       <div class="container" style="margin-bottom: -9px !important">
							    <div class="row justify-content-end" >
							    	
							    	<form class="form-inline" action="InventoryDetailsApproved.htm" method="post" id="dateform" >
		
								    	<label style=" font-weight: 800;margin-top: 5px;margin-left: 5px"> Declaration Year : &nbsp; </label>
										<input  class="form-control date"  data-date-format="yyyy-mm-dd" id="datepicker1" name="DeclarationYear"  required="required"  style="width: 100px;">
									    <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
					    				
					    			</form>	
					    				
					    		</div>
					    		<br>
		    </div>
		  </div>
		</div>
		<%} %>
					    	
			
<div class="card" >
    <div class="card-body " >
      <form action="#" method="POST" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
				 <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable" style="width:100% ;" >
						<thead>
							<tr>
								<th style="width: 2%">SN</th>
								<th style="width: 4%">Declared By </th> 
								<th style="width: 3%">Declaration Year</th>
								<th style="width: 4%"> Date</th>
								<th style="width: 4%">No of Items</th> 
								<th style="width: 3%">Total Qty</th>
								<th style="width: 5%">Status</th>
								<th style="width: 5%">Remarks</th>
								<th style="width: 5%">Action</th>																		
							</tr>
						</thead>
						<tbody>
						 
					 <% int count=0;
						
						String Approved ="Approved";
						
						int total=0;
						int item=0;
						for(Object[] obj:InventoryApprovedList){
							total=(Integer.parseInt(obj[4].toString())+Integer.parseInt(obj[5].toString())+Integer.parseInt(obj[6].toString())+Integer.parseInt(obj[7].toString())
							+Integer.parseInt(obj[8].toString())+Integer.parseInt(obj[9].toString())+Integer.parseInt(obj[10].toString())+Integer.parseInt(obj[11].toString()) +Integer.parseInt(obj[12].toString()));
							
						if(Integer.parseInt(obj[4].toString())!=0){%><% item++; %><%}  if(Integer.parseInt(obj[5].toString())!=0){%><% item++; %><%} 
						if(Integer.parseInt(obj[6].toString())!=0){%><%item++; %><%}  if(Integer.parseInt(obj[7].toString())!=0){%><%item++; %><%}
						if(Integer.parseInt(obj[8].toString())!=0){%><%item++; %><%}   if(Integer.parseInt(obj[9].toString())!=0){%><%item++; %><%}
						if(Integer.parseInt(obj[10].toString())!=0){%><%item++; %><%}  if(Integer.parseInt(obj[11].toString())!=0){%><%item++; %><%}
						if(Integer.parseInt(obj[12].toString())!=0){%><%item++; %><%}
						String Remarks=obj[14].toString();
						%>
						
						 <tr>
							        <td style="width: 1%; text-align: center;"><%=++count %></td>
							        <td style="width: 1%; text-align: left;"><%=obj[1] %></td>
									<td style="width: 2%;text-align:center;" ><%=obj[2].toString().substring(0,4) %></td>
									<td style="width: 2%;text-align:center;"><%=rdf.format(sdf.parse(obj[3].toString())) %></td>
									<td style="width: 2%;text-align:center;"><%=item %></td>  
									<td style="width: 2%;text-align:center;"><%=total %></td>
									<td style="width: 2%;text-align:center;" ><%if(obj[13].toString().equals("A")){ %><%=Approved %><%}  %></td>
									<td style="width: 2%;text-align:left;word-wrap: break-word;word-break: break-all;white-space: normal !important;"><% if( !Remarks.equals("") && Remarks.length()<10){%><%=Remarks %><%} else if(Remarks.length()>10){ %><%=Remarks.substring(0,10) %> 
									<button type="button" class="editable-click" style="border-style:none;" name=""  id="InventoryId" value="<%=obj[0] %>" onclick="descmodal('<%=obj[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										</button><%} else {%>-<%} %>
									<input type="hidden" name="Remark<%=obj[0]%>"  id="Remark<%=obj[0]%>" value="<%=obj[14] %>">
									</td>
									
									<td style="width: 2%;text-align:center;">
									
									<button type="submit" class="btn btn-sm " name="inventoryid" value="<%=obj[0] %>" formaction="InventoryFormDownload.htm" formmethod="GET"  data-toggle="tooltip" title="" data-original-title="Downlaod">
									<i style="color: #019267" class="fa-solid fa-download"></i>
									</button> 
									</td>

							</tr>
							   <%item=0;}%>
						</tbody>
					</table>
					
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
          </form>
      </div>
   </div>
   			
</div>

</div>	

<div class="modal fade" id="descmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 75% !important;height: 40%;">
				<div class="modal-content" style="min-height: 80%;" >
				    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Remarks</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				    
					<div class="modal-body"  style="padding: 0.5rem !important;">
						<div class="card-body" style="min-height:30% ;max-height: 25% !important;">
							<div  class="row" id="descdata">
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
		

		
</body>
<script>

  $(document).ready(function(){
	 
	$("#datepicker1").datepicker({
    	
    	autoclose: true,
    	 format: 'yyyy',
    		 viewMode: "years", 
    		    minViewMode: "years"
    });
	
 <% if(DeclarationYear!=null){%>
  document.getElementById("datepicker1").value = <%=DeclarationYear %>
  <%} %>
    
    $('#datepicker1').change(function () {
    	
       $('#dateform').submit();
        
    }); 
  
});
  
  

</script>

<script>
function descmodal(InventoryId)
{
	        $("#InventoryId").val(InventoryId);
	        $('#descdata').html($('#Remark'+InventoryId).val())
			$('#descmodal').modal('toggle');
		
}


</script>

</html>
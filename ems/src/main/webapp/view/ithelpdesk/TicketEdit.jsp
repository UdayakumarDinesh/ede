<%@page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.text.SimpleDateFormat"%> 
<%@page import="java.time.LocalDate"%>
 <%@page import="com.vts.ems.ithelpdesk.model.HelpdeskTicket"%> 

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
				<h5>Ticket Edit</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
							<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
							<li class="breadcrumb-item "><a href="ITTicketList.htm">TicketList</a></li>
					<li class="breadcrumb-item active" aria-current="page">Ticket
						Edit</li>
				</ol>
			</div>

		</div>
		<%
		String ses=(String)request.getParameter("result"); 
		 	String ses1=(String)request.getParameter("resultfail");
			if(ses1!=null)
			{
		%>
		<div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1%>
			</div>
		</div>
		<%
		}if(ses!=null){
		%>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses%>
			</div>
		</div>
		<%
		}
		%>
		
		
		<%
						List<Object[]> category=(List<Object[]>)request.getAttribute("category"); 
								
								List<Object[]> subcategory=(List<Object[]>)request.getAttribute("subcategory"); 
								
								HelpdeskTicket desk=(HelpdeskTicket)request.getAttribute("desk");
								
								int val=desk.getTicketSubCategoryId();
						%>
		
		<div class="card" >
				<div class="card-body main-card " >
					<form action="TicketUpdate.htm" method="post" autocomplete="off" enctype="multipart/form-data" id="ticketsubmit">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="row">
						
							<div class="col-3" >
									<b>Category :</b><span class="mandatory"	style="color: red;">*</span>
							        <select class="form-control select2" style="margin:10px;" name="Category" onchange="GetCatgory()" required="required" id="Category" >
								<option></option>
								    <%for(Object[] catgry:category){%>
								      <option  value="<%=catgry[0]%>" <% if(catgry[0].toString().equalsIgnoreCase(String.valueOf(desk.getTicketCategoryId()))){%> selected="selected" <%} %>><%=catgry[1]%></option>
								      <%} %>
							       </select>
							</div>
								
								<div class="col-2">
									<b>Sub-Category :</b><span class="mandatory"	style="color: red;">*</span>	
							<select class="form-control select2" name="SubCategory" required="required" id="SubCategory" >
							</select>
							</div>
								<div class="col-3" style="margin-left:-2px;" >
								<b>Description : </b><span class="mandatory"	style="color: red;">*</span>
								<input type="text" class="form-control w-145" name="Description" value="<%=desk.getTicketDesc() %>"   id="Desc"  style="width:140%;">
							</div>
								<div class="col-2" style="margin-left:101px;">
									<b>File :</b> 
							<input type="file"  style="width: 150%" class="form-control input-sm "  value="<%=desk.getFilePath() %>"  name="FormFile" id="formFile"
							accept=".xlsx,.xls,.pdf,.doc,.docx ">
							
							<div class="col-md-2" >
							<%if(!desk.getFileName().toString().equals("")){ %>
							<button type="submit" formnovalidate="formnovalidate" class="btn btn-sm" style="margin-left:204px; margin-top:-65px;" 
									name="TICKETID" value="<%=desk.getTicketId() %>"
									 formaction="TicketFormDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
										  <i style="color: #019267" class="fa-solid fa-download fa-1x" ></i>
									</button>
									<%} %>
									</div>
								</div>
						</div>
						<br>
						<div class="row">
						</div>
						
						<div class="row justify-content-center">
						<input type="hidden" name="TicketId" value="<%=desk.getTicketId()%>">
						 <button type="submit" class="btn btn-sm submit-btn"    onclick="return confirm('Are You Sure To Update ?')" >UPDATE</button>				
						</div>
					</form>
				</div>
			</div>	
	</div>
</body>
<script>


$("#ticketsubmit").on('submit', function (e) {

	  var data =$('#Desc').val();
	  if(data.trim()==='' ){
		  alert("Please Enter Description!");
		  return false;
	  }else if(data.length>999){
		  alert("Description data is too long!");
		  return false;
	  }else{
		  return true;
	  }  
});  

$(function(){
    $("#formFile").on('change', function(event)
	{
    	
    	var file = $("#formFile").val();
    	console.log(file);
       	var upld = file.split('.').pop(); 
       	if(!(upld.toLowerCase().trim()==='pdf' || upld.toLowerCase().trim()==='xlsx' 
       				|| upld.toLowerCase().trim()==='xls' || upld.toLowerCase().trim()==='doc' || upld.toLowerCase().trim()==='docx')  )
       	{
    	    alert("Only PDF,Word and Excel documents are allowed to Upload")
    	    document.getElementById("formFile").value = "";
    	    return;
    	}
        
    });
});


$(document).ready(function() {
	GetCatgory()
});
	
     function GetCatgory() {
      var selectedValue = $("#Category").val();
      console.log(selectedValue);
      $.ajax({
        type: "GET",
        url: "GetSubCategoryListAjax.htm",
        data: { 
        	
        	selectedValue:selectedValue,
        },
        success: function(result) {
         var result1 = JSON.parse(result);
        console.log("sublist--"+result1);
        var values = Object.keys(result1).map(function(e) {
			return result1[e];
		});
        $('#SubCategory').html("");	
        
        var s = '';
       var v=<%=val%>
       
		for (i = 0; i < values.length; i++) 
		{
			var a=values[i][0];
			if(v===a)
		    {
				s += '<option value="'+values[i][0]+'" selected="selected">'+values[i][2]  + '</option>';
			}
			else{
			s += '<option value="'+values[i][0]+'">'+values[i][2]  + '</option>';
			}
		
		}
		$('#SubCategory').html(s);	
        
        }
      });
    }
</script>
</html>
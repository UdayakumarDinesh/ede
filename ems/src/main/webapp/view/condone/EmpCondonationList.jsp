<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
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

</style>
</head>
<body>

<div class="card-header page-top " >
		<div class="row">
			<div class="col-md-6">
				<h5>Condonation List</h5>
			</div>
				<div class="col-md-6 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item active " aria-current="page">Condonation List</li>
					</ol>
				</div>
			</div>
	</div>	

<% String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%>
	<div align="center">
		<div class="alert alert-danger" role="alert">
	    <%=ses1 %>
	    </div>
	</div>
	<%}if(ses!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
	    	<%=ses %>
		</div>
	</div>
	<%} %>
<%
List<Object[]> EmpCondoneList=(List<Object[]>)request.getAttribute("EmpCondoneList");

%>

<div class="page card dashboard-card" style="background: transparent;">
   <div class="card-body" >
		<%List<String> Statuses = List.of("INI", "SDG", "SCE"); %>
		<form action="#" method="POST" id="CondoneForm">
           	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="table-responsive" >
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:5%"></th>
					  <th style="width:10%">Type</th>
					  <th style="width:50%">Subject</th>
                       <th style="width:15%">Status</th>
                       <th style="width:20%">Status</th>
                  	</tr>
				</thead>
                 <tbody>
                       <%if (EmpCondoneList!=null)
                       { 
                    	   int SN=0;
                           for(Object[] condone:EmpCondoneList ){
                       %>
                        <tr>
                        	
							<td style="text-align: center;">
								<%if(Statuses.contains(condone[2].toString())){ %>
									<input type="radio" name="CondoneId" value="<%=condone[0]%>" >
								<%}else{ %>
									<input type="radio" name="CondoneId" value="0" disabled>
								<%} %>
							</td>
                            <td><%=condone[11]%></td>
                            <td ><%=condone[4] %></td>
                            <td><%=condone[10] %></td>
                            <td>
                            	<button type="submit" class="btn btn-sm preview-btn" name="CondoneId" value="<%=condone[0] %>" formaction="CondonePreview.htm" data-toggle="tooltip" data-placement="top" title="" data-original-title="Preview and Forward">
									<i class="fa-solid fa-forward" style="color: #084594"></i> Preview	
								</button>
                            </td>
                        </tr>
                       <%}} %>
                          
                 </tbody>
   
            </table>
            
          </div>
          
          	<div class="row" align="center">
            	<div class="col-md-12">
            		<input type="hidden" name="isApproval" value="N">
            		<button type="submit" class="btn btn-sm add-btn" formaction="CondoneAdd.htm"  formmethod="post" >ADD</button>
            		<button type="submit" class="btn btn-sm edit-btn" formaction="CondoneEdit.htm"  formmethod="post" Onclick="Edit()">EDIT</button>
            		<button type="submit" class="btn btn-sm delete-btn" formaction="CondoneDelete.htm" formmethod="post"Onclick="Delete()">DELETE</button>
            	</div>
			</div>
            
          
         </form>

   </div>
</div>


<script type="text/javascript">



function Edit()
{
	var fields = $("input[name='CondoneId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Notice ");
        event.preventDefault();
		return false;
	}
	return true;
	
}
	   	   
	   

function Delete(){ 
	
	var fields = $("input[name='CondoneId']").serializeArray();

	if (fields.length === 0){
		alert("Please Select Atleast One Form");
		event.preventDefault();
		return false;
	}
	
    if(confirm("Are You Sure To Delete!")){
		
		$("#CondoneForm").submit();
		return true;

	}else{
		
		event.preventDefault();
		return false;
	}
}

</script>



</body>
</html>
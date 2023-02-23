<%@page import="com.vts.ems.leave.model.LeaveRaSa"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" %>
<!DOCTYPE html >
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
.modal-dialog {
    max-width:750px;
    margin: 2rem auto;
}
.modal-body {
    margin: 0.5rem !important;
}
</style>
</head>
<body >
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>ASSIGN ADD-EDIT</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item "><a href="AssignReccSanc.htm">Assign Recc-Sanc</a></li>
						<li class="breadcrumb-item active " aria-current="page">Assign Add-Edit</li>
					</ol>
				</div>
			</div>
	</div>
<%
List<Object[]> AllEmployee=(List<Object[]>)request.getAttribute("AllEmployee");
List<Object[]> ReccSanc=(List<Object[]>)request.getAttribute("ReccSanc");
String empNo=(String)request.getAttribute("empNo");
%> 

<div class="page card dashboard-card">		 
   <div class="card-body" >          
    <div class="row">
   		
   		<div class="col-md-12">
   			<form action="assign-recc-sanc.htm" method="POST" >
			    <div class="row" style="margin-top: -4px;">
				    
				    <div class="col-md-6" style="margin-top: 4px;font-weight: bold;text-align: right;">
				            Employee :
				    </div>
				    <div class="col-md-4">
					    <select class="form-control  selectpicker" required="required" name="selecRadioForEmpid" title="Select Employee" data-live-search="ture" id="empNo">
					    <%for(Object[] emp:AllEmployee){ %>
					    <option value="<%=emp[0] %>" <%if(empNo.equals(emp[0])){ %>selected="selected" <%}%>><%=emp[1] %></option>
					    <%} %>
					    </select>
				    </div>
				    <div class="col-md-2">
				    <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
				    <button type="submit" name="ViewDetails" value="ViewDetails" class="btn btn-info btn-sm">Change</button>
				   	<input type="button" value="Modify" class="btn  btn-sm misc1-btn" onclick="assignRASA()" >
				   
				    </div>
				    </div>
				    </form>
				    
				    <div class="row" style="margin-top: 4px;">
				    
				    <div class="col-md-2" ></div>
				    <div class="col-md-8" >
				     <div class="table-responsive">

		              <table class="table table-bordered table-hover table-striped table-condensed"  > 
                               <thead>
                               <tr>
                               <th>Officer Type</th>
                               <th>Officer Name</th>
                               <th>Status</th>
                               </tr>
                               </thead>
                               <tbody>
                              <%if(ReccSanc!=null&&ReccSanc.size()>0){
                              for(Object[] ls:ReccSanc){%>
	                            <tr> 
	                             <td style='text-align:left;'><%=ls[2]%></td>
                                  <td style='text-align:left;'><%=ls[0]%>, <%=ls[1]%></td>
                                 <td style='text-align:center;'><%=ls[3]%></td>
                                </tr>   
                              <% } }else{%>
                              <tr>
                              <td colspan="3" align="center"> Not Assigned, Please  
                              <input type="button" value="Assign" class="btn  btn-sm btn-link" onclick="assignRASA()" >
                              </td>
                              </tr>
                            <%} %>
                            </tbody> 
                         </table>
                    </div>
                    </div>
				    <div class="col-md-2" ></div>
				    </div>
				    
				    </div>
				    </div>
  
   </div>
</div>   
				       
   <%LeaveRaSa ReccSancById=(LeaveRaSa)request.getAttribute("ReccSancById");
   List<Object[]> RaSaStatus=(List<Object[]>)request.getAttribute("RaSaStatus");
   %>         
   <div class="modal fade my-modal" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
 	  <div class="modal-dialog modal-dialog-centered" role="document">
 	    <div class="modal-content">
 	      <div class="modal-header">
 	        <h5 class="modal-title" id="exampleModalLongTitle">Assign Recommending and Sanctioning Officer </h5>
  	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
  	          <span aria-hidden="true">&times;</span>
  	        </button>
  	      </div>
  	      <div class="modal-body">
  	      
  	      	<form action="assign-recc-sanc.htm" method="POST">
  	      		<div class="row">
  	      		    <div class="col-md-1">
  	      		    </div>
  	      		    <div class="col-md-2">
  	      		    RA No:<br>
  	      		    <input class="form-control" type="number" name="RANO" <%if(ReccSancById!=null){ %> value="<%=ReccSancById.getRANO() %>" <%}else{ %> value="1" <%} %> required="required"  min="0"  max="3">
  	      		    </div>
  	      			<div class="col-md-4" >
  	      		    RA1 Name:<br>
  	      		   <select class="form-control  selectpicker" required="required" name="RA1" title="Select Employee" data-live-search="ture" >
  	      		       <option value="0000000" selected="selected">Not Required</option>
					    <%for(Object[] emp:AllEmployee){ %>
					    <option value="<%=emp[0] %>" <%if(ReccSancById!=null){ if(emp[0].equals(ReccSancById.getRA())){ %>selected="selected" <%}}%>><%=emp[1] %></option>
					    <%} %>
					    </select>
  	      		    </div>
  	      		    <div class="col-md-4">
  	      		    RA2 Name:<br>
  	      		    <select class="form-control  selectpicker" required="required" name="RA2" title="Select Employee" data-live-search="ture" >
					    <option value="0000000" selected="selected">Not Required</option>
					    <%for(Object[] emp:AllEmployee){ %>
					    <option value="<%=emp[0] %>" <%if(ReccSancById!=null){ if(emp[0].equals(ReccSancById.getRA2())){ %>selected="selected" <%}}%>><%=emp[1] %></option>
					    <%} %>
					    </select>
  	      		    </div>
  	      		     <div class="col-md-1">
  	      		    </div>
  	      		     <div class="col-md-1">
  	      		    </div>
  	      		     <div class="col-md-2">
  	      		    Leave status:<br>
  	      		    <select class="form-control  selectpicker" required="required" name="LeaveStatus" title="Leave Status" data-live-search="ture" >
					    <%for(Object[] obj:RaSaStatus){ %>
					    <option value="<%=obj[1] %>" <%if(ReccSancById!=null){ if(obj[1].toString().equals(ReccSancById.getLeave_Status())){ %>selected="selected" <%}}%>><%=obj[2] %></option>
					    <%} %>
					    </select>
  	      		    </div>
  	      		    <div class="col-md-4">
  	      		    RA3 Name:<br>
  	      		    <select class="form-control  selectpicker" required="required" name="RA3" title="Select Employee" data-live-search="ture" >
					   <option value="0000000" selected="selected">Not Required</option>
					    <%for(Object[] emp:AllEmployee){ %>
					    <option value="<%=emp[0]%>" <%if(ReccSancById!=null){ if(emp[0].equals(ReccSancById.getRA3())){ %>selected="selected" <%}}%>><%=emp[1] %></option>
					    <%} %>
					    </select>
  	      		    </div>
  	      		    <div class="col-md-4">
  	      		    SA Name:<br>
  	      		    <select class="form-control  selectpicker" required="required" name="SA" title="Select Employee" data-live-search="ture" >
					    <option value="0000000" selected="selected">Not Required</option>
					    <%for(Object[] emp:AllEmployee){ %>
					    <option value="<%=emp[0] %>" <%if(ReccSancById!=null){ if(emp[0].equals(ReccSancById.getSA())){ %>selected="selected" <%}}%>><%=emp[1] %></option>
					    <%} %>
					    </select>
  	      		    </div>
  	      		    </div>
  	      		    <div class="row">
  	      		    <div class="col-md-1">
  	      		    </div>
  	      		    <div class="col-md-2">
  	      		    TD Status:<br>
  	      		      	<select class="form-control  selectpicker" required="required" name="TdStatus" title="Td Status" data-live-search="ture" >
					    <%for(Object[] obj:RaSaStatus){ %>
					    <option value="<%=obj[1] %>" <%if(ReccSancById!=null){ if(obj[1].toString().equals(ReccSancById.getTD_STATUS())){ %>selected="selected" <%}}%>><%=obj[2] %></option>
					    <%} %>
					    </select>
  	      		    </div>
  	      			<div class="col-md-4">
  	      		    TD RA Name:<br>
  	      		    <select class="form-control  selectpicker" required="required" name="TDRA" title="Select Employee" data-live-search="ture" >
					    <option value="0000000" selected="selected">Not Required</option>
					    <%for(Object[] emp:AllEmployee){ %>
					    <option value="<%=emp[0] %>" <%if(ReccSancById!=null){ if(emp[0].equals(ReccSancById.getTD_RA())){ %>selected="selected" <%}}%>><%=emp[1] %></option>
					    <%} %>
					    </select>
  	      		    </div>
  	      		    <div class="col-md-4">
  	      		    TD SA Name:<br>
  	      		    <select class="form-control  selectpicker" required="required" name="TDSA" title="Select Employee" data-live-search="ture" >
					    <option value="0000000" selected="selected">Not Required</option>
					    <%for(Object[] emp:AllEmployee){ %>
					    <option value="<%=emp[0] %>" <%if(ReccSancById!=null){ if(emp[0].equals(ReccSancById.getTD_SA())){ %>selected="selected" <%}}%>><%=emp[1]%></option>
					    <%} %>
					    </select>
  	      		    </div>
  	      		    
  	      		    </div>
  	      		
  	      			
  	      		<br>
  	      		<div align="center">
  	      			<input type="submit" class="btn btn-primary btn-sm submit " name="add"  value="Assign"  onclick="return confirm('Are You Sure To Submit ?')" > 
  	      		</div>
  	      		<br>

                <input type="hidden" name="saRaId"  <%if(ReccSancById!=null){ %> value="<%=ReccSancById.getLeaveSaRaId()%>" <%}else{ %> value="0" <%} %> />
                <input type="hidden" name="selecRadioForEmpid" value="<%=empNo %>" />
  	      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  	      	</form>
  	      		
  	      		
  	      </div>
  	    </div>
  	  </div>
	</div>        
 <script type="text/javascript">
 function assignRASA(){
	 $('#exampleModalCenter').modal('toggle');
 }
 </script>
</body>
</html>
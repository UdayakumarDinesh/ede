<%@page import="com.vts.ems.master.model.DivisionGroup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.List,com.vts.ems.pis.model.Employee" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Division Group</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
  <%
  DivisionGroup divgrp =(DivisionGroup) request.getAttribute("divgrp");
  List<Object[]> list = (List<Object[]>)request.getAttribute("grpheadlist");
  %>
  
  <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(divgrp!=null){ %>
				<h5>Group Edit</h5>
				<%}else{ %>
				<h5>Group Add</h5>
				<%}%>
			</div>
			
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					    <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> 
						<li class="breadcrumb-item "><a href="DivisionGroup.htm">Group List </a></li>
						<%if(divgrp!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Group Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Group Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
		  <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body  " align="center" >
					<%if(divgrp!=null){ %>
					<form name="myfrm" action="DivisionGroupEdit.htm" method="POST" id="addfrm1" autocomplete="off"  enctype="multipart/form-data" >	
						<%}else{%>
					<form name="myfrm" action="DivisionGroupAdd.htm" method="POST" id="addfrm1" autocomplete="off"   >	
						<%}%>
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">								
										<tr>
											<th><label>Group Code<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" style="text-transform:uppercase"
												placeholder=" Enter Group Code" type="text" id="groupCode" name="groupCode" value="<%if(divgrp!=null){ %><%=divgrp.getGroupCode()%><%} %>"
												required="required" maxlength="3" style="font-size: 15px;"></td>
										</tr>
										<tr>
											<th><label>Group Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" style="text-transform:capitalize"
												placeholder=" Enter Group Name" type="text" name="groupName" value="<%if(divgrp!=null){%><%=divgrp.getGroupName()%> <%} %>"
												required="required" maxlength="255" style="font-size: 15px;" id="groupName"></td>
												
										</tr>
										<tr>
											<th><label>Group Head Name <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="groupHeadId" id="groupHeadId" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(list!=null&&list.size()>0){for(Object[] O:list){ %>
												<option value="<%=O[0]%>" <%if(divgrp!=null){if(divgrp.getGroupHeadId()==Long.parseLong(O[0].toString())){%> selected   <%}}%> > <%=O[1]%></option>
												<%}}%>
											    </select></td>
										</tr>	
									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(divgrp!=null){ %>
							<input type="hidden" id="divgrpid" name="divgrpid" value="<%=divgrp.getGroupId()%>">
								<button type="button" class="btn btn-sm submit-btn"
									onclick="return checkDuplicateEdit('addfrm1');"
									name="action"  value="EDIT">SUBMIT</button>
									<%}else{ %>
									<button type="button" class="btn btn-sm submit-btn"
									onclick="return checkDuplicate('addfrm1');"
									name="action"  value="ADD">SUBMIT</button>
									
									<%} %>
							</div>

						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
								
					<%if(divgrp!=null){ %>
					</form>
					<%}else{ %>
					</form>
					<%} %>
				</div>
	   </div>
	</div>

</div>

<script type="text/javascript">
function checkDuplicate(frmid)
{
	 var $groupCode1 = $("#groupCode").val();	
	
	 var count=true;
		$.ajax({
			type : "GET",
			url : "DivisionAddcheck.htm",	
			datatype : 'json',
			data : {
				groupCode : $groupCode1,				
			},
			success : function(result) {
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult[0]>0){
					alert('Group Code Already Exists');
					event.preventDefault();
					return false;
				}
				else if(count){
				var ret = confirm('Are you Sure To Submit ?') ;
				if(ret){
					var $groupCode1 = $("#groupCode").val();	
					if($groupCode1==null || $groupCode1==""||$groupCode1=="null"){
						alert('Enter Data Properly');
						
						return false;
					}else{
						$('#'+frmid).submit();
						return true;
					}	
					}else{
						
						return false;
					}
			}else{
				
				return false;
			}
			}
		});
		
} 

function checkDuplicateEdit(frmid)
{
	 var $groupCode1 = $("#groupCode").val();	
		
	 var count=true;
		$.ajax({
			type : "GET",
			url : "DivisionAddcheck.htm",	
			datatype : 'json',
			data : {
				groupCode : $groupCode1,				
			},
			success : function(result) {
				var ajaxresult = JSON.parse(result);
				
				
				if(ajaxresult[0]>1){
					alert('Group Code Already Exists');
					event.preventDefault();
					return false;
				}
				else if(count){
				var ret = confirm('Are you Sure To Submit ?') ;
				if(ret){
					var $groupCode1 = $("#groupCode").val();	
					if($groupCode1==null || $groupCode1==""||$groupCode1=="null"){
						alert('Enter Data Properly');
						
						return false;
					}else{
						$('#'+frmid).submit();
						return true;
					}	
					}else{
						
						return false;
					}
			}else{
				
				return false;
			}
			}
		});
		
} 


</script>

</body>
</html>
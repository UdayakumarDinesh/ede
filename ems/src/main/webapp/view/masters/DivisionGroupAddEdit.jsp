<%@page import="com.vts.ems.master.model.DivisionGroup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" java.util.List,com.vts.ems.pis.model.Employee" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Group</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
  <%
  DivisionGroup divgrp =(DivisionGroup) request.getAttribute("divgrp");
  List<Object[]> list = (List<Object[]>)request.getAttribute("grpheadlist");
  List<Object[]> deptlist = (List<Object[]>)request.getAttribute("Deparmentlist");
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
					<form name="myfrm" action="DivisionGroupEdit.htm" method="POST" id="addfrm1" autocomplete="off"  enctype="multipart/form-data">	
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
											<td><select class="form-control select2"  name="groupHeadId" id="groupHeadId" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(list!=null&&list.size()>0){for(Object[] emp:list){ %>
												<option value="<%=emp[2]%>" <%if(divgrp!=null){if(divgrp.getGroupHeadId().equalsIgnoreCase(emp[2].toString())){%> selected   <%}}%> > <%=emp[1]%></option>
												<%}}%>
											    </select></td>
										</tr>	
										<tr>
											<th><label>Department Name <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="DepartmentId" id="DepartmentId" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(list!=null&&list.size()>0){
													for(Object[] div:deptlist){ %>
												<option value="<%=div[0]%>" <%if(divgrp!=null){if(divgrp.getDivisionId()==Long.parseLong(div[0].toString())){%> selected   <%}}%> > <%=div[2]%></option>
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
									onclick="return checkDuplicateAdd('addfrm1');"
									name="action"  value="ADD">SUBMIT</button>
									
									<%} %>
							</div>

						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						<%if(list!=null){ %>
                <!--------------------------- container ------------------------->
			    <div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">The Reason For Edit</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					        <div class="modal-body">
					             <div class="form-inline">
					        	 <div class="form-group "  >
					               <label>File : &nbsp;&nbsp;&nbsp;</label> 
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile" > 
					      		 </div>
					      		 </div>
					        	
					        	<div class="form-inline">
					        	<div class="form-group w-100">
					               <label>Comments : &nbsp;&nbsp;&nbsp;</label> 
					              <textarea  class=" form-control w-100" maxlength="1000" style="text-transform:capitalize;"  id="comments"  name="comments" required="required" ></textarea> 
					      		</div>
					      		</div>
					      </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="EDITTEST" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
			<!----------------------------- container Close ---------------------------->	
						<%}%>				
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

$('#groupCode').keypress(function (e) {
    var regex = new RegExp("^[A-Za-z0-9]*$" );
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    else
    {
    e.preventDefault();
    alert('Please Enter Alphabates And Numbers');
    return false;
    }
});

function checkDuplicateAdd(frmid)
{
	 var $groupCode1 = $("#groupCode").val();	
	 var $groupName1 = $('#groupName').val().trim();
		var $groupHeadId1 =$('#groupHeadId'); 
	
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
				
				if(ajaxresult>0){
					alert('Group Code Already Exists');
					event.preventDefault();
					return false;
				}
				else if(count){
				var ret = confirm('Are you Sure To Submit ?') ;
				if(ret){
					
					if($groupCode1==null || $groupCode1==""||$groupCode1=="null" || $groupName1==null || $groupName1=="" || $groupName1=="null" || $groupHeadId1==null || $groupHeadId1==""|| $groupHeadId1=="null"){
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
	 var $groupId1 = $('#divgrpid').val();
	 var $groupName1 = $('#groupName').val().trim();
	 var $groupHeadId1 =$('#groupHeadId').val();	
	 var count=true;
		$.ajax({
			type : "GET",
			url : "DivisionEditcheck.htm",	
			datatype : 'json',
			data : {
				groupCode : $groupCode1,	
				groupId : $groupId1,
			},
			success : function(result) {
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult>0){
					alert('Group Code Already Exists');
					event.preventDefault();
					return false;
				}
				else if(count){
				var ret = confirm('Are you Sure To Submit ?') ;
				if(ret){
					
					if($groupCode1==null || $groupCode1==""||$groupCode1=="null"|| $groupName1==null || $groupName1=="" || $groupName1=="null" || $groupHeadId1==null || $groupHeadId1==""|| $groupHeadId1=="null"){
						alert('Enter Data Properly');
						
						return false;
					}else{						
						$('#myModal').modal('show');
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
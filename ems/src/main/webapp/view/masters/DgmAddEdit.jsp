
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.vts.ems.master.model.DgmMaster,java.util.List,com.vts.ems.pis.model.Employee"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DGM</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
  <%
  DgmMaster dgm =(DgmMaster) request.getAttribute("dgm");
  List<Object[]> list = (List<Object[]>)request.getAttribute("emplist");
  %>
  
  <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(dgm!=null){ %>
				<h5>DGM Edit</h5>
				<%}else{ %>
				<h5>DGM Add</h5>
				<%}%>
			</div>
			
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					    <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> 
						<li class="breadcrumb-item "><a href="Dgm.htm">DGM List </a></li>
						<%if(dgm!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">DGM Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">DGM Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
    <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body" align="center" >
					<%if(dgm!=null){ %>
					<form name="myfrm" action="DgmEdit.htm" method="POST" id="addfrm1" autocomplete="off" enctype="multipart/form-data">	
						<%}else{%>
					<form name="myfrm" action="DgmAdd.htm" method="POST" id="addfrm1" autocomplete="off">	
						<%}%>
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">								
										<tr>
											<th><label>DGM Code<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" style="text-transform:uppercase"
												placeholder="Enter DGM Code" type="text" id="dgmCode" name="dgmCode" value="<%if(dgm!=null){ %><%=dgm.getDGMCode()%><%} %>"
												required="required"  maxlength="3" style="font-size: 15px;"></td>
										</tr>
										<tr>
											<th><label>DGM Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" style=""
												placeholder=" Enter DGM Name" type="text" name="dgmName" value="<%if(dgm!=null){%><%=dgm.getDGMName()%> <%} %>"
												required="required" maxlength="255" style="font-size: 15px;" id="dgmName"></td>
												
										</tr>
										<tr>
											<th><label>DGM Employee <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="dgmEmpNo" id="dgmEmpNo" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option disabled="disabled" selected="selected" hidden="true" value="">--Select--</option>
												<%if(list!=null&&list.size()>0){for(Object[] O:list){ %>
												<option value="<%=O[2]%>" <%if(dgm!=null){if( dgm.getDGMEmpNo().equalsIgnoreCase(O[2].toString())  ){%> selected   <%}}%> > <%=O[1]%></option>
												<%}}%>
											    </select></td>
										</tr>	
									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(dgm!=null){ %>
							<input type="hidden" id="dgmId" name="dgmId" value="<%=dgm.getDGMId()%>">
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
						<%if(dgm!=null){ %>
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
					<%if(dgm!=null){ %>
					</form>
					<%}else{ %>
					</form>
					<%} %>
				</div>
	   </div>
	</div>

</div>

<script type="text/javascript">

/* $('#dgmCode').keypress(function (e) {
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
}); */


function checkDuplicateAdd(frmid)
{
	 var $dgmCode = $("#dgmCode").val();	
	 var $dgmName = $('#dgmName').val().trim();
		var $dgmEmpNo =$('#dgmEmpNo').val(); 
	
	 var count=true;
		$.ajax({
			type : "GET",
			url : "DgmCodeAddCheckDuplicate.htm",	
			datatype : 'json',
			data : {
				dgmCode : $dgmCode,				
			},
			success : function(result) {
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult>0){
					alert('DGM Code Already Exists');
					event.preventDefault();
					return false;
				}
				else if(count){
				var ret = confirm('Are you Sure To Submit ?') ;
				if(ret){
					
					if($dgmCode==null || $dgmCode==""||$dgmCode=="null" || $dgmName==null || $dgmName=="" || $dgmName=="null" || $dgmEmpNo==null || $dgmEmpNo==""|| $dgmEmpNo=="null"){
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
	 var $dgmCode = $("#dgmCode").val();	
	 var $dgmId = $('#dgmId').val();
	 var $dgmName = $('#dgmName').val().trim();
	 var $dgmEmpNo =$('#dgmEmpNo').val();	
	 var count=true;
		$.ajax({
			type : "GET",
			url : "DgmCodeEditCheckDuplicate.htm",	
			datatype : 'json',
			data : {
				dgmCode : $dgmCode,	
				dgmId : $dgmId,
			},
			success : function(result) {
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult>0){
					alert('DGM Code Already Exists');
					event.preventDefault();
					return false;
				}
				else if(count){
				var ret = confirm('Are you Sure To Submit ?') ;
				if(ret){
					
					if($dgmCode==null || $dgmCode==""||$dgmCode=="null"|| $dgmName==null || $dgmName=="" || $dgmName=="null" || $dgmEmpNo==null || $dgmEmpNo==""|| $dgmEmpNo=="null"){
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
<script type="text/javascript">
$(document).ready(function() {
	  // Prevent spaces in input field
	  $('#dgmCode').on('keypress', function(e) {
	    if (e.which === 32) {
	      return false;
	    }
	  });
	});

</script>
</body>
</html>
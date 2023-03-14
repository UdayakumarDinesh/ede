<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.vts.ems.ithelpdesk.model.HelpdeskSubCategory, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sub-Category</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
HelpdeskSubCategory category = (HelpdeskSubCategory) request.getAttribute("ticketsubcategory");
List<Object[]> list = (List<Object[]>)request.getAttribute("ticketCategoryList");
%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(category!=null){ %>
				<h5>Sub-Category Edit</h5>
				<%}else{ %>
				<h5>Sub-Category Add</h5>
				<%}%>
			</div>
			
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					    <li class="breadcrumb-item "><a href="ITDashboard.htm">IT Help Desk </a></li> 
						<li class="breadcrumb-item "><a href="TicketSubCategory.htm">Sub-Category List </a></li>
						<%if(category!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Sub-Category Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Sub-Category Add</li>
						<%} %>
					</ol>
			  </div>
		</div>
	</div>
  <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >
					<%if(category!=null){ %>
					<form name="myfrm" action="TicketSubCategoryEdit.htm" method="POST" id="addfrm1" autocomplete="off">	
						<%}else{%>
					<form name="myfrm" action="TicketSubCategoryAdd.htm" method="POST" id="addfrm1" autocomplete="off">	
						<%}%>
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">								
										 <tr>
											<th><label>Ticket Category <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="ticketCategory" id="ticketCategory" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(list!=null&&list.size()>0){for(Object[] Obj:list){ %>
												<option value="<%=Obj[0]%>" <%if(category!=null){if(category.getTicketCategoryId()==Long.parseLong(Obj[0].toString())){%> selected   <%}}%> > <%=Obj[1]%></option>
												<%}}%>
											    </select></td>
										</tr> 	
										<tr>
											<th><label>Ticket Sub Category<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Ticket Category" type="text" id="ticketSubCategory" name="ticketSubCategory" value="<%if(category!=null){ %><%=category.getTicketSubCategory()%><%} %>"
												required="required" maxlength="100" style="font-size: 15px;"></td>
										</tr>
										
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(category!=null){ %>
							<input type="hidden" id="TicketSubCategoryId" name="TicketSubCategoryId" value="<%=category.getTicketSubCategoryId()%>">
								<button type="button" class="btn btn-sm submit-btn"
									onclick="return checkDuplicateEdit('addfrm1');"
									name="action" value="EDIT">SUBMIT</button>
									<%}else{ %>
									<button type="button" class="btn btn-sm submit-btn"
									onclick="return checkDuplicateAdd('addfrm1');"
									name="action"  value="ADD">SUBMIT</button>
									
									<%} %>
							</div>

						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
								
					<%if(category!=null){ %>
					</form>
					<%}else{ %>
					</form>
					<%} %>
				</div>
	   </div>
	</div>

</div>
<script type="text/javascript">


function checkDuplicateAdd(frmid)
{
	 var $ticketSubCategory = $("#ticketSubCategory").val().trim();	
	 var $ticketCategoryId = $("#ticketCategory").val();
	
	 var count=true;
		$.ajax({
			type : 'GET',
			url : 'TicketSubCategoryAddcheck.htm',	
			datatype : 'json',
			data : {
				ticketSubCategory : $ticketSubCategory,
				ticketCategoryId : $ticketCategoryId, 
			},
			success : function(result) {
				var ajaxresult = JSON.parse(result);
				if(ajaxresult>0){
					alert('Sub-Category Already Exists');
					return false;
				}
				else if(count){
				var ret = confirm('Are you Sure To Submit ?') ;
				if(ret){
					
					
					if($ticketCategoryId==null || $ticketCategoryId==""||$ticketCategoryId=="null" ||$ticketSubCategory==null || $ticketSubCategory==""||$ticketSubCategory=="null"){
						alert('Enter Data Properly');
						event.preventDefault();
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
	 var $ticketSubCategory = $("#ticketSubCategory").val().trim();
	 var $ticketCategoryId = $("#ticketCategory").val();
	 var $ticketSubCategoryId =$('#TicketSubCategoryId').val();
	 var count=true;
		$.ajax({
			type : 'GET',
			url : 'TicketSubCategoryEditcheck.htm',	
			datatype : 'json',
			data : {
				ticketSubCategory : $ticketSubCategory,
				ticketCategoryId : $ticketCategoryId,
				ticketSubCategoryId:$ticketSubCategoryId,
			},
			success : function(result) {
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult>0){
					alert('Sub-Category Already Exists');
					event.preventDefault();
					return false;
				}
				else if(count){
				var ret = confirm('Are you Sure To Submit ?') ;
				if(ret){
					
					if($ticketCategoryId==null || $ticketCategoryId==""||$ticketCategoryId=="null"||$ticketSubCategory==null || $ticketSubCategory==""||$ticketSubCategory=="null"){
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
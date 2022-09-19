<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.util.List" %>
 <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

<%
List<Object[]> disciplinelist = (List<Object[]>)request.getAttribute("DisciplineList");

%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Discipline List </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item" ><a href="PIS.htm">PIS</a></li>
						<li class="breadcrumb-item active" aria-current="page">Discipline List</li>
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
		
	<div class="card">
		<div class="card-body">
			<form action="DisciplineListAddEditDelete.htm" method="post" id="empForm">
				<div align="right">
				</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
							 <thead> 
                                <tr>            
                                   <th>Discipline Name</th>
                                   <th>Action (Edit &amp; Delete) </th>
                                 </tr>
                              </thead>
                               <tbody>
                              <%for(Object[] ls:disciplinelist){%>
	                        <tr> 
								 <td ><%=ls[1]%></td>
                                 <td align="center">
                                 <input type="hidden" id="<%=ls[0]%>" value="<%=ls[1]%>">
	                                 <button type="button" class="btn btn-sm" name="EditDiscipline" value="<%=ls[0]%>" onclick="ShowModel('<%=ls[0]%>')"    data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa-solid fa-pen-to-square" style="color: #E45826"></i></button>
									 <button type="submit"  class="btn btn-sm" formnovalidate="formnovalidate" formaction="DisciplineListAddEditDelete.htm" formmethod="POST" Onclick="return confirm('Are You Sure To Delete?');" name="DeleteDiscipline" value="<%=ls[0]%>" data-toggle="tooltip" data-placement="top" title="Delete Bill"><i class="fa-solid fa-trash-can" style="color: red;"></i></button>
								 </td>
                            </tr>
                             <%}%>
                           </tbody> 
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
	
	                <div class="row text-center">
						<div class="col-md-12">
							<button type="button" class="btn btn-sm add-btn " name="Action" value="ADD" onclick="ShowAddModel()"   >ADD </button>
						</div> 
					</div>
					<!--------------------------- container ------------------------->
			<div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">Discipline Edit</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					        <div class="modal-body">
						        	<div class="form-inline">
						        	<div class="form-group "  >
						               <label>Discipline Name : &nbsp;&nbsp;&nbsp;</label> 
						               <input type="text" class=" form-control w-100" required="required"  id="editdiscipline" name="Discipline" > 
						      		</div>
						      		</div>
					        </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <input type="hidden" name="EditDisci" id="editdisci"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="button"  class="btn btn-sm submit-btn" name="action" value="ADD" onclick="checkData();" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!-----------------------------Edit container Close ---------------------------->
				</form>	
				
						<!---------------------------Add container ------------------------->
			<div class="container">
					<form action="AddDiscipline.htm" method="post">
				<!-- The Modal -->
				<div class="modal" id="myModal1">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">Discipline Add</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					        <div class="modal-body">
						        	<div class="form-inline">
						        	<div class="form-group "  >
						               <label>Discipline Name : &nbsp;&nbsp;&nbsp;</label> 
						               <input type="text" class=" form-control w-100" required="required"  id="discipline" name="Adddiscipline" > 
						      		</div>
						      		</div>
					        </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADD" onclick="return confirm('Are You Sure To Submit!');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					  </form>
					</div>
					<!----------------------------- Add container Close ---------------------------->
			</div>
		</div>		
	</div>
 </div>
</body>
<script type="text/javascript">
function ShowModel(val)
{
	var id=val;
	var discipline = $('#'+id).val();
	console.log(discipline);
	$('#editdiscipline').val(discipline);
	$('#editdisci').val(id);
	$('#myModal').modal('show');
}

function ShowAddModel() {
	$('#myModal1').modal('show');
}

 function checkData()
{
 var quali = $("#editdiscipline").val();
	if(quali!=null && quali!="" && quali!="null"){
		if(confirm("Are you sure to submit!")){
			$("form#empForm").submit();
			return true;
		}else{
			return false;
		}
		
	}else{
		alert('Enter the Discipline!');
		return false;
	}
	
} 
</script>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@page import="java.util.List"%>
       <%@page import="com.vts.ems.chss.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

<%
List<Object[]> main =(List<Object[]>)request.getAttribute("treatment");
CHSSMedicinesList list = (CHSSMedicinesList)request.getAttribute("medicinelist");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(list!=null){ %>
				<h5>CHSS Medicine Edit</h5>
				<%}else{ %>
				<h5>CHSS Medicine Add</h5>
				<%}%>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<!-- <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> -->
						<li class="breadcrumb-item "><a href="MedicineList.htm"> CHSS Medicine List </a></li>
						<%if(list!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Medicine Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Medicine Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
	
<div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body main-card " align="center" >
					<%if(list!=null){ %>
					<form name="myfrm" action="ChssMedicineEdit.htm" method="POST" id="addform" autocomplete="off" enctype="multipart/form-data" >
						<%}else{ %>
						<form name="myfrm" action="ChssMedicine.htm" method="POST" id="addform" autocomplete="off">
						<%} %>
						<div class="form-group">
							<div class="table-responsive">
								<table class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									
									
										<tr>
											<th><label>Treatment Name <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="tratementname" id="tratementname" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(main!=null&&main.size()>0){for(Object[] O:main){ %>
												
												<option value="<%=O[0]%>" <%if(list!=null){if(list.getTreatTypeId()==Long.parseLong(O[0].toString())){%> selected <%}}%>> <%=O[1]%></option>
												<%}}%>
											    </select></td>
										</tr>								
										<tr>
											<th><label>Medicine Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Medicine Name" type="text" id="MedicineName" name="MedicineName" value="<%if(list!=null){ %><%=list.getMedicineName()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px; text-transform:capitalize; "
												></td>
										</tr>
									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(list!=null){ %>
							<input type="hidden" name="medicineId" value="<%=list.getMedicineId()%>">
							<input type="hidden" name="Action" value="EDITMEDICINE">
								   <button type="button" class="btn btn-sm submit-btn" onclick ="return checkDuplicate1()" >SUBMIT</button>
									<%}else{ %>
									<input type="hidden" name="Action" value="ADDMEDICINE">
									<button type="button" class="btn btn-sm submit-btn" onclick ="return checkDuplicate()" >SUBMIT</button>
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
					        	 <div class="form-group"  >
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="Action" value="EDITMEDICINE" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>		
					        		       
					      </div>
					    </div>
					  </div>
					  
					</div>
		            <%}%>
					<!----------------------------- container Close ---------------------------->
			<%if(list!=null){%></form>
			<%}else{%></form><%}%>
				</div>
	   </div>
	</div>
</div>


<script type="text/javascript">
function checkDuplicate()
{
	var $name = $("#MedicineName").val();	
	var $treatid = $("#tratementname").val();
		$.ajax({
			type : "GET",
			url : "DuplicateMedicine.htm",	
			datatype : 'json',
			data : {
				MedicineName : $name,
				Treatmentid : $treatid
			},
			success :  function(result){
				 var rr=result;
                 var a = parseInt(rr) ;
				
				if(a > 0){					
					alert("Medicine Already Exist!");
					return false;
				}else if(confirm("Are you sure to Submit!")){
					var $treatmentname = $("#tratementname").val();
					var $name1 = $("#MedicineName").val();	
					if($treatmentname=="null" || $treatmentname==null ||  $name1=="" || $name1==" " ){
						alert("Enter Data Properly!");
						return false;
					}else{
						document.getElementById("addform").submit();
					}				
				}
			}
		});	
}



</script>
 
<script type="text/javascript">
function checkDuplicate1()
{
	var $name = $("#MedicineName").val();	
	var $treatid = $("#tratementname").val();

		$.ajax({
			type : "GET",
			url : "DuplicateMedicine.htm",	
			datatype : 'json',
			data : {
				MedicineName : $name,
				Treatmentid : $treatid
			},
			success :  function(result){
				 var rr=result;
                 var a = parseInt(rr) ;
				
				if(a >= 1){					
					alert("Medicine Already Exist!");
					return false;
				}else {
					var $treatmentname = $("#tratementname").val();
					var $name1 = $("#MedicineName").val();	
					if($treatmentname=="null" || $treatmentname==null ||  $name1=="" || $name1==" " ){
						alert("Enter Data Properly!");
						return false;
					}else{
						$('#myModal').modal('show');
						//document.getElementById("addform").submit();
					}
	
				}
			}
		});	
}
</script> 

</body>
</html>
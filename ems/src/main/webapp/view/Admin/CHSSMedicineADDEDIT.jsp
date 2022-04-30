<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@page import="java.util.List"%>
       <%@page import="com.vts.ems.chss.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CHSS Medicine </title>
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>

<%
List<Object[]> main =(List<Object[]>)request.getAttribute("treatment");
CHSSMedicineList list = (CHSSMedicineList)request.getAttribute("medicinelist");
%>

<div class="col page card">
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
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
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
	
	
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<form name="myfrm" action="ChssMedicine.htm" method="POST" id="addform">
						<div class="form-group">
							<div class="table-responsive">
								<table
									class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									<thead>
									
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
												required="required" maxlength="255" style="font-size: 15px;"
												></td>
										</tr>
									
								</thead>
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(list!=null){ %>
							<input type="hidden" name="medicineId" value="<%=list.getMedicineId()%>">
							<input type="hidden" name="Action" value="EDITMEDICINE">
								   <button type="button" class="btn btn-sm submit-btn" onclick ="return checkDuplicate()" >UPDATE</button>
									<%}else{ %>
									<input type="hidden" name="Action" value="ADDMEDICINE">
									<button type="button" class="btn btn-sm submit-btn" onclick ="return checkDuplicate()" >SUBMIT</button>
									<%} %>
							</div>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					</form>
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
<!-- 
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
				console.log(a);
				if(a > 1){					
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
</script> -->

</body>
</html>
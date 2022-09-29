<%@page import="com.vts.ems.master.model.CHSSDoctorRates"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.List"%>
 <%@page import="com.vts.ems.master.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CHSS</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
List<Object[]> treatment =(List<Object[]>)request.getAttribute("treatment");
CHSSDoctorRates list = (CHSSDoctorRates)request.getAttribute("DoctorRates");
%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(list!=null){ %>
				<h5>CHSS  Doctors Edit</h5>
				<%}else{ %>
				<h5>CHSS  Doctors Add</h5>
				<%}%>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<!-- <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> -->
						<li class="breadcrumb-item "><a href="DoctorsMaster.htm"> CHSS Doctors List </a></li>
						<%if(list!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page"> Doctors Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page"> Doctors Add</li>
						<%} %>
					</ol>
		        </div>
	      </div>
 </div>

	 <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body main-card  " align="center" >
					<%if(list!=null){ %>
					<form name="myfrm" action="DoctorsMasterEdit.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data" >
					<%}else{ %>	
					<form name="myfrm" action="DoctorsMasterAdd.htm" method="POST" id="myfrm1" autocomplete="off">
					<%}%>	
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									
										<tr>
											<th><label>Treatment <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2" name="Treatment" id="treatment" data-container="body" data-live-search="true" <%if(list!=null){%> disabled="disabled" <%}%>  style="font-size: 5px;" required>
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(treatment!=null&&treatment.size()>0){for(Object[] O:treatment){ %>
												<option value="<%=O[0]%>" <%if(list!=null){if(list.getTreatTypeId()==Long.parseLong(O[0].toString())){%> selected   <%}}%> > <%=O[1]%></option>
												<%}}%>
											</select></td>
										</tr>
										<tr>
											<th><label>Doctor Qualification <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" <%if(list!=null){%> readonly="readonly" <%}%>
												placeholder=" Enter Doctor Qualification" type="text" name="Qualification" <%if(list!=null){ %>value="<%=list.getDocQualification()%>"<%} else{%>onblur="return checkCode();"<%}%>
												required="required" maxlength="255" style="font-size: 15px; text-transform:capitalize;"
												id="qualification" ></td>
										</tr>
										
	                                     <tr>	
											<th><label> Consultation-1   <span class="mandatory" style="color: red;"> *</span>
											(In Rs)</label></th>
											<td><input class="form-control "
												placeholder="Enter Consultation-1" type="text" id="Consultation-1" name="ConsultationOne" value="<%if(list!=null){%><%=list.getConsultation_1()%><%}%>"
												required="required" maxlength="10" style="font-size: 15px;"
												 ></td>
										</tr>
									
										<tr>
											<th ><label> Consultation-2<span class="mandatory" style="color: red;"> *</span>
											(In Rs)</label> </th>
											<td><input class="form-control "
												placeholder="Enter Consultation-2" type="text" id="Consultation-2" name="ConsultationTwo" value="<%if(list!=null){%><%=list.getConsultation_2()%><%}%>"
												required="required" maxlength="10" style="font-size: 15px;"
												 ></td>
										</tr>
	
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(list!=null){ %>
							<input type="hidden" name="DocRateId" value="<%=list.getDocRateId()%>">
								<button type="submit" class="btn btn-sm submit-btn "
									onclick="return checkDuplicate1();"
									name="Action"  value="EDITDOCRATE">SUBMIT</button>
									<%}else{ %>
									<button type="submit" class="btn btn-sm submit-btn"
									onclick="return checkDuplicate();"
									name="Action"  value="ADDDOCRATE">SUBMIT</button>
									
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="Action" value="EDITDOCRATE" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
			<!----------------------------- container Close ---------------------------->	
						<%}%>
						<%if(list!=null){%></form><%}else{ %></form><%} %>
				  
				</div>
	   </div>
	</div>

</div>
<script type="text/javascript">
setPatternFilter($("#Consultation-1"), /^-?\d*$/);
setPatternFilter($("#Consultation-2"), /^-?\d*$/);
function setPatternFilter(obj, pattern) {
	  setInputFilter(obj, function(value) { return pattern.test(value); });
	}

function setInputFilter(obj, inputFilter) {
	  obj.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
	    if (inputFilter(this.value)) {
	      this.oldValue = this.value;
	      this.oldSelectionStart = this.selectionStart;
	      this.oldSelectionEnd = this.selectionEnd;
	    } else if (this.hasOwnProperty("oldValue")) {
	      this.value = this.oldValue;
	      this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	    }
	  });
	}
</script>

<script type="text/javascript">
function checkDuplicate()
{
	var $treatment = $("#treatment").val();	
	var $qualification =$("#qualification").val();
	var $Consultationone = $("#Consultation-1").val();
	var $Consultationtwo = $("#Consultation-2").val();
	

					if ($treatment =="null" ||  $treatment==null|| $treatment == "" || $treatment == " " ) {
						alert("Select Treatment!");
						return false;
					}else if($qualification=="null" || $qualification==null ||  $qualification== "" || $qualification== " " ){
						alert("Enter Doctor Qualification !");
						return false;
					}else  if ($Consultationone=="null" || $Consultationone==null || $Consultationone == "" || $Consultationone == " "){ 
						alert("Enter Consultation-1!");
						return false;
					}else if ($Consultationtwo=="null" || $Consultationtwo==null || $Consultationtwo == "" || $Consultationtwo == " " ) {
						alert("Enter Consultation-2 !");
						return false;
					}else{
						if(confirm("Are you sure to Submit!")){
							return true;
							document.getElementById("myfrm1").submit();
						}else{
							return  false;
							
						}
						
					}
		
		
}
</script>

<script type="text/javascript">

function checkCode()
{
	var treatment =$("#treatment").val();
	
	if(treatment!=null && treatment!=""&& treatment!="null"){
		var DocQuali = $("#qualification").val();	

		var retValue = false;
			$.ajax({
				type : "GET",
				url : "DuplicateDocQualification.htm",	
				datatype : 'json',
				data : {
					treatment : treatment,
					docqualifiaction: DocQuali
				},
				success :  function(result){
					
					 var rr=result;
	                 var a = parseInt(rr) ;				
					if(a >= 1){					
							alert("Qualification Already Exist For Selected Treatment Type!");
							$("#qualification").val('');
						    retValue = false;
						
					     }else {
							   retValue = true;					
						  }	
			    }  
		  });
	}else{
		alert("Select Treatment!");
		$("#qualification").val('');
	    retValue = false;
	}
	
		if(retValue==true){
			
			return true;
		}else{
			return retValue;
		}
}
</script>

<script type="text/javascript">

function checkDuplicate1()
{
	var $treatment = $("#treatment").val();	
	var $qualification =$("#qualification").val();
	var $Consultationone = $("#Consultation-1").val();
	var $Consultationtwo = $("#Consultation-2").val();
	
	
	
	if ($treatment =="null" ||  $treatment==null|| $treatment == "" || $treatment == " " ) {
		alert("Select Treatment!");
		return false;
	}else if($qualification=="null" || $qualification==null ||  $qualification== "" || $qualification== " " ){
		alert("Enter Doctor Qualification !");
		return false;
	}else  if ($Consultationone=="null" || $Consultationone==null || $Consultationone == "" || $Consultationone == " "){ 
		alert("Enter Consultation-1!");
		return false;
	}else if ($Consultationtwo=="null" || $Consultationtwo==null || $Consultationtwo == "" || $Consultationtwo == " " ) {
		alert("Enter Consultation-2 !");
		return false;
	}else {
	
		$('#myModal').modal('show');
		retValue = true;

	}
}






</script>
</body>
</html>
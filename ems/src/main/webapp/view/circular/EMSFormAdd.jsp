<%@page import="java.util.List"%>
<%@page import="com.vts.ems.circularorder.model.EMSDepCircular"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<%-- <jsp:include page="../static/sidebar.jsp"></jsp:include> --%>
</head>
<body>
<%	
	List<Object[]> DepTypeList = (List<Object[]>)request.getAttribute("DepTypeList");
	String DepTypeId = (String)request.getAttribute("DepTypeId");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Form Add</h5>
			</div>
				<div class="col-md-9">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<!-- <li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li> -->
						<li class="breadcrumb-item "><a href="EMSForms.htm"> Forms </a></li>
                        <li class="breadcrumb-item active " aria-current="page">Form Add</li>
					</ol>
			   </div>
		</div>
	</div>
		 
	<div class="page card dashboard-card">
	<div class="card-body" align="center">			
		<div class="card" style="width: 50%">
			<div class="card-body main-card" align="left" >
			
				<form  action="EMSFormAddSubmit.htm" method="POST" autocomplete="off" enctype="multipart/form-data" id="Add-Submit-Form">
				<div class="row" >
					<div class="col-md-6">
						<div class="form-group">						
							<label><b>Department</b><span class="mandatory"	style="color: red;">*</span></label>
							<select class="form-control select2" name="DepTypeId" id="DepTypeId" required="required">
								<option value="" disabled selected >Choose...</option>
								
									<%for(Object[] deptype : DepTypeList){ %>
										<option value="<%=deptype[0]%>"  <%if(DepTypeId.equalsIgnoreCase(deptype[0].toString())){ %>selected <%} %> ><%=deptype[2]%></option>
									<%} %>
								
							</select>
						</div>
					</div>
				</div>	
		
				<div class="row" >
					<div class="col-md-6">
						<div class="form-group">						
							<label><b>Form No.</b><span class="mandatory"	style="color: red;">*</span></label>
							<input type="text"  class="form-control input-sm " value=""  name="FormNo" id="FormNo" required="required" maxlength="100" > 
						</div>
					</div>
					
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>File</b> <span class="mandatory"	style="color: red;">*</span></label>
							<input type="file"  accept="application/pdf" style="width: 100%" class="form-control input-sm "  value="" id="formFile" name="FormFile" >
						</div>
					</div>
				</div>
					
				
					<div class="row">
					<div class="col-md-12" >
						<div class="form-group">
						<label><b>Description</b><span class="mandatory"	style="color: red;">*</span></label><br>
						  <textarea id="description" name="description" style="border-bottom-color: gray;width: 100%" required="required" maxlength="255" 
						  placeholder="Maximum 255 characters" ></textarea>
						</div>
					</div>
			
						
				</div>
					<div class="row" >
		    			<div class="col-md-12" align="center">
		    				<button type="button" class="btn btn-sm submit-btn"  onclick="return CheckFormNo();"  >SUBMIT</button>
		    			</div>
		    		</div> 
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					<input type="hidden" name="DepTypeId" value="<%=DepTypeId%>">		
				</form>
			</div>
	   </div>
	</div>
</div>

</body>
<script type="text/javascript">

$(function(){
    $("#formFile").on('change', function(event)
	{
    	
    	var file = $("#formFile").val();
    	console.log(file);
       	var upld = file.split('.').pop();  
       	if(!(upld.toLowerCase().trim()==='pdf' ))
       	{
    	    alert("Only PDF are allowed to Upload")
    	    document.getElementById("formFile").value = "";
    	    return;
    	}
        
    });
});
/* Add-Submit-Form */

function CheckFormNo()
{
	
	var $FormNo = $('#FormNo').val();
	if($FormNo.trim()==='')
	{
			alert('Please Fill All the Form inputs !');
	}
	else{
		$.ajax({
	
			type : "GET",
			url : "FormNoCheckAjax.htm",
			data :       
			{
				FormNo : $FormNo,
			},
			datatype : 'json',
			success : function(result) {
			var result = JSON.parse(result);
				
				if(result > 0)
				{
					alert('Form No Already Exists !!');	
				}else
				{
					var $DepTypeId= $('#DepTypeId').val();
					var $description= $('#description').val();
					var $formFile = $("#formFile").val();
					
					if($DepTypeId==null || $DepTypeId.trim()==='' || $description.trim()==='' || $formFile.trim()==='')
					{
						 alert('Please Fill All the Form inputs !');
					}
					else{
						if(confirm('Are You Sure to Submit ?'))
						{
							$('#Add-Submit-Form').submit();
						}
					}
					
				}
			
			}
		});
	}
	
}


</script>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
    <%@page import="com.ibm.icu.text.DecimalFormat"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>PassWord Change</title>

</head>
<body>

<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
DecimalFormat df=new DecimalFormat("0.00");
NFormatConvertion nfc=new NFormatConvertion();
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Password Change</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>			
					<li class="breadcrumb-item active " aria-current="page">Password Change</li>
				</ol>
			</div>	
		</div>
	</div>	
	

<%String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%>
	<div align="center">

		<div class="alert alert-danger" role="alert">
			<%=ses1 %>
		</div>
	
	<%}if(ses!=null){ %>

		<div class="alert alert-success" role="alert">
			<%=ses %>
		</div>
  </div>
	<%}%>
	
	
<div class="container" style="margin-top: 10px;">
	<div class="row" style="">

		<div class="col-md-2" ></div>
		<div class="col-md-8" >

 			<div class="card shadow-nohover" style="width: 800px;" >
				
				<div class="card-header" style=" background-color: #005C97;margin-top: ">
                    <b class="text-white">Password Change</b>
        		</div>
        
        		<div class="card-body" >
        			<form action="PasswordChanges.htm" method="POST" name="myfrm" id="myfrm">
        			
                		<div class="row justify-content-md-center">
                    		<div class="col-md-4 ">
                        		<div class="form-group">
                            		<label class="control-label">Old Password</label>
                              		 <input  class="form-control form-control"  type="password"  placeholder="Old Password " required name="OldPassword" >
                        		</div>
                    		</div>
                		</div>
                		
                		<div class="row justify-content-md-center">
                			<div class="col-md-4 ">
                        		<div class="form-group">
                            		<label class="control-label">New Password</label>
                              		 <input  class="form-control form-control"  type="password" placeholder="New Password" required  name="NewPassword" id="password" >
                        		</div>
                    		</div>	
                    	</div>
                    	
                    	<div class="row justify-content-md-center">
                			<div class="col-md-4 ">
                        		<div class="form-group">
                            		<label class="control-label">Confirm New Password</label>
                              		 <input  class="form-control form-control"  type="password" placeholder="Confirm New Password" required name="NewPassword" id="confirm_password" >
                        			<span id='message'></span>
                        		</div>
                    		</div>	
                    	</div>

				        <div class="form-group" align="center" >
					 		<input type="submit" class="btn btn-sm submit-btn" id="sub" value="SUBMIT" name="sub"  onclick="return AddPwd(myfrm)" > 							
						</div>
				
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
				 	</form>
        
     			</div>    
        
        	</div>
		</div>
		<div class="col-md-2" ></div>
	</div>
</div>	
	

<script type="text/javascript">

$('#password, #confirm_password').on('keyup', function () {
	
	  if ($('#password').val() == $('#confirm_password').val()) {
	    $('#message').html('Password Matched').css('color', 'green');
	  } else 
	    $('#message').html('Password Not Matching').css('color', 'red');
	  	  
	});
	

	


$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})


function Prints(myfrm){
	
	 var fields = $("input[name='btSelectItem']").serializeArray();

	 
	  if (fields.length === 0){
		  myalert();
	 event.preventDefault();
	return false;
	}
	 

		  return true;
	 
			
	}
	
function Add(myfrm){
	
	event.preventDefault();
	
	bootbox.confirm({ 
 		
	    size: "small",
			message: "<center></i>&nbsp;&nbsp;&nbsp;&nbsp;<b class='editbox'>Are You Sure ?</b></center>",
	    buttons: {
	        confirm: {
	            label: 'Yes',
	            className: 'btn-success'
	        },
	        cancel: {
	            label: 'No',
	            className: 'btn-danger'
	        }
	    },
	    callback: function(result){ 
	 
	    	if(result){
	    	
	    		$("sub").value;
	         $("#myfrm").submit(); 
	    	}
	    	else{
	    		event.preventDefault();
	    	}
	    } 
	}) 
	
	
}


function AddPwd(myfrm){
	
		if(confirm("Are You Sure To Submit?")){
			   $("#myfrm").submit(); 
		}else{
			return false;
		}
}
$(document).ready(function(){
	
	$("#table").DataTable({
		"pageLength": 5
	})
})



</script>
</body>
</html>
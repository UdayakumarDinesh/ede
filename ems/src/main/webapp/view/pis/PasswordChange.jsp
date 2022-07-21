<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
    <%@page import="com.ibm.icu.text.DecimalFormat"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
<!DOCTYPE html>
<html>
<head>
<%


String ForceResetPwd = (String)request.getAttribute("ForcePwd"); 
 
%>

<meta charset="ISO-8859-1">

     

<%if(ForceResetPwd!=null && ForceResetPwd.equals("N")){ %>
	<jsp:include page="../static/header.jsp"></jsp:include> 
<%}else{ %>
	<jsp:include page="../static/dependancy.jsp"></jsp:include>
	<title>Reset Password</title>
<%} %>


</head>
<body>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Password Change</h5>
			</div>
			<div class="col-md-9 ">
				<%if(ForceResetPwd!=null && ForceResetPwd.equals("N")){ %>
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active " aria-current="page">Password Change</li>
				</ol>
				<%} %>
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
					
					 <div class="row"> 
	                   	
	                		<div class="col-md-1"></div>
	                    		<div class="col-md-11">
								<%if(ForceResetPwd!=null && ForceResetPwd.equals("Y")){ %>
								<span style="font-weight: bold;color:#EB1D36;font-size:20px;">Note : </span> <span style="font-size:20px; ">Please reset Your default Password to continue.</span>
								<br><br>
								<%} %>
					</div></div>
					
					
		<form action="PasswordChanges.htm" method="POST" name="myfrm" id="myfrm">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						
	                   <div class="row"> 
	                   	
	                		<div class="col-md-1"></div>
	                    		<div class="col-md-3">
	                        		<div class="form-group">
	                              		<label class="control-label">Old Password</label><span class="mandatory">*</span>
                              		 	<input  class="form-control form-control"  type="password"  placeholder="Old Password " required name="OldPassword"  id="Oldpassword">
	                        		</div>
	                    		</div>
	         					
	                    		<div class="col-md-3">
	                        		<div class="form-group">
	                            		<label class="control-label">New Password</label><span class="mandatory">*</span>
                              		 	<input  class="form-control form-control"  type="password" placeholder="New Password" required  name="NewPassword" id="password"   >
	                        		</div>
	                    		</div>
	                    		
	                    		<div class="col-md-3">
	                        		<div class="form-group">
	                            		<label class="control-label">Confirm New Password</label><span class="mandatory">*</span>
	                              		 <input  class="form-control form-control"  type="password" placeholder="Confirm New Password" required name="NewPassword" id="confirm_password"   >
	                        			<span id='message'></span>
	                        		</div>
	                    		</div>
	                    		
	                    		       		
	                        </div> 
	                        
	                          <div  align="center"> 
	                          		<input type="hidden" name="type" value="AD"/>
	                                   <!--  <button class="btn btn-sm submit-btn" type="submit" name="Action" value="EDIT" style="margin-top: 2.15rem;">Submit</button>  -->
	                                    <input type="button" class="btn btn-sm submit-btn" id="sub" value="SUBMIT" name="sub"  onclick="return checkoldPassword()"  > 
	                          </div>
	                          
                        </form>      
	 	
	  	</div>
	
	
	
		   
	     </div>	
	
	

<script type="text/javascript">





function checkoldPassword()
{
	var Npwd  = $('#password').val();
	var cpwd = $('#confirm_password').val();
	var Opwd = $('#Oldpassword').val();
	
	
	
	if(Npwd!=null && Npwd!='' && cpwd!=null && cpwd!='' && Opwd!=null && Opwd!='')
	{
	
		$.ajax({
	
			type : "GET",
			url : "NewPasswordChangeCheck.htm",
			data : {
					
				oldpass :Opwd,
			},
			datatype : 'json',
			success : function(result) {
						
				
			if(result==='1')
			{
				
				if(!(cpwd===Npwd))
				{
					alert("New Password and Confirm Password Should be Same!");
					$('#message').html('Password Not Matching').css('color', 'red');
				}
				else if(Npwd === Opwd)
				{
					alert('Your New Password Should Not Be Same as Your Old Password');	
				}
				else if(Npwd==='123' || Npwd==='1234')
				{
					alert('Default or Weak Passwords are not Allowed');
				}
				else if(confirm("Are You Sure To Submit?"))
				{
					$('#myfrm').submit();
				}
				
			
			}
			else{
				alert('Please Enter Correct Old Password.');
			}
			
			
			
			}
		});
	}else{
		alert("Please Enter All Fields!");
		return false;
	}

}




$('#password, #confirm_password').on('keyup', function () {
	
	  if ($('#password').val() === $('#confirm_password').val()) {
	    $('#message').html('Password Matched').css('color', 'green');
	  } else 
	    $('#message').html('Password Not Matching').css('color', 'red');
	  	  
});

function AddPwd(myfrm)
{
		

		
			
				
			   
		
}

</script>
</body>
</html>
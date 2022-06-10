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
					<!-- <li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li> -->			
					<li class="breadcrumb-item active " aria-current="page">Password Change</li>
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
                              		 	<input  class="form-control form-control"  type="password" placeholder="New Password" required  name="NewPassword" id="password" >
	                        		</div>
	                    		</div>
	                    		
	                    		<div class="col-md-3">
	                        		<div class="form-group">
	                            		<label class="control-label">Confirm New Password</label><span class="mandatory">*</span>
	                              		 <input  class="form-control form-control"  type="password" placeholder="Confirm New Password" required name="NewPassword" id="confirm_password" >
	                        			<span id='message'></span>
	                        		</div>
	                    		</div>
	                    		
	                    		       		
	                        </div> 
	                        
	                          <div  align="center"> 
	                          		<input type="hidden" name="type" value="AD"/>
	                                   <!--  <button class="btn btn-sm submit-btn" type="submit" name="Action" value="EDIT" style="margin-top: 2.15rem;">Submit</button>  -->
	                                    <input type="submit" class="btn btn-sm submit-btn" id="sub" value="SUBMIT" name="sub"  onclick="return AddPwd(myfrm)" > 
	                          </div>
	                          
                        </form>      
	 	
	  	</div>
	
	
	
		   
	     </div>	
	
	

<script type="text/javascript">

$('#password, #confirm_password').on('keyup', function () {
	
	  if ($('#password').val() == $('#confirm_password').val()) {
	    $('#message').html('Password Matched').css('color', 'green');
	  } else 
	    $('#message').html('Password Not Matching').css('color', 'red');
	  	  
	});

function AddPwd(myfrm){
		var pwd  = $('#password').val();
		var cpwd = $('#confirm_password').val();
		var Opwd = $('#Oldpassword').val();

		if(confirm("Are You Sure To Submit?")){
			if(pwd!=null && pwd!='' && cpwd!=null && cpwd!='' && Opwd!=null && Opwd!=''){
				if(cpwd!=pwd){
					alert("New Password and Confirm Password Should be Same!");
					$('#message').html('Password Not Matching').css('color', 'red');
					return false;
				}else{
					$("#myfrm").submit(); 
				}
				
			}else{
				alert("Enter All Passowrd!");
				return false;
			}
			   
		}else{
			return false;
		}
}

</script>
</body>
</html>
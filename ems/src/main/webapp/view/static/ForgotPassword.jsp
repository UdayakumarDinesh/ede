<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reset Password</title>
<spring:url value="../webjars/jquery/3.4.0/jquery.min.js" var="jqueryminjs" />  
<script src="${jqueryminjs}"></script> 


<spring:url value="../webjars/bootstrap/4.0.0/js/bootstrap.bundle.min.js" var="BsBundlejs" />  
<script src="${BsBundlejs}"></script> 

<spring:url value="../webjars/bootstrap/4.0.0/css/bootstrap.min.css" var="loginPageCss1" />
<link href="${loginPageCss1}" rel="stylesheet" />

<spring:url value="/webresources/css/LoginPage.css" var="loginPageCss" />
<link href="${loginPageCss}" rel="stylesheet" />

<!-- ----------  fontawesome  ---------- -->
<spring:url value="/webresources/fontawesome/css/all.css" var="fontawesome" />     
<link href="${fontawesome}" rel="stylesheet" />

<!-- ----------  Master.css  ---------- -->
<spring:url value="/webresources/css/master.css" var="MasterCss" />     
<link href="${MasterCss}" rel="stylesheet" />


<!-- ----------  Master.css  ---------- -->
<spring:url value="/webresources/css/buttons.css" var="ButtonsCss" />     
<link href="${ButtonsCss}" rel="stylesheet" />


<style type="text/css">

*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
 }

.card-holder{
  width: 100%;
  min-height: 70vh;
}

.card{
  background: #0e6fb6;
  padding: 10px;  
  width: 550px;
  min-height: 100px;
  text-align: center;
  font-family: Arial;
  border-radius: 20px;
  box-shadow: 15px 15px 15px rgb(0 0 0 / 25%);
}
.card-holder{
  /* other base styles go here */
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>

</head>
<body>
	<div class="card-holder">
	  <div class="card">
	     <h3 style="color: #FFD36E;">Reset Password</h3>
	    <hr style="background-color: #ffffff">
	    <form action="#" method="post" >
		    <div align="left" style="padding: 10px;">
		    	<div id="otp-div">
			    	<span style="color: #ffffff;"><b>OTP  :</b>&nbsp;&nbsp;</span>
			    	<input type="text" class="form-control" style="width: 40%;display: inline;"  name="otp" id="otpvalue" placeholder="Enter OTP">&nbsp;&nbsp;&nbsp;
			    	<button class="btn btn-sm submit-btn" type="button" onclick="validateOtp();">Submit</button>
		    	</div>
		    	<div id="password-div" style="display: none;">	    		
			    	<h5 style="color: #ffffff">New Password :</h5>
			    	<input type="text" class="form-control" style="width: 80%;"  name="NewPassword" id="NewPassword" placeholder="Enter New Password" >
			    	<br>
			    	<h5 style="color: #ffffff">Re-New Password :</h5>
			    	<input type="text" class="form-control" style="width: 80%;"  name="ReNewPassword" id="ReNewPassword" placeholder="Re-Enter New Password" onkeyup ="validatePassword();" >
			    	<div id="err-msg" style="display: none;color: yellow; margin-top: 5px; "> Passwords do not match </div>
			    	
			    	<div align="center">
			    		<button type="button" class="btn btn-sm " id="update-btn" style="margin-top: 10px"  onclick="validatePassword();">Update</button>
			    	</div>
		    	</div>
		    	
		    </div>	  
	    </form> 
	  </div>
	</div>

<script type="text/javascript">


function validatePassword()
{
	$NewPassword = $('#NewPassword').val().trim();
	$ReNewPassword = $('#ReNewPassword').val().trim();
	
	if($NewPassword === $ReNewPassword)
	{
		$('#err-msg').hide();
		$('#update-btn').prop('disabled', false);
	}
	else 
	{
		/* alert('Please Re-Enter Same Password!'); */
		$('#err-msg').show();
		$('#update-btn').prop('disabled', true);
	}
}



var $genotp='d1';
function validateOtp()
{
	$userotp = $('#otpvalue').val().trim();
	
	if($genotp === $userotp)
	{
		$('#otp-div').hide();
		$('#password-div').show();
	}
	else if($userotp === "")
	{
		alert('Please Enter OTP sent to Your E-Mail.');
	}
	else 
	{
		alert('Please Enter Correct OTP !');
	}
	
}



</script>	

<script type="text/javascript">

/* 
document.onkeydown = function(e) {
	if(event.keyCode == 123) {
	return false;
	}
	if(e.ctrlKey && e.shiftKey && e.keyCode == 'I'.charCodeAt(0)){
	return false;
	}
	if(e.ctrlKey && e.shiftKey && e.keyCode == 'J'.charCodeAt(0)){
	return false;
	}
	if(e.ctrlKey && e.keyCode == 'U'.charCodeAt(0)){
	return false;
	}
	}

$(document).bind("contextmenu",function(e){
	  return false;
	    }); */
</script>
</body>
</html>
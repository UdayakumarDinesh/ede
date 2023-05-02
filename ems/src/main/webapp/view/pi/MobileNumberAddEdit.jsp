<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.List"%>
  <%@page import="com.vts.ems.pi.model.PisMobileNumber"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mobile Number</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
	<%
	PisMobileNumber mobile =(PisMobileNumber)request.getAttribute("mobile");
	%>
	
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
			<%if(mobile!=null){ %>
				<h5>Mobile Number Edit</h5><%}else{ %>
						<h5>Mobile Number Add</h5><%}%>
			</div>
			   <div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				        <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				        <li class="breadcrumb-item"><a href="PIHomeTownMobile.htm">HomeTown & Mobile</a> </li>
						<li class="breadcrumb-item active " aria-current="page">Mobile Number</li>
					</ol>
				</div>
		</div>
	</div>

<div class="page card dashboard-card"> 
	

	<div class="card-body">

		
		<div class="row">
		<div class="col-1"></div>
		<%if(mobile!=null){ %>
		<form action="MobileNumberEdit.htm" method="POST" autocomplete="off" id="myform">
		<%}else{%>
		<form action="MobileNumberAdd.htm" method="POST" autocomplete="off" id="myform">
		<%}%>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" style="width: 140%;" > 
		<div class="card-header">
		<h5>Fill Mobile Number Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				        
				       <div class="col-md-4">
                        <div class="form-group">
                         <label>Mobile Number<span class="mandatory">*</span></label>
                           <input id="MobileTextBox" type="text" value="<%if(mobile!=null && mobile.getMobileNumber()!=null){ %> <%=mobile.getMobileNumber() %> <%} %>" class="form-control input-sm " name="mobile"  maxlength="10"  placeholder="Enter Mobile No. " onblur="checknegative(this)" >  
                        </div>
                        </div>
                         
                        
                           <div class="col-md-4">
                           <div class="form-group">
                            <label> Alt Mobile No.</label>
                            <input id="AltMobileTextBox" type="text" value="<%if(mobile!=null && mobile.getAltMobileNumber()!=null){ %> <%=mobile.getAltMobileNumber() %> <%} %> " class="form-control input-sm " name="altMobile"  maxlength="10"   placeholder="Enter Alternate Mobile No." onblur="checknegative(this)"/>
                           </div>
                        </div>
		                
	                 <div class="col-md-3">
                        <div class="form-group">
                            <label>Mobile From<span class="mandatory">*</span></label>
                            
                            <input type="text"  value="<%if(mobile!=null && mobile.getMobileFrom()!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(mobile.getMobileFrom().toString())%> <%}%>" class="form-control input-sm " id="formRes" name="fromRes" required="required" placeholder="Enter Discipline" >   
                        </div>
                       </div>
		         
		                              
			          </div>
            
                
    		
		              </div>
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<%if(mobile!=null){ %>
							<input type="hidden" name="mobileNumberId" value="<%=mobile.getMobileNumberId() %>">
			                  <button type="submit" class="btn btn-sm submit-btn AddItem"	 name="Action" value="EDIT" onclick="return CommentsModel();">SUBMIT</button>
							<%}else{%>
				              <button type="submit" class="btn btn-sm submit-btn"	onclick="return CommentsModel();" name="Action" value="ADD">SUBMIT</button>
							<%}%>						
							 </div>
							</div>
						 </div>			 
						</div>								
			<%if(mobile!=null){ %>
			</form>
			<%}else{ %>
			</form>
			<%}%>	
			
		</div>
		</div>				
		</div>
</body>
<script type="text/javascript">
function CommentsModel()
{

	  var Mobile = $("#MobileTextBox").val();
	  var AltMobile = $("#AltMobileTextBox").val();
	  var formRes = $("#formRes").val();
  
	  if(confirm('Are You Sure to Submit?')){
	  
	  if(formRes==null || formRes=='' || formRes=="null" ){
			alert('Select from date!');
			return false;
		}else if(Mobile==null || Mobile=='' || Mobile=="null" ){
			alert('Enter the Mobile Number!');
			return false;
		}/*else if(AltMobile==null || AltMobile=='' || AltMobile=="null" ){
			alert('Enter the Alt Mobile Number!');
			return false;
		}else if(LandLine==null || LandLine=='' || LandLine=="null" ){
			alert('Enter the LandLine Number!');
			return false;
		} else{
			$('#myModal').modal('show');
		} */else{
			$('#myform').submit();
			return true;
		}
	  event.preventDefault;
	  }else{
		  return false;
	  }
}
</script>

<script type="text/javascript">
$('#formRes').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>
<script type="text/javascript">


setPatternFilter($("#MobileTextBox"), /^-?\d*$/);
setPatternFilter($("#AltMobileTextBox"), /^-?\d*$/);


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


function checknegative(str) {
    if (parseFloat(document.getElementById(str.id).value) < 0) {
        document.getElementById(str.id).value = "";
        document.getElementById(str.id).focus();
        alert('Negative Values Not allowed');
        return false;
    }
}
</script>
</html>
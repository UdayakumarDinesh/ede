<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.time.LocalDate"%>
    <%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Appointment Add</title>
</head>
<body>
<%
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> DesignationList = (List<Object[]>)request.getAttribute("DesignationList");
List<Object[]> Recruitment = (List<Object[]>)request.getAttribute("Recruitment");

%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Appointment Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)
						</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="AppointmentList.htm?empid=<%=empdata[2]%>">Appointment List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Appointment Add</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="AddAppointment.htm" method="POST" autocomplete="off" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" > 
		<div class="card-header">
		<h5>Fill Appointment Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				      <div class="col-md-4">
		                <div class="form-group">
		                    <label>Mode of Recruitment<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Recruitmentid" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                       <%if(Recruitment!=null && Recruitment.size()>0){for(Object[] obj :Recruitment){ %>
		                        <option value="<%=obj[0]%>"><%=obj[1]%></option>
		                        <%}} %>              
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-3">
		                 <div class="form-group">
                            <label>DRDO/OTHERS<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="drdo" required  data-live-search="true">
		                        <option value="DRDO">DRDO</option>
		                        <option value="OTHERS">OTHERS</option>              
		                    </select>
                        </div>
		                
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                    <label>Designation<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Designation" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                       <%if(DesignationList!=null && DesignationList.size()>0){for(Object[] obj :DesignationList){ %>
		                        <option value="<%=obj[0]%>"><%=obj[1]%></option>
		                        <%}} %>              
		                    </select>
		                </div>
		               </div> 
		                
		               
		       </div>	

                
                 <div class="row">
				       <div class="col-md-5">
		                <div class="form-group">
		                	<label>Org/Lab<span class="mandatory">*</span></label>
		                    <input  id="university" maxlength="255" type="text" name="OrgLab" class="form-control input-sm" required="required"  placeholder="Enter Org/Lab" > 
		                </div>
		              </div>
		              
		              <div class="col-md-3">
		                <div class="form-group">
		                	<label>From Date<span class="mandatory">*</span></label>
		                    <input type="text" class="form-control input-sm mydate" readonly="readonly"   id="fromdate" name="fromdate"  required="required"  > 
							                  
		                </div>
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                     <label>To Date<span class="mandatory">*</span></label>
		                   	 <input type="text" class="form-control input-sm mydate" readonly="readonly"   id="todate" name="todate"  required="required"  > 
							  
		                </div>
		               </div>           		        	       
				</div>	
             
                <div class="row">
                      <div class="col-md-5">
		                 <div class="form-group">
                            <label>Ceptam Cycle<span class="mandatory">*</span></label>
                            <input  id="CeptamCycle" maxlength="100" type="text" name="CeptamCycle" class="form-control input-sm" required="required"  placeholder="Enter Ceptam Cycle" > 
		                </div>
                        </div>
                      
                 <div class="col-3">
                        <div class="form-group">
                            <label>Vacancy Year<span class="mandatory">*</span></label>
                            <input class="form-control input-sm mydate" data-date-format="yyyy-mm-dd" id="datepicker1" name="vacancyyear"  required="required"  >
                        </div>
                 </div>
                
                <div class="col-3">
                        <div class="form-group">
		                	<label>Recruitment Year<span class="mandatory">*</span></label>
		                    <input class="form-control input-sm mydate" data-date-format="yyyy-mm-dd" id="datepicker2" name="recruitmentyear"  required="required"  >
		                </div>
                </div>
                 
                  
                </div>

						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<input type="hidden" name="empid" <%if(empdata!=null){%> value="<%= empdata[2]%>" <%}%>>
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
									name="action" value="submit">SUBMIT</button>
									</div>
							</div>
						</div>
						</div></div>
			</form>
			</div>
		</div>
		</div>
		</div>
		
		<script type="text/javascript">
		  $("#datepicker1").datepicker({
		    	
		    	autoclose: true,
		    	 format: 'yyyy',
		    		 viewMode: "years", 
		    		    minViewMode: "years"
		    });
		  document.getElementById("datepicker1").value =new Date().getFullYear()
		  	  $("#datepicker2").datepicker({
		    	
		    	autoclose: true,
		    	 format: 'yyyy',
		    		 viewMode: "years", 
		    		    minViewMode: "years"
		    });
		  document.getElementById("datepicker2").value =new Date().getFullYear()
		</script>

<script type="text/javascript">

setPatternFilter($("#cgpa"), /^[0-9]*(\.[0-9]{0,2})?$/);

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
$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :$("#fromdate").val(),  
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
</script>

</body>
</html>
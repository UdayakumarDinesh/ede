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
<title>Awards Add</title>
</head>
<body>
<%
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> pisawardslist = (List<Object[]>)request.getAttribute("PisAwardsList");


%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Awards Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="AwardsList.htm?empid=<%=empdata[2]%>">Awards List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Awards Add</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="AddAwards.htm" method="POST" autocomplete="off" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" > 
		<div class="card-header">
		<h5>Fill Awards Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				      <div class="col-md-4">
		                <div class="form-group">
		                    <label>Award Name<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Awardname" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                       <%if(pisawardslist!=null && pisawardslist.size()>0){for(Object[] obj :pisawardslist){ %>
		                        <option value="<%=obj[0]%>"><%=obj[1]%></option>
		                        <%}} %>              
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-2">
		                 <div class="form-group">
                            <label>Certificate<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="certificate" required  data-live-search="true">
		                        <option value="Y">YES</option>
		                        <option value="N">NO</option>              
		                    </select>
                        </div>
		                
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                    <label>Award From<span class="mandatory">*</span></label>
		                      <input  id="awardfrom"  type="text" name="AwardFrom" class="form-control input-sm" required="required"  placeholder="Enter Award From" > 
		                </div>
		               </div> 
		               
		                 <div class="col-md-2">
		                 <div class="form-group">
                            <label>Citation<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Citation" required  data-live-search="true">
		                        <option value="Y">YES</option>
		                        <option value="N">NO</option>              
		                    </select>
                        </div>
		              </div>
		               
		       </div>	

                
                 <div class="row">
				       <div class="col-md-4">
		                <div class="form-group">
		                	<label>Award Details<span class="mandatory">*</span></label>
		                    <input  id="awarddetails"  type="text" name="AwardDetails" class="form-control input-sm" required="required"  placeholder="Enter Award Details" > 
		                </div>
		              </div>
		              
		               <div class="col-md-2">
		                 <div class="form-group">
                            <label>Cash<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Cash" required  data-live-search="true">
		                        <option value="Y">YES</option>
		                        <option value="N">NO</option>              
		                    </select>
                        </div>
		              </div>
		              
		                <div class="col-md-4">
		                <div class="form-group">
		                    <label>Award Category<span class="mandatory">*</span></label>
		                    <select class="form-control input-sm select2" name="AwardCategory" required  data-live-search="true">
		                        <option value="Indivicual">Individual</option>
		                        <option value="Group">Group</option>       
		                        <option value="Institutional">Institutional</option>       
		                        <option value="Others">Others</option>              
		                    </select>
		                </div>
		               </div>         
		               
		                <div class="col-2">
                        <div class="form-group">
                            <label>Award Year<span class="mandatory">*</span></label>
                            <input class="form-control input-sm mydate" data-date-format="yyyy-mm-dd" id="datepicker1" name="AwardYear"  required="required"  >
                        </div>
                        </div>  		        	       
				</div>	
             
                <div class="row">
                      <div class="col-md-5">
		                 <div class="form-group">
                            <label>Cash Amount<span class="mandatory">*</span></label>
                            <input  id="cashamount" maxlength="6" type="text" name="CashAmount" class="form-control input-sm" required="required"  placeholder="Enter Cash Amount" > 
		                </div>
                        </div>
                      
                    <div class="col-md-2">
		                 <div class="form-group">
                            <label>Medallion<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Medallion" required  data-live-search="true">
		                        <option value="Y">YES</option>
		                        <option value="N">NO</option>              
		                    </select>
                        </div>
		              </div>
                 
                     <div class="col-md-3">
		                <div class="form-group">
		                	<label>Award Date<span class="mandatory">*</span></label>
		                    <input type="text" class="form-control input-sm mydate" readonly="readonly"   id="awarddate" name="AwardDate"  required="required">                   
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
		  	 
		</script>

<script type="text/javascript">
setPatternFilter($("#cashamount"), /^-?\d*$/);
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
$('#awarddate').daterangepicker({
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


</script>

</body>
</html>
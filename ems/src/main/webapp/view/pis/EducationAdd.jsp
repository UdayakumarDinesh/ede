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
<title>Education ADD</title>
</head>
<body>
<%
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> QualificationList = (List<Object[]>)request.getAttribute("QualificationList");
List<Object[]> DisciplineList = (List<Object[]>)request.getAttribute("DisciplineList");

%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Education Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)
						</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="EducationList.htm?empid=<%=empdata[2]%>">Education List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Education Add</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="AddEducation.htm" method="POST" autocomplete="off" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" > 
		<div class="card-header">
		<h5>Fill Qualification Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				      <div class="col-md-4">
		                <div class="form-group">
		                    <label>Qualification:<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Qualification" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                       <%if(QualificationList!=null && QualificationList.size()>0){for(Object[] obj :QualificationList){ %>
		                        <option value="<%=obj[0]%>"><%=obj[1]%></option>
		                        <%}} %>              
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-2">
		                 <div class="form-group">
                            <label>Sponsored:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Sponsored" required  data-live-search="true">
		                        <option value="Y">YES</option>
		                        <option value="N">NO</option>              
		                    </select>
                        </div>
		                
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                    <label>Discipline:<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Discipline" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                       <%if(DisciplineList!=null && DisciplineList.size()>0){for(Object[] obj :DisciplineList){ %>
		                        <option value="<%=obj[0]%>"><%=obj[1]%></option>
		                        <%}} %>              
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-2">
		                 <div class="form-group">
                            <label>HindiProf:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="HindiProf" required  data-live-search="true">
		                        <option value="Y">YES</option>
		                        <option value="N">NO</option>              
		                    </select>
                        </div>
		              </div>
		       </div>	

                
                 <div class="row">
				       <div class="col-md-4">
		                <div class="form-group">
		                	<label>University:<span class="mandatory">*</span></label>
		                    <input  id="university" maxlength="255" type="text" name="University" class="form-control input-sm" required="required"  placeholder="Enter University" > 
		                </div>
		              </div>
		              
		              <div class="col-md-3">
		                <div class="form-group">
		                	<label>Division:<span class="mandatory">*</span></label>
		                    <input  id="division"  type="text" name="Division" class="form-control input-sm" required="required"  maxlength="25" placeholder="Enter Division" >
		                </div>
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                     <label>Specialization:<span class="mandatory">*</span></label>
		                    <input  id="specialization" maxlength="255"  type="text" name="Specialization" class="form-control input-sm"    required="required"  placeholder="Enter Specialization" onclick="checkLength()"> 
		                </div>
		               </div> 
		                 <div class="col-md-2">
		                 <div class="form-group">
                            <label>Honours:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Honours" required  data-live-search="true">
		                        <option value="Y">YES</option>
		                        <option value="N">NO</option>              
		                    </select>
                        </div>
		              </div>
		                 		        	       
				</div>	
             
                <div class="row">
                      <div class="col-md-3">
		                 <div class="form-group">
                            <label>Acquired:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="Acquired" required  data-live-search="true">
		                        <option value="B">Before Join</option>
		                        <option value="A">After Join</option>              
		                    </select>
                        </div>
                        </div>
                      
                 <div class="col-3">
                        <div class="form-group">
                            <label>Year Of Passing<span class="mandatory">*</span></label>
                            <input class="form-control input-sm mydate" data-date-format="yyyy-mm-dd" id="datepicker1" name="yearofpassing"  required="required"  >
                        </div>
                 </div>
                
                <div class="col-3">
                        <div class="form-group">
		                	<label>CGPA:<span class="mandatory">*</span></label>
		                    <input  id="cgpa"  maxlength="3.2" type="text" name="CGPA" class="form-control input-sm" required="required"  placeholder="Enter Division" >
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

</script>

</body>
</html>
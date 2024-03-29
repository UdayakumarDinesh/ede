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
</head>
<body>
<%
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> RelativeList = (List<Object[]>)request.getAttribute("FamilyRelation");
List<Object[]> StatusList = (List<Object[]>)request.getAttribute("FamilyStatus");

%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Family Members Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)
						</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="FamilyMembersList.htm?empid=<%=empdata[2]%>">Family Members List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Family Add</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="AddFamilyDetails.htm" method="POST" autocomplete="off" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" > 
		<div class="card-header">
		<h5>Fill Family Member Details</h5>
		</div>
			<div class="card-body"  >
			  <!-- NAME & Date Of Birth -->
              <div class="row">
				      <div class="col-md-8">
		                <div class="form-group">
		                    <label>Name:<span class="mandatory">*</span></label>
		                    <input type="text"  id="NameTextBox"  class="form-control input-sm" style="text-transform:capitalize"  maxlength="100" name="memberName" required="required" placeholder="Enter name"  onclick="return trim(this)" onchange="return trim(this)">
		                </div>
		               </div> 
		                
		                <div class="col-md-4">
		                 <div class="form-group">
                            <label>Date Of Birth:<span class="mandatory">*</span></label>
                            <input type="text" class="form-control input-sm mydate" readonly="readonly" name="dob"required="required">
                        </div>
		                
		              </div>
		       </div>	
                <!-- / .NAME & Date Of Birth -->
                
                
                
                 
                <!-- RELATIONSHIP & BENID-->
                
                 <div class="row">
				       <div class="col-md-5">
		                <div class="form-group">
		                	<label>Relation:<span class="mandatory">*</span></label>
		                    <select class="form-control input-sm select2" name="relation" required  data-live-search="true">
		                      <option value="" disabled="disabled">Select Relation</option>
		                        <% for(Object[] relativeLs:RelativeList){ %> 
		                        <option value="<%=relativeLs[0]%>"><%=relativeLs[1]%></option>
		                        <%} %> 
		                                     
		                    </select>
		                </div>
		              </div>
		              
		              <div class="col-md-3">
		                <div class="form-group">
		                	<label>Gender:<span class="mandatory">*</span></label>
		                    <select class="form-control input-sm select2" name="Gender" required  data-live-search="true">
		                      <option value="" disabled="disabled">Select Gender</option>
		                      
		                        <option value="M">Male</option>
		                       <option value="F">Female</option>
		                                     
		                    </select>
		                </div>
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                     <label>Ben Id:<span class="mandatory">*</span></label>
		                    <input  id="BenidTextBox"  type="text" name="benId" class="form-control input-sm"   maxlength="9" required="required"  placeholder="Enter BenID" onclick="checkLength()"> 
		                </div>
		               </div> 
		                 		        	       
				</div>	
             
                <div class="row">
                  <div class="col-4">
                         <div class="form-group">
                          <label>Status<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="status" required="required">
                              <!-- <option value="none" selected disabled hidden>Select Status</option> -->
                              
                               <% for(Object[] statusLs:StatusList){ %> 
		                        <option value="<%=statusLs[0]%>"><%=statusLs[1]%></option>
		                        <%} %>
                            </select>
                            </div>  
                        </div>
                        
                      
                        <div class="col-4">
                         <div class="form-group">
                          <label>Status From<span class="mandatory">*</span></label>
                            <input type="text" class="form-control input-sm mydate" name="statusDate" readonly="readonly" required="required" placeholder="Enter Date" />
                         </div>
                        </div>
                   
                <div class="col-2">
                        <div class="form-group">
                            <label>BG:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm " name="bloodgroup" required="required" style="width:70px; height: 30px;" >
                                
                                <option value="A-">A-</option>
                                <option value="A+">A+</option>
                                <option value="B-">B-</option>
                                <option value="B+">B+</option>
                                <option value="AB-">AB-</option>
                               <option value="AB+">AB+</option>
                               <option value="O-">O-</option>
                               <option value="O+">O+</option>
                               <option value="Not Available">Not Available</option>
                            </select>
                        </div>
                    </div>
                
                
                <div class="col-2">
                       <div class="form-group">
		                	<label>PH:<span class="mandatory">*</span></label>
		                    <select name="PH" class="form-control input-sm" style="width: 70px;height: 30px;">
		                    <option value="N">No</option>
		                    <option value="Y">Yes</option>
		                    </select>
		                 </div>
                    </div>
                 
                  
                </div>
               <!-- //Status,Status From,BG,PH -->
                
                
                
               <!-- dependency ,Employed,Married-->
					<div class="row">
		                <!-- Medical -->
		                <div class="col-4">
		                    <label>Med Dependent:<span class="mandatory">*</span></label>
		                    <div class="form-group">
		                        <div class="input-group">
		                          <span class="input-group-addon" style="padding: 3px 6px;">
		                           <select name="medicaldep" class="form-control input-sm" style="width: 100%; height: 30px;">
		                          <option value="N">No</option>
		                          <option value="Y" >Yes</option>
		                         
		                          </select>
		                            
		                          </span>
		                          <input type="text" class="form-control input-sm mydate" name="medicaldepdate" readonly="readonly" placeholder="Enter Date" required style="width: 85px">
		                        </div><!-- /input-group -->
		                    </div> 
		                </div><!-- / Medical -->
		                
		                <!-- LTC Dependent -->
		                <div class="col-4">
		                    <label>LTC Dependent:<span class="mandatory">*</span></label>
		                    <div class="form-group">
		                        <div class="input-group">
		                          <span class="input-group-addon" style="padding: 3px 6px;">
		                          <select name="ltcdep" class="form-control input-sm" style="width: 100%; height: 30px;">
		                          <option value="Y" >Yes</option>
		                          <option value="N">No</option>
		                          </select>
		                               
		                          </span>
		                         <input type="text" class="form-control input-sm mydate"  readonly="readonly" value="<%=LocalDate.now() %>" placeholder=""  id="LTC" name="LTC"  required="required"  >
		                        </div><!-- /input-group -->
		                    </div> 
		                </div><!-- LTC Dependent -->
		           <!-- CEA Dependent -->
		                <div class="col-4">
		                    <label>CEA Dependent:<span class="mandatory">*</span></label>
		                    <div class="form-group">
		                        <div class="input-group">
		                          <span class="input-group-addon" style="padding: 3px 6px;">
		                          <select name="ceadep" class="form-control input-sm" style="width: 100%; height: 30px;">
		                          <option value="Y" >Yes</option>
		                          <option value="N">No</option>
		                          </select>
		                               
		                          </span>
		                         <input type="text" class="form-control input-sm mydate"  readonly="readonly" value="<%=LocalDate.now() %>" placeholder=""  id="CEA" name="CEA"  required="required"  >
		                        </div><!-- /input-group -->
		                    </div> 
		                </div><!-- CEA  Dependent -->
		              </div>
		          
		         
		          
		         <div class="row">
                   <div class="col-2">
                       <div class="form-group">
		                	<label>Marital Status<span class="mandatory">*</span></label>
		                    <select name="maritalstatus" class="form-control input-sm" style="width:117px; height: 30px;">
		                    <option value="U">Unmarried</option>
		                    <option value="M">Married</option>
		                    <option value="D">Divorced</option>
		                    <option value="W">Widow</option>
		                     <option value="S">Separated</option>
		                    </select>
		                 </div>
                    </div>
                    
                    
                     <div class="col-3">
                       <div class="form-group">
		                	<label>Employment<span class="mandatory">*</span></label>
		                    <select name="employement"  class="form-control input-sm" id="mem-employed" style="width:135px; height: 30px; padding:-0.625rem!;" required="required">
		                    <option disabled="disabled" value="" selected="selected">select</option>
		                    <option value="E">Employed</option>
		                    <option value="U">Unemployed</option>
		                    <option value="B">Business</option>
		                    <option value="H">Home maker</option>
		                    <option value="S">Student</option>
		                    <option value="I">Infant</option>
		                    </select>
		                 </div>
                    </div>
		        
                <!--// dependency ,Employed,Married-->
						<!-- <div class="row" id = "EmpHide"> -->
								<div class="col-md-3" >
			                    	<div class="form-group">
			                        <label>Employed Status:</label>
			                        	<select class="form-control input-sm" name="EmpStatus" id="mem-employed-status">
				                            <option value="" selected disabled >Select Status</option>
						                    <option value="Private">Private</option>
						                    <option value="Central Goverment">Central Government</option>
						                    <option value="State Goverment">State Government</option>
						                    <option value="PSU">PSUs</option>
			                            </select>
			                        </div>  
			               		</div>
			               		<div class="col-md-3" >
			                    	<div class="form-group">
			                        <label>Occupation:<span class="mandatory">*</span></label>
			                        	<input type="text" class="form-control" name="memberOccupation" value="" id="mem-occupation" maxlength="100"  placeholder="Occupation">
			                        </div>  
			               		</div>
			               		</div>
			               		<div class="col-md-3" >
			                    	<div class="form-group">
			                        <label>Income (Rs / Month)<span class="mandatory">*</span></label>
			                        	<input type="number" class="form-control numberonly" name="memberIncome" value="0" id="mem-income" min="0" max="9999999999" >
			                        </div>  
			               		</div>
							
		
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<input type="hidden" name="empid" value="<%= empdata[2]%>">
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
									name="action" value="submit">SUBMIT</button>
									</div>
							</div>
						</div>
						</div>
			</form>
			</div>
		</div>
		</div>
		</div>
		
</body>
<script type="text/javascript">
$('.mydate').daterangepicker({
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

<!-- $("#mem-employed").on("change",function(e){
	   
	 var EmpValue =$("#mem-employed").val();
	 
	    if(!(EmpValue=="E")){
			
	    	$("#EmpHide").hide();
	    	$('#mem-employed-status').removeAttr('required');
	    	$('#mem-occupation').removeAttr('required');
			$('#mem-income').removeAttr('required');
	      }else{
	    	  
	    	$("#EmpHide").show();
	    	$('#mem-employed-status').attr('required','required');
	    	$('#mem-occupation').attr('required','required');
			$('#mem-income').attr('required','required');
	      }
});



$("#mem-employed").on("change",function(e){
	   
	 var EmpValue =$("#mem-employed").val();
	    
	 
	    if(!(EmpValue=="U"||EmpValue=="B"||EmpValue=="H"||EmpValue=="S"||EmpValue=="I")){
		
	    	  $("#EmpHide").show();
	    	  
	      }else{
	    	  
	    	  $("#EmpHide").hide();
	    	  
	      }
});
 

$(document).ready(function(){

	   $("#EmpHide").hide();
});-->


<script type="text/javascript">

setPatternFilter($("#BenidTextBox"), /^-?\d*$/);

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
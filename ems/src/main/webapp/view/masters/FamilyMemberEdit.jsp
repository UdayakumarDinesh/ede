<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
     <%@page import="java.time.LocalDate"%>
      <%@page import="com.vts.ems.pis.model.EmpFamilyDetails" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>

<%
EmpFamilyDetails memberdata=(EmpFamilyDetails)request.getAttribute("memberdetails");
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> RelativeList = (List<Object[]>)request.getAttribute("FamilyRelation");
List<Object[]> StatusList = (List<Object[]>)request.getAttribute("FamilyStatus");

%>
	<div class="col page card">
		<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Family Members Edit<small><b>&nbsp;&nbsp;<%if(empdata!=null){%> <%=empdata[0]%>(<%=empdata[1]%>)
						<%}%></b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item active " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Family Edit</li>
					</ol>
				</div>
			</div>
		</div>
		
		<div class="card-body">
		
		<div class="row">
		<div class="col-3"></div>
		<form action="EditFamilyDetails.htm" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card"  > 
		<div class="card-header">
		<h5>Fill Family Member Details</h5>
		</div>
			<div class="card-body"  >
			  <!-- NAME & Date Of Birth -->
              <div class="row">
				      <div class="col-md-8">
		                <div class="form-group">
		                    <label>Name:<span class="mandatory">*</span></label>
		                    <input type="text"  id="NameTextBox" value="<%=memberdata.getMember_name()%>"  class="form-control input-sm"   maxlength="100" name="memberName" required="required" placeholder="Enter name"  onclick="return trim(this)" onchange="return trim(this)">
		                </div>
		               </div> 
		                
		                <div class="col-md-4">
		                 <div class="form-group">
                            <label>Date Of Birth:<span class="mandatory">*</span></label>
                            <input type="text" class="form-control input-sm mydate" value="<%=memberdata.getDob()%>" id="DOB" readonly="readonly" name="dob"required="required">
                        </div>
		                
		              </div>
		       </div>	
                <!-- / .NAME & Date Of Birth -->
                
                
                
                 
                <!-- RELATIONSHIP & BENID-->
                
               	
				
				
				 <div class="row">
				       <div class="col-md-4">
		                <div class="form-group">
		                	<label>Relation:<span class="mandatory">*</span></label>
		                    <select class="form-control input-sm selectpicker" name="relation" required  data-live-search="true">
		                      <option value="">Select Relation</option>
		                        <% for(Object[] relativeLs:RelativeList){ %> 
		                       <option value="<%=relativeLs[0]%>" <%if(memberdata.getRelation_id() == Integer.parseInt(relativeLs[0].toString())){%>selected<%}%>><%=relativeLs[1]%></option>
		                        <%} %> 
		                                     
		                    </select>
		                </div>
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                	<label>Gender:<span class="mandatory">*</span></label>
		                    <select class="form-control input-sm select2" name="Gender" required  data-live-search="true">
		                      <option value="">Select Gender</option>
		                      
		                        <option value="M" <%if(memberdata.getGender().equalsIgnoreCase("M")){ %>selected<%}%>>Male</option>
		                       <option value="F"  <%if(memberdata.getGender().equalsIgnoreCase("F")){ %>selected<%}%>>Female</option>
		                                     
		                    </select>
		                </div>
		              </div>
		              
		               <div class="col-md-4">
		                <div class="form-group">
		                    <label>Ben Id:<span class="mandatory">*</span></label>
		                    <input  id="BenidTextBox" value="<%=memberdata.getCghs_ben_id()%>"  type="text" name="benId" class="form-control input-sm"   maxlength="9"   placeholder="Enter BenID" onclick="checkLength()">
		                </div>
		               </div> 
		                 		        	       
				</div>	
				
				
                <!-- RELATIONSHIP & BENID-->
                
                
                  
               <!-- Status,Status From,BG,PH -->
                <div class="row">
                  <div class="col-4">
                         <div class="form-group">
                          <label>Status<span class="mandatory">*</span></label>
                            <select class="form-control input-sm" name="status" required="required">
                              <option value="none" selected disabled hidden>Select Status</option> 
                              

                               <%for(Object[] statusLs:StatusList){ %> 
		                       <option value="<%=statusLs[0]%>" <%if(memberdata.getFamily_status_id()==Integer.parseInt(statusLs[0].toString())){%>selected<%}%>><%=statusLs[1]%></option>
		                       <%} %> 

                               <%--  <% for(Object[] statusLs:StatusList){ %> 
		                       <option value="<%=statusLs[0]%>" <%if(memberdata.getFamily_status_id()==Integer.parseInt()){%>selected<%}%>><%=statusLs[1]%></option>
		                        <%} %> --%>

                            </select>
                            </div>  
                        </div>
                                              
                        <div class="col-4">
                         <div class="form-group">
                          <label>Status From<span class="mandatory">*</span></label>
                            <input type="text" class="form-control input-sm mydate" value="<%=memberdata.getStatus_from()%>" id="STATUSDATE" name="statusDate" readonly="readonly" required="required" placeholder="Enter Date" />
                         </div>
                        </div>
                   
                <div class="col-2">
                        <div class="form-group">
                            <label>BG:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm" name="bloodgroup" required="required" style="width:70px; height: 30px;" >
                                
                                <option value="A-" <%if(memberdata.getBlood_group().equalsIgnoreCase("A-")){%> selected <%}%>>A-</option>
                                <option value="A+" <%if(memberdata.getBlood_group().equalsIgnoreCase("A+")){%> selected <%}%>>A+</option>
                                <option value="B-" <%if(memberdata.getBlood_group().equalsIgnoreCase("B-")){%> selected <%}%>>B-</option>
                                <option value="B+" <%if(memberdata.getBlood_group().equalsIgnoreCase("B+")){%> selected <%}%>>B+</option>
                                <option value="AB-" <%if(memberdata.getBlood_group().equalsIgnoreCase("AB-")){%> selected <%}%>>AB-</option>
                               <option value="AB+" <%if(memberdata.getBlood_group().equalsIgnoreCase("AB+")){%> selected <%}%>>AB+</option>
                               <option value="O-" <%if(memberdata.getBlood_group().equalsIgnoreCase("O-")){%> selected <%}%>>O-</option>
                               <option value="O+" <%if(memberdata.getBlood_group().equalsIgnoreCase("O+")){%> selected <%}%>>O+</option>
                               <option value="Not Available" <%if(memberdata.getBlood_group().equalsIgnoreCase("Not Available")){%> selected <%}%>>Not Available</option>
                            </select>
                        </div>
                    </div>
                
                
                <div class="col-2">
                       <div class="form-group">
		                	<label>PH:<span class="mandatory">*</span></label>
		                    <select name="PH" class="form-control input-sm" style="padding: 3px 6px; ">
		                    <option value="N" <%if(memberdata.getPH().equalsIgnoreCase("N")){%> selected <%}%> >No</option>
		                    <option value="Y"  <%if(memberdata.getPH().equalsIgnoreCase("Y")){%> selected <%}%>>Yes</option>
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
		                          <option value="N" <%if(memberdata.getMed_dep().equalsIgnoreCase("N")){%>selected<%}%> >No</option>
		                          <option value="Y" <%if(memberdata.getMed_dep().equalsIgnoreCase("Y")){%>selected<%}%> >Yes</option>
		                         
		                          </select>
		                            
		                          </span>
		                          <input type="text" class="form-control input-sm mydate" value="<%=memberdata.getMed_dep_from()%>" id="MEDICALDEPDATE" name="medicaldepdate" readonly="readonly" placeholder="Enter Date" required style="width: 85px">
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
		                          <option value="Y" <%if(memberdata.getLtc_dep().equalsIgnoreCase("Y")){%> selected <%}%>>Yes</option>
		                          <option value="N" <%if(memberdata.getLtc_dep().equalsIgnoreCase("N")){%> selected <%}%> >No</option>
		                          </select>
		                               
		                          </span>
		                        <input type="text" class="form-control input-sm mydate"  readonly="readonly" value="<%=memberdata.getLtc_dep_from()%>" placeholder=""  id="LTC" name="LTC"  required="required"  >
		                        </div><!-- /input-group -->
		                    </div> 
		                </div><!-- LTC Dependent -->
		          
		          
		         
		          
		         
                   <div class="col-2">
                       <div class="form-group">
		                	<label>Married:<span class="mandatory">*</span></label>
		                    <select name="married_unmarried" class="form-control input-sm" style="padding: 3px 6px; height: 30px;">
		                    <option value="N" <%if(memberdata.getMar_unmarried().equalsIgnoreCase("N")){%> selected <%}%> >No</option>
		                    <option value="Y" <%if(memberdata.getMar_unmarried().equalsIgnoreCase("Y")){%> selected <%}%>>Yes</option>
		                    </select>
		                 </div>
                    </div>
                    
                    
                     <div class="col-2">
                       <div class="form-group">
		                	<label>Employed: <%=memberdata.getEmp_unemp() %><span class="mandatory">*</span></label>
		                    <select name="emp_unemp" id="EmpId" class="form-control input-sm" style="width:70px; height: 30px;">
		                    <option value="N" <%if(memberdata.getEmp_unemp().equalsIgnoreCase("N")){%> selected <%}%> >No</option>
		                    <option value="Y" <%if(memberdata.getEmp_unemp().equalsIgnoreCase("Y")){%> selected <%}%> >Yes</option>       
		                    </select>
		                 </div>
                    </div>
     
		          
		           </div>
               
                <!--// dependency ,Employed,Married-->
				<div class="row">
				
							 <div class="col-md-4" id ="EmpHide">
                         <div class="form-group">
                          <label>Employed Status:  <span class="mandatory">*</span></label>
                            <select class="form-control input-sm" name="EmpStatus" required="required">
                              <!-- <option value="none" selected disabled hidden>Select Status</option> -->
		                    <option value="Private" <% if(memberdata.getEmpStatus()!=null){ if(memberdata.getEmpStatus().equalsIgnoreCase("Private")){%> selected <%}}%>>Private</option>
		                    <option value="Central Goverment"  <% if(memberdata.getEmpStatus()!=null){ if(memberdata.getEmpStatus().equalsIgnoreCase("Central Goverment")){%> selected <%}}%>>Central Goverment.</option>
		                    <option value="State Goverment" <%if(memberdata.getEmpStatus()!=null){  if(memberdata.getEmpStatus().equalsIgnoreCase("State Goverment")){%> selected <%}}%>>State Goverment</option>
		                    <option value="PSU" <%if(memberdata.getEmpStatus()!=null){  if(memberdata.getEmpStatus().equalsIgnoreCase("PSU")){%> selected <%}}%>>PSU</option>
		                      
                            </select>
                            </div>  
                        </div>
                      
				</div>
		</div>
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							 <input type="hidden" name="empid" value="<%=empdata[2]%>"> 
							  <input type="hidden" name="familyid" value="<%=memberdata.getFamily_details_id()%>"> 
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
									name="action" value="submit">SUBMIT</button>
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
$('#LTC').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#MEDICALDEPDATE').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$('#STATUSDATE').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#DOB').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$(document).ready(function(){
	
	   $("#EmpHide").hide();
});


$("#EmpId").on("change",function(e){
	 
	 var EmpValue =$("#EmpId").val();	   

	 
	    if(!(EmpValue=="N")){
		
	    	  $("#EmpHide").show();
	    	  
	      }else{
	    	  
	    	  $("#EmpHide").hide();
	    	  
	      }
});
 


</script>

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



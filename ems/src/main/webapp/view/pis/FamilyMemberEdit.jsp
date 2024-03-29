<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
      <%@page import="com.vts.ems.pis.model.EmpFamilyDetails" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

<%
EmpFamilyDetails memberdata=(EmpFamilyDetails)request.getAttribute("memberdetails");
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> RelativeList = (List<Object[]>)request.getAttribute("FamilyRelation");
List<Object[]> StatusList = (List<Object[]>)request.getAttribute("FamilyStatus");

%>
	<div class="card-header page-top">
			<div class="row">
				<div class="col-6">
					<h5>
						Family Members Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)
						</b></small>
					</h5>
				</div>
				<div class="col-md-6">
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

	<div class=" page card dashboard-card">

		<div class="card-body">
		
		<div class="row">
		<div class="col-3"></div>
		<div class="card" >
		<form action="EditFamilyDetails.htm" method="POST" enctype="multipart/form-data" autocomplete="off">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		 
		<div class="card-header">
		<h5>Fill Family Member Details</h5>
		</div>
			<div class="card-body"  >
			  <!-- NAME & Date Of Birth -->
              <div class="row">
				      <div class="col-md-8">
		                <div class="form-group">
		                    <label>Name:<span class="mandatory">*</span></label>
		                    <input type="text"  id="NameTextBox"  style="text-transform:capitalize" <%if(memberdata!=null && memberdata.getMember_name()!=null){ %>value="<%=memberdata.getMember_name()%>"<%}%>  class="form-control input-sm"   maxlength="100" name="memberName" required="required" placeholder="Enter name"  onclick="return trim(this)" onchange="return trim(this)">
		                </div>
		               </div> 
		                
		                <div class="col-md-4">
		                 <div class="form-group">
                            <label>Date Of Birth:<span class="mandatory">*</span></label>
                            <input type="text" class="form-control input-sm mydate" value="<%if(memberdata!=null&&memberdata.getDob()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(memberdata.getDob().toString())%><%}%>" id="DOB" readonly="readonly" name="dob"required="required">
                        </div>
		                
		              </div>
		       </div>	
                <!-- / .NAME & Date Of Birth -->
          
                 
                <!-- RELATIONSHIP & BENID-->

				 <div class="row">
				       <div class="col-md-5">
		                <div class="form-group">
		                	<label>Relation:<span class="mandatory">*</span></label>
		                    <select class="form-control input-sm selectpicker" name="relation" required  data-live-search="true">
		                     
		                        <% for(Object[] relativeLs:RelativeList){ %> 
		                       <option value="<%=relativeLs[0]%>" <%if(memberdata.getRelation_id() == Integer.parseInt(relativeLs[0].toString())){%>selected<%}%>><%=relativeLs[1]%></option>
		                        <%} %> 
		                                     
		                    </select>
		                </div>
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                	<label>Gender:<span class="mandatory">*</span></label>
		                    <select class="form-control input-sm select2" name="Gender" required  data-live-search="true">
		                     
		                      
		                        <option value="M" <%if(memberdata.getGender()!=null && memberdata.getGender().equalsIgnoreCase("M")){ %>selected<%}%>>Male</option>
		                       <option value="F"  <%if(memberdata.getGender()!=null && memberdata.getGender().equalsIgnoreCase("F")){ %>selected<%}%>>Female</option>
		                                     
		                    </select>
		                </div>
		              </div>
		              
		                <div class="col-md-4">
		                <div class="form-group">
		                    <label>Ben Id:<span class="mandatory">*</span></label>
		                    <input  id="BenidTextBox" value="<%=memberdata.getCghs_ben_id()%>"  type="text" name="benId" class="form-control input-sm"   maxlength="9"   placeholder="Enter BenID" onclick="checkLength()" required="required">
		                </div>
		               </div>  
		                 		        	       
				</div>			
                <!-- RELATIONSHIP & BENID-->
                
                
                  
                <!-- Status,Status From,BG,PH -->
                <div class="row">
                  <div class="col-4">
                         <div class="form-group">
                          <label>Status<span class="mandatory">*</span></label>
                            <select class="form-control input-sm select2" name="status" required="required">
                              <option value="none" selected disabled >Select Status</option> 
                              
                               <%for(Object[] statusLs:StatusList){ %> 
		                       <option value="<%=statusLs[0]%>" <%if(memberdata!=null && memberdata.getFamily_status_id()==Integer.parseInt(statusLs[0].toString())){%>selected<%}%>><%=statusLs[1]%></option>
		                       <%} %> 

                            </select>
                            </div>  
                        </div>
                                              
                        <div class="col-4">
                         <div class="form-group">
                          <label>Status From<span class="mandatory">*</span></label>
                            <input type="text" class="form-control input-sm mydate" value="<%if(memberdata!=null&&memberdata.getStatus_from()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(memberdata.getStatus_from().toString())%><%}%>" id="STATUSDATE" name="statusDate" readonly="readonly" required="required" placeholder="Enter Date" />
                         </div>
                        </div>
                   
                <div class="col-2">
                        <div class="form-group">
                            <label>BG:<span class="mandatory">*</span></label>
                            <select class="form-control input-sm" name="bloodgroup" required="required" style="width:70px; height: 30px;" >
                                
                                <option value="A-"  <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("A-")){%>  selected <%}%>>A-</option>
                                <option value="A+"  <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("A+")){%>  selected <%}%>>A+</option>
                                <option value="B-"  <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("B-")){%>  selected <%}%>>B-</option>
                                <option value="B+"  <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("B+")){%>  selected <%}%>>B+</option>
                                <option value="AB-" <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("AB-")){%> selected <%}%>>AB-</option>
                                <option value="AB+" <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("AB+")){%> selected <%}%>>AB+</option>
                                <option value="O-"  <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("O-")){%>  selected <%}%>>O-</option>
                                <option value="O+"  <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("O+")){%>  selected <%}%>>O+</option>
                                <option value="Not Available" <%if(memberdata!=null && memberdata.getBlood_group()!=null && memberdata.getBlood_group().equalsIgnoreCase("Not Available")){%> selected <%}%>>Not Available</option>
                            </select>
                        </div>
                    </div>
                
                
                <div class="col-2">
                       <div class="form-group">
		                	<label>PH:<span class="mandatory">*</span></label>
		                    <select name="PH" class="form-control input-sm" style="padding: 3px 6px; ">
		                    <option value="N" <%if(memberdata!=null && memberdata.getPH()!=null && memberdata.getPH().equalsIgnoreCase("N")){%> selected <%}%> >No </option>
		                    <option value="Y" <%if(memberdata!=null && memberdata.getPH()!=null && memberdata.getPH().equalsIgnoreCase("Y")){%> selected <%}%> >Yes</option>
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
		                          <option value="N" <%if(memberdata!=null && memberdata.getMed_dep()!=null && memberdata.getMed_dep().equalsIgnoreCase("N")){%>selected<%}%> >No</option>
		                          <option value="Y" <%if(memberdata!=null && memberdata.getMed_dep()!=null && memberdata.getMed_dep().equalsIgnoreCase("Y")){%>selected<%}%> >Yes</option>
		                         
		                          </select>
		                            
		                          </span>
		                          <input type="text" class="form-control input-sm mydate" value="<%if(memberdata!=null&&memberdata.getMed_dep_from()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(memberdata.getMed_dep_from().toString())%><%}%>" id="MEDICALDEPDATE" name="medicaldepdate" readonly="readonly" placeholder="Enter Date" required style="width: 85px">
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
		                          <option value="Y" <%if(memberdata!=null && memberdata.getLtc_dep()!=null && memberdata.getLtc_dep().equalsIgnoreCase("Y")){%> selected <%}%>>Yes</option>
		                          <option value="N" <%if(memberdata!=null && memberdata.getLtc_dep()!=null && memberdata.getLtc_dep().equalsIgnoreCase("N")){%> selected <%}%> >No</option>
		                          </select>
		                               
		                          </span>
		                        <input type="text" class="form-control input-sm mydate"  readonly="readonly" value="<%if(memberdata!=null&&memberdata.getLtc_dep_from()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(memberdata.getLtc_dep_from().toString())%><%}%>" placeholder=""  id="LTC" name="LTC"  required="required"  >
		                        </div><!-- /input-group -->
		                    </div> 
		                </div><!-- LTC Dependent -->
		          
		          
		         
		          
		         
                   <div class="col-2">
                       <div class="form-group">
		                	<label>Married:<span class="mandatory">*</span></label>
		                    <select name="married_unmarried" class="form-control input-sm" style="padding: 3px 6px; height: 30px;">
		                    <option value="N" <%if(memberdata!=null && memberdata.getMar_unmarried()!=null && memberdata.getMar_unmarried().equalsIgnoreCase("N")){%> selected <%}%> >No</option>
		                    <option value="Y" <%if(memberdata!=null && memberdata.getMar_unmarried()!=null && memberdata.getMar_unmarried().equalsIgnoreCase("Y")){%> selected <%}%>>Yes</option>
		                    </select>
		                 </div>
                    </div>
                    
                    
                     <div class="col-2">
                       <div class="form-group">
		                	<label>Employed: <span class="mandatory">*</span></label>
		                    <select name="emp_unemp" id="EmpId" class="form-control input-sm" style="width:70px; height: 30px;">
		                    <option value="N" <%if(memberdata!=null && memberdata.getEmp_unemp()!=null && memberdata.getEmp_unemp().equalsIgnoreCase("N")){%> selected <%}%> >No</option>
		                    <option value="Y" <%if(memberdata!=null && memberdata.getEmp_unemp()!=null && memberdata.getEmp_unemp().equalsIgnoreCase("Y")){%> selected <%}%> >Yes</option>       
		                    </select>
		                 </div>
                    </div>
     
		          
		           </div> 
               
				
							<div class="row" id = "EmpHide">
								<div class="col-md-3" >
			                    	 <div class="form-group">
		                         		<label>Employed Status:  <span class="mandatory">*</span></label>
		                           		<select class="form-control input-sm" name="EmpStatus" required="required">
						                    <option value="Private" <% if(memberdata!=null && memberdata.getEmpStatus()!=null && memberdata.getEmpStatus().equalsIgnoreCase("Private")){%> selected <%}%>>Private</option>
						                    <option value="Central Goverment"  <% if(memberdata!=null && memberdata.getEmpStatus()!=null && memberdata.getEmpStatus().equalsIgnoreCase("Central Goverment")){%> selected <%}%>>Central Government</option>
						                    <option value="State Goverment" <%if(memberdata!=null && memberdata.getEmpStatus()!=null && memberdata.getEmpStatus().equalsIgnoreCase("State Goverment")){%> selected <%}%>>State Government</option>
						                    <option value="PSU" <%if(memberdata!=null && memberdata.getEmpStatus()!=null && memberdata.getEmpStatus().equalsIgnoreCase("PSU")){%> selected <%}%>>PSUs</option>
		                          		</select>
		                            </div>  
			               		</div>
			               		<div class="col-md-3" >
			                    	<div class="form-group">
			                        <label>Occupation:<span class="mandatory">*</span></label>
			                        	<input type="text" class="form-control" name="memberOccupation" <%if(memberdata!=null && memberdata.getMemberOccupation()!=null){%> value="<%=memberdata.getMemberOccupation() %>" <%}else{ %> value="" <%} %> id="mem-occupation" maxlength="100">
			                        </div>  
			               		</div>
			               		
			               		<div class="col-md-3" >
			                    	<div class="form-group">
			                        <label>Income (Rs / Month)<span class="mandatory">*</span></label>
			                        	<input type="number" class="form-control numberonly" name="memberIncome" <%if(memberdata!=null && memberdata.getMemberIncome()!=null){%> value="<%=memberdata.getMemberIncome() %>" <%}else{ %> value="0" <%} %> id="mem-income" max="9999999999" >
			                        </div>  
			               		</div>
							</div>
				
				
				 
		</div>
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							 <input type="hidden" name="empid" <%if(empdata!=null){%> value="<%= empdata[2]%>" <%}%>>
							  <input type="hidden" name="familyid" value="<%=memberdata.getFamily_details_id()%>"> 
								<button type="submit" class="btn btn-sm submit-btn AddItem"
									
									name="action" value="submit" onclick="return CommentsModel();">SUBMIT</button>
									</div>
							</div>
						</div>
			<!--------------------------- container ------------------------->
			<div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">The Reason For Edit</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					        <div class="modal-body">
						        	<div class="form-inline">
						        	<div class="form-group "  >
						               <label>File : &nbsp;&nbsp;&nbsp;</label> 
						               <input type="file" class=" form-control w-100"   id="file" name="selectedFile" > 
						      		</div>
						      		</div>
						        	
						        	<div class="form-inline">
						        	<div class="form-group w-100">
						               <label>Comments : &nbsp;&nbsp;&nbsp;</label> 
						              <textarea  class=" form-control w-100" maxlength="1000" style="text-transform:capitalize;"  id="comments"  name="comments" required="required" ></textarea> 
						      		</div>
						      		</div>
					        </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick="return confirm('Are You Sure To Submit!');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
			</form>
		</div>
		</div>
		</div>
		
		</div>
</body>
<script type="text/javascript">
function CommentsModel()
{
	var name = $("#NameTextBox").val();
	var benid = $("#BenidTextBox").val();
	if(name==null || name=='' || name=="null" ){
		alert('Enter the Name!');
		return false;
	}else if(benid==null || benid=='' || benid=="null" ){
		alert('Enter the BenId!');
		return false;
	}else{
		$('#myModal').modal('show');
	}
		 
}
</script>
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
	
	employementstatus();
});


$("#EmpId").on("change",function (e){
	 
	 var EmpValue =$("#EmpId").val();	   

	 
	    if(!(EmpValue=="N")){
		
	    	  $("#EmpHide").show();
	    	  
	      }else{
	    	  
	    	  $("#EmpHide").hide();
	    	  
	      }
});
function employementstatus(e){
	 
	 var EmpValue =$("#EmpId").val();	   

	 
	    if(!(EmpValue=="N")){
		
	    	  $("#EmpHide").show();
	    	  
	      }else{
	    	  
	    	  $("#EmpHide").hide();
	    	  
	      }
}


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



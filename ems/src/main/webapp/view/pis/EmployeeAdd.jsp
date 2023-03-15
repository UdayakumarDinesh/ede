<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.vts.ems.pis.model.DivisionMaster"%>
<%@page import="com.vts.ems.pis.model.PisPayLevel"%>
<%@page import="com.vts.ems.pis.model.EmpStatus"%>
<%@page import="com.vts.ems.pis.model.PisCadre"%>
<%@page import="com.vts.ems.pis.model.PisCatClass"%>
<%@page import="com.vts.ems.pis.model.PisCategory"%>
<%@page import="com.vts.ems.pis.model.EmployeeDesig"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 
<script type="text/javascript">
function validateform(){
	var x = confirm("Are you sure To Submit?");
	
	  if (x){
		  var pan =$("#PAN").val();
		  var uid =$("#UIDTextBox").val();
		  var sbiAccount = $("#SBITextBox").val();
		  var internalnum = $("#InternalNum").val();
		  var height = $("#height").val();
		  var permpassno = $("#permpassno").val();
          var dcmafno= $("#dcmafno").val();
          var chssno= $("#chssno").val();
          var iticreditsocno=$("#iticreditsocno").val();
          var benovelentno=$("#benovelentno").val();
		if(pan.length<10){
			 alert("Check PAN Number!");
		       event.preventDefault();
		       return false;
		}
		if(uid.length<12){
			 alert("Check Aadhaar Number!");
		       event.preventDefault();
		       return false;
		}
		if(sbiAccount.length<11){
			 alert("Check SBI Number!");
		       event.preventDefault();
		       return false;
		}
		if(internalnum.length<4){
			 alert("Check Extension Number!");
		       event.preventDefault();
		       return false;
		}
		if(height==null || height=="" || height=="null"){
			 alert("Enter Height");
		       event.preventDefault();
		       return false;
		}
		if(permpassno==null || permpassno=="" || permpassno=="null"){
			 alert("Check Permanent Pass Number!");
		       event.preventDefault();
		       return false;
		}
		/* if(dcmafno==null || dcmafno=="" || dcmafno=="null"){
			 alert("Check DCMAF Number!");
		       event.preventDefault();
		       return false;
		}
		if(chssno==null || chssno=="" || chssno=="null"){
			 alert("Check CHSS Number!");
		       event.preventDefault();
		       return false;
		}
		if(iticreditsocno==null || iticreditsocno=="" || iticreditsocno=="null"){
			 alert("Check ITI Credit Society Number!");
		       event.preventDefault();
		       return false;
		}
		if(benovelentno==null || benovelentno=="" || benovelentno=="null"){
			 alert("Check Benovelent Fund Number!");
		       event.preventDefault();
		       return false;
		} */
	      return true;
	  
	  }else{
	 return false;}
}
</script>
</head>
<body>

<%

SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
SimpleDateFormat sdf1=DateTimeFormatUtil.getRegularDateFormat();

List<EmployeeDesig> desiglist=(List<EmployeeDesig>)request.getAttribute("desiglist");
List<PisCategory> piscategorylist=(List<PisCategory>)request.getAttribute("piscategorylist");
List<PisCatClass> piscatclasslist=(List<PisCatClass>)request.getAttribute("piscatclasslist");
List<PisCadre> piscaderlist=(List<PisCadre>)request.getAttribute("piscaderlist");
List<EmpStatus> empstatuslist=(List<EmpStatus>)request.getAttribute("empstatuslist");
List<PisPayLevel> paylevellist=(List<PisPayLevel>)request.getAttribute("paylevellist");
List<DivisionMaster> divisionlist=(List<DivisionMaster>)request.getAttribute("divisionlist");


%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Employee Add</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<!-- <li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li> -->
					<li class="breadcrumb-item "><a href="PisAdminEmpList.htm">Employee List</a></li>
					<li class="breadcrumb-item active " aria-current="page">Employee Add</li>
				</ol>
			</div>	
		</div>
	</div>	

 <div class=" page card dashboard-card">
	
	
	<div class="card-body" >
		<div class="card" >
						
			<div class="card-body">
				<form action="EmployeeAddSubmit.htm" method="post" autocomplete="off" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			
			            <div class="col-md-2">
			                <label>Rank/Salutation<span class="mandatory">*</span></label><br>
			                 <select class=" form-control select2" name="salutation" required="required">
								<option value="Mr.">Mr.</option>
								<option value="Mrs.">Mrs.</option>
								<option value="Miss.">Miss.</option>
								<option value="Dr.">Dr.</option>
							</select>
			            </div>
			
			
			            <div class="col-md-4">
			                <label>Name<span class="mandatory">*</span></label> 
			            	<input type="text" name="empname" required="required" id="empname" style="text-transform:capitalize"  class="form-control input-sm"   maxlength="100"  placeholder="Employee name"   onclick="return trim(this)" onchange="return trim(this)">
			            </div>			
			            <div class="col-md-2">			
			                <label>Designation<span class="mandatory">*</span></label>
			                <select class="form-control select2 " name="Designationid" required="required" data-live-search="true" >
								<%if(desiglist!=null && desiglist.size()>0){
					                for(EmployeeDesig desig : desiglist){ %>
				                    <option value="<%=desig.getDesigId() %>" ><%= desig.getDesignation()%></option>
					                <%}
				                } %>  
							</select>
			            </div>
			
			            <div class="col-md-2">
			                <label>Employee No<span class=" mandatory ">*</span></label>
			                <input type="text" id="PunchcardTextBox" name="PunchCardNo"  value="" maxlength="5"
			                    class=" form-control input-sm " placeholder="Employee No " required="required"
			                     onblur="checknegative(this)">
			            </div>
				
						<div class="col-md-2">
			                <label>Department <span class="mandatory">*</span></label>
			                <select name="divisionid" class="form-control input-sm select2" required data-live-search="true">
								<%for( DivisionMaster division: divisionlist){ %>
									<option value="<%=division.getDivisionId()%>"><%=division.getDivisionName()%></option>
								<%} %>			
			
			                </select>
			            </div>
			
			        </div>
			    </div>
			
			    <div class="form-group">
			        <div class="row">
			
			           <div class="col-md-2">
			                <label>Gender<span class="mandatory">*</span></label>
			                <select name="gender" class="form-control input-sm" required>
			                    <option value="M">Male</option>
			                    <option value="F">Female</option>
			                </select>
			            </div>
			
						 <div class="col-md-2">
						 	<label>DOB<span class="mandatory">*</span></label>
							<div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly" value="<%=LocalDate.now() %>" placeholder=""  id="dob" name="dob"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">
							      
							    </label>                    
							</div>
						 </div>
		
			          <div class="col-md-2">
			                <label class="text-nowrap">Blood Group<span class="mandatory">*</span></label>
			                <select name="bloodgroup" class="form-control input-sm select2" required data-live-search="true">
			
			                    <option value="A-">A-</option>
			                    <option value="A+">A+</option>
			                    <option value="B-">B-</option>
			                    <option value="B+">B+</option>
			                    <option value="AB-">AB-</option>
			                    <option value="AB+">AB+</option>
			                    <option value="O-">O-</option>
			                    <option value="O+">O+</option>
			                    <option value="NOT">Not Available</option>
			                </select>
			            </div>
			
			 		  <div class="col-md-2">
			                <label>Marital Status </label>
			                <select name="MaritalStatus" class=" form-control input-sm select2 " >
			                	<option value="U">UnMarried</option>
								<option value="M">Married</option>									
			                </select>			                
			            </div>
			            
			            <div class="col-md-2">
			                <label>DOJ<span class="mandatory">*</span></label>
			                <div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly" value="<%=LocalDate.now() %>" placeholder=""  id="doj" name="doj"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">
							    </label>                    
							</div>
			            </div>
					          
						<div class="col-md-2">
			                <label>Home Town </label>
			                <input type="text" id="txtName" name="HomeTown" style=" text-transform:uppercase " value=""
			                    maxlength=" 240 " class=" form-control input-sm " placeholder="Home Town " 
			                    onclick=" Validate() ">
			            </div>
			            
			            
			          </div>
			        </div>
			   
			    <div class="form-group">
			        <div class="row">
			            
			             <div class="col-md-2">
			                <label>Mobile No<span class=" mandatory ">*</span></label>
			                <input type="text" name="PhoneNo"  value="" maxlength="10"
			                    class=" form-control input-sm Phoneno" placeholder="Phone no " required="required"
			                     onblur="checknegative(this) ">
			              </div>
			              
			               <div class="col-md-2">
			                <label>Alternate Mobile No</label>
			                <input type="text" name="AltPhoneno" value=""  maxlength="10"
			                    class=" form-control input-sm Phoneno" placeholder="Alternate Phone no "   onblur="checknegative(this) ">
			              </div>
			            
			              <div class="col-md-2">
			                <label>Extension number<span class="mandatory">*</span></label>
			                <input type="text" name="internalNo" value="" maxlength="4" class=" form-control input-sm "
			                    placeholder="Extension Number " onblur=" checknegative(this) "   id="InternalNum"
			                    onkeypress=" return isNumber(event) " required="required">
			             </div>
			            
						<div class="col-md-2">
			                <label>Internal Email<span class=" mandatory ">*</span></label>
			                <input type="email" value="" name="email" class=" form-control input-sm " maxlength="100"
			                    placeholder="Internal Email " required="required" onclick=" return trim(this)"
			                    onchange="return trim(this) ">
			            </div>
			            
			            <div class="col-md-2">
			                <label>Aadhaar No<span class="mandatory">*</span></label>
			                <input id="UIDTextBox" type="text" name="uid" value="" class="form-control input-sm" maxlength="12" placeholder="Aadhaar No" required>
			            </div>
			            
			            <div class="col-md-2">
			                <label>PAN<span class="mandatory">*</span></label>
			                <input  type="text"   id="PAN" name="pan" style="text-transform:uppercase" value="" class="form-control input-sm "  maxlength="10" placeholder="PAN">
			            </div>

			    	</div>
			    </div>
			
			
			    <div class="form-group">
			        <div class="row">
			       
			        	 <div class="col-md-2">
			                <label>UAN No</label>
			                <input type="text"  name="UANNo" id="UANNo" value="" maxlength="12"
			                    class=" form-control input-sm " placeholder="UAN No "    onblur="checknegative(this) ">
			            </div>
			           <div class="col-md-2">
			                <label>SBI Account<span class=" mandatory ">*</span></label>
			                <input type="text" id="SBITextBox" value="" name="SBI" class=" form-control input-sm " required
			                    maxlength=" 11 " placeholder="Account Number " onblur=" checknegative(this) ">
			            </div>
			            
			            <div class="col-md-2">
			                <label>PayLevel<span class=" mandatory ">*</span></label>
			                <select name="payLevel" class=" form-control input-sm select2 " data-live-search=" true ">
								<%for( PisPayLevel paylevel: paylevellist){ %>
									<option value="<%=paylevel.getPayLevelId() %>"><%=paylevel.getPayLevel()%></option>
								<%} %>
			
			                </select>
			            </div>
			            
			            <div class="col-md-2">
			                <label>Basic Pay<span class=" mandatory ">*</span></label>
			                <input type="text" id="basicpaybox" value="" name="basicpay" class=" form-control input-sm " maxlength="12"
			                    placeholder="Basic Pay" required="required">
			            </div>
			            
			             <div class="col-md-2">
			                <label>GPF/PRAN</label>
			                <input type="text" name="gpf" value="" class=" form-control input-sm " maxlength=" 12 "
			                    placeholder="GPF " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			            
			            <div class="col-md-2">
			                <label>Service Status<span class=" mandatory ">*</span></label>
			                <select name="ServiceStatus" class=" form-control input-sm select2  " required="required"
			                    data-live-search=" true ">
			
			                    <option value="Confirmed"> Confirmed</option>
			                    <option value="Probation"> Probation</option>
			                    <option value="Adhoc">     Adhoc</option>
			                    <option value="Temporary"> Temporary</option>
			                    <option value="Contract">  Contract</option>
			                </select>
			            </div>
			            
						            
			
			        </div>
			    </div>
			    
				    <div class="form-group">
				        <div class="row">
    
    					 <div class="col-md-2">
			                <label>Religion</label>
			                <select name="religion" class="form-control input-sm select2" data-live-search="true">
			                    <option value="Christian">Christian</option>
			                    <option value="Hindu">Hindu</option>
			                    <option value="Islam">Islam</option>
			                    <option value="Jain">Jain</option>
			                    <option value="Parsi">Parsi </option>
			                    <option value="Sikh">Sikh</option>
			                    <option value="Others">Others</option>
			
			                </select>
			            </div>			
			            
    					<div class="col-md-2">
			                <label>Category</label>
			                <select name="category" class=" form-control input-sm select2 "  data-live-search="true">
								<%for( PisCategory category: piscategorylist){ %>
									<option value="<%=category.getCategory_id()%>"><%=category.getCategory_desc()%></option>
								<%} %>				
			                </select>
			            </div>
	            
			              <div class="col-md-2">
			                <label class="text-nowrap">Cadre Name</label>
			                <select name="caderid" id="CadreId" class="form-control select2">
								<%for( PisCadre cadre: piscaderlist){ %>
									<option value="<%=cadre.getCadreId()%>"><%=cadre.getCadre()%></option>
								<%} %>
			                </select>
			            </div>
			
			
			            <div class="col-md-2">
			                <label>CAT Class</label>
			                <select name="catcode" class="form-control select2" required data-live-search="true">
								<%for( PisCatClass catclass: piscatclasslist){ %>
									<option value="<%=catclass.getCat_id()%>"><%=catclass.getCat_name()%></option>
								<%} %>
			
			                </select>
			            </div>		
 
			             <div class="col-md-2">
			                <label>Physically Handicap</label>
			                
			                 <select name="ph" class=" form-control input-sm select2 " >
			                 	<option value="N">No</option>
								<option value="Y">YES</option>								
			                </select>	

			            </div>      
			                
			             <div class="col-md-2">
			                <label>Identification Mark</label>
			                <input type="text" value="" name="idMark" class=" form-control input-sm " maxlength="99"
			                    placeholder="Identification Mark " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
					</div>
				</div>
			            
			            
			     <div class="form-group">
			        <div class="row"> 
			        
			            <div class="col-md-2">
			                <label>Emp Status<span class="mandatory">*</span></label>
			                <select id="Emptype" name="empstatus"  id="empstatus" class=" form-control input-sm select2 " required data-live-search="true"  >
                   				<%for( EmpStatus status: empstatuslist){ %>
								<option value="<%=status.getEmp_status()%>"><%=status.getEmp_status_name()%></option>
								<%}%>
			                </select>
			            </div> 
			            <div class="col-md-2">
			                <label>Availed Govt Quarters</label>          
			                <select name="gq" class=" form-control input-sm select2" >
			                	<option value="N">No</option>
								<option value="Y">YES</option>								
			                </select>	
			            </div> 
			
			            <div class="col-md-2" id="EmpHide">
			                <label>Emp Status Date<span class=" mandatory ">*</span></label>
			                <input type="text" readonly="readonly" required="required" id="EmpStatusDate" name="EmpStatusDate" value="<%=LocalDate.now() %>" class=" form-control input-sm " placeholder="EmpStatus Date ">
			            </div>
			            
			            <div class="col-md-2" >
			            	<label> Height(In Cms) <span class=" mandatory ">*</span></label>
			            	<input id="height" name="Height" maxlength="3" class=" form-control input-sm " placeholder="Enter In Centimeters">
			            </div>
			            
			             <div class="col-md-2">
			                <label>Ex ServiceMan   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			                 <select name="ExMan" class=" form-control input-sm select2 " >
			                	<option value="N"> No </option>
								<option value="Y"> Yes </option>	
			                 </select>	
			            </div>
			
			            <div class="col-md-2">
			                <label>Per Pass No</label>
			                <input type="text" id="permpassno" name="PermPassNo" value="" class=" form-control input-sm " maxlength="10"  placeholder="Permanent Pass No">
			            </div> 
			         </div>
			    </div> 			    
                <div class="form-group">
			        <div class="row">
			        
			            <div class="col-md-2">
			                <label>Date of Promotion</label>
			                <input type="text" id="dop" name="DOP" readonly="readonly" value="<%=LocalDate.now() %>" class=" form-control input-sm " >
			            </div>
			            
			            <div class="col-md-2">
			                <label>DCMAF No</label>
			                <input type="text" id="dcmafno" name="DCMAFNo" value="" class=" form-control input-sm " maxlength="20"  placeholder="DCMAF No">
			            </div>
			            
			             <div class="col-md-2">
			                <label>CHSS No</label>
			                <input type="text" id="chssno" name="CHSSNo" value="" class=" form-control input-sm " maxlength="20"  placeholder="CHSS No">
			            </div>
			            
			            <div class="col-md-2">
			                <label>ITI Credit Society No</label>
			                <input type="text" id="iticreditsocno" name="ITICreditSocNo" value="" class=" form-control input-sm " maxlength="20"  placeholder="ITI Credit Society No">
			            </div>
			            
			             <div class="col-md-2">
			                <label>Benovelent Fund No</label>
			                <input type="text" id="benovelentno" name="BenovelentNo" value="" class=" form-control input-sm " maxlength="20"  placeholder="Benovelent Fund No">
			            </div>
			            
			        </div>
			     </div>    
			    <div class="row">
			    	<div class="col-12" align="center">
			    	<button type="submit" class="btn btn-sm submit-btn" Onclick="return validateform();" name="action" value="submit" >SUBMIT</button>
			    	</div>
			    </div> 
			
				</form>
			</div>
		</div>
	
	 </div>
</div>

<script type="text/javascript">


	  
 $(document).ready(function () {
	 
	 $('#dob').daterangepicker({
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
		 
	
		    $('#doa').daterangepicker({
				
				"singleDatePicker" : true,
				"linkedCalendars" : false,
				"showCustomRangeLabel" : true,
				"minDate" :$('#dob').val(), 
				"startDate" : new Date(),
				"cancelClass" : "btn-default",
				showDropdowns : true,
				locale : {
					format : 'DD-MM-YYYY'
				}
			});
		    
			 $('#doj').daterangepicker({
					"singleDatePicker" : true,
					"linkedCalendars" : false,
					"showCustomRangeLabel" : true,
					"minDate"  : $('#dob').val(),  
					"startDate" : new Date(),
					"cancelClass" : "btn-default",
					showDropdowns : true,
					locale : {
						format : 'DD-MM-YYYY'
					}
				});
		
		 

	});
 
 $('#dob').on('change', function() { 
	    var datearray =  $('#dob').val();
	   
	    $('#doa').daterangepicker({
			
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,
			"minDate" :datearray, 
			"startDate" : new Date(),
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
	    
		 $('#doj').daterangepicker({
				"singleDatePicker" : true,
				"linkedCalendars" : false,
				"showCustomRangeLabel" : true,
				"minDate" :datearray,  
				"startDate" : new Date(),
				"cancelClass" : "btn-default",
				showDropdowns : true,
				locale : {
					format : 'DD-MM-YYYY'
				}
			});
	  });
 
 $('#EmpStatusDate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,  
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
             
 $('#dop').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,  
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		drops:'up',
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 
	 $('empstatus').select2("enable", false)
	
		$('#empname').keypress(function (e) {
	        var regex = new RegExp("^[a-zA-Z \s]+$");
	        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	        if (regex.test(str)) {
	            return true;
	        }
	        else
	        {
	        e.preventDefault();
	        alert('Please Enter Alphabate');
	        return false;
	        }
	    });
	
	
</script>
    <script type="text/javascript">
setPatternFilter($("#PunchcardTextBox"), /^-?\d*$/);
setPatternFilter($(".Phoneno"), /^-?\d*$/);
setPatternFilter($("#UANNo"), /^-?\d*$/);
setPatternFilter($("#UIDTextBox"), /^-?\d*$/);
setPatternFilter($("#SBITextBox"), /^-?\d*$/);
setPatternFilter($("#InternalNum"), /^-?\d*$/);
setPatternFilter($("#basicpaybox"), /^-?\d*$/);
setPatternFilter($("#height"), /^[0-9]*(\.[0-9]{0,2})?$/);
setPatternFilter($("#permpassno"), /^-?\d*$/);

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
<script type="text/javascript">
 $("#PunchcardTextBox").blur(function(){
		
    var punchcard =$("#PunchcardTextBox").val();
    console.log(punchcard);
    if(punchcard.length>=4){

         $.ajax({
                   url:"requestbypunchajax",
                   type:"GET",
               	  data:{PunchCardNo:punchcard},
                   dataType:'Json',
                   success:function(data){
                      var rr=data;
                      var a = parseInt(rr);
                            
                      if (a == 1){
                 	    
                 		alert("Employee No. already Exist!");
                 		 $("#PunchcardTextBox").val("");
                     	}
            }
             });


        }else{
        	
     	   $("#awailable").html("");

            }
      }); 


$("#PAN").keypress(function(event){

	console.log(event.target.value);
    var regex = new RegExp("^[a-zA-Z0-9\b]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       alert("Enter Valid PAN Number!");
       $("#PAN").html("");
       event.preventDefault();
       return false;
    }
});



function trim(el) {
    el.value = el.value.
       replace (/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
       replace (/[ ]{2,}/gi," ").       // replaces multiple spaces with one space 
       replace (/\n +/,"\n");           // Removes spaces after newlines
    return;
}

function isNumber(evt)
  {
   evt = (evt) ? evt : window.event;
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    return false;
  }
   return true; 
}




</script>
</body>
</html>
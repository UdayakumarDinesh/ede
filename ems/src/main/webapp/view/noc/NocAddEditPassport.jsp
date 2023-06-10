<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.noc.model.NocPassport"%> 
  <%@page import="com.vts.ems.pis.model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 

</head>
<style>
body{

 overflow-x: hidden;
  overflow-y: hidden;

}
</style>
<body>

  <%
  
    NocPassport passport=(NocPassport)request.getAttribute("passport");
    Object[] NocEmpList= (Object[])request.getAttribute("NocEmpList");
    Object[] EmpPassport= (Object[])request.getAttribute("EmpPassport");
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	String Empid=(String)request.getAttribute("EmpId");
	Object[] empData=(Object[])request.getAttribute("EmpData");
	
	Passport pispassport=(Passport)request.getAttribute("pispassport");
	
  
  %>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
			<% if(passport!=null){ %>
			
			  <h5>NOC Passport Edit <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			 <%}
			else { %>
			 <h5>NOC Passport Add <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			 <%} %>
			
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="Passport.htm">Passport List</a></li>
					<% if(passport!=null){ %>
					<li class="breadcrumb-item active " aria-current="page">Passport Edit</li>
					<%}
					else { %>
						<li class="breadcrumb-item active " aria-current="page">Passport Add</li>
					<% } %>
					
					
				</ol>
			</div>	
		</div>
	</div>
		
		<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){ %>
	
		<div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
		</div>
		<%}if(ses!=null){ %>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
		<%} %>
	
	
	<div class=" page card dashboard-card">
  
		<div class="card" >
			<div class="card-body" >
			<% if(passport!=null) {%>
				<form action="PassportEditSubmit.htm" method="post" autocomplete="off" >
			<%}
			else{%>
			
			<form action="PassportAddSubmit.htm" method="post" autocomplete="off" >
			
			<%} %>
			
			
				
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			         
			
			  <div class="col-md-2">
			                <label> Passport Type:</label>
			                
			                 
			                   
			                  <% if(EmpPassport!=null ) { %>
			                    <input  class="form-control input-sm " type="text" name="PassportExist" readonly value="Renewal">
			                    
			                    <%}
			                    else{%>
			                    <select name="PassportExist" class="form-control select2"  id="passporttype" required="required">
			                        
			                    	 <option value="New">New</option>
			                    	   <option value="Renewal">Renewal</option>
			                    </select>
			                   <%} %>
			                
			             
			     </div>
			
			
			            <div class="col-md-2">
			                <label>Name</label> 
			            	<input type="text" name="Name" value="<%=NocEmpList[0] %>" class="form-control input-sm" readonly >
			            </div>			
			            	
			            <div class="col-md-2">
			                <label>Designation</label>
			                <input type="text" id="" name="Designation"    value="<%=NocEmpList[1] %>"
			                    class=" form-control input-sm " readonly
			                    >
			            </div>
			            <div class="col-md-2">
			                <label> Department</label>
			                <input type="text" id="" name="Department"    value="<%=NocEmpList[2] %>"
			                    class=" form-control input-sm " readonly
			                     >
			            </div>
			             <div class="col-md-4">
			                <label>Present  Address </label>
			                <input type="text" id="" name="Present"    value="<%=NocEmpList[3] %>,<%=NocEmpList[6] %>,<%=NocEmpList[7] %> <%=NocEmpList[8] %>"
			                    class=" form-control input-sm " readonly
			                    >
			            </div>
				  </div>
			    </div>
			
			    <div class="form-group">
			        <div class="row">
			          <div class="col-md-3">
			                <label> Permanent Address </label>
			                <input type="text" id="" name="Permanent"    value="<%=NocEmpList[4] %>,<%=NocEmpList[9] %>,<%=NocEmpList[10] %> <%=NocEmpList[11] %>"
			                    class=" form-control input-sm " readonly
			                    >
			            </div>
			            
			             <div class="col-md-2">
			                <label>Details of </label>
			                <select  name="RelationType" class="form-control select2"  id="reltype" >
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="F" <%if(passport!=null){ if(passport.getRelationType().toString().equals("F")){%> selected  <% }}%>>Father</option>
			                    <option value="H" <%if(passport!=null){ if(passport.getRelationType().toString().equals("H")){%> selected  <% }}%>>Husband</option>
			                    <option value="G" <%if(passport!=null){ if(passport.getRelationType().toString().equals("G")){%> selected  <% }}%>>Guardian</option>
			                </select>
			            </div> 
			            
			              <div class="col-md-2">
			                <label> Name </label>
			                <input type="text" id="relativename" name="RelationName"   value="<%if(passport!=null){ %><%=passport.getRelationName() %><%} %>"
			                   class=" form-control input-sm "  required="required" >
			                    
			            </div>
			            <div class="col-md-2">
			                <label> Occupation </label>
			                <input type="text" id="relativeoccup" name="RelationOccupation"   value="<%if(passport!=null){ %><%=passport.getRelationOccupation()%><%} %>"
			                    class="form-control input-sm "  required="required">
			                   
			            </div>
			            
			             <div class="col-md-3">
			                <label> Address </label>
			                <input type="text" id="relativeaddres" name="RelationAddress"  value="<%if(passport!=null){ %><%=passport.getRelationAddress()%><%} %>"  required="required"
			                    class="form-control input-sm " >
			             
			            </div>
			            
			           
			       </div>
			     </div>
			   
			    <div class="form-group">
			        <div class="row">
			            
			
			             <div class="col-md-6">
			                <label> Details of blood / close relations working in foreign embassy / firms in India / Abroad 
                              </label>
                             <div class="col-md-13" style="margin-left:1px;">  
			                <input type="text" name="RelationAbroad"     value="<%if(passport!=null){ %><%=passport.getRelationAbroad()%><%}else{%>NA<%} %>"
			                    class="form-control input-sm "  required="required"  >
			                   
			                    </div>
			              </div>
			              
			             <div class="col-md-6">
			                <label> Details of employment during last ten years  </label>
                            
                             <div class="col-md-13" style="margin-left:0px;">  
			                <input type="text" name="EmployementDetails"     value="<%if(passport!=null){ %><%=passport.getEmployementDetails()%><%} else{%>NA<%} %>"
			                    class="form-control input-sm "   required="required"  >
			                   
			                   </div>
			              </div>
			              
			              
			              
			          </div>
			    </div> 
			    
			    <div class="form-group" >
			  
			        <div class="row">
			         <% if( EmpPassport!=null) { %>
			         
			         <div class="col-md-2">
			                <label> Passport Type </label>
			                <input type="text" id="" name="PassportType"  value="<%=EmpPassport[0] %>"
			                    class=" form-control input-sm " readonly="readonly">
			                   
			            </div>
			            
			             <div class="col-md-2">
			                <label> Passport No. </label>
			                <input type="text" id="" name="PassportNo"    value="<%=EmpPassport[1] %>"
			                    class=" form-control input-sm "  readonly="readonly">
			                   
			            </div> 
			            
			              <div class="col-md-2">
			                <label>Date of Issue  </label>
			                <input type="text" id="" name="ValidFrom"    value="<%=rdf.format(sdf.parse(EmpPassport[2].toString()))%>"
			                   class=" form-control input-sm "   readonly="readonly">
			                    
			            </div>
			            
			            <div class="col-md-2">
			                <label>Validity </label>
			                <input type="text" id="" name="ValidTo"   value="<%=rdf.format(sdf.parse(EmpPassport[3].toString()))%>"
			                    class=" form-control input-sm "  readonly="readonly">
			                   
			            </div>
			            
			         
			       <%} %>
			      
			        <div class="col-md-2" id="ptype">
			                <label>Passport Type</label><br>
                              <select  name="PassportType" class="form-control select2"  data-live-search="true" style="width:200px;">
                              
                                     		<option value="Official" >Official</option>
					                        <option value="Diplomatic" >Diplomatic</option>
					                        <option value="Ordinary" >Ordinary</option>				                         
                              </select>
			                   
			            </div>
			            
			             <div class="col-md-2" id="status">
                        
                              <label>Status</label><br>
                              <select  name="Status" class="form-control select2"  data-live-search="true" style="width:200px;">
                              
                                     		<option value="Valid" >Valid</option>
					                        <option value="Cancelled" >Cancelled</option>
					                        <option value="Surrendered" >Surrendered</option>
					                        <option value="Lab Custody" >Lab Custody</option>
					                        <option value="HQ Custody" >HQ Custody</option>				                         
                              </select>
                       
                       </div>
			            
			             <div class="col-md-2" id="pno">
	                    
	                            <label>Passport No:</label>
	                            <input id="passportno" type="text"  class="form-control input-sm "  name="PassportNo"  maxlength="6"   onblur="checknegative(this)">
	                    
                    </div>
                    
                    <div class="col-md-1" id="validfrom">
                      
                             <label> Valid From  </label>
	                       	  <input type="text" class="form-control input-sm pisfromdate" style="width: 110px;"value="" name="ValidFrom" id="pisfromdate"   /> 
                      
                     </div>    
                     
                      <div class="col-md-1" style="margin-left:20px;" id="validtill">
	                       
	                             <label>Valid To </label>
	                       	     <input type="text" class="form-control input-sm pistodate"  style="width: 110px;" value="" name="ValidTo" id="pistodate"   />
	                      
                       </div>     
			       
			       
			         
			    	 
			    	
			        <div class="col-md-3" style="margin-left:25px;">
			                <label> Details of passport lost,if any</label>
			                <input type="text" id="" name="LostPassport"    value="<%if(passport!=null){ %><%=passport.getLostPassport()%><%} else{%>NA<%} %>" 
			                    class=" form-control input-sm "  required="required" >
			              
			       </div>
			       
			     </div>
			       
			   </div>
			         
			     
			     <div class="form-group">
			       <div class="row">
			       
			        <div class="col-md-2">
			                <label> Passport Type Required </label>
			                <select name="Passporttype" class="form-control select2"  id="Passporttype" >
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="Ordinary" <%if(passport!=null){ if(passport.getPassportType().toString().equalsIgnoreCase("Ordinary")){%> selected  <% }}%>>Ordinary</option>
			                    <option value="Official" <%if(passport!=null){ if(passport.getPassportType().toString().equalsIgnoreCase("Official")){%> selected  <% }}%>>Official</option>
			                    <option value="Diplomatic" <%if(passport!=null){ if(passport.getPassportType().toString().equalsIgnoreCase("Diplomatic")){%> selected  <% }}%>>Diplomatic</option>
			                </select>
			             
			            </div>
			            
			            
			        <div class="col-md-2">
			                <label>I certify that</label>
			                <select name="ContractualObligation" class="form-control select2"  style="width:320%;" id="Certify"  required="required">
			                
			                    <option value="N" <%if(passport!=null){ if(passport.getContractualObligation().toString().equalsIgnoreCase("N")){%> selected  <% }}%>>I am not under contractual obligation to serve STARC for any specific period </option>
			                    <option value="Y" <%if(passport!=null){ if(passport.getContractualObligation().toString().equalsIgnoreCase("Y")){%> selected  <% }}%>> I am under contractual obligation to serve STARC for a specific period</option>
			                   
			                </select>
			             
			            </div>
			            
			            
			            
			             <div class="col-md-2"  style="margin-left:420px;" id="showfromdate">
			                <label>From Date  <span class="mandatory"	style="color: red;">*</span></label>
			              
							    <input type="text"  style="width:110px;" class="form-control input-sm mydate"  value="<% if(passport!=null){%><%=rdf.format(sdf.parse(passport.getFromDate()))%><%} %>"   id="fromdate" name="fromdate"  required="required"  > 
							</div>
			           
			            <div class="col-md-2" style="margin-left:-76px;"  id="showtodate">
			                <label>To Date <span class="mandatory"	style="color: red;">*</span></label>
			            
							    <input type="text" style="width:110px;" class="form-control input-sm mydate"  value="<% if(passport!=null){%><%=rdf.format(sdf.parse(passport.getToDate()))%><%} %>"   id="todate" name="todate"  required="required"  > 
						 </div>
			            
			           </div>
			     </div>
			     
			 <div class="col-12" align="center">
			       
			       <% if(passport!=null){ %>
			    	<button type="submit" class="btn btn-sm submit-btn"  name="action" value="submit"  onclick=" return message()"  >Submit</button>
			    	<input type="hidden" name="Passportid" value="<%=passport.getNocPassportId() %>">
			    	
			    	<%}else{ %>
			    	
			    	<button type="submit" class="btn btn-sm submit-btn"  name="action" value="submit"  onclick=" return message()"  >Submit</button>
			    	
			    	<%} %>
			       
			  </div>
			    
			  <% if(passport!=null){ %>  
			  </form>
			  
			  <%}
			  else{%>
			  </form>
			 <%} %>
			</div>
		</div>
	 </div>



	
<script type="text/javascript">


 
$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	
	<%if(passport!=null && passport.getFromDate()!=null){ %>
	"startDate" : new Date("<%=passport.getFromDate()%>"),
	<%}%>
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
	"minDate" :$('#validfrom').val(),    
	<%if(passport!=null && passport.getToDate()!=null){ %>
	"startDate" : new Date("<%=passport.getToDate()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$("#fromdate").change( function(){
	var validdate = $("#fromdate").val();
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : validdate,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	
	
});	


window.onload = function() {
	
	   
	     var id=$('#Certify').val()
	 if(id=="Y"){
		 
		 $("#showfromdate").show();
		  $("#showtodate").show(); 
	}  
	 else{
	
		  $("#showfromdate").hide();
		  $("#showtodate").hide(); 
	 }
	   
	     var select=$('#passporttype').val()
	     
	     if(select=="New" || select==undefined){
	    	 
	    	 $('#ptype').hide();
	    	 $('#status').hide();
	    	 $('#pno').hide();
	    	 $('#validfrom').hide();
	    	 $('#validtill').hide();
	    	 
	    }
	     else{
	    	 
	    	 $('#ptype').show();
	    	 $('#status').show();
	    	 $('#pno').show();
	    	 $('#validfrom').show();
	    	 $('#validtill').show();
	    	 
	     }
	};
	
	$(document).ready(function() {
	    $('#Certify').on('change', function() {
	    var selectedValue = $(this).val();
	    if(selectedValue=="Y"){
	    	
	    	$('#showfromdate').show();
	    	$('#showtodate').show();
	    }
	    else{
	    	
	    	$('#showfromdate').hide();
	    	$('#showtodate').hide();
	    }
	    
	    });
	  });
	
	
	
	$('#pisfromdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		
		<% if(pispassport!=null && pispassport.getValidFrom()!=null){ %>
		"startDate" : new Date("<%=pispassport.getValidFrom()%>"),
		<% } %>
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});


	$('#pistodate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :$('#pisfromdate').val(),    
		<% if(pispassport!=null && pispassport.getValidTo()!=null){ %>
		"startDate" : new Date("<%=pispassport.getValidTo()%>"),
		<% } %>
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	
	
		
	
	$(document).ready(function() {
	    $('#passporttype').on('change', function() {
	    var selectedValue = $(this).val();
	    if(selectedValue=="New"){
	    	
	    	 $('#ptype').hide();
	    	 $('#status').hide();
	    	 $('#pno').hide();
	    	 $('#validfrom').hide();
	    	 $('#validtill').hide();
	    	
	    }
	    else{
	    	
	    	 $('#ptype').show();
	    	 $('#status').show();
	    	 $('#pno').show();
	    	 $('#validfrom').show();
	    	 $('#validtill').show();
	    	
	    }
	    if(selectedValue=="Renewal"){
	    	
	    	
	    	$('#passportno').attr('required', true);
	    	
	    }
	    
	    
	    });
	  });
	
		    
		    
</script>

<script>

function checknegative(str) {
    if (parseFloat(document.getElementById(str.id).value) < 0) {
        document.getElementById(str.id).value = "";
        document.getElementById(str.id).focus();
        alert('Negative Values Not allowed');
        return false;
    }
}

setPatternFilter($("#passportno"), /^-?\d*$/);
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

function message()
{
	
	var x = confirm("Are you sure To Submit?");
	
	if(x){
		
		
		 var relationtype =$("#reltype").val();
		 var relativename =$("#relativename").val();
		 
		 var relativeoccupation =$("#relativeoccup").val();
		 var relativeaddres=$("#relativeaddres").val();
		 var Passporttyperequired=$("#Passporttype").val();
		 
		 
		 if(relationtype==null || relationtype=="" || relationtype=="null"){
			 alert("Enter Details Of");
		       event.preventDefault();
		       return false;
		}
		if(relativename==null || relativename=="" || relativename=="null"){
			 alert("Enter Name!");
		       event.preventDefault();
		       return false;
		}
		if(relativeoccupation==null || relativeoccupation=="" || relativeoccupation=="null"){
			 alert("Enter Occupation!");
		       event.preventDefault();
		       return false;
		}
	
		if(relativeaddres==null || relativeaddres=="" || relativeaddres=="null"){
			 alert("Enter Address!");
		       event.preventDefault();
		       return false;
		}
		if(Passporttyperequired==null ||  Passporttyperequired=="" || Passporttyperequired=="null"){
			 alert("Enter Passport Type Required!");
		       event.preventDefault();
		       return false;
		}
		
		
		return true;
		  
	  }else{
	 return false;
	 
	  }
}




</script>

</body>
</html>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@ page language="java" %>
<!DOCTYPE html>
<%@page import="java.util.*,java.text.SimpleDateFormat"%>
<html>
<head>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">

.dashboard-card{
	overflow-x:hidden !important ; 
}

 #dis{
 text-align: center;
 }
 
.btn-group1 button {
  background-color: #344b8a; /* Green background */
  color: white; /* White text */
  padding: 10px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  float: left; /* Float the buttons side by side */
}

/* Clear floats (clearfix hack) */
.btn-group1:after {
  content: "";
  clear: both;
  display: table;
}

.btn-group1 button:not(:last-child) {
  border-right:thin;
}

/* Add a background color on hover */
.btn-group1 button:hover {
  background-color: #3e8e41;
}
.card-body{
padding: 0.25rem 0.25rem 0.25rem 0.25rem !important;
}

.navbar{
padding: 0.05rem 0rem 0.05rem 0rem !important;
}

.card-header {
    padding: 0.7rem 0.4rem 0.3rem 0.4rem !important;
    }
    
 span, label{
 font-weight: bold !important;
 }   
 .appl{
 padding: 2px 6px 2px 6px;
 }
 
 .nav-link span{
 	font-weight: 100 !important;
 }
 
 .table thead tr{
	background-color: white;
	color: black;
	font-size: 13px !important;
}
</style>
</head>
<body style="overflow-x: hidden !important ">

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>LEAVE Modify</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item "><a href="LeaveApply.htm">Leave Apply</a></li>
						<li class="breadcrumb-item active " aria-current="page">Leave Modify</li>
					</ol>
				</div>
			</div>
	</div>
	
 <% Object[] leav=(Object[])request.getAttribute("leavedetails");
    List<Object[]> empdetails=(List<Object[]>)request.getAttribute("empdetails");
    List<Object[]> officerdetails=(List<Object[]>)request.getAttribute("officerdetails");
    List<Object[]> emplist=(List<Object[]>)request.getAttribute("EmpList");
    LeaveRegister register=(LeaveRegister)request.getAttribute("register");
    String empNo=(String)request.getAttribute("EmpNo");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    long roleid=(Long)session.getAttribute("FormRole");
	   %>	
<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >
     <div class="row">
			<div class="col-md-4">
			 <div  class="card">
	            <div class="card-header">
	                <span class="h6">Current Leave Balance</span>
	            </div>
	            <div class="card-body">
	            <div class="table-responsive">
	                <table class="table table-bordered table-hover table-striped table-condensed">
	                    <thead>
	                        <tr>
	                            <th>CL</th>
	                            <th>EL</th>
	                            <th>HPL/CML</th>
	                            <th>RH</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                           
	                         <tr>
	                            <td><%=register.getCL() %></td>
	                            <td><%=register.getEL() %></td>
	                            <td><%=register.getHPL() %>/<%=register.getHPL()/2 %></td>
	                            <td><%=register.getRH() %></td>    
	                        </tr>
	                        
	                        
	                        
	                      
	                    </tbody>
	                </table>
	                </div>
	            </div>
	        </div><!-- / current-leave-balance-->
	        
	         <!--Holidays-->
	        <div  class="card" style="margin-top:7px;">
	            <div class="card-header">
	                <span class="h6">Important Dates</span>
	            </div>
	           
	                    <ul class="nav nav-tabs" >
	                        <li class="nav-item"><a style="font-size: 11px !important;" class="nav-link  active" id="upcoming" href="#" onclick="getHoliday('U','upcoming')">Upcoming</a></li>
	                        <li class="nav-item"><a style="font-size: 11px !important;" class="nav-link" id="gen" href="#" onclick="getHoliday('G','gen')">GN</a></li>
	                        <li class="nav-item"><a style="font-size: 11px !important;" class="nav-link" id="rh" href="#" onclick="getHoliday('R','rh')">RH</a></li>
	                        <li class="nav-item"><a style="font-size: 11px !important;" class="nav-link" id="wor" href="#" onclick="getHoliday('W','wor')">Wor</a></li>
	                        <li class="nav-item"><a style="font-size: 11px !important;" class="nav-link" id="hol" href="#" onclick="getHoliday('H','hol')">Hol</a></li>
	                    </ul> 
	                    
	                    
	                    
	            <div class="card-body">
	                 
	                   <div class="table-responsive">
	                        <table class="table table-bordered table-hover table-striped table-condensed">
	                            <thead>
	                            <tr>
	                                  <th>Date</th>
	                                  <th style=" text-align: left;">Event</th> 
	                                    
	                            </tr>
	                            </thead>
	                            <tbody id="other-list-table"  >
	                       
	                          </tbody>
	                        </table>
	              
	                    
	                    
	                    
	                 </div>   
	                
	            </div>
	        </div>
	        <!--Holidays-->
			</div>
			<!--Leave Apply  -->
			
			<%
			List<Object[]> leaveType=(List<Object[]>)request.getAttribute("leaveType");
			List<Object[]> purposeList=(List<Object[]>)request.getAttribute("purposeList");
			%>
			<div class="col-md-8">
			      <div  class="card">
	                    <div class="card-header">
                                 <div  id="successmsg" style='font-size: 16px;  font-weight: bold;' class="text-center">
	                                  <%if(request.getParameter("result")!=null){
	                                      %>
	                                  <span style="color:green; display:block;">Successfully Applied</span>
	                                  <%}if(request.getParameter("resultfail")!=null){%>
	                                  <span style="color:red;">Some Error Occur While Applying</span>
	                                     <%} %>
	   
	                                 <%if(request.getParameter("SameDateHandingOverAlreadyPresent")!=null){%>
	                                 <span style="color:red;">Same Date And Employee Handing Over Already Present</span>
	                                    <%}%>
	   
	                                          </div>
	                                           <div id="sp" class="text-center" style=" "></div>
                                
                                  <!-- Recc -->  
                     <%if(empdetails!=null&&empdetails.size()>0){ 
                       for(Object[] obj:empdetails){ 
                       %>       <div class="row" style="margin-top:0px;" >
                                <div class="col-sm-1"></div>
	                            <div class="col-sm-4" style="margin-top:10px; text-align: left;; color: green;"><b class="h5">Leave Application </b>  </div>  
	                            <div class="col-sm-6" style="margin-top:10px; text-align: right; color: green;"> <b class="h6" style="margin-left: 10px;"><%=obj[1] %></b>, <small> <%=obj[2] %> </small> </div>
	                            </div>         
                              
	                  <%}} %>
	                    <!-- / Recc -->
	                  <!-- // reset apply and check button -->
	                    </div>
	                  <div class="card-body">
                           <form action="apply-leave-add.htm" method="post">
	                    
	                    
	                    <!-- Leave Type-->
	                    <div class="form-group">
	                        <div class="row" style="margin-top:10px; ">
	                            <div class="col-sm-3" align="right"><label for="leaveType">Leave Type : </label></div>
	                            
	                              <div class="col-sm-3">
	                                <select id="leaveType"  name="leavetypecode" class="form-control selectpicker" onChange="leavecheck(); cmlMessage();" required="required" >
	                                  <% if(leaveType!=null&&leaveType.size()>0){
	                                       for(Object[] l:leaveType){
                                              if(l[0].toString().equalsIgnoreCase(leav[7].toString())){
	                                       %>
	                                       <option value="<%=l[0]%>"><%=l[1]%> </option>
	                                       <%}}}%>
	                                </select>
	                              </div>
	                              <div id="fullhalfdiv"  class="col-sm-3">
	                                <select  id="halforfull" name="fullhalf" class="form-control selectpicker" onChange="halffull()" required="required">
	                                    <option value="X" 
	                                    <%if("X".equalsIgnoreCase(leav[9].toString())){
	                                       %>
	                                       selected="selected"
	                                       <%}%>
	                                    >Full</option>
	                                     <option value="H" <%if("A".equalsIgnoreCase(leav[9].toString())||"F".equalsIgnoreCase(leav[9].toString())){
	                                       %>
	                                       selected="selected"
	                                       <%}%>>Half</option>
	                                     <option value="T" <%if("T".equalsIgnoreCase(leav[9].toString())){
	                                       %>
	                                       selected="selected"
	                                       <%}%>>Hours</option>
	                                </select>
	                            </div>
	                            <div id="hours"  class="col-sm-2">
	                                <select   name="hours" class="form-control selectpicker"  title="Hours">
	                                     <option value="0.1" >1</option>
	                                     <option value="0.2" >2</option>
	                                     <option value="0.3" >3</option>
	                                     <option value="0.4" >4</option>
	                                     <option value="0.5" >5</option>
	                                     <option value="0.6" >6</option>
	                                </select>
	                            </div>
	                              
	                              </div>
	                              </div>
	                              <!-- EL Encashment -->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-sm-3" align="right">
	                                <label for="from">LTC ENCASH : </label>
	                            </div>
	                            <div class="col-sm-3">
	                                <select id="Elcash" name="elcash" class="form-control selectpicker">
	                                    <option value="N"
	                                    <%if("N".equalsIgnoreCase(leav[12].toString())){
	                                       %>
	                                       selected="selected"
	                                       <%}%>>No</option>
	                                    <option value="Y"
	                                    <%if("Y".equalsIgnoreCase(leav[12].toString())){
	                                       %>
	                                       selected="selected"
	                                       <%}%>
	                                    >Yes</option>
	                                    
	                                </select>
	                            </div>
	                            
	                            
	                             <div  id="fnandiv" class="col-sm-3">
	                                <select  id="anorfn" name="anfn"  class="form-control selectpicker" onChange="halffull()" >
	                                    <option value="F" <%if("F".equalsIgnoreCase(leav[9].toString())){
	                                       %>
	                                       selected="selected"
	                                       <%}%>>FN</option>
	                                    <option value="A"
	                                    <%if("A".equalsIgnoreCase(leav[9].toString())){
	                                       %>
	                                       selected="selected"
	                                       <%}%>>AN</option>
	                                    
	                                </select>
	                            </div>
	                       
	                       
	                            
	                            
	                               
	                        </div>
	                    </div>
	                    <!--ElEncashment  --> 
	                            <!-- date from to -->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-sm-3" align="right" style="margin-top: 5px;">
	                                <label for="fromApplyDate">Date : </label>
	                            </div>
	                            
	                                
	                             <div class="col-sm-1" align="right" style="margin-top: 5px;">
	                             <b><span id="spanfrom">From</span></b>   
	                             </div>
	                            
	                            <div id="fromdiv" class="col-sm-3" align="left">
	                             <input id="fromApplyDate" type="text" class="form-control input-sm from" placeholder="from" onChange="fromDatefun()" name="startdate"  value="<%=sdf.format(leav[3])%>" maxlength="10" >
	                            </div>
	                            <div class="col-sm-1" align="right" style="margin-top: 5px;">
	                             <b><span id="spanto">To</span></b>    
	                             </div>
	                            <div  id="todiv" class="col-sm-3" align="left">
	                            <input id="toApplyDate"  type="text" class="form-control input-sm"  onchange="toDatefun()" name="enddate"   value="<%=sdf.format(leav[4])%>"  maxlength="10" data-toggle="tooltip" data-placement="bottom" title="Please Select From Date First">
	                            </div>
	                           
	                           
	                            
	                             
	                        </div>
	                    </div>
	                     <!--/date from to  -->  
	                           <!-- Purpose of leave -->
	                    <div   class="form-group">
	                        <div class="row">
	                            <div class="col-sm-3" align="right">
	                                <label for="from">Purpose : </label>
	                            </div>
	                            <div class="col-sm-8">
	                                <select class="form-control selectpicker" name="leavepurpose" required="required">
	                                   
	                                   <%  for(Object[] pl:purposeList) {	                                 
	                                   %>
	                                    <option value="<%=pl[1]%>" 
	                                    <%if(pl[1].toString().equalsIgnoreCase(leav[1].toString())){
	                                       %>
	                                       selected="selected"
	                                       <%}%>
	                                    ><%=pl[1]%></option>
	                                  
	                                   <%} %>
	                                       
	                                </select>    
	                            </div>
	                        </div>    
	                    </div>
	                    <!-- /. Purpose of leave:-->
	                    
	                    
	                    <input  type="hidden" name="empNo"  value="<%=empNo%>">
	                   <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	                    <!-- Leave address -->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-sm-3" align="right">
	                                <label for="from">Leave address : </label>
	                            </div> 
	                            <div class="col-sm-8">
	                                <textarea class="form-control"  name="leaveadd" required><%=leav[2]%></textarea>
	                            </div>
	                        </div>
	                    </div>
	                    <!-- / .leaveAddress-->
	                    
	                    <!-- Remarks -->
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-sm-3" align="right">
	                                <label for="from">Remarks : </label>
	                            </div>
	                            <div class="col-sm-8">
	                                <input id="from" class="form-control input-sm" name="remark" value="<%=leav[11]%>" placeholder="Enter Some Remark">
	                            </div>
	                        </div>
	                    </div>
	                    <!-- / Remarks -->    
	                    <!-- HandingOver To -->

	                 
	                    <div class="form-group">
	                        <div class="row">
	                            <div class="col-sm-3" align="right">
	                                <label for="from">Handing Over To:</label>
	                            </div>
	                            <div class="col-sm-8">
	                                <select name="HandingOverEmpid" class="form-control input-sm selectpicker" data-live-search="true">
	                                
	                                 <option value="NotSelected" selected="selected">Not Applicable</option>
	                                 <%if(emplist!=null&&emplist.size()>0){
	                                   for(Object[] ls:emplist){
	                                   if(!ls[0].toString().equalsIgnoreCase(empNo)){
	                                   %>
                                       <option value="<%=ls[0]%>"><%=ls[1]%> (<%=ls[0]%>)-<%=ls[2]%></option>
                                     <%}}}%>
	                                </select>
	                            </div>
	                        </div>
	                    </div>
	                   
	                     <!--  reset apply and check button -->
	                    <div class="form-group">
	                    	<div class="row">
	                    		<div class="col-sm-1"></div>
	                    		<div class="col-sm-2">
	                    			<button class="btn  btn-block reset-btn" type="reset" onclick="resetform()" >Reset</button>
	                    		</div>
	                    		
	                    		<div id="submit" class="col-sm-8">
	                    			<button  type="submit" class="btn btn-success btn-block" name="ApplyLeaveSave" value=ApplyLeaveSave>Apply leave</button>
	                    		</div>
	                    		
	                    		<div id="check" class="col-sm-8">
	                    			<button  type="button" class="btn btn-primary btn-block" onclick="show()">Check leave</button>
	                    		</div>
	                    		
	                    	</div>
	                    </div> 
	                              
	                              </form>
                        </div>
                        </div>
                        
                       
			</div>
			<!--Leave Apply End  -->
		</div>
		</div>
		</div>
	



<script type="text/javascript">
  $( document ).ready(function() {
	  getHoliday('U','upcoming');
    });

	function getHoliday(type,id){
		$(".nav-link").removeClass("active");
		$('#'+id).addClass('active');
		
		$.ajax({
			
			type : "GET",
			url : "GetHolidays.htm",
			data : {
				type:type
				
			},
			datatype : 'json',
			success : function(result) {
			var result = JSON.parse(result);
			var consultVals= Object.keys(result).map(function(e){
			return result[e]
			})
			

			
			var otherHTMLStr = '';
			for(var c=0;c<consultVals.length;c++)
			{
				var other = consultVals[c];
			
				otherHTMLStr +=	'<tr> ';
				otherHTMLStr +=	'	<td  style="text-align: center;" >'+ other[0] +'</td> ';
				otherHTMLStr +=	'	<td  style="text-align: left;" >'+ other[1] +' </td> ';
				otherHTMLStr +=	'</tr> ';

				
				
			}
			
			if(consultVals.length>0){
				$('.downloadtable').css('display','block');
			}
			
			$('#other-list-table').html(otherHTMLStr);

				
			}
		});
		
		
		
	}


function Edit(myfrm){
	
	 var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	}
	 
	var cnf=confirm("Are U Sure To Edit!");
	  if(cnf){
	
		 
	
		  return true;
	  }
	  
	  
	  else{
		  event.preventDefault();
			return false;
			}
			
	}
function Delete(myfrm){
	

	var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	
	}
	  var cnf=confirm("Are U Sure To Cancel!");
	  if(cnf){
	
	return true;
	
	}
	  else{
		  event.preventDefault();
			return false;
			}
	
	}

var fromApplyDate, toApplyDate;


fromApplyDate = $('#fromApplyDate');
toApplyDate = $('#toApplyDate');

$(fromApplyDate).daterangepicker({
	"singleDatePicker": true,
	"showDropdowns": true,
	"showCustomRangeLabel": true,
	"cancelClass": "btn-default",
	autoUpdateInput: false,
	locale: {
		format: 'DD-MM-YYYY',    
	}
});

$(fromApplyDate).on('apply.daterangepicker', function(ev, picker) {
	$(this).val(picker.startDate.format('DD-MM-YYYY'));
	if($(this).val != "")
	   {
		$('#toApplyDate').attr('disabled', false).daterangepicker({
		"minDate": $(this).val(),
		"startDate":$(this).val(),
		"singleDatePicker": true,
		locale: {
    	format: 'DD-MM-YYYY'
		}
	}); 	
	}			
});


function fromDatefun()
{
	
	$("#submit").hide(); //hide submit button (after check not allow to submit if some changes)
	$("#check").show();
	
	 var ssuccessmsg=document.getElementById("successmsg");
	 successmsg.innerHTML="     ";
	 
	 var sp=document.getElementById("sp");
     sp.innerHTML="     ";
} 



function toDatefun()
{
	$("#submit").hide(); //hide submit button (after check not allow to submit if some changes)
	$("#check").show();
	
	 var ssuccessmsg=document.getElementById("successmsg");
	 successmsg.innerHTML="     ";
	 
	 
	 var sp=document.getElementById("sp");
     sp.innerHTML="     ";
}

$(document).ready(function(){

	$("#submit").hide();
	$("#hours").hide();
	$("#anorfn").prop("disabled", true);
	$(".selectpicker[data-id='anorfn']").addClass("disabled");
	$('.selectpicker').selectpicker('refresh');
});

function cmlMessage()
{
	var leavetype=document.getElementById("leaveType").value;
	
	 if(leavetype==0003)
	 	{
		 
		 var sp=document.getElementById("sp");
	     sp.innerHTML="<b style='color:red; text-align: center; font-size: 20px;'>Please Upload MC  Cerificate After Apply CML Leave</b>";
	 	}
	
	
}



var anfn="X";

function leavecheck()
{
	var leavetype=document.getElementById("leaveType").value;
	$("#submit").hide(); // hide submit button (after check not allow to submit if some changes)
	$("#check").show();
	$("#hours").hide();
	
	 var ssuccessmsg=document.getElementById("successmsg");
	 successmsg.innerHTML="     ";
	 
	 var sp=document.getElementById("sp");
     sp.innerHTML="     ";
	
	 if(leavetype==0001)
 	{
		 var halforfull=document.getElementById("halforfull").value; 
		 if(halforfull=="H")
		 {$("#todiv").hide();//from date disabled
		 var spanfrom=document.getElementById("spanfrom");
		 spanfrom.innerHTML ="On";
		 var spanfrom=document.getElementById("spanto");
		 spanfrom.innerHTML =" ";
		 }
		
		 
 	 $("#fullhalfdiv").show();
     $("#fnandiv").show();
 	}
 else
 	{
	 $("#todiv").show();//from date enabled
	 var spanfrom=document.getElementById("spanfrom");
	 spanfrom.innerHTML ="From";
	 var spanfrom=document.getElementById("spanto");
	 spanfrom.innerHTML ="To";
	 
	 
	 
 	 $("#fullhalfdiv").hide();
     $("#fnandiv").hide();
 	}
		

 }
 

function halffull()
{
	
	
	$("#submit").hide(); //hide submit button (after check not allow to submit if some changes)
	$("#check").show();
	 var ssuccessmsg=document.getElementById("successmsg");
	 successmsg.innerHTML="     ";
	
	 var sp=document.getElementById("sp");
     sp.innerHTML="     ";
	 
	 
	 
	 var halforfull=document.getElementById("halforfull").value;
	 var leavetype=document.getElementById("leaveType").value;
	 
	 if(halforfull=="X")
	{
	$("#hours").hide();	 
	$("#anorfn").prop("disabled", true);
	$(".selectpicker[data-id='anorfn']").addClass("disabled");
	$('.selectpicker').selectpicker('refresh'); //fn/an dropdown disabled
	$("#todiv").show();//fromd date enabled
	
	 var spanfrom=document.getElementById("spanfrom");
	 spanfrom.innerHTML ="From";
	 var spanfrom=document.getElementById("spanto");
	 spanfrom.innerHTML ="To";
	anfn="X";
	
	}
	
	 if((halforfull=="H"||halforfull=="T")&&leavetype=="0001")
	{
		 
		
			
					$('#toApplyDate').attr('disabled', false).daterangepicker({
					"minDate": $('#fromApplyDate').val(),
					"startDate":$('#fromApplyDate').val(),
					"singleDatePicker": true,
					locale: {
			    	format: 'DD-MM-YYYY'
					}
				    }); 			
		 $("#anorfn").prop("disabled", false);
			$(".selectpicker[data-id='anorfn']").removeClass("disabled");
			$('.selectpicker').selectpicker('refresh');// fn/an dropdown enabled
		$("#todiv").hide();//fromd date disabled
		 var spanfrom=document.getElementById("spanfrom");
		 spanfrom.innerHTML ="On";
		 var spanfrom=document.getElementById("spanto");
		 spanfrom.innerHTML =" ";
		
		var anorfn=document.getElementById("anorfn").value;
		
		if(anorfn=="F")
			{
			anfn="F";
			}
		else{anfn="A";}	
		
		if(halforfull=="T"){
			$("#hours").show();
		}
			
	}
	 
}

 function resetform()
 {
	 
	
	 $("#hours").hide();
	 $("#submit").hide();
	 $("#check").show();
	 $("#fullhalfdiv").show();
     $("#fnandiv").show(); 
     $("#anorfn").prop("disabled", true);
     $(".selectpicker[data-id='anorfn']").addClass("disabled");
 	 $('.selectpicker').selectpicker('refresh');

    // halffull();
    $("#todiv").show();//fromd date enabled
	
	 var spanfrom=document.getElementById("spanfrom");
	 spanfrom.innerHTML ="From";
	 var spanfrom=document.getElementById("spanto");
	 spanfrom.innerHTML ="To";
	 anfn="X";
    
     
     var sp=document.getElementById("sp");
     sp.innerHTML="     ";
     
     var ssuccessmsg=document.getElementById("successmsg");
	 successmsg.innerHTML="     ";
     
 }
 
 function show()
 {
	var empno="<%=empNo%>"; 
 	var leavetype=document.getElementById("leaveType").value;
 	//var anfn=document.getElementById("anfn").value;
 	var halforfull=document.getElementById("halforfull").value;
 	var hours=document.getElementById("hours").value;
 	var ELCash=document.getElementById("Elcash").value;
 	var fdate=document.getElementById("fromApplyDate").value;
 	var tdate=document.getElementById("toApplyDate").value;
 	
 	$.ajax({
		
		type : "GET",
		url : "GetLeaveChecked.htm",
		data : {

			empno:empno,
			leavetype:leavetype,
			ELCash:ELCash,
			fdate:fdate,
			tdate:tdate,
			halforfull:halforfull,
			hours:hours,
			
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		 var sp=document.getElementById("sp");
		if(result[1]=='Fail'){
	     sp.innerHTML="<b style='color:red; text-align: center; font-size: 17px;'>"+result[0]+"</b>";
		}else if(result[1]=='Pass'){
			 $("#check").hide();
			 $("#submit").show();
			 sp.innerHTML="<b style='color:green; text-align: center; font-size: 17px;'>"+result[0]+"</b><br><b style='color:orange; text-align: center; font-size: 15px;margin-left:10px;'>No Of Days: "+result[2]+"</b>";
		}
		
		
			
		}
	});
 }
</script>
</body>
</html>


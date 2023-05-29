<%@page import="java.time.LocalTime"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Insert title here</title>
<style type="text/css">
body{

 overflow-x: hidden;
  overflow-y: hidden;

}
.table thead th {
    color: white;
   
    text-align: center;
    padding-bottom: 0.1rem !important;
    padding-top: 0.1rem !important;
}
.trup
		{
			padding:5px 10px 0px 10px ;			
			border-top-left-radius : 5px; 
			border-top-right-radius: 5px;
			font-size: 14px;
			font-weight: 600;
		}
		
		.trdown
		{
			padding:0px 10px 5px 10px ;			
			border-bottom-left-radius : 5px; 
			border-bottom-right-radius: 5px;
			font-size: 14px;
			font-weight: 600;
		}

</style>
</head>
<body>
<%
 Object[] emplist = (Object[])request.getAttribute("ApprovalEmp"); 
List<Object[]> ModeOfTravelList=(List<Object[]>)request.getAttribute("ModeOfTravelList");
List<Object[]> CityList=(List<Object[]>)request.getAttribute("CityList");
Object[] empdata  = (Object[])request.getAttribute("Empdata");
int paylevel = 0;
if(empdata!=null && empdata[4]!=null){
	paylevel= Integer.parseInt(empdata[4].toString());
}
%>
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
				<h5>Add Tour Program <small><b>&nbsp;&nbsp;&nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%>
						</b></small></h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="TourProgram.htm"> Tour </a></li>
						<li class="breadcrumb-item active " aria-current="page">Add Tour Program </li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 
	<div class="page card dashboard-card">
		<div class="card-body" >
				<div class="row">
					<div class="col-12">
						<div class="card">
							<div class="card-header" style="height: 43px;"> <h4>Tour Application  	<span id="sp" style=" float: right;"></span></h4>
							</div>					
							<div class="card-body">
								<form action="TourApplyAdd.htm" method="post" autocomplete="off" id="TourRequestForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								    <div class="form-group">
								        <div class="row">
								        	<div class="col-md-2">
										 		<label>Departure Date :<span class="mandatory">*</span></label>
												<div class=" input-group">
												    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="doD" name="DepartureDate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
										     </div>
										     
										     <div class="col-md-2">
										 		<label>Arrival Date :<span class="mandatory">*</span></label>
												<div class=" input-group">
												    <input type="text" class="form-control input-sm " readonly="readonly"  placeholder=""  id="doA" name="ArrivalDate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
										     </div>
										     <div class="col-md-4">					        	
									                <label>Place Of Stay :<span class=" mandatory ">*</span></label>
									                <input type="text" value="" name="POS" id="pos" class=" form-control input-sm " maxlength="255"   placeholder="Enter the Place Of Stay "  required="required">
											</div>
											<div class="col-md-4">					        	
									                <label>Purpose :<span class=" mandatory ">*</span></label>
									                <input type="text" value="" name="Purpose" id="purpose" class=" form-control input-sm " maxlength="255"   placeholder="Enter the Purpose "  required="required">
											</div>
									    
										</div>
									</div>
									<div class="form-group">
								        <div class="row">
								        	<div class="col-md-3">
										 		<label>EJP From :<span class="mandatory">*</span></label>
												<div class=" input-group">
												    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="EJPFROM" name="ejpfrom"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
										     </div>
										     
										     <div class="col-md-3">
										 		<label>EJP To :<span class="mandatory">*</span></label>
												<div class=" input-group">
												    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="EJPTO" name="ejpto"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
										     </div>
										     
										<%if(paylevel >= 6){%>
											 <div class="col-md-3">
											 	<label>  Justification for Air Travel : </label>
									    		 <div class=" input-group">
											         <input type="text" value="" name="airtraveljusti" id="airtraveljusti" class=" form-control input-sm " maxlength="255"   placeholder="Enter Air Travel Justification  "  required="required">
												</div>
											</div>
											<div class="col-md-3">
												  <label >Advance Required : </label>
												  <select class="form-control test-type fromcity selectpicker "  style="width: 100%" data-size="auto" id="isamtreq" name="reqadvamt" onchange="HideTourAdv()" data-live-search="true" data-container="body" >
													     <option value="N">No</option>
							                             <option value="Y">Yes</option>
							                       </select> 
											 </div>
										<%}else{%>
											<div class="col-md-6">
												 <label >Advance Required : </label>
												  <select class="form-control test-type fromcity selectpicker "  style="width: 100%" data-size="auto" id="isamtreq" name="reqadvamt" onchange="HideTourAdv()" data-live-search="true" data-container="body" >
													     <option value="N">No</option>
							                             <option value="Y">Yes</option>
							                       </select> 
											 </div>
										<%}%>
								        </div>
								    </div>
									  
						<div id="advAmountreq" style="display: none;">
						<hr>
								  <div class="form-group" >
								    <label>1) Air / Train / Bus Fare From & To :</label> 
								    	<div class="row">
								    	 <div class="col-md-1">	</div>
								    	 
								    	    <div class="col-md-3">
									    		<label>  Total fare : </label>
									    		 <div class=" input-group">
												    <input  type="text"  name="tourfare"  id="tourfare" class=" form-control input-sm " placeholder="Enter Total fare Amount"   value=""  maxlength="12" required="required">                     
												</div>
									   		</div>
								    	 
								    		 <div class="col-md-2">	
									    		 <label> From Date :</label>
									    		 <div class=" input-group">
												    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="farefromdate" name="farefromdate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
								    		 </div>
								    		 
								    		 <div class="col-md-2">	
									    		 <label> To Date :</label>
									    		 <div class=" input-group">
												    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="faretodate" name="faretodate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
								    		 </div>	
								    		 
								    		  <div class="col-md-3">
									    		<label> Total Proposed Amt : </label>
									    		 <div class=" input-group">
												    <input  type="text"  name="totalproposed"  id="totalproposed" class=" form-control input-sm " placeholder="Enter Total Proposed Amount"   value=""  maxlength="12" required="required">                     
												</div>
									   		</div>
								    		 
								  </div>
								</div>    
								
								<div class="form-group" >
									<label> 2) Daily Allowance  & Boarding Charges :</label>
									<div class="row">
										
										<div class="col-md-2">
											<label>Boarding Days :</label>
											<div class=" input-group">
												<input  type="text"  name="boardingdays"  id="boardingdays" class=" form-control input-sm " placeholder="Enter No of Days"   value=""  maxlength="12" required="required">                     
											</div>
										</div>
										
										<div class="col-md-2">
											<label>Charges Per Days :</label>
											<div class=" input-group">
												<input  type="text"  name="boardingperday"  id="boardingperday" class=" form-control input-sm " placeholder="Enter  Amount"   value=""  maxlength="12" required="required">                     
											</div>
										</div>
									
										
										
										<div class="col-md-2">
											<label>Allowance Days :</label>
											<div class=" input-group">
												<input  type="text"  name="allowancenoday"  id="allowancenoday" class=" form-control input-sm " placeholder="Enter Allowance Days"   value=""  maxlength="12" required="required">                     
											</div>
										</div>
										
										<div class="col-md-2">
											<label>Allowance Per Days :</label>
											<div class=" input-group">
												<input  type="text"  name="allowanceperday"  id="allowanceperday" class=" form-control input-sm " placeholder="Enter Amt Per Days"   value=""  maxlength="12" required="required">                     
											</div>
										</div>
										<div class="col-md-2">	
									    	<label style="margin-left: -5px;"> Allowance From Date :</label>
									    		<div class=" input-group">
												    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="allowancefromdate" name="allowancefromdate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
								    	</div>
								    		 
								    	<div class="col-md-2">	
									    	<label> Allowance To Date :</label>
									    		<div class=" input-group">
												    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="allowancetodate" name="allowancetodate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												 </label>                    
											</div>
								    	</div>
										
									</div>
								</div>
								<hr>
								</div>
								    <div class="form-group">
								    	 <label> Earliest presence required at destination :</label>
								        <div class="row">
									        		  <div class="col-md-2"></div>
										       		  <div class="col-md-2">	  	
															<div class=" input-group">
																<label>Time :<span class=" mandatory ">*</span></label>&nbsp;&nbsp;&nbsp;
															     <input  class="form-control" id="earliesttime" placeholder="Start Time"   name="EarliestTime" value="<%=LocalTime.now() %>"  maxlength="250" required="required">                     
															</div>
													  </div>
													 
													  <div class="col-md-2">			
															<div class=" input-group" style="width: 184px;">
															<label>Date :<span class=" mandatory ">*</span></label>	&nbsp;&nbsp;&nbsp;&nbsp;        	
		  													<input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="earliestdate" name="EarliestDate"  required="required"  > 
														    <label class="input-group-addon btn" for="testdate"></label>      													
														    </div>
													  </div>
													 
													   <div class="col-md-3">			
															<div class=" input-group">
																<label>Place :<span class=" mandatory ">*</span></label>	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        	
															     <input  class=" form-control input-sm " id="earliestplace" placeholder="Place"   name="EarliestPlace" value=""  maxlength="250" required="required">                     
															</div>
													   </div>
								        </div>
								    </div> 				    
				 <!-- td Onward --> 
	         <div class="form-group">
	              <div class="row">
	            	  <div class="col-md-12">
	              			<table class="table " id="myTable20" >
								  <thead style="background-color: white; color: black;">
										<tr>
											<th> Date</th>
											<th> Time</th>
											<th> Mode Of Travel</th>
											<th> From City</th>
											<th> To City</th>
											<th>  <button type="button" class="btn btn-sm tbl-row-add-tests"  data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button></th>	
										</tr>
									</thead>	
									<tr class="tr_clone_tests" id="tr_clone_tests">
						         		<td  width="15%">
						         			<input type="text" class="form-control input-sm DepDate" readonly="readonly"  placeholder=""  id="DepDate" name="DepDate"  required="required"  > 
						         		</td>      						         		 
						         		<td width="15%">						         		
						         			<input  class="form-control DepTime" id="time" placeholder="Time"   name="tourtime" value="<%=LocalTime.now() %>"  maxlength="250" required="required">                     					         		
										</td>
						         		<td width="20%">
						         		 	<select class="form-control test-type multipleSelect selectpicker "  style="width: 100%" data-size="auto" name="modeoftravel"  data-live-search="true" data-container="body" >
												 <option value="0">Select</option>
					                             <%for(Object[] ls:ModeOfTravelList){%> 
					                             <option value="<%=ls[0]%>"><%=ls[1]%></option>
					                            <%}%>
											</select>
						         		 </td>
										<td  width="25%">
											<select class="form-control test-type fromcity selectpicker "  style="width: 100%" data-size="auto" name="fromcity" id="" data-live-search="true" data-container="body" >
												<option value="0">Select City</option>
											    <%for(Object[] ls:CityList){%> 
					                             <option value="<%=ls[0]%>"><%=ls[1]%></option>
					                            <%}%>
					                        </select>	
										</td>						         		                                      
																	
										<td width= "25%">
											<select class="form-control test-type tocity selectpicker "  style="width: 100%" data-size="auto" name="tocity" id="" data-live-search="true" data-container="body" >	
												<option value="0">Select City</option>
											    <%for(Object[] ls:CityList){%> 
					                             <option value="<%=ls[0]%>"><%=ls[1]%></option>
					                            <%}%>
					                        </select>								
										</td>							
										
										<td>
											<button type="button" class="btn btn-sm tbl-row-rem_tests"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> 
										</td>
										
									</tr>
							</table>
					  </div>  
	              </div>
	         </div>

	         <div align="center">
	         		 <button type="button" class="btn btn-sm submit-btn" style="background-color: #417ccd;" id="tour" name="action" value="submit" onclick="TourCheck()">Check Tour</button> 	
	         </div>
	         <div align="center">
	         	<button type="button" class="btn btn-sm submit-btn" id="submitbtn" name="action" value="submit" onclick="TourApply()">Submit</button>	         	
	         </div>
		</form>		 
							</div>
						</div>
					</div>
				</div>				

			<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b> Tour Approval Flow</b></div>
		 	</div>
		 	<div align="center" style=" padding-top: 10px; padding-bottom: 15px; " >
	              <table >
	               		<tr>
	                		<td class="trup" style="text-align: center; background: linear-gradient(to top, #3c96f7 10%, transparent 115%);">
	                			User <br> <%=session.getAttribute("EmpName")%>
	                		</td>
	                		<td rowspan="2" >
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		
	                		<td class="trup" style="text-align: center; background: linear-gradient(to top, #FBC7F7 10%, transparent 115%);">
	                			Dept Incharge <br> <%=emplist[0]%>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               			<td class="trup"  style="text-align: center; background: linear-gradient(to top, #4DB6AC 10%, transparent 115%);">
	                			DGM <br> <%=emplist[1]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		
	                		<td class="trup"  style="text-align: center; background: linear-gradient(to top, #6ba5df 10%, transparent 115%);" >
	                			F & A <br> <%=emplist[2]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                		<td class="trup" style="text-align: center; background: linear-gradient(to top, #42f2f5 10%, transparent 115%);">
	                			P & A <br> <%=emplist[3]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                		<td class="trup" style="text-align: center; background: linear-gradient(to top, #eb76c3 10%, transparent 115%);">
	                			CEO <br> <%=emplist[4]%>
	                		</td>
	                		
	               		
	               	</tr>			   
		                	
	                          	
			           </table>			             
			 	</div>
			<hr>
	</div>
</div>
</body>
<script type="text/javascript">
function setTooltip()
{
	$('[data-toggle="tooltip"]').tooltip({
		 trigger : 'hover',
		 html : true,
		 boundary: 'window'
	});
	$('[data-toggle="tooltip"]').on('click', function () {
		$(this).tooltip('hide');
	});
}


var EmpCount = 1;
$("table").on('click','.tbl-row-add-tests' ,function() 
{
	var $tr = $('.tr_clone_tests').last('.tr_clone_tests');
	var $clone = $tr.clone();
	$tr.after($clone);
	$clone.find('.bootstrap-select').replaceWith(function() { return $('select', this); })  ;  
	$clone.find('.selectpicker').selectpicker('render'); 
	$clone.find('.bootstrap-input').replaceWith(function() { return $('input', this); })  ;  
		   			   			   	
	$clone.find(".items").val("").end();  
	DepDate();
	setTooltip();
		  
});


$("table").on('click','.tbl-row-rem_tests' ,function() {
	var cl=$('.tr_clone_tests').length;
	if(cl>1){
	   var $tr = $(this).closest('.tr_clone_tests');
	   var $clone = $tr.remove();
	   $tr.after($clone);
	} 
});

$(function() {
	   $('#earliesttime').daterangepicker({
	            timePicker : true,
	            singleDatePicker:true,
	            timePicker24Hour : true,
	            timePickerIncrement : 1,
	            timePickerSeconds : false,
	            locale : {
	                format : 'HH:mm'
	            }
	        }).on('show.daterangepicker', function(ev, picker) {
	            picker.container.find(".calendar-table").hide();
	   });
	})

	DepDate();
	function DepDate()
	{
		$('.DepDate').daterangepicker({
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,	
			"minDate":$("#doD").val(),
			"maxDate" :$('#doA').val(),
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

			   $('.DepTime').daterangepicker({
			            timePicker : true,
			            singleDatePicker:true,
			            timePicker24Hour : true,
			            timePickerIncrement : 1,
			            timePickerSeconds : false,
			            locale : {
			                format : 'HH:mm'
			            }
			        }).on('show.daterangepicker', function(ev, picker) {
			            picker.container.find(".calendar-table").hide();
			   });	
	}
$('#earliestdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate":$("#doD").val(),
	"maxDate" :$('#doA').val(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#doD').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#doA').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#dos').val(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$( "#doD" ).change(function() {
	
	$('#doA').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : $('#doD').val(), 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});


 $( "#doA" ).change(function() { 
	$('#earliestdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
	$('.DepDate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
	


 $('#farefromdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 
 $('#faretodate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 $('#allowancefromdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 
 $('#allowancetodate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 
 $('#EJPFROM').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 $('#EJPTO').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 });
 
 $('#farefromdate, #faretodate ,#allowancefromdate , #allowancetodate , #EJPFROM , #EJPTO').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,	
		"minDate":$("#doD").val(),
		"maxDate" :$('#doA').val(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
function TourApply(){
	var placeofstay = $("#pos").val();
	var purpose = $("#purpose").val();
	var airtraveljusti = $("#airtraveljusti").val();
	var isamtreq = $("#isamtreq").val()
	var earlisttime = $("#earliesttime").val();
	var earliestdate = $("#earliestdate").val();
	var earliestplace = $("#earliestplace").val();

	var tourtime = [];
	$("input[name='tourtime']").each(function() {
		tourtime.push($(this).val());
	});
	var departuredate = [];
	$("input[name='DepDate']").each(function() {
		departuredate.push($(this).val());
	});
	var modeoftravel =  $(".multipleSelect :selected").map(function(i, el) {
	    return $(el).val();
	}).get();
	
	var fromcity = $(".fromcity :selected").map(function(i, el) {
	    return $(el).val();
	}).get();
	
	var tocity = $(".tocity :selected").map(function(i, el) {
	    return $(el).val();
	}).get();

	if(placeofstay.trim() =='' || placeofstay==null || placeofstay =='null' || placeofstay ==undefined){
		alert("Enter the Place Of Stay!");
	}else if (purpose.trim()=='' || purpose==null || purpose =='null' || purpose ==undefined){
		alert("Enter the Tour purpose!");
	}else if(earlisttime.trim() =='00:00' || earlisttime.trim() =='' || earlisttime==null || earlisttime =='null' || earlisttime ==undefined){
		alert("Enter the Earliest presence time!");
	}else if(earliestdate.trim() =='' || earliestdate==null || earliestdate =='null' || earliestdate ==undefined){
		alert("Enter the Earliest presence Date!");
	}else if(earliestplace.trim() =='' || earliestplace==null || earliestplace =='null' || earliestplace ==undefined){
		alert("Enter the Earliest presence Place!");
	}else if(tourtime.includes('00:00')){
		alert("Select the Time!");
	}else if(modeoftravel.includes('0')){
		alert("Select the All Mode Of Travel!");
	}else if(fromcity.includes('0')){
		alert("Select the  FromCity!");
	}else if(tocity.includes('0')){
		alert("Select the  Tocity!");
	}else if(isamtreq =='Y' ){
		var tourfare = $("#tourfare").val();
		var boardingdays = $("#boardingdays").val();
		var boardingperday = $("#boardingperday").val();
		var allowancenoday = $("#allowancenoday").val();
		var allowanceperday = $("#allowanceperday").val();
		var totalproposedamt = $("#totalproposed").val();
		var fromdate = $("#doD").val();
		var todate = $("#doA").val();
		
		var dayscount = getNumberOfDays(fromdate ,todate );
		
		if(tourfare.trim()=='' || tourfare==null || tourfare =='null' || tourfare == undefined){
			alert("Enter the Air / Train / Bus Fare Amount!");
		}else if(boardingdays.trim()=='' || boardingdays==null || boardingdays=='null' || boardingdays==undefined){
			alert("Enter the days for Boarding Charges!");
		}else if (boardingperday.trim()=='' || boardingperday==null || boardingperday =='null' || boardingperday == undefined){
			alert("Enter the Amount per day Boarding Charges!");
		}else  if(allowancenoday.trim()=='' || allowancenoday==null || allowancenoday =='null' || allowancenoday == undefined){
			alert("Enter the days for Daily Allowance!");
		}else if (allowanceperday.trim()=='' || allowanceperday==null || allowanceperday =='null' || allowanceperday == undefined){
			alert("Enter the Amount per day Daily Allowance!");
		}else if(boardingdays > dayscount){
			alert("Boarding Charges days sholud be less than total tour period days!");
		}else if(allowancenoday > dayscount){
			alert("Daily Allowance days sholud be less than total tour period days!");
		}else if(totalproposedamt.trim()=='' || totalproposedamt==null || totalproposedamt =='null' || totalproposedamt == undefined){
			alert("Enter the Total Proposed Amount!");
		}else{
			TourCheck();
			if(confirm("Are you sure to submit?"))
			{
				$("#TourRequestForm").submit();
			}
		}
	}else{
		TourCheck();
		if(confirm("Are you sure to submit?"))
		{
			$("#TourRequestForm").submit();
		}
	}
}

setPatternFilter($("#tourfate"), /^-?\d*$/);
setPatternFilter($("#boardingdays"), /^-?\d*$/);
setPatternFilter($("#boardingperday"), /^-?\d*$/);
setPatternFilter($("#allowanceperday"), /^-?\d*$/);
setPatternFilter($("#allowancenoday"), /^-?\d*$/);
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

 $("#submitbtn").hide();
function TourCheck()
{
	$("#submitbtn").hide();
	var departuredate=$("#doD").val();
	var arrivaldate=$("#doA").val();
	
	$.ajax({
		
		type : "GET",
		url : "checktour.htm",
		data : {
			
			DepartureDate : departuredate,
			ArrivalDate   :arrivaldate,
		},
		datatype : 'json',
		success : function(result) {
		 var result = JSON.parse(result);
		 var sp=document.getElementById("sp");
			if(result[1]=='Fail'){
				
		     sp.innerHTML="<b style='color:red; text-align: center; font-size: 17px;'>"+result[0]+"</b>";
		     
			}else if(result[1]=='Pass'){
				
				 $("#tour").hide();
				 $("#submitbtn").show();
				 sp.innerHTML="<b style='color:green; text-align: center; font-size: 17px;'>"+result[0]+"</b>";
				 
			}	
		}
	});
}

function HideTourAdv() {

	var val = $("#isamtreq").val();
	var div = document.getElementById('advAmountreq');

	if(val=='Y'){
		div.removeAttribute('style');
	}else if(val=='N'){
		div.setAttribute('style','display: none;');
	}
}

function getNumberOfDays(start, end) {
	
	const part1 = start.split('-');
	const part2 = end.split('-');
	const fromdate = part1[2] + '/' + part1[1] + '/' + part1[0];
	const todate = part2[2] + '/' + part2[1] + '/' + part2[0];
	
    const date1 = new Date(fromdate);
    const date2 = new Date(todate);

    // One day in milliseconds
    const oneDay = 1000 * 60 * 60 * 24;

    // Calculating the time difference between two dates
    const diffInTime = date2.getTime() - date1.getTime();

    // Calculating the no. of days between two dates
    const diffInDays = Math.round(diffInTime / oneDay);

    return diffInDays+1;
}
</script>
</html>
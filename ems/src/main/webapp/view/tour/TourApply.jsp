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
.table thead th {
    color: white;
    background-color: #0e6fb6;
    text-align: center;
    padding-bottom: 0.1rem !important;
    padding-top: 0.1rem !important;
}

</style>
</head>
<body>
<%
List<Object[]> emplist = (List<Object[]>)request.getAttribute("emplist");
List<Object[]> ModeOfTravelList=(List<Object[]>)request.getAttribute("ModeOfTravelList");
List<Object[]> CityList=(List<Object[]>)request.getAttribute("CityList");

%>
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Add Tour Program </h5>
			</div>
				<div class="col-md-9 ">
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
							<div class="card-body">
								<form action="TourApplyAdd.htm" method="post" autocomplete="off" id="requesttourform">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								    <div class="form-group">
								        <div class="row">
								        	<div class="col-md-3">
										 		<label>Departure Date:<span class="mandatory">*</span></label>
												<div class=" input-group">
												    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="doD" name="DepartureDate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
										     </div>
										     
										     <div class="col-md-3">
										 		<label>Arrival Date:<span class="mandatory">*</span></label>
												<div class=" input-group">
												    <input type="text" class="form-control input-sm " readonly="readonly"  placeholder=""  id="doA" name="ArrivalDate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
										     </div>
										     <div class="col-md-3">					        	
									                <label>Place Of Stay:<span class=" mandatory ">*</span></label>
									                <input type="text" value="" name="POS" id="pos" class=" form-control input-sm " maxlength="255"   placeholder="Place Of Stay "  required="required">
											</div>
											<div class="col-md-3">					        	
									                <label>Purpose:<span class=" mandatory ">*</span></label>
									                <input type="text" value="" name="Purpose" id="purpose" class=" form-control input-sm " maxlength="255"   placeholder="Enter Purpose "  required="required">
											</div>
									     </div>
									</div>
									<div class="form-group">
								        <div class="row">
								       		 <div class="col-md-4">					        	
									                <label>Employees proposed to be deputed:<span class=" mandatory ">*</span></label>
									                <select class="form-control select2"  name="EmpNo" id="empno" data-container="body" data-live-search="true"  required="required" > 
											              	<%if(emplist!=null){
															for(Object[] emp:emplist){ %>
															<option value="<%=emp[1]%>" ><%=emp[2]%>(<%=emp[3]%>)</option>
															<%}} %>
													</select>
											 </div>
											 <div class="col-md-5">					        	
									                <label>Air Travel of non-entitled employee (if applicable): </label>
									                <input type="text" value="" name="airtraveljusti" id="airtraveljusti" class=" form-control input-sm " maxlength="255"   placeholder="Enter Air Travel Justification  "  required="required">
											</div>
											 <div class="col-md-3">
												 <label >Required Advance Amt: <span class=" mandatory ">*</span></label>
												  <input  type="text"  name="reqadvamt"  id="reqadvamt" class=" form-control input-sm " placeholder="Enter Amount"   value=""  maxlength="12" required="required">                     	
											 </div>
								        </div>
								    </div>   
								    <div class="form-group" id="tabledata" ></div>    
								    <div class="form-group">
								    	<label> Earliest presence required at destination :</label>  <label style="margin-left: 525px;"> Remarks :</label>
								        <div class="row">
								        <div class="col-md-1">	</div>
								       		 <div class="col-md-2">	  	
													<div class=" input-group">
														<label>Time:<span class=" mandatory ">*</span></label>&nbsp;&nbsp;&nbsp;
													     <input  class="form-control" id="earliesttime" placeholder="Start Time"   name="EarliestTime" value=""  maxlength="250" required="required">                     
													</div>
											 </div>
											  <div class="col-md-3">			
													<div class=" input-group">
													<label>Date:<span class=" mandatory ">*</span></label>	&nbsp;&nbsp;&nbsp;&nbsp;        	
  													<input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="earliestdate" name="EarliestDate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate"></label>      													
												    </div>
											 </div>
											  <div class="col-md-3">			
													<div class=" input-group">
														<label>Place:<span class=" mandatory ">*</span></label>	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        	
													     <input  class=" form-control input-sm " id="earliestplace" placeholder="Place"   name="EarliestPlace" value=""  maxlength="250" required="required">                     
													</div>
											 </div>
											 <div class="col-md-3">			
													<div class=" input-group">
													     <input  class=" form-control input-sm " id="Remarks" placeholder="Remarks"   name="Remarks" value=""  maxlength="250" required="required">                     
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
						         			<input  class="form-control DepTime" id="time" placeholder="Time"   name="tourtime" value=""  maxlength="250" required="required">                     					         		
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
											<select class="form-control test-type fromcity selectpicker "  style="width: 100%" data-size="auto" name="fromcity"  data-live-search="true" data-container="body" >
												<option value="0">Select City</option>
											    <%for(Object[] ls:CityList){%> 
					                             <option value="<%=ls[0]%>"><%=ls[1]%></option>
					                            <%}%>
					                        </select>	
										</td>						         		                                      
																	
										<td width= "25%">
											<select class="form-control test-type tocity selectpicker "  style="width: 100%" data-size="auto" name="tocity"  data-live-search="true" data-container="body" >	
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
		         	<button type="button" class="btn btn-sm submit-btn" name="action" value="submit" onclick="TourApply()">Submit</button>	
	         </div>

								</form>		 
							</div>
						</div>
					</div>
				</div>				
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
			<%-- <%if(apply!=null&&apply.getDateOfTravel()!=null){%>
			"startDate" : new Date("<%=apply.getDateOfTravel()%>"),
			<%}%> --%>
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
	<%-- <%if(apply!=null&&apply.getDateOfTravel()!=null){%>
	"startDate" : new Date("<%=apply.getDateOfTravel()%>"),
	<%}%> --%>
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
	<%-- <%if(apply!=null&&apply.getDateOfTravel()!=null){%>
	"startDate" : new Date("<%=apply.getDateOfTravel()%>"),
	<%}%> --%>
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
	<%-- <%if(apply!=null&&apply.getEndDateOfTravel()!=null){%>
	"startDate" :new Date("<%=apply.getEndDateOfTravel()%>"),
	<%}%> --%>
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


function getEmpList()
{
	var j=0;
	var empid = $("#empid").val();
	var emplist = [<%int i=0; for (Object[] obj:emplist) { %> ['<%= obj[1]%>', '<%= obj[2]%>', '<%= obj[3]%>', '<%= obj[4]%>' , '<%= obj[5]%>', '<%= obj[6]%>',' <%= obj[7]%>'     ] <%= i + 1 < emplist.size() ? ",":"" %><% } %>];
	var table='';
	   table+='<table class="table meeting" > '
	   table+='<thead style="height: 0px;"><tr><th style="font-weight: 600;">SN</th><th style="font-weight: 600;">Name</th><th style="font-weight: 600;">EmpNo</th><th style="font-weight: 600;">DOB</th><th style="font-weight: 600;">Level</th><th style="font-weight: 600;">Mobile No</th><th style="font-weight: 600;">Email</th></tr></thead>';
	   table+='<tbody>';
	   for(var i=0;i<emplist.length;i++){
		   if(empid.includes(emplist[i][0])){
			   table+='<tr>';
				   table+='<td>'+(++j)+'</td>';
				   table+='<td>'+emplist[i][1]+'('+emplist[i][2] +')</td>';
				   table+='<td>'+emplist[i][0]+'</td>';
				   table+='<td>'+emplist[i][3]+'</td>';
				   table+='<td>'+emplist[i][4]+'</td>';
				   table+='<td>'+emplist[i][5]+'</td>';
				   table+='<td>'+emplist[i][6]+'</td>';
			   table+='</tr>';
		   }
   		}
   table+='</tbody></table> <hr style="margin-top: -15px;">'
   if(empid.length>0){
	   $("#tabledata").html(table);
   }else{
	   $("#tabledata").html('');
   }
}



function TourApply(){
	var placeofstay = $("#pos").val();
	var purpose = $("#purpose").val();
	var airtraveljusti = $("#airtraveljusti").val();
	var reqadvamt = $("#reqadvamt").val()
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

	console.log(earlisttime);
	if(placeofstay.trim() =='' || placeofstay==null || placeofstay =='null' || placeofstay ==undefined){
		alert("Enter the Place Of Stay!");
	}else if (purpose.trim()=='' || purpose==null || purpose =='null' || purpose ==undefined){
		alert("Enter the Tour purpose!");
	}else if(reqadvamt.trim() =='' || purpose==null || purpose =='null' || purpose ==undefined){
		alert("Enter the Required Advance Amount!");
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
	}else{
		if(confirm("Are you sure to submit?")){
			document.getElementById("requesttourform").submit();
		}
	}

}

setPatternFilter($("#reqadvamt"), /^-?\d*$/);
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

</script>
</html>
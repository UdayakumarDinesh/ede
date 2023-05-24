<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.Tour.model.TourOnwardReturn"%>
<%@page import="com.vts.ems.Tour.model.TourApply"%>
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
.table thead th {
    color: white;
    background-color: #0e6fb6;
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
TourApply apply =(TourApply)request.getAttribute("TourApply");
List<TourOnwardReturn> tourdetails = (List<TourOnwardReturn>)request.getAttribute("Touronwarddetails");
Object[] empdata = (Object[])request.getAttribute("Empdata");

%>
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
				<h5>Modify Tour Program <small><b>&nbsp;&nbsp;&nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="TourProgram.htm"> Tour </a></li>
						<li class="breadcrumb-item active " aria-current="page">Modify Tour Program </li>
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
					<form action="TourModify.htm" method="POST" autocomplete="off" id="TourRequestForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								    <div class="form-group">
								        <div class="row">
								        	<div class="col-md-3">
										 		<label>Departure Date :<span class="mandatory">*</span></label>
												<div class=" input-group">
												    <input type="text" class="form-control input-sm mydate"  readonly="readonly"  placeholder=""  id="doD" name="DepartureDate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
										     </div>
										     
										     <div class="col-md-3">
										 		<label>Arrival Date :<span class="mandatory">*</span></label>
												<div class=" input-group">
												    <input type="text" class="form-control input-sm " readonly="readonly"   placeholder=""  id="doA" name="ArrivalDate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate">						      
												    </label>                    
												</div>
										     </div>
										     <div class="col-md-3">					        	
									                <label>Place Of Stay :<span class=" mandatory ">*</span></label>
									                <input type="text" <%if(apply!=null && apply.getStayPlace()!=null){%>value="<%=apply.getStayPlace()%>" <%}%> name="POS" id="pos" class=" form-control input-sm "  maxlength="255"   placeholder="Place Of Stay "  required="required">
											</div>
											<div class="col-md-3">					        	
									                <label>Purpose :<span class=" mandatory ">*</span></label>
									                <input type="text" <%if(apply!=null && apply.getPurpose()!=null){%>value="<%=apply.getPurpose()%>" <%}%> name="Purpose" id="purpose" class=" form-control input-sm " maxlength="255"   placeholder="Enter Purpose "  required="required">
											</div>
									     </div>
									</div>
									<div class="form-group">
								        <div class="row">
											 <div class="col-md-5">					        	
									                <label>Air Travel of non-entitled employee (if applicable) : </label>
									                <input type="text" <%if(apply!=null && apply.getAirTravJust()!=null){%>value="<%=apply.getAirTravJust()%>" <%}%> name="airtraveljusti" id="airtraveljusti" class=" form-control input-sm " maxlength="255"   placeholder="Enter Air Travel Justification  "  required="required">
											</div>
											 <div class="col-md-3">
												 <label >Required Advance Amt : <span class=" mandatory ">*</span></label>
												 <select class="form-control test-type fromcity selectpicker "  style="width: 100%" data-size="auto" name="reqadvamt"  id="reqadvamt" onchange="HideTourAdv()" data-live-search="true" data-container="body" >
													     <option value="N" <%if(apply!=null && apply.getAdvancePropsed().equalsIgnoreCase("N")){%>selected="selected" <%}%>>No</option>
							                             <option value="Y" <%if(apply!=null && apply.getAdvancePropsed().equalsIgnoreCase("Y")){%>selected="selected" <%}%>>Yes</option>
							                     </select> 
											 </div>
											 <div class="col-md-4">	
											 		<label>Remarks : </label>
													<input  class=" form-control input-sm " <%if(apply!=null && apply.getRemarks()!=null){%>value="<%=apply.getRemarks()%>" <%}%> id="Remarks" placeholder="Remarks"   name="Remarks"   maxlength="250" required="required">                     	
											 </div>
								        </div>
								    </div>   
								    <div class="form-group" id="tabledata" ></div>    
								    <div class="form-group">
								    	<label> Earliest presence required at destination :</label>  
								        <div class="row">
								        <div class="col-md-1">	</div>
								       		 <div class="col-md-2">	  	
													<div class=" input-group">
														<label>Time :<span class=" mandatory ">*</span></label>&nbsp;&nbsp;&nbsp;
													     <input  class="form-control" id="earliesttime" placeholder="Start Time"   name="EarliestTime" <%if(apply!=null && apply.getEarliestTime()!=null){%>value="<%=apply.getEarliestTime()%>" <%}%>   required="required">                     
													</div>
											 </div>
											  <div class="col-md-3">			
													<div class=" input-group">
													<label>Date :<span class=" mandatory ">*</span></label>	&nbsp;&nbsp;&nbsp;&nbsp;        	
  													<input type="text"  class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="earliestdate" name="EarliestDate"  required="required"  > 
												    <label class="input-group-addon btn" for="testdate"></label>      													
												    </div>
											 </div>
											  <div class="col-md-3">			
													<div class=" input-group">
														<label>Place :<span class=" mandatory ">*</span></label>	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        	
													     <input type="text" class=" form-control input-sm " <%if(apply!=null && apply.getEarliestPlace()!=null){%>value="<%=apply.getEarliestPlace()%>" <%}%> id="earliestplace" placeholder="Place"   name="EarliestPlace"   maxlength="250" required="required">                     
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
									<%for(TourOnwardReturn obj:   tourdetails){%>
									<tr class="tr_clone_tests" id="tr_clone_tests">
									
						         		<td  width="15%">
						         			<input type="text"  <%if(obj.getTourDate()!=null){%> value="<%=DateTimeFormatUtil.SqlToRegularDate( obj.getTourDate().toString())%>" <%}%> class="form-control input-sm DepDate" readonly="readonly"  placeholder=""  id="DepDate" name="DepDate"  required="required"  > 
						         		</td>      						         		 
						         		<td width="15%">						         		
						         			<input  class="form-control DepTime" <%if(obj.getTourTime()!=null){%> value="<%=obj.getTourTime()%>" <%}%> id="time" placeholder="Time" name="tourtime" maxlength="250" required="required">                     					         		
										</td>
						         		<td width="20%">
						         		 	<select class="form-control test-type multipleSelect selectpicker "  style="width: 100%" data-size="auto" name="modeoftravel"  data-live-search="true" data-container="body" >
												 <option value="0">Select</option>
					                             <%for(Object[] ls:ModeOfTravelList){%> 
					                             <option value="<%=ls[0]%>" <%if(obj.getModeId()== Long.parseLong(ls[0].toString())){%>selected="selected" <%}%>><%=ls[1]%></option>
					                            <%}%>
											</select>
						         		 </td>
										<td  width="25%">
											<select class="form-control test-type fromcity selectpicker "  style="width: 100%" data-size="auto" name="fromcity"  data-live-search="true" data-container="body" >
												<option value="0">Select City</option>
											    <%for(Object[] ls:CityList){%> 
					                             <option value="<%=ls[0]%>" <%if(obj.getFromCityId()==Long.parseLong(ls[0].toString())){%>selected="selected" <%}%>><%=ls[1]%></option>
					                            <%}%>
					                        </select>	
										</td>						         		                                      
																	
										<td width= "25%">
											<select class="form-control test-type tocity selectpicker "  style="width: 100%" data-size="auto" name="tocity"  data-live-search="true" data-container="body" >	
												<option value="0">Select City</option>
											    <%for(Object[] ls:CityList){%> 
					                             <option value="<%=ls[0]%>" <%if(obj.getToCityId()==Long.parseLong(ls[0].toString())){%> selected="selected" <%}%>><%=ls[1]%></option>
					                            <%}%>
					                        </select>								
										</td>							
										
										<td>
											<button type="button" class="btn btn-sm tbl-row-rem_tests"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> 
										</td>
										
									</tr>
								<%}%>
							</table>
					  </div>  
	              </div>
	         </div>
	         
	         <div align="center">
	         		 <button type="button" class="btn btn-sm submit-btn" style="background-color: #417ccd;" id="tour" name="check" value="submit" onclick="TourCheck()">Check Tour</button> 	
	         </div>
	         <div align="center">
	        	 <input type="hidden" name="tourapplyid" <%if(apply!=null && apply.getTourApplyId()!=null){%> value="SubmitModify/<%=apply.getTourApplyId()%>" <%}%>>
	        	 <input type="hidden" name="tourno" <%if(apply!=null && apply.getTourNo()!=null){%> value="<%=apply.getTourNo()%>" <%}%>>
	         	<button type="button" class="btn btn-sm submit-btn" id="submitbtn" name="Action1" value="SubmitEdit1" onclick="TourApply()">Submit</button>	         	
	         </div>
		</form>		 
							</div>
						</div>
					</div>
				</div>	
				
				
				<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b> Tour Modify Approval Flow</b></div>
		 	</div>
		 	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               		<tr>
	                		<td class="trup" style="background: linear-gradient(to top, #3c96f7 10%, transparent 115%);">
	                			User <br> <%=session.getAttribute("EmpName")%>
	                		</td>
	                		<td rowspan="2" >
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		
	                		<td class="trup" style="background: linear-gradient(to top, #FBC7F7 10%, transparent 115%);">
	                			Dept Incharge <br> <%=emplist[0]%>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               			<td class="trup"  style="background: linear-gradient(to top, #4DB6AC 10%, transparent 115%);">
	                			DGM <br> <%=emplist[1]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		
	                		<td class="trup"  style="background: linear-gradient(to top, #6ba5df 10%, transparent 115%);" >
	                			F & A <br> <%=emplist[2]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                		<td class="trup" style="background: linear-gradient(to top, #42f2f5 10%, transparent 115%);">
	                			P & A <br> <%=emplist[3]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                		<td class="trup" style="background: linear-gradient(to top, #eb76c3 10%, transparent 115%);">
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
	 <%if(apply!=null&&apply.getEarliestDate()!=null){%>
	"startDate" : new Date("<%=apply.getEarliestDate()%>"),
	<%}%> 
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
	 <%if(apply!=null&&apply.getStayFrom()!=null){%>
	"startDate" : new Date("<%=apply.getStayFrom()%>"),
	<%}%> 
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
	 <%if(apply!=null&&apply.getStayTo()!=null){%>
	"startDate" :new Date("<%=apply.getStayTo()%>"),
	<%}%> 
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
});

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

	if(placeofstay.trim() =='' || placeofstay==null || placeofstay =='null' || placeofstay ==undefined){
		alert("Enter the Place Of Stay!");
	}else if (purpose.trim()=='' || purpose==null || purpose =='null' || purpose ==undefined){
		alert("Enter the Tour purpose!");
	}else if(reqadvamt.trim() =='' || purpose==null || purpose =='null' || purpose ==undefined){
		alert("Enter the Required Advance Amount!");
	}else if(reqadvamt<=0){
		alert("Advance Amount Can't be Negtive!");
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
		TourCheck();
		if(confirm("Are you sure to submit?"))
		{
			$("#TourRequestForm").submit();
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

 $("#submitbtn").hide();
function TourCheck()
{
	$("#submitbtn").hide();
	var departuredate=$("#doD").val();
	var arrivaldate=$("#doA").val();
	var advamt = $("#reqadvamt").val();
	$.ajax({
		
		type : "GET",
		url : "checktour.htm",
		data : {
			AdvanceReqAmount    :	advamt,
			DepartureDate : departuredate,
			ArrivalDate   :arrivaldate,
		},
		datatype : 'json',
		success : function(result) {
			console.log(result);
		 var result = JSON.parse(result);
		 var sp=document.getElementById("sp");
			if(result[1]=='Fail' && result[1]!=null){
		     sp.innerHTML="<b style='color:red; text-align: center; font-size: 17px;'>"+result[0]+"</b>";
			}else if(result[1]=='Pass'){
				 $("#tour").hide();
				 $("#submitbtn").show();
				 sp.innerHTML="<b style='color:green; text-align: center; font-size: 17px;'>"+result[0]+"</b>";
				 
			}	
		}
	});
} 
</script>
</html>
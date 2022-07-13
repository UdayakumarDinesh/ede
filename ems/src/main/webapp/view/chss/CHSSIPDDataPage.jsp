<%@page import="com.vts.ems.chss.model.CHSSIPDClaimsInfo"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.vts.ems.chss.model.CHSSConsultMain"%>
<%@page import="com.vts.ems.chss.model.CHSSApply"%>
<%@page import="com.vts.ems.chss.model.CHSSApproveAuthority"%>
<%@page import="com.vts.ems.chss.model.CHSSMedicinesList"%>
<%@page import="com.vts.ems.chss.model.CHSSTestSub"%>
<%@page import="com.vts.ems.chss.model.CHSSDoctorRates"%>
<%@page import="com.vts.ems.chss.model.CHSSOtherItems"%>
<%@page import="com.vts.ems.chss.model.CHSSTestMain"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

input[type=number]{
    -moz-appearance: textfield;
}

.table thead{
    background: #9AD0EC;
}

.filter-option-inner, .bootstrap-select .dropdown-menu
{
   max-width: 100%!important;  
   font-weight: 500 !important;
}

.filter-option-inner-inner
{
	max-width: 100%!important;
}

option {
  max-width: 80% !important;
}

p {
	text-align: justify;
	text-justify: inter-word;
}

table.roundedCorners { 
  border-radius: 8px; 
  border-spacing: 0;
  }
table.roundedCorners tr:last-child > td {
  border-bottom: none;
}


table th:first-child{
  border-radius:8px 0 0 0;
}

table th:last-child{
  border-radius:0 8px 0 0;
}


</style>
</head>
<body>

<%
	
	Object[] employee = (Object[] )request.getAttribute("employee") ;
	Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
	String isself = chssapplydata[3].toString();
	List<CHSSTreatType> treattypelist=(List<CHSSTreatType>)request.getAttribute("treattypelist");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();	
	
	CHSSIPDClaimsInfo ipdbasicinfo = (CHSSIPDClaimsInfo)request.getAttribute("ipdbasicinfo") ;
	
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Apply</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<!-- <li class="breadcrumb-item "><a href="CHSSAppliedList.htm">CHSS List</a></li> -->
						<li class="breadcrumb-item active " aria-current="page">Claim Data</li>
					</ol>
				</div>
			</div>
	</div>	
	
	<div class="page card dashboard-card">
		
		<div class="card-body" >
		  
			<div align="center">
				<%String ses=(String)request.getParameter("result"); 
				String ses1=(String)request.getParameter("resultfail");
				if(ses1!=null){ %>
					<div class="alert alert-danger" role="alert">
						<%=ses1 %>
					</div>
					
				<%}if(ses!=null){ %>
					
					<div class="alert alert-success" role="alert">
						<%=ses %>
					</div>
				<%} %>
			</div>
			
			<div class="card" >
				<div class="card-body main-card " >
				<%if(Integer.parseInt(chssapplydata[9].toString())>1){ %>
					<div class="row">
						<div class="col-md-12">
							<p>Remark : 
							<%=chssapplydata[19] %></p>
						</div>
					</div>
				<%} %>
				
				<form action="CHSSApplyEdit.htm" method="post" autocomplete="off"  >
						
					<div class="card" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
						<div class="row">
							
							<b> &nbsp; Claim Id : </b><%=chssapplydata[16] %>
									
						</div>
						<div class="row">
									
							<%if(isself.equalsIgnoreCase("N")){
								Object[] familyMemberData = (Object[])request.getAttribute("familyMemberData") ; %>
								
								<div class="col-4" >
									<div class="row" style="padding:5px; ">
										<b>Patient Name : &nbsp;</b> <%=familyMemberData[1] %>
									</div>
										
									<div class="row" style="padding:5px; ">
										<b>Relation : &nbsp;</b><%=familyMemberData[7] %>
										<input type="hidden" name="patientid" value="<%=familyMemberData[0]%>">
										<input type="hidden" name="relationid" value="<%=familyMemberData[2]%>">
									</div>
								</div>
							<%}else{ %>
								<div class="col-4">
									<div class="row" style="padding:5px; ">
										<b> Patient Name : &nbsp;</b><%=employee[2] %>
									</div>
											
									<div class="row" style="padding:5px; ">
										<b>Relation : &nbsp;</b>SELF
										<input type="hidden" name="patientid" value="<%=employee[0]%>">
										<input type="hidden" name="relationid" value="0">
									</div>
								</div>
							<% } %>
							<div class="col-2">
								<b>Treatment Type : </b><br>
								<select class="form-control select2 w-100" name="treatmenttype" id="treatmenttype" required="required" data-live-search="true" >
									<option value="" selected="selected" disabled="disabled">Choose..</option>
									<%for(CHSSTreatType treattype : treattypelist ){ %>
										<option value="<%=treattype.getTreatTypeId()%>" <%if(Integer.parseInt(chssapplydata[7].toString()) == treattype.getTreatTypeId()){ %>selected<%} %> ><%=treattype.getTreatmentName() %></option>
									<%} %>								
								</select>
							</div>
							<div class="col-2">
								<b>Claim Type : </b> 
								<br>
								<select class="form-control" name="chsstype" id="chsstype">
									<option value="OPD" <%if(chssapplydata[6].toString().equalsIgnoreCase("OPD")){ %>Selected<%} %> >OPD</option>
									<option value="IPD" <%if(chssapplydata[6].toString().equalsIgnoreCase("IPD")){ %>Selected<%} %> >IPD</option>
								</select>
							</div>
							<div class="col-3">
								<b>Ailment/Disease/Accident : </b><br>
								<input type="text" class="form-control w-100" name="ailment" value="<%=chssapplydata[17] %>" required="required" maxlength="255" >
							</div>
							
									
							<div class="col-1">
								<button type="submit" class="btn btn-sm update-btn " style="margin-top: 20px;"  data-toggle="tooltip" data-placement="top" title="Update"> Update <!-- <i class="fa-solid fa-pen-to-square" style="color: #FF7800;"> --></i></button>  <!-- Onclick="return confirm ('Are You Sure To Update?');" --> 
							</div>
						</div>
					</div>
					<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
							
				<div class="row">
					<div class="col-md-12" align="center">
						<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;"> Basic Details</span>
						<br>
					</div>
					<%if(ipdbasicinfo!=null){ %>
					<form action="CHSSIPDBasicInfoEdit.htm" method="post" autocomplete="off"  >
					<%}else{ %>
					<form action="CHSSIPDBasicInfoAdd.htm" method="post" autocomplete="off"  >
					<%} %>
					
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-3">
								<b>Hospital :</b>
								<br>
								<input type="text" class="form-control" name="hospitalname" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getHospitalName()%>"  <%}else{ %>  value="" <%} %> maxlength="150" required>
							</div>
							<div class="col-md-3">
								<b>Room Type :</b>
								<br>
								<input type="text" class="form-control" name="room-type" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getRoomType()%>"  <%}else{ %>  value="" <%} %>  required>
							</div>
							<div class="col-md-2">
								<b>Admitted Date :</b>
								<br>
								<input type="text" class="form-control"  data-date-format="dd/mm/yyyy" name="admitted-date" id="admitted-date" value="" readonly="readonly" required>
							</div>
							<div class="col-md-1">
								<b>Time :</b>
								<br>
								<input type="text" class="form-control" name="admitted-time" id="admitted-time" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getAdmissionTime()%>"  <%}else{ %> value="<%=LocalTime.now()%>" <%} %>  readonly="readonly" required>
							</div>
							<div class="col-md-2">
								<b>Discharged Date :</b>
								<br>
								<input type="text" class="form-control" data-date-format="dd/mm/yyyy" name="discharged-date" id="discharged-date" value="" readonly="readonly"  required>
							</div>
							<div class="col-md-1">
								<b>Time :</b>
								<br>
								<input type="text" class="form-control" name="discharged-time" id="discharged-time" <%if(ipdbasicinfo!=null){ %> value="<%=ipdbasicinfo.getDischargeTime()%>"  <%}else{ %> value="<%=LocalTime.now()%>" <%} %>  readonly="readonly" required>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-4">
							<span style="font-weight: 600; font-size: 15px;color: #CA4E79;text-decoration: underline;"> Claim For :</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<b>Domiciliary Hospitalization :</b>
								&nbsp;&nbsp;
								<input type="radio"  name="DomicHospi" value="1" <%if(ipdbasicinfo!=null && ipdbasicinfo.getDomiciliaryHosp()==1){ %> checked="checked" <%} %> required> &nbsp; YES &nbsp;&nbsp;
								<input type="radio"  name="DomicHospi" value="0" <%if(ipdbasicinfo!=null && ipdbasicinfo.getDomiciliaryHosp()==0){ %> checked="checked" <%} %> >&nbsp; NO
							</div>
							<div class="col-md-3">
								<b>Day Care :</b>
								&nbsp;&nbsp;
								<input type="radio"  name="DayCare" value="1" <%if(ipdbasicinfo!=null && ipdbasicinfo.getDayCare()==1){ %> checked="checked" <%} %> required> &nbsp; YES &nbsp;&nbsp;
								<input type="radio"  name="DayCare" value="0" <%if(ipdbasicinfo!=null && ipdbasicinfo.getDayCare()==0){ %> checked="checked" <%} %> >&nbsp; NO
							</div>
							<div class="col-md-5">
								<b>Extended Care / Inpatient Rehabiliatation :</b>
								&nbsp;&nbsp;
								<input type="radio"  name="ExtCareRehab" value="1"  <%if(ipdbasicinfo!=null && ipdbasicinfo.getExtCareRehab()==1){ %> checked="checked" <%} %> required> &nbsp; YES &nbsp;&nbsp;
								<input type="radio"  name="ExtCareRehab" value="0"  <%if(ipdbasicinfo!=null && ipdbasicinfo.getExtCareRehab()==0){ %> checked="checked" <%} %> >&nbsp; NO
							</div>
						</div>
					
					</div>
					<div align="center">	
						<br>
						<%if(ipdbasicinfo!=null){ %>
						<button type="submit" class="btn btn-sm update-btn" onclick="return confirm('Are You Sure to Update ?');">update</button>
						<input type="hidden" name="ipdclaiminfoid" value="<%=ipdbasicinfo.getIPDClaimInfoId()%>">
						<%}else{ %>
						<button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure to Submit ?');">submit</button>
						<%} %>
					</div>
					
					
					<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	
					
					</form>
				</div>
			</div>
		</div>		
				
	</div>
		
</div>



	
<script type="text/javascript">   

$('#admitted-date').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(),
	<%if(ipdbasicinfo!=null){ %> "startDate" : new Date('<%=ipdbasicinfo.getAdmissionDate()%>'),   <%}else{ %> "startDate" : new Date(), <%} %>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


	$('#admitted-time').daterangepicker({
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

 

	 $('#discharged-date').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :$('#admitted-date').val(),
		"maxDate" :new Date(),
		<%if(ipdbasicinfo!=null){ %> "startDate" : new Date('<%=ipdbasicinfo.getDischargeDate()%>'),   <%} %>
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});

	$(function() {
		$('#discharged-time').daterangepicker({
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


$('#admitted-date').change(function()
{
 $('#discharged-date').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(),
	"minDate" :$('#admitted-date').val(),
	/* "startDate" : result, */
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$(function() {
	$('#discharged-time').daterangepicker({
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

});
 
</script>



</body>
</html>
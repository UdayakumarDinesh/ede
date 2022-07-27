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
	 
	List<CHSSDoctorRates> doctorrates = (List<CHSSDoctorRates>)request.getAttribute("doctorrates");
	List<Object[]> consultmainlist =(List<Object[]>)request.getAttribute("consultmainlist");
	List<Object[]> consulthistory=(List<Object[]>)request.getAttribute("consulthistory");
	
	Object[] consultcount = (Object[])request.getAttribute("consultcount");
	Object[] medicinecount = (Object[])request.getAttribute("medicinecount");
	
	int mcount= 0, ccount=0;
	
	if(consultcount!=null)
	{
		ccount = Integer.parseInt(consultcount[0].toString());
	}
	
	if(medicinecount!=null)
	{
		mcount = Integer.parseInt(medicinecount[0].toString());
	}
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Apply - <%=chssapplydata[6] %></h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
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
										<option value="<%=treattype.getTreatTypeId()%>" <%if(Integer.parseInt(chssapplydata[7].toString())==treattype.getTreatTypeId()){ %>selected<%} %> ><%=treattype.getTreatmentName() %></option>
									<%} %>								
								</select>
							</div>
							<div class="col-2">
								<b>Claim Type : </b> 
								<br>
								<select class="form-control" name="chsstype" id="chsstype">
									<option value="OPD" <%if(chssapplydata[6].toString().equalsIgnoreCase("OPD")){ %>Selected<%} %> >OPD</option>
									<%-- <option value="IPD" <%if(chssapplydata[6].toString().equalsIgnoreCase("IPD")){ %>Selected<%} %> >IPD</option> --%>
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
						<div class="col-md-6">
						
							<form method="post" action="#" autocomplete="off"  >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<div class="table-responsive">
									<div style="text-align: center;margin: 3px;width: 99%">
										<b style="color: #F32424">Consultations in this claim</b>
									</div>
									<table class="table table-bordered table-hover table-condensed info shadow-nohover roundedCorners ">
										
										<thead style="border-radius: 13px">
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:30%;" >Doctor Name</th>
												<th style="width:20%;" > Date </th>
												<th style="width:25%;" >Qualifications  </th>
												<th style="width:20%;" >Action  </th>
											</tr>
										</thead>
										<tbody >
										<%
										
										ArrayList<String> claimconsultids = new ArrayList<String>();
										int sno=0;
										for(Object[] cmain : consultmainlist)
										{
											sno++;											
										%>
											
											<tr class="" >
												<%if(chssapplydata[0].toString().equalsIgnoreCase(cmain[1].toString())){ %>
													<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno %></span> </td>
													<td> <input type="text" class="form-control items" name="docname-<%=cmain[0]%>"   value="<%=cmain[2] %>" style="width:100%;text-transform: capitalize; "  maxlength="500" required="required"></td>													<td> <input type="text" class="form-control consultdate" name="consultdate-<%=cmain[0]%>"   value="<%=rdf.format(sdf.parse(cmain[3].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
													<td>
														<select class="form-control" name="doc-qualification-<%=cmain[0]%>" required="required" >
															<%for(CHSSDoctorRates rate:doctorrates ){ %>
																<option value="<%=rate.getDocRateId() %>" <%if(rate.getDocRateId()==Integer.parseInt(cmain[4].toString())){ %>selected  <%} %> ><%=rate.getDocQualification() %></option>
															<%} %>
														</select>
													</td>
													<td>
														<%if(Integer.parseInt(cmain[5].toString())==0){ %>
															<button type="submit"  class="btn btn-sm update-btn" formaction="CHSSConsultMainEdit.htm"  Onclick="return confirm('Are You Sure To Update?');" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip" data-placement="top" title="Update Consultation Details">														
																update
															</button>
															<button type="submit" class="btn btn-sm" style="background-color: #34B3F1;color:#ffffff;"  formaction="CHSSConsultBills.htm" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip"  data-placement="top" title="Add Bills" >
																Bills
															</button>		
															<button type="submit"  class="btn btn-sm" formaction="CHSSConsultMainDelete.htm"  Onclick="return confirm('Are You Sure To Delete?');" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip" data-placement="top" title="Delete Bill">
																<i class="fa-solid fa-trash-can" style="color: red;"></i>
															</button>		
														<%}else{ %>
															<button type="submit"  class="btn btn-sm update-btn" formaction="CHSSConsultMainEdit.htm"  Onclick="return confirm('Are You Sure To Update?');" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip" data-placement="top" title="Update Consultation Details" disabled>														
																update
															</button>
															<button type="submit" class="btn btn-sm" style="background-color: #34B3F1;color:#ffffff;"  formaction="CHSSConsultBills.htm" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip"  data-placement="top" title="Add Bills" >
																Bills
															</button>		
															<button type="submit"  class="btn btn-sm" formaction="CHSSConsultMainDelete.htm"  Onclick="return confirm('Are You Sure To Delete?');" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip" data-placement="top" title="Delete Bill" disabled>
																<i class="fa-solid fa-trash-can" style="color: red;"></i>
															</button>	
														<%} %>																								
																								
													</td>										
												<%}else{
													claimconsultids.add(cmain[0].toString());
													%>	
													<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno %></span> </td>
													<td> <input type="text" class="form-control items" name="docname-<%=cmain[0]%>"  readonly="readonly" value="<%=cmain[2] %>" style="width:100%; "  maxlength="500" required="required"></td>
													<td> <input type="text" class="form-control consultdate" name="consultdate-<%=cmain[0]%>"   value="<%=rdf.format(sdf.parse(cmain[3].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
													<td>
														<select class="form-control" name="doc-qualification-<%=cmain[0]%>" required="required" >
															<%for(CHSSDoctorRates rate:doctorrates ){ %>
																<option value="<%=rate.getDocRateId() %>" <%if(rate.getDocRateId()==Integer.parseInt(cmain[4].toString())){ %>selected  <%} %> ><%=rate.getDocQualification() %></option>
															<%} %>
														</select>
													</td>
													<td>
													<button type="submit" class="btn btn-sm" style="background-color: #34B3F1;color:#ffffff;"  formaction="CHSSConsultBills.htm" name="consultmainid" value="<%=cmain[0]%>" data-toggle="tooltip"  data-placement="top" title="Add Bills" >
															Bills
														</button>											
													</td>				
												
												<%} %>
											</tr>											
											<%} %>
											
											<%if(sno==0){ %>
												<tr>
													<td colspan="6" style="text-align: center ;">
														Consultations Not Added
													</td>
												</tr>
											
											<%} %>
										</tbody>							
										
									</table>
								</div>
								
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							</form>
							
							<form method="post" action="CHSSConsultMainAdd.htm" autocomplete="off" >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<div class="table-responsive">
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
									<tbody>
										<tr class="" >
											<td style="width:5%;text-align: center;"><span class="sno" id="sno"><%=++sno %></span> </td>
											<td style="width:30%;" ><input type="text" class="form-control items" name="docname"  value="" style="width:100%; "  maxlength="500" required="required"></td>
											<td style="width:20%;" ><input type="text" class="form-control consultdate " name="consultdate"  value="" style="width:100%; "    maxlength="10" readonly required="required"></td>
											<td style="width:25%;">
												<select class="form-control" name="doc-qualification" required="required" >
													<%for(CHSSDoctorRates rate:doctorrates ){ %>
														<option value="<%=rate.getDocRateId() %>"><%=rate.getDocQualification() %></option>
													<%} %>
												</select>
											</td>
											<td style="width:20%;" >
												<button type="submit"  class="btn btn-sm add-btn"  name="action" value="add" >Add</button>  <!-- Onclick="return confirm('Are You Sure To Add ?');" -->
											</td>										
										</tr>
									</tbody>	
								</table>
							</div>
						</form>
						</div>
				
				
						<div class="col-md-6">
							
							<form method="post" action="#" autocomplete="off"  >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<div class="table-responsive">
									<div  style="text-align: center;margin: 3px;width: 99%">
										<b style="color: #F32424">Add From Previous Consultations</b>
									</div>
									<table class="table table-bordered table-hover table-condensed  info shadow-nohover roundedCorners" >
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:50%;" >Doctor Name</th>
												<th style="width:20%;" > Date </th>
												<th style="width:25%;" >Action  </th>
											</tr>
										</thead>
										<tbody >
										<%	int sno1=0;
										
										for(Object[] consult : consulthistory)
										{
											if(!claimconsultids.contains(consult[0].toString())){
											sno1++;	
										
										%>
											
											<tr class="" >
											
												<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno1 %></span> </td>
												<td> <%=consult[3]%></td>
												<td> <%=rdf.format(sdf.parse(consult[2].toString())) %></td>
											
												<td>
													<button type="submit" class="btn btn-sm" style="background-color: #34B3F1;color:#ffffff;"  formaction="CHSSConsultBills.htm" name="consultmainid" value="<%=consult[0]%>" data-toggle="tooltip"  data-placement="top" title="Add Bills" >
														<!-- <i class="fa-solid fa-receipt"></i> --> Bills
													</button>
												</td>										
											
											</tr>
											
											<%} 
											}%>
											<%if(sno1==0){ %>
												<tr>
													<td colspan="6" style="text-align: center ;">
														Previous Consultations Not Found
													</td>
												</tr>
											
											<%} %>
										</tbody>							
										
									</table>
								</div>
								
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							</form>
						
						</div>			
				
					</div>	
						<form action="CHSSFormEdit.htm" method="post" id="form2">
							<div class="row">
								
								<div class="col-md-12" align="center" style="margin-top: 5px;">
									<%if(consultmainlist.size()>0){ %>
									<button type="submit" class="btn btn-sm misc1-btn" name="claimaction" value="F"  data-toggle="tooltip" data-placement="top" title="Preview and Forward"  >
										<i class="fa-solid fa-forward" style="color: #084594"></i> Preview	
									</button>
									
									<%} %>
								</div>
							</div>
							
							<input type="hidden" name="isapproval" value="n">
							<input type="hidden" name="show-edit" value="N">
							<input type="hidden" name="claimaction" value="F">
							<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
					</form>
					
					<form action="CHSSForm.htm" method="post" id="previewform" target="_blank">	
						<input type="hidden" name="claimaction" value="F">
						<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
					
				</div>
			</div>		
			
		</div>
	
	 </div>

<script type="text/javascript">


$(document).ready( function() {
	onlyNumbers();
	
	<%if(consultmainlist.size()>0){%>
		$('#treatmenttype').prop('disabled', true);
		$('#chsstype').prop('disabled', true);
	<%}%> 
});   


var threeMonthsAgo = moment().subtract(3, 'months');

function  onlyNumbers() {    
    
    $('.numberonly').keypress(function (e) {    

        var charCode = (e.which) ? e.which : event.keyCode    

        if (String.fromCharCode(charCode).match(/[^0-9]/g))    

            return false;                        

    });    

}

$('.consultdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(), 
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

function setTooltip()
{
	$('[data-toggle="tooltip"]').tooltip({
		 trigger : 'hover',
	});
	$('[data-toggle="tooltip"]').on('click', function () {
		$(this).tooltip('hide');
	});
}
</script>

<!-- -------------------------------------------------------modal script --------------------------------------------------- -->


</body>
</html>
<%@page import="com.vts.ems.chss.model.CHSSTestMain"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

<style type="text/css">
.nav-pills .nav-link.active, .nav-pills .show>.nav-link 
{
	background-color: #750550;
}
.table thead{
    background: #9AD0EC;
}
</style>
</head>
<body>

<%
	
	Employee employee = (Employee )request.getAttribute("employee") ;
	Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
	String isself = chssapplydata[3].toString();
	List<CHSSTreatType> treattypelist=(List<CHSSTreatType>)request.getAttribute("treattypelist");
	List<Object[]> chssbillslist=(List<Object[]>)request.getAttribute("chssbillslist");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	
	List<CHSSTestMain> testmainlist = (List<CHSSTestMain>)request.getAttribute("testmainlist");
	
	String billid =(String)request.getAttribute("billid");
%>

 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Apply</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item "><a href="CHSSAppliedList.htm">CHSS List</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Data</li>
					</ol>
				</div>
			</div>
	</div>	
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
				<div class="card-body " >
					<form action="CHSSApplyEdit.htm" method="post" autocomplete="off"  >
						
						<div class="card" style="padding: 0.5rem 1rem;margin:-10px 0px 5px 0px;">
						<div class="row">
							
							<%if(isself.equalsIgnoreCase("N")){
								Object[] familyMemberData = (Object[])request.getAttribute("familyMemberData") ; %>
								
								<div class="col-5" >
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
								<div class="col-5">
								<div class="row" style="padding:5px; ">
									<b> Patient Name : &nbsp;</b><%=employee.getEmpName() %>
								</div>
								
								<div class="row" style="padding:5px; ">
									<b>Relation : &nbsp;</b>SELF
									<input type="hidden" name="patientid" value="<%=employee.getEmpId()%>">
									<input type="hidden" name="relationid" value="0">
								</div>
								</div>
							<%} %>
							<div class="col-3">
								<b>Treatment Type : </b><br>
								<select class="form-control select2" name="treatmenttype" required="required">
									<option value="" selected="selected" disabled="disabled">Choose..</option>
									<%for(CHSSTreatType treattype : treattypelist ){ %>
										<option value="<%=treattype.getTreatTypeId()%>" <%if(Integer.parseInt(chssapplydata[7].toString())==treattype.getTreatTypeId()){ %>selected<%} %> ><%=treattype.getTreatmentName() %></option>
									<%} %>								
								</select>
							</div>
							<div class="col-2">
								<b>No of Enclosures : </b> <br><input type="number" class="form-control" name="enclosurecount" value="<%=chssapplydata[7] %>" min="1" required="required" >
							</div>
							<div class="col-2">
								<button type="submit" class="btn btn-sm update-btn" style="margin-top: 15px;" Onclick="return confirm ('Are You Sure To Update?');">UPDATE</button> 
							</div>
						</div>
						</div>
						<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
						
							<form method="post" action="#" autocomplete="off"  >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
									<div class="table-responsive">
									<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
										<thead>
											<tr>
												<th style="width:5%;" >SN</th>
												<th style="width:35%;" >Hospital / Medical / Diagnostics Centre Name</th>
												<th style="width:20%;" >Bill / Receipt No.</th>
												<th style="width:10%;" >Bill Date</th>
												<th style="width:15%; text-align: right;">Amount (&#8377;)</th>
												<th style="width:10%;" >Action  </th>
											</tr>
										</thead>
										<tbody >
										<%	int sno=0;
										for(Object[] obj : chssbillslist){
											sno++;%>
											
											<tr class="" >
											
												<td  style="text-align: center;" > <span class="sno" id="sno"><%=sno %></span> </td>
												<td> <input type="text" class="form-control items" name="centername-<%=obj[0]%>"  id="centername-<%=obj[0]%>"  value="<%=obj[3] %>" style="width:100%; "  maxlength="500" required="required"></td>
												<td> <input type="text" class="form-control items" name="billno-<%=obj[0]%>" id="billno-<%=obj[0]%>"  value="<%=obj[2] %>" style="width:100%;"   maxlength="100" required="required"></td>
												<td> <input type="text" class="form-control billdate" name="billdate-<%=obj[0]%>"  id="billdate-<%=obj[0]%>" value="<%=rdf.format(sdf.parse(obj[4].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
												<%if(obj[5]!=null){ %>
												<td> <input type="number" class="form-control items " name="billamount-<%=obj[0]%>" id="billamount-<%=obj[0]%>"  value="<%=obj[5] %>" style="width:100%;direction: rtl;" min="0" max="9999999" required="required" readonly="readonly"></td>
												<%}else{ %>
												<td> <input type="number" class="form-control items " name="billamount-<%=obj[0]%>" id="billamount-<%=obj[0]%>"  value="0" style="width:100%;direction: rtl;" min="0" max="9999999" required="required" readonly="readonly"></td>
												<%} %>
												<td>
													<button type="submit"  class="btn btn-sm" formaction="CHSSBillEdit.htm" Onclick="return confirm('Are You Sure To Update?');" name="billid" value="<%=obj[0]%>" data-toggle="tooltip" data-placement="top" title="Update Bill">
														<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
													</button>
													<button type="submit"  class="btn btn-sm" formaction="CHSSBillDelete.htm" Onclick="return confirm('Are You Sure To Delete?');" name="billid" value="<%=obj[0]%>" data-toggle="tooltip" data-placement="top" title="Delete Bill">
														<i class="fa-solid fa-trash-can" style="color: red;"></i>
													</button>
													
													<button type="button"  class="btn btn-sm" formaction="CHSSBillDelete.htm"  Onclick="showBillDetails('<%=obj[0]%>')" name="billid" value="<%=obj[0]%>" data-toggle="tooltip"  data-placement="top" title="Bill Details" >
														<i class="fa-solid fa-file-lines"></i>
													</button>
													
												</td>										
											
											</tr>
											
											<%} %>
											<%if(sno==0){ %>
												<tr>
													<td colspan="6" style="text-align: center ;">
														Bills Not Added
													</td>
												</tr>
											
											<%} %>
										</tbody>							
										
									</table>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
							</form>
							<form method="post" action="CHSSBillAdd.htm" autocomplete="off" >
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<div class="table-responsive">
								<table class="table table-bordered table-hover table-condensed  info shadow-nohover">
									<tbody>
										<tr class="" >
										
											<td style="width:5%;text-align: center;"><span class="sno" id="sno"><%=++sno %></span> </td>
											<td style="width:35%;" ><input type="text" class="form-control items " name="centername"  value="" style="width:100%; "  maxlength="500" required="required"></td>
											<td style="width:20%;" ><input type="text" class="form-control items " name="billno"  value="" style="width:100%;"   maxlength="100" required="required"></td>
											<td style="width:10%;" ><input type="text" class="form-control billdate " name="billdate"  value="" style="width:100%; "    maxlength="10" readonly required="required"></td>
											<!-- <td style="width:15%;" ><input type="number" class="form-control items  " name="billamount"  value="" style="width:100%;direction: rtl;" min="0" max="9999999" required="required" ></td> -->
											<td style="width:25%;" >
												<button type="submit"  class="btn btn-sm add-btn" Onclick="return confirm('Are You Sure To Add ?');" name="action" value="add" >ADD</button>
											</td>										
										</tr>
									</tbody>	
								</table>
							</div>
						</form>
					
				</div>
			</div>		
			
		</div>
	
	 </div>
	 
<div class="modal fade my-bill-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" style="min-width: 80% !important;min-height: 80% !important; ">
		<div class="modal-content" >
			<div class="modal-header" style="background: #F5C6A5 ">
		          <div class="row" style="width: 100%;">
				    <div class="col-3">
				    	<span><b>BillNo : </b><span id="modal-billno" ></span></span> 
				    </div>
				    <div class="col-3">
				    	<span><b>Medical Center : </b><span id="modal-centername" ></span></span>
				    </div>
				    <div class="col-3">
				    	<span><b>Bill Date : </b><span id="modal-billdate" ></span></span>
				    </div>
				    <div class="col-3">
				    	<span><b>Bill Amount : &#8377;  </b><span id="modal-billamount" ></span></span>
				    </div>
			    </div>
			    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			    	<i class="fa-solid fa-xmark" aria-hidden="true" ></i>
			    </button>
		    </div>
			<div class="modal-body" style="min-height: 30rem;">
		
			    <div class="row" >
			    	<div class="col-12">
				    	<div class="nav nav-pills nav-justified" id="nav-tabs">
				    		<a class="nav-item nav-link active" data-toggle="tab" id="nav-consultation-tab" href="#nav-consultation" role="tab" aria-controls="nav-consultation"  >Consultation</a>
				    		<a class="nav-item nav-link " data-toggle="tab" id="nav-tests-tab" href="#nav-tests" role="tab" aria-controls="nav-tests"   Onclick="getTestsData();"  >Tests</a>
				    		<a class="nav-item nav-link " data-toggle="tab" id="nav-medicines-tab" href="#nav-medicines" role="tab" aria-controls="nav-medicines" Onclick="getMedicinesData();"  >Medicines</a>
				    		<a class="nav-item nav-link " data-toggle="tab" id="nav-others-tab" href="#nav-others" role="tab" aria-controls="nav-others" onclick="" >Others</a>
				    		<a class="nav-item nav-link " data-toggle="tab" id="nav-misc-tab" href="#nav-misc" role="tab" aria-controls="nav-misc" onclick="getmiscData()" >Miscellaneous</a>
				    	</div>
			    	</div>
			    </div>
			   <div class="tab-content " id="nav-tabContent" style="margin-top: 10px;">
			   
<!-- ------------------------------------------------------- consultation --------------------------------------------------- -->		   
			   		<div class="tab-pane fade show active" id="nav-consultation" role="tabpanel" aria-labelledby="nav-consultation-tab">
			   		
				   		<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:12%;"> Consultation </th>
											<th style="width:30%;">Name of the Doctor</th>
											<th style="width:15%;">Qualification</th>
											<th style="width:15%;">Date</th>
											<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:8%;" > Action </th>
										</tr>
									</thead>
									<tbody id="consult-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="ConsultationBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:15%;"> Consultation </th>
											<th style="width:30%;">Name of the Doctor</th>
											<th style="width:15%;">Qualification</th>
											<th style="width:15%;">Date</th>
											<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:5%;" > <button type="button" class="btn btn-sm tbl-row-add-cons" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_cons" >
											<td   style="text-align: center;" ><span class="sno" id="sno" >1</span> </td>
											<td>
												<select class="form-control w-100" name="consult-type" required="required" >
													<option value="Fresh">Fresh</option>
													<option value="FollowUp">FollowUp</option>
												</select>
											</td>
											<td><input type="text" class="form-control items" name="doc-name" id="doc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
											<td><input type="text" class="form-control items" name="doc-qualification" id="doc-qualification" value="" style="width:100%;"   maxlength="50" required="required"></td>
											<td><input type="text" class="form-control cons-date" name="cons-date" id="cons-date" value="" style="width:100%;"  maxlength="10" readonly required="required"></td>
											<td><input type="number" class="form-control items " name="cons-charge" id="cons-charge" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_cons"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit"  onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>	
								</div>
								
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
				    		
			   		</div>
<!-- ------------------------------------------------------- consultation --------------------------------------------------- -->
<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->			   	
			   		<div class="tab-pane fade " id="nav-tests" role="tabpanel" aria-labelledby="nav-tests-tab">
			   			<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:35%;"> Category </th>
											<th style="width:35%;">Test Name</th>
											<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > Action </th>
										</tr>
									</thead>
									<tbody id="tests-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="TestsBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:35%;"> Category </th>
											<th style="width:35%;">Test Name</th>
											<th style="width:15%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:5%;" > <button type="button" class="btn btn-sm tbl-row-add-tests" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button>  </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_tests" >
											<td   style="text-align: center;" ><span class="sno" id="sno">1</span> </td>
											<td>
												<select class="form-control test-type " id="test-type-1" style="width: 100%" name="test-type" required="required" onchange="getTestSubAdd(1)" >
												
													<option value="" selected="selected" disabled="disabled">Choose..</option>
													<%for(CHSSTestMain testmain : testmainlist){ %>
														<option value="<%=testmain.getTestMainId()%>"><%=testmain.getTestMainName()%></option>
													<%} %>
												</select>
											</td>
											<td>
												<select class="form-control test-id " id="test-id-1" style="width: 100%"  name="test-id" required="required" >
													<option value="" selected="selected" disabled="disabled">Choose..</option>
												</select>
											</td>
											<td><input type="number" class="form-control items" name="tests-cost" id="tests-cost" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_tests"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit" >SUBMIT</button>	
								</div>
								
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
			   			
			   		</div>
<!-- ------------------------------------------------------- Tests --------------------------------------------------- -->
<!-- ------------------------------------------------------- medicines --------------------------------------------------- -->			   		
			   		<div class="tab-pane fade " id="nav-medicines" role="tabpanel" aria-labelledby="nav-medicines-tab">
			   		
				   		<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:45%;"> Medicine Name </th>
											<th style="width:20%;">Date</th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > Action </th>
										</tr>
									</thead>
									<tbody id="meds-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="MedicinesBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:45%;"> Medicine Name </th>
											<th style="width:20%;">Date</th>
											<th style="width:20%; text-align: right;">Amount (&#8377;)</th> 
											<th style="width:10%;" > <button type="button" class="btn btn-sm tbl-row-add-meds" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_meds" >
											<td   style="text-align: center;" ><span class="sno" id="sno">1</span> </td>
											<td><input type="text" class="form-control items" name="meds-name" id="meds-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
											<td><input type="text" class="form-control meds-date" name="meds-date" id="meds-date" value="" style="width:100%;"  maxlength="10" readonly required="required"></td>
											<td><input type="number" class="form-control items " name="meds-cost" id="meds-cost" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_meds"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit"  onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>	
								</div>
								
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
				    		
			   			
			   		</div>
<!-- ------------------------------------------------------- medicines --------------------------------------------------- -->
<!-- ------------------------------------------------------- Others --------------------------------------------------- -->	
			   		<div class="tab-pane fade " id="nav-others" role="tabpanel" aria-labelledby="nav-others-tab">
			   		
			   			<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:65%;"> Item Name </th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > Action </th>
										</tr>
									</thead>
									<tbody id="oths-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="OthersBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:65%;"> Item Name </th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > <button type="button" class="btn btn-sm tbl-row-add-oths" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_oths" >
											<td   style="text-align: center;" ><span class="sno" id="sno">1</span> </td>
											<td><input type="text" class="form-control items" name="oths-name" id="oths-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
											<td><input type="number" class="form-control items " name="oths-cost" id="oths-cost" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_oths"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>	
								</div>
								
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
			   			
			   		</div>
<!-- ------------------------------------------------------- Others --------------------------------------------------- -->	
<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->	
			   		<div class="tab-pane fade " id="nav-misc" role="tabpanel" aria-labelledby="nav-misc-tab">
			   		
			   			<div class="col-md-12" >
				    		<form action="#" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:65%;"> Item Name </th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > Action </th>
										</tr>
									</thead>
									<tbody id="misc-list-table">
										

									</tbody>
								</table>
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
				   		<div class="col-md-12" >
				    		<form action="MiscBillAdd.htm" method="post" autocomplete="off" style="width: 100%;">
				    			<table class="table table-bordered table-hover table-striped table-condensed  info shadow-nohover" >
									<thead>
										<tr>
											<th style="width:5%;" >SN</th>
											<th style="width:65%;"> Item Name </th>
											<th style="width:20%; text-align: right;">Amount  (&#8377;)</th> 
											<th style="width:10%;" > <button type="button" class="btn btn-sm tbl-row-add-misc" data-toggle="tooltip" data-placement="top" title="Add Row"><i class="fa-solid fa-plus " style="color: green;"></i></button> </th>
										</tr>
									</thead>
									<tbody>
										<tr class="tr_clone_misc" >
											<td   style="text-align: center;" ><span class="sno" id="sno">1</span> </td>
											<td><input type="text" class="form-control items" name="misc-name" id="misc-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
											<td><input type="number" class="form-control items " name="misc-cost" id="misc-cost" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>
											<td><button type="button" class="btn btn-sm tbl-row-rem_misc"><i class="fa-solid fa-minus" style="color: red;" data-toggle="tooltip" data-placement="top" title="Remove This Row" ></i></button> </td>
										</tr>
									</tbody>							
									
								</table>
								<div align="center">
									<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>	
								</div>
								
								<input type="hidden" class="billid" name="billid" value="">
								<input type="hidden" name="chssapplyid" value="<%=chssapplydata[0]%>">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    </form>
				    	</div>
			   			
			   		</div>
<!-- ------------------------------------------------------- Miscellaneous --------------------------------------------------- -->			   		
			   </div>
			</div>
		      
		</div>
	</div>
</div>
<script type="text/javascript">

function showBillDetails($billid)
{
	$(".billid").val($billid);
	
	$.ajax({

		type : "GET",
		url : "GetBillDataAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		
			$('#modal-billno').html(result[2]);
			$('#modal-centername').html(result[3]);
			$('#modal-billdate').html(result[4]);
			if(result[5]!=null){
				$('#modal-billamount').html(result[5]);
			
			}else{
				$('#modal-billamount').html('0');
			}
			
		}
	});
	
	
	$.ajax({

		type : "GET",
		url : "ChssConsultationListAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var consultVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var consultHTMLStr = '';
		for(var c=0;c<consultVals.length;c++)
		{
			var consult = consultVals[c];
			consultHTMLStr +=	'<tr> ';
			consultHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (c+1) +'.</span> </td> ';
			consultHTMLStr +=	'	<td> ';
			consultHTMLStr +=	'		<select class="form-control w-100" name="consult-type-'+consult.ConsultationId+'"  required="required" > ';
																		
										if(consult.ConsultType.toUpperCase() === 'FRESH'){
			consultHTMLStr +=	'			<option value="Fresh" selected >Fresh</option> ';
			consultHTMLStr +=	'			<option value="FollowUp">FollowUp</option> ';
										}else{
			consultHTMLStr +=	'			<option value="Fresh">Fresh</option> ';
			consultHTMLStr +=	'			<option value="FollowUp" selected >FollowUp</option> ';
										}				
			
			consultHTMLStr +=	'		</select> ';
			consultHTMLStr +=	'	</td> ';
			consultHTMLStr +=	' 	<td><input type="text" class="form-control items" name="doc-name-'+consult.ConsultationId+'" id="doc-name" value="'+consult.DocName+'" style="width:100%; "  maxlength="255" required="required"></td> ';
			consultHTMLStr +=	'	<td><input type="text" class="form-control items" name="doc-qualification-'+consult.ConsultationId+'" id="doc-qualification" value="'+consult.DocQualification+'" style="width:100%;"   maxlength="50" required="required"></td> ';
			
			let now = new Date(consult.ConsultDate);
			var dateString = moment(now).format('DD-MM-YYYY');
			
			consultHTMLStr +=	'	<td><input type="text" class="form-control cons-date" name="cons-date-'+consult.ConsultationId+'" id="cons-date" value="'+dateString+'" style="width:100%;"  maxlength="10" readonly required="required"></td> ';
			
			consultHTMLStr +=	'	<td><input type="number" class="form-control items " name="cons-charge-'+consult.ConsultationId+'" id="cons-charge" value="'+consult.ConsultCharge+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="req uired" ></td> ';
			consultHTMLStr +=	'	<td>';
			consultHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="consultationid" value="'+consult.ConsultationId+'" formaction="ConsultationBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
			consultHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="consultationid" value="'+consult.ConsultationId+'" formaction="ConsultationBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
			consultHTMLStr +=	'	</td> ';
			consultHTMLStr +=	'</tr> ';
			
		}
		
		if(consultVals.length==0){
			
			consultHTMLStr +=	'<tr><td colspan="7" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		$('#consult-list-table').html(consultHTMLStr);
		
		setTooltip();
		$('.cons-date').daterangepicker({
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

		}
	});
	
	$('#nav-consultation-tab').click();
	
	$('.my-bill-modal').modal('toggle');
	
}




</script>

<script type="text/javascript">

var count=1;

$("table").on('click','.tbl-row-add-cons' ,function() 
{
   	var $tr = $('.tr_clone_cons').last('.tr_clone_cons');
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	$clone.find(".items").val("").end();

	count++;
	
	$clone.find(".sno").html(count).end(); 
	 
  	$clone.find('.cons-date').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"maxDate" :new Date(),
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
	}
	});
  	
  	setTooltip();
  
});


$("table").on('click','.tbl-row-rem_cons' ,function() {
var cl=$('.tr_clone_cons').length;
if(cl>1){
          
   var $tr = $(this).closest('.tr_clone_cons');
   var $clone = $tr.remove();
   $tr.after($clone);
  
}
  
});

$('.cons-date').daterangepicker({
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


</script>

<!-- ------------------------------------------------------- tests script --------------------------------------------------- -->

<script type="text/javascript">

var counttest=1;

$("table").on('click','.tbl-row-add-tests' ,function() 
{
	
   	var $tr = $('.tr_clone_tests').last('.tr_clone_tests');
   
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	
   
   	$clone.find('.items').val('').end();
	counttest++;
   	$clone.find('.sno').html(counttest).end();
   	$clone.find('.test-type').attr('id', 'test-type-'+counttest).end();
   	$clone.find('.test-type').attr("onchange", "getTestSubAdd("+counttest+")").val("").end();
	$clone.find('.test-id').attr('id', 'test-id-'+counttest).html('<option value="" selected="selected" disabled="disabled">Choose..</option>').end();
	
	 
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


function getTestSubAdd(testrowid)
{
	var testmainid = $('#test-type-'+testrowid).val();
	$.ajax({

		type : "GET",
		url : "GetTestSubListAjax.htm",
		data : {
				
			testmainid : testmainid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var testsubs= Object.keys(result).map(function(e){
			return result[e]
		})
		var subtestsHTML = '<option value="" selected="selected" disabled="disabled">Choose..</option>';
		for(var st=0;st<testsubs.length;st++)
		{
			var subtest = testsubs[st];			
			subtestsHTML += '<option value="'+subtest.TestSubId+'" >';
			subtestsHTML += subtest.TestName ;
			subtestsHTML += '</option>';
		}
		
		if(testsubs.length==0){
			
			 subtestsHTML +='<option value="" selected="selected" disabled="disabled">No Data</option> ';
		}
		
		$('#test-id-'+testrowid).html(subtestsHTML);
		/* $('#test-id-'+testrowid).select2(); */
		setTooltip();

		}
	});
}
var $testsmainlist = null;
function getTestsMainList()
{
	$.ajax({

		type : "GET",
		url : "GetTestMainListAjax.htm",
		data : {
			
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var testsmain= Object.keys(result).map(function(e){
			return result[e]
		})
		 $testsmainlist = testsmain;
	
		}
	});	
	
}
getTestsMainList();

const getTestsSubList = async function(testmainid,testssublist) 
{
	/* var testssublist= null; */
	$.ajax({
	
		type : "GET",
		url : "GetTestSubListAjax.htm",
		async : true,
		data : {
			testmainid : testmainid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var testsub= Object.keys(result).map(function(e){
			return result[e]
		})
	
		testssublist =  testsub;
		console.log(testssublist );
		}
	});	
	
	return testssublist;
}

function getTestsData()
{
	var $billid = $('.billid').val();
	
	$.ajax({

		type : "GET",
		url : "ChssTestsListAjax.htm",
		async : false,
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var testVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var testsHTMLStr = '';
		
		for(var t=0;t<testVals.length;t++)
		{
			
			var test = testVals[t];
			console.log(test);
			testsHTMLStr +=	'<tr> ';
			testsHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (t+1) +'.</span> </td> ';
			
			
			testsHTMLStr +=	'<td>';
			testsHTMLStr +=	'	<select class="form-control" style="width: 100%" name="test-type-'+test.CHSSTestId+'" required="required" onchange="getTestSubEdit('+test.CHSSTestId+')" >';
								for(var t=0;t<$testsmainlist.length;t++){
									if(true){
			testsHTMLStr +=	'		<option value="'+$testsmainlist[t].TestMainId+'"  >'+$testsmainlist[t].TestMainName+'</option>';
									}
								}
			testsHTMLStr +=	'	</select>';
			testsHTMLStr +=	'</td>';

			var testssublist = null;
			
				$.ajax({
				
					type : "GET",
					url : "GetTestSubListAjax.htm",
					async : true,
					data : {
						testmainid : test.TestMainId,
					},
					datatype : 'json',
					success : function(result) {
					var result = JSON.parse(result);
					var testsub= Object.keys(result).map(function(e){
						return result[e]
					})
				
					testssublist =  testsub;
					/* funcname(test,testsHTMLStr,testssublist);
					console.log(testssublist ); */
					
					testsHTMLStr +=	'<td>';
					testsHTMLStr +=	'	<select class="form-control" style="width: 100%" name="test-type-'+test.CHSSTestId+'" required="required" onchange="getTestSubEdit('+test.CHSSTestId+')" >';
										for(var i=0;i<testssublist.length;i++){
											if(true){
					testsHTMLStr +=	'		<option value="'+testssublist[i].TestSubId+'">'+testssublist[i].TestName+'</option>';
											}
										}
					testsHTMLStr +=	'	</select>';
					testsHTMLStr +=	'</td>';
					
					
					testsHTMLStr +=	'	<td><input type="number" class="form-control items " name="tests-cost'+test.CHSSTestId+'"  value="'+test.TestCost+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="req uired" ></td> ';
					testsHTMLStr +=	'	<td>';
					testsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="testsid" value="'+test.CHSSTestId+'" formaction="MedicineBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
					testsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="testsid" value="'+test.CHSSTestId+'" formaction="MedicineBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
					testsHTMLStr +=	'	</td> ';
					testsHTMLStr +=	'</tr> ';
					}
				});	
				
			
		
			
			
		}
		
		if(testVals.length==0){
			
			medsHTMLStr +=	'<tr><td colspan="7" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		setTimeout(function() { 
			$('#tests-list-table').html(testsHTMLStr)
			}, 10);
		
		
		setTooltip();
		$('.meds-date').daterangepicker({
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

		}
	});
}

function funcname(test,testsHTMLStr,testssublist){	
	
	
	testsHTMLStr +=	'<td>';
	testsHTMLStr +=	'	<select class="form-control" style="width: 100%" name="test-type-'+test.CHSSTestId+'" required="required" onchange="getTestSubEdit('+test.CHSSTestId+')" >';
						for(var t=0;t<testssublist.length;t++){
							if(true){
	testsHTMLStr +=	'		<option value="'+testssublist[t].TestSubId+'"  >'+testssublist[t].TestName+'</option>';
							}
						}
	testsHTMLStr +=	'	</select>';
	testsHTMLStr +=	'</td>';
	
	
	testsHTMLStr +=	'	<td><input type="number" class="form-control items " name="tests-cost'+test.CHSSTestId+'"  value="'+test.TestCost+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="req uired" ></td> ';
	testsHTMLStr +=	'	<td>';
	testsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="testsid" value="'+test.CHSSTestId+'" formaction="MedicineBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
	testsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="testsid" value="'+test.CHSSTestId+'" formaction="MedicineBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
	testsHTMLStr +=	'	</td> ';
	testsHTMLStr +=	'</tr> ';
	}



</script>
<!-- ------------------------------------------------------- tests script --------------------------------------------------- -->

<!-- ------------------------------------------------------- medicines script--------------------------------------------------- -->
<script type="text/javascript">

var count=1;

$("table").on('click','.tbl-row-add-meds' ,function() 
{
   	var $tr = $('.tr_clone_meds').last('.tr_clone_meds');
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	$clone.find(".items").val("").end();

	count++;
	
	$clone.find(".sno").html(count).end(); 
	 
  	$clone.find('.meds-date').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"maxDate" :new Date(),
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
	}
	});
  	
  	setTooltip();
  
});


$("table").on('click','.tbl-row-rem_meds' ,function() {
var cl=$('.tr_clone_meds').length;
if(cl>1){
          
   var $tr = $(this).closest('.tr_clone_meds');
   var $clone = $tr.remove();
   $tr.after($clone);
  
}
  
});

$('.meds-date').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(),
	"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
}
});


/* <td><input type="text" class="form-control items" name="meds-name" id="meds-name" value="" style="width:100%; "  maxlength="255" required="required"></td>
<td><input type="text" class="form-control meds-date" name="meds-date" id="meds-date" value="" style="width:100%;"  maxlength="10" readonly required="required"></td>
<td><input type="number" class="form-control items " name="meds-cost" id="meds-cost" value="" style="width:100%;direction: rtl;" min="1" max="9999999" required="required" ></td>

 */
function getMedicinesData(){
	
	var $billid = $('.billid').val();
	
	$.ajax({

		type : "GET",
		url : "ChssMedicinesListAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var medsVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var medsHTMLStr = '';
		for(var m=0;m<medsVals.length;m++)
		{
			var meds = medsVals[m];
			medsHTMLStr +=	'<tr> ';
			medsHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (m+1) +'.</span> </td> ';
			
			medsHTMLStr +=	' 	<td><input type="text" class="form-control items" name="meds-name-'+meds.MedicineId+'" id="doc-name" value="'+meds.MedicineName+'" style="width:100%; "  maxlength="255" required="required"></td> ';
			
			let now = new Date(meds.MedicineDate);
			var dateString = moment(now).format('DD-MM-YYYY');
			
			medsHTMLStr +=	'	<td><input type="text" class="form-control meds-date" name="meds-date-'+meds.MedicineId+'" id="meds-date" value="'+dateString+'" style="width:100%;"  maxlength="10" readonly required="required"></td> ';
			
			medsHTMLStr +=	'	<td><input type="number" class="form-control items " name="meds-cost-'+meds.MedicineId+'" id="meds-cost" value="'+meds.MedicineCost+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="req uired" ></td> ';
			medsHTMLStr +=	'	<td>';
			medsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="medicineid" value="'+meds.MedicineId+'" formaction="MedicineBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
			medsHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="medicineid" value="'+meds.MedicineId+'" formaction="MedicineBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
			medsHTMLStr +=	'	</td> ';
			medsHTMLStr +=	'</tr> ';
			
		}
		
		if(medsVals.length==0){
			
			medsHTMLStr +=	'<tr><td colspan="7" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		$('#meds-list-table').html(medsHTMLStr);
		
		setTooltip();
		$('.meds-date').daterangepicker({
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

		}
	});
}

</script>
<!-- ------------------------------------------------------- medicines script --------------------------------------------------- -->

<!-- ------------------------------------------------------- Miscellaneous script--------------------------------------------------- -->
<script type="text/javascript">

var misccount=1;

$("table").on('click','.tbl-row-add-misc' ,function() 
{
   	var $tr = $('.tr_clone_misc').last('.tr_clone_misc');
   	var $clone = $tr.clone();
   	$tr.after($clone);
   	$clone.find(".items").val("").end();

   	misccount++;
	
	$clone.find(".sno").html(misccount).end(); 
	 
   	setTooltip();
  
});


$("table").on('click','.tbl-row-rem_misc' ,function() {
var cl=$('.tr_clone_misc').length;
if(cl>1){
          
   var $tr = $(this).closest('.tr_clone_misc');
   var $clone = $tr.remove();
   $tr.after($clone);
  
}
  
});

function getmiscData(){
	
	var $billid = $('.billid').val();
	
	$.ajax({

		type : "GET",
		url : "ChssMiscListAjax.htm",
		data : {
				
			billid : $billid,
		},
		datatype : 'json',
		success : function(result) {
		var result = JSON.parse(result);
		var miscVals= Object.keys(result).map(function(e){
			return result[e]
		})
		var miscHTMLStr = '';
		for(var m=0;m<miscVals.length;m++)
		{
			var misc = miscVals[m];
			
			
				miscHTMLStr +=	'<tr> ';
				miscHTMLStr +=	'	<td  style="text-align: center;" ><span class="sno" id="sno" >'+ (m+1) +'.</span> </td> ';
				
				miscHTMLStr +=	' 	<td><input type="text" class="form-control items" name="misc-name-'+misc.ChssMiscId+'"  value="'+misc.MiscItemName+'" style="width:100%; "  maxlength="255" required="required"></td> ';
				
				let now = new Date(misc.MedicineDate);
				var dateString = moment(now).format('DD-MM-YYYY');
				
				miscHTMLStr +=	'	<td><input type="number" class="form-control items " name="misc-cost-'+misc.ChssMiscId+'"  value="'+misc.MiscItemCost+'" style="width:100%;direction: rtl;" min="1" max="9999999" required="req uired" ></td> ';
				miscHTMLStr +=	'	<td>';
				miscHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="chssmiscid" value="'+misc.ChssMiscId+'" formaction="MiscBillEdit.htm" data-toggle="tooltip" data-placement="top" title="Update"  Onclick="return confirm(\'Are You Sure To Update ?\');"><i class="fa-solid fa-pen-to-square" style="color: #FF7800;" ></i></button>'; 
				miscHTMLStr +=	'		<button type="submit" class="btn btn-sm" name="chssmiscid" value="'+misc.ChssMiscId+'" formaction="MiscBillDelete.htm" data-toggle="tooltip" data-placement="top" title="Delete"  Onclick="return confirm(\'Are You Sure To Delete ?\');"><i class="fa-solid fa-trash-can" style="color: red;"></i></button> ';
				miscHTMLStr +=	'	</td> ';
				miscHTMLStr +=	'</tr> ';
			
			
		}
		
		if(miscVals.length==0){
			
			miscHTMLStr +=	'<tr><td colspan="4" style="text-align: center;"> No Record Found</td></tr> ';
		}
		
		$('#misc-list-table').html(miscHTMLStr);
		
		setTooltip();
	

		}
	});
}

</script>
<!-- ------------------------------------------------------- Miscellaneous script --------------------------------------------------- -->
<script type="text/javascript">



$('.billdate').daterangepicker({
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




<%-- <%if(chssbillslist.size()>0){%>
	showBillDetails(<%=chssbillslist.get(0)[0]%>);
<%}%> --%>
</script>
</body>
</html>
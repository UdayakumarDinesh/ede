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
												<th style="width:5%;" >SNo.</th>
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
											
												<td> <span class="sno" id="sno"><%=sno %></span> </td>
												<td> <input type="text" class="form-control items" name="centername-<%=obj[0]%>"  id="centername-<%=obj[0]%>"  value="<%=obj[3] %>" style="width:100%; "  maxlength="500" required="required"></td>
												<td> <input type="text" class="form-control items" name="billno-<%=obj[0]%>" id="billno-<%=obj[0]%>"  value="<%=obj[2] %>" style="width:100%;"   maxlength="100" required="required"></td>
												<td> <input type="text" class="form-control billdate" name="billdate-<%=obj[0]%>"  id="billdate-<%=obj[0]%>" value="<%=rdf.format(sdf.parse(obj[4].toString())) %>" style="width:100%; "    maxlength="10" readonly required="required"></td>
												<td> <input type="number" class="form-control items " name="billamount-<%=obj[0]%>" id="billamount-<%=obj[0]%>"  value="<%=obj[5] %>" style="width:100%;direction: rtl;" min="0" max="9999999" required="required" ></td>
												<td>
													<button type="submit"  class="btn" formaction="CHSSBillEdit.htm" Onclick="return confirm('Are You Sure To Update?');" name="billid" value="<%=obj[0]%>" data-toggle="tooltip" data-placement="top" title="Update Bill">
														<i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
													</button>
													<button type="submit"  class="btn" formaction="CHSSBillDelete.htm" Onclick="return confirm('Are You Sure To Delete?');" name="billid" value="<%=obj[0]%>" data-toggle="tooltip" data-placement="top" title="Delete Bill">
														<i class="fa-solid fa-trash-can" style="color: red;"></i>
													</button>
													
													<button type="button"  class="btn" formaction="CHSSBillDelete.htm"  Onclick="showBillDetails('<%=obj[0]%>')" name="billid" value="<%=obj[0]%>" data-toggle="tooltip"  data-placement="top" title="Bill Details" >
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
										
											<td style="width:5%;" ><span class="sno" id="sno"><%=++sno %></span> </td>
											<td style="width:35%;" ><input type="text" class="form-control items " name="centername"  value="" style="width:100%; "  maxlength="500" required="required"></td>
											<td style="width:20%;" ><input type="text" class="form-control items " name="billno"  value="" style="width:100%;"   maxlength="100" required="required"></td>
											<td style="width:10%;" ><input type="text" class="form-control billdate " name="billdate"  value="" style="width:100%; "    maxlength="10" readonly required="required"></td>
											<td style="width:15%;" ><input type="number" class="form-control items  " name="billamount"  value="" style="width:100%;direction: rtl;" min="0" max="9999999" required="required" ></td>
											<td style="width:10%;" >
												<button type="submit"  class="btn btn-sm add-btn" Onclick="return confirm('Are You Sure To Add ?');" name="action" value="add" >													ADD
												</button>
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
	<div class="modal-dialog modal-lg modal-dialog-centered" style="min-width: 80% !important; ">
		<div class="modal-content" >
			<div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">BillNo:</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			    	<span aria-hidden="true">&times;</span>
			    </button>
		    </div>
		    <div class="modal-body">
		    <form>
		        <div class="form-group">
		            <label for="recipient-name" class="col-form-label">Recipient:</label>
		            <input type="text" class="form-control" id="recipient-name">
		        </div>
		        <div class="form-group">
		            <label for="message-text" class="col-form-label">Message:</label>
		            <textarea class="form-control" id="message-text"></textarea>
		        </div>
		    </form>
		</div>
		      
		</div>
	</div>
</div>

	 

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

function showBillDetails($billid)
{
	var $centername=$('#centername-'+$billid).val();
	var $billno=$('#billno-'+$billid).val();
	var $billdate=$('#billdate-'+$billid).val();
	var $billamount=$('#billamount-'+$billid).val();
	
	console.log($billid , $centername, $billno, $billdate,  $billamount);
	
	$('.my-bill-modal').modal('toggle');
}



</script>


</body>
</html>
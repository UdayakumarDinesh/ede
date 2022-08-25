<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.io.File"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import=" java.math.RoundingMode, java.text.DecimalFormat" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"   import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

<jsp:include page="../static/sidebar.jsp"></jsp:include> 

<style>


.a-box {
  display: inline-block;
  width: 100%;
  text-align: center;
}

.img-container {
    height: 11rem;
   /*  width: 200px; */
    overflow: hidden;
    border-radius: 0px 0px 20px 20px;
    display: inline-block;
}

.img-container img {
    transform: skew(0deg, -13deg);
    height: 6.5rem;
    margin: 25px 0px 0px 0px;
        border-radius: 53%;
}

.inner-skew {
    display: inline-block;
    border-radius: 20px;
    overflow: hidden;
    padding: 0px;
    transform: skew(0deg, 13deg);
    font-size: 0px;
    margin: 30px 0px 0px 0px;
    background: #c8c2c2;
    height: 11rem;
    width: 8rem;
}

.text-container {
  box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.2);
  padding: 67px 20px 8px 20px;
  border-radius: 20px;
  background: #fff;
  margin:-83px 0px 0px 0px;
  line-height: 19px;
  font-size: 14px;
}

.text-container h3 {
  margin: 16px 0px 3px 0px;
  font-size: 100%;
  white-space:pre-line;
      font-family: 'Lato';
    text-transform: capitalize;
    font-weight: 700;
    color: #005C97;
}

.main{
	padding :0px 0px 1.5rem 0px ;
	cursor: pointer;
	 transition: transform .2s;
	 
}

.profile-card-container{
	border-radius: 12px;
    background-color: white;
    /* max-height: 20rem !important; */
    padding: 0px 20px;
    margin: 6px 8px 14px 2px;
    max-width:50rem;
}

.fa-angle-left, .fa-angle-right{
	font-size: 3rem;
	color:black;
	
}

.table-card , .profile-card-container{
	  border: none;
	  box-shadow: 4px 7px 5px rgb(0 0 0 / 10%);
}

.table-card{
	border-radius: 0px !important;
}

.profile-card-container > .row {
  display: block;
  overflow-x: auto;
  white-space: nowrap;
  overflow-y : hidden;
}
.profile-card-container > .row > .col-md-2 {
  display: inline-block;
}

.table thead tr{
	background-color: #0e6fb6;
	color: white;
}

.main:hover{
	transform : scale(1.1);
}




.outer-wrapper {
  max-width: 100vw;
  overflow-x: scroll;
  position: relative;
  scrollbar-color: #d5ac68 #f1db9d;
  scrollbar-width: thin;
  -ms-overflow-style: none;
}


.pseduo-track {
  background-color: #f1db9d;
  height: 2px;
  width: 100%;
  position: relative;
  top: -3px;
  z-index: -10;
}


.outer-wrapper::-webkit-scrollbar {
  height: 5px;
}

.outer-wrapper::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 0px rgba(0, 0, 0, 0);
}

.outer-wrapper::-webkit-scrollbar-thumb {
  height: 5px;
  background-color: #d5ac68;
}

.outer-wrapper::-webkit-scrollbar-thumb:hover {
  background-color: #f1db9d;
}

.outer-wrapper::-webkit-scrollbar:vertical {
  display: none;
}

.inner-wrapper {
  display: flex;
  padding-bottom: 10px;
}

.pseudo-item {
  height: 300px;
  width: 369px;
  margin-right: 59px;
  flex-shrink: 0;
  background-color: gray;
}

.pseudo-item:nth-of-type(2n) {
  background-color: lightgray;
}

.select2-container{
	width:282px !important;
}

.custom-navbar{
	border-top-left-radius: 7px;
	border-top-right-radius: 7px;
	background-color: #e9ecef !important;
}

.employee-details{
	margin-bottom: 0px !important;
	font-size: 12px;
    color: #005C97;
    font-weight: 600;
    text-align: left;
    padding: 0px 0px 0px 0rem;
}

 .heading-breadcrumb{
	   /*  margin-top: 10px !important; */
	    font-family: 'Montserrat',sans-serif;
	    font-weight: 700 !important;
	    color: #005C97;
} 

.page-button{
	margin: 0.4rem 19px;
    background-color: #e9ecef !important;
    border-bottom: 0;
    border-radius: 7px;
    padding: 0rem 1.25rem;
} 
 

.apply-bn
{
	padding: 2px;
	margin-top: 5px;

}


</style>
</head>
<body >

<% String logintype = (String) session.getAttribute("LoginType"); 
	List<Object[]> dashboard = (List<Object[]>)request.getAttribute("dashboard");
	List<Object[]> empfamilylist = (List<Object[]>)request.getAttribute("empfamilylist");
	Object[] employee = (Object[])request.getAttribute("employee") ;
	List<Object[]> empchsslist = (List<Object[]>)request.getAttribute("empchsslist");
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	IndianRupeeFormat nfc=new IndianRupeeFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	String Fromdate=(String)request.getAttribute("Fromdate");
	String Todate=(String)request.getAttribute("Todate");
	
	String patientname = "All";
	if(request.getAttribute("patientname")!=null){
		patientname=(String)request.getAttribute("patientname") ;
	}
	
	String patientidvalue = "0";
	if(request.getAttribute("patientidvalue")!=null){
		patientidvalue=(String)request.getAttribute("patientidvalue");
	}
	
	String IsSelf = "Y";
	if(request.getAttribute("IsSelf")!=null){
		IsSelf=(String)request.getAttribute("IsSelf");
	}
	
	DecimalFormat df = new DecimalFormat("0.00");
%>





	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3 ">
				<h5>CHSS DASHBOARD</h5>
			</div>
			<div class="col-md-9 " >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">CHSS</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	

 
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
	
<div class=" card profile-card-container " >
 
	<div class="row" >
		<div class="col-md-2">
		<%if(!IsSelf.equalsIgnoreCase("Y")){ %>
			<div class="main" onclick="submitform('Y','<%=employee[0]%>','<%=employee[2] %>')" > 
			<%}else{ %>
			<div class="main" > 
			<%} %>
			 
				<div class="a-box">
					<div class="img-container">
						<div class="img-inner">
							<div class="inner-skew">
								<% if(employee[5].toString().equalsIgnoreCase("F")) { %>
									<img src="view/images/femaleuser.png" alt="Photo Not Found">
								<% }else{ %>
									<img src="view/images/maleuser.png" alt="Photo Not Found">
								<% } %>
							</div>
						</div>
					</div>
					<div class="text-container" <%if(patientidvalue.equalsIgnoreCase(employee[0].toString())) {%>style="box-shadow: 0px 0px 10px 0px rgb(230 100 10 / 90%)" <%} %>>
						<h3><%=employee[2] %> <span style="font-weight: 700;font-size: 13px;" > (Self)</span></h3>
						<p class="employee-details">&#9679; DOB : <%=rdf.format(sdf.parse(employee[10].toString()))%></p>
						<%-- <p class="employee-details">&#9679; Blood Group : <%if(employee.getBloodGroup()!=null){ %> <%=employee.getBloodGroup()%> <%}else{ %> - <%} %></p> --%>
						
						<%if(IsSelf.equalsIgnoreCase("Y")){ %>
						<button type="button" class="btn btn-sm misc2-btn apply-bn"	<% if(patientname.equalsIgnoreCase("All")){ %> style="display: none" <%} %> name="Action" value="APPLY" onclick="applyform()" data-toggle="tooltip" data-placement="bottom" title="Apply New Claim">
							
							&nbsp;&nbsp;Apply&nbsp;
						</button>
						<%} %>
					</div>
				</div>	
			</div>
		</div>
		
		<%for(Object[] obj : empfamilylist){ %>
		<div class="col-md-2">
			<%if(!(IsSelf.equalsIgnoreCase("N") && patientidvalue.equalsIgnoreCase(obj[0].toString()))){ %>
			<div class="main" onclick="submitform('N','<%=obj[0]%>','<%=obj[1] %>')" > 
			<%}else{ %>
			<div class="main" > 
			<%} %>
				<div class="a-box">
					<div class="img-container">
						<div class="img-inner">
							<div class="inner-skew">
								<%if(obj[8].toString().equalsIgnoreCase("F")){ %>
								<img src="view/images/femaleuser.png" alt="Photo Not Found">
								<%}else{ %>
								<img src="view/images/maleuser.png" alt="Photo Not Found">
								<%}%>
							</div>
						</div>
					</div>
					<div class="text-container" <%if(patientidvalue.equalsIgnoreCase(obj[0].toString())) {%>style="box-shadow: 0px 0px 10px 0px rgb(230 100 10 / 90%)" <%} %>>
						<h3><%=obj[1] %> <span style="font-weight: 700;font-size: 13px;" >(<%=obj[7] %>)</span></h3>
						<p class="employee-details">&#9679; DOB : <%=rdf.format(sdf.parse(obj[3].toString()))%></p>
						<p class="employee-details">&#9679;Dep from : <%=rdf.format(sdf.parse(obj[9].toString()))%></p>						
						<%if(IsSelf.equalsIgnoreCase("N") && patientidvalue.equalsIgnoreCase(obj[0].toString())){ %>
							<button type="button" class="btn btn-sm misc2-btn apply-bn"	<% if(patientname.equalsIgnoreCase("All")){ %> style="display: none" <%} %> name="Action" value="APPLY" onclick="applyform()" data-toggle="tooltip" data-placement="bottom" title="Apply New Claim">
								&nbsp;&nbsp;Apply&nbsp;
							</button>
						<%} %>
						 
					</div>
				</div>	
			</div>
		</div>
		<%} %>
	</div>
</div>

	<div class="nav navbar bg-light dashboard-margin custom-navbar">
		<div class="col-md-3"></div>
		<div class="col-md-4 d-flex justify-content-center">
			<h4 style="color: #005C97;font-weight: 700;text-transform: capitalize;"><%=patientname %> Applied List 	</h4>
		</div>
		<label style=" font-weight: 800">From Date : &nbsp; </label>
		<input  class="form-control form-control date"  data-date-format="dd-mm-yyyy" id="datepicker1" name="Fromdate"  required="required"  style="width: 120px;"
		<%if(Fromdate!=null){%> value="<%=(Fromdate) %>" <%} %> onchange="changeform('<%=patientname %>')" >
	
		<label style="font-weight: 800;padding-left: 5px">To Date :  &nbsp; </label>
		<input  class="form-control form-control" data-date-format="dd-mm-yyyy" id="datepicker3" name="Todate"  style="width: 120px;"
		<%if(Todate!=null){%> value="<%=(Todate) %>" <%} %>  onchange="changeform('<%=patientname %>')" >  
		
	</div>

<div class="card table-card dashboard-margin" >
	<div class="card-body "  style="padding: 1rem !important;">				
		<form action="#" method="post" id="ClaimForm">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="table-responsive" style="max-height: 200px !important" >
					<table class="table table-bordered table-hover table-striped table-condensed" id="myTable"  > 
						<thead>
							<tr>
								<td style="padding-top:5px; padding-bottom: 5px;">SN</td>
								<td style="padding-top:5px; padding-bottom: 5px;" >Claim No</td>
								<td style="padding-top:5px; padding-bottom: 5px;" >Patient Name</td>
								<td style="padding-top:5px; padding-bottom: 5px;" >Type</td>
								<td style="padding-top:5px; padding-bottom: 5px;">Applied Date</td>
								<td style="padding-top:5px; padding-bottom: 5px;">Claim Amt (&#8377;)</td>
								<td style="padding-top:5px; padding-bottom: 5px;">Admitted Amt (&#8377;)</td>
								<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
								<td style="padding-top:5px; padding-bottom: 5px;">Action</td>
							</tr>
						</thead>
						<tbody>
							<%long slno=0;
							for(Object[] obj : empchsslist){ 
								slno++; %>
								<tr>
									<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%>.</td>
									<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
									<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[12] %></td>
									<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[6] %></td>
									<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
									<td style="padding-top:5px; padding-bottom: 5px;text-align: right"> <%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[27].toString()))))%></td>
									<td style="padding-top:5px; padding-bottom: 5px;text-align: right">
										<%if(Integer.parseInt(obj[9].toString())>14){ %>
											<%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[28].toString()))))%>
										<%}else{ %>
											-
										<%} %>
									</td>
									<td style="padding-top:5px; padding-bottom: 5px;" class="editable-click">
									 <button class="btn btn-sm btn-link w-100 " formaction="Chss-Status-details.htm" name="chssapplyid" value="<%=obj[0]%>" formtarget="_blank" 
									 data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color:<%=obj[25]%>; font-weight: 600;">  &nbsp;<%=obj[18] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
									
									</td>
									<td style="padding-top:5px; padding-bottom: 5px;">
										<%if(obj[6].toString().equalsIgnoreCase("OPD")){ %>
											<%if(Integer.parseInt(obj[9].toString())==1 || Integer.parseInt(obj[9].toString())==3 || Integer.parseInt(obj[9].toString())==7){ %>
												
													<button type="submit" class="btn btn-sm edit-btn" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSConsultMainData.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Edit">
														Edit
													</button>
												
												<%if(Integer.parseInt(obj[9].toString())==1 && obj[16]!=null && obj[16].toString().trim().equals("-")){ %>
												<button type="submit" class="btn btn-sm " name="chssapplyid" value="<%=obj[0] %>" formaction="ClaimDeleteEmp.htm" onclick="return confirm('Are You Sure to Delete?');"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Delete Claim">
													<i class="fa-solid fa-trash-can " style="color: red"></i>
												</button>
												<%} %>
											<% } %>
												
											<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>
											<%if(Integer.parseInt(obj[9].toString())>1 && Integer.parseInt(obj[9].toString())!=3 ){ %>	
											<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEmpDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
											</button>
											<%} %>
											<%if(Integer.parseInt(obj[9].toString())==2 && obj[24].toString().equalsIgnoreCase("0")){ %>
											<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSEmpClaimRevoke.htm" onclick="return confirm('Are you sure to revoke this claim?');" formmethod="post" data-toggle="tooltip" data-placement="top" title="Revoke Submission">
												<i class="fa-solid fa-backward" style="color: #333C83"></i>
											</button>
											<%} %>
										<%}else if(obj[6].toString().equalsIgnoreCase("IPD")){ %>
											<%if(Integer.parseInt(obj[9].toString())==1 || Integer.parseInt(obj[9].toString())==3 || Integer.parseInt(obj[9].toString())==7){ %>
												
													<button type="submit" class="btn btn-sm edit-btn" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDApply.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Edit">
														Edit
													</button>
												
												<%if(Integer.parseInt(obj[9].toString())==1 && obj[16]!=null && obj[16].toString().trim().equals("-")){ %>
												<button type="submit" class="btn btn-sm " name="chssapplyid" value="<%=obj[0] %>" formaction="ClaimDeleteEmp.htm" onclick="return confirm('Are You Sure to Delete?');"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Delete Claim">
													<i class="fa-solid fa-trash-can " style="color: red"></i>
												</button>
												<%} %>
											<% } %>
												
											<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>
											<%if(Integer.parseInt(obj[9].toString())>1 && Integer.parseInt(obj[9].toString())!=3 ){ %>	
											<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
											</button>
											<%} %>
											<%if(Integer.parseInt(obj[9].toString())==2 && obj[24].toString().equalsIgnoreCase("0")){ %>
												<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSEmpClaimRevoke.htm" onclick="return confirm('Are you sure to revoke this claim?');" formmethod="post" data-toggle="tooltip" data-placement="top" title="Revoke Submission">
													<i class="fa-solid fa-backward" style="color: #333C83"></i>
												</button>
											<%} %>
										
										
										
										<%} %>
										<input type="hidden" name="ActivateDisp" value="Y">
										
										<input type="hidden" name="view_mode" value="U">
									</td>
								</tr>
							<%} %>
						</tbody>
					</table>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
		</div>
	</div>
 </div> 
</div>
<form action="CHSSApplyDetails.htm" method="post" id="myform" >
	<input type="hidden" name="isself" id="isself" >
	<input type="hidden" name="patientid" id="patientid" >
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


<form action="CHSSDashboard.htm" method="post" id="changeform" >
	<input type="hidden" name="isselfvalue" id="isselfvalue" >
	<input type="hidden" name="patientidvalue" id="patientidvalue" >
	<input type="hidden" name="fromdate" id="fromdate" >
	<input type="hidden" name="todate" id="todate" >
	<input type="hidden" name="patientname" id="patientname" >
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<script>


function applyform(){

	
	$('#isself').val('<%=IsSelf%>')		
	$('#patientid').val(<%=patientidvalue%>)
	$('#myform').submit(); 
	
}

function changeform(val){

	$('#fromdate').val($('#datepicker1').val());
	$('#todate').val($('#datepicker3').val());
	$('#patientidvalue').val(<%=patientidvalue%>);
	$('#patientname').val(val)

	if(<%=employee[1]%>==<%=patientidvalue%>){
		$('#isselfvalue').val('Y')		
	}else{
		$('#isselfvalue').val('N')		
	}

	
	$('#changeform').submit();
}

function submitform(value,patientid,patientname){
	/* $('#isself').val(value)
	$('#patientid').val(patientid)
	$('#myform').submit(); */
	
	$('#fromdate').val($('#datepicker1').val());
	$('#todate').val($('#datepicker3').val());
	$('#patientidvalue').val(patientid);
	$('#isselfvalue').val(value);
	$('#patientname').val(patientname)
	
	$('#changeform').submit();
	
	
}

$(document).ready(function(){
	
	
	
	
    $("#datepicker1").daterangepicker({
        minDate: 0,
        maxDate: 0,
        numberOfMonths: 1,
        autoclose: true,
        "singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
        onSelect: function(selected) {
        $("#datepicker3").datepicker("option","minDate", selected)
        },
        locale : {
			format : 'DD-MM-YYYY'
		}
    });

    $("#datepicker3").daterangepicker({
        minDate: 0,
        maxDate: 0, 
        numberOfMonths: 1,
        autoclose: true,
        "singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate":$("#datepicker1").val(),
	    onSelect: function(selected) {
	    	console.log(selected);
	    $("#datepicker1").datepicker("option","maxDate", selected)
        },
        locale : {
			format : 'DD-MM-YYYY'
		}
    }); 
    
	var windowwidth= $(window).width(); 
	$('.profile-card-container').css('max-width',(windowwidth - 230) +'px'  );
    
    

});

$("#myTable1").DataTable({
    "lengthMenu": [ 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});




</script>
</body>
</html>
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
    max-height: 20rem !important;
    padding: 0px 20px;
    margin: 10px 20px 25px 20px;
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
    padding: 0px 0px 0px 2rem;
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
 




</style>
</head>
<body>

<% String logintype = (String) session.getAttribute("LoginType"); 
	List<Object[]> dashboard = (List<Object[]>)request.getAttribute("dashboard");
	List<Object[]> empfamilylist = (List<Object[]>)request.getAttribute("empfamilylist");
	Employee employee = (Employee )request.getAttribute("employee") ;
	List<Object[]> empchsslist = (List<Object[]>)request.getAttribute("empchsslist");
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
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
	String profilepicpath = (String) request.getAttribute("profilepicpath"); 
	
	DecimalFormat df = new DecimalFormat("0.00");
%>

 <div class="col page">
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3  ">
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

 	<%-- <div class="card-body" >
		<form action="#" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="card" >
				<div class="card-body " >
					<div class="row" > 
						<div class="col-md-3 ">
							<button type="submit" class=" db-button w-100" formaction="CHSSApply.htm" >CHSS Apply</button>
						</div>
						<div class="col-md-3 ">
							<button type="submit" class=" db-button w-100" formaction="CHSSAppliedList.htm" >CHSS List</button>
						</div>
						<%if( logintype.equalsIgnoreCase("K") || logintype.equalsIgnoreCase("V") ){ %>
							<div class="col-md-3 ">
								<button type="submit" class=" db-button w-100" formaction="CHSSApprovalsList.htm" >CHSS Approvals</button>
							</div>

						<%} %>
						
						
						<%if(dashboard!=null){  for(Object[] O:dashboard){%>							
							 <div class="col-md-3 ">
								<button type="submit" class=" db-button w-100" formaction="<%=O[1]%>" ><%=O[0]%></button>
							</div>
						<%}}%>

					</div>
				</div>
			</div>		
		</form>
		
	</div>  --%>

 </div> 
<!--  
 <div class="card-header page-button">
		<div class="row">
			<div class="col-md-3 heading-breadcrumb">
				<h5 style="font-weight: 700 !important">CHSS DASHBOARD</h5>
			</div>
			
			<div class="col-md-9 " >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">CHSS</li>
				  </ol>
				</nav>
			</div>
						
		</div>
	</div> 
  -->
 
 
 <div class="card profile-card-container" >
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
	

	<div>
 		<form action="#" method="post" style="margin-top: 5px;">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
 				<div class="row"> 
						
				<%if(dashboard!=null)
				{ 
					for(Object[] obj:dashboard)
					{
						if(!(obj[2].toString().equals("4") || obj[2].toString().equals("5"))) 
						{ %>	
											
						<div class="col-md-3" >
							<button type="submit" class=" db-button w-100" formaction="<%=obj[1]%>" ><%=obj[0]%></button>
						</div>
						
					<%	} 
					}
				}%>
						
			</div>
		</form>
	</div> 	

	<div class="row" >
		<div class="col-md-2">
			<div class="main" onclick="submitform('Y','<%=employee.getEmpId()%>','<%=employee.getEmpName() %>')" > 
				<div class="a-box">
					<div class="img-container">
						<div class="img-inner">
							<div class="inner-skew">
								<% if(employee.getGender().equalsIgnoreCase("F")) { %>
									<img src="view/images/femaleuser.png" alt="Photo Not Found">
								<% }else{ %>
									<img src="view/images/maleuser.png" alt="Photo Not Found">
								<% } %>
							</div>
						</div>
					</div>
					<div class="text-container" <%if(patientidvalue.equalsIgnoreCase(Long.toString(employee.getEmpId()))) {%>style="box-shadow: 0px 0px 10px 0px rgb(230 100 10 / 90%)" <%} %>>
						<h3><%=employee.getEmpName() %> <span style="font-weight: 700;font-size: 13px;" > (Self)</span></h3>
						<p class="employee-details">&#9679; DOB : <%=rdf.format(sdf.parse(employee.getDOB().toString()))%></p>
						<p class="employee-details">&#9679; Blood Group : <%if(employee.getBloodGroup()!=null){ %> <%=employee.getBloodGroup()%> <%}else{ %> - <%} %></p> 
					</div>
				</div>	
			</div>
						
		</div>
		
		<%for(Object[] obj : empfamilylist){ %>
		<div class="col-md-2">
			<div class="main" onclick="submitform('N','<%=obj[0]%>','<%=obj[1] %>')" > 
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
						<p class="employee-details">	&#9679; DOB : <%=rdf.format(sdf.parse(obj[3].toString()))%></p>
						<p class="employee-details"> 	&#9679; Blood Group : <%if(obj[6]!=null){ %> <%=obj[6]%> <%}else{ %> - <%} %></p> 
					</div>
				</div>	
			</div>
		</div>
		<%} %>
	</div>
</div>

	<div class="nav navbar bg-light dashboard-margin custom-navbar">

		<div class="col-md-3">
			<button type="button" class="btn btn-sm misc2-btn"	<% if(patientname.equalsIgnoreCase("All")){ %> style="display: none" <%} %> name="Action" value="APPLY" onclick="applyform()" data-toggle="tooltip" data-placement="bottom" title="Apply"><i class="fa-solid fa-paper-plane"></i> &nbsp;&nbsp;APPLY&nbsp;</button>
			
		</div>
		<div class="col-md-5 d-flex justify-content-center">
			<h4 style="color: #005C97;font-weight: 700;text-transform: capitalize;"><%=patientname %> Applied List 	</h4>
		</div>
		<label style=" font-weight: 800">From Date : &nbsp; </label>
		<input  class="form-control form-control date"  data-date-format="dd-mm-yyyy" id="datepicker1" name="Fromdate"  required="required"  style="width: 120px;"
		<%if(Fromdate!=null){%> value="<%=(Fromdate) %>" <%} %> onchange="changeform('<%=patientname %>')" >
					  
	
		<label style="font-weight: 800;padding-left: 5px">To Date :  &nbsp; </label>
		<input  class="form-control form-control" data-date-format="dd-mm-yyyy" id="datepicker3" name="Todate"  style="width: 120px;"
					 	 <%if(Todate!=null){%> value="<%=(Todate) %>" <%} %>  onchange="changeform('<%=patientname %>')" >  
					
		<%-- <form class="form-inline my-2 my-lg-0">
	    	<select class="form-control select2" id="empname" required="required" name="empname"  onchange="changeform()" >
				<option value="0" <%if(patientidvalue.equalsIgnoreCase("0")){ %>selected<% } %> >All</option>
				<option value="<%=employee.getEmpId()%>" <%if(patientidvalue.equalsIgnoreCase(Long.toString(employee.getEmpId()))){ %>selected<% } %> ><%=employee.getEmpName() %> (Self)</option>
				<% for (Object[] obj : empfamilylist) {%>
					<option value="<%=obj[0]%>" <%if(obj[0].toString().equalsIgnoreCase(patientidvalue)){ %>selected<% } %> ><%=obj[1]%> (<%=obj[7] %>)</option>
				<%} %>
			</select>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    	</form> --%>

	</div>

<div class="card table-card dashboard-margin" >
	<div class="card-body "  style="padding: 1rem !important;">				
		<form action="#" method="post" id="ClaimForm">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="table-responsive">
					<table class="table table-bordered table-hover table-striped table-condensed" id="myTable" > 
						<thead>
							<tr>
								<td style="padding-top:5px; padding-bottom: 5px;">SN</td>
								<td style="padding-top:5px; padding-bottom: 5px;" >Claim No</td>
								<td style="padding-top:5px; padding-bottom: 5px;" >Patient Name</td>
								<td style="padding-top:5px; padding-bottom: 5px;">Applied Date</td>
								<td style="padding-top:5px; padding-bottom: 5px;">Claim Amnt</td>
								<td style="padding-top:5px; padding-bottom: 5px;">Admitted Amnt</td>
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
									<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
									<td style="padding-top:5px; padding-bottom: 5px;text-align: right">&#8377; <%=df.format(obj[24])%></td>
									<td style="padding-top:5px; padding-bottom: 5px;text-align: right">
										<%if("14".equals(obj[9].toString())){ %>
											&#8377; <%=df.format(obj[25])%>
										<%}else{ %>
											-
										<%} %>
									</td>
									<td style="padding-top:5px; padding-bottom: 5px;" class="editable-click"> <a class="font" href="Chss-Status-details.htm?chssapplyid=<%=obj[0]%>" target="_blank"  title="Click for Details."
									 
										<%if("1".equals(obj[9].toString()) || "2".equals(obj[9].toString()) ){%>  
										    style=" color:#2155CD; font-weight: 600;"				
											<%}else if("3".equals(obj[9].toString())||"5".equals(obj[9].toString()) ||"7".equals(obj[9].toString())||"9".equals(obj[9].toString()) || "11".equals(obj[9].toString())||"13".equals(obj[9].toString())){%>
											 style="  color:#B20600; font-weight: 600;"     
											<%}else  if("4".equals(obj[9].toString())||"6".equals(obj[9].toString())||"8".equals(obj[9].toString())|| "10".equals(obj[9].toString()) || "12".equals(obj[9].toString())){%>
												style=" color:#F66B0E; font-weight: 600;"
											<%}else if("14".equals(obj[9].toString())){ %>
											style=" color:#125C13; font-weight: 600;"
											<%}else{ %>
											 style=" color:#4700D8; font-weight: 600;"
											<%} %>
									>
									
									<%=obj[18] %> </a></td>
									<td style="padding-top:5px; padding-bottom: 5px;">
										<%if(Integer.parseInt(obj[9].toString())==1 || Integer.parseInt(obj[9].toString())==3 || Integer.parseInt(obj[9].toString())==7){ %>
											<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSAppliedDetails.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Edit">
												<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
											</button>	
										<%} %>
											
										<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSForm.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
											<i class="fa-solid fa-eye"></i>
										</button>	
											
										<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEmpDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
											<i style="color: #019267" class="fa-solid fa-download"></i>
										</button>
										<input type="hidden" name="isapproval" value="N">							
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

	if(<%=employee.getEmpId()%>==<%=patientidvalue%>){
		$('#isself').val('Y')		
	}else{
		$('#isself').val('N')		
	}
	$('#patientid').val(<%=patientidvalue%>)
	$('#myform').submit(); 
	
}

function changeform(val){

	$('#fromdate').val($('#datepicker1').val());
	$('#todate').val($('#datepicker3').val());
	$('#patientidvalue').val(<%=patientidvalue%>);
	$('#patientname').val(val)

	if(<%=employee.getEmpId()%>==<%=patientidvalue%>){
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
        	console.log(selected);
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

});
</script>
</body>
</html>
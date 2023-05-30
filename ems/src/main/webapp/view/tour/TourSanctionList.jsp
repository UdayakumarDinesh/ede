<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
body{
  overflow-x: hidden;
  overflow-y: hidden;
}
</style>
<title>Tour Apply List</title>
</head>
<body>
<%
String logintype = session.getAttribute("LoginType").toString();
List<Object[]>  sanctionlist = (List<Object[]>)request.getAttribute("SanctionList");
Object[] empdata = (Object[])request.getAttribute("Empdata");
List<Object[]> emplist = (List<Object[]>)request.getAttribute("emplist");
List<Object[]> pandalist = (List<Object[]>)request.getAttribute("pandalist");
String fromdate = (String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");
String empno = (String)request.getAttribute("empno");
String Loginempno = session.getAttribute("EmpNo").toString();

%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Tour Sanctioned List <small><b>&nbsp;&nbsp; &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TourProgram.htm">Tour</a></li>
						<li class="breadcrumb-item active " aria-current="page">Tour List</li>
					</ol>
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

<div class="container-fluid">	
 <form action="TourSanctionedlist.htm" method="post"  class="navbar-form navbar-right"  style="margin-top:8px;">
  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		  <div  class="row" align="center">
                       <div class="form-group col-sm-1"  ></div>
                         <div class="form-group col-sm-4"  >
	                         <select class="form-control input-sm selectpicker" name="empno" required="required" data-live-search="true">
									<%if(emplist!=null){
										if(logintype.equalsIgnoreCase("P")){
										for(Object[] emp:emplist){%>
											<option value="<%=emp[1]%>" <%if(empno.equalsIgnoreCase(emp[1].toString())){%>selected="selected" <%}%> ><%=emp[2]%>(<%=emp[3]%>)</option>
									<%}}else{
										for(Object[] emp:emplist){
										if(emp[1].toString().equalsIgnoreCase(empno)){%>
											<option value="<%=emp[1]%>" <%if(empno.equalsIgnoreCase(emp[1].toString())){%>selected="selected" <%}%> ><%=emp[2]%>(<%=emp[3]%>)</option>
									<%}}}}%>
	                         </select>
                        </div>   
                      
                        <div class="form-group col-sm-1" style="text-align: right;margin-top: 4px;">
                        	<label style=" font-weight: 800">From  : &nbsp; </label>
						</div>
						<div class="form-group col-sm-1" style="padding-right: 2px;padding-left: 2px;">
							<input  class="form-control form-control"   data-date-format="dd-mm-yyyy" id="fromdate"  name="fromdate"  required="required"  style="width: 120px;">
						</div>
						
						<div class="form-group col-sm-1" style="text-align: right;margin-top: 4px;"> 
							<label style="font-weight: 800;padding-left: 5px">To  :  &nbsp; </label>
						</div>
				        <div class="form-group col-sm-1" style="padding-right: 2px;padding-left: 2px;">
							<input  class="form-control form-control" data-date-format="dd-mm-yyyy" <%if(todate!=null && todate!=""){%>value="<%=todate%>"<%}%>  id="todate"  name="todate"  style="width: 120px;">						
						</div>
						
						<div class="col-sm-1" style="margin-left: 35px;  margin-top: 3px;text-align: left;">
					   		<button type="submit" class="btn  btn-success btn-sm" name="ChooseDate" value="ChooseDate">submit</button>
                       </div>
                 </div>
</form> 



<div class="card">
	<div class="card-body">
<form action="TourCancel.htm" method="post" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
								  <tr>
								  	  <th>SN</th>
								  	  <th>Tour No</th>
									  <th>Date</th>
								      <th>Applied On</th>
								      <th>Name</th>
								      <th>Purpose</th>
								      <th>Status</th>
								      <th>Action</th>
								  </tr>
					  </thead>
	                  <tbody>
	  
							 <%if(sanctionlist!=null&&sanctionlist.size()!=0){
						                            	 int sn=0;  
                               for(Object[] hlo :sanctionlist){
                            	   String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(hlo[1].toString());
                                   String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(hlo[2].toString());
                                   String  applydate=DateTimeFormatUtil.fromDatabaseToActual(hlo[3].toString());
                                   long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(hlo[1].toString(), hlo[2].toString());
                                   SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                     			   Date d1 = sdf1.parse(hlo[1].toString());
                     			  sdf1.applyPattern("dd-MM-yyyy");
                     			  String parsedate = sdf1.format(d1);
                            	   %>
	                             <tr>
	                             	  <td align="center"> <%=++sn %></td>
	                             	   <td>
	                             	   <%if(hlo[10]!=null){
	                             	   	if(empno.equalsIgnoreCase(session.getAttribute("EmpNo").toString())){
	                             	   %>
		                             	  <button type="submit" class="btn btn-sm btn-link w-100 " formaction="TourApplyReport.htm" formmethod="get" name="tourapplyid" value="Apply/<%=hlo[7]%>" formtarget="_blank" 
														  style=" color:<%=hlo[10]%>; font-weight: 600;" ><%=hlo[10]%></button>
										  <%}else{%> <span style="color: blue;"><%=hlo[10]%></span> <%}%>
		                             	  <%}else{%>
		                             	    <button class="btn btn-sm btn-link w-100 ">--</button>
		                             	  <%}%>
	                             	   </td>
									  <td align="center"><%=stayfromdate%> To <%=staytodate%></td>
                               		  <td align="center"><%=applydate %><br/> for <%=noofdays%> Day(s)</td>
									  <td align="center"><%=hlo[0]+"" %></td>
									  <td><%=hlo[4]+"" %></td>
									  <td align="center" style=" color:<%=hlo[9]%>; font-weight: 600;">&nbsp;<%=hlo[8]%> </td>
									  <td> 
									  <%if(new Date().before(new SimpleDateFormat("dd-MM-yyyy").parse(parsedate))){%>
									  <%if(hlo[11].toString().equalsIgnoreCase(Loginempno)){%>
									  	<button class="btn btn-sm" type="submit" formaction="TourModify.htm" formmethod="GET" name="tourapplyid"  value="Modify/<%=hlo[7]%>"  data-toggle="tooltip" data-placement="top" title="Modify">
                                			<i class="fa fa-exclamation-circle" aria-hidden="true"></i>
                                		</button>
                                		<%}}%> 
									   <%if(hlo[6].toString().equalsIgnoreCase("ABC")){%>
									   		<%if(hlo[11].toString().equalsIgnoreCase(Loginempno)){%>
									  			<button type="button" class="btn btn-sm edit-btn"  onclick="CancelTour('<%=hlo[7]%>')"  data-toggle="tooltip" data-placement="top" title="Cancel"><i class="fa fa-times" aria-hidden="true"></i> </button>
									  		<%}%>
									  		<%if(logintype.equalsIgnoreCase("P")){%>
									  			<button type="button" class="btn btn-sm" style="background-color: #0ae3d9;" onclick="IssueMO('<%=hlo[7]%>')" data-toggle="tooltip" data-placement="top" title="Issue MO"> Issue MO </button>
									  		<%}%>
									  	<%}%>	
									   <%-- <%if(!hlo[6].toString().equalsIgnoreCase("INI") && !hlo[6].toString().equalsIgnoreCase("REV") && !hlo[6].toString().equalsIgnoreCase("ABC")){%>
									  		<button type="submit" class="btn btn-sm delete-btn" name="Action" value="Revoke/<%=hlo[7]%>"   data-toggle="tooltip" data-placement="top" title="Revoke"><i class="fa fa-undo" aria-hidden="true" ></i> </button>								  	
									  	<%}%> --%>
									  	 
									  </td>
								</tr> 
                  <%}}%>
	            </tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
				</form>	
	</div>
	<!---------------------- Model Open --------------------------------->

	<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <form action="TourCancel.htm">
		      <div class="modal-header">
		        <h5 class="modal-title">Reason For Cancel Tour</h5>
                 <button  type="button"  class="close" data-dismiss="modal">&times;</button>
		      </div>
		      <div class="modal-body" align="center">
		        <textarea rows="3" cols="40"  name="reason" required="required" maxlength="490" placeholder="Enter The Reason........!"></textarea>
		      </div>
		      <div class="modal-footer">
		      	<input type="hidden" name="Action" id="action">
		        <button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are you sure to Submit?')">Submit</button>
		      </div>
	      </form>
	    </div>
	  </div>
	</div>
<!---------------------- Model Close --------------------------------->

<!---------------------- Model Open --------------------------------->
	<div class="modal fade" id="IssueMOOrder" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      
		      <div class="modal-header">
		        <h5 class="modal-title">Issue Movement Order For Tour</h5>
                 <button  type="button"  class="close" data-dismiss="modal">&times;</button>
		      </div>
		      <div class="modal-body" align="center">
				<form action="IssueMOFromPA.htm" method="post" autocomplete="off" id="TourRequestForm">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="form-group">
							<div class="row">
							    <div class="col-md-1"></div>
								<div class="col-md-4">
									<label>Issued By :<span class="mandatory">*</span></label>
								</div>
								<div class="col-md-5">
									<select class="form-control input-sm selectpicker" name="issuedby" required="required" data-live-search="true">
										<%for(Object[] emp: pandalist){ %>
											<option value="<%=emp[1]%>" ><%=emp[2]%>(<%=emp[3]%>)</option>
	                        			<%}%>
	                        		 </select>
							 	</div>
						    </div>
							<div class="row" style="margin-top: 10px;">
							<div class="col-md-1"></div>
								<div class="col-md-4">
									<label>Issued On :<span class="mandatory">*</span></label>
								</div>
								<div class="col-md-5">
									<div class=" input-group">
										<input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="issueddate" name="issueddate"  required="required"  > 
										<label class="input-group-addon btn" for="testdate"></label>                    
									</div>
							 	</div>
						    </div>
						    <div class="row" style="margin-top: 15px;">
						    	<div class="col-md-5"></div>
						    	<div class="col-md-3">
						    		<input type="hidden" name="tourapply" id="TOURAPPLY">
		      						  <button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are you sure to Submit?')">Submit</button>
						    	</div>
						    </div>
						</div>
			</form>
	    </div>
	  </div>
	</div>
<!---------------------- Model Close --------------------------------->
</div>	
</div>
<script type="text/javascript">

function CancelTour(myfrm) {
	
	 $("#action").val("CANCEL/"+myfrm);
	 $('#staticBackdrop').modal('toggle');
}

function IssueMO(myfrm)
{
	 $("#TOURAPPLY").val(myfrm);
	 $('#IssueMOOrder').modal('toggle');
}

</script>
<script type="text/javascript">
$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	
	<%if(fromdate!=null && fromdate!=""){%>
	"startDate" :new Date("<%=fromdate%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#todate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#fromdate').val(),
	<%if(todate!=null && todate!=""){%>
	"startDate" :new Date("<%=todate%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$( "#fromdate" ).change(function() {
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : $('#fromdate').val(), 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});


</script>
</body>

</html>
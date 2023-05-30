<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Collector"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<meta charset="ISO-8859-1">
<title>Tour Approval</title>
<style type="text/css">
body{
 overflow-x: hidden;

}

  .toggle.ios, .toggle-on.ios, .toggle-off.ios { border-radius: 20rem; }
  .toggle.ios .toggle-handle { border-radius: 20rem; }

.app:hover{background-color:#008000; color:white;}
.app{ border: 1px solid #2E8B57 ; padding: 2px 2px 2px 2px; width:50%;  border-radius: 5px;}

.disapp:hover{background-color:#B22222; color:white;}
.disapp{ border: 1px solid 	#FF0000 ; padding: 2px 2px 2px 2px; width:50%;  border-radius: 5px;}


.sendBack:hover{background-color:#FFA500 ; color:white;}
.sendBack{ border: 1px solid 	#DAA520 ; padding: 2px 2px 2px 2px; width:60%;  border-radius: 10px;}


.table thead tr{
	background-color: white;
	color: black;
}
.card-header {
    padding: 0.25rem 1.25rem 0.25rem 1.25rem;
    }
  #blink ,#blink2,#blink3 {
	  animation: blinker 2s linear infinite;
	  background-color:red !important;
	  width: 16px;
	  top: 3.2px;
  } 
  @keyframes blinker {
  50% {
    opacity: 0.35;
  }
}


#button {
   float: left;
   width: 80%;
   padding: 5px;
   background: #dcdfe3;
   color: black;
   font-size: 17px;
   border:none;
   border-left: none;
   cursor: pointer;
}

.dataTables_length 
{
	margin-right: 410px;
}
.dataTables_info
{
	margin-right: 410px;
}
</style>
</head>
<body>
<%
Object[] empdata = (Object[])request.getAttribute("Empdata");

List<Object[]> approvallist = (List<Object[]>)request.getAttribute("approvallist");
List<Object[]> canceledlist = (List<Object[]>)request.getAttribute("canceledlist");
/* List<Object[]> approval = approvallist.stream().filter(e->!e[7].toString().equalsIgnoreCase("ABD")).collect(Collectors.toList());
List<Object[]> fAndAdeptlist = approvallist.stream().filter(e->e[7].toString().equalsIgnoreCase("ABD")).collect(Collectors.toList());
 */

List<Object[]> approvedlist = (List<Object[]>)request.getAttribute("approvedlist");
String fromdate = (String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
int totalcount = approvallist.size() + canceledlist.size();
String tab   = (String)request.getAttribute("tab");

%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Tour Approval <small><b>&nbsp;&nbsp; &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
			<div class="col-md-6">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="TourApprovallist.htm">Tour </a></li>
					<li class="breadcrumb-item active " aria-current="page">Tour Approval</li>
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

	<div class="row w-100" style="margin-bottom: 10px;">
		<div class="col-12">
         <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist" style="background-color: #E1E5E8;padding:0px;">
		  <li class="nav-item" style="width: 50%;"  >
		    <div class="nav-link active" style="text-align: center; cursor: pointer;" id="pills-mov-property-tab" data-toggle="pill" data-target="#pills-mov-property" role="tab" aria-controls="pills-mov-property" aria-selected="true">
			   <span>Pending</span> 
				<span class="badge badge-danger badge-counter count-badge" style="margin-left: 0px;">
				   	    <%if(totalcount>99){ %>
				   			99+
				   		<%}else{ %>
				   			<%=totalcount %>
						<%} %>	 		   			
				  </span> 
		    </div>
		  </li>
		  <li class="nav-item"  style="width: 50%;">
		    <div class="nav-link" style="text-align: center; cursor: pointer;" id="pills-imm-property-tab" data-toggle="pill" data-target="#pills-imm-property" role="tab" aria-controls="pills-imm-property" aria-selected="false">
		    	 <span>Approved</span> 
		    </div>
		  </li>
		</ul>
	   </div>
	</div>	
<div class="card">

   <div class="card-body" align="center" >
   
   <div class="container-fluid">
<div class="tab-content" id="pills-tabContent">
   <div class="tab-pane fade show active" id="pills-mov-property" role="tabpanel" aria-labelledby="pills-mov-property-tab">
<div class="card-body main-card " >	
<form action="##" method="post">
             <div class="table-responsive">
         
		        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;" id="myTable2">
		            <thead>
		                <tr style="background-color: #0e6fb6; color: white;">
		                	<th >SN</th>
		                    <th >Name &amp; Designation </th>
		                    <th >From : To</th>
		                    <th >Applied On</th>
		                    <th >Last Action</th>
		                    <th >Action</th>
		                </tr>
		            </thead>
		            <tbody>
		            <%int count=0; 
		            if(approvallist!=null){
		            for(Object[] ls:approvallist){
		            		String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(ls[3].toString());
		                    String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(ls[4].toString());
		                    long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(ls[3].toString(), ls[4].toString());   
		            %>
		             <tr>	
		             		<td style="text-align:center;"> <%=++count%></td>
		                    <td style="text-align:center;"> <%=ls[1]%> <br> <%=ls[2]%></td>
		                    <td style="text-align:center;"> <%=stayfromdate%> <br> <%=staytodate%></td>
		                    <td style="text-align:center;"> <%=ls[5]%> <br> for <%=noofdays%> Day(s)</td> 
		                    <td style="text-align:left;">   <%=ls[8] %> </td>
		                    <td style="text-align:center;"> 
		                    <button type="submit" class="btn btn-sm" name="Action" value="Preview/<%=ls[0]%>"  formaction="TourApplyList.htm" formmethod="POST"  data-toggle="tooltip" data-placement="top" title="View Form" >
									 <i class="fa-solid fa-eye"></i>
							</button>
		           		   </td>
		           </tr>
		           <%  }}%> <!-- if closed -->
		           
		             <%
		            if(canceledlist!=null){
		            for(Object[] ls:canceledlist){
		            		String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(ls[3].toString());
		                    String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(ls[4].toString());
		                    long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(ls[3].toString(), ls[4].toString());   
		            %>
		             <tr>
		             		<td style="text-align:center;"> <%=++count%></td>
		                    <td style="text-align:center;"><%= ls[1]%><br> <%=ls[2]%></td>
		                    <td style="text-align:center;"><%=stayfromdate%><br><%=staytodate%></td>
		                    <td style="text-align:center;"><%=ls[5]%><br>for <%=noofdays%> Day(s)</td> 
		                    <td style="text-align:left;"> <%=ls[8] %></td>
		                    <td style="text-align:center;">
		                     <button type="submit" class="btn btn-sm" name="Action" value="Preview/<%=ls[0]%>"  formaction="TourCancel.htm"  formmethod="POST"  data-toggle="tooltip" data-placement="top" title="View Form" >
									 <i class="fa-solid fa-eye"></i>
							</button>
		                    
		           		   </td>
		           </tr>
		           <% }}%> <!-- if closed -->
		           </tbody>
		     </table>
		      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		
        </div>
     </form>
     </div>	
   </div>
   
   <!-- Approved List -->	
		<div class="tab-pane fade" id="pills-imm-property" role="tabpanel" aria-labelledby="pills-imm-property-tab">	
		<div class="card-body main-card " >	
		
		<form method="post" action="TourApprovallist.htm" >
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="hidden" name="tab" value="closed"/>
					<div class="row w-100" style="margin-top: 10px;margin-bottom: 10px;">
						<div class="col-md-12" style="float: right;">
							<table style="float: right;">
								<tr>
									<td> From Date :&nbsp; </td>
							        <td> 
										<input type="text" class="form-control input-sm mydate" onchange="this.form.submit()"   readonly="readonly"  <%if(fromdate!=null){%>
								        value="<%=DateTimeFormatUtil.SqlToRegularDate(fromdate)%>" <%}%>   id="fromdate" name="fromdate"  required="required"   > 
									</td>
									<td></td>
									<td >To Date :&nbsp;</td>
									<td>					
										<input type="text"  class="form-control input-sm mydate" onchange="this.form.submit()"   readonly="readonly" <%if(todate!=null){%>
								         value="<%=DateTimeFormatUtil.SqlToRegularDate(todate)%>" <%}%>    id="todate" name="todate"  required="required"  > 							
									</td>
									
								</tr>
							</table>
					 	</div>
					 </div>
		</form>
		<div class="row" >
		 <div class="col-md-12">
		
		<form action="" method="POST" id="circularForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <div class="table-responsive">
              <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable1">
				<thead>
					<tr style="background-color: #0e6fb6; color: white;">
					   <th style="width:5%">   SN</th>
					   <th style="width:15%">  TourNo</th>
					   <th style="width:20%">  Date </th>
					   <th style="width:20%;"> Applied On</th>
					   <th style="width:20%">  Employee Name</th>
                       <th style="width:20%">  Approved Date</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SNA1=0;
                          for(Object[] form:approvedlist ){
                        	  String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(form[3].toString());
                              String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(form[4].toString());
                              String  applydate=DateTimeFormatUtil.fromDatabaseToActual(form[5].toString());
                              long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(form[3].toString(), form[4].toString());
                       %>
                        <tr>                           
                            <td style="text-align: center;"> <%=++SNA1%>    </td>
                            <td style="text-align: center;"> <%if(form[1]!=null){%> <%=form[1]%> <%}else{%> -- <%}%> </td>
                            <td style="text-align: center;"> <%=stayfromdate%> To <%=staytodate%></td>
                            <td style="text-align: center;"> <%=applydate %><br/> for <%=noofdays%> Day(s)</td> 
                            <td style="text-align: center;"> <%=form[2]%> </td>
                            <td style="text-align: center;"> <%= DateTimeFormatUtil.fromDatabaseToActual(form[8].toString())%></td> 
                        </tr>
                       <%} %> 
                          
                   </tbody>
   
                 </table>
                </div>       
               </form>
               </div>
               </div>
               </div>				
			  </div>
   </div>
</div>	
<script type="text/javascript">
	
	$("#myTable1").DataTable({
	    "lengthMenu": [ 50, 75, 100],
	    "pagingType": "simple"

	});
	$("#myTable2").DataTable({
	    "lengthMenu": [ 50, 75, 100],
	    "pagingType": "simple"

	});
	
	$('#fromdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		/* "minDate" :datearray,   */
		<%--  "startDate" : new Date('<%=fromdate%>'),  --%>
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
			<%-- "startDate" : new Date('<%=todate%>'), --%> 
			"minDate" :$("#fromdate").val(),  
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
			<%if(tab!=null && tab.equals("closed")){%>
				$('#pills-imm-property-tab').click();
			<%}%>
	</script>
</div>
</div> 	
</body>
</html>
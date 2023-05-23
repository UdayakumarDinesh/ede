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
</style>
</head>
<body>
<%
Object[] empdata = (Object[])request.getAttribute("Empdata");

Object[] fapadetails = (Object[])request.getAttribute("FAPAdetails");
List<Object[]> approvallist = (List<Object[]>)request.getAttribute("approvallist");
List<Object[]> canceledlist = (List<Object[]>)request.getAttribute("canceledlist");
List<Object[]> approval = approvallist.stream().filter(e->!e[7].toString().equalsIgnoreCase("ABD")).collect(Collectors.toList());
List<Object[]> fAndAdeptlist = approvallist.stream().filter(e->e[7].toString().equalsIgnoreCase("ABD")).collect(Collectors.toList());


String fromdate = (String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");
String empno= (String)request.getAttribute("empno");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Tour Approval <small><b>&nbsp;&nbsp; &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
			<div class="col-md-6">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="TourProgram.htm">Tour</a></li>
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
<div class="container-fluid">		
<div class="card">

   <div class="card-body" align="center" >
	    <div class="row" style="margin-top:7px;"> 
	    <div class="col-md-12">

<div class="card">
      <div class="card-header" align="left">
        <h5>
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse0" id="applcollapse"><span  style="font-size:14px">
          
          <i class="fa fa-envelope" aria-hidden="true"   style="font-size:20px"></i> 
           <span class="badge badge-danger badge-counter badge-success" id="blink"><% if(approval!=null){ %>  <%=approval.size()%> <%}else{ %> 0 <%} %></span>
          Applied Tour  For Approval </span></a>
        </h5>
      </div>
	<div id="collapse0" class="card-collapse collapse in" style="padding: 10px;">
	<form action="DeptInchApproval.htm" method="post">
	        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;">
	            <thead>
	                <tr>
	                    <th>Name &amp; Designation </th>
	                    <th>From : To</th>
	                    <th>Applied On</th>
	                    <th>Last Action</th>
	                    <th>Comment</th>
	                    <th>
	                    All <input type="checkbox" id="ghapp"  onclick="changemyapp('ghapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;">Submit</button></th>
	                </tr>
	            </thead>
	            <tbody>
	            <%int count=0; 
	            if(approval!=null){
	            for(Object[] ls:approval){
	            		String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(ls[3].toString());
	                    String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(ls[4].toString());
	                    long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(ls[3].toString(), ls[4].toString());   
	            %>
	             <tr>
	             
	                    <td style="text-align:center;"><%= ls[1]%><br> <%=ls[2]%></td>
	                    <td style="text-align:center;"><%=stayfromdate%><br><%=staytodate%></td>
	                    <td style="text-align:center;"><%=ls[5]%><br>for <%=noofdays%> Day(s)</td> 
	                    <td style="text-align:left;"> <%=ls[8] %></td>
	                    <td style="text-align:center;"><textarea name="<%=ls[0]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
	                    <td > <input type="hidden" name="EmpNo<%=ls[0]%>" value="<%=ls[9]%>">
	        				<!-- CBY:checkbox yes -->
					        <span class="app ghapp">
							<label for="ResponseSaidCBY<%=count%>">App</label> 
							<input type="checkbox" class="ghapp"  id="ResponseSaidCBY<%=count%>" name="approve" value="<%=ls[0]%>_<%=ls[7]%>" onclick="changemyclick('<%=count%>')" >
					        </span>
					        
					         <!-- CBY:checkbox No -->
					        <span class="disapp ghappdis" style="margin-right: -10px;"> 
							<label for="ResponseSaidCBN<%=count%>">DisApp</label> 
							<input type="checkbox" class="ghappdis" id="ResponseSaidCBN<%=count%>" name="reject" value="<%=ls[0]%>_<%=ls[7]%>"  onclick="changemyclick('<%=count%>')" >
					         </span> 
	           		   </td>
	           </tr>
	           <% ++count; }}%> <!-- if closed -->
	           </tbody>
	     </table>
	      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	 </form>
	</div>
</div>
	   <%if(fapadetails!=null && fapadetails[1]!=null && fapadetails[1].toString().equalsIgnoreCase(session.getAttribute("EmpNo").toString())){%>
<div class="card" style="margin-top: 10px;">
      <div class="card-header" align="left">
        <h5>
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse1" id="applcollapse1"><span  style="font-size:14px">
          
          <i class="fa fa-envelope" aria-hidden="true"   style="font-size:20px"> 
         
          </i> 
           <span class="badge badge-danger badge-counter badge-success" id="blink2"><% if(fAndAdeptlist!=null){ %>  <%=fAndAdeptlist.size()%> <%}else{ %> 0 <%} %></span>
          Applied Tour To F & A Department for Approval</span></a>
        </h5>
      </div>
<div id="collapse1" class="card-collapse collapse in" style="padding: 10px;">


<form action="DeptInchApproval.htm" method="post">
   
        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;">
            <thead>
                <tr>
                    <th>Name &amp; Designation </th>
                    <th>From : To</th>
                    <th>Applied On</th>
                    <th>Forward By</th>
                    <th>Funds Availability</th>
                    <th>Comment</th>
                    <th>
                    All <input type="checkbox" id="FAapp"  onclick="changemyapp1('FAapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;">Submit</button></th>
                </tr>
            </thead>
            <tbody>
            <%int count1=0; 
            if(fAndAdeptlist!=null){
            for(Object[] ls:fAndAdeptlist){
            		String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(ls[3].toString());
                    String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(ls[4].toString());
                    long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(ls[3].toString(), ls[4].toString());   
            %>
             <tr>
                    <td style="text-align:center;"><%= ls[1]%><br> <%=ls[2]%></td>
                    <td style="text-align:center;"><%=stayfromdate%><br><%=staytodate%></td>
                    <td style="text-align:center;"><%=ls[5]%><br>for <%=noofdays%> Day(s)</td> 
                    <td style="text-align:left;"> <%=ls[8] %></td>
                    <td style="text-align:center;">	
						<input name="Funds<%=ls[0]%>" id="funds<%=ls[0]%>" value="false" onchange="funds('<%=ls[0]%>', this)"  type="checkbox" data-toggle="toggle" data-style="ios" data-onstyle="primary" data-offstyle="danger" data-width="105" data-height="15" data-on="<i class='fa fa-check' aria-hidden='true'></i> Yes" data-off="<i class='fa fa-times' aria-hidden='true'></i> No" >
					</td>
                    <td style="text-align:center;"><textarea name="<%=ls[0]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
                    <td> 
        				<!-- CBY:checkbox yes -->
				        <span class="app FAapp">
						<label for="ResponseSaidCBY<%=count%>">App</label> 
						<input type="checkbox" class="FAapp"  id="ResponseSaidCBY<%=count1%>" name="approve" value="<%=ls[0]%>_<%=ls[7]%>" onclick="changemyclick('<%=count1%>')" >
				        </span>
				        
				         <!-- CBY:checkbox No -->
				        <span class="disapp ghappdis" style="margin-right: -10px;"> 
						<label for="ResponseSaidCBN<%=count%>">DisApp</label> 
						<input type="checkbox" class="ghappdis" id="ResponseSaidCBN<%=count1%>" name="reject" value="<%=ls[0]%>_<%=ls[7]%>"  onclick="changemyclick('<%=count1%>')" >
				         </span> 
           		   </td>
           </tr>
           <% ++count1; }}%> 
           </tbody>
     </table>
      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
 </form>
</div>
</div>
<%}%>
<div class="card" style="margin-top: 10px;">
      <div class="card-header" align="left">
        <h5>
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse2" id="applcollapse2"><span  style="font-size:14px">
          
          <i class="fa fa-envelope" aria-hidden="true"   style="font-size:20px"> </i> 
           <span class="badge badge-danger badge-counter badge-success" id="blink3"><% if(canceledlist!=null){ %>  <%=canceledlist.size()%> <%}else{ %> 0 <%} %></span>
          Canceled Tour  For Approval </span></a>
        </h5>
      </div>
	<div id="collapse2" class="card-collapse collapse in" style="padding: 10px;">
	<form action="CancelApproval.htm" method="post">
	        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;">
	            <thead>
	                <tr>
	                    <th>Name &amp; Designation </th>
	                    <th>From : To</th>
	                    <th>Applied On</th>
	                    <th>Last Action</th>
	                    <th>Comment</th>
	                    <th>
	                    All <input type="checkbox" id="CAapp"  onclick="changemyapp2('CAapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;">Submit</button></th>
	                </tr>
	            </thead>
	            <tbody>
	            <%int count2=0; 
	            if(canceledlist!=null){
	            for(Object[] ls:canceledlist){
	            		String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(ls[3].toString());
	                    String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(ls[4].toString());
	                    long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(ls[3].toString(), ls[4].toString());   
	            %>
	             <tr>
	             
	                    <td style="text-align:center;"><%= ls[1]%><br> <%=ls[2]%></td>
	                    <td style="text-align:center;"><%=stayfromdate%><br><%=staytodate%></td>
	                    <td style="text-align:center;"><%=ls[5]%><br>for <%=noofdays%> Day(s)</td> 
	                    <td style="text-align:left;"> <%=ls[8] %></td>
	                    <td style="text-align:center;"><textarea name="<%=ls[0]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
	                    <td > <input type="hidden" name="EmpNo<%=ls[0]%>" value="<%=ls[9]%>">
	        				<!-- CBY:checkbox yes -->
					        <span class="app CAapp">
							<label for="ResponseSaidCBY<%=count2%>">App</label> 
							<input type="checkbox" class="CAapp"  id="ResponseSaidCBY<%=count2%>" name="approve" value="<%=ls[0]%>_<%=ls[7]%>" onclick="changemyclick('<%=count2%>')" >
					        </span>
					        
					         <!-- CBY:checkbox No -->
					        <span class="disapp ghappdis" style="margin-right: -10px;"> 
							<label for="ResponseSaidCBN<%=count2%>">DisApp</label> 
							<input type="checkbox" class="ghappdis" id="ResponseSaidCBN<%=count2%>" name="reject" value="<%=ls[0]%>_<%=ls[7]%>"  onclick="changemyclick('<%=count2%>')" >
					         </span> 
	           		   </td>
	           </tr>
	           <% ++count2; }}%> <!-- if closed -->
	           </tbody>
	     </table>
	      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	 </form>
	</div>
</div>

	   </div>
	   </div>			
	</div>	
	<script type="text/javascript">
	<%if(approval!=null && approval.size()>0){%>   $('#applcollapse').click(); <%}%>
	<%if(fAndAdeptlist!=null && fAndAdeptlist.size()>0){%> $('#applcollapse1').click(); <%}%>
	<%if(canceledlist!=null && canceledlist.size()>0){%> $('#applcollapse2').click(); <%}%>
	function changemyclick(a)
	{
		document.getElementById("ResponseSaidCBY"+a).onchange = function () {
		  if (document.getElementById("ResponseSaidCBN"+a).checked) {
			             document.getElementById("ResponseSaidCBN"+a).checked = false;
	          }
	       }
	  document.getElementById("ResponseSaidCBN"+a).onchange = function () {
		       if (document.getElementById("ResponseSaidCBY"+a).checked) {
			    document.getElementById("ResponseSaidCBY"+a).checked = false;
	         }
	     }
	}
	function changemyapp(a)
	{
		$('.'+a+'dis input[type=checkbox]').prop('checked',false); 
		if(document.getElementById(a).checked) {
			$('.'+a+' input[type=checkbox]').prop('checked',true); 
		} else {
			$('.'+a+' input[type=checkbox]').prop('checked',false); 
		} 
	}
	
	function changemyapp1(a)
	{
		$('.'+a+'dis input[type=checkbox]').prop('checked',false); 
		if(document.getElementById(a).checked) {
			$('.'+a+' input[type=checkbox]').prop('checked',true); 
		} else {
			$('.'+a+' input[type=checkbox]').prop('checked',false); 
		} 
	}
	
	function changemyapp2(a)
	{
		$('.'+a+'dis input[type=checkbox]').prop('checked',false); 
		if(document.getElementById(a).checked) {
			$('.'+a+' input[type=checkbox]').prop('checked',true); 
		} else {
			$('.'+a+' input[type=checkbox]').prop('checked',false); 
		} 
	}
	function funds(val, val1){
		$("#funds"+val).val($(val1).prop('checked'));
	}
	</script>
</div>
</div> 	
</body>
</html>
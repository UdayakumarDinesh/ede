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
<title>Dir Approval</title>
<style type="text/css">
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
   
</style>
</head>
<body>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Leave Approval</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Director Approval</li>
					</ol>
				</div>
			</div>
</div>
<%
String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%><center>
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></center>
	<%}if(ses!=null){ %>
	<center>
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div></center>
                    <%} %>			
	
<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >

    <% int applied=0;
    int modified=0;
    int cancelled=0;
    List<Object[]> ghlist=(List<Object[]>)request.getAttribute("LeaveApprovalDir");
    for(Object[] ls:ghlist){ 
    if("LRO".equalsIgnoreCase(ls[15].toString())){
    	applied++;
    }else if("LMO".equalsIgnoreCase(ls[15].toString())){
    	modified++;
    }else  if("LCO".equalsIgnoreCase(ls[15].toString())){
    	cancelled++;
    }
    }
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>
	    <div class="row" style="margin-top:7px;"> 
	    <div class="col-md-12">

<div class="card">
      <div class="card-header" align="left">
        <h5>
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse1" id="applcollapse"><span  style="font-size:14px">
          
          <i class="fa fa-envelope" aria-hidden="true"   style="font-size:20px"> 
         
          </i> 
           <span class="badge badge-danger badge-counter badge-success" ><%=applied%></span>
          Applied Leaves  For Recommendation  and Sanction </span></a>
        </h5>
      </div>
  <div id="collapse1" class="card-collapse collapse in" style="padding: 10px;">

<!-- Applied  leaves -->
<form action="leaveApprovals.htm" method="post">
   
        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;">
            <thead>
                <tr>
                    <th>Name &amp; Designation </th>
                    <th>Leave Type</th>
                    <th>From : To</th>
                    <th>Applied On</th>
                    <th>Last Action</th>
                    <th>Comment</th>
                    <th>
                    All <input type="checkbox" id="ghapp"  onclick="changemyapp('ghapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;">Submit</button></th>
                </tr>
            </thead>
            <tbody>
            <%int count=0; %>
            <%for(Object[] ls:ghlist){ %>
            <% 
              String Status=ls[15].toString();
              if("LRO".equalsIgnoreCase(Status) )
              {

            %>
             <tr>
                    <td style="text-align:left;"><%= ls[1]%><br> <%=ls[2]%></td>
                    <td style="text-align:center;"><button class="btn btn-primary btn-sm btn-outline" formaction="leaveprint.htm" name="applId" value="<%=ls[10]%>" formtarget="_blank"><%=ls[14] %><%if("F".equalsIgnoreCase(ls[4].toString())||"A".equalsIgnoreCase(ls[4].toString())){out.println(" ("+ls[4]+")");} %></button>
                    <%if(ls[3].toString().equals("0003")){%>
                    <br>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">MC</a>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">FC</a>
                    <%}%>
                    
                    
                    </td>
                    
                    
                    
                    
                    <td><%=ls[6] %><br><%=ls[7] %></td>
                    <td><%=ls[12] %><br>for <%=ls[8]%> Day(s)</td> 
                    <td style="text-align:left;"><%=ls[17] %><br><%=ls[9] %></td>
                    <td><textarea name="<%=ls[10]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
                    <td> 
        
         <!-- CBY:checkbox yes -->
        <span class="app ghapp">
		<label for="ResponseSaidCBY<%=count%>">Approve</label> 
		<input type="checkbox" class="ghapp"  id="ResponseSaidCBY<%=count%>" name="approve" value="<%=ls[10]%>_<%=ls[16]%>" onclick="changemyclick('<%=count%>')" >
        </span>
        
         <!-- CBY:checkbox No -->
        <span class="disapp ghappdis" style="margin-right: -10px;"> 
		<label for="ResponseSaidCBN<%=count%>">DisApprove</label> 
		<input type="checkbox" class="ghappdis" id="ResponseSaidCBN<%=count%>" name="reject" value="<%=ls[10]%>_<%=ls[16]%>"  onclick="changemyclick('<%=count%>')" >
         </span> 
         
          <input type="hidden" name="empid<%=ls[10]%>" value="<%=ls[0]%>"> 
          <input type="hidden" name="leaveCode<%=ls[10]%>" value="<%=ls[3]%>">
         <input type="hidden" name="NoDays<%=ls[10]%>" value="<%=ls[8]%>">
         <input type="hidden" name="Cat_Id<%=ls[10]%>" value="<%=ls[13]%>">
            </td>
           </tr>
           <% ++count;}} %> <!-- if closed -->
           
           <% %><!-- for closed -->
           </tbody>
     </table>
      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
      <input type="hidden" name="Type" value="DIR">
 </form>

<!-- //Applied  leaves -->


</div>
<!-- //Applied  leaves accordion group -->

	   
	   </div>
	   
	   
<div class="card" style="margin-top: 10px;">
      <div class="card-header" align="left">
        <h5>
          <a data-toggle="collapse" data-parent="#accordion_m" href="#collapse1_m"><span  style="font-size:14px">
          
          <i class="fa fa-envelope" aria-hidden="true"   style="font-size:20px"> 
         
          </i> 
           <span class="badge badge-danger badge-counter badge-success" ><%=modified %></span>
          Amended Leaves  For Recommendation  and Sanction </span></a>
        </h5>
      </div>
  <div id="collapse1_m" class="card-collapse collapse in" style="padding: 10px;">

<!-- Applied  leaves -->
<form action="leaveApprovals.htm" method="post">
   
        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;">
            <thead>
                <tr>
                    <th>Name &amp; Designation </th>
                    <th>Leave Type</th>
                    <th>From : To</th>
                    <th>Applied On</th>
                    <th>Last Action</th>
                    <th>Comment</th>
                    <th>
                    All <input type="checkbox" id="ghmapp"  onclick="changemyapp('ghmapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;">Submit</button></th>
                </tr>
            </thead>
            <tbody>
            <%int countm=0; %>
            <%for(Object[] ls:ghlist){ %>
            <% 
              String Status=ls[15].toString();
              if("LMO".equalsIgnoreCase(Status) )
              {

            %>
             <tr>
                    <td style="text-align:left;"><%= ls[1]%><br> <%=ls[2]%></td>
                    <td style="text-align:center;"><button class="btn btn-primary btn-sm btn-outline" formaction="leaveprint.htm" name="applId" value="<%=ls[10]%>" formtarget="_blank"><%=ls[14] %><%if("F".equalsIgnoreCase(ls[4].toString())||"A".equalsIgnoreCase(ls[4].toString())){out.println(" ("+ls[4]+")");} %></button>
                    <%if(ls[3].toString().equals("0003")){%>
                    <br>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">MC</a>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">FC</a>
                    <%}%>
                    
                    
                    </td>
                    
                    
                    
                    
                    <td><%=ls[6] %><br><%=ls[7] %></td>
                    <td><%=ls[12] %><br>for <%=ls[8]%> Day(s)</td> 
                    <td style="text-align:left;"><%=ls[17] %><br><%=ls[9] %></td>
                    <td><textarea name="<%=ls[10]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
                    <td> 
        
         <!-- CBY:checkbox yes -->
        <span class="app ghmapp">
		<label for="ResponseSaidCBY<%=ls[10]%>">Approve</label> 
		<input type="checkbox" class="ghmapp"  id="ResponseSaidCBY<%=ls[10]%>" name="approve" value="<%=ls[10]%>_<%=ls[16]%>" onclick="changemyclick('<%=ls[10]%>')" >
        </span>
        
         <!-- CBY:checkbox No -->
        <span class="disapp ghmappdis" style="margin-right: -10px;"> 
		<label for="ResponseSaidCBN<%=ls[10]%>">DisApprove</label> 
		<input type="checkbox" class="ghmappdis" id="ResponseSaidCBN<%=ls[10]%>" name="reject" value="<%=ls[10]%>_<%=ls[16]%>"  onclick="changemyclick('<%=ls[10]%>')" >
         </span> 
         
          <input type="hidden" name="empid<%=ls[10]%>" value="<%=ls[0]%>"> 
          <input type="hidden" name="leaveCode<%=ls[10]%>" value="<%=ls[3]%>">
         <input type="hidden" name="NoDays<%=ls[10]%>" value="<%=ls[8]%>">
         <input type="hidden" name="Cat_Id<%=ls[10]%>" value="<%=ls[13]%>">
            </td>
           </tr>
           <% ++countm;}} %> <!-- if closed -->
           
           <% %><!-- for closed -->
           </tbody>
     </table>
      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
      <input type="hidden" name="Type" value="DIR">
 </form>

<!-- //Applied  leaves -->


</div>
<!-- //Applied  leaves accordion group -->

	   
	   </div>
	   
	   <div class="card" style="margin-top: 10px;">
      <div class="card-header" align="left">
        <h5>
          <a data-toggle="collapse" data-parent="#accordion_c" href="#collapse1_c"><span  style="font-size:14px">
          
          <i class="fa fa-envelope" aria-hidden="true"   style="font-size:20px"> 
         
          </i> 
           <span class="badge badge-danger badge-counter badge-success" ><%=cancelled%></span>
         Cancelled Leaves  For Recommendation  and Sanction </span></a>
        </h5>
      </div>
  <div id="collapse1_c" class="card-collapse collapse in" style="padding: 10px;">

<!-- Applied  leaves -->
<form action="leaveApprovals.htm" method="post">
   
        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;">
            <thead>
                <tr>
                    <th>Name &amp; Designation </th>
                    <th>Leave Type</th>
                    <th>From : To</th>
                    <th>Applied On</th>
                    <th>Last Action</th>
                    <th>Comment</th>
                    <th>
                    All <input type="checkbox" id="ghcapp"  onclick="changemyapp('ghcapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;">Submit</button></th>
                </tr>
            </thead>
            <tbody>
            <% %>
            <%for(Object[] ls:ghlist){ %>
            <% 
              String Status=ls[15].toString();
              if("LCO".equalsIgnoreCase(Status))
              {

            %>
             <tr>
                    <td style="text-align:left;"><%= ls[1]%><br> <%=ls[2]%></td>
                    <td style="text-align:center;"><button class="btn btn-primary btn-sm btn-outline" formaction="leaveprint.htm" name="applId" value="<%=ls[10]%>" formtarget="_blank"><%=ls[14] %><%if("F".equalsIgnoreCase(ls[4].toString())||"A".equalsIgnoreCase(ls[4].toString())){out.println(" ("+ls[4]+")");} %></button>
                    <%if(ls[3].toString().equals("0003")){%>
                    <br>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">MC</a>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">FC</a>
                    <%}%>
                    
                    
                    </td>
                    
                    
                    
                    
                    <td><%=ls[6] %><br><%=ls[7] %></td>
                    <td><%=ls[12] %><br>for <%=ls[8]%> Day(s)</td> 
                    <td style="text-align:left;"><%=ls[17] %><br><%=ls[9] %></td>
                    <td><textarea name="<%=ls[10]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
                    <td> 
        
         <!-- CBY:checkbox yes -->
        <span class="app ghcapp">
		<label for="ResponseSaidCBY<%=ls[10]%>">Approve</label> 
		<input type="checkbox" class="ghcapp"  id="ResponseSaidCBY<%=ls[10]%>" name="approve" value="<%=ls[10]%>_<%=ls[16]%>" onclick="changemyclick('<%=ls[10]%>')" >
        </span>
        

         
          <input type="hidden" name="empid<%=ls[10]%>" value="<%=ls[0]%>"> 
          <input type="hidden" name="leaveCode<%=ls[10]%>" value="<%=ls[3]%>">
         <input type="hidden" name="NoDays<%=ls[10]%>" value="<%=ls[8]%>">
         <input type="hidden" name="Cat_Id<%=ls[10]%>" value="<%=ls[13]%>">
            </td>
           </tr>
           <% }} %> <!-- if closed -->
           
           <% %><!-- for closed -->
           </tbody>
     </table>
      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
      <input type="hidden" name="Type" value="DIR">
 </form>

<!-- //Applied  leaves -->


</div>
<!-- //canceledd  leaves accordion group -->

	   
	   </div>
	
	
	  <%List<Object[]> relist=(List<Object[]>)request.getAttribute("LeaveApprovalDirRecc"); %>
	   <div class="card" style="margin-top:7px;">
      <div class="card-header" align="left" >
        <h5>
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse4"><span  style="font-size:14px">
          
          <i class="fa fa-envelope" aria-hidden="true"   style="font-size:20px"> 
         
          </i> 
           <span class="badge badge-danger badge-counter badge-success" ><%=relist.size()%></span>
           Recommended Leaves  For Sanction </span></a>
        </h5>
      </div>
  <div id="collapse4" class="card-collapse collapse in" style="padding: 10px;">

<!-- Applied  leaves -->
<form action="leaveApprovals.htm" method="post">
   
        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;">
            <thead>
                <tr>
                    <th>Name &amp; Designation </th>
                    <th>Leave Type</th>
                    <th>From : To</th>
                    <th>Applied On</th>
                    <th>Last Action</th>
                    <th>Comment</th>
                    <th>
                    All <input type="checkbox" id="reapp"  onclick="changemyapp('reapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;">Submit</button></th>
                </tr>
            </thead>
            <tbody>
            <%int countre=0; %>
            <%for(Object[] ls:relist){ %>
            <% 


            %>
             <tr>
                    <td style="text-align:left;"><%= ls[1]%><br> <%=ls[2]%></td>
                    <td style="text-align:center;"><button class="btn btn-primary btn-sm btn-outline" formaction="leaveprint.htm" name="applId" value="<%=ls[10]%>" formtarget="_blank"><%=ls[14] %><%if("F".equalsIgnoreCase(ls[4].toString())||"A".equalsIgnoreCase(ls[4].toString())){out.println(" ("+ls[4]+")");} %></button>
                    <%if(ls[3].toString().equals("0003")){%>
                    <br>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">MC</a>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">FC</a>
                    <%}%>
                    
                    
                    </td>
                    
                    
                    
                    
                    <td><%=ls[6] %><br><%=ls[7] %></td>
                    <td><%=ls[12] %><br>for <%=ls[8]%> Day(s)</td> 
                    <td style="text-align:left;"><%=ls[17] %><br><%=ls[9] %></td>
                    <td><textarea name="<%=ls[10]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
                    <td> 
        
         <!-- CBY:checkbox yes -->
        <span class="app reapp">
		<label for="ResponseSaidCBY<%=countre%>">Approve</label> 
		<input type="checkbox" class="reapp"  id="ResponseSaidCBY<%=countre%>" name="approve" value="<%=ls[10]%>_<%=ls[16]%>" onclick="changemyclick('<%=count%>')" >
        </span>
        
         <!-- CBY:checkbox No -->
        <span class="disapp reappdis" style="margin-right: -10px;"> 
		<label for="ResponseSaidCBN<%=countre%>">DisApprove</label> 
		<input type="checkbox" class="reappdis" id="ResponseSaidCBN<%=countre%>" name="reject" value="<%=ls[10]%>_<%=ls[16]%>"  onclick="changemyclick('<%=count%>')" >
         </span> 
         
          <input type="hidden" name="empid<%=ls[10]%>" value="<%=ls[0]%>"> 
          <input type="hidden" name="leaveCode<%=ls[10]%>" value="<%=ls[3]%>">
         <input type="hidden" name="NoDays<%=ls[10]%>" value="<%=ls[8]%>">
         <input type="hidden" name="Cat_Id<%=ls[10]%>" value="<%=ls[13]%>">
            </td>
           </tr>
           <% ++countre;} %> <!-- if closed -->
           
           <% %><!-- for closed -->
           </tbody>
     </table>
      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
      <input type="hidden" name="Type" value="DIR">
 </form>

<!-- //Recc  leaves -->


</div>
<!-- //Reecc  leaves accordion group -->

	   
	   </div>   
	   <%List<Object[]> nrlist=(List<Object[]>)request.getAttribute("LeaveApprovalDirNr"); %>
	   <div class="card" style="margin-top:7px;">
      <div class="card-header" align="left">
        <h5>
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse5"><span  style="font-size:14px">
          
          <i class="fa fa-envelope" aria-hidden="true"   style="font-size:20px"> 
         
          </i> 
           <span class="badge badge-danger badge-counter badge-success" ><% if(nrlist!=null){%><%=nrlist.size()%> <%}else{ %> 0<%} %></span>
           Not Recommended Leaves  For Sanction </span></a>
        </h5>
      </div>
  <div id="collapse5" class="card-collapse collapse in" style="padding: 10px;">

<!-- Applied  leaves -->
<form action="leaveApprovals.htm" method="post">
   
        <table  class="table table-bordered table-hover table-striped table-condensed" style="padding: 10px;">
            <thead>
                <tr>
                    <th>Name &amp; Designation </th>
                    <th>Leave Type</th>
                    <th>From : To</th>
                    <th>Applied On</th>
                    <th>Last Action</th>
                    <th>Comment</th>
                    <th>
                    All <input type="checkbox" id="nrapp"  onclick="changemyapp('nrapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;">Submit</button></th>
                </tr>
            </thead>
            <tbody>
            <%int countnr=0;
            if(nrlist!=null){
            for(Object[] ls:nrlist){ 
            %>
             <tr>
                    <td style="text-align:left;"><%= ls[1]%><br> <%=ls[2]%></td>
                    <td style="text-align:center;"><button class="btn btn-primary btn-sm btn-outline" formaction="leaveprint.htm" name="applId" value="<%=ls[10]%>" formtarget="_blank"><%=ls[14] %><%if("F".equalsIgnoreCase(ls[4].toString())||"A".equalsIgnoreCase(ls[4].toString())){out.println(" ("+ls[4]+")");} %></button>
                    <%if(ls[3].toString().equals("0003")){%>
                    <br>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">MC</a>
                    <a class="btn btn-danger btn-xs btn-outline" href="<%=ls[15]%>" target="_blank">FC</a>
                    <%}%>
                    
                    
                    </td>
                    
                    
                    
                    
                    <td><%=ls[6] %><br><%=ls[7] %></td>
                    <td><%=ls[12] %><br>for <%=ls[8]%> Day(s)</td> 
                    <td style="text-align:left;"><%=ls[17] %><br><%=ls[9] %></td>
                    <td><textarea name="<%=ls[10]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
                    <td> 
        
         <!-- CBY:checkbox yes -->
        <span class="app nrapp">
		<label for="ResponseSaidCBY<%=count%>">Approve</label> 
		<input type="checkbox" class="nrapp"  id="ResponseSaidCBY<%=countnr%>" name="approve" value="<%=ls[10]%>_<%=ls[16]%>" onclick="changemyclick('<%=count%>')" >
        </span>
        
         <!-- CBY:checkbox No -->
        <span class="disapp nrappdis" style="margin-right: -10px;"> 
		<label for="ResponseSaidCBN<%=countnr%>">DisApprove</label> 
		<input type="checkbox" class="nrappdis" id="ResponseSaidCBN<%=countnr%>" name="reject" value="<%=ls[10]%>_<%=ls[16]%>"  onclick="changemyclick('<%=count%>')" >
         </span> 
         
          <input type="hidden" name="empid<%=ls[10]%>" value="<%=ls[0]%>"> 
          <input type="hidden" name="leaveCode<%=ls[10]%>" value="<%=ls[3]%>">
         <input type="hidden" name="NoDays<%=ls[10]%>" value="<%=ls[8]%>">
         <input type="hidden" name="Cat_Id<%=ls[10]%>" value="<%=ls[13]%>">
            </td>
           </tr>
           <% ++countnr;}} %> <!-- if closed -->
           
           <% %><!-- for closed -->
           </tbody>
     </table>
      <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
      <input type="hidden" name="Type" value="DIR">
 </form>

<!-- //Nr  leaves -->


</div>
<!-- //Nr  leaves accordion group -->

	   
	   </div>
	   
	   </div>
	   </div>		
			
	</div>	
	<script type="text/javascript">
	$('#applcollapse').click();
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
	</script>
</body>
</html>
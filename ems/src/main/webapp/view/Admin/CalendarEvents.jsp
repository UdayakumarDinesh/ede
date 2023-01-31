<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" 
    import ="java.text.SimpleDateFormat"
    import="java.util.Date"
    import="com.vts.ems.utils.DateTimeFormatUtil"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Calendar Events List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style>
input,textarea{
    margin: 8px;
}

</style>
</head>
<body>
     <%
     List<Object[]>eventTypeList=(List<Object[]>)request.getAttribute("EventTypeList"); 
     List<Object[]>eventList=(List<Object[]>)request.getAttribute("EventsList");
     String year=(String)request.getAttribute("year");
     SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
     %>
     
     <div class="card-header page-top">
		 <div class="row">
			<div class="col-md-3"> <h5>Calendar Events List</h5></div>
			<div class="col-md-9">
			    <ol class="breadcrumb">
			         <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
			         <li class="breadcrumb-item active" aria-current="page">Calendar Events List</li>
			    </ol>
		   </div> 
		</div>
	</div>
	<%	String result=(String)request.getParameter("result"); 
 	String resultFail=(String)request.getParameter("resultfail");
	if(resultFail!=null)
	{
	%>
	<div align="center">
		<div class="alert alert-danger" role="alert">
        	<%=resultFail %>
        </div>
   	</div>
	<%}if(result!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
        	<%=result %>
        </div>
    </div>
	<%} %>
<div class="page card dashboard-card">
	<div class="card-body" align="center">			
		<div class="card" style="width: 65%;">
			<div class="card-body main-card" align="left" style="padding-top: 0px;padding-bottom: 0px;">
			 <form action="CalendarEvents.htm" method="POST" autocomplete="off" id="myform">
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			   <div class="form-group">
			      <table style="margin-top: 0px;width: 100%;">
			               <tr >
			                 	<th style="width: 12%;"><label class="input-group"><b>Event Date&nbsp;</b><span class="mandatory" style="color:red;">*</span></label></th>
							    <td style="width: 38%;"><input type="text" class="form-control input-sm mydate" id="date" name="Eventdate"  required="required"  > 							        						       
							    </td>							      
			                    <th style="width: 15%;"> <label style="margin-left: 20px;"><b>Event Type</b><span class="mandatory" style="color:red;">*&nbsp;</span></label></th>
			                    <td style="width: 35%;">   
			                          <select class="form-control  input-sm select2" required="required" name="EventType" style="width:100%;"  >
				                        <%  
				                          if(eventList!=null){ 
				                           for(Object[] obj:eventTypeList){
				                        %>
				                        <option value="<%=obj[1]%>"><%=obj[2] %></option>
				                        <% }}%>
				                   </select>
				               </td>
                           </tr>
			               
			             <tr>
			              <th style="width: 13%;"> 
			                   <label ><b>Event Name</b><span class="mandatory" style="color:red;">*&nbsp;</span></label>
			              </th>
			               <td colspan="3">
			                   <input type="text" class="form-control input-sm" placeholder="Enter Event Name" id="eventName"name="EventName" required="required" maxlength="255">
			               </td>
			           </tr>
			           <tr>
			              <th> 
			                 <label><b>Description</b><!-- <span class="mandatory" style="color:red;">*&nbsp;</span> --></label>
			              </th>
			            <td colspan="3">   
			                 <textarea name="description" id="descrip" class="form-control w-100" required="required" maxlength="500" 
						      placeholder="Maximum 500 characters" ></textarea>
			            </td>
			         </tr>
			     
		    		<tr>
		    		
		    		  <td>  
		    		      <button type="submit" class="btn btn-sm submit-btn"  style="margin-left:345%; margin-top:12%;" onclick="return addEvent();" formaction="CalendarEventAdd.htm" formmethod="get">SUBMIT</button>
		              </td>   	   
		    </table>
				</div>
			</form>
   </div>
 </div>
</div>
<form action="CalendarEvents.htm" method="POST" id="frm" style="margin-left:30%;"> 
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table>
               <tr>
                     <th style="width: 25%;"><label >Events / Holidays for  the Year  </label></th>
                     <td> <input type="text" id="eventYear" name="eventYear" class="form-control input-sm date" onchange="this.form.submit()" value="<%=year%>"style="width:195px;"></td>
               </tr>
        </table>
        
         
            </form>
	<div class="card-body main-card">
        <form action="calendarEventEdit.htm" method="post"  style="margin-top: 0%;" >
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <div class="table-responsive" style="" >
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable"  >
				<thead>
					<tr>
					   <th style="width:4%">Select</th>
					   <th style="width:8%">Event Date</th>
					   <th style="width:10%">Event Type</th>
                       <th style="width:11%">Event Name</th>
                       <th style="width:41%">Event Description</th>
                       
                  	</tr>
				</thead>
				<tbody>
				       <%String eventId =null;
				          if(eventList!=null){
				        	  
					     for(Object[] event:eventList){
					    	// LocalDate ld= LocalDate.now();
					      eventId=event[1].toString();
					    	 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");					    	
					   %>
				
				       <tr>
				           <td style="text-align: center;">
				           	<% if(LocalDate.parse(event[1].toString()).isAfter(LocalDate.now()) ){ %>
				           	<input type="radio" name="EMSEventId" value="<%=event[0]%>" required="required">
				          	<%}else{ %>
				          	<input type="radio" disabled="disabled" required="required">
				          	<%} %>
				           </td>
				           <td style="text-align: center;"><%=sdf.format(event[1])%></td>
				           <td><%=event[2] %></td>
				           <td><%=event[3] %></td>
				           <td><%=event[4] %></td>
				       </tr>
				       <%}}else{ %>
				              <tr>
				              	<td colspan="12" style="text-align: center;">No Records Found</td>
				              </tr>
				       <%} %>
				</tbody>
			</table>
		</div>
		 <button  style="margin-left: 45%;padding:4px 17px;"  type="submit" class="btn btn-sm edit-btn" onclick="return Edit()" value="<%=year %>" name="eventYear" >EDIT </button>	
		 <button style="margin-left: 20px;" type="submit" class="btn btn-sm delete-btn" formaction="CalendarEventDelete.htm" value="<%=year %>" name="eventYear" onclick="return Delete()" formmethod="post" name="action" value="DELETE">DELETE </button>
	</form>
			
		</div>
 </div> 
 <script type="text/javascript">
$('.mydate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate":new Date(),
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
</script>
<script>
function addEvent(){	
	var eventName=$('#eventName').val();
	
			if(eventName==null ||eventName==""||eventName=="null"||eventName.trim()===""){
				alert('Enter Data Properly');
				return false;
			}else{
				var ret = confirm('Are you Sure To Submit ?');
				if(ret){
				$('#myform').submit();
				return true;
			}
				}	
		   return false;
		   event.preventDefault();
	}
		
</script>
<script type="text/javascript">
$('.date').datepicker({
    format: "yyyy",
    orientation: "bottom",
    minViewMode: "years",   
    autoclose:"true"
});
</script>
<script>
function Edit()
{
	var fields = $("input[name='EMSEventId']").serializeArray();
	var eventyear=$('#eventYear').val();
	if (fields.length === 0 && eventyear>=new Date().getFullYear() ) {
		
		alert("please select any one option ! ");
       event.preventDefault();
		return false;
	}else if(eventyear<new Date().getFullYear()){
			alert("You Cannot Edit this Events ")
			 event.preventDefault();
			return false;
	}
		
	else {
		var ret = confirm('Are you Sure want To Edit ?');
	  if(ret){
		$('#form2').submit();
		return true;
		}
	  else
		  {
		  return false;
		  event.preventDefault();
		  }
	  return false;
		 event.preventDefault();
	}
	 return false;
	 event.preventDefault();
	
}
function Delete()
{
	var fields = $("input[name='EMSEventId']").serializeArray();
	var eventyear=$('#eventYear').val();
	if (fields.length === 0 && eventyear>=new Date().getFullYear() ) {
		
		alert("please Select Any One Option! ");
       event.preventDefault();
		return false;
	}else if(eventyear<new Date().getFullYear()){
			alert("You Cannot Delete this Events ")
			 event.preventDefault();
			return false;
	}
		
	else {
		var ret = confirm('Are you Sure want To Delete ?');
	  if(ret){
		$('#form2').submit();
		return true;
		}
	  else
		  {
		  return false;
		  event.preventDefault();
		  }
	  return false;
		 event.preventDefault();
	}
	 return false;
	 event.preventDefault();
	
}
</script>
</body>
</html>
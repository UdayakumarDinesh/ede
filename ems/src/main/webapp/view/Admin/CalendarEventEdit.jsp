<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" 
     import="com.vts.ems.Admin.model.CalendarEvents"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CalendarEvent Edit</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<style>
input,textarea{
    margin: 8px;

}
</style>
</head>
<body>
    <%
         List<Object[]>eventTypeList=(List<Object[]>)request.getAttribute("EventTypeList"); 
         Object[] event=(Object[])request.getAttribute("EventsList");
         String year=(String)request.getAttribute("year");
         
     %>
<div class="card-header page-top">
		 <div class="row">
			<div class="col-md-4"> <h5>Calendar Event Edit</h5></div>
			<div class="col-md-8">
			    <ol class="breadcrumb">
			         <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
			         <li class="breadcrumb-item "><a href="CalendarEvents.htm">Calendar Events List</a></li>
			         <li class="breadcrumb-item active" aria-current="page">Calendar Event Edit</li>
			    </ol>
		   </div> 
		</div>
	</div>
	<div class="page card dashboard-card">
	<div class="card-body" align="center">			
		<div class="card" style="width: 65%;">
			<div class="card-body main-card" align="left" style="padding-top: 0px;padding-bottom: 0px;">			
			 <form action="CalendarEvents.htm" method="POST" autocomplete="off" id="myform">
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			   <div class="form-group">
			      <table style="margin-top: 0px;width: 100%;">
			                   <%if(event!=null){  %>
			                    <input type="hidden" name="EMSEventId"  value="<%= event[0]%>">
			               <tr >
			                     
			                     
			                 	<th style="width: 12%;"><label class="input-group"><b>Event Date&nbsp;</b><span class="mandatory" style="color:red;">*</span></label></th>
							    <td style="width: 38%;"><input type="text" class="form-control input-sm mydate" id="date" name="Eventdate"  value="<%= event[1] %>" required="required" readonly="readonly"  > 							        						       
							    </td>							      
			                    <th style="width: 15%;"> <label style="margin-left: 20px;"><b>Event Type</b><span class="mandatory" style="color:red;">*&nbsp;</span></label></th>
			                    <td style="width: 35%;">   
			                          <select class="form-control  input-sm select2" required="required" name="EventType"  style="width:100%;"  >
				                        <%  
				                          if(eventTypeList!=null){ 
				                           for(Object[] obj:eventTypeList){
				                        %>
				                        <option value="<%=obj[1]%>"  <%if((obj[1]).equals(event[2])){ %>selected="selected" <%}%>><%=obj[2]%></option>
				                        <%}} %>
				                      
				                   </select>
				               </td>
                           </tr>
			               
			             <tr>
			              <th style="width: 13%;"> 
			                   <label ><b>Event Name</b><span class="mandatory" style="color:red;">*&nbsp;</span></label>
			              </th>
			               <td colspan="3">
			                   <input type="text" class="form-control input-sm" placeholder="Enter Event Name" id="eventName" name="EventName" value="<%=event[4] %>" required="required" maxlength="255">
			               </td>
			           </tr>
			           <tr>
			              <th> 
			                 <label><b>Description</b><!-- <span class="mandatory" style="color:red;">*&nbsp;</span> --></label>
			              </th>
			            <td colspan="3">   
			                 <textarea name="description" id="descrip" class="form-control w-100" required="required" maxlength="500" 
						      placeholder="Maximum 500 characters" ><%=event[5] %></textarea>
			            </td>
			         </tr>
			    <%} %>
		    		<tr>
		    		
		    		  <td>  
		    		      <button type="submit" class="btn btn-sm submit-btn"  style="margin-left:345%; margin-top:12%;" onclick="return addEvent();" formaction="UpdateCalendarEvent.htm" formmethod="post" name="EventYear" value="<%=year %>" >SUBMIT</button>
		              </td>  
		               	 </tr>		               
		    </table>
				</div>
			</form>
          </div>
        </div>
      </div>
  </div>
   <!-- <script type="text/javascript">

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
</script>  -->
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
</body>
</html>
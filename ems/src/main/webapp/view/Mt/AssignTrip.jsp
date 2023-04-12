<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="java.time.LocalDate ,java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="com.vts.ems.Mt.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>AssignTrip</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
#myTable_length ,#myTable_info{
float: left;
}

</style>
</head>
<body>
<%  MtTrip mttrip = (MtTrip)request.getAttribute("TripData");
	String comp=(String)request.getAttribute("comp"); 
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	List<Object[]> driverlist=(List<Object[]>)request.getAttribute("DriverList");
	List<MtVehicle> vehiclelist=(List<MtVehicle>)request.getAttribute("vehiclelist");
	String username= (String)request.getAttribute("empname");
	String groupname=(String)request.getAttribute("groupcode");
 %>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
			<%if(mttrip!=null){%>
				<h5>Assign Trip Edit <small> <%if(username!=null){%> <%=username%> <%}%> </small> </h5>
			<%}else{%>
				<h5>Assign Trip      <small> <%if(username!=null){%> <%=username%> <%}%> </small> </h5>
			<%}%>
			</div>
				<div class="col-md-6 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<%if(mttrip!=null){ %>
							<li class="breadcrumb-item active " aria-current="page">MT Assign Trip Edit</li>
						<%}else{%>
						    <li class="breadcrumb-item active " aria-current="page">MT Assign Trip</li>
						<%}%>
						
						
					</ol>
				</div>
			</div>
</div>	

 
    <%String ses=(String)request.getParameter("result"); 
	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){%>
	<center><div class="alert alert-danger" role="alert"><%=ses1 %></div></center>
	<%}if(ses!=null){%>
	<center><div class="alert alert-success" role="alert" ><%=ses %> </div></center>
    <%}%>
 
 <div class=" page card dashboard-card">

	<div class="card-body" >
	<div class="row">
	<div class="col-12">
		<div class="card" >					
			<div class="card-body">
				<form action="MTTrip.htm" method="POST"autocomplete="off" id="tripform" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

 <div class="form-group">
 <div class="row"  >
		  <div class="col-md-4"  align="left">
				<label >Vehicle Operator:<span class="mandatory" style="color: red;">*</span></label>
				<div class=" input-group">				
				<select class="form-control select2"  name="Driver" id="driver" data-container="body" data-live-search="true"  required="required" >
						<option value="" disabled="disabled" selected="selected" hidden="true" >--Select--</option>
						<option value="0"  <%if(mttrip!=null && mttrip.getDriverId()==0 && 0==mttrip.getDriverId()){%> selected="selected" <%}%>>Hired Driver</option>
						<%if(driverlist!=null){
						for(Object[] drvlist:driverlist){ %>
						<option value="<%=drvlist[0]%>" <%if(mttrip!=null && mttrip.getDriverId()!=0 && drvlist[0].toString().equalsIgnoreCase(String.valueOf(mttrip.getDriverId()))){%> selected="selected" <%}%>><%=drvlist[1] %></option>
						<%}} %>
				</select>
				</div>
		</div>
		
		<div class="col-md-4" align="left" >
				<label >Vehicle Type:<span class="mandatory" style="color: red;">*</span></label>
				<div class=" input-group">
				<select class="form-control select2"  name="Vehicle"  data-container="body" data-live-search="true" required="required" id="vehicle" >
						<option value="" disabled="disabled" selected="selected" hidden="true" >--Select--</option>
						<option value="0" <%if(mttrip!=null && mttrip.getVehicleId()==0 && 0==mttrip.getVehicleId()){%>selected="selected" <%}%>>Hired Vehicle</option>
						<%if(vehiclelist!=null){
						for(MtVehicle vlist:vehiclelist){ %>
						<option value="<%=vlist.getVehicleId()%>" <%if(mttrip!=null && mttrip.getVehicleId()!=0 && vlist.getVehicleId()==mttrip.getVehicleId()){%>selected="selected" <%}%>><%=vlist.getVehicleName() %>&nbsp<%=vlist.getBaNo() %></option>
						<%}}%>
				</select>
				</div>
		</div>
		<div class="col-md-2" align="left">
			<label >If Hired, Vehicle #:</label>
			<div class=" input-group">
			<input  class="form-control date"  placeholder="Hired Vehicle" <%if(mttrip!=null && mttrip.getHiredVehicle()!=null){%>value="<%=mttrip.getHiredVehicle()%>"<%}%>      type="text" name="Hired" id="vehicle1">
	    	</div>
	    </div>
		<div class="col-md-2" align="left">
			<label >Place:<span class="mandatory" style="color: red;">*</span></label>	
			<div class=" input-group">
			<input  class="form-control "  placeholder="Place" <%if(mttrip!=null && mttrip.getPlace()!=null){%>value="<%=mttrip.getPlace()%>"<%}%> type="text" name="Place" id="place" required="required">
		     </div>
	 </div>	     
</div>
</div>


 <div class="form-group">
 <div class="row"  >


		<div class="col-md-2" align="left">
				<label >Duty Date :<span class="mandatory" style="color: red;">*</span></label>
				<div class=" input-group">
				<input  class="form-control input-sm mydate" <%if(mttrip!=null && mttrip.getTripDate()!=null){%>value="<%=mttrip.getTripDate()%>"<%}%>  placeholder="Duty Date" id="dutydate" name="DutyDate" required="required">
		   </div>  
		</div>
		<div class="col-md-2" align="left">
				<label >Approx Depar Time:<span class="mandatory" style="color: red;">*</span></label>
			<div class=" input-group">
				 <input  class="form-control  " id="stime" placeholder="Time" <%if(mttrip!=null && mttrip.getStartTime()!=null){%>value="<%=mttrip.getStartTime()%>"<%}%> name="STime" required="required" id="Stime"> 
			</div>
		</div>
		
		<div class="col-md-2" align="left">
				<label >Duty End Date :<span class="mandatory" style="color: red;">*</span></label>
				<div class=" input-group">
				<input  class="form-control input-sm mydate"  placeholder="Duty End Date" <%if(mttrip!=null && mttrip.getTripEndDate()!=null){%>value="<%=mttrip.getTripEndDate()%>"<%}%> id="dutyenddate" name="DutyEndDate" required="required">
		       </div>
		</div>
		<div class="col-md-2" align="left">
				<label >Approx Return Time :<span class="mandatory" style="color: red;">*</span></label>
				 <input  class="form-control  " id="etime" placeholder="Time" <%if(mttrip!=null && mttrip.getEndTime()!=null){%>value="<%=mttrip.getEndTime()%>"<%}%> name="ETime" required="required" id="Etime"> 
		</div>


		 <div class="col-md-4" align="left">
				<label >Comments :<span class="mandatory" style="color: red;">*</span></label>
				 <div class=" input-group">
				 <input  class="form-control" placeholder="Comments" <%if(mttrip!=null && mttrip.getMtoComments()!=null){%>value="<%=mttrip.getMtoComments()%>"<%}%> type="text" name="Comments" id="comments" required="required" >
		       </div>
 		</div>
 </div>
</div>
		<%if(mttrip!=null){%>
		<input type="hidden" name="TripId" <%if(mttrip!=null && mttrip.getTripId()!=0){%>value="<%=mttrip.getTripId()%>"<%}%>>
		 <center><button type="button" class="btn btn-sm submit-btn" onclick="CheckData()" >Submit</button></center>
		<%}else{%>
	    <center><button type="button" class="btn btn-primary btn-sm"  onclick="CheckData()">CREATE</button></center>
	    <%}%>
	
	
	</form>
	</div>
	</div>
	</div>
	 </div>
	<br>

<%if(mttrip==null){ %><!-- for edit the trip hide lists -->


<%List<Object[]>  triplist = (List<Object[]>)request.getAttribute("triplist");
List<Object[]> reqlinkduty=(List<Object[]>)request.getAttribute("linkrequestlist");%>

<div class="row">
	<div class="col-12">
	
<div class="card" >
		<div class="card-header" style="height: 36px; margin-top: -3px;"><b> LIST OF ALLOTED TRIP </b></div>
   <div class="card-body" align="center">
   
		  <form action="MtTripC.htm" method="POST" >
		  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		   <div class="table-responsive">
		  <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
	          <thead>
		             <tr>
		                 <th>Select</th>
		                 <th>Vehicle</th>
		        	     <th>Place</th>
		                 <th>Vehicle Operator</th>
		                 <th>Trip Date</th>
		                 <th>Start Time</th>
		                 <th>Link Request</th>	
		            </tr>
		         </thead>
			<tbody>
			<%if(triplist!=null){ for(Object[] obj:triplist){%>
		<tr>
			<td align="center"><input type="radio" name="ATripid" <%if(obj[1]!=null){%>value=<%=obj[1]%><%}%> required="required" 
				    <%if(reqlinkduty!=null){ for(Object[] reqlink:reqlinkduty){
				    	 if(reqlink[5].toString().equalsIgnoreCase(obj[1].toString())){%> disabled="disabled" <%}}}%>></td> 
					    
			<td ><%if(obj[7]!=null && obj[8]!=null){%>  <%=obj[7]%>  <%=obj[8]%>    <%}%></td>
	      	<td ><%if(obj[4]!=null ){%> <%=obj[4]%> <%}%></td>
	      	<td ><%if(obj[0]!=null ){%> <%=obj[0]%> <%}%></td>
	      	<td align="center" ><%if(obj[5]!=null ){%> <%=sdf.format(obj[5])%> <b>To</b> <br> <%=sdf.format(obj[9])%> <%}%></td>
	      	<td align="center"><%if(obj[6]!=null ){%>   <%=obj[6]%>  <%}%></td>
	      	<td style="max-width:200px; font-size:x-small; overflow: hidden; word-break: break-word !important; white-space: normal !important; ">
			    <%for(Object[] reqlink:reqlinkduty){	    	 
			    	if(reqlink[5].toString().equalsIgnoreCase(obj[1].toString())){%>
			    		 <%=reqlink[0]%> <%=reqlink[1]%> TO <%=reqlink[2]%> <%=reqlink[3]%><a  style="color:red; width: 10%;" href="MtUnlink.htm?lid=<%=reqlink[6]%>&appid=<%=reqlink[4] %>" onclick="return confirm('Are you sure To Remove?')"  ><i class="fa-regular fa-circle-xmark" ></i></a><br>	    	
			    <% }} %>
	        </td>
				</tr>
				<%}}%>
			</tbody>	
		 </table>
		 </div> 
		 <%if(triplist!=null &&triplist.size()>0){%>
		 <div align="center">
				<button type="submit" class="btn btn-sm edit-btn" style="margin-right: 5px;" name="action" value="edit"  Onclick="EditALLOTEDTRIP()" >EDIT </button>
				<button type="submit" class="btn btn-sm delete-btn" style="margin-right: 5px;" name="action" value="delete"   Onclick="DeleteALLOTEDTRIP()">Delete </button>
		 </div>
		 <%}%>
		 </form>	
	 </div>
	 </div>
	 </div>
	 </div>

<br>




<div class="row">
	<div class="col-12">
	
<div class="card" >
		<div class="card-header" style="height: 36px; margin-top: -3px;"><b >REQUEST FOR NEXT FIVE DAYS</b></div>
   <div class="card-body" align="center">
   
   <%List<Object[]> firstapply=(List<Object[]>)request.getAttribute("firstapply"); 
   List<Object[]> secondapply=(List<Object[]>)request.getAttribute("secondapply");%>

	<%if(firstapply!=null&&firstapply.size()>0){%>	
	 <center><b>Requests for Date :</b><%=sdf.format(new Date()) %></center>
		  <form action="MTRequestEdit.htm" method="POST" name="frm2">
		   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		   <div class="table-responsive">
		  <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
  
             <thead>
	             <tr >
	               	 <th>Select</th>
	                 <th>Indentor</th>
	        	     <th>Date</th>
	        	     <th>Time</th>
	                 <th>Source</th>
	                 <th>Destination</th>
	                 <th>Nature</th>
	               	 <th>Reason</th>
	                 <th>User Comments</th>
	                 <th>MTO Comments</th>	
	              </tr>
	         </thead>
	  <tbody>
	  <tr>
		<%for(Object[] alist:firstapply){
		%>
			
	      	  
			      	<td align="center"><input type="radio" name="Aid" value="<%=alist[0]%>" required="required" ></td> 
			      	<td><%=alist[2]%> <%=alist[3]%></td>
			      	<td ><%=sdf.format(alist[4])%> <b>To </b> <%=sdf.format(alist[16])%></td>
			      	<td ><%=alist[5]%>-<%=alist[6]%></td>
			      	<td ><%=alist[7]%></td>
			      	<td ><%=alist[8]%></td>
			        <td ><%=alist[9]%></td>
			        <td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;">
			      	<%if(alist[15]!=null){%>
			      	<%=alist[15]%>
			      	<%}else{ %><span>No Reason</span><%} %>
			      	</td>
			      
			      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;">
			      	<%if(alist[14]!=null){%>
			      	<%=alist[14]%>
			      	<%}else{ %><span>No Comment</span><%} %>
			      	</td>
			      	<td >
			      	<%if(alist[13]!=null){%>
			      	<%=alist[13]%>
			      	<%}else{ %><span>No Comment</span><%} %>
			      	</td>	      	
			
	 
	 <%} %>
	  </tr>
</tbody>
	 </table>
	 </div>
	   <div align="center">	
	   <button type="submit" class="btn btn-sm edit-btn" name="sub" value="edit"  onclick="Edit(frm2)">EDIT</button>&nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;
		<!-- <button type="submit" class="btn btn-sm delete-btn" name="sub" value="delete" formaction="MTRequestDelete.htm"  onclick="Delete(frm2)">DELETE</button> -->
	 </div>

	   </form>
	 <%} if( secondapply!=null&&secondapply.size()>0){%>	 
	<%--  <center><b>Requests for Date :</b><%=sdf.format(new Date().getTime()+(24*60*60*1000)) %></center> --%>
	    <form action="MTRequestEdit.htm" method="POST" name="frm2">
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    <div class="table-responsive">
	   <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" >
   	
    <thead>
	             <tr >
	               	  <th>Select</th>
	                  <th>Indentor</th>
	        	      <th>Date</th>
	        	      <th>Time</th>
	                  <th>Source</th>
	                  <th>Destination</th>
	                  <th>Nature</th>
	                  <th>Reason</th>
	                  <th>User Comments</th>
	                  <th>MTO Comments </th>		
	               </tr>
	         </thead>
	         <tbody>
		<%for(Object[] alist:secondapply){%>
			
	      	<tr>
	      	<td align="center"><input type="radio" name="Aid" value="<%=alist[0]%>" required="required" ></td> 
	      	<td ><%=alist[2]%>(<%=alist[3]%>)</td>      	
	      	<td style="width:98px;" ><%=sdf.format(alist[4])%><br> To <br><%=sdf.format(alist[16])%> </td>
	      	<td ><%=alist[5]%>-<%=alist[6]%></td>
	      	<td ><%=alist[7]%></td>
	      	<td ><%=alist[8]%></td>
	        <td ><%=alist[9]%></td>
	      
	        <td>
	      	<%if(alist[15]!=null){%>
	      	<%=alist[15]%>
	      	<%}else{ %><span>No Reason</span><%} %>
	      	</td>
	      
	      	<td>
	      	<%if(alist[14]!=null){%>
	      	<%=alist[14]%>
	      	<%}else{ %><span>No Comment</span><%} %>
	      	</td>
	      	<td>
	      	<%if(alist[13]!=null){%>
	      	<%=alist[13]%>
	      	<%}else{ %><span>No Comment</span><%} %>
	      	</td>
			 
	 </tr>
	 <%}%> 
	 </tbody>
	 </table>
	 </div>
	<div align="center">	
	<button type="submit" class="btn btn-sm edit-btn" name="sub" value="edit"  onclick="Edit(frm2)">EDIT</button>&nbsp; &nbsp; &nbsp; 
	<!--  <button type="submit" class="btn btn-sm delete-btn" name="sub" value="delete" formaction="MTRequestDelete.htm"  onclick="Delete(frm2)">DELETE</button> -->
	</div> 
	   </form>
<%}%><!-- second apply close -->
</div>
</div>
	 </div>
		</div>
	<%}%>  <!--  trip list close -->
</div>
</div>
<script type="text/javascript">
$(function () {
    $('#stime','#etime').datetimepicker({
    	
    	format: 'HH:mm'
    });
});

$(function() {
	   $('#stime,#etime,#stime1,#etime1').daterangepicker({
	            timePicker : true,
	            singleDatePicker:true,
	            timePicker24Hour : true,
	            timePickerIncrement : 1,
	            timePickerSeconds : false,
	            locale : {
	                format : 'HH:mm'
	            }
	        }).on('show.daterangepicker', function(ev, picker) {
	            picker.container.find(".calendar-table").hide();
	   });
	})


$('#dutydate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	
	<%if(mttrip!=null && mttrip.getTripDate()!=null){%>
	"startDate" : new Date("<%=mttrip.getTripDate()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#dutyenddate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#dutydate').val(),
	<%if(mttrip!=null && mttrip.getTripEndDate()!=null){%>
	"startDate" :new Date("<%=mttrip.getTripEndDate()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$( "#dutydate" ).change(function() {
	
	$('#dutyenddate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : $('#dutydate').val(), 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});


function Edit(myfrm) {

	var fields = $("input[name='Aid']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One ");

		event.preventDefault();
		return false;
	}
	return true;
}

function Delete(myfrm) {

	var fields = $("input[name='Aid']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One ");
		event.preventDefault();
		return false;
	}
	var cnf = confirm("Are You Sure To Delete!");

	if (cnf) {
		return true;
	} else {
		event.preventDefault();
		return false;
	}

}


function EditALLOTEDTRIP() {

	var fields = $("input[name='ATripid']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Alloted Trip");

		event.preventDefault();
		return false;
	}
	return true;
}

function DeleteALLOTEDTRIP() {

	var fields = $("input[name='ATripid']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Alloted Trip ");
		event.preventDefault();
		return false;
	}
	var cnf = confirm("Are You Sure To Delete!");

	if (cnf) {

		return true;

	} else {
		event.preventDefault();
		return false;
	}

}

function CheckData(){
	
	var driver  = $('#driver').val();
	var vehicle = $('#vehicle').val();
	var vehicle1 = $('#vehicle1').val();
	var place = $('#place').val();
	var stime = $('#stime').val();
	var etime = $('#etime').val();
	var comments = $('#comments').val();
	
	 if (driver==""||driver==null ||driver=="null"){
		alert("Please Select Vehicle Operator!");
		return false;
	}else if (vehicle==""||vehicle==null ||vehicle=="null"){
		alert("Please Select the Vehicle!");
		return false;
	}else if (driver==0 && vehicle==0 && vehicle1==""||vehicle1==null ||vehicle1=="null"){
		alert("Please Enter  Hired Vehicle !");
		return false;
	}else if (place==""||place==null ||place =="null"){
		alert("Please Enter Place !");
		return false;
	}else if(stime=="00:00"){
		alert("Please Select Start Time!");
		return false;
	}else if(etime=="00:00"){
		alert("Please Select Return Time!");
		return false;
	}else if (comments == ""||comments == null ||comments == "null"){
		alert("Please Enter the Comments!");
		return false;
	}else{
		if(confirm("Are you sure to Submit!")){
			$('#tripform').submit();
			return true;
		}else{
			return false;
		}
	}
}

</script>	
	
	
</body>
</html>
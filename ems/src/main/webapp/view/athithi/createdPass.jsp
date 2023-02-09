<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
.wave{
	position: fixed;
	bottom: 0;
	left: 0;
	height: 100%;
	z-index: -1;
}
#wrapper{
		background-image: url("vtsfolder/images/background.png") !important;
		background-repeat: no-repeat;
		background-size:  100%;
		width: 100%;
	}
</style>
</head>
<body style="background-image: url('vtsfolder/images/background.png')">
<%
List<Object[]> passList=(List<Object[]>)request.getAttribute("createdPassList");
String fdate=request.getAttribute("fdate").toString();
String tdate=request.getAttribute("tdate").toString();
SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12" style="top: 10px;">
				<div class="card shadow-nohover">
					<div class="card-header">
						<div class="col-md-12" >
					   			<form class="form-inline" method="post" action="createdPass" >
					   			<h5>Pending Pass List</h5>
								<div class="col-md-4"></div>
								<div class="form-group">
									<label for="fdate">From Date:</label>
									 <input class="form-control" data-date-format="dd/mm/yyyy" id="fdate" name="fdate" required="required" value="<%=fdate %>"  style="margin-left: 5px;">
								</div>
								<div class="form-group">
									<label for="tdate" style="margin-left: 5px;">To Date:</label>
									 <input class="form-control" data-date-format="dd/mm/yyyy" id="tdate" name="tdate" required="required"  value="<%=tdate %>" style="margin-left: 5px;">
								</div>
								<button type="submit" class="btn  btn-sm submit " style="margin-left: 20px;">Submit</button>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
		   					</div>
					</div>
					<div class="card-body">
						<table class="table table-bordered table-hover table-striped table-condensed" id="myTable">
							<thead>
								<tr>
								    <th>SN</th>
								    <th style="width:10%;">Date</th>
									<th>Company Name</th>
									<th style="width:13%;">Visitor Count</th>
									<th >Officer</th>
									<th style="width:5%;" >Print</th>
									<th style="width:9%;" >Out Time</th>
							
								</tr>
							</thead>
							<tbody>
							<%if(passList!=null){ 
							int count=1;
							%>
							<%for(Object[] pass:passList){ %>
								<tr>
									<td><%=count++%></td>
									<td><%=sdf1.format(pass[3])%></td>
									<td><%=pass[4]%></td>
									<td><%=pass[5]%></td>
									<td><%=pass[6]%>&nbsp;(<%=pass[7]%>)</td>
									<td>
										<form action="passPrint" method="get">
											<input type="hidden" name="intimationId" value="<%=pass[1]%>" />
											<input type="hidden" name="passId" value="<%=pass[0]%>" />
											<button type="submit" class="btn" formtarget="_blank">
												<i class="fa fa-print" style="font-size: 1.5em; color: #0779e4; text-align: center;" aria-hidden="true"></i>
											</button>
										</form>
									</td>
									<td>
								        <button class="btn"><i class="fa fa-pencil" style="font-size: 1.5em;" aria-hidden="true" onclick="info(<%=pass[0]%>)"  data-toggle="modal" data-target="#statusFullForm" ></i></button>
								   </td>
								</tr>
								<%}%>
		                      <%}%>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
					<div class="modal fade" id="statusFullForm" role="dialog">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<h4>Visitor List</h4>
								<button type="button" class="close" data-dismiss="modal">&times;</button>
							</div>
							<div class="modal-body">
							  <div class="row">
							    <div class="col-md-12">
							        <table id="addDataTable" class="table table-hover table-striped  table-condensed  table-bordered  ">
                                      <thead>
                                      <tr>
                                        <th>Name</th>
                                        <th style="width:13%;">Batch No</th>
                                        <th style="width:15%;">In Time</th>
                                        <th style="width:18%;">Out Time</th>
                                      </tr>
                                      </thead>	
                                      <tbody id="visitorList">
                                        
                                      </tbody>						        
							        </table>
							    </div>
							  </div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
				function info(passId){
					var y='';
					$.ajax({
						
						type : "GET",
						url : "passVisitorList",
						data : {
							passId : passId 
						},
						datatype : 'json',
						success : function(result) {
							var values = JSON.parse(result);
							for(i = 0; i < values.length; i++){
					
								var time=values[i][3];
								var stime=time.toString();
								stime=stime.substring(13, 18);
								if(values[i][4]!=null){
									var time1=values[i][4];
									var stime1=time1.toString();
									stime1=stime1.substring(13, 18);
									y=y+'<tr><td>'+values[i][5]+' ('+values[i][6]+')</td><td>'+values[i][2]+'</td><td>'+stime+'</td><td>'+stime1+'</td></tr>'
								}else{
						        y=y+'<tr><td>'+values[i][5]+' ('+values[i][6]+')</td><td>'+values[i][2]+'</td><td>'+stime+'</td><td id="sub'+values[i][0]+'"> <button class="btn" onclick=submitPass('+values[i][0]+')  ><i class="fa fa-upload" style="color:#61b15a;" aria-hidden="true"></i></button> </td></tr>'
								}
								}
							$("#visitorList").html(y);
						}
						});	
					
					
				}
				
				
				function submitPass(passEmpId){
					  if (confirm('Do want to Submit')) {
					$.ajax({
						
						type : "GET",
						url : "passVisitorSubmit",
						data : {
							passEmpId : passEmpId 
						},
						datatype : 'json',
						success : function(result) {
							var values = JSON.parse(result);
							var time1='';
							var d = new Date();  
						    time1=time1+d.getHours(); 
						    time1=time1+':';
						    time1=time1+d.getMinutes(); 
						    $("#sub"+passEmpId).html(time1);
							
						}
						});	
					  }
					
				}
				</script>

<script>

$( "#fdate" ).change( function(){
    
	$( "#tdate" ).daterangepicker({
		"singleDatePicker" : true,
	    "linkedCalendars" : false,
	    "showCustomRangeLabel" : true,
	    "minDate" :$("#fdate").val(),  
	    "cancelClass" : "btn-default",
	    showDropdowns : true,
	    	locale : {
	    	format : 'DD-MM-YYYY'
	    } 
	});

	
});

$( "#fdate" ).daterangepicker({
    "singleDatePicker" : true,
    "linkedCalendars" : false,
    "showCustomRangeLabel" : true,
    "minDate" :new Date(),  
    "cancelClass" : "btn-default",
    showDropdowns : true,
    locale : {
    	format : 'DD-MM-YYYY'
    }
});

        
$("#tdate").daterangepicker({
	"singleDatePicker" : true,
    "linkedCalendars" : false,
    "showCustomRangeLabel" : true,
    "minDate" :$("#fdate").val(),  
    "startDate" : $("#fdate").val(),
    "cancelClass" : "btn-default",
    showDropdowns : true,
    	locale : {
    	format : 'DD-MM-YYYY'
    } 
});




</script>
  

</body>


</html>
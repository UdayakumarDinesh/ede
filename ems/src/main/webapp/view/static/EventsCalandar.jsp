<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<spring:url value="/webresources/addons/EventCalandar/zabuto_calendar.min.css" var="EventCalanderCss" />
<link href="${EventCalanderCss}" rel="stylesheet" />
	 
<spring:url value="/webresources/addons/EventCalandar/zabuto_calendar.js" var="EventCalanderjs" />
<script src="${EventCalanderjs}"></script>
 <%List<Object[]> EventTypes = (List<Object[]>)request.getAttribute("CalandarEventType"); %>


<%-- <style type="text/css">

<%for(Object[] etype : EventTypes){%>

.event-color-<%=etype[1]%>
{
	background-color: <%=etype[3]%>;
}
<%}%>

</style> --%>

<style>


table.lightgrey-weekends
{
	height: 55vh;
}

table.event-calander thead th, 
.zabuto-calendar__navigation__item--prev, 
.zabuto-calendar__navigation__item--next, 
.zabuto-calendar__navigation__item--header
{
	color: black;
}


</style>

</head>

<body>
<%
	List<Object[]> CalandarEventList = (List<Object[]>)request.getAttribute("CalandarEvents");
	String year = (String)request.getAttribute("year");
	String month = (String)request.getAttribute("month");
	HashMap<LocalDate, ArrayList<Object[]>> eventMap=new LinkedHashMap<LocalDate, ArrayList<Object[]>>(); 
	
	CalandarEventList.stream()
	.forEach(event -> {if(eventMap.get(LocalDate.parse(event[2].toString()))==null) 
						{ 	ArrayList<Object[]> EventValueList = new ArrayList<>();
							EventValueList.add(event);
							eventMap.put(LocalDate.parse(event[2].toString()), EventValueList);
						}
						else {
							
							ArrayList<Object[]> EventValueList = eventMap.get(LocalDate.parse(event[2].toString()));
							EventValueList.add(event);
							eventMap.put(LocalDate.parse(event[2].toString()), EventValueList);
						}
	
		});

%>
	<div class="card-header page-top" style="padding: 0.25rem 1.25rem;">
		<div class="row">
			<div class="col-md-3">
				<h5 style="padding-top: 0.5rem">Calandar</h5>
			</div>
			<div class="col-md-9"></div>
		</div>
	</div>


	<div class="card dashboard-card">
		<div class="card-body ">
			<div align="center">
				<% String ses=(String)request.getParameter("result"); 
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
			<div class="container-fluid" style="padding: 0px;">

				<div class="row">
					<div class="col-md-3 event-list-table" align="center"  style="height:78vh;overflow: auto;">
						<span style="font-weight: bold;">Calandar Event For the Year  <span style="color: red"><%=year %></span></span>
						<table class="table table-bordered table-hover table-striped table-condensed" >
							<thead>
								<tr>
									<th style="border: 1px solid black;text-align: center;width: 100px;">Date</th>
									<th style="border: 1px solid black;">Event / Holiday</th>
								</tr>
							</thead>
							<tbody >
								<%	int count=0;
										for(Object[] event :CalandarEventList){ 
										 %>
											<tr class="event-row" id="event-row-<%=event[0]%>" >
												<td  style="border: 1px solid black;text-align: center;center;width: 100px;vertical-align: middle;"><%=DateTimeFormatUtil.SqlToRegularDate(event[2].toString()) %></td>
												<td style="border: 1px solid black;"><%=event[3] %></td>
											</tr>
								<%} %>
							</tbody>
						</table>
					</div>
					<div class="col-md-9">
						<div id="demo-calendar" style="width: 100%;" ></div>
						<div class="row" style="margin-top: 5px;">
							<div class="col-md-12">
								<div  id="event-description" style="padding:5px;width: 100%;height: 22vh;border:1px solid black;padding:5px;border-radius: 5px;overflow: auto; "></div>
							</div>
						</div>
					</div>
				</div>
				

			</div>
		</div>

	</div>
<form action="Calandar.htm" method="post" id="calandar-form"> 
<input type="hidden" name="year" value="<%=year %>" id="cal-year">
<input type="hidden" name="month" value="<%=month %>" id="cal-month">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<script>
$(document).ready(function () {
  $("#demo-calendar").zabuto_calendar({
	
    classname: 'table table-bordered lightgrey-weekends event-calander table clickable',
    year : <%=year%>,
    month : <%=month%>,
    events: [
      	
    	<% boolean today =true;
	    	for(Map.Entry<LocalDate, ArrayList<Object[]>> DateEvent : eventMap.entrySet()){
				int flag=0;
				for(Object[] event : DateEvent.getValue()){ %>
				{
	    			"eventid":<%=event[0]%>,
		            "description" : "<%=event[4]%>",
		            "color":  "<%=event[6]%>",
		            "FormatedDate" : "<%=DateTimeFormatUtil.SqlToRegularDate(event[2].toString()) %>",
		            "eventName": "<%=event[3]%>",
		            
		            "date": "<%=event[2]%>",
			        <%-- "classname": "event-color-<%=event[1]%>", --%>
						
			        "datebgcolor" : "<%=event[6]%>",
			        
		            <%if(LocalDate.now().equals(LocalDate.parse(event[2].toString()))){
		            	today =false;
		            %>
		            "markup": "<div class=\"today-badge rounded-pill bg-success\" style=\"color:#FFFFFF !important;\">[day]</div>",
		            <%}%>
			        
	         	},
    		
    			<%}%>
    		<%}%>
    	<%if(today){%>
	    	{
	    		"eventid" : 0 ,
	            "date": "<%=LocalDate.now() %>",
	            "markup": "<div class=\"today-badge rounded-pill bg-success\" style=\"color:#FFFFFF !important;\">[day]</div>"
	     	}
    	
    	<%}%>
    	
    	
      ]
    
  });
});

$('#demo-calendar').on('zabuto:calendar:day', function (event) {
	if(event.hasEvent && event.eventdata.events[0].eventid>0 ){
		var edata = event.eventdata.events;
    	
    	$('.event-row').css("background-color", "");
    	
    	var descriptionHtml = '';
    	for(var i=0;i<edata.length;i++)
    	{
    		$('#event-row-'+edata[i].eventid).css("background-color", edata[i].color);
    		
    		descriptionHtml += '<div class="event-div"> <span style="font-weight:bold;border-radius:3px;padding:2px 3px;background-color:'+edata[i].color +'">' + edata[i].FormatedDate + " - "+  edata[i].eventName + ' :</span>';
    		descriptionHtml += '<br><p style="text-indent: 2em;">' + edata[i].description + '</p></div>';
    	}
    	
    	$('#event-description').html(descriptionHtml);
    	
        $('.event-list-table').animate({
            scrollTop: $('#event-row-'+edata[0].eventid).offset().top - 200},100
        );
    	
    }else{
    	$('#event-description').html('');
    	
    }
});



$('#demo-calendar').on('zabuto:calendar:goto', function (e) {
	
	var  cyear = <%=year%>;
	if(cyear != e.year){
		$('#cal-year').val(e.year);
		$('#cal-month').val(e.month);
		$('#calandar-form').submit();
	}
});


</script>
</body>
</html>
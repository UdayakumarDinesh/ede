<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style>
.table thead tr th{vertical-align:middle; text-align: center;  }
.table tbody tr td{vertical-align:middle; text-align: center; }

.label-sanc{
  background-color:#006400;
}
 html, body {
    margin: 0;
    padding: 0;
    font-family: Helvetica, sans-serif;
  }
  body {
    background: #25303B;
  }
  section#timeline {
    width: 80%;
    margin: 20px auto;
    position: relative;
  }
  section#timeline:before {
    content: '';
    display: block;
    position: absolute;
    left: 50%;
    top: 0;
    margin: 0 0 0 -1px;
    width: 2px;
    height: 100%;
    background:black;
  }
  section#timeline article {
    width: 100%;
    margin: 0 0 20px 0;
    position: relative;
  }
  section#timeline article:after {
    content: '';
    display: block;
    clear: both;
  }
  section#timeline article div.inner {
    width: 40%;
    float: left;
    margin: 5px 0 0 0;
    border-radius: 6px;
  }
  section#timeline article div.inner span.date {
    display: block;
    width: 70px;
    height: 65px;
    padding: 5px 0;
    position: absolute;
    top: 0;
    left: 50%;
    margin: 0 0 0 -32px;
    border-radius: 100%;
    font-size: 12px;
    font-weight: 900;
    text-transform: uppercase;
    background: #25303B;
    color: rgba(255,255,255,255);
   /*  border: 2px solid rgba(255,255,255,0.2); */
    box-shadow: 0 0 0 7px #5c6166;
  }
  section#timeline article div.inner span.date span {
    display: block;
    text-align: center;
  }
  section#timeline article div.inner span.date span.day {
    font-size: 12px;
  }
  section#timeline article div.inner span.date span.month {
    font-size: 13px;
  }
  section#timeline article div.inner span.date span.year {
    font-size: 9px;
  }
  section#timeline article div.inner h2 {
    padding: 10px;
    margin: 0;
    color: #fff;
    font-size: 13px;
    text-transform: uppercase;
    letter-spacing: 0px;
    border-radius: 6px 6px 0 0;
    position: relative;
    font-family: 'Muli',sans-serif;
    height: 31px;
  }
  section#timeline article div.inner h2:after {
    content: '';
    position: absolute;
    top: 20px;
    right: -5px;
      width: 10px; 
      height: 10px;
    -webkit-transform: rotate(-45deg);
  }
  section#timeline article div.inner p {
    
    padding-top:5px;
    padding-left:15px;
    padding-bottom:10px;
    margin: 0;
    font-size: 14px;
    background: #fff;
    color: #656565;
    border-radius: 0 0 6px 6px;
    font-family: 'Lato',sans-serif;
  }
  section#timeline article:nth-child(2n+2) div.inner {
    float: right;
  }
  section#timeline article:nth-child(2n+2) div.inner h2:after {
    left: -5px;
  }
  section#timeline article:nth-child(odd) div.inner h2 {
    background: #bd493e;
  }
  section#timeline article:nth-child(odd) div.inner h2:after {
    background: #bd493e;
  }
  section#timeline article:nth-child(even) div.inner h2 {
    background: #9da61e;
  }
  section#timeline article:nth-child(even) div.inner h2:after {
    background: #9da61e;
  }
/* timeline customization */

.remarks_title{
	font-size: 12px;
	font-weight: 800;
	color:black;
}
</style>
</head>
<body >
<%SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy"); 
SimpleDateFormat month=new SimpleDateFormat("MMM");
SimpleDateFormat day=new SimpleDateFormat("dd");
SimpleDateFormat year=new SimpleDateFormat("yyyy");
SimpleDateFormat time=new SimpleDateFormat("HH:mm");
List<Object[]> statuslist = (List<Object[]>)request.getAttribute("trans");
%>
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>LEAVE STATUS</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item"> <a href="LeaveStatusList.htm">Leave Status List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Leave Status</li>
					</ol>
				</div>
			</div>
	</div>	

	 <div class="page card dashboard-card">
	

	      <section id="timeline">
	      
	       <% int count=1;
			 for(Object[] object:statuslist){
			 %>
	      
			  <article>
			    <div class="inner">
			      <span class="date">
			        <span class="day"><%=day.format(object[3]) %></span>
			        <span class="month"><%=month.format(object[3]) %></span>
			        <span class="year"><%=year.format(object[3]) %></span>
			      </span>
			      <h2><%=object[0] %> at <%=time.format(object[3]) %></h2> 
				  <p style="background-color:  #f0f2f5;">
				  <span class="remarks_title"> <%=object[5] %> : </span>
				  				<%=object[1] %>, <%=object[2] %><br>
				  	<%if(object[4]!= null){%>
				  		<span class="remarks_title">Remarks : </span>
				  				<%=object[4] %>
				  						<%}else{ %> 
				  							<span class="remarks_title">No Remarks </span> 
				  								<%} %>
				  </p>
			    </div>
			  </article>
			  
			<%count++;}%> 		
		</section>
		
</div>
   

</body>
</html>
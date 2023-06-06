<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate"%>  
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<% 
   Object[] obj=(Object[])request.getAttribute("NOCHigherEducationDetails");
%>
<style type="text/css">

			.break {
				page-break-after: always;
			}

			#pageborder {
				position: fixed;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
				border: 2px solid black;
			}

			@page {
				size: 790px 950px;
				margin-top: 49px;
				margin-left: 72px;
				margin-right: 39px;
				margin-buttom: 49px;
				border: 2px solid black;

				@bottom-right {
					counter-increment: page;
  					counter-reset: page 2;
					content: "Page "counter(page) " of "counter(pages);
					margin-bottom: 30px;
					margin-right: 10px;
				}

				@top-right {
					content: "";
					margin-top: 30px;
					margin-right: 10px;
				}

				@top-left {
					margin-top: 30px;
					margin-left: 10px;
					
				}

				@top-center {
					margin-top: 30px;
					content: "";

				}

				@bottom-center {
					margin-bottom: 30px;
					content: "";
				}
			}

p {
	text-align: justify;
	text-justify: inter-word;
}
body
{
	font-size:14px !important;
}
div
{
	width: 650px !important;
}
table{
	align: left;
	width: 650px !important;
	max-width: 650px !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	margin-left:10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
	
	 -ms-word-break: break-all;
     word-break: break-all;

     /* Non standard for WebKit */
     word-break: break-word;

-webkit-hyphens: auto;
   -moz-hyphens: auto;
        hyphens: auto;
	
}
.center{

	text-align: center;
}

.right
{
	text-align: right;
}


.text-blue
{
	color: blue;
	font-weight:500px;
	font-size: 16px;
}

</style>
<meta charset="ISO-8859-1">
<title>NOC Higher Education</title>
</head>
<body>

  <% 

  SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
  SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
  LocalDate date = LocalDate.now(); 
  //Object[] emp=(Object[])request.getAttribute("P&ANameDesig");
  List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
  Object[] emp = (Object[])request.getAttribute("EmpData");
  

  %>

<div style="text-align:center;margin-top:10px;">
         <div align="left" style="margin-left:10px !important;width:45%;" >Ref:STARC/P&A/PERS(<%=obj[0] %>):-<%=obj[15] %></div>
             <div align="right" style="margin-top:-20px;margin-right:10px;" >Date:&nbsp;<span class="text-blue"><%=rdf.format(sdf.parse(date.toString())) %></span></div>
            <br>
            <br>
            
		    <div align="left" style="margin-left:10px;">
		    
		                        <span style="font-size: 15px;"><%=obj[1] %> <%=obj[2] %></span><br>
								<span style="font-weight: 400; font-size: 15px; ">EmpNo : <%=obj[0] %></span><br>
								<span style="font-weight: 400; font-size: 15px; "><%=obj[3] %></span><br>
		                        <span style="font-weight: 400; font-size: 15px; ">STARC, Bengaluru</span><br>
		    
		    </div> 
	
	    <h3 style="text-align: center;text-decoration: underline;margin-top:40px;">Permission for pursuing <span class="text-blue" style="font-weight:600"><%=obj[11] %></span> from  <span class="text-blue"  style="font-weight:600"><%=obj[9] %></span> </h3>
	
	      <div style="margin-left: 10px;margin-top:40px;text-align: justify;width:650px; text-justify: inter-word;font-size: 16px;line-height:1.7" align="left">
				
		    Please refer your application dated  <span class="text-blue"><%=rdf.format(sdf.parse(obj[20].toString())) %></span> on the subject cited above.<br>
            The Component Authority has granted permission for pursuing   <span class="text-blue"><%=obj[11] %></span>   in  <span class="text-blue"><%=obj[12] %></span> from  <span class="text-blue"><%=obj[9] %></span> through
		    <span class="text-blue"><%=obj[13] %> </span> subject to the declaration provided in your application.
		
	</div>
	
	<br>
		<br>
		<br>
		 <% if(PandAs.contains(emp[0])){ %>
		 <div style="margin-left:10px !important;margin-top:20px;text-align:left;"> 
						        <span class="text-blue" style="text-transform: uppercase;" ><%=emp[1]%></span><br>
								<span style="font-weight: 400; font-size: 15px; "><%=emp[2]%></span><br>
								<span style="font-weight: 400; font-size: 15px; ">for CEO,STARC</span><br>
								
		 </div>
	
	<%} %> 
	</div>


</body>
</html>
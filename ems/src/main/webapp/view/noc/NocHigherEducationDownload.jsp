<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%
Object[] obj=(Object[])request.getAttribute("NOCHigherEducationDetails");
%>
<head>
<meta charset="ISO-8859-1">
<style type="text/css">


			
			#pageborder {
				position: fixed;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
				border: 2px solid black;
			}

			@page {
				size: 790px 1050px;
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
					content: "<%=obj[14]%>";
				}

				@top-left {
					margin-top: 30px;
					margin-left: 10px;
					content: "Emp No: <%=obj[0]%>";
				
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
</style>

</head>
<body>

<% 

     
      String LabLogo = (String)request.getAttribute("lablogo");
      int slno=0;
%>


            <div class="center">

				 <div style="width: 100%;float:left;">
		         <div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 70px; height: 80px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
                 <div style="width: 90%; height: 75px; border: 0; text-align: center;"><h3 style="">NOC FOR HIGHER EDUCATION </h3></div>
		       </div>
		       
	        <br>
	        <br>
							<table style="border: 0px; width: 100%;">
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Name and Designation</td>
									<td colspan="2" style="color: blue;"><%=obj[2] %>  -  <%=obj[3] %>
									</td>
									
								</tr>
								
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Emp.No.</td> 
									
									<td colspan="2" style="color: blue;"><%=obj[0] %></td>
								</tr>
								
								<tr>
									<td style="border-bottom: 0;width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: center">Level in the Pay Matrix</td>
									<td style="width:20%;text-align: center">Grade</td>
									<td style="width:20%;text-align: center">Basic Pay</td>
									
								
								</tr>
								<tr>
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[4] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[6] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[5] %></td>
								
								   
								</tr>
								
								<tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td>Educational Qualification</td>
								   <td colspan="2" style="color: blue;"><%=obj[7] %></td>
								 </tr>
								 
								<tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td >Institution Type</td>
								   <td colspan="2" style="color: blue;"><%=obj[8] %></td>
								 </tr>
								  
								<tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td >Academic Year</td>
								   <td colspan="2" style="color: blue;"><%=obj[9].toString().substring(0,4) %></td>
								 </tr>
								 
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Name of Course</td>
								 	<td colspan="2" style="color: blue;"><%=obj[10] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Specialization</td>
								 	<td colspan="2" style="color: blue;"><%=obj[11] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Education Type</td>
								 	<td colspan="2" style="color: blue;"><%=obj[12] %></td>
								</tr> 
								
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Essential Education Qualification</td>
								 	<td colspan="2"style="color: blue;"><%=obj[13] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td colspan="3" style="width:20%;text-align: left">Declaration:
									
									<br>
									
									
									a)&nbsp; All expenses for the course shall be borne by me.<br>
									b)&nbsp;  No official / special leave /flexible working hours will use for attending classes
									
									    or for appearing in examinations / Projects etc.<br>
									
									c)&nbsp;  I will not refuse any official duty / outstation duty during the period.<br>
									
									d)&nbsp;  Pursuing the course will not hamper date -to-day official activities.<br>
									
									e)&nbsp;  Publication of research papers ,if any will be with the prior approval of CEO.<br>
									
									f)&nbsp;&nbsp; I will not utilize Society resources for research purposes.<br>
									
									g)&nbsp; I will obtain prior permission for visiting abroad for course work.<br>
									
									
									</td>
								 	
								</tr> 
								 
						</table>
								
								
				</div>				
					







</body>
</html>
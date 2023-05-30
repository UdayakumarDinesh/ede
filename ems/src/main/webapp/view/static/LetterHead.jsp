
<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Letter Head</title>

<style>


</style>
</head>
<body>


<%


    LabMaster Labdetails=(LabMaster)request.getAttribute("Labmaster");
    //System.out.println("labname---"+Labdetails.getLabName());
    String lablogo=(String)request.getAttribute("lablogo");

%>
<div align="center">
		<table style="margin-left:10px; margin-top:15px;width:660px; border-collapse: collapse;width: 660px;">
			<tr>
				<td style="text-align:center; height:50px;border: 1px solid black;" width="100px"  ><div
					style="font-size: 40px;  font-style: italic;"><img style="width: 80px; height: 80px; margin-top: -50px;margin-left: 10px;" align="left"   <%if(lablogo!=null ){ %> src="data:image/*;base64,<%=lablogo%>" alt="Configuraton"<%}else	{ %> alt="File Not Found" <%} %>></div></td>
				<td style="text-align:center; height:50px;border: 1px solid black;" width="800px" ><span style="font-weight:bold;font-size:14px; text-transform: uppercase;"><%=Labdetails.getLabName()%></span>
				 

				 <!-- <div align="left">&nbsp;<span style="font-weight:bold;">(A Government of India Society)</span><br>&nbsp;Vijinapura Road,<br>&nbsp;Dooravani Nagar P.O.,<br>&nbsp;Bangalore-560016 <br>&nbsp;Karnataka,INDIA</div>  -->
			
				<div align="left" style=""> 
				
						        <span style="font-weight:bold;" >&nbsp;(A Government of India Society)</span>
								<div style="margin-top: 10px;">
								<span style="font-weight: 400;">&nbsp;Vijinapura Road,</span><br>
								<span style="font-weight: 400;">&nbsp;Dooravani Nagar P.O.,</span><br>
								<span style="font-weight: 400;">&nbsp;Bangalore-560016</span><br>
								<span style="font-weight: 400;">&nbsp;Karnataka,INDIA</span></div>
		        </div>
		        
				<div style="margin-top:-70px;margin-left:280px !important;"align="left"> 
						        <span >Phone &nbsp;&nbsp;: +91-080-2565 9802 Extn : 9809</span><br>
								<span style="font-weight: 400;">Direct &nbsp;&nbsp;: +91-80-2565 0356</span><br>
								<span style="font-weight: 400;">Telefax : +91-80-2565 0712</span><br>
								<span style="font-weight: 400;">e-mail &nbsp;&nbsp;: <span style="text-decoration: underline;"> <%=Labdetails.getLabEmail() %></span></span>
		        </div>
		        
		       
				<!-- <div style="margin-top:-80px;"align="right">Phone : +91-080-2565 9802 Extn : 9809&nbsp;<br>Direct : +91-80-2565 0356 &nbsp;<br>Telefax : +91-80-2565 0712 &nbsp;<br>e-mail : <span  style="text-decoration: underline;">sitarpurchase.sitar@gov.in</span>&nbsp;</div>  -->
				</td>
				
				
			</tr>
			
		</table>
		
		</div>



</body>
</html>
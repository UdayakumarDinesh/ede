
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDate"%>   
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/LetterHead.jsp"></jsp:include>


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
				size: 790px 1120px;
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
					content: "";
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
 

.square{
  background-color: white;
  width: 70px;
  height:70px;
  border: 1px solid black;
  padding: 20px;
  margin-left: 500px;
  align:right;

}


.text-blue
{
	color: blue;
	font-weight:400px;
}
/* table {
	border-collapse: collapse;
	
	width: 620px;
}

.table tr, th, td {
	border: 1px solid black;
	padding: 0px;
}
.table tr{
	height: 10px;
} */
 
</style>

<title>NOC Passport Certificate</title>
</head>
<body>


	<%
	
	   Object[] obj= (Object[])request.getAttribute("NocPassportDetails");
	   SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
       LocalDate date = LocalDate.now(); 
       Object[] title = (Object[])request.getAttribute("Emptitle");
    
	%>

<!-- <div align="center">
		<table style="margin-left:10px; margin-top:15px;width:650px;">
			<tr>
				<td style="text-align:center;" width="110px"  ><span style="font-size: 10px;margin-left:90px;">TM</span><span
					style="font-size: 35px; font-style: italic;"> SITAR </span></td>
				<td style="text-align:center;" width="600px"  ><span style="font-weight:bold;">SOCIETY FOR INTEGRATED CIRCUIT TECHNOLOGY AND APPLIED RESEARCH</span>
				 
				<br>
				<br>
				
				<div align="left">&nbsp;<span style="font-weight:bold;">(A Government of India Society)</span><br>&nbsp;Vijinapura Road,<br>&nbsp;Dooravani Nagar P.O.,<br>&nbsp;Bangalore-560016 <br>&nbsp;Karnataka,INDIA</div> 
				<div style="margin-top:-80px;"align="right">Phone : +91-080-2565 9802 Extn : 9809&nbsp;<br>Direct : +91-80-2565 0356 &nbsp;<br>Telefax : +91-80-2565 0712 &nbsp;<br>e-mail : <span  style="text-decoration: underline;">sitarpurchase.sitar@gov.in</span>&nbsp;</div> 
				
				</td>
				
			</tr>
			
		</table>
		</div> -->
      

       <div style="text-align:center;margin-top:60px;">
         <div align="left" style="margin-left:10px !important;" >Ref:STARC/P&A/PERS(<%=obj[0] %>):-<%=obj[11] %></div>
            <div class="square" style="text-align:center">Applicant photo</div>
            <br>
            
		      <h3 style="text-align: center;text-decoration: underline;">NO OBJECTION CERTIFICATE</h3>
	
	
	      <div style="margin-left: 10px;text-align: justify;width:650px; text-justify: inter-word;font-size: 18px;" align="left">
				
		Mr.&nbsp;<span class="text-blue" style="text-decoration: underline;"><%=obj[1] %></span> son of / Daughter of  Mr. <%if(obj[35]!=null){ %> <span class="text-blue" style="text-decoration: underline;"><%=obj[35] %></span><%} else{ %><span class="text-blue"> NA </span> <%} %>&nbsp;who is an Indian national,is employed in this office 
          as from &nbsp;<span class="text-blue"style="text-decoration: underline;font-size:16px;" ><%=rdf.format(sdf.parse(obj[21].toString())) %></span>&nbsp; till <span class="text-blue" style="text-decoration: underline;font-size:16px;"> <%=rdf.format(sdf.parse(obj[22].toString())) %></span> &nbsp;date.This office has no objection for obtaining his Passport.	   			
			   			
				   																			
	</div>
		
		<br>
		 
		 <div align="left" style="margin-left:10px !important;" >Date:&nbsp;<span class="text-blue"><%=rdf.format(sdf.parse(date.toString())) %></span></div>
		 	
		<br>
		<br>
		
		 <div style="margin-right:60px !important;margin-top:20px;text-align:right;"> 
						        <span class="text-blue" style=" text-transform: uppercase;"><%=obj[33] %></span><br>
								<span style="font-weight: 400; font-size: 18px; ">Dy.Manager-Personnel & Administration</span><br>
								<span style="font-weight: 400; font-size: 18px;">TelNo.<%=obj[38] %>/<%=obj[39] %>(Extn-<%=obj[36] %></span>)<br>
								<span style="font-weight: 400; font-size: 18px;">E-Mail id:&nbsp;<span style="text-decoration: underline;"><%=obj[37] %></span></span><br>
		 </div>
	   
	
</div>
		
		

</body>
</html>
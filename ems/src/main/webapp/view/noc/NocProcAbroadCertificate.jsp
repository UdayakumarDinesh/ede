<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate"%>  
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

<title>NOC Proceeding Abroad</title>
</head>
<body>

    <%
       SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
       Object[] obj=(Object[])request.getAttribute("NocProcAbroadDetails");
       LocalDate date = LocalDate.now(); 

    %>

     <div style="text-align:center;margin-top:60px;">
         <div align="left" style="margin-left:10px !important;" >Ref:STARC/P&A/PERS(<%=obj[0] %>):-<%=obj[17] %></div>
            <div class="square" style="text-align:center">Applicant photo</div>
            <br>
            
		      <h3 style="text-align: center;text-decoration: underline;">NO OBJECTION CERTIFICATE  </h3>
	
	
	      <div style="margin-left: 10px;text-align: justify;width:650px; text-justify: inter-word;font-size: 18px;" align="left">
				
		Mr.&nbsp;<span class="text-blue" style="text-decoration: underline;"><%=obj[1] %></span>,&nbsp;EmpNo.<span class="text-blue" style="text-decoration: underline;"><%=obj[0] %></span>
		working as <span class="text-blue" style="text-decoration: underline;"><%=obj[2] %></span> in STARC is permitted to visit from <span class="text-blue" style="text-decoration: underline;"><%=rdf.format(sdf.parse(obj[30].toString())) %></span> to  <span class="text-blue" style="text-decoration: underline;"><%=rdf.format(sdf.parse(obj[33].toString())) %></span>
		as a Student for <span class="text-blue" style="text-decoration: underline;"><%=obj[31] %></span> subject to the following conditions:<br>
         
         <br>
		a) He should not take up any appointment or undergo any training/seminar/workshop/conference other than the one specified ,during his stay abroad without prior permission.
		<br>
		b) That STARC will not be made liable for any expenditure including travel expenditure etc. in connection with his trip abroad. 			
		<br>
		c) He should not tender resignation of the post held by him in STARC while abroad and his resignation for the appointment if tendered while abroad will not be accepted on any account.
		<br>
		d) He should not canvas or seek any business while abroad
		<br>
		e) He should surrender his STARC Identity Card before proceeding abroad
		<br>
		This No Objection Certificate is valid for a period of six months from the date of issue .It is issued in connection with the purpose indicated above and should not be used for any other purpose.
		
		
				   																			
	</div>
		
		<br>
		 
		 <div align="left" style="margin-left:10px !important;" >Date:&nbsp;<span class="text-blue"><%=rdf.format(sdf.parse(date.toString())) %></span></div>
		 	
		<br>
		<br>
		
		 <div style="margin-right:60px !important;margin-top:20px;text-align:right;"> 
						        <span class="text-blue" style=" text-transform: uppercase;"><%=obj[53] %></span><br>
								<span style="font-weight: 400; font-size: 18px; ">Dy.Manager-Personnel & Administration</span><br>
								<span style="font-weight: 400; font-size: 18px;">TelNo.<%=obj[64] %>/<%=obj[65] %>(Extn-<%=obj[62] %></span>)<br>
								<span style="font-weight: 400; font-size: 18px;">E-Mail id:&nbsp;<span style="text-decoration: underline;"><%=obj[63] %></span></span><br>
		 </div>
	   
	
</div>
		




</body>
</html>
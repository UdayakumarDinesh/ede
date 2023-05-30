<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<% 

    Object[] obj=(Object[])request.getAttribute("NocProcAbroadDetails");
     
%>
<head>
<meta charset="ISO-8859-1">



<style type="text/css">
/* @media print {
	#printPageButton {
		display: none;
	}
}
 */
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
					content: "<%=obj[17]%>";
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
 
table {
	border-collapse: collapse;
	font-weight: bold;
	width: 620px;
}

/* tr, th, td {
	border: 2px solid black;
	
}
 */
td{
    word-break: break-word;
	overflow-wrap: anywhere;

}
  .text-center {
	text-align: center;
} 

/* .declare {
	width: 620px;
	margin-top: 25px;
	text-align: justify;
}

.decDiv {
	display: flex;
	justify-content: space-between;
	font-weight: bold;
	margin-top: 60px;
} */

.note {
	width: 620px;
	margin-top: 30px;
}

.table3 tr, th, td {
	border: 1px solid black;
	padding: 0px;
}
.table3 tr{
	height: 10px;
}

.table3 td {
	font-weight: normal;
}
.accDPT{
	margin-top: 30px;
}
.table4 tr, th, td {
	border: 1px solid black;
	padding: 5px;
}
.table4 td {
	font-weight: normal;
}

.table4 td:first-child:not(.exlude){
	width: 45%;
}
.sign{
	width: 620px;
	text-align: right;
	margin-top: 60px;
} 
.break
	 {
	  	page-break-before:always;
	 }
.text-blue
{
	color: blue;
	font-weight:400px;
}
.break1
	 {
	  	page-break-after:always;
	 }	 
</style>

<title>NOC Proceeding Abroad </title>
</head>
<body>


	<%
	
	   SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
       String LabLogo = (String)request.getAttribute("lablogo");
       Object[] Ceoname=(Object[])request.getAttribute("CeoName");
    
	%>


	<div align="center">
		<table style="margin-left:10px; margin-top:15px;width:650px;">
			<tr>
				<td class="text-center" rowspan="2">
					<img style="width: 70px; height: 60px;  margin-top: -30px;margin-left: 10px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
				<td class="text-center" width="300px" rowspan="2">APPLICATION FOR OBTAINING NO OBJECTION CERTIFICATE FOR PROCEEDING ABROAD  <br>
				
				</td>
				<td>STARC -<%=obj[17] %>
				</td>
				
			</tr>
			<tr>
				<td>Date of Issue : <% if (obj[54]!=null){%><%=rdf.format(sdf.parse(obj[54].toString())) %><%} %>
				</td>
				
			</tr>
		</table>
		</div>

       <div style="text-align:center;">
		<h4 style="text-align: center;margin-top:5px;">PART - I </h4>
		<table style="margin-top: -10px;margin-left:15px;width:650px;">	
									<tbody>
										
										<tr>
											<th style="width:250px;">1.Name</th>
											<th style="width:0px;">2.EmpNo</th>
											<th style="width:250px;">3.Designation</th>
											<th style="width:50px;">4.Department</th>
										</tr>
									<tr>
										
											
											<td class="text-blue" style="text-transform: uppercase;text-align:left;"><%=obj[1] %></td>
											<td class="text-blue" ><%=obj[0] %></td>
											<td class="text-blue" style="text-align:left;" ><%=obj[2] %></td>
											<td class="text-blue" ><%=obj[3] %></td>
									</tr>
										
									</tbody>
								</table>
								
	<table style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:650px;">
	
	<%-- <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>1. Name (in BLOCK LETTERS) <span style="margin-left:80px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;text-align: left; text-transform: uppercase;"><%=obj[1] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>2. Designation  <span style="margin-left:207px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;text-align: left;"><%=obj[2] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>3. EmpNo  <span style="margin-left:236px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;text-align: left;"><%=obj[0] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>4. Department / Group  <span style="margin-left:147px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;text-align: left;"><%=obj[3] %></td>
	</tr> --%>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding:-15px;"><h4>&nbsp;&nbsp;5. Applicants Residential Address  </h4></td>
	<td  style="border: 0;padding:-15px;"></td>
	</tr>
	
	<tr>
	<td style="width:0px;border: 0;text-align: left;padding:-12px;"><h4>&nbsp;&nbsp;(a) Present &nbsp; &nbsp;&nbsp; &nbsp;:</h4></td>
	<td class="text-blue" style="border: 0;text-align: justify;padding:-12px;"><span style="margin-left:-165px"><%=obj[4] %>,<%=obj[5] %>,<%=obj[6] %>,<%=obj[7] %></span></td>
	</tr>
	
	<tr>
	<td style="width:0px;border: 0;text-align: left;padding:-12px;"><h4>&nbsp; (b) Permanent : </h4></td>
	<td class="text-blue" style="border: 0;text-align: justify;padding:-12px;"><span style="margin-left:-165px"><%=obj[5] %>,<%=obj[9] %>,<%=obj[10] %>,<%=obj[11] %></span></td>
	</tr>
	
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding:-15px;"><h4>&nbsp;&nbsp;6. Detail of <% if(obj[18].toString().equalsIgnoreCase("F")){%>Father<%} else if(obj[18].toString().equalsIgnoreCase("H")){ %> Husband<%} 
	else if (obj[18].toString().equalsIgnoreCase("G")){ %> Guardian <%} %> <br> 
       </h4></td>
      
    <td style="border: 0;padding:-15px;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;text-align: left;padding:-15px"><h4>&nbsp;  (a) Name <span style="margin-left:45px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding:-15px"><span style="margin-left:-150px"><%=obj[19] %></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;text-align: left;padding:-15px;"><h4>&nbsp;  (b) Occupation <span style="margin-left:5px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding:-15"><span style="margin-left:-150px"><%=obj[20]%></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;text-align: left;padding:-15px;"><h4>&nbsp;  (c) Address   <span style="margin-left:30px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding:-15px;"><span style="margin-left:-150px"><%=obj[21] %></span></td>
	</tr>
	</table>
	
	<table style="margin-left:15px; margin-top:5px;border-collapse: collapse; width:650px;">
	<tr>
	<td style="width:750px;text-align: justify;border: 0;padding:-12"><h4>7. Details of blood / close relations working in  foreign embassy / firms in India / Abroad 
         : <span class="text-blue"><% if(obj[22].toString().equalsIgnoreCase("NA")){%><%=obj[22] %><%} %></span></h4> </td>
    </tr>
    
    <%  if(!obj[22].toString().equalsIgnoreCase("NA")){%>
    
    <tr>
    <td class="text-blue" style="border: 0;text-align: justify;padding:0"><%=obj[22] %></td>
	</tr>
	
	<%} %>
	<tr>
	<td style="width:350px;text-align: left;border: 0;text-align: justify;padding:-12"><h4>8. Details of employment during  last ten years : <span class="text-blue"><% if(obj[23].toString().equalsIgnoreCase("NA")){%><%=obj[23] %><%} %></span>
       </h4> </td>
    </tr>
    
    <%  if(!obj[23].toString().equalsIgnoreCase("NA")){%>
    <tr>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding:0"><%=obj[23] %></td>
	</tr>
	
	<%} %>
	<tr>
	<td style="width:350px;text-align: justify;border: 0;padding:-15"><h4>&nbsp;9. Are you involved in any court / police / vigilance case for which your presence is required in &nbsp; India
	
      : <span class="text-blue"><% if (obj[24].toString().equalsIgnoreCase("Y")){%>Yes<%} else{ %>No<%} %></span> </h4></td>
    
   
	</tr>
	
	<tr>
	<td style="width:350px;text-align: justify;border: 0;padding:-15"><h4>&nbsp;10. Whether Annual Property Return of the previous year has been filed 
	: <span class="text-blue"><% if (obj[25].toString().equalsIgnoreCase("Y")){%>Yes<%} else{ %>No<%} %> </span></h4></td>
	</tr>
    
  
	<tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15"><h4>&nbsp;11. Details of passport held previously, if any 	
      : &nbsp;<% if(obj[12].toString().equalsIgnoreCase("NA") && obj[13].toString().equalsIgnoreCase("NA") && obj[14].toString().equalsIgnoreCase("NA") &&  obj[15].toString().equalsIgnoreCase("NA")){ %><span class="text-blue">NA</span><%} %> 	</h4> </td>
    
     <td style="border: 0;padding:-15"></td>
	</tr>
	
	<% if(!obj[12].toString().equalsIgnoreCase("NA") && !obj[13].toString().equalsIgnoreCase("NA") &&  !obj[14].toString().equalsIgnoreCase("NA") && !obj[15].toString().equalsIgnoreCase("NA")){ %> 
	<table style="margin-top: 5px;margin-left:15px;width:650px;">	
									<tbody>
										
										<tr>
											<th>Type</th>
											<th>Passport No</th>
											<th>Date of Issue</th>
											<th>Validity</th>
										</tr>
									<tr>
										
											<td class="text-blue" ><%=obj[12] %></td>
											<td class="text-blue" ><%=obj[13] %></td>
											<td class="text-blue" ><%=rdf.format(sdf.parse(obj[14].toString())) %></td>
											<td class="text-blue" ><%=rdf.format(sdf.parse(obj[15].toString())) %></td>
									</tr>
										
									</tbody>
	</table>
	<%} %>
	
	</table>

	<table style="margin-left:15px; margin-top:0px;font-family:FontAwesome; width:650px;"> 
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding:-12;"><h4>12. Details of passport lost, if any :  <span class="text-blue"><% if(obj[26].toString().equals("NA")){%>NA<%} %></span>
       </h4></td>
    </tr>
    <% if(!obj[26].toString().equals("NA")){%>
    <tr>
    <td class="text-blue" style="border: 0;text-align: justify;padding:-12;"></td>
	</tr>
	<%} %>
	
	
	<%-- <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>&nbsp;11. Details of passport held previously,<br> if any 	
      </h4> </td>
    
     <td style="border: 0;"></td>
	</tr>
	
	
	<tr>
	<td style="width:350px;border: 0;"><h4>(a) Type  <span style="margin-left:220px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: left;"><%=obj[6]%></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4>(b) Passport No  <span style="margin-left:180px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: left;"><%=obj[7] %></td>
	</tr>
	
	
	<tr>
	<td style="width:350px;border: 0;"><h4>(c) Date of Issue <span style="margin-left:180px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: left;"><%=rdf.format(sdf.parse(obj[8].toString())) %></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4> (d) Validity <span style="margin-left:210px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: left;"><%=rdf.format(sdf.parse(obj[9].toString())) %></td>
	</tr>
	 --%>
	 
	
	<tr>
	<td style="width:400px;text-align: justify; border: 0;padding:-15px;;"><h4> &nbsp;13. Have you visited any foreign country before? 
	
	   : <span class="text-blue"><% if (obj[27].toString().equalsIgnoreCase("Y")){%>Yes<%} else{ %>No<%} %></span></h4></td>
      </tr>
    
   
	
	<% if (obj[27].toString().equalsIgnoreCase("Y")){ %>
	<tr>
	<td style="width:750px;border: 0;text-align: left;padding:-15px;"><h4> &nbsp;a) Details of such visits to include countries visited with details of dates :
	
	   </h4></td>
     </tr>
    
    <tr> 
       <td class="text-blue" style="border: 0;text-align: justify;padding:0;"><%=obj[28] %></td>
	</tr>
	
  <%} %>
	
	<tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15px;"><h4>&nbsp;14. Countries proposed to be visited : <span class="text-blue"><%=obj[29] %></span>
	   </h4></td>
     </tr> 
    
     <%-- <td class="text-blue" style="border: 0;text-align: justify;padding:-12;"><span style="margin-left:5px" ><%=obj[29] %></span></td>
	</tr> --%>
	
	<tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15"><h4>&nbsp;15. Date of departure : <span class="text-blue"><%=rdf.format(sdf.parse(obj[30].toString())) %></span>
	   </h4></td>
      </tr>
    
    <%--  <td class="text-blue" style="border: 0;text-align: justify;padding:-15;"><span style="margin-left:5px" ><%=rdf.format(sdf.parse(obj[30].toString())) %></span></td>
	</tr> --%>
	
	
	<tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15;"><h4> &nbsp;16. Purpose of visit : <span class="text-blue"><%=obj[31] %></span>
	   </h4></td>
     </tr> 
    
     <%-- <td class="text-blue" style="border: 0;text-align: justify;padding:-12;"><span style="margin-left:5px" ><%=obj[31] %></span></td>
	</tr> --%>
	
	<tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15;"><h4>&nbsp;17. Probable duration of stay at each country : <span class="text-blue"><%=obj[32] %></span>
	   </h4></td>
      </tr>
    
   <%--   <td class="text-blue" style="border: 0;text-align: justify;padding:-12;"><span style="margin-left:5px" ><%=obj[32] %></span></td>
	</tr> --%>
	
	<tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15"><h4> &nbsp;18. Probable date of return : <span class="text-blue"><%=rdf.format(sdf.parse(obj[33].toString())) %></span>
	   </h4></td>
     </tr>
    
    <%--  <td class="text-blue" style="border: 0;text-align: justify;padding:-12"><span style="margin-left:5px" ><%=rdf.format(sdf.parse(obj[33].toString())) %></span></td>
	</tr> --%>
	
	<tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15"><h4> &nbsp;19. Going : <span class="text-blue"><% if(obj[34].toString().equalsIgnoreCase("A")) {%>Alone<%} else {%>With Family<%} %> </span>
	   </h4></td>
      </tr>
      
    <%--   <td class="text-blue" style="border: 0;text-align: justify;padding:-12"><% if(obj[34].toString().equalsIgnoreCase("A")) {%><span style="margin-left:5px" >Alone</span><%} else {%><span style="margin-left:5px" >Family</span><%} %></td>
	</tr>
	 --%>
	 
	<% if(obj[34].toString().equalsIgnoreCase("WF")) {%>
	<tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15"><h4> &nbsp;&nbsp;a) Details of family : <span class="text-blue"><%=obj[35] %></span>
	   </h4></td>
    </tr>
      
      <%-- <td class="text-blue" style="border: 0;text-align: justify;padding:-12"><span style="margin-left:5px" ><%=obj[35] %></span></td>
	</tr> --%>
	
	<%} %>
	
	<tr>
	
	<td style="width:800px;text-align: left;border: 0;padding:-12"><h4> 20. Approximate amount expected  to be incurred for the trip including journey and stay abroad  : &nbsp; &nbsp; &nbsp; <span class="text-blue">Rs. <%=obj[36]%></span>
	
	   </h4></td>
     </tr>
    
    <%--  <td class="text-blue" style="border: 0;text-align: justify;padding:-15"><span style="margin-left:10px" ><%=obj[36]%></span></td>
	</tr> --%>
	
  <tr>
	<td style="width:400px;text-align: left;border: 0;padding:-12"><h4> 21. Trip financed by :  <span class="text-blue"><% if(obj[37].toString().equalsIgnoreCase("S")){ %>Self<%} else{ %>Other People<%} %> </span>
	
	   </h4></td>
   </tr>   
    
    <%--  <td class="text-blue" style="border: 0;text-align: justify;padding:-15"><% if(obj[37].toString().equalsIgnoreCase("S")){ %><span style="margin-left:5px" >SELF</span><%} else{ %><span style="margin-left:5px" >OTHER PEOPLE</span><%} %></td>
  </tr> --%>
  
  <% if(obj[37].toString().equalsIgnoreCase("S")) { %>
  <tr>
  <td style="width:400px;text-align:left;border: 0;padding:-15"><h4>&nbsp; a) Source of Amount Being Spent : <span  class="text-blue"><%=obj[38]%></span>
	
	   </h4></td>
	 </tr>
	<%}
	
   else if(obj[37].toString().equalsIgnoreCase("OP")){ %> 
  
  
  <tr>
  <td style="width:400px;text-align: left;border: 0;padding:-15"><h4>&nbsp;a)Name :  <span class="text-blue"><%=obj[39] %></span>
	
	   </h4></td>
    </tr>  
    
    <%--  <td class="text-blue" style="border: 0;text-align: justify;padding:-12"><span style="margin-left:5px"><%=obj[39] %></span></td>
  </tr> --%>
  
  <tr>
  <td style="width:400px;text-align: left;border: 0;padding:-15"><h4> &nbsp;b)Nationality :  <span class="text-blue"><%=obj[40] %></span>
	
	   </h4></td>
    </tr> 
   <tr>
  <td style="width:400px;text-align: left;border: 0;padding:-15"><h4> &nbsp;c)Relationship : <span class="text-blue"><%=obj[41] %></span>
	
	   </h4></td>
     </tr> 
    
     <%-- <td class="text-blue" style="border: 0;text-align: justify;padding:-12"><span style="margin-left:5px"><%=obj[41] %></span></td>
  </tr> --%>
  
   <tr>
  <td style="width:400px;text-align: left;border: 0;padding:-15"><h4> &nbsp;d)Address : <span class="text-blue"><%=obj[42] %> </span>
	
	   </h4></td>
      </tr>
    
    <%--  <td class="text-blue" style="border: 0;text-align: justify;padding:-10"><span style="margin-left:5px"><%=obj[42] %></span></td>
  </tr> --%>
  
   <%} %> 
   
   <tr>
	<td style="width:400px;text-align: left;border: 0;padding:-15"><h4> &nbsp;22. Are you likely to accept any foreign Hospitality : <span class="text-blue"><% if(obj[43].toString().equalsIgnoreCase("Y")){ %><span style="margin-left:5px">Yes</span><%} else{ %><span style="margin-left:5px">No</span><%} %> </span>
        </h4></td>
      </tr>
    
     <%-- <td class="text-blue" style="border: 0;text-align: left;padding:-10"><% if(obj[43].toString().equalsIgnoreCase("Y")){ %><span style="margin-left:5px">YES</span><%} else{ %><span style="margin-left:5px">NO</span><%} %></td>
  </tr>
	 --%>
	
	</table>
	
	
	
	 <div style="margin-left: 5px;margin-top:-20px;text-align: justify; text-justify: inter-word;font-size: 16px;" align="left">
						
		<h4>23. I certify that:<br>&emsp;
		 
          <% if (obj[24].toString().equalsIgnoreCase("N")){%>
           <span style="color:red; font-size:17px;">I am not involved in any court / police / disciplinary / vigilance case and there 
              is no restriction placed by any authority.</span> <%}  %> 
             <br>
              I undertake that I will return my Identity Card before proceeding abroad <br>&emsp; 
              I undertake that I shall not settle permanently abroad and rejoin my duty after expiry of leave
            <br>
              <% if(obj[45].toString().equalsIgnoreCase("N")) {%> 
		  
           I am not under contractual obligation to serve STARC for any specific period.
		
		<%} else{ %>
	        
            I am under contractual obligation to serve STARC for a period from 
              <span class="text-blue"><%=rdf.format(sdf.parse(obj[46].toString())) %></span>  to   <span class="text-blue"><%=rdf.format(sdf.parse(obj[47].toString())) %></span>
              
              <%} %>
          </h4>		   			
	</div>
		
	<br>
	<br>
	<br>
	
	
	 <div  style="margin-left:430px !important;" > Signature of the Applicant</div>	
	 <% if(obj[50]!=null) {%><div  style="margin-left:440px !important;">Forwarded On :&nbsp;<span class="text-blue"><%=rdf.format(sdf.parse(obj[50].toString())) %></span> </div> <%} %>
	 
	 
	 
	  <% if(obj[55]!=null && obj[56]!=null && obj[57]!=null){ %>
	  
	  <h1 class="break1"></h1>
	  
	    <div><span style="margin-top:0px;font-weight: 600;font-size: 16px;text-decoration: underline;text-align:center;">Filled by Department</span></div> 
	 <table style="margin-left:20px; margin-top:5px;border-collapse: collapse;font-family:FontAwesome; width:650px;"> 
	 
	 <tr>
		 <td style="width:650px;text-align: left;border: 0;padding: -15px;"><h4> 1. Is the applicant handling any classified work divulgence of which may affect the security of service and the country?
		 : &nbsp; <span class="text-blue"><% if(obj[55].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %> </span> 
         </h4></td>
      </tr>
	 
	 <tr>
		 <td style="width:650px;text-align: left;border: 0;padding: -15px;"><h4> 2. Is the individuals visit recommended even if the answer to Sl.No.1 above is in the affirmative
		 : &nbsp; <span class="text-blue"><% if(obj[56].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %> </span> 
         </h4></td>
      </tr>
	 
	 <tr>
		 <td style="width:650px;text-align: left;border: 0;padding: -15px;"><h4> 3.Whether the leave will be granted for proceeding abroad
		 : &nbsp; <span class="text-blue"><% if(obj[57].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %> </span> 
         </h4></td>
      </tr>
	 
	 </table>
	 <%} %>
	 
	
	 
	<% if(obj[58]!=null  && obj[61]!=null){ %>
	    
	     <hr>
		  <div style="margin-top:0px;font-weight: 600;font-size: 16px;text-decoration: underline;text-align:center;">Filled by P&A Department</div> 
		  <table style="margin-left:10px; margin-top:5px;border-collapse: collapse;font-family:FontAwesome; width:650px;">
		  
		  
		 <tr>
		     <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;1.(a) Are the entries given by the applicant in paras 1 to 6 in Part - I correct? :&nbsp;<span class="text-blue"><% if(obj[58].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span></h4></td>
            
         </tr>
          
          <% if(obj[58].toString().equalsIgnoreCase("N")){  %>
          <tr>
          
            <td style="width:750px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;(b) If not, mention variations </h4></td>
            </tr>
            
          <tr>
             <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><%=obj[59] %></td>
          </tr>
          
          <%} %>
          
          <tr>
          
            <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;2.(a) Was the individual ever involved in any 	:
              department enquiry or other cases? :&nbsp;<span class="text-blue">
            <% if(obj[24].toString().equalsIgnoreCase("Y")){ %> Yes<%} else{ %>No<%} %> </span></h4></td>
          
          </tr>
          
           
	        <% if(obj[24].toString().equalsIgnoreCase("Y")){ %>
	        <tr>
	        <td style="width:350px;text-align: justify;border: 0;padding: -12px;"><h4>
                &nbsp;(b) If so, nature of enquiry and its results? :</h4></td> 
            
            </tr>
            <tr>
            <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><%=obj[60] %></td>
            
           </tr>
           <%} %>
        
        <tr>
          
            <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;3.Are any financial dues outstanding against the 	:
                applicant? :&nbsp;<span class="text-blue">
            <% if(obj[61].toString().equalsIgnoreCase("Y")){ %> Yes<%} else{ %>No<%} %> </span></h4></td>
          
          </tr>
          
        
           <tr>
           <td style="width:350px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;3. Applicant is :&nbsp; <% if(obj[45].toString().equalsIgnoreCase("N")) {%>
		  
            not under contractual obligation to serve STARC for any specific period. <%} 
		
		     else if(obj[45].toString().equalsIgnoreCase("Y")){ %>
	        
               under contractual obligation to serve STARC for a period from 
              <span class="text-blue"><%=rdf.format(sdf.parse(obj[46].toString())) %></span>  to <span class="text-blue" ><%=rdf.format(sdf.parse(obj[47].toString())) %></span>
              
            <%} %> </h4></td>
             
              
           </tr>
          
        </table> 
        
        <br>
         <% if (obj[53]!=null){ %><div  style="margin-left:420px !important;" ><span class="text-blue"><%=obj[53]%></span></div><%} %>
	 <div  style="margin-left:470px !important;">P&A.Incharge </div> 
      <%} %>
       
       
        
        <br>
        
	 <% if(obj[49].toString().equalsIgnoreCase("APR")){ %>
		 <div style="margin-top:0x;text-align:center;"> 
		                         <span style="font-weight: 600; font-size: 16px;margin-left:0px;">APPROVED</span><br><br>
						        <span style="font-weight: 500; font-size: 16px;margin-left:0px;">CEO:&nbsp;<span class="text-blue" ><% if (Ceoname!=null){%><%=Ceoname[1] %><%} %></span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[54].toString().substring(0, 10)) +" "+obj[54].toString().substring(11,19) %></span></span><br>
		</div>	
		<%} %>
	 

	
	  			 		
	
</div>
		
		

</body>
</html>
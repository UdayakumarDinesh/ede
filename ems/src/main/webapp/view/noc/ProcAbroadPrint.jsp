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
					content: "<%=obj[11]%>";
					margin-top: 30px;
					margin-right: 10px;
				}

				@top-left {
					margin-top: 30px;
					margin-left: 10px;
					content: "<%=obj[0] %>";
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
</style>

<title>NOC Passport Print</title>
</head>
<body>


	<%
	
	   SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
       
    
	%>


	<div align="center">
		<table style="margin-left:10px; margin-top:15px;width:650px;">
			<tr>
				<td class="text-center" rowspan="2"><span
					style="font-size: 30px">STARC</span> <br>BANGALORE</td>
				<td class="text-center" width="300px" rowspan="2">APPLICATION FOR OBTAINING NO OBJECTION CERTIFICATE FOR PROCEEDING ABROAD  <br>
				
				</td>
				<td>STARC -BNG -<br>P&A-042
				</td>
				<td>Rev.: 01</td>
			</tr>
			<tr>
				<td>Date of Issue: <br>
				</td>
				<td>Total<br> Pages-5
				</td>
			</tr>
		</table>
		</div>

       <div style="text-align:center;">
		<h3 style="text-align: center;">PART - I </h3>
	<table style="margin-left:10px; margin-top:15px;border-collapse: collapse; width:650px;">
	<tr>
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
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>5.  Applicants Residential Address  </h4></td>
	<td  style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4> (a) Present  <span style="margin-left:194px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;text-align: left;"><%=obj[4] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4> (b) Permanent  <span style="margin-left:170px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;text-align: left;"><%=obj[5] %></td>
	</tr>
	</table>
	
	<h1 class="break"></h1>
	
	<table style="margin-left:10px; margin-top:15px;font-family:FontAwesome; width:650px;"> 
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 6. Detail of <% if(obj[12].toString().equalsIgnoreCase("F")){%>Father<%} else if(obj[12].toString().equalsIgnoreCase("H")){ %> Husband<%} 
	else if (obj[12].toString().equalsIgnoreCase("G")){ %> Guardian <%} %> <br> 
       </h4></td>
      
    <td style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4>  (a) Name <span style="margin-left:200px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: left;"><%=obj[13] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4>  (b) Occupation <span style="margin-left:160px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: left;"><%=obj[14]%></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4>  (c) Address   <span style="margin-left:180px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: left;"><%=obj[15] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 7. Details of blood / close relations <br>&emsp; working in  foreign embassy / <span style="margin-left:110px;"> :</span><br> firms in India / Abroad 
         </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: left;"><% if (obj[16].toString().trim().length()>0){%><%=obj[16] %><%}  else{ %>N/A<%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;text-align: left;"><h4> 8. Details of employment during <span style="margin-left:90px"> :</span><br> last ten years  
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: left;"><% if (obj[17].toString().trim().length()>0){%><%=obj[17] %><%}  else{ %>N/A<%} %></td>
	</tr>
	
	
		<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 9. Are you involved in any court <span style="margin-left:90px"> :</span><br>&emsp;/ police / vigilance case for <br>&emsp; which your presence is required <br>&emsp; in India
	
      </h4></td>
    
    <td class="text-blue" style="border: 0;text-align: left;"><% if (obj[18].toString().equalsIgnoreCase("Y")){%>YES<%} else{ %>NO<%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 10. Whether Annual Property <span style="margin-left:105px"> :</span> <br>&emsp; Return of the previous year <br>&emsp;has been filed 
	
	
      </h4></td>
    
    <td class="text-blue" style="border: 0;text-align: left;"><% if (obj[19].toString().equalsIgnoreCase("Y")){%>YES<%} else{ %>NO<%} %></td>
	</tr>
	
	
	</table>
	
	<h1 class="break"></h1>
	
	<table style="margin-left:10px; margin-top:15px;font-family:FontAwesome; width:650px;"> 
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>9. Details of passport held previously,<br> if any 	
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
	
	<tr>
	
	<td style="width:350px;text-align: left;border: 0;"><h4> 10. Details of passport lost, if any <span style="margin-left:100px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: left;"><% if(!obj[37].toString().equals("")){%><%=obj[37] %><%} else{ %>N/A<%} %></td>
	</tr>
	
	
	
	<tr>
	<td style="width:350px;text-align: left; border: 0;"><h4> 13. Have you visited any foreign <span style="margin-left:105px;">:</span><br>&emsp;country before? 
	
	
	 </h4></td>
      
    
    <td class="text-blue" style="border: 0;text-align: left;"><% if (obj[20].toString().equalsIgnoreCase("Y")){%>YES<%} else{ %>NO<%} %></td>
	</tr>
	
	<% if (obj[20].toString().equalsIgnoreCase("Y")){%>
	<tr>
	<td style="width:350px;border: 0;text-align: left;"><h4>&emsp; a) Details of such visits to include <span style="margin-left:100px;">:</span><br> countries visited with details <br>of dates
	
	
	 </h4></td>
      
    
    <td class="text-blue" style="border: 0;text-align: left;"><% if(obj[21].toString().trim().length()>0){ %><%=obj[21] %><%} %></td>
	</tr>
  <%} %>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>14. Countries proposed to be  <span style="margin-left:135px"> :</span><br>&emsp; visited	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><%=obj[22] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>15. Date of departure  <span style="margin-left:185px"> :</span>
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><%=rdf.format(sdf.parse(obj[23].toString())) %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>16. Purpose of visit<span style="margin-left:200px"> :</span>
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><%=obj[24] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>17. Probable duration of stay at<span style="margin-left:115px"> :</span><br>&emsp; each country
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><%=obj[25] %></td>
	</tr>
	</table>
	
	<h1 class="break"></h1>
	
	<table>
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>18. Probable date of return<span style="margin-left:150px"> :</span>
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><%=rdf.format(sdf.parse(obj[26].toString())) %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>19. Going <span style="margin-left:270px"> :</span>
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><% if(obj[27].toString().equalsIgnoreCase("A")) {%>Alone<%} else {%>Family<%} %></td>
	</tr>
	
	<% if(obj[27].toString().equalsIgnoreCase("WF")) {%>
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>&emsp; a) Details of family <span style="margin-left:210px"> :</span>
	   </h4></td>
      
      <td class="text-blue" style="border: 0;text-align: left;"><%=obj[28] %></td>
	</tr>
	<%} %>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>20. Approximate amount expected <br>&emsp; to be incurred for the trip <span style="margin-left:160px"> :</span><br>&emsp; including journey and stay <br>&emsp; abroad
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;">&#8377;&nbsp;<%=obj[29]%></td>
	</tr>
	
  <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>21. Trip financed by  <span style="margin-left:198px"> :</span>
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><% if(obj[30].toString().equalsIgnoreCase("S")){ %>SELF<%} else{ %>OTHER PEOPLE<%} %></td>
  </tr>
  
  <% if(obj[30].toString().equalsIgnoreCase("S")) { %>
  <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4>&emsp;&emsp; a) Amount Spend  <span style="margin-left:220px"> :</span>
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;">&#8377;<%=obj[31]%><%-- <a  href="NocProcAbroadDownload.htm?ProcAbrId=<%=obj[10].toString()%>" 
     style="margin-left:0px; margin-top:0px;color:red;">Download file</a> --%></td> 
									  
										  
									
      
  </tr>
	
	<%}
	
   else if(obj[30].toString().equalsIgnoreCase("OP")){ %> 
  
  
  <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4> &emsp;&emsp;a)Name&Nationality <span style="margin-left:158px"> :</span>
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><%=obj[33] %></td>
  </tr>
  
  
   <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4> &emsp;&emsp;b)Relationship <span style="margin-left:200px"> :</span>
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><%=obj[34] %></td>
  </tr>
  
   <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4> &emsp;&emsp;c)Address <span style="margin-left:232px"> :</span>
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><%=obj[35] %></td>
  </tr>
  
   <%} %> 
   
   <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>22. Are you likely to accept any <span style="margin-left:122px"> :</span><br>&emsp;&emsp; foreign Hospitality
        
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;text-align: left;"><% if(obj[36].toString().equalsIgnoreCase("Y")){ %>YES<%} else{ %>NO<%} %></td>
  </tr>
	
	
	</table>
	
	<h1 class="break"></h1>
	
	 <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 16px;" align="left">
						
		<h4>  23. I certify that:<br>&emsp;
		  <br>&emsp; 
          
           <% if (obj[18].toString().equalsIgnoreCase("N")){%>
           I am not involved in any court / police / disciplinary / vigilance case and there 
              is no restriction placed by any authority. <%}  %> 
             
              I undertake that I will return my Identity Card before proceeding abroad <br>&emsp; 
              I undertake that I shall not settle permanently abroad and rejoin my duty after <br>&emsp;expiry of leave
            
              
              </h4>		   			
				   			
				   																			
		</div>
		
		
   <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 16px;" align="left">
						
		<h4>  24. I certify that:<br>&emsp;
		<% if(obj[39].toString().equalsIgnoreCase("N")) {%>
		   <br>&emsp;
           I am not under contractual obligation to serve STARC for any specific period. <%} 
		
		else if(obj[39].toString().equalsIgnoreCase("Y")){%>
	        <br>&emsp;
            I am under contractual obligation to serve STARC for a period from 
              <span class="text-blue" ><%=rdf.format(sdf.parse(obj[40].toString())) %></span>  to   <span class="text-blue" ><%=rdf.format(sdf.parse(obj[41].toString())) %></span>
              
          <%} %>
       </h4>		   			
				   			
				   																			
	</div> 
	
	<br>
	 <div  style="margin-left:450px !important;" > Signature of the Applicant</div>	
	 <% if(obj[44]!=null) {%><div  style="margin-left:450px !important;">Forwarded Date :&nbsp;<span class="text-blue"><%=rdf.format(sdf.parse(obj[44].toString())) %></span> </div> <%} %>
	 <br>
	 <div align="left" style="margin-left:10px;" > Dept.Incharge : <span class="text-blue" ><%=obj[42] %></span></div>	
	 
	 <br>
	
	<%-- 
	<% if(obj[27].toString().equalsIgnoreCase("APR")){%>
	    <hr>
		  <div><span style="margin-left: -80px;font-weight: 600;font-size: 18px;text-decoration: underline;">Filled by P&A Department</span></div> 
		  <table style="margin-left:10px; margin-top:35px;border-collapse: collapse;font-family:FontAwesome; width:650px;">
		 <tr>
		 <td style="width:350px;text-align: left;border: 0;"><h4> 1.(a) Are the entries given by the <span style="margin-left:45px"> :</span> <br>&emsp;&emsp;&emsp;applicant in 
             paras 1 to 6 in <br>&emsp;&emsp;Part - I correct?</h4></td>
            
            <td class="text-blue" style="border: 0;text-align: left;"><% if(obj[28].toString().equalsIgnoreCase("Y")){ %>YES<%} else{ %>NO<%} %></td>
            
          </tr>
          
          <tr>
          <td style="width:350px;text-align: left;border: 0;"><h4>&emsp;(b) If not, mention variations <span style="margin-left:72px"> :</span> </h4></td>
          <td class="text-blue" style="border: 0;text-align: left;"><%=obj[29] %></td>
          </tr>
          
          <tr>
           <td style="width:350px;text-align: left;border: 0;"><h4>2.(a) Whether the employee is <span style="margin-left:72px"> :</span> <br> &emsp; &emsp; &emsp; under suspension?
           </h4></td>
           
           <td class="text-blue" style="border: 0;text-align: left;"><% if(obj[30].toString().equalsIgnoreCase("Y")){ %>YES<%} else{ %>NO<%} %></td>
           
           </tr>
        </table> --%>
        
           
           
      <%--  <table style="margin-left:10px; margin-top:35px;border-collapse: collapse;font-family:FontAwesome; width:650px;">
           <tr>
           <td style="width:350px;text-align: left;border: 0;"><h4>&emsp;
               (b) Whether the employee is <span style="margin-left:75px"> :</span><br> &emsp;&emsp;involved in any	
	           Disciplinary <br>&emsp;&emsp;/Criminal / Corruption /<br>&emsp; &emsp;Court
	          Case </h4></td> 
	         
	          <td class="text-blue" style="border: 0;text-align: left;"><% if(obj[31].toString().equalsIgnoreCase("Y")){ %>YES<%} else{ %>NO<%} %></td>
	          
	        </tr>
	        
	        <tr>
	        <td style="width:350px;text-align: left;border: 0;"><h4>
                &emsp;(c) If so, details of the case<span style="margin-left:90px"> :</span> </h4></td> 
            
            <td class="text-blue" style="border: 0;text-align: left;"><%=obj[32] %></td>
            
           </tr>
           
           <tr>
           <td style="width:350px;text-align: left;border: 0;text-align: left;"><h4> &nbsp;3. Applicant is<span style="margin-left:167px"> :</span></h4></td>
             
              
            <td  style="border: 0;text-align: left;"><% if(obj[20].toString().equalsIgnoreCase("N")) {%>
		  
            not under contractual obligation to serve STARC for any specific period. <%} 
		
		     else if(obj[20].toString().equalsIgnoreCase("Y")){%>
	        
                under contractual obligation to serve STARC for a period from 
              <span class="text-blue"   ><%=rdf.format(sdf.parse(obj[21].toString())) %></span>  to   <span class="text-blue"  ><%=rdf.format(sdf.parse(obj[22].toString())) %></span>
              
            <%} %>
          
          </td>
          </tr>
        </table>  --%>
        
      
        
	
	 
	<%--  <% if (obj[33]!=null){ %><div  style="margin-left:450px !important;" ><span class="text-blue"><%=obj[33]%></span></div><%} %>
	 <div  style="margin-left:450px !important;">P&A.Incharge </div> 
	 <br>
	 <% if(obj[34]!=null){ %><div align="left" style="margin-left:10px;" > Approved Date : <span class="text-blue" ><%=rdf.format(sdf.parse(obj[34].toString())) %></span></div><%} %>	
		
	   <%} %>
	  			 --%>				
	
</div>
		
		

</body>
</html>
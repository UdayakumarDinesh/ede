<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
 <%@page import="java.util.List"%>
 <%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<% 

     
     Object[] obj= (Object[])request.getAttribute("NocPassportDetails");
     
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

/* .table3 tr, th, td {
	border: 1px solid black;
	padding: 0px;
}
.table3 tr{
	height: 10px;
}

/* .table3 td {
	font-weight: normal;
} */
/* .accDPT{
	margin-top: 30px;
} */
 .table4 tr, th, td {
	border: 1px solid black;
	padding: 5px;
	font-weight:400
	
}

/* .table4 td {
	font-weight: normal;
}

.table4 td:first-child:not(.exlude){
	width: 45%;
}
.sign{
	width: 620px;
	text-align: right;
	margin-top: 60px;
}   */
.break
	 {
	  	page-break-before:always;
	 }
	 
.break1
	 {
	  	page-break-after:always;
	 }	 
.text-blue
{
	color: blue;
	font-weight:500
	font-size: 17px;
	
}	

h4{

font-weight:400

} 


</style>

<title>NOC Passport</title>
</head>
<body>


	<%
	
	   SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
       String lablogo=(String)request.getAttribute("lablogo");
       Object[] Ceoname=(Object[])request.getAttribute("CeoName");
       
       List<Object[]> ApprovalData = (List<Object[]>)request.getAttribute("NOCPassportApprovalData");
       
       
       List<String> RecommendStatus = Arrays.asList("VGI","VDI","VDG","VSO"); 
      
    
	%>


	<div align="center">
		<table style="margin-left:10px; margin-top:15px;width:650px;">
			<tr>
				<td class="text-center" rowspan="2"><span
					style="font-size: 30px"><img style="width: 70px; height: 60px; margin-top: -20px;margin-left: 10px;" align="left"   <%if(lablogo!=null ){ %> src="data:image/*;base64,<%=lablogo%>" alt="Configuraton"<%}else	{ %> alt="File Not Found" <%} %>></span> <br></td>
				<td class="text-center" width="300px" rowspan="2" style="font-weight:600">APPLICATION FOR OBTAINING NO OBJECTION CERTIFICATE FOR PASSPORT  <br>
				
				</td>
				<td colspan="2" style="font-weight:600">STARC - <%=obj[17]%> 
				</td>
				
			</tr>
			<tr>
				<td colspan="2" style="font-weight:600"> Date of Issue:&nbsp; <% if (obj[38]!=null){ %><%=rdf.format(sdf.parse(obj[38].toString())) %><%} %> 
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
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -15px;"><h4 >&nbsp;&nbsp;5. Applicants Residential Address  </h4></td>
	<td  style="border: 0;padding: -15px;"></td>
	</tr>
	
	<tr>
	<td style="width:0px;border: 0;padding: -12px;text-align: left"><h4>&nbsp; (a) Present  &nbsp; &nbsp;&nbsp; &nbsp;: </h4></td>
	<td class="text-blue" style="border: 0;text-align: justify;padding: -12px;"><span style="margin-left:-165px;"><%=obj[4] %>,<%=obj[5] %>,<%=obj[6] %> <%=obj[7] %></span></td>
	</tr>
	
	
	
	<tr>
	<td style="width:0px;border: 0;padding: -12px;text-align: left"><h4>&nbsp; (b) Permanent :</h4></td>
	<td class="text-blue" style="border: 0;text-align: justify;padding: -12px;"><span style="margin-left:-165px;"><%=obj[8] %>,<%=obj[9] %>,<%=obj[10] %> <%=obj[11] %></span></td>
	</tr>
	
		<tr>
	<td style="width:350px;text-align: justify;border: 0;padding: -15px;"><h4>&nbsp;&nbsp;6. Detail of <% if(obj[18].toString().equalsIgnoreCase("F")){%>Father<%} else if(obj[18].toString().equalsIgnoreCase("H")){ %> Husband<%} 
	else if (obj[18].toString().equalsIgnoreCase("G")){ %> Guardian <%} %> <br> 
       </h4></td>
      
    <td style="border: 0;padding: -15px;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -15px;text-align: left"><h4>&nbsp; (a) Name <span style="margin-left:45px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -15px;"><span style="margin-left:-150px;"><%=obj[19] %></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -15px;text-align: left"><h4>&nbsp; (b) Occupation <span style="margin-left:5px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -15px;"><span style="margin-left:-150px;"><%=obj[20]%></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -15px;text-align: left"><h4>&nbsp; (c) Address   <span style="margin-left:30px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -15px;"><span style="margin-left:-150px;"><%=obj[21] %></span></td>
	</tr>
	
	</table>
	
	<table style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:650px;">
	<tr>
	<td style="width:750px;text-align: justify;border: 0;padding: -15px;"><h4>&nbsp;&nbsp;7. Details of blood / close relations working in  foreign embassy / firms in India / Abroad 
         <% if(obj[22].toString().equalsIgnoreCase("NA")){ %>: <span class="text-blue"><%=obj[22] %></span><%} %> </h4> </td>
    </tr>
    
      <% if(!obj[22].toString().equalsIgnoreCase("NA")){ %>
    <tr>
       <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><% if (obj[22].toString().trim().length()>0){%><%=obj[22] %><%} %></td>
	</tr>
	
	<%} %>
	<tr>
	<td style="width:750px;text-align: justify;border: 0;text-align: left;padding: -15px;"><h4>&nbsp;&nbsp;8. Details of employment during last ten years  
       <% if(obj[23].toString().equalsIgnoreCase("NA")){ %>: <span class="text-blue"><%=obj[23] %></span><%} %> </h4> </td>
    </tr>
      
      <% if(!obj[23].toString().equalsIgnoreCase("NA")){ %> 
    <tr>
      <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><% if (obj[23].toString().trim().length()>0){%><%=obj[23] %><%} %></td>
	</tr>
	
	<%} %>
	<tr>
	<td style="width:350px;text-align: justify;border: 0;padding: -15px;"><h4>&nbsp;&nbsp;9. Details of passport held previously, if any : &nbsp;<% if(obj[12].toString().equalsIgnoreCase("NA") && obj[13].toString().equalsIgnoreCase("NA") && obj[14].toString().equalsIgnoreCase("NA") &&  obj[15].toString().equalsIgnoreCase("NA")){ %><span class="text-blue">NA</span><%} %> 	
       </h4> </td>
    
     <td style="border: 0;padding: -15px;"></td>
	</tr>
	
	</table>
	
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
	
	<table style="margin-left:10px; margin-top:0px;font-family:FontAwesome; width:650px;"> 
	 <tr>

     <td style="width:350px;text-align: left;border: 0;padding: -15px;"><h4>&nbsp; 10. Details of passport lost, if any :
        <%if(obj[24].toString().equals("NA")){ %><span class="text-blue">NA</span><%} %></h4> </td>
       <tr>
    <% if(!obj[24].toString().equals("NA")){%>
    <tr>
    <td class="text-blue" style="border: 0;width:350px;text-align: justify;padding: 0px;"><%=obj[24] %></td>
	</tr>
	<%} %>
	</table>
	
	<table style="margin-left:10px; margin-top:0px;font-family:FontAwesome; width:650px;"> 
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -15px;"><h4>&nbsp; 11. Type of passport required :
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><span style="margin-left:-90px"><%=obj[25] %></span></td>
	</tr>
	</table>
	
	
	
	
	 <div style="margin-left: 5px;margin-top:-20px;text-align: justify;font-size: 16px;width:650px;" align="left" >
						
		<h4 style="line-height:1.4;">12. I certify that:<br>&emsp;
		 
          (a) My application for the above passport is not for proceeding to a foreign 
           country. <span style="color:red;"><br>I shall separately seek the NO OBJECTION CERTIFICATE  
           before proceeding to a foreign country. </span>
           <br>
          <span style="color:red;">(b) I am not involved in any court / police / disciplinary / vigilance case and there 
              is no restriction placed by any authority. </span>
              
             <% if(obj[26].toString().equalsIgnoreCase("N")) {%>
		   <br>&emsp;
           I am not under contractual obligation to serve STARC for any specific period. <%} 
		
		else if(obj[26].toString().equalsIgnoreCase("Y")){%>
	        <br>&emsp;
            I am under contractual obligation to serve STARC for a period from 
              <span class="text-blue" ><%=rdf.format(sdf.parse(obj[27].toString())) %></span>  to   <span class="text-blue" ><%=rdf.format(sdf.parse(obj[28].toString())) %></span>
              
              
          <%} %> 
          
         </h4>		   			
				   			
				   																			
		</div>
		
		
  
	
	<br>
	<br>
	
	<div  style="margin-left:320px !important;" > Signature of the Applicant</div>	
	 <% if(obj[29]!=null) {%><div   style="margin-left:400px !important;">Forwarded On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[29].toString().substring(0, 10)) +" "+obj[29].toString().substring(11,19) %></span> </div> <%} %>
	 <br>
	
	 
	
	
	<% if(obj[37].toString().equalsIgnoreCase("APR") || (obj[37].toString().equalsIgnoreCase("DPR"))){ %>
	
	<h1 class="break1"></h1>
	 
		<div style="margin-left: -80px;margin-top:0px;font-weight: 600;font-size: 16px;text-decoration: underline;">Filled by P&A Department</div> 
		  <table style="margin-left:10px; margin-top:5px;border-collapse: collapse;font-family:FontAwesome; width:650px;">
		  
		  
		 <tr>
		     <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;1.(a) Are the entries given by the applicant in paras 1 to 6 in Part - I correct? :&nbsp;<span class="text-blue"><% if(obj[30].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span></h4></td>
            
         </tr>
          
          <% if(obj[30].toString().equalsIgnoreCase("N")){  %>
          <tr>
          
            <td style="width:750px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;(b) If not, mention variations </h4></td>
            </tr>
            
          <tr>
             <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><%=obj[31] %></td>
          </tr>
          
          <%} %>
          
          <tr>
          
            <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;2.(a) Whether the employee is under suspension? :&nbsp;<span class="text-blue">
            <% if(obj[32].toString().equalsIgnoreCase("Y")){ %> Yes<%} else{ %>No<%} %> </span></h4></td>
          
          </tr>
          
          
           <tr>
           
           <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>
              &nbsp; (b) Whether the employee is involved in any	
	           Disciplinary / Criminal / Corruption / Court Case: &nbsp;<span class="text-blue" ><% if(obj[33].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span> </h4></td> 
	         
	       </tr>
	         
	        <% if(obj[33].toString().equalsIgnoreCase("Y")){ %>
	        <tr>
	        <td style="width:350px;text-align: justify;border: 0;padding: -12px;"><h4>
                &nbsp;(c) If so, details of the case :</h4></td> 
            
            </tr>
            <tr>
            <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><%=obj[34] %></td>
            
           </tr>
           <%} %>
        
           <tr>
           <td style="width:350px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;3. Applicant is :&nbsp; <% if(obj[26].toString().equalsIgnoreCase("N")) {%>
		  
            not under contractual obligation to serve STARC for any specific period. <%} 
		
		     else if(obj[26].toString().equalsIgnoreCase("Y")){ %>
	        <br>&nbsp;&nbsp;&nbsp;
               under contractual obligation to serve STARC for a period from 
              <span class="text-blue"><%=rdf.format(sdf.parse(obj[27].toString())) %></span>  to <span class="text-blue" ><%=rdf.format(sdf.parse(obj[28].toString())) %></span>
              
            <%} %> </h4></td>
             
              
           </tr>
          
        </table> 
        
      	<%} %>
	  			
        
	 <br>
	
	 
	 
	  <%  
	  if(obj[35].toString().equalsIgnoreCase("A")){
	  
	  for(Object[] ad :ApprovalData) {
		 
		    if( RecommendStatus.contains(ad[8].toString())){ %>
				 <div align="left" style="margin-left:10px !important;font-size: 15px;">Recommended By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
	              <div  align="left" style="margin-left:10px !important;font-size: 15px;">Recommended On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
	              
	              <br>
		 <% } 
	 
	  else if(ad[8].toString().equalsIgnoreCase("VPA")){%>
		
				<div align="left" style="margin-left:450px !important;font-size: 15px;">Verified By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
			    <div align="left" style="margin-left:450px !important;font-size: 15px;">Verified On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
		
		
	<% }}}%> 
		
	    <br>
		<br>
		<br>
		
		      <%    for(Object[] ad :ApprovalData) {
				 if(ad[8].toString().equalsIgnoreCase("APR")){%>
			
		
		<div align="center" style="margin-left:0px;text-align:center;"> 
		                         <span style="font-weight: 600; font-size: 16px;">APPROVED</span><br><br>
						        <span style="font-weight: 500; font-size: 15px;">Approved By:&nbsp;<span class="text-blue" ><%=ad[2] %></span></span><br>
								<span style="font-weight: 400; font-size: 15px;">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %></span></span><br>
		</div>
		
		 <%}
		
			else if(ad[8].toString().equalsIgnoreCase("DPR")){%>
		 
		<br>
		<div align="center" style="margin-left:0px;text-align:center;"> 
		                        <span style="font-weight: 600; font-size: 16px;">DISAPPROVED</span><br><br><br>
						        <span style="font-weight: 500; font-size: 15px;">DisApproved By:&nbsp;<span class="text-blue" ><%=ad[2] %></span></span><br>
								<span style="font-weight: 400; font-size: 15px;">DisApproved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %></span></span><br>
		</div> 
		
		<%}} %>
		
	
	  	
	  							
	
</div> 
		
		

</body>
</html>
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
					content: "<%=obj[11]%>";
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
	 
.break1
	 {
	  	page-break-after:always;
	 }	 
.text-blue
{
	color: blue;
	font-weight:500
	
	
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
      
    
	%>


	<div align="center">
		<table style="margin-left:10px; margin-top:15px;width:650px;">
			<tr>
				<td class="text-center" rowspan="2"><span
					style="font-size: 30px"><img style="width: 70px; height: 60px; margin-top: -20px;margin-left: 10px;" align="left"   <%if(lablogo!=null ){ %> src="data:image/*;base64,<%=lablogo%>" alt="Configuraton"<%}else	{ %> alt="File Not Found" <%} %>></span> <br></td>
				<td class="text-center" width="300px" rowspan="2">APPLICATION FOR OBTAINING NO OBJECTION CERTIFICATE FOR PASSPORT  <br>
				
				</td>
				<td colspan="2">STARC - <%=obj[11]%> <!-- <!-- -BNG --- <br>P&A-058 --> 
				</td>
				<!-- <td>Rev.: 01</td> -->
			</tr>
			<tr>
				<td colspan="2"> Date of Issue:&nbsp; <% if (obj[34]!=null){ %><%=rdf.format(sdf.parse(obj[34].toString())) %><%} %> 
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
	<td style="width:350px;text-align: left;border: 0;padding: -5px;"><h4>1. Name (in BLOCK LETTERS) <span style="margin-left:80px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;text-align: left;padding: -5px; text-transform: uppercase;"><%=obj[1] %></td>
	</tr>
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -5px;"><h4>2. Designation  <span style="margin-left:207px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;text-align: left;padding: -5px;"><%=obj[2] %></td>
	</tr>
		<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -5px;"><h4>3. EmpNo  <span style="margin-left:236px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;text-align: left;padding: -5px;"><%=obj[0] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -5px;"><h4>4. Department / Group  <span style="margin-left:147px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;text-align: left;padding: -5px;"><%=obj[3] %></td>
	</tr> --%>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -15px;"><h4>&nbsp;&nbsp;5. Applicants Residential Address  </h4></td>
	<td  style="border: 0;padding: -15px;"></td>
	</tr>
	
	<tr>
	<td style="width:0px;border: 0;padding: -12px;text-align: left"><h4>&nbsp; (a) Present  &nbsp; &nbsp;&nbsp; &nbsp;: </h4></td>
	<td class="text-blue" style="border: 0;text-align: justify;padding: -12px;"><span style="margin-left:-165px;"><%=obj[4] %>,<%=obj[40] %>,<%=obj[41] %> <%=obj[42] %></span></td>
	</tr>
	
	
	
	<tr>
	<td style="width:0px;border: 0;padding: -12px;text-align: left"><h4>&nbsp; (b) Permanent :</h4></td>
	<td class="text-blue" style="border: 0;text-align: justify;padding: -12px;"><span style="margin-left:-165px;"><%=obj[5] %>,<%=obj[43] %>,<%=obj[44] %> <%=obj[45] %></span></td>
	</tr>
	
		<tr>
	<td style="width:350px;text-align: justify;border: 0;padding: -15px;"><h4>&nbsp;&nbsp;6. Detail of <% if(obj[12].toString().equalsIgnoreCase("F")){%>Father<%} else if(obj[12].toString().equalsIgnoreCase("H")){ %> Husband<%} 
	else if (obj[12].toString().equalsIgnoreCase("G")){ %> Guardian <%} %> <br> 
       </h4></td>
      
    <td style="border: 0;padding: -15px;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -15px;text-align: left"><h4>&nbsp; (a) Name <span style="margin-left:45px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -15px;"><span style="margin-left:-150px;"><%=obj[13] %></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -15px;text-align: left"><h4>&nbsp; (b) Occupation <span style="margin-left:5px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -15px;"><span style="margin-left:-150px;"><%=obj[14]%></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -15px;text-align: left"><h4>&nbsp; (c) Address   <span style="margin-left:30px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -15px;"><span style="margin-left:-150px;"><%=obj[15] %></span></td>
	</tr>
	
	</table>
	
	<table style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:650px;">
	<tr>
	<td style="width:750px;text-align: justify;border: 0;padding: -15px;"><h4>&nbsp;&nbsp;7. Details of blood / close relations working in  foreign embassy / firms in India / Abroad 
         </h4> </td>
    </tr>
    
    <tr>
       <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><% if (obj[16].toString().trim().length()>0){%><%=obj[16] %><%}  else{ %>N/A<%} %></td>
	</tr>
	
	<tr>
	<td style="width:750px;text-align: justify;border: 0;text-align: left;padding: -15px;"><h4>&nbsp;&nbsp;8. Details of employment during last ten years  
       </h4> </td>
    </tr>
      
    <tr>
      <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><% if (obj[17].toString().trim().length()>0){%><%=obj[17] %><%}  else{ %>N/A<%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: justify;border: 0;padding: -15px;"><h4>&nbsp;&nbsp;9. Details of passport held previously, if any : &nbsp;<% if(obj[6].toString().equalsIgnoreCase("NA") && obj[7].toString().equalsIgnoreCase("NA") && obj[8].toString().equalsIgnoreCase("NA") &&  obj[9].toString().equalsIgnoreCase("NA")){ %><span class="text-blue">NA</span><%} %> 	
       </h4> </td>
    
     <td style="border: 0;padding: -15px;"></td>
	</tr>
	
	</table>
	
<% if(!obj[6].toString().equalsIgnoreCase("NA") && !obj[7].toString().equalsIgnoreCase("NA") &&  !obj[8].toString().equalsIgnoreCase("NA") && !obj[9].toString().equalsIgnoreCase("NA")){ %> 
	<table style="margin-top: 5px;margin-left:15px;width:650px;">	
									<tbody>
										
										<tr>
											<th>Type</th>
											<th>Passport No</th>
											<th>Date of Issue</th>
											<th>Validity</th>
										</tr>
									<tr>
										
											<td class="text-blue" ><%=obj[6] %></td>
											<td class="text-blue" ><%=obj[7] %></td>
											<td class="text-blue" ><%=rdf.format(sdf.parse(obj[8].toString())) %></td>
											<td class="text-blue" ><%=rdf.format(sdf.parse(obj[9].toString())) %></td>
									</tr>
										
									</tbody>
								</table>
	<%} %>
	
	<%-- <tr>
	<td style="width:350px;border: 0;padding: -5px;"><h4>(a) Type  <span style="margin-left:220px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: left;padding: -5px;"><%=obj[6]%></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -5px;"><h4>(b) Passport No  <span style="margin-left:180px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: left;padding: -5px;"><%=obj[7] %></td>
	</tr>
	
	
	<tr>
	<td style="width:350px;border: 0;padding: -5px;"><h4>(c) Date of Issue <span style="margin-left:180px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: left;padding: -5px;"><%=rdf.format(sdf.parse(obj[8].toString())) %></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -5px;"><h4> (d) Validity <span style="margin-left:210px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: left;padding: -5px;"><%=rdf.format(sdf.parse(obj[9].toString())) %></td>
	</tr> --%>
	
	<table style="margin-left:10px; margin-top:0px;font-family:FontAwesome; width:650px;"> 
	 <tr>

     <td style="width:350px;text-align: left;border: 0;padding: -15px;"><h4>&nbsp; 10. Details of passport lost, if any <span style="margin-left:3px"> :</span>
        <%if(obj[18].toString().equals("NA")){ %><span class="text-blue">NA</span><%} %></h4> </td>
       <tr>
    <% if(!obj[18].toString().equals("NA")){%>
    <tr>
    <td class="text-blue" style="border: 0;width:350px;text-align: justify;padding: 0px;"><%=obj[18] %></td>
	</tr>
	<%} %>
	</table>
	
	<table style="margin-left:10px; margin-top:0px;font-family:FontAwesome; width:650px;"> 
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -15px;"><h4>&nbsp; 11. Type of passport required  <span style="margin-left:15px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><span style="margin-left:-65px"><%=obj[19] %></span></td>
	</tr>
	</table>
	
	
	
	<%-- <table style="margin-left:10px; margin-top:0px;font-family:FontAwesome; width:650px;"> 
    <tr>

     <td style="width:350px;text-align: left;border: 0;padding: -5px;"><h4> 10. Details of passport lost, if any <span style="margin-left:10px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: justify;padding: -5px;"><% if(!obj[18].toString().equals("")){%><span style="margin-left:-75px"><%=obj[18] %></span><%} else{ %><span style="margin-left:-75px">N/A</span><%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -5px;"><h4> 11. Type of passport required  <span style="margin-left:30px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: justify;padding: -5px;"><span style="margin-left:-75px"><%=obj[19] %></span></td>
	</tr> 
	
	
	
	</table> --%>
	
	
	
	<!-- <table style="margin-left:10px; margin-top:15px;font-family:FontAwesome; width:650px;"> 
	
	
	
	</table>
	 -->
	
	 <div style="margin-left: 5px;margin-top:-20px;text-align: justify;font-size: 16px;" align="left">
						
		<h4>12. I certify that:<br>&emsp;
		 
          (a) My application for the above passport is not for proceeding to a foreign 
           country. <span style="color:red;">I shall <br> &emsp;&emsp; separately seek the NO OBJECTION CERTIFICATE  
           before proceeding to a foreign country. </span>
           <br>
          <span style="color:red;">(b) I am not involved in any court / police / disciplinary / vigilance case and there 
              is no restriction placed by any authority. </span></h4>		   			
				   			
				   																			
		</div>
		
		
   <div style="margin-left: 10px;margin-top:-10px;text-align: justify; text-justify: inter-word;font-size: 16px;" align="left">
						
		<h4>13. I certify that:
		<% if(obj[20].toString().equalsIgnoreCase("N")) {%>
		   <br>&emsp;
           I am not under contractual obligation to serve STARC for any specific period. <%} 
		
		else if(obj[20].toString().equalsIgnoreCase("Y")){%>
	        <br>&emsp;
            I am under contractual obligation to serve STARC for a period from 
              <span class="text-blue" ><%=rdf.format(sdf.parse(obj[21].toString())) %></span>  to   <span class="text-blue" ><%=rdf.format(sdf.parse(obj[22].toString())) %></span>
              
          <%} %>
       </h4>		   			
				   			
				   																			
	</div>
	
	<br>
	<br>
	
	<div  style="margin-left:320px !important;" > Signature of the Applicant</div>	
	 <% if(obj[25]!=null) {%><div   style="margin-left:400px !important;">Forwarded On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[25].toString().substring(0, 10)) +" "+obj[25].toString().substring(11,19) %></span> </div> <%} %>
	 <br>
	 <%-- <div align="left" style="margin-left:10px;" > Dept.Incharge : <span class="text-blue" ><%=obj[23] %></span></div>	 --%>
	 <%-- <%=rdf.format(sdf.parse(obj[25].toString())) %> --%>
	 
	
	
	
	<% if(obj[27].toString().equalsIgnoreCase("APR")){ %>
	
	<h1 class="break1"></h1>
	 
		<div style="margin-left: -80px;margin-top:0px;font-weight: 600;font-size: 16px;text-decoration: underline;">Filled by P&A Department</div> 
		  <table style="margin-left:10px; margin-top:5px;border-collapse: collapse;font-family:FontAwesome; width:650px;">
		  
		  
		 <tr>
		     <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;1.(a) Are the entries given by the applicant in paras 1 to 6 in Part - I correct? :&nbsp;<span class="text-blue"><% if(obj[28].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span></h4></td>
            
         </tr>
          
          <% if(obj[28].toString().equalsIgnoreCase("N")){  %>
          <tr>
          
            <td style="width:750px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;(b) If not, mention variations </h4></td>
            </tr>
            
          <tr>
             <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><%=obj[29] %></td>
          </tr>
          
          <%} %>
          
          <tr>
          
            <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;2.(a) Whether the employee is under suspension? :&nbsp;<span class="text-blue">
            <% if(obj[30].toString().equalsIgnoreCase("Y")){ %> Yes<%} else{ %>No<%} %> </span></h4></td>
          
          </tr>
          
          
           <tr>
           
           <td style="width:650px;text-align: justify;border: 0;padding: -12px;"><h4>
              &nbsp; (b) Whether the employee is involved in any	
	           Disciplinary / Criminal / Corruption / Court Case: &nbsp;<span class="text-blue" style="margin-left:20px;"><% if(obj[31].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span> </h4></td> 
	         
	       </tr>
	         
	        <% if(obj[31].toString().equalsIgnoreCase("Y")){ %>
	        <tr>
	        <td style="width:350px;text-align: justify;border: 0;padding: -12px;"><h4>
                &nbsp;(c) If so, details of the case :</h4></td> 
            
            </tr>
            <tr>
            <td class="text-blue" style="border: 0;text-align: justify;padding: 0px;"><%=obj[32] %></td>
            
           </tr>
           <%} %>
        
           <tr>
           <td style="width:350px;text-align: justify;border: 0;padding: -12px;"><h4>&nbsp;3. Applicant is :<br>&nbsp; <% if(obj[20].toString().equalsIgnoreCase("N")) {%>
		  
            not under contractual obligation to serve STARC for any specific period. <%} 
		
		     else if(obj[20].toString().equalsIgnoreCase("Y")){ %>
	        
               under contractual obligation to serve STARC for a period from 
              <span class="text-blue"><%=rdf.format(sdf.parse(obj[21].toString())) %></span>  to <span class="text-blue" ><%=rdf.format(sdf.parse(obj[22].toString())) %></span>
              
            <%} %> </h4></td>
             
              
           </tr>
          
        </table> 
        
      
        
	 <br>
	 <br>
	 <br>
	 
	 <% if (obj[33]!=null){ %><div  style="margin-left:420px !important;" ><span class="text-blue"><%=obj[33]%></span></div><%} %>
	 <div  style="margin-left:470px !important;">P&A.Incharge </div> 
	
	<%--  <% if(obj[34]!=null){ %><div style="margin-left:400px !important;" > Approved Date : <span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[34].toString().substring(0, 10)) +" "+obj[34].toString().substring(11,19) %></span></div><%} %>	 --%>
		
		<br>
		<br>
		
		<% if(obj[24].toString().equalsIgnoreCase("APR")){ %>
		 <div style="margin-top:0x;text-align:center;"> 
		                         <span style="font-weight: 600; font-size: 16px;margin-left:0px;">APPROVED</span><br><br>
						        <span style="font-weight: 500; font-size: 16px;margin-left:0px;">CEO:&nbsp;<span class="text-blue" ><% if (Ceoname!=null){%><%=Ceoname[1] %><%} %></span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[34].toString().substring(0, 10)) +" "+obj[34].toString().substring(11,19) %></span></span><br>
		</div>	
		<%} %>
		
		<%} %>
	  			
	  	
	  							
	
</div> 
		
		

</body>
</html>
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
	font-weight:500
	
	
}	 
</style>

<title>NOC Passport Print</title>
</head>
<body>


	<%
	
	   SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
       String lablogo=(String)request.getAttribute("lablogo");
    
	%>


	<div align="center">
		<table style="margin-left:10px; margin-top:15px;width:650px;">
			<tr>
				<td class="text-center" rowspan="2"><span
					style="font-size: 30px"><img style="width: 80px; height: 80px; margin-top: -30px;margin-left: 10px;" align="left"   <%if(lablogo!=null ){ %> src="data:image/*;base64,<%=lablogo%>" alt="Configuraton"<%}else	{ %> alt="File Not Found" <%} %>></span> <br></td>
				<td class="text-center" width="300px" rowspan="2">APPLICATION FOR OBTAINING NO OBJECTION CERTIFICATE FOR PASSPORT  <br>
				
				</td>
				<td>STARC -BNG -<br>P&A-058
				</td>
				<td>Rev.: 01</td>
			</tr>
			<tr>
				<td>Date of Issue: <br>
				</td>
				<td>Total<br> Pages-3
				</td>
			</tr>
		</table>
		</div>

       <div style="text-align:center;"> 
		<h3 style="text-align: center;">PART - I </h3>
		 
								<table style="margin-top: 10px;margin-left:15px;width:650px;">	
									<tbody>
										
										<tr>
											<th>1.Name</th>
											<th>2.EmpNo</th>
											<th>3.Department</th>
											<th>4.Designation</th>
										</tr>
									<tr>
										
											<td class="text-blue" style="text-transform: uppercase;"><%=obj[1] %></td>
											<td class="text-blue" ><%=obj[0] %></td>
											<td class="text-blue" ><%=obj[3] %></td>
											<td class="text-blue" ><%=obj[2] %></td>
									</tr>
										
									</tbody>
								</table>
	<table style="margin-left:10px; margin-top:0px;border-collapse: collapse; width:650px;">
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
	<td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;5. Applicants Residential Address  </h4></td>
	<td  style="border: 0;padding: -10px;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -10px;"><h4> (a) Present  <span style="margin-left:70px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><span style="margin-left:-60px;"><%=obj[4] %></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -10px;"><h4> (b) Permanent  <span style="margin-left:50px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><span style="margin-left:-60px;"><%=obj[5] %></span></td>
	</tr>
	
		<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;6. Detail of <% if(obj[12].toString().equalsIgnoreCase("F")){%>Father<%} else if(obj[12].toString().equalsIgnoreCase("H")){ %> Husband<%} 
	else if (obj[12].toString().equalsIgnoreCase("G")){ %> Guardian <%} %> <br> 
       </h4></td>
      
    <td style="border: 0;padding: -10px;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -10px;"><h4>  (a) Name <span style="margin-left:75px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><span style="margin-left:-60px;"><%=obj[13] %></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -10px;"><h4>  (b) Occupation <span style="margin-left:40px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><span style="margin-left:-60px;"><%=obj[14]%></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;padding: -10px;"><h4>  (c) Address   <span style="margin-left:65px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><span style="margin-left:-60px;"><%=obj[15] %></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;7. Details of blood / close relations <br>&emsp; working in  foreign embassy / <span style="margin-left:35px;"> :</span><br> firms in India / Abroad 
         </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><% if (obj[16].toString().trim().length()>0){%><span style="margin-left:-60px;"><%=obj[16] %></span><%}  else{ %><span style="margin-left:-75px;">N/A</span><%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;text-align: left;padding: -10px;"><h4>&nbsp;8. Details of employment during <span style="margin-left:5px"> :</span><br> last ten years  
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><% if (obj[17].toString().trim().length()>0){%><span style="margin-left:-60px;"><%=obj[17] %></span><%}  else{ %><span style="margin-left:-75px;">N/A</span><%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;9. Details of passport held previously,<br> if any 	
      </h4> </td>
    
     <td style="border: 0;padding: -10px;"></td>
	</tr>
	
	
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
	
	
	
	</table>
	
	<table style="margin-left:10px; margin-top:0px;font-family:FontAwesome; width:650px;"> 
	 <tr>

     <td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4> 10. Details of passport lost, if any <span style="margin-left:10px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><% if(!obj[18].toString().equals("")){%><span style="margin-left:-60px"><%=obj[18] %></span><%} else{ %><span style="margin-left:-75px">N/A</span><%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4> 11. Type of passport required  <span style="margin-left:30px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><span style="margin-left:-60px"><%=obj[19] %></span></td>
	</tr>
	</table>
	
	<h1 class="break"></h1>
	
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
	
	 <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 16px;" align="left">
						
		<h4>  12. I certify that:<br>&emsp;
		  <br>&emsp; 
          (a) My application for the above passport is not for proceeding to a foreign 
           country. I shall <br> &emsp;&emsp; separately seek the NO OBJECTION CERTIFICATE  
           before proceeding to a foreign country. <br>&emsp;
           <br>&emsp;  
          (b) I am not involved in any court / police / disciplinary / vigilance case and there 
              is no <br> &emsp;&emsp; restriction placed by any authority. </h4>		   			
				   			
				   																			
		</div>
		
		
   <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 16px;" align="left">
						
		<h4>  13. I certify that:<br>&emsp;
		<%if(obj[20].toString().equalsIgnoreCase("N")) {%>
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
	<br>
	<div  style="margin-left:380px !important;" > Signature of the Applicant</div>	
	 <% if(obj[25]!=null) {%><div   style="margin-left:400px !important;">Forwarded Date :&nbsp;<span class="text-blue"><%=rdf.format(sdf.parse(obj[25].toString())) %></span> </div> <%} %>
	 <br>
	 <%-- <div align="left" style="margin-left:10px;" > Dept.Incharge : <span class="text-blue" ><%=obj[23] %></span></div>	 --%>
	 
	 <br>
	
	
	
	<% if(obj[27].toString().equalsIgnoreCase("APR")){ %>
	
	
	  <h1 class="break"></h1>
		<div><span style="margin-left: -80px;font-weight: 600;font-size: 16px;text-decoration: underline;">Filled by P&A Department</span></div> 
		  <table style="margin-left:10px; margin-top:0px;border-collapse: collapse;font-family:FontAwesome; width:650px;">
		  
		  
		 <tr>
		 <td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;1.(a) Are the entries given by the  <br>&emsp;&emsp;&emsp;applicant in 
             paras 1 to 6 in <span style="margin-left:40px"> :</span><br>&emsp;&emsp;Part - I correct?</h4></td>
            
            <td class="text-blue" style="border: 0;text-align: left;padding: -10px;"><% if(obj[28].toString().equalsIgnoreCase("Y")){ %><span style="margin-left:-60px;">YES</span><%} else{ %><span style="margin-left:-60px;">NO</span><%} %></td>
            
          </tr>
          
          <tr>
          <td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;(b) If not, mention variations <span style="margin-left:25px"> :</span> </h4></td>
          <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><span style="margin-left:-60px;"><%=obj[29] %></span></td>
          </tr>
          
          <tr>
           <td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;2.(a) Whether the employee is <span style="margin-left:20px"> :</span> <br> &emsp; &emsp; &emsp; under suspension?
           </h4></td>
           
           <td class="text-blue" style="border: 0;text-align: left;padding: -10px;"><% if(obj[30].toString().equalsIgnoreCase("Y")){ %><span style="margin-left:-60px;"> YES</span><%} else{ %><span style="margin-left:-60px;">NO</span><%} %></td>
           
           </tr>
        
        
          
           
      <!--  <table style="margin-left:10px; margin-top:35px;border-collapse: collapse;font-family:FontAwesome; width:650px;"> -->
           <tr>
           <td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;
               (b) Whether the employee is <br> &emsp;&emsp;involved in any	
	           Disciplinary <span style="margin-left:30px"> :</span><br>&emsp;&emsp;/Criminal / Corruption /<br>&emsp; &emsp;Court
	          Case </h4></td> 
	         
	          <td class="text-blue" style="border: 0;text-align: left;padding: -10px;"><% if(obj[31].toString().equalsIgnoreCase("Y")){ %><span style="margin-left:-60px;">YES</span><%} else{ %><span style="margin-left:-60px;">NO</span><%} %></td>
	          
	        </tr>
	        
	        <tr>
	        <td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>
                &nbsp;(c) If so, details of the case<span style="margin-left:40px"> :</span> </h4></td> 
            
            <td class="text-blue" style="border: 0;text-align: justify;padding: -10px;"><span style="margin-left:-60px;"><%=obj[32] %></span></td>
            
           </tr>
        
           <tr>
           <td style="width:350px;text-align: left;border: 0;padding: -10px;"><h4>&nbsp;3. Applicant is<span style="margin-left:120px"> :</span></h4></td>
             
              
            <td  style="border: 0;text-align: justify;font-weight:400px;padding: -10px;"><% if(obj[20].toString().equalsIgnoreCase("N")) {%>
		  
            <span style="margin-left:-60px;"><br>not under contractual obligation to serve STARC for any specific period.</span> <%} 
		
		     else if(obj[20].toString().equalsIgnoreCase("Y")){ %>
	        
                <span style="margin-left:-60px;text-align: justify;"> <br>under contractual obligation to serve STARC for a period from 
              <%=rdf.format(sdf.parse(obj[21].toString())) %>  to <%=rdf.format(sdf.parse(obj[22].toString())) %></span>
              
            <%} %>
          
          </td>
          </tr>
        </table> 
        
      
        
	 <br>
	 <br>
	 <br>
	 
	 <% if (obj[33]!=null){ %><div  style="margin-left:330px !important;" ><span class="text-blue"><%=obj[33]%></span></div><%} %>
	 <div  style="margin-left:380px !important;">P&A.Incharge </div> 
	
	 <% if(obj[34]!=null){ %><div style="margin-left:470px !important;" > Approved Date : <span class="text-blue" ><%=rdf.format(sdf.parse(obj[34].toString())) %></span></div><%} %>	
		
	   <%} %>
	  							
	
</div> 
		
		

</body>
</html>
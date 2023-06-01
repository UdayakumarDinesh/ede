<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
 <%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%> 
<%@page import="com.vts.ems.noc.model.NocProceedingAbroad"%> 
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Proceeding Abroad Preview</title>

<style>
.text-blue
{
	color: blue;
	font-weight:500px;
	font-size: 18px;
}

 td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
	font-size:18px;
} 

th{
text-align: left;
width:75%;
font-weight:100;
}	

h4{
font-size:18px;

}

body {
  
  overflow-x: hidden;
 
}
</style>
</head>

<body>


<%
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
    String LabLogo = (String)request.getAttribute("LabLogo");
	 List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
     List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");
     
     List<String> FormStatus = Arrays.asList("FWD","VGI","VDI","VDG");
     
     List<Object[]> RemarksHistory=(List<Object[]>)request.getAttribute("NOCProceedingAbroadRemarkHistory");
     String isApproval = (String)request.getAttribute("isApproval");
     Object[] obj=(Object[])request.getAttribute("NocProcAbroadDetails");
     
     
    
     String EmpNo=(String)request.getAttribute("EmpNo");
     
     NocProceedingAbroad ProcAbroad=(NocProceedingAbroad)request.getAttribute("ProcAbroad");
     
     
     Object[] empData=(Object[])request.getAttribute("EmpData");
 	
     String CEOempno=(String)request.getAttribute("CEOempno");
     
     Object[] Ceoname=(Object[])request.getAttribute("CeoName");
     
     List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
     
 	List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
 	List<String> DHs = (List<String>)request.getAttribute("DivisionHeadEmpNos");
 	List<String> GHs = (List<String>)request.getAttribute("GroupHeadEmpNos");
 	
 	 List<String> RecommendStatus = Arrays.asList("VGI","VDI","VDG");
    %>

<div class="card-header page-top "> 
		<div class="row">
			<div class="col-md-4">
				<h5>Proceeding Abroad Preview</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
							<li class="breadcrumb-item "><a href="NocApproval.htm">NOC Approval</a></li>
					<li class="breadcrumb-item active" aria-current="page">
						Preview</li>
						<%}
						else{%>
						<li class="breadcrumb-item "><a href="ProceedingAbroad.htm">Proceeding Abroad List</a></li>
					<li class="breadcrumb-item active" aria-current="page">Proceeding Abroad
						
						<%} %>
						
				</ol>
			</div>

		</div>
	

</div>


<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){ %>
	
		<div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
		</div>
		<%}if(ses!=null){ %>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
	<%} %>
		

   <div class="page card dashboard-card"> 
		<div class="card-body"  >
			<div class="card" style="padding-top:0px;margin-top: -15px;width:75%;margin-left:160px;">
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"  align="left">
				<form action="##"  >
				<div class="" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
				
								<div align="center">
								<div style="width: 100%;">
									<div style="width: 100%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
									 <div ><span style="margin-left: 0px;font-weight: 600;font-size: 18px;">APPLICATION FOR OBTAINING NO OBJECTION CERTIFICATE FOR PROCEEDING ABROAD</span></div> 
									
				
								</div>
								</div>
								 <div align="center"><span style="border: 0px;font-weight: 600;font-size:18px;">PART-I </span> </div> 
								
								

       <div style="text-align:center;">
       
       <table style="margin-top: 55px;margin-left:15px;width:850px;border: 1px solid black;">	
									<tbody>
										
										<tr>
											<th style="border: 1px solid black;width:250px;text-align:center;">1.Name</th>
											<th style="border: 1px solid black;width:0px;text-align:center;">2.EmpNo</th>
							                <th style="border: 1px solid black;width:280px;text-align:center;">3.Designation</th>				
											<th style="border: 1px solid black;width:50px;text-align:center;">4.Department</th>
											
										</tr>
									<tr>
										
											<td class="text-blue" style="text-transform: uppercase;"><% if(obj[1]!=null){%><%=obj[1] %><%} %></td>
											<td class="text-blue" style="text-align:center;" ><%=obj[0] %></td>
											<td class="text-blue" ><%=obj[2] %></td>
											<td class="text-blue" style="text-align:center;"><%=obj[3] %></td>
											
									</tr>
										
									</tbody>
								</table>
		
	<table style="margin-left:10px; margin-top:25px;border-collapse: collapse; width:100%;">
	
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>5.  Applicants Residential Address  </h4></td>
	<td  style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4> (a) Present  <span style="margin-left:50px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-223px"><%=obj[4] %>,<%=obj[5] %>,<%=obj[6] %>,<%=obj[7] %></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4> (b) Permanent  <span style="margin-left:23px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-223px"><%=obj[8] %>,<%=obj[9] %>,<%=obj[10] %>,<%=obj[11] %></span></td>
	</tr>
	
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 6. Detail of <% if(obj[18].toString().equalsIgnoreCase("F")){%>Father<%} else if(obj[18].toString().equalsIgnoreCase("H")){ %> Husband<%} 
	else if (obj[18].toString().equalsIgnoreCase("G")){ %> Guardian <%} %>
	 <br> 
       </h4></td>
      
    <td style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (a) Name <span style="margin-left:79px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-225px"><%=obj[19] %></span></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (b) Occupation <span style="margin-left:32px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-226px"><%=obj[20] %></span></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (c) Address<span style="margin-left:60px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-225px"><%=obj[21] %></span></td>
	</tr>
	
	</table>
	
	<table  style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:100%;" >
	<tr>
	<td style="width:100%;text-align: left;border: 0;"><h4>7. Details of blood&nbsp;/&nbsp;close relations working in foreign embassy&nbsp;/&nbsp;firms in India&nbsp;/&nbsp;Abroad 
         :  <span class="text-blue"><% if(obj[22].toString().equalsIgnoreCase("NA")){%><%=obj[22] %><%} %></span>  </h4> </td>
    </tr>
    
     <% if(!obj[22].toString().equalsIgnoreCase("NA")){%>
    <tr>
    
    <td class="text-blue" style="border: 0;text-align:justify;"><%=obj[22] %></td>
    
	</tr>
	<%} %>
	
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 8. Details of employment during  last ten years  
       :  <span class="text-blue"><% if(obj[23].toString().equalsIgnoreCase("NA")){%><%=obj[23] %><%} %></span></h4> </td>
    </tr>
      
      <% if(!obj[23].toString().equalsIgnoreCase("NA")){%>
     <tr>
     
    <td class="text-blue" style="border: 0;text-align:justify"><%=obj[23] %></td>
    
	</tr>
	<%} %>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 9. Are you involved in any court / police / vigilance case for which your presence is required in India :
	<span class="text-blue"><% if (obj[24].toString().equalsIgnoreCase("Y")){%>Yes<%} else{ %>No<%} %></span>
	  </h4></td>
    </tr>
    
    
	
	<tr>
	  <td style="width:350px;text-align: left;border: 0;"><h4> 10. Whether Annual Property Return of the previous year has been filed :
	  <span class="text-blue"><% if (obj[25].toString().equalsIgnoreCase("Y")){%>Yes<%} else{ %>No<%} %></span>
	  </h4></td>
	</tr>
	
	
    <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>11. Details of passport held previously, if any : &nbsp;<% if(obj[12].toString().equalsIgnoreCase("NA") && obj[13].toString().equalsIgnoreCase("NA") && obj[14].toString().equalsIgnoreCase("NA") &&  obj[15].toString().equalsIgnoreCase("NA")){ %><span class="text-blue">NA</span><%} %>	
      </h4> </td>
    </tr>
     
	<% if(!obj[12].toString().equalsIgnoreCase("NA") && !obj[13].toString().equalsIgnoreCase("NA") &&  !obj[14].toString().equalsIgnoreCase("NA") && !obj[15].toString().equalsIgnoreCase("NA")){ %> 
	<table style="margin-top: 0px;margin-left:15px;width:850px;border: 1px solid black;">	
									<tbody>
										
										<tr>
											<th style="border: 1px solid black;width:50px;text-align:center;">Type</th>
											<th style="border: 1px solid black;width:0px;text-align:center;">Passport No</th>
							                <th style="border: 1px solid black;width:50px;text-align:center;">Date of Issue</th>				
											<th style="border: 1px solid black;width:50px;text-align:center;">Validity</th>
											
										</tr>
									<tr>
										
											<td class="text-blue" ><%=obj[12]%></td>
											<td class="text-blue" style="text-align:center;"><%=obj[13] %></td>
											<td class="text-blue" style="text-align:center;"><%=rdf.format(sdf.parse(obj[14].toString())) %></td>
											<td class="text-blue" style="text-align:center;"><%=rdf.format(sdf.parse(obj[15].toString())) %></td>
											
									</tr>
										
									</tbody>
								</table>
	
	 <%} %> 
	
	</table>
	
	<table style="margin-left:10px; margin-top:2px;border: none;font-family:FontAwesome; width:100%;"> 
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 12. Details of passport lost, if any : <%if(obj[26].toString().equalsIgnoreCase("NA")){ %><span class="text-blue">NA</span><%} %>
       </h4> </td>
       
     </tr>  
    
    <% if(!obj[26].toString().equalsIgnoreCase("NA")){%>
    <tr>
    <td class="text-blue" style="border: 0;width:100%;text-align:justify;"><%=obj[26] %></td>
	</tr>
	
	<%} %>
	
	<tr>
	<td style="width:427px;text-align: left;border: 0;"><h4> 13. Have you visited any foreign country before? :
	<%if(obj[27].toString().equals("Y")){ %><span class="text-blue">Yes</span><%} else{ %><span class="text-blue">No</span><%} %> 
	&nbsp; <% if (obj[27].toString().equalsIgnoreCase("Y")){%> <span class="text-blue"><% if(obj[28].toString().trim().length()>0){ %><%=obj[28] %><%} %> </span> <%} %>
	
	
	</h4></td>
    </tr> 
 
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>14. Countries proposed to be visited : <span class="text-blue"><%=obj[29] %></span>
	   </h4></td>
     </tr> 
     
  
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>15. Date of departure : <span class="text-blue"> <%=rdf.format(sdf.parse(obj[30].toString())) %></span>
	   </h4></td>
     <tr> 
    
   
	<tr>
	<td style="width:100%;text-align: left;border: 0;"><h4>16. Purpose of visit : <span class="text-blue"><%=obj[31] %></span>
	   </h4></td>
     </tr>
   
	<tr>
	<td style="width:100%;text-align: left;border: 0;"><h4>17. Probable duration of stay at each country
	  :  <span class="text-blue"><%=obj[32] %></span></h4></td>
      
    </tr>
    
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>18. Probable date of return : <span class="text-blue"><%=rdf.format(sdf.parse(obj[33].toString())) %></span>
	   </h4></td>
      </tr>
   
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>19. Whether going alone or with family : <span class="text-blue"><% if(obj[34].toString().equalsIgnoreCase("A")) {%>Alone<%} else {%>With Family<%} %></span>
	   </h4></td>
      </tr>
  
	
	<% if(obj[34].toString().equalsIgnoreCase("WF")) {%>
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> &nbsp;&nbsp; (a) Details of family : <span class="text-blue"><%=obj[35] %></span>
	   </h4></td>
      </tr>
      
      
	<%} %>
	
	<tr>
	<td style="text-align: left;border: 0;"><h4>20. Approximate amount expected to be incurred for the trip including journey and stay abroad : Rs <span class="text-blue"><%=obj[36]%></span>
	 </h4></td>
      </tr>
   
	
  <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>21. Trip financed by : <span class="text-blue"><% if(obj[37].toString().equalsIgnoreCase("S")){ %>SELF<%} else{ %>OTHER PEOPLE<%} %></span>
	
	   </h4></td>
      </tr>
   
  <% if(obj[37].toString().equalsIgnoreCase("S")) { %>
  <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4> (a)Source of Amount Being Spent : <span class="text-blue"><%=obj[38] %></span>
	
	   </h4></td>
   </tr>   
    
	<%}
	
  else if(obj[37].toString().equalsIgnoreCase("OP")){ %> 
  
  
  <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4> &nbsp;&nbsp; (a) Name : <span class="text-blue"><%=obj[39] %></span>
	  </h4></td>
   </tr>  
    
    
  <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4> &nbsp;&nbsp; (b) Nationality : <span class="text-blue"><%=obj[40] %></span>
	  </h4></td>
   </tr>  
  
  
   <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4> &nbsp;&nbsp; (c) Relationship :  <span class="text-blue"><%=obj[41] %> </span>
	 </h4></td>
    </tr>  
    
  
   <tr>
  <td style="width:350px;text-align: left;border: 0;"><h4> &nbsp;&nbsp; d)Address :  <span class="text-blue"><%=obj[42] %> </span>
	</h4></td>
    </tr>
    
   <%} %> 
   
  <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>22. Are you likely to accept any foreign Hospitality
        : <span class="text-blue"><% if(obj[43].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %> </span></h4></td>
      
  </tr>

	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 23. I certify that :
       </h4> </td>
    </tr>
    
    <tr>
    <td  class="tabledata" style="border: 0;"><h4>
           <% if (obj[24].toString().equalsIgnoreCase("N")){%>
           <span style="color:red; font-size:17px;">I am not involved in any court / police / disciplinary / vigilance case and there 
              is no restriction placed by any authority.</span> <%} %><br>
              I undertake that I will return my Identity Card before proceeding abroad <br>
              I undertake that I shall not settle permanently abroad and rejoin my duty after expiry 
              of leave <br>
              <% if(obj[44].toString().equalsIgnoreCase("N")) {%> 
		  
           I am not under contractual obligation to serve STARC for any specific period.
		
		<%} else{ %>
	        
            I am under contractual obligation to serve STARC for a period from 
              <span class="text-blue"><%=rdf.format(sdf.parse(obj[45].toString())) %></span>  to   <span class="text-blue"><%=rdf.format(sdf.parse(obj[46].toString())) %></span>
              
              <%} %>
           </h4>
              
     </td>
   </tr>
</table>

	<br>
	 <% if(obj[49]!=null) {%><div   style="margin-left:500px !important;">Forwarded On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[49].toString().substring(0, 10)) +" "+obj[49].toString().substring(11,19) %></span> </div>
	 <hr>
	  <%} %>
	 
	 <!-- ----------------------------------------Form filled by the department---------------------------------------------------------------- -->
	 
	 <% if(obj[54]!=null && obj[55]!=null && obj[56]!=null && (isApproval==null)){ %>
	
		  <div>
		  <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">PART-II</span><br>
          <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">SECTION - A</span><br>
		  
		  <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">Filled by Department in which individual is Employed</span></div> 
		  <table class="table2" style="margin-left:10px; margin-top:10px;border-collapse: collapse; width:100%;">
		  
		 <tr>
		 
		   <td style="text-align: left;border: 0;width:89%;"><h4> 1. Is the applicant handling any classified work divulgence of which may affect the security of service and the country? : &nbsp;<span class="text-blue"><% if(obj[54].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %> </span></h4></td>
             
            </tr>
            
           <tr>
          
           <td style="width:50%;text-align: left;border: 0;"><h4>2. Is the individual's visit recommended even if the answer to Sl. No. 1 above is in the affirmative?
             : <span class="text-blue"><% if(obj[55].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span> 
           </h4></td>
           
           </tr>
           
           
           <tr>
           <td style="width:50%;text-align: left;border: 0;"><h4>3. Whether the leave will be granted for proceeding	abroad?
            :&nbsp;<span class="text-blue"><% if(obj[56].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span></h4></td> 
	         </tr>
	      </table> 
	      
	      <% int count=1;
	 
	 for(Object[] rh:RemarksHistory) {
		 
		 if(RecommendStatus.contains(rh[4].toString())){
		 
			 if(count==1){%>
				 <div align="left" style="margin-left:550px !important;">Recommended By :<span class="text-blue"><%=rh[2] %></span> </div>
	              <div  align="left" style="margin-left:550px !important;">Recommended On :<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(rh[3].toString().substring(0, 10)) +" "+rh[3].toString().substring(11,19) %> </span></div>
	              
	              <br>
			<%} 
		else{%>
	 <div align="left" style="margin-left:5px !important;">Recommended By :<span class="text-blue"><%=rh[2] %></span> </div>
	 <div  align="left" style="margin-left:5px !important;">Recommended On :<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(rh[3].toString().substring(0, 10)) +" "+rh[3].toString().substring(11,19) %> </span></div>
	 <br>
	 
	 <%}count++;
	
			 }} %>
			 
			 
	      
	     <hr>
	      <%}  %>
	      
	      
	      <!-- ------------------------------------------------------------------------------------------------------------------------------- -->
	      
	      <!-- ---------------------------------------------------------------------------------Form showing to p&A dept-------------------------------------------------- -->
	      
	        <% if(PandAs.contains(EmpNo) || CEOempno.contains(EmpNo)) { %>
	      
	       <div>
		  <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">PART-II</span><br>
          <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">SECTION - A</span><br>
		  
		  <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">Filled by Department in which individual is Employed</span></div> 
		  <table class="table2" style="margin-left:10px; margin-top:10px;border-collapse: collapse; width:100%;">
		  
		 <tr>
		 
		   <td style="text-align: left;border: 0;width:89%;"><h4> 1. Is the applicant handling any classified work divulgence of which may affect the security of service and the country? : &nbsp;<span class="text-blue"><% if(obj[54].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %> </span></h4></td>
             
         </tr>
            
           <tr>
             <td style="width:50%;text-align: left;border: 0;"><h4>2. Is the individual's visit recommended even if the answer to Sl. No. 1 above is in the affirmative?
             : <span class="text-blue"><% if(obj[55].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span> 
           </h4></td>
           
           </tr>
           
           
           <tr>
           <td style="width:50%;text-align: left;border: 0;"><h4>3. Whether the leave will be granted for proceeding abroad?
            :&nbsp;<span class="text-blue"><% if(obj[56].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span></h4></td> 
	         </tr>
	      </table> 
	      <hr>
	      <%} %> 
	      
	      <!-- ---------------------------------------------------------------------------------------------------------------------------- -->
	      
          <% if(obj[57]!=null  && obj[60]!=null && (isApproval==null || CEOempno.contains(EmpNo))){ %>
          
		  <div>
		   <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">SECTION -B</span><br>
		  <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center">Filled by P&A Department</span></div> 
		  <table class="table2" style="margin-left:10px; margin-top:10px;border-collapse: collapse; width:100%;">
		  
		 <tr>
		    <td style="text-align: left;border: 0;width:89%;"><h4> 1.(a) Are the entries given by the applicant in paras 1 to 6 in Part - I correct?  : &nbsp;<span class="text-blue"><% if(obj[57].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %> </span></h4></td>
         </tr>
            
           <% if(obj[57].toString().equalsIgnoreCase("N")){ %>
          
          <tr>
          
             <td style="width:60%;text-align: left;border: 0;"><h4>(b) If not, mention variations </h4></td>
         
          </tr>
          
          <tr>
             <td class="text-blue" style="border: 0;text-align: justify;width:100%;"><%=obj[58] %></td>
          </tr>
          <%} %>
          
         <tr>
          
           <td style="width:50%;text-align: left;border: 0;"><h4>2.(a) Was the individual ever involved in any department enquiry or other cases?
            :&nbsp; <span class="text-blue"><% if(obj[24].toString().equalsIgnoreCase("Y")){ %>Yes<%} 
           else{ %>No<%} %></span>
           </h4></td>
           
         </tr>
         
         <% if(obj[24].toString().equalsIgnoreCase("Y")){ %>
	        <tr>
	        
	        <td style="width:50%;text-align: left;border: 0;"><h4>
                (c) If so, details of the case</h4></td> 
            
            </tr>
            <tr>
            <td class="text-blue" style="border: 0;text-align: justify;"><%=obj[59] %></td>
            
           </tr>
           <%} %>
           
            <tr>
          
           <td style="width:50%;text-align: left;border: 0;"><h4>3. Are any financial dues outstanding against the applicant?
           
            :&nbsp; <span class="text-blue"><% if(obj[60].toString().equalsIgnoreCase("Y")){ %>Yes<%} 
            else{ %>No<%} %></span>
           </h4></td>
           
         </tr>
           
           
           </table> 
          
           <table class="table2" style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:100%;"> 
           <tr>
           <td style="text-align: left;border: 0;"><h4>4. Applicant is : <% if(obj[44].toString().equalsIgnoreCase("N")) {%> not under contractual obligation to serve STARC for any specific period.
           <%} else if(obj[44].toString().equalsIgnoreCase("Y")){%>
            under contractual obligation to serve STARC for a period from 
              <span class="text-blue"   ><%=rdf.format(sdf.parse(obj[45].toString())) %></span> to <span class="text-blue"  ><%=rdf.format(sdf.parse(obj[46].toString())) %></span>
           
           <%} %>
             </h4></td>
           
           </tr>
          
          </table>
          
          <br>
          <% if (obj[52]!=null){ %><div  style="margin-left:430px !important;" ><span class="text-blue"><%=obj[52]%></span></div>
	 <div  style="margin-left:470px !important;">P&A.Incharge </div> 
      <%} %>
          
           
           <hr>
          <%} %>
          
          
	
	  	<% if(obj[48]!=null && toUserStatus.contains(obj[48].toString())) { %>
	  	<div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;">
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100"   name="remarks"  maxlength="500"></textarea>
				  </div>
                <button type="submit" class="btn btn-sm submit-btn"  name="Action" value="A"  formaction="NOCProcAbroadForward.htm"  onclick="return confirm('Are You Sure To Submit?');" >Forward</button>
                
				</div>
		<%} %>
		
		
		<!-- ----------------------------------------  P&A Department-------------------------------------------------------------------------------------------- -->
		
		 
		  <!-- <div><span style="margin-left: -80px;font-weight: 600;font-size: 18px;text-decoration: underline;">Filled by P&A Department</span></div> 
		  <table style="margin-left:10px; margin-top:35px;border-collapse: collapse; width:100%;">
		 <tr>
		 <td style="width:400px;text-align: left;border: 0;"><h4> 1.(a) Are the entries given by the <span style="margin-left:17px"> :</span> <br>&emsp;&emsp;applicant in 
             paras 1 to 6 in <br>&emsp;&emsp;Part - I correct?</h4></td>
            
            <td class="text-blue" style="border: 0;text-align: left;"></td>
            
          </tr>
          
          <tr>
          <td style="width:400px;text-align: left;border: 0;"><h4>&emsp;(b) If not, mention variations <span style="margin-left:35px"> :</span> </h4></td>
          <td class="text-blue" style="border: 0;"></td>
          </tr>
          
          <tr>
           <td style="width:350px;text-align: left;border: 0;"><h4>2.(a) Whether the employee is <span style="margin-left:40px"> :</span> &emsp; &emsp; &emsp; under suspension?
           </h4></td>
           
           <td class="text-blue" style="border: 0;"></td>
           
           </tr>
           <tr>
           <td style="width:350px;text-align: left;border: 0;"><h4>&emsp;
               (b) Whether the employee is <span style="margin-left:31px"> :</span><br> &emsp;&emsp;involved in any	
	           Disciplinary <br>&emsp;&emsp;/Criminal / Corruption /<br>&emsp; &emsp;Court
	          Case </h4></td> 
	         
	          <td class="text-blue" style="border: 0;"></td>
	          
	        </tr>
	        
	        <tr>
	        <td style="width:350px;text-align: left;border: 0;"><h4>
                &emsp;(c) If so, details of the case<span style="margin-left:62px"> :</span> </h4></td> 
            
            <td class="text-blue" style="border: 0;"></td>
            
           </tr>
           
           <tr>
           <td style="width:350px;text-align: left;border: 0;"><h4> &nbsp;3. Applicant is<span style="margin-left:197px"> :</span>  
             
              
            <td  style="border: 0;">
		  
            not under contractual obligation to serve STARC for any specific period.  
		
		    
	        
                under contractual obligation to serve STARC for a period from 
              <span class="text-blue"   ></span>  to   <span class="text-blue"  ></span>
              
          
          
          </td>
          </tr>
        </table> 
         -->
        
        
        
        <% if( (GHs.contains(EmpNo) && FormStatus.contains(obj[48].toString())) || (DHs.contains(EmpNo) &&FormStatus.contains(obj[48].toString()))  || (DGMs.contains(EmpNo) && FormStatus.contains(obj[48].toString()))) {%>
      
          <div><span style="font-weight: 600;font-size: 18px;text-decoration: underline;tex-align:center;">PART-II</span><br>
          <span style="font-weight: 600;font-size: 18px;text-decoration: underline;tex-align:center;">SECTION - A</span><br>
          <span style="font-weight: 600;font-size: 18px;text-decoration: underline;tex-align:center;">To be completed by the Department in which the individual is employed</span>
          </div> 
         <div class="card" >
		<div class="card-body" >
        <div class="form-group" style="margin-bottom:-2rem;">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 100%;">
								       <tr>
											<th><label>1. Is the applicant handling any classified work divulgence of which may affect the security of service and the country?
											
                                                  <span class="mandatory"	style="color: red;">*</span></label></th>
											<td>
											<select class="form-control select2"  name="WorkHandled" id="workhandled" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="N" <% if(ProcAbroad!=null &&  ProcAbroad.getWorkHandled()!=null){ if(ProcAbroad.getWorkHandled().toString().equalsIgnoreCase("N")){%> selected  <% } }%>>No</option>
												<option value="Y" <% if(ProcAbroad!=null &&  ProcAbroad.getWorkHandled()!=null){ if(ProcAbroad.getWorkHandled().toString().equalsIgnoreCase("Y")){%> selected  <% } }%>>Yes</option>
												
												
											    </select></td>
										</tr>
										
										<tr>
											<th>
											<label>2. Is the individuals visit recommended even if the answer to Sl.No.1 above is in the affirmative <span class="mandatory"	style="color: red;">*</span></label></th>
											<td>
											<select class="form-control select2"  name="VisitRecommended" id="visitrecomnd" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="N"  <% if(ProcAbroad!=null &&  ProcAbroad.getVisitRecommended()!=null){ if(ProcAbroad.getVisitRecommended().toString().equalsIgnoreCase("N")){%> selected  <% } }%>  >No</option>
												<option value="Y"   <% if(ProcAbroad!=null &&  ProcAbroad.getVisitRecommended()!=null){ if(ProcAbroad.getVisitRecommended().toString().equalsIgnoreCase("Y")){%> selected  <% } }%> >Yes</option>
											</select>
											
										 </td>
										</tr>
										
										<tr>
											<th><label>3. Whether the leave will be granted for proceeding abroad <span class="mandatory"	style="color: red;">*</span></label></th>
											<td>
											<select class="form-control select2"  name="LeaveGranted" id="leavegranted" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												
												<option value="N"  <% if(ProcAbroad!=null &&  ProcAbroad.getLeaveGranted()!=null){ if(ProcAbroad.getLeaveGranted().toString().equalsIgnoreCase("N")){%> selected  <% } }%> >No</option>
												<option value="Y"  <% if(ProcAbroad!=null &&  ProcAbroad.getLeaveGranted()!=null){ if(ProcAbroad.getLeaveGranted().toString().equalsIgnoreCase("Y")){%> selected  <% } }%>  >Yes</option>
											</select>
											</td>
										</tr>	
																		
								</table>
								
								<button type="submit"  class="btn btn-sm update-btn"  style="margin-bottom:28px;margin-top:0px;margin-left:0px"   formaction="NOCProcAbroadDeptDetailsUpdate.htm"   name="ProcAbrId" value="<%=obj[16] %>"  onclick="return confirm('Are You Sure To Update?');">Update</button>
								
							</div>
						</div>
			       </div>
			    </div>
			    
			  <%} %> 
			  
			  
		<% if(PandAs.contains(EmpNo)) { %>
		
         <div>
         <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">SECTION -B</span><br>
         <span style="font-weight: 600;font-size: 18px;text-decoration: underline;text-align:center;">To be completed by the P&A Department</span></div> 
         <div class="card" >
		 <div class="card-body" >
         <div class="form-group" style="margin-bottom:-1rem;">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 100%;">
								       <tr>
											<th style="text-align:left;"><label>1.(a) Are the entries given by the applicant in paras 1 to 11 in Part - I correct?
                                                  <span class="mandatory"	style="color: red;">*</span></label></th>
											<td>
											<select class="form-control select2"  name="PassportEntries" id="Entries" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="N"  <% if(ProcAbroad!=null &&  ProcAbroad.getProcAbroadEntries()!=null){ if(ProcAbroad.getProcAbroadEntries().toString().equalsIgnoreCase("N")){%> selected  <% } }%> >No</option>
												<option value="Y" <% if(ProcAbroad!=null &&  ProcAbroad.getProcAbroadEntries()!=null){ if(ProcAbroad.getProcAbroadEntries().toString().equalsIgnoreCase("Y")){%> selected  <% } }%> >Yes</option>
												
												</select></td>
										</tr>
										
										
																		
										<tr class="trow">
											<th style="text-align:left;"><label>(b) If not, mention variations<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control" 
												placeholder="" type="text" name="EntryDetails" value="<%if(ProcAbroad!=null && ProcAbroad.getProcAbroadEntriesDetails()!=null ){%><%=ProcAbroad.getProcAbroadEntriesDetails() %><%} %>"
												  style="font-size:15px;"></td>
										</tr>
										
										
																		
										<tr>
											<th style="text-align:left;"><label>2.(a) Was the individual ever involved in any department enquiry or other cases?

											<span class="mandatory"	style="color: red;">*</span></label></th>
											<td>
											    <select class="form-control select2"  name="EmpInvolvment" id="Involved" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="N" <% if(ProcAbroad!=null &&  ProcAbroad.getEmployeeInvolvement()!=null){ if(ProcAbroad.getEmployeeInvolvement().toString().equalsIgnoreCase("N")){%> selected  <% } }%>>No</option>
												<option value="Y" <% if(ProcAbroad!=null &&  ProcAbroad.getEmployeeInvolvement()!=null){ if(ProcAbroad.getEmployeeInvolvement().toString().equalsIgnoreCase("Y")){%> selected  <% } }%>>Yes</option>
												
												</select>
											 </td>
										</tr>
										
																			
										<tr class="trow1">
											<th  style="text-align:left;"><label> (b) If so, nature of enquiry and its results? <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control" 
												placeholder="" type="text" id="" name="CaseDetails" value="<%if(ProcAbroad!=null && ProcAbroad.getEmployeeCaseDetails()!=null ){%><%=ProcAbroad.getEmployeeCaseDetails() %><%} %>"
												  style="font-size: 15px;"></td>
										</tr>
										
										<tr>
											<th  style="text-align:left;"><label>3. Are any financial dues outstanding against the applicant?
											 <span class="mandatory " style="color: red;">*</span></label></th>
											<td>
											  <select class="form-control select2"  name="EmpDues" id="dues" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="N" <% if(ProcAbroad!=null &&  ProcAbroad.getEmployeeDues()!=null){ if(ProcAbroad.getEmployeeDues().toString().equalsIgnoreCase("N")){%> selected  <% } }%> >No</option>
												<option value="Y" <% if(ProcAbroad!=null &&  ProcAbroad.getEmployeeDues()!=null){ if(ProcAbroad.getEmployeeDues().toString().equalsIgnoreCase("Y")){%> selected  <% } }%> >Yes</option>
											   </select>
											 </td>
										</tr>	
											
										
										<tr>
											<th  style="text-align:left;"><label>4. Is the applicant under contractual obligation to serve STARC for any specific period? If so, specify the period.
                                              <span class="mandatory" style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="obligation" id="oblig" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												
												<option value="N"  <% if(ProcAbroad!=null &&  ProcAbroad.getContractualObligation()!=null){ if(ProcAbroad.getContractualObligation().toString().equalsIgnoreCase("N")){%> selected <% } }%> >No</option>
												<option value="Y"  <% if(ProcAbroad!=null &&  ProcAbroad.getContractualObligation()!=null){ if(ProcAbroad.getContractualObligation().toString().equalsIgnoreCase("Y")){%> selected <% } }%> >Yes</option>
												
											  </select>
											 </td>
											 
											 </tr>
											
											 <tr  class="trow3">
											<th  style="text-align:left;"><label> From Date<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control input-sm mydate" 
												placeholder="" type="text" id="fromdate" name="FromDate" value="<% if(ProcAbroad!=null && ProcAbroad.getFromDate()!=null ){%><%=rdf.format(sdf.parse(ProcAbroad.getFromDate()))%><%} %>"
												required="required"  style="font-size: 15px;"></td>
										</tr >
											 
											 <tr class="trow4" >
											<th  style="text-align:left;"> <label> To Date<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control input-sm mydate" 
												placeholder="" type="text" id="todate" name="ToDate"  value="<% if(ProcAbroad!=null && ProcAbroad.getFromDate()!=null ){%><%=rdf.format(sdf.parse(ProcAbroad.getFromDate()))%><%} %>"
												required="required"  style="font-size: 15px;"></td>
										</tr>
										
							
												
								</table>
								
								
								<button type="submit"  class="btn btn-sm update-btn"  style="margin-top:0px;margin-left:0px"   formaction="NOCProcAbroadPAForm.htm"   name="ProcAbrId" value="<%=obj[16]%>"  onclick="return confirm('Are You Sure To Update?');">Update</button>
								
							
								<%-- <a type="button"  class="btn btn-sm update-btn"  style="margin-bottom:28px;margin-top:28px;margin-left:0px"   href="NOCPassportPAForm.htm?PassportId=<%=obj[10] %>" onclick="return confirm('Are You Sure To Update?');">Update</a>  --%>
							</div>
							
							
						</div>
			       
			      </div>
			    </div>
			    <%} %>
			    
			    <% if(obj[48].toString().equalsIgnoreCase("APR")){ %>
		 <div style="margin-top:0x;text-align:center;"> 
		                         <span style="font-weight: 600; font-size: 16px;margin-left:0px;">APPROVED</span><br><br>
						        <span style="font-weight: 500; font-size: 16px;margin-left:0px;">CEO:&nbsp;<span class="text-blue" ><% if (Ceoname!=null){%><%=Ceoname[1] %><%} %></span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[53].toString().substring(0, 10)) +" "+obj[53].toString().substring(11,19) %></span></span><br>
		</div>	
		<%} %>
			      
			     <% if(RemarksHistory.size()>1){ %>
				  
				  <div class="col-md-5" align="left" style="margin-left:1rem;margin-top:2rem; padding:0px;border: 1px solid black;border-radius: 5px;">
				<table style="margin: 3px;padding: 0px;">
					<tr>
						<td style="border:none;padding: 0px">
							<h5 style="text-decoration: underline;color:red;">Remarks :</h5> 
						</td>											
					</tr>
					<% for(Object[] rh:RemarksHistory) {%>
					<tr>
					    <% if(rh[1]!=null){ %>
						<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
						    	<span style="font-size:16px;"><%=rh[2] %> :-</span>&nbsp;&nbsp;  
						    	<span style="border:none;" class="text-blue" >	<% if (!rh[1].toString().equals("")){%><%=rh[1] %><%} else{ %>--<%} %></span>
						  </td>
						 <%}%>
					</tr>
					<%} %>
				</table>
					
			</div>
			<%} %>
			    
			  
			 
			  <% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
			 
				 <div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;"> 
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100" id="remarksarea" name="remarks" maxlength="500"></textarea>
				  </div>
				  
				   <% if(CEOempno.toString().equals(empData[0].toString())){ %>
				  
				  
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCProcAbroadForward.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Approve?');" >
							Approve
						</button>
					<% } else if (PandAs.contains(empData[0].toString())){ %>
					
				     <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCProcAbroadForward.htm" name="Action" value="A"  onclick=" return verify()"  >
							Verify
						</button>
						
					<%}else{%>
						
                           <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCProcAbroadForward.htm" name="Action" value="A"  onclick=" return message()" >
								Recommend
							</button>
						
					<%} %>
				  
				  <button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="NOCProcAbroadForward.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
						
					 </div> 
				<%} %>
				
				
	 
				
				 <input type="hidden" name="ProcAbroadId" value="<%=obj[16]%>"> 						
	
       </div>
       </div> 
     </form>
    </div>
    </div>
   </div>
 </div>

		
</body>
<script>
function validateTextBox() {
    if (document.getElementById("remarksarea").value.trim() != "") {
    	return confirm('Are You Sure To Return?');
    	
    } else {
        alert("Please enter Remarks");
        return false;
    }
}

function verify(){
	
	var entries=$('#Entries').val()
	var involve=$('#Involved').val()
	 var dues=$('#dues').val()
	 
	if(entries!=null &&   involve!=null && dues!=null ){
		return confirm('Are You Sure To Verify?');
	}
	else{
		alert("Please Fill The Details");
		 return false;
	}
}


function message(){
	
	var workhandled =$('#workhandled').val()
	var visitrecomnd=$('#visitrecomnd').val()
	 var leavegranted=$('#leavegranted').val()
	 
	 if(workhandled!=null &&   workhandled!=null && workhandled!=null ){
		return confirm('Are You Sure To Recommend?');
	}
	else{
		alert("Please Fill The Details");
		 return false;
	}
}


</script>

<script>

$(document).ready(function() {
	
	   var entries=$('#Entries').val()
	   var involve=$('#Involved').val()
	   var id=$('#oblig').val()
	  
	  
	 if(id=="Y"){
		
		 $('.trow').hide();
		 $('.trow1').hide();
		 $('.trow3').show();
		 $('.trow4').show(); 
	}
	 else{
		
         $('.trow').hide();
         $('.trow1').hide();
         $('.trow3').hide();
		 $('.trow4').hide();
	 }
	 
	  if(entries=="N"){
		
		   $('.trow').show(); 
	}
	 
	 else{
		 
		
          $('.trow').hide();
          
	 }
	  
	 if(involve=="Y")
	{  
		  
		   $('.trow1').show();
    }
	 else{
		
        
         $('.trow1').hide();
         
	 }

});


  $(document).ready(function() {
     $('#Entries').on('change', function() {
        var selectedValue = $(this).val();
        if(selectedValue=="N"){
	
         $('.trow').show();
	
      }
   else{
	
	$('.trow').hide();
	
    }

   });
  });

  $(document).ready(function() {
	     $('#Involved').on('change', function() {
	        var selectedValue = $(this).val();
	        if(selectedValue=="Y"){
		
	         $('.trow1').show();
		
	      }
	   else{
		
		$('.trow1').hide();
		
	    }

	   });
	  });
  
  $(document).ready(function() {
	     $('#oblig').on('change', function() {
	        var selectedValue = $(this).val();
	        if(selectedValue=="Y"){
		
	         $('.trow3').show();
	         $('.trow4').show();
	      }
	   else{
		
		$('.trow3').hide();
		 $('.trow4').hide();
		
	    }

	   });
	  });
 


  $('#fromdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		//"minDate" :new Date(), 
		<%if(ProcAbroad!=null && ProcAbroad.getFromDate()!=null){ %>
		"startDate" : new Date("<%=ProcAbroad.getFromDate()%>"),
		<%}%>
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});

	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :$('#validfrom').val(),    
		<%if(ProcAbroad!=null && ProcAbroad.getToDate()!=null){ %>
		"startDate" : new Date("<%=ProcAbroad.getToDate()%>"),
		<%}%>
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
</script>
	

</html>







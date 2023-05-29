<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
 <%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%> 
<%@page import="com.vts.ems.noc.model.NocPassport"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Passport Preview</title>

<style>

body {
  
  overflow-x: hidden;
  overflow-y: hidden;
}

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
	padding: 1px;
	word-break: break-word;
	overflow-wrap: anywhere;
	font-size:5px;
} 
/* th{
text-align: left;
width:75%;
}	 */

.table2 td{
padding: 1px;

}
th{
font-weight:100;
}

h4{
font-size:18px;

}

</style>
</head>

<body>


<%
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
    
     String LabLogo = (String)request.getAttribute("LabLogo");
	 
     Object[] obj= (Object[])request.getAttribute("NocPassportFormDetails");
     
     List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
     List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");
     
     String isApproval = (String)request.getAttribute("isApproval");
     
     String LoginType=(String)request.getAttribute("LoginType");
     
     NocPassport passport=(NocPassport)request.getAttribute("passport");
     
     
     List<Object[]> RemarksHistory=(List<Object[]>)request.getAttribute("NOCPassportRemarkHistory");
     
     Object[] Ceoname=(Object[])request.getAttribute("CeoName");
     
     Object[] empData=(Object[])request.getAttribute("EmpData");
	
     String CEOempno=(String)request.getAttribute("CEOempno");
     
     List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	%>


		

 <div class="card-header page-top "> 
		<div class="row">
			<div class="col-md-5">
				<h5>Passport Preview <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
							<li class="breadcrumb-item "><a href="NocApproval.htm">NOC Approval</a></li>
					<li class="breadcrumb-item active" aria-current="page">
						Preview</li>
						<%}
						else{%>
						<li class="breadcrumb-item "><a href="Passport.htm">Passport List</a></li>
					<li class="breadcrumb-item active" aria-current="page">Passport
						
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
									 <div ><span style="margin-left: -5px;font-weight: 600;font-size: 18px;">APPLICATION FOR OBTAINING NO OBJECTION CERTIFICATE FOR PASSPORT</span></div> 
									<!-- <div ><span style="margin-left: -90px;font-weight: 600">APPLICATION FOR OBTAINING NO OBJECTION <br>CERTIFICATE FOR PASSPORT</span></div> -->
				
								</div>
								</div>
								
								 <div align="center"><span style="border: 0px;font-weight: 500;font-size:18px;">PART-I </span>
								</div> 
								

       <div style="text-align:center;">
		<!-- <h3 style="text-align: center;">PART - I </h3> -->
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
	
	<%-- <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>1. Name (in BLOCK LETTERS) <span style="margin-left:49px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0; text-transform: uppercase;"><%=obj[1] %></td>
	</tr>
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>2. Designation  <span style="margin-left:198px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;"><%=obj[2] %></td>
	</tr>
		<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>3. EmpNo  <span style="margin-left:243px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;"><%=obj[0] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>4. Department / Group  <span style="margin-left:116px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;"><%=obj[3] %></td>
	</tr> --%>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>5.  Applicants Residential Address  </h4></td>
	<td  style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:100px;border: 0;"><h4> (a) Present  <span style="margin-left:50px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-223px"><%=obj[4] %> , <%=obj[40] %> , <%=obj[41] %> <%=obj[42] %></span></td>
	</tr>
	
	<tr>
	<td style="width:350px;border: 0;"><h4> (b) Permanent  <span style="margin-left:23px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-226px"> <%=obj[5] %> , <%=obj[43] %> , <%=obj[44] %> <%=obj[45] %></span></td>
	</tr>
	<!-- </table> -->
	
	
	
	<!-- <table style="margin-left:10px; margin-top:15px;font-family:FontAwesome; width:100%;"> --> 
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 6. Detail of <% if(obj[12].toString().equalsIgnoreCase("F")){%>Father<%} else if(obj[12].toString().equalsIgnoreCase("H")){ %> Husband<%} 
	else if (obj[12].toString().equalsIgnoreCase("G")){ %> Guardian <%} %> <br> 
       </h4></td>
      
    <td style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (a) Name <span style="margin-left:79px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-225px"><%=obj[13] %></span></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (b) Occupation <span style="margin-left:32px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-226px"><%=obj[14] %></span></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (c) Address   <span style="margin-left:60px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;text-align:justify;"><span class="text-blue" style="margin-left:-225px"><%=obj[15] %></span></td>
	</tr>
	
	</table>
	
	<table  style="margin-left:10px; margin-top:25px;border-collapse: collapse; width:100%;" >
	
	<tr>
	<td style="width:100%;text-align: left;border: 0;"><h4>7. Details of blood&nbsp;/&nbsp;close relations working in foreign embassy&nbsp;/&nbsp;firms in India&nbsp;/&nbsp;Abroad 
         </h4> </td>
    </tr>
      
     <tr>
     <td class="text-blue" style="border: 0;text-align:justify;"><% if (obj[16].toString().trim().length()>0){%><%=obj[16] %><%} else{ %>N/A<%} %></td>
	</tr> 
	
	<tr>
	<td style="width:100%;text-align: left;border: 0;"><h4>8. Details of employment during last ten years  
       </h4> </td>
    </tr>
    
     <tr> 
    <td class="text-blue" style="border: 0;text-align:justify;"><% if (obj[17].toString().trim().length()>0){%><%=obj[17] %><%}  else{ %>N/A<%} %></td>
	</tr>
	
	<tr>
	<td style="width:20%;text-align:justify;border: 0;"><h4>9. Details of passport held previously, if any : &nbsp;<% if(obj[6].toString().equalsIgnoreCase("NA") && obj[7].toString().equalsIgnoreCase("NA") && obj[8].toString().equalsIgnoreCase("NA") &&  obj[9].toString().equalsIgnoreCase("NA")){ %><span class="text-blue">NA</span><%} %></h4></td>
     
    </tr>
	
	 <% if(!obj[6].toString().equalsIgnoreCase("NA") && !obj[7].toString().equalsIgnoreCase("NA") &&  !obj[8].toString().equalsIgnoreCase("NA") && !obj[9].toString().equalsIgnoreCase("NA")){ %> 
	<table style="margin-top: 0px;margin-left:15px;width:850px;border: 1px solid black;">	
									<tbody>
										
										<tr>
											<th style="border: 1px solid black;width:50px;text-align:center;">Type</th>
											<th style="border: 1px solid black;width:0px;text-align:center;">Passport No</th>
							                <th style="border: 1px solid black;width:50px;text-align:center;">Date of Issue</th>				
											<th style="border: 1px solid black;width:50px;text-align:center;">Validity</th>
											
										</tr>
									<tr>
										
											<td class="text-blue" ><%=obj[6]%></td>
											<td class="text-blue" style="text-align:center;"><%=obj[7] %></td>
											<td class="text-blue" style="text-align:center;"><%=rdf.format(sdf.parse(obj[8].toString())) %></td>
											<td class="text-blue" style="text-align:center;"><%=rdf.format(sdf.parse(obj[9].toString())) %></td>
											
									</tr>
										
									</tbody>
								</table>
	
	 <%} %> 
	
	</table>
	
	<%-- <tr>
	<td style="width:48%;border: 0;"><h4>(a) Type  <span style="margin-left:265px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;"><%=obj[6]%></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>(b) Passport No  <span style="margin-left:191px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;"><%=obj[7] %></td>
	</tr>
	
	
	<tr>
	<td style="width:400px;border: 0;"><h4>(c) Date of Issue <span style="margin-left:186px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;"><%=rdf.format(sdf.parse(obj[8].toString())) %></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4> (d) Validity <span style="margin-left:237px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;"><%=rdf.format(sdf.parse(obj[9].toString())) %></td>
	</tr> --%>
	<!-- </table> -->
	

	<table  style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:100%;" >
	<!-- <table style="margin-left:10px; margin-top:15px;border: none;font-family:FontAwesome; width:650px;"> --> 
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 10. Details of passport lost, if any <span style="margin-left:15px"> :</span>
        <%if(obj[18].toString().equals("NA")){ %><span class="text-blue">NA</span><%} %></h4> </td>
       
     </tr>  
    <% if(!obj[18].toString().equals("NA")){%>
    <tr>
    <td class="text-blue" style="border: 0;text-align:justify;"><%=obj[18] %></td>
	</tr>
	<%} %>
	</table>
	
	<table  style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:100%;" >
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 11. Type of passport required  <span style="margin-left:10px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;"><span class="text-blue" style="margin-left:-93px;" > <%=obj[19] %></span></td>
	</tr>
	</table>
	
	<table style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:100%;">
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 12. I certify that
       </h4> </td>
    </tr>
    
    <tr>
    <td style="border: 0;width:900px;"><h4>(a) My application for the above passport is not for proceeding to a foreign 
           country.</h4> <span style="color:red;font-size:17px;">I shall  separately seek the NO OBJECTION CERTIFICATE  
           before proceeding to a foreign country.</span>
           
           <span style="color:red;font-size:17px;">(b) I am not involved in any court / police / disciplinary / vigilance case and there 
              is no restriction placed by any authority.</span>
              
              <% if(obj[20].toString().equalsIgnoreCase("N")) {%>
		  
           <h4>I am not under contractual obligation to serve STARC for any specific period.</h4> <%} 
		
		else if(obj[20].toString().equalsIgnoreCase("Y")){%>
	        
           <h4> I am under contractual obligation to serve STARC for a period from 
              <span class="text-blue"><%=rdf.format(sdf.parse(obj[21].toString())) %></span>  to   <span class="text-blue"  ><%=rdf.format(sdf.parse(obj[22].toString())) %></span>
              </h4>
          <%} %>
              
       </td>
	</tr>
	
	</table>
	
	<%-- <table>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 13. I certify that: <span style="margin-left:180px"> :</span>
       </h4> </td>
    
    <td style="border: 0;"><% if(obj[20].toString().equalsIgnoreCase("N")) {%>
		  
           I am not under contractual obligation to serve STARC for any specific period. <%} 
		
		else if(obj[20].toString().equalsIgnoreCase("Y")){%>
	        
            I am under contractual obligation to serve STARC for a period from 
              <span class="text-blue"   ><%=rdf.format(sdf.parse(obj[21].toString())) %></span>  to   <span class="text-blue"  ><%=rdf.format(sdf.parse(obj[22].toString())) %></span>
              
          <%} %></td>
	</tr>
	
	</table> --%>
	
	
	<br>
	
	 <% if(obj[25]!=null) {%><div   style="margin-left:500px !important;">Forwarded On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[25].toString().substring(0, 10)) +" "+obj[25].toString().substring(11,19) %></span> </div> <%} %>
	 <br>
	<%--  <div align="left" style="margin-left:10px;font-weight:400;" > Dept.Incharge : <span class="text-blue"  ><%=obj[23] %></span></div>	 --%>
	  	
		
		
		<!-- ----------------------------------------  P&A Department-------------------------------------------------------------------------------------------- -->
		
		 <% if(obj[27].toString().equalsIgnoreCase("APR")) {%>
		 <hr>
		  <div><span style="margin-left: -80px;font-weight: 600;font-size: 18px;text-decoration: underline;">Filled by P&A Department</span></div> 
		  <table class="table2" style="margin-left:10px; margin-top:35px;border-collapse: collapse; width:100%;">
		  
		 <tr>
		 
		   <td style="text-align: left;border: 0;width:89%;"><h4> 1.(a) Are the entries given by the applicant in paras 1 to 6 in Part - I correct?  : &nbsp;<span class="text-blue"><% if(obj[28].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %> </span></h4></td>
             
             <%--  <td class="text-blue" style="border: 0;text-align: left;margin-left:50px;"><% if(obj[28].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></td> --%>
            </tr>
            
           <% if(obj[28].toString().equalsIgnoreCase("N")){ %>
          
          <tr>
          
             <td style="width:60%;text-align: left;border: 0;"><h4>(b) If not, mention variations </h4></td>
         
          </tr>
          
          <tr>
             <td class="text-blue" style="border: 0;text-align: justify;width:100%;"><%=obj[29] %></td>
          </tr>
          <%} %>
          
          <tr>
          
           <td style="width:50%;text-align: left;border: 0;"><h4>2.(a) Whether the employee is under suspension? : <span class="text-blue"><% if(obj[30].toString().equalsIgnoreCase("Y")){ %>Yes<%} 
           else{ %>No<%} %></span>
           </h4></td>
           
           </tr>
           
           <tr>
           <td style="width:50%;text-align: left;border: 0;"><h4>
              (b) Whether the employee is involved in any	
	           Disciplinary / Criminal / Corruption / Court
	          Case :&nbsp;<span class="text-blue"><% if(obj[31].toString().equalsIgnoreCase("Y")){ %>Yes<%} else{ %>No<%} %></span></h4></td> 
	         
	            
	        </tr>
	        <% if(obj[31].toString().equalsIgnoreCase("Y")){ %>
	        <tr>
	        
	        <td style="width:50%;text-align: left;border: 0;"><h4>
                (c) If so, details of the case</h4></td> 
            
            </tr>
            <tr>
            <td class="text-blue" style="border: 0;text-align: justify;"><%=obj[32] %></td>
            
           </tr>
           <%} %>
           
          </table> 
          
           <table class="table2" style="margin-left:10px; margin-top:5px;border-collapse: collapse; width:100%;">
           <tr>
           <td style="width:20%;text-align: left;border: 0;"><h4>3. Applicant is :
             </h4></td>
           
            <td style="border: 0; text-align:justify" ><% if(obj[20].toString().equalsIgnoreCase("N")) {%>
		  
            <h4 >not under contractual obligation to serve STARC for any specific period.</h4> <%} 
		
		     else if(obj[20].toString().equalsIgnoreCase("Y")){%>
	        
               <h4 style="margin-left:-43px;"> under contractual obligation to serve STARC for a period from 
              <span class="text-blue"   ><%=rdf.format(sdf.parse(obj[21].toString())) %></span> to <span class="text-blue"  ><%=rdf.format(sdf.parse(obj[22].toString())) %></span>
              </h4>
            <%} %>
          
          </td>
          
          </table> 
       
        <%} %>
        
        <% if(LoginType.toString().equalsIgnoreCase("P") && obj[27].toString().equalsIgnoreCase("VPA")) { %>
         <div><span style="margin-left: -80px;font-weight: 600;font-size: 18px;text-decoration: underline;">To be completed by the P&A Department</span></div> 
         <div class="card" >
		<div class="card-body" >
        <div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 100%;">
								       <tr>
											<th style="text-align:left;"><label> Are the entries given by the applicant in paras 1 to 6 in Part - I correct?
                                                  <span class="mandatory"	style="color: red;">*</span></label></th>
											<td>
											<select class="form-control select2"  name="PassportEntries" id="Entries" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="N" <% if(passport!=null &&  passport.getPassportEntries()!=null){ if(passport.getPassportEntries().toString().equalsIgnoreCase("N")){%> selected  <% } }%> >NO</option>
												<option value="Y" <% if(passport!=null &&  passport.getPassportEntries()!=null){ if(passport.getPassportEntries().toString().equalsIgnoreCase("Y")){%> selected  <% } }%> >YES</option>
												
												</select></td>
										</tr>
										
										
																		
										<tr class="trow">
											<th style="text-align:left;"><label> If not, mention variations<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control" 
												placeholder="" type="text" name="Variation" value="<%if(passport!=null && passport.getPassportEntriesDetails()!=null ){%><%=passport.getPassportEntriesDetails()%><%} %>"
												  style="font-size:15px;"></td>
										</tr>
										
										<tr>
											<th  style="text-align:left;"><label> Whether the employee is under suspension?	 <span class="mandatory"	style="color: red;">*</span></label></th>
											<td>
											  <select class="form-control select2"  name="EmpSuspension" id="suspended" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="N" <%if(passport!=null && passport.getEmployeeSuspensed()!=null){ if(passport.getEmployeeSuspensed().toString().equalsIgnoreCase("N")){%> selected  <% }}%>>NO</option>
												<option value="Y" <%if(passport!=null && passport.getEmployeeSuspensed()!=null){ if(passport.getEmployeeSuspensed().toString().equalsIgnoreCase("Y")){%> selected  <% }}%>>YES</option>
												
											</select></td>
										</tr>	
																		
										<tr >
											<th style="text-align:left;"><label> Whether the employee is involved in any	Disciplinary / Criminal / Corruption / CourtCase
											<span class="mandatory"	style="color: red;">*</span></label></th>
											<td>
											    <select class="form-control select2"  name="EmpInvolvment" id="Involved" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="N"<%if(passport!=null && passport.getEmployeeInvolvement()!=null){ if(passport.getEmployeeInvolvement().toString().equalsIgnoreCase("N")){%> selected  <% }}%>>NO</option>
												<option value="Y"<%if(passport!=null && passport.getEmployeeInvolvement()!=null){ if(passport.getEmployeeInvolvement().toString().equalsIgnoreCase("Y")){%> selected  <% }}%>>YES</option>
												
												
											  </select>
											 </td>
										</tr>
										
																			
										<tr class="trow1">
											<th  style="text-align:left;"><label> If so, details of the case<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control" 
												placeholder="" type="text" id="" name="CaseDetails" value="<%if(passport!=null && passport.getEmployeeCaseDetails()!=null ){%><%=passport.getEmployeeCaseDetails() %><%} %>"
												  style="font-size: 15px;"></td>
										</tr>
										
										
										
										<tr>
											<th  style="text-align:left;"><label>Is the applicant under contractual obligation to serve STARC for any specific period? If so, specify the period.
                                              <span class="mandatory" style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="obligation" id="oblig" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												
												<option value="N" <%if(passport!=null && passport.getContractualObligation()!=null){ if(passport.getContractualObligation().toString().equalsIgnoreCase("N")){%> selected  <% }}%>>NO</option>
												<option value="Y" <%if(passport!=null && passport.getContractualObligation()!=null){ if(passport.getContractualObligation().toString().equalsIgnoreCase("Y")){%> selected  <% }}%>>YES</option>
												
											  </select>
											 </td>
											 
											 </tr>
											
											 <tr  class="trow3">
											<th  style="text-align:left;"><label> From Date<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control input-sm mydate" 
												placeholder="" type="text" id="fromdate" name="FromDate" value="<% if(passport!=null && passport.getFromDate()!=null ){%><%=rdf.format(sdf.parse(passport.getFromDate()))%><%} %>"
												required="required"  style="font-size: 15px;"></td>
										</tr >
											 
											 <tr class="trow4" >
											<th  style="text-align:left;"> <label> To Date<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control input-sm mydate" 
												placeholder="" type="text" id="todate" name="ToDate"  value="<% if(passport!=null && passport.getToDate()!=null){%><%=rdf.format(sdf.parse(passport.getToDate()))%><%} %>"
												required="required"  style="font-size: 15px;"></td>
										</tr>
										
							
												
								</table>
								
								
								<button type="submit"  class="btn btn-sm update-btn"  style="margin-bottom:28px;margin-top:28px;margin-left:0px"   formaction="NOCPassportPAForm.htm"   name="Passportid" value="<%=obj[10]%>"  onclick="return confirm('Are You Sure To Update?');">Update</button>
								
							
								<%-- <a type="button"  class="btn btn-sm update-btn"  style="margin-bottom:28px;margin-top:28px;margin-left:0px"   href="NOCPassportPAForm.htm?PassportId=<%=obj[10] %>" onclick="return confirm('Are You Sure To Update?');">Update</a>  --%>
							</div>
							
							
						</div>
			       
			      </div>
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
			
			<br>
			
			<% if(obj[24]!=null && toUserStatus.contains(obj[24].toString())) { %>
	  	<div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;">
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100"   name="remarks"  maxlength="500"></textarea>
				  </div>
                <button type="submit" class="btn btn-sm submit-btn"  name="Action" value="A"  formaction="NOCPassportForward.htm"  onclick="return confirm('Are You Sure To Forward?');" >Forward</button>
                
				</div>
		<%} %>
		<br>
		
		<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
		<%-- <div align="left">
				   	<%if(obj[26]!=null){ %> <span style="color: red">Remarks :</span> <%=obj[26] %> <%} %>
				   		
				  </div> --%>
				 
			 
				  <% if(!obj[24].toString().equalsIgnoreCase("APR")){ %> 
				  
		         <div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;"> 
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100" id="remarksarea" name="remarks" maxlength="500"></textarea>
				  </div>
				  
				  <% if(CEOempno.toString().equals(empData[0].toString())){ %>
				  
				  
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCPassportForward.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Approve?');" >
							Approve
						</button>
					<% } else if (PandAs.contains(empData[0].toString())){ %>
					
				     <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCPassportForward.htm" name="Action" value="A"  onclick=" return message()"  >
							Verify
						</button>
						
					<%}else{%>
						
                           <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCPassportForward.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Recommend?');" >
								Recommend
							</button>
						
					<%} %>
				  
				  <button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="NOCPassportForward.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
					 </div>
					 
					 <%} %>
					 
				<%} %>
				<br>
				
				 <% if(obj[24].toString().equalsIgnoreCase("APR")){ %>
		 <div style="margin-top:0x;text-align:center;"> 
		                         <span style="font-weight: 600; font-size: 16px;margin-left:0px;">APPROVED</span><br>
						        <span style="font-weight: 500; font-size: 16px;margin-left:0px;">CEO:&nbsp;<span class="text-blue" ><% if (Ceoname!=null){%><%=Ceoname[1] %><%} %></span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[34].toString().substring(0, 10)) +" "+obj[34].toString().substring(11,19) %></span></span><br>
		</div>	
		<%} %> 
				 <input type="hidden" name="passportid" value="<%if(obj[10]!=null){ %><%=obj[10] %><% }%>"> 						
	
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


function message(){
	
	var entries=$('#Entries').val()
	var involve=$('#Involved').val()
	 var suspended=$('#suspended').val()
	 
	  
	 
	if(entries!=null &&   involve!=null && suspended!=null ){
		return confirm('Are You Sure To Verify?');
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
		<%if(passport!=null && passport.getFromDate()!=null){ %>
		"startDate" : new Date("<%=passport.getFromDate()%>"),
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
		<%if(passport!=null && passport.getToDate()!=null){ %>
		"startDate" : new Date("<%=passport.getToDate()%>"),
		<%}%>
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
</script>


</html>







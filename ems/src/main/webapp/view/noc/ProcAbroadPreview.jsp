<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
 <%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%> 

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
     
     String isApproval = (String)request.getAttribute("isApproval");
     
     
     Object[] obj=(Object[])request.getAttribute("NocProcAbroadDetails");
    
     
    
     
    
	
	%>


		

 <div class="card-header page-top "> 
		<div class="row">
			<div class="col-md-4">
				<h5>Passport Preview</h5>
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
			<div class="card" style="padding-top:0px;margin-top: -15px;">
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"  align="left">
				<form action="##"  >
				<div class="" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
				
								<div align="center">
								<div style="width: 100%;">
									<div style="width: 100%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
									 <div ><span style="margin-left: -80px;font-weight: 600;font-size: 18px;">APPLICATION FOR OBTAINING NO OBJECTION CERTIFICATE FOR PROCEEDING ABROAD</span></div> 
									<!-- <div ><span style="margin-left: -90px;font-weight: 600">APPLICATION FOR OBTAINING NO OBJECTION <br>CERTIFICATE FOR PASSPORT</span></div> -->
				
								</div>
								</div>
								<!-- <div align="center"><span style="border: 0px;font-weight: 600">PART-I </span>
								</div> -->
								

       <div style="text-align:center;">
		<!-- <h3 style="text-align: center;">PART - I </h3> -->
	<table style="margin-left:10px; margin-top:85px;border-collapse: collapse; width:100%;">
	<tr>
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
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>5.  Applicants Residential Address  </h4></td>
	<td  style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4> (a) Present  <span style="margin-left:233px"> :</span> </h4></td>
	<td class="text-blue" style="border: 0;"><%=obj[4] %></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4> (b) Permanent  <span style="margin-left:199px"> :</span></h4></td>
	<td class="text-blue" style="border: 0;"><%=obj[5] %></td>
	</tr>
	<!-- </table> -->
	
	
	
	<!-- <table style="margin-left:10px; margin-top:15px;font-family:FontAwesome; width:100%;"> --> 
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 6. Detail of <% if(obj[12].toString().equalsIgnoreCase("F")){%>Father<%} else if(obj[12].toString().equalsIgnoreCase("H")){ %> Husband<%} 
	else if (obj[12].toString().equalsIgnoreCase("G")){ %> Guardian <%} %>
	 <br> 
       </h4></td>
      
    <td style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (a) Name <span style="margin-left:253px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;"><%=obj[13] %></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (b) Occupation <span style="margin-left:196px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;"><%=obj[14] %></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>  (c) Address   <span style="margin-left:232px"> :</span></h4></td>
      
    <td class="text-blue" style="border: 0;"><%=obj[15] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 7. Details of blood / close relations <span style="margin-left:6px"> :</span> <br> &emsp; working in foreign embassy / <br> &emsp; firms in India / Abroad 
         </h4> </td>
    
      
    <td class="text-blue" style="border: 0;"><% if (obj[16].toString().trim().length()>0){%><%=obj[16] %><%} else{ %>N/A<%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 8. Details of employment during <span style="margin-left:29px"> :</span><br>&emsp; last ten years  
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;"><% if (obj[17].toString().trim().length()>0){%><%=obj[17] %><%} else{ %>N/A<%} %></td>
	</tr>
	
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 9. Are you involved in any court <br>&emsp;/ police / vigilance case for <br>&emsp; which your presence is required <span style="margin-left:5px"> :</span><br>&emsp; in India
	
      </h4></td>
    
    <td class="text-blue" style="border: 0;"><% if (obj[18].toString().equalsIgnoreCase("Y")){%>YES<%} else{ %>NO<%} %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 10. Whether Annual Property <br>&emsp; Return of the previous year <span style="margin-left:49px"> :</span> <br>&emsp;has been filed 
	
	
      </h4></td>
    
    <td class="text-blue" style="border: 0;"><% if (obj[19].toString().equalsIgnoreCase("Y")){%>YES<%} else{ %>NO<%} %></td>
	</tr>
	
   
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>11. Details of passport held  <br>&emsp; previously, if any 	
      </h4> </td>
    
     <td style="border: 0;"></td>
	</tr>
	
	
	<tr>
	<td style="width:400px;border: 0;"><h4>(a) Type  <span style="margin-left:265px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;"><%=obj[6]%></td>
	</tr>
	
	<tr>
	<td style="width:400px;border: 0;"><h4>(b) Passport No  <span style="margin-left:191px"> :</span>
       </h4> </td>
    
      
    <td class="text-blue" style="border: 0;"><%=obj[7]%></td>
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
	</tr>
	<!-- </table> -->
	
	<tr></tr>
	<!-- <table style="margin-left:10px; margin-top:15px;border: none;font-family:FontAwesome; width:650px;"> --> 
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 12. Details of passport lost, if any <span style="margin-left:20px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;"></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 13. Have you visited any foreign <span style="margin-left:30px;">:</span><br>&emsp;country before? 
	
	
	 </h4></td>
      
    
    <td class="text-blue" style="border: 0;"><% if (obj[20].toString().equalsIgnoreCase("Y")){%>YES<%} else{ %>NO<%} %></td>
	</tr>
	
	<% if (obj[20].toString().equalsIgnoreCase("Y")){%>
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>&emsp; Details of such visits to include <br>&emsp; countries visited with details <span style="margin-left:34px;">:</span><br>&emsp;of dates
	
	
	 </h4></td>
      
    
    <td class="text-blue" style="border: 0;"><% if(obj[21].toString().trim().length()>0){ %><%=obj[21] %><%} %></td>
	</tr>
  <%} %>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>14. Countries proposed to be  <span style="margin-left:56px"> :</span><br>&emsp; visited	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;"><%=obj[22] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>15. Date of departure  <span style="margin-left:128px"> :</span>
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;"><%=rdf.format(sdf.parse(obj[23].toString())) %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>16. Purpose of visit<span style="margin-left:156px"> :</span>
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;"><%=obj[24] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>17. Probable duration of stay at<span style="margin-left:35px"> :</span><br>&emsp; each country
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;"><%=obj[25] %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>18. Probable date of return<span style="margin-left:78px"> :</span>
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;"><%=rdf.format(sdf.parse(obj[26].toString())) %></td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>19. Going <span style="margin-left:244px"> :</span>
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;"><% if(obj[27].toString().equalsIgnoreCase("A")) {%>Alone<%} else {%>Family<%} %></td>
	</tr>
	
	<% if(obj[27].toString().equalsIgnoreCase("WF")) {%>
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>&emsp; Details of family <span style="margin-left:152px"> :</span>
	   </h4></td>
      
      <td class="text-blue" style="border: 0;"><%=obj[28] %></td>
	</tr>
	<%} %>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>20. Approximate amount expected <br>&emsp; to be incurred for the trip <span style="margin-left:63px"> :</span><br>&emsp; including journey and stay <br>&emsp; abroad
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;">&#8377;&nbsp;<%=obj[29]%></td>
	</tr>
	
  <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4>21. Trip financed by  <span style="margin-left:63px"> :</span>
	
	   </h4></td>
      
    
     <td class="text-blue" style="border: 0;"><% if(obj[30].toString().equalsIgnoreCase("S")){ %>SELF<%} else{ %>OTHER PEOPLE<%} %></td>
	</tr>
	
	
	<!-- <tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 14. Type of passport required  <span style="margin-left:55px"> :</span>
       </h4> </td>
    
    <td class="text-blue" style="border: 0;"></td>
	</tr> -->
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 12. I certify that:  <span style="margin-left:180px"> :</span>
       </h4> </td>
    
    <td  class="tabledata" style="border: 0;">(a) My application for the above passport is not for proceeding to a foreign 
           country. I shall  separately seek the NO OBJECTION CERTIFICATE  
           before proceeding to a foreign country. <br>
           
           (b) I am not involved in any court / police / disciplinary / vigilance case and there 
              is no restriction placed by any authority.</td>
	</tr>
	
	<tr>
	<td style="width:350px;text-align: left;border: 0;"><h4> 13. I certify that: <span style="margin-left:180px"> :</span>
       </h4> </td>
    
    <td  style="border: 0;">
		  
           I am not under contractual obligation to serve STARC for any specific period.
		
		
	        
            I am under contractual obligation to serve STARC for a period from 
              <span class="text-blue"   ></span>  to   <span class="text-blue"  ></span>
              
         </td>
	</tr>
	
	</table>
	
	
	<br>
	<!--  <div align="left" style="margin-left:10px;font-weight:400;" > Dept.Incharge : <span class="text-blue"  ></span></div>	 -->
	  	
	  	<div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;">
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100"   name="remarks"  maxlength="500"></textarea>
				  </div>
                <button type="submit" class="btn btn-sm submit-btn"  name="Action" value="A"  formaction=""  onclick="return confirm('Are You Sure To Submit?');" >Forward</button>
                
				</div>
		
		<br>
		
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
        
      
         <%-- <div><span style="margin-left: -80px;font-weight: 600;font-size: 18px;text-decoration: underline;">To be completed by the P&A Department</span></div> 
         <div class="card" >
		<div class="card-body" >
        <div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 100%;">
								       <tr>
											<th><label> Are the entries given by the applicant in paras 1 to 6 in Part - I correct?
                                                  <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="PassportEntries" id="Entries" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="Y" >YES</option>
												<option value="N"  >NO</option>
												
											    </select></td>
										</tr>
										
										
																		
										<tr class="trow">
											<th><label> If not, mention variations<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control" 
												placeholder="" type="text" name="Variation" value=""
												  style="font-size:15px;"></td>
										</tr>
										
										<tr>
											<th><label> Whether the employee is under suspension?	 <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="EmpSuspension" id="" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												
												<option value="Y" >YES</option>
												<option value="N" >NO</option>
												
											    </select></td>
										</tr>	
																		
										<tr >
											<th><label> Whether the employee is involved in any	Disciplinary / Criminal / Corruption / CourtCase
											<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="EmpInvolvment" id="Involved" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="Y">YES</option>
												<option value="N">NO</option>
												
											  </select>
											 </td>
										</tr>
										
																			
										<tr class="trow1">
											<th><label> If so, details of the case<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control" 
												placeholder="" type="text" id="" name="CaseDetails" value=""
												  style="font-size: 15px;"></td>
										</tr>
										
										
										
										<tr>
											<th><label>Is the applicant under contractual obligation to serve STARC for any specific period? If so, specify the period.
                                              <span class="mandatory" style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="obligation" id="oblig" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<option value="Y" >YES</option>
												<option value="N" >NO</option>
												
											  </select>
											 </td>
											 
											 </tr>
											
											 <tr  class="trow3">
											<th><label> From Date<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control input-sm mydate" 
												placeholder="" type="text" id="fromdate" name="FromDate" value=""
												required="required"  style="font-size: 15px;"></td>
										</tr >
											 
											 <tr class="trow4" >
											<th><label> To Date<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control input-sm mydate" 
												placeholder="" type="text" id="todate" name="ToDate" value=""
												required="required"  style="font-size: 15px;"></td>
										</tr>
										
							
												
								</table>
								
								
								<button type="submit"  class="btn btn-sm update-btn"  style="margin-bottom:28px;margin-top:28px;margin-left:0px"   formaction="NOCPassportPAForm.htm"   name="Passportid" value=""  onclick="return confirm('Are You Sure To Update?');">Update</button>
								
							
								<a type="button"  class="btn btn-sm update-btn"  style="margin-bottom:28px;margin-top:28px;margin-left:0px"   href="NOCPassportPAForm.htm?PassportId=<%=obj[10] %>" onclick="return confirm('Are You Sure To Update?');">Update</a> 
							</div>
							
							
						</div>
			       
			      </div>
			    </div>
			    --%>
			    
		
		
		<%-- <div align="left">
				   	<%if(obj[26]!=null){ %> <span style="color: red">Remarks :</span> <%=obj[26] %> <%} %>
				   		
				  </div> --%>
				 
				  <!-- 
				  <div class="col-md-5" align="left" style="margin-left:1rem;margin-top:2rem; padding:0px;border: 1px solid black;border-radius: 5px;">
				<table style="margin: 3px;padding: 0px;">
					<tr>
						<td style="border:none;padding: 0px">
							<h6 class="text-blue"style="text-decoration: underline;">Remarks :</h6> 
						</td>											
					</tr>
					<tr>
						<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
						    	
						</td>
					</tr>
				</table>
					
			</div>
			 -->
			 
				 
				  
		        <!--  <div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;"> 
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100" id="remarksarea" name="remarks" maxlength="500"></textarea>
				  </div>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCPassportForward.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Verify	
						</button>
					
				   		<button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="NOCPassportForward.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
					 </div> -->
					
				
				 <input type="hidden" name="passportid" value=""> 						
	
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




</script>

<script>

$(document).ready(function() {
	
	   var entries=$('#Entries').val()
	   var involve=$('#Involved').val()
	   console.log("involve--"+ involve);
	   var id=$('#oblig').val()
	   //console.log("id--"+typeof id);
	  
	 if(id=="Y"){
		 console.log(id=="Y");
		 $('.trow').hide();
		 $('.trow1').hide();
		 $('.trow3').show();
		 $('.trow4').show(); 
	}
	 else{
		 console.log(id=="Y"); console.log(id);
         $('.trow').hide();
         $('.trow1').hide();
         $('.trow3').hide();
		 $('.trow4').hide();
	 }
	 
	  if(entries=="N"){
		 console.log(entries=="N");
		   $('.trow').show(); 
	}
	 
	 else{
		 
		 console.log(entries=="N"); console.log(entries);
          $('.trow').hide();
          
	 }
	  
	 if(involve=="Y")
	{  
		   console.log(involve=="Y");
		   $('.trow1').show();
    }
	 else{
		 console.log(involve=="Y"); console.log(involve);
        
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
		
		"startDate" : new Date(""),
		
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
		
		"startDate" : new Date(""),
		
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
</script>


</html>







<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.noc.model.NocProceedingAbroad"%> 
  <%@page import="com.vts.ems.pis.model.*"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 

<style>

body{

 overflow-x: hidden;
 

}

</style>

</head>
<body>

  <%
  
   
    Object[] NocEmpList= (Object[])request.getAttribute("NocEmpList");
    Object[] EmpPassport= (Object[])request.getAttribute("EmpPassport");
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	String Empid=(String)request.getAttribute("EmpId");
	
	 NocProceedingAbroad ProcAbroad=(NocProceedingAbroad)request.getAttribute("ProcAbroad");
	 Object[] empData=(Object[])request.getAttribute("EmpData");

	 Passport pispassport=(Passport)request.getAttribute("pispassport");
	
  %>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
			
			  <h5>Proceeding Abroad <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			 
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="ProceedingAbroad.htm">Proceeding Abroad List</a></li>
					<% if(ProcAbroad!=null){ %>
					<li class="breadcrumb-item active " aria-current="page">Proceeding Abroad Edit</li>
					
					<%} else{ %>
					<li class="breadcrumb-item active " aria-current="page">Proceeding Abroad Add</li>
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
	
	
	<div class=" page card dashboard-card">
  
		<div class="card" >
			<div class="card-body" >
			
			<% if(ProcAbroad!=null){ %>
			
			<form action="ProcAbroadEditSubmit.htm" method="post"  >
			
			<%} else {%>
			
			<form action="ProcAbroadAddSubmit.htm" method="post"  >
			
			<%} %>
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			         
			         
			           <div class="col-md-2">
			                <label> Passport Type:</label>
			                
			                 <% if(EmpPassport!=null ) { %>
			                    <input  class="form-control input-sm " type="text" name="PassportExist" readonly value="Renewal">
			                    
			                    <%}
			                    else{%>
			                    <select name="PassportExist" class="form-control select2"  id="passporttype" required="required">
			                        
			                    	 <option value="New">New</option>
			                    	   <option value="Renewal">Renewal</option>
			                    </select>
			                   <%} %>
			                
			             
			     </div>
			
			           <div class="col-md-2">
			                <label>Name</label> 
			            	<input type="text" name="Name" value="<%=NocEmpList[0] %>" class="form-control input-sm" readonly >
			            </div>			
			            	
			            <div class="col-md-2">
			                <label>Designation</label>
			                <input type="text" id="" name="Designation"    value="<%=NocEmpList[1] %>"
			                    class=" form-control input-sm " readonly
			                    >
			            </div>
			            <div class="col-md-2">
			                <label> Department</label>
			                <input type="text" id="" name="Department"    value="<%=NocEmpList[2] %>"
			                    class=" form-control input-sm " readonly >
			                     
			            </div>
			             <div class="col-md-4">
			                <label>Present  Address </label>
			                <input type="text" id="" name="Present"    value="<%=NocEmpList[3] %>,<%=NocEmpList[6] %>,<%=NocEmpList[7] %> <%=NocEmpList[8] %>"
			                    class=" form-control input-sm " readonly  >
			                   
			            </div>
				  </div>
			    </div>
			
			    <div class="form-group">
			        <div class="row">
			          <div class="col-md-3">
			                <label> Permanent Address </label>
			                <input type="text" id="" name="Permanent"    value="<%=NocEmpList[4] %>,<%=NocEmpList[9] %>,<%=NocEmpList[10] %> <%=NocEmpList[11] %> "
			                    class=" form-control input-sm " readonly
			                    >
			            </div>
			            
			             <div class="col-md-2">
			                <label>Relation Of </label>
			                <select name="RelationType" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="F" <% if(ProcAbroad!=null){  if(ProcAbroad.getRelationType().toString().equals("F")){ %> selected <%}}%> >Father</option>
			                    <option value="H" <% if(ProcAbroad!=null){  if(ProcAbroad.getRelationType().toString().equals("H")){ %> selected <%}}%> >Husband</option>
			                    <option value="G"  <% if(ProcAbroad!=null){  if(ProcAbroad.getRelationType().toString().equals("G")){ %> selected <%}}%> >Guardian</option>
			                </select>
			            </div> 
			            
			              <div class="col-md-2">
			                <label>Relative Name </label>
			                <input type="text" id="" name="RelationName"   value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getRelationName() %><%}  %>"
			                   class=" form-control input-sm "  required="required" >
			                    
			            </div>
			            <div class="col-md-2">
			                <label>Relative Occupation </label>
			                <input type="text" id="" name="RelationOccupation"   value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getRelationOccupation() %><%} %>"
			                    class="form-control input-sm "  required="required">
			                   
			            </div>
			            
			             <div class="col-md-3">
			                <label>Relative Address </label>
			                <input type="text" id="" name="RelationAddress"  value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getRelationAddress() %><%} %>"  required="required"
			                    class="form-control input-sm " >
			             
			            </div>
			            
			           
			       </div>
			     </div>
			   
			    <div class="form-group">
			        <div class="row">
			            
			
			             <div class="col-md-6">
			                <label> Details of blood /close relations working in foreign embassy / firms in India / Abroad 
                              </label>
                             <div class="col-md-13" style="margin-left:1px;">  
			                <input type="text" name="RelationAbroad"     value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getRelationAbroad() %><%} else{%>NA<%} %>"
			                    class="form-control input-sm "  required="required"  >
			                   
			                    </div>
			              </div>
			              
			             <div class="col-md-6">
			                <label> Details of employment during last ten years  </label>
                            
                             <div class="col-md-13" style="margin-left:0px;">  
			                <input type="text" name="EmployementDetails"     value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getEmployementDetails() %><%} else{%>NA<%} %>"
			                    class="form-control input-sm"  required="required"  >
			                   
			                   </div>
			              </div>
			              
			           </div>
			    </div> 
			    
			    <div class="form-group">
			        <div class="row">
			            
			
			             <div class="col-md-6">
			                <label>  Are you involved in any court / police / vigilance </label>
                              
                             <div class="col-md-7" style="margin-left:-15px;">  
			                <select name="EmployeeInvolvement" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                   
			                    <option value="N" <% if(ProcAbroad!=null){  if(ProcAbroad.getEmployeeInvolvement().toString().equals("N")){ %> selected <%}}%>>NO</option>
			                     <option value="Y" <% if(ProcAbroad!=null){  if(ProcAbroad.getEmployeeInvolvement().toString().equals("Y")){ %> selected <%}}%>>YES</option>
			                   
			                </select>
			                   
			             </div>
			           </div>
			              
			             <div class="col-md-6" style="margin-left:-271px;">
			                <label> Whether Annual Property Return of the
                                    previous year has been filed  </label>
                            
                            
                             <div class="col-md-9" style="margin-left:-13px;">  
			               <select name="PropertyFiled" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                     
			                      <option value="N" <% if(ProcAbroad!=null){  if(ProcAbroad.getPropertyFiled().toString().equals("N")){ %> selected <%}}%>>NO</option>
			                       <option value="Y" <% if(ProcAbroad!=null){  if(ProcAbroad.getPropertyFiled().toString().equals("Y")){ %> selected <%}}%>>YES</option>
			                </select>
			                   
			                   </div>
			              </div>
			              
			           </div>
			    </div> 
			    
			    <div class="form-group" >
			  
			        <div class="row">
			         <% if( EmpPassport!=null) { %>
			         
			         <div class="col-md-2">
			                <label> Passport Type </label>
			                <input type="text" id="" name="PassportType"  value="<%=EmpPassport[0] %>"
			                    class=" form-control input-sm " readonly="readonly">
			                   
			            </div>
			            
			             <div class="col-md-2">
			                <label> Passport No. </label>
			                <input type="text" id="" name="PassportNo"    value="<%=EmpPassport[1] %>"
			                    class=" form-control input-sm "  readonly="readonly">
			                   
			            </div> 
			            
			              <div class="col-md-2">
			                <label>Date of Issue  </label>
			                <input type="text" id="" name="ValidFrom"    value="<%=rdf.format(sdf.parse(EmpPassport[2].toString()))%>"
			                   class=" form-control input-sm "   readonly="readonly">
			                    
			            </div>
			            
			            <div class="col-md-2">
			                <label>Validity </label>
			                <input type="text" id="" name="ValidTo"   value="<%=rdf.format(sdf.parse(EmpPassport[3].toString()))%>"
			                    class=" form-control input-sm "  readonly="readonly">
			                   
			            </div>
			            
			         
			       <%} %>
			      
			        <div class="col-md-2" id="ptype">
			                <label>Passport Type</label><br>
                              <select  name="PassportType" class="form-control select2"  data-live-search="true" style="width:200px;">
                              
                                     		<option value="Official" >Official</option>
					                        <option value="Diplomatic" >Diplomatic</option>
					                        <option value="Ordinary" >Ordinary</option>				                         
                              </select>
			                   
			            </div>
			            
			             <div class="col-md-2" id="status">
                        
                              <label>Status</label><br>
                              <select  name="Status" class="form-control select2"  data-live-search="true" style="width:200px;">
                              
                                     		<option value="Valid" >Valid</option>
					                        <option value="Cancelled" >Cancelled</option>
					                        <option value="Surrendered" >Surrendered</option>
					                        <option value="Lab Custody" >Lab Custody</option>
					                        <option value="HQ Custody" >HQ Custody</option>				                         
                              </select>
                       
                       </div>
			            
			             <div class="col-md-2" id="pno">
	                    
	                            <label>Passport No:</label>
	                            <input id="passportno" type="text"  class="form-control input-sm "  name="PassportNo"  maxlength="6"   onblur="checknegative(this)">
	                    
                    </div>
                    
                    <div class="col-md-1" id="validfrom">
                      
                             <label> Valid From  </label>
	                       	  <input type="text" class="form-control input-sm pisfromdate" style="width: 110px;"value="" name="ValidFrom" id="pisfromdate"   /> 
                      
                     </div>    
                     
                      <div class="col-md-1" style="margin-left:20px;" id="validtill">
	                       
	                             <label>Valid To </label>
	                       	     <input type="text" class="form-control input-sm pistodate"  style="width: 110px;" value="" name="ValidTo" id="pistodate"   />
	                      
                       </div>  
                       
                       </div>
                       </div>   
                       
                       
			      <div class="form-group">
			        <div class="row">
			            
			
			             <div class="col-md-4">
			                <label>Have you visited any foreign country before?</label>
                              
                           
			                <select name="ForeignVisit" class="form-control select2"  id="fvisit" required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                   
			                    <option value="N" <% if(ProcAbroad!=null){  if(ProcAbroad.getForeignVisit().toString().equals("N")){ %> selected <%}}%> >NO</option>
			                     <option value="Y" <% if(ProcAbroad!=null){  if(ProcAbroad.getForeignVisit().toString().equals("Y")){ %> selected <%}}%> >YES</option>
			                   
			                </select>
			                   
			                    
			              </div>
			              
			             <div class="col-md-6" style="margin-left:0px;" id="details">
			                <label> If so, give details of such visits to include countries visited with details of dates<span class="mandatory">*</span></label>
			                
                            <input class="form-control input-sm"type="text" id="" name="ForeignVisitDetails"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getForeignVisitDetails()%><%}%>" >
			                     
			                   
			                   </div>
			              </div>
			              
			           </div>
			           
			           
		     <div class="form-group">
			        <div class="row">
			            
			
			            <div class="col-md-3" >
			                <label>Countries proposed to be visited	</label>
			                
			                   <input class="form-control input-sm" type="text" id="" name="CountriesProposed"  value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getCountriesProposed() %><%}%>" >
			                     
			              </div>
			             
			             <div class="col-md-2" ">
			                <label> Date of departure	</label>
			                
                            <input type="text" class="form-control input-sm mydate"  value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getDepartureDate() %><%}%>"   id="departure" name="DepartureDate"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate"></label>  
			                     
			                   
			               </div>
			                   
			            <div class="col-md-3" >
			                <label> Purpose of visit  </label>
			                
                               <input class="form-control input-sm"  type="text" id="" name="VisitPurpose"    value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getVisitPurpose() %><%}%>" >
			                     
			                   
			            </div> 
			            
			                <div class="col-md-3" >
			                <label> Probable duration of stay at each country	 </label>
			                <input class="form-control input-sm"  type="text" id="" name="StayDuration"  min="0" value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getStayDuration() %><%} %>" >
			                     
			                   
			            </div> 
			            
			          </div>
			              
			       </div>
			       
			       
			    
			   <div class="form-group">
			        <div class="row">
			            
			
			            <div class="col-md-2" >
			                <label> Probable date of return	 </label>			
			                
			                <input type="text" class="form-control input-sm mydate"  value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getReturnDate() %><%}%>"    id="return" name="ReturnDate"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate"></label>  
			                     
			                   
			             </div>
			             
			             <div class="col-md-2">
			                <label> Whether going   </label>	
			                <select name="Going" class="form-control select2"  id="select" required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="A" <% if(ProcAbroad!=null){  if(ProcAbroad.getGoing().toString().equals("A")){ %> selected <%}}%> >Alone</option>
			                    <option value="WF" <% if(ProcAbroad!=null){  if(ProcAbroad.getGoing().toString().equals("WF")){ %> selected <%}}%> >With family</option>
			                   
			                </select>
                              
			                     
			                   
			             </div>
			                   
			            <div class="col-md-3" id="fam">
			                <label> If with  family, give details <span class="mandatory"	style="color: red;">*</span></label>

			                  <input class="form-control input-sm"  type="text" id="" name="FamilyDetails"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getFamilyDetails() %><%}%>" >
			                  
			              </div> 
			            
			                <div class="col-md-4" >
			                <label> Excepted Amount for trip(journey and stay abroad)</label>
			                <input class="form-control input-sm"  type="number" id="" name="ExpectedAmount" step=".01" min="0" max="9999999"  value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getExpectedAmount() %><%} else{%>0.00<%} %>"  >
			                     
			                   
			            </div> 
			            
			          </div>
			              
			       </div>
			       
			       
			     <div class="form-group">
			        <div class="row">
			            
			
			            <div class="col-md-2" >
			                <label>Is the trip financed by  </label>
			                
			                <select name="FinancedBy" class="form-control select2"  id="trip" required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="S" <% if(ProcAbroad!=null){  if(ProcAbroad.getFinancedBy().toString().equals("S")){ %> selected <%}}%>>SELF</option>
			                    <option value="OP" <% if(ProcAbroad!=null){  if(ProcAbroad.getFinancedBy().toString().equals("OP")){ %> selected <%}}%>>OTHER PERSON</option>
			                   
			                </select>
			               
                          </div>
                          
                          
			            
			             
			             <div class="col-md-3" id="amt">
			                <label>If so the source of amount being spent<span class="mandatory"	style="color: red;">*</span></label>

			                 <input class="form-control input-sm" type="text" id="" name="AmountSource"  min="0" value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getAmountSource() %><%} %>"  >
			                     
			                   
			            </div>
			              
			              <div class="col-md-3" id="name" >
			                <label>Name of the person <span class="mandatory"	style="color: red;">*</span></label>
			                <input class="form-control input-sm"  type="text" id="" name="Name1"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getName() %><%}%>"  >
			              </div> 
			              
			              <div class="col-md-3" id="nationality" >
			                <label>Nationality of the person <span class="mandatory"	style="color: red;">*</span></label>
			                <input class="form-control input-sm"  type="text" id="" name="Nationality"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getNationality() %><%}%>"  >
			              </div>
			            
			             <div class="col-md-3" id="rel" >
			                <label>Relationship  <span class="mandatory"	style="color: red;">*</span></label>
			                <input class="form-control input-sm"  type="text" id="" name="Relationship"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getRelationship() %><%}%>" >
			                     
			                   
			            </div> 
			            
			             <div class="col-md-3" id="add" >
			                <label>Address <span class="mandatory"	style="color: red;">*</span></label>
			                <input class="form-control input-sm"  type="text" id="" name="RelationshipAddress"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getRelationshipAddress() %><%}%>" >
			                     
			            </div> 
			                  
			           
			         </div>
			            
			            
			       </div>
			       
			       
			       
			    <div class="form-group">
			  
			        <div class="row">
			        
			       
			       
			        <div class="col-md-4">
			                <label> Details of passport lost, if any </label>
			                <input type="text" id="" name="LostPassport"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getLostPassport() %><%} else{%>NA<%} %>"
			                    class=" form-control input-sm " >
			             
			            </div>
			            
			             <div class="col-md-2">
			                <label> Passport Type Required </label>
			                <select name="Passporttype" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="Ordinary" <% if(ProcAbroad!=null){  if(ProcAbroad.getPassportType().toString().equals("Ordinary")){ %> selected <%}}%>     >Ordinary</option>
			                    <option value="Official" <% if(ProcAbroad!=null){  if(ProcAbroad.getPassportType().toString().equals("Official")){ %> selected <%}}%>     >Official</option>
			                    <option value="Diplomatic" <% if(ProcAbroad!=null){  if(ProcAbroad.getPassportType().toString().equals("Diplomatic")){ %> selected <%}}%> >Diplomatic</option>
			                </select>
			             
			            </div>
			            
			          
			         </div>
			        
			     </div>
			     
			     <div class="form-group">
			       <div class="row">
			        <div class="col-md-4" style="width:50px;">
			                <label > Are you likely to accept any foreign Hospitality </label>
                            <select name="Hospatility" class="form-control select2"  id=""  required="required" style="width:300px;">
			                 
			                    <option value="" selected="selected" disabled="disabled">Select</option>
			                  
			                    <option value="N" <% if(ProcAbroad!=null){  if(ProcAbroad.getHospatility().toString().equals("N")){ %> selected <%}}%> >NO</option>
			                     <option value="Y" <% if(ProcAbroad!=null){  if(ProcAbroad.getHospatility().toString().equals("Y")){ %> selected <%}}%> >YES</option>
			                </select>
			                   
			               
			        </div> 
			       
			            
			            
			        <div class="col-md-2">
			                <label>I certify that</label>
			                <select name="ContractualObligation" class="form-control select2"  style="width:310%;" id="Certify"  required="required">
			                 <!-- <option value="" selected="selected" disabled="disabled">Select</option>  -->
			                    <option value="N" <% if(ProcAbroad!=null){  if(ProcAbroad.getContractualObligation().toString().equals("N")){ %> selected <%}}%> >I am not under contractual obligation to serve STARC for any specific period </option>
			                    <option value="Y" <% if(ProcAbroad!=null){  if(ProcAbroad.getContractualObligation().toString().equals("Y")){ %> selected <%}}%> > I am under contractual obligation to serve STARC for a specific period</option>
			                   
			                </select>
			             
			            </div>
			            
			            
			            
			             <div class="col-md-1"  style="margin-left:356px;" id="showfromdate">
			                <label>From Date </label>
			               <div class=" input-group">
							    <input type="text" class="form-control input-sm mydate"  style="width:105px;" value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getFromDate() %><%} %>"   id="fromdate" name="fromdate"  required="required"  > 
							     <label class="input-group-addon btn" for="testdate">
							      
							    </label>                    
							</div>
			                    
			            </div>
			           
			            <div class="col-md-1" style="margin-left:15px;"  id="showtodate">
			                <label>To Date </label>
			               <div class=" input-group">
			               
							    <input type="text" class="form-control input-sm mydate"  style="width:105px;" value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getToDate() %><%} %>"   id="todate" name="todate"  required="required"  > 
							     <label class="input-group-addon btn" for="testdate">
							      
							    </label>                    
							</div>
			            </div>
			            
			           </div>
			     </div>
			     
			  
			 <div class="col-12" align="center">
			       
			    	<button type="submit" class="btn btn-sm submit-btn"  name="action" value="submit" onclick="return confirm('Are You Sure To Submit')">Submit</button>
			    	  <input type="hidden" name="ProcAbroadId" value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getNocProcId() %><%} %>"> 
			    	
			</div>
			    
			  </form>
			</div>
		</div>
	 </div>



	
<script type="text/javascript">




 
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

$('#return').daterangepicker({
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

$('#departure').daterangepicker({
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


$('#pisfromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	<% if(pispassport!=null && pispassport.getValidFrom()!=null){ %>
	"startDate" : new Date("<%=pispassport.getValidFrom()%>"),
	<% } %>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$('#pistodate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#pisfromdate').val(),    
	<% if(pispassport!=null && pispassport.getValidTo()!=null){ %>
	"startDate" : new Date("<%=pispassport.getValidTo()%>"),
	<% } %>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});	


window.onload = function() {
	
	   
	     var id=$('#Certify').val()
	 if(id=="Y"){
		 
		 $("#showfromdate").show();
		  $("#showtodate").show(); 
	}  
	 else{
	
		  $("#showfromdate").hide();
		  $("#showtodate").hide(); 
	 }
	};
	
	var select=$('#passporttype').val()
    
    if(select=="New" || select==undefined){
   	 
   	 $('#ptype').hide();
   	 $('#status').hide();
   	 $('#pno').hide();
   	 $('#validfrom').hide();
   	 $('#validtill').hide();
   	 
   }
    else{
   	 
   	 $('#ptype').show();
   	 $('#status').show();
   	 $('#pno').show();
   	 $('#validfrom').show();
   	 $('#validtill').show();
   	 
    }

	
	$(document).ready(function() {
	    $('#Certify').on('change', function() {
	    var selectedValue = $(this).val();
	    if(selectedValue=="Y"){
	    	
	    	$('#showfromdate').show();
	    	$('#showtodate').show();
	    }
	    else{
	    	
	    	$('#showfromdate').hide();
	    	$('#showtodate').hide();
	    }
	    
	    });
	  });
	
	
	 
	$(document).ready(function() {
		
		   var trip=$('#trip').val()
		   var family=$('#select').val()
		   var visit= $('#fvisit').val()
		   
		 if(trip=="S"){
			 
			    $('#amt').show();
			    $('#name').hide();
		    	$('#nationality').hide();
		    	$('#rel').hide();
		    	$('#add').hide();
		    	
		}
		 else{
			    $('#amt').hide();
		    	$('#name').hide();
		    	$('#nationality').hide();
		    	$('#rel').hide();
		    	$('#add').hide();
		     }
		 
		 if(trip=="OP"){
			 
			 $('#name').show();
			 $('#nationality').show();
			 $('#rel').show();
		     $('#add').show();
		     
		 }
		 else{
			 
			 $('#name').hide();
			 $('#nationality').hide();
			 $('#rel').hide();
		     $('#add').hide();
		   
		 }
		 
		 
		 if(family=="WF"){
			 $('#fam').show();
			 
		 }
		 else{
			 $('#fam').hide(); 
			 
		 }
		 
		 if(visit=="Y"){
			 
			 $('#details').show();
			 
		 }
		 else{
			 
			 $('#details').hide();
			 
		 }
		 
		 
	}); 
	
	$(document).ready(function() {
	    $('#trip').on('change', function() {
	    var selectedValue = $(this).val();
	    if(selectedValue=="S"){
	    	
	    	$('#amt').show();
	        $('#name').hide();
	        $('#nationality').hide();
	    	$('#rel').hide();
	    	$('#add').hide();
	    }
	    else{
	    	
	    	 $('#amt').hide();
	    	 $('#name').show();
	    	 $('#nationality').show();
	    	 $('#rel').show();
		     $('#add').show();
	    	 
	    }
	    
	    });
	    
});
	
	$(document).ready(function() {
	    $('#select').on('change', function() {
	    var selectedValue = $(this).val();
	    if(selectedValue=="WF"){
	    	
	    	 $('#fam').show();
	    }
	    else{
	    	 $('#fam').hide();
	    	
	    }
	
	    });
	    
	});
	
	$(document).ready(function() {
	    $('#fvisit').on('change', function() {
	    var selectedValue = $(this).val();
	    if(selectedValue=="Y"){
	    	
	    	 $('#details').show();
	    }
	    else{
	    	 $('#details').hide();
	    	
	    }
	
	    });
	    
	});

	
	$(document).ready(function() {
	    $('#passporttype').on('change', function() {
	    var selectedValue = $(this).val();
	    if(selectedValue=="New"){
	    	
	    	 $('#ptype').hide();
	    	 $('#status').hide();
	    	 $('#pno').hide();
	    	 $('#validfrom').hide();
	    	 $('#validtill').hide();
	    	
	    }
	    else{
	    	
	    	 $('#ptype').show();
	    	 $('#status').show();
	    	 $('#pno').show();
	    	 $('#validfrom').show();
	    	 $('#validtill').show();
	    	
	    }
	    if(selectedValue=="Renewal"){
	    	
	    	
	    	$('#passportno').attr('required', true);
	    	
	    }
	    
	    
	    });
	  });
	
	
	
		    
</script>

</body>
</html>
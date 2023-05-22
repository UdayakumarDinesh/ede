<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.noc.model.NocProceedingAbroad"%> 


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 

</head>
<body>

  <%
  
   
    Object[] NocEmpList= (Object[])request.getAttribute("NocEmpList");
    Object[] EmpPassport= (Object[])request.getAttribute("EmpPassport");
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	String Empid=(String)request.getAttribute("EmpId");
	
	 NocProceedingAbroad ProcAbroad=(NocProceedingAbroad)request.getAttribute("ProcAbroad");
	 
	
  %>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-4">
			
			  <h5>Proceeding Abroad</h5>
			 
			</div>
			<div class="col-md-8 ">
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
			
			<form action="ProcAbroadEditSubmit.htm" method="post" autocomplete="off" enctype="multipart/form-data" >
			
			<%} else {%>
			
			<form action="ProcAbroadAddSubmit.htm" method="post" autocomplete="off" enctype="multipart/form-data" >
			
			<%} %>
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			         
			           <div class="col-md-3">
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
			                    class=" form-control input-sm " readonly
			                     >
			            </div>
			             <div class="col-md-3">
			                <label>Present  Address </label>
			                <input type="text" id="" name="Present"    value="<%=NocEmpList[3] %>"
			                    class=" form-control input-sm " readonly
			                    >
			            </div>
				  </div>
			    </div>
			
			    <div class="form-group">
			        <div class="row">
			          <div class="col-md-3">
			                <label> Permanent Address </label>
			                <input type="text" id="" name="Permanent"    value="<%=NocEmpList[4] %>"
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
			                <label>Relation Name </label>
			                <input type="text" id="" name="RelationName"   value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getRelationName() %><%} %>"
			                   class=" form-control input-sm "  required="required" >
			                    
			            </div>
			            <div class="col-md-2">
			                <label>Relation Occupation </label>
			                <input type="text" id="" name="RelationOccupation"   value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getRelationOccupation() %><%} %>"
			                    class="form-control input-sm "  required="required">
			                   
			            </div>
			            
			             <div class="col-md-3">
			                <label>Relation Address </label>
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
			                <input type="text" name="RelationAbroad"     value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getRelationAbroad() %><%} %>"
			                    class="form-control input-sm "  required="required"  >
			                   
			                    </div>
			              </div>
			              
			             <div class="col-md-6">
			                <label> Details of employment during last ten years  </label>
                            
                             <div class="col-md-13" style="margin-left:0px;">  
			                <input type="text" name="EmployementDetails"     value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getEmployementDetails() %><%} %>"
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
			                <label> If so, give details of such visits to include countries visited with details of dates</label>
			                
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
			                <input class="form-control input-sm"  type="number" id="" name="StayDuration"  min="0" value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getStayDuration() %><%} %>" >
			                     
			                   
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
			                <input class="form-control input-sm"  type="number" id="" name="ExpectedAmount"   min="0" value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getExpectedAmount() %><%}%>"  >
			                     
			                   
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
                          
                          
			            
			             
			             <div class="col-md-2" id="amt">
			                <label>If self,amount spend <span class="mandatory"	style="color: red;">*</span></label>

			                 <input class="form-control input-sm" type="number" id="" name="AmountSpend"  min="0" value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getAmountSpend() %><%} %>"  >
			                     
			                   
			            </div>
			              
			               <div class="col-md-3" id="name" >
			                <label>Name, Nationality of the person <span class="mandatory"	style="color: red;">*</span></label>
			                <input class="form-control input-sm"  type="text" id="" name="NameNationality"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getNameNationality() %><%}%>"  >
			                     
			                   
			            </div> 
			            
			             <div class="col-md-3" id="rel" >
			                <label>Relationship  <span class="mandatory"	style="color: red;">*</span></label>
			                <input class="form-control input-sm"  type="text" id="" name="Relationship"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getRelationship() %><%}%>" >
			                     
			                   
			            </div> 
			            
			             <div class="col-md-3" id="add" >
			                <label>Address <span class="mandatory"	style="color: red;">*</span></label>
			                <input class="form-control input-sm"  type="text" id="" name="RelationshipAddress"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getRelationshipAddress() %><%}%>" >
			                     
			            </div> 
			                  
			            <div class="col-md-2" id="doc" >
			                <label> Documentary Proof  <span class="mandatory"	style="color: red;">*</span></label>
			                
                           <input type="file"  style="width: 120%;" class="form-control input-sm "  value="<% if(ProcAbroad!=null){%><%=ProcAbroad.getFilePath() %><%} %>" id="formFile" name="FormFile" 
							accept=".xlsx,.xls,.pdf,.doc,.docx ">
			                
			                <div class="col-md-2" >
			                <% if(ProcAbroad!=null) {
							if(!ProcAbroad.getFileName().toString().equals("") ){ %>
							<button type="submit" formnovalidate="formnovalidate" class="btn btn-sm" style="margin-left:204px; margin-top:-65px;" 
									name="ProcAbrId" value="<%=ProcAbroad.getNocProcId()%>"
									 formaction="NocProcAbroadDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
										  <i style="color: #019267" class="fa-solid fa-download fa-1x" ></i>
									</button>
									<%}} %> 
									</div>     
			                   
			            </div> 
			            
			         </div>
			            
			            
			       </div>
			       
			       
			       
			    <div class="form-group">
			  
			        <div class="row">
			         <% if(EmpPassport!=null) { %>
			         
			         <div class="col-md-2">
			                <label> Passport Type </label>
			                <input type="text" id="" name="Permanent"  value="<%=EmpPassport[0] %>"
			                    class=" form-control input-sm " readonly="readonly">
			                   
			            </div>
			            
			             <div class="col-md-2">
			                <label> Passport No. </label>
			                <input type="text" id="" name="Permanent"    value="<%=EmpPassport[1] %>"
			                    class=" form-control input-sm "  readonly="readonly">
			                   
			            </div> 
			            
			              <div class="col-md-2">
			                <label>Date of Issue  </label>
			                <input type="text" id="" name=""    value="<%=rdf.format(sdf.parse(EmpPassport[2].toString()))%>"
			                   class=" form-control input-sm "   readonly="readonly">
			                    
			            </div>
			            
			            <div class="col-md-2">
			                <label>Validity </label>
			                <input type="text" id="" name="RelationOccupation"   value="<%=rdf.format(sdf.parse(EmpPassport[3].toString()))%>"
			                    class=" form-control input-sm "  readonly="readonly">
			                   
			            </div>
			       <%} else{ %>
			       
			    	  <a type="button"  class="btn btn-sm add-btn"  style="margin-bottom:28px;margin-top:28px;margin-left:28px" href="AddEditPassport.htm?empid=<%=Empid %>&NOC=noc">Add Passport</a> 
			    	<%} %> 
			       
			        <div class="col-md-4">
			                <label> Details of passport lost, if any </label>
			                <input type="text" id="" name="LostPassport"   value="<% if(ProcAbroad!=null){%> <%=ProcAbroad.getLostPassport() %><%}%>"
			                    class=" form-control input-sm " >
			             
			            </div>
			          
			         </div>
			        
			     </div>
			     
			     <div class="form-group">
			       <div class="row">
			       
			        <div class="col-md-2">
			                <label> Passport Type Required </label>
			                <select name="Passporttype" class="form-control select2"  required="required">
			                <option value="" selected="selected" disabled="disabled">Select</option>
			                    <option value="Ordinary" <% if(ProcAbroad!=null){  if(ProcAbroad.getPassportType().toString().equals("Ordinary")){ %> selected <%}}%>     >Ordinary</option>
			                    <option value="Official" <% if(ProcAbroad!=null){  if(ProcAbroad.getPassportType().toString().equals("Official")){ %> selected <%}}%>     >Official</option>
			                    <option value="Diplomatic" <% if(ProcAbroad!=null){  if(ProcAbroad.getPassportType().toString().equals("Diplomatic")){ %> selected <%}}%> >Diplomatic</option>
			                </select>
			             
			            </div>
			            
			            
			        <div class="col-md-2">
			                <label>I certify that</label>
			                <select name="ContractualObligation" class="form-control select2"  style="width:290%;" id="Certify"  required="required">
			                 <!-- <option value="" selected="selected" disabled="disabled">Select</option>  -->
			                    <option value="N" <% if(ProcAbroad!=null){  if(ProcAbroad.getContractualObligation().toString().equals("N")){ %> selected <%}}%> >I am not under contractual obligation to serve STARC for any specific period </option>
			                    <option value="Y" <% if(ProcAbroad!=null){  if(ProcAbroad.getContractualObligation().toString().equals("Y")){ %> selected <%}}%> > I am under contractual obligation to serve STARC for a specific period</option>
			                   
			                </select>
			             
			            </div>
			            
			            
			            
			             <div class="col-md-2"  style="margin-left:335px;" id="showfromdate">
			                <label>From Date  <span class="mandatory"	style="color: red;">*</span></label>
			               <div class=" input-group">
							    <input type="text" class="form-control input-sm mydate"  value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getFromDate() %><%} %>"   id="fromdate" name="fromdate"  required="required"  > 
							     <label class="input-group-addon btn" for="testdate">
							      
							    </label>                    
							</div>
			                    
			            </div>
			           
			            <div class="col-md-2" style="margin-left:1px;"  id="showtodate">
			                <label>To Date <span class="mandatory"	style="color: red;">*</span></label>
			               <div class=" input-group">
			               
							    <input type="text" class="form-control input-sm mydate"  value="<%if(ProcAbroad!=null){ %><%=ProcAbroad.getToDate() %><%} %>"   id="todate" name="todate"  required="required"  > 
							     <label class="input-group-addon btn" for="testdate">
							      
							    </label>                    
							</div>
			            </div>
			            
			           </div>
			     </div>
			     
			   <div class="form-group">  
			     <div class="row">
			       <div class="col-md-4">
			                <label > Are you likely to accept any foreign Hospitality </label>
                            <select name="Hospatility" class="form-control select2"  id=""  required="required">
			                 
			                    <option value="" selected="selected" disabled="disabled">Select</option>
			                  
			                    <option value="N" <% if(ProcAbroad!=null){  if(ProcAbroad.getHospatility().toString().equals("N")){ %> selected <%}}%> >NO</option>
			                     <option value="Y" <% if(ProcAbroad!=null){  if(ProcAbroad.getHospatility().toString().equals("Y")){ %> selected <%}}%> >YES</option>
			                </select>
			                   
			               
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
		   
		   console.log("trip--"+ trip);
		  
		 if(trip=="S"){
			 
			    $('#amt').show();
			    $('#doc').show();
		    	$('#name').hide();
		    	$('#rel').hide();
		    	$('#add').hide();
		    	
		}
		 else{
			    $('#amt').hide();
		    	$('#doc').hide();
		    	$('#name').hide();
		    	$('#rel').hide();
		    	$('#add').hide();
		     }
		 
		 if(trip=="OP"){
			 
			 $('#name').show();
			 $('#rel').show();
		     $('#add').show();
		     $('#doc').show();
		 }
		 else{
			 
			 $('#name').hide();
			 $('#rel').hide();
		     $('#add').hide();
		    /*  $('#doc').hide(); */
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
	    	$('#doc').show();
	    	$('#name').hide();
	    	$('#rel').hide();
	    	$('#add').hide();
	    }
	    else{
	    	
	    	$('#amt').hide();
	    	$('#doc').show();
	    	 $('#name').show();
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

	
	$(function(){
	    $("#formFile").on('change', function(event)
		{
	    	
	    	var file = $("#formFile").val();
	    	
	       	var upld = file.split('.').pop(); 
	       	if(!(upld.toLowerCase().trim()==='pdf' || upld.toLowerCase().trim()==='xlsx' 
	       				|| upld.toLowerCase().trim()==='xls' || upld.toLowerCase().trim()==='doc' || upld.toLowerCase().trim()==='docx')  )
	       	{
	    	    alert("Only PDF,Word and Excel documents are allowed to Upload")
	    	    document.getElementById("formFile").value = "";
	    	    return;
	    	}
	        
	    });
	});
	
		    
</script>

</body>
</html>
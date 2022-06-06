<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.vts.ems.pis.model.DivisionMaster"%>
<%@page import="com.vts.ems.pis.model.PisPayLevel"%>
<%@page import="com.vts.ems.pis.model.EmpStatus"%>
<%@page import="com.vts.ems.pis.model.PisCadre"%>
<%@page import="com.vts.ems.pis.model.PisCatClass"%>
<%@page import="com.vts.ems.pis.model.PisCategory"%>
<%@page import="com.vts.ems.pis.model.EmployeeDesig"%>
<%@page import="com.vts.ems.pis.model.EmployeeDetails"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
	
 <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script> --> 
<!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script> -->
 <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script> -->
<!-- <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" /> -->
<script type="text/javascript">
function validateform(){
	var x = confirm("Are you sure To Submit?");
	
	  if (x){
		  var pan =$("#PAN").val();
		  var uid =$("#UIDTextBox").val();
		  var sbiAccount = $("#SBITextBox").val();
		  var internalnum = $("#InternalNum").val();
		  
		if(pan.length<10){
			 alert("Check PAN Number!");
		       event.preventDefault();
		       return false;
		}
		if(uid.length<12){
			 alert("Check UID Number!");
		       event.preventDefault();
		       return false;
		}
		if(sbiAccount.length<11){
			 alert("Check SBI Number!");
		       event.preventDefault();
		       return false;
		}
		if(internalnum.length<4){
			 alert("Check Internal Number!");
		       event.preventDefault();
		       return false;
		}
	      return true;
	  
	  }else{
	 return false;}
}
</script>
</head>
<body>

<%

SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
SimpleDateFormat sdf1=DateTimeFormatUtil.getRegularDateFormat();

List<EmployeeDesig> desiglist=(List<EmployeeDesig>)request.getAttribute("desiglist");
List<PisCategory> piscategorylist=(List<PisCategory>)request.getAttribute("piscategorylist");
List<PisCatClass> piscatclasslist=(List<PisCatClass>)request.getAttribute("piscatclasslist");
List<PisCadre> piscaderlist=(List<PisCadre>)request.getAttribute("piscaderlist");
List<EmpStatus> empstatuslist=(List<EmpStatus>)request.getAttribute("empstatuslist");
List<PisPayLevel> paylevellist=(List<PisPayLevel>)request.getAttribute("paylevellist");
List<DivisionMaster> divisionlist=(List<DivisionMaster>)request.getAttribute("divisionlist");

Employee emp=(Employee)request.getAttribute("emp");
EmployeeDetails employee=(EmployeeDetails )request.getAttribute("employee");
SimpleDateFormat dateconvertion = new SimpleDateFormat("yyyy-MM-dd");


%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Employee Edit</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
					<li class="breadcrumb-item "><a href="PisAdminEmpList.htm">Employee List</a></li>
					<li class="breadcrumb-item active " aria-current="page">Employee Edit</li>
				</ol>
			</div>	
		</div>
	</div>	


 <div class="  page card dashboard-card">
	
	
	<div class="card-body" >
		<div class="card" >
						
						
			<div class="card-body">
				<form action="EmployeeEditSubmit.htm" method="post" autocomplete="off" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			
			            <div class="col-md-2">
			                <label>Rank/Salutation<span class="mandatory">*</span></label><br>
			                 <select class=" form-control select2" name="salutation" required="required">
								<option value="Mr."   <%if(employee!=null && employee.getTitle()!=null && employee.getTitle().equalsIgnoreCase("Mr.")){ %>selected  <%} %>  >Mr.</option>
								<option value="Mrs."  <%if(employee!=null && employee.getTitle()!=null && employee.getTitle().equalsIgnoreCase("Mrs.")){ %>selected  <%} %>  >Mrs.</option>
								<option value="Miss." <%if(employee!=null && employee.getTitle()!=null && employee.getTitle().equalsIgnoreCase("Miss.")){ %>selected  <%} %> >Miss.</option>
								<option value="Dr."   <%if(employee!=null && employee.getTitle()!=null && employee.getTitle().equalsIgnoreCase("Dr.")){ %>selected  <%} %> >Dr.</option>
							</select>
			            </div>
			
			
			            <div class="col-md-4">
			                <label>Name<span class="mandatory">*</span></label>
			                <input type="text" name="empname" required="required" id="empname" style="text-transform:capitalize"  class="form-control input-sm" value="<%=emp.getEmpName() %>"  maxlength="100"  placeholder="Enter Employee name"   onclick="return trim(this)" onchange="return trim(this)">
			                <%-- <input name="empname" required="required" id="empname" style="text-transform:uppercase" value="<%=employee.getEmpName() %>" maxlength="75" placeholder="Enter Employee name" class="form-control input-sm" onclick="return trim(this)" onkeyup="return trim(this)" /> --%>
			            </div>			
			            <div class="col-md-2">			
			                <label>Designation<span class="mandatory">*</span></label>
			                <select class="form-control select2 " name="Designationid" required="required" data-live-search="true" >
								<%if(desiglist!=null && desiglist.size()>0){
					                for(EmployeeDesig desig : desiglist){ %>
				                    <option value="<%=desig.getDesigId() %>" <%if(emp.getDesigId()==desig.getDesigId()){ %>selected  <%} %> ><%= desig.getDesignation()%></option>
					                <%}
				                } %>  
							</select>
			            </div>
			
			            <div class=" col-md-2 ">
			                <label>Employee No<span class=" mandatory ">*</span></label>
			                <input type="text" id="PunchcardTextBox" name="PunchCardNo" id="PunchCard" value="<%if(employee!=null&employee.getPunchCard()!=null){%><%=employee.getPunchCard()%><%}%>" maxlength="4"
			                    class=" form-control input-sm " placeholder="Enter Employee No" required="required"
			                     onblur="checknegative(this) ">
			            </div>
			            
			         <div class="col-md-2">
			                <label>Division <span class="mandatory">*</span></label>
			                <select name="divisionid" class="form-control input-sm select2" required data-live-search="true">
								<%for( DivisionMaster division: divisionlist){ %>
									<option value="<%=division.getDivisionId()%>"<%if(employee!=null   && division.getDivisionId()==emp.getDivisionId()){%>selected<%}%>><%=division.getDivisionName()%></option>
								<%} %>			
			
			                </select>
			            </div>
			
			        </div>
			    </div>
			
			    <div class="form-group">
			        <div class="row">
	
			
			 <div class="col-md-2">
						 	<label>DOB<span class="mandatory">*</span></label>
							<div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly" value="<%if(employee!=null&&employee.getDOB()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(employee.getDOB().toString())%><%}%>" placeholder=""  id="dob" name="dob"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">
							      
							    </label>                    
							</div>
						 </div>
		
			        <%--     <div class="col-md-2">
			                <label>DOA<span class="mandatory">*</span></label>
			               <div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly" value="<%if(employee!=null&&employee.getDOA()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(employee.getDOA().toString()) %><%}%>" placeholder=""  id="doa" name="doa"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">
							      
							    </label>                    
							</div>
			            </div> --%>
			
			
			            <div class="col-md-2">
			                <label>DOJ<span class="mandatory">*</span></label>
			                <div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly" value="<%if(employee!=null&&employee.getDOJL()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(employee.getDOJL().toString()) %><%}%>" placeholder=""  id="doj" name="doj"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">
							    </label>                    
							</div>
			            </div>

			            <div class="col-md-2">
			                <label>Gender<span class="mandatory">*</span></label>
			                <select name="gender" class="form-control input-sm" required>
			                    <option value="M" <%if(employee!=null && employee.getGender()!=null  && employee.getGender().equalsIgnoreCase("M")){ %>selected  <%} %> >Male</option>
			                    <option value="F" <%if(employee!=null && employee.getGender()!=null  && employee.getGender().equalsIgnoreCase("F")){ %>selected  <%} %>>Female</option>
			                </select>
			            </div>
	       
			
						<div class=" col-md-2 ">
			                <label>Email<span class=" mandatory ">*</span></label>
			                <input type="email"  name="email" class=" form-control input-sm " maxlength="100"
			                  value="<%if(emp!=null && emp.getEmail()!=null){%><%=emp.getEmail()%><%}%>"  placeholder="Enter Email " required="required" onclick=" return trim(this) "
			                    onchange=" return trim(this) ">
			            </div>
			            
			           <div class="col-md-2">
			                <label>PAN<span class="mandatory">*</span></label>
			                <input type="text" id="PAN" name="pan" style="text-transform:uppercase" value="<%if(employee!=null && employee.getPAN()!=null){%><%=employee.getPAN()%><%}%>" class="form-control input-sm " maxlength="10" placeholder="Enter PAN">
			            </div>
			
			
			            <div class="col-md-2">
			                <label>UID<span class="mandatory">*</span></label>
			                <input id="UIDTextBox" type="text" name="uid" value="<%if(employee!=null){%><%=employee.getUID()%><%}%>" class="form-control input-sm" maxlength="12" placeholder="Enter UID" required>
			            </div>
		
			        </div>
			    </div>
			   
			    <div class="form-group">
			        <div class="row">
			        
						 <div class=" col-md-2 ">
			                <label>Religion<span class=" mandatory ">*</span></label>
			                <select name="religion" class=" form-control input-sm select2 " data-live-search=" true ">
			              
			                    <option value="Christian" <%if(employee!=null && employee.getReligion()!=null  && employee.getReligion().equalsIgnoreCase("Christian")){%>selected<%}%>>Christian</option>
			                    <option value="Hindu"     <%if(employee!=null && employee.getReligion()!=null  && employee.getReligion().equalsIgnoreCase("Hindu")){%>selected<%}%> >Hindu</option>
			                    <option value="Islam"     <%if(employee!=null && employee.getReligion()!=null  && employee.getReligion().equalsIgnoreCase("Islam")){%>selected<%}%> >Islam</option>
			                    <option value="Jain"      <%if(employee!=null && employee.getReligion()!=null  && employee.getReligion().equalsIgnoreCase("Jain")){%>selected<%}%>>Jain </option>
			                    <option value="Parsi"     <%if(employee!=null && employee.getReligion()!=null  && employee.getReligion().equalsIgnoreCase("Parsi")){%>selected<%}%>>Parsi </option>
			                    <option value="Sikh"      <%if(employee!=null && employee.getReligion()!=null  && employee.getReligion().equalsIgnoreCase("Sikh")){%>selected<%}%>>Sikh</option>
			                    <option value="Others"    <%if(employee!=null && employee.getReligion()!=null  && employee.getReligion().equalsIgnoreCase("Others")){%>selected<%}%>>Others</option>
			
			                </select>
			            </div>
			            

						<div class=" col-md-2 ">
			                <label>Service Status<span class=" mandatory ">*</span></label>
			                <select name="ServiceStatus" class=" form-control input-sm select2  " required="required"
			                    data-live-search=" true ">		
			                    <option value=" Confirmed " <%if(employee!=null && employee.getServiceStatus()!=null && employee.getServiceStatus().equalsIgnoreCase("Confirmed")){%>selected<%}%>>Confirmed</option>
			                    <option value=" Probation " <%if(employee!=null && employee.getServiceStatus()!=null && employee.getServiceStatus().equalsIgnoreCase("Probation")){%>selected<%}%>>Probation</option>
			                    <option value=" Adhoc "     <%if(employee!=null && employee.getServiceStatus()!=null && employee.getServiceStatus().equalsIgnoreCase("Adhoc"))    {%>selected<%}%>>Adhoc</option>
			                    <option value=" Temporary " <%if(employee!=null && employee.getServiceStatus()!=null && employee.getServiceStatus().equalsIgnoreCase("Temporary")){%>selected<%}%>>Temporary</option>
			                    <option value=" Contract "  <%if(employee!=null && employee.getServiceStatus()!=null && employee.getServiceStatus().equalsIgnoreCase("Contract")) {%>selected<%}%>>Contract</option>
			                </select>
			            </div>
			            
			            <div class=" col-md-2 ">
			                <label>Phone No<span class=" mandatory ">*</span></label>
			                <input type="text"  name="PhoneNo" id="PhoneNo" <%if(employee!=null && employee.getPhoneNo()!=null){%> value="<%=employee.getPhoneNo()%>"<%}%> maxlength="10"
			                    class=" form-control input-sm " placeholder="Enter Phone no " required="required"
			                     onblur="checknegative(this) ">
			            </div>
			            
			            <div class=" col-md-2 ">
			                <label>SBI Account<span class=" mandatory ">*</span></label>
			                <input type="text" id="SBITextBox" value="<%if(employee!=null && employee.getSBIAccNo()!=null ){%><%=employee.getSBIAccNo()%><%}%>" name="SBI" class=" form-control input-sm " required
			                    maxlength=" 11 " placeholder="Enter Account Number " onblur=" checknegative(this) ">
			            </div>
			            
						 <%-- <div class=" col-md-2 ">
			                <label>PunchCard No<span class=" mandatory ">*</span></label>
			                <input type="text" id="PunchcardTextBox" name="PunchCardNo" id="PunchCard" value="<%if(employee!=null){%><%=employee.getPunchCard()%><%}%>" maxlength="4"
			                    class=" form-control input-sm " placeholder="Enter PunchCard " required="required"
			                     onblur="checknegative(this)">
			            </div> --%>

                        <div class=" col-md-2 ">
			                <label>PayLevel<span class=" mandatory ">*</span></label>
			                <select name="payLevel" class=" form-control input-sm select2 " data-live-search=" true ">
								<%for( PisPayLevel paylevel: paylevellist){ %>
									<option value="<%=paylevel.getPayLevelId()%>"  <%if(paylevel.getPayLevelId()==employee.getPayLevelId()){%>selected<%}%>><%=paylevel.getPayLevel()%></option>
								<%} %>
			
			                </select>
			            </div>
			 			
			 			<div class=" col-md-2 ">
			                <label>Basic Pay<span class=" mandatory ">*</span></label>
			                <input type="text" id="basicpaybox" 
			                <%if(employee.getBasicPay()!=null ){ %>
			                
			                value="<%=employee.getBasicPay() %>" 
			                <%}else{ %>
			                
			                 value="0" 
			                <%} %>
			                
			                name="basicpay" class=" form-control input-sm " maxlength="12"
			                    placeholder="Basic Pay" required="required">
			            </div>
			
			        </div>
			    </div>
			
			    <div class=" form-group ">
			        <div class=" row ">

     				<div class="col-md-2">
			                <label class="text-nowrap">Blood Group<span class="mandatory">*</span></label>
			                <select name="bloodgroup" class="form-control input-sm select2" required data-live-search="true">
			
			                    <option value="A-"  <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("A-")){%>selected<%}%>   >A-</option>
			                    <option value="A+"  <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("A+")){%>selected<%}%>   >A+</option>
			                    <option value="B-"  <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("B-")){%>selected<%}%>   >B-</option>
			                    <option value="B+"  <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("B+")){%>selected<%}%>   >B+</option>
			                    <option value="AB-" <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("AB-")){%>selected<%}%>  >AB-</option>
			                    <option value="AB+" <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("AB+")){%>selected<%}%>  >AB+</option>
			                    <option value="O-"  <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("O-")){%>selected<%}%>   >O-</option>
			                    <option value="O+"  <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("O+")){%>selected<%}%>   >O+</option>
			                    <option value="NOT" <%if(employee!=null && employee.getBloodGroup()!=null && employee.getBloodGroup().equalsIgnoreCase("NOT")){%>selected<%}%>  >Not Available</option>
			                </select>
			            </div>
			
						
			            
			            <div class=" col-md-2 ">
			                <label>Category<span class=" mandatory ">*</span></label>
			                <select name="category" class=" form-control input-sm select2 " required data-live-search="true">
								<%for( PisCategory category: piscategorylist){ %>
									<option value="<%=category.getCategory_id()%>" <%if(category.getCategory_id()==employee.getCategoryId()){%> selected <%}%>><%=category.getCategory_desc()%></option>
								<%} %>				
			                </select>
			            </div>
			
			
			            <!-- <div class=" col-md-2 ">
			                <label> Sub Category/Caste <span class=" mandatory ">*</span></label>
			                <input type="text" id="subcategory1 " name="subcategory" value="" maxlength=" 20 "
			                    class=" form-control input-sm " placeholder="Enter Sub Category ">
			
			            </div> -->
			
			               <div class=" col-md-2 ">
			                <label>Home Town<span class=" mandatory ">*</span></label>
			                <input type="text" id="txtName" name="HomeTown" style=" text-transform:uppercase " value="<%if(employee!=null && employee.getHomeTown()!=null ){%><%=employee.getHomeTown()%><%} %>"
			                    maxlength=" 240 " class=" form-control input-sm " placeholder="Enter Home Town " required="required"
			                    onclick=" Validate() ">
			               </div>
						
						
			            
			            <div class=" col-md-2 ">
			                <label>Internal number<span class="mandatory"></span></label>
			                <input type="text" name="internalNo" value="<%if(employee!=null && employee.getInternalNumber()!=null){%><%=employee.getInternalNumber()%><%}%>" maxlength="4" class=" form-control input-sm "
			                    placeholder="Enter Internal Number " onblur=" checknegative(this) "
			                    onkeypress=" return isNumber(event) ">
			            </div>
			            
			                        <div class="col-md-2">
			                <label class="text-nowrap  ">Cadre Name<span class="mandatory">*</span></label>
			                <select name="caderid" id="CadreId" class="form-control select2">
								<%for( PisCadre cadre: piscaderlist){ %>
									<option value="<%=cadre.getCadreId()%>"  <%if( employee.getCadreId()!=0 && employee.getCadreId()==cadre.getCadreId()){ %>selected  <%} %>   ><%=cadre.getCadre()%></option>
								<%} %>
			                </select>
			            </div>
			
			            <div class="col-md-2">
			                <label>CAT CLASS<span class="mandatory">*</span></label>
			                <select name="catcode" class="form-control select2" required data-live-search="true">
								<%for( PisCatClass catclass: piscatclasslist){ %>
									<option value="<%=catclass.getCat_id()%>"   <%if(employee.getCatId()!=null && employee.getCatId().equalsIgnoreCase(catclass.getCat_id()) ){ %>selected  <%} %>    ><%=catclass.getCat_name()%></option>
								<%} %>
			
			                </select>
			            </div>
			
			        </div>
			    </div>
					    
			    
			    
			    <div class=" form-group ">
			        <div class=" row ">

						<div class="col-md-2">
			                <label>Availed Govt Quarters</label>
			                
			                <select name="gq" class=" form-control input-sm select2 " >
			                	<option value="N" <%if(employee!=null && employee.getQuarters()!=null  && employee.getQuarters().equalsIgnoreCase("N")){%>selected<%}%>>No</option>
								<option value="Y" <%if(employee!=null && employee.getQuarters()!=null  && employee.getQuarters().equalsIgnoreCase("Y")){%>selected<%}%>>YES</option>								
			                </select>	
			            </div>
			            
			             <div class=" col-md-2 ">
			                <label>Physically Handicap</label>
			                
			                 <select name="ph" class=" form-control input-sm select2 " >
			                 	<option value="N" <%if(employee!=null && employee.getPH()!=null &&employee.getPH().equalsIgnoreCase("N")){%>selected<%}%>>No</option>
								<option value="Y" <%if(employee!=null && employee.getPH()!=null  &&employee.getPH().equalsIgnoreCase("Y")){%>selected<%}%>>YES</option>								
			                </select>	

			            </div>
			            
			           
			            
			            
			            <div class=" col-md-2 ">
			                <label>Marital Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			                <select name="MaritalStatus" class=" form-control input-sm select2 " >
			                	<option value="U" <%if(employee!=null && employee.getMaritalStatus()!=null && employee.getMaritalStatus().equalsIgnoreCase("U")){%>selected<%}%>>UnMarried</option>
								<option value="M" <%if(employee!=null && employee.getMaritalStatus()!=null && employee.getMaritalStatus().equalsIgnoreCase("M")){%>selected<%}%>>Married</option>									
			                </select>			                
			            </div>
			            
			            
			            <!-- <div class=" col-md-2 ">
			                <label>PIN DRONA:</label>
			                <input type="text" name="drona" value="" class=" form-control input-sm " maxlength="10" placeholder="Enter DRONA " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div> -->
			
			
			
			            <div class=" col-md-2 ">
			                <label>GPF/PRAN:</label>
			                <input type="text" name="gpf" value="<%if(employee!=null && employee.getGPFNo()!=null){%><%=employee.getGPFNo()%><%}%>" class=" form-control input-sm " maxlength=" 12 "
			                    placeholder="Enter GPF " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			
			            <div class=" col-md-4 ">
			                <label>Identification Mark</label>
			                <input type="text" value="<%if(employee!=null && employee.getIdMark()!=null){%><%=employee.getIdMark()%><%}%>" name="idMark" class=" form-control input-sm " maxlength="99"
			                    placeholder="Enter Identification Mark " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			            
					</div>
				</div>
			            
			            
			  <!--   <div class=" form-group ">
			        <div class=" row "> -->
			
			
			          
			
			           <%--  <div class=" col-md-2 ">
			                <label>Emp Status<span class=" mandatory ">*</span></label>
			                <select id="Emptype" name="empstatus"  id="empstatus" class=" form-control input-sm select2 " required data-live-search="true"  >
                   				<%for( EmpStatus status: empstatuslist){ %>
								<option value="<%=status.getEmp_status_id()%>"><%=status.getEmp_status_name()%></option>
								<%} %>
								
								<option value="1">PRESENT</option>
			
			                </select>
			            </div> --%>
			
			          <%--   <div class=" col-md-2 " id=" EmpHide ">
			                <label>Emp Status Date<span class=" mandatory ">*</span></label>
			                <input type="date" name="EmpStatusDate" value="<%=LocalDate.now() %>" class=" form-control input-sm " placeholder="Enter EmpStatus Date ">
			            </div> --%>
			            
			             <!-- <div class=" col-md-2 ">
			                <label>Ex ServiceMan   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			                
			                 <select name="ExMan" class=" form-control input-sm select2 " >
			                	<option value="N">No</option>
								<option value="Y">Yes</option>	
								
			                </select>	
			                
			            </div>
			
			            <div class=" col-md-2 ">
			                <label>Per Pass No</label>
			                <input type="text" name="PermPassNo" value="" class=" form-control input-sm " maxlength="10"
			                    placeholder="Enter Permanent Pass No">
			            </div> -->
			      <!--   </div>
			    </div> -->
			     <div class=" form-group ">
			        <div class="row">
			        
			    
			
			        </div>
    			</div>

			    
			    <div class="row" >
			    	<div class="col-12" align="center">
					<input type="hidden" value="<%=emp.getEmpId()%>" name="EmpId">
					<input type="hidden" value="<%=employee.getEmpDetailsId()%>" name="empdetailsid">
					<button type="submit" class="btn btn-sm submit-btn" name="action" Onclick="return validateform();" value="submit" >SUBMIT</button>
			    	</div>
			    </div> 
			
				</form>
			</div>
		</div>
	
	 </div>
</div>




<script type="text/javascript">
setPatternFilter($("#PunchcardTextBox"), /^-?\d*$/);
setPatternFilter($("#PhoneNo"), /^-?\d*$/);
setPatternFilter($("#UIDTextBox"), /^-?\d*$/);
setPatternFilter($("#SBITextBox"), /^-?\d*$/);
setPatternFilter($("#PAN"),/^[a-zA-Z0-9_]*$/);
setPatternFilter($("#basicpaybox"), /^-?\d*$/);

function setPatternFilter(obj, pattern) {
	  setInputFilter(obj, function(value) { return pattern.test(value); });
	}

function setInputFilter(obj, inputFilter) {
	  obj.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
	    if (inputFilter(this.value)) {
	      this.oldValue = this.value;
	      this.oldSelectionStart = this.selectionStart;
	      this.oldSelectionEnd = this.selectionEnd;
	    } else if (this.hasOwnProperty("oldValue")) {
	      this.value = this.oldValue;
	      this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	    }
	  });
	}
function checknegative(str) {
    if (parseFloat(document.getElementById(str.id).value) < 0) {
        document.getElementById(str.id).value = "";
        document.getElementById(str.id).focus();
        alert('Negative Values Not allowed');
        return false;
    }
}

</script>


<script type="text/javascript">


$(document).ready(function () {
	
	
 $('#dob').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

 $('#doa').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :  $('#dob').val(), 
		//"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 
 $('#doj').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :  $('#dob').val(), 
		//"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
 
 
});


$('#dob').on('change', function() { 
    var datearray =  $('#dob').val();
   
    $('#doa').daterangepicker({
		
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :datearray, 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
    
	 $('#doj').daterangepicker({
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,
			"minDate" :datearray,  
			"startDate" : new Date(),
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
  });
 




	 $('empstatus').select2("enable", false)
	 
	 $('#empname').keypress(function (e) {
	        var regex = new RegExp("^[a-zA-Z \s]+$");
	        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	        if (regex.test(str)) {
	            return true;
	        }
	        else
	        {
	        e.preventDefault();
	        alert('Please Enter Alphabate');
	        return false;
	        }
	    });
	
	
</script>
<script type="text/javascript">
$("#PunchcardTextBox").blur(function(){

    var punchcard =$("#PunchcardTextBox").val();

    if(punchcard.length>=4){

         $.ajax({
                   url:"requestbypunchajax",
                   type:"GET",
               	  data:{PunchCardNo:punchcard},
                   dataType:'Json',
                   success:function(data){
                      var rr=data;
                      var a = parseInt(rr) ;
                            
                      if (a > 1){
                 	    
                 		alert("Punch Card No. All ready Taken!");

                     	}
            }
             });


        }else{

     	   $("#awailable").html(" ");

            }
      });


function isNumber(evt)
  {
   evt = (evt) ? evt : window.event;
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    return false;
  }
   return true; 
}
</script>

</body>
</html>
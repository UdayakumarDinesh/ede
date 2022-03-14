<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.vts.ems.pis.model.DivisionMaster"%>
<%@page import="com.vts.ems.pis.model.PisPayLevel"%>
<%@page import="com.vts.ems.pis.model.EmpStatus"%>
<%@page import="com.vts.ems.pis.model.PisCadre"%>
<%@page import="com.vts.ems.pis.model.PisCatClass"%>
<%@page import="com.vts.ems.pis.model.PisCategory"%>
<%@page import="com.vts.ems.pis.model.EmployeeDesig"%>
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

</head>
<body>

<%

DateTimeFormatUtil util =new DateTimeFormatUtil();
SimpleDateFormat sdf= util.getSqlDateFormat();
SimpleDateFormat sdf1=util.getRegularDateFormat();

List<EmployeeDesig> desiglist=(List<EmployeeDesig>)request.getAttribute("desiglist");
List<PisCategory> piscategorylist=(List<PisCategory>)request.getAttribute("piscategorylist");
List<PisCatClass> piscatclasslist=(List<PisCatClass>)request.getAttribute("piscatclasslist");
List<PisCadre> piscaderlist=(List<PisCadre>)request.getAttribute("piscaderlist");
List<EmpStatus> empstatuslist=(List<EmpStatus>)request.getAttribute("empstatuslist");
List<PisPayLevel> paylevellist=(List<PisPayLevel>)request.getAttribute("paylevellist");
List<DivisionMaster> divisionlist=(List<DivisionMaster>)request.getAttribute("divisionlist");

Employee employee=(Employee )request.getAttribute("employee");
SimpleDateFormat dateconvertion = new SimpleDateFormat("yyyy-MM-dd");


%>



 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Employee Add</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm">Home</a></li>
					<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
					<li class="breadcrumb-item "><a href="PisAdminEmpList.htm">Employee List</a></li>
					<li class="breadcrumb-item active " aria-current="page">Employee Add</li>
				</ol>
			</div>	
		</div>
	</div>	
	
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
								<option value="Mr." <%if(employee.getTitle().equalsIgnoreCase("Mr.")){ %>selected  <%} %>  >Mr.</option>
								<option value="Mrs." <%if(employee.getTitle().equalsIgnoreCase("Mrs.")){ %>selected  <%} %>  >Mrs.</option>
								<option value="Miss." <%if(employee.getTitle().equalsIgnoreCase("Miss.")){ %>selected  <%} %> >Miss.</option>
								<option value="Dr." <%if(employee.getTitle().equalsIgnoreCase("Dr.")){ %>selected  <%} %> >Dr.</option>
							</select>
			            </div>
			
			
			            <div class="col-md-4">
			                <label>Name<span class="mandatory">*</span></label>
			                <input name="empname" required="required" id="empname" style="text-transform:uppercase" value="<%=employee.getEmpName() %>" maxlength="75" placeholder="Enter Employee name" class="form-control input-sm" onclick="return trim(this)" onkeyup="return trim(this)" />
			            </div>			
			            <div class="col-md-2">			
			                <label>Designation<span class="mandatory">*</span></label>
			                <select class="form-control select2 " name="Designationid" required="required" data-live-search="true" >
								<%if(desiglist!=null && desiglist.size()>0){
					                for(EmployeeDesig desig : desiglist){ %>
				                    <option value="<%=desig.getDesigId() %>" <%if(employee.getDesignationId()==desig.getDesigId()){ %>selected  <%} %> ><%= desig.getDesignation()%></option>
					                <%}
				                } %>  
							</select>
			            </div>
			
			            <div class="col-md-2">
			                <label class="text-nowrap  ">Cadre Name<span class="mandatory">*</span></label>
			                <select name="caderid" id="CadreId" class="form-control select2">
								<%for( PisCadre cadre: piscaderlist){ %>
									<option value="<%=cadre.getCadreId()%>"  <%if(employee.getCadreId()==cadre.getCadreId()){ %>selected  <%} %>   ><%=cadre.getCadre()%></option>
								<%} %>
			                </select>
			            </div>
			
			            <div class="col-md-2">
			                <label>CAT CLASS<span class="mandatory">*</span></label>
			                <select name="catcode" class="form-control select2" required data-live-search="true">
								<%for( PisCatClass catclass: piscatclasslist){ %>
									<option value="<%=catclass.getCat_id()%>"   <%if(employee.getCatId().equalsIgnoreCase(catclass.getCat_id()) ){ %>selected  <%} %>    ><%=catclass.getCat_name()%></option>
								<%} %>
			
			                </select>
			            </div>
			        </div>
			    </div>
			
			    <div class="form-group">
			        <div class="row">
			
			            <div class="col-md-2">
			                <label>DOB<span class="mandatory">*</span></label>
			                
			                <input type="date" style="width: 100%;" class="form-control input-sm" value="<%if(employee!=null&&employee.getDOB()!=null){%><%=dateconvertion.format(dateconvertion.parse(employee.getDOB().toString())) %><%}%>" placeholder=""  id="dob" name="dob"  required="required"  >
			            </div>
			
			
			            <div class="col-md-2">
			                <label>DOA<span class="mandatory">*</span></label>
			                <input type="date" style="width: 100%;" value="<%if(employee!=null&&employee.getDOA()!=null){%><%=dateconvertion.format(dateconvertion.parse(employee.getDOA().toString())) %><%}%>" class="form-control input-sm "  placeholder="Date of appointment" name="doa" required maxlength="10">
			            </div>
			
			
			            <div class="col-md-2">
			                <label>DOJ<span class="mandatory">*</span></label>
			                <input type="date" style="width: 100%;" value="<%if(employee!=null&&employee.getDOJL()!=null){%><%=dateconvertion.format(dateconvertion.parse(employee.getDOJL().toString())) %><%}%>" class="form-control input-sm " placeholder="Date of Joining" name="doj" required maxlength="10">
			            </div>
			
			            <div class="col-md-2">
			                <label>Gender<span class="mandatory">*</span></label>
			                <select name="gender" class="form-control input-sm" required>
			                    <option value="M" <%if(employee.getGender().equalsIgnoreCase("M")){ %>selected  <%} %> >Male</option>
			                    <option value="F" <%if(employee.getGender().equalsIgnoreCase("F")){ %>selected  <%} %>>Female</option>
			                </select>
			            </div>
			
			
			            <div class="col-md-2">
			                <label class="text-nowrap">Blood Group<span class="mandatory">*</span></label>
			                <select name="bloodgroup" class="form-control input-sm select2" required data-live-search="true">
			
			                    <option value="A-"  <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("A-")){%>selected<%}%>   >A-</option>
			                    <option value="A+"  <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("A+")){%>selected<%}%>   >A+</option>
			                    <option value="B-"  <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("B-")){%>selected<%}%>   >B-</option>
			                    <option value="B+"  <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("B+")){%>selected<%}%>   >B+</option>
			                    <option value="AB-" <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("AB-")){%>selected<%}%>  >AB-</option>
			                    <option value="AB+" <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("AB+")){%>selected<%}%>  >AB+</option>
			                    <option value="O-"  <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("O-")){%>selected<%}%>   >O-</option>
			                    <option value="O+"  <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("O+")){%>selected<%}%>   >O+</option>
			                    <option value="NOT" <%if(employee!=null&&employee.getBloodGroup().equalsIgnoreCase("NOT")){%>selected<%}%>  >Not Available</option>
			                </select>
			            </div>
			
						<div class=" col-md-2 ">
			                <label>Email<span class=" mandatory ">*</span></label>
			                <input type="email"  name="email" class=" form-control input-sm " maxlength="100"
			                  value="<%if(employee!=null&&employee.getEmail()!=null){%><%=employee.getEmail()%><%}%>"  placeholder="Enter Email " required="required" onclick=" return trim(this) "
			                    onchange=" return trim(this) ">
			            </div>
			            
			
			
			
			        </div>
			    </div>
			   
			    <div class="form-group">
			        <div class="row">
			        
						 <div class=" col-md-2 ">
			                <label>Religion<span class=" mandatory ">*</span></label>
			                <select name="religion" class=" form-control input-sm select2 " data-live-search=" true ">
			                    <option value=" Christian " <%if(employee!=null&&employee.getReligion().equalsIgnoreCase("Christian")){%>selected<%}%>>Christian</option>
			                    <option value=" Hindu " <%if(employee!=null&&employee.getReligion().equalsIgnoreCase("Hindu")){%>selected<%}%> >Hindu</option>
			                    <option value=" Islam " <%if(employee!=null&&employee.getReligion().equalsIgnoreCase("Islam")){%>selected<%}%> >Islam</option>
			                    <option value=" Jain "  <%if(employee!=null&&employee.getReligion().equalsIgnoreCase("Jain")){%>selected<%}%>>Jain </option>
			                    <option value=" Parsi " <%if(employee!=null&&employee.getReligion().equalsIgnoreCase("Parsi")){%>selected<%}%>>Parsi </option>
			                    <option value=" Sikh "  <%if(employee!=null&&employee.getReligion().equalsIgnoreCase("Sikh")){%>selected<%}%>>Sikh</option>
			                    <option value=" Others "<%if(employee!=null&&employee.getReligion().equalsIgnoreCase("Others")){%>selected<%}%>>Others</option>
			
			                </select>
			            </div>
			            
			            <div class="col-md-2">
			                <label>Division <span class="mandatory">*</span></label>
			                <select name="divisionid" class="form-control input-sm select2" required data-live-search="true">
								<%for( DivisionMaster division: divisionlist){ %>
									<option value="<%=division.getDivisionId()%>"<%if(employee!=null&&division.getDivisionId()==employee.getDivisionId()){%>selected<%}%>><%=division.getDivisionName()%></option>
								<%} %>			
			
			                </select>
			            </div>
			
			
			
			            <div class="col-md-2">
			                <label>PAN<span class="mandatory">*</span></label>
			                <input type="text" id="PAN" name="pan" style="text-transform:uppercase" value="<%if(employee!=null){%><%=employee.getPAN()%><%}%>" class="form-control input-sm " maxlength="10" placeholder="Enter PAN">
			            </div>
			
			
			            <div class="col-md-2">
			                <label>UID<span class="mandatory">*</span></label>
			                <input id="UIDTextBox" type="text" name="uid" value="<%if(employee!=null){%><%=employee.getUID()%><%}%>" class="form-control input-sm" maxlength="12" placeholder="Enter UID" required>
			            </div>
			
						<div class=" col-md-2 ">
			                <label>Service Status<span class=" mandatory ">*</span></label>
			                <select name="ServiceStatus" class=" form-control input-sm select2  " required="required"
			                    data-live-search=" true ">		
			                    <option value=" Confirmed " <%if(employee!=null&&employee.getServiceStatus().equalsIgnoreCase("Confirmed")){%>selected<%}%>>Confirmed</option>
			                    <option value=" Probation " <%if(employee!=null&&employee.getServiceStatus().equalsIgnoreCase("Probation")){%>selected<%}%>>Probation</option>
			                    <option value=" Adhoc "     <%if(employee!=null&&employee.getServiceStatus().equalsIgnoreCase("Adhoc")){%>selected<%}%>>Adhoc</option>
			                    <option value=" Temporary " <%if(employee!=null&&employee.getServiceStatus().equalsIgnoreCase("Temporary")){%>selected<%}%>>Temporary</option>
			                    <option value=" Contract "  <%if(employee!=null&&employee.getServiceStatus().equalsIgnoreCase("Contract")){%>selected<%}%>>Contract</option>
			                </select>
			            </div>
			            
						 <div class=" col-md-2 ">
			                <label>PunchCard No<span class=" mandatory ">*</span></label>
			                <input type="text" id="PunchcardTextBox" name="PunchCardNo" id="PunchCard" value="<%if(employee!=null){%><%=employee.getPunchCard()%><%}%>" maxlength="4"
			                    class=" form-control input-sm " placeholder="Enter PunchCard " required="required"
			                    onkeyup=" checkpunchcard() " onblur=" checknegative(this) ">
			            </div>

			
			        </div>
			    </div>
			
			    <div class=" form-group ">
			        <div class=" row ">
			
			           
			
			
			           
			
						<div class=" col-md-2 ">
			                <label>PayLevel<span class=" mandatory ">*</span></label>
			                <select name="payLevel" class=" form-control input-sm select2 " data-live-search=" true ">
								<%for( PisPayLevel paylevel: paylevellist){ %>
									<option value="<%=paylevel.getPayLevelId()%>"  <%if(paylevel.getPayLevelId()==employee.getPayLevelId()){%>selected<%}%>><%=paylevel.getPayLevel()%></option>
								<%} %>
			
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
			                <label>SBI Account<span class=" mandatory ">*</span></label>
			                <input type="text" id="SBITextBox" value="<%if(employee!=null){%><%=employee.getSBIAccNo()%><%}%>" name="SBI" class=" form-control input-sm " required
			                    maxlength=" 11 " placeholder="Enter Account Number " onblur=" checknegative(this) ">
			            </div>
			
			
			
			
			            <div class=" col-md-2 ">
			                <label>Home Town<span class=" mandatory ">*</span></label>
			                <input type="text" id="txtName" name="HomeTown" style=" text-transform:uppercase " value="<%if(employee!=null){%><%=employee.getHomeTown()%><%} %>"
			                    maxlength=" 240 " class=" form-control input-sm " placeholder="Enter Home Town " required="required"
			                    onclick=" Validate() ">
			            </div>
						
						<div class=" col-md-2 ">
			                <label>Height</label>
			                <input type="text" value="<%if(employee!=null){%><%=employee.getHeight()%><%}%>" name="height" class=" form-control input-sm " maxlength="50"
			                    placeholder="Enter Height " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			
			            
			
			        </div>
			    </div>
					    
			    
			    
			    <div class=" form-group ">
			        <div class=" row ">
			        
			        
			            <div class=" col-md-2 ">
			                <label>Internal number<span class="mandatory"></span></label>
			                <input type="text" name="internalNo" value="<%if(employee!=null){%><%=employee.getInternalNumber()%><%}%>" maxlength="4" class=" form-control input-sm "
			                    placeholder="Enter Internal Number " onblur=" checknegative(this) "
			                    onkeypress=" return isNumber(event) ">
			            </div>
			            
						<div class="col-md-2">
			                <label>Availed Govt Quarters</label>
			                
			                <select name="gq" class=" form-control input-sm select2 " >
			                	<option value="N" <%if(employee!=null&&employee.getQuarters().equalsIgnoreCase("N")){%>selected<%}%>>No</option>
								<option value="Y" <%if(employee!=null&&employee.getQuarters().equalsIgnoreCase("Y")){%>selected<%}%>>YES</option>								
			                </select>	
			            </div>
			            
			             <div class=" col-md-2 ">
			                <label>Physically Handicap</label>
			                
			                 <select name="ph" class=" form-control input-sm select2 " >
			                 	<option value="N" <%if(employee!=null&&employee.getPH().equalsIgnoreCase("N")){%>selected<%}%>>No</option>
								<option value="Y" <%if(employee!=null&&employee.getPH().equalsIgnoreCase("Y")){%>selected<%}%>>YES</option>								
			                </select>	

			            </div>
			            
			           
			            
			            
			            <div class=" col-md-2 ">
			                <label>Marital Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			                <select name="MaritalStatus" class=" form-control input-sm select2 " >
			                	<option value="U" <%if(employee!=null&&employee.getMaritalStatus().equalsIgnoreCase("U")){%>selected<%}%>>UnMarried</option>
								<option value="M" <%if(employee!=null&&employee.getMaritalStatus().equalsIgnoreCase("M")){%>selected<%}%>>Married</option>									
			                </select>			                
			            </div>
			            
			            
			            <!-- <div class=" col-md-2 ">
			                <label>PIN DRONA:</label>
			                <input type="text" name="drona" value="" class=" form-control input-sm " maxlength="10" placeholder="Enter DRONA " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div> -->
			
			
			
			            <div class=" col-md-2 ">
			                <label>GPF/PRAN:</label>
			                <input type="text" name="gpf" value="<%if(employee!=null){%><%=employee.getGPFNo()%><%}%>" class=" form-control input-sm " maxlength=" 12 "
			                    placeholder="Enter GPF " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			
			            
			            
					</div>
				</div>
			            
			            
			    <div class=" form-group ">
			        <div class=" row ">
			
			
			            <div class=" col-md-4 ">
			                <label>Identification Mark</label>
			                <input type="text" value="<%if(employee!=null){%><%=employee.getIdMark()%><%}%>" name="idMark" class=" form-control input-sm " maxlength="99"
			                    placeholder="Enter Identification Mark " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			
			            <div class=" col-md-2 ">
			                <label>Emp Status<span class=" mandatory ">*</span></label>
			                <select id="Emptype" name="empstatus"  id="empstatus" class=" form-control input-sm select2 " required data-live-search="true"  >
                   				<%-- <%for( EmpStatus status: empstatuslist){ %>
								<option value="<%=status.getEmp_status_id()%>"><%=status.getEmp_status_name()%></option>
								<%} %> --%>
								
								<option value="1">PRESENT</option>
			
			                </select>
			            </div>
			
			            <div class=" col-md-2 " id=" EmpHide ">
			                <label>Emp Status Date<span class=" mandatory ">*</span></label>
			                <input type="date" name="EmpStatusDate" value="<%=LocalDate.now() %>" class=" form-control input-sm " placeholder="Enter EmpStatus Date ">
			            </div>
			            
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
			        </div>
			    </div>
			       
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    
			    <div class="row" >
			    	<div class="col-12" align="center">
								<input type="hidden" value="<%=employee.getEmpId()%>" name="EmpId">
								<button type="submit" class="btn btn-sm submit-btn" name="action" value="submit" >SUBMIT</button>
			    	</div>
			    </div> 
			
				</form>
			</div>
		</div>
	
	 </div>
</div>
<script type="text/javascript">
/* $('#dob').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
	  */
	 $('empstatus').select2("enable", false)
	 $('#empname').bind('keyup', function() {
		  var c = this.selectionStart,
		      r = /[^a-z]/gi,
		      v = $(this).val();
		  if(r.test(v)) {
		    $(this).val(v.replace(r, ''));
		    c--;
		  }
		  this.setSelectionRange(c, c);
		}); 
	
	
</script>
    

</body>
</html>
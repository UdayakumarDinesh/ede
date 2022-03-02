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
						
			    <div class="form-group">
			        <div class="row">
			
			            <div class="col-md-2">
			                <label>Rank/Salutation<span class="mandatory">*</span></label><br>
			                 <select class=" form-control select2" name="salutation" required="required">
								<option value="Mr.">Mr.</option>
								<option value="Mrs.">Mrs.</option>
								<option value="Miss.">Miss.</option>
								<option value="Dr.">Dr.</option>
							</select>
			            </div>
			
			
			            <div class="col-md-4">
			                <label>Name<span class="mandatory">*</span></label>
			                <input name="empname" required="required" id="NameTextBox" style="text-transform:uppercase" value="" maxlength="75" placeholder="Enter Employee name" class="form-control input-sm" onclick="return trim(this)" onchange="return trim(this)" />
			            </div>
			
			
			            <div class="col-md-2">
			
			                <label>Designation<span class="mandatory">*</span></label>
			                <select class="form-control select2 " name="Designationid" required="required" data-live-search="true" >
								<%if(desiglist!=null && desiglist.size()>0){
					                for(EmployeeDesig desig : desiglist){ %>
				                    <option value="<%=desig.getDesigId() %>" ><%= desig.getDesignation()%></option>
					                <%}
				                } %>  
							</select>
			            </div>
			
			            <div class="col-md-2">
			                <label class="text-nowrap  ">Cadre Name<span class="mandatory">*</span></label>
			                <select name="cadrecode" id="CadreId" class="form-control select2">
								<%for( PisCadre cadre: piscaderlist){ %>
									<option value="<%=cadre.getCadreId()%>"><%=cadre.getCadre()%></option>
								<%} %>
			                </select>
			            </div>
			
			
			            <div class="col-md-2">
			                <label>CAT CLASS<span class="mandatory">*</span></label>
			                <select name="catcode" class="form-control select2" required data-live-search="true">
								<%for( PisCatClass catclass: piscatclasslist){ %>
									<option value="<%=catclass.getCat_class_id()%>"><%=catclass.getCat_name()%></option>
								<%} %>
			
			                </select>
			            </div>
			
			
			        </div>
			    </div>
			
			
			    <div class="form-group">
			        <div class="row">
			
			            <div class="col-md-2">
			                <label>DOB<span class="mandatory">*</span></label>
			                
			                <input type="text" class="form-control" value="<%=sdf1.format(sdf.parse(LocalDate.now().toString())) %>" data-date-format="dd/mm/yyyy"  id="dob" name="dob"  required="required" readonly="readonly" >
			            </div>
			
			
			            <div class="col-md-2">
			                <label>DOA<span class="mandatory">*</span></label>
			                <input type="text" value="" class="form-control input-sm currentdate" placeholder="Date of appointment" name="doa" required maxlength="10">
			            </div>
			
			
			            <div class="col-md-2">
			                <label>DOJ<span class="mandatory">*</span></label>
			                <input type="text" value="" class="form-control input-sm currentdate" placeholder="Date of Joining" name="doj" required maxlength="10">
			            </div>
			
			
			            <div class="col-md-2">
			                <label>Gender<span class="mandatory">*</span></label>
			                <select name="gender" class="form-control input-sm" required>
			                    <option value="M">Male</option>
			                    <option value="F">Female</option>
			                </select>
			            </div>
			
			
			            <div class="col-md-2">
			                <label class="text-nowrap">Blood Group<span class="mandatory">*</span></label>
			                <select name="bloodgroup" class="form-control input-sm select2" required data-live-search="true">
			
			                    <option value="A-">A-</option>
			                    <option value="A+">A+</option>
			                    <option value="B-">B-</option>
			                    <option value="B+">B+</option>
			                    <option value="AB-">AB-</option>
			                    <option value="AB+">AB+</option>
			                    <option value="O-">O-</option>
			                    <option value="O+">O+</option>
			                    <option value="NOT">Not Available</option>
			                </select>
			            </div>
			
			
			            <div class="col-md-2">
			                <label>Availed Govt Quarters</label>
			
			            </div>
			
			
			
			        </div>
			    </div>
			    <!-- //DOB,DOA,DOJ,GENDER,BG,CATEGORY,QUATERS  -->
			
			
			    <!-- ,CADRE,PAN,UID,PIN, -->
			    <div class="form-group">
			        <div class="row">
			
			            <div class="col-md-2">
			                <label>Division <span class="mandatory">*</span></label>
			                <select name="DivisionId" class="form-control input-sm select2" required data-live-search="true">
								<%for( DivisionMaster division: divisionlist){ %>
									<option value="<%=division.getDivisionId()%>"><%=division.getDivisionName()%></option>
								<%} %>			
			
			                </select>
			            </div>
			
			
			
			            <div class="col-md-2">
			                <label>PAN<span class="mandatory">*</span></label>
			
			
			                <input type="text" id="PAN" name="pan" style="text-transform:uppercase" value="" class="form-control input-sm " maxlength="10" placeholder="Enter PAN">
			
			
			            </div>
			
			
			            <div class="col-md-2">
			                <label>UID<span class="mandatory">*</span></label>
			                <input id="UIDTextBox" type="text" name="uid" value="" class="form-control input-sm" maxlength="12" " placeholder=" Enter UID " required>
			
			            </div>
			
			
			            <div class=" col-md-2 ">
			                <label>PIN DRONA:</label>
			                <input type=" text " name="drona" value=" " class=" form-control input-sm " maxlength=" 10 "
			                    placeholder=" Enter DRONA " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			
			
			
			            <div class=" col-md-2 ">
			                <label>GPF/PRAN:</label>
			                <input type=" text " name="gpf" value=" " class=" form-control input-sm " maxlength=" 12 "
			                    placeholder=" Enter GPF " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			
			
			
			            <div class=" col-md-2 ">
			                <label>Physically Handicap</label>
			
			
			
			            </div>
			
			
			        </div>
			    </div>
			
			
			
			    <div class=" form-group ">
			        <div class=" row ">
			
			            <div class=" col-md-2 ">
			                <label>Service Status<span class=" mandatory ">*</span></label>
			                <select name="ServiceStatus" class=" form-control input-sm select2  " required=" required "
			                    data-live-search=" true ">
			
			                    <option value=" Confirmed ">Confirmed</option>
			                    <option value=" Probation ">Probation</option>
			                    <option value=" Adhoc ">Adhoc</option>
			                    <option value=" Temporary ">Temporary</option>
			                    <option value=" Contract ">Contract</option>
			                </select>
			            </div>
			
			
			            <div class=" col-md-2 ">
			                <label>Internal number<span class=" mandatory "></span></label>
			                <input type=" text " name="internalNo" value=" " maxlength=" 4 " class=" form-control input-sm "
			                    placeholder=" Enter Internal Number " onblur=" checknegative(this) "
			                    onkeypress=" return isNumber(event) ">
			            </div>
			
			
			            <div class=" col-md-2 ">
			                <label>Category<span class=" mandatory ">*</span></label>
			                <select name="category" class=" form-control input-sm select2 " required data-live-search=" true ">
								<%for( PisCategory category: piscategorylist){ %>
									<option value="<%=category.getCategory_id()%>"><%=category.getCategory_desc()%></option>
								<%} %>				
			                </select>
			            </div>
			
			
			            <div class=" col-md-2 ">
			                <label> Sub Category/Caste <span class=" mandatory "></span></label>
			                <input type="text" id="subcategory1 " name="subcategory" value=" " maxlength=" 20 "
			                    class=" form-control input-sm " placeholder=" Enter Sub Category ">
			
			            </div>
			
			
			
			
			
			            <div class=" col-md-2 ">
			                <label>Home Town<span class=" mandatory ">*</span></label>
			                <input type=" text " id=" txtName " name="HomeTown" style=" text-transform:uppercase " value=" "
			                    maxlength=" 240 " class=" form-control input-sm " placeholder=" Enter Home Town " required=" required "
			                    onclick=" Validate() ">
			            </div>
			
			
			            <div class=" col-md-2 ">
			                <label>Marital Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			
			            </div>
			
			
			
			
			
			        </div>
			    </div>
			
			    <div class=" form-group ">
			        <div class=" row ">
			
			            <div class=" col-md-2 ">
			                <label>PayLevel<span class=" mandatory ">*</span></label>
			                <select name="payLevel" class=" form-control input-sm selectpicker " data-live-search=" true ">
			
			
			                </select>
			            </div>
			
			
			            <div class=" col-md-2 ">
			                <label>SBI Account<span class=" mandatory ">*</span></label>
			                <input type="text" id="SBITextBox" value="" name="SBI" class=" form-control input-sm " required
			                    maxlength=" 11 " placeholder=" Enter Account Number " onblur=" checknegative(this) ">
			            </div>
			
			
			
			            <div class=" col-md-2 ">
			                <label>Religion<span class=" mandatory ">*</span></label>
			                <select name="religion" class=" form-control input-sm select2 " data-live-search=" true ">
			                    <option value=" Christian ">Christian</option>
			                    <option value=" Hindu ">Hindu</option>
			                    <option value=" Islam ">Islam</option>
			                    <option value=" Jain ">Jain</option>
			                    <option value=" Parsi ">Parsi </option>
			                    <option value=" Sikh ">Sikh</option>
			                    <option value=" Others ">Others</option>
			
			                </select>
			            </div>
			
			
			            <div class=" col-md-2 ">
			                <label>Height</label>
			                <input type="text" value=" " name="height" class=" form-control input-sm " maxlength=" 50 "
			                    placeholder=" Enter Height " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			
			
			
			            <div class=" col-md-2 ">
			                <label>Email<span class=" mandatory ">*</span></label>
			                <input type="email" value="" name="email" class=" form-control input-sm " maxlength="100"
			                    placeholder=" Enter Email " required=" required " onclick=" return trim(this) "
			                    onchange=" return trim(this) ">
			            </div>
			
			            <div class=" col-md-2 ">
			                <label>Ex ServiceMan   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			            </div>
			
			
			
			        </div>
			    </div>
			
			
			
			    <div class=" form-group ">
			        <div class=" row ">
			
			
			            <div class=" col-md-2 ">
			                <label>PunchCard No<span class=" mandatory ">*</span></label>
			                <input type="text" id="PunchcardTextBox" name="PunchCardNo " id="PunchCard " value="" maxlength="4"
			                    class=" form-control input-sm " placeholder=" Enter PunchCard " required="required"
			                    onkeyup=" checkpunchcard() " onblur=" checknegative(this) ">
			            </div>
			            <span id=" awailable "></span>
			
			
			
			            <div class=" col-md-4 ">
			                <label>Identification Mark</label>
			                <input type="text" value="" name="idMark" class=" form-control input-sm " maxlength="99"
			                    placeholder=" Enter Identification Mark " onclick=" return trim(this) " onchange=" return trim(this) ">
			            </div>
			
			            <div class=" col-md-2 ">
			                <label>Emp Status<span class=" mandatory ">*</span></label>
			                <select id="Emptype" name="empstatus" class=" form-control input-sm select2 " required data-live-search="true">
                   					<%for( EmpStatus status: empstatuslist){ %>
									<option value="<%=status.getEmp_status_id()%>"><%=status.getEmp_status_name()%></option>
								<%} %>
			
			                </select>
			            </div>
			
			            <div class=" col-md-2 " id=" EmpHide ">
			                <label>Emp Status Date<span class=" mandatory ">*</span></label>
			                <input type="text" name="EmpStatusDate" value="" maxlength="4" class=" form-control input-sm currentdate "
			                    placeholder=" Enter EmpStatus Date " onblur=" checknegative(this) "
			                    onkeypress=" return isNumber(event) ">
			            </div>
			
			            <div class=" col-md-2 ">
			                <label>Per Pass No</label>
			                <input type="text" name="PermPassNo" value="" class=" form-control input-sm " maxlength="10"
			                    placeholder="Enter Permanent Pass No">
			            </div>
			
			
			        </div>
			    </div>
			</div>
					
		</div>
	
	 </div>
</div>
<script type="text/javascript">
	function trim(el) {
		console.log(el);
		el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
		replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
		replace(/\n +/, "\n"); // Removes spaces after newlines
		return;
	}
	/* 
	  $( function() {
		    $( "#dob" ).datepicker({
		    	 dateFormat: 'dd-mm-yy',
		    	
		    });
		  } ); */
</script>
    




</body>
</html>
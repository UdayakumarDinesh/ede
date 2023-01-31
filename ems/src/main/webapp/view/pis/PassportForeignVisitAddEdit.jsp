<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.vts.ems.pis.model.*"%>
    <%@page import="java.util.List"%>
    <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Foreign Visit AddEdit</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<script type="text/javascript">
 function trim (el) {
	    el.value = el.value.
	       replace (/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
	       replace (/[ ]{2,}/gi," ").       // replaces multiple spaces with one space 
	       replace (/\n +/,"\n");           // Removes spaces after newlines
	    return;
	}
 </script>
 <style type="text/css">

 </style>
</head>
<body>
<%
List<Object[]> States = (List<Object[]>)request.getAttribute("States");
Object[] empdata = (Object[])request.getAttribute("Empdata");
PassportForeignVisit foreignvisit = (PassportForeignVisit)request.getAttribute("foreignvisit"); 

%>

<div class="card-header page-top">
					<div class="row">
						<div class="col-md-6">
						<%if(foreignvisit!=null){ %>
							<h5> Passport Foreign Visit Edit<small><b>&nbsp;&nbsp; <%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%> 
									</b></small></h5><%}else{ %>
							<h5>Passport Foreign Visit Add<small><b>&nbsp;&nbsp; <%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%> 
									</b></small></h5><%}%>
						</div>
						   <div class="col-md-6">
								<ol class="breadcrumb ">
									<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
									<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
									<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
									<li class="breadcrumb-item  " aria-current="page"><a href="PassportList.htm?empid=<%if(empdata!=null){%><%=empdata[2]%><%}%>">Passport List</a></li>
									<li class="breadcrumb-item active " aria-current="page">Passport Foreign Visit Address </li>
								</ol>
							</div>
					</div>
				</div>

<div class=" page card dashboard-card"> 

				<div class="card-body" align="center">
		
		<div class="row">
		<div class="col-1"></div>
		<%if(foreignvisit!=null){ %>
				<form action="ForeignVisitEdit.htm" method="POST" autocomplete="off" enctype="multipart/form-data" >
		<%}else{ %>
				<form action="ForeignVisitAdd.htm" method="POST" autocomplete="off">
		<%}%>
		 <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%>  <%}%>"> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card"  style="width: 80%;" > 
		<div class="card-header" align="left">
		<h5>Fill Passport Foreign Visit Details</h5>
		</div>
			<div class="card-body" align="left">
			 
          <div class="row">   
                     <div class="col-md-3">
		                <div class="form-group">
		                	<label>Country</label>
		                   <select  name="state" class="form-control input-sm selectpicker" data-live-search="true">

								<%-- <%if(States!=null){ 
								for (Object[] O : States) {%>
								<%if(foreignvisit!=null){ %>
								<option value="<%=O[1]%>"   <%if(foreignvisit.getCountryName().equalsIgnoreCase(O[1].toString())){%> selected<%}%>><%=O[1]%></option>
								<%}else{%>
								<option value="<%=O[1]%>"   ><%=O[1]%></option>
								<%}}}%> --%>
							<%if(foreignvisit!=null){ %>
							<option value="<%=foreignvisit.getCountryName() %>" ><%=foreignvisit.getCountryName() %></option>
							<%} %>										                   
		                   <option value="Afghanistan" >Afghanistan</option>
		                   <option value="Albania" >Albania</option>
		                   <option value="Algeria" >Algeria</option>
		                   <option value="Andorra">Andorra</option>
		                   <option value="Angola">Angola</option>
		                   <option value="Antigua & Barbuda">Antigua & Barbuda</option>
                           <option value="Anguilla">Anguilla</option>
                           <option value="Argentina">Argentina</option>
                           <option value="Armenia">Armenia</option>
                           <option value="Australia">Australia</option>
                           <option value="Austria">Austria</option>
                           <option value="Azerbaijan">Azerbaijan</option>
                           <option value="Bahamas">Bahamas</option>
                           <option value="Bahrain">Bahrain</option>
                           <option value="Bangladesh">Bangladesh</option>
                           <option value="Barbados">Barbados</option>
                           <option value="Belarus">Belarus</option>
                           <option value="Belgium">Belgium</option>
                           <option value="Belize">Belize</option>
                           <option value="Benin">Benin</option>
                           <option value="Bermuda">Bermuda</option>
                           <option value="Bhutan">Bhutan</option>
                           <option value="Bolivia">Bolivia</option>
                          <option value="Bosnia & Herzegovina">Bosnia & Herzegovina</option>
                          <option value="Botswana">Botswana</option>
                          <option value="Brazil">Brazil</option>
                          <option value="Brunei Darussalam">Brunei Darussalam</option>
                          <option value="Bulgaria">Bulgaria</option>
                          <option value="Burkina Faso">Burkina Faso</option>
                          <option value="Burundi">Burundi</option>
                          <option value="Cambodia">Cambodia</option>
                          <option value="Cameroon">Cameroon</option>
                          <option value="Canada">Canada</option>
                          <option value="Cape Verde">Cape Verde</option>
                          <option value="Cayman Islands">Cayman Islands</option>
                          <option value="Central African Republic">Central African Republic</option>
                          <option value="Chad">Chad</option>
                          <option value="Chile">Chile</option>
                          <option value="China">China</option>
                          <option value="China - Hong Kong / Macau">China - Hong Kong / Macau</option>
                          <option value="Colombia">Colombia</option>
                          <option value="Comoros">Comoros</option>
                          <option value="Congo">Congo</option>
                          <option value="Costa Rica">Costa Rica</option>
<option value="Croatia">Croatia</option>
<option value="Cuba">Cuba</option>
<option value="Cyprus">Cyprus</option>
<option value="Czech Republic">Czech Republic</option>
<option value="Denmark">Denmark</option>
<option value="Djibouti">Djibouti</option>
<option value="Dominica">Dominica</option>
<option value="Dominican Republic">Dominican Republic</option>
<option value="Ecuador">Ecuador</option>
<option value="Egypt">Egypt</option>
<option value="El Salvador">El Salvador</option>
<option value="Equatorial Guinea">Equatorial Guinea</option>
<option value="Eritrea">Eritrea</option>
<option value="Estonia">Estonia</option>
<option value="Ethiopia">Ethiopia</option>
<option value="Fiji">Fiji</option>
<option value="Finland">Finland</option>
<option value="France">France</option>
<option value="French Guiana">French Guiana</option>
<option value="Gabon">Gabon</option>
<option value="Gambia">Gambia</option>
<option value="Georgia">Georgia</option>
<option value="Germany">Germany</option>
<option value="Ghana">Ghana</option>
<option value="Great Britain">Great Britain</option>
<option value="Greece">Greece</option>
<option value="Grenada">Grenada</option>
<option value="Guadeloupe">Guadeloupe</option>
<option value="Guatemala">Guatemala</option>
<option value="Guinea">Guinea</option>
<option value="Guinea-Bissau">Guinea-Bissau</option>
<option value="Guyana">Guyana</option>
<option value="Haiti">Haiti</option>
<option value="Honduras">Honduras</option>
<option value="Hungary">Hungary</option>
<option value="Iceland">Iceland</option>
<option value="India">India</option>
<option value="Indonesia">Indonesia</option>
<option value="Iran">Iran</option>
<option value="Iraq">Iraq</option>
<option value="Israel">Israel</option>
<option value="Italy">Italy</option>
<option value="Ivory Coast (Cote d'Ivoire)">Ivory Coast (Cote d'Ivoire)</option>
<option value="Jamaica">Jamaica</option>
<option value="Japan">Japan</option>
<option value="Jordan">Jordan</option>
<option value="Kazakhstan">Kazakhstan</option>
<option value="Kenya">Kenya</option>
<option value="Korea, Democratic Republic of (North Korea)">Korea, Democratic Republic of (North Korea)</option>
<option value="Korea, Republic of (South Korea)">Korea, Republic of (South Korea)</option>
<option value="Kosovo">Kosovo</option>
<option value="Kuwait">Kuwait</option>
<option value="Kyrgyz Republic (Kyrgyzstan)">Kyrgyz Republic (Kyrgyzstan)</option>
<option value="Laos">Laos</option>
<option value="Latvia">Latvia</option>
<option value="Lebanon">Lebanon</option>
<option value="Lesotho">Lesotho</option>
<option value="Liberia">Liberia</option>
<option value="Libya">Libya</option>
<option value="Liechtenstein">Liechtenstein</option>
<option value="Lithuania">Lithuania</option>
<option value="Luxembourg">Luxembourg</option>
<option value="Macedonia">Macedonia</option>
<option value="Madagascar">Madagascar</option>
<option value="Malawi">Malawi</option>
<option value="Malaysia">Malaysia</option>
<option value="Maldives">Maldives</option>
<option value="Mali">Mali</option>
<option value="Malta">Malta</option>
<option value="Martinique">Martinique</option>
<option value="Mauritania">Mauritania</option>
<option value="Mauritius">Mauritius</option>
<option value="Mayotte">Mayotte</option>
<option value="Mexico">Mexico</option>
<option value="Moldova">Moldova</option>
<option value="Monaco">Monaco</option>
<option value="Mongolia">Mongolia</option>
<option value="Montenegro">Montenegro</option>
<option value="Montserrat">Montserrat</option>
<option value="Morocco">Morocco</option>
<option value="Mozambique">Mozambique</option>
<option value="Myanmar/Burma">Myanmar/Burma</option>
<option value="Namibia">Namibia</option>
<option value="Nepal">Nepal</option>
<option value="New Zealand">New Zealand</option>
<option value="Nicaragua">Nicaragua</option>
<option value="Niger">Niger</option>
<option value="Nigeria">Nigeria</option>
<option value="Norway">Norway</option>
<option value="Oman">Oman</option>
<option value="Pacific Islands">Pacific Islands</option>
<option value="Pakistan">Pakistan</option>
<option value="Panama">Panama</option>
<option value="Papua New Guinea">Papua New Guinea</option>
<option value="Paraguay">Paraguay</option>
<option value="Peru">Peru</option>
<option value="Philippines">Philippines</option>
<option value="Poland">Poland</option>
<option value="Portugal">Portugal</option>
<option value="Puerto Rico">Puerto Rico</option>
<option value="Qatar">Qatar</option>
<option value="Reunion">Reunion</option>
<option value="Romania">Romania</option>
<option value="Russia">Russia</option>
<option value="Rwanda">Rwanda</option>
<option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
<option value="Saint Lucia">Saint Luci</option>
<option value="Saint Vincent and the Grenadines">Saint Vincent and the Grenadines</option>
<option value="Samoa">Samoa</option>
<option value="Sao Tome and Principe">Sao Tome and Principe</option>
<option value="Saudi Arabia">Saudi Arabia</option>
<option value="Senegal">Senegal</option>
<option value="Serbia">Serbia</option>
<option value="Seychelles">Seychelles</option>
<option value="Sierra Leone">Sierra Leone</option>
<option value="Singapore">Singapore</option>
<option value="lovak Republic (Slovakia)">lovak Republic (Slovakia)</option>
<option value="Slovenia">Slovenia</option>
<option value="Solomon Islands">Solomon Islands</option>
<option value="Somalia">Somalia</option>
<option value="South Africa">South Africa</option>
<option value="South Sudan">South Sudan</option>
<option value="Spain">Spain</option>
<option value="Sri Lanka">Sri Lanka</option>
<option value="Sudan">Sudan</option>
<option value="Suriname">Suriname</option>
<option value="Swaziland">Swaziland</option>
<option value="Sweden">Sweden</option>
<option value="Switzerland">Switzerland</option>
<option value="Syria">Syria</option>
<option value="Tajikistan">Tajikistan</option>
<option value="Tanzania">Tanzania</option>
<option value="Thailand">Thailand</option>
<option value="Netherlands">Netherlands</option>
<option value="Timor Leste">Timor Leste</option>
<option value="Togo">Togo</option>
<option value="Trinidad & Tobago">Trinidad & Tobago</option>
<option value="Tunisia">Tunisia</option>
<option value="Turkey">Turkey</option>
<option value="Turkmenistan">Turkmenistan</option>
<option value="Turks & Caicos Islands">Turks & Caicos Islands</option>
<option value="Uganda">Uganda</option>
<option value="Ukraine">Ukraine</option>
<option value="United Arab Emirates">United Arab Emirates</option>
<option value="United Kingdom(UK)">United Kingdom(UK)</option>
<option value="United States of America (USA)">United States of America (USA)</option>
<option value="Uruguay">Uruguay</option>
<option value="Uzbekistan">Uzbekistan</option>
<option value="Venezuela">Venezuela</option>
<option value="Vietnam">Vietnam</option>
<option value="Virgin Islands (UK)">Virgin Islands (UK)</option>
<option value="Virgin Islands (US)">Virgin Islands (US)</option>
<option value="Yemen">Yemen</option>
<option value="Zambia">Zambia</option>
<option value="Zimbabwe">Zimbabwe</option>
							</select>
		                </div>
		                </div>
         
                   <div class="col-md-3">
                        <div class="form-group">
                          <label>Visit From:</label>
                          <input type="text" class="form-control input-sm valifrom" value="" name="VisitFrom" id="visitfrom" readonly="readonly" required="required"  /> 
                       </div> 
                    </div>	 
                               
		            <div class="col-md-3">
	                       <div class="form-group">
	                             <label>Valid To </label>
	                       	     <input type="text" class="form-control input-sm " value="" name="VisitTo" id="visitto" readonly="readonly" required="required"  />
	                       </div>
                       </div> 
                        <div class="col-md-3">
	                    <div class="form-group">
	                            <label>NOC No:<span class="mandatory">*</span></label>
	                            <input id="nocno" type="text" maxlength="25" class="form-control input-sm " <%if(foreignvisit!=null && foreignvisit.getNocLetterNo()!=null){%> value="<%=foreignvisit.getNocLetterNo()%>" <%}%> name="NOCNo"  required="required"   placeholder="Enter NOC No" onblur="checknegative(this)">
	                    </div>
                    </div>
                      
               </div>
               
               <div class="row">
               			<div class="col-md-3">
	                       <div class="form-group">
	                             <label>NOC Date </label>
	                       	     <input type="text" class="form-control input-sm " value="" name="NOCDate" id="nocdate" readonly="readonly" required="required"  />
	                       </div>
                       </div> 
                       <div class="col-md-3">
	                       <div class="form-group">
	                             <label>Purpose </label>
	                       	     <input type="text" class="form-control input-sm " maxlength="255" <%if(foreignvisit!=null && foreignvisit.getPurpose()!=null){%> value="<%=foreignvisit.getPurpose()%>" <%}%> name="Purpose" id="purpose"  required="required"  placeholder="Enter Purpose"/>
	                       </div>
                       </div> 
                       <div class="col-md-3">
	                       <div class="form-group">
	                             <label>NOC Issued From </label>
	                       	     <input type="text" class="form-control input-sm " maxlength="255" <%if(foreignvisit!=null && foreignvisit.getNocIssuedFrom()!=null){%> value="<%=foreignvisit.getNocIssuedFrom()%>" <%}%> name="NOCIssuedFrom" id="nocissuedfrom" placeholder="Enter NOC Issued From"  required="required"  />
	                       </div>
                       </div> 
                       <div class="col-md-3">
	                       <div class="form-group">
	                             <label>Remark</label>
	                       	     <input type="text" class="form-control input-sm "  maxlength="255" <%if(foreignvisit!=null && foreignvisit.getRemarks()!=null){%> value="<%=foreignvisit.getRemarks()%>" <%}%> name="Remark" id="remark"  placeholder="Enter Remark" required="required"  />
	                       </div>
                       </div> 
               </div>

		
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<%if(foreignvisit!=null){ %>
							<input type="hidden"  name="foreignvisitid" value="<%=foreignvisit.getPassportVisitId()%>">
				              <button type="submit" class="btn btn-sm submit-btn AddItem"	 name="action" value="submit" onclick="return CommentsModel();" >SUBMIT</button>
								<%}else{%>
			                   <button type="submit" class="btn btn-sm submit-btn"	onclick="return confirm('Are You Sure To Submit?');" name="Action" value="ADD">SUBMIT</button>
								<%}%>
		                     <%if(empdata!=null){%>  <a href="PassportList.htm?empid=<%=empdata[2]%>"   class="btn btn-sm  btn-info">BACK</a><%}%>
							 </div>
							</div>
						   </div> 
					      </div>							
						 </div>	
						 <%if(foreignvisit!=null){ %>
<!--------------------------- container ------------------------->
			<div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">The Reason For Edit</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					         <div class="modal-body">
					             <div class="form-inline">
					        	 <div class="form-group "  >
					               <label>File : &nbsp;&nbsp;&nbsp;</label> 
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile"  > 
					      		 </div>
					      		 </div>
					        	
					        	<div class="form-inline">
					        	<div class="form-group w-100">
					               <label>Comments : &nbsp;&nbsp;&nbsp;</label> 
					              <textarea  class=" form-control w-100" maxlength="1000" style="text-transform:capitalize;"  id="comments"  name="comments" required="required" ></textarea> 
					      		</div>
					      		</div>
					        </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Submit?');" name="Action" value="EDIT">SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
					<%}%>
					<%if(foreignvisit!=null){ %> </form>
					 <%}else{%></form><%}%>
				         </div>
				        </div>	
		               </div>
</body>

<script type="text/javascript">
$('#visitfrom').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	<%if(foreignvisit!=null && foreignvisit.getVisitFromDate()!=null){ %>
	"startDate" : new Date("<%=foreignvisit.getVisitFromDate()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#visitto').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#visitfrom').val(),    
	<%if(foreignvisit!=null && foreignvisit.getVisitToDate()!=null){ %>
	"startDate" : new Date("<%=foreignvisit.getVisitToDate()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$("#visitfrom").change( function(){
	var validdate = $("#visitfrom").val();
	
	$('#visitto').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : validdate,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	
	
});						


$('#nocdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,  
	<%if(foreignvisit!=null && foreignvisit.getNocLetterDate()!=null){ %>
	"startDate" : new Date("<%=foreignvisit.getNocLetterDate()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
</script>
<script type="text/javascript">

setPatternFilter($("#nocno"), /^-?\d*$/);


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


function CommentsModel()
{
	var nocno = $("#nocno").val();
	var Purpose = $("#purpose").val();
	var NOCIssuedFrom = $("#nocissuedfrom").val();
	var Remark = $("#remark").val();
	
	if(nocno==null || nocno=='' || nocno=="null" ){
		alert('Enter the NOC No!');
		return false;
	}else if(Purpose==null || Purpose=='' || Purpose=="null" ){
		alert('Enter the Purpose!');
		return false;
	}else if(NOCIssuedFrom==null || NOCIssuedFrom=='' || NOCIssuedFrom=="null" ){
		alert('Enter the NOC Issued From!');
		return false;
	}else if(Remark==null || Remark=='' || Remark=="null" ){
		alert('Enter the Remark!');
		return false;
	}else {
		$('#myModal').modal('show');
	}
		 
}
</script> 
</html>
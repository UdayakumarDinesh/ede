<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.time.LocalDate"%>
    <%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Publication Add</title>
</head>
<body>
<%
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> pisstatelist  = (List<Object[]>)request.getAttribute("PisStateList");

%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Publication Add<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PublicationList.htm?empid=<%=empdata[2]%>">Publication List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Publication Add</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="AddPublication.htm" method="POST" autocomplete="off" id="form1">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" > 
		<div class="card-header">
		<h5>Fill Publication Details</h5>
		</div>
			<div class="card-body">
			 
              <div class="row">
				      <div class="col-md-3">
		                <div class="form-group">
		                    <label>Publication Type<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="PublicationType" id="publicationtype" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                        <option value="JOURNAL">JOURNAL</option>
		                        <option value="BOOK">BOOK</option>
		                        <option value="PAPER">PAPER</option>
		                        <option value="POSTER">POSTER</option>
		                        <option value="ARTICLE">ARTICLE</option>
		                        <option value="PATENT">PATENT</option>
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-3">
		                 <div class="form-group">
                            <label>Author<span class="mandatory">*</span></label>
                            <input  id="author"  type="text" name="Author"  class="form-control input-sm" required="required"  placeholder="Enter Author" > 
                        </div>
		                
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                    <label>Discipline<span class="mandatory">*</span></label>
		                    <input  id="discipline" maxlength="25" type="text" name="Discipline"  class="form-control input-sm" required="required"  placeholder="Enter Discipline" > 
		                </div>
		               </div> 
		               
		                 <div class="col-md-3">
		                 <div class="form-group">
                            <label>Title<span class="mandatory">*</span></label>
		                    <input  id="title"  type="text" maxlength="100" name="Title"  class="form-control input-sm" required="required"  placeholder="Enter Title" > 
		                </div>
                        </div>
		              </div>

                <div class="row">
                     <div class="col-md-3">
		                <div class="form-group">
		                	<label>Publication Name<span class="mandatory">*</span></label>
		                    <input  id="publication"  type="text" name="Publication" class="form-control input-sm" required="required"  placeholder="Enter Publication Name" > 
		                </div>
		              </div>
		              
		                <div class="col-md-3">
		                <div class="form-group">
		                	<label>Publication Date<span class="mandatory">*</span></label>
		                    <input type="text" class="form-control input-sm mydate" readonly="readonly"   id="publicationdate" name="PublicationDate"  required="required">                   
		                </div>
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                    <label>Patent No<span class="mandatory">*</span></label>
		                      <input  id="patentno"  type="text" name="PatentNo" class="form-control input-sm" required="required"  placeholder="Enter Patent No" > 
		                </div>
		               </div>
		               
		               <div class="col-md-3">
		                <div class="form-group">
		                    <label>Country<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Country" id="country" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                      <%-- <%if(pisstatelist!=null && pisstatelist.size()>0){for(Object[] obj : pisstatelist){%>
		                        <option value="<%=obj[0]%>"><%=obj[1]%></option>
		                       <%}}%> --%>
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
                  
                </div>

						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<input type="hidden" name="empid" <%if(empdata!=null){%> value="<%=empdata[2]%>" <%}%>> 
								<button type="button" class="btn btn-sm submit-btn"
									onclick="AddPublication();"
									name="action" value="submit">SUBMIT</button>
							  </div>
							</div>
						</div>
						</div></div>
			</form>
			</div>
		</div>
		</div>
		</div>
<script type="text/javascript">
$('#publicationdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
</script>
<script type="text/javascript">

$('#author').keypress(function (e) {
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


	setPatternFilter($("#patentno"), /^-?\d*$/);

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


function AddPublication()
{
	
	  var PublicationType =$("#publicationtype").val();
	  var Author =$("#author").val();
	  var Discipline= $("#discipline").val();
	  var Title = $("#title").val();
	  var Publication = $("#publication").val();
	  var Patentno = $("#patentno").val();
	  var Country = $("#country").val();

	  
	  if(PublicationType==null || PublicationType=='' || PublicationType=="null" ){
			alert('Select the Publication Type!');
			return false;
		}else if(Author==null || Author=='' || Author=="null"||Author.trim()==="" ){
			alert('Enter the Author Name!');
			return false;
		}else if(Discipline==null || Discipline=='' || Discipline=="null"||Discipline.trim()==="" ){
			alert('Enter the Discipline!');
			return false;
		}else if(Title==null || Title=='' || Title=="null"||Title.trim()==="" ){
			alert('Enter the Title!');
			return false;
		}else if(Publication==null || Publication=='' || Publication=="null"||Publication.trim()==="" ){
			alert('Enter the Publication Name!');
			return false;
		}else if(Patentno==null || Patentno=='' || Patentno=="null" ){
			alert('Enter the Patent Number!');
			return false;
		}else if(Country==null || Country=='' || Country=="null" ){
			alert('Slect the Country!');
			return false;
		}else{
			
			if(confirm("are you sure to submit?")){
				$("#form1").submit();
			}else{
				
			}
		}

}
</script>
</body>
</html>
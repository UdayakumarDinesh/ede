<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List,com.vts.ems.property.model.PisImmovableProperty"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 
</head>
<body>
<%
PisImmovableProperty imm = (PisImmovableProperty)request.getAttribute("ImmProperty");
List<Object[]> States = (List<Object[]>)request.getAttribute("States");
%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<%if(imm!=null){ %>
				<h5 style="width:115%;">Immovable Property Edit</h5><%}else{ %>
				<h5 style="width:115%;">Immovable Property Add</h5><%}%>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="PropertyDashBoard.htm">Property</a></li>
					<li class="breadcrumb-item active " aria-current="page">Immovable Property</li>
				</ol>
			</div>	
		</div>
</div>	
<div class="page card dashboard-card"> 
  <div class="card-body">
	<div class="row">
	  <div class="col-1"></div>
		<%if(imm!=null){ %>
		<form action="ImmovablePropEdit.htm" method="POST" autocomplete="off" id="myform2">
		<%}else{%>
		<form action="ImmovablePropAdd.htm" method="POST" autocomplete="off" id="myform1">
		<%}%>
		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		   <div class="card"> 
			 <div class="card-body"  >			 
                 <div class="row">
				   <div class="col-md-2">
				      <label>Purpose:<span class="mandatory">*</span></label> 
				       <select name="purpose" class="form-control input-sm select2" required>
			               <option value="I" <%if(imm!=null && imm.getPurpose()!=null && "I".equalsIgnoreCase(imm.getPurpose()) ){ %> selected="selected" <%} %> >Intimation of transaction</option>
			               <option value="P" <%if(imm!=null && imm.getPurpose()!=null && "P".equalsIgnoreCase(imm.getPurpose()) ){ %> selected="selected" <%} %>>Permission of transaction</option>
			           </select>
				   </div>     
				   <div class="col-md-2">
				       <label>Transaction State:<span class="mandatory">*</span></label>
				       <select name="transState" id="transState" class="form-control input-sm select2"  required>
			               <option value="A" <%if(imm!=null && imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%> selected="selected" <%} %> >Acquired</option>
			               <option value="D" <%if(imm!=null && imm.getTransState()!=null && "D".equalsIgnoreCase(imm.getTransState())) {%> selected="selected" <%} %> >Disposed off</option>
			           </select>
				   </div>
				    <div class="col-md-2">
				       <label>Transaction Date:<span class="mandatory">*</span></label>
				       <input class="form-control input-sm " type="text" name="transDate"  id="transDate" value="<%if(imm!=null && imm.getTransDate()!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(imm.getTransDate().toString()) %> <%} %> " placeholder="Enter Discipline" required="required" readonly>
				   </div> 
				   <div class="col-md-2">
				       <label>Mode:<span class="mandatory">*</span></label>
				       <select name="mode" id="mode" class="form-control input-sm select2" required>
			               <option value="Purchase/Sale" <%if(imm!=null && imm.getMode()!=null && "Purchase/Sale".equalsIgnoreCase(imm.getMode())) {%> selected="selected" <%} %> >Purchase/Sale</option>
			               <option value="Gift" <%if(imm!=null && imm.getMode()!=null && "Gift".equalsIgnoreCase(imm.getMode())) {%> selected="selected" <%} %> >Gift</option>
			               <option value="Mortgage" <%if(imm!=null && imm.getMode()!=null && "Mortgage".equalsIgnoreCase(imm.getMode())) {%> selected="selected" <%} %> >Mortgage</option>
			               <option value="Lease" <%if(imm!=null && imm.getMode()!=null && "Lease".equalsIgnoreCase(imm.getMode())) {%> selected="selected" <%} %> >Lease</option>
			               <option value="Others" <%if(imm!=null && imm.getMode()!=null && "Others".equalsIgnoreCase(imm.getMode())) {%> selected="selected" <%} %> >Others</option>
			           </select>
				   </div>
				     <div class="col-md-4">
				       <label>Location:<span class="mandatory">*</span></label>
				       <input class="form-control" type="text" name="location" id="location" value="<%if(imm!=null && imm.getLocation()!=null) {%><%=imm.getLocation()%> <%} %>" maxlength="225" placeholder="Municipal No, Village, Taluk" required>
				     </div>              
			     </div>
			     <br>
			     <div class="row">			       
			        <div class="col-md-2">
			          <label>District:<span class="mandatory">*</span></label>
			          <input class="form-control" type="text" name="district" id="district" value="<%if(imm!=null && imm.getDistrict()!=null) {%><%=imm.getDistrict()%> <%} %>" placeholder="Enter District" required>
			        </div>
			         <div class="col-md-2">
                        <div class="form-group">
                          <label>State:<span class="mandatory">*</span></label>
                           <select id="state" name="state" class="form-control input-sm selectpicker" data-live-search="true" required>
                                <option disabled="disabled" value="" selected="selected"> Select</option>
								<%if(States!=null){ 
								for (Object[] O : States) {%>
								<%if(imm!=null){ %>
								<option value="<%=O[1]%>"   <%if(imm.getState()!=null && imm.getState().equalsIgnoreCase(O[1].toString())){%> selected<%}%>><%=O[1]%></option>
								<%}else{%>
								<option value="<%=O[1]%>"   ><%=O[1]%></option>
								<%}}}%>
							</select>
                       </div>
                    </div>	 			        
			        <div class="col-md-2">
                        <div class="form-group">
                            <label>Pincode:<span class="mandatory">*</span></label>
                            <input type="text" class="form-control input-sm"  name="pincode" id="CityPinTextBox" maxlength="6" value="<%if(imm!=null && imm.getPincode()!=null){ %> <%=imm.getPincode()%> <%} %> " placeholder="Enter PIN" onblur="checknegative(this)">
                        </div>
                    </div>
                    <div class="col-md-2">
				       <label>Ownership:<span class="mandatory">*</span></label>
				        <select name="ownership" class="form-control input-sm select2" required>
			               <option value="F" <%if(imm!=null && imm.getOwnership()!=null && "F".equalsIgnoreCase(imm.getOwnership())){ %> selected <%} %> >Freehold</option>
			               <option value="L" <%if(imm!=null && imm.getOwnership()!=null && "L".equalsIgnoreCase(imm.getOwnership())){ %> selected <%} %> >LeaseHold</option>
			           </select>
				    </div>
				    <div class="col-md-4">
				       <label>Description:</label>
				       <input class="form-control" type="text" name="description" value="<%if(imm!=null && imm.getDescription()!=null){ %> <%=imm.getDescription()%> <%} %> " maxlength="500" placeholder="Enter Incase of Cultivable or Irrigated land">
				    </div> 
			     </div>
			     <div class="row">
			        <div class="col-md-2" id="interest">
			           <label>Applicant Interest :<span class="mandatory" >*</span></label>
			           <select  name="applicantInterest" id="applicantInterest" class="form-control input-sm select2" onchange="showcents()" required>
			               <option value="F" <%if(imm!=null && imm.getApplicantInterest()!=null && "F".equalsIgnoreCase(imm.getApplicantInterest() )){ %> selected <%} %> >Full</option>
			               <option value="P" <%if(imm!=null && imm.getApplicantInterest()!=null && "P".equalsIgnoreCase(imm.getApplicantInterest() )){ %> selected <%} %> >Part</option>
			           </select>
			        </div>
			        <div class="col-md-2" id="extent" >			         
			            <label>Extent :</label>
			           <input class="form-control" type="text" name="partialInterest" id="partialInterest" value="<%if(imm!=null && imm.getPartialInterest()!=null){ %> <%=imm.getPartialInterest()%> <%} %>" maxlength="225" <%if(imm==null || "F".equalsIgnoreCase(imm.getApplicantInterest()) ) {%> readonly <%} %> >
			        </div>
			        <div class="col-md-4">			         
			            <label>Ownership Particulars:</label>
			           <input class="form-control" type="text" name="osParticulars" id="osParticulars" value="<%if(imm!=null && imm.getOsParticulars()!=null ){ %><%=imm.getOsParticulars()%> <%} %>" maxlength="500" placeholder="Enter only if transaction is  not exclusive" >
			        </div>
			        <div class="col-md-4">			         
			            <label>Share of each member:</label>
			           <input class="form-control" type="text" name="osShare" id="osShare" value="<%if(imm!=null && imm.getOsShare()!=null ){ %><%=imm.getOsShare()%> <%} %>" maxlength="225" placeholder="Enter only if transaction is not exclusive" >
			        </div>
			     </div>
			      <br>
			     <div class="row">
			        <div class="col-md-2">			         
			            <label>Property Price:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="price" id="price" value="<%if(imm!=null && imm.getPrice()!=null ){ %><%=imm.getPrice()%> <%} %>" maxlength="225" placeholder="Enter price" onblur="checknegative(this)" required>
			        </div>
			        <div class="col-md-2" id="finance">
			           <label>Source for finance:<span class="mandatory" >*</span></label>
			           <select name="financeSource" id="financeSource" class="form-control input-sm select2" required>
			               <option value="Personal savings" <%if(imm!=null && imm.getFinanceSource()!=null && "Personal savings".equalsIgnoreCase(imm.getFinanceSource()) ) {%>selected <%} %> >Personal savings</option>
			               <option value="Other sources" <%if(imm!=null && imm.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(imm.getFinanceSource()) ) {%>selected <%} %> >Other sources</option>
			           </select>
			        </div>
			        <div class="col-md-2" id="others">			         
			            <label>Other Sources:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="otherSource" id="otherSource" value="<%if(imm!=null && imm.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(imm.getFinanceSource()) ){ %><%=imm.getFinanceSource()%> <%} %>" maxlength="225" placeholder="Enter Source Details" required>
			        </div>
			        <div class="col-md-2" id="sanction">
			           <label>Requisite Sanctioned?<span class="mandatory" >*</span></label>
			           <select name="requisite" id="requisite" class="form-control input-sm select2" required>
			               <option value="N">No</option>
			               <option value="Y">Yes</option>
			           </select>
			        </div>
			        
			        <div class="col-md-2" id="party">			         
			            <label>Party's Name:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="partyName" id="partyName" maxlength="225" placeholder="Enter Party Name" required>
			        </div>
			        <div class="col-md-4">			         
			            <label>Party's Address:<span class="mandatory" >*</span></label>
			           <input class="form-control" type="text" name="partyAddress" id="partyAddress" maxlength="500" placeholder="Enter Party Address" required>
			        </div>
			     </div>
			     <br>
			     <div class="row">
			        <div class="col-md-4">			         
			            <label>Transaction Arrangement:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="transArrangement" id="transArrangement" maxlength="500" placeholder="Enter how transaction arranged or to be arrange" required>
			        </div>			     
			        <div class="col-md-2">
			            <label>Is Party Related?<span class="mandatory" >*</span></label>
			            <select name="partyRealted" id="partyRealted" class="form-control input-sm select2" required>
			               <option value="N">No</option>
			               <option value="Y">Yes</option>
			            </select>
			        </div>
			        <div class="col-md-2" id="relation">			         
			            <label>Relationship:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="relationship" id="relationship" maxlength="225" placeholder="Enter relationship" required>
			        </div>
			        <div class="col-md-2">
			            <label>Any dealings w.party?<span class="mandatory" >*</span></label>
			            <select name="futureDealings" id="futureDealings" class="form-control input-sm select2" required>
			               <option value="N">No</option>
			               <option value="Y">Yes</option>
			            </select>
			        </div>
			        <div class="col-md-2" id="dealing">			         
			            <label>Nature of dealing:<span class="mandatory" >*</span></label>
			            <input class="form-control" type="text" name="dealingNature" id="dealingNature" maxlength="225" placeholder="Enter Dealing Nature" required>
			        </div>	
			     </div>
			     <br>
			     <div class="row">
			        <div class="col-md-4" id="sitarSanction">			         
			            <label>Sanction Under SITAR:<span class="mandatory" >*</span></label>
			            <select name="sanctionRequired" id="sanctionRequired" class="form-control input-sm select2" required>
			               <option value="N">Not Required</option>
			               <option value="Y">Required</option>
			            </select>
			        </div>
			        <div class="col-md-4">			         
			            <label>Any relevant facts:</label>
			            <input class="form-control" type="text" name="relavantFacts" maxlength="225" placeholder="Enter Relavant facts">
			        </div>	
			     </div>	
	         </div>
		     	  <div class="row">
					 <div class="col-12" align="center">
						<div class="form-group">
							<%if(imm!=null){ %>
							<input type="hidden" name="immpropertyId" value="<%=imm.getImmPropertyId()%>">
			                  <button type="submit" class="btn btn-sm submit-btn AddItem"	 name="Action" value="EDIT" onclick="return CommentsModel();">SUBMIT</button>
							<%}else{%>
				              <button type="button" class="btn btn-sm submit-btn"	onclick="return CommentsModel();" name="Action" value="ADD">SUBMIT</button>
							<%}%>						
					    </div>
					 </div>
				 </div>			 
		   </div>								
		 <%if(imm!=null){ %>
		 </form>
		 <%}else{ %>
		 </form>
		 <%}%>				
		</div>
	</div>				
</div>
<script type="text/javascript">
function CommentsModel(){
	
	var transState = $('#transState').val();
	var location = $('#location').val();
	var district = $('#district').val();
	var state = $('#state').val();
	var pincode = $('#CityPinTextBox').val();
	var applicantInterest = $('#applicantInterest').val();
	var partialInterest = $('#partialInterest').val();
	var price = $('#price').val();
	var financeSource = $('#financeSource').val();
	var otherSource = $('#otherSource').val();
	var partyName = $('#partyName').val();	
	var partyAddress = $('#partyAddress').val();	
	var transArrangement = $('#transArrangement').val();	
	var dealingNature = $('#dealingNature').val();	

	
	if(location==null || location=="" || location=="null"){
		alert('Enter Location Details!');
		return false;
	}else if(district==null || district=="" || district=="null" ){
		alert('Enter District Details!');
		return false;
	}else if(state==null || state=="" || state=="null" ){
		alert('Select State Details!');
		return false;
	}else if(pincode==null || pincode=="" || pincode=="null" ){
		alert('Enter Pincode Details!');
		return false;
	}else if(applicantInterest=="P" && (partialInterest==null || partialInterest=="" || partialInterest=="null") ){
		alert('Enter Extent Details!');
		return false;
	}else if(price==null || price=="" || price=="null" ){
		alert('Enter Property Price!');
		return false;
	}else if(financeSource=="Other sources" && (otherSource==null || otherSource=="" || otherSource=="null") ){
		alert('Enter Other Source Details!');
		return false;
	}else if(partyName==null || partyName=="" || partyName=="null" ){
		alert('Enter Party Name!');
		return false;
	}else if(partyAddress==null || partyAddress=="" || partyAddress=="null" ){
		alert('Enter Party Address Details!');
		return false;
	}else if(transArrangement==null || transArrangement=="" || transArrangement=="null" ){
		alert('Enter Transaction Arrangement Details!');
		return false;
	}else if(dealingNature==null || dealingNature=="" || dealingNature=="null" ){
		alert('Enter Nature of dealing Details!');
		return false;
	}else{
		if(confirm('Are You Sure to submit')){
			$('#myform1').submit();
			return true;
		}else{
			return false;
		}
		
	}
	
}
</script>

<script type="text/javascript">
$('#transDate').daterangepicker({
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

</script>

<script type="text/javascript">

setPatternFilter($("#CityPinTextBox"), /^-?\d*$/);
setPatternFilter($("#price"), /^-?\d*$/);

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

function showcents(){
var x=$('#applicantInterest').val();
if(x=="P"){
	$('#extent').html('<label>Extent <span class="mandatory">*</span></label><input class="form-control" type="text" name="partialInterest" id="partialInterest" maxlength="225" value="<%if(imm!=null && imm.getPartialInterest()!=null){ %> <%=imm.getPartialInterest()%> <%} %>" required>')
}else{
	
	$('#extent').html('<label>Extent </label><input class="form-control" type="text" name="partialInterest" id="partialInterest" value="<%if(imm!=null && imm.getPartialInterest()!=null){ %> <%=imm.getPartialInterest()%> <%} %>" maxlength="225" readonly>');
	$('#partialInterest').val('');
}
} 

$( document ).ready(function() {
	$('#sanction').hide();
	$('#others').hide();
	$('#relation').hide();	
	$("#party").addClass("col-md-4");
	$("#dealing").addClass("col-md-4");
	/* $("#interest").addClass("col-md-4"); */
	$('#sitarSanction').hide();
});

/*  $("#applicantInterest").change(function(){
	var interest = $('#applicantInterest').val();
	  if(interest=="P"){
		$('#extent').show();
		$("#interest").removeClass("col-md-4");
		}
	  else{
		$('#extent').hide();
		$("#interest").addClass("col-md-4");
		}
}); */

$("#transState").change(function(){
	var acquire = $('#transState').val();
	var otherSource = $('#financeSource').val();
	var mode = $('#mode').val();
	  if(acquire=="A")
	  {
		$('#finance').show();
		$('#sanction').hide();
		
	    if(otherSource=="Other sources")
	    {
		   $('#others').show();
	       $("#party").removeClass("col-md-4");
		}
	    if(mode=="Gift")
	    {
		  $('#sitarSanction').show();
	    }
	  }
	  else{
		$('#finance').hide();
		$('#sanction').show();
		 $('#others').hide(); 
		 $("#party").addClass("col-md-4");
		 $('#sitarSanction').hide();
		}
	});
	
$("#financeSource").change(function(){
	var otherSource = $('#financeSource').val();

	  if(otherSource=="Other sources"){
		$('#others').show();
		$("#party").removeClass("col-md-4");
		}
	  else{
		$('#others').hide();			
		$("#party").addClass("col-md-4");
		}
});


$("#partyRealted").change(function(){
	var relation = $('#partyRealted').val();

	  if(relation=="Y"){
		$('#relation').show();
		$("#dealing").removeClass("col-md-4");
		}
	  else{
		$('#relation').hide();
		$("#dealing").addClass("col-md-4");
		}
});

$("#mode").change(function(){
	var mode = $('#mode').val();

	  if(mode=="Gift"){
		$('#sitarSanction').show();
		}
	  else{
		$('#sitarSanction').hide();			
		}
});

/* function showFinanceSource(){
  var acquire = $('#transState').val();
	
  if(acquire=="A"){
     $('#finance').css("display","block");
	$('#sanction').css("display","none");
 
	}else{
		$('#finance').css("display","none");
		$('#sanction').css("display","block");
	}
} */

</script> 
</body>
</html>
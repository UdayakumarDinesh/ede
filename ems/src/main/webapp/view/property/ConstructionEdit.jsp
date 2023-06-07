<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List,com.vts.ems.property.model.PisPropertyConstruction"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 

<style type="text/css">

</style>
</head>
<body>
<%
Object[] empData=(Object[])request.getAttribute("EmpData");
PisPropertyConstruction con = (PisPropertyConstruction)request.getAttribute("Construction");
%>

<div class="card-header page-top">
		<div class="row">
				<div class="col-md-7">
				<h5>Construction Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			   </div>
			<div class="col-md-5">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="PropertyDashBoard.htm">Property</a></li>
					<li class="breadcrumb-item "><a href="ConstructionRenovation.htm">Construction</a></li>
					<li class="breadcrumb-item active " aria-current="page">Construction Edit</li>
				</ol>
			</div>	
		</div>
</div>	
<div class="page card dashboard-card"> 
  <div class="card-body">
	<div class="row">
	  <div class="col-12">
	  <div class="card">	
		<form action="ConstructionEdit.htm" method="POST" autocomplete="off" id="myform1">
		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			 <div class="card-body">			 
                 <div class="row">
				   <div class="col-md-4">
				      <label>Permission for:<span class="mandatory">*</span></label> 
				      <select name="transaction" class="form-control input-sm select2" required>
			               <option value="C" <%if(con!=null && con.getTransactionState().equalsIgnoreCase("C")){%>selected<%}%> >Construction of house</option>
			               <option value="A" <%if(con!=null && con.getTransactionState().equalsIgnoreCase("A")){%>selected<%}%> >Addition of an existing house</option>
			               <option value="R" <%if(con!=null && con.getTransactionState().equalsIgnoreCase("R")){%>selected<%}%> >Renovation of an existing house</option>
			           </select>
				   </div>
				   <div class="col-md-2">
				      <label>Estimated Cost:<span class="mandatory">*</span></label>
				      <input class="form-control" type="text" name="estimatedCost" value="<%if(con!=null && con.getEstimatedCost()!=null){%><%=con.getEstimatedCost()%><%}%>" id="estimatedCost" placeholder="Enter Estimated Cost">
				   </div>
				   <div class="col-md-2">
                        <div class="form-group">
                            <label>Completed By:<span class="mandatory">*</span></label>                        
                            <input type="text" class="form-control input-sm " name="completedBy" value="<%if(con!=null && con.getConstrCompletedBy()!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(con.getConstrCompletedBy()+"") %><% }%>" id="completedBy" required="required" placeholder="Enter Discipline" readonly>   
                        </div>
                   </div>
                   <div class="col-md-4">
				       <label>Construction will be:<span class="mandatory">*</span></label>
				       <select name="supervisedBy" id="supervisedBy" class="form-control input-sm select2" required>
			               <option value="Myself" <%if(con!=null && con.getSupervisedBy()!=null && con.getSupervisedBy().equalsIgnoreCase("Myself")){ %>selected<%} %> >Supervised Myself</option>
			               <option value="Others" <%if(con!=null && con.getSupervisedBy()!=null && !con.getSupervisedBy().equalsIgnoreCase("Myself")){ %>selected<%} %>>Done by others</option>
			           </select>
				   </div>
				  </div>
				  
				  <div class="row">
				    
				   <div class="col-md-2" id="others">
				       <label>Others:<span class="mandatory">*</span></label>
			           <input class="form-control" type="text" name="others" value="<%if(con!=null && con.getSupervisedBy()!=null && !con.getSupervisedBy().equalsIgnoreCase("Myself")) {%><%=con.getSupervisedBy()%><%} %>" id="othersSupervised" placeholder="Enter Others Details" maxlength="225">
				   </div> 
				    <div class="col-md-2" id="official">
				       <label>Official dealings:<span class="mandatory">*</span></label>
				       <select name="officialDealings" id="officialDealings" class="form-control input-sm select2" required>
			               <option value="N" <%if(con!=null && con.getContractorDealings()!=null && con.getContractorDealings().equalsIgnoreCase("N")) {%>selected<%} %>>No</option>
			               <option value="Y" <%if(con!=null && con.getContractorDealings()!=null && con.getContractorDealings().equalsIgnoreCase("Y")) {%>selected<%} %>>Yes</option>
			           </select>
				   </div> 
				   <div class="col-md-2" id="dealingNature">
				      <label>Nature of Dealings:<span class="mandatory">*</span></label>
			           <input class="form-control" type="text" name="natureOfDealing" value="<%if(con!=null && con.getNatureOfDealings()!=null ) {%><%=con.getNatureOfDealings() %><%} %>" id="natureOfDealing" placeholder="Enter Nature of Dealing" maxlength="225" style="width: 105%;">
				   </div>
				   <div class="col-md-2" id="contractorName">
				      <label>Contractor Name:<span class="mandatory">*</span></label>
			           <input class="form-control" type="text" name="contractorName" value="<%if(con!=null && con.getContractorName()!=null) {%><%=con.getContractorName() %><%} %>" id="NameOfcontractor" placeholder="Enter Contractor Name" maxlength="225" style="width: 105%;">
				   </div>
				   <div class="col-md-4" id="contractorAddress">
				      <label>Contractor Business Place:<span class="mandatory">*</span></label>
			           <input class="form-control" type="text" name="contractorPlace" value="<%if(con!=null && con.getContractorPlace()!=null) {%><%=con.getContractorPlace() %><%} %>" id="contractorPlace" placeholder="Enter Contractor Business Place" maxlength="225">
				   </div>   
				  </div>
				  
				  <br>
				  <div class="form-group">
				     <label>The Cost of proposed Construction will be as under:</label>
				     <div class="row">
				      
				        <div class="col-md-3">
				           <label>Own Savings:</label>
				           <input class="form-control" type="text" name="ownSavings" value="<%if(con!=null && con.getOwnSavings()!=null) {%><%=con.getOwnSavings() %><%} %>" id="ownSavings" placeholder="Enter Own Savings">
				        </div>
				        <div class="col-md-3">
				           <label>Loans / Advances with full details:</label>
				           <input class="form-control" type="text" name="loans" value="<%if(con!=null && con.getLoans()!=null) {%><%=con.getLoans() %><%} %>" placeholder="Enter Loans / Advances Details" maxlength="225">
				        </div>
				        <div class="col-md-3">
				           <label>Other Sources with full details:</label>
				           <input class="form-control" type="text" name="otherSources" value="<%if(con!=null && con.getOtherSources()!=null){%><%=con.getOtherSources() %><%} %>" placeholder="Enter Other Sources Details" maxlength="225">
				        </div>
				         <div class="col-md-3">
				            <label>Total Proposed Cost:<span class="mandatory">*</span></label>
				           <input class="form-control" type="text" name="proposedCost" value="<%if(con!=null && con.getProposedCost()!=null){ %><%=con.getProposedCost() %><%} %>" id="proposedCost" placeholder="Enter Total Cost">
				        </div>
				       </div>
				       <br>
				       <div class="row">
				      
				        <div class="col-md-3">
				           <label>Own Savings details:</label>
				           <input class="form-control" type="text" name="ownSavingsDetails" value="<%if(con!=null && con.getOwnSavingsDetails()!=null){ %><%=con.getOwnSavingsDetails() %><%} %>"  id="ownSavingsDetails" placeholder="Enter Own Savings Details" maxlength="225">
				        </div>
				        <div class="col-md-3">
				           <label>Loans / Advances with full details:</label>
				           <input class="form-control" type="text" name="loansDetails" value="<%if(con!=null && con.getLoansDetails()!=null){ %><%=con.getLoansDetails()%><%} %>" placeholder="Enter Loans / Advances Details" maxlength="225">
				        </div>
				        <div class="col-md-3">
				           <label>Other Sources with full details:</label>
				           <input class="form-control" type="text" name="otherSourcesDetails" value="<%if(con!=null && con.getOtherSourcesDetails()!=null){ %><%=con.getOtherSourcesDetails() %><%} %>" placeholder="Enter Other Sources Details" maxlength="225">
				        </div>				        
				       </div>
				     </div>
			  
			         <br>
                     <br>
		     	     <div class="row">
					    <div class="col-md-12" align="center">
						   <div class="form-group">		
						       <input type="hidden" name="ConstructionId" value="<%if(con!=null){%><%=con.getConstructionId()%><%}%>">					
				              <button type="button" class="btn btn-sm submit-btn"	onclick="return CommentsModel();" name="Action" value="ADD">SUBMIT</button>										
					       </div>
					    </div>
				     </div>			 
		       </div>								
		    </form>			
		  </div>
	    </div>
	 </div>
   </div>
</div>				
<script type="text/javascript">
function CommentsModel(){
	
	var estimatedCost = $('#estimatedCost').val();
	var supervisedBy = $('#supervisedBy').val();
	var othersSupervised = $('#othersSupervised').val();
	var officialDealings = $('#officialDealings').val();
	var natureOfDealing = $('#natureOfDealing').val();
	var contractorName = $('#NameOfcontractor').val();
	var contractorPlace = $('#contractorPlace').val();
	var proposedCost = $('#proposedCost').val();

    if(estimatedCost==null || estimatedCost=="" || estimatedCost=="null"){
		alert('Enter Estimated Cost!');
		return false;
	}else if(supervisedBy=="Others" && (othersSupervised==null || othersSupervised=="" || othersSupervised=="null") ){
		alert('Enter Others Details!');
		return false;
	}else if(supervisedBy=="Others" && officialDealings=="Y" && (natureOfDealing==null || natureOfDealing=="" || natureOfDealing=="null") ){
		alert('Enter Nature of Dealings Details!');
		return false;
	}else if(supervisedBy=="Others" && (contractorName==null || contractorName=="" || contractorName=="null") ){
		alert('Enter Contractor Name!');
		return false;
	}else if(supervisedBy=="Others" && (contractorPlace==null || contractorPlace=="" || contractorPlace=="null") ){
		alert('Enter Contractor Business Place!');
		return false;
	}else if(proposedCost==null || proposedCost=="" || proposedCost=="null"){
		alert('Enter Total Proposed Cost Construction!');
		return false;
	}else if(proposedCost!=estimatedCost){
		alert('Estimated Cost And Total Proposed Cost Should be equal!');
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
$('#completedBy').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :new Date(), 
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>

<script type="text/javascript">

setPatternFilter($("#estimatedCost"), /^-?\d*$/);
setPatternFilter($("#ownSavings"), /^-?\d*$/);
setPatternFilter($("#loans"), /^-?\d*$/);
setPatternFilter($("#otherSources"), /^-?\d*$/);
setPatternFilter($("#proposedCost"), /^-?\d*$/);

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
$(document).ready(function(){
	var supervisedBy = $('#supervisedBy').val();
	var officialDealings = $('#officialDealings').val();
	var officialDealings = $('#officialDealings').val();
	
	if(supervisedBy=="Myself"){
		$('#official').hide();
		$('#others').hide();
		$('#contractorName').hide();
		$('#contractorAddress').hide();
	}
	else{
		$('#official').show();
		$('#others').show();
		$('#contractorName').show();
		$('#contractorAddress').show();
	}
	
	if(officialDealings=="Y"){
		$('#dealingNature').show();
	}else{
		$('#dealingNature').hide();
	}
});

$('#supervisedBy').change(function(){
	var supervisedBy = $('#supervisedBy').val();
	var officialDealings = $('#officialDealings').val();
	
	if(supervisedBy=="Others"){
		$('#others').show();
		$('#official').show();
		$('#contractorName').show();
		$('#contractorAddress').show();
		if(officialDealings=="Y"){
			$('#dealingNature').show();
		}else{
			$('#dealingNature').hide();
		}
	}
	else{
		$('#others').hide();
		$('#official').hide();
		$('#dealingNature').hide();
		$('#contractorName').hide();
		$('#contractorAddress').hide();
	}
	
});

$('#officialDealings').change(function(){
	var officialDealings = $('#officialDealings').val();
	if(officialDealings=="Y"){
		$('#dealingNature').show();
	}else{
		$('#dealingNature').hide();
	}
});
</script>
<script type="text/javascript">

$(document).ready(function(){
	
	 function calculateTotal() {
        var ownSavings = parseFloat($('#ownSavings').val()) || 0;
        var loans = parseFloat($('#loans').val()) || 0;
        var otherSources = parseFloat($('#otherSources').val()) || 0;
        var total = ownSavings + loans + otherSources;
        $('#proposedCost').val(total);
      }

      // Call the calculateTotal function whenever any of the input fields change
      $('#ownSavings, #loans, #otherSources').on('input', calculateTotal);
});

</script>
</body>
</html>
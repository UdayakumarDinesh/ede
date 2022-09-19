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

								<%if(States!=null){ 
								for (Object[] O : States) {%>
								<%if(foreignvisit!=null){ %>
								<option value="<%=O[1]%>"   <%if(foreignvisit.getCountryName().equalsIgnoreCase(O[1].toString())){%> selected<%}%>><%=O[1]%></option>
								<%}else{%>
								<option value="<%=O[1]%>"   ><%=O[1]%></option>
								<%}}}%>
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
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
		                      <%if(pisstatelist!=null && pisstatelist.size()>0){for(Object[] obj : pisstatelist){%>
		                        <option value="<%=obj[0]%>"><%=obj[1]%></option>
		                       <%}}%>
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
		}else if(Author==null || Author=='' || Author=="null" ){
			alert('Enter the Author Name!');
			return false;
		}else if(Discipline==null || Discipline=='' || Discipline=="null" ){
			alert('Enter the Discipline!');
			return false;
		}else if(Title==null || Title=='' || Title=="null" ){
			alert('Enter the Title!');
			return false;
		}else if(Publication==null || Publication=='' || Publication=="null" ){
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
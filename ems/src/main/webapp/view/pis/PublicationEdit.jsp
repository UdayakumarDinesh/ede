<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.time.LocalDate"%>
        <%@page import="java.util.List" %>
         <%@page import="com.vts.ems.pis.model.*" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Property Edit</title>
</head>
<body>
<%

Publication pub = (Publication)request.getAttribute("PublicationDetails");
Object[] empdata      = (Object[])request.getAttribute("Empdata");
List<Object[]> pisstatelist  = (List<Object[]>)request.getAttribute("PisStateList");
%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-5">
					<h5>
						Publication Edit<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%=empdata[0]%> (<%=empdata[1]%>)</b></small>
					</h5>
				</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PublicationList.htm?empid=<%=empdata[2]%>">Publication List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Publication Edit</li>
					</ol>
				</div>
			</div>
		</div>
		
	<div class="page card dashboard-card">
		
		
		<div class="card-body" >
		
		<div class="row" >
		<div class="col-1"></div>
		<div class="col-9">
		<form action="EditPublication.htm" method="POST" autocomplete="off" enctype="multipart/form-data" >
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
		                     <select class="form-control input-sm select2" name="PublicationType" required  data-live-search="true">
		                      
		                        <option value="JOURNAL" <%if(pub!=null && pub.getPub_type()!=null && "JOURNAL".equalsIgnoreCase(pub.getPub_type())){%> selected="selected"<%}%>>JOURNAL</option>
		                        <option value="BOOK"    <%if(pub!=null && pub.getPub_type()!=null && "BOOK".equalsIgnoreCase(pub.getPub_type())){%>    selected="selected"<%}%>>BOOK</option>
		                        <option value="PAPER"   <%if(pub!=null && pub.getPub_type()!=null && "PAPER".equalsIgnoreCase(pub.getPub_type())){%>   selected="selected"<%}%>>PAPER</option>
		                        <option value="POSTER"  <%if(pub!=null && pub.getPub_type()!=null && "POSTER".equalsIgnoreCase(pub.getPub_type())){%>  selected="selected"<%}%>>POSTER</option>
		                        <option value="ARTICLE" <%if(pub!=null && pub.getPub_type()!=null && "ARTICLE".equalsIgnoreCase(pub.getPub_type())){%> selected="selected"<%}%>>ARTICLE</option>
		                        <option value="PATENT"  <%if(pub!=null && pub.getPub_type()!=null && "PATENT".equalsIgnoreCase(pub.getPub_type())){%>  selected="selected"<%}%>>PATENT</option>
		                    </select>
		                </div>
		               </div> 
		                
		                <div class="col-md-3">
		                 <div class="form-group">
                            <label>Author<span class="mandatory">*</span></label>
                            <input  id="author" <%if(pub!=null && pub.getAuthors()!=null){%> value="<%=pub.getAuthors()%>" <%}%>  type="text" name="Author" class="form-control input-sm" required="required"  placeholder="Enter Author" > 
                        </div>
		                
		              </div>
		              
		               <div class="col-md-3">
		                <div class="form-group">
		                    <label>Discipline<span class="mandatory">*</span></label>
		                    <input  id="discipline" <%if(pub!=null && pub.getDiscipline()!=null){%> value="<%=pub.getDiscipline()%>" <%}%> type="text" name="Discipline" class="form-control input-sm" required="required"  placeholder="Enter Discipline" > 
		                </div>
		               </div> 
		               
		                 <div class="col-md-3">
		                 <div class="form-group">
                            <label>Title<span class="mandatory">*</span></label>
		                    <input  id="title" <%if(pub!=null && pub.getTitle()!=null){%> value="<%=pub.getTitle()%>" <%}%>  type="text" name="Title" class="form-control input-sm" required="required"  placeholder="Enter Title" > 
		                </div>
                        </div>
		              </div>

                <div class="row">
                     <div class="col-md-3">
		                <div class="form-group">
		                	<label>Publication Name<span class="mandatory">*</span></label>
		                    <input  id="publication" <%if(pub!=null && pub.getPub_name_vno_pno()!=null){%> value="<%=pub.getPub_name_vno_pno()%>" <%}%>  type="text" name="Publication" class="form-control input-sm" required="required"  placeholder="Enter Publication Name" > 
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
		                      <input  id="patentno" <%if(pub!=null && pub.getPatent_no()!=null){%> value="<%=pub.getPatent_no()%>" <%}%>  type="text" name="PatentNo" class="form-control input-sm" required="required"  placeholder="Enter Patent No" > 
		                </div>
		               </div>
		               
		               <div class="col-md-3">
		                <div class="form-group">
		                    <label>Country<span class="mandatory">*</span></label>
		                     <select class="form-control input-sm select2" name="Country" required  data-live-search="true">
		                      <option disabled="disabled" value="" selected="selected"> Select</option>
		                      <%if(pisstatelist!=null && pisstatelist.size()>0){for(Object[] obj : pisstatelist){%>
		                        <option value="<%=obj[0]%>" <%if(pub!=null && pub.getCountry()!=null && pub.getCountry().equalsIgnoreCase(obj[0].toString())){%> selected="selected"<%}%>><%=obj[1]%></option>
		                       
		                       <%}}%>
		                    </select>
		                </div>
		               </div>
                  
                </div>

						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							    <input type="hidden" name="empid" <%if(empdata!=null){%> value="<%=empdata[2]%>" <%}%>> 
								<input type="hidden" name="publicationid" <%if(pub!=null && pub.getPublication_id()!=0){%> value="<%=pub.getPublication_id()%>" <%}%>> 
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return CommentsModel();"
									name="action" value="submit">SUBMIT</button>
									</div>
							</div>
						</div>
						</div></div>
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
						               <input type="file" class=" form-control w-100"   id="file" name="selectedFile" > 
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick="return confirm('Are You Sure To Submit!');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
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
	<%if(pub!=null && pub.getPub_date()!=null){%>
	"startDate" : new Date("<%=pub.getPub_date()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
</script>
<script type="text/javascript">
		function CommentsModel()
		{
			var author = $("#author").val();
			var discipline = $("#discipline").val();
			var title = $("#title").val();
			var publication = $("#publication").val();
			var patentno = $("#patentno").val();
			
			
			if(author==null || author=='' || author=="null" ){
				alert('Enter the Author!');
				return false;
			}else if(discipline==null || discipline=='' || discipline=="null" ){
				alert('Enter the Discipline!');
				return false;
			}else if(title==null || title=='' || title=="null" ){
				alert('Enter the Title!');
				return false;
			}else if(publication==null || publication=='' || publication=="null" ){
				alert('Enter the Publication Name!');
				return false;
			}else if(patentno==null || patentno=='' || patentno=="null" ){
				alert('Enter the Patent No!');
				return false;
			}else {
				$('#myModal').modal('show');
			}
				 
		}
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

$('#patentno').keypress(function (e) {
    var regex = new RegExp("^-?\d*$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    else
    {
    e.preventDefault();
    alert('Please Enter Digits');
    return false;
    }
});
</script>
</body>
</html>
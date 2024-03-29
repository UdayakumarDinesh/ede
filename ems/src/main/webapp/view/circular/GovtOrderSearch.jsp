<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
    <%@ page import="java.util.*" %>
    <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

<style>
#button {
   float: left;
   width: 80%;
   padding: 5px;
   background: #dcdfe3;
   color: black;
   font-size: 17px;
   border:none;
   border-left: none;
   cursor: pointer;
}

</style>
</head>
<body>
<%	
	String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
	List<Object[]> SearchList=(List<Object[]>)request.getAttribute("SearchList");
	List<Object[]> DepTypeList=(List<Object[]>)request.getAttribute("DepTypeList");
	String TopicId = (String)request.getAttribute("TopicId");
	String Search = (String)request.getAttribute("Search");
%>


<div class="card-header page-top">
  <div class="row">  
  <div class="col-md-8" style="width:30px">
    	<h5>Government Order Search</h5>
  </div>   
     	<div class="col-md-4">
			<ol class="breadcrumb">
				<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
				<li class="breadcrumb-item "><a href="GovtOrdersList.htm"> Government Orders </a></li>
				<li class="breadcrumb-item active " aria-current="page">Government Order Search</li>
                </ol>                
		</div>  
 </div>
</div>

		 
	    	<div class="card" >
	        <div class="card-header" style="height: 4rem">
	              <form action="GovtOrderSearch.htm" method="POST"> 
	                   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					      <div class="row" >
					      		
								<div class="col-md-4" >
									
										<b>Topic : &nbsp;</b>
										<select class="form-control select2" name="TopicId" style="width: 70%" onchange="this.form.submit()" >
											<option value="A" <%if(TopicId.equalsIgnoreCase("A") ){ %> selected <%} %> >All</option>
											
											<%for(Object[] dep :DepTypeList){ %>
											<option value="<%=dep[0]%>" <%if(TopicId.equalsIgnoreCase(dep[0].toString())){ %> selected <%} %>><%=dep[2]%></option>
											<%} %>
											
										</select>

	
								</div>
								
							    <div class="col-md-4">
                                     <input type="text" placeholder="Search Government Order" name="search" value="<%if(Search!=null) {%><%=Search %><%} %>" autocomplete="off" style=" width: 100%;padding: 5px;border-left: none;border-top: none;border-right: none;border-down: black;">
                                </div>
                                <div class="col-md-1" >
                                	<button type="submit" id="button" ><i class="fa fa-search"></i></button>
                                </div>
                                
				          </div>       	
                  </form>	      
	       </div>    	             
	       </div>
    

<div class="card-body main-card  " >
       
        <form action="#" method="POST">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					    <th style="width:4%">SN</th>
					  <th style="width:10%">Order No</th>
                        <th style="width:11%">Date</th>
                        <th style="width:45%">Subject</th>
                        <th style="width:8%">Download</th>
                  	</tr>
				</thead>
				<tbody>
				        <%
				        if(SearchList!=null){ int slno=0;  
                         for(Object[] ls:SearchList ){ 
                        %> 
                     <tr>
                         <td style="text-align: center;"><%=++slno %>.</td>
                         <td><%=ls[1]%></td>
                         <td style="text-align: center;"><%=ls[2]%></td>
                         <td><%=ls[3]%></td>
                         <td>
                        <button type="submit" class="btn btn-sm" name="OrderId" value="<%=ls[0] %>" formaction="GovtOrderDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download" formtarget="_blank">
						 <i class="fa-solid fa-download " style="color: green;"></i>
						 </button></td>
                         
                      </tr>
                           <%} }%>
                          
			    </tbody>
		     </table>
		       </div>
	    </form>
	    </div>
	    
	    <script type="text/javascript">

$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :$("#fromdate").val(),  
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
	$(document).ready(function(){
		   $('#fromdate, #todate').change(function(){
		       $('#myform').submit();
		    });
		});
</script>
</body>
</html>
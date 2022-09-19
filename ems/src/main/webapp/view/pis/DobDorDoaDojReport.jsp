<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
      <%@page import="java.time.LocalDate"%>
       <%@page import="java.util.List" %>
       <%@page import="com.vts.ems.pis.model.*" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dob-Dor-Doa-Doj Report</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<spring:url value="/webresources/js/dataTables.buttons.min.js" var="datatablejsbuttons" />
<script src="${datatablejsbuttons}"></script>

<spring:url value="/webresources/js/buttons.flash.min.js" var="datatablejsflash" />
<script src="${datatablejsflash}"></script>

<spring:url value="/webresources/js/jszip.min.js" 	var="datatablejszip" />
<script src="${datatablejszip}"></script>

<spring:url value="/webresources/js/pdfmake.min.js" var="datatablejspdfmake" />
<script src="${datatablejspdfmake}"></script>

<spring:url value="/webresources/js/vfs_fonts.js" var="datatablejsvfs" />
<script src="${datatablejsvfs}"></script>

<spring:url value="/webresources/js/buttons.html5.min.js" 	var="datatablejshtml5" />
<script src="${datatablejshtml5}"></script>

<spring:url value="/webresources/js/buttons.print.min.js" 	var="datatablejsprint" />
<script src="${datatablejsprint}"></script>
</head>
<body>
<%
List<Object[]> reportobjs=(List<Object[]>)request.getAttribute("reportobjs");
List<String> rages=(List<String>)request.getAttribute("rages");
List<String> serviceyears=(List<String>)request.getAttribute("serviceyears");
String msg=(String)request.getAttribute("msg");
String type=(String)request.getAttribute("type");
Integer cyear=(Integer)request.getAttribute("cyear");
String cmonth=(String)request.getAttribute("cmonth");
Integer cmonthvalue=(Integer)request.getAttribute("cmonthvalue");
%>
<div class="card-header page-top">
			<div class="row">
				<div class="col-6">
					<h5>
						Dob-Dor-Doa-Doj Report
					
					</h5>
				</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PIS.htm">PIS</a></li>
						<li class="breadcrumb-item active " aria-current="page">Dob-Dor-Doa-Doj Report</li>
					</ol>
				</div>
			</div>
		</div>
		
		
 <div class=" page card dashboard-card" >
	<div class="container-fluid" style="margin-top: 10px;margin-bottom: -30px;">	
<form action="dobdordoadojreports.htm" method="post" id="form1">
<div class="nav navbar bg-light dashboard-margin custom-navbar" >					
						
						<div class="col-2"></div>
		  		        <div class="col-md-2">
		  		        	<select class="form-control input-sm select2"  name="dvalue" data-live-search="true" required="required">	
		                           <option value="DOB" <%if(type!=null && "DOB".equalsIgnoreCase(type)){%> selected="selected" <%}%>>DOB</option>
		                           <option value="DOA" <%if(type!=null && "DOA".equalsIgnoreCase(type)){%> selected="selected" <%}%>>DOA</option>
		                           <option value="DOR" <%if(type!=null && "DOR".equalsIgnoreCase(type)){%> selected="selected" <%}%>>DOR</option>
		                           <option value="DOJ" <%if(type!=null && "DOJ".equalsIgnoreCase(type)){%> selected="selected" <%}%>>DOJ</option>	
			 				</select>
			 				</div>
			 				 
			 				 
			 				 <div class="col-md-2">
		  		        	<select class="form-control input-sm select2" name="month" data-live-search="true" required="required">
		                            <option value="0"  <%if(cmonthvalue!=null && "0".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>--No Month--</option>
									<option value="1"  <%if(cmonthvalue!=null && "1".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>January</option>
									<option value="2"  <%if(cmonthvalue!=null && "2".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>February</option>
									<option value="3"  <%if(cmonthvalue!=null && "3".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>March</option>
									<option value="4"  <%if(cmonthvalue!=null && "4".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>April</option>
									<option value="5"  <%if(cmonthvalue!=null && "5".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>May</option>
									<option value="6"  <%if(cmonthvalue!=null && "6".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>June</option>
									<option value="7"  <%if(cmonthvalue!=null && "7".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>July</option>
									<option value="8"  <%if(cmonthvalue!=null && "8".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>August</option>
									<option value="9"  <%if(cmonthvalue!=null && "9".equalsIgnoreCase(String.valueOf(cmonthvalue))){%>  selected="selected" <%}%>>September</option>
									<option value="10" <%if(cmonthvalue!=null && "10".equalsIgnoreCase(String.valueOf(cmonthvalue))){%> selected="selected" <%}%>>October</option>
									<option value="11" <%if(cmonthvalue!=null && "11".equalsIgnoreCase(String.valueOf(cmonthvalue))){%> selected="selected" <%}%>>November</option>
									<option value="12" <%if(cmonthvalue!=null && "12".equalsIgnoreCase(String.valueOf(cmonthvalue))){%> selected="selected" <%}%>>December</option>
			 				</select>
						</div>
						
						<div class="col-md-2">
			 			   <input class="form-control input-sm mydate" readonly="readonly" data-date-format="yyyy-mm-dd" id="datepicker1" name="year"   placeholder="Select Year" required="required" >
						  </div>
					   <div class="col-md-2">
					        <button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Submit?');"	name="action" value="submit">SUBMIT</button>
					   </div>
      				  
      				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      				   </div>
      				</form>
</div>       

	<div class="card-body" style="margin-top: 30px;">

		<div class="card">
			<div class="card-body">
					<table class="table table-hover table-striped  table-condensed  table-bordered DataTableWithPdfandExcel"  id="mytable">
                                <thead>
                                   <tr>            
                                   <th>Name &amp; Designation</th>
                                   <th>PunchCard</th>
                                   <th>Division</th>
                                   <%if(type.equals("DOB")) {%>
                                   <th>DOB</th>
                                   <%} %>
                                   <%if(type.equals("DOA")) {%>
                                   <th>DOA</th>
                                   <%} %>
                                   <%if(type.equals("DOR")) {%>
                                   <th>DOR</th>
                                   <%} %>
                                   <%if(type.equals("DOJ")) {%>
                                   <th>DOJ</th>
                                   <%} %>
                                   <th>Age</th>
                                   <!-- <th>DOA</th> -->
                                   <th>Service Years</th>
                                   
                                   </tr>
                             </thead>
                          <tbody>
                          <%if(reportobjs!=null && reportobjs.size()>0){
                          int a=0,b=0;%>
                        <%for(Object[] ls:reportobjs){%>
                              
	                          <tr> 
	                          
	                             <td style='text-align:left;'> <%=ls[5]%> - <%=ls[6]%> </td>
                                 <td> <%=ls[8]%> </td>
                                 <td> <%=ls[7]%> </td>
                                  <%if(type.equals("DOB")){%>
                                    <td align="center"> <%=ls[4]%> </td>
                                   <%}%>
                                   <%if(type.equals("DOA")){%>
                                    <td align="center"> <%=ls[4]%> </td>
                                   <%}%>
                                   <%if(type.equals("DOR")){%>
                                   <td align="center"> <%=ls[4]%> </td>
                                   <%}%>
                                   <%if(type.equals("DOJ")){%>
                                   <td align="center"> <%=ls[4]%> </td>
                                   <%}%>
                                  <td align="center"><%=rages.get(a++)%> </td>
                                  <td><%=serviceyears.get(b++)%></td>
                                 
                            </tr>
                     <%}}%>
                          </tbody>  
                  </table>
			</div>
		</div>
	</div>
</div>			
<script type="text/javascript">
		   $("#datepicker1").datepicker({
		    	
		    	autoclose: true,
		    	 format: 'yyyy',
		    		 viewMode: "years", 
		    		    minViewMode: "years"
		    });

		 <%if(cyear!=null){%>
		        document.getElementById("datepicker1").value =<%=cyear%>
		  <%}else{%>
		  		document.getElementById("datepicker1").value =new Date().getFullYear()
		  <%}%>
  
		  
		  
		  $(document).ready(function() {
				
			    $('.DataTableWithPdfandExcel').dataTable( 
			    {    	
			    	dom: 'Brt',
			    	lengthMenu: [ 50, 75, 100],
			    	buttons: [
			         	{
			         		
			         		extend: "pdfHtml5",
			         		className: "btn-sm btn1  ",
			         		title: ' Report', 
			         		orientation: 'portrait',
			         		pageSize: 'A4',
			         		titleAttr: 'Print PDF',
			         		text: '<i class="fa-solid fa-file-pdf" style="color: green;" ></i>',
			         		
			         		download:'open',
			         		
			         	 	
			         	},
			         	{
			         		extend: "excel",
			         		className: "btn-sm btn1 ",
			         		title: ' Report',
			         		titleAttr: 'Print excel',
			         		text: '<i class="fa-solid fa-file-excel" style="color: green;"></i>'
			         	}
			         	/* ,{
			                extend:    'csvHtml5',
			                className: "btn-sm btn1 ",
			                text:      '<i class="fa fa-file-text-o" style="font-size:25px" aria-hidden="true" ></i>',
			                titleAttr: 'CSV'
			            },{
			                extend: 'copyHtml5',
			                className: "btn-sm btn1 ",
			                text:   '<i class="fa fa-clipboard" style="font-size:25px" aria-hidden="true"></i>',
			                titleAttr: 'Copy',
			                title: 'Milestone Report'
			            } */
			    	]
			    	
			    } );
			} );

</script>			
</body>
</html>
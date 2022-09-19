<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quarterly Strength Report</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
Object[]  basicDetails=(Object[])request.getAttribute("basicDetails");
List<Object[]>	getemployeesIdNameList=(List<Object[]>)request.getAttribute("getemployeesIdNameList");
Object[]   CheckConfirmationYearAndFetchData=(Object[])request.getAttribute("CheckConfirmationYearAndFetchData");

List<Object[]> cadreNames=(List<Object[]>)request.getAttribute("cadreNames");
List<Object[]> EmployeeList=(List<Object[]>)request.getAttribute("EmployeeList");
Object cardid=request.getAttribute("CadreIdAndCadreName");

%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>Quarterly Strength Report</h5>
			</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PIS.htm">PIS</a></li>
						<li class="breadcrumb-item active " aria-current="page">Family List</li>
					</ol>
				</div>
			</div>
	     </div>
	     
<div class=" page card dashboard-card" >
	<div class="container-fluid" style="margin-top: 10px;margin-bottom: -30px;">	

<div class="nav navbar bg-light dashboard-margin custom-navbar"  >
                           <div class="col-md-2" align="right"></div>
						   <div class="col-md-2"  style="margin-top: 30px; margin-left: 400px;">
							<form action="QuarterlyStrengthReportDownload.htm" method="post" target="blank" class="pull-right" style="margin-top:-30px;">	  
					          	  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					          	  <input type="hidden" name="CadreValue" <%if(cardid!=null){%> value="<%=cardid%>" <%}%>/>
					              <button type="submit" class="btn btn-danger btn-xs " name="PrintPdfIndividualReport" value="PrintPdfIndividualReport" data-toggle="tooltip" data-placement="bottom" title="Print Employee Details"><i class="fa-solid fa-file-pdf " style="font-size:24px"></i></button>
					              <button type="submit" class="btn btn-success btn-xs" name="ExportIndividualReport" value="ExportIndividualReport"  title="Export To Excel" formaction="DownloadQuarterlyStrengthExcel.htm" formmethod="post" formnovalidate="formnovalidate"><i class="fa-solid fa-file-excel" style="font-size:24px" ></i></button>
							
							</form>
							</div>
							 <div class="col-md-4" align="left" style="margin-left: -120px;">
						   <form action="QuarterlyStrength.htm" method="POST" id="form1">
		  		        	<select class="form-control input-sm select2" name="CadreId" id="cadreid" style="width:100%;"  onchange="this.form.submit();">
			 				     
			 				     <option value="0" <%if(cardid!=null&&cardid.toString().equals("0")){%>  selected="selected" <%}%>>ALL</option>
			 					  <%if(cadreNames!=null&&cadreNames.size()>0){  
			 					  for(Object[] ls:cadreNames){ %>
			 					 <option value="<%=ls[0]%>" <%if(cardid!=null&&cardid.toString().equals(ls[0].toString())){%>  selected="selected" <%}%>><%= ls[1]%> </option>
					             <%}}%> 
             				</select>
             				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             				</form>
						  </div> 
      				 </div>
</div>       

	
	<div class="card-body" >
	
	<div class="card"  style="margin-top: 20px;">
			
				<div class="card-body " >
						
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
								   <tr>            
	                                   <th>Name of Post</th>
	                                   <th>RE</th>
	                                   <th>Corporate Pool</th>
	                                   <th>Authorised Transaction<br>Trans In</th>
	                                   <th>Authorised Transaction<br>Trans Out</th>
	                                   <th>Total RE</th>
	                                   <th>SC</th>
	                                   <th>ST</th>
	                                   <th>OBC</th> 
	                                   <th>EXSM</th>
	                                   <th>HEL PHY HND</th>
	                                   <th>SPRT</th>
	                                   <th>OTH</th>
	                                   <th>Total Held</th>
	                                   <th>VAC</th>
	                                   <th>Male</th>
	                                   <th>Female</th>
                                  </tr>
                               </thead>
                                <tbody>
			             <%for(Object[] list:EmployeeList){    
                  if(list[8]!=null&&list[9]!=null||list[10]!=null||list[11]!=null&&list[13]!=null&&list[14]!=null&&list[15]!=null&&list[16]!=null&&list[17]!=null&&list[18]!=null&&list[19]!=null){
                               	int value =  Integer.parseInt(list[8].toString());
                                int value2 = Integer.parseInt(list[9].toString());
                                int value3 =Integer.parseInt(list[10].toString());
                                int value4 = Integer.parseInt(list[11].toString());
                                int TotalRE = value+value2+value3-value4;
                               	int data1 =  Integer.parseInt(list[13].toString());
                               	int data2 =  Integer.parseInt(list[14].toString());
                               	int data3 =  Integer.parseInt(list[15].toString());
                               	int data4 =  Integer.parseInt(list[16].toString());
                               	int data5 =  Integer.parseInt(list[17].toString());
                            	int data6 =  Integer.parseInt(list[18].toString());
                            	int data7 =  Integer.parseInt(list[19].toString());
                            	int TotalHeld = data1+data2+data3+data4+data5+data6+data7;
                            	
                            	int VAC = TotalRE-TotalHeld;
                            	
                            %>       
	                    <tr> 
	                                 <td > <%=list[5] %> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[8]!=null){out.println(list[8]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[9]!=null){out.println(list[9]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[10]!=null){out.println(list[10]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[11]!=null){out.println(list[11]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%=TotalRE%></td> 
                                     <%-- <td> <%if(list!=null){%><%if(list[3]!=null){out.println(list[3]);}else{out.println("0");%><%}}%></td> --%>
                                     <td align="center"> <%if(list!=null){%><%if(list[13]!=null){out.println(list[13]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%if(list!=null){%><%if(list[14]!=null){out.println(list[14]);}else{out.println("0");%><%}}%> </td>
                                     <td align="center"> <%=list[15]%> </td>
                                     <td align="center"> <%=list[16]%> </td>
                                     <td align="center"> <%=list[17]%> </td>
                                     <td align="center"> <%=list[18]%> </td>
                                     <td align="center"> <%=list[19]%> </td>
                                     <td align="center"> <%=TotalHeld%> </td> 
                                     <td align="center"> <%=VAC%> </td> 
                                     <td align="center"> <%=list[20]%> </td>
                                     <td align="center"> <%=list[21]%> </td>
                                  
                              <% }} %>
                            </tr>  
                                </tbody> 
							</table>
		   </div>
		 </div>
		</div>	
	   </div>
	  </div>   	
</body>
</html>
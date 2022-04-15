<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"   import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<style>


.a-box {
  display: inline-block;
  width: 100%;
  text-align: center;
}

.img-container {
    height: 11rem;
   /*  width: 200px; */
    overflow: hidden;
    border-radius: 0px 0px 20px 20px;
    display: inline-block;
}

.img-container img {
    transform: skew(0deg, -13deg);
    height: 6.5rem;
    margin: 25px 0px 0px 0px;
        border-radius: 53%;
}

.inner-skew {
    display: inline-block;
    border-radius: 20px;
    overflow: hidden;
    padding: 0px;
    transform: skew(0deg, 13deg);
    font-size: 0px;
    margin: 30px 0px 0px 0px;
    background: #c8c2c2;
    height: 11rem;
    width: 8rem;
}

.text-container {
  box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.2);
  padding: 67px 20px 8px 20px;
  border-radius: 20px;
  background: #fff;
  margin:-83px 0px 0px 0px;
  line-height: 19px;
  font-size: 14px;
}

.text-container h3 {
  margin: 16px 0px 0px 0px;
  font-size: 18px;
      font-family: 'Lato';
    text-transform: capitalize;
    font-weight: 700;
    color: #005C97;
}

.main{
	padding :0px 0px 1.5rem 0px ;
	cursor: pointer;
	 transition: transform .2s;
	 
}

.profile-card-container{
	border-radius: 12px;
    background-color: white;
    max-height: 16rem !important;
    padding: 0px 20px;
}

.fa-angle-left, .fa-angle-right{
	font-size: 3rem;
	color:black;
	
}

.table-card , .profile-card-container{
	  margin: 0px 20px;
}

.profile-card-container > .row {
  display: block;
  overflow-x: auto;
  white-space: nowrap;
  overflow-y : hidden;
}
.profile-card-container > .row > .col-md-2 {
  display: inline-block;
}

.table thead tr{
	background-color: #0e6fb6;
	color: white;
}

.main:hover{
	transform : scale(1.1);
}




.outer-wrapper {
  max-width: 100vw;
  overflow-x: scroll;
  position: relative;
  scrollbar-color: #d5ac68 #f1db9d;
  scrollbar-width: thin;
  -ms-overflow-style: none;
}

.pseduo-track {
  background-color: #f1db9d;
  height: 2px;
  width: 100%;
  position: relative;
  top: -3px;
  z-index: -10;
}


.outer-wrapper::-webkit-scrollbar {
  height: 5px;
}

.outer-wrapper::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 0px rgba(0, 0, 0, 0);
}

.outer-wrapper::-webkit-scrollbar-thumb {
  height: 5px;
  background-color: #d5ac68;
}

.outer-wrapper::-webkit-scrollbar-thumb:hover {
  background-color: #f1db9d;
}

.outer-wrapper::-webkit-scrollbar:vertical {
  display: none;
}

.inner-wrapper {
  display: flex;
  padding-bottom: 10px;
}

.pseudo-item {
  height: 300px;
  width: 369px;
  margin-right: 59px;
  flex-shrink: 0;
  background-color: gray;
}

.pseudo-item:nth-of-type(2n) {
  background-color: lightgray;
}


</style>
</head>
<body>

<% String logintype = (String) session.getAttribute("LoginType"); 
	List<Object[]> dashboard = (List<Object[]>)request.getAttribute("dashboard");
	List<Object[]> empfamilylist = (List<Object[]>)request.getAttribute("empfamilylist");
	Employee employee = (Employee )request.getAttribute("employee") ;
	List<Object[]> empchsslist = (List<Object[]>)request.getAttribute("empchsslist");
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();


%>

 <div class="col page">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS DASHBOARD</h5>
			</div>
			<div class="col-md-9 " >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">CHSS</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	
	<br>
 	<div class="card-body" >
		<form action="#" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="card" >
				<div class="card-body " >
					<div class="row" > 
						<%-- <div class="col-md-3 ">
							<button type="submit" class=" db-button w-100" formaction="CHSSApply.htm" >CHSS Apply</button>
						</div>
						<div class="col-md-3 ">
							<button type="submit" class=" db-button w-100" formaction="CHSSAppliedList.htm" >CHSS List</button>
						</div>
						<%if( logintype.equalsIgnoreCase("K") || logintype.equalsIgnoreCase("V") ){ %>
							<div class="col-md-3 ">
								<button type="submit" class=" db-button w-100" formaction="CHSSApprovalsList.htm" >CHSS Approvals</button>
							</div>

						<%} %> --%>
						
						
						<%if(dashboard!=null){  for(Object[] O:dashboard){%>							
							 <div class="col-md-3 ">
								<button type="submit" class=" db-button w-100" formaction="<%=O[1]%>" ><%=O[0]%></button>
							</div>
						<%}}%>

					</div>
				</div>
			</div>		
		</form>
		
	</div> 

 </div> 
 
 
 <div class="card profile-card-container" >

	<div class="row" >
		<div class="col-md-2">
			<div class="main" onclick="submitform('Y','<%=employee.getEmpId()%>')" data-toggle="tooltip" data-placement="bottom" title="Apply">
				<div class="a-box">
					<div class="img-container">
						<div class="img-inner">
							<div class="inner-skew">
								<img src="view/images/tharun.jpg">
							</div>
						</div>
					</div>
					<div class="text-container">
						<h3><%=employee.getEmpName() %> <span style="font-weight: 700;font-size: 13px;" > (Self)</span></h3>
					</div>
				</div>	
			</div>
			
			
		</div>
		
		<%for(Object[] obj : empfamilylist){ %>
		<div class="col-md-2">
			<div class="main" onclick="submitform('N','<%=obj[0]%>')" data-toggle="tooltip" data-placement="bottom" title="Apply"> 
				<div class="a-box">
					<div class="img-container">
						<div class="img-inner">
							<div class="inner-skew">
								<img src="view/images/user.png">
							</div>
						</div>
					</div>
					<div class="text-container">
						<h3><%=obj[1] %> <span style="font-weight: 700;font-size: 13px;text-transform: lowercase;" >(<%=obj[7] %>)</span></h3>
					</div>
				</div>	
			</div>  
		</div>
	 <%} %>
	
</div>


</div>



<br>

<div class="card table-card" >
	<div class="card-body " >				
		<form action="#" method="post" id="ClaimForm">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed" id="myTable" > 
								<thead>
									<tr>
										<td style="padding-top:5px; padding-bottom: 5px;">SN</td>
										<td style="padding-top:5px; padding-bottom: 5px;" >Claim No</td>
										<td style="padding-top:5px; padding-bottom: 5px;" >Patient Name</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Ailment</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Applied Date</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Claim Amount</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Admitted Amount</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Action</td>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
									for(Object[] obj : empchsslist){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[12] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[17] %></td>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
											<td style="padding-top:5px; padding-bottom: 5px;">-</td>
											<td style="padding-top:5px; padding-bottom: 5px;">-</td>
											<td style="padding-top:5px; padding-bottom: 5px;" class="editable-click"> <a class="font" href="Chss-Status-details.htm?chssapplyid=<%=obj[0]%>" target="_blank"  title="Click for Details." ><%=obj[18] %> </a></td>
											
											
											<td style="padding-top:5px; padding-bottom: 5px;">
												<%if(Integer.parseInt(obj[9].toString())==1 || Integer.parseInt(obj[9].toString())==3 || Integer.parseInt(obj[9].toString())==9 || Integer.parseInt(obj[9].toString())==11 || Integer.parseInt(obj[9].toString())==13 || Integer.parseInt(obj[9].toString())==7){ %>
													<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSAppliedDetails.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Edit">
														<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
													</button>	
												<%} %>
												
												<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSForm.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
													<i class="fa-solid fa-eye"></i>
												</button>	
												
												<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEmpDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
													<i style="color: #019267" class="fa-solid fa-download"></i>
												</button>
												<input type="hidden" name="isapproval" value="N">							
											</td>
										</tr>
									<%} %>
								</tbody>
							</table>
						</div>
					</form>
				</div>
		</div>		

<!-- 
  <div class="outer-wrapper">
    <div class="inner-wrapper">
      <div class="pseudo-item"></div>
      <div class="pseudo-item"></div>
      <div class="pseudo-item"></div>
      <div class="pseudo-item"></div>
      <div class="pseudo-item"></div>
      <div class="pseudo-item"></div>
      <div class="pseudo-item"></div>
      <div class="pseudo-item"></div>
    </div>
  </div>


 -->
<form action="CHSSApplyDetails.htm" method="post" id="myform" style="padding: 1rem">
	<input type="hidden" name="isself" id="isself" >
	<input type="hidden" name="patientid" id="patientid" >
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


<script>

function submitform(value,patientid){
	$('#isself').val(value)
	$('#patientid').val(patientid)
	$('#myform').submit();
}




$('#carousel-example').on('slide.bs.carousel', function (e) {
    /*
        CC 2.0 License Iatek LLC 2018 - Attribution required
    */
    var $e = $(e.relatedTarget);
    var idx = $e.index();
    var itemsPerSlide = 6;
    var totalItems = $('.carousel-item').length;
    console.log(totalItems)
    console.log(idx)
 
    if (idx >= totalItems-(itemsPerSlide-1)) {
        var it = itemsPerSlide - (totalItems - idx);
        
        console.log(it)
        
        for (var i=0; i<it; i++) {
            // append slides to end
            if (e.direction=="left") {
                $('.carousel-item').eq(i).appendTo('.carousel-inner');
            }
            else {
                $('.carousel-item').eq(0).appendTo('.carousel-inner');
            }
        }
    }
});
</script>
</body>
</html>
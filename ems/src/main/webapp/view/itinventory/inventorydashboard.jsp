<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="org.bouncycastle.util.Arrays"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<jsp:include page="../static/header.jsp"></jsp:include>
		<jsp:include page="../static/sidebar.jsp"></jsp:include>
		 
	</head>
	<style>
	.card-counter{
    box-shadow: 2px 2px 10px #DADADA;
    margin: 5px;
    padding: 20px 10px;
    background-color: #fff;
    height: 100px;
    border-radius: 5px;
    transition: .3s linear all;
  }

  .card-counter:hover{
    box-shadow: 4px 4px 20px #DADADA;
    transition: .3s linear all;
  }

  .card-counter.primary{
    background-color: #57C5B6;
    color: #FFF;
  }


  .card-counter.success{
    background-color: #EB6440;
    color: #FFF;
  }  

  .card-counter i{
    font-size: 5em;
    opacity: 0.2;
  }

  .card-counter .count-numbers{
    position: absolute;
    right: 35px;
    top: 20px;
    font-size: 1.5vw;
    display: block;
  }

  .card-counter .count-name{
    position: absolute;
    right: 35px;
    top: 65px;
    font-style: italic;
    text-transform: capitalize;
    opacity: 0.5;
    display: block;
    font-size: 20px;
  }
	
	hovereffect {
width:100%;
height:100%;
float:left;
overflow:hidden;
position:relative;
text-align:center;
cursor:default;
}

.hovereffect .overlay {
width:2%;
height:2%;
position:absolute;
overflow:hidden;
top:0;
left:0;
opacity:0;
background-color:rgba(0,0,0,0.5);
-webkit-transition:all .4s ease-in-out;
transition:all .4s ease-in-out
}

.hovereffect img {
display:block;
position:relative;
-webkit-transition:all .4s linear;
transition:all .4s linear;
}

 .hovereffect h2 {
text-transform:uppercase;
color:#fff;
text-align:center;
position:relative;
font-size:17px;
background:rgba(0,0,0,0.6);
-webkit-transform:translatey(-100px);
-ms-transform:translatey(-100px);
transform:translatey(-100px);
-webkit-transition:all .2s ease-in-out;
transition:all .2s ease-in-out;
padding:10px;
}

.hovereffect a.info {
text-decoration:none;
display:inline-block;
text-transform:uppercase;
color:#fff;
border:1px solid #fff;
background-color:transparent;
opacity:0;
filter:alpha(opacity=0);
-webkit-transition:all .2s ease-in-out;
transition:all .2s ease-in-out;
margin:50px 0 0;
padding:7px 14px;
}

.hovereffect a.info:hover {
box-shadow:0 0 5px #fff;
}

.hovereffect:hover img {
-ms-transform:scale(1.2);
-webkit-transform:scale(1.2);
transform:scale(1.2);
}

.hovereffect:hover .overlay {
opacity:1;
filter:alpha(opacity=100);
}

.hovereffect:hover h2,.hovereffect:hover a.info {
opacity:1;
filter:alpha(opacity=100);
-ms-transform:translatey(0);
-webkit-transform:translatey(0);
transform:translatey(0);
}

.hovereffect:hover a.info {
-webkit-transition-delay:.2s;
transition-delay:.2s;
}

 

	</style>
	<body>
	<%
	String EmpNo=(String)request.getAttribute("empNo");
	List<Object[]> EmployeeList=(List<Object[]>)request.getAttribute("EmployeeList");
	String DeclarationYear=(String)request.getAttribute("DeclarationYear");
	List<Object[]> InventoryCount=(List<Object[]>)request.getAttribute("InventoryDashBoardCount");
	int quantity=0;
	int item=0;
	int computer=0;
	for(Object[] obj:InventoryCount){ 
		computer=Integer.parseInt(obj[0].toString());
    quantity=quantity+(Integer.parseInt(obj[0].toString())+Integer.parseInt(obj[1].toString())+Integer.parseInt(obj[2].toString())+Integer.parseInt(obj[3].toString())
   +Integer.parseInt(obj[4].toString())+Integer.parseInt(obj[5].toString())+Integer.parseInt(obj[6].toString())+Integer.parseInt(obj[7].toString()) +Integer.parseInt(obj[8].toString()));

    if(Integer.parseInt(obj[0].toString())!=0){%><% item++; %><%}  if(Integer.parseInt(obj[1].toString())!=0){%><% item++; %><%} 
	if(Integer.parseInt(obj[2].toString())!=0){%><%item++; %><%}  if(Integer.parseInt(obj[3].toString())!=0){%><%item++; %><%}
	if(Integer.parseInt(obj[4].toString())!=0){%><%item++; %><%}   if(Integer.parseInt(obj[5].toString())!=0){%><%item++; %><%}
	if(Integer.parseInt(obj[6].toString())!=0){%><%item++; %><%}  if(Integer.parseInt(obj[7].toString())!=0){%><%item++; %><%}
	if(Integer.parseInt(obj[8].toString())!=0){%><%item++; %><%}}
    
    %>

	
	
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
		<div class="row">
				<div class="col-md-5">
					<h5 style="padding-top: 0.5rem">IT INVENTORY DASHBOARD </h5>
				</div>
			  	<div class="col-md-7" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">INVENTORY</li>
				  </ol>
				</nav>
			</div>		
			</div>
	</div>	
	
	<div class="card-header" style="height: 4rem">
				<form  action="ITInventoryDashboard.htm" method="POST" id="form">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	              
					<div class="row justify-content-right">
						<div class="col-7">
						<div class="col-md-4 d-flex justify-content-center"  >
			        </div>
				 </div>
						<div class="col-2" style="margin-left: -20%; font-color: black;">
							<h6 style="color:#000000;" >Employee Name : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -5%">
						  <select class="form-control select2" onchange="this.form.submit();" name="EmpName"  required="required" style="width:300px;" id="empno">
						  <option value="" selected="selected" >All</option>	
                              		<%for(Object[] obj:EmployeeList) {%>
                              	
					           <option value="<%=obj[0]%>" <%if(obj[0].toString().equals(EmpNo)){ %>selected <%} %>><%=obj[1] %></option>
					           <%} %>
					           
					           </select><label
								class="input-group-addon btn" for="testdate"></label>	
						</div>


						<div class="col-2" style="margin-left: 17%">
							<h6 style="color:#000000;">Declaration Year : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -6%">
							<input type="text" style="width: 146%; background-color: white;"
								class="form-control input-sm mydate"
								onchange="this.form.submit()"  
								id="datepicker1" name="DeclarationYear"
								required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				
	                  <div class="container" >
							 <div class="row">
							   <div class="col-md-3">
							      <div class="card-counter primary">
							        <i class="fa fa-archive"></i>
							        
							        <span class="count-numbers"><%if(item!=0){%><%=item %><%}else{ %>0<%} %></span>
							        <%item=0; %>
							        <span class="count-name">No Of Items</span>
							      </div>
							    </div>
							  
							     <div class="col-md-3">
							      <div class="card-counter success">
							        <i class="fa fa-database"></i>
							        <span class="count-numbers"><%if(quantity!=0){%><%=quantity %><%} else{ %>0<%} %></span>
							        <span class="count-name">Quantity</span>
							      </div>
							    </div>
							    
							  
				          </div>
					</div>
	
<hr>
	
	
	<div class="row">
	<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
  <div style=" margin-top:0px; margin-left:23px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #049F7D ;">COMPUTER</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/computer.png" class="img-responsive img-rounded" style="height:100px; ">
   </div>
   <div style=" margin-top:10px; margin-left:10px;" align="left">
 <span style="margin-left:42px;color: #049F7D;font-weight: bold;"><%=computer %></span>
 </div>
  </div>
  
</div>

</div>
	
	
	<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
  <div style=" margin-top:0px; margin-left:34px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #EBB02D ;">LAPTOP</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/laptop-screen.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
  </div>
  </div>
  
</div>

</div>

<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
  <div style=" margin-top:0px; margin-left:32px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #569DAA ;">PENDRIVE</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/pendrive.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
  </div>
  </div>
  
</div>
</div>


<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
    <div style=" margin-top:0px; margin-left:32px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #146C94 ;">PRINTER</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/printer.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
  </div>
  </div>
  
</div>
</div>

<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
    <div style=" margin-top:0px; margin-left:22px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #E74646 ;">TELEPHONE</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/telephone.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
  </div>
  </div>
  
</div>
</div>

</div>

<div class="row">

<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
  <div style=" margin-top:0px; margin-left:16px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #D14D72 ;">FAX-MACHINE</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/fax-machine.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
  </div>
  </div>
  
</div>
</div>

<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
  <div style=" margin-top:0px; margin-left:30px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #FF8400 ;">SCANNER</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/scanner.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
  </div>
  </div>
  
</div>
</div>
 
<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
  <div style=" margin-top:0px; margin-left:15px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #2C3333 ;">XEROX MACHINE</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/copy-machine.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
  </div>
  </div>
  
</div>
</div>

<div class="col-md-2 hovereffect" >
  <div class="panel panel-default" >
  <div class="panel-body" >
  <div style=" margin-top:0px; margin-left:15px;" align="left">
 <h6 style=" font-size: small; font-weight: bold; color: #E86A33 ;">MISCELLANEOUS</h6>
 </div>
   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
   <img src="./webresources/assortment.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
  </div>
  </div>
  
</div>
</div>

 </div>
 
 
 
 
</body>	
<script>

  $(document).ready(function(){
	 
	$("#datepicker1").datepicker({
    	
    	autoclose: true,
    	 format: 'yyyy',
    		 viewMode: "years", 
    		    minViewMode: "years"
    });
	
 <% if(DeclarationYear!=null){%>
  document.getElementById("datepicker1").value = <%=DeclarationYear %>
  <%} %>
    
    $('#datepicker1').change(function () {
    	
       $('#form').submit();
        
    }); 
  
});
  
  $(document).ready(function(){
	  
	  $('#empno').change(function () {
	    	
	       $('#form').submit();
	        
	    });  
	  
  });
  
  

</script>
	
</html>
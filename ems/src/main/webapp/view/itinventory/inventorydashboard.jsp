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
    box-shadow: 5px 2px 10px #DADADA;
    margin: 5px;
    padding: 20px 10px;
    background-color: #fff;
    height: 100px;
    
    border-radius: 5px;
   /*  transition: .3s linear all; */
  }

  .card-counter:hover{
    box-shadow: 4px 4px 20px #DADADA;
    transition: .3s linear all;
  }

  .card-counter.primary{
    background-color:  #66347F;    /* #57C5B6; */
    color: #FFF;
  }


  .card-counter.success{
    background-color: #EB6440;
    color: #FFF;
  } 
  
   .card-counter.green{
    background-color: #539165;
    color: #FFF;
  } 

 .card-counter.red{
    background-color: #D14D72;
    color: #FFF;
  } 

.card-counter.blue{
    background-color: #19A7CE;
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
    font-size: 1.8vw;
    display: block;
  }

  .card-counter .count-name{
    position: absolute;
    right: 35px;
    top: 65px;
    font-style: italic;
    text-transform: capitalize;
    opacity: 1.0;
    display: block;
    font-size: 20px;
  }

  .container {
    
    
  width: 150%
    
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
	
	String LoginType   = (String)request.getAttribute("LoginType");
	String EmpName     = (String)session.getAttribute("EmpName");
	String EmpNo=(String)request.getAttribute("empno");
	List<Object[]> EmployeeList=(List<Object[]>)request.getAttribute("EmployeeList");
	String DeclarationYear=(String)request.getAttribute("DeclarationYear");
	List<Object[]> InventoryCount=(List<Object[]>)request.getAttribute("InventoryDashBoardCount");
	int quantity=0;
	int item=0;
	int computer=0;
	int laptop=0;
	int pendrive=0;
	int printer=0;
	int telephone=0;
	int fax=0;
	int scanner=0;
	int xerox=0;
	int miscellaneous=0;
	int declared=0;
	int returned=0;
	int approved=0;
	for(Object[] obj:InventoryCount){
		
		computer=Integer.parseInt(obj[0].toString());
		laptop=Integer.parseInt(obj[1].toString());
		pendrive=Integer.parseInt(obj[2].toString());
		printer=Integer.parseInt(obj[3].toString());
		telephone=Integer.parseInt(obj[4].toString());
		fax=Integer.parseInt(obj[5].toString());
		scanner=Integer.parseInt(obj[6].toString());
		xerox=Integer.parseInt(obj[7].toString());
		miscellaneous=Integer.parseInt(obj[8].toString());
		declared=Integer.parseInt(obj[9].toString());
		returned=Integer.parseInt(obj[10].toString());
		approved=Integer.parseInt(obj[11].toString());
		
		quantity=computer+laptop+pendrive+printer+telephone+fax+scanner+xerox+miscellaneous;
				
		if(computer!=0){%><% item++; %><%}  if(laptop!=0){%><% item++; %><%} 
		if(pendrive!=0){%><%item++; %><%}  if(printer!=0){%><%item++; %><%}
		if(telephone!=0){%><%item++; %><%}   if(fax!=0){%><%item++; %><%}
		if(scanner!=0){%><%item++; %><%}  if(xerox!=0){%><%item++; %><%}
		if(miscellaneous!=0){%><%item++; %><%}}
		
	
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
						 <% if(LoginType.toString().equals("A")){%>
	                     <select class="form-control select2" onchange="this.form.submit();" name="empNo"  required="required" style="width:300px;" id="EmpName">
	                     <option value="0"  >All</option>
						  <% for(Object[] emp:EmployeeList){%>
							  
                              <option value="<%=emp[0]%>" <%if(emp[0].toString().equals(EmpNo)){ %>selected <%} %>><%=emp[1] %></option>
                              	
					      <%} %>
					      </select>
					      <%}else{%>
						
						  <% for(Object[] emp:EmployeeList){
					        	  if(emp[0].equals(EmpNo)){ %>	  
					      <input type="text" class="form-control " style="width: 300px;" readonly="readonly" value=<%=emp[1] %>>
						<%}} %> 
						<%} %>
						 </div>
						 


						<div class="col-2" style="margin-left: 17%">
							<h6 style="color:#000000;">Declaration Year : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -6%">
							<input type="text" style="width: 146%; background-color: white;"
								class="form-control input-sm mydate"
								onchange="this.form.submit()"  
								id="datepicker1" name="DeclarationYear"
								required="required"> 
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
						
						<div class="row">
				   <div class="col-md-11">
	                  <div class="container" >
	                  
							 <div class="row">
							   <div class="col-md-2" >
							      <div class="card-counter primary">
							        <i class="fa fa-archive"></i>
							      
							        <span class="count-numbers"><%if(item!=0){%><%=item %><%}else{ %>0<%} %></span>
							        
							        <span class="count-name">No of Items</span>
							      </div>
							    </div>
							  
							     <div class="col-md-2">
							      <div class="card-counter success">
							        <i class="fa fa-database"></i>
							        <span class="count-numbers"><%if(quantity!=0 ){%><%=quantity %><%} else{ %>0<%} %></span>
							        <span class="count-name">Quantity</span>
							      </div>
							  </div>
							  
							    <%--  <div class="col-md-2">
							      <div class="card-counter blue">
							        <i class="fa fa-id-card"></i>
							        
							        <span class="count-numbers"><%if(declared!=0 ){%><%=declared %><%} else{ %>0<%} %></span>
							        
							        <span class="count-name">Declared</span>
							      </div>
							    </div>
							    
							     <div class="col-md-2">
							      <div class="card-counter red">
							        <i class="fa fa-refresh"></i>
							        
							        <span class="count-numbers"><%if(returned!=0 ){%><%=returned %><%} else{ %>0<%} %></span>
							       
							        <span class="count-name">Returned</span>
							      </div>
							    </div>
							  
							  <div class="col-md-2">
							      <div class="card-counter green">
							        <i class="fa fa-check-square"></i>
							        
							        <span class="count-numbers"><%if(approved!=0 ){%><%=approved %><%} else{ %>0<%} %></span>
							        
							        <span class="count-name">Approved</span>
							      </div>
							    </div> --%>
							  
				          </div>
					</div>
					 </div> 
					 </div>
	
<hr>
	
	
	<div class="row"  style="margin-left:35px;" >
	<div class="col-md-2 hovereffect" >
    <div class="panel panel-default" >
    <div class="panel-body" >
	 <div style=" margin-top:0px; margin-left:10px;" align="left">
	 <h6 style=" font-size: 15px; font-weight: bold; color: #049F7D ;">COMPUTER = <span  style="font-size: 20px;"><%=computer %></span></h6>
	</div>
	  <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
	   <img src="./webresources/computer.png" class="img-responsive img-rounded" style="height:100px; ">
	   </div>
	   <div style=" margin-top:10px; margin-left:10px;" align="left">
	 <span style="margin-left:42px;color: #049F7D;font-weight: bold;"></span>
	 </div>
	 </div>
   </div>
</div>
	
	
	<div class="col-md-2 hovereffect" >
	  <div class="panel panel-default" >
	    <div class="panel-body" >
	      <div style=" margin-top:0px; margin-left:22px;" align="left">
	 <h6 style=" font-size:15px; font-weight: bold; color: #EBB02D ;">LAPTOP = <span  style="font-size: 20px;"><%=laptop %></span></h6>
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
	  <div style=" margin-top:0px; margin-left:15px;" align="left">
	 <h6 style=" font-size: 15px; font-weight: bold; color: #569DAA ;">PENDRIVE = <span  style="font-size: 20px;"><%=pendrive %></span></h6>
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
	    <div style=" margin-top:0px; margin-left:16px;" align="left">
	 <h6 style=" font-size: 15px; font-weight: bold; color: #146C94 ;">PRINTER = <span  style="font-size: 20px;"><%=printer %></span></h6>
	 </div>
	   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
	   <img src="./webresources/printer.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
	  </div>
	  </div>
	</div>
  </div>
 </div>

 <div class="row" style="margin-left:35px;">

	<div class="col-md-2 hovereffect" >
	  <div class="panel panel-default" >
	  <div class="panel-body" >
	    <div style=" margin-top:0px; margin-left:10px;" align="left">
	 <h6 style=" font-size: 15px; font-weight: bold; color: #E74646 ;">TELEPHONE = <span  style="font-size: 20px;"><%=telephone %></span></h6>
	 </div>
	   <div class="thumbnail " style="margin-top: 10px;margin-left:10px;">
	   <img src="./webresources/telephone.png" class="img-responsive img-rounded" style="height:100px; "><br><br><br>
	  </div>
	  </div>
	</div>
	</div>

 <div class="col-md-2 hovereffect" >
	  <div class="panel panel-default" >
	  <div class="panel-body" >
	  <div style=" margin-top:0px; margin-left:0px;" align="left">
	 <h6 style=" font-size: 15px; font-weight: bold; color: #D14D72 ;">FAX-MACHINE = <span style="font-size: 20px;"><%=fax %></span></h6>
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
	  <div style=" margin-top:0px; margin-left:17px;" align="left">
	 <h6 style=" font-size: 15px; font-weight: bold; color: #FF8400 ;">SCANNER = <span  style="font-size: 20px;"><%=scanner %></span></h6>
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
	  <div style=" margin-top:0px; margin-left:0px;" align="left">
	 <h6 style=" font-size: 15px; font-weight: bold; color: #2C3333 ;">XEROX MACHINE = <span style="font-size: 20px;"><%=xerox %></span></h6>
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
	  <div style=" margin-top:-109px; margin-left:15px;" align="left">
	 <h6 style=" font-size: 15px; font-weight: bold; color: #E86A33 ;">MISCELLANEOUS = <span  style="font-size: 20px;"><%=miscellaneous %></span></h6>
	 </div>
	   <div class="thumbnail " style="margin-top: 10px;margin-left:29px;">
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
    
    $('.count-numbers').each(function(){
        $(this).prop('Counter',0).animate({
            Counter: $(this).text()
        },{
            duration: 1000,
            easing: 'swing',
            step: function (now){
                $(this).text(Math.ceil(now));
            }
        });
    });
 
}); 

</script>
	
</html>
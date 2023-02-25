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
	<style>
	
	.highcharts-figure,	.highcharts-data-table table {
	    min-width: 310px;
	    max-width: 800px;
	}

	.highcharts-data-table table {
	    font-family: Verdana, sans-serif;
	    border-collapse: collapse;
	    border: 1px solid #ebebeb;
	    margin: 10px auto;
	    text-align: center;
	    width: 100%;
	    max-width: 500px;
	}
	
	.highcharts-data-table caption {
	    padding: 1em 0;
	    font-size: 1.2em;
	    color: #555;
	}
	
	.highcharts-data-table th {
	    font-weight: 600;
	    padding: 0.5em;
	}
	
	.highcharts-data-table td,
	.highcharts-data-table th,
	.highcharts-data-table caption {
	    padding: 0.5em;
	}
	
	.highcharts-data-table thead tr,
	.highcharts-data-table tr:nth-child(even) {
	    background: #f8f8f8;
	}
	
	.highcharts-data-table tr:hover {
	    background: #f1f7ff;
	}
	
	/* counter css */
	
	.counter{
    color: #628900;
    background: linear-gradient(to bottom, #628900 49%, transparent 50%);
    font-family: 'Poppins', sans-serif;
    text-align: center;
    /* width: 200px; */
   /*  height: 200px; */
    padding: 19px 20px 18px;
    margin: 0 auto;
    border: 18px solid #628900;
    border-radius: 100% 100%;
    box-shadow: inset 0 8px 10px rgba(0, 0, 0, 0.3);
	}
	.counter .counter-value{
	    color: #fff;
	    font-size: 23px; 
	    font-weight: 600;
	    display: block;
	    margin: 0 0 16px;
	}
	.counter h3{
	    font-size: 14px;
	    font-weight: 600;
	    text-transform: uppercase;
	    margin: 0;
	}
	.counter.blue{
	    color: #187498;
	    background: linear-gradient(to bottom, #187498 49%, transparent 50%);
	    border-color: #187498;
	}
	.counter.purple{
	    color: #6E3274;
	    background: linear-gradient(to bottom, #6E3274 49%, transparent 50%);
	    border-color: #6E3274;
	}
	.counter.green{
	    color: #36AE7C;
	    background: linear-gradient(to bottom, #36AE7C 49%, transparent 50%);
	    border-color: #36AE7C;
	}
	@media screen and (max-width:990px) {
	    .counter{ margin-bottom: 40px; }
	}

	@media screen and (min-width: 1151px) and (max-width : 1500px){
		.counter {
			width : 150px;
			height: 150px;
		}
	}
	
	@media screen and (min-width: 1501px) {
		.counter {
			width : 180px;
			height: 180px;
			padding: 49px 19px 19px;
		}
		
		.counter h3{
			font-size: 20px
		}
		
		.counter .counter-value {
		    font-size: 30px;
		    margin: -27px 0 21px;
		}
	}

	@media screen and (max-width : 1150px){
		.counter h3{
			font-size: 11px
		}
		.counter {
			width : 135px;
			height: 135px;
		}
		
		.counter .counter-value{
			font-size: 17px;
			 margin: 0 0 18px;
		}
	}

	
	/* second card counter */
	
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
    background-color: #187498;
    color: #FFF;
  }

  .card-counter.danger{
    background-color: #ef5350;
    color: #FFF;
  }  

  .card-counter.success{
    background-color: #36AE7C;
    color: #FFF;
  }  

  .card-counter.info{
    background-color: #26c6da;
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
    font-size: 16px;
  }
  
  .toggle{
  	margin: 0px 35px !important;
  }
  

	@media (max-width: 600px) {
	    .highcharts-figure,
	    .highcharts-data-table table {
	        width: 100%;
	    }
	
	}
	
	#container, #container3, #container4, #container-speed {
    height: 300px;
    min-width: 310px;
    max-width: 800px;
}
  
  .violet{
  	background-color: #533E85;
  	color:white;
  }
  .nav-pills .nav-link.active, .nav-pills .show>.nav-link {
    color: #fff;
    background-color: #750550;
}
  
</style>

</head>

<body  >
	
	
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
			
			<div class="row">
				<div class="col-md-5">
					<h5 style="padding-top: 0.5rem">IT HELPDESK DASHBOARD </h5>
				</div>
			  	<div class="col-md-7" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">HELPDESK</li>
				  </ol>
				</nav>
			</div>		
			</div>
			
			
	</div>	



</html>
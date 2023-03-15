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
	.counter.yellow{
	    color: #E1C16E;
	    background: linear-gradient(to bottom, #E1C16E 49%, transparent 50%);
	    border-color: #E1C16E;
	}
	.counter.red{
	    color: 	#FF6347;
	    background: linear-gradient(to bottom, 	#FF6347 49%, transparent 50%);
	    border-color: #FF6347;
	
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
			font-size: 17px
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
<body>
	
	<%
      String Fromdate=(String)request.getAttribute("FromDate");
      String Todate=(String)request.getAttribute("ToDate"); 
      Object[] TotalCountData = (Object[])request.getAttribute("countdata");
      String LoginType=(String)request.getAttribute("LoginType");
      List<Object[]> graphdata  = (List<Object[]> )request.getAttribute("graphdata");
      List<Object[]> piechartdata  = (List<Object[]> )request.getAttribute("piechartdata");
      double total=0;
	  for(Object[] obj:piechartdata){
    	 total=total+(Double.parseDouble(obj[4].toString()));
	   }
	%>
	
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


 <div class="card dashboard-card" > 
	<div align="center">
			<%String ses=(String)request.getParameter("result"); 
			String ses1=(String)request.getParameter("resultfail");
			if(ses1!=null){ %>
				<div class="alert alert-danger" role="alert">
					<%=ses1 %>
				</div>
			<%}if(ses!=null){ %>
				<div class="alert alert-success" role="alert">
					<%=ses %>
				</div>
			<%} %>
		</div>
		
		<div class="card" >
			<div class="card-header" style="height: 4rem">
				<form  action="ITDashboard.htm " method="POST" id="myform">
				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	              
					<div class="row justify-content-right">
						<div class="col-7">
						<div class="col-md-4 d-flex justify-content-center"  >
			        </div>
				 </div>
						<div class="col-2" style="margin-left: 7%; font-color: black;">
							<h6 style="color:#000000;" >From Date : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -9%">
							<input type="text"
								style="width: 146%; background-color: white; text-align: left;"
								class="form-control input-sm"
								onchange="this.form.submit()" 
								<%if(Fromdate!=null){%>
								value="<%=Fromdate%>" <%} %>
								 id="fromdate" name="FromDate"
								required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>


						<div class="col-2" style="margin-left: 1%">
							<h6 style="color:#000000;">To Date : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -11%">
							<input type="text" style="width: 146%; background-color: white;"
								class="form-control input-sm mydate"
								onchange="this.form.submit()"  <%if(Todate!=null){ %>  value="<%=Todate%>"<%}%> 
								id="todate" name="ToDate"
								required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		
		
		<div class="card-body " style="padding-top: 6px;max-height:50rem;">
		<div class="container-fluid" >
		<div class="row" >
					  <div class="col-md-10">
					   
							<div class="row">
							    <div class="col-md-4" style="max-width:19.333%;">
							            <div class="counter purple">
							           <%--  <%totalcount=(Long.parseLong(TotalCountData[0].toString()))+(Long.parseLong(TotalCountData[1].toString()))+(Long.parseLong(TotalCountData[4].toString())); %> --%>
							               <span class="counter-value"><%=TotalCountData[0] %></span>
							                <h3>TOTAL</h3>
							            </div>
							        </div>
							      
							        <%if(LoginType.toString().equalsIgnoreCase("A") || LoginType.toString().equalsIgnoreCase("U") ) {%> 
							        <div class="col-md-4" style="max-width:19.333%;">
							           <div class="counter blue" style="cursor: pointer;" >
							            <span class="counter-value"> <%=TotalCountData[1] %></span>
							                <h3>PENDING</h3>
							            </div>
							        </div>
							       <%} %>
							        <div class="col-md-4" style="max-width:19.333%;">
							            <div class="counter green">
							                <span class="counter-value"><%=TotalCountData[2] %></span>
							                <h3>ASSIGNED</h3>
							            </div>
							        </div>
							         <%if(LoginType.toString().equalsIgnoreCase("CE")){%>
							        <div class="col-md-4" style="max-width:19.333%;">
							            <div class="counter yellow">
							                <span class="counter-value"><%=TotalCountData[4] %></span>
							                <h3>RETURNED</h3>
							             </div>
							        </div>
							        <%} %>
							        <div class="col-md-4" style="max-width:19.333%;">
							            <div class="counter red">
							                <span class="counter-value"><%=TotalCountData[5] %></span>
							                <h3>CLOSED</h3>
							            </div>
							        </div>
						   </div>
						 </div>
					  </div>
					    	
					   <hr>
					  
					    <div class="row">
					   		<div class="col-md-6">
					   			 <div id="container" style="display:block;" ></div>
							     
					   		</div>
					   		<div class="col-md-6">
					   			 <div id="container4"  style="display: block"></div> 
							   
					   		</div>
					   </div>
                    </div>
			 </div>
	    </div> 
	 </div>

<script type="text/javascript">

/* Counts Graph */	

	 Highcharts.chart('container', {
		
		   chart: {
		        type: 'column',
		    },
	    title: {
	        text: 'Category'
	    },
	    
	    xAxis: {
	         categories: [ <% for (Object[]  obj : graphdata ) { %>   '<%=obj[0]%>' ,   <%}%>  ] 
	   
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Count'
	        }
	    },
	    labels: {
	        /*  items: [{
	            html: 'Amount Settled',
	            style: {
	                left:'465px',
	                top: '0px',
	                color: ( // theme
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'black' ,
	                fontSize:'10px'
	                
	            }
	        }]  */
	   },
	     colors: [
	        '#3371FF',
	        '#58FF33',
	    	'#FF33FC',
	    ], 
	      series: [{
	        type: 'column',
	        name: 'Pending',
	        data: [ <% for (Object[]  obj : graphdata ) { %>   <%=obj[1]%> ,   <%}%> ] 
	    },  {
	        type: 'column',
	        name: 'Assigned',
	        data: [ <% for (Object[]  obj : graphdata ) { %>   <%=obj[2]%> ,   <%}%>  ]
	    } , {
	   
	        type: 'column',
	        name: 'Closed',
	        data: [ <% for (Object[]  obj : graphdata ) { %>   <%=obj[3]%> ,   <%}%>  ]
	    }
	    
	    ],
	    
	    credits: {
            enabled: false
        },
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                // Make the labels less space demanding on mobile
                chartOptions: {
                    xAxis: {
                        labels: {
                            formatter: function () {
                                return this.value.charAt(0);
                            }
                        }
                    },
                    yAxis: {
                        labels: {
                            align: 'left',
                            x: 0,
                            y: -2
                        },
                        title: {
                            text: ''
                        }
                    }
                }
            }]
        }
	});  
 


	/********************** Amount Gauge Unit ***********************************/	



	/* ****************************************** Pie Chart Amount Graph *************************************************** */
	
	// Radialize the colors
	/* Highcharts.setOptions({ */
		
		/* colors: ['#50B432', '#ED561B', '#DDDF00', '#24CBE5'] */
	   /*  colors: Highcharts.map(Highcharts.getOptions().colors, function (color) { */
	       /*  return {
	            radialGradient: {
	                cx: 0.5,
	                cy: 0.3,
	                r: 0.7
	            },
	            stops: [
	                [0, color],
	                [1, Highcharts.color(color).brighten(-0.3).get('rgb')] // darken
	            ]
	        }; */
	   /*  }) */
	/* }); */

	// Build the chart
	Highcharts.chart('container4', {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie',

	    },
	    title: {
	        text: 'Category'
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b> {point.y:,.2f}&#37</b>'
	    },
	    
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: true,
	                format: '<b>{point.name}</b>: {point.y:,.2f}&#37',
	                connectorColor: 'silver'
	                
	            },
	            colors: [
	            	      '#ECFF33',
	            	      '#4CFF33',
	            	      '#F033FF',
	            	      '#334FFF',
	            	      '#33FFFF',
	  	                  '#FF3352',
	  	                  '#33FFAC',
	  	                  '#273746'
	            	   
	              ],
	        }
	    },

	    series: [{
	        name: 'Percentage',
	        data: [
		        
	        	
	        	<% int j=0;{
	        	  for(Object[] obj : piechartdata ) { 
	        	  Double no=((((Double.parseDouble(obj[4].toString()))/total)*100));  %>
	        	{ 
		        	name: '<%=obj[0] %>',
		        	  y:<%=(Math.round(no * 100.0)/100.0)%>,
		                <%-- color: Highcharts.getOptions().colors[<%=j%>]    --%>
		        }
		        ,
		       <%}}%>
		        
		        ],
	    }],
	    credits: {
            enabled: false
        },
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                // Make the labels less space demanding on mobile
                chartOptions: {
                    xAxis: {
                        labels: {
                            formatter: function () {
                                return this.value.charAt(0);
                            }
                        }
                    },
                    yAxis: {
                        labels: {
                            align: 'left',
                            x: 0,
                            y: -2
                        },
                        title: {
                            text: ''
                        }
                    }
                }
            }]
        }
	});


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

</html>
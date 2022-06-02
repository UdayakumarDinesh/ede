<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>CHSS</title>
		<jsp:include page="../static/header.jsp"></jsp:include>
	<style>
			.highcharts-figure,
	.highcharts-data-table table {
	    min-width: 310px;
	    max-width: 800px;
	    margin: 1em auto;
	}
	
	#container {
	    height: 400px;
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
	    color: #36AE7C;
	    background: linear-gradient(to bottom, #36AE7C 49%, transparent 50%);
	    font-family: 'Poppins', sans-serif;
	    text-align: center;
	    width: 175px;
	    height: 175px;
	    padding: 12px 15px 35px;
	    margin: 0 auto;
	    border: 18px solid #36AE7C;
	    border-radius: 100% 100%;
	    box-shadow: inset 0 8px 10px rgba(0, 0, 0, 0.3);
	}
	.counter .counter-value{
	    color: #fff;
	    font-size: 30px;
	    font-weight: 600;
	    display: block;
	    margin: 0 0 25px;
	}
	.counter h3{
	    font-size: 18px;
	    font-weight: 600;
	    text-transform: uppercase;
	    margin: 0;
	}
	.counter.red{
	    color: #7D1E6A;
	    background: linear-gradient(to bottom, #7D1E6A 49%, transparent 50%);
	    border-color: #7D1E6A;
	}
	.counter.orange{
	    color: #fb8603;
	    background: linear-gradient(to bottom, #fb8603 49%, transparent 50%);
	    border-color: #fb8603;
	}
	.counter.blue{
	    color: #187498;
	    background: linear-gradient(to bottom, #187498 49%, transparent 50%);
	    border-color: #187498;
	}
	@media screen and (max-width:990px) {
	    .counter{ margin-bottom: 40px; }
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
    font-size: 32px;
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
    font-size: 18px;
  }
  
  .toggle{
  	margin: 0px 35px !important;
  }
	
	</style>

	</head>

	<body>
	<%	
		List<Object[]> emplogintypelist     = (List<Object[]> )session.getAttribute("emplogintypelist");
		String logintype   = (String)session.getAttribute("LoginType");
		String Fromdate=(String)request.getAttribute("Fromdate");
		String Todate=(String)request.getAttribute("Todate"); 
		Object[] TotalCountData = (Object[])request.getAttribute("countdata");
		List<Object[]> graphdata  = (List<Object[]> )request.getAttribute("graphdata");
	
	%>
	
	<div class="card-header page-top" style="padding: 0.25rem 1.25rem;">
		<div class="row">
			<div class="col-md-3">
				<h5 style="padding-top: 0.5rem">MAIN DASHBOARD </h5>
			</div>
			<div class="col-md-9">
					<form action="EmpLogitypeChange.htm" method="post" style="float: right;">
							
								<b>Login As : &nbsp;</b> 
								<select class="form-control select2" name="logintype" onchange="this.form.submit();" style="margin-top: -5px;width: 200px;">
									<%for(Object[] login:emplogintypelist){ %>
										<option value="<%=login[0]%>" <%if(logintype.equalsIgnoreCase(login[0].toString())){ %>selected <%} %>><%=login[1]%></option>
									<%} %>
								</select>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							
					</form> 
				
			</div>
		</div>
	</div>	


	<div class="card dashboard-card" >
		<div class="card-body " >

			<div class="container-fluid">
			  <div class="row">
			  
			    <div class="col">
			    	
					<div class="row">
				        <div class="col-md-3 col-sm-6">
				            <div class="counter red">
				                <span class="counter-value"><%=TotalCountData[2] %></span>
				                <h3>Total</h3>
				            </div>
				        </div>
				        <div class="col-md-3 col-sm-6">
				            <div class="counter blue">
				                <span class="counter-value"><%=TotalCountData[0] %></span>
				                <h3>Pending</h3>
				            </div>
				        </div>
				        <div class="col-md-3 col-sm-6">
				            <div class="counter ">
				                <span class="counter-value"><%=TotalCountData[1] %></span>
				                <h3>Approved</h3>
				            </div>
				        </div>
				    </div>
				    <hr>
				    <br>
				</div>
				
			    <div class="col">
			    
			    	<div class="container">
					    <div class="row justify-content-end" >
					    	
					    	<form class="form-inline" action="MainDashBoard.htm" method="post" id="dateform" >

						    	<label style=" font-weight: 800;margin-top: 5px;margin-left: 5px"> Financial Year : &nbsp; </label>
								<input  class="form-control date"  data-date-format="yyyy-mm-dd" id="datepicker1" name="FromDate"  required="required"  style="width: 120px;">
								<input  class="form-control date"  data-date-format="yyyy-mm-dd" id="datepicker2" name="ToDate"  readonly	 required="required"  style="width: 120px;">
	
				    			<input type="checkbox"  checked data-toggle="toggle" data-width="100"  data-onstyle="success" data-offstyle="primary"  data-on=" <i class='fa-solid fa-user'></i>&nbsp;&nbsp; Self" data-off="<i class='fa-solid fa-industry'></i>&nbsp;&nbsp; Unit" >
			    				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
			    			</form>	
			    				
			    		</div>
			    		<br>
			    	</div>
			    	
			    	<div class="container" style="margin-bottom: 22px">
					    <div class="row">
					    <div class="col-md-6">
					      <div class="card-counter primary">
					        <i class="fa fa-code-fork"></i>
					        <span class="count-numbers">&#8377;50,839</span>
					        <span class="count-name">Amount Claimed</span>
					      </div>
					    </div>
		
					    <div class="col-md-6">
					      <div class="card-counter success">
					        <i class="fa fa-database"></i>
					        <span class="count-numbers">&#8377;28,456</span>
					        <span class="count-name">Amount Settled</span>
					      </div>
					    </div>
					  </div>
					  
					</div>
					
					<hr>
					
			    </div>
			    
			    <div class="w-100"></div>
			    
			    <div class="col"><figure class="highcharts-figure">
					    <div id="container"></div>
					</figure>
				</div>
					
			    <div class="col">
			    	<figure class="highcharts-figure">
    					<div id="container2"></div>
					</figure>
			    </div>
			    
			  </div>
			</div>
	
		</div>
	</div>
	 
	
	
<script type="text/javascript">
	
/* Counts Graph */	
	
/* 	Highcharts.chart('container', {
    chart: {
        type: 'column'
    },
    title: {
        text: 'Claims for the Financial Year 2022-23'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: [
        	'Total',
            'Self',
            'Mem1',
            'Mem2',
            'Mem3',
            'Mem4',
           
        ],
        crosshair: true
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Amount'
        }
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    colors: [
        '#187498',
        '#36AE7C',
    ],
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'top',
        x: -40,
        y: 90,
        floating: true,
        borderWidth: 1,
        backgroundColor:
            Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF',
        shadow: true
    },
    series: [{
        name: 'Applied',
        data: [29,12, 5, 4, 5, 3]

    }, {
        name: 'Approved',
        data: [17,6, 1, 3, 5, 2]

    }],
    credits: {
        enabled: false
    },
});  */


	Highcharts.chart('container', {
	    title: {
	        text: 'Analysis'
	    },
	    
	    xAxis: {
	         categories: [ <% for (Object[]  obj : graphdata ) { %>   '<%=obj[4]%>' ,   <%}%>   ] 
	   
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Count'
	        }
	    },
	    labels: {
	        items: [{
	            html: 'Total Amount Settled',
	            style: {
	                left:'570px',
	                top: '0px',
	                color: ( // theme
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'black' 
	                
	            }
	        }]
	    },
	    colors: [
	        '#187498',
	        '#36AE7C',
	    ],
	    series: [{
	        type: 'column',
	        name: 'Pending',
	        data: [ <% for (Object[]  obj : graphdata ) { %>   <%=obj[6]%> ,   <%}%>   ] 
	    }, {
	        type: 'column',
	        name: 'Approved',
	        data: [ <% for (Object[]  obj : graphdata ) { %>   <%=obj[7]%> ,   <%}%>   ]
	    }
	  
	    
	    /* , 
	    {
	        type: 'spline',
	        name: 'Average',
	        data: [3, 2.67, 3, 6.33, 3.33],
	        marker: {
	            lineWidth: 2,
	            lineColor: Highcharts.getOptions().colors[3],
	            fillColor: 'white'
	        }
	    } */
	    
	    , {
	        type: 'pie',
	        name: 'Amount Approved',
	        data: [{
	            name: 'Self',
	            y: 13,
	            color: Highcharts.getOptions().colors[0] // Jane's color
	        }, {
	            name: 'Mem1',
	            y: 23,
	            color: Highcharts.getOptions().colors[1] // John's color
	        }, {
	            name: 'Mem2',
	            y: 19,
	            color: Highcharts.getOptions().colors[2] // Joe's color
	        }
	        
	        
	        
	        ],
	        center: [620, 50],
	        size: 100,
	        showInLegend: false,
	        dataLabels: {
	            enabled: false
	        }, 
	        
	    	
	    }],
	    credits: {
            enabled: false
        },
	}); 



	/* Amount Graph */	

	// Radialize the colors
	Highcharts.setOptions({
	    colors: Highcharts.map(Highcharts.getOptions().colors, function (color) {
	        return {
	            radialGradient: {
	                cx: 0.5,
	                cy: 0.3,
	                r: 0.7
	            },
	            stops: [
	                [0, color],
	                [1, Highcharts.color(color).brighten(-0.3).get('rgb')] // darken
	            ]
	        };
	    })
	});

// Build the chart
	Highcharts.chart('container2', {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie'
	    },
	    title: {
	        text: 'Amount Claimed vs Amount Settled'
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	    },
	    accessibility: {
	        point: {
	            valueSuffix: '%'
	        }
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: true,
	                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                connectorColor: 'silver'
	            },
	            colors: [
	                '#187498', 
	                '#36AE7C', 
	                '#DDDF00', 
	                '#24CBE5', 
	                '#64E572', 
	                '#FF9655', 
	                '#FFF263', 
	                '#6AF9C4'
	              ],
	        }
	    },
	    series: [{
	        name: 'Share',
	        data: [
	            { name: 'Amount Claimed', y: 61 },
	            { name: 'Amount Settled', y: 52 },
	         
	        ]
	    }],
	    credits: {
            enabled: false
        },
	});
	
	
	
	
	

	$(document).ready(function(){
		
	    $('.counter-value').each(function(){
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

	    
	    $("#datepicker1").datepicker({
	    	
	    	autoclose: true,
	    	 format: 'yyyy',
	    		 viewMode: "years", 
	    		    minViewMode: "years"
	    });

	   

	    <%if(Fromdate!=null){%>
	    document.getElementById("datepicker1").value =<%=Fromdate%>
	     <%} %>
	     <%if(Todate!=null){%>
	     document.getElementById("datepicker2").value =<%=Todate%>
	      <%} %>
	    
	    
	    $('#datepicker1').change(function () {
	    	
	        var startDate = document.getElementById("datepicker1").value;
	        var year1=Number(startDate);
	     
	        document.getElementById("datepicker2").value = year1+1;
	        
	        $('#dateform').submit();
	        
	    }); 
	    
	
	    

	});

</script>


</html>
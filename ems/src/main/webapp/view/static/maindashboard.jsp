<%@page import="org.bouncycastle.util.Arrays"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
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
    font-size: 2vw;
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
  
  @media only screen and (max-width: 1600px){
  	
  	.counter h3{
  		font-size: 12px !important;
  	}
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
		Object[] amountdata = (Object[])request.getAttribute("amountdata"); 
		List<Object[]> amountdataindividual  = (List<Object[]> )request.getAttribute("amountdataindividual");
		String isself   = (String)request.getAttribute("isself");
		List<Object[]> monthlywisedata = (List<Object[]>)request.getAttribute("monthlywisedata");
	
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
				
			    <div class="col" style="margin-top: -7px">
			    
			    	<div class="container">
					    <div class="row justify-content-end" >
					    	
					    	<form class="form-inline" action="MainDashBoard.htm" method="post" id="dateform" >

						    	<label style=" font-weight: 800;margin-top: 5px;margin-left: 5px"> Financial Year : &nbsp; </label>
								<input  class="form-control date"  data-date-format="yyyy-mm-dd" id="datepicker1" name="FromDate"  required="required"  style="width: 120px;">
								<input  class="form-control date"  data-date-format="yyyy-mm-dd" id="datepicker2" name="ToDate"  readonly	 required="required"  style="width: 120px;">
	
								<%
								String[] arr = new String[]{ "Z", "K", "V" , "W" };
								if( ArrayUtils.contains(arr,  logintype) ){ %>
				    				<input type="checkbox" <%if(isself.equalsIgnoreCase("Y")) {%> checked <%} %> data-toggle="toggle" data-width="100" id="isself" data-onstyle="success" data-offstyle="primary"  data-on=" <i class='fa-solid fa-user'></i>&nbsp;&nbsp; Self" data-off="<i class='fa-solid fa-industry'></i>&nbsp;&nbsp; Unit" >
			    				<%} %>
			    				
			    				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
			    				<input type="hidden" name="isselfvalue" id="isselfvalue" />
			    			</form>	
			    				
			    		</div>
			    		<br>
			    	</div>
			    	
			    	<div class="container" style="margin-bottom: 22px">
					    <div class="row">
					    <div class="col-md-6">
					      <div class="card-counter primary">
					        <i class="fa fa-code-fork"></i>
					        <span class="count-numbers">&#8377; <%if(amountdata[0]!=null) {%> <%=amountdata[0] %> <%}else {%>0 <%} %></span>
					        <span class="count-name">Total Amount Claimed</span>
					      </div>
					    </div>
		
					    <div class="col-md-6">
					      <div class="card-counter success">
					        <i class="fa fa-database"></i>
					        <span class="count-numbers">&#8377; <%if(amountdata[1]!=null) {%> <%=amountdata[1] %> <%}else {%>0 <%} %></span>
					        <span class="count-name">Total Amount Settled </span>
					      </div>
					    </div>
					  </div>
					  
					</div>
					
					<hr>
					
			    </div>
			    
			    <div class="w-100"></div>
			    
			    <div class="col"><figure class="highcharts-figure">
					    <div id="container" style="display:block;" ></div>
					    <div id="container3" style="display:block;" ></div>
					</figure>
				</div>
					
			    <div class="col">
			    	<figure class="highcharts-figure">
    					<div id="container2"  ></div>
					</figure>
					 <div id="container4"></div>
			    </div>
			    
			   

			    
			    
			  </div>
			</div>
	
		</div>
	</div>
	 
<script>


$(document).ready(function(){

	var selfvalue = '<%=isself%>';
	console.log(selfvalue)
	
	if(selfvalue == 'Y'){
		$('#container').css("display" , "block");
		$('#container3').css("display" , "none");
	}else if(selfvalue== 'N'){
		$('#container').css("display", "none");
		$('#container3').css("display" , "block");
	}
	
	
})




$('#isself').change(function(){
	
	if($(this).prop("checked")==true){
		//is self
		$('#isselfvalue').val('Y');
		$('#dateform').submit();
		
	}else if($(this).prop("checked")==false){
		console.log("asdas")
		$('#isselfvalue').val('N')
		$('#dateform').submit();
	}
	
})

</script>
	
<script type="text/javascript">

/* Counts Graph */	

	Highcharts.chart('container', {
	    title: {
	        text: 'Claim Summary'
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
	    
	    , 
	    
	    {
	        type: 'pie',
	        name: 'Amount Approved',
	        data: [
	        
	        <%  int i=0; 
	        for(Object[] obj : amountdataindividual) { %>	
	        {
	            name: '<%=obj[4]%>',
	            y:<%=obj[7]%>,
	            color: Highcharts.getOptions().colors[<%=i%>] 
	        }
	        ,
	        <% i++; }%>
	        
	        
	        ],
	        
	        
	        center: [480, 50],
	        size: 100,
	        showInLegend: false,
	        dataLabels: {
	            enabled: false
	        }, 
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
                chartOptions: {
                    legend: {
                        align: 'center',
                        verticalAlign: 'bottom',
                        layout: 'horizontal'
                    },
                    yAxis: {
                        labels: {
                            align: 'left',
                            x: 0,
                            y: -5
                        },
                        title: {
                            text: null
                        }
                    },
                    subtitle: {
                        text: null
                    },
                    credits: {
                        enabled: false
                    }
                }
            }]
        }
	}); 



	/********************** Amount Graph ***********************************/	

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
	        text: 'Total Amount Claimed vs Total Amount Settled'
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
	           
	        	 <%if(!amountdata[0].toString().equalsIgnoreCase("0.00")){%>{ name: 'Total Amount Claimed', y: <%=amountdata[0]%> },
	            { name: 'Total Amount Settled', y: <%=amountdata[1]%> },
	            <%}%>
	         
	        ] 
	    }],
	    credits: {
            enabled: false
        },
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        align: 'center',
                        verticalAlign: 'bottom',
                        layout: 'horizontal'
                    },
                    yAxis: {
                        labels: {
                            align: 'left',
                            x: 0,
                            y: -5
                        },
                        title: {
                            text: null
                        }
                    },
                    subtitle: {
                        text: null
                    },
                    credits: {
                        enabled: false
                    }
                }
            }]
        }
	});
	


	/********************************************* Unit Graph Month Wise ************************************** */
	
	Highcharts.chart('container3', {

    chart: {
        type: 'column'
    },

    colors: [
        '#36AE7C', 
        '#187498',
        '#116530',
        '#4D96FF'
      ],
    
    title: {
        text: 'Complete Analysis'
    },

    xAxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    },

    yAxis: {
        allowDecimals: false,
        min: 0,
        title: {
            text: 'Count'
        }
    },

    tooltip: {
        formatter: function () {
            return '<b>' + this.x + '</b><br/>' +
                this.series.name + ': ' + this.y + '<br/>' +
                'Total: ' + this.point.stackTotal;
        }
    },

    plotOptions: {
        column: {
            stacking: 'normal'
        },
        
    },

    series: [{
        name: 'Self Approved',
        data: [ <% for (Object[]  obj : monthlywisedata ) { %>   <%=obj[1]%> ,   <%}%>   ] ,
        stack: 'male'
    }, {
        name: 'Self Pending',
        data: [ <% for (Object[]  obj : monthlywisedata ) { %>   <%=obj[0]%> ,   <%}%>   ] ,
        stack: 'male'
    }, {
        name: 'Family Approved',
        data: [ <% for (Object[]  obj : monthlywisedata ) { %>   <%=obj[3]%> ,   <%}%>   ] ,
        stack: 'female'
    }, {
        name: 'Family Pending',
        data: [ <% for (Object[]  obj : monthlywisedata ) { %>   <%=obj[2]%> ,   <%}%>   ] ,
        stack: 'female'
    }],
    credits: {
        enabled: false
    },
    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal'
                },
                yAxis: {
                    labels: {
                        align: 'left',
                        x: 0,
                        y: -5
                    },
                    title: {
                        text: null
                    }
                },
                subtitle: {
                    text: null
                },
                credits: {
                    enabled: false
                }
            }
        }]
    }
    
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
	        $('#isselfvalue').val('<%=isself%>');
	        
	        $('#dateform').submit();
	        
	    }); 
	    
	
	    

	});

</script>


</html>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
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
    text-align: center;
   	max-width: 200px; 
    margin: 0 auto;
	}
	.counter .counter-content{
	    height: 200px;
	    padding: 55px 0 0;
	    margin: 0 0 10px;
	    position: relative;
	    z-index: 1;
	}
	.counter .counter-content:before,
	.counter .counter-content:after{
	    content: '';
	    background: linear-gradient(to right bottom,#e9e9e9,#fff);
	    border-radius: 50% 50% 0 50%;
	    border: 4px solid #fff;
	    box-shadow: 3px 3px 5px rgba(0,0,0,0.4);
	    position: absolute;
	    left: 7px;
	    top: 7px;
	    right: 7px;
	    bottom: 7px;
	    z-index: -1;
	}
	.counter .counter-content:after{
	    background: linear-gradient(to right bottom, transparent 50%, #EA237E 51%);
	    height: 70%;
	    width: 70%;
	    border: none;
	    border-radius: 0;
	    box-shadow: none;
	    right: 0;
	    bottom: 0;
	    left: auto;
	    top: auto;
	    z-index: -2;
	}
	.counter .counter-icon{
	    color:#999;
	    font-size: 27px;
	    line-height: 27px;
	    position: absolute;
	    bottom: 15px;
	    right: 15px;
	}
	.counter .counter-value{
	    color:#fff;
	    background: #EA237E;
	    font-size: 25px;
	    font-weight: 600;
	    line-height: 6rem;
	   	width: 47%;
    	max-width: 56%;
	    /* height: 95px; */
	    border-radius: 50px;
	    display: inline-block;
	}
	.counter h3{
	    color: #888;
	    font-size: 17px;
	    font-weight: 700;
	    text-transform: uppercase;
	    margin: 0;
	}
	.counter.purple .counter-value{ background: #6D4B87; }
	.counter.purple .counter-content:after{
	    background: linear-gradient(to right bottom, transparent 50%, #835AA8 51%);
	}
	.counter.blue .counter-value{ background: #187498; }
	.counter.blue .counter-content:after{
	    background: linear-gradient(to right bottom, transparent 50%, #187498 51%);
	}
	.counter.skyblue .counter-value{ background: #36AE7C; ; }
	.counter.skyblue .counter-content:after{
	    background: linear-gradient(to right bottom, transparent 50%, #36AE7C  51%);
	}\
	@media screen and (max-width:990px){
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

	.counter .counter-value{
		line-height: 4rem;
		font-size: 20px;
		width: 36%;
		
	}
	
	.counter .counter-content{
		height: 125px;
		padding: 37px 0 0;
	}

	.counter{
   	max-width: 165px; 
	}

  	.counter h3{
  		font-size: 12px !important;
  	}
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
  
	</style>

	</head>

	<body  >
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
	
		IndianRupeeFormat nfc=new IndianRupeeFormat();
	%>
	
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
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
			<div class="container-fluid">
			  <div class="row">
			  
			    <div class="col-md-6">
			   		<div class="container">
					    <div class="row">
					        <div class="col-md-4 col-sm-6">
					            <div class="counter purple">
					                <div class="counter-content ">
					                    <div class="counter-icon">
					                        <i class="fa fa-globe"></i>
					                    </div>
					                    <span class="counter-value"><%=TotalCountData[2] %></span>
					                </div>
					                <h3>TOTAL</h3>
					            </div>
					        </div>
					        
					        <div class="col-md-4 col-sm-6">
					            <div class="counter blue">
					                <div class="counter-content">
					                    <div class="counter-icon">
					                        <i class="fa-solid fa-clock"></i>
					                    </div>
					                    <span class="counter-value"><%=TotalCountData[0] %></span>
					                </div>
					                <h3>PENDING</h3>
					            </div>
					        </div>
					        <div class="col-md-4 col-sm-6">
					            <div class="counter skyblue">
					                <div class="counter-content">
					                    <div class="counter-icon">
					                        <i class="fa-solid fa-circle-check"></i>
					                    </div>
					                    <span class="counter-value"><%=TotalCountData[1] %></span>
					                </div>
					                <h3>APPROVED</h3>
					            </div>
					        </div>
					        
					    </div>
					</div>
			    </div>
			    	
			   	<div class="col-md-6">
			   	
			   		<div class="container" style="margin-bottom: -9px !important">
					    <div class="row justify-content-end" >
					    	
					    	<form class="form-inline" action="MainDashBoard.htm" method="post" id="dateform" >

						    	<label style=" font-weight: 800;margin-top: 5px;margin-left: 5px"> Financial Year : &nbsp; </label>
								<input  class="form-control date"  data-date-format="yyyy-mm-dd" id="datepicker1" name="FromDate"  required="required"  style="width: 120px;">
								<input  class="form-control date"  data-date-format="yyyy-mm-dd" id="datepicker2" name="ToDate"  readonly	 required="required"  style="width: 120px;">
	
								<%
								String[] arr = new String[]{ "Z", "K", "V" , "W" , "F" };
								if( ArrayUtils.contains(arr,  logintype) ){ %>
				    				<input type="checkbox" <%if(isself.equalsIgnoreCase("Y")) {%> checked <%} %> data-toggle="toggle" data-width="100" id="isself" data-onstyle="success" data-offstyle="primary"  data-on=" <i class='fa-solid fa-user'></i>&nbsp;&nbsp; Self" data-off="<i class='fa-solid fa-industry'></i>&nbsp;&nbsp; Unit" >
			    				<%} %>
			    				
			    				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
			    				<input type="hidden" name="isselfvalue" id="isselfvalue" />
			    			</form>	
			    				
			    		</div>
			    		<br>
			    	</div>
			    	
			    	<div class="container" >
					    <div class="row">
					    <div class="col-md-6">
					      <div class="card-counter primary">
					        <i class="fa fa-code-fork"></i>
					        <span class="count-numbers">&#8377; <%if(amountdata[0]!=null) {%> <%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(amountdata[0].toString() ))))%> <%}else {%>0 <%} %></span>
					        <span class="count-name">Total Amount Claimed</span>
					      </div>
					    </div>
		
					    <div class="col-md-6">
					      <div class="card-counter success">
					        <i class="fa fa-database"></i>
					        <span class="count-numbers">&#8377; <%if(amountdata[1]!=null) {%> <%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(amountdata[1].toString() ))))%> <%}else {%>0 <%} %></span>
					        <span class="count-name">Total Amount Settled </span>
					      </div>
					    </div>
					  </div>
					  
					</div>
			   	
			   	</div> 	

				</div>
				
			   <hr>
			   
			   <div class="row">
			   		<div class="col-md-6">
			   			<div id="container" style="display:block;" ></div>
					    <div id="container3" style="display:block;" ></div>
			   		</div>
			   		<div class="col-md-6">
			   			<div id="container4"  style="display: block"></div>
					    <div id="container-speed" style="display: block" ></div>
			   		</div>
			   </div>
			   
			  </div>
			</div>
	
		</div>
	 
	 
	

	
	 
<script>



$(document).ready(function(){
	
	
	var selfvalue = '<%=isself%>';
	if(selfvalue == 'Y'){
		$('#container').css("display" , "block");
		$('#container4').css("display" , "block");
		$('#container3').css("display" , "none");
		$('#container-speed').css("display" , "none");
		
	}else if(selfvalue== 'N'){
		$('#container').css("display", "none");
		$('#container4').css("display", "none");
		$('#container3').css("display" , "block");
		$('#container-speed').css("display" , "block");
	}
	
	
})

$('#isself').change(function(){
	
	if($(this).prop("checked")==true){
		//is self
		$('#isselfvalue').val('Y');
		$('#dateform').submit();
		
	}else if($(this).prop("checked")==false){
		$('#isselfvalue').val('N')
		$('#dateform').submit();
	}
	
})

</script>
	
<script type="text/javascript">

/* Counts Graph */	

	Highcharts.chart('container', {
		
		   chart: {
		        type: 'column',
		    },
	    title: {
	        text: 'Claim Summary'
	    },
	    
	    
	    
	    xAxis: {
	         categories: [ <% for (Object[]  obj : graphdata ) { %>   '<%=obj[1]%>' ,   <%}%>   ] 
	   
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
	        }] */
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

	var gaugeOptions = {
    chart: {
        type: 'solidgauge',
        marginTop : 30,
    },

    title: null,

    pane: {
        size: '100%',
        startAngle: -90,
        endAngle: 90,
        background: {
            backgroundColor:
                Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
            innerRadius: '60%',
            outerRadius: '100%',
            shape: 'arc'
        }
    },

    exporting: {
        enabled: false
    },

    tooltip: {
        enabled: true
    },

    // the value axis
    yAxis: {
        stops: [
            [0.1, '#DF5353'], // green
            [0.5, '#DDDF0D'], // yellow
            [0.9, '#55BF3B'] // red 
        ],
        lineWidth: 0,
        tickWidth: 0,
        minorTickInterval: null,
        tickAmount: 2,
        title: {
            y: 70
        },
        labels: {
            y: 16,
          
        }
    },

	    plotOptions: {
	        solidgauge: {
	            dataLabels: {
	                y: 5,
	                borderWidth: 0,
	                useHTML: true,
	                
	            }
	        }
	    }
	};
	
	// The amount gauge

	var chartSpeed = Highcharts.chart('container-speed', Highcharts.merge(gaugeOptions, {
    yAxis: {
        min: 0,
        max: <%=amountdata[0]%>  ,
        tickWidth: 0,
        tickPositions: [0 , <%=amountdata[0]%> ],
        title: {
            text: 'Amount Settled',
          
        }
    },

    credits: {
        enabled: false
    },

    series: [{
        name: 'Amount Settled',
        data: [<%=Math.round(Double.parseDouble(amountdata[1].toString() ))%> ],
        dataLabels: {
            format:
                '<div style="text-align:center">' +
                '<span style="font-size:25px">&#8377; {y}</span><br/>' +
                '<span style="font-size:12px;opacity:0.4"></span>' +
                '</div>'
        },
        tooltip: {
            valuePrefix: ' &#8377;'
        }
    }],
    
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

}));


	/* ****************************************** Pie Chart Amount Graph *************************************************** */
	
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
	Highcharts.chart('container4', {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie',

	    },
	    title: {
	        text: 'Total Amount Settled'
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>&#8377; {point.y:,.2f}</b>'
	    },
	    
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: true,
	                format: '<b>{point.name}</b>:&#8377; {point.y:,.2f}',
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
		        
		        <% if(!amountdata[1].toString().equalsIgnoreCase("0.00")){  int j=0; 
		        for(Object[] obj : amountdataindividual) { %>	
		        {
		            name: '<%=obj[1]%>',
		            y:<%=obj[7]%>,
		            color: Highcharts.getOptions().colors[<%=j%>] 
		        }
		        ,
		        <% j++; 
		        }
		        }
		        %>
		        
		        
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
	
	


	/********************************************* Unit Graph Month Wise ************************************** */
	
	Highcharts.chart('container3', {

    chart: {
        type: 'column',
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
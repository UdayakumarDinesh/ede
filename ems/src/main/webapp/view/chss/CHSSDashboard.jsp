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
	<%	
	
		/* List<Object[]> emplogintypelist     = (List<Object[]>)request.getAttribute("logintypeslist"); */
		String logintype   = (String)session.getAttribute("LoginType");
		String Fromdate=(String)request.getAttribute("Fromdate");
		String Todate=(String)request.getAttribute("Todate"); 
		Object[] TotalCountData = (Object[])request.getAttribute("countdata"); 
		List<Object[]> graphdata  = (List<Object[]> )request.getAttribute("graphdata");
		Object[] amountdata = (Object[])request.getAttribute("amountdata"); 
		List<Object[]> amountdataindividual  = (List<Object[]> )request.getAttribute("amountdataindividual");
		String isself   = (String)request.getAttribute("isself");
		List<Object[]> monthlywisedata = (List<Object[]>)request.getAttribute("monthlywisedata");
		
		List<Object[]> EmpanelledHospitals = (List<Object[]>)request.getAttribute("EmpanelledHospitals");
		List<Object[]> EmpanelledDoctors = (List<Object[]>)request.getAttribute("EmpanelledDoctors");
		String chsspolicypdf = (String)request.getAttribute("chss_policy_pdf");
		IndianRupeeFormat nfc=new IndianRupeeFormat();
	%>
	
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
			
			<div class="row">
				<div class="col-md-5">
					<h5 style="padding-top: 0.5rem">CHSS DASHBOARD </h5>
				</div>
			  	<div class="col-md-7" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">CHSS</li>
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
		<div class="row">
				<div class="col-md-12" style="padding-top:0px;">
				  		<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist" style="background: #ECD4B9;padding: 0px;border-radius: 0px;">
						  <li class="nav-item" style="width: 25%;text-align: center;">
						    <a class="nav-link active" style="border-radius: 0px;" id="pills-dashboard-tab" data-toggle="pill" href="#pills-dashboard" role="tab" aria-controls="pills-dashboard" aria-selected="true">Dashboard</a>
						  </li>
						  <li class="nav-item" style="width: 25%;text-align: center;">
						    <a class="nav-link" style="border-radius: 0px;" id="pills-policy-tab" data-toggle="pill" href="#pills-policy" role="tab" aria-controls="pills-policy" aria-selected="false">CHSS - Policy</a>
						  </li>
						  <li class="nav-item" style="width: 25%;text-align: center;">
						    <a class="nav-link" style="border-radius: 0px;" id="pills-hospital-tab" data-toggle="pill" href="#pills-hospital" role="tab" aria-controls="pills-hospital" aria-selected="false">Empanelled Hospitals</a>
						  </li>
						  <li class="nav-item" style="width: 25%;text-align: center;">
						    <a class="nav-link" style="border-radius: 0px;" id="pills-doctor-tab" data-toggle="pill" href="#pills-doctor" role="tab" aria-controls="pills-doctor" aria-selected="false">Empanelled Doctors</a>
						  </li>
						</ul>
				</div>
			</div>
		<div class="card-body " style="padding-top: 0px;">
		
		
			<div class="container-fluid" >
				  
			<div class="tab-content" id="pills-tabContent">
		  	
		  		<div class="tab-pane fade show active" id="pills-dashboard" role="tabpanel" aria-labelledby="pills-dashboard-tab">
					  <div class="row">
					  
					    <div class="col-md-6">
					   		
							<div class="container">
							    <div class="row">
							        <div class="col-md-4">
							            <div class="counter purple">
							                <span class="counter-value"><%=TotalCountData[2] %></span>
							                <h3>TOTAL</h3>
							            </div>
							        </div>
							        <div class="col-md-4 ">
							            <div class="counter blue">
							                <span class="counter-value"><%=TotalCountData[0] %></span>
							                <h3>PENDING</h3>
							            </div>
							        </div>
							        <div class="col-md-4 ">
							            <div class="counter green">
							                <span class="counter-value"><%=TotalCountData[1] %></span>
							                <h3>APPROVED</h3>
							            </div>
							        </div>
							    </div>
							</div>
					    </div>
					    	
					   	<div class="col-md-6">
					   	
					   		<div class="container" style="margin-bottom: -9px !important">
							    <div class="row justify-content-end" >
							    	
							    	<form class="form-inline" action="CHSSDashboard.htm" method="post" id="dateform" >
		
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
					    	
					    	<div class="container">
							    <div class="row">
							    
							     <div class="col-md-4">
							      <div class="card-counter violet">
							        <i class="fa-solid fa-list-check"></i>
							        <span class="count-numbers">&#8377; <%if(amountdata[2]!=null) {%> <%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(amountdata[2].toString() ))))%> <%}else {%>0 <%} %></span>
							        <span class="count-name"> Amount InProcess </span>
							      </div>
							    </div>
							    
							    <div class="col-md-4">
							      <div class="card-counter primary">
							        <i class="fa fa-code-fork"></i>
							        <span class="count-numbers">&#8377; <%if(amountdata[0]!=null) {%> <%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(amountdata[0].toString() ))))%> <%}else {%>0 <%} %></span>
							        <span class="count-name"> Amount Claimed</span>
							      </div>
							    </div>
				
							    <div class="col-md-4">
							      <div class="card-counter success">
							        <i class="fa fa-database"></i>
							        <span class="count-numbers">&#8377; <%if(amountdata[1]!=null) {%> <%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(amountdata[1].toString() ))))%> <%}else {%>0 <%} %></span>
							        <span class="count-name"> Amount Settled </span>
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
			   
				<div class="tab-pane fade" id="pills-policy" role="tabpanel" aria-labelledby="pills-policy-tab">
					<div>
					    <iframe src="data:application/pdf;base64,<%=chsspolicypdf %>" width="100%" style="height: 33rem !important;" > PDF- File Missing</iframe>
					</div>
			 	</div>
			 	
			 	<div class="tab-pane fade" id="pills-hospital" role="tabpanel" aria-labelledby="pills-hospital-tab">
					<div>
			 			<table class="table table-bordered table-hover table-striped table-condensed myDataTable" > 
							<thead>
								<tr>
									<th>Select</th>
									<th>Hospital Name </th>
									<th>Hospital Address</th>								
								</tr>
							</thead>
							<tbody>
								<% if(EmpanelledHospitals!=null && EmpanelledHospitals.size()>0){ 
									for(Object[] obj : EmpanelledHospitals){
								%>
									<tr>
										<td style="text-align:center;  width: 10%;"><%=EmpanelledHospitals.indexOf(obj)+1 %> </td>
										<td style=" width: 35%;"><%=obj[1]%></td>
										<td style=" width: 45%;"><%=obj[2]%></td>
									</tr>
								<%} }%>
							</tbody>
						</table>
			 		</div>
			 	</div>
			 	
			 	<div class="tab-pane fade" id="pills-doctor" role="tabpanel" aria-labelledby="pills-doctor-tab">
					<div>
			 			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
								<thead>
									<tr>
										<th>Select</th>
										<th>Name </th>
										<th>Address </th>
										<th>Contact Details</th>																		
									</tr>
								</thead>
								<tbody>
									<%if(EmpanelledDoctors!=null && EmpanelledDoctors.size()>0){ 
										for(Object[] obj : EmpanelledDoctors){
									%>
										<tr>
											<td style="text-align:center;  width: 5%;"> <input type="radio" name="doctorId" value="<%=obj[0]%>"> </td>
											<td ><%=obj[1]%></td>
										    <td ><%=obj[3]%></td>
											<td ><%=obj[4]%></td>
										</tr>
								<%} }%>
								</tbody>
							</table>
			 			
			 		</div>
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

	var maxamount = Math.round(<%=amountdata[0]%>);
	
	var chartSpeed = Highcharts.chart('container-speed', Highcharts.merge(gaugeOptions, {
    yAxis: {
        min: 0,
        max: maxamount  ,
        tickWidth: 0,
        tickPositions: [0 , maxamount ],
        title: {
            text: 'Amount Settled',
        },
        labels : {
        	style :{
        		'font-size': 20	
        	}
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
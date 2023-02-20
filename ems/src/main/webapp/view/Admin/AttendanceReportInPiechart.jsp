<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Attendance Report Pie Chart</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<style>
 #container4,#container1{
    height: 300px;
    min-width: 310px;
    max-width: 600px;
}
 
.highcharts-figure,	.highcharts-data-table table {
	    min-width: 310px;
	    max-width: 600px;
	}

	.highcharts-data-table table {
	    font-family: Verdana, sans-serif;
	    border-collapse: collapse;
	    border: 1px solid #ebebeb;
	    margin: 10px auto;
	    text-align: center;
	    width: 100%;
	    max-width: 400px;
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
	.dot1 {
  height: 25px;
  width: 25px;
  background-color: #187498;
  border-radius: 50%;
margin-left: 10px;
}
.dot2 {
  height: 25px;
  width: 25px;
  background-color: rgb(60,60,60);
  border-radius: 50%;
 margin-left: 10px;
}
.dot3 {
  height: 25px;
  width: 25px;
  background-color: #64E572;                                             
  border-radius: 50%;
 margin-left: 10px;
}
.dot4 {
  height: 25px;
  width: 25px;
  background-color:rgb(255,155,10);
  border-radius: 50%;
 margin-left: 10px;
}
.dot5 {
  height: 25px;
  width: 25px;
  background-color:rgb(106,90,205);
  border-radius: 50%;
margin-left: 10px;
}
.flex-container{
diplay :flex;
justify-content: space-between;
 margin-left: 10px;
 margin-top: 10px;
}
</style>
</head>
<body>
<%
List<Object[]> list=(List<Object[]>)request.getAttribute("list");
String date =(String)request.getAttribute("date");
%>
<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
			
			<div class="row">
				<div class="col-md-5">
					<h5 style="padding-top: 0.5rem">Attendance Report Pie Chart </h5>
				</div>
			  	<div class="col-md-7" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Attendance Report Pie Chart</li>
				  </ol>
				</nav>
			</div>		
		</div>
	</div>
			 <div class="card dashboard-card" >
			<!--<div class="card"> -->
			<div class="card-body main-card">
			<form action="AttendancePieChart.htm" method="POST" id="myform">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	        <div align="right">
	        <table>
	        <tr>
	       <th> <label style="font-weight: Bold;">Date : &nbsp;&nbsp;</label></th>
	       <td> <input type="text" style="background-color:white;width: 7rem;" onchange="this.form.submit()" class="form-control input-sm" id="mydate" readonly="readonly" name="date" value="<%if(date!=null){%><%=date%><%}%>"></td>
	        </tr>
	         </table>
	          </div>
	         
		    
		     <div class="card-body main-card">
		     <div class="row">
		     <div class="col-md-6">
	         <div  style="width:100%;border: solid;height:380px;">
	         
					   			<div id="container4"  style="display: block"></div>
					   			<div class="flex-container">
					   			<div class="row">
					   			<div class="dot1"></div><b style="padding-left:5px;">Before 9:00</b>
                                <div class="dot2"></div><b style="padding-left:5px;">9:00 - 9:30</b>
                                <div class="dot3"></div><b style="padding-left:5px;">9:30 - 10:00</b>
                                <div class="dot4"></div><b style="padding-left:5px;">10:00 - 10:30</b>
                                <div class="dot5"></div><b style="padding-left:5px;">After 11:00</b>
							    </div>
		                    </div>
		                           
		     </div>
		     </div>
		     <div class="col-md-6">
		     <div  style="width:100%;border: solid;height:380px;">
		     <div id="container1" style="display: block"></div>
		     
		     </div>
		     </div>
		     </div>
		     </div>
		       </form>
		  </div>
		 </div>	
<!--	</div> -->
	
<script>
$(document).ready(function(){	
		
		$('#container4').css("display" , "block");			
		 $('.flex-container').css("display" , "block"); 
		 $('#container1').css("display" , "block");	
});
</script>

<script>
//           **********************  Pie-Chart  *******************************
//Radialize the colors
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
        text: 'Employee In-Time Statistics'
    },
    tooltip: {
        pointFormat: '{series.name}<b> {point.y:}</b>'
    },
      
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>{point.y:}',
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
        name: 'Attendance In Time Statistics',
        data: [
	        <%  if(list!=null){	       
	         int j=0;
        	for(Object[] obj:list){
        		//System.out.println(obj[1]+","+obj[0]);
	       %>
		        {
		        	 name: '<%=obj[1]%> , Count: <%=obj[0]%>',
			            y:<%=obj[0]%>,
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
                        text: 'fhg'
                    }
                }
            }
        }]
    }
});
</script>
<%-- <script>
        //******************** Today's Trend (Area Chart ) ********************
    Highcharts.chart('container', {
		
		   chart: {
		        type: 'area',
		    },
	    title: {
	        text: 'Todays Trend'
	    },
	    
	    
	    
	    xAxis: {
	    	categories: [<%  if(list!=null){	 
	    		for(Object[] obj:list){
	    		%>     
	          '<%=obj[1]%>' ,
	                      <%}}%>],
	         title:{
	        	 
	          text:'Time'
	         }
	    },
	    yAxis: {
	        min: 0,
	        max:100,
	        title: {
	            text: 'Employee Count'
	        }
	    
	    },
	   
	    colors: [
	        '#187498',
	        '#36AE7C',
	    ],
	     series: [{
	        type: 'area',
	        name: 'count',
	        data: [  <%if(list!=null){
	        	for(Object[] obj:list){
	       %>
	       <%=obj[0]%> , 
	        <%}}%>],
	    }, 
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
</script> --%>
<script>
Highcharts.chart('container1', {
	  chart: {
	    type: 'area'
	  },
	  accessibility: {
	    description: ''
	  },
	  title: {
	    text: ''
	  },
	  subtitle: {
	    text: ''
	  },
	  xAxis: {
	    allowDecimals: false,
	    labels: {
	      formatter: function () {
	        return this.value; // clean, unformatted number for year
	      }
	    },
	    accessibility: {
	      rangeDescription: 'Range: 8 to 12.'
	    }
	  },
	  yAxis: {
	    title: {
	      text: 'Employee Count'
	    },
	    labels: {
	    	formatter: function () {
	        return this.value; 
	      }
	      
	    }
	  },
	  tooltip: {
	    pointFormat: ''
	  },
	  plotOptions: {
	    area: {
	      pointStart:8,
	      marker: {
	        enabled: false,
	        symbol: 'circle',
	        radius: 2,
	        states: {
	          hover: {
	            enabled: true
	          }
	        }
	      }
	    }
	  },
	  series: [{
	    name: 'Todays Trend',
	    data: [
	    	<%if(list!=null){%>
	    	<%for(Object[] obj:list){%>
	 	       <%=obj[0]%> , 
	 	    <%}%>
	 	    <%}%>
	    ]
	  },]
	});
</script>
<script>

$('#mydate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate":new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});		
	$(document).ready(function(){
		   $('#mydate').change(function(){
		       $('#myform').submit();
		    });
		});
	</script> 
</body>
</html>
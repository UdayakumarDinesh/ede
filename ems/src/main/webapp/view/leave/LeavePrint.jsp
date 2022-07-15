<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/png" href="view/images/lablogoui.png">
<jsp:include page="/view/static/dependancy.jsp"></jsp:include>
        
<spring:url value="/webresources/css/Header.css" var="Header" />
<title>Leave Print</title>

 
 <style> .shubh{
        
        font-size: 20px;
        padding-top:20px;
        }
        
        .shubh p1{padding-left:20px;}
        .shubh p2{padding-left:27px;}
    </style>
</head>
<body>
 
 <% Object[] printleave=(Object[])request.getAttribute("print" );%>
<%if(printleave!=null){
	Object[] lab=(Object[])request.getAttribute("Lab" );
%>

<div class="row" ><!-- row open -->
     <div class="col-md-3"></div>   
       <!-- main column contains data-->
       <div class="col-md-6"  style="margin-top: 50px;  border:1px solid blue;">
           
           <!-- page header -->
           <div class="card-header"><h3 style="text-align: center;font-weight: bold;"><%=lab[1] %></h3></div>
              <!-- //page header -->  
                 <h3 style="text-align: center;"> Application For Applied Leave</h3>
                
                
     
           <table class="table table-hover  table-striped table-condensed table-bordered">
          
           <tbody>
           
           <tr>
               <td>1.Name</td>
               <td>:<%=printleave[1]%></td>
           </tr>
           
           <tr>
                <td>2.Designation</td>
                <td>:<%=printleave[2]%></td>
           </tr>
          
           <tr>
                <td>3.Division/Group</td>
                <td>:<%=printleave[3]%></td>
           </tr>
          
          
          <tr>
          <td>4.Nature Of Leave</td>
          <td>:<%=printleave[4]%> <%if("F".equalsIgnoreCase(printleave[5].toString())){out.println("(FN)");}else if ("A".equalsIgnoreCase(printleave[5].toString())){out.println("(AN)");} %></td>
           </tr>
          
          
          <tr>
          <td>5.Period Of Leave</td>
          <td>:From <%=printleave[6]%>  To  <%=printleave[7] %> &nbsp;&nbsp;&nbsp;Days: <%=printleave[8] %></td>
            </tr>
          
          
          <tr>
          <td>6.Purpose Of Leave</td>
          <td>:<%=printleave[9] %></td>
            </tr>
          
          
            <tr>
          <td>7.Leave Address</td>
          <td>: <%=printleave[10] %></td>
            </tr>
          
           <tr>
          <td>8.Remark  </td>
          <td>: <%=printleave[11] %>  </td>
            </tr>
          
           <tr>
          <td>9.Status  </td>
          <td>:<%=printleave[12] %>  </td>
            </tr>
          
          
           <tr>
          <td>10.LTC </td>
          <td>:
          <%if(("N".equalsIgnoreCase(printleave[13].toString()))){%>
          <%="No LTC Leave"%>
          <%}else if(("Y".equalsIgnoreCase(printleave[13].toString()))){%>
          <%= "LTC Leave"%>
          <%}%><% else if (("A".equalsIgnoreCase(printleave[13].toString()))){%>
          <%="LTC Applied"%>
          <%}%>
         
      
          </td>
          </tr>
         
          </tbody>
        </table>
         <% List<Object[]> transleave=(List<Object[]>)request.getAttribute("trans" );%>
        <%for(Object[] obj:transleave ){
	      if(!"LUE".equals(obj[6].toString())){
         %>
        <span><b> <%=obj[5] %> : </b>   <%=obj[1]%>, <%=obj[2]%>  <b> On </b> <%=obj[3] %>   </span><br>
        
        <%}} %>
        <br>
        <br>
          
          <!-- //part I -->
          
          
  
        
         </div> <!--// main column contains data -->
           
       
     
        <div  class="col-md-3"></div>
        
        </div><!-- //row closed-->
  

<%}else{%>
<div ><b style="color: red;">Some Problem Occur / Or You May Enter Wrong Details In URL</b></div>

<%}%>



</body>
</html>
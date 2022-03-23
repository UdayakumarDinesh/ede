<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- ----------  jquery  ---------- -->
<script src="./webjars/jquery/3.4.0/jquery.min.js"></script>

<!--BootStrap Bundle JS  -->
<script src="./webjars/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>

<!--BootStrap CSS  -->
<link rel="stylesheet" href="./webjars/bootstrap/4.0.0/css/bootstrap.min.css" />


<!-- ----------  daterangepicker  ---------- -->


<spring:url value="/webresources/moment.min.js" var="momentjs" />  
<script src="${momentjs}"></script> 

<spring:url value="/webresources/daterangepicker.min.js" var="daterangepickerjs" />  
<script src="${daterangepickerjs}"></script>

<spring:url value="/webresources/daterangepicker.min.css" var="daterangepickerCss" />     
<link href="${daterangepickerCss}" rel="stylesheet" />




 <!-- ----------  select2   ---------- -->
<spring:url value="/webresources/addons/select2/select2.min.js" var="select2js" />  
<script src="${select2js}"></script> 

<spring:url value="/webresources/addons/select2/select2.min.css" var="select2css" />     
<link href="${select2css}" rel="stylesheet" />


 <!-- ----------  selectpicker  ---------- -->
<spring:url value="/webresources/addons/selectpicker/bootstrap-select.min.js" var="selectpickerjs" />  
<script src="${selectpickerjs}"></script> 

<spring:url value="/webresources/addons/selectpicker/bootstrap-select.min.css" var="selectpickercss" />     
<link href="${selectpickercss}" rel="stylesheet" />


<!-- ----------  Data tables  ---------- -->
<spring:url value="/webresources/addons/DataTable/dataTables.bootstrap4.min.css" var="DataTableCss" />
<link href="${DataTableCss}" rel="stylesheet" />

<spring:url value="/webresources/addons/DataTable/jquery.dataTables.min.js" var="DataTableJjs" />    
<script src="${DataTableJjs}"></script>

<spring:url value="/webresources/addons/DataTable/dataTables.bootstrap4.min.js" var="DataTablejs" /> 
<script src="${DataTablejs}"></script>


<!-- ----------  fontawesome  ---------- -->
<spring:url value="/webresources/fontawesome/css/all.css" var="fontawesome" />     
<link href="${fontawesome}" rel="stylesheet" />

<!-- ----------  Master.css  ---------- -->
<spring:url value="/webresources/css/master.css" var="MasterCss" />     
<link href="${MasterCss}" rel="stylesheet" />

<!-- ----------  Master.js  ---------- -->
<spring:url value="/webresources/js/master.js" var="MasterJs" /> 
<script src="${MasterJs}"></script>

<!-- ----------  Master.css  ---------- -->
<spring:url value="/webresources/css/buttons.css" var="ButtonsCss" />     
<link href="${ButtonsCss}" rel="stylesheet" />




</head>
</html>
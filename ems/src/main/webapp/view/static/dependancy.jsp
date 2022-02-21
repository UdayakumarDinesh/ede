<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- ----------  jquery  ---------- -->
<script src="./webjars/jquery/3.6.0/jquery.min.js"></script>

<!--BootStrap Bundle JS  -->
<script src="./webjars/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>

<!--BootStrap JS  -->
<script src="./webjars/bootstrap/4.0.0/js/*.js"></script>

<!--BootStrap CSS  -->
<link rel="stylesheet" href="./webjars/bootstrap/4.0.0/css/bootstrap.min.css" />


<!-- ----------  daterangepicker  ---------- -->
<spring:url value="/webresources/addons/daterangepicker.min.js" var="daterangepickerjs" />  
<script src="${daterangepickerjs}"></script> 
<spring:url value="/webresources/addons/daterangepicker.min.css" var="daterangepickerCss" />     
<link href="${daterangepickerCss}" rel="stylesheet" />

<!-- ----------  daterangepicker  ---------- -->
<spring:url value="/webresources/fontawesome/css/all.css" var="fontawesome" />     
<link href="${fontawesome}" rel="stylesheet" />

</head>
</html>
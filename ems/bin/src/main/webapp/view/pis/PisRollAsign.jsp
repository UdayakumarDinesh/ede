<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.List,com.vts.*"%>
<!DOCTYPE html>
<html>
<head>
 
<style type="text/css">
.hidden{
    display:none;
}
</style>


<title>ASSIGN ROLL</title>
</head>
<body>
<%//List<Object[]> typeList=(List<Object[]>)request.getAttribute("typeList");
List<Object[]> empList=(List<Object[]>)request.getAttribute("empList");
List<Object[]> LeaveList=(List<Object[]>)request.getAttribute("LeaveList");
List<Object[]> TDList=(List<Object[]>)request.getAttribute("TDList");
List<Object[]> HRList=(List<Object[]>)request.getAttribute("HRList");
List<Object[]> MTList=(List<Object[]>)request.getAttribute("MTList");
List<Object[]> DOList=(List<Object[]>)request.getAttribute("DOList");
List<Object[]> PISList=(List<Object[]>)request.getAttribute("PISList");
List<Object[]> MMGList=(List<Object[]>)request.getAttribute("MMGList");
List<Object[]> ATTNDList=(List<Object[]>)request.getAttribute("ATTNDList");
%>
<div class="row">
<div class="col-md-12">
<section class="content-header">
			<h5>ASSIGN ROLE</h5>
			<ol class="breadcrumb" >
				<li class="breadcrumb-item"><a href="DashBoard.htm"><i class="fa fa-home"></i> Home</a></li>
				
				<li class="breadcrumb-item"><a href="PisDashBoard.htm"></i> PIS DashBoard</a></li>
			
				<li class="breadcrumb-item active">Assign Role</li>
			</ol>
		  </section> 
		  </div>
</div>

<%String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%><center>
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></center>
	<%}if(ses!=null){ %>
	<center>
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div></center>
                    <%} %>



<div class="row"> 
 <div class="col-md-12"  style="top: 10px;">
 

          <div class="container-fluid" >
<nav style="background-color: navy;">
  <div class="nav nav-tabs " id="nav-tab" role="tablist" style="font-size: 12px;   font-weight: bold; color: white; " >
 
    <a class="nav-item nav-link active" id="nav-leave-tab" data-toggle="tab" href="#nav-leave" role="tab" aria-controls="nav-leave" aria-selected="true"><b>LEAVE AUTHORITY</b></a>
    <a class="nav-item nav-link " id="nav-td-tab" data-toggle="tab" href="#nav-td" role="tab" aria-controls="nav-td" aria-selected="false">TD AUTHORITY</a>
    <a class="nav-item nav-link" id="nav-hr-tab" data-toggle="tab" href="#nav-hr" role="tab" aria-controls="nav-hr" aria-selected="false">HR AUTHORITY</a>
    <a class="nav-item nav-link" id="nav-mt-tab" data-toggle="tab" href="#nav-mt" role="tab" aria-controls="nav-mt" aria-selected="false">MT AUTHORITY</a>
     <a class="nav-item nav-link" id="nav-do-tab" data-toggle="tab" href="#nav-do" role="tab" aria-controls="nav-do" aria-selected="false">DO AUTHORITY</a>
      <a class="nav-item nav-link" id="nav-pis-tab" data-toggle="tab" href="#nav-pis" role="tab" aria-controls="nav-pis" aria-selected="false">PIS AUTHORITY</a>
       <a class="nav-item nav-link" id="nav-mmg-tab" data-toggle="tab" href="#nav-mmg" role="tab" aria-controls="nav-mmg" aria-selected="false">MMG AUTHORITY</a>
          <a class="nav-item nav-link" id="nav-attnd-tab" data-toggle="tab" href="#nav-attnd" role="tab" aria-controls="nav-attnd" aria-selected="false">ATTND AUTHORITY</a>
   
  </div>
</nav>
 
 </div>
 
  <div class="container-fluid" >
           <div class="tab-content " id="nav-tabContent">
            <div class="tab-pane fade show active" id="nav-leave" role="tabpanel" aria-labelledby="nav-leave-tab">
                 <div class="card border-primary" style="margin-top: 10px;">
                <div class="card-header">
                    
                      
                </div>
                 
               <!-- edit and Delete -->
                <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr style="font-size: 12px; line-height: 0px;">
                                <th >NAME</th>
                                <th>DESG</th>
                                <th>ROLE</th>
                               
                            </tr>
                        </thead>
                        <tbody>  
                        <%for(Object[] obj:LeaveList){ %>
                        <tr>
                        <td><%=obj[0] %></td>
                        <td><%=obj[1] %></td>
                         <td><%if(obj[2].toString().equals("P")){ %>USER<%} %><%if(obj[2].toString().equals("A")){ %>ADMIN<%} %>
                        <%if(obj[2].toString().equals("O")){ %>ADMIN OFFICER<%} %><%if(obj[2].toString().equals("D")){ %>DIRECTOR<%} %></td>
                        </tr>
                        <%} %>
                        
            	</tbody>
            	</table>
            
            </div>
           
           
           
           </div>
           </div>
 
</div>

 <div class="tab-pane fade show " id="nav-td" role="tabpanel" aria-labelledby="nav-td-tab">
                 <div class="card border-primary" style="margin-top: 10px;">
                <div class="card-header">
                    
                      
                </div>
                 
               <!-- edit and Delete -->
                <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr style="font-size: 12px; line-height: 0px;">
                                <th >NAME</th>
                                <th>DESG</th>
                                <th>ROLE</th>
                               
                            </tr>
                        </thead>
                        <tbody>  
                        <%for(Object[] obj:TDList){ %>
                        <tr>
                        <td><%=obj[0] %></td>
                        <td><%=obj[1] %></td>
                        <td><%if(obj[2].toString().equals("P")){ %>USER<%} %><%if(obj[2].toString().equals("A")){ %>ADMIN<%} %>
                        <%if(obj[2].toString().equals("B")){ %>ACCOUNT OFFICER<%} %><%if(obj[2].toString().equals("D")){ %>DIRECTOR<%} %>
                        <%if(obj[2].toString().equals("N")){ %>ASSOCIATE DIRECTOR<%} %>
                        </td>
                        </tr>
                        <%} %>
                        
            	</tbody>
            	</table>
            
            </div>
           
           
           
           </div>
           </div>
 
</div>

<div class="tab-pane fade show " id="nav-hr" role="tabpanel" aria-labelledby="nav-hr-tab">
                 <div class="card border-primary" style="margin-top: 10px;">
                <div class="card-header">
                    
                      
                </div>
                 
               <!-- edit and Delete -->
                <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr style="font-size: 12px; line-height: 0px;">
                                <th >NAME</th>
                                <th>DESG</th>
                                <th>ROLE</th>
                               
                            </tr>
                        </thead>
                        <tbody>  
                        <%for(Object[] obj:HRList){ %>
                        <tr>
                        <td><%=obj[0] %></td>
                        <td><%=obj[1] %></td>
                        <td><%if(obj[2].toString().equals("P")){ %>USER<%} %><%if(obj[2].toString().equals("A")){ %>ADMIN<%} %>
                        <%if(obj[2].toString().equals("B")){ %>ACCOUNT OFFICER<%} %><%if(obj[2].toString().equals("H")){ %>HR <%} %>
                        <%if(obj[2].toString().equals("D")){ %>DIRECTOR<%} %> 
                        <%if(obj[2].toString().equals("S")){ %>HR HEAD<%} %>
                        <% %>
                        </td>
                        </tr>
                        <%} %>
                        
            	</tbody>
            	</table>
            
            </div>
           
           
           
           </div>
           </div>
 
</div>

<div class="tab-pane fade show " id="nav-mt" role="tabpanel" aria-labelledby="nav-mt-tab">
                 <div class="card border-primary" style="margin-top: 10px;">
                <div class="card-header">
                    
                      
                </div>
                 
               <!-- edit and Delete -->
                <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr style="font-size: 12px; line-height: 0px;">
                                <th >NAME</th>
                                <th>DESG</th>
                                <th>ROLE</th>
                               
                            </tr>
                        </thead>
                        <tbody>  
                        <%for(Object[] obj:MTList){ %>
                        <tr>
                        <td><%=obj[0] %></td>
                        <td><%=obj[1] %></td>
                        <td><%if(obj[2].toString().equals("Y")){ %>USER<%} %><%if(obj[2].toString().equals("A")){ %>ADMIN<%} %>
                        <%if(obj[2].toString().equals("N")){ %>SCIENTIST E ABOVE<%} %><%if(obj[2].toString().equals("M")){ %>MTO<%} %></td>
                        </tr>
                        <%} %>
                        
            	</tbody>
            	</table>
            
            </div>
           
           
           
           </div>
           </div>
 
</div>

<div class="tab-pane fade show " id="nav-do" role="tabpanel" aria-labelledby="nav-do-tab">
                 <div class="card border-primary" style="margin-top: 10px;">
                <div class="card-header">
                    
                      
                </div>
                 
               <!-- edit and Delete -->
                <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr style="font-size: 12px; line-height: 0px;">
                                <th >NAME</th>
                                <th>DESG</th>
                                <th>ROLE</th>
                               
                            </tr>
                        </thead>
                        <tbody>  
                        <%for(Object[] obj:DOList){ %>
                        <tr>
                        <td><%=obj[0] %></td>
                        <td><%=obj[1] %></td>
                        <td><%if(obj[2].toString().equals("U")){ %>USER<%} %><%if(obj[2].toString().equals("A")){ %>ADMIN<%} %><%if(obj[2].toString().equals("O")){ %>ADMIN OFFICER<%} %></td>
                        </tr>
                        <%} %>
                        
            	</tbody>
            	</table>
            
            </div>
           
           
           
           </div>
           </div>
 
</div>

<div class="tab-pane fade show " id="nav-pis" role="tabpanel" aria-labelledby="nav-pis-tab">
                 <div class="card border-primary" style="margin-top: 10px;">
                <div class="card-header">
                    
                      
                </div>
                 
               <!-- edit and Delete -->
                <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr style="font-size: 12px; line-height: 0px;">
                                <th >NAME</th>
                                <th>DESG</th>
                                <th>ROLE</th>
                               
                            </tr>
                        </thead>
                        <tbody>  
                        <%for(Object[] obj:PISList){ %>
                        <tr>
                        <td><%=obj[0] %></td>
                        <td><%=obj[1] %></td>
                        <td><%if(obj[2].toString().equals("U")){ %>USER<%} %><%if(obj[2].toString().equals("A")){ %>ADMIN<%} %></td>
                        </tr>
                        <%} %>
                        
            	</tbody>
            	</table>
            
            </div>
           
           
           
           </div>
           </div>
 
</div>


<div class="tab-pane fade show " id="nav-mmg" role="tabpanel" aria-labelledby="nav-mmg-tab">
                 <div class="card border-primary" style="margin-top: 10px;">
                <div class="card-header">
                    
                      
                </div>
                 
               <!-- edit and Delete -->
                <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr style="font-size: 12px; line-height: 0px;">
                                <th >NAME</th>
                                <th>DESG</th>
                                <th>ROLE</th>
                               
                            </tr>
                        </thead>
                        <tbody>  
                        <%for(Object[] obj:MMGList){ %>
                        <tr>
                        <td><%=obj[0] %></td>
                        <td><%=obj[1] %></td>
                        <td><%if(obj[2].toString().equals("Y")){ %>ADMIN<%} %><%if(obj[2].toString().equals("D")){ %>DIRECTOR<%} %></td>
                        </tr>
                        <%} %>
                        
            	</tbody>
            	</table>
            
            </div>
           
           
           
           </div>
           </div>
 
</div>

<div class="tab-pane fade show " id="nav-attnd" role="tabpanel" aria-labelledby="nav-attnd-tab">
                 <div class="card border-primary" style="margin-top: 10px;">
                <div class="card-header">
                    
                      
                </div>
                 
               <!-- edit and Delete -->
                <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr style="font-size: 12px; line-height: 0px;">
                                <th >NAME</th>
                                <th>DESG</th>
                                <th>ROLE</th>
                               
                            </tr>
                        </thead>
                        <tbody>  
                        <%for(Object[] obj:ATTNDList){ %>
                        <tr>
                        <td><%=obj[0] %></td>
                        <td><%=obj[1] %></td>
                        <td><%if(obj[2].toString().equals("U")){ %>USER<%} %><%if(obj[2].toString().equals("A")){ %>ADMIN<%} %>
                        <%if(obj[2].toString().equals("D")){ %>DIRECTOR<%} %>
                        </td>
                        </tr>
                        <%} %>
                        
            	</tbody>
            	</table>
            
            </div>
           
           
           
           </div>
           </div>
 
</div>

 </div>

</div>
</div>
</div>
<div class="container-fluid" style="margin-top: 30px;">

<div class="card  border-success " >
		   <form name="myForm" id="myForm" action="PisRollAssign.htm" method="POST" >	
     <div class="card-body  " style="background-color: #ECF1F1;">
<div class="row"  >









<div class="col-md-3"  >
 <div class="form-group">
<label >MODULE
<span class="mandatory" style="color: red;">*</span>
</label>
<select  class="form-control form-control-sm selectpicker"  name="Module"  data-live-search="true" required="required" id="Module">
 <option value="" disabled="disabled" selected="selected" hidden="true" >--Select--</option>         
 
  <option value="HR">HR</option>
  <option value="MT">MT</option>
  <option value="DO">DO</option>
  <option value="PIS">PIS</option>
  <option value="LEAVE">LEAVE</option>
  <option value="TD">TD</option>
    <option value="MMG">MMG</option>
    <option value="ATTND">ATTND</option>
   </select>
</div>
</div>

<div class="col-md-3"  >
 <div class="form-group">
<label >ROLE
<span class="mandatory" style="color: red;">*</span>
</label>
<select  class="form-control form-control-sm " data-live-search="true"  name="roll" id="roll"  required="required" data-live-search="true">
 
  

   </select>
   
</div>
</div>
<div class="col-md-3"  >
 <div class="form-group">
<label >NAME:
<span class="mandatory" style="color: red;">*</span>
</label>
<select  class="form-control form-control-sm "  name="EmpId"   required="required" id="oname" >
 <%-- <option value="" disabled="disabled" selected="selected" hidden="true" >--Select--</option>         
  <%for(Object[] dlist:empList) {%>
   <option value="<%=dlist[0]%>"><%=dlist[1]%></option>
   <%} %>
 --%>
  
   </select>
</div>
</div>

<center> <button type="submit" class="btn btn-primary btn-sm"  style="margin-top: 31px;"  >SUBMIT</button>

</div>


 
</center>

</div>
</form>
</div>
</div>



</body>
<script type="text/javascript">
$(document).ready(function () {
$('#Module').on('change', function() {
	
	  var opt= this.value;
	

if(opt=="PIS"){
	 
	$("#roll")
    .empty()
    .append('<option value="U" >SELECT</option>')
    .append(' <option value="U" >USER</option>')
    .append('<option value="A" >ADMIN</option>')
    ;
	
	  
}
 
 if(opt=="LEAVE"){
	 
	$("#roll")
    .empty()
    .append('<option value="P" >SELECT</option>')
    .append(' <option value="P" >USER</option>')
    .append('<option value="A">ADMIN</option>')
    .append('<option value="O">ADMIN OFFICER</option>')
    .append('<option value="D">DIRECTOR</option>')
    ;
	  
	  
}


if(opt=="TD"){
	 
	$("#roll")
    .empty()
    .append('<option value="P" >SELECT</option>')
    .append(' <option value="P" >USER</option>')
    .append('<option value="A">ADMIN</option>')
    .append('<option value="B">ACCOUNT OFFICER</option>')
    .append('<option value="TDN">ASSOCIATE DIRECTOR</option>')
    .append('<option value="D">DIRECTOR</option>')
    ;
	  

}



if(opt=="HR"){
	 
	$("#roll")
    .empty()
    .append('<option value="HRN" >SELECT</option>')
    .append('  <option value="HRN" >USER</option>')
    .append('<option value="A">ADMIN</option>')
    .append('<option value="B">ACCOUNT OFFICER</option>')
    .append('<option value="D">DIRECTOR</option>')
    .append(' <option value="H">HR </option>')
     .append('<option value="HRS">HR HEAD</option>')

    ;

	 
}

if(opt=="MT"){
	 
	$("#roll")
    .empty()
    .append('<option value="Y" >SELECT</option>')
    .append('<option value="Y" >USER</option>')
    .append('<option value="M">ADMIN</option>')
    .append('<option value="N">SCIENTIST E ABOVE</option>')
    .append('<option value="M">MTO</option>')
    ;
  
}

if(opt=="DO"){
	 
	$("#roll")
    .empty()
    .append('<option value="U" >SELECT</option>')
    .append('<option value="U" >USER</option>')
    .append('<option value="DA">ADMIN</option>')
    .append('<option value="O">ADMIN OFFICER</option>')
    ;


} 

if(opt=="MMG"){
	 
	$("#roll")
    .empty()
    .append('<option value="MMGN" >SELECT</option>')
    .append('<option value="MMGN" >USER</option>')
    .append('<option value="MMGY">ADMIN</option>')
    .append('<option value="D">DIRECTOR</option>')
    ;


} 
if(opt=="ATTND"){
	 
	$("#roll")
    .empty()
    .append('<option value="U" >SELECT</option>')
    .append('<option value="U" >USER</option>')
    .append('<option value="A">ADMIN</option>')
    .append('<option value="D">DIRECTOR</option>')
    ;


} 


	 
	});
});

$(document).ready(function () {
	$('#roll').on('change', function() {
		
		  var opt= this.value;
		  
		  
		  var allpermissionList = eval(${permissionList});
		  var scientistelist = eval(${scientistelist});
		  var PisGHADMIN = eval(${PisGHADMIN});
		  var PisDOADMIN = eval(${PisDOADMIN});
		  var PisDir = eval(${PisDir});
		  var PisAssDir = eval(${PisAssDir});
		  
		  if(opt=="Z"){	 
				$("#oname")
			    .empty();
					        $(function(){
					        	$.each(PisAssDir, function (index, permission) {
					            
					            	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
					            	
					            });
					        });
					        } 	
		  
		  
if(opt=="O"){
				 
				
				
	$("#oname")
    .empty();
		        $(function(){
		        	$.each(PisGHADMIN, function (index, permission) {
		            
		            	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
		            	
		            });
		        });
		                   



		} 		  
		  
if(opt=="DA"){
				 
				
	$("#oname")
    .empty();
			    
		        $(function(){
		        	$.each(PisDOADMIN, function (index, permission) {
		            
		            	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
		            	
		            });
		        });
		                   



		} 		
if(opt=="D"){
				 
				
	$("#oname")
    .empty();
			    
		        $(function(){
		        	$.each(PisDir, function (index, permission) {
		            
		            	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
		            	
		            });
		        });
		                   



		} 		  
		 	  
		  
if(opt=="A"){
				 
				
				
	$("#oname")
    .empty(); 
		        $(function(){
		        	$.each(PisGHADMIN, function (index, permission) {
		            
		            	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
		            	
		            });
		        });
		                   



		} 	
if(opt=="TDN"){
	 
	
	$("#oname")
    .empty();
    
    $(function(){
    	$.each(scientistelist, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



} 
if(opt=="HRS"){
	 
	
	$("#oname")
    .empty();
    
    $(function(){
    	$.each(scientistelist, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



} 
if(opt=="MMGY"){
	 
	
	$("#oname")
    .empty();
    
    $(function(){
    	$.each(PisGHADMIN, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



} 
if(opt=="M"){
	 
	
	$("#oname")
    .empty();
    
    $(function(){
    	$.each(PisGHADMIN, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



} 
		  
		  
if(opt=="MMGN"){
				 
				
				
	$("#oname")
    .empty(); 
		        $(function(){
		        	$.each(allpermissionList, function (index, permission) {
		            
		            	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
		            	
		            });
		        });
		                   



		} 		  
if(opt=="B"){
	 
	
	$("#oname")
    .empty();
    
    $(function(){
    	$.each(allpermissionList, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



} 	
if(opt=="H"){
	 
	
	$("#oname")
    .empty();
    
    $(function(){
    	$.each(allpermissionList, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



} 
if(opt=="Y"){
	 
	
	$("#oname")
    .empty();
    
        $(function(){
        	$.each(allpermissionList, function (index, permission) {
            
            	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
            	
            });
        });
                   



} 

if(opt=="U"){
	 
	$("#oname")
    .empty();
	
    
    $(function(){
    	$.each(allpermissionList, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



}
if(opt=="HRN"){
	 
	$("#oname")
    .empty();
	
    
    $(function(){
    	$.each(allpermissionList, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



}
if(opt=="P"){
	 
	
	$("#oname")
    .empty();
    
    $(function(){
    	$.each(allpermissionList, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



}

if(opt=="N"){
	 
	$("#oname")
    .empty();
	
    
    $(function(){
    	$.each(scientistelist, function (index, permission) {
        
        	$("#oname").append("<option value='"+permission.id+"'>" + permission.name + "</option>");
        	
        });
    });
               



} 

	 
	});
});
</script>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.List"%>
  <%@page import="com.vts.ems.pi.model.PisHometown"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hometown</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
	<%
	List<Object[]> States = (List<Object[]>)request.getAttribute("States");
	PisHometown hometown =(PisHometown)request.getAttribute("Hometown");
	%>
	
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
			<%if(hometown!=null){ %>
				<h5>Hometown Edit</h5><%}else{ %>
						<h5>Hometown Add</h5><%}%>
			</div>
			   <div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				        <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				        <li class="breadcrumb-item"><a href="PIHomeTownMobile.htm">Hometown & Mobile</a> </li>
						<li class="breadcrumb-item active " aria-current="page">Hometown</li>
					</ol>
				</div>
		</div>
	</div>

<div class="page card dashboard-card"> 
	

	<div class="card-body">

		
		<div class="row">
		<div class="col-1"></div>
		<%if(hometown!=null){ %>
		<form action="HomeTownEdit.htm" method="POST" autocomplete="off" id="myform">
		<%}else{%>
		<form action="HomeTownAdd.htm" method="POST" autocomplete="off" id="myform">
		<%}%>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="card" style="width: 140%;" > 
		<div class="card-header">
		<h5>Fill Hometown Details</h5>
		</div>
			<div class="card-body"  >
			 
              <div class="row">
				        
				        <div class="col-md-5">
				        <div class="form-group">
		                     <label>Hometown:<span class="mandatory">*</span></label>
		                     <input type="text" value="<%if(hometown!=null && hometown.getHometown()!=null) {%> <%=hometown.getHometown() %> <%} %> " class="form-control input-sm" maxlength="250" id="hometown" name="hometown" required="required" placeholder="Enter HomeTown" onclick="return trim(this)" onchange="return trim(this)"> 
		                </div>
		                </div>
		                
	             <div class="col-md-4">
                    <div class="form-group">
                            <label>Nearest Railway Station:<span class="mandatory">*</span></label>
                            <input type="text" value="<%if(hometown!=null && hometown.getNearestRailwayStation()!=null) {%> <%=hometown.getNearestRailwayStation() %> <%} %>" id="nearestRailwayStation"  name="nearestRailwayStation" class="form-control input-sm" maxlength="250"  placeholder="Enter Nearest Railway Station"   required="required" onclick="return trim(this)" onchange="return trim(this)">
                    </div>
                    </div> 
		         
		                <div class="col-md-3">
                        <div class="form-group">
                              <label>State:<span class="mandatory">*</span></label>
                              <select id="state" name="state" class="form-control input-sm selectpicker" data-live-search="true" required>
                                      <%if(States!=null){ 
					                        for(Object[] O:States){%>
					                        <%if(hometown!=null){%>
					                        <option value="<%=O[1]%>" <%if(hometown!=null){if( hometown.getState().equalsIgnoreCase(O[1].toString())  ){%> selected   <%}}%> ><%=O[1]%></option>				                        
					                        <%}else{ %>
					                        <option value="<%=O[1]%>" ><%=O[1]%></option>
					                        <%}}}%>
                              </select>
                       </div>
                       </div>	                    
			        </div> 		
	         	  </div>
						<div class="row">
							<div class="col-12" align="center">
							 <div class="form-group">
							<%if(hometown!=null){ %>
							<input type="hidden" name="hometownId" value="<%=hometown.getHometownId() %>">
			                  <button type="submit" class="btn btn-sm submit-btn AddItem"	 name="Action" value="EDIT" onclick="return CommentsModel();">SUBMIT</button>
							<%}else{%>
				              <button type="submit" class="btn btn-sm submit-btn"	onclick="return CommentsModel();" name="Action" value="ADD">SUBMIT</button>
							<%}%>						
							 </div>
							</div>
						 </div>			 
						</div>								
			<%if(hometown!=null){ %>
			</form>
			<%}else{ %>
			</form>
			<%}%>	
			
		</div>
		</div>				
		</div>
</body>
<script type="text/javascript">
function CommentsModel()
{
	  var NearestRailwayStation =$("#nearestRailwayStation").val();
	  var State =$("#state").val();
	  var Hometown =$("#hometown").val();
	  
	  if(confirm('Are You Sure to Submit?')){
	  
	  if(Hometown==null || Hometown=='' || Hometown=="null" ){
			alert('Enter the Hometown!');
			return false;
		}else if(State==null || State=='' || State=="null" ){
			alert('Please Select the State!');
			return false;
		}else if(NearestRailwayStation==null || NearestRailwayStation=='' || NearestRailwayStation=="null" ){
			alert('Enter the Nearest Railway Station!');
			return false;
		}else{
			$('#myform').submit();
			return true;
		}
	  event.preventDefault;
	  }else{
		  return false;
	  }
}
</script>

</html>
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>Sets list for category: ${category.name}</h1>

<div class="boxes">

	<c:forEach items="${sets}" var="set">
	
		<div class="box">
			<div class="box-name">
				<a href="/preview-${set.id}">${set.name}</a>
			</div>
			
			<div class="dropdown">
	            <img id="three-dots" class="three-dots" src="images/three_dots_res_2.png" alt="Three dots">
	            <!-- <div id="my-dropdown" class="dropdown-content">
	                <a href="">edit</a>
	                <a href="">remove</a>
	            </div> -->
	        </div>
	
	        <div class="left-side">
	            <!-- It should be empty -->
	        </div>
			
			<br><span>words:</span>
	        <br><span>last result:</span>
	        <br><span>best result:</span>
		</div>
	
	</c:forEach>

</div>

<div style="text-align:center;">
	<a type="button" class="btn btn-success" href="/add-set-${category.id}">Add a set</a>
	<br/><br/>
	<a type="button" class="btn btn-success" href="/index">Go back</a>
</div>

<%@ include file="common/footer.jspf"%>
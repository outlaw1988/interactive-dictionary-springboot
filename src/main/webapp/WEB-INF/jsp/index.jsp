<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>Categories available:</h1>

<div class="categories">

	<c:forEach items="${categories}" var="category">
	
		<div class="category">
			<div class="category-name">
				<a>${category.name}</a>
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
			
			<br><span>sets:</span>
	        <br><span>words:</span>
	        <br><span>None <-> None</span>
		</div>
	
	</c:forEach>

</div>

<div style="text-align:center;">
	<a type="button" class="btn btn-success" href="/add-category">Add a category</a>
</div>

<%@ include file="common/footer.jspf"%>
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>Categories available:</h1>

<div class="boxes">

	<c:forEach items="${categories}" var="category" varStatus="loop">
	
		<div class="box">
			<div class="box-name">
				<a href="/category-${category.id}">${category.name}</a>
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
			
			<br><span>sets: ${setCounters[loop.index]}</span>
	        <br><span>words: ${wordCounters[loop.index]}</span>
	        <br><span>${category.defaultSrcLanguage} <-> ${category.defaultTargetLanguage}</span>
		</div>
	
	</c:forEach>

</div>

<div style="text-align:center;">
	<a type="button" class="btn btn-success" href="/add-category">Add a category</a>
</div>

<%@ include file="common/footer.jspf"%>
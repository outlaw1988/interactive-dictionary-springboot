<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>Sets list for category: ${category.name}</h1>

<div class="boxes">

	<c:forEach items="${sets}" var="set" varStatus="loop">
	
		<div class="box">
			<div class="box-name">
				<a href="/preview-${set.id}">${set.name}</a>
			</div>
			
			<div class="dropdown">
	            <img id="three-dots" class="three-dots" src="images/three_dots_res_2.png" alt="Three dots">
	            <div id="my-dropdown-${loop.index}" class="dropdown-content">
	                <a href="">remove</a>
	            </div>
	        </div>
	
	        <div class="left-side">
	            <!-- It should be empty -->
	        </div>
			
			<br><span>words: ${wordCounters[loop.index]}</span>
	        <br><span>last result: ${lastResults[loop.index]}%</span>
	        <br><span>best result: ${bestResults[loop.index]}%</span>
		</div>
	
	</c:forEach>

</div>

<div style="text-align:center;">
	<a type="button" class="btn btn-success" href="/add-set-${category.id}">Add a set</a>
	<br/><br/>
	<a type="button" class="btn btn-success" href="/index">Go back</a>
</div>

<%@ include file="common/footer.jspf"%>
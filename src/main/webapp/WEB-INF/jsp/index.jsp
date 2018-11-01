<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>List of categories:</h1>

<c:forEach items="${categories}" var="category">
	<div class="category">
		<div class="category-name">
			<a>${category.name}</a>
		</div>
	</div>
</c:forEach>

<div>
	<a class="button" href="/add-category">Add a category</a>
</div>

<%@ include file="common/footer.jspf"%>
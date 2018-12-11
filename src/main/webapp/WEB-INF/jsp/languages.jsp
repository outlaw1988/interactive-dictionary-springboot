<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th class="table-headers">Language name</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${languages}" var="language">
				<tr>
					<td class="table-words">${language.name}</td>
					<td class="table-words"><a type="button" class="btn btn-warning" 
					href="/remove-language-${language.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div style="text-align:center;">
		<a id="add-language" type="button" class="btn btn-success" href="/add-language">Add a language</a>
	</div>
</div>

<%@ include file="common/footer.jspf"%>
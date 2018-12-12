<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<form:form method="post" modelAttribute="language">
		<form:hidden path="id" />
		<fieldset class="form-group">
			<form:label path="name">Language name</form:label>
			<form:input path="name" type="text" class="form-control"
				required="required" />
			<form:errors path="name" class="error" />
		</fieldset>
		<div style="text-align:center;">
			<button id="add-language" type="submit" class="btn btn-success">Add</button>
		</div>
		<br/>
		<div style="text-align:center;">
			<a id="go-back" type="button" class="btn btn-success" href="/languages">Go back</a>
		</div>
	</form:form>
</div>

<%@ include file="common/footer.jspf"%>
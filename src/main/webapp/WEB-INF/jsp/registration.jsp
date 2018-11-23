<%@ include file="common/header.jspf"%>
<%-- <%@ include file="common/navigation.jspf"%> --%>

<div class="container">
	
	<h1>Registration form</h1>
	
	<form:form method="post" modelAttribute="user">
		
		<form:hidden path="id" />
		
		<fieldset class="form-group">
			<form:label path="username">User name:</form:label>
			<form:input path="username" type="text" class="form-control"
				required="required" />
			<form:errors path="username" class="error" />
		</fieldset>
		
		<fieldset class="form-group">
			<form:label path="password">Password:</form:label>
			<form:input path="password" type="password" class="form-control"
				required="required" />
			<form:errors path="password" class="error" />
		</fieldset>
		
		<br/><br/>
		
		<button type="submit" class="btn btn-success">Apply</button>
		
	</form:form>
	
	<span>${successMessage}</span>
	
</div>

<%@ include file="common/footer.jspf"%>
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
			<form:label path="email">Email:</form:label>
			<form:input path="email" type="text" class="form-control"
				required="required" />
			<form:errors path="email" class="error" />
		</fieldset>
		
		<fieldset class="form-group">
			<form:label path="password">Password:</form:label>
			<form:input path="password" type="password" class="form-control"
				required="required" />
			<form:errors path="password" class="error" />
		</fieldset>
		
		<fieldset class="form-group">
			<form:label path="passwordConfirm">Confirm password:</form:label>
			<form:input path="passwordConfirm" type="password" class="form-control"
				required="required" />
			<form:errors path="passwordConfirm" class="error" />
		</fieldset>
		
		<br/><br/>
		
		<button id="apply" type="submit" class="btn btn-success">Apply</button>
		
	</form:form>
	
	<span id="success-message">${successMessage}</span>
	
	<a id="go-back" type="button" href="/login" class="btn btn-success">Go to login page</a>
	
</div>

<%@ include file="common/footer.jspf"%>
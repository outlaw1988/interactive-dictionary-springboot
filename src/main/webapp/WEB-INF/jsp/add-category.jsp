<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<form:form method="post" modelAttribute="category">
		<form:hidden path="id" />
		<fieldset class="form-group">
			<form:label path="name">Category name</form:label>
			<form:input path="name" type="text" class="form-control"
				required="required" />
			<form:errors path="name" cssClass="text-warning" />
		</fieldset>
		
		<br/>
		<form:label path="defaultSrcLanguage">Default source language:</form:label>
		<br/>
		<form:select path="defaultSrcLanguage">
		    <%-- <form:option value="NONE">--SELECT--</form:option>
		    <form:options items="${languages}"></form:options> --%>
		    <option value="None">--SELECT--</option>
		    <c:forEach items="${languages}" var="language"> 
            	<option value="${language.getId()}">${language.getName()}</option> 
            </c:forEach>
		</form:select>
		<br/><br/>
		<form:label path="defaultTargetLanguage">Default target language:</form:label>
		<br/>
		<form:select  path="defaultTargetLanguage">
		    <%-- <form:option value="NONE">--SELECT--</form:option>
		    <form:options items="${languages}"></form:options> --%>
		    <option value="None">--SELECT--</option>
		    <c:forEach items="${languages}" var="language"> 
            	<option value="${language.getId()}">${language.getName()}</option> 
            </c:forEach> 
		</form:select>
		
		<br/><br/>
		
		<button type="submit" class="btn btn-success">Add</button>
	</form:form>
</div>

<%@ include file="common/footer.jspf"%>
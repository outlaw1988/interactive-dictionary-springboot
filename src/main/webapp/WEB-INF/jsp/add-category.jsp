<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<form:form method="post" modelAttribute="category">
		<form:hidden path="id" />
		<fieldset class="form-group">
			<form:label path="name">Category name:</form:label>
			<form:input path="name" type="text" class="form-control"
				required="required" />
			<form:errors path="name" class="error" />
		</fieldset>
		
		<br/>
		<form:label path="defaultSrcLanguage">Default source language:</form:label>
		<br/>
		<form:select path="defaultSrcLanguage">
		    <option value="">--SELECT--</option>
		    <c:forEach items="${languages}" var="language"> 
            	<option value="${language.getId()}">${language.getName()}</option> 
            </c:forEach>
		</form:select>
		
		<p><form:errors class="error" path="defaultSrcLanguage"/></p>
		
		<br/>
		
		<form:label path="defaultTargetLanguage">Default target language:</form:label>
		<br/>
		<form:select  path="defaultTargetLanguage">
		    <option value="">--SELECT--</option>
		    <c:forEach items="${languages}" var="language"> 
            	<option value="${language.getId()}">${language.getName()}</option> 
            </c:forEach> 
		</form:select>
		
		<p><form:errors class="error" path="defaultTargetLanguage"/></p>
		
		<br/>
		
		<form:label path="defaultTargetSide">Default target side:</form:label>
		<br/>
		<form:select path="defaultTargetSide">
			<option value="right">right</option>
			<option value="left">left</option>
		</form:select>
		
		<br/><br/>
		
		<form:label path="defaultCountdownDuration">Default countdown duration:</form:label>
		<br/>
		<form:select path="defaultCountdownDuration">
			<option value="30">30</option>
			<option value="20" selected="selected">20</option>
			<option value="10">10</option>
			<option value="5">5</option>
			<option value="0">off</option>
		</form:select>
		
		<br/><br/>
		
		<button id="add-category" type="submit" class="btn btn-success">Add</button>
	</form:form>
	
	<a id="go-back" type="button" class="btn btn-success" href="/index">Go back</a>
	
</div>

<%@ include file="common/footer.jspf"%>
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">

	<form:form method="post" modelAttribute="set">
		<form:hidden path="id" />
		<%-- <fieldset class="form-group">
			<form:label path="name">Set name:</form:label>
			<form:input path="name" type="text" class="form-control"
				required="required" />
			<form:errors path="name" cssClass="text-warning" />
		</fieldset>
		
		<br/><br/>
		
		<form:label path="defaultSrcLanguage">Target language:</form:label>
		<form:select path="defaultSrcLanguage">
		    <option value="None">--SELECT--</option>
		    <c:forEach items="${languages}" var="language"> 
            	<option value="${language.getId()}">${language.getName()}</option> 
            </c:forEach>
		</form:select>
		
		<button type="submit" class="btn btn-success">Add</button> --%>
		
		
		
	</form:form>

</div>

<%@ include file="common/footer.jspf"%>
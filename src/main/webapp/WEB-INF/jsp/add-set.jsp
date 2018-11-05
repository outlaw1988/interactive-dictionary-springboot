<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
		
	<form action="" method="post">
	
		<form:hidden path="id" />
		
		<spring:bind path="set.name">
			<span>Set name:</span>
			<input type="text" name="${status.expression}" value="${status.value}">
		</spring:bind>
		
		<br/><br/>
		
		<spring:bind path="setup.targetLanguage">
			<span>Target language:</span>
			<form:select path="setup.targetLanguage">
				<option value="${category.defaultTargetLanguage.id}" selected="selected">${category.defaultTargetLanguage}</option>
				<option value="${category.defaultSrcLanguage.id}">${category.defaultSrcLanguage}</option>
			</form:select>
		</spring:bind>
		
		<spring:bind path="setup.targetSide">
			<span>Target side:</span>
			<form:select path="setup.targetSide">
				<option value="${targetSide}" selected="selected">${targetSide}</option>
				<option value="${srcSide}">${srcSide}</option>
			</form:select>
		</spring:bind>
		
		<spring:bind path="setup.countdownDuration">
			<span>Target side:</span>
			<form:select path="setup.countdownDuration">
				<option value="30" <c:if test="${category.defaultCountdownDuration == '30'}"> selected="selected" </c:if> >30</option>
				<option value="20" <c:if test="${category.defaultCountdownDuration == '20'}"> selected="selected" </c:if> >20</option>
				<option value="10" <c:if test="${category.defaultCountdownDuration == '10'}"> selected="selected" </c:if> >10</option>
				<option value="5"  <c:if test="${category.defaultCountdownDuration == '5'}">  selected="selected" </c:if> >5</option>
			</form:select>
		</spring:bind>
		
		<br/><br/>
		
		<button type="submit" class="btn btn-success">Add</button>
	</form>
		
	

</div>

<%@ include file="common/footer.jspf"%>
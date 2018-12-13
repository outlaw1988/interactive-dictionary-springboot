<%@ include file="common/header.jspf"%>

<h1>Reset password</h1>

<c:choose>
	<c:when test="${invalidToken == true}">
		
		<div style="text-align:center;">
			<span>Invalid token</span>
		</div>
		
	</c:when>
	<c:otherwise>
	
		<div style="text-align:center;">
			<form:form method="post">
				<span>New password:</span>
				<br/>
				<input type="password" id="password" name="password" required="required"/>
				<br/>
				<span>Password confirm:</span>
				<br/>
				<input type="password" id="password-confirm" name="password-confirm" required="required"/>
				<br/>
				<div style="text-align:center;">
					<span id="password-error" class="error">${passwordError}</span>
				</div>
				<br/>
				<button id="change-password" type="submit" class="btn btn-success">Change</button>
			</form:form>
		</div>
	
	</c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf"%>
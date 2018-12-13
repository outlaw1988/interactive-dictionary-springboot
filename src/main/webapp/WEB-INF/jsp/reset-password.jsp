<%@ include file="common/header.jspf"%>

<h1>Reset password</h1>

<c:choose>
	<c:when test="${invalidToken == true}">
		
		<div style="text-align:center;">
			<span>Invalid token</span>
			<br/><br/>
			<a id="go-back" type="button" class="btn btn-success" href="/login">Go to login page</a>
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
			<div style="text-align:center;">
				<a id="go-back" type="button" class="btn btn-success" href="/login">Go to login page</a>
			</div>
		</div>
		<p style="text-align: center;">
	      	<span id="success-message">${successMessage}</span>
	    </p>
	
	</c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf"%>
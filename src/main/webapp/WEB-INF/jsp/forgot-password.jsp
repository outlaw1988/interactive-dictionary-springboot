<%@ include file="common/header.jspf"%>

<div class="container">

	<h1>Forgot password</h1>
	
	<form:form method="post">
		<div style="text-align:center;">
			<span>Please enter your email:</span>
			<br/>
			<input type="email" id="email" name="email"/>
		</div>
		<br/>
		<div style="text-align:center;">
			<button id="send-email" type="submit" class="btn btn-success">Send</button>
			<br/><br/>
			<a id="go-back" type="button" class="btn btn-success" href="/login">Go back</a>
		</div>
		
	</form:form>
	
	<br/>
	<div style="text-align:center;">
		<span id="info-message">${infoMessage}</span>
	</div>
	
</div>

<%@ include file="common/footer.jspf"%>
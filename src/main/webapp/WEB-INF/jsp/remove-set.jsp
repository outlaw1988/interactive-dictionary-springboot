<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<br>
<p>Are you sure you want to remove set: ${set.name}?</p>

<form action="?${_csrf.parameterName}=${_csrf.token}" method="post">

  <button type="submit" name="yes" class="btn btn-primary">YES</button>
  <button type="submit" name="no" class="btn btn-primary">NO</button>

</form>

<%@ include file="common/footer.jspf"%>
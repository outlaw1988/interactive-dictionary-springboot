<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<c:choose>
  <c:when test="${isInUse == true}">
    <br>
    <p>Removing is not possible, because this language is in use.</p>
    <br>
    <a href="/languages">
    	<button type="button" class="btn btn-primary">Go back</button>
    </a>
  </c:when>
  <c:otherwise>
    <br>
    <p>Are you sure you want to remove language: ${language.name}?</p>

    <form action="" method="post">

      <button type="submit" name="yes" class="btn btn-primary">YES</button>
      <button type="submit" name="no" class="btn btn-primary">NO</button>

    </form>
  </c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf"%>
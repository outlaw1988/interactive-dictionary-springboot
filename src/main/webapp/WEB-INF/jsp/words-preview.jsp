<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>Words list for set: ${set.name}</h1>

<br>

<table class="table-add-set">
  	<tr>
    	<th class="table-headers">Source language</th>
    	<th class="table-headers">Target language</th>
  	</tr>
    <tr>
    	<th class="table-headers">${set.srcLanguage}</th>
    	<th class="table-headers">${set.targetLanguage}</th>
  	</tr>
  	
  	<c:forEach items="${words}" var="word">
  		<tr>
        	<td class="table-words">${word.srcWord}</td>
        	<td class="table-words">${word.targetWord}</td>
      	</tr>
  	</c:forEach>
</table>
<br>

<div style="text-align:center;">
	<a id="perform-exam" type="button" class="btn btn-success" href="/exam-${set.id}">Perform exam</a>
</div>

<br>

<div style="text-align:center;">
	<c:choose>
		<c:when test="${set.isFree == 1}">
			<a id="go-back" type="button" class="btn btn-success" href="/free-sets">Go back</a>
		</c:when>
		<c:otherwise>
			<a id="go-back" type="button" class="btn btn-success" href="/category-${set.category.id}">Go back</a>
		</c:otherwise>
	</c:choose>
</div>

<%@ include file="common/footer.jspf"%>
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
    	<th class="table-headers">${setup.srcLanguage}</th>
    	<th class="table-headers">${setup.targetLanguage}</th>
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
	<a type="button" class="btn btn-success" href="">Perform exam</a>
</div>

<br>

<div style="text-align:center;">
	<a type="button" class="btn btn-success" href="category-${set.category.id}">Go back</a>
</div>

<%@ include file="common/footer.jspf"%>
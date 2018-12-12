<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h3>Words list for set:</h3>
<h3><span class="category-set-name">${set.name}</span></h3>

<br>

<div class="container">
	<table id="words-preview-table" class="table table-striped table-hover" 
	style="border-collapse:collapse; table-layout:fixed; width:60vw; margin: 0 auto; ">
		<thead>
		  	<tr>
		    	<th class="table-headers">Source language</th>
		    	<th class="table-headers">Target language</th>
		  	</tr>
		    <tr>
		    	<th class="table-headers">${set.srcLanguage}</th>
		    	<th class="table-headers">${set.targetLanguage}</th>
		  	</tr>
		</thead>
	  	
	  	<tbody>
		  	<c:forEach items="${words}" var="word">
		  		<tr>
		        	<td class="table-words" style="word-wrap:break-word;">${word.srcWord}</td>
		        	<td class="table-words" style="word-wrap:break-word;">${word.targetWord}</td>
		      	</tr>
		  	</c:forEach>
		</tbody>
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
</div>

<%@ include file="common/footer.jspf"%>
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<c:choose>
		<c:when test="${set.isFree == 1}">
			<h3>Exam summary for set: ${sessionScope.set.name}</h3>
		</c:when>
		<c:otherwise>
			<h3>Exam summary for category: ${sessionScope.category.name}, set: ${sessionScope.set.name}</h3>
		</c:otherwise>
	</c:choose>



<div class="container">

	<div class="center">
		<p>Your current result: <span id="last-result">${lastResult}%</span></p>
		<p>Your best result: <span id="best-result">${bestResult}%</span></p>
	</div>
	
	<table class="table table-striped table-hover" 
	style="border-collapse:collapse; table-layout:fixed; width:60vw; margin: 0 auto;">
		<thead>
			<tr>
			     <th class="table-headers">Source language</th>
			     <th class="table-headers">Target language</th>
			</tr>
			<tr>
			     <th class="table-headers">${srcLanguage}</th>
			     <th class="table-headers">${targetLanguage}</th>
			</tr>
		</thead>
	  
	    <tbody>
			<script language="javascript" type="text/javascript">
		
			    var answersList = ${sessionScope.answersList};
			    var arrayLength = answersList.length;
			    
			    var srcWords = ${srcWords};
			    var targetWords = ${targetWords};
			
			    var tableHtml = "";
			
			    for (var i = 0; i < arrayLength; i++) {
			
			        if (answersList[i] == 1){
			            tableHtml += "<tr> <td align='center' style='color: #008000; word-wrap:break-word;'>" + srcWords[i].toString() + "</td> <td align='center' style='color: #008000; word-wrap:break-word;'>" + targetWords[i].toString() + "</td> </tr>";
			        }
			        else if (answersList[i] == 0) {
			            tableHtml += "<tr> <td align='center' style='color: #ff0000; word-wrap:break-word;'>" + srcWords[i].toString() + "</td> <td align='center' style='color: #ff0000; word-wrap:break-word;'>" + targetWords[i].toString() + "</td> </tr>";
			        }
			    }
			
			    tableHtml += "</table>"
			
			    document.write(tableHtml);
	
			</script>
	  
	  	</tbody>
	  
	</table>
	
	<br> <br>
	
	<c:choose>
		<c:when test="${set.isFree == 1}">
			<a id="go-back" type="button" class="btn btn-success" href="/free-sets">Go back</a>
		</c:when>
		<c:otherwise>
			<a id="go-back" type="button" class="btn btn-success" href="/category-${sessionScope.category.id}">Go back</a>
		</c:otherwise>
	</c:choose>

</div>

<%@ include file="common/footer.jspf"%>
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h3>Exam summary for category: ${sessionScope.category.name}, set: ${sessionScope.set.name}</h3>

<div class="container">

	<div class="center">
		<p>Your current result: ${lastResult}%</p>
		<p>Your best result: ${bestResult}%</p>
	</div>
	
	<table class="table-add-set">
	  <tr>
	      <th class="table-headers">Source language</th>
	      <th class="table-headers">Target language</th>
	  </tr>
	    <tr>
	      <th class="table-headers">${srcLanguage}</th>
	      <th class="table-headers">${targetLanguage}</th>
	  </tr>
	  
	<script language="javascript" type="text/javascript">
	
	    var answersList = ${sessionScope.answersList};
	    var arrayLength = answersList.length;
	    console.log("Answers list: " + answersList);
	    
	    var srcWords = ${srcWords};
	    var targetWords = ${targetWords};
	
	    var tableHtml = "";
	
	    for (var i = 0; i < arrayLength; i++) {
	
	        if (answersList[i] == 1){
	            tableHtml += "<tr> <td align='center' style='color: #008000;'>" + srcWords[i].toString() + "</td> <td align='center' style='color: #008000;'>" + targetWords[i].toString() + "</td> </tr>";
	        }
	        else if (answersList[i] == 0) {
	            tableHtml += "<tr> <td align='center' style='color: #ff0000;'>" + srcWords[i].toString() + "</td> <td align='center' style='color: #ff0000;'>" + targetWords[i].toString() + "</td> </tr>";
	        }
	    }
	
	    tableHtml += "</table>"
	
	    document.write(tableHtml);
	
	</script>
	  
	</table>
	
	<br> <br>
	
	<a type="button" class="btn btn-success" href="/category-${sessionScope.category.id}">Go back</a>

</div>

<%@ include file="common/footer.jspf"%>
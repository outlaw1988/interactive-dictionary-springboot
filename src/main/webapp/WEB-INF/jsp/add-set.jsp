<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
		
	<form:form method="post" modelAttribute="set">
	
		<form:hidden path="id" />
		
		<fieldset class="form-group">
			<form:label path="name">Set name:</form:label>
			<form:input path="name" type="text" class="form-control"
				required="required" />
			<form:errors path="name" class="error" />
		</fieldset>
		
		<br/>
		
		<span>Target language:</span>
		<form:select id="target-language" path="targetLanguage" >
			<option value="${category.defaultTargetLanguage.id}" selected="selected">${category.defaultTargetLanguage}</option>
			<option value="${category.defaultSrcLanguage.id}">${category.defaultSrcLanguage}</option>
		</form:select>
		
		<span>Target side:</span>
		<form:select id="target-side" path="targetSide">
			<option value="${targetSide}" selected="selected">${targetSide}</option>
			<option value="${srcSide}">${srcSide}</option>
		</form:select>
		
		<span>Countdown duration:</span>
		<form:select path="countdownDuration">
			<option value="30" <c:if test="${category.defaultCountdownDuration == '30'}"> selected="selected" </c:if> >30</option>
			<option value="20" <c:if test="${category.defaultCountdownDuration == '20'}"> selected="selected" </c:if> >20</option>
			<option value="10" <c:if test="${category.defaultCountdownDuration == '10'}"> selected="selected" </c:if> >10</option>
			<option value="5"  <c:if test="${category.defaultCountdownDuration == '5'}">  selected="selected" </c:if> >5</option>
		</form:select>
				
		<br/><br/>
		
		<table id="set_def_table" class="table-add-set">
	      	<tr>
	        	<th class="table-headers">
	          		<span id="left-label">
		          		<c:choose>
						    <c:when test="${targetSide == 'left'}">
						        Target language 
						        <br />
						    </c:when>    
						    <c:otherwise>
						        Source language
						        <br />
						    </c:otherwise>
						</c:choose>
	          		</span>
	        	</th>
		        <th class="table-headers">
			        <span id="right-label">
		          		<c:choose>
						    <c:when test="${targetSide == 'left'}">
						        Source language
						        <br />
						    </c:when>    
						    <c:otherwise>
						        Target language
						        <br />
						    </c:otherwise>
						</c:choose>
	          		</span>
		        </th>
	      	</tr>
		    <tr>
		        <th class="table-headers">
			        <span id="left-lan">
		          		<c:choose>
						    <c:when test="${targetSide == 'left'}">
						        ${category.defaultTargetLanguage}
						        <br />
						    </c:when>    
						    <c:otherwise>
						        ${category.defaultSrcLanguage}
						        <br />
						    </c:otherwise>
						</c:choose>
	          		</span>
		        </th>
		        <th class="table-headers">
		        	<span id="right-lan">
		          		<c:choose>
						    <c:when test="${targetSide == 'left'}">
						        ${category.defaultSrcLanguage}
						        <br />
						    </c:when>    
						    <c:otherwise>
						        ${category.defaultTargetLanguage}
						        <br />
						    </c:otherwise>
						</c:choose>
	          		</span>
		        </th>
		    </tr>
	
			<c:forEach begin="1" end="10" varStatus="loop">
			    <tr>
			    	<%-- <td>${loop.index}</td> --%>
	                <td class="table-words"><input type="text" name="left_field_${loop.index}" id="left_field_${loop.index}"/></td>
	                <td class="table-words"><input type="text" name="right_field_${loop.index}" id="right_field_${loop.index}"/> <img src="images/remove_icon_res.png" onclick="removeWords(this)"></td>
	            </tr>
			</c:forEach>

	    </table>
    
    	<br/><br/>
    
    	<button type="button" class="btn btn-success" onclick="addWord()">Add word</button>
    	
    	<br/><br/>

		<button type="submit" class="btn btn-success">Add</button>
		
		<br/><br/>
		
		<a type="button" class="btn btn-success" href="/category-${category.id}">Go back</a>
	</form:form>
		
</div>

<script language="javascript" type="text/javascript">

	var idx = 10;
	var currTargetSide = "${targetSide}";
    var currTargetLan = "${category.defaultTargetLanguage}";
    var currSourceLan = "${category.defaultSrcLanguage}";

	function addWord() {
	    idx += 1;
	
	    var table = document.getElementById("set_def_table");
	    var row = table.insertRow(-1);
	    var cell1 = row.insertCell(0);
	    var cell2 = row.insertCell(1);
	    cell1.innerHTML = "<input type='text' name='left_field_" + idx + "' id='left_field_" + idx + "'/>";
	    cell1.align = "center";
	    cell2.innerHTML = "<input type='text' name='right_field_" + idx + "' id='right_field_" + idx + "'/>";
	    cell2.innerHTML += " <img src='images/remove_icon_res.png' onclick='removeWords(this)'>"
	    cell2.align = "center";
	}

	function removeWords(btn) {
	    var row = btn.parentNode.parentNode;
	    row.parentNode.removeChild(row);
	}
	
	function swapFields() {
        for (var i = 1; i <= idx; i++) {
            var check = document.getElementById("left_field_" + i);
            if (check) {
                // swapping
                temp = document.getElementById("left_field_" + i).value;
                document.getElementById("left_field_" + i).value = document.getElementById("right_field_" + i).value;
                document.getElementById("right_field_" + i).value = temp;
            }
        }
    }

    $('#target-language').on('change', function (e) {
    	
        console.log("Target language changed!!");
        
        var temp = currTargetLan;
        currTargetLan = currSourceLan;
        currSourceLan = temp;

        if (currTargetSide == "left") {
            document.getElementById("left-lan").innerHTML = currTargetLan;
            document.getElementById("right-lan").innerHTML = currSourceLan;
        }
        else if (currTargetSide == "right") {
          document.getElementById("left-lan").innerHTML = currSourceLan;
          document.getElementById("right-lan").innerHTML = currTargetLan;
        }

        swapFields();
    });

    $('#target-side').on('change', function (e) {
    	
    	console.log("Target side changed!!");
    	
        currTargetSide = this.value;

        if (currTargetSide == "left") {
            document.getElementById("left-label").innerHTML = "Target language";
            document.getElementById("right-label").innerHTML = "Source language";

            document.getElementById("left-lan").innerHTML = currTargetLan;
            document.getElementById("right-lan").innerHTML = currSourceLan;
        }
        else if (currTargetSide == "right") {
          document.getElementById("left-label").innerHTML = "Source language";
          document.getElementById("right-label").innerHTML = "Target language";

          document.getElementById("left-lan").innerHTML = currSourceLan;
          document.getElementById("right-lan").innerHTML = currTargetLan;
        }

        swapFields();
    });
	
</script>

<%@ include file="common/footer.jspf"%>
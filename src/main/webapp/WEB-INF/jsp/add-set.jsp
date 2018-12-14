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
		
		<c:choose>
			<c:when test="${sessionScope.hasErrorMode == true}">
			
				<span>Target language:</span>
				<form:select id="target-language" path="targetLanguage" >
					<option value="${set.targetLanguage.id}" selected="selected">${set.targetLanguage}</option>
					<option value="${set.srcLanguage.id}">${set.srcLanguage}</option>
				</form:select>
			
			</c:when>
			<c:otherwise>
			
				<span>Target language:</span>
				<form:select id="target-language" path="targetLanguage" >
					<option value="${category.defaultTargetLanguage.id}" selected="selected">${category.defaultTargetLanguage}</option>
					<option value="${category.defaultSrcLanguage.id}">${category.defaultSrcLanguage}</option>
				</form:select>
				
			</c:otherwise>
		</c:choose>
		
		<span>Target side:</span>
		<form:select id="target-side" path="targetSide">
			<option value="${targetSide}" selected="selected">${targetSide}</option>
			<option value="${srcSide}">${srcSide}</option>
		</form:select>
		
		<c:choose>
		
			<c:when test="${sessionScope.hasErrorMode == true}">
			
				<span>Countdown duration:</span>
				<form:select path="countdownDuration">
					<option value="30" <c:if test="${set.countdownDuration == '30'}"> selected="selected" </c:if> >30</option>
					<option value="20" <c:if test="${set.countdownDuration == '20'}"> selected="selected" </c:if> >20</option>
					<option value="10" <c:if test="${set.countdownDuration == '10'}"> selected="selected" </c:if> >10</option>
					<option value="5"  <c:if test="${set.countdownDuration == '5'}">  selected="selected" </c:if> >5</option>
				</form:select>
			
			</c:when>
			
			<c:otherwise>
			
				<span>Countdown duration:</span>
				<form:select path="countdownDuration">
					<option value="30" <c:if test="${category.defaultCountdownDuration == '30'}"> selected="selected" </c:if> >30</option>
					<option value="20" <c:if test="${category.defaultCountdownDuration == '20'}"> selected="selected" </c:if> >20</option>
					<option value="10" <c:if test="${category.defaultCountdownDuration == '10'}"> selected="selected" </c:if> >10</option>
					<option value="5"  <c:if test="${category.defaultCountdownDuration == '5'}">  selected="selected" </c:if> >5</option>
				</form:select>
			
			</c:otherwise>
		
		</c:choose>
		
		<br/><br/>
		
		<div class="import-words">
			<p>
			
			<span>Import words from file:</span>
			
			<br/>
			
			<input type="file" id="upload" name="upload" onchange='openFile(event)'/>
					
			<span>Separator:</span>
			<select id="separator">
			    <option value=";">;</option>
			    <option value=",">,</option>
			</select>
			
			</p>
		</div>
				
		<br/><br/>
		
		<table id="set_def_table" class="table-add-set">
	      	<tr>
	      		<th></th>
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
		        <th></th>
	      	</tr>
		    <tr>
		    	<th></th>
		        <th class="table-headers">
			        <span id="left-lan">
		          		
		          		<c:choose>
		          		
		          			<c:when test="${sessionScope.hasErrorMode == true}">
		          			
		          				<c:choose>
								    <c:when test="${targetSide == 'left'}">
								        ${set.targetLanguage}
								        <br />
								    </c:when>    
								    <c:otherwise>
								        ${set.srcLanguage}
								        <br />
								    </c:otherwise>
								</c:choose>
		          			
		          			</c:when>
		          			
		          			<c:otherwise>
		          			
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
		          			
		          			</c:otherwise>
		          		
		          		</c:choose>
		          		
		          		
	          		</span>
		        </th>
		        <th class="table-headers">
		        	<span id="right-lan">
		        	
		        		<c:choose>
		          		
		          			<c:when test="${sessionScope.hasErrorMode == true}">
		          			
		          				<c:choose>
								    <c:when test="${targetSide == 'left'}">
								        ${set.srcLanguage}
								        <br />
								    </c:when>    
								    <c:otherwise>
								        ${set.targetLanguage}
								        <br />
								    </c:otherwise>
								</c:choose>
		          			
		          			</c:when>
		          			
		          			<c:otherwise>
		          			
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
		          			
		          			</c:otherwise>
		          		
		          		</c:choose>
		      
	          		</span>
		        </th>
		        <th></th>
		    </tr>
			
			<c:choose>
				<c:when test="${sessionScope.hasErrorMode == true}">
					<c:forEach items="${words}" var="word" varStatus="loop">
		    
				    	<c:choose>
				    		<c:when test="${targetSide == 'left'}">
				    			<tr class="words-row">
				    				<td>${loop.index + 1}</td>
				    				<td class="table-words"><input type="text" name="left_field_${loop.index + 1}" id="left_field_${loop.index + 1}" value="${word.get(1)}"/></td>
		                			<td class="table-words"><input type="text" name="right_field_${loop.index + 1}" id="right_field_${loop.index + 1}" value="${word.get(0)}"/></td>
		                			<td><img src="images/remove_icon_res.png" onclick="removeWords(this)"></td>
				    			</tr>
				    		</c:when>
				    		<c:otherwise>
				    			<tr class="words-row">
				    				<td>${loop.index + 1}</td>
				    				<td class="table-words"><input type="text" name="left_field_${loop.index + 1}" id="left_field_${loop.index + 1}" value="${word.get(0)}"/></td>
		                			<td class="table-words"><input type="text" name="right_field_${loop.index + 1}" id="right_field_${loop.index + 1}" value="${word.get(1)}"/></td>
		                			<td><img src="images/remove_icon_res.png" onclick="removeWords(this)"></td>
				    			</tr>
				    		</c:otherwise>
				    	</c:choose>
				    
				    </c:forEach>
				</c:when>
				
				<c:otherwise>
				
					<c:forEach begin="1" end="10" varStatus="loop">
					    <tr class="words-row">
					    	<td><span>${loop.index}</span></td>
			                <td class="table-words"><input type="text" name="left_field_${loop.index}" id="left_field_${loop.index}"/></td>
			                <td class="table-words"><input type="text" name="right_field_${loop.index}" id="right_field_${loop.index}"/></td>
			                <td><img src="images/remove_icon_res.png" onclick="removeWords(this)"></td>
			            </tr>
					</c:forEach>
					
				</c:otherwise>
				
			</c:choose>
			
			

	    </table>
    
    	<br/><br/>
    
    	<div style="text-align:center;">
    		<button id="add-word" type="button" class="btn btn-success" onclick="addWord()">Add word</button>
    	</div>
    	<br/><br/>

		<button id="add-set" type="submit" class="btn btn-success">Add</button>
		
		<br/><br/>
		
		<a id="go-back" type="button" class="btn btn-success" href="/category-${category.id}">Go back</a>
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
	    row.className = "words-row";
	    var cell0 = row.insertCell(0);
	    var cell1 = row.insertCell(1);
	    var cell2 = row.insertCell(2);
	    var cell3 = row.insertCell(3);
	    cell1.innerHTML = "<input type='text' name='left_field_" + idx + "' id='left_field_" + idx + "'/>";
	    cell1.align = "center";
	    cell2.innerHTML = "<input type='text' name='right_field_" + idx + "' id='right_field_" + idx + "'/>";
	    cell2.align = "center";
	    cell3.innerHTML = " <img src='images/remove_icon_res.png' onclick='removeWords(this)'>"
	    
	    generateIndexes();
	}
	
	function generateIndexes() {
		var table = document.getElementById("set_def_table");
		for (var i = 2, row; row = table.rows[i]; i++) {
		   row.cells[0].innerHTML = (i - 1);
		}
	}

	function removeWords(btn) {
	    var row = btn.parentNode.parentNode;
	    row.parentNode.removeChild(row);
	    generateIndexes();
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
    
    var openFile = function(event) {
        var input = event.target;

        var reader = new FileReader();
        reader.onload = function(){
          var text = reader.result;        
          var lines = text.split('\n');
          
          $('.words-row').remove();
          idx = 0;
          var separator = document.getElementById("separator").value;

          for(var line = 0; line < lines.length; line++){
              addWord();
             
        	  var arr = lines[line].split(separator);
        	  var srcWord = arr[0];
        	  var targetWord = arr[1];
        	  
        	  document.getElementById("left_field_" + (line + 1)).value = srcWord;
        	  document.getElementById("right_field_" + (line + 1)).value = targetWord;
          }
        };
        reader.readAsText(input.files[0]);
      };
	
</script>

<%@ include file="common/footer.jspf"%>
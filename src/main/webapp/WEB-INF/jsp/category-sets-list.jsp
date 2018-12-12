<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>Sets list for category: ${category.name}</h1>

<div class="boxes">

	<c:forEach items="${sets}" var="set" varStatus="loop">
	
		<div class="box">
			<div class="box-name">
				<a href="/preview-${set.id}"><span class="box-label">${set.name}</span></a>
			</div>
			
			<div class="dropdown" onclick="dropDown(${loop.index})">
	            <img id="three-dots" class="three-dots" src="images/three_dots_res_2.png" alt="Three dots">
	            <div id="my-dropdown-${loop.index}" class="dropdown-content">
	            	<a href="/update-set-${set.id}">edit</a>
	                <a href="/remove-set-${set.id}">remove</a>
	            </div>
	        </div>
	
	        <div class="left-side">
	            <!-- It should be empty -->
	        </div>
			
			<div>
				<span>words: <span id="words-num">${wordCounters[loop.index]}</span></span>
		        <br><span>last result: <span id="last-result-${loop.index}">${lastResults[loop.index]}%</span></span>
		        <br><span>best result: <span id="best-result-${loop.index}">${bestResults[loop.index]}%</span></span>
		        <br><span>${srcLanguages[loop.index]} -> ${targetLanguages[loop.index]}</span>
	        </div>
		</div>
	
	</c:forEach>

</div>

<div style="text-align:center;">
	<a id="add-set" type="button" class="btn btn-success" href="/add-set-${category.id}">Add a set</a>
	<br/><br/>
	<a id="go-back" type="button" class="btn btn-success" href="/index">Go back</a>
</div>

<script language="javascript" type="text/javascript">

    var currCounter = "";

    function dropDown(counter) {
        currCounter = counter;
        document.getElementById("my-dropdown-" + counter).style.display = "block";
    }

    window.onclick = function(event) {
        //console.log('.three-dots-' + currCounter);
        if (!event.target.matches('.three-dots')) {
            var dropdowns = document.getElementsByClassName("dropdown-content");
            var i;
            for (i = 0; i < dropdowns.length; i++) {
                var openDropdown = dropdowns[i];
                openDropdown.style.display = "none";
                /* if (openDropdown.classList.contains('show')) {
                    openDropdown.classList.remove('show');
                } */
          }
        }
    }

</script>

<%@ include file="common/footer.jspf"%>
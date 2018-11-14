<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h1>Sets list for category: ${category.name}</h1>

<div class="boxes">

	<c:forEach items="${sets}" var="set" varStatus="loop">
	
		<div class="box">
			<div class="box-name">
				<a href="/preview-${set.id}">${set.name}</a>
			</div>
			
			<div class="dropdown" onclick="dropDown(${loop.index})">
	            <img id="three-dots" class="three-dots" src="images/three_dots_res_2.png" alt="Three dots">
	            <div id="my-dropdown-${loop.index}" class="dropdown-content">
	                <a href="/remove-set-${set.id}">remove</a>
	            </div>
	        </div>
	
	        <div class="left-side">
	            <!-- It should be empty -->
	        </div>
			
			<br><span>words: ${wordCounters[loop.index]}</span>
	        <br><span>last result: ${lastResults[loop.index]}%</span>
	        <br><span>best result: ${bestResults[loop.index]}%</span>
	        <br><span>${srcLanguages[loop.index]} -> ${targetLanguages[loop.index]}</span>
		</div>
	
	</c:forEach>

</div>

<div style="text-align:center;">
	<a type="button" class="btn btn-success" href="/add-set-${category.id}">Add a set</a>
	<br/><br/>
	<a type="button" class="btn btn-success" href="/index">Go back</a>
</div>

<script language="javascript" type="text/javascript">

    var currCounter = "";

    function dropDown(counter) {
        //console.log("Dropdown clicked... " + counter);
        currCounter = counter;
        //console.log("my-dropdown-" + counter);
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
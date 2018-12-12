<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h3>Categories available:</h3>

<div class="boxes">

	<c:forEach items="${categories}" var="category" varStatus="loop">
	
		<div class="box">
			<div class="box-name" >
				<a href="/category-${category.id}"><span class="box-label">${category.name}</span></a>
			</div>
			
			<div class="dropdown" onclick="dropDown(${loop.index})">
	            <img id="three-dots" class="three-dots" src="images/three_dots_res_2.png" alt="Three dots">
	            <div id="my-dropdown-${loop.index}" class="dropdown-content">
	                <a href="/update-category-${category.id}">edit</a>
	                <a href="/remove-category-${category.id}">remove</a>
	            </div>
	        </div>
	
	        <div class="left-side">
	            <!-- It should be empty -->
	        </div>
			
			<div>
				<span>sets: ${setCounters[loop.index]}</span>
		        <br><span>words: ${wordCounters[loop.index]}</span>
		        <br><span>${category.defaultSrcLanguage} <-> ${category.defaultTargetLanguage}</span>
		    </div>
		</div>
	
	</c:forEach>

</div>

<div style="text-align:center;">
	<a id="add-category" type="button" class="btn btn-success" href="/add-category">Add a category</a>
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
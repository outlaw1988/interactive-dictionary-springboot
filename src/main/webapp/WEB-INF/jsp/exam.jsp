<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<h3>Exam is performing for category: ${sessionScope.category.name}, set: ${sessionScope.set.name}</h3>
<div class="container">
	<br><br>

	<div class="progress">
	  <div id="progress-bar" class="progress-bar" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width:100%">
	    <span class="sr-only">70% Complete</span>
	  </div>
	</div>
	
	<div class="center">
	    <span id="secs-left">5 second(s) left</span>
	
		<br><br>
		
		<span id="curr_word_num">${sessionScope.currWordIdxToShow}</span>/${sessionScope.size}
		
		<br><br>
		
		Translate word: <span id="src_word">${srcWord}</span>
		
		<br><br>
		
		<input type="text" name="answer" id="answer" autofocus/>
		<button type="button" onclick="checkWord()" id="check-button">Check</button>
		<button type="button" onclick="nextWord()" id="next-button">Next</button>
		
		<br><br>
		
		<div id="succ_or_fail" class="answer_list" ></div>
		
		<br><br>
		
		<a type="button" class="btn btn-success" href="/category-${sessionScope.category.id}">Cancel</a>
	
	</div>

</div>

<script type="text/javascript"> 

var currWordNum = 0;
var size = ${sessionScope.size};
var timeOutControl;
var countdownDuration = "${sessionScope.countdownDuration}";

$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

var checkWord = function () {
	 //console.log("Check word called from javascript...");
	 stopTimeout();
	 document.getElementById("answer").disabled = true;
	 
	 var data = {
	     "answer": $("#answer").val()
	 }
	
	 var json = JSON.stringify(data);
	 
	 $.ajax ({
	     url: "/exam-check",
	     type: "POST",
	     data: json,
	     dataType: "json",
	     contentType: "application/json; charset=utf-8",
	     success: function(response){
	         
	         if (response.message == "OK") {
	             document.getElementById("succ_or_fail").innerHTML = response.message;
	             document.getElementById("succ_or_fail").style.color = 'green'
	         }
	         else {
	             document.getElementById("succ_or_fail").innerHTML = response.message;
	             document.getElementById("succ_or_fail").style.color = 'red'
	         }
	     },
	    error: function () {
	        console.log("Error message!!!!");
	    }
	 });
  
}

var nextWord = function() {
    stopTimeout();
    progress(countdownDuration, countdownDuration);

    document.getElementById("answer").disabled = false;
    document.getElementById("succ_or_fail").innerHTML = "";
    document.getElementById("answer").value = "";
    document.getElementById("answer").focus();

    var url = "exam-next";
    var data = {
        "request": "",
    }

    var json = JSON.stringify(data);

	if (currWordNum < (size - 1)) {
        
        $.ajax ({
            url: "/exam-next",
            type: "POST",
            data: json,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(response){
            	currWordNum += 1
                document.getElementById("curr_word_num").innerHTML = response.wordIdxToShow;
                document.getElementById("src_word").innerHTML = response.srcWord;
            },
    	    error: function () {
    	        //console.log("Error message!!!!");
    	    }
        });
        
    } else if (currWordNum == (size - 1)) {
        
        $.ajax ({
            url: "/exam-next",
            type: "POST",
            data: json,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            /*success: function(response){
             	//empty
            },
    	    error: function () {
    	        console.log("Error message!!!!");
    	    } */
        });
        
        setTimeout(function(){
        	loadUrl("/exam-summary");
        }, 500);
    }

}

function loadUrl(newLocation) {
    window.location = newLocation;
    return false;
}

/* window.onkeyup = function(e) {
    var key = e.keyCode ? e.keyCode : e.which;

    // enter
    if (key == 13) {
        checkWord();
    // control TODO not work
    } else if (key == 17) {
        nextWord();
    }

} */

document.onkeyup = function (event) {
	if (event.which == 13 || event.keyCode == 13) {
		checkWord();
	} else if (event.which == 17 || event.keyCode == 17) {
		nextWord();
	}
};

function progress(timeleft, timetotal) {
    var percent = (timeleft / timetotal) * 100;
    // console.log("Time left: " + timeleft);
    // console.log("Percent left: " + percent);
    var percentStr = percent.toString() + "%";
    document.getElementById("progress-bar").style.width = percentStr;
    document.getElementById("secs-left").innerHTML = timeleft.toString() + " second(s) left";
    if(timeleft >= 0) {
        timeOutControl = setTimeout(function() {
            progress(timeleft - 1, timetotal);
        }, 1000);
    }
    else {
        nextWord();
    }
};

progress(countdownDuration, countdownDuration);

function stopTimeout() {
    clearTimeout(timeOutControl);
}

</script>

<%@ include file="common/footer.jspf"%>
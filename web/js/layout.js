
var DOM = [];
var headerHeightRatio = 0.9;
//var contentWidth = 0.6;

function initDOM(){
	
	DOM["wraper"] = document.getElementById("wraper");
	
	DOM["top_banner_content"] = document.getElementById("top_banner_content");
	DOM["header"] = document.getElementById("header");
	DOM["header_content"] = document.getElementById("header_content");
	DOM["register"] = document.getElementById("register");
	
	DOM["body_content"] = document.getElementById("body_content");
	
	
	window.addEventListener( 'resize', onWindowResize, false );
	window['onresize'] = onWindowResize;
	onWindowResize();
	
}

function onWindowResize(){

	// start page
	if(DOM["header"] != null){
		var height = window.innerHeight;
		var headerH = (height*headerHeightRatio) + "px";
		DOM["header"].style.height = headerH;
		DOM["register"].style.marginTop = ((height*headerHeightRatio) - height*0.3) + "px";

	}
	
}
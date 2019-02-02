function test(color, indexR, indexC){
	
	indexC = indexC + 1;
	var costID = 'cost' + indexR + 'c' + indexC;
	var timeID = 'time' + indexR + 't' + indexC;
	

	if(color == 1){
		document.getElementById(costID).style.backgroundColor = "#BE9EC9"; // purple
		document.getElementById(timeID).style.backgroundColor = "#BE9EC9";
	}
	if(color == 2){
		document.getElementById(costID).style.backgroundColor = "#6F9FD8"; // blue 
		document.getElementById(timeID).style.backgroundColor = "#6F9FD8";
	}

}


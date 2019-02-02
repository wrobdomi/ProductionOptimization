var x_graph_position_Task = 0;
var y_graph_position_Task = 75;


var x_graph_position_Machine = 0;
var y_graph_position_Machine = 450;

var color = '#' + Math.floor(Math.random() * 10);
var letters = ['a' , 'b', 'c' , 'd', 'e' , 'f'];


function drawTasks(id, details){
		
	x_graph_position_Task = x_graph_position_Task + 65;
	//y_graph_position = y_graph_position + 50;
	
    var nodeId = "#node";
    nodeId = nodeId.concat(id);
    
    var label = 'Id: ' + id + '\n D: ' + details;
    	
	cy.add(
		{ data: { id: 'node' + id } }
    );
    
    cy.$(nodeId).position('x', x_graph_position_Task);
	cy.$(nodeId).position('y', y_graph_position_Task);
	cy.$(nodeId).style('background-color', '#E0E0E0');
	// cy.$(nodeId).style('shape', 'hexagon');
	cy.$(nodeId).style('text-wrap', 'wrap');
	cy.$(nodeId).style('label', label);
	
	if(x_graph_position_Task==1040){
		console.log('This is y graph position: ');
		console.log(y_graph_position_Task);
		y_graph_position_Task = y_graph_position_Task + 90;
		x_graph_position_Task = 0;
	}	
		
		
}


function drawMachines(id, detailCost, detailTime){
	
	x_graph_position_Machine = x_graph_position_Machine + 70;
	//y_graph_position = y_graph_position + 50;
	
    var nodeId = "#node";
    var machineNode = "m";
    nodeId = nodeId.concat(id).concat(machineNode);
    
    var label = 'Id: ' + id + '\n C: ' + detailCost + '\n T: ' + detailTime;
    
	cy.add(
		{ data: { id: 'node' + id + machineNode } }
    );
    
    cy.$(nodeId).position('x', x_graph_position_Machine);
	cy.$(nodeId).position('y', y_graph_position_Machine);
	cy.$(nodeId).style('background-color', '#7e3878');
	cy.$(nodeId).style('shape', 'rectangle');
	cy.$(nodeId).style('text-wrap', 'wrap');
	cy.$(nodeId).style('label', label);
	
	if(x_graph_position_Machine==1050){
		y_graph_position_Machine = y_graph_position_Machine + 100;
		x_graph_position_Machine = 0;
	}
	
}

function drawConnections(taskId, machineId){
	  
	  var source = 'node' + taskId;
	  var target = 'node' + machineId + 'm';
	  var edgeId = '#edge' + 't' + taskId + 'm' + machineId;
	
	    
	  cy.add({
	        data: {
	            id: 'edge' + 't' + taskId + 'm' + machineId,
	            source: source,
	            target: target
	        }
	    });
	  
	  cy.$(edgeId).style('line-color', 'gray' );
}


function getRandomColor(){
	
	color = '#' + Math.floor(Math.random() * 10);
	
	for( i=0; i<5 ; i++) {
		
		// get random 0 or 1
		var choice = Math.floor(Math.random() * 2);
		
		if(choice == 1){
			// get random 0 to 9
			var num = Math.floor(Math.random() * 10);
			color = color + num;
		}
		else{
			// get random 0 to 5
			var num = Math.floor(Math.random() * 6);
			color = color + letters[num];
		}
		
	}
	
	return color;
	
}


	
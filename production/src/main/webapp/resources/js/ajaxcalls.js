var costbox = false;
var timebox = false;

function setCostCon(){
	costbox = !costbox;	
}

function setTimeCon(){
	timebox = !timebox;	
}

function callmodeltwo() {
	
	var criteria = {
			temperature:  $("#temperature").val(),
			coolingWay: $("#cooling").val(),
			alpha: $("#alpha").val(),
			iterations: $("#iterations").val(),
			assignments: $("#assignments").val(),
			cWeight: $("#cWeight").val(),
			tWeight: $("#tWeight").val(),
			costConEnabled: costbox,
			timeConEnabled: timebox,
			costCon: $("#costCon").val(),
			timeCon: $("#timeCon").val()
	};
	
	$.ajax({
        type: "POST",
        data: JSON.stringify(criteria),
        url: "http://localhost:8082/EngineeringDegreeApp/annealingtwo/solve",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success :function(response) {
            try {
                // console.log('Successfully received');
                
            	// console.log("Value of costCon and timeCon are: ");
            	// console.log($("#costCon").val());
            	// console.log($("#timeCon").val());
            	
            	
            	cy.remove('edge');
                $("#solutionMatrix").empty();
                $("#chartsPlace1").empty();
                $("#chartsPlace2").empty();
                $("#chartsPlace3").empty();
                $("#chartsPlace4").empty();
                $("#chartsPlace5").empty();
           
                
                $("#solutionMatrix").append("<h3 class='main-title'>Solution</h3>");
                $("#solutionMatrix").append("<hr>");
                $("#solutionMatrix").append("<div class='table-caption'>Best found solution</div>");
                $("#solutionMatrix").append("<table id='solutionTable' style='border: 1px solid black; border-collapse: collapse; margin: auto;''> </table>");
                
               
                $("#solutionTable").append("<tr id='solutionRow0' style='border: 1px solid black; border-collapse: collapse;'> </tr>");
				$("#solutionTable").append("<tr id='solutionRow1' style='border: 1px solid black; border-collapse: collapse;'> </tr>");
                $("#solutionTable").append("<tr id='solutionRow2' style='border: 1px solid black; border-collapse: collapse;'> </tr>");
                
                for(var i = 0 ; i < response.solution[0].length ; i++){
                	var val = response.taskOffset + i;
                	// console.log(val);
                	$("#solutionRow0").append("<td style='border: 1px solid black; border-collapse: collapse; text-align: center; width:25px; background-color:#82b74b' >" + val + "</td>");
                }
                
                for(var i =0 ; i < response.solution.length ; i++){
                	for( var j = 0; j < response.solution[0].length ; j++ ){
                	
                		
                		if(i == 0){
                			$("#solutionRow1").append("<td style='border: 1px solid black; border-collapse: collapse; text-align: center; width:25px;' >" + response.solution[i][j] + "</td>");
                		}
                		if(i == 1){
               			 	$("#solutionRow2").append("<td style='border: 1px solid black; border-collapse: collapse; text-align: center; width:25px; ' >" + response.solution[i][j] + "</td>");
                		}
         
                	}
                     
                }
                
                 
               for(var i = 0 ; i < response.solution[0].length; i++ ){
            	   
            	   var tID = response.taskOffset + i; 
            	   
            	   for( var j = 0; j <  response.solution.length ; j++ ){
            		   
            		  console.log(i + ' ' + j);
            		   
            		   if(response.solution[j][i] > 0){
            			   
            			   var mID = response.solution[j][i];
            			   console.log('Connecting task ' + tID + ' and machine ' + mID);
            			   drawConnections( tID ,  mID );
            			   
            		   }
            
            	   }
               }
                
            	
                $("#solutionMatrix").append("<br>");
                $("#solutionMatrix").append("<span class='border'>Initial objective function: " + response.initialEnergy + "</span>");
                $("#solutionMatrix").append("<span class='border' style='margin-left: 50px;'>Final objective function: " + response.finalEnergy + "</span>");
                
                $("#chartsPlace1").append("<canvas id='myChart1' width='1100' height='350'></canvas>"); 
                $("#chartsPlace2").append("<canvas id='myChart2' width='1100' height='350'></canvas>");
                $("#chartsPlace3").append("<canvas id='myChart3' width='1100' height='350'></canvas>"); 
                $("#chartsPlace4").append("<canvas id='myChart4' width='1100' height='350'></canvas>");
                $("#chartsPlace5").append("<canvas id='myChart5' width='1100' height='350'></canvas>");
                
                
                // console.log("====================================")
                var indexes = [];
                for(var i = 0 ; i < response.listLoopNum.length ; i++){
                	indexes[i] = response.listLoopNum[i];
                }
                var energies = [];
                for(var i = 0 ; i < response.listEnergy.length ; i++){
                	energies[i] = response.listEnergy[i];
                }
                var temperatures = [];
                for(var i = 0 ; i < response.listTemperature.length ; i++){
                	temperatures[i] = response.listTemperature[i];
                }
                var acceptance = [];
                for(var i = 0 ; i < response.listAcceptance.length ; i++){
                	acceptance[i] = response.listAcceptance[i];
                }
                var acceptanceProb = [];
                for(var i = 0 ; i < response.listAcceptanceProb.length ; i++){
                	acceptanceProb[i] = response.listAcceptanceProb[i];
                }
                var listTime = [];
                for(var i = 0 ; i < response.listTime.length ; i++){
                	listTime[i] = response.listTime[i];
                }
                var listCost = [];
                for(var i = 0 ; i < response.listCost.length ; i++){
                	listCost[i] = response.listCost[i];
                }
                
                
                // ************** CHART *********************************************** //
                var ctx = document.getElementById('myChart1').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [{
                            label: "Objective function - accepted solutions",
                            fill: false,
                            backgroundColor: '#3F69AA',
                            borderColor: '#3F69AA',
                            data: energies,
                        }]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                var ctx = document.getElementById('myChart2').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [{
                            label: "Temperature",
                            fill: false,
                            backgroundColor: '#E47A2E',
                            borderColor: '#E47A2E',
                            data: temperatures,
                        }]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                var ctx = document.getElementById('myChart3').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [
                        	{
                            label: "Accepted Worse Solutions",
                            fill: false,
                            backgroundColor: '#EC9787',
                            borderColor: '#EC9787',
                            data: acceptance,
                            showLine: false, 
                        },
                        {
                            label: "Acceptance probability",
                            fill: false,
                            backgroundColor: '#B4B7BA',
                            borderColor: '#B4B7BA',
                            data: acceptanceProb,
                            showLine: false, 
                        }
                        
                        ]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                var ctx = document.getElementById('myChart4').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [{
                            label: "Cost",
                            fill: false,
                            backgroundColor: '#C0AB8E',
                            borderColor: '#C0AB8E',
                            data: listCost,
                        }]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                var ctx = document.getElementById('myChart5').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [{
                            label: "Time",
                            fill: false,
                            backgroundColor: '#F6D155',
                            borderColor: '#F6D155',
                            data: listTime,
                        }]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                
                
                // ******************************************************************** // 
            
            }
            catch(err) {
                alert('Error: ' + err);
            }
       
        }
    });


}



function callmodelone() {
	
	var criteria = {
			temperature:  $("#temperature").val(),
			coolingWay: $("#cooling").val(),
			alpha: $("#alpha").val(),
			iterations: $("#iterations").val(),
			assignments: $("#assignments").val(),
			cWeight: $("#cWeight").val(),
			tWeight: $("#tWeight").val()
	};
	
	$.ajax({
        type: "POST",
        data: JSON.stringify(criteria),
        url: "http://localhost:8082/EngineeringDegreeApp/annealing/solve",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success :function(response) {
            try {
                
            	console.log('Successfully received');
                
            	cy.remove('edge');
                $("#solutionMatrix").empty();
                $("#chartsPlace1").empty();
                $("#chartsPlace2").empty();
                $("#chartsPlace3").empty();
                $("#chartsPlace4").empty();
                $("#chartsPlace5").empty();
                
                
                $("#solutionMatrix").append("<h3 class='main-title'>Solution</h3>");
                $("#solutionMatrix").append("<hr>");
                $("#solutionMatrix").append("<div class='table-caption'>Best found solution</div>");
                $("#solutionMatrix").append("<table id='solutionTable' style='border: 1px solid black; border-collapse: collapse; margin: auto;''> </table>");
                
               
                $("#solutionTable").append("<tr id='solutionRow0' style='border: 1px solid black; border-collapse: collapse;'> </tr>");
				$("#solutionTable").append("<tr id='solutionRow1' style='border: 1px solid black; border-collapse: collapse;'> </tr>");
               
                
                for(var i = 0 ; i < response.solution.length ; i++){
                	var val = response.taskOffset + i;
                	// console.log(val);
                	$("#solutionRow0").append("<td style='border: 1px solid black; border-collapse: collapse; text-align: center; width:25px; background-color:#BFD641;' >" + val + "</td>");
                }
                
            
            	for( var j = 0; j < response.solution.length ; j++ ){
            			
            			$("#solutionRow1").append("<td style='border: 1px solid black; border-collapse: collapse; text-align: center; width:25px;' >" + response.solution[j] + "</td>");
            
            	}
                     
                
                
                 
               for(var i = 0 ; i < response.solution.length; i++ ){
            	   
            	   var tID = response.taskOffset + i; 
            	     
            			   var mID = response.solution[i];
            			   console.log('Connecting task ' + tID + ' and machine ' + mID);
            			   drawConnections( tID ,  mID );
            			  
               }
                
            	
                $("#solutionMatrix").append("<br>");
                $("#solutionMatrix").append("<span class='border'>Initial objective function: " + response.initialEnergy + "</span>");
                $("#solutionMatrix").append("<span class='border' style='margin-left: 50px;'>Final objective function: " + response.finalEnergy + "</span>");
                
                $("#chartsPlace1").append("<canvas id='myChart1' width='1100' height='350'></canvas>"); 
                $("#chartsPlace2").append("<canvas id='myChart2' width='1100' height='350'></canvas>");
                $("#chartsPlace3").append("<canvas id='myChart3' width='1100' height='350'></canvas>"); 
                $("#chartsPlace4").append("<canvas id='myChart4' width='1100' height='350'></canvas>");
                $("#chartsPlace5").append("<canvas id='myChart5' width='1100' height='350'></canvas>");
                
                
                // console.log("====================================")
                var indexes = [];
                for(var i = 0 ; i < response.listLoopNum.length ; i++){
                	indexes[i] = response.listLoopNum[i];
                }
                var energies = [];
                for(var i = 0 ; i < response.listEnergy.length ; i++){
                	energies[i] = response.listEnergy[i];
                }
                var temperatures = [];
                for(var i = 0 ; i < response.listTemperature.length ; i++){
                	temperatures[i] = response.listTemperature[i];
                }
                var acceptance = [];
                for(var i = 0 ; i < response.listAcceptance.length ; i++){
                	acceptance[i] = response.listAcceptance[i];
                }
                var acceptanceProb = [];
                for(var i = 0 ; i < response.listAcceptanceProb.length ; i++){
                	acceptanceProb[i] = response.listAcceptanceProb[i];
                }
                var listTime = [];
                for(var i = 0 ; i < response.listTime.length ; i++){
                	listTime[i] = response.listTime[i];
                }
                var listCost = [];
                for(var i = 0 ; i < response.listCost.length ; i++){
                	listCost[i] = response.listCost[i];
                }
                
                
                // ************** CHART *********************************************** //
                var ctx = document.getElementById('myChart1').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [{
                            label: "Objective function - accepted solutions",
                            fill: false,
                            backgroundColor: '#3F69AA',
                            borderColor: '#3F69AA',
                            data: energies,
                        }]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                var ctx = document.getElementById('myChart2').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [{
                            label: "Temperature",
                            fill: false,
                            backgroundColor: '#E47A2E',
                            borderColor: '#E47A2E',
                            data: temperatures,
                        }]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                var ctx = document.getElementById('myChart3').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [
                        	{
                            label: "Accepted Worse Solutions",
                            fill: false,
                            backgroundColor: '#EC9787',
                            borderColor: '#EC9787',
                            data: acceptance,
                            showLine: false, 
                        },
                        {
                            label: "Acceptance probability",
                            fill: false,
                            backgroundColor: '#B4B7BA',
                            borderColor: '#B4B7BA',
                            data: acceptanceProb,
                        }
                        
                        ]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                var ctx = document.getElementById('myChart4').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [{
                            label: "Cost",
                            fill: false,
                            backgroundColor: '#C0AB8E',
                            borderColor: '#C0AB8E',
                            data: listCost,
                        }]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                var ctx = document.getElementById('myChart5').getContext('2d');
                var chart = new Chart(ctx, {
                    // The type of chart we want to create
                    type: 'line',

                    // The data for our dataset
                    data: {
                        labels: indexes,
                        datasets: [{
                            label: "Time",
                            fill: false,
                            backgroundColor: '#F6D155',
                            borderColor: '#F6D155',
                            data: listTime,
                        }]
                    },

                    // Configuration options go here
                    options: {}
                });
                
                
                
                // ******************************************************************** // 
            
            }
            catch(err) {
                alert('Error: ' + err);
            }
       
        }
    });


}






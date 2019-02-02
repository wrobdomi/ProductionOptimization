var costboxoneG = false;
var timeboxoneG = false;

function setCostConG(){
	costboxoneG = !costboxoneG;	
}

function setTimeConG(){
	timeboxoneG = !timeboxoneG;	
}
	
function callmodelgen(){
	
		
		var criteria = {
				populationSize:  $("#populationSize").val(),
				
				parentSelection: $("#parentSelection").val(),
				tournamentMembers: $("#tournamentMembers").val(),
				
				crossoverSelection: $("#crossoverSelection").val(),
				crossoverProb: $("#crossoverProb").val(),
				
				mutationSelection: $("#mutationSelection").val(),
				mutationSize: $("#mutationSize").val(),
				mutationProb: $("#mutationProb").val(),
				
				recreatingSelection: $("#recreatingSelection").val(),
				
				cWeight: $("#cWeightG").val(),
				tWeight: $("#tWeightG").val(),
				
				iterNum: $("#iterNum").val()
				//costConEnabled: costboxoneG,
				//timeConEnabled: timeboxoneG,
				//costCon: $("#costConG").val(),
				//timeCon: $("#timeConG").val()
		};
		
		$.ajax({
	        type: "POST",
	        data: JSON.stringify(criteria),
	        url: "http://localhost:8082/EngineeringDegreeApp/annealing/solvegenetic",
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
	                $("#chartsPlace6").empty();
	                
	                $("#solutionMatrix").append("<h3 class='main-title'>Solution</h3>");
	                $("#solutionMatrix").append("<hr>");
	                $("#solutionMatrix").append("<div class='table-caption'>Best solution from last population</div>");
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
	                $("#solutionMatrix").append("<span class='border'>Initial best fitness: " + response.initialFitness + "</span>");
	                $("#solutionMatrix").append("<span class='border' style='margin-left: 50px;'>Final best fitness: " + response.finalFitness + "</span>");
	                
	                $("#chartsPlace1").append("<canvas id='myChart1' width='1100' height='350'></canvas>"); 
	                $("#chartsPlace2").append("<canvas id='myChart2' width='1100' height='350'></canvas>");
	                $("#chartsPlace3").append("<canvas id='myChart3' width='1100' height='350'></canvas>"); 
	                $("#chartsPlace4").append("<canvas id='myChart4' width='1100' height='350'></canvas>");
	                $("#chartsPlace5").append("<canvas id='myChart5' width='1100' height='350'></canvas>");
	                $("#chartsPlace6").append("<canvas id='myChart6' width='1100' height='350'></canvas>");
//	                
//	                // console.log("====================================")
	                var indexes = [];
	                for(var i = 0 ; i < response.listIterationNum.length ; i++){
	                	indexes[i] = response.listIterationNum[i];
	                }
	                var populationFitness = [];
	                for(var i = 0 ; i < response.listPopulationFitness.length ; i++){
	                	populationFitness [i] = response.listPopulationFitness[i];
	                }
	                var bestFromPopulationFitness = [];
	                for(var i = 0 ; i < response.listBestFromPopulationFitness.length ; i++){
	                	bestFromPopulationFitness[i] = response.listBestFromPopulationFitness[i];
	                }
	                var worstFromPopulationFitness = [];
	                for(var i = 0 ; i < response.listWorstFromPopulationFitness.length ; i++){
	                	worstFromPopulationFitness[i] = response.listWorstFromPopulationFitness[i];
	                }
	                var meanFromPopulationFitness = [];
	                for(var i = 0 ; i < response.listMeanFromPopulationFitness.length ; i++){
	                	meanFromPopulationFitness[i] = response.listMeanFromPopulationFitness[i];
	                }
	                var crossoverApplied = [];
	                for(var i = 0 ; i < response.listCrossoverApplied.length ; i++){
	                	crossoverApplied[i] = response.listCrossoverApplied[i];
	                }
	                var crossoverProb = [];
	                for(var i = 0 ; i < response.listCrossoverProb.length ; i++){
	                	crossoverProb[i] = response.listCrossoverProb[i];
	                }
	                var mutationApplied = [];
	                for(var i = 0 ; i < response.listMutationApplied.length ; i++){
	                	mutationApplied[i] = response.listMutationApplied[i];
	                }
	                var mutationProb = [];
	                for(var i = 0 ; i < response.listMutationProb.length ; i++){
	                	mutationProb[i] = response.listMutationProb[i];
	                }
	                var crossProbTreshold = [];
	                for(var i = 0 ; i < response.listCrossoverProb.length ; i++){
	                	crossProbTreshold[i] = $("#crossoverProb").val();
	                }
	                var mutProbTreshold = [];
	                for(var i = 0 ; i < response.listMutationProb.length ; i++){
	                	mutProbTreshold[i] = $("#mutationProb").val();
	                }
	                
	                
	                var listTime = [];
	                for(var i = 0 ; i < response.listTime.length ; i++){
	                	listTime[i] = response.listTime[i];
	                }
	                var listCost = [];
	                for(var i = 0 ; i < response.listCost.length ; i++){
	                	listCost[i] = response.listCost[i];
	                }
//	                
//	                
//	                // ************** CHART *********************************************** //
	                var ctx = document.getElementById('myChart1').getContext('2d');
	                var chart = new Chart(ctx, {
	                    // The type of chart we want to create
	                    type: 'line',

	                    // The data for our dataset
	                    data: {
	                        labels: indexes,
	                        datasets: [{
	                            label: "Fitness function - Per population",
	                            lineTension: 0,  
	                            fill: false,
	                            backgroundColor: '#3F69AA',
	                            borderColor: '#3F69AA',
	                            data: populationFitness,
	                        }]
	                    },

	                    // Configuration options go here
	                    options: {}
	                });
//	                
	                var ctx = document.getElementById('myChart2').getContext('2d');
	                var chart = new Chart(ctx, {
	                    // The type of chart we want to create
	                    type: 'line',

	                    // The data for our dataset
	                    data: {
	                        labels: indexes,
	                        datasets: [
	                        	{
		                            label: "Best fitness function from population",
		                            lineTension: 0,  
		                            fill: false,
		                            backgroundColor: '#E47A2E',
		                            borderColor: '#E47A2E',
		                            data: bestFromPopulationFitness,
	                        	},
	                        	{
		                            label: "Worst fitness function from population",
		                            lineTension: 0,  
		                            fill: false,
		                            backgroundColor: '#2b5797',
		                            borderColor: '#2b5797',
		                            data: worstFromPopulationFitness,
		                        	},
	                        	{
		                            label: "Mean fitness function from population",
		                            lineTension: 0,  
		                            fill: false,
		                            backgroundColor: '#66757F',
		                            borderColor: '#66757F',
		                            data: meanFromPopulationFitness,
	                        	}
	                        
	                        ]
	                    },

	                    // Configuration options go here
	                    options: {}
	                });
//	                
	                var ctx = document.getElementById('myChart3').getContext('2d');
	                var chart = new Chart(ctx, {
	                    // The type of chart we want to create
	                    type: 'line',

	                    // The data for our dataset
	                    data: {
	                        labels: indexes,
	                        datasets: [
	                        	{
	                            label: "Crossover Applied",
	                            fill: false,
	                            backgroundColor: '#D5AE41',
	                            borderColor: '#D5AE41',
	                            data: crossoverApplied,
	                            showLine: false, 
	                        },
	                        {
	                            label: "Crossover Probability",
	                            fill: false,
	                            backgroundColor: '#766F57',
	                            borderColor: '#766F57',
	                            data: crossoverProb,
	                            showLine: false, 
	                        },
	                        {
	                            label: "Crossover Probability Treshold",
	                            fill: false,
	                            backgroundColor: '#E47A2E',
	                            borderColor: '#E47A2E',
	                            data: crossProbTreshold,
	                        }
	                        
	                        ]
	                    },

	                    // Configuration options go here
	                    options: {}
	                });
//	                
	                
	                var ctx = document.getElementById('myChart4').getContext('2d');
	                var chart = new Chart(ctx, {
	                    // The type of chart we want to create
	                    type: 'line',

	                    // The data for our dataset
	                    data: {
	                        labels: indexes,
	                        datasets: [
	                        	{
	                            label: "Mutation Applied",
	                            fill: false,
	                            backgroundColor: '#D5AE41',
	                            borderColor: '#D5AE41',
	                            data: mutationApplied,
	                            showLine: false, 
	                        },
	                        {
	                            label: "Mutation Probability",
	                            fill: false,
	                            backgroundColor: '#ECDB54',
	                            borderColor: '#ECDB54',
	                            data: mutationProb,
	                            showLine: false, 
	                        },
	                        {
	                            label: "Mutation Probability Treshold",
	                            fill: false,
	                            backgroundColor: '#E47A2E',
	                            borderColor: '#E47A2E',
	                            data: mutProbTreshold,
	                        }
	                        
	                        ]
	                    },
	                    
	                    // Configuration options go here
	                    options: {}
	                });
	                
	                
	                
//	                
	                var ctx = document.getElementById('myChart5').getContext('2d');
	                var chart = new Chart(ctx, {
	                    // The type of chart we want to create
	                    type: 'line',

	                    // The data for our dataset
	                    data: {
	                        labels: indexes,
	                        datasets: [{
	                            label: "Cost for best fit solution",
	                            lineTension: 0, 
	                            fill: false,
	                            backgroundColor: '#C0AB8E',
	                            borderColor: '#C0AB8E',
	                            data: listCost,
	                        }]
	                    },

	                    // Configuration options go here
	                    options: {}
	                });
	                
	                
	                
	                var ctx = document.getElementById('myChart6').getContext('2d');
	                var chart = new Chart(ctx, {
	                    // The type of chart we want to create
	                    type: 'line',

	                    // The data for our dataset
	                    data: {
	                        labels: indexes,
	                        datasets: [{
	                            label: "Time for best fit solution",
	                            lineTension: 0, 
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





	
	
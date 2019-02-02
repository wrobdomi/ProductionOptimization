<%@ include file="common/header.jspf" %>
	
	<%@ include file="common/sidenav.jspf" %>
	
	
	
	 
	<div class="container main-title">
		<h2> 	<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;</span> Simulated Annealing - Model 1</h2>
		<hr>
	</div>
		
	 
	<span> <img src="../resources/images/agh.png" style="width:80px;height:125px;">  </span>
	
	<br>
	
	<div class="container main-title">
		<h3 class="main-title"> Input Data </h3>
		<hr>
	</div>	
	
	<%@ include file="common/costMatrix.jspf" %>
	
	<br>
	
	<%@ include file="common/timeMatrix.jspf" %>
	
	<br>
	
	<div class="container" id="graphPlace" style="height:600px">
	
		<div id="cy"></div>
	
	</div>
	
	<script>
		var edge_color = 0;
		var cy = cytoscape({
			  container: document.getElementById('cy'),
			  zoomingEnabled: false,
			  panningEnabled: false
			});
		cy.on('click', 'node', function(evt){
				var id = this.id();
				id = '#' + id;
				console.log(id);
				var j = cy.$(id);
				
				
				if(edge_color == 0 ){
					j.connectedEdges().animate({
				    style: { lineColor: 'red'} });
					edge_color = 1;
				}
				else{
					j.connectedEdges().animate({
					style: { lineColor: 'gray'} });
					edge_color = 0 
				}
			});
	</script>
	
	<br>
	
	<div class="container main-title">
		<h3 class="main-title"> Simulated Annealing Algorithm </h3>
		<hr>
	</div>	
	
	<%@ include file="fragments/annealingoneform.jspf" %>
		
		
	
		
	<div class="container main-title">
		<h3 class="main-title"> Genetic Algorithm </h3>
		<hr>
	</div>	
	
	<%@ include file="fragments/geneticonealgoform.jspf" %>
	
	
	
	<div class="container-fluid main-title" id="solutionMatrix"></div>
	
	
	<br>
	
	
	
	
	<div class="container" id="chartsHolder">
		
		<div class="row" > 
		
			<div id="chartsPlace1" style="margin: auto;"></div>
			
		</div>
		
		<div class="row" > 
		
			<div id="chartsPlace2" style="margin: auto;"></div>
			
		</div>
		
		<div class="row" > 
		
			<div id="chartsPlace3" style="margin: auto;"></div>
			
		</div>
		
		<div class="row" > 
		
			<div id="chartsPlace4" style="margin: auto;"></div>
			
		</div>
		
		<div class="row" > 
		
			<div id="chartsPlace5" style="margin: auto;"></div>
			
		</div>
		
		<div class="row" > 
		
			<div id="chartsPlace6" style="margin: auto;"></div>
			
		</div>
		
		
	</div>
		
		
		
		
	<br>
	<br>
	
	
   	<script src="<c:url value="/resources/js/ajaxcallsone.js" />"></script>
   	<script src="<c:url value="/resources/js/ajaxcallsonegenetic.js" />"></script>
   		
   		
	<c:forEach var="tempTask" items="${tasks}">
	
		<script>drawTasks( ${tempTask.id} ,  ${tempTask.detailsNumber} );</script>
			
	</c:forEach>


	<c:forEach var="tempMachine" items="${machines}">
			
			<script>drawMachines( ${tempMachine.id} ,  ${tempMachine.detailCost}, ${tempMachine.detailTime} );</script>
	
	</c:forEach>

	
	
</body>

</html>
<%@ include file="common/header.jspf" %>
	
	<%@ include file="common/sidenav.jspf" %>
	
	
	
	 
	<div class="container main-title">
		<h2> 	<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;</span> Simulated Annealing - Model 2</h2>
		<hr>
	</div>
		
		

	 
	<span> <img src="../resources/images/agh.png" style="width:80px;height:125px;">  </span>
	
	<br>
	
	<div class="container main-title">
		<h3 class="main-title"> Input Data </h3>
		<hr>
	</div>	
	
	<div class="container"  style="text-align:center;"> 
		<div class="row">
		
				<form action="${pageContext.request.contextPath}/annealingtwo/getassign" method="get">
				
					<input  class="btn btn-secondary" type="submit"  value="Get random assignments"/> 
					 
				</form>
		 
		
		</div>	
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
		<h3 class="main-title"> Algorithm </h3>
		<hr>
	</div>	
	
	<!--  Algorithm parameters -->
	
	<div class="container">
	
		<form>
			
			<div class="form-row">
			
			    <div class="form-group col-md-4 main-title">
	              <label for="temperature">Initial temperature:</label>
	              <input type="number" class="form-control" id="temperature" placeholder="14500" value="14500" min="50" max="5000" step="10">
		        </div>
	
			    
			    <div class="form-group col-md-4 main-title">
			      <label for="colling">Cooling function:</label>
	                <select class="form-control" id="cooling">
	                    <option>T(i+1) = &#945 &#8901 T(i)</option>
	                    <option>T(i+1) = T0 / ( 1 + log( 1 + i) )</option>
	                </select>
			    </div>
			    
			     <div class="form-group col-md-4 main-title">
	              <label for="alpha">Alpha:</label>
	              <input type="number" class="form-control" id="alpha" placeholder="0.99" value="0.99" min="0" max="0.99" step="0.01">
		        </div>
		        
			    
			</div>
			
			<div class="form-row">
				
				<div class="form-group col-md-6 main-title">
	              <label for="iterations">Number of iterations:</label>
	              <input type="number" class="form-control" id="iterations" placeholder="200" value="200" min="50" max="5000" step="5">
		        </div>
		        
		        <div class="form-group col-md-6 main-title">
		          <label for="assignments">Change of assignments in one iteration:</label>
	              <input type="number" class="form-control" id="assignments" placeholder="10" value="10" min="1" max="50" step="1">	
		        </div>
		        
		        
			</div>
			
			
			<div class="form-row">
				
				<div class="form-group col-md-6 main-title">
	              <label for="cWeight">Cost weight:</label>
	              <input type="number" class="form-control" id="cWeight" placeholder="0.12" value="0.12" min="0" max="1" step="0.1">
		        </div>
		        
		        <div class="form-group col-md-6 main-title">
		          <label for="tWeight">Time weight:</label>
	              <input type="number" class="form-control" id="tWeight" placeholder="1" value="1" min="0" max="1" step="0.1">	
		        </div>
		        
		        
			</div>
			
			<div class="form-row">
			
				<div class="form-group col-md-6 main-title">
					 <input class="form-check-input" type="checkbox" id="costconenabled" value="true" onclick="setCostCon()">
  					 <label class="form-check-label" for="costconenabled">Cost constraints</label>
				</div>
				
				<div class="form-group col-md-6 main-title">
					<input class="form-check-input" type="checkbox" id="timeconenabled" value="true" onclick="setTimeCon()">
 					<label class="form-check-label" for="timeconenabled">Time constraints</label>
				</div>
				
			</div>
			
			
			<div class="form-row">
				
				<div class="form-group col-md-6 main-title">
	              <label for="costCon">Cost upper bound:</label>
	              <input type="number" class="form-control" id="costCon" placeholder="58000" value="58000" min="100" max="100000" step="100">
		        </div>
		        
		        <div class="form-group col-md-6 main-title">
		          <label for="timeCon">Time upper bound:</label>
	              <input type="number" class="form-control" id="timeCon" placeholder="40000" value="40000" min="100" max="100000" step="50">	
		        </div>
		        
		        
			</div>
			
			
		
		</form>
		
		<span> <button class="btn btn-secondary"  onclick="callmodeltwo()">Use algorithm</button> </span>
		
	</div>
	
	
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
		
	</div>
	
	
	
	<br>
	<br>
	<br>
	
	
			<c:forEach var="costRow" items="${colors}" varStatus="loop2">
				
							<c:forEach var="cell" items="${costRow}" varStatus="loop">
								
								  <script>test(${cell}, '${loop2.index}', ${loop.index});</script>		
									
							</c:forEach>
					   
			</c:forEach>
			



		<script src="<c:url value="/resources/js/ajaxcalls.js" />"></script>
		
		
	
    	
    		
		<c:forEach var="tempTask" items="${tasks}">
		
			<script>drawTasks( ${tempTask.id} ,  ${tempTask.detailsNumber} );</script>
				
		</c:forEach>
	
	
		<c:forEach var="tempMachine" items="${machines}">
				
				<script>drawMachines( ${tempMachine.id} ,  ${tempMachine.detailCost}, ${tempMachine.detailTime} );</script>
		
		</c:forEach>
	
	
</body>

</html>
<%@ include file="common/header.jspf" %>
	
	<%@ include file="common/sidenav.jspf" %>
	
	
	
	 
	<div class="container main-title">
		<h2> 	<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;</span> Tasks & Machines</h2>
		<hr>
	</div>
		
	<div class="container">
		<div class="row">
		
			<form action="${pageContext.request.contextPath}/production/gettasks" method="post">
				
				 <span style="margin-left:50px;"> Number of Tasks: </span> <input name="tasksNum" type="text" />  
				 <span style="margin-left:50px;"> Number of Machines: </span> <input name="machinesNum" type="text"/> 
				 <span style="margin-left:100px;"> <input type="submit" value="Get random data" style="background-color:#b2b2b2"/> </span>
				 
			</form>
		
		 <span> <img src="../resources/images/agh.png" style="width:80px;height:125px;">  </span>
		 
		</div>
	</div>
	 
	
	
	
	
	<div class="container">
		
		<!-- Create Header of the Border  -->
		<div class="container-fluid table-header" style="width:80%;">
			<div class="row">
				<div class="col-3 "> <b>Task Id</b> </div>
				<div class="col-9 "> <b>Details Required</b> </div>
			</div>	
		</div>
		
	<c:forEach var="tempTask" items="${tasks}">
	
	 	<div class="container-fluid row-border-task" style="width:80%;" data-toggle="collapse" data-target="#collapseTask${tempTask.id}" aria-expanded="false" aria-controls="collapseExample">
			<div class="row">
				<div class="col-3 "> <b> ${tempTask.id} </b> </div>
				<div class="col-9 "> <b> ${tempTask.detailsNumber} </b> </div>
			</div>	
		</div>
					
				<div class="container-fluid collapse row-border-task" style="width:80%;" id="collapseTask${tempTask.id}">
				
					<c:forEach var="tempMachine" items="${tempTask.machines}">
						 <div class="row-border-machine" style="margin:5px;">
						    <div class="row">
						    	<div class="col-4" style="padding-left:25px;"> Machine ID: ${tempMachine.id} </div>
								<div class="col-4" style="padding-left:25px;"> Detail Cost: ${tempMachine.detailCost}  </div>
								<div class="col-4" style="padding-left:25px;"> Time per detail: ${tempMachine.detailTime}  </div>
						    </div>
						 </div>
					</c:forEach>	 
					
				</div>
							
	</c:forEach>
						
	</div>
	
	<!-- Graph visualization -->
	<div class="container" style="height:600px">
		
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
	
	
	<c:forEach var="tempTask" items="${tasks}">
	
		<script>drawTasks( ${tempTask.id} ,  ${tempTask.detailsNumber} );</script>
			
	</c:forEach>


	<c:forEach var="tempMachine" items="${machines}">
			
			<script>drawMachines( ${tempMachine.id} ,  ${tempMachine.detailCost}, ${tempMachine.detailTime} );</script>
	
	</c:forEach>	
	
	
	<c:forEach var="tempMachine" items="${machines}">
		
		<c:forEach var="tempTask" items="${tempMachine.tasks}">
		
			<script>drawConnections( ${tempTask.id} ,  ${tempMachine.id} );</script>
	
		</c:forEach>
		
	</c:forEach>
	
	
	<br>
	
	<%@ include file="common/costMatrix.jspf" %>
	
	<br>
	
	<%@ include file="common/timeMatrix.jspf" %>
	
	
	<br>
	<br>
	<br>
	
	
	
</body>

</html>
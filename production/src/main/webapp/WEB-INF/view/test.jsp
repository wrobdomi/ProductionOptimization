<%@ include file="common/header.jspf" %>

<html>

<head>

</head>

<body>

THIS IS TEST PAGE

<c:forEach var="tempEnergy" items="${energyList}">
			
	<p> ${tempEnergy} </p>
	
</c:forEach>	

</body>

</html>
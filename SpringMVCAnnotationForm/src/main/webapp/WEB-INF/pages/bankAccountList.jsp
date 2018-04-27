<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	isELIgnored="false" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Applicant List</title>
<style>
table {
	margin-top: 10px;
	border: solid black 1px;
}

table  td {
	padding: 5px;
}

.message {
	font-size: 90%;
	color: blue;
	font-style: italic;
	margin-top: 30px;
}
</style>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />

</head>

<body>

	<h2>Bank Account List</h2>

	<a href="${pageContext.request.contextPath}/createBankAccount">Create
		Bank Account</a> |
	<a href="${pageContext.request.contextPath}/transfertMoney">Transfert
		Money</a> |
|	<a href="${pageContext.request.contextPath}/generateNextAccount">Generate Accounts</a>
	<br />


			<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Full name</th>
					<th>Balance</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
		<c:forEach items="${bankAccountInfos}" var="info">
		
		 <spring:url value="editBankAccount?id=${info.id}" var="editUrl" />
		 <spring:url value="deleteBankAccount?id=${info.id}" var="deleteUrl" />		 

			<tr>
				<td>${info.id}</td>
				<td>${info.fullName}</td>
				<td>${info.balance}</td>
				<td><button class="btn btn-info"  onclick="location.href='${editUrl}'">Edit</button></td>
				<td><button class="btn btn-info"  onclick="location.href='${deleteUrl}'">Delete</button></td>			
				
			</tr>

		</c:forEach>
	</table>
	<c:if test="${not empty message}">

		<div class="message">${message}</div>
	</c:if>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	isELIgnored="false" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Bank Account</title>
<style>
.error-message {
	color: red;
	font-size: 90%;
	font-style: italic;
}
</style>
</head>
<body>

	<h3>${formTitle}</h3>

	<form:form action="processGenerateNextAccount" method="POST"
		modelAttribute="generateAccountForm">

		<table>
			<tr>
				<td>Number of accounts to generate</td>
				<td><form:input path="nbAccounts" /></td>
				<td><form:errors path="nbAccounts" class="error-message" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="Submit" /> <a
					href="${pageContext.request.contextPath}/bankAccountList">Cancel</a>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</form:form>

</body>
</html>
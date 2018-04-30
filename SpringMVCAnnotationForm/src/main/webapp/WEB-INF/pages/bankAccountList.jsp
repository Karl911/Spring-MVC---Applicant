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
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" />

</head>
<body>

	<!-- <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
 	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
 	-->
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<h2>Bank Account List</h2>

	<a href="${pageContext.request.contextPath}/createBankAccount">Create
		Bank Account</a> |
	<a href="${pageContext.request.contextPath}/transfertMoney">Transfert
		Money</a> |
|	<a href="${pageContext.request.contextPath}/generateNextAccount">Generate Accounts</a>

		<table class="table table-bordered">
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
				<td><button id="delete${info.id}" class="btn btn-info" >Delete</button></td>
			</tr>

		</c:forEach>
	</table>
	<c:if test="${not empty message}">

		<div class="message">${message}</div>
	</c:if>

	<script>
	function confirmation(question) {
	    var defer = $.Deferred();
	    $('<div></div>')
	        .html(question)
	        .dialog({
	            autoOpen: true,
	            modal: true,
			    height: 180,
	            width: 380,
	            title: 'Confirmation',
	            buttons: {
	                "Yes": function () {
	                    defer.resolve("true");//this text 'true' can be anything. But for this usage, it should be true or false.
	                    $(this).dialog("close");
	                },
	                "Cancel": function () {
	                    //defer.resolve("false");//this text 'false' can be anything. But for this usage, it should be true or false.
	                    $(this).dialog("close");
	                }
	            },
	            close: function () {
	                //$(this).remove();
	                $(this).dialog('destroy').remove()
	            }
	        });
	    return defer.promise();
	};

	function compute(buttons){
		btnDelete.each (function (index) {
		
		var name = $(this).attr("id");
		var accountId = name.substr(6, name.length);

		console.log(index + ": " + name);
		
			$("#" + name).click(function() {
				var question = "Do you really want to delete the bank account "	+ accountId + " ?";
				confirmation(question).then(function(answer) {
					console.log(answer);
					var ansbool = (String(answer) == "true");
					if (ansbool) {
						//location.href='${deleteUrl}';
						location.href = "deleteBankAccount?id="+ accountId
					}
					else {
						alert("and then there is "	+ ansbool);
					//FALSE
					}
				});
			})
		});
	}

		//$(":button").on('click', onclick);
		//$("#delete69").on('click', onclick);
		var btnDelete = $('[id*="delete"]');
		compute(btnDelete);
	</script>

</body>
</html>
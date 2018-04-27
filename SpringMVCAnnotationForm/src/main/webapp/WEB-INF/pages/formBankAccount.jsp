<%@ page language="java" contentType="text/html; charset=ISO-8859-1" isELIgnored="false"
	pageEncoding="ISO-8859-1"%>
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
   font-size:90%;
   font-style: italic;
}
</style>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />

</head>

<body>
 
  
   <div class="form-group">
			<div class="col-sm-offset-1">
				 <h3>${formTitle}</h3>
			</div>
		</div>
 
   <form:form class="form-horizontal"  action="saveBankAccount" method="POST"
       modelAttribute="bankAccountForm">
 
       <form:hidden path="id" />
 
 		<spring:bind path="fullName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Full Name</label>
				<div class="col-sm-1">
					<form:input path="fullName" type="text" class="form-control " id="fullName" placeholder="fullName" />
					<form:errors path="fullName" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		 <spring:bind path="balance">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Solde</label>
				<div class="col-sm-1">
					<form:input path="balance" type="text" class="form-control " id="balance" placeholder="balance" />
					<form:errors path="balance" class="control-label" />
				</div>
			</div>
			
		</spring:bind>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-1">
				<button type="submit" class="btn-lg btn-primary pull-left">Submit</button>
			</div>
		</div>
		
   </form:form>
 
</body>
</html>
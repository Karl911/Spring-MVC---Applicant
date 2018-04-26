<%@ page language="java" contentType="text/html; charset=ISO-8859-1" isELIgnored="false"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transfert Money</title>
<style>
   table  {
       margin-top: 10px;
       border: solid black 1px;
   }
   table  td {
        padding: 5px;
   }
   .message  {
        font-size:90%;
        color:blue;
        font-style:italic;
        margin-top:30px;
   }
</style>
</head>
<body>
 
<a href="${pageContext.request.contextPath}/createBankAccount">Create Bank Account</a>
<br/>
 
<h3>${formTitle}</h3>
 
   <form:form action="saveTransfertMoney" method="POST"
       modelAttribute="sendMoneyForm">
 
		<table>
           <tr>
               <td>Balance</td>
               <td><form:input path="balance" /></td>
               <td><form:errors path="balance"
                       class="error-message" /></td>      
           </tr>
    		<tr>
               <td>&nbsp;</td>
               <td><input type="submit" value="Submit" />
                  <a href="${pageContext.request.contextPath}/bankAccountList">Cancel</a>
               </td>
               <td>&nbsp;</td>
           </tr>
       </table>
   </form:form>
</body>
</html>
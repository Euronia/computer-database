<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Edit computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/computerdatabase/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/computerdatabase/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="/computerdatabase/css/main.css" rel="stylesheet" media="screen">
</head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.excilys.formation.pagination.Page" %>
<script src="../computerdatabase/js/jquery.min.js"></script>
<script src="../computerdatabase/js/jquery.validate.min.js"></script>
<script src="../computerdatabase/js/validation/addcomputer-validation.js"></script>
<jsp:useBean id="computer" type="com.excilys.formation.dto.ComputerDto" scope="application" />
<jsp:useBean id="pageCompany" type="com.excilys.formation.pagination.Page" scope="application" />
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id }
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" value="${computer.id }" name="id" id="id"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" value ="${computer.name}"class="form-control" id="computerName" name="name" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" 
                                <c:if test="${not empty computer.introduced}"> value="${computer.introduced }"</c:if>id="introduced" name="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" 
                                 <c:if test="${not empty computer.discontinued}"> value="${computer.discontinued }"</c:if>id="discontinued" name="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0" > - - - - -</option>
                                	<c:forEach items="${pageCompany.elements}" var="company">
                                    	<option value="${company.id}">${company.name}</option>
                                	</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>
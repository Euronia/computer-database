<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.excilys.formation.pagination.Page" %>
<html>
<head>
<title>Add computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<link href="/computerdatabase/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/computerdatabase/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="/computerdatabase/css/main.css" rel="stylesheet" media="screen">
</head>
<script src="../computerdatabase/js/jquery.min.js"></script>
<script src="../computerdatabase/js/jquery.validate.min.js"></script>
<script src="../computerdatabase/js/validation/addcomputer-validation.js"></script>
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
                    <h1>Add Computer</h1>
                    <sf:form action="addComputer" method="POST" id="addComputer" modelAttribute="computerDto">
                        <fieldset>
                            <div class="form-group">
                                <sf:label path="name">Computer name</sf:label>
                                <sf:input type="text" class="form-control" path="name" id="computerName" placeholder="Computer name"/>
                            	<sf:errors path="name" cssClass="alert alert-danger" element="div"/>
                            </div>
                            <div class="form-group">
                                <sf:label path="introduced">Introduced date</sf:label>
                                <sf:input type="date" class="form-control" path="introduced" id="introduced" placeholder="Introduced date"/>
                           		<sf:errors path="introduced" cssClass="alert alert-danger" element="div" />
                            </div>
                            <div class="form-group">
                                <sf:label path="discontinued">Discontinued date</sf:label>
                                <sf:input type="date" class="form-control" path="discontinued" id="discontinued" placeholder="Discontinued date"/>
                            	<sf:errors path="discontinued" cssClass="alert alert-danger" element="div" />
                            </div>
                            <div class="form-group">
                                <sf:label path="companyId">Company</sf:label>
                                <sf:select class="form-control" path="companyId" id="companyId" >
                                	<sf:option value="0" > - - - - -</sf:option>
                                	<c:forEach items="${pageCompany.elements}" var="company">
                                    	<sf:option value="${company.id}">${company.name}</sf:option>
                                	</c:forEach>
                                </sf:select>
                                <sf:errors path="companyId" cssClass="alert alert-danger" element="div" />
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="/computerdatabase/dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </sf:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>

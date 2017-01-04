<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title><spring:message code="addComputer.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/computerdatabase/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/computerdatabase/resources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="/computerdatabase/resources/css/main.css" rel="stylesheet" media="screen">
</head>
<script src="../computerdatabase/resources/js/jquery.min.js"></script>
<script src="../computerdatabase/resources/js/jquery.validate.min.js"></script>
<script src="../computerdatabase/resources/js/validation/addcomputer-validation.js"></script>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
             <span style="float: right"> <a href="?locale=en">en</a> | <a href="?locale=fr">fr</a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="addComputer.title"/></h1>
                    <sf:form action="addComputer" method="POST" id="addComputer" modelAttribute="computerDto">
                        <fieldset>
                            <div class="form-group">
                                <sf:label path="name"><spring:message code="computer.name"/></sf:label>
                                <sf:input type="text" class="form-control" path="name" id="computerName" placeholder="Computer name"/>
                            	<sf:errors path="name" cssClass="alert alert-danger" element="div"/>
                            </div>
                            <div class="form-group">
                                <sf:label path="introduced"><spring:message code="computer.introduced"/></sf:label>
                                <sf:input type="date" class="form-control" path="introduced" id="introduced" placeholder="Introduced date"/>
                           		<sf:errors path="introduced" cssClass="alert alert-danger" element="div" />
                            </div>
                            <div class="form-group">
                                <sf:label path="discontinued"><spring:message code="computer.discontinued"/></sf:label>
                                <sf:input type="date" class="form-control" path="discontinued" id="discontinued" placeholder="Discontinued date"/>
                            	<sf:errors path="discontinued" cssClass="alert alert-danger" element="div" />
                            </div>
                            <div class="form-group">
                                <sf:label path="companyId"><spring:message code="computer.company"/></sf:label>
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
                            <input type="submit" value=<spring:message code="addComputer.add"/> class="btn btn-primary">
                            or
                            <a href="/computerdatabase/dashboard" class="btn btn-default"><spring:message code="addComputer.cancel"/></a>
                        </div>
                    </sf:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>

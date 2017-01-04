<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="../mytags.tld" prefix="m" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
<title> Dashboard</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
 <link href="/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/resources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="/resources/css/main.css" rel="stylesheet" media="screen">


</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a> 
            <span style="float: right"> <a href="?locale=en">en</a> | <a href="?locale=fr">fr</a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${pageComputer.totalElements} <spring:message code="dashboard.title"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder=<spring:message code="dashboard.search.placeholder" /> />
                        <input type="submit" id="searchsubmit" value=<spring:message code="dashboard.search.filter" />
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="/computerdatabase/addComputer"><spring:message code="dashboard.add"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>
        <div class="container" style="margin-top: 10px;">
	        <table class="table table-striped table-bordered">
	                <thead>
	                    <tr>
	                        <!-- Variable declarations for passing labels as parameters -->
	                        <!-- Table header for Computer Name -->
	
	                        <th class="editMode" style="width: 60px; height: 22px;"><input
	                            type="checkbox" id="selectall" /> <span
	                            style="vertical-align: top;"> - <a href="#"
	                                id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
	                                    class="fa fa-trash-o fa-lg"></i>
	                            </a>
	                        </span></th>
	                        <th><spring:message code="computer.name"/></th>
	                        <th><spring:message code="computer.introduced"/></th>
	                        <!-- Table header for Discontinued Date -->
	                        <th><spring:message code="computer.discontinued"/></th>
	                        <!-- Table header for Company -->
	                        <th><spring:message code="computer.company"/></th>
	                    </tr>
	                </thead>
	                <!-- Browse attribute computers -->
	                <tbody id="results">
	                <my:PaginationTag/>
	                </tbody>
	            </table>
            </div>
    </section>

	<my:LinkTag/>
    
<script src="/dashboard/strings.js"></script>
<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/dashboard.js"></script>

</body>
</html>
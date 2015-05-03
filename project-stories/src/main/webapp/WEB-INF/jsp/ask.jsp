<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
	<title> Main Page </title>

	<meta charset="UTF-8">
		<style type = "text/css">
	
		<%@include file = "/resources/css/menustyle.css"%>
	
	</style>
</head>
<body>
	<div id="all">
		<div id="menu">
			<header>
				<nav>
					<ul id="ulmenu">
						<li id="limenu"> <img src="resources/images/upc2.png" alt="upclogo" class="icon" id="upc"> </li>
						<li id="limenu"> <a href="stories.html"><b>ASW News</b></a> </li>
						<li id="limenu">|</li>
						<li id="limenu"> <a href="ask.html">ask</a></li>
						<li id="limenu">|</li>
						<li id="limenu"> <a href="submit.html">submit</a></li>
						<li id="limenu" style="float:right"> <a href="login.html" style="float:right">login</a></li>
					</ul>
				</nav>
			</header>
		</div>
		<div id="space">
			<table class="table table-bordered table-hover table-striped">

	<tbody>
	<c:set var="count" value="0" scope="page" />
		<c:forEach items="${asks}" var="ask">
		<c:set var="count" value="${count + 1}" scope="page" />
						<tr>

							<td colspan="4">
							
								<form action="like.html?idStory=${ask.getId()}" method="POST" id=like>
								<c:out value="${count}" />. 
									<input type="image" src="resources/images/like.png">
									<a
								href="/project-stories/comments.html?idStory=${ask.getId()} ">
									<c:out value="${ask.title}" />
							</a> 
								</form> </td></tr>
						<tr>
							<td id="trdescription"><c:out value="${ask.score}" />
								points by <c:out value="${ask.by.username}" /> <c:out
									value="${ask.time}" /> | comments <a
								href="/project-stories/comments.html?idStory=${ask.getId()} ">
									<c:out value="${ask.getDescendants()}" />
							</a></td>
						</tr>
					</c:forEach>
	</tbody>
</table>
		</div>
	</div>
</body>

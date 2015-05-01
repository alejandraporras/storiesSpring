<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
	<title> Comentario de historias</title>

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
						<li id="limenu"><img src="resources/images/upc2.png"
							alt="upclogo" class="icon" id="upc"></li>
						<li id="limenu"><a href="stories.html"><b>ASW News</b></a></li>
						<li id="limenu">|</li>
						<li id="limenu"><a href="ask.html">ask</a></li>
						<li id="limenu">|</li>
						<li id="limenu"><a href="submit.html">submit</a></li>
						<li id="limenu" style="float: right"><a href="login.html"
							style="float: right">login</a></li>
					</ul>
				</nav>
			</header>
		</div>
		<div id="space">
			<table class="table table-bordered table-hover table-striped">

				<tbody>
					<tr>
						<c:out value="${story.title}" />
						<c:out value="${story.url}" />
						<c:out value="${story.score}" />
						by
						<c:out value="${story.by.username}" />
						<c:out value="${story.time}" />
						| comments
						<a href="/project-stories/comments.html?idStory=${story.getId()} ">
							<c:out value="${story.getDescendants()}" />
						</a>
</tr>
						<br />
						<c:if test="${story.type=='ask'}"> 
    				<c:out value="${story.text}" /> </c:if>
    
					


					<c:forEach items="${commentsOfC}" var="comment">
			<tr>

				<td> 
					
					<c:out value="${comment.by.username}" />
					<c:out value="${comment.time}" />
					<br/>
					<c:out value="${comment.text}" />
					<br/>
					<a href="/project-stories/reply.html?idComment=${comment.getId()} " > Reply</a>

				</td>
			</tr>
		</c:forEach>
				</tbody>





			</table>
		</div>
	</div>
</body>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
	<title> Reply a comment</title>

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
			<p>
			<ol type="1">
				<header>
					<div id="comentari">
						<p>
					
							<font face="verdana" color="#000000" size="2"> 
							<c:out value="${comment.by.username}" /> <c:out value="${comment.time}" /> minuts ago |
							</font> 
									
								<sup><font
								face="verdana" color="#828282" size="1"> <br /> 

										<c:out value="${comment.text}" />
								</a>

							</font></sup>

						</p>

					</div>
				</header>
			</ol>
			</p>
		<form name="replyForm" action="reply.html?idComment=${comment.getId()}" method="POST" id="replyForm">
        <table cellspacing="5" cellpadding="0" border="0" style="margin-top:5px">
        	<tr>
        		
            	<td><textarea id = "textin" rows="4" cols="50" name="textin" form="replyForm"></textarea></td>
          	</tr>
          	
            	<td><input type="submit" value="Reply" /></td>
          	</tr>
          	
        </table>
    	</form>
			
		</div>


	</div>
</body>

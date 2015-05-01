<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type = "text/css">
	
		<%@include file = "/resources/css/menustyle.css"%>
	
	</style>

<title>Insert title here</title>
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
				<li id="limenu"> <a href="submit.html" style="color:white"><b>submit</b> </a></li>
				<li id="limenu" style="float:right"> <a href="login.html" style="float:right">login</a></li>
			</ul>
		</nav>
	</header>
	</div>
	<div id="space">
		
	
		<form name="submitForm" action="stories.html" method="POST" id="submitForm">
        <table cellspacing="5" cellpadding="0" border="0" style="margin-top:5px">
        	<tr>
        		<td>title</td>
          		<td><input type="text" name="title" style="width:420px" /></td>
          	</tr>
          	<tr>
           		<td>url</td>
            	<td><input type="text" name="url" style="width:420px" /></td>
            </tr>
            <tr>
            	<td></td>
            	<td><b>or</b></td>
            </tr>
        	<tr>
            	<td>text</td>
            	<!--<td><input type="text" name="textin"/></td>-->
            	<td><textarea id = "textin" rows="4" cols="50" name="textin" form="submitForm"></textarea></td>
          	</tr>
          	<tr>
            	<td></td>
            	<td><input type="submit" value="submit" /></td>
          	</tr>
          	<tr/>
          	<tr/>
          	<tr>
          		<td></td>
          		<td>Leave url blank to submit a question for discussion. If there is no url, the text (if any) will appear at the top of the thread.</td>
          	</tr>
        </table>
    	</form>
	
	</div>
	</div>
</body>
</html>
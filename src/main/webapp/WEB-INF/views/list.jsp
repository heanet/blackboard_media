<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>HEAnet Media Plugin - List</title>
		<link rel="stylesheet" href="<c:url value="../assets/css/list.css" />" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
		<script type="text/javascript" src="../assets/js/functions.js"></script>
	</head>
	<body>
		<div class="navbar">
			<h1>HEAnet Media</h1>
			<h2>Choose your video</h2>
		</div>
		<div id="list">
			<ul>
			<c:forEach items="${medias}" var="media" varStatus="loopStatus">
				<li id="${media.getId()}" class="video unchecked" onclick="setVideo('${media.getId()}','${media.getSource()}')">
					<img src="${media.getThumbnail()}" width="${thumbnailWidth}" height="${thumbnailHeight}" />
					<p class="overlay" style="display:none;">${media.getTitle()}</p>
				</li>
			</c:forEach>
			</ul>
		</div>
		<form id="form" action="${targetUrl}" onsubmit="return getEmbedTag()">
			<input type="hidden" name="embedHtml" id="embedHtml" value="" />
			<input type="submit" value="Add Video">
		</form>
	</body>
</html>

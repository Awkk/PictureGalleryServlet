<link rel="stylesheet" type="text/css" href="./css/header.css">
<script src="./js/header.js"></script>


<div class="header">
    <a href="/gallery" class="logo">Picture Gallery</a>

    <div class="header-right">
        <a href="/gallery">Gallery</a>
        <a href="/gallery/upload.jsp">Upload</a>
        <a href="logout">Logout</a>
        <a class="active">User: <%= (String)request.getSession(false).getAttribute("userid") %></a>
    </div>
</div>
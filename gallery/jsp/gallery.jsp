<link rel="stylesheet" type="text/css" href="./css/gallery.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">

<div class="header">
    <a href="/gallery" class="logo">Picture Gallery</a>

    <div class="header-right">
        <a href="/gallery">Gallery</a>
        <a href="/gallery/upload.jsp">Upload</a>
        <a href="logout">Logout</a>
        <a class="active">User: <%= (String)request.getSession(false).getAttribute("userid") %></a>
    </div>
</div>
<div id="galleryContainer">
    <form class="example" action="">
        <input id="searchField" type="text" placeholder="Search..">
        <button id="searchBtn" type="button" onclick="getPicList()">Search</button>
    </form>


    <div id="gallery">
        <img id="picture">
        <div id="fileName" class="desc"></div>
        <div id="caption" class="desc"></div>
        <div id="date" class="desc"></div>
    </div>

    <div id="buttonRow">
        <button class="arrow" onclick="prevPic()">&#60;</button>
        <button onclick="autoPlay()">Auto</button>
        <button class="arrow" onclick="nextPic()">&#62;</button>
    </div>

    <div id="count"></div>

</div>
<script src="./js/gallery.js"></script>
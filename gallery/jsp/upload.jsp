<link rel="stylesheet" type="text/css" href="./css/upload.css">
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
<div id="id01" class="modal">
    <form class="modal-content" action="upload" method="POST" enctype="multipart/form-data">
        <div class="container">
            <div>
                <label for="myFile"><b>Upload File</b></label>
                <input type="file" name="myFile" required>
            </div>
            <div>
                <label for="caption"><b>Caption</b></label>
                <input type="text" placeholder="Enter caption" name="caption">
            </div>
            <div>
                <label for="datePicked"><b>Date</b></label>
                <input type="date" name="datePicked" required>
            </div>

            <%
                String message = (String) request.getAttribute("message");
                if(message != null){
            %>
            <div class="message">
                <p><%= message %></p>
            </div>
            <%
                }
            %>
            <div>
                <button type="submit">Submit</button>
            </div>
        </div>
    </form>
</div>
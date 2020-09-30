<link rel="stylesheet" type="text/css" href="./css/login.css">

<div id="id01" class="modal">
    <span class="close"><a href="loginPage"></a></span>
    <form class="modal-content" action="signin" method="POST">
        <div class="container">
            <label for="userid"><b>UserID</b></label>
            <input type="text" placeholder="Enter UserID" name="userid" required>

            <label for="password"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="password" required>

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
            <div class="clearfix">
                <button type="submit" class="cancelbtn" formaction="signup">Sign Up</button>
                <button type="submit" class="signupbtn" formaction="login">Login</button>
            </div>
        </div>
    </form>
</div>
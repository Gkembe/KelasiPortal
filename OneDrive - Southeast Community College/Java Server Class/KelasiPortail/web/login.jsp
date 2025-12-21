<%-- 
    Document   : login
    Created on : Nov 27, 2025, 6:45:34 PM
    Author     : kembe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Kelasi Portal — Sign In</title>
        <link rel="stylesheet" href="login.css" />

    </head>
    <body>

        <style>


            
        </style>
        <header class="topbar">
            <a class="brand" href="index.html">Kelasi Portal</a>
        </header>

        <main >
            <section class="auth-card" aria-labelledby="signin-title">
                <h1 id="signin-title">Sign In</h1>
                <p class="auth-subtitle">Access your school dashboard.</p>

                <h2>${message}</h2>

                <form class="auth-form" id="loginForm" action="Public" method="post" >

                    <input type="hidden" name="action" value="login">

                    <br>
                    <div class="input-group">
                        <label for="loginEmail">Email</label>
                        <input type="text" id="loginEmail" name="email" placeholder="admin@school.com" required />
                    </div>

                    <div class="input-group">
                        <label for="loginPassword">Password</label>
                        <input type="password" id="loginPassword" name="password" placeholder="Enter your password" required minlength="6" />
                    </div>

                    <div class="auth-actions">
                        <label class="checkbox">
                            <input type="checkbox" id="remember" name="remember" />
                            <span>Remember me</span>
                        </label>
                        <a class="link" href="#">Forgot password?</a>
                    </div>

                    <input type="submit" class="btn btn--full" id="" Value="Sign In">
                </form>

                <p class="auth-switch">
                    Don’t have an account?
                    <a class="link" href="signup.jsp">Create one</a>
                </p>
                <script src="login.js"></script>
            </section>
        </main>
    </body>
</html>

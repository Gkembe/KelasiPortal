<%-- 
    Document   : index
    Created on : Nov 27, 2025, 6:42:42 PM
    Author     : kembe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Kelasi Portal</title>
        <link rel="stylesheet" href="index.css" />
    </head>
    <body>

        <style>
            
        </style>
        <main class="container">
            <section class="left-section" aria-labelledby="app-title">
                <h1 id="app-title" class="logoname">Kelasi Portal</h1>
                <p class="subtitle">Learn, progress, and connect with other students.</p>

                <nav class="buttons" aria-label="Authentication">
                    <a class="btn" href="login.jsp">Sign In</a>
                    <a class="btn btn--outline" href="signup.jsp">Sign Up</a>
                </nav>

                <p>${message}</p>
            </section>

            <figure id="welcome_img" class="hero">
                <img src="logPage/2.png" alt="Students studying together" />
            </figure>
        </main>
    </body>
</html>
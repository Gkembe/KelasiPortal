<%-- 
    Document   : addUsers
    Created on : Dec 30, 2025, 9:26:56 PM
    Author     : kembe
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/dashboard.css">
    </head>
    <body>




        <div class="layout">
            <!-- SIDEBAR -->
            <div class="sidebar">
                <div class="brand">
                    <div class="brand-title">KELASI</div>
                    <div class="brand-sub">${school.shortName}</div>
                </div>

                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoSchoolProfile">School Profile</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/Private?action=listUsers">All Users</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listTeachers">Teachers</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listStudents">Students</a>

                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout">Logout</a>
            </div>


            <div class="content">

                <div class="topbar">
                    <div>
                        <h1>Add Student</h1>
                        <div class="subtext">Fill student information</div>
                    </div>
                </div>

                <div class="section">
                    <div class="section-header">
                        <h2>Student Information</h2>
                    </div>
                    <ul style="color: red">

                        <c:forEach var="b" items="${badMessage}">


                            <li>${b}</li>
                        </c:forEach>

                    </ul>
                    <p style="color: green">${message}</p>

                    <form class="form" action="${pageContext.request.contextPath}/Private" method="post">
                        <input type="hidden" name="action" value="addUsers">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">
                        <input type="hidden" name="userID" value="${user.userID}">

                        <div class="form-grid">

                            <div class="form-row">
                                <label for="username">Username ADMIN</label>
                                <input type="text" class="form-input" name="username" placeholder="Andree"  />
                            </div>
                            <div class="form-row">
                                <label for="adminemail">Email ADMIN</label>
                                <input type="email" class="form-input" name="adminemail" placeholder="admin@school.com" />
                            </div>

                            <div class="form-row">
                                <label for="adminphone" class="form-label">Phone ADMIN</label>
                                <input type="text" class="form-input" name="adminphone" placeholder="+14024307272"  />
                            </div>
                            <div class="form-row">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-input" name="password" placeholder="At least 6 characters"  minlength="6" />
                            </div>
                            <div class="form-row">
                                <label for="confirm" class="form-label">Confirm Password</label>
                                <input type="password" class="form-input" name="confirmpassword" placeholder="Re-type password"  minlength="6" />
                            </div>
                        </div>
                        <div class="actions">
                            <input class="btn btn-primary" type="submit" value="Add ADMIN">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>


    </body>
</html>

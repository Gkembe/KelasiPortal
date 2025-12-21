<%-- 
    Document   : users
    Created on : Dec 17, 2025, 5:20:19 PM
    Author     : kembe
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Kelasi - Users</title>
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

            <!-- CONTENT -->
            <div class="content">

                <div class="topbar">
                    <div>
                        <h1>Users</h1>
                        <div class="subtext">Manage accounts for <b>${school.schoolName}</b></div>
                    </div>

                    <br>
                </div>
                <div class="section">
                    <div class="section-header">
                        <h2>Users List</h2>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/Private?action=gotoAddUser">+ Add User</a>
                    </div>



                    <table class="table">
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Created</th>

                            </tr>
                        </thead>

                        <tbody>
                            <c:choose> 

                                <c:when test="${empty users}">


                                    <tr>
                                        <td colspan="5" class="empty">No users found.</td>
                                    </tr>

                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="u" items="${users.values()}">
                                        <tr>
                                            <td>${u.userName}</td>
                                            <td>${u.role}</td>
                                            <td>${u.email}</td>
                                            <td>${u.phoneNumber}</td>
                                            <td>${u.getFormattedTime()}</td>

                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                </div>


            </div>

    </body>
</html>

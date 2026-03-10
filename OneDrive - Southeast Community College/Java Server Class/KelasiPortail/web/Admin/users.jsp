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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/buttonAndForm.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    </head>
    <body>
        
        <div class="layout">


            <div class="sidebar">
                <div class="brand">
                    <div class="brand-title">KELASI</div>
                    <div class="brand-sub">${school.shortName}</div>
                    <c:choose>
                        <c:when test="${not empty school.schoolLogo}">
                            <img class="logo"  src="${pageContext.request.contextPath}/uploads/logos/${school.getSchoolLogo()}" alt="School Logo">
                        </c:when>

                    </c:choose>
                </div>

                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoSchoolProfile">School Profile</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/Private?action=listUsers">Administrators</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listTeachers">Teachers</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listStudents">Students</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoLevelsList">Levels</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoDepartmentList">Section</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout">Logout</a>
            </div>


            <div class="content">

                <div class="topbar">
                    <div>
                        <h1><i class="fa-solid fa-circle-user"></i> Administrators</h1>
                        <div class="subtext">Manage accounts for <b>${school.schoolName}</b></div>
                    </div>

                    <br>
                </div>
                <div class="section">
                    <div class="section-header">
                        <h2>Admin List</h2>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/Private?action=gotoAddUser">+ Add ADMIN</a>
                    </div>


                    <p class="mes">${me}</p>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Created</th>
                                <th>Status</th>
                                <th>Inactive</th>
                                <th>Active</th>

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
                                            <td>${u.isActive}</td>
                                            <td><a class="status-btn disabled" 
                                                   href="${pageContext.request.contextPath}/Private?action=inactiveUser&userID=${u.userID}"
                                                   onclick="return confirm('Are you sure you want to deactive this Administrator?')"
                                                   >DISABLE</a></td>
                                            <td><a class="status-btn activate"
                                                   href="${pageContext.request.contextPath}/Private?action=activeUser&userID=${u.userID}"
                                                   onclick="return confirm('Are you sure you want to active this Administrator?')"
                                                   >ACTIVATE</a></td>

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

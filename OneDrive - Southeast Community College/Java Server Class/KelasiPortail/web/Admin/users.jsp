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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    </head>
    <body>
        <style>.status-btn{
                display:inline-block;
                padding:6px 14px;
                font-size:13px;
                font-weight:600;
                border-radius:8px;
                text-decoration:none;
                border:1px solid transparent;
                cursor:pointer;
                transition: all 0.2s ease-in-out;
            }

            .status-btn.disabled{
                background:#f87171;
                color:#fff;
                border-color:#ef4444;
            }
            .status-btn.disabled:hover{
                background:#ef4444;
            }

            .status-btn.activate{
                background:#60a5fa;
                color:#fff;
                border-color:#3b82f6;
            }
            .status-btn.activate:hover{
                background:#3b82f6;
            }
            .mes {color: red;}
        </style>
        <div class="layout">

          
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
                        <h1><i class="fa-solid fa-circle-user"></i> Users</h1>
                        <div class="subtext">Manage accounts for <b>${school.schoolName}</b></div>
                    </div>

                    <br>
                </div>
                <div class="section">
                    <div class="section-header">
                        <h2>Users List</h2>
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

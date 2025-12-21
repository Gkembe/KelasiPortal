<%-- 
    Document   : allTeachers
    Created on : Dec 20, 2025, 1:56:48 PM
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
                        <h1>Teachers</h1>
                        <div class="subtext">Manage accounts for <b>${school.schoolName}</b></div>
                    </div>

                    <br>
                </div>
                <div class="section">
                    <div class="section-header">
                        <h2>Teachers List</h2>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/Private?action=gotoAddTeacher">+ Add Teacher</a>
                    </div>



                    <table class="table">
                        <thead>
                            <tr>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Subject</th>
                                <th>Qualification</th>
                                <th>Phone Number</th>
                                <th>Office Location</th>
                                <th>Status</th>
                                <th>Hire Date</th>
                                <th>Created Date</th>
                                

                            </tr>
                        </thead>

                        <tbody>
                            <c:choose> 

                                <c:when test="${empty teachers}">


                                    <tr>
                                        <td colspan="5" class="empty">No teacher found.</td>
                                    </tr>

                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="t" items="${teachers.values()}">
                                        <tr>
                                            <td>${t.firstName}</td>
                                            <td>${t.lastName}</td>
                                            <td>${t.subject}</td>
                                            <td>${t.qualification}</td>
                                            <td>${t.phoneNumber}</td>
                                            <td>${t.officeLocation}</td>
                                            <td>${t.isActive}</td>
                                            <td>${t.hireDate}</td>
                                                
                                            <td>${t.getFormattedTime()}</td>

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


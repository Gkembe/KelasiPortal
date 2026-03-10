<%-- 
    Document   : searchTeacher
    Created on : Jan 24, 2026, 4:38:01 PM
    Author     : kembe
--%>

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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/teacherButton.css">
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
                        <h1><i class="fa-solid fa-chalkboard-teacher"></i> Teachers</h1>
                        <div class="subtext">Manage accounts for <b>${school.schoolName}</b></div>
                        <div class="searchDiv">
                            <form class="forms" action="Private" method="post">

                                <input type="hidden" name="action" value="searchTeacher">

                                <input class="search" type="search" name="teacherName" placeholder="Search...">

                                <input type="submit" value="search ID">


                            </form>

                        </div>
                    </div>

                    <br>
                </div>
                <div class="section">
                    <div class="section-header">
                        <h2>Teachers Found</h2>
                        <a class="btn" href="${pageContext.request.contextPath}/Private?action=listTeachers">Cancel</a>
                    </div>


                    <div class="table-wraper">
                        <table class="table" id="tab">
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
                                    <th>Inactive</th>
                                    <th>Active</th>


                                </tr>
                            </thead>

                            <tbody>
                                <c:choose> 

                                    <c:when test="${empty getTeacher}">


                                        <tr>
                                            <td colspan="11" class="empty">No teacher found.</td>
                                        </tr>

                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="t" items="${getTeacher.values()}">
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
                                                <td>
                                                    <a class="status-btn disabled"
                                                       href="${pageContext.request.contextPath}/Private?action=inactiveTeacherSearch&teacherID=${t.teacherID}"
                                                       onclick="return confirm('Are you sure you want to deactivate this Teacher?')">
                                                        DISABLE
                                                    </a>
                                                </td>

                                                <td>
                                                    <a class="status-btn activate"
                                                       href="${pageContext.request.contextPath}/Private?action=activeTeacherSearch&teacherID=${t.teacherID}"
                                                       onclick="return confirm('Are you sure you want to activate this Teacher?')">
                                                        ACTIVATE
                                                    </a>
                                                </td>



                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>


            </div>

    </body>
</html>








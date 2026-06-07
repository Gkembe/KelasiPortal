<%-- 
    Document   : courses
    Created on : Jun 2, 2026, 1:08:21 PM
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

                <a class="nav-link active" href="${pageContext.request.contextPath}/Private?action=gotoProfile"><i class="fas fa-chart-line"></i> Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoSchoolProfile"><i class="fas fa-school"></i> School Profile</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listUsers"><i class="fas fa-user-shield"></i> Administrators</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listTeachers"><i class="fas fa-chalkboard-teacher"></i> Teachers</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listStudents"><i class="fas fa-user-graduate"></i> Students</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoLevelsList"><i class="fas fa-layer-group"></i> Levels</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoDepartmentList"><i class="fas fa-th-large"></i> Section</a>

                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoCourses"><i class="fa-solid fa-book-open"></i>  Courses</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
            </div>


            <div class="content">

                <div class="topbar">
                    <div>
                        <h1><i class="fa-solid fa-book-open"></i> Course</h1>
                        <div class="subtext">Manage accounts for <b>${school.schoolName}</b></div>
                        <div class="searchDiv">
                            <form class="forms" action="Private" method="post">

                                <input type="hidden" name="action" value="searchStudent">

                                <input class="search" type="search" name="studentID" placeholder="Search...">

                                <input type="submit" value="search ID">


                            </form>

                        </div>
                    </div>

                    <br>
                </div>





                <!-- Original -->
                <div class="section">
                    <div class="section-header">
                        <h2>Courses List</h2>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/Private?action=gotoAddCourses">+ Add Course</a>
                    </div>


                    <div class="table-wraper">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Course Code</th>
                                    <th>Course Name</th>

                                    <th>Status</th>
                                    <th>Created AT</th>

                                    <th>Inactive</th>
                                    <th>Active</th>


                                </tr>
                            </thead>

                            <tbody>
                                <c:choose> 

                                    <c:when test="${empty courses}">


                                        <tr>
                                            <td colspan="6" class="empty">No course found.</td>
                                        </tr>

                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="c" items="${courses.values()}">
                                            <tr>

                                                <td>${c.courseCode}</td>
                                                <td>${c.courseName}</td>

                                                <td>${c.status}</td>
                                                <td>${c.getFormattedTime()}</td>

                                                <td><a class="status-btn disabled" 
                                                       href="${pageContext.request.contextPath}/Private?action=deactivateCourse&courseID=${c.courseID}"
                                                       onclick="return confirm('Are you sure you want to deactive this course?')"
                                                       >DISABLE</a></td>
                                                <td><a class="status-btn activate"
                                                       href="${pageContext.request.contextPath}/Private?action=activateCourse&courseID=${c.courseID}"
                                                       onclick="return confirm('Are you sure you want to active this course?')"
                                                       >ACTIVATE</a></td>



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


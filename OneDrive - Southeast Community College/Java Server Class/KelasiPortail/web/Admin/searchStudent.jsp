<%-- 
    Document   : students
    Created on : Dec 21, 2025, 12:20:21 PM
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
                        <h1><i class="fa-solid fa-user-graduate"></i> Students</h1>
                        <div class="subtext">Manage accounts for <b>${school.schoolName}</b></div>

                        <div class="searchDiv">
                            <form class="forms" action="Private" method="post">

                                <input type="hidden" name="action" value="searchStudent"><!-- comment -->

                                <input class="search" type="search" name="studentID" placeholder="Search...">

                                <input type="submit" value="search ID">


                            </form>

                        </div>
                    </div>

                    <br>
                </div>
                <div class="section">
                    <div class="section-header">
                        <h2>Student Found</h2>
                        <a class="btn" href="${pageContext.request.contextPath}/Private?action=listStudents">Cancel</a>
                    </div>


                    <div class="table-wraper">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Student ID</th>
                                    <th>First Name</th>
                                    <th>Middle Name</th>
                                    <th>Last Name</th>
                                    
                                    <th>Status</th>
                                   
                                    <th>Inactive</th>
                                    <th>Active</th>
                                    <th>Profile</th>


                                </tr>
                            </thead>

                            <tbody>
                                <c:choose> 

                                    <c:when test="${empty getStudent}">


                                        <tr>
                                            <td colspan="14" class="empty">No student found.</td>
                                        </tr>

                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="s" items="${getStudent.values()}">
                                            <tr>
                                                <td>${s.registrationNumber}</td>
                                                <td>${s.firstName}</td>
                                                <td>${s.middleName}</td>
                                                <td>${s.lastName}</td>
                                                
                                                <td>${s.isActive}</td>
                                                
                                                <td><a class="status-btn disabled" 
                                                       href="${pageContext.request.contextPath}/Private?action=inactiveStudentSearch&registrationNumber=${s.registrationNumber}"
                                                       onclick="return confirm('Are you sure you want to deactive this student?')"
                                                       >DISABLE</a></td>
                                                <td><a class="status-btn activate"
                                                       href="${pageContext.request.contextPath}/Private?action=activeStudentSearch&registrationNumber=${s.registrationNumber}"
                                                       onclick="return confirm('Are you sure you want to active this student?')"
                                                       >ACTIVATE</a></td>
                                                <td><a class="status-btn activate"
                                                       href="${pageContext.request.contextPath}/Private?action=studentProfile&registrationNumber=${s.registrationNumber}"

                                                       >Profile</a></td>

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

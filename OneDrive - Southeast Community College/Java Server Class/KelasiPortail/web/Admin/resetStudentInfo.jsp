<%-- 
    Document   : resetStudentInfo
    Created on : Mar 13, 2026, 12:17:36 PM
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
                        <h1>Reset Student</h1>
                        <div class="subtext">Fill student information</div>
                    </div>
                </div>

                <div class="section">
                    <div class="section-header">
                        <h2>Student Information</h2>
                    </div>
                    <ul style="color: red">

                        <c:forEach var="e" items="${ERRORS}">


                            <li>${e}</li>
                            </c:forEach>


                    </ul>
                    <p>${success}</p>

                    <form class="form" action="${pageContext.request.contextPath}/Private" method="post">
                        <input type="hidden" name="action" value="resetStudentInfo">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">
                        <input type="hidden" name="userID" value="${student.userID}">
                        <input type="hidden" name="studentID" value="${student.studentID}">
                        <input type="hidden" name="registrationNumber" value="${student.registrationNumber}">
                        <div class="form-grid">

                            <div class="form-row">
                                <label class="form-label">Phone Number</label>
                                <input class="form-input" type="text" name="phoneNumber" value="${user.phoneNumber}" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">Email</label>
                                <input class="form-input" type="text" name="email" value="${user.email}" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">New Password</label>
                                <input class="form-input" type="text" name="password">
                            </div>
                            <div class="form-row">
                                <label class="form-label">Confirm Password</label>
                                <input class="form-input" type="text" name="confirmPassword">
                            </div>
                        </div>
                            
                            <div class="actions">
                            <input class="btn btn-primary" type="submit" value="Add Student">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=studentProfile&registrationNumber=${registrationNumber}">Cancel</a>
                        </div>
                            
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>



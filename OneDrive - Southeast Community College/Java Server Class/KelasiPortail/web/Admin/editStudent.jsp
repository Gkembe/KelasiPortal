<%-- 
    Document   : editStudent
    Created on : Mar 11, 2026, 8:06:46 PM
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
                        <h1>Edit Student</h1>
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
                        <input type="hidden" name="action" value="editStudent">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">
                        <input type="hidden" name="studentID" value="${student.studentID}">
                        <input type="hidden" name="registrationNumber" value="${student.registrationNumber}">
                        <div class="form-grid">

                            <div class="form-row">
                                <label class="form-label">First Name</label>
                                <input class="form-input" type="text" name="firstName" value="${student.firstName}" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">Middle Name (Optional)</label>
                                <input class="form-input" type="text" name="middleName" value="${student.middleName}" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">Last Name</label>
                                <input class="form-input" type="text" name="lastName" value="${student.lastName}" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">Gender</label>
                                <select class="form-input" name="gender" required>
                                    <option value="MALE">MALE</option>
                                    <option value="FEMALE">FEMALE</option>
                                    <option value="OTHER">OTHER</option>
                                </select>
                            </div>

                            <div class="form-row">
                                <label class="form-label">Birth Date</label>
                                <input class="form-input" type="date" name="birthDate" value="${student.dateOfBirth}">
                            </div>


                            <div class="form-row">
                                <label class="form-label">Academic Year</label>
                                <input class="form-input" type="text" name="academicYear" value="${student.academicYear}">
                            </div>


                            <div class="form-row">
                                <label class="form-label">Phone Number</label>
                                <input class="form-input" type="text" name="phoneNumber" placeholder="+14024307272" value="${student.phoneNumber}">
                            </div>

                            <div class="form-row">
                                <label class="form-label">Address</label>
                                <input class="form-input" type="text" name="address" placeholder="7830 D Street Apt 3" value="${student.address}">
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


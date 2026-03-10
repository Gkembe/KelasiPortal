<%-- 
    Document   : studentProfile
    Created on : Mar 8, 2026, 7:50:49 PM
    Author     : kembe
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Kelasi - Admin Profile</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/dashboard.css">
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

                <a class="nav-link active" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoSchoolProfile">School Profile</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listUsers">Administrators</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listTeachers">Teachers</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listStudents">Students</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoLevelsList">Levels</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoDepartmentList">Section</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout">Logout</a>
            </div>


            <div class="content">


                <div class="topbar">
                    <div>
                        <h1>Profile</h1>
                        <div class="subtext">
                            ${student.firstName} ${student.lastName} | ${student.registrationNumber}
                        </div>
                    </div>

                    
                </div>


                <div class="cards">



                    <div class="section">
                        <div class="section-header">
                            <h2>Student Information</h2>
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoEditSchoolProfile">Edit</a>
                        </div>

                        <div class="info-grid">
                            <div class="info-item"><span>First Name:</span> ${student.firstName}</div>
                            <div class="info-item"><span>Middle Name:</span> ${student.middleName}</div>
                            <div class="info-item"><span>Last Name:</span> ${student.lastName}</div>
                            <div class="info-item"><span>Gender:</span> ${student.gender}</div>
                            <div class="info-item"><span>Date of Birth:</span> ${student.dateOfBirth}</div>
                            <div class="info-item"><span>Enrollment Date:</span> ${student.enrollmentDate}</div>
                            <div class="info-item"><span>Academic Year:</span> ${student.academicYear}</div>
                            <div class="info-item"><span>Phone Number:</span> ${student.phoneNumber}</div>

                            <div class="info-item"><span> 📍 Address:</span> ${student.address}</div>


                            <div class="info-item"><span>Registration Number:</span> ${student.registrationNumber}</div>
                            <div class="info-item"><span>Level :</span> ${student.levelName}</div>
                            <div class="info-item"><span>Section :</span> ${student.departmentName}</div>
                            <div class="info-item"><span>Level Code :</span> ${student.levelCode} ${student.departmentName}</div>


                            <div class="info-item"><span>Creation Date:</span> ${student.getCreatedFormattedTime()}</div>
                            <div class="info-item"><span>Last Update:</span> ${student.getUpdatedFormattedTime()}</div>
                            <div class="info-item">
                                <span>Status:</span>
                                <c:choose>
                                    <c:when test="${student.isActive == 'ACTIVE'}">
                                        <span class="badge active">ACTIVE</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge inactive">INACTIVE</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>


                        </div>
                    </div>




                </div>
            </div>

    </body>
</html>

<%-- 
    Document   : teacherProfile
    Created on : Jun 7, 2026, 5:57:59 PM
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
                        <h1>Profile</h1>
                        <div class="subtext">
                            ${teacher.firstName} ${teacher.middleName} ${teacher.lastName}
                        </div>


                    </div>


                </div>


                <div class="cards">



                    <div class="section">
                        <div class="section-header">
                            <h2>Teacher Information</h2>
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoEditTeacherProfile&teacherID=${teacher.teacherID}">Edit Info</a>


                        </div>

                        <div class="info-grid">
                            <div class="info-item"><span>First Name:</span> ${teacher.firstName}</div>
                            <div class="info-item"><span>Middle Name:</span> ${teacher.middleName}</div>
                            <div class="info-item"><span>Last Name:</span> ${teacher.lastName}</div>
                            <div class="info-item"><span>Gender:</span> ${teacher.gender}</div>
                            <div class="info-item"><span>Date of Birth:</span> ${teacher.dateOfBirth}</div>
                            <div class="info-item"><span>Hire Date:</span> ${teacher.hireDate}</div>
                            <div class="info-item"><span>Subject:</span> ${teacher.subject}</div>
                            <div class="info-item"><span>Phone Number:</span> ${teacher.phoneNumber}</div>

                            <div class="info-item"><span>Address:</span> ${teacher.address}</div>


                            <div class="info-item"><span>Qualification:</span> ${teacher.qualification}</div>
                            <div class="info-item"><span>Office Location :</span> ${teacher.officeLocation}</div>
                            <div class="info-item"><span>Email :</span> ${teacher.email}</div>



                            <div class="info-item"><span>Creation Date:</span> ${teacher.getFormattedTime()}</div>

                            <div class="info-item">
                                <span>Status:</span>
                                <c:choose>
                                    <c:when test="${teacher.isActive == 'ACTIVE'}">
                                        <span class="badge active">ACTIVE</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge inactive">INACTIVE</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>


                        </div>


                    </div>

                    <div class="section">
                        <h2>Quick Actions</h2>
                        <div class="actions">
                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/Private?action=gotoAddStudentGuardian&teacherID=${teacher.teacherID}&userID=${teacher.userID}">+ Attribute Courses</a>
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoResetTeacherLoginInfo&teacherID=${teacher.teacherID}&userID=${teacher.userID}">Reset login</a>
                        </div>

                    </div>
                        
                        
                        
                        
                        
                </div>
            </div>


    </body>
</html>

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
                            ${student.firstName} ${student.lastName} | ${student.registrationNumber}
                        </div>


                    </div>


                </div>


                <div class="cards">



                    <div class="section">
                        <div class="section-header">
                            <h2>Student Information</h2>
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoEditStudentProfile&registrationNumber=${student.registrationNumber}&studentID=${student.studentID}">Edit Info</a>


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

                            <div class="info-item"><span>Address:</span> ${student.address}</div>


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

                    <div class="section">
                        <h2>Quick Actions</h2>
                        <div class="actions">
                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/Private?action=gotoAddStudentGuardian&registrationNumber=${student.registrationNumber}&studentID=${student.studentID}&userID=${student.userID}">+ Add Guardian</a>
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoResetStudentLoginInfo&registrationNumber=${student.registrationNumber}&studentID=${student.studentID}&userID=${student.userID}">Reset login</a>
                        </div>

                    </div>

                    <div class="section">
                        <h2>Guardians Information</h2>
                    <div class="table-wraper">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Full Name</th>
                                    <th>Phone Number</th>

                                    <th>Email Address</th>
                                    <th>Home Address</th>
                                    <th>Occupation</th>
                                    <th>Relationship</th>
                                    <th>Default</th>
                                    <th>Created AT</th>

                                    <th>Updated AT</th>
                                    <th>Delete</th>
                                    <th>Update Guardian</th>



                                </tr>
                            </thead>

                            <tbody>
                                <c:choose> 

                                    <c:when test="${empty guardian}">


                                        <tr>
                                            <td colspan="12" class="empty">No guardian found.</td>
                                        </tr>

                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="g" items="${guardian.values()}">
                                            <tr>

                                                <td>${g.fullName}</td>
                                                <td>${g.phone}</td>

                                                <td>${g.email}</td>
                                                <td>${g.address}</td>
                                                <td>${g.occupation}</td>
                                                <td>${g.relationship}</td>

                                                <td><c:choose>
                                                        <c:when test="${g.isPrimary == 'YES'}">
                                                            Default
                                                        </c:when>
                                                        <c:otherwise>
                                                            NO
                                                        </c:otherwise>
                                                    </c:choose></td>
                                                <td>${g.getFormattedTime()}</td>
                                                <td>${g.getUpdatedFormattedTime()}</td>
                                                <td><a class="status-btn disabled" 
                                                       href="${pageContext.request.contextPath}/Private?action=deactivateGuardian&studentID=${g.studentID}&guardianID=${g.guardianID}&registrationNumber=${student.registrationNumber}"
                                                       onclick="return confirm('Are you sure you want to delete this Guardian?')"
                                                       >Delete</a></td>

                                                <td><a class="status-btn activate" 
                                                       href="${pageContext.request.contextPath}/Private?action=gotoEditGuardian&studentID=${g.studentID}&guardianID=${g.guardianID}&registrationNumber=${student.registrationNumber}&studentID=${student.studentID}"
                                                       
                                                       >Edit</a></td>




                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                    </div>

                


            </div>
        </div>

    </body>
</html>

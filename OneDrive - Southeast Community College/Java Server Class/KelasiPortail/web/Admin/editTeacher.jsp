<%-- 
    Document   : editTeacher
    Created on : Jun 8, 2026, 11:47:11 AM
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
                        <h1>Edit Teacher</h1>
                        <div class="subtext">Fill teacher information</div>
                    </div>
                </div>

                <div class="section">
                    <div class="section-header">
                        <h2>Teacher Information</h2>
                    </div>
                    <ul style="color: red">

                        <c:forEach var="me" items="${errors}">


                            <li>${me}</li>
                            </c:forEach>


                    </ul>

                    <p>${erros}</p>

                    <p>${message}</p>

                    <form class="form" action="${pageContext.request.contextPath}/Private" method="post">
                        <input type="hidden" name="action" value="editTeacher">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">
                        <input type="hidden" name="userID" value="${user.userID}">
                        <input type="hidden" name="teacherID" value="${teacher.teacherID}">

                        <div class="form-grid">
                            <div class="form-row">
                                <label class="form-label">First Name</label>
                                <input class="form-input" type="text" name="firstName" value="${teacher.firstName}" placeholder="John" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">Middle Name</label>
                                <input class="form-input" type="text" name="middleName" value="${teacher.middleName}" placeholder="Doe" >
                            </div>
                            <div class="form-row">
                                <label class="form-label">Last Name</label>
                                <input class="form-input" type="text" name="lastName" value="${teacher.lastName}" placeholder="Doe" >
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Gender</label>
                                <select class="form-input" name="gender" value="${teacher.gender}" required>
                                    <option value="MALE">MALE</option>
                                    <option value="FEMALE">FEMALE</option>
                                    <option value="OTHER">OTHER</option>
                                </select>
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Birth Date</label>
                                <input class="form-input" type="date" name="birthDate" value="${teacher.dateOfBirth}">
                            </div>

                            <div class="form-row">
                                <label class="form-label">Phone Number</label>
                                <input class="form-input" type="text" name="phoneNumber" value="${teacher.phoneNumber}" placeholder="+14024307272">
                            </div>

                            <div class="form-row">
                                <label class="form-label">Office Location</label>
                                <input class="form-input" type="text" name="officeLocation" value="${teacher.officeLocation}" placeholder="Room 203">
                            </div>
                            
                             <div class="form-row">
                                <label class="form-label">Home Address</label>
                                <input class="form-input" type="text" name="address" value="${teacher.address}" placeholder="6758 D street APT 3">
                            </div>
                            
                             

                            <div class="form-row">
                                <label class="form-label">Subject</label>
                                <input class="form-input" type="text" name="specification" value="${teacher.subject}" placeholder="Mathematics / Computer Science">
                            </div>

                           
                            
                            <div class="form-row">
                                <label class="form-label">Qualification</label>
                                <input class="form-input" type="text" name="qualification" value="${teacher.qualification}" placeholder="BS Computer Sceience">
                            </div>

                            <div class="form-row">
                                <label class="form-label">Hire Date</label>
                                <input class="form-input" type="date" name="hireDate" value="${teacher.hireDate}">
                            </div>

                            
                           

                            <div class="form-row">
                                <label for="adminemail">Email</label>
                                <input type="email" class="form-input" name="adminemail" value="${teacher.email}" placeholder="admin@school.com" />
                            </div>

                            

                            
                        </div>

                        <div class="actions">
                            <input class="btn btn-primary" type="submit" value="Save">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=teacherProfile&teacherID=${teacher.teacherID}">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>


    </body>
</html>

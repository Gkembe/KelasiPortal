<%-- 
    Document   : addStudentGuardian
    Created on : Mar 9, 2026, 8:18:01 PM
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
                        <h1>Add Student Guardian</h1>
                        <div class="subtext">Fill Guardian information</div>
                    </div>
                </div>

                <div class="section">
                    <div class="section-header">
                        <h2>Section Information</h2>
                    </div>
                    <ul style="color: red">

                        <c:forEach var="e" items="${ERROR}">


                            <li>${e}</li>
                            </c:forEach>

                    </ul>
                    <p style="color: green">${success}</p>

                    <form class="form" action="${pageContext.request.contextPath}/Private" method="post">
                        <input type="hidden" name="action" value="addStudentGuardian">
                        <input type="hidden" name="studentID" value="${studentID}">
                        <input type="hidden" name="registrationNumber" value="${registrationNumber}">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">


                        <div class="form-grid">

                            <div class="form-row">
                                <label for="fullName" class="form-label">Full Name</label>
                                <input type="text" class="form-input" name="fullName" placeholder="Jean Robert"  />
                            </div>

                            <div class="form-row">
                                <label for="phone" class="form-label">Phone Number</label>
                                <input type="text" class="form-input" name="phone" placeholder="+14023307287"  />
                            </div>

                            <div class="form-row">
                                <label for="email" class="form-label">Email </label>
                                <input type="text" class="form-input" name="email" placeholder="JeanRobert@gmail.com"  />
                            </div>

                            <div class="form-row">
                                <label for="address" class="form-label">Address</label>
                                <input type="text" class="form-input" name="address" placeholder="Av Sao 17 Q/Kindele "  />
                            </div>

                            <div class="form-row">
                                <label for="occupation" class="form-label">Occupation</label>
                                <input type="text" class="form-input" name="occupation" placeholder="Software engineer"  />
                            </div>

                            <div class="form-row">
                                <label class="form-label">Relationship</label>
                                <select class="form-input" name="relationship" required>
                                    <option value="Father">Father</option>
                                    <option value="Mother">Mother</option>
                                    <option value="Uncle">Uncle</option>
                                    <option value="Aunt">Aunt</option>
                                    <option value="Brother">Brother</option>
                                    <option value="Sister">Sister</option>
                                    <option value="Other">Other</option>
                                </select></div>

                            <div class="form-row">
                                <label class="form-label">Default</label>
                                <select class="form-input" name="isPrimary" required>
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>

                                </select>
                            </div>

                            



                        </div>
                        <div class="actions">
                            <input class="btn btn-primary" type="submit" value="Add Guardian">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=studentProfile&registrationNumber=${registrationNumber}">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>


    </body>
</html>
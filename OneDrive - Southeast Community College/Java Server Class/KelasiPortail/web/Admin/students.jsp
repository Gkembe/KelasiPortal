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
    </head>
    <body>

        <style> </style>

        <div class="layout">

            <!-- SIDEBAR -->
            <div class="sidebar">
                <div class="brand">
                    <div class="brand-title">KELASI</div>
                    <div class="brand-sub">${school.shortName}</div>
                </div>

                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoSchoolProfile">School Profile</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/Private?action=listUsers">All Users</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listTeachers">Teachers</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listStudents">Students</a>

                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout">Logout</a>
            </div>

            <!-- CONTENT -->
            <div class="content">

                <div class="topbar">
                    <div>
                        <h1>Students</h1>
                        <div class="subtext">Manage accounts for <b>${school.schoolName}</b></div>
                    </div>

                    <br>
                </div>
                <div class="section">
                    <div class="section-header">
                        <h2>Students List</h2>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/Private?action=gotoAddStudent">+ Add Student</a>
                    </div>


                    <div class="table-wraper">
                        <table class="table" id="tab">
                            <thead>
                                <tr>
                                    <th>Student ID</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Gender</th>
                                    <th>Birth Date</th>
                                    <th>Grade Level</th>
                                    <th>Department</th>
                                    <th>Enrollment Date</th>
                                    <th>Academic Year</th>
                                    <th>Phone Number</th>
                                    <th>Address</th>
                                    <th>Status</th>
                                    <th>Created AT</th>
                                    <th>Updated AT</th>
                                    <th>Inactive</th>
                                    <th>Active</th>


                                </tr>
                            </thead>

                            <tbody>
                                <c:choose> 

                                    <c:when test="${empty student}">


                                        <tr>
                                            <td colspan="13" class="empty">No student found.</td>
                                        </tr>

                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="s" items="${student.values()}">
                                            <tr>
                                                <td>${s.registrationNumber}</td>
                                                <td>${s.firstName}</td>
                                                <td>${s.lastName}</td>
                                                <td>${s.gender}</td>
                                                <td>${s.dateOfBirth}</td>
                                                <td>${s.gradeLevel}</td>
                                                <td>${s.department}</td>
                                                <td>${s.enrollmentDate}</td>
                                                <td>${s.academicYear}</td>
                                                <td>${s.phoneNumber}</td>
                                                <td>${s.address}</td>
                                                <td>${s.isActive}</td>
                                                <td>${s.getCreatedFormattedTime()}</td>
                                                <td>${s.getUpdatedFormattedTime()}</td>
                                                <td><a style="border: 1px solid #d1d5db; text-decoration: none; background-color: rgb(232, 97, 97);" 
                                                       href="${pageContext.request.contextPath}/Private?action=inactiveStudent&registrationNumber=${s.registrationNumber}"
                                                       onclick="return confirm('Are you sure you want to deactive this student?')"
                                                       >DISABLE</a></td>
                                                <td><a class="desactBu" style="border: 1px solid #d1d5db; text-decoration: none; background-color: #72a8e6ff;"
                                                       href="${pageContext.request.contextPath}/Private?action=activeStudent&registrationNumber=${s.registrationNumber}"
                                                       onclick="return confirm('Are you sure you want to active this student?')"
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

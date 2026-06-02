<%-- 
    Document   : addSection
    Created on : Feb 19, 2026, 8:26:03 PM
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

                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
            </div>


            <div class="content">

                <div class="topbar">
                    <div>
                        <h1>Choose Section</h1>
                        <div class="subtext">Fill Section information</div>
                    </div>
                </div>

                <div class="section">
                    <div class="section-header">
                        <h2>Section Information</h2>
                    </div>
                    <ul style="color: red">

                        <c:forEach var="e" items="${errors}">


                            <li>${e}</li>
                            </c:forEach>

                    </ul>
                    <p style="color: green">${success}</p>

                    <form class="form" action="${pageContext.request.contextPath}/Private" method="post">
                        <input type="hidden" name="action" value="gotoAddStudent">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">


                        <div class="form-row">
                            <label class="form-label">Section</label>


                            <select name="departmentID">

                                <option value="">-- Select Section --</option>

                                <c:forEach var="d" items="${departments}">
                                    <option value="${d.departmentID}">
                                        ${d.departmentName}
                                    </option>
                                </c:forEach>
                            </select>


                        </div>
                        <div class="actions">
                            <input class="btn btn-primary" type="submit" value="Ok">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>


    </body>
</html>



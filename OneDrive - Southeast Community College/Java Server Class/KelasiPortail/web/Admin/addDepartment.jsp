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
                        <h1>Add School Section</h1>
                        <div class="subtext">Fill Section information</div>
                    </div>
                </div>

                <div class="section">
                    <div class="section-header">
                        <h2>Section Information</h2>
                    </div>
                    <ul style="color: red">

                        <c:forEach var="e" items="${ERRORS}">


                            <li>${e}</li>
                            </c:forEach>

                    </ul>
                    <p style="color: green">${success}</p>

                    <form class="form" action="${pageContext.request.contextPath}/Private" method="post">
                        <input type="hidden" name="action" value="addDepartment">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">
                        

                        <div class="form-grid">

                            <div class="form-row">
                                <label for="departmentName">Section Name</label>
                                <input type="text" class="form-input" name="departmentName" placeholder="Electronic"  />
                            </div>
                            

                           
                            
                        </div>
                        <div class="actions">
                            <input class="btn btn-primary" type="submit" value="Add Section">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>


    </body>
</html>



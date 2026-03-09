<%-- 
    Document   : addLevels
    Created on : Jan 25, 2026, 5:30:28 PM
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
                </div>

                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoSchoolProfile">School Profile</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/Private?action=listUsers">All Users</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listTeachers">Teachers</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listStudents">Students</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoLevelsList">Levels</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoDepartmentList">Section</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout">Logout</a>
            </div>


            <div class="content">

                <div class="topbar">
                    <div>
                        <h1>Add School Level</h1>
                        <div class="subtext">Fill Level information</div>
                    </div>
                </div>

                <div class="section">
                    <div class="section-header">
                        <h2>Level Information</h2>
                    </div>
                    <ul style="color: red">

                        <c:forEach var="e" items="${ERRORS}">


                            <li>${e}</li>
                            </c:forEach>

                    </ul>
                    <p style="color: green">${success}</p>

                    <form class="form" action="${pageContext.request.contextPath}/Private" method="post">
                        <input type="hidden" name="action" value="addLevels">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">
                        

                        <div class="form-grid">

                            <div class="form-row">
                                <label for="levelName">Level Name</label>
                                <input type="text" class="form-input" name="levelName" placeholder="Troisieme"  />
                            </div>
                            <div class="form-row">
                                <label for="levelCode">Level Code</label>
                                <input type="text" class="form-input" name="levelCode" placeholder="3th" />
                            </div>
                            
                            <div class="form-row">
                                <label for="levelCode">Section </label>
                                <select class="form-input" name="departmentID" required>
                                    <option value="">--Select Section--</option>
                                    <c:forEach var="d" items="${department.values()}">
                                        

                                        <option value="${d.departmentID}">${d.departmentName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                           
                            
                        </div>
                        <div class="actions">
                            <input class="btn btn-primary" type="submit" value="Add Level">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>


    </body>
</html>


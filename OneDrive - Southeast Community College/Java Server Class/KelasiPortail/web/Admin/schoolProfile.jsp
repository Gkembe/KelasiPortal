<%-- 
    Document   : schoolProfile
    Created on : Jan 4, 2026, 4:32:35 PM
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

            <!-- SIDEBAR -->
            <div class="sidebar">

                <div class="brand">

                    <div class="brand-title">KELASI</div>
                    <div class="brand-sub">${school.shortName}</div>
                </div>

                <a class="nav-link active" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=gotoSchoolProfile">School Profile</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listUsers">All Users</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listTeachers">Teachers</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Private?action=listStudents">Students</a>

                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout">Logout</a>
            </div>

            <!-- CONTENT -->
            <div class="content">

                <!-- TOP BAR -->
                <div class="topbar">
                    <div>
                        <h1>Profile</h1>
                        <div class="subtext">
                            ${school.schoolName}
                        </div>
                    </div>

                    <div class="school-mini">
                        <c:choose>
                            <c:when test="${not empty school.schoolLogo}">
                                <img class="logo" src="${pageContext.request.contextPath}/uploads/logos/${school.getSchoolLogo()}" alt="School Logo">
                            </c:when>


                        </c:choose>



                    </div>
                </div>

                <!-- STATS CARDS  -->
                <div class="cards">


                    <!-- SCHOOL INFO -->
                    <div class="section">
                        <div class="section-header">
                            <h2>School Information</h2>
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoEditSchoolProfile">Edit</a>
                        </div>

                        <div class="info-grid">
                            <div class="info-item"><span>Name:</span> ${school.schoolName}</div>
                            <div class="info-item"><span>Short Name:</span> ${school.shortName}</div>

                            <div class="info-item"><span>📧 Email:</span> ${school.schoolEmail}</div>
                            <div class="info-item"><span>Country:</span> ${school.country}</div>
                            <div class="info-item"><span> 📍 City:</span> ${school.schoolCity}</div>
                            <div class="info-item"><span>School Address:</span> ${school.schoolAddress}</div>
                            <div class="info-item"><span>Website:</span> ${school.website}</div>
                            <div class="info-item"><span>Registration Number:</span> ${school.registrationNumber}</div>
                            <div class="info-item"><span>School Type:</span> ${school.schoolType}</div>
                            <div class="info-item"><span>School Level:</span> ${school.schoolLevel}</div>
                            <div class="info-item"><span>Creation Date:</span> ${school.getFormattedTime()}</div>
                            <div class="info-item">
                                <span>Status:</span>
                                <c:choose>
                                    <c:when test="${school.active}">
                                        <span class="badge active">ACTIVE</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge inactive">INACTIVE</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="info-item"><span>SchoolID:</span> ${school.schoolID}</div>
                        </div>
                    </div>

                    <!-- QUICK ACTIONS -->
                    <div class="section">
                        <h2>Quick Actions</h2>
                        <div class="actions">

                            <p>${successMessage}</p>

                            <c:if test="${not empty errors}">
                                <div class="alert alert-danger">
                                    <ul>
                                        <c:forEach var="e" items="${errors}">
                                            <c:if test="${not empty e}">
                                                <li>${e}</li>
                                                </c:if>
                                            </c:forEach>
                                    </ul>
                                </div>
                            </c:if>

                            <br>
                            <form action="${pageContext.request.contextPath}/Private"
                                  method="post"
                                  enctype="multipart/form-data">


                                <input type="hidden" name="action" value="changeLogo">


                                <input type="file" name="schoollogo" accept="image/png, image/jpeg" required>
                                <button type="submit" class="btn btn-primary">
                                    <c:choose>
                                        <c:when test="${empty school.schoolLogo}">
                                            Upload Logo
                                        </c:when>
                                        <c:otherwise>
                                            Change Logo
                                        </c:otherwise>
                                    </c:choose>
                                </button>



                            </form>
                        </div>
                    </div>

                </div>
            </div>

    </body>
</html>
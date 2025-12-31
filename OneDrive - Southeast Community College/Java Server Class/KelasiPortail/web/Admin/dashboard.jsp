 <%-- 
    Document   : dashboard
    Created on : Dec 17, 2025, 10:24:51 AM
    Author     : kembe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>Kelasi - Admin Dashboard</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/dashboard.css">
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
                <h1>Dashboard</h1>
                <div class="subtext">
                    Welcome <b>${loggedInUser.userName}</b> (Role: <b>${loggedInUser.role}</b>)
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
            <div class="card">
                <div class="card-title">School</div>
                <div class="card-value">${school.shortName}</div>
                <div class="card-note">${school.schoolName}</div>
            </div>

            <div class="card">
                <div class="card-title">Users</div>
                <div class="card-value">${requestScope.totalUsers}</div>
                <div class="card-note">Total accounts</div>
            </div>

            <div class="card">
                <div class="card-title">Students</div>
                <div class="card-value">${requestScope.totalStudents}</div>
                <div class="card-note">Registered students</div>
            </div>

            <div class="card">
                <div class="card-title">Teachers</div>
                <div class="card-value">${requestScope.totalTeachers}</div>
                <div class="card-note">Active teachers</div>
            </div>
        </div>

        <!-- SCHOOL INFO -->
        <div class="section">
            <div class="section-header">
                <h2>School Information</h2>
                <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoSchoolProfile">Edit</a>
            </div>

            <div class="info-grid">
                <div class="info-item"><span>Name:</span> ${school.schoolName}</div>
                <div class="info-item"><span>Short Name:</span> ${school.shortName}</div>
                <div class="info-item"><span>Email:</span> ${school.schoolEmail}</div>
                <div class="info-item"><span>Country:</span> ${school.country}</div>
                <div class="info-item"><span>City:</span> ${school.schoolCity}</div>
                <div class="info-item"><span>Website:</span> ${school.website}</div>

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
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/Private?action=gotoAddUser">+ Add ADMIN</a>
                <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoAddTeacher">+ Add Teacher</a>
                <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoAddStudent">+ Add Student</a>
            </div>
        </div>

    </div>
</div>

</body>
</html>

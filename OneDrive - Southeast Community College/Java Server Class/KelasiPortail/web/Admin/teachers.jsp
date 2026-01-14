<%-- 
    Document   : teachers
    Created on : Dec 18, 2025, 1:09:32 PM
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

                <a class="nav-link logout" href="${pageContext.request.contextPath}/Public?action=logout">Logout</a>
            </div>


            <div class="content">

                <div class="topbar">
                    <div>
                        <h1>Add Teacher</h1>
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
                        <input type="hidden" name="action" value="addTeacher">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">
                        <input type="hidden" name="userID" value="${user.userID}">

                        <div class="form-grid">
                            <div class="form-row">
                                <label class="form-label">First Name</label>
                                <input class="form-input" type="text" name="firstName" placeholder="John" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">Last Name</label>
                                <input class="form-input" type="text" name="lastName" placeholder="Doe" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">Phone Number</label>
                                <input class="form-input" type="text" name="phoneNumber" placeholder="+14024307272">
                            </div>

                            <div class="form-row">
                                <label class="form-label">Office Location</label>
                                <input class="form-input" type="text" name="officeLocation" placeholder="Room 203">
                            </div>

                            <div class="form-row">
                                <label class="form-label">Specialty</label>
                                <input class="form-input" type="text" name="specification" placeholder="Mathematics / Computer Science">
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Qualification</label>
                                <input class="form-input" type="text" name="qualification" placeholder="BS Computer Sceience">
                            </div>

                            <div class="form-row">
                                <label class="form-label">Hire Date</label>
                                <input class="form-input" type="date" name="hireDate">
                            </div>

                            <div class="form-row">
                                <label class="form-label">Status</label>
                                <select class="form-input" name="status" required>
                                    <option value="ACTIVE">ACTIVE</option>
                                    <option value="INACTIVE">INACTIVE</option>
                                </select>
                            </div>
                            <div class="form-row">
                                <label for="username">Username ADMIN</label>
                                <input type="text" class="form-input" name="username" placeholder="Andree"  />
                            </div>


                            <div class="form-row">
                                <label for="adminemail">Email ADMIN</label>
                                <input type="email" class="form-input" name="adminemail" placeholder="admin@school.com" />
                            </div>

                            <div class="form-row">
                                <label for="adminphone" class="form-label">Phone ADMIN</label>
                                <input type="text" class="form-input" name="adminphone" placeholder="+14024307272"  />
                            </div>

                            
                                <div class="form-row">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-input" name="password" placeholder="At least 6 characters"  minlength="6" />
                                </div>
                                <div class="form-row">
                                    <label for="confirm" class="form-label">Confirm Password</label>
                                    <input type="password" class="form-input" name="confirmpassword" placeholder="Re-type password"  minlength="6" />
                                </div>
                            
                        </div>

                        <div class="actions">
                            <input class="btn btn-primary" type="submit" value="Add Teacher">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>


    </body>
</html>

<%-- 
    Document   : addStudents
    Created on : Dec 20, 2025, 3:59:58 PM
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


            <div class="content">

                <div class="topbar">
                    <div>
                        <h1>Add Student</h1>
                        <div class="subtext">Fill student information</div>
                    </div>
                </div>

                <div class="section">
                    <div class="section-header">
                        <h2>Student Information</h2>
                    </div>
                    <ul style="color: red">
                        
                        <c:forEach var="b" items="${badMessage}">
                            
                            
                            <li>${b}</li>
                        </c:forEach>
                        
                        
                    </ul>
                    
                    
                    
                    <p>${message}</p>

                    <form class="form" action="${pageContext.request.contextPath}/Private" method="post">
                        <input type="hidden" name="action" value="addStudent">
                        <input type="hidden" name="schoolID" value="${school.schoolID}">
                        <input type="hidden" name="userID" value="${user.userID}">

                        <div class="form-grid">
                            <div class="form-row">
                                <label class="form-label">Student ID</label>
                                <input class="form-input" type="text" name="registrationNumber" placeholder="GK7878" >
                            </div>
                            <div class="form-row">
                                <label class="form-label">First Name</label>
                                <input class="form-input" type="text" name="firstName" placeholder="John" >
                            </div>

                            <div class="form-row">
                                <label class="form-label">Last Name</label>
                                <input class="form-input" type="text" name="lastName" placeholder="Doe" >
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Gender</label>
                                <select class="form-input" name="gender" required>
                                    <option value="MALE">MALE</option>
                                    <option value="FEMALE">FEMALE</option>
                                    <option value="OTHER">OTHER</option>
                                </select>
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Birth Date</label>
                                <input class="form-input" type="date" name="birthDate">
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Grade Level</label>
                                <select class="form-input" name="gradeLevel" required>
                                    <option value="">--Select Grade Level--</option>
                                    <option value="Freshman">Freshman</option>
                                    <option value="Sophomore">Sophomore</option>
                                    <option value="Junior">Junior</option>
                                    <option value="Senior">Senior</option>
                                </select>
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Department</label>
                                <input class="form-input" type="text" name="department" placeholder="Cybersecurity">
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Enrollment Date</label>
                                <input class="form-input" type="date" name="enrollmentDate">
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Academic Year</label>
                                <input class="form-input" type="text" name="academicYear" placeholder="2020-2021">
                            </div>
                            
                            
                            <div class="form-row">
                                <label class="form-label">Phone Number</label>
                                <input class="form-input" type="text" name="phoneNumber" placeholder="+14024307272">
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Address</label>
                                <input class="form-input" type="text" name="address" placeholder="7830 D Street Apt 3">
                            </div>
                            
                            <div class="form-row">
                                <label class="form-label">Status</label>
                                <select class="form-input" name="isActive" required>
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
                            <input class="btn btn-primary" type="submit" value="Add Student">
                            <a class="btn" href="${pageContext.request.contextPath}/Private?action=gotoProfile">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>


    </body>
</html>

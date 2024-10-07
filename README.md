# Spring Security Quick Start Guide

This project is a Spring Boot application that demonstrates how to implment role-based access control using Spring Security.

## Project Structure
### Pages
- **/home** :  A publicly accessible home page that can be viewed without authentication or authorization.
- **/user/home** : A protected page that is only accessible by authenticated users with the "USER" role
- **/admin/home** : A protected page that is only accessible by authenticated users with the "ADMIN" role.

**Role-based redirection after successful login :**
  - Admin users are redirected to **/admin/home**
  - Normal users are redirected to **/user/home**

### Custom Error pages
**Custom error pages**
  - **403 Forbidden page** displayed for unauthorized access attempts
  - **404 Not Found page** displayed for non-existing routes

### Thymeleaf Templates
- **home.html** : Publicly accessible home page
- **admin_home.html** : Admin-specific home page
- **user_home.html** : User-specific home page
- **custom_login_page.html** : Custom login page for user authentication

## Technologies Used

 
    

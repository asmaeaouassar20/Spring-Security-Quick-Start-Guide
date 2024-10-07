# Spring Security Quick Start Guide

This project is a Spring Boot application that demonstrates how to implment role-based access control using Spring Security.

## features
- **/home** :  A publicly accessible home page that can be viewed without authentication or authorization.
- **/user/home** : A protected page that is only accessible by authenticated users with the "USER" role
- **/admin/home** : A protected page that is only accessible by authenticated users with the "ADMIN" role.

**Role-based redirection after successful login :**
  - Admin users are redirected to **/admin/home**
  - Normal users are redirected to **/user/home**
 
**Custom error pages**
  - **403 Forbidden page** displayed for unauthorized access attempts
  - **404 Not Found page** displayed for non-existing routes
 
    

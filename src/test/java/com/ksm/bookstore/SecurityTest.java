package com.ksm.bookstore;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * Verifies the public pages render and the FORM login protects the admin pages.
 */
@QuarkusTest
class SecurityTest {

   @Test
   void homePageIsPublic() {
      given().when().get("/pages/public/home.xhtml")
               .then().statusCode(200).body(containsString("pnlHome"));
   }

   @Test
   void adminPagesRedirectToLogin() {
      given().redirects().follow(false)
               .when().get("/pages/admin/dashboard.xhtml")
               .then().statusCode(302).header("Location", endsWith("/pages/login/login.xhtml"));
   }

   @Test
   void validLoginIssuesCredentialCookie() {
      given().redirects().follow(false)
               .formParam("j_username", "admin").formParam("j_password", "admin")
               .when().post("/j_security_check")
               .then().statusCode(302).cookie("quarkus-credential", notNullValue());
   }

   @Test
   void loggedInAdminIsVisibleToFaces() {
      final String credential = given().redirects().follow(false)
               .formParam("j_username", "admin").formParam("j_password", "admin")
               .when().post("/j_security_check")
               .then().statusCode(302).extract().cookie("quarkus-credential");

      given().cookie("quarkus-credential", credential)
               .when().get("/pages/public/home.xhtml")
               .then().statusCode(200)
               .body(containsString("Welcome: admin"))
               .body(containsString(">Admin<"));
   }

   @Test
   void invalidLoginRedirectsToErrorPage() {
      given().redirects().follow(false)
               .formParam("j_username", "admin").formParam("j_password", "wrong")
               .when().post("/j_security_check")
               .then().statusCode(302).header("Location", endsWith("/pages/login/login-error.xhtml"));
   }
}

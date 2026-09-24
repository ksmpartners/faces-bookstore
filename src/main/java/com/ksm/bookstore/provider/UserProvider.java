package com.ksm.bookstore.provider;

import java.io.Serializable;
import java.util.Map;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@SessionScoped
@Named
public class UserProvider implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = Logger.getLogger(UserProvider.class);

   /**
    * Cookie Quarkus FORM authentication uses to hold the logged in user.
    */
   private static final String CREDENTIAL_COOKIE = "quarkus-credential";

   public String logout() {
      final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
      LOG.debugf("Logging out user: %s", externalContext.getRemoteUser());
      // Quarkus FORM auth is cookie based so the cookie must be removed along with the session
      externalContext.addResponseCookie(CREDENTIAL_COOKIE, "", Map.of("maxAge", 0, "path", "/"));
      externalContext.invalidateSession();
      return "home";
   }

   public String getUserName() {
      return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
   }

   public boolean isAdminUser() {
      return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(AccessProvider.ADMIN);
   }

   public boolean isGeneralUser() {
      return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(AccessProvider.GENERAL);
   }

   public boolean isUserLoggedIn() {
      return (getUserName() != null);
   }

}

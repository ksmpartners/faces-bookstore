package com.ksm.bookstore.provider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@SessionScoped
@Named
public class UserProvider implements Serializable {
   private static final long serialVersionUID = 1L;

   public void logout() {
      FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
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

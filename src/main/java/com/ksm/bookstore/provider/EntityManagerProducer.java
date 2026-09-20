package com.ksm.bookstore.provider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {
   @PersistenceContext
   private EntityManager entityManager;

   @Produces
   @RequestScoped
   public EntityManager getEntityManager() {
      return entityManager;
   }
}

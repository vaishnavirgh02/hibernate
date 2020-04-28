package com.capg;


import javax.persistence.*;
import java.util.List;

public class ProjectMain {
    EntityManagerFactory emf;

    public static void main(String[] args) {
        ProjectMain project = new ProjectMain();
        project.execute();
    }

    void execute() {
        //entity manager factory created
        emf = Persistence.createEntityManagerFactory("author-mgt");
        Author author = createAuthor();
        int authorId = author.getAuthorId();
        Author found = findAuthorById(authorId);
        print(found);

        found.setFirstName("Amy");
        found.setMiddleName("Elliot");
        found.setLastName("Dunne");
        updateAuthor(found);

        print(found);

        List<Author>auth=findAllAuthor();

        System.out.println("Author");
        print(auth);
        emf.close();
    }

    void print(Author author){
        System.out.println("author id="+author.getAuthorId()+"First Name="+author.getFirstName()+"Middle Name="+author.getMiddleName()+"Last Name ="+author.getLastName()+"Phone No ="+author.getPhoneNo());

    }
    void print(List<Author>author){
        for (Author auth:author){
            print(auth);
        }
    }

    Author findAuthorById(int authorId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Author a = em.find(Author.class, authorId);
        transaction.commit();
        em.close();
        return a;
    }

    List<Author> findAllAuthor(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query =em.createQuery("from Author");
        List<Author> author=query.getResultList();
        return author;
    }

    Author createAuthor() {
        
        
        EntityManager em = emf.createEntityManager();
        
      
        
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Author author = new Author();
        author.setFirstName("Amy");
        author.setMiddleName("Elliot");
        author.setLastName("Dunne");
        author.setPhoneNo("69857284");
        
        
        em.persist(author);   
        
        transaction.commit();
        
        System.out.println( author.getAuthorId());
        
        em.close();
        return author;
    }
    
    Author updateAuthor(Author author){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        author=em.merge(author);
        transaction.commit();
        em.close();
        return author;
    }

   
    void removeAuthorById(int id){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Author author=em.find(Author.class,id);
        em.remove(author);
        transaction.commit();
        em.close();
        System.out.println("author removed with id="+id);
    }
}
package com.flock.springbootbackend.repository;

import com.flock.springbootbackend.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("Select c FROM Contact c WHERE (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.name ASC")
    public List<Contact> searchPrefixOrderByNameASC(@Param("prefix") String prefix);

    @Query("Select c FROM Contact c WHERE c.name LIKE :prefix% or c.email LIKE :prefix% ORDER BY c.name DESC")
    public List<Contact> searchPrefixOrderByNameDESC(@Param("prefix") String prefix);

    @Query("Select c FROM Contact c WHERE (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.score ASC")
    public List<Contact> searchPrefixOrderByScoreASC(@Param("prefix") String prefix);

    @Query("Select c FROM Contact c WHERE c.name LIKE :prefix% or c.email LIKE :prefix% ORDER BY c.score DESC")
    public List<Contact> searchPrefixOrderByScoreDESC(@Param("prefix") String prefix);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.score = c.score + 1 WHERE c.cid = :cid")
    public void updateScore(@Param("cid") int cid);


    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.name=:name, c.email=:email, c.phone=:phone, c.address=:address WHERE c.cid = :cid")
    public void updateContact(@Param("cid") int cid, @Param("name") String name, @Param("email") String email, @Param("phone") String phone, @Param("address") String address);

    @Transactional
    @Modifying
    @Query("DELETE FROM Contact c WHERE c.cid = :cid")
    public void deleteContact(@Param("cid") int cid);

    //    @Query("SELECT s FROM Contact s WHERE s.name LIKE :name%")
//    public List<Contact> startsWithName(@Param("name") String namePrefix);
//
//    @Query("SELECT s FROM Contact s WHERE s.name LIKE %:name")
//    public List<Contact> endsWithName(@Param("name") String nameSuffix);
//
//    @Query("SELECT s FROM Contact s WHERE s.name LIKE %:name%")
//    public List<Contact> containsName(@Param("name") String name);

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Contact s WHERE s.id = :id")
//    public void deleteStudentId(@Param("id") int id);
//
//    @Transactional
//    @Modifying
//    @Query("UPDATE Contact s SET s.name = :name , s.address = :address WHERE s.id = :id")
//    public void updateStudent(@Param("id") int id, @Param("name") String name, @Param("address") String address);
}

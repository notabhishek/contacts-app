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

    @Query("Select c FROM Contact c WHERE c.uid = :uid AND (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.name ASC")
    public List<Contact> searchPrefixOrderByNameASC(@Param("uid") int uid, @Param("prefix") String prefix);

    @Query("Select c FROM Contact c WHERE c.uid = :uid AND (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.name DESC")
    public List<Contact> searchPrefixOrderByNameDESC(@Param("uid") int uid, @Param("prefix") String prefix);

    @Query("Select c FROM Contact c WHERE c.uid = :uid AND (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.score ASC")
    public List<Contact> searchPrefixOrderByScoreASC(@Param("uid") int uid, @Param("prefix") String prefix);

    @Query("Select c FROM Contact c WHERE c.uid = :uid AND (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.score DESC")
    public List<Contact> searchPrefixOrderByScoreDESC(@Param("uid") int uid, @Param("prefix") String prefix);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.score = c.score + 1 WHERE c.uid = :uid AND c.cid = :cid")
    public void updateScore(@Param("uid") int uid, @Param("cid") int cid);


    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.name=:name, c.email=:email, c.phone=:phone, c.address=:address WHERE c.uid = :uid AND c.cid = :cid")
    public void updateContact(@Param("uid") int uid, @Param("cid") int cid, @Param("name") String name, @Param("email") String email, @Param("phone") String phone, @Param("address") String address);

    @Transactional
    @Modifying
    @Query("DELETE FROM Contact c WHERE c.uid = :uid AND c.cid = :cid")
    public void deleteContact(@Param("uid") int uid, @Param("cid") int cid);


//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Contact c WHERE c.uid = :uid AND c.cid in ?2")
//    public void deleteContacts(@Param("uid") int uid, List<Integer> ids);
}

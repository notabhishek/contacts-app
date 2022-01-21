package com.flock.springbootbackend.repository;

import com.flock.springbootbackend.Utils;
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

    @Query(Utils.ContactQueryConstants.SELECT_CONTACTS_NAME_ASC)
    public List<Contact> searchPrefixOrderByNameASC(@Param("uid") int uid, @Param("prefix") String prefix);

    @Query(Utils.ContactQueryConstants.SELECT_CONTACTS_NAME_DESC)
    public List<Contact> searchPrefixOrderByNameDESC(@Param("uid") int uid, @Param("prefix") String prefix);

    @Query(Utils.ContactQueryConstants.SELECT_CONTACTS_SCORE_ASC)
    public List<Contact> searchPrefixOrderByScoreASC(@Param("uid") int uid, @Param("prefix") String prefix);

    @Query(Utils.ContactQueryConstants.SELECT_CONTACTS_SCORE_DESC)
    public List<Contact> searchPrefixOrderByScoreDESC(@Param("uid") int uid, @Param("prefix") String prefix);

    @Transactional
    @Modifying
    @Query(Utils.ContactQueryConstants.UPDATE_SCORE)
    public void updateScore(@Param("uid") int uid, @Param("cid") int cid);


    @Transactional
    @Modifying
    @Query(Utils.ContactQueryConstants.UPDATE_CONTACT)
    public void updateContact(@Param("uid") int uid, @Param("cid") int cid, @Param("name") String name, @Param("email") String email, @Param("phone") String phone, @Param("address") String address);

    @Transactional
    @Modifying
    @Query(Utils.ContactQueryConstants.DELETE_CONTACT)
    public void deleteContact(@Param("uid") int uid, @Param("cid") int cid);


    @Transactional
    @Modifying
    @Query(Utils.ContactQueryConstants.DELETE_CONTACTS)
    public void deleteContacts(@Param("uid") int uid,@Param("ids") List<Integer> ids);

    @Transactional
    @Modifying
    @Query(Utils.ContactQueryConstants.UPDATE_FAV)
    public void updateFav(@Param("uid") int uid, @Param("cid") int cid, @Param("fav") boolean fav);
}

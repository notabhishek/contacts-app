package com.flock.springbootbackend.repository;

import com.flock.springbootbackend.model.DeletedContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BinRepo extends JpaRepository<DeletedContact, Integer> {
    @Query("SELECT d FROM DeletedContact d WHERE d.uid = :uid AND d.cid = :cid")
    public DeletedContact getDeletedContact(@Param("uid") int uid, @Param("cid") int cid);

    @Transactional
    @Modifying
    @Query("DELETE FROM DeletedContact d WHERE d.uid = :uid AND d.cid = :cid")
    public void deleteFromBin(@Param("uid") int uid, @Param("cid") int cid);

    @Query("SELECT d FROM DeletedContact d WHERE d.uid = :uid")
    public List<DeletedContact> getAll(@Param("uid") int uid);

    @Query("SELECT d FROM DeletedContact d WHERE d.uid = :uid AND d.cid = :cid")
    public DeletedContact get(@Param("uid") int uid, @Param("cid") int cid);
}

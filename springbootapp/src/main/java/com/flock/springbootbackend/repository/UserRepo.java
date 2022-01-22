package com.flock.springbootbackend.repository;

import com.flock.springbootbackend.utils.Constants;
import com.flock.springbootbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    public Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(Constants.INCREMENT_MAXCID)
    public void incrementMaxCid(@Param("uid") int uid);
}

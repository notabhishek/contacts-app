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

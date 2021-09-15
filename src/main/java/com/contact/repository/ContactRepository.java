package com.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}

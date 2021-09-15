package com.contact.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.contact.model.Contact;
import com.contact.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepository contactrepo;
	
	@Override
	public List<Contact> getAllContacts() {
		// TODO Auto-generated method stub
		return contactrepo.findAll();
	}

	@Override
	public void saveContact(Contact contact) {
		// TODO Auto-generated method stub
		this.contactrepo.save(contact);
		
	}

	@Override
	public Contact getContactById(long id) {
		// TODO Auto-generated method stub
		Optional<Contact> optional= contactrepo.findById(id);
		Contact contact=null;
		if(optional.isPresent()) {
			contact=optional.get();
		}else
			throw new RuntimeException("Contact Not found by id: " + id);
		return contact;
	}

	@Override
	public void deleteContactById(long id) {
		// TODO Auto-generated method stub
		this.contactrepo.deleteById(id);
	}

	@Override
	public Page<Contact> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		// TODO Auto-generated method stub
		Sort sort=sortDirection.equals(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		Pageable pageable=PageRequest.of(pageNo - 1, pageSize, sort);
		return this.contactrepo.findAll(pageable);
	}

}

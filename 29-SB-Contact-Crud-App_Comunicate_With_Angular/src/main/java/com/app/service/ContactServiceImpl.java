package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Contact;
import com.app.repo.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public void saveContact(Contact contact) {

		Contact save = contactRepository.save(contact);
	}

	@Override
	public Contact getContactById(Integer id) {

		Contact contact =new Contact();
		Optional<Contact> optional = contactRepository.findById(id);
		if(optional.isPresent())
		{
			contact = optional.get();
		}
		return contact;
	}

	@Override
	public String updateContact(Contact contact) {

		Contact save = contactRepository.save(contact);
		
		return "Updated Successfully";
	}

	@Override
	public List<Contact> getAllContacts() {

		return contactRepository.findAll();
	}

	@Override
	public String deleteContactById(Integer id) {

		contactRepository.deleteById(id);
		return "Deleted Successfully";
	}

}

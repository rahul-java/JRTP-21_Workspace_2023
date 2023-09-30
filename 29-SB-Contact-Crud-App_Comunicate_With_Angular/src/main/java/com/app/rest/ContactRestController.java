package com.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Contact;
import com.app.service.ContactService;

@RestController
@CrossOrigin
public class ContactRestController {

	@Autowired
	private ContactService service;
	
	@PostMapping("/contact")
	public String createContact(@RequestBody Contact contact)
	{
		service.saveContact(contact);
		return "Saved Successfully";
	}
	
	@GetMapping("/contact/{id}")
	public Contact getContactById(@PathVariable Integer id)
	{
		Contact contactById = service.getContactById(id);
		return contactById;
	}
	
	@DeleteMapping("/contact/{contactId}")
	public String deleteContact(@PathVariable Integer contactId)
	{
		return service.deleteContactById(contactId);
	}
	
	@PutMapping("/contact")
	public String updateContact(@RequestBody Contact contact)
	{
		return service.updateContact(contact);
	}
	 
	@GetMapping("/contacts")
	public List<Contact> getAllContacts()
	{
		return service.getAllContacts();
	}
}

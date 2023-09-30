package com.app.service;

import java.util.List;

import com.app.entity.Contact;

public interface ContactService {

	public void saveContact(Contact contact);
	public Contact getContactById(Integer id);
	public String updateContact(Contact contact);
	public List<Contact> getAllContacts();
	public String deleteContactById(Integer id);
}

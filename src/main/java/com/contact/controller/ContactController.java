package com.contact.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.model.Contact;
import com.contact.service.ContactService;

@Controller
public class ContactController {
	
	@Autowired
	public ContactService contactservice;
	
//	Display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
//		model.addAttribute("listContact",contactservice.getAllContacts());
//		return "index";
		
		return findPaginated(1,"firstname", "asc", model);
	}
	
	@GetMapping("/newContactForm")
	public String showNewForm(Model model) {
//		Create model attribute to bind the form data
		Contact contact=new Contact();
		model.addAttribute("contact", contact);
		return "new_contact";
	}
	
	@PostMapping("/saveContact")
	public String saveContact(@ModelAttribute("contact") Contact contact) {
//		save contact to database
		contactservice.saveContact(contact);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable (value= "id") long id, Model model) {
//		get contact from the service
		Contact contact=contactservice.getContactById(id);
		
//		set contact as a model attribute to pre-populate the form
		model.addAttribute("contact", contact);
		return "update_contact";
	}
	
	@GetMapping("/deleteContact/{id}")
	public String deleteContact(@PathVariable (value = "id") long id) {
		
//		Call delete contact method
		this.contactservice.deleteContactById(id);	
		return "redirect:/";	
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value= "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize= 6;
		
		Page<Contact> page=contactservice.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Contact> listContact=page.getContent(); 
		
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listContact", listContact);
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		return "index";
	}
	
}

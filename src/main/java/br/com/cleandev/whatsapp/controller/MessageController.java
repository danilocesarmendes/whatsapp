package br.com.cleandev.whatsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cleandev.whatsapp.domain.Message;
import br.com.cleandev.whatsapp.service.MessageService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/api")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@PostMapping("/message")
	public ResponseEntity<Void> createMessageOfWhatsAppInQueeue(@RequestBody Message message) {

		try {
			messageService.insert(message);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

package br.com.cleandev.whatsapp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cleandev.whatsapp.domain.Message;
import br.com.cleandev.whatsapp.repository.MessageRepository;

@Service
public class MessageService {

	

	private final MessageRepository messageRepository;

	@Autowired
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public void insert(Message entity) {
		entity.setCreatedAt(new Date());
		messageRepository.save(entity);
	}
	
}

package br.com.cleandev.whatsapp;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import br.com.cleandev.whatsapp.service.WhatsAppEngine;

public class SendImageWhatsAppTest {

	@Test
	public void sendMessage() throws Exception {
		
		ClassPathResource file = new ClassPathResource("static/images/images1.jpeg");
		
		WhatsAppEngine whatsApp = new WhatsAppEngine();
		whatsApp.sendFileToNewNumber("16991001800", file.getFile().getAbsolutePath());
		
	}
}

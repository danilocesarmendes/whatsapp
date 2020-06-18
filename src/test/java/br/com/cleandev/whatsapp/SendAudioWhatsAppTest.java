package br.com.cleandev.whatsapp;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import br.com.cleandev.whatsapp.service.WhatsAppEngine;

public class SendAudioWhatsAppTest {

	@Test
	public void sendMessage() throws Exception {
		
		ClassPathResource file = new ClassPathResource("static/media/audio_teste.ogg");
		
		WhatsAppEngine whatsApp = new WhatsAppEngine();
		whatsApp.sendFileToNewNumber("16991001800", file.getFile().getAbsolutePath());
		
	}
}

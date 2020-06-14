package br.com.cleandev.whatsapp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cleandev.whatsapp.domain.Message;
import br.com.cleandev.whatsapp.repository.MessageRepository;

/**
 * 
 * @author Danilo Mendes
 * @version 1.0, 14/06/2020
 * @since 1.0
 */
@Service
public class SendWhatsAppService {

	private final MessageRepository messageRepository;

	@Autowired
	public SendWhatsAppService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public void sendMessageWhatsApp() throws NoAlertPresentException, NoSuchElementException, Exception {

		Optional<List<Message>> list = messageRepository.findByShippingDateIsNullOrderByIdAsc();

		if (list.isPresent()) {
			for (Message message : list.get()) {
				try {
					WhatsAppEngine whatsApp = new WhatsAppEngine();

					whatsApp.sendToNewNumber(message.getPhone().replaceAll("\\D", "").trim(),
							message.getText());

					message.setShippingDate(new Date());
					messageRepository.saveAndFlush(message);
//					whatsApp.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
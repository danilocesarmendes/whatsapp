package br.com.cleandev.whatsapp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.cleandev.whatsapp.service.SendWhatsAppService;

@Component
public class Schedule {

	private SendWhatsAppService sendWhatsAppService;

	@Autowired
	public Schedule(SendWhatsAppService sendWhatsAppService) {
		this.sendWhatsAppService = sendWhatsAppService;
	}

	@Scheduled(cron = "0/10 * * * * *")
	protected void init() throws Exception {

		try {
			sendWhatsAppService.sendMessageWhatsApp();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
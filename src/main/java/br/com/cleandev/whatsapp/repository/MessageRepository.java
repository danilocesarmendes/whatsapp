package br.com.cleandev.whatsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cleandev.whatsapp.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}

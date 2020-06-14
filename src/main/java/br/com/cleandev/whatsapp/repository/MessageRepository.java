package br.com.cleandev.whatsapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cleandev.whatsapp.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	Optional<List<Message>> findByShippingDateIsNullOrderByIdAsc();	

}

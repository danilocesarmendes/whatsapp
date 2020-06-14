package br.com.cleandev.whatsapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@ToString()
@Builder
@Table
@Entity(name = "message")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O campo text para envio de mensagem não pode ser null")
	@NotBlank(message = "O campo text para envio de mensagem não pode ser vazio")
	private String text;

	@NotNull(message = "O campo phone não pode ser null")
	@NotBlank(message = "O campo phone não pode ser vazio")
	private String phone;

	@NotNull
	private Date createdAt;
	
	private Date shippingDate;

}

package fi.ishtech.happeo.codingexercise.entity;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_organisation")
@DynamicInsert
@DynamicUpdate
@Data
public class Organisation implements Serializable {

	private static final long serialVersionUID = 4210802098680226564L;

	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false, unique = true)
	private String name;

}

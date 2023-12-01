package fi.ishtech.happeo.codingexercise.entity;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "t_user", uniqueConstraints = @UniqueConstraint(name = "uk_user_org_external_id", columnNames = {
		"organisation_id", "externalId" }))
@DynamicInsert
@DynamicUpdate
@Data
public class User implements Serializable {

	private static final long serialVersionUID = -5046540120527349896L;

	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false, unique = true)
	private String email;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	@Column(nullable = true, updatable = false)
	private String passwordHash;

	@EqualsAndHashCode.Exclude
	@Column(nullable = false)
	private String firstName;

	@EqualsAndHashCode.Exclude
	@Column(nullable = false)
	private String lastName;

	@Column(name = "organisation_id", nullable = false, updatable = false)
	private Long organisationId;

	@Column(nullable = true, updatable = false)
	private String externalId;

	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(optional = false)
	@JoinColumn(name = "organisation_id", nullable = true, insertable = false, updatable = false)
	private Organisation organisation;

}

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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "t_org_provisioner")
@DynamicInsert
@DynamicUpdate
@Data
public class OrgProvisioner implements Serializable {

	private static final long serialVersionUID = -6936548493046088442L;

	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "organisation_id", nullable = false, updatable = false)
	private Long organisationId;

	@Column(name = "provisioner_id", nullable = false, updatable = false)
	private Long provisionerId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	@Column(nullable = true, updatable = false)
	private String secret;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(optional = false)
	@JoinColumn(name = "organisation_id", nullable = true, insertable = false, updatable = false)
	private Organisation organisation;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(optional = false)
	@JoinColumn(name = "provisioner_id", nullable = true, insertable = false, updatable = false)
	private Provisioner provisioner;

}

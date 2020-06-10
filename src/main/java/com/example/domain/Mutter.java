package com.example.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Mutter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mutter implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", length=8)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name="TIMESTAMP")
	private Date timestamp;
			
	@Column(nullable = false, name = "TEXT", length = 120)
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "USER_NAME", referencedColumnName = "NAME")
	private User user;

}

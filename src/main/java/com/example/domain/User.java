package com.example.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "mutters")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Integer Id;
	
	@Column(unique = true, nullable = false, name="NAME", length=64)
	private String name;
	
	@Column(nullable = false, name="PASS", length=64)
	@JsonIgnore
	private String pass;
	
	@Column(nullable = false, name="ENABLE")
	private boolean enable;//有効なアカウントかどうか

	@Column(nullable = false, name="ADMIN")
	private boolean admin;//管理者かどうか
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Mutter> mutters;

}

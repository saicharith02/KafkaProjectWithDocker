package com.example.consumer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Info {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String word;
	private String entity;
	private int frequency;
	
	public Info(Long id, String word, String entity, int frequency) {
		super();
		this.id = id;
		this.word = word;
		this.entity = entity;
		this.frequency = frequency;
	}
	public Info() {
	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	@Override
	public String toString() {
		return "Info [id=" + id + ", word=" + word + ", entity=" + entity + ", frequency=" + frequency + "]";
	}
	
}

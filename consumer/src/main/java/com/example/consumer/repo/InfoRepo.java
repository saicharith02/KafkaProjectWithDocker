package com.example.consumer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.consumer.entity.Info;

public interface InfoRepo extends JpaRepository<Info,Long>{

	 public Info findByWordAndEntity(String word, String entity);

}

package com.example.consumer.service;

//import org.apache.logging.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.consumer.entity.Info;
import com.example.consumer.repo.InfoRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InfoGetter {
	private static final Logger logger = LoggerFactory.getLogger(InfoGetter.class);
	@Autowired
	InfoRepo inforepo;
	ObjectMapper objmapp=new ObjectMapper();
	@KafkaListener(topics="wordcollector1",groupId="my-group")
	public void consume(String message) {
		try {
            // Parse the message as a JSON array directly
//            System.out.println(message);
			
            JsonNode jsonNode = objmapp.readTree(message);

            if (jsonNode.get("entities").isArray()) {
                for (JsonNode entityNode : jsonNode.get("entities")) {
                    if (entityNode.isArray() && entityNode.size() >= 2) {
                        String word = entityNode.get(0).asText();
                        String entity = entityNode.get(1).asText();
                        
                        Info wordFrequency = inforepo.findByWordAndEntity(word, entity);
                        if (wordFrequency == null) {
                            wordFrequency = new Info();
                            wordFrequency.setWord(word);
                            wordFrequency.setEntity(entity);
                            wordFrequency.setFrequency(1);
                        } else {
                            wordFrequency.setFrequency(wordFrequency.getFrequency() + 1);
                        }
                     inforepo.save(wordFrequency);
                    } else {
                        logger.warn("Unexpected entity format: {}", entityNode);
                    }
                }
            } else {
                logger.error("Unexpected JSON format: {}", jsonNode);
            }
        }
		catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
        }
	}
	
}

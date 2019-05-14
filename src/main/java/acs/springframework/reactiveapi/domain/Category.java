package acs.springframework.reactiveapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Category {
	
	@Id
	private String id;
	
	private String description;
	
}

package com.model;

import java.io.Serializable;
import java.util.List;

public class ConceptsSearch implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private String concept;
	
	private List<SearchObj> searchList;

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public List<SearchObj> getSearchList() {
		return searchList;
	}

	public void setSearchList(List<SearchObj> searchList) {
		this.searchList = searchList;
	}

}

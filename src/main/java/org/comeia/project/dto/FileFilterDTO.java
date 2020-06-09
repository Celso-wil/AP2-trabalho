package org.comeia.project.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.comeia.project.search.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper=false)
public @Data class FileFilterDTO implements Serializable {

	private String Name;
	private String type;
	private String conteudo;
	
	public static List<SearchCriteria> buildCriteria(FileFilterDTO filter) {
		List<SearchCriteria> criterias = new ArrayList<>();
		
		if(filter.getName() != null && !filter.getName().isEmpty()) { 
			criterias.add(new SearchCriteria("Name", "%".concat(filter.getName()).concat("%")));
		}
		
		if(filter.getType() != null && !filter.getType().isEmpty()) { 
			criterias.add(new SearchCriteria("type", filter.getType()));
		}
		if(filter.getConteudo() != null && !filter.getConteudo().isEmpty()) { 
			criterias.add(new SearchCriteria("Conteudo", "%".concat(filter.getConteudo()).concat("%")));
		}
		return criterias;
	}
}

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
public @Data class ServerFilterDTO implements Serializable {

	private String name;
	private String type;
	private String port;
	private String login;
	private String senha;
	
	public static List<SearchCriteria> buildCriteria(ServerFilterDTO filter) {
		List<SearchCriteria> criterias = new ArrayList<>();
		
		if(filter.getName() != null && !filter.getName().isEmpty()) { 
			criterias.add(new SearchCriteria("name", "%".concat(filter.getName()).concat("%")));
		}
		
		if(filter.getType() != null && !filter.getType().isEmpty()) { 
			criterias.add(new SearchCriteria("type", filter.getType()));
		}
		
		if(filter.getPort() != null && !filter.getPort().isEmpty()) { 
			criterias.add(new SearchCriteria("port", "%".concat(filter.getPort()).concat("%")));
		}
		
		if(filter.getLogin() != null && !filter.getLogin().isEmpty()) { 
			criterias.add(new SearchCriteria("login", "%".concat(filter.getLogin()).concat("%")));
		}
		
		if(filter.getSenha() != null && !filter.getSenha().isEmpty()) { 
			criterias.add(new SearchCriteria("senha", "%".concat(filter.getSenha()).concat("%")));
		}
		
		return criterias;
	}
}

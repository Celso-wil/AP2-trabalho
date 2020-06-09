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
public @Data class LoginFilterDTO implements Serializable {

	private String fullName;
	private String senha;
	
	public static List<SearchCriteria> buildCriteria(LoginFilterDTO filter) {
		List<SearchCriteria> criterias = new ArrayList<>();
		
		if(filter.getFullName() != null && !filter.getFullName().isEmpty()) { 
			criterias.add(new SearchCriteria("fullName", "%".concat(filter.getFullName()).concat("%")));
		}
		if(filter.getSenha() != null && !filter.getSenha().isEmpty()) { 
			criterias.add(new SearchCriteria("senha", "%".concat(filter.getSenha()).concat("%")));
		}
		
		return criterias;
	}
}

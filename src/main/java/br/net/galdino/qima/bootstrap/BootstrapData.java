package br.net.galdino.qima.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.net.galdino.qima.domain.Category;
import br.net.galdino.qima.repository.CategoryRepository;

@Component
public class BootstrapData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    
	public BootstrapData(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		Category category = new Category();
		category.setName("Category 1");
		category.setDescription("Description Category 1");
		categoryRepository.save(category);
		
		Category category2 = new Category();
		category2.setName("Category 2");
		category2.setDescription("Description Category 2");
		categoryRepository.save(category2);
		
	}

}

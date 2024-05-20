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
		
		Category category1 = new Category();
		category1.setName("Category 1");
		category1.setDescription("Description Category 1");
		categoryRepository.save(category1);
		
		Category category2 = new Category();
		category2.setName("Category 2");
		category2.setDescription("Description Category 2");
		categoryRepository.save(category2);
		
		Category category3 = new Category();
		category3.setName("Category 3");
		category3.setDescription("Description Category 3");
		categoryRepository.save(category3);
		
		Category category4 = new Category();
		category4.setName("Category 4");
		category4.setDescription("Description Category 4");
		categoryRepository.save(category4);
		
		Category category5 = new Category();
		category5.setName("Category 5");
		category5.setDescription("Description Category 5");
		categoryRepository.save(category5);
		
	}

}

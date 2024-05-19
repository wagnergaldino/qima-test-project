package br.net.galdino.qima.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.net.galdino.qima.domain.Category;
import br.net.galdino.qima.domain.Product;
import br.net.galdino.qima.repository.CategoryRepository;
import br.net.galdino.qima.repository.ProductRepository;
import jakarta.validation.Valid;


@Controller
public class ProductController {
	
	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	
	public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}
	
	@GetMapping("/products")
	public String getAll(Model model, @RequestParam(required = false) String keyword,
									  @RequestParam(defaultValue = "1") int page,
									  @RequestParam(defaultValue = "3") int size,
									  @RequestParam(defaultValue = "id,asc") String[] sort) {
	    try {
		    List<Product> products = new ArrayList<Product>();
		      
		    String sortField = sort[0];
		    String sortDirection = sort[1];
		      
		    Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		    Order order = new Order(direction, sortField);
		      
		    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
	
		    Page<Product> pageProds;
		    if (keyword == null) {
		        pageProds = productRepository.findAll(pageable);
		    } else {
		        pageProds = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
		        model.addAttribute("keyword", keyword);
		    }
	
 	        products = pageProds.getContent();
		      
		    model.addAttribute("products", products);
		    model.addAttribute("currentPage", pageProds.getNumber() + 1);
		    model.addAttribute("totalItems", pageProds.getTotalElements());
		    model.addAttribute("totalPages", pageProds.getTotalPages());
		    model.addAttribute("pageSize", size);
		    model.addAttribute("sortField", sortField);
		    model.addAttribute("sortDirection", sortDirection);
		    model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		      
	    } catch (Exception e) {
	        model.addAttribute("message", e.getMessage());
	    }

	    return "products";
    }
	
	@GetMapping("/products/new")
	public String addProduct(Model model) {
		Product product = new Product();
		Category category = new Category();
		category.setId(0L);
		product.setCategory(category);
		product.setAvailable(true);
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);
		model.addAttribute("pageTitle", "Create new Product");
		
		return "new_product_form";

	}

	@PostMapping("/products/save")
	public String saveProduct(@Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
	    try {
	    	
	    	if(product.getName() == null || product.getName().isEmpty()
	    			|| product.getDescription() == null || product.getDescription().isEmpty()
	    			|| product.getCategory() == null || product.getPrice() == null
	    			|| product.getAvailable() == null) {
	    		if(product.getId() != null && product.getId() > 0) {
			        return "edit_product_form";
		        } else {
			        return "new_product_form";
		        }
	    	}
	    	
	    	if(bindingResult.hasErrors()) {
	    		if(product.getId() != null && product.getId() > 0) {
			        return "edit_product_form";
		        } else {
			        return "new_product_form";
		        }
	    	}
	    	
	    	productRepository.save(product);
	        redirectAttributes.addFlashAttribute("message", "The Product has been saved successfully!");
	    } catch (Exception e) {
	        redirectAttributes.addAttribute("message", e.getMessage());
	        if(product.getId() != null && product.getId() > 0) {
		        return "edit_product_form";
	        } else {
		        return "new_product_form";
	        }
	    }
	    return "redirect:/products";
	}

	@GetMapping("/products/{id}")
	public String editProduct(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
	    try {
	    	Product product = productRepository.findById(id).get();	    	

	    	List<Category> categories = categoryRepository.findAll();
			model.addAttribute("categories", categories);
	        model.addAttribute("product", product);
	        model.addAttribute("selectedCategory", product.getCategory().getId());
	        model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");
	    } catch (Exception e) {
	    	redirectAttributes.addFlashAttribute("message", e.getMessage());
	    }	
	    return "edit_product_form";
	    //return "redirect:/products";	    
	}

	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
	    try {
	        productRepository.deleteById(id);
	        redirectAttributes.addFlashAttribute("message", "The Product with id=" + id + " has been deleted successfully!");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("message", e.getMessage());
	    }
	    return "redirect:/products";
	}

}

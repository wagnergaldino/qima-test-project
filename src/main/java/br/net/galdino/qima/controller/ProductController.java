package br.net.galdino.qima.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.net.galdino.qima.domain.Product;
import br.net.galdino.qima.repository.ProductRepository;


@Controller
public class ProductController {
	
	private ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@GetMapping("/products")
	public String getAll(Model model, @RequestParam(required = false) String keyword,
									  @RequestParam(defaultValue = "1") int page,
									  @RequestParam(defaultValue = "6") int size,
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
		product.setAvailable(true);

	    model.addAttribute("product", product);
	    model.addAttribute("pageTitle", "Create new Product");

	    return "product_form";
	}

	@PostMapping("/products/save")
	public String saveProduct(Product product, RedirectAttributes redirectAttributes) {
	    try {
	    	productRepository.save(product);
	        redirectAttributes.addFlashAttribute("message", "The Product has been saved successfully!");
	    } catch (Exception e) {
	        redirectAttributes.addAttribute("message", e.getMessage());
	    }
	    return "redirect:/products";
	}

	@GetMapping("/products/{id}")
	public String editProduct(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
	    try {
	    	Product product = productRepository.findById(id).get();

	        model.addAttribute("product", product);
	        model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");

	        return "product_form";
	    } catch (Exception e) {
	    	redirectAttributes.addFlashAttribute("message", e.getMessage());
	    }	    
	    return "redirect:/products";	    
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
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

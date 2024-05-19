package br.net.galdino.qima.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.net.galdino.qima.domain.Product;
import br.net.galdino.qima.repository.ProductRepository;

public class ProductControllerTest {
	
	@Mock
    private ProductRepository productRepository;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll_NoKeyword() {
        int page = 1;
        int size = 3;
        String[] sort = {"id", "asc"};

        List<Product> products = new ArrayList<>();
        products.add(new Product());
        Page<Product> pageProds = new PageImpl<>(products);

        ExampleMatcher matcher = ExampleMatcher.matchingAny();
        Example<Product> example = Example.of(new Product(), matcher);

        when(productRepository.findAll(example, PageRequest.of(page, size, Sort.by(sort[0]).descending()))).thenReturn(pageProds);

        String result = productController.getAll(model, null, page, size, sort);

        Assertions.assertEquals("products", result);

    }

    @Test
    public void testGetAll_WithKeyword() {

        String keyword = "test";
        int page = 1;
        int size = 3;
        String[] sort = {"id", "asc"};

        List<Product> products = new ArrayList<>();
        products.add(new Product());
        Page<Product> pageProds = new PageImpl<>(products);

        Sort.Order order = new Sort.Order(Direction.fromString(sort[1]), sort[0]);
        Sort sortObj = Sort.by(order);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        when(productRepository.findByNameContainingIgnoreCase(keyword, pageable)).thenReturn(pageProds);

        String result = productController.getAll(model, keyword, page, size, sort);

        verify(model).addAttribute("keyword", keyword);
        Assertions.assertEquals("products", result);

    }

    @Test
    public void testSaveProduct_ValidProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        String result = productController.saveProduct(product, mock(BindingResult.class), redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("message", "The Product has been saved successfully!");
        Assertions.assertEquals("redirect:/products", result);
    }
  
	@Test
	public void testSaveProduct_InvalidProduct() {
		Product product = new Product();
		when(productRepository.save(product)).thenReturn(product);

		String result = productController.saveProduct(product, mock(BindingResult.class), redirectAttributes);
		Assertions.assertEquals("redirect:/products", result);
	}

    @Test
    public void testDeleteProduct_ValidId() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        String result = productController.deleteProduct(id, model, redirectAttributes);

        verify(productRepository).deleteById(id);
        Assertions.assertEquals("redirect:/products", result);
    }

    @Test
    public void testDeleteProduct_InvalidId() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        String result = productController.deleteProduct(id, model, redirectAttributes);

        Assertions.assertEquals("redirect:/products", result);
    }
    
}

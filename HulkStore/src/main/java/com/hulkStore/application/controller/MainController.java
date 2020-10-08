package com.hulkStore.application.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hulkStore.application.Exception.CustomeFieldValidationException;
import com.hulkStore.application.Exception.ProductOrIdNotFound;
import com.hulkStore.application.Exception.UsernameOrIdNotFound;
import com.hulkStore.application.dto.ChangePasswordForm;
import com.hulkStore.application.entity.Product;
import com.hulkStore.application.entity.User;
import com.hulkStore.application.repository.RoleRepository;
import com.hulkStore.application.service.ProductService;
import com.hulkStore.application.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	RoleRepository roleRepository;

	@GetMapping({"/","/login"})
	public String index() {
		return "index";
	}
	
	
	@GetMapping("/products")
	public String productForm(Model model) {
		model.addAttribute("productForm", new Product());
		model.addAttribute("productList", productService.getAllProducts());
		model.addAttribute("listTab","active");
		return "products/product-view";
	}
	
	@PostMapping("/productForm")
	public String createProduct(@Valid @ModelAttribute("productForm")Product product, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("productForm", product);
			model.addAttribute("formTab","active");
		}else {
			try {
				productService.createProduct(product);
				model.addAttribute("productForm", new Product());
				model.addAttribute("listTab","active");
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				model.addAttribute("productForm", product);
				model.addAttribute("formTab","active");
				model.addAttribute("productList", productService.getAllProducts());
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("productForm", product);
				model.addAttribute("formTab","active");
				model.addAttribute("productList", productService.getAllProducts());
			}
		}
		model.addAttribute("productList", productService.getAllProducts());
		return "products/product-view";
	}
	
	
	@GetMapping("/editProduct/{id}")
	public String getEditProductForm(Model model, @PathVariable(name = "id")Long id) throws Exception {
		Product productToEdit = productService.getProductById(id);
		model.addAttribute("productForm", productToEdit);
		model.addAttribute("formTab","active");
		model.addAttribute("productList", productService.getAllProducts());
		model.addAttribute("editMode","true");
		return "products/product-view";
	}
	
	
	@PostMapping("/editProduct")
	public String postEditProductForm(@Valid @ModelAttribute("productForm")Product product, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("productForm", product);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				productService.updateProduct(product);
				model.addAttribute("productForm", new Product());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("productForm", product);
				model.addAttribute("formTab","active");
				model.addAttribute("productList", productService.getAllProducts());
				model.addAttribute("editMode","true");
			}
		}
		model.addAttribute("productList", productService.getAllProducts());
		return "products/product-view";
	}
	
	
	@GetMapping("/productForm/cancel")
	public String cancelEditProduct(ModelMap model) {
		return "redirect:/products";
	}
	
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(Model model, @PathVariable(name="id") Long id) {
		try {
			productService.deleteProduct(id);
		} catch (ProductOrIdNotFound poin) {
			model.addAttribute("listErrorMessage",poin.getMessage());
		}
		return productForm(model);
	}
	
	
	@GetMapping("/userForm")
	public String userForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("listTab","active");
		return "user-form/user-view";
	}
	
	
	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
		}else {
			try {
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles", roleRepository.findAll());
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles", roleRepository.findAll());
			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "user-form/user-view";
	}
	
	
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name = "id")Long id) throws Exception {
		User userToEdit = userService.getUserById(id);
		model.addAttribute("userForm", userToEdit);
		model.addAttribute("formTab","active");
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("editMode","true");
		model.addAttribute("passwordForm",new ChangePasswordForm(id));
		return "user-form/user-view";
	}
	
	
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		}else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("editMode","true");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "user-form/user-view";
	}
	
	
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
	
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name="id") Long id) {
		try {
			userService.deleteUser(id);
		} catch (UsernameOrIdNotFound uoin) {
			model.addAttribute("listErrorMessage",uoin.getMessage());
		}
		return userForm(model);
	}
	
	
	@PostMapping("/editUser/changePassword")
	public ResponseEntity<String> postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
			userService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("success");
	}
} 

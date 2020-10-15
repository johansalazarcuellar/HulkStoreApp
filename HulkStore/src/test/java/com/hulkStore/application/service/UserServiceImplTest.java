package com.hulkStore.application.service;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulkStore.application.Exception.UsernameOrIdNotFound;
import com.hulkStore.application.entity.User;
import com.hulkStore.application.dto.ChangePasswordForm;
import com.hulkStore.application.repository.UserRepository;

@WithMockUser(username = "admin", password = "$2a$04$n6WIRDQlIByVFi.5rtQwEOTAzpzLPzIIG/O6quaxRKY2LlIHG8uty", roles = "ADMIN")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@MockBean
	UserRepository userRepository;

	@Test
	public void testGetAllUsers() {
		assertNotNull(userServiceImpl.getAllUsers());
	}

	@Test
	public void testCreateUser() throws Exception {
		
		User user = new User();
		user.setId((long) 1);
		user.setFirstName("test");
		user.setLastName("testero");
		user.setEmail("test@testero.com");
		user.setRoles(null);
		user.setPassword("000");
		user.setConfirmPassword("000");
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userServiceImpl.createUser(user));
		
	}

	@Test
	public void testGetUserById() throws UsernameOrIdNotFound {
		
		Long idUser = (long) 1;
		
		User user = new User();
		user.setId((long) 1);
		user.setFirstName("test");
		user.setLastName("testero");
		user.setEmail("test@testero.com");
		user.setRoles(null);
		user.setPassword("000");
		user.setConfirmPassword("000");
		
		Optional<User> optionalUser = Optional.of(user);
		when(userRepository.findById(idUser)).thenReturn(optionalUser);
		assertNotNull(userServiceImpl.getUserById(idUser));
	}

	@Test
	public void testUpdateUser() throws Exception {
		Long idUser = (long) 1;
		
		User user = new User();
		user.setId((long) 1);
		user.setFirstName("test");
		user.setLastName("testero");
		user.setEmail("test@testero.com");
		user.setRoles(null);
		user.setPassword("000");
		user.setConfirmPassword("000");
		
		Optional<User> optionalUser = Optional.of(user);
		when(userRepository.findById(idUser)).thenReturn(optionalUser);
		when(userRepository.save(user)).thenReturn(user);
		assertNotNull(userServiceImpl.updateUser(user));
	}

	@Test
	public void testMapUser() {
		User fromUser = new User();
		fromUser.setId((long) 1);
		fromUser.setFirstName("test");
		fromUser.setLastName("testero");
		fromUser.setEmail("test@testero.com");
		fromUser.setRoles(null);
		fromUser.setPassword("000");
		fromUser.setConfirmPassword("000");
		
		User toUser = new User();
		toUser.setId((long) 1);
		toUser.setFirstName("name");
		toUser.setLastName("lastname");
		toUser.setEmail("name@lastname.com");
		toUser.setRoles(null);
		toUser.setPassword("111");
		toUser.setConfirmPassword("111");
		
		userServiceImpl.mapUser(fromUser, toUser);
		assumeTrue(true);
	}

	@Test
	public void testDeleteUser() throws UsernameOrIdNotFound {
		Long idUser = (long) 1;
		
		User user = new User();
		user.setId((long) 1);
		user.setFirstName("test");
		user.setLastName("testero");
		user.setEmail("test@testero.com");
		user.setRoles(null);
		user.setPassword("000");
		user.setConfirmPassword("000");
		
		Optional<User> optionalUser = Optional.of(user);
		when(userRepository.findById(idUser)).thenReturn(optionalUser);
		userServiceImpl.deleteUser(idUser);
		verify(userRepository, times(1)).delete(user);
	}

	@Test
	public void testIsLoggedUserADMIN() {
		assertTrue(userServiceImpl.isLoggedUserADMIN());
	}

	@Test
	public void testLoggedUserHasRole() {
		assertTrue(userServiceImpl.loggedUserHasRole("ROLE_ADMIN"));
	}

	@Test
	public void testGetLoggedUser() throws Exception {
		String username = "admin";
		
		User user = new User();
		user.setId((long) 1);
		user.setFirstName("test");
		user.setLastName("testero");
		user.setEmail("test@testero.com");
		user.setRoles(null);
		user.setPassword("000");
		user.setConfirmPassword("000");
		
		Optional<User> optionalUser = Optional.of(user);
		when(userRepository.findByUsername(username)).thenReturn(optionalUser);
		assertEquals(user, userServiceImpl.getLoggedUser());
	}

	@Test
	public void testChangePassword() throws Exception {
		Long idUser = (long) 1;
		
		User user = new User();
		user.setId((long) 1);
		user.setFirstName("test");
		user.setLastName("testero");
		user.setEmail("test@testero.com");
		user.setRoles(null);
		user.setPassword("000");
		user.setConfirmPassword("000");
		
		ChangePasswordForm form = new ChangePasswordForm();
		form.setId((long) 1);
		form.setCurrentPassword("000");
		form.setNewPassword("123");
		form.setConfirmPassword("123");
		
		Optional<User> optionalUser = Optional.of(user);
		when(userRepository.findById(idUser)).thenReturn(optionalUser);
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userServiceImpl.changePassword(form));
	}

}

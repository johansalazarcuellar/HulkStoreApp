package com.hulkStore.application.service;



import com.hulkStore.application.Exception.UsernameOrIdNotFound;
import com.hulkStore.application.dto.ChangePasswordForm;
import com.hulkStore.application.entity.User;

public interface UserService {
	
	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;
	
	public User getUserById(Long id) throws UsernameOrIdNotFound;
	
	public User updateUser(User user) throws Exception;
	
	public void deleteUser(Long id) throws UsernameOrIdNotFound;
	
	public User changePassword(ChangePasswordForm form) throws Exception;
}

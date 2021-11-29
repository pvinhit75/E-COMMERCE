package com.unitech.ShoppingCart.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.test.annotation.Rollback;

import com.unitech.commom.entities.Role;
import com.unitech.commom.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userVinhPham = new User("vinhpham@gmail.com","vinhld123", "Vinh", "Pham");
		userVinhPham.addRole(roleAdmin);
		
		User saveUser = repo.save(userVinhPham);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userNamPhuong = new User("namphuong@gmail.com", "phuongld123", "Nam", "Phuong");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userNamPhuong.addRole(roleEditor);
		userNamPhuong.addRole(roleAssistant);
		
		User saveUser = repo.save(userNamPhuong);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetuserById() {
		User userVinhPham = repo.findById(1).get();
		System.out.println(userVinhPham);
		assertThat(userVinhPham).isNotNull();
	}
	
	//Doi gmail va bat Enable
	@Test
	public void testUpdateUserDetails() {
		User userNamPhuong = repo.findById(2).get();
		userNamPhuong.setEnabled(true);
		userNamPhuong.setEmail("namphuongg@gmail.com");
		
		repo.save(userNamPhuong);
	}
	
	//update user role
	@Test
	public void testUpdateUserRoles() {
		User userNamPhuong = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		userNamPhuong.getRoles().remove(roleEditor);
		userNamPhuong.addRole(roleSalesperson);
		
		repo.save(userNamPhuong);
	}
	
	//xoa nguoi dung hien tai
	
	@Test
	public void testDeleteUsers() {
		Integer userId = 2;
		repo.deleteById(userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "namhpuong@gmail.com";
		User userByEmail = repo.getUserByEmail(email);
		
		assertThat(userByEmail).isNotNull();
	}
	
	//xoa nguoi dung hien tai (2)
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
		
	}
	
	@Test
	public void testEnableUser() {
		Integer id =1;
		repo.updateEnabledStatus(id, true);
		
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(null, pageable);

		List<User> listUsers = page.getContent();

		listUsers.forEach(user -> System.out.println(user));

		assertThat(listUsers.size()).isEqualTo(pageSize);
	}

}

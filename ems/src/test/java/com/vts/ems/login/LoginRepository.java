package com.vts.ems.login;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoginRepository extends JpaRepository<Login, Long> {
	Login findByUsername(String Username);
}

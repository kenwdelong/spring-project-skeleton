package com.kendelong.service.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kendelong.domain.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>
{
	User findByEmail(String email);
}

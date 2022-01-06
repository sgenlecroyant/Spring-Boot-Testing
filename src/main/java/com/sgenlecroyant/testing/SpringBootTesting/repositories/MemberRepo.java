package com.sgenlecroyant.testing.SpringBootTesting.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgenlecroyant.testing.SpringBootTesting.models.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer>{
	
	public Optional<Member> findMemberByUsername(String username);

}

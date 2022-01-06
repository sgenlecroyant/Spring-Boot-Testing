package com.sgenlecroyant.testing.SpringBootTesting.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.sgenlecroyant.testing.SpringBootTesting.models.Member;

@DataJpaTest(showSql = true)
class MemberRepoTest {
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Test
	void shouldFailWhenSearchByUsername() {
		// Given
		Member member = new Member("Hakim", "Ziyech", "hakim@gmail.com");
		
		// when
		Optional<Member> foundMemberByUsername = this.memberRepo.findMemberByUsername(member.getUsername());
		
		// then
		assertThat(foundMemberByUsername).isEmpty();
	}
	
	@Test
	void shouldSucceedWhenSearchByUsername() {
		// Given: this member exists in the database
		Member memberfromDB = new Member("Jenny", "Richards", "jenny@gmail.com");
		this.memberRepo.save(memberfromDB);
		System.out.println(this.memberRepo.findMemberByUsername("jenny@gmail.com"));
		// when
		Optional<Member> foundMemberByUsername = this.memberRepo.findMemberByUsername(memberfromDB.getUsername());
		
		// then
		assertThat(foundMemberByUsername).isNotEmpty()
		.isInstanceOf(Optional.class)
		.satisfies((member) -> {
			Member realMember = member.get();
			assertThat(realMember.getFirstName()).isEqualTo(memberfromDB.getFirstName());
			assertThat(realMember.getLastName()).isEqualTo(memberfromDB.getLastName());
			assertThat(realMember.getUsername()).isEqualTo(memberfromDB.getUsername());
		});
	}


}

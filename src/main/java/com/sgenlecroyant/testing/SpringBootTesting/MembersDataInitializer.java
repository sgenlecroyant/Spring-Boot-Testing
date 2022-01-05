package com.sgenlecroyant.testing.SpringBootTesting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sgenlecroyant.testing.SpringBootTesting.models.Member;
import com.sgenlecroyant.testing.SpringBootTesting.repositories.MemberRepo;

@Component
public class MembersDataInitializer implements CommandLineRunner{
	
	private Member member1;
	private Member member2;
	private Member member3;
	private Member member4;
	private Member member5;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public void run(String... args) throws Exception {
		this.member1 = new Member("Rick", "Robinson", "rick@gmail.com");
		this.member2 = new Member("Jenny", "Richards", "jenny@gmail.com");
		this.member3 = new Member("Anna", "Smith", "anna@gmail.com");
		this.member4 = new Member("Maria", "Jones", "maria@gmail.com");
		this.member5 = new Member("James", "Peterson", "peterson@gmail.com");
		
		this.memberRepo.saveAll(List.of(member1, member2, member3, member4,member5));
	}

}

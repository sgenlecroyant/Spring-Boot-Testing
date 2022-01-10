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
	private Member member6;
	private Member member7;
	private Member member8;
	private Member member9;
	private Member member10;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public void run(String... args) throws Exception {
		this.member1 = new Member("Rick", "Robinson", "rick@gmail.com");
		this.member2 = new Member("Jenny", "Richards", "jenny@gmail.com");
		this.member3 = new Member("Anna", "Smith", "anna@gmail.com");
		this.member4 = new Member("Maria", "Jones", "maria@gmail.com");
		this.member5 = new Member("James", "Peterson", "peterson@gmail.com");
		this.member6 = new Member("James", "Bond", "james@gmail.com");
		this.member7 = new Member("Robert", "Patrick", "patrick@gmail.com");
		this.member8 = new Member("Rod", "Johnson", "rodj@gmail.com");
		this.member9 = new Member("Pep", "Raina", "pep@gmail.com");
		this.member10 = new Member("Michael", "Jay", "jay@gmail.com");
		
		this.memberRepo.saveAll(List.of(
				member1, member2, member3, member4,member5,
				member6, member6, member7, member8, member9, member10));
	}

}

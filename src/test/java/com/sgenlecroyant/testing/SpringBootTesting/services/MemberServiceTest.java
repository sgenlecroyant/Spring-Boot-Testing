package com.sgenlecroyant.testing.SpringBootTesting.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sgenlecroyant.testing.SpringBootTesting.models.Member;
import com.sgenlecroyant.testing.SpringBootTesting.repositories.MemberRepo;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {
	
	@Mock
	private MemberRepo memberRepo;
	
	private MemberService memberService;
	
	private AutoCloseable autoCloseable;
	
	@BeforeEach
	public void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		this.memberService = new MemberService(this.memberRepo);
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		this.autoCloseable.close();
	}
	
	@Test
	@Rollback(value = true)
	public void test_findMemberByUsername() {
		Member member = new Member("Franck", "Sgen", "sgen@gmail.com");
		
		Mockito.when(this.memberRepo.findMemberByUsername(member.getUsername())).thenReturn(Optional.ofNullable(member));
		Optional<Member> foundMemberByUsername = this.memberService.findMemberByUsername(member.getUsername());
		
		assertThat(foundMemberByUsername).isNotEmpty();
	}
	
	@Test
	public void test_saveNewMember_FAIL() {
		
		Member member = new Member("Franck", "Sgen", "sgen@gmail.com");
		// 2 different ways and both give the same results
		
		// the first way: much more obvious
		Mockito.when(this.memberRepo.findMemberByUsername(member.getUsername())).thenReturn(Optional.ofNullable(member));
		
		// second way: the most preferred way
//		BDDMockito.given(this.memberRepo.findMemberByUsername(member.getUsername())).willReturn(Optional.ofNullable(member));
		
		Mockito.verify(this.memberRepo, never()).save(Mockito.any());
		
		assertThatThrownBy(() -> this.memberService.saveNewMember(member))
							.isInstanceOf(IllegalArgumentException.class)
							.hasMessageContaining("duplicated");
		
	}
	
	@Test
	public void test_saveNewMember_SUCCESS() {
		
		Member member = new Member("Franck", "Sgen", "sgen@gmail.com");
		
		this.memberService.saveNewMember(member);
		
		ArgumentCaptor<Member> memberArgumentCaptor = 
				ArgumentCaptor.forClass(Member.class);
		
		 	 	 	 	  				// here we try to capture the actual value if passed
		Mockito.verify(this.memberRepo).save(memberArgumentCaptor.capture());
								// we get the value back for later use: assertions for example
		Member capturedMember = memberArgumentCaptor.getValue();
		 
		// the tests
		assertThat(capturedMember.getFirstName()).isEqualTo(member.getFirstName());
	}
	
	@Test
	public void test_deleteMemberById_PASSED() {
		
		Member member = new Member("Franck", "Sgen", "sgen@gmail.com");
		member.setId(100);
		Mockito.when(this.memberRepo.findById(100)).thenReturn(Optional.ofNullable(member));
		
		this.memberService.deleteMemberById(100);
		
		Mockito.verify(this.memberRepo).deleteById(Mockito.anyInt());
	}
	
	@Test
	public void test_deleteMemberById_FAIL() {
		
		Member member = new Member("Franck", "Sgen", "sgen@gmail.com");
		member.setId(100);
		
		Mockito.when(this.memberRepo.findById(100)).thenReturn(Optional.empty());
		
		boolean wasDeleted = this.memberService.deleteMemberById(100);
		
		Mockito.verify(this.memberRepo, Mockito.never()).deleteById(Mockito.anyInt());
		
		assertThat(wasDeleted).isFalse();
	}
	
	@Test
	public void test_findAllMembers() {
		
		Mockito.when(this.memberRepo.findAll()).thenReturn(
				List.of(new Member("Hello", "World", "world@gmail.com"), 
						new Member("Hello", "people", "people@gmail.com"), 
						new Member("Hello", "Beauties", "beauties@gmail.com")));
		List<Member> allMembers = this.memberService.findAllMembers();
		assertThat(allMembers).allMatch((member) -> {
			return member.getUsername().length() > 0;
		}).allSatisfy((member) -> {
			assertThat(member.getFirstName()).isNotNull();
			assertThat(member.getLastName()).isNotNull();
			assertThat(member.getUsername()).isNotNull();
			
			assertThat(member.getFirstName()).isNotEmpty();
			assertThat(member.getLastName()).isNotEmpty();
			assertThat(member.getUsername()).isNotEmpty();
		});
	}

}

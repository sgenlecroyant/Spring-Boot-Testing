package com.sgenlecroyant.testing.SpringBootTesting.restControllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgenlecroyant.testing.SpringBootTesting.models.Member;
import com.sgenlecroyant.testing.SpringBootTesting.repositories.MemberRepo;
import com.sgenlecroyant.testing.SpringBootTesting.services.MemberService;

@WebMvcTest(controllers = MemberRestController.class)
class MemberRestControllerTest {

	@MockBean
	private MemberService memberService;
	
//	@InjectMocks
//	private MemberRestController memberRestController;
	
	private MockMvc mockMvc;
	
//	@MockBean
//	private MemberRepo memberRepo;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		System.out.println("Set up done!");
	}
	
	@Test
	public void test_findAllMembers() throws Exception {
		Member member1 = new Member("Hello", "World", "hello@gmail.com");
		Member member2 = new Member("Hello", "People", "people@gmail.com");
		List<Member> members = List.of(member1, member2);
		
		Mockito.when(this.memberService.findAllMembers()).thenReturn(members);
		
		Mockito.when(this.memberService.findAllMembers()).thenReturn(members);
		
		MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/members"))
				.andExpect(status()
						.isOk())
				.andReturn();
		String contentAsString = mvcResult.getResponse().getContentAsString();
		String membersAsString = this.objectMapper.writeValueAsString(members);
		assertThat(contentAsString).isEqualTo(membersAsString);
		
		System.out.println(contentAsString);
	}
}
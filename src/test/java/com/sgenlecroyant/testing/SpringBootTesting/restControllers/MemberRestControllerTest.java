package com.sgenlecroyant.testing.SpringBootTesting.restControllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
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
	
	@Test
	public void test_findMemberById_SUCCESS() throws Exception {
		Member member1 = new Member("Hello", "World", "hello@gmail.com");
		member1.setId(200);
		
		Mockito.when(this.memberService.findMemberById(200)).thenReturn(Optional.ofNullable(member1));
		
		MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/members/" +200))
					.andExpect(status().isOk()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		String contentAsString = response.getContentAsString();
		
		assertThat(contentAsString.length()).isGreaterThan(0);
		System.out.println("content as string: " +contentAsString);
	}
	
	@Test
	public void test_findMemberById_FAIL() throws Exception {
		
		Mockito.when(this.memberService.findMemberById(200)).thenReturn(Optional.empty());
		
		
		assertThatThrownBy(() -> this.mockMvc.perform(get("/api/v1/members/" +200))
				.andExpect(status()
						.isInternalServerError()))
		.isInstanceOf(Exception.class)
		.hasMessageContaining("not valid");
	}
	
	@Test
	public void test_deleteMemberById_SUCCESS() throws Exception {
		
		Mockito.when(this.memberService.deleteMemberById(100))
		.thenReturn(true);
		
		this.mockMvc.perform(delete("/api/v1/members/" +100))
		.andExpect(status().isOk())
		.andReturn();
		
	}
	
	@Test
	public void test_deleteMemberById_FAILURE() throws Exception {
		
		Mockito.when(this.memberService.deleteMemberById(100))
		.thenReturn(false);
		
		this.mockMvc.perform(delete("/api/v1/members/" +100))
		.andExpect(status().isNotFound())
		.andReturn();
		
	}
}

package com.sgenlecroyant.testing.SpringBootTesting.restControllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgenlecroyant.testing.SpringBootTesting.models.Member;
import com.sgenlecroyant.testing.SpringBootTesting.services.MemberService;

@RestController
@RequestMapping(value = "/api/v1")
public class MemberRestController {

	@Autowired
	private MemberService memberService;

	@GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Member> findAllMembers() {
		return this.memberService.findAllMembers();
	}

	@GetMapping(value = "/members/{id}")
	public ResponseEntity<Member> findMemberById(@PathVariable Integer id) throws Exception {
		Optional<Member> foundMemberById = this.memberService.findMemberById(id);

		return ResponseEntity.ok().body(foundMemberById.orElseThrow(() -> {
			return new Exception("The provided id was not valid");
		}));
	}

	@DeleteMapping(value = "/members/{id}")
	public ResponseEntity<Member> deleteMemberById(@PathVariable Integer id) {
		boolean wasMemberDeleted = this.memberService.deleteMemberById(id);
		if (wasMemberDeleted) {
			return new ResponseEntity<Member>(HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "/members", consumes = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("static-access")
	public ResponseEntity<Member> registerMember(@RequestBody Member member) {
		Member savedMember = this.memberService.saveNewMember(member);
		return ResponseEntity.of(Optional.ofNullable(savedMember)).status(HttpStatus.FOUND).build();
	}

	@PutMapping(value = "/members")
	public ResponseEntity<Member> updateMember(@PathVariable Integer targetMemberId, @RequestBody Member member) {
		Member updatedMember = this.memberService.updateMember(targetMemberId, member);
		return new ResponseEntity<Member>(updatedMember, HttpStatus.OK);
	}
}

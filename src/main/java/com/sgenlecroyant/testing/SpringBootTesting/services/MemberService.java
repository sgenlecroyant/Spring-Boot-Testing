package com.sgenlecroyant.testing.SpringBootTesting.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgenlecroyant.testing.SpringBootTesting.loosecoupling.member.MemberServiceHelper;
import com.sgenlecroyant.testing.SpringBootTesting.models.Member;
import com.sgenlecroyant.testing.SpringBootTesting.repositories.MemberRepo;

@Service
public class MemberService implements MemberServiceHelper{
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public Optional<Member> findMemberByUsername(String username) {
		return this.memberRepo.findByUsername(username);
	}
	@Override
	public Optional<Member> findMemberById(Integer id) {
		return this.memberRepo.findById(id);
	}

	@Override
	public Member saveNewMember(Member member) {
		Optional<Member> foundMemberByUsername = this.memberRepo.findByUsername(member.getUsername());
		if(foundMemberByUsername.isPresent()) {
			throw new IllegalArgumentException("duplicated username");
		}
		return this.memberRepo.save(member);
	}

	@Override
	public boolean deleteMemberById(Integer id) {
		
		Optional<Member> foundMember = this.memberRepo.findById(id);
		if(foundMember.isPresent()) {
			this.memberRepo.deleteById(id);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Member updateMember(Integer targetMemberId, Member member) {
		Optional<Member> foundById = this.memberRepo.findById(targetMemberId);
		if(foundById.isPresent()) {
			Member oldMember = new Member();
			oldMember.setId(foundById.get().getId());
			oldMember.setFirstName(member.getFirstName());
			oldMember.setLastName(member.getLastName());
			oldMember.setUsername(member.getUsername());
			this.memberRepo.save(oldMember);
			return oldMember;
		}else {
			throw new IllegalStateException(String.format("%d is invalid is", targetMemberId));		}
	}

	@Override
	public List<Member> findAllMembers() {
		
		return this.memberRepo.findAll();
	}

}

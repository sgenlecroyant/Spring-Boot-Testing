package com.sgenlecroyant.testing.SpringBootTesting.loosecoupling.member;

import java.util.List;
import java.util.Optional;

import com.sgenlecroyant.testing.SpringBootTesting.models.Member;

public interface MemberServiceHelper {
	// utility methods
	public Optional<Member> findMemberByUsername(String username);
	public Optional<Member> findMemberById(Integer id);
	public Member saveNewMember(Member member);
	public boolean deleteMemberById(Integer id);
	public Member updateMember(Integer targetMemberId, Member member);
	public List<Member> findAllMembers();
}

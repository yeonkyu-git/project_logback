package project.logback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.logback.entity.Member;
import project.logback.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public Member join(Member member) {
        memberRepository.save(member);
        return member;
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }


}

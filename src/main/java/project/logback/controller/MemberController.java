package project.logback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.logback.entity.Member;
import project.logback.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/add")
    public Member sign(@RequestBody Member member) {
        log.info("sign in user...");
        return memberService.join(member);
    }


    @GetMapping("/{memberId}")
    public Member findMember(@PathVariable("memberId") Long memberId) {
        log.info("find member...");
        return memberService.findById(memberId);
    }

    @PostMapping("/login/{memberId}")
    public String login(@PathVariable("memberId") Long memberId,
                        HttpServletRequest request) {
        log.info("login ....");

        Member member = memberService.findById(memberId);

        if (member != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMemberName", member.getUsername());
        }
        return "ok";
    }

    @GetMapping("/all")
    public List<Member> findMembers() {
        return memberService.findAll();
    }
}

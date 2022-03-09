package project.logback.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.logback.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class MemberRepository {

    private static final Map<Long, Member> store = new ConcurrentHashMap<Long, Member>();
    private static Long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }


    public Member findById(Long memberId) {
        return store.get(memberId);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clear() {
        store.clear();
    }

}

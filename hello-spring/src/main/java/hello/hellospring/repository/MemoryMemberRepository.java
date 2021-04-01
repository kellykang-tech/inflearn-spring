package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;



public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);            // id = 1
        store.put(member.getId(), member);  // 이름은 어떻게 받을 건지? -> test에서 setName에 설정해줌.
        return member;                      // id = 1, name = spring
    }


    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());     // store에 있는 values들 == Member들
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }
}


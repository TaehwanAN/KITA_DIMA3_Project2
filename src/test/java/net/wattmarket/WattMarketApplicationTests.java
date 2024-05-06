package net.wattmarket;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.wattmarket.entity.MembersEntity;
import net.wattmarket.repository.MembersRepository;
@SpringBootTest
class WattMarketApplicationTests {

	@Autowired
    private MembersRepository membersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

	// @Test
	// void contextLoads() {
	// }
	@Test
    void encodeExistingPasswords() {
        // 모든 멤버를 가져옵니다.
        List<MembersEntity> members = membersRepository.findAll();

        // 각 멤버의 비밀번호를 인코딩합니다.
        for (MembersEntity member : members) {
            String rawPassword = member.getMemberPw();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            member.setMemberPw(encodedPassword);
			member.setJoinDate(LocalDateTime.now());
            membersRepository.save(member);
        }
    }

}

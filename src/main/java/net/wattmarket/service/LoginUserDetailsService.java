package net.wattmarket.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.wattmarket.dto.LoginUserDetails;
import net.wattmarket.dto.MembersDTO;
import net.wattmarket.entity.MembersEntity;
import net.wattmarket.repository.MembersRepository;



@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
	private final MembersRepository membersRepository;


	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		MembersEntity memberEntity = membersRepository.findByMemberId(memberId)
			.orElseThrow(() -> new UsernameNotFoundException("유저아이디 못찾음"));
		MembersDTO memberDTO = MembersDTO.toDTO(memberEntity);
		LoginUserDetails loginMemberDTO = new LoginUserDetails(memberDTO);
		// log.info(loginMemberDetails.toString());
		return loginMemberDTO;
	}
}





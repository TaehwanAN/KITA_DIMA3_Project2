package net.wattmarket.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.ToString;

@ToString
public class LoginUserDetails implements UserDetails {


	private static final long serialVersionUID = 1L;
	private String memberName;
    private String memberId;
    private String memberPw;
    private String memberRole;
    
	public LoginUserDetails(MembersDTO membersDTO){
		this.memberId=membersDTO.getMemberId();
        this.memberPw=membersDTO.getMemberPw();
		this.memberName=membersDTO.getMemberName();
		this.memberRole=membersDTO.getMemberRole();
    }


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collection = new ArrayList<>();
    collection.add(new GrantedAuthority() {
        private static final long serialVersionUID = 1L;
        @Override
        public String getAuthority() {
            return memberRole;
        }
        @Override
        public String toString() {
            return getAuthority();
        }
    });
    return collection;
}

	@Override
	public String getPassword() {
		return this.memberPw;
	}

	@Override
	public String getUsername() {
		return this.memberId;
	}

	public String getUserName(){
		return this.memberName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
    @Override
	public boolean isEnabled() {
		return true;
	}
}
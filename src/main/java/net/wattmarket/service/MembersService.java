package net.wattmarket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.wattmarket.dto.MembersDTO;
import net.wattmarket.entity.MembersEntity;
import net.wattmarket.repository.MembersRepository;

@Service
@RequiredArgsConstructor
public class MembersService {
    private final MembersRepository membersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    // 회원가입
    public Boolean joinMember(
        MembersDTO membersDTO
    ){
        if(membersDTO.getInstalledCapacity()==-1){
            membersDTO.setMemberRole("ROLE_CONSUMER");
        } else {
            membersDTO.setMemberRole("ROLE_PROSUMER");
        }
        if (!isValid()){
            return false;
        }
        membersDTO.setMemberPw(bCryptPasswordEncoder.encode(membersDTO.getMemberPw()));
        membersDTO.setKepcoCustNum(getKepcoCustNum(membersDTO.getNationalIdFirst(), membersDTO.getNationalIdSecond()));

        List<Double> location = randomLocation();
        membersDTO.setLocationX(location.get(0));
        membersDTO.setLocationY(location.get(1));
        membersRepository.save(MembersEntity.toEntity(membersDTO));
        return true;
    }

    // 아이디, 주민번호 검증
    public Boolean isValid(){
        return true;
    }
    //한전 고객번호 가져오기
    public String getKepcoCustNum(
        String nationalIdFirst,
        String nationalIdSecond
    ){
        //한전에다가 주민번호 보내서 받아오면 됨

        //임시로 가데이터 만들어두기
        Random random = new Random();
        int part1 = random.nextInt(100);  // 0부터 99까지의 난수 생성
        int part2 = random.nextInt(10000);  // 0부터 9999까지의 난수 생성
        int part3 = random.nextInt(10000);  // 0부터 9999까지의 난수 생성

        return String.format("%02d-%04d-%04d", part1, part2, part3);
    }

    /**
	 * 임시 메소드: 로케이션 랜덤으로 설정
	 * 변경할 방향: Geocoding API이용해서 주소값 파라미터로 받아서 위경도 값 리턴하도록.
	 * 만약 자바스크립트에서 해결해서 올 수 있다면, 해올것
	 * @return
	 */
	public List<Double> randomLocation(){
		List<Double> loc = new ArrayList<Double>();
		// 위도 범위: 33 ~ 43
        // 경도 범위: 124 ~ 132
        double minLatitude = 33.0;
        double maxLatitude = 43.0;
        double minLongitude = 124.0;
        double maxLongitude = 132.0;

        Random random = new Random();

        double randomLatitude = minLatitude + (maxLatitude - minLatitude) * random.nextDouble();
        double randomLongitude = minLongitude + (maxLongitude - minLongitude) * random.nextDouble();

		loc.add(randomLongitude); loc.add(randomLatitude);

		return loc;
    }
}
    

package net.wattmarket.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.wattmarket.entity.MembersEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembersDTO {
    private Long memberNum;
    private String memberName;
    private String memberId;
    private String memberPw;
    private String memberPhone;
    private String nationalIdFirst;
    private String nationalIdSecond;
    private String customerType;
    private String contractType;
    private String memberAddrFirst;
    private String memberAddrSecond;
    private String memberAddrThird;
    private int installedCapacity;
    private boolean isAgree;
    private LocalDateTime joinDate;
    private String memberRole;
	private String kepcoCustNum;
    private Double locationX;
    private Double locationY;

    public static MembersDTO toDTO(MembersEntity membersEntity){
        MembersDTO membersDTO = MembersDTO.builder()
            .memberNum(membersEntity.getMemberNum()).memberName(membersEntity.getMemberName())
            .memberId(membersEntity.getMemberId()).memberPw(membersEntity.getMemberPw()).memberPhone(membersEntity.getMemberPhone())
            .nationalIdFirst(membersEntity.getNationalIdFirst()).nationalIdSecond(membersEntity.getNationalIdSecond())
            .customerType(membersEntity.getCustomerType()).contractType(membersEntity.getContractType())
            .memberAddrFirst(membersEntity.getMemberAddrFirst()).memberAddrSecond(membersEntity.getMemberAddrSecond()).memberAddrThird(membersEntity.getMemberAddrThird())
            .installedCapacity(membersEntity.getInstalledCapacity()).isAgree(membersEntity.isAgree()).joinDate(membersEntity.getJoinDate())
            .memberRole(membersEntity.getMemberRole()).kepcoCustNum(membersEntity.getKepcoCustNum())
            .locationX(membersEntity.getLocationX()).locationY(membersEntity.getLocationY())
            .build();
        return membersDTO;
    }
}

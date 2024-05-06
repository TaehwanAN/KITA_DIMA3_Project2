package net.wattmarket.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ForeignKey;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.wattmarket.dto.MembersDTO;
import java.util.List;
import java.util.ArrayList;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="MEMBERS")
public class MembersEntity {

    @SequenceGenerator(name="members_seq", sequenceName = "members_seq", initialValue = 3, allocationSize = 1)
    @Column(name="MEMBER_NUM") @GeneratedValue(generator = "members_seq") @Id
    private Long memberNum;

    @Column(name="MEMBER_NAME")
    private String memberName;
    
    
    @Column(name="MEMBER_ID", unique = true)
    private String memberId;
    @Column(name="MEMBER_PW")
    private String memberPw;
    @Column(name="MEMBER_PHONE")
    private String memberPhone;
    @Column(name="NATIONAL_ID_FIRST")
    private String nationalIdFirst;
    @Column(name="NATIONAL_ID_SECOND")
    private String nationalIdSecond;
    @Column(name="CUSTOMER_TYPE")
    private String customerType;
    @Column(name="CONTRACT_TYPE")
    private String contractType;
    @Column(name="MEMBER_ADDR_FIRST")
    private String memberAddrFirst;
    @Column(name="MEMBER_ADDR_SECOND")
    private String memberAddrSecond;
    @Column(name="MEMBER_ADDR_THIRD")
    private String memberAddrThird;
    @Column(name="INSTALLED_CAPACITY")
    private int installedCapacity;
    @Column(name="IS_AGREE")
    private boolean isAgree;
    @Column(name="JOIN_DATE") @CreationTimestamp
    private LocalDateTime joinDate;
    @Column(name="MEMBER_ROLE")
    private String memberRole;
    @Column(name="KEPCO_CUST_NUM")
    private String kepcoCustNum;
    @Column(name="LOCATION_X")
    private Double locationX;
    @Column(name="LOCATION_Y")
    private Double locationY;

    public static MembersEntity toEntity(MembersDTO membersDTO){
        MembersEntity membersEntity =MembersEntity.builder()
            .memberNum(membersDTO.getMemberNum()).memberName(membersDTO.getMemberName())
            .memberId(membersDTO.getMemberId()).memberPw(membersDTO.getMemberPw()).memberPhone(membersDTO.getMemberPhone())
            .nationalIdFirst(membersDTO.getNationalIdFirst()).nationalIdSecond(membersDTO.getNationalIdSecond())
            .customerType(membersDTO.getCustomerType()).contractType(membersDTO.getContractType())
            .memberAddrFirst(membersDTO.getMemberAddrFirst()).memberAddrSecond(membersDTO.getMemberAddrSecond()).memberAddrThird(membersDTO.getMemberAddrThird())
            .installedCapacity(membersDTO.getInstalledCapacity()).isAgree(membersDTO.isAgree()).joinDate(membersDTO.getJoinDate())
            .memberRole(membersDTO.getMemberRole()).kepcoCustNum(membersDTO.getKepcoCustNum())
            .locationX(membersDTO.getLocationX()).locationY(membersDTO.getLocationY())
            .build();
            
        return  membersEntity;
    }
    // @OneToMany(mappedBy = "prodMembersEntity",
	// 	cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	// private List<ProductionsEntity> prodEntity = new ArrayList<>();
	// @OneToMany(mappedBy = "consMembersEntity",
	// 	cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	// private List<ConsumptionsEntity> consEntity = new ArrayList<>();
}

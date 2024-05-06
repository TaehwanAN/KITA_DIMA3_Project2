package net.wattmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.wattmarket.entity.MembersEntity;
import java.util.Optional;
import java.util.List;

@Repository
public interface MembersRepository extends JpaRepository<MembersEntity,Long>{

    Optional<MembersEntity> findByMemberId(String memberId);

    @Query("SELECT m.memberRole FROM MembersEntity m WHERE m.memberId = :memberId")
    String findMemberRoleByMemberId(@Param("memberId") String memberId);

    @Query("SELECT m.locationX,m.locationY, m.installedCapacity FROM MembersEntity m WHERE m.memberId = :memberId")
    List<Object[]> findPredictInfoByMemberId(@Param("memberId") String memberId);

    @Query("SELECT m.contractType FROM MembersEntity m WHERE m.memberId =:memberId")
    String findContractTypeByMemberId(@Param("memberId") String memberId);
}

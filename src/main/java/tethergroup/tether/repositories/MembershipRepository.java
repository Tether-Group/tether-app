package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findMembershipByUser_IdAndGroup_Id(Long userId, Long groupId);

    List<Membership> findMembershipsByUser_Id(Long userId);

    @Query(nativeQuery = true,
            value = "SELECT group_id FROM memberships m INNER JOIN groups g ON g.id = m.group_id INNER JOIN users u ON g.admin_id = :id WHERE m.is_pending = 1")
    List<Long> findAdminsPendingGroupRequestsCount(@Param("id")Long id);
}

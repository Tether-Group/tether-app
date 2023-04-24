package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findMembershipByUser_IdAndGroup_Id(Long userId, Long groupId);

    List<Membership> findMembershipsByUser_Id(Long  userId);

    List<Membership> findMembershipsByGroup_Id(Long groupId);

    @Query(nativeQuery = true,
            value = "SELECT m.* FROM memberships m JOIN users u ON m.user_id = u.id JOIN groups g ON m.group_id = g.id WHERE m.is_pending = 1 AND g.admin_id = :id")
    List<Membership> findMembershipsFromGroupJoinRequestsForTheLoggedInUser(@Param("id")Long id);

    @Override
    void deleteById(Long id);

    @Query(nativeQuery = true,
    value = "select * from memberships m where m.group_id = :group_id and is_pending = 0 limit 5")
    List<Membership> findMembershipsByGroupIdWhereIsNotPendingLimitFive(@Param("group_id") Long groupId);
}

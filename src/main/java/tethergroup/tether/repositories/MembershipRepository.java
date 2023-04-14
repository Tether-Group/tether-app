package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findMembershipByUser_IdAndGroup_Id(Long userId, Long groupId);

//    List<Membership> findMembershipsByUser_Id(Long userId);

//    @Query(nativeQuery = true,
//            value = "SELECT u.id, u.username, u.first_name, u.last_name, u.email, u.password, u.bio FROM memberships m JOIN users u ON m.user_id = u.id JOIN groups g ON m.group_id = g.id WHERE m.is_pending = 1 AND g.admin_id = :id")
//    List<User> findAdminsPendingGroupRequestsCount(@Param("id")Long id);
}

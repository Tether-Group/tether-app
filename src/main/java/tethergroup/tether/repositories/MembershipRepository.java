package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findMembershipByUser_IdAndGroup_Id(Long userId, Long groupId);
}

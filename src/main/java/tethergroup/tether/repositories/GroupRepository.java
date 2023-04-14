package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.User;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    //    random group query for visitor
    @Query(nativeQuery = true, value = "SELECT * FROM groups ORDER BY rand() LIMIT 50")
    List<Group> randomGroupsLimitFifty();

    @Query(nativeQuery = true, value = "SELECT * FROM groups ORDER BY rand() LIMIT 5")
    List<Group> randomGroupsLimitFive();

    //    query for users groups on the latest group created that they are in
    @Query(nativeQuery = true, value = "SELECT * FROM groups g ORDER BY g.id DESC")
    List<Group> groupsByDescendingId();

//    Group searchByGroupName(String name);

    //    query for searching groups in navbar
    @Query("FROM Group g WHERE g.name LIKE %:term% OR g.description LIKE %:term%")
    List<Group> findLikeGroupNameOrDescription(@Param("term") String group);

    @Query(nativeQuery = true,
            value = "SELECT g.* FROM memberships m JOIN users u ON m.user_id = u.id JOIN groups g ON m.group_id = g.id WHERE m.is_pending = 1 AND g.admin_id = :id")
    List<Group> findGroupsFromGroupJoinRequestsForTheLoggedInUser(@Param("id")Long id);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) FROM memberships m JOIN users u ON m.user_id = u.id JOIN groups g ON m.group_id = g.id WHERE m.is_pending = 1 AND g.admin_id = :id")
    Long getCountOfGroupRequestsForLoggedInUserAndAdmin(Long id);
}

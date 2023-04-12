package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Group;

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
}

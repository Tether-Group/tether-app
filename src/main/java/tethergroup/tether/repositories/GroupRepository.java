package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tethergroup.tether.models.Group;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
//    random group query for visitor
    @Query(nativeQuery = true, value= "SELECT * FROM groups ORDER BY rand() LIMIT 50")
    List<Group> randomGroups();

//    query for users groups on the latest group created that they are in
    @Query(nativeQuery = true, value = "SELECT * FROM groups g ORDER BY g.id DESC")
    List<Group> groupsByDescendingId();

//    Group searchByGroupName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM `groups` WHERE  ${global-search} = name")
    List<Group>groupsSearched();
}

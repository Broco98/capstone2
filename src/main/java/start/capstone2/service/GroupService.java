package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.capstone2.domain.user.Group;
import start.capstone2.domain.user.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public Long createGroup(String name) {
        Group group = Group.createGroup(name);
        groupRepository.save(group);

        return group.getId();
    }

}

package start.capstone2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import start.capstone2.domain.user.Group;
import start.capstone2.domain.user.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public Long createGroup(String name) {
        Group group = Group.createGroup(name);
        groupRepository.save(group);

        return group.getId();
    }

    public Group findById(Long id) {
        return groupRepository.findById(id).orElseThrow();
    }
}

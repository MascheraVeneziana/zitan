package org.mascheraveneziana.zitan.repository;

import java.util.List;

import org.mascheraveneziana.zitan.domain.Meeting;
import org.springframework.data.repository.CrudRepository;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {
    List<Meeting> findAll();
}

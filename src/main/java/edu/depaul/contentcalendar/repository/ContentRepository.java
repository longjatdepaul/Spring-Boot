package edu.depaul.contentcalendar.repository;

import edu.depaul.contentcalendar.model.Content;
import org.springframework.data.repository.ListCrudRepository;

public interface ContentRepository extends ListCrudRepository<Content, Integer> {
}

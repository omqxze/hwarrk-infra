package com.hwarrk.repository;

import com.hwarrk.entity.ProjectLike;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectLikeRepositoryCustom {
    List<ProjectLike> getProjectLikes(Long memberId, Long lastProjectLikeId, Pageable pageable);
}

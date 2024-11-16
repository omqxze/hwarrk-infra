package com.hwarrk.common.dto.req;

import com.hwarrk.common.dto.dto.RecruitingPositionDto;
import com.hwarrk.entity.Post;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateReq {

    private long projectId;
    private String title;
    private String body;
    private List<RecruitingPositionDto> recruitingPositionDtoList;
    private List<String> skills;

    public Post createPost() {
        return Post.builder()
                .title(title)
                .body(body)
                .build();
    }
}

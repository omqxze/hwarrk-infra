package com.hwarrk.common.dto.req;

import com.hwarrk.common.dto.dto.RecruitingPositionDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateReq {

    private String title;
    private String body;
    private List<RecruitingPositionDto> recruitingPositionDtoList;
    private List<String> skills;
}

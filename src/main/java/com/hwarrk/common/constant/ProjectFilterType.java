package com.hwarrk.common.constant;

import java.util.Arrays;

public enum ProjectFilterType {

    TRENDING("인기 급상승"), LATEST("최신"), FAVORITE("찜한 프로젝트");

    private final String name;

    ProjectFilterType(String name) {
        this.name = name;
    }

    public static ProjectFilterType findType(String type) {
        return Arrays.stream(values()).filter(v -> v.name.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 필터 타입 입니다."));
    }
}

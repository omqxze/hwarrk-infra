package com.hwarrk.repository;

import static com.hwarrk.entity.QMember.member;
import static com.hwarrk.entity.QPosition.position;
import static com.hwarrk.entity.QPost.post;
import static com.hwarrk.entity.QProject.project;
import static com.hwarrk.entity.QProjectLike.projectLike;
import static com.hwarrk.entity.QRecruitingPosition.recruitingPosition;

import com.hwarrk.common.constant.ProjectFilterType;
import com.hwarrk.common.constant.RecruitingType;
import com.hwarrk.common.dto.dto.ProjectWithLikeDto;
import com.hwarrk.common.dto.dto.QProjectWithLikeDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public PageImpl<ProjectWithLikeDto> findFilteredProjects(RecruitingType recruitingType,
                                                             ProjectFilterType filterType,
                                                             String keyWord, Long memberId, Pageable pageable) {

        JPQLQuery<ProjectWithLikeDto> query = queryFactory
                .select(new QProjectWithLikeDto(
                        project,
                        Expressions.booleanTemplate("CASE WHEN {0} IS NOT NULL THEN true ELSE false END",
                                projectLike.id)
                ))
                .from(project)
                .join(project.post, post)
                .leftJoin(project.projectLikes, projectLike)
                .where(eqRecruitingType(recruitingType));

        if (filterType == ProjectFilterType.TRENDING) {
            query.orderBy(project.views.desc());
        }

        if (filterType == ProjectFilterType.LATEST) {
            query.orderBy(project.createdAt.desc());
        }

        if (filterType == ProjectFilterType.FAVORITE) {
            query.join(project.projectLikes, projectLike)
                    .on(projectLike.member.id.eq(memberId));
        }

        List<ProjectWithLikeDto> projects = query
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        long totalProjectCount = queryFactory.selectFrom(project)
                .where(eqRecruitingType(recruitingType), containsKeyWord(keyWord))
                .fetchCount();

        return new PageImpl<>(projects, pageable, totalProjectCount);
    }

    private BooleanExpression eqRecruitingType(RecruitingType recruitingType) {
        return post.recruitingType.eq(recruitingType);
    }

    private BooleanExpression containsKeyWord(String keyWord) {
        if (Optional.ofNullable(keyWord).isPresent()) {
            return post.title.containsIgnoreCase(keyWord);
        }
        return Expressions.TRUE;
    }


    @Override
    public List<ProjectWithLikeDto> findRecommendedProjects(Long memberId) {
        return queryFactory
                .select(new QProjectWithLikeDto(
                        project,
                        Expressions.booleanTemplate("CASE WHEN {0} IS NOT NULL THEN true ELSE false END",
                                projectLike.id)
                ))
                .from(project)
                .join(project.post, post)
                .leftJoin(project.projectLikes, projectLike)
                .join(post.positions, recruitingPosition)
                .join(member).on(member.id.eq(memberId))
                .join(member.positions, position)
                .where(
                        recruitingPosition.position.eq(position.positionType)
                        // TODO skill 이 1개 이상 일치 로직 필요
                )
                .fetch();
    }
}

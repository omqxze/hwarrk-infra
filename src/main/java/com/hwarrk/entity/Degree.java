package com.hwarrk.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DEGREE")
public class Degree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "degree_id")
    private Long id;
    private String degreeType;
    private String universityType;
    private String school;
    private String major;
    private String graduationType;
    private String entranceDate;
    private String graduationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Degree(String degreeType, String universityType, String school, String major, String graduationType, String entranceDate, String graduationDate, Member member) {
        this.degreeType = degreeType;
        this.universityType = universityType;
        this.school = school;
        this.major = major;
        this.graduationType = graduationType;
        this.entranceDate = entranceDate;
        this.graduationDate = graduationDate;
        this.member = member;
    }
}

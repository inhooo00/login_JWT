//package com.example.login_jwt.member.domain;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class MemberLike {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne
//    @JoinColumn(name = "liked_member_id")
//    private Member likedMember;
//
//    public MemberLike(Member member, Member likedMember) {
//        this.member = member;
//        this.likedMember = likedMember;
//    }
//}

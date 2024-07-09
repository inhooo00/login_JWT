//package com.example.login_jwt.member.domain;
//
//import com.example.copro.board.domain.Board;
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class MemberScrapBoard {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Schema(description = "스크랩 id", example = "1")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id", nullable = false)
//    private Board board;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;
//
//    @Builder
//    private MemberScrapBoard(Board board, Member member){
//        this.board = board;
//        this.member = member;
//    }
//
//    public static MemberScrapBoard of(Board board, Member member) {
//        return MemberScrapBoard.builder()
//                .board(board)
//                .member(member)
//                .build();
//    }
//
//}

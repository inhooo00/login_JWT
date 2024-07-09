//package com.example.login_jwt.member.domain.repository;
//
//import com.example.copro.board.domain.Board;
//import com.example.copro.member.domain.Member;
//import com.example.copro.member.domain.MemberScrapBoard;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface MemberScrapBoardRepository extends JpaRepository<MemberScrapBoard, Long> {
//    Optional<MemberScrapBoard> findByMemberMemberIdAndBoardBoardId(Long memberId, Long boardId);
//
//    Page<MemberScrapBoard> findByMember(Member member, Pageable pageable);
//
//    boolean existsByMemberAndBoard(Member member, Board board);
//}

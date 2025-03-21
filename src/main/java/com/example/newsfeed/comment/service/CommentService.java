package com.example.newsfeed.comment.service;

import com.example.newsfeed.base.config.AuthUser;
import com.example.newsfeed.comment.dto.*;
import com.example.newsfeed.comment.entity.CommentEntity;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.entity.PostEntity;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //댓글 생성
    @Transactional
    public SaveCommentResponseDto saveComment(Long postId, SaveCommentRequestDto dto) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("게시글이 없습니다."));
        UserEntity user = userRepository.findById(post.getUser().getUserId())
                .orElseThrow(() -> new IllegalStateException("사용자가 아닙니다."));
        CommentEntity comment = new CommentEntity(dto.getComment(), post, user);
        commentRepository.save(comment);
        return new SaveCommentResponseDto(comment.getUser().getNickName(), comment.getComment(), comment.getCreatedAt());
    }

    //댓글 다건 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComment() {
        List<CommentEntity> comments = commentRepository.findAll();
        List<CommentResponseDto> dtos = new ArrayList<>();
        for(CommentEntity comment : comments){
            dtos.add(new CommentResponseDto(comment.getUser().getNickName(), comment.getComment(), comment.getCreatedAt(), comment.getUpdatedAt()));
        }
        return dtos;
    }

    //댓글 단건 조회
    @Transactional(readOnly = true)
    public CommentResponseDto findComment(Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("조회할 댓글 없음"));
        return new CommentResponseDto(comment.getUser().getNickName(), comment.getComment(), comment.getCreatedAt(), comment.getUpdatedAt());
    }

    //댓글 수정
    @Transactional
    public UpdateCommentResponseDto updateComment(Long commentId, AuthUser user, UpdateCommentRequestDto dto) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("수정할 댓글 없음"));
        if((!comment.getUser().getUserId().equals(user.getId())) //댓글 작성자
                ||(!comment.getPost().getUser().getUserId().equals(user.getId()))){//게시글 작성자
            throw new IllegalStateException("댓글 작성자 or 댓글의 게시글 작성자가 아님");
        }
        comment.update(dto.getComment());
        return new UpdateCommentResponseDto(comment.getUser().getNickName(), comment.getComment(), comment.getCreatedAt(), comment.getUpdatedAt());
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, AuthUser user) {
        CommentEntity comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new IllegalStateException("삭제할 댓글 없음"));
        if((!comment.getUser().getUserId().equals(user.getId()))//댓글 작성자
                ||(!comment.getPost().getUser().getUserId().equals(user.getId()))){//게시글 작성자
            throw new IllegalStateException("댓글 작성자 or 댓글의 게시글 작성자가 아님");
        }
        commentRepository.deleteById(comment.getId());
    }

}

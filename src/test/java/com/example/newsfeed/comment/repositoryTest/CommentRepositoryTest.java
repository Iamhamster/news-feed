package com.example.newsfeed.comment.repositoryTest;

import com.example.newsfeed.comment.entity.CommentEntity;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.post.entity.PostEntity;
import com.example.newsfeed.user.entity.UserEntity;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Mock
    private CommentEntity comment;
//    @Mock
//    private UserEntity user;
//    @Mock
//    private PostEntity post;

//    @Test
//    void 댓글을_저장할_수_있다() {
//        user = new UserEntity("asd@asd.com", "Test1234!23", "닉네임", "한윤희");
//        post = new PostEntity("댓글 제목", "댓글 내용", user, 1L);
//
//        comment = new CommentEntity("테스트 댓글", post, user);
//        ReflectionTestUtils.setField(comment, "id", 1L);
//        commentRepository.save(comment);
//
//        //when
//        Optional<CommentEntity> savedComment = commentRepository.findById(comment.getId());
//
//        //then
//        assertThat(savedComment).isPresent();
//        assertThat(savedComment.get().getComment()).isEqualTo("댓글 내용");
//    }
//
//    @Test
//    void 모든_댓글을_조회할_수_있다() {
//        //when
//        List<CommentEntity> comments = commentRepository.findAll();
//
//        //then
//        assertThat(comments).isNotEmpty();
//        assertThat(comments.get(0).getComment()).isEqualTo("댓글 내용");
//    }
//
//    @Test
//    void 댓글을_수정할_수_있다() {
//        //given
//        comment.update("수정된 댓글");
//        commentRepository.save(comment);
//
//        //when
//        Optional<CommentEntity> updatedComment = commentRepository.findById(comment.getId());
//
//        //then
//        assertThat(updatedComment).isPresent();
//        assertThat(updatedComment.get().getComment()).isEqualTo("수정된 댓글");
//    }

    @Test
    void 댓글을_삭제할_수_있다() {
        //when
        commentRepository.deleteById(comment.getId());
        Optional<CommentEntity> deletedComment = commentRepository.findById(comment.getId());

        //then
        assertThat(deletedComment).isEmpty();
    }
}

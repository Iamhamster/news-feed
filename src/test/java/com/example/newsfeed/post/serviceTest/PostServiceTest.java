package com.example.newsfeed.post.serviceTest;

import com.example.newsfeed.post.dto.*;
import com.example.newsfeed.post.entity.PostEntity;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.post.service.PostService;
import com.example.newsfeed.user.entity.UserEntity;
import com.example.newsfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void 게시물을_생생할_수_있다(){
        //given
        Long userId = 1L;
        Long postId = 1L;
        Long likeCount = 0L;
        UserEntity user = makeUserEntity(userId);
        PostEntity post = new PostEntity("게시글 제목", "게시글 내용", user, likeCount);
        ReflectionTestUtils.setField(post, "id", postId);
        SavePostRequestDto dto = new SavePostRequestDto("게시글 제목", "게시글 내용");
        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        //when
        SavePostResponseDto savePost = postService.savePost(userId, dto);

        //then
        assertEquals(post.getTitle(), savePost.getTitle());
        assertEquals(post.getContent(), savePost.getContent());
    }



    //게시물 다건 조회
    @Test
    public void 모든_게시물을_조회할_수_있다() {
        // given
        Long userId = 1L;
        UserEntity user = makeUserEntity(userId);
        List<PostResponseDto> dtos = new ArrayList<>();
        dtos.add(new PostResponseDto(
                "닉네임",
                "게시글 제목",
                "게시글 내용",
                0L,
                LocalDateTime.of(2024,3,3,9, 0, 0),
                LocalDateTime.of(2024,3,3,21, 0, 0)
        ));
        List<PostEntity> postEntitys = new ArrayList<>();
        for (PostResponseDto dto : dtos) {
            postEntitys.add(new PostEntity(
                    dto.getTitle(),
                    dto.getContent(),
                    user,
                    dto.getLikeCount()
                )
            );
        }
        Pageable pageable= PageRequest.of(0, 10);
        String startDate=null;
        String endDate=null;
        //Page<PostEntity> postPage = new PageImpl<>(postEntitys);

        given(postRepository.findAll()).willReturn(postEntitys);

        // when
        Page<PostResponseDto> result = postService.findAllPost(pageable, startDate, endDate);

        // then
        assertEquals(dtos.size(), result.getSize());
        assertEquals(dtos.get(0).getTitle(), result.get());
        assertEquals(dtos.get(0).getContent(), result.get());
    }


    //게시물 단건 조회
    @Test
    void 게시물을_조회할_수_있다(){
        //given
        Long postId = 1L;
        Long likeCount = 0L;
        UserEntity user = new UserEntity("asd@asd.com", "Test1234!23", "닉네임", "한윤희");
        PostEntity post = new PostEntity("게시글 제목", "게시글 내용", user, likeCount);
        ReflectionTestUtils.setField(post, "id", postId);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        //when
        PostResponseDto dto = postService.findPost(postId);

        //then
        assertEquals("게시글 제목", dto.getTitle());
        assertEquals("게시글 내용", dto.getContent());
        assertEquals(likeCount, dto.getLikeCount());
    }



    //게시물 수정
    @Test
    void 게시물을_수정할_수_있다(){
        //given
        Long userId = 1L;
        Long postId = 1L;
        Long likeCount = 0L;
        UserEntity user = new UserEntity("asd@asd.com", "Test1234!23", "닉네임", "한윤희");
        PostEntity post = new PostEntity("게시글 제목", "게시글 내용", user, likeCount);
        ReflectionTestUtils.setField(user, "userId", userId);
        ReflectionTestUtils.setField(post, "id", postId);
        UpdatePostRequestDto dto = new UpdatePostRequestDto("게시글 제목2", "게시글 내용2", 1L);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        //when
        UpdatePostResponseDto result = postService.updatePost(userId, postId, dto);

        //then
        assertEquals("게시글 제목2", result.getTitle());
        assertEquals("게시글 내용2", result.getContent());
        assertEquals(likeCount, result.getLikeCount());
    }



    //게시물 삭제
    @Test
    public void 가게를_폐업한다() {
        // given
        Long postId = 1L;
        doNothing().when(postRepository).deleteById(anyLong());

        // when
        postService.deletePost(postId);

        // then
        verify(postRepository, times(1)).deleteById(postId);//메서드 호출 여부 확인
    }



    /*todo: 팔로우 부분*/
    //팔로우 리스트


    //"게시물을_생생할_수_있다"에 사용
    private UserEntity makeUserEntity(Long userId) {
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "userId", userId);
        ReflectionTestUtils.setField(user, "email", "asd@asd.com");
        ReflectionTestUtils.setField(user, "password", "Test1234!23");
        ReflectionTestUtils.setField(user, "nickName", "닉네임");
        ReflectionTestUtils.setField(user, "name", "한윤희");
        ReflectionTestUtils.setField(user, "profile", "프로필");
        ReflectionTestUtils.setField(user, "followerUsers", 0);
        ReflectionTestUtils.setField(user, "followingUsers", 0);
        return user;
    }
}

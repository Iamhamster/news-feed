package com.example.newsfeed.post.controllerTest;

import com.example.newsfeed.post.controller.PostController;
import com.example.newsfeed.post.dto.*;
import com.example.newsfeed.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @Test
    void 게시물을_생성() throws Exception {
        //given
        Long userId = 1L;
        Long postId = 1L;
        Long likeCount = 3L;
        SavePostRequestDto requestDto = new SavePostRequestDto("게시글 제목", "게시글 내용");
        SavePostResponseDto responseDto = new SavePostResponseDto(
                userId,
                postId,
                requestDto.getTitle(),
                likeCount,
                requestDto.getContent(),
                LocalDateTime.now()
        );

        given(postService.savePost(anyLong(), any())).willReturn(responseDto);

        System.out.println("test: "+objectMapper.writeValueAsString(requestDto));

        //when&then
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
//                .andExpect(jsonPath("$.content").value(responseDto.getContent()))
//                .andExpect(jsonPath("$.id").value(responseDto.getPostId()))
//                .andExpect(jsonPath("$.likeCount").value(responseDto.getLikeCount()))
//                .andExpect(jsonPath("$.createdAt").exists());
    }



    @Test
    void 게시물을_모두_조회() throws Exception {
        //given
        Long likeCount = 3L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        String startDate = null;
        String endDate = null;
        List<PostResponseDto> postList = new ArrayList<>();
        postList.add(new PostResponseDto("닉네임", "게시글 제목", "게시글 내용", likeCount, LocalDateTime.now(), LocalDateTime.now()));
        Page<PostResponseDto> pageResponse = new PageImpl<>(postList, pageable, 1);

        given(postService.findAllPost(pageable, startDate, endDate)).willReturn(pageResponse);

        //when&then
        mockMvc.perform(get("/api/v1/posts/create")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("게시글 제목"))
                .andExpect(jsonPath("$[0].content").value("게시글 내용"))
                .andExpect(jsonPath("$[0].likeCount").value(likeCount))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").exists());
    }
}

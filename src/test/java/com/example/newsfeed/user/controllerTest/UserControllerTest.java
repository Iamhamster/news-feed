package com.example.newsfeed.user.controllerTest;

import com.example.newsfeed.user.controller.UserController;
import com.example.newsfeed.user.dto.*;
import com.example.newsfeed.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void 비밀번호_수정() throws Exception {
        //given
        Long userId = 1L;
        String password = "Test1234!23";
        String newPassword = "Test123!123";
        UpdatePasswordUserRequestDto dto = new UpdatePasswordUserRequestDto(
                password,
                newPassword
        );

        BDDMockito.given(userService.updatePasswordUser(anyLong(), any())).willReturn(new UpdatePasswordResponseDto(password));

        //when&then
        mockMvc.perform(patch("/api/v1/auths/password/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value(password));
                //.andReturn().getResponse().getContentAsString();
    }



    @Test
    void 프로필_생성() throws Exception {
        //given
        SaveUserRequestDto dto = new SaveUserRequestDto("프로필");
        given(userService.saveUser(any())).willReturn(new SaveUserResponseDto(dto.getProfile()));

        //whan&then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile").value(dto.getProfile()));
    }



    @Test
    void 프로필_조회() throws Exception{
        //given
        Long userId = 1L;
        UserResponseDto dto = new UserResponseDto("프로필");
        given(userService.findUser(anyLong())).willReturn(dto);

        //whan&then
        mockMvc.perform(get("/api/v1/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile").value(dto.getProfile()));
    }



    @Test
    void 프로필_수정() throws Exception{
        //given
        Long userId = 1L;
        UpdateUserResponseDto dto = new UpdateUserResponseDto("프로필");

        given(userService.updateUser(anyLong(), any())).willReturn(dto);

        //whan&then
        mockMvc.perform(put("/api/v1/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile").value(dto.getProfile()));
    }
}

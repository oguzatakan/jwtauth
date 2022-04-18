package com.atakanoguz.jwtauth.controller;

import com.atakanoguz.jwtauth.entity.User;
import com.atakanoguz.jwtauth.repository.UserRepository;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    private final Faker faker = new Faker();

    @Autowired
    private UserRepository userRepository;

    private String generateFakeString(int length) {

        int leftLimit = 97;
        int rightLimit = 122;

        Random random = new Random();

        return random.ints(leftLimit, rightLimit-1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    //Begin of get request "/users" url tests

    @Test
    @DisplayName("Can '/users:GET' access without authentication, Expected: UnAuthorized")
    public void getUsersWithoutAuthentication() throws Exception {

        var mvcResult = mvc.perform(get("/users"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.httpStatus").value(UNAUTHORIZED.name()))
                .andExpect(jsonPath("$.message")
                        .value("Invalid username or password!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("Can '/users:GET' accessible with 'ROLE_USER', Expected Forbidden")
    public void getUsersWithUserRole() throws Exception {

        var mvcResult = mvc.perform(get("/users"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.httpStatus").value(FORBIDDEN.name()))
                .andExpect(jsonPath("$.message").value("Access to this resource on the server is denied!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType()).isEqualTo("application/json;charset=UTF-8");

    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("Can '/users:GET' get all records with 'ROLE_ADMIN', Expected Ok")
    public void getUsers() throws Exception {

        var mvcResult = mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").isNumber())
                .andExpect(jsonPath("$.[0].userName").isString())
                .andExpect(jsonPath("$.[0].fullName").isString())
                .andExpect(jsonPath("$.[0].email").isString())
                .andExpect(jsonPath("$.[0].createdDate").isString())
                .andExpect(jsonPath("$.[0].roles").isArray())
                .andExpect(jsonPath("$.[0].roles[0].id").isNumber())
                .andExpect(jsonPath("$.[0].roles[0].name").isString())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    //End of get request '/users' url tests

    //Begin of get request '/users/*/' url tests

    @Test
    @DisplayName("Can '/users/*/GET' access without authentication, Expected: UnAuthorized")
    public void getUserWithoutAuthentication() throws Exception {

        var mvcResult = mvc.perform(get("/users/user"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.httpStatus").value(UNAUTHORIZED.name()))
                .andExpect(jsonPath("$.message")
                        .value("Invalid username or password!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_USER"})
    @DisplayName("Can '/users/*/:GET' accessible with 'ROLE_USER', Expected BAD_REQUEST")
    public void getUserWithOtherUser() throws Exception {

        var mvcResult = mvc.perform(get("users/user"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatus").value(BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message")
                        .value("You are not authorized to perform this action!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo("application/json;charset=UTF-8");

    }













}

package com.shopsupport.supportservice.services;

import com.shopsupport.supportservice.dto.CredentialDto;
import com.shopsupport.supportservice.entities.User;
import com.shopsupport.supportservice.dto.UserDto;
import com.shopsupport.supportservice.exceptions.AppException;
import com.shopsupport.supportservice.mappers.UserMapper;
import com.shopsupport.supportservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;



}
package net.javaguides.springboot.service;

import net.javaguides.springboot.dto.AdminAddUserRequestDto;
import net.javaguides.springboot.dto.AdminUpdateUserRequestDto;
import net.javaguides.springboot.dto.ChangePasswordRequestDto;
import net.javaguides.springboot.dto.UserListResponseDto;
import net.javaguides.springboot.dto.LoginResponseDto;
import net.javaguides.springboot.dto.UserLoginDto;
import net.javaguides.springboot.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(UserRegistrationDto dto);
    LoginResponseDto loginUser(UserLoginDto dto);
    void addUserByAdmin(AdminAddUserRequestDto dto);
    List<UserListResponseDto> getAllNonAdminUsers();
    void approveUser(Integer id);
    void rejectUser(Integer id);
    AdminUpdateUserRequestDto getUserById(Integer id); // for prefill
    void updateUser(Integer id, AdminUpdateUserRequestDto dto);
    void deleteUser(Integer id);
    void changePassword(ChangePasswordRequestDto dto);
    Optional<Integer> getUserIdByEmail(String email);
}

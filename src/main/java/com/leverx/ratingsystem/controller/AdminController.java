package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.dto.user.AdminUserDto;
import com.leverx.ratingsystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/pending-comments")
    public ResponseEntity<List<AdminCommentDto>> getPendingComments() {
        var pendingComments = adminService.getPendingComments();
        return new ResponseEntity<>(pendingComments, HttpStatus.OK);
    }

    @PostMapping("/pending-comments/{id}/approve")
    public ResponseEntity<Void> approveComment(@PathVariable UUID id) {
        adminService.approveComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/pending-comments/{id}/reject")
    public ResponseEntity<Void> rejectComment(@PathVariable UUID id) {
        adminService.rejectComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/pending-users")
    public ResponseEntity<List<AdminUserDto>> getPendingUsers() {
        var pendingUsers = adminService.getPendingUsers();
        return new ResponseEntity<>(pendingUsers, HttpStatus.OK);
    }

    @PostMapping("/pending-users/{id}/approve")
    public ResponseEntity<Void> approveUser(@PathVariable UUID id) {
        adminService.approveUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/pending-users/{id}/reject")
    public ResponseEntity<Void> rejectUser(@PathVariable UUID id) {
        adminService.rejectUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

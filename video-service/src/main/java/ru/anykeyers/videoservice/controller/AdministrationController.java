package ru.anykeyers.videoservice.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.service.AdministrationService;
import ru.anykeyers.videoservice.service.impl.AdministrationServiceImpl;

import java.util.List;

/**
 * Контроллер для администрирования
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdministrationController {

    private final AdministrationService administrationService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return administrationService.getAllUsers();
    }

    @PostMapping("/block/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User blockUser(@PathVariable Long id) {
        return administrationService.blockUser(id);
    }

    @DeleteMapping("/delete/video/{uuid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteVideo(@PathVariable String uuid) {
        administrationService.deleteVideo(uuid);
    }

}

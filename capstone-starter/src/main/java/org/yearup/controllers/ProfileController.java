package org.yearup.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@PreAuthorize("permitAll()")
public class ProfileController {

    private ProfileDao profileDao;
    private UserDao userDao;

    // constructor
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @GetMapping("/profile")
    public Profile getProfile(Principal principal) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);

        return profileDao.getUserId(user.getId());

    }

    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public void updateProfile(@RequestBody Profile updatedProfile, Principal principal) {

        String userName = principal.getName();
        User user = userDao.getByUserName(userName);

        profileDao.update(user.getId(), updatedProfile);

    }


}

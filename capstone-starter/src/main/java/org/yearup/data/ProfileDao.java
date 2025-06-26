package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    Profile getUserId(int id);

    void update(int id, Profile updatedProfile);
}

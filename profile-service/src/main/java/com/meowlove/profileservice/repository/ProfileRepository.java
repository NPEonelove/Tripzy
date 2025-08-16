package com.meowlove.profileservice.repository;

import com.meowlove.profileservice.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    boolean existsProfileByUsername(String username);

    Optional<Profile> findProfileByProfileId(UUID profileId);

    Optional<Profile> findProfileByUserId(UUID userId);

    Boolean existsProfileByUserId(UUID userId);
}

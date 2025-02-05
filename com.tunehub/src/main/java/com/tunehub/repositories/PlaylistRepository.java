package com.tunehub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.entities.Playlist;
import java.util.List;
import com.tunehub.entities.Song;


public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
}

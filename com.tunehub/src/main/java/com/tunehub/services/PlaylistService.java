package com.tunehub.services;

import java.util.List;
import java.util.Optional;

import com.tunehub.entities.Playlist;

public interface PlaylistService {

	public void addPlaylist(Playlist playlist);

	public List<Playlist> fetchAllPlaylists();

}

package com.tunehub.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tunehub.entities.Playlist;
import com.tunehub.entities.Song;
import com.tunehub.repositories.PlaylistRepository;
import com.tunehub.services.PlaylistService;
import com.tunehub.services.SongService;

@RestController
public class PlayListController {
	@Autowired
	SongService songService;

	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	PlaylistRepository repo;

	

	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		playlistService.addPlaylist(playlist);
		List<Song> songList = playlist.getSongs();
		for (Song s : songList) {
			s.getPlaylist().add(playlist);
			songService.updateSong(s);
			// update the song object in db..
		}
		return "adminHome";
	}
	

	
	@GetMapping("/viewPlaylists")
	public String viewSongs(Model model) {
		List<Playlist> playlists = playlistService.fetchAllPlaylists();
		model.addAttribute("playlists", playlists);
		return "displayPlaylist";

	}
	

	@GetMapping("/playlist/{id}")
	public List<Song> showSongsOfPlaylist(@PathVariable int id, Model model) {
	
		  Optional<Playlist> playlistOptional = repo.findById(id);
			Playlist playlist = playlistOptional.get();
			List<Song> songs = playlist.getSongs();
			System.out.println(songs);
			
			List<Song> songs2 = new ArrayList<>();
			for (Song song : songs) {
				Song s = new Song();
				s.setName(song.getName());
				s.setArtist(song.getArtist());
				s.setGenre(song.getGenre());
				s.setLink(song.getLink());
				s.setImage(song.getImage());
				songs2.add(s);
			}
			
	        return songs2;	
		
	}
}

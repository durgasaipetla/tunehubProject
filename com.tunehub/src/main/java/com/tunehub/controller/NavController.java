package com.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tunehub.entities.Song;
import com.tunehub.services.SongService;

@Controller


public class NavController {
	@Autowired
	SongService songService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	@GetMapping("/overview")
	public String overview() {
		return "overview";
	}
	
	@GetMapping("/newSong")
	public String newSong() {
		return "newSong";
	}
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
	}
}

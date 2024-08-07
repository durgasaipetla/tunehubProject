package com.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tunehub.entities.Playlist;
import com.tunehub.entities.Song;
import com.tunehub.entities.Users;
import com.tunehub.services.PlaylistService;
import com.tunehub.services.SongService;
import com.tunehub.services.UsersService;

import jakarta.servlet.http.HttpSession;




@Controller
public class UsersController {
	@Autowired 
	UsersService service;
	@Autowired
	PlaylistService playlistservice;
	@Autowired
	SongService songservice;
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user, Model model) {
		boolean user_status = service.emailExists(user.getEmail());
		
		String msg;
		if (user_status == false) {
			msg = service.addUser(user);
			System.out.println("user added successfully");
		} else {
			msg = "user already exists";
			System.out.println("user already exists");
		}
		model.addAttribute("msg", msg);

		return "home";
	}

	@SuppressWarnings("unused")
	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session) {

		String msg;
		Users user = service.getUser(email);
		boolean userStatus = user.isPremium();
		
		if (user == null) {
			throw new IllegalStateException("User must not be null");
			}
		
		if  (service.validateUser(email, password) == true) {
			String role = service.getRole(email);
			
			session.setAttribute("email", email);
			if (role.equals("admin")) {
				List<Playlist> playlists = playlistservice.fetchAllPlaylists();
				model.addAttribute("playlists",playlists);
				List<Song> songsList = songservice.fetchAllSongs();
				model.addAttribute("songs",songsList);
				return "adminHome";
			}
			
			else if( userStatus && role.equals("customer")){
//				Users user = service.getUser(email);
//				boolean userStatus = user.isPremium();
				model.addAttribute("isPremium", userStatus);
				List<Playlist> playlists = playlistservice.fetchAllPlaylists();
				model.addAttribute("playlists",playlists);
				List<Song> songsList = songservice.fetchAllSongs();
				model.addAttribute("songs",songsList);
				return "premiumHome";
			}
			else {
				return "customerHome";
				
			}

		} else {
			msg = "Email is not register";
			model.addAttribute("msg", msg);
			return "login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

}

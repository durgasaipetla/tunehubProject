package com.tunehub.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Song {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
int id;
String name;
String artist;
String genre;
String link;
String image;
@ManyToMany
List<Playlist> playlist;
public Song(int id, String name, String artist, String genre, String link, String image, List<Playlist> playlist) {
	super();
	this.id = id;
	this.name = name;
	this.artist = artist;
	this.genre = genre;
	this.link = link;
	this.image= image;
	this.playlist = playlist;

}

public Song() {
	super();
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getArtist() {
	return artist;
}
public void setArtist(String artist) {
	this.artist = artist;
}
public String getGenre() {
	return genre;
}
public void setGenre(String genre) {
	this.genre = genre;
}
public String getLink() {
	return link;
}
public void setLink(String link) {
	this.link = link;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public List<Playlist> getPlaylist() {
	return playlist;
}

@Override
public String toString() {
	return "Song [id=" + id + ", name=" + name + ", artist=" + artist + ", genre=" + genre + ", link=" + link
			+ ", image=" + image + "]";
}

}



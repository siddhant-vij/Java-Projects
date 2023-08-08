package p0Projects;

import java.util.ArrayList;
import java.util.LinkedList;

class Song {
  private String title;
  private double duration;

  public Song(String title, double duration) {
    this.title = title;
    this.duration = duration;
  }

  public String getTitle() {
    return this.title;
  }

  @Override
  public String toString() {
    return this.title + ": " + this.duration;
  }
}

class Album {
  private String name;
  private String artist;
  private ArrayList<Song> songs;

  public Album(String name, String artist) {
    this.name = name;
    this.artist = artist;
    this.songs = new ArrayList<>();
  }

  private Song findSong(String title) {
    ArrayList<String> songNames = new ArrayList<>();
    for (Song song : this.songs) {
      songNames.add(song.getTitle());
    }
    return songNames.contains(title) ? this.songs.get(songNames.indexOf(title)) : null;
  }

  public boolean addSong(String title, double duration) {
    if (findSong(title) == null) {
      Song newSong = new Song(title, duration);
      this.songs.add(newSong);
      return true;
    } else {
      return false;
    }
  }

  public boolean addToPlayList(int track, LinkedList<Song> playlist) {
    if (track <= 0 || track > songs.size()) {
      System.out.println("This album: \"" + this.name + "\" by " + this.artist + "- does not have a track " + track);
      return false;
    }
    String title = this.songs.get(track - 1).getTitle();
    return addToPlayList(title, playlist);
  }

  public boolean addToPlayList(String title, LinkedList<Song> playlist) {
    Song song = findSong(title);
    if (song != null) {
      playlist.add(song);
      return true;
    } else {
      System.out.println("The song " + title + " is not in this album: \"" + this.name + "\" by " + this.artist);
      return false;
    }
  }
}

class AlbumInnerClass {
  private String name;
  private String artist;
  private SongList songs;

  public AlbumInnerClass(String name, String artist) {
    this.name = name;
    this.artist = artist;
    this.songs = new SongList();
  }

  public boolean addSong(String title, double duration) {
    if (this.songs.findSong(title) == null) {
      Song newSong = new Song(title, duration);
      this.songs.add(newSong);
      return true;
    } else {
      return false;
    }
  }

  public boolean addToPlayList(int track, LinkedList<Song> playlist) {
    Song song = this.songs.findSong(track-1);
    if (song != null) {
      playlist.add(song);
      return true;
    } else {
      System.out.println("This album: \"" + this.name + "\" by " + this.artist + "- does not have a track " + track);
      return false;
    }
  }

  public boolean addToPlayList(String title, LinkedList<Song> playlist) {
    Song song = this.songs.findSong(title);
    if (song != null) {
      playlist.add(song);
      return true;
    } else {
      System.out.println("The song " + title + " is not in this album: \"" + this.name + "\" by " + this.artist);
      return false;
    }
  }

  private class SongList {
    private ArrayList<Song> songs;

    private SongList() {
      this.songs = new ArrayList<>();
    }

    private boolean add(Song song) {
      if (this.songs.contains(song)) {
        return false;
      } else {
        this.songs.add(song);
        return true;
      }
    }

    private Song findSong(String title) {
      ArrayList<String> songNames = new ArrayList<>();
      for (Song song : this.songs) {
        songNames.add(song.getTitle());
      }
      return songNames.contains(title) ? this.songs.get(songNames.indexOf(title)) : null;
    }

    private Song findSong(int track) {
      if (track <= 0 || track > this.songs.size()) {
        return null;
      }
      String title = this.songs.get(track).getTitle();
      return findSong(title);
    }

  }
}

public class SongPlaylistFromAlbum {
  public static void main(String[] args) {
    ArrayList<Album> albums = new ArrayList<>();
    ArrayList<AlbumInnerClass> albumsIC = new ArrayList<>();

    Album album = new Album("Stormbringer", "Deep Purple");
    album.addSong("Stormbringer", 4.6);
    album.addSong("Love don't mean a thing", 4.22);
    album.addSong("Holy man", 4.3);
    album.addSong("Hold on", 5.6);
    album.addSong("Lady double dealer", 3.21);
    album.addSong("You can't do it right", 6.23);
    album.addSong("High ball shooter", 4.27);
    album.addSong("The gypsy", 4.2);
    album.addSong("Soldier of fortune", 3.13);
    albums.add(album);

    AlbumInnerClass albumIC = new AlbumInnerClass("Stormbringer", "Deep Purple");
    albumIC.addSong("Stormbringer", 4.6);
    albumIC.addSong("Love don't mean a thing", 4.22);
    albumIC.addSong("Holy man", 4.3);
    albumIC.addSong("Hold on", 5.6);
    albumIC.addSong("Lady double dealer", 3.21);
    albumIC.addSong("You can't do it right", 6.23);
    albumIC.addSong("High ball shooter", 4.27);
    albumIC.addSong("The gypsy", 4.2);
    albumIC.addSong("Soldier of fortune", 3.13);
    albumsIC.add(albumIC);

    album = new Album("For Those About To Rock", "AC/DC");
    album.addSong("For those about to rock", 5.44);
    album.addSong("I put the finger on you", 3.25);
    album.addSong("Lets go", 3.45);
    album.addSong("Inject the venom", 3.33);
    album.addSong("Snowballed", 4.51);
    album.addSong("Evil walks", 3.45);
    album.addSong("C.O.D.", 5.25);
    album.addSong("Breaking the rules", 5.32);
    album.addSong("Night of the long knives", 5.12);
    albums.add(album);

    albumIC = new AlbumInnerClass("For Those About To Rock", "AC/DC");
    albumIC.addSong("For those about to rock", 5.44);
    albumIC.addSong("I put the finger on you", 3.25);
    albumIC.addSong("Lets go", 3.45);
    albumIC.addSong("Inject the venom", 3.33);
    albumIC.addSong("Snowballed", 4.51);
    albumIC.addSong("Evil walks", 3.45);
    albumIC.addSong("C.O.D.", 5.25);
    albumIC.addSong("Breaking the rules", 5.32);
    albumIC.addSong("Night of the long knives", 5.12);
    albumsIC.add(albumIC);

    LinkedList<Song> playList = new LinkedList<Song>();
    albums.get(0).addToPlayList("You can't do it right", playList);
    albums.get(0).addToPlayList("Holy man", playList);
    albums.get(0).addToPlayList("Speed king", playList); // Does not exist
    albums.get(0).addToPlayList(9, playList);
    albums.get(1).addToPlayList(3, playList);
    albums.get(1).addToPlayList(2, playList);
    albums.get(1).addToPlayList(24, playList); // There is no track 24

    albumsIC.get(0).addToPlayList("You can't do it right", playList);
    albumsIC.get(0).addToPlayList("Holy man", playList);
    albumsIC.get(0).addToPlayList("Speed king", playList); // Does not exist
    albumsIC.get(0).addToPlayList(9, playList);
    albumsIC.get(1).addToPlayList(3, playList);
    albumsIC.get(1).addToPlayList(2, playList);
    albumsIC.get(1).addToPlayList(24, playList); // There is no track 24
  }
}

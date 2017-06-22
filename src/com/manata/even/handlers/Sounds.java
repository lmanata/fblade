package com.manata.even.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sounds {

	private static HashMap<String,Music> musics;
	private static Music playing;
	public static boolean musicloop;
	
	static {
		musics = new HashMap<String,Music>();
	}
	
	public static void load(String path, String name){
		Music music = Gdx.audio.newMusic(Gdx.files.internal(path));
		musics.put(name,music);
	}
	
	public static void play(String name){
		musics.get(name).play();
		playing = musics.get(name);
		playing.setVolume(0.4f);
	}
	
	public static void loop(String name){
		musics.get(name).setLooping(musicloop);
		playing = musics.get(name);
	}
	
	public static void stop(String name){
		musics.get(name).stop();
	}
	
	public static void stopall(){
		for(Music music : musics.values()){
			music.stop();
		}
	}
}
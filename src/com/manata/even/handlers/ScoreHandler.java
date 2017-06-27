package com.manata.even.handlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScoreHandler {
	private static final Path SCORE_FILE_PATH = FileSystems.getDefault().getPath("res", "Scores.txt");
	
	private ArrayList<Float> scores = new ArrayList<Float>();

	public ScoreHandler() {
		// TODO make this better
		File f = new File("res/Scores.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		read();
	}

	public void add(float score) {
		this.scores.add(score);
	}
	
	///TODO Write this better
	public void write() {
		try {
			FileWriter filewriter = new FileWriter("res/Scores.txt", true);
			BufferedWriter output = new BufferedWriter(filewriter);
			for (float x: scores) {
				output.write(String.format("%f\n", x));
			}

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void read() {

		try(Stream<String> lines = Files.lines(SCORE_FILE_PATH)) {
			
			lines
			.filter(i -> !i.equals(""))
			.map((line) -> {
				return Float.parseFloat(line);
			})
			.collect(Collectors.toCollection(() -> this.scores));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public float getTopScore() {
		return this.scores.stream().sorted().findFirst().orElse(0.0f);
	}

	public Float[] getSortedScores() {
		return this.scores.stream().sorted().toArray(Float[]::new);
	}
}
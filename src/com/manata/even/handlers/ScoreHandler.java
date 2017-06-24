package com.manata.even.handlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreHandler {

	public FileWriter filewriter;
	public BufferedWriter output;
	public String filename = "res/Scores.txt";
	public File file;
	public ArrayList<String> scores = new ArrayList<String>();
	private boolean error = false;
	public float topscore = 0;

	public ScoreHandler() {
		init();
	}

	public void init() {
		read();
	}

	public void print(String score) {
		try {
			filewriter = new FileWriter(filename, true);
			output = new BufferedWriter(filewriter);
			output.newLine();
			output.write(score);
			output.close();
		} catch (IOException e) {
			error = true;
			e.printStackTrace();
		}
	}

	public void read() {

		FileReader filereader = null;
		try {
			filereader = new FileReader("res/Scores.txt");
			error = false;
		} catch (FileNotFoundException e) {
			error = true;
			e.printStackTrace();
		}
		if (!error) {
			Scanner scanner = new Scanner(filereader);
			while (scanner.hasNextLine()) {
				String toadd = scanner.nextLine().split(";")[0];
				Float checkscore = Float.parseFloat(toadd);
				scores.add(toadd);
				if (checkscore > topscore)
					topscore = checkscore;
			}
			scanner.close();
		} else {
			try {
				file = new File(filename);
				filewriter = new FileWriter(filename);
				output = new BufferedWriter(filewriter);
				output.write("0;");
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public float[] getScores(ArrayList<String> list) {
		float r[] = new float[list.size() - 1];
		int counter = 0;
		for (String s : list) {
			if (counter == list.size() - 1)
				break;
			float toadd = Float.parseFloat(s);
			r[counter] = toadd;
			counter++;
		}
		;

		float lista[] = order(r);

		return lista;

	}

	public float[] order(float[] list) {
		float temp;
		for (int i = 0; i < list.length - 1; i++) {
			for (int j = 1; j < list.length - i; j++) {
				if (list[j - 1] < list[j]) {
					temp = list[j - 1];
					list[j - 1] = list[j];
					list[j] = temp;
				}
			}
		}
		return list;
	}
}
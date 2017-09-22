package de.factfinder.export;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.factfinder.export.io.ExportOrchestrator;

public class ExportMain {

	public static void main(String[] args) throws IOException {
		if (args.length == 3) {
			ExportOrchestrator.runExport(args[0], args[1], args[2]);
		} else {
			String inputBaseDir;
			String outputBaseDir;
			String baseUrl;
			try {
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Please enter input directory containing markdown files: ");
				inputBaseDir = input.readLine();
				if ("q".equals(inputBaseDir)) {
					System.out.println("Exit!");
					System.exit(0);
				}
				System.out.println("Please enter output directory for csv files: ");
				outputBaseDir = input.readLine();
				if ("q".equals(outputBaseDir)) {
					System.out.println("Exit!");
					System.exit(0);
				}
				System.out.println("Please enter base url: ");
				baseUrl = input.readLine();
				if ("q".equals(outputBaseDir)) {
					System.out.println("Exit!");
					System.exit(0);
				}
				ExportOrchestrator.runExport(inputBaseDir, outputBaseDir, baseUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

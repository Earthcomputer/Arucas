package me.senseiwells;

import me.senseiwells.arucas.api.ContextBuilder;
import me.senseiwells.arucas.api.docs.parser.JsonParser;
import me.senseiwells.arucas.utils.ArgumentParser;
import me.senseiwells.arucas.utils.Context;
import me.senseiwells.impl.wrappers.ArucasTestWrapper;
import me.senseiwells.impl.wrappers.ChildWrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
	public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
		ContextBuilder builder = new ContextBuilder()
			.setDisplayName("System.in")
			.addDefault()
			.generateArucasFiles();

		Context context = builder.build();

		ArgumentParser parser = new ArgumentParser()
			.addArgument("-noformat", c -> c.getOutput().setFormatting("", "", ""))
			.addArgument("-debug", c -> c.setDebug(true))
			.addArgument("-genjson", c -> JsonParser.of(builder).write(() -> c.getImportPath().getParent().resolve("docs").resolve("AllDocs.json")));
		parser.parse(context, args);

		context.getOutput().println("Welcome to Arucas Interpreter");
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		while (running) {
			System.out.print("\n>> ");

			String line = scanner.nextLine().trim();
			switch (line) {
				case "" -> {
					continue;
				}
				case "quit", "exit" -> {
					running = false;
					continue;
				}
			}
			if (line.endsWith(".arucas")) {
				try {
					line = Files.readString(Path.of(line));
				}
				catch (Exception e) {
					context.getOutput().logError("Could not read file: \n" + e);
					continue;
				}
			}

			context.getThreadHandler().runOnMainThreadFuture(context, "System.in", line).get();
		}
	}
}

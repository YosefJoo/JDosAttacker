package com.anas.jdosattacker.args;

import com.anas.jdosattacker.MainController;
import com.anas.jdosattacker.request.Requester;
import org.apache.commons.cli.*;

public class ArgumentsProcessor {
    private Options options;
    private CommandLineParser parser;

    public ArgumentsProcessor() {
        initialize();
        setupOptions();
    }

    private void setupOptions() {
        options.addOption("h", "help", false, "Print this help");
        options.addOption("v", "version", false, "Print version");
        options.addOption("u", "url", true, "URL to attack");
        options.addOption("t", "threads", true, "Number of threads to use");
        options.addOption("n", "number", true, "Number of requests to send from each thread");
        options.addOption("useragent", true, "User-Agent to use");
        options.addOption("requestMethod", true, "Request method to use (default: GET)");
        options.addOption("connectTimeout", true, "Connection timeout (default: 5000)");
    }

    private void initialize() {
        options = new Options();
        parser = new DefaultParser();
    }

    public void process(String[] args) {
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("help")) {
                printHelp();
            } else if (commandLine.hasOption("version")) {
                printVersion();
            } else if (commandLine.hasOption("url"))
                Requester.setUrl(commandLine.getOptionValue("url"));

            if (commandLine.hasOption("threads"))
                MainController.setThreadsNum(Integer.parseInt(commandLine.getOptionValue("threads")));
            if (commandLine.hasOption("number"))
                Requester.setReqNumber(Integer.parseInt(commandLine.getOptionValue("number")));
            if (commandLine.hasOption("connectTimeout"))
                Requester.setConnectTimeout(Integer.parseInt(commandLine.getOptionValue("connectTimeout")));
            if (commandLine.hasOption("useragent"))
                Requester.setUserAgent(commandLine.getOptionValue("useragent"));
            if (commandLine.hasOption("requestMethod"))
                Requester.setRequestMethod(commandLine.getOptionValue("requestMethod"));

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            printHelp();
            System.exit(1);
        }
    }

    private void printVersion() {
        System.out.println("Version: " + MainController.version);
        System.exit(0);
    }

    private void printHelp() {
        new HelpFormatter().printHelp("java -jar jdosattacker.jar -u <URL>", options);
    }
}

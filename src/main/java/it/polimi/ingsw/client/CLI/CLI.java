package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.Communicator;
import it.polimi.ingsw.client.CommunicatorRMI;
import it.polimi.ingsw.client.CommunicatorSocket;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.CLIView;

import java.util.*;

public class CLI {

    private Scanner in;
    private Communicator communicator;
    private String username;
    private CLIView view;
    public static final String DEFAULT_SERVER = "localhost";
    public static final String DEFAULT_SERVER_SOCKET_PORT = "3001";
    public static final String DEFAULT_SERVER_RMI_PORT = "1099";
    private static final String INPUT_STREAM_SEPARATOR_SYMBOL = " ";
    private static final String MESSAGE_SEPARATOR_SYMBOL = "/";

    /**
     * creates a new {@link CLI}.
     * it does that initialising a {@link CLIView}, a {@link Scanner} and the field {@link #username}
     */
    public CLI() {
        view = new CLIView();
        this.in = new Scanner(System.in);
        this.username = new String();
    }

    /**
     * allows the user to chose the network technology to communicate with the server.
     * It does that by asking within commandline whether to communicate by socket or rmi and starts the corresponding
     * method: {@link #startSocket()} , {@link #startRMI()}
     */
    public void startCLI() {
        println("What communication technology do you want to use? " +
                "(type \"RMI\" for RMI  and \"socket\" for socket ...)");
        boolean temp = true;
        while (temp) {
            String mode = in.nextLine();
            mode = mode.toLowerCase();
            switch (mode) {
                case "rmi":
                    temp = false;
                    startRMI();
                    break;
                case "socket":
                    temp = false;
                    startSocket();
                    break;
                default:
                    println("Wrong input...\n");
                    println("What communication technology do you want to use? " +
                            "(type \"RMI\" for RMI  and \"socket\" for socket ...)");
            }
        }
        login();
    }

    /**
     * starts client with RMI technology initialising a {@link CommunicatorRMI}
     */
    private void startRMI() {
        communicator = new CommunicatorRMI(view);
        boolean temp = true;
        String server = new String();
        String port = new String();
        while (temp) {
            println("Insert server IP (leave it blank for default: " + DEFAULT_SERVER + ")");
            server = in.nextLine();
            if (server.equals("")) {
                server = DEFAULT_SERVER;
            }
            println("Insert server port (leave it blank for default:" + DEFAULT_SERVER_RMI_PORT + ")");
            port = in.nextLine();
            if (port.equals("")) {
                port = DEFAULT_SERVER_RMI_PORT;
            }
            int serverPort;
            try {
                serverPort = Integer.parseInt(port);
                communicator.initialize(server, serverPort);
                temp = false;
            } catch (NetworkErrorException e) {
                if (server.equals(DEFAULT_SERVER))
                    println("Server Offline");
                else
                    println("Server Offline or WRONG ip address");
            } catch (NumberFormatException e) {
                println("Wrong server port.");
            }
        }
    }

    /**
     * starts client with Socket technology initialising a {@link CommunicatorSocket}
     */
    private void startSocket() {
        communicator = new CommunicatorSocket(view);
        boolean temp = true;
        String server = new String();
        String port = new String();
        while (temp) {
            println("Insert server IP (leave it blank for default: " + DEFAULT_SERVER + ")");
            server = in.nextLine();
            if (server.equals("")) {
                server = DEFAULT_SERVER;
            }
            println("Insert server port (leave it blank for default: " + DEFAULT_SERVER_SOCKET_PORT + ")");
            port = in.nextLine();
            if (port.equals("")) {
                port = DEFAULT_SERVER_SOCKET_PORT;
            }
            int serverPort;
            try {
                serverPort = Integer.parseInt(port);
                communicator.initialize(server, serverPort);
                temp = false;
            } catch (NetworkErrorException e) {
                println("Server Offline or wrong IP:PORT");
            } catch (NumberFormatException e) {
                println("Wrong server port.");
            }
        }
    }

    /**
     * handles login phase
     */
    private void login() {
        boolean temp = true;
        String password;
        while (temp) {
            println("LOGIN");
            println("Username: ");
            username = in.nextLine();
            println("Password: ");
            password = in.nextLine();
            try {
                communicator.login(username, password);
                temp = false;
            } catch (NetworkErrorException e) {
                println("Server Offline / Network Error");
                startCLI();
            } catch (NotValidInputException e) {
                println("Wrong password or user already taken");
            }
        }
        view.setUsername(username);
        lobby();
    }

    /**
     * handles lobby phase
     */
    private void lobby() {
        boolean temp = true;
        String yearStr;
        String monthStr;
        String dayStr;
        int day;
        int month;
        int year;
        long time;
        while (temp) {
            try {
                print("LOBBY\nYear(YYYY):\t");
                yearStr = in.nextLine();
                if (!yearStr.equals("")) {
                    print("Month(MM):\t");
                    monthStr = in.nextLine();
                    print("Day(DD):\t");
                    dayStr = in.nextLine();
                    year = Integer.parseInt(yearStr);
                    month = Integer.parseInt(monthStr);
                    day = Integer.parseInt(dayStr);
                    time = isDateValid(day, month, year);
                    communicator.lobby(username, time);
                    temp = false;
                } else {
                    communicator.lobby(username, (long) 0);
                    temp = false;
                }
            } catch (NetworkErrorException e) {
                println("Server Offline / Network Error");
                startCLI();
            } catch (NotValidInputException | NumberFormatException e) {
                println("Invalid time");
            }
        }
        game();
    }

    /**
     * handles game phase
     */
    private void game() {
        boolean temp = true;
        boolean oneExit = false;
        String message = new String();
        while (temp) {
            message = in.nextLine();
            ArrayList<String> commands = new ArrayList<>(Arrays.asList(message.split("\\s*" + INPUT_STREAM_SEPARATOR_SYMBOL + "\\s*")));
            message = String.join(MESSAGE_SEPARATOR_SYMBOL, commands);
            try {
                if (message.equals("exit")) {
                    if (oneExit) {
                        temp = false;
                        oneExit = false;
                    } else {
                        oneExit = true;
                        communicator.sendMessage(message);
                    }
                } else {
                    if (message.equals("join")) {
                        oneExit = false;
                    }
                    communicator.sendMessage(message);
                }
            } catch (NetworkErrorException e) {
                println("Server Offline / Network Error");
                temp = false;
            }
        }
        startCLI();
    }

    /**
     * shows into commandLine the given message and goes to next line
     *
     * @param message : message to be shown to commandLine
     */
    private void println(String message) {
        System.out.println(message);
    }

    /**
     * shows into commandLine the given message.
     *
     * @param message the given message
     */
    private void print(String message) {
        System.out.print(message);
    }

    /**
     * checks if a given date(DD/MM/YYYY) is valid or not.
     * If the given date is valid it return its corresponding UNIX Time
     * else it throws a NotValidInputException.
     *
     * @param day   Date's day (YY)
     * @param month Date's month (MM)
     * @param year  Date's year (YYYY)
     * @return Date's UNIX Time
     * @throws NotValidInputException when the given Date is not valid
     */
    private long isDateValid(int day, int month, int year) throws NotValidInputException {
        GregorianCalendar cal = new GregorianCalendar(year, month - 1, day);
        cal.setLenient(false);
        try {
            cal.get(Calendar.DATE);
            return cal.getTime().getTime() / 1000;
        } catch (IllegalArgumentException e) {
            throw new NotValidInputException();
        }
    }
}


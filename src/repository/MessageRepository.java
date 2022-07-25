package repository;

import entity.Message;
import jdbc.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MessageRepository {
    private static Scanner scanner = new Scanner(System.in);

    public static void message_inbox(String receiverID) throws SQLException {
        ResultSet resultSet = Database.received_messages(receiverID);
        while (resultSet.next()){
            StringBuilder message = new StringBuilder();
            message.append("from @").append(resultSet.getString("senderID")).append(":\n");
            message.append(resultSet.getString("text")).append("\n");
            message.append(resultSet.getString("time")).append("\n");
            message.append("@").append(resultSet.getInt("id")).append("\n");
            message.append("------------------------------------------------");

            System.out.println(message);
        }
    }

    public static void send_message(String senderID) throws SQLException {
        System.out.println("Enter <cansel> for cansel.");
        String receiverID = scan_receiverID();
        if (receiverID==null) return;

        System.out.println("""
                new
                reply
                forward
                """
        );
        String text;
        while (true) {
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("cancel")) return;

            if (command.equalsIgnoreCase("new")) {
                text = scan_text();
                if (text == null) return;
                break;
            }
            else if (command.equalsIgnoreCase("reply")) {
                text = scan_reply(senderID);
                break;
            }
            else if (command.equalsIgnoreCase("forward")) {
                text = forward(senderID);
                break;
            }
        }

        if (Database.add_message(new Message(text, senderID, receiverID)) > 0)
            System.out.println("Message sent successfully.");
        else System.err.println("something went wrong!\nPlease try again.");
    }

    public static void send_message(String senderID, String receiverID) throws SQLException {
        System.out.println("Enter <cansel> for cansel.");
        System.out.println("""
                new
                reply
                forward
                """);

        String text;
        while (true) {
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("new")) {
                text = scan_text();
                if (text == null) return;
                break;
            }
            else if (command.equalsIgnoreCase("reply")) {
                text = scan_reply(senderID);
                break;
            }
            else if (command.equalsIgnoreCase("forward")) {
                text = forward(senderID);
                break;
            }
        }

        if (Database.add_message(new Message(text, senderID, receiverID)) > 0)
            System.out.println("Message sent successfully.");
        else System.err.println("something went wrong!\nPlease try again.");
    }

    private static String scan_receiverID() throws SQLException {
        System.out.println("Please inter receiverID: ");
        while (true) {
            String receiverID = scanner.nextLine().trim();
            if (receiverID.equalsIgnoreCase("cansel")) return null;
            if (Database.user_exists(receiverID)) return receiverID;
            else System.err.println("User doesn't exist.");
        }
    }

    public static String scan_text() {
        System.out.println("Please enter the message (type end to finish): ");
        StringBuilder text = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("cansel")) return null;
            if (line.equalsIgnoreCase("end")) return text.toString();
            else text.append(line).append("\n");
        }
    }

    public static void sent(String senderID) throws SQLException {
        ResultSet resultSet = Database.sent_messages(senderID);
        while (resultSet.next()) {
            StringBuilder message = new StringBuilder();
            message.append("to ").append(resultSet.getString("receiverID")).append(":\n");
            message.append(resultSet.getString("text")).append("\n");
            message.append(resultSet.getString("time")).append("\n");
            message.append("@").append(resultSet.getInt("id")).append("\n");
            message.append("------------------------------------------------");

            System.out.println(message);
        }
    }

    private static String scan_reply(String userID) throws SQLException {
        System.out.println("messageID:");
        while (true) {
            String messageID = scanner.nextLine().trim();
            if (messageID.equalsIgnoreCase("cancel")) return null;
            try {
                if (Database.isRelated(Integer.parseInt(messageID), userID)) {
                    return "reply to message @" + messageID + ":\n" + scan_text();
                } else System.err.println("Choose a related message.");
            }
            catch (Exception e) {
                System.err.println("Invalid input.");
            }
        }
    }

    private static String forward(String userID) throws SQLException {
        System.out.println("messageID:");
        while (true) {
            String messageID = scanner.nextLine().trim();
            if (messageID.equalsIgnoreCase("cancel")) return null;
            try {
                if (Database.isRelated(Integer.parseInt(messageID), userID)) {
                    return forward_text(messageID);
                } else System.err.println("Choose a related message.");
            }
            catch (Exception e) {
                System.err.println("Invalid input.");
            }
        }
    }

    private static String forward_text(String messageID) throws SQLException {
        ResultSet resultSet = Database.forward_text(Integer.parseInt(messageID));
        resultSet.next();
        return "forwarded from @"+resultSet.getString(1)+":\n"+resultSet.getString(2);
    }
}

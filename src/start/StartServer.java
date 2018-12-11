package start;

import DB.DatabaseHandler;
import config.Const;
import model.ObjMessage;
import objects.Doctor;
import objects.Service;
import objects.User;

import java.lang.String;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StartServer extends Thread {

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private ArrayList<ClientThread> clientsConnected = new ArrayList<ClientThread>();
    private boolean keepGoing = true;
    private static int connectCount = 0;
    private ResultSet result = null;

    public static void main(String[] arg) {
        StartServer server = new StartServer();
        server.start();
    }

    @Override
    public void run() {
        startServer();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(Const.PORT);
            System.out.println("start started in Thread - " + Thread.currentThread().getName() + "\n" +
                    "Waiting for connection....");

            while (keepGoing) {
                System.out.println(connectCount + " - connect");
                clientSocket = serverSocket.accept();
                connectCount++;
                System.out.println("connection " + connectCount + " established....");

                ClientThread client = new ClientThread(clientSocket);
                clientsConnected.add(client);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException in StartServer.run()");
        }
    }

    class ClientThread extends Thread {
        Socket clientSocket;
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        ObjMessage objMessage;
        User user;
        Doctor doctor;

        ClientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                output = new ObjectOutputStream(clientSocket.getOutputStream());
                input = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean flag = true;
            while (flag) {
                try {
                    objMessage = (ObjMessage) input.readObject();

                    switch (objMessage.getTypeObject()){
                        case "user":
                            user = objMessage.getUserObject();

                            switch (user.getType()) {
                                case "login":
                                    login();
                                    break;
                                case "message":
                                    break;
                                case "logout":
                                    flag = false;
                                    break;
                                case "register":
                                    register(DatabaseHandler.registerUser(user));
                                    break;
                                case "view_order_ticket":
                                    output.writeObject(DatabaseHandler.getTicket(objMessage));
                                    break;
                                case "view_users":
                                    returnViewUser(DatabaseHandler.viewUser());
                                    break;
                                case "block_users":
                                    DatabaseHandler.blockUser(objMessage.getUserObject());
                                    break;
                                case "unlock_users":
                                    DatabaseHandler.unlockUser(objMessage.getUserObject());
                                    break;
                            }
                            break;
                        case "doctor":
                            doctor = objMessage.getDoctorObject();

                            switch (doctor.getType()) {
                                case "view_doctor":
                                    returnViewDoctor(DatabaseHandler.viewDoctor());
                                    break;
                                case "add_doctor":
                                    DatabaseHandler.addDoctor(doctor);
                                    break;
                                case "edit_doctor":
                                    DatabaseHandler.editDoctor(doctor);
                                    break;
                                case "delete_doctor":
                                    DatabaseHandler.deleteDoctor(doctor);
                                    break;
                                case "order_ticket":
                                    output.writeObject(DatabaseHandler.checkout(objMessage));
                                    output.flush();
                                    break;
                                case "view_ticket":
                                    output.writeObject(DatabaseHandler.viewTicket(objMessage));
                                    output.flush();
                                    break;
                                case "update_all":
                                    DatabaseHandler.updateAll();
                                    break;
                                case "view_services":
                                    returnViewServices(DatabaseHandler.viewServices());
                                    break;
                                case "add_service":
                                    DatabaseHandler.addServices(objMessage);
                                    break;
                                case "delete_service":
                                    DatabaseHandler.deleteServices(objMessage);
                                    break;
                                case "order_paid_service":
                                    DatabaseHandler.orderService(objMessage);
                                    break;
                            }
                            break;
                    }
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Client disconnect");
            close();
        }

        private void returnViewServices(ArrayList<Service> services) throws IOException {
            output.writeObject(services);
            output.flush();
        }

        private void returnViewUser(ArrayList<User> users) throws IOException {
            output.writeObject(users);
            output.flush();
        }

        private void returnViewDoctor(ArrayList<Doctor> doctors) throws IOException {
            output.writeObject(doctors);
            output.flush();
        }

        private void register(User user) throws IOException {
            output.writeObject(user);
            output.flush();
        }

        private void login() throws SQLException, IOException {
            result = DatabaseHandler.getUser(user);
            if (result.next()) {
                register(getUser());
            } else {
                output.writeObject(null);
                output.flush();
            }
        }

        private User getUser() {
            try {
                user.setFirstName(result.getString(Const.USERS_NAME));
                user.setLastName(result.getString(Const.USERS_SURNAME));
                user.setEmail(result.getString(Const.USERS_EMAIL));
                user.setPassword(result.getString(Const.USERS_PASSWORD));
                user.setRole(result.getInt(Const.USERS_ROLE_ID));
                user.setId(result.getInt(Const.USERS_ID));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user;
        }

        private void close() {

                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }
    }
}
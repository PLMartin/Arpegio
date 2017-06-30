package Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    //On initialise des valeurs par défaut

    private int port = 2345;

    private String host = "127.0.0.1";

    private ServerSocket server = null;

    private boolean isRunning = true;


    public static void main(String [] args){


        Server serv = new Server();
        serv.open();
    }


    public Server(){

        try {
            // creer une socket de 100 connexion max
            server = new ServerSocket(port, 100, InetAddress.getByName(host));

        }
        catch (UnknownHostException e) {
            e.printStackTrace();

        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void open(){

        //Toujours dans un thread à part vu qu'il est dans une boucle infinie

        Thread t = new Thread(new Runnable(){

            public void run(){

                while(isRunning == true){
                    try {
                        //On attend une connexion d'un client
                        Socket client = server.accept();

                        //Une fois reçue, on la traite dans un thread séparé
                        System.out.println("Connexion cliente reçue.");
                        Thread t = new Thread(new ClientProcessor(client));
                        t.start();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try{
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    server = null;
                }
            }
        });

        t.start();
    }

    public void close(){
        isRunning = false;
    }

}
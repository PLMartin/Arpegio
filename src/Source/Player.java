package Source;

import Util.FIFO;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Player extends Items implements Runnable {

    protected FIFO shots;

    private Socket connexion = null;

    private PrintWriter writer = null;

    private BufferedInputStream reader = null;

    private static int count = 0;

    private String name = "Source-";

    public int timeBeforeNewShot = 0;


    Player(){
        speed = new Point(0, 0);
        direction = new Point(-7, 0);
        setImage("Images/player1.jpeg");
        shots = new FIFO(5);
    }

    public void shot(Point direction){
        if(timeBeforeNewShot == 0){
            shots.push(new Shots(this.position, new Point(direction.x, direction.y)));
            timeBeforeNewShot = 500;  // TIME BETWEEN TWO SHOTS (ms)
        }
    }

    public void connexion(String host, int port){

        name += ++count;
        try{
            connexion = new Socket(host, port);
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void run(){

        //nous n'allons faire que 10 demandes par thread...
        for(int i =0; i < 10; i++){
            try {
                Thread.currentThread().sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                writer = new PrintWriter(connexion.getOutputStream(), true);
                reader = new BufferedInputStream(connexion.getInputStream());

                //On envoie la commande au serveur
                String commande = getCommand();
                writer.write(commande);

                //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
                writer.flush();

                System.out.println("Commande " + commande + " envoyée au serveur");

                //On attend la réponse

                String response = read();
                System.out.println("\t * " + name + " : Réponse reçue " + response);



            }
            catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                Thread.currentThread().sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writer.write("CLOSE");
        writer.flush();
        writer.close();

    }



    //Méthode qui permet d'envoyer des commandeS de façon aléatoire

    private String getCommand(){
        return "";
    }

    //Méthode pour lire les réponses du serveur

    private String read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;

    }

}

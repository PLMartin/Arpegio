package Util;

import Source.Shots;

public class FIFO {
    private static Shots[] container;
    private static int size;
    private static int MAX_SIZE;

    public FIFO(int s){
        this.size = 0;
        MAX_SIZE = s;
        container = new Shots[MAX_SIZE];
    }

    public void push(Shots objet){
        if(size < MAX_SIZE) {
            container[size] = objet;
            size += 1;
        }
        else {
            System.out.println("Pas assez de munitions !");
        }
    }

    public Shots pop(){
        assert(size > 0);
        Shots ret = container[0];
        for(int i = 0; i < size-1; i++){
            container[i] = container[i+1];
        }
        size -= 1;
        return ret;
    }

    public Shots [] toArray(){
        return container;
    }

    public int getSize(){
        return this.size;
    }

}

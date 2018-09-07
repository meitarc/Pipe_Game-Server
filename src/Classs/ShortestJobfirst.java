package Classs;

import java.io.*;
import java.net.Socket;

public class ShortestJobfirst implements Comparable<ShortestJobfirst>{

    private Socket socket;
    private double compared;

    public ShortestJobfirst(Socket aClient) {
        this.socket=aClient;
        this.compared=0;

        try {
            InputStream inFromClient = aClient.getInputStream();
            ObjectInputStream objectInputStream=new ObjectInputStream(
                    (inFromClient));
            this.compared=objectInputStream.readDouble();
            //System.out.println("compared "+this.compared);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public Socket getSocket() {
        return socket;
    }

    @Override
    public int compareTo(ShortestJobfirst o) {
        double d=this.compared-o.compared;
        if(d==0)
        {
            return 0;
        }
        else if(0<d)//o.compared<this.compared
        {
            return (int) Math.ceil(d);
        }
        else//if(d<0)//this.compared<o.compared
        {
            return (int)Math.floor(d);
        }
    }
}

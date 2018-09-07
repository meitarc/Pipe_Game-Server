package test;

import server.MyClientHandler;
import server.MyServer;
import server.Server;

public class MainTrain {
    // run your server here
    static Server s;
    public static void runServer(int port){
        s=new MyServer(port);
        s.start(new MyClientHandler());
    }
    // stop your server here
    public static void stopServer(){
        s.stop();
    }
    public static void main(String[] args) {
        int port=6400;
        runServer(port);
        //stopServer();
        System.out.println("done");
    }
}

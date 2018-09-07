package server;

import Classs.ShortestJobfirst;

import java.io.*;
import java.net.*;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Supplier;


public class MyServer implements Server{
	
	///DONT PRINT ANYTHING IN THAT CLASS!!!!!!!!!!!!
	private final int port;
	private ClientHandler ch;
	private volatile boolean stop;

	private int m_now;
	private int m=1;


	public MyServer(int port) {
		this.port=port;
		stop=false;
	}
	
	private void runServer(){
		m_now=0;
		ServerSocket server= null;
		try {
			server = new ServerSocket(port);
			ExecutorService executor = Executors.newFixedThreadPool(2);
			PriorityBlockingQueue<ShortestJobfirst> socketBlockingQueue=new PriorityBlockingQueue<>();
			//System.out.println("1");
			Object toomanyclinet=new Object();
			Thread thread=new Thread(() -> {

				while (!stop||!socketBlockingQueue.isEmpty()){
					try {
						Socket aClient = socketBlockingQueue.take().getSocket();

						if(m<=m_now) {
							try {
								toomanyclinet.wait();
							} catch (Exception e) {
								//e.printStackTrace();
							}
						}
						executor.submit(() -> {
							try {
								m_now++;
								InputStream inFromClient = aClient.getInputStream();
								OutputStream outToClient = aClient.getOutputStream();
								// interact (read & write) with the client according to protocol
								ch.handleClient(inFromClient, outToClient);
								inFromClient.close();
								outToClient.close();
								aClient.close();
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								m_now--;
								toomanyclinet.notify();
							}
						});
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				toomanyclinet.notifyAll();

			});
			thread.start();
			//System.out.println("2");
			try {
				server.setSoTimeout(1000);
			} catch (SocketException e) {
				//e.printStackTrace();
			}
			while(!stop){
				try {
					Socket aClient=server.accept(); // blocking call
					socketBlockingQueue.put(new ShortestJobfirst(aClient));
				}  catch (IOException e) {
					//e.printStackTrace();
				}
			}
			toomanyclinet.notifyAll();
			//System.out.println("3");
			//System.out.println("end");
			server.close();

		} catch (IOException e) {
			//e.printStackTrace();
		}
		
	}
	
	/*
	 * © Gil Yermiyah, Ziv Sofrin.
	 */
	
	@Override
	public void start(ClientHandler clientHandler) {
		this.ch=clientHandler;
		new Thread(()->{
			try {
				runServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start(); // we will learn about it semester B…
	}

	@Override
	public void stop() {
		stop=true;
	}

}

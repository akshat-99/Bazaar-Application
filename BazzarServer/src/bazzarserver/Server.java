/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

        
package bazzarserver;

import java.io.IOException;
import java.net.*;
//import java.IOexpection;
import javax.swing.*;

/**
 *
 * @author Dell
 */
public class Server {
     ServerSocket serversocket = null;
        Socket socket;  
        Thread t;
    int Status=0;
    public int flag=0,k=0;
    
    
    void Start() throws IOException
    {
        if(Status==0)
        {
            flag=1;
            k=0;
            
        try
        {
           serversocket = new ServerSocket(5436);
            System.out.println("ServerStarted");
            Status=1;
        }
        catch(Exception e){
                 JOptionPane.showMessageDialog(null,"unable to start ServerSocket");
          }
     
        
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {   if(k==1)
                    this.Stop();
            socket = serversocket.accept();
            System.out.println("socket connected to serversocket");
            HandleClient r = new HandleClient(socket);
            System.out.println("handleclient object created");
            t =new Thread(r);
            System.out.println("thread started");
            t.start();
            } catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null,"unable to start Thread");
                    }
        }
        Status=0;
        }
        
    }
    void Stop()
    {
            try{
                socket.close();
                flag=0;
                Status=0;
                t.interrupt();
                System.out.println("Server Closed");
            }
            catch(Exception ex){
                System.out.print("unable to close"+ ex);
            }
        }
        
        
        
    }

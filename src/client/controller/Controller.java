/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Request;
import models.Student;

/**
 *
 * @author cuongnv
 */
public class Controller {
    private String host ="localhost";
    private int port = 8888;
    private Socket client;
    private ObjectInputStream ois ;
    private ObjectOutputStream oos ;
    public Controller(){
        open();
    }
    public boolean update(Student s){
        send(new Request("update",(Object)s));
        return (boolean)receive();
        
    }
    public boolean add(Student s){
        send(new Request("add", (Object)s));
        return (boolean)receive();
    }
    public boolean delete(int ma){
        send(new Request("delete",(Object)ma));
        return (boolean)receive();
    }
    public ArrayList<Student> search(String key){
        send(new Request("search",(Object)key));
        return (ArrayList<Student>) receive();
                
    }
    public void send(Request req){
        try {
            oos.writeObject(req);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Object receive(){
        Object o = null;
        try {
            o = ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
        
    }
    public void open(){
        try {
            client  = new Socket(host,port);
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

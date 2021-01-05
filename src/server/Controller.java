/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
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
    private String host = "localhost";
    private int port = 8888;
    private Connection con ;
    private ServerSocket server ;
    private Socket client ;
    private ObjectOutputStream oos;
    private ObjectInputStream ois ;
    public Controller(){
        connectDb();
        open();
        while(true){
            try {
                client = server.accept();
                ois = new ObjectInputStream(client.getInputStream());
                oos = new ObjectOutputStream(client.getOutputStream());
                Request res = (Request)ois.readObject();
                switch(res.getName()){
                    case "update":
                        update((Student)res.getData());
                        break;
                    case "delete":
                        delete((int)res.getData());
                        break;
                    case "add":
                        add((Student) res.getData());
                        break;
                    case "search":
                        search((String)res.getData());
                        break;
                   
                }
                
                
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    public void search(String k){
        try {
            ArrayList<Student> res = new ArrayList<>();
            String sql ="select * from tbl_student where ten like '%"+k+"%'";
            PreparedStatement ps =con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                res.add(new Student(rs.getInt("ma"),rs.getString("ten"),rs.getString("khoa"),rs.getString("ngaysinh"),rs.getString("quequan")));
            }
            send(res);
            
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void add(Student s){
        try {
            String sql ="insert into tbl_student(ten,khoa,ngaysinh,quequan) values(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getTen());
            ps.setString(2, s.getKhoa());
            ps.setString(3, s.getNgaysinh());
            ps.setString(4, s.getQuequan());
            ps.execute();
            send(true);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            send(false);
        }
        
    }    
    public void delete(int ma){
        String sql ="delete from tbl_student where ma="+ma;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            send(true);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            send(false);
        }
        
    }
    public void update(Student s){
        try {
            String sql ="update tbl_student set ten=?,khoa=?,ngaysinh=?,quequan=? where ma="+s.getMa();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getTen());
            ps.setString(2, s.getKhoa());
            ps.setString(3, s.getNgaysinh());
            ps.setString(4, s.getQuequan());
            ps.execute();
            send(true);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            send(false);
        }
        
        
        
    }
    public void send(Object o){
        try {
            oos.writeObject(o);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void open(){
        try {
            server  = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void connectDb(){
        try {
            String sql = "jdbc:mysql://localhost/LTM";
            String classUrl ="com.mysql.jdbc.Driver";
            Class.forName(classUrl);
            con = DriverManager.getConnection(sql,"root","root");     
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
    }
    
}

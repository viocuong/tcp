/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author cuongnv
 */
public class Student implements Serializable{
    private String ten ;
    private int ma ;
    private String khoa ;
    private String ngaysinh ;
    private String quequan ;

    public Student(int ma,String ten,  String khoa, String ngaysinh, String quequan) {
        this.ten = ten;
        this.ma = ma;
        this.khoa = khoa;
        this.ngaysinh = ngaysinh;
        this.quequan = quequan;
    }

    public Student(String ten, String khoa, String ngaysinh, String quequan) {
        this.ten = ten;
        this.khoa = khoa;
        this.ngaysinh = ngaysinh;
        this.quequan = quequan;
    }

    public String getTen() {
        return ten;
    }

    public int getMa() {
        return ma;
    }

    public String getKhoa() {
        return khoa;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getQuequan() {
        return quequan;
    }
    
}

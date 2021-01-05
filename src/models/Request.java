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
public class Request implements Serializable{
    private String name;
    private Object data ;

    public Request(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public Request(String name) {
        this.name = name;
    }

   

    public String getName() {
        return name;
    }

    public Object getData() {
        return data;
    }
    
}

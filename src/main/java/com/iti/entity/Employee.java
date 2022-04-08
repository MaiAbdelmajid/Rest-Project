/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahmedmedhat
 */

@XmlRootElement (name="person")
public class Employee {
    Integer id;
    String name;

    public Employee(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return id+"::"+name;
    }
    
}

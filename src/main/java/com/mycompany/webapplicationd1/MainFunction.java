/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webapplicationd1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iti.dbconnection.DBConnection;
import com.iti.entity.Employee;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ahmedmedhat
 */
@Path("/api")
public class MainFunction {
    @GET
    @Path("/employee/{id}")
//    @Produces("applicatoin/json")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.TEXT_PLAIN)

    public String getEmployeeByID(@PathParam("id") int id) {
//        Employee e = null;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        //open connection from database
        DBConnection c = new DBConnection();
        c.getConnection();
        Employee e = c.getEmployeeByID(id);
        String empg = gson.toJson(e);
        return empg;
    }
    
    
    @GET
    @Path("/employee/all")
    @Produces(MediaType.TEXT_PLAIN)

    public String getAllEmployee() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        //open connection from database
        DBConnection c = new DBConnection();
        c.getConnection();
        List<Employee> e = c.getEmployee();
        String empg = gson.toJson(e);
        return empg;
    }
    


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST 
    @Path("/employee/add/")
    public void setEmployee(String data){
        System.out.print("IN POST METHOD");
        Gson gson = new Gson();
        Employee emp = gson.fromJson(data, Employee.class);
        DBConnection c = new DBConnection();
        c.getConnection();
        c.setEmployee(emp);
    }
  
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/employee/update/")
    public void updateEmployee(String data){
        Employee e = null;
        DBConnection c = new DBConnection();
        Gson gson = new Gson();
        e = gson.fromJson(data, Employee.class);
        c.getConnection();
        c.updateEmployee(e);
    }
    
    @DELETE
    @Path("/employee/delete/{id}")
    public void deleteEmployee(@PathParam("id") int id){
        DBConnection c = new DBConnection();
        c.getConnection();
        c.deleteEmployee(id);
    }   
    // validation 
    // client
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.dbconnection;

import com.iti.entity.Employee;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author ahmedmedhat
 */
public class StartDB {
    

//    public StartDB() {
//        Connection con = DBConnection.getConnection();
//    }

    public List<Employee> getEmployee() throws SQLException {
        Statement stmt = null;
        ResultSet rs;
        List<Employee> emp = null;
        String sqlQu = "select * from employee";
       
        
        rs = stmt.executeQuery(sqlQu);
        String name = null;
        int id = 0;
        while (rs.next()) {
            name = rs.getString("name");
            id = rs.getInt("id");
            emp = (List<Employee>) new Employee(id, name);
        }
        return emp;
    
    }

    public Employee getEmployeeByID(int iD) throws SQLException {
        Connection connection =null;
        Statement stmt = connection.createStatement();
        ResultSet rs;
        Employee emp = null;
        String sqlQu = "select * from employee "
                + "where id = " + iD + "";
        rs = stmt.executeQuery(sqlQu);
        String name;
        int id;
        while (rs.next()) {
            name = rs.getString("name");
            id = rs.getInt("id");
            emp = new Employee(id, name);
        }
        return emp;
    }

}

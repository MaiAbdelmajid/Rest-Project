/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.dbconnection;

import com.iti.entity.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.shaded.com.ongres.scram.common.ScramAttributes;

/**
 *
 * @author ahmedmedhat
 */
public class DBConnection {

    public Connection c;
    private PreparedStatement preStm;
    private final String url = "jdbc:postgresql://localhost:5432/test";
    private final String userName = "postgres";
    private final String password = "postgres";
    private ResultSet rs;

    public void getConnection() {
        c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, userName, password);
//            return c;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
//        return null;
    }

    public Employee getEmployeeByID(int iD) {
        Employee emp = null;
        try {
            String sqlQu = "select * from emplyee "
                    + "where id = ?";
            preStm = c.prepareStatement(sqlQu);
            preStm.setInt(1, iD);
            rs = preStm.executeQuery();
            while (rs.next()) {
                emp = new Employee(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emp;
    }

    public ArrayList<Employee> getEmployee() {
        Employee emp = null;
        String sqlQu = "select * from emplyee";
        ArrayList<Employee> empList = new ArrayList();

        try {
            preStm = c.prepareStatement(sqlQu);
            rs = preStm.executeQuery();
            while (rs.next()) {
                emp = new Employee(rs.getInt(1), rs.getString(2));
                empList.add(emp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return empList;
    }

    public void setEmployee(Employee e) {
        String sqlQu = "insert into emplyee values(?,?)";
        try {
            preStm = c.prepareStatement(sqlQu);
            preStm.setInt(1, e.getId());
            preStm.setString(2, e.getName());
            preStm.executeUpdate();
            System.out.println("TRY");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            System.out.println("CATCH");
        }
    }
    
    public void deleteEmployee(int id){
        String sqlQU = "delete from emplyee "
                + "where id=?";
        try {
            preStm = c.prepareStatement(sqlQU);
            preStm.setInt(1, id);
            preStm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void updateEmployee(Employee e){
        String sqlQU = "update emplyee set name = ?"
                + "where id = ?";
        try {
            preStm = c.prepareStatement(sqlQU);
            preStm.setInt(2, e.getId());
            preStm.setString(1, e.getName());
            preStm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}

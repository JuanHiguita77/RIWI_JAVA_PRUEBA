package Model;

import Entity.Contratacion;
import Entity.Coder;
import database.CRUD;
import database.ConfigDB;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoderModel implements CRUD
{
    @Override
    public Object create(Object object)
    {

        Connection conexion = ConfigDB.openConnection();

        Coder coder = (Coder) object;

        try {
            //El id se pone automatico por la base de datos
            String sql = "INSERT INTO coder (nombre, apellidos, documento, cohorte, cv) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = conexion.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,coder.getName());
            preparedStatement.setString(2,coder.getSurname());
            preparedStatement.setString(3,coder.getDocument());
            preparedStatement.setInt(4,coder.getCohorte());
            preparedStatement.setString(5,coder.getCv());

            preparedStatement.execute();

            ResultSet respuesta = preparedStatement.getGeneratedKeys();

            //Le damos el id correspondiente a cada nueva entidad
            while (respuesta.next()){
                coder.setId_coder(respuesta.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "New coder Added.");

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ConfigDB.closeConnection();

        return coder;
    }

    @Override
    public List<Object> list()
    {
        Connection conexion = ConfigDB.openConnection();

        List<Object> coderList = new ArrayList<>();

        try
        {
            String sqlQuery = "SELECT * FROM coder;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {
                Coder coder = new Coder();

                coder.setId_coder(resultado.getInt("coder.id_coder"));
                coder.setName(resultado.getString("coder.nombre"));
                coder.setSurname(resultado.getString("coder.apellidos"));
                coder.setDocument(resultado.getString("coder.documento"));
                coder.setCohorte(resultado.getInt("coder.cohorte"));
                coder.setCv(resultado.getString("coder.cv"));

                coderList.add(coder);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Model Coder Error in list metod" + error.getMessage());
        }

        ConfigDB.closeConnection();

        return coderList;
    }

    @Override
    public boolean delete(Object object)
    {
        Coder coder = (Coder) object;

        Connection  conexion = ConfigDB.openConnection();

        boolean isDeleted = false;

        try
        {
            String sqlQuery = "DELETE FROM coder WHERE id_coder = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, coder.getId_coder());

            if (preparedStatement.executeUpdate() > 0)
            {
                isDeleted = true;
            }

            JOptionPane.showMessageDialog(null, "Succesfull Delete");

        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error deleting in coder Model: " +  e.getMessage());
        }

        ConfigDB.closeConnection();

        return isDeleted;
    }

    @Override
    public boolean update(Object object)
    {

        Connection conexion = ConfigDB.openConnection();

        boolean isUpdated = false;

        try
        {
            Coder coder = (Coder) object;

            String sqlQuery = "UPDATE coder SET nombre = ?, apellidos = ?, documento = ?, cohorte = ?, cv = ? WHERE id_coder = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Se le pasa posicion y dato al statement
            preparedStatement.setString(1, coder.getName());
            preparedStatement.setString(2, coder.getSurname());
            preparedStatement.setString(3, coder.getDocument());
            preparedStatement.setInt(4, coder.getCohorte());
            preparedStatement.setString(5, coder.getCv());
            preparedStatement.setInt(6, coder.getId_coder());

            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0)
            {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Dont Updated X.x");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null , "Model coder Updating Error --> " + e.getMessage());
        }
        finally
        {
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

    public List<Object> findByCohorte(int cohorte)
    {
        Connection conexion = ConfigDB.openConnection();

        List<Object> coderList = new ArrayList<>();

        //Global
        Coder coder = null;

        try
        {
            String sqlQuery = "SELECT * FROM coder WHERE cohorte = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el ID al query
            preparedStatement.setInt(1, cohorte);

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            while(resultado.next())
            {
                coder = new Coder();

                coder.setId_coder(resultado.getInt("id_coder"));
                coder.setName(resultado.getString("nombre"));
                coder.setSurname(resultado.getString("apellidos"));
                coder.setDocument(resultado.getString("documento"));
                coder.setCohorte(resultado.getInt("cohorte"));
                coder.setCv(resultado.getString("cv"));

                coderList.add(coder);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "coder model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return coderList;
    }

    public List<Object> findByClan(String clan)
    {
        Connection conexion = ConfigDB.openConnection();

        List<Object> coderList = new ArrayList<>();

        //Global
        Coder coder = null;

        try
        {
            String sqlQuery = "SELECT * FROM vacante JOIN coder ON vacante.clan = coder.cv WHERE vacante.clan LIKE ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el ID al query
            preparedStatement.setString(1, "%" + clan + "%");

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            while(resultado.next())
            {
                coder = new Coder();

                coder.setId_coder(resultado.getInt("id_coder"));
                coder.setName(resultado.getString("nombre"));
                coder.setSurname(resultado.getString("apellidos"));
                coder.setDocument(resultado.getString("documento"));
                coder.setCohorte(resultado.getInt("cohorte"));
                coder.setCv(resultado.getString("cv"));

                coderList.add(coder);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "coder model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return coderList;
    }

    public List<Object> findBytech(String tech)
    {
        Connection conexion = ConfigDB.openConnection();

        List<Object> coderList = new ArrayList<>();

        //Global
        Coder coder = null;

        try
        {
            String sqlQuery = "SELECT * FROM vacante JOIN coder ON vacante.tecnologia LIKE CONCAT('%', coder.cv, '%') WHERE coder.cv LIKE ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos la palabra al query
            preparedStatement.setString(1, "%" + tech + "%");

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            while(resultado.next())
            {
                coder = new Coder();

                coder.setId_coder(resultado.getInt("id_coder"));
                coder.setName(resultado.getString("nombre"));
                coder.setSurname(resultado.getString("apellidos"));
                coder.setDocument(resultado.getString("documento"));
                coder.setCohorte(resultado.getInt("cohorte"));
                coder.setCv(resultado.getString("cv"));

                coderList.add(coder);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "coder model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return coderList;
    }
}


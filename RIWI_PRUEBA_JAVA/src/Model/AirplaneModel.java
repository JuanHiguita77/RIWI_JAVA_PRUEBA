package Model;

import Entity.Avion;
import database.CRUD;
import database.ConfigDB;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AirplaneModel implements CRUD
{
    @Override
    public Object create(Object object) {

        Connection conexion = ConfigDB.openConnection();

        Avion airplane = (Avion) object;

        try {
            //El id se pone automatico por la base de datos
            String sql = "INSERT INTO avion (id_avion, modelo, capacidad) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = conexion.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,airplane.getId_airplane());
            preparedStatement.setString(2,airplane.getModel());
            preparedStatement.setInt(3,airplane.getCapacity());

            preparedStatement.execute();

            ResultSet respuesta = preparedStatement.getGeneratedKeys();

            //Le damos el id correspondiente a cada nueva entidad
            while (respuesta.next()){
                airplane.setId_airplane(respuesta.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "New Airplane Added.");

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ConfigDB.closeConnection();

        return airplane;
    }

    @Override
    public List<Object> list() {

        Connection conexion = ConfigDB.openConnection();

        List<Object> airplaneList = new ArrayList<>();

        try
        {
            String sqlQuery = "SELECT * FROM avion;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {

                Avion airplane = new Avion();

                airplane.setId_airplane(resultado.getInt("id_avion"));
                airplane.setModel (resultado.getString("modelo"));
                airplane.setCapacity(resultado.getInt("capacidad"));

                airplaneList.add(airplane);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Model Error in list metod" + error.getMessage());
        }

        ConfigDB.closeConnection();

        return airplaneList;
    }

    @Override
    public boolean delete(Object object)
    {
        Avion airplane = (Avion) object;

        Connection  conexion = ConfigDB.openConnection();

        boolean isDeleted = false;

        try
        {
            String sqlQuery = "DELETE FROM avion WHERE id_avion = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, airplane.getId_airplane());

            int resultadoFilasAfectadas = preparedStatement.executeUpdate();

            if (resultadoFilasAfectadas > 0)
            {
                isDeleted = true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Dont deleted x.x");
            }

        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error deleting in airplane Model: " +  e.getMessage());
        }

        ConfigDB.closeConnection();

        return isDeleted;
    }

    @Override
    public boolean update(Object object) {

        Connection conexion = ConfigDB.openConnection();

        boolean isUpdated = false;

        try
        {
            Avion airplane = (Avion) object;

            String sqlQuery = "UPDATE avion SET id_avion = ?, modelo = ?, capacidad = ? WHERE id_avion = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            //Se le pasa posicion y dato al statement

            preparedStatement.setInt(1, airplane.getId_airplane());
            preparedStatement.setString(2, airplane.getModel());
            preparedStatement.setInt(3, airplane.getCapacity());
            preparedStatement.setInt(4, airplane.getId_airplane());

            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0)
            {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null , "Model Airplane Updating Error --> " + e.getMessage());
        }
        finally
        {
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

    public Avion findById(int id_cite)
    {
        Connection conexion = ConfigDB.openConnection();

        //Global
        Avion airplane = null;

        try
        {
            String sqlQuery = "SELECT * FROM avion WHERE id_avion = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el ID al query
            preparedStatement.setInt(1, id_cite);

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            if(resultado.next())
            {
                airplane = new Avion();

                airplane.setId_airplane(resultado.getInt("id_avion"));
                airplane.setModel(resultado.getString("modelo"));
                airplane.setCapacity(resultado.getInt("capacidad"));

            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Airplanee model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return airplane;
    }

}

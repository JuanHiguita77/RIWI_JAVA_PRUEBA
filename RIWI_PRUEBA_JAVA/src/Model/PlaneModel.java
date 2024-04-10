package Model;

import Entity.Pasajero;
import Entity.Vuelo;
import database.CRUD;
import database.ConfigDB;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaneModel implements CRUD
{
    @Override
    public Object create(Object object) {

        Connection conexion = ConfigDB.openConnection();

        Vuelo plane = (Vuelo) object;

        try {
            //El id se pone automatico por la base de datos
            String sql = "INSERT INTO vuelo (destino, fecha_salida, hora_salida, fk_id_avion) VALUES (?, ?, ?, ?);";

            PreparedStatement preparedStatement = conexion.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,plane.getDestiny());
            preparedStatement.setDate(2,plane.getOut_date());
            preparedStatement.setTime(3,plane.getOut_hour());
            preparedStatement.setInt(4,plane.getFk_id_airplane());

            preparedStatement.execute();

            ResultSet respuesta = preparedStatement.getGeneratedKeys();

            //Le damos el id correspondiente a cada nueva entidad
            while (respuesta.next()){
                plane.setId_fly(respuesta.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "New Plane Added.");

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ConfigDB.closeConnection();

        return plane;
    }

    @Override
    public List<Object> list() {

        Connection conexion = ConfigDB.openConnection();

        List<Object> planeList = new ArrayList<>();

        try
        {
            String sqlQuery = "SELECT * FROM vuelo;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {

                Vuelo plane = new Vuelo();
                
                plane.setId_fly(resultado.getInt("id_vuelo"));
                plane.setDestiny(resultado.getString("destino"));
                plane.setOut_date(resultado.getDate("fecha_salida"));
                plane.setOut_hour(resultado.getTime("hora_salida"));
                plane.setFk_id_airplane(resultado.getInt("fk_id_avion"));

                planeList.add(plane);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Plane Model Error in list" + error.getMessage());
        }

        ConfigDB.closeConnection();

        return planeList;
    }

    @Override
    public boolean delete(Object object)
    {
        Vuelo plane = (Vuelo) object;

        Connection  conexion = ConfigDB.openConnection();

        boolean isDeleted = false;

        try
        {
            String sqlQuery = "DELETE FROM vuelo WHERE id_vuelo = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, plane.getId_fly());

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
            JOptionPane.showMessageDialog(null, "Error deleting in Plane Model: " +  e.getMessage());
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
            Vuelo plane = (Vuelo) object;

            String sqlQuery = "UPDATE vuelo SET destino = ?, fecha_salida = ?, hora_salida = ?, fk_id_avion = ? WHERE id_vuelo = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Se le pasa posicion y dato al statement
            preparedStatement.setString(1, plane.getDestiny());
            preparedStatement.setDate(2, plane.getOut_date());
            preparedStatement.setTime(3, plane.getOut_hour());
            preparedStatement.setInt(4, plane.getFk_id_airplane());
            preparedStatement.setInt(5, plane.getId_fly());

            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0)
            {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null , "Model Plane Updating Error --> " + e.getMessage());
        }
        finally
        {
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

    public Vuelo findById(int id_vuelo)
    {
        Connection conexion = ConfigDB.openConnection();

        //Global
        Vuelo plane = null;

        try
        {
            String sqlQuery = "SELECT * FROM vuelo WHERE id_vuelo = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el ID al query
            preparedStatement.setInt(1, id_vuelo);

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            if(resultado.next())
            {
                plane = new Vuelo();

                plane.setId_fly(resultado.getInt("id_vuelo"));
                plane.setDestiny(resultado.getString("destino"));
                plane.setOut_date(resultado.getDate("fecha_salida"));
                plane.setOut_hour(resultado.getTime("hora_salida"));
                plane.setFk_id_airplane(resultado.getInt("fk_id_avion"));
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Plane model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return plane;
    }

    public Vuelo findByName(String planeDestiny)
    {
        Connection conexion = ConfigDB.openConnection();

        //Global
        Vuelo plane = null;

        try
        {
            String sqlQuery = "SELECT * FROM vuelo WHERE destino LIKE ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el nombre al query
            preparedStatement.setString(1, "%" + planeDestiny + "%");

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            if(resultado.next())
            {
                plane = new Vuelo();

                plane.setId_fly(resultado.getInt("id_vuelo"));
                plane.setDestiny(resultado.getString("destino"));
                plane.setOut_date(resultado.getDate("fecha_salida"));
                plane.setOut_hour(resultado.getTime("hora_salida"));
                plane.setFk_id_airplane(resultado.getInt("fk_id_avion"));

            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Plane model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return plane;
    }
}

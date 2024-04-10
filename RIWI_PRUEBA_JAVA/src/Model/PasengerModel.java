package Model;

import Entity.Pasajero;
import database.CRUD;
import database.ConfigDB;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PasengerModel implements CRUD
{
    @Override
    public Object create(Object object) {

        Connection conexion = ConfigDB.openConnection();

        Pasajero pasenger = (Pasajero) object;

        try {
            //El id se pone automatico por la base de datos
            String sql = "INSERT INTO pasajero (nombre, apellido, documento_identidad) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = conexion.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,pasenger.getName());
            preparedStatement.setString(2,pasenger.getSurname());
            preparedStatement.setString(3,pasenger.getPasenger_document());

            preparedStatement.execute();

            ResultSet respuesta = preparedStatement.getGeneratedKeys();

            //Le damos el id correspondiente a cada nueva entidad
            while (respuesta.next()){
                pasenger.setId_pasenger(respuesta.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "New Pasenger Added.");

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ConfigDB.closeConnection();

        return pasenger;
    }

    @Override
    public List<Object> list() {

        Connection conexion = ConfigDB.openConnection();

        List<Object> pasengerList = new ArrayList<>();

        try
        {
            String sqlQuery = "SELECT * FROM pasajero;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {

                Pasajero pasenger = new Pasajero();

                //Los datos que vamos a listar se lo pasamos a la entidad
                pasenger.setId_pasenger(resultado.getInt("id_pasajero"));
                pasenger.setName(resultado.getString("nombre"));
                pasenger.setSurname(resultado.getString("apellido"));
                pasenger.setPasenger_document(resultado.getString("documento_identidad"));

                pasengerList.add(pasenger);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Model Pasenger Error" + error.getMessage());
        }

        ConfigDB.closeConnection();

        return pasengerList;
    }

    @Override
    public boolean delete(Object object)
    {
        Pasajero pasenger = (Pasajero) object;

        Connection  conexion = ConfigDB.openConnection();

        boolean isDeleted = false;

        try
        {
            String sqlQuery = "DELETE FROM pasajero WHERE id_pasajero = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, pasenger.getId_pasenger());

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
            JOptionPane.showMessageDialog(null, "Error in delete Model: " +  e.getMessage());
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
            Pasajero pasenger = (Pasajero) object;

            String sqlQuery = "UPDATE pasajero SET nombre = ?, apellido = ?, documento_identidad = ? WHERE id_pasajero = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            //Se le pasa posicion y dato al statement
            preparedStatement.setInt(4, pasenger.getId_pasenger());
            preparedStatement.setString(1, pasenger.getName());
            preparedStatement.setString(2, pasenger.getSurname());
            preparedStatement.setString(3, pasenger.getPasenger_document());

            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0)
            {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null , "Model Pasenger Updating Error --> " + e.getMessage());
        }
        finally
        {
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

    //No viene del CRUD, es propio
    public Pasajero findByName(String pasengerName)
    {
        Connection conexion = ConfigDB.openConnection();

        //Global
        Pasajero pasenger = null;

        try
        {
            String sqlQuery = "SELECT * FROM pasajero WHERE nombre LIKE ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el nombre al query
            preparedStatement.setString(1, "%" + pasengerName + "%");

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            if(resultado.next())
            {
                pasenger = new Pasajero();

                pasenger.setId_pasenger(resultado.getInt("id_pasajero"));
                pasenger.setName(resultado.getString("nombre"));
                pasenger.setSurname(resultado.getString("apellido"));
                pasenger.setPasenger_document(resultado.getString("documento_identidad"));

            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Pasenger model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return pasenger;
    }

    public Pasajero findById(int id_pasenger)
    {
        Connection conexion = ConfigDB.openConnection();

        //Global
        Pasajero pasenger = null;

        try
        {
            String sqlQuery = "SELECT * FROM pasajero WHERE id_pasajero = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el ID al query
            preparedStatement.setInt(1, id_pasenger);

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            if(resultado.next())
            {
                pasenger = new Pasajero();

                pasenger.setId_pasenger(resultado.getInt("id_pasajero"));
                pasenger.setName(resultado.getString("nombre"));
                pasenger.setSurname(resultado.getString("apellido"));
                pasenger.setPasenger_document(resultado.getString("documento_identidad"));

            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Pasenger model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return pasenger;
    }
}

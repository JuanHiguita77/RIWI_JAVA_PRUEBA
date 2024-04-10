package Model;

import Entity.Empresa;
import database.CRUD;
import database.ConfigDB;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaModel implements CRUD
{
    @Override
    public Object create(Object object)
    {

        Connection conexion = ConfigDB.openConnection();

        Empresa enterprise = (Empresa) object;

        try {
            //El id se pone automatico por la base de datos
            String sql = "INSERT INTO empresa (nombre, sector, ubicacion, contacto) VALUES (?, ?, ?, ?);";

            PreparedStatement preparedStatement = conexion.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,enterprise.getName());
            preparedStatement.setString(2,enterprise.getSector());
            preparedStatement.setString(3,enterprise.getUbication());
            preparedStatement.setString(4,enterprise.getContact());

            preparedStatement.execute();

            ResultSet respuesta = preparedStatement.getGeneratedKeys();

            //Le damos el id correspondiente a cada nueva entidad
            while (respuesta.next()){
                enterprise.setId_empresa(respuesta.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "New Enterprise Added.");

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ConfigDB.closeConnection();

        return enterprise;
    }

    @Override
    public List<Object> list()
    {

        Connection conexion = ConfigDB.openConnection();

        List<Object> enterpriseList = new ArrayList<>();

        try
        {
            String sqlQuery = "SELECT * FROM empresa;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {

                Empresa enterprise = new Empresa();

                enterprise.setId_empresa(resultado.getInt("id_empresa"));
                enterprise.setName(resultado.getString("nombre"));
                enterprise.setSector(resultado.getString("sector"));
                enterprise.setUbication(resultado.getString("ubicacion"));
                enterprise.setContact(resultado.getString("contacto"));

                enterpriseList.add(enterprise);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Model Enterprise Error" + error.getMessage());
        }

        ConfigDB.closeConnection();

        return enterpriseList;
    }

    @Override
    public boolean delete(Object object)
    {
        Empresa enterprise = (Empresa) object;

        Connection conexion = ConfigDB.openConnection();

        boolean isDeleted = false;

        try
        {
            String sqlQuery = "DELETE FROM empresa WHERE id_empresa = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, enterprise.getId_empresa());

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
    public boolean update(Object object)
    {
        Connection conexion = ConfigDB.openConnection();

        boolean isUpdated = false;

        try
        {
            Empresa enterprise = (Empresa) object;

            String sqlQuery = "UPDATE empresa SET nombre = ?, sector = ?, ubicacion = ?, contacto = ? WHERE id_empresa = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Se le pasa posicion y dato al statement
            preparedStatement.setString(1, enterprise.getName());
            preparedStatement.setString(2, enterprise.getSector());
            preparedStatement.setString(3, enterprise.getUbication());
            preparedStatement.setString(4, enterprise.getContact());
            preparedStatement.setInt(5, enterprise.getId_empresa());

            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0)
            {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null , "Model Enterprise Updating Error --> " + e.getMessage());
        }
        finally
        {
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

}

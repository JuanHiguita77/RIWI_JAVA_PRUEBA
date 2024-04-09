package Model;

import Entity.Coder;
import Entity.Vacante;
import database.CRUD;
import database.ConfigDB;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacanteModel implements CRUD
{
    @Override
    public Object create(Object object)
    {

        Connection conexion = ConfigDB.openConnection();

        Vacante vacant = (Vacante) object;

        try {
            //El id se pone automatico por la base de datos
            String sql = "INSERT INTO vacante (fk_id_empresa, titulo, descripcion, duracion, estado, tecnologia, clan) VALUES (?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = conexion.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,vacant.getId_empresa());
            preparedStatement.setString(2,vacant.getTitle());
            preparedStatement.setString(3,vacant.getDescription());
            preparedStatement.setString(4,vacant.getDuration());
            preparedStatement.setString(5,vacant.getState());
            preparedStatement.setString(6,vacant.getTechnology());
            preparedStatement.setString(7,vacant.getClan());

            preparedStatement.execute();

            ResultSet respuesta = preparedStatement.getGeneratedKeys();

            //Le damos el id correspondiente a cada nueva entidad
            while (respuesta.next()){
                vacant.setId_vacante(respuesta.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "New Vacant Added.");

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ConfigDB.closeConnection();

        return vacant;
    }

    @Override
    public List<Object> list()
    {

        Connection conexion = ConfigDB.openConnection();

        List<Object> vacantList = new ArrayList<>();

        try
        {
            String sqlQuery = "SELECT * FROM vacante;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {

                Vacante vacant = new Vacante();

                vacant.setId_vacante(resultado.getInt("vacante.id_vacante"));
                vacant.setId_empresa(resultado.getInt("vacante.fk_id_empresa"));
                vacant.setTitle(resultado.getString("vacante.titulo"));
                vacant.setDescription(resultado.getString("vacante.descripcion"));
                vacant.setDuration(resultado.getString("vacante.duracion"));
                vacant.setState(resultado.getString("vacante.estado"));
                vacant.setTechnology(resultado.getString("vacante.tecnologia"));
                vacant.setClan(resultado.getString("vacante.clan"));

                vacantList.add(vacant);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Model Error in list metod" + error.getMessage());
        }

        ConfigDB.closeConnection();

        return vacantList;
    }

    @Override
    public boolean delete(Object object)
    {
        Vacante vacant = (Vacante) object;

        Connection  conexion = ConfigDB.openConnection();

        boolean isDeleted = false;

        try
        {
            String sqlQuery = "DELETE FROM vacante WHERE id_vacante = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, vacant.getId_vacante());

            int resultadoFilasAfectadas = preparedStatement.executeUpdate();

            if (resultadoFilasAfectadas > 0)
            {
                isDeleted = true;
                JOptionPane.showMessageDialog(null, "Deleted Success!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Dont deleted x.x");
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error deleting in Vacant Model: " +  e.getMessage());
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
            Vacante vacant = (Vacante) object;

            String sqlQuery = "UPDATE vacante SET fk_id_empresa = ?, titulo = ?, descripcion = ?, duracion = ?, estado = ?, tecnologia = ?, clan = ? WHERE id_vacante = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            //Se le pasa posicion y dato al statement

            preparedStatement.setInt(1, vacant.getId_empresa());
            preparedStatement.setString(2, vacant.getTitle());
            preparedStatement.setString(3, vacant.getDescription());
            preparedStatement.setString(4, vacant.getDuration());
            preparedStatement.setString(5, vacant.getState());
            preparedStatement.setString(6, vacant.getTechnology());
            preparedStatement.setString(7, vacant.getClan());
            preparedStatement.setInt(8, vacant.getId_vacante());


            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0)
            {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null , "Model Vacant Updating Error --> " + e.getMessage());
        }
        finally
        {
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

    public List<Object> findByTitleAndTech(String title, String tech)
    {
        Connection conexion = ConfigDB.openConnection();

        List<Object> vacantList = new ArrayList();

        //Global
        Vacante vacant = null;

        try
        {
            String sqlQuery = "SELECT * FROM vacante WHERE titulo LIKE ? AND tecnologia LIKE ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el ID al query
            preparedStatement.setString(1, "%" + title + "%");
            preparedStatement.setString(2, "%" + tech + "%");

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            while(resultado.next())
            {
                vacant = new Vacante();

                vacant.setId_vacante(resultado.getInt("vacante.id_vacante"));
                vacant.setId_empresa(resultado.getInt("vacante.fk_id_empresa"));
                vacant.setTitle(resultado.getString("vacante.titulo"));
                vacant.setDescription(resultado.getString("vacante.descripcion"));
                vacant.setDuration(resultado.getString("vacante.duracion"));
                vacant.setState(resultado.getString("vacante.estado"));
                vacant.setTechnology(resultado.getString("vacante.tecnologia"));
                vacant.setClan(resultado.getString("vacante.clan"));

                vacantList.add(vacant);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Vacant model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return vacantList;
    }

}

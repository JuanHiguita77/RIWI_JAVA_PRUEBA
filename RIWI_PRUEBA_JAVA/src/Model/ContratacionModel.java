package Model;

import Entity.Contratacion;
import database.CRUD;
import database.ConfigDB;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContratacionModel implements CRUD
{
    @Override
    public Object create(Object object)
    {

        Connection conexion = ConfigDB.openConnection();

        Contratacion contract = (Contratacion) object;

        try {
            //El id se pone automatico por la base de datos
            String sql = "INSERT INTO contratacion (fk_id_vacante, fk_id_coder, fecha_aplicacion, estado, salario) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = conexion.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,contract.getId_vacante());
            preparedStatement.setInt(2,contract.getId_coder());
            preparedStatement.setDate(3,contract.getAplication_date());
            preparedStatement.setString(4,contract.getState());
            preparedStatement.setDouble(5,contract.getSalary());


            preparedStatement.execute();

            ResultSet respuesta = preparedStatement.getGeneratedKeys();

            //Le damos el id correspondiente a cada nueva entidad
            while (respuesta.next()){
                contract.setId_contratacion(respuesta.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "New Contract Realized.");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ConfigDB.closeConnection();

        return contract;
    }

    @Override
    public List<Object> list()
    {

        Connection conexion = ConfigDB.openConnection();

        List<Object> contractList = new ArrayList<>();

        try
        {
            String sqlQuery = "SELECT * FROM contratacion;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {

                Contratacion contract = new Contratacion();
                
                contract.setAplication_date(resultado.getDate("fecha_aplicacion"));
                contract.setState(resultado.getString("estado"));
                contract.setSalary(resultado.getInt("salario"));
                contract.setId_vacante(resultado.getInt("fk_id_vacante"));
                contract.setId_coder(resultado.getInt("fk_id_coder"));
                contract.setId_contratacion(resultado.getInt("id_contratacion"));

                contractList.add(contract);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Model Contract Error" + error.getMessage());
        }

        ConfigDB.closeConnection();

        return contractList;
    }

    @Override
    public boolean delete(Object object)
    {
        Contratacion contract = (Contratacion) object;

        Connection  conexion = ConfigDB.openConnection();

        boolean isDeleted = false;

        try
        {
            String sqlQuery = "DELETE FROM contratacion WHERE id_contratacion = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, contract.getId_contratacion());

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
            JOptionPane.showMessageDialog(null, "Error deleting in Contract Model: " +  e.getMessage());
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
            Contratacion contract = (Contratacion) object;

            String sqlQuery = "UPDATE contratacion SET fecha_aplicacion = ?, estado = ?, salario = ?, fk_id_vacante = ?, fk_id_coder = ? WHERE id_contratacion = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Se le pasa posicion y dato al statement
            preparedStatement.setDate(1, contract.getAplication_date());
            preparedStatement.setString(2, contract.getState());
            preparedStatement.setDouble(3, contract.getSalary());
            preparedStatement.setInt(4, contract.getId_vacante());
            preparedStatement.setInt(5, contract.getId_coder());
            preparedStatement.setInt(6, contract.getId_contratacion());

            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0)
            {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null , "Model Contract Updating Error --> " + e.getMessage());
        }
        finally
        {
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

    public boolean verifyOfert(String description)
    {
        Connection conexion = ConfigDB.openConnection();

        boolean resultado = true;

        try
        {
            String sqlQuery = "SELECT * FROM contratacion JOIN coder ON contratacion.fk_id_coder = coder.id_coder JOIN vacante ON contratacion.fk_id_vacante = vacante.id_vacante WHERE coder.cv LIKE ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setString(1, "%" + description + "%");

            resultado = preparedStatement.execute();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Contract model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return resultado;
    }
}

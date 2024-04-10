package Model;

import Entity.Reservacion;
import database.CRUD;
import database.ConfigDB;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel implements CRUD
{
    @Override
    public Object create(Object object) {

        Connection conexion = ConfigDB.openConnection();

        Reservacion reservation = (Reservacion) object;

        try {
            //El id se pone automatico por la base de datos
            String sql = "INSERT INTO reservacion (id_reservacion, fecha_reservacion, asiento, fk_id_pasajero, fk_id_vuelo) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = conexion.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,reservation.getId_reservation());
            preparedStatement.setDate(2,reservation.getReservation_date());
            preparedStatement.setString(3,reservation.getSeat());
            preparedStatement.setInt(4,reservation.getFk_id_pasenger());
            preparedStatement.setInt(5,reservation.getFk_id_fly());

            preparedStatement.execute();

            ResultSet respuesta = preparedStatement.getGeneratedKeys();

            //Le damos el id correspondiente a cada nueva entidad
            while (respuesta.next()){
                reservation.setId_reservation(respuesta.getInt(1));
            }

            JOptionPane.showMessageDialog(null, "New Reservation Added.");

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        ConfigDB.closeConnection();

        return reservation;
    }

    @Override
    public List<Object> list() {

        Connection conexion = ConfigDB.openConnection();

        List<Object> reservationList = new ArrayList<>();

        try
        {
            String sqlQuery = " SELECT r.*, p.nombre, p.apellido , p.documento_identidad, v.destino, v.fecha_salida, v.hora_salida FROM reservacion r JOIN pasajero p ON r.fk_id_pasajero = p.id_pasajero JOIN vuelo v ON r.fk_id_vuelo = v.id_vuelo;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {

                Reservacion reservation = new Reservacion();

                // Para la tabla reservacion
                reservation.setId_reservation(resultado.getInt("id_reservacion"));
                reservation.setReservation_date(resultado.getDate("fecha_reservacion"));
                reservation.setSeat(resultado.getString("asiento"));

                // Para la tabla pasajero
                reservation.setFk_id_pasenger(resultado.getInt("fk_id_pasajero"));
                reservation.setPassenger_name(resultado.getString("nombre"));
                reservation.setPassenger_surname(resultado.getString("apellido"));
                reservation.setPassenger_document(resultado.getString("documento_identidad"));

                // Para la tabla vuelo
                reservation.setFk_id_fly(resultado.getInt("fk_id_vuelo"));
                reservation.setDestination(resultado.getString("destino"));
                reservation.setOut_date(resultado.getDate("fecha_salida"));
                reservation.setOut_time(resultado.getTime("hora_salida"));

                reservationList.add(reservation);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Model Reservation Error in list metod " + error.getMessage());
        }

        ConfigDB.closeConnection();

        return reservationList;
    }

    @Override
    public boolean delete(Object object)
    {
        Reservacion reservation = (Reservacion) object;

        Connection  conexion = ConfigDB.openConnection();

        boolean isDeleted = false;

        try
        {
            String sqlQuery = "DELETE FROM reservacion WHERE id_reservacion = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, reservation.getId_reservation());

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
            JOptionPane.showMessageDialog(null, "Error deleting in Reservation Model: " +  e.getMessage());
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
            Reservacion reservation = (Reservacion) object;

            String sqlQuery = "UPDATE reservacion SET fecha_reservacion = ?, asiento = ?, fk_id_pasajero = ?, fk_id_vuelo = ? WHERE id_reservacion = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            //Se le pasa posicion y dato al statement
            preparedStatement.setDate(1, reservation.getReservation_date());
            preparedStatement.setString(2, reservation.getSeat());
            preparedStatement.setInt(3, reservation.getFk_id_pasenger());
            preparedStatement.setInt(4, reservation.getFk_id_fly());
            preparedStatement.setInt(5, reservation.getId_reservation());

            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0)
            {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null , "Model Reservation Updating Error --> " + e.getMessage());
        }
        finally
        {
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

    public Reservacion findById(int id_reservation)
    {
        Connection conexion = ConfigDB.openConnection();

        Reservacion reservation = null;

        try
        {
            String sqlQuery = "SELECT * FROM reservacion WHERE id_reservacion = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            //Le pasamos el ID al query
            preparedStatement.setInt(1, id_reservation);

            ResultSet resultado = preparedStatement.executeQuery();

            //Asignamos los datos encontrados
            if(resultado.next())
            {
                reservation = new Reservacion();

                reservation.setId_reservation(resultado.getInt("id_reservacion"));
                reservation.setReservation_date(resultado.getDate("fecha_reservacion"));
                reservation.setSeat(resultado.getString("asiento"));

            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Reservation model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return reservation;
    }

    public List<Object> findReservationsInPlane(int id_plane) {

        Connection conexion = ConfigDB.openConnection();

        List<Object> reservationList = new ArrayList<>();

        try
        {
            String sqlQuery = " SELECT r.*, p.*, v.* FROM reservacion r JOIN pasajero p ON r.fk_id_pasajero = p.id_pasajero JOIN vuelo v ON r.fk_id_vuelo = v.id_vuelo WHERE fk_id_vuelo = ?;";

            PreparedStatement preparedStatement = conexion.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, id_plane);

            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next())
            {

                Reservacion reservation = new Reservacion();

                // Para la tabla reservacion
                reservation.setId_reservation(resultado.getInt("id_reservacion"));
                reservation.setReservation_date(resultado.getDate("fecha_reservacion"));
                reservation.setSeat(resultado.getString("asiento"));

                // Para la tabla pasajero
                reservation.setFk_id_pasenger(resultado.getInt("fk_id_pasajero"));
                reservation.setPassenger_name(resultado.getString("nombre"));
                reservation.setPassenger_surname(resultado.getString("apellido"));
                reservation.setPassenger_document(resultado.getString("documento_identidad"));

                // Para la tabla vuelo
                reservation.setFk_id_fly(resultado.getInt("fk_id_vuelo"));
                reservation.setDestination(resultado.getString("destino"));
                reservation.setOut_date(resultado.getDate("fecha_salida"));
                reservation.setOut_time(resultado.getTime("hora_salida"));

                reservationList.add(reservation);
            }
        }
        catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, "Model Reservation Error in list metod " + error.getMessage());
        }

        ConfigDB.closeConnection();

        return reservationList;
    }

    public boolean airplaneCapacity(int id_airplane)
    {
        Connection conexion = ConfigDB.openConnection();

        Reservacion reservation = null;

        int airplaneCapacity = 0;
        int ocupedSeats = 0;

        try
        {
            String sqlCapacity = " SELECT capacidad FROM vuelo JOIN avion ON vuelo.fk_id_avion = avion.id_avion WHERE vuelo.id_vuelo = ?";
            String sqlSeatOcuped = "SELECT COUNT(*) AS asientos_ocupados FROM reservacion WHERE fk_id_vuelo = ?";

            PreparedStatement preparedStatementcapacity = conexion.prepareStatement(sqlCapacity);
            PreparedStatement preparedStatementOcupedSeats = conexion.prepareStatement(sqlSeatOcuped);


            //Le pasamos el ID al query
            preparedStatementcapacity.setInt(1, id_airplane);

            ResultSet resultCapacity = preparedStatementcapacity.executeQuery();


            if (resultCapacity.next()) {
                airplaneCapacity = resultCapacity.getInt("capacidad");
            }

            preparedStatementOcupedSeats.setInt(1, id_airplane);

            ResultSet resultOcuped = preparedStatementOcupedSeats.executeQuery();


            if (resultOcuped.next())
            {
                ocupedSeats = resultOcuped.getInt("asientos_ocupados");
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Reservation capacity filter model error" + e.getMessage());
        }

        ConfigDB.closeConnection();

        return ocupedSeats < airplaneCapacity;
    }
}

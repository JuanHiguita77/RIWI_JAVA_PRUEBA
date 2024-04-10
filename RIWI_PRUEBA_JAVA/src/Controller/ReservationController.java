package Controller;

import Entity.Pasajero;
import Entity.Reservacion;
import Entity.Vuelo;
import Model.PasengerModel;
import Model.PlaneModel;
import Model.ReservationModel;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ReservationController
{
    //Listar factorizado para cualquier objeto de lista
    public static String listAll(List<Object> objectList)
    {
        String list = "--- RESERVATIONS LIST --- \n";

        for (Object reservation: objectList){
            Reservacion reservationNew = (Reservacion) reservation;
            list += reservationNew.toString() + "\n";
        }

        return list;
    }

    public static String listAllReservations()
    {
        ReservationModel reservationModel = new ReservationModel();
        String listReservations = "RESERVATION LIST \n";

        for (Object reservation: reservationModel.list()){

            Reservacion reservationNew = (Reservacion) reservation;
            listReservations += reservationNew.toString() + "\n";
        }

        return listReservations;
    }


    public static void listReservations()
    {
        ReservationModel reservationModel = new ReservationModel();

        JOptionPane.showMessageDialog(null, listAll(reservationModel.list()));
    }

    public static void delete()
    {
        ReservationModel reservationModel = new ReservationModel();

        String reservationList = listAllReservations();

        int id = Integer.parseInt(JOptionPane.showInputDialog(reservationList + "Input the Reservation ID to delete"));

        //Buscamos primero si existe
        Reservacion reservation = reservationModel.findById(id);

        if (reservation == null)
        {
            JOptionPane.showInputDialog(null,"Unknown Reservation");
        }
        else
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are you sure to delete? -- > " + reservation.toString());

            if (confirm == 1)
            {
                JOptionPane.showMessageDialog(null,"Stopped!");
            }
            else
            {
                reservationModel.delete(reservation);
                JOptionPane.showMessageDialog(null, "Deleted sucessfully! --> " + reservation.toString());
            }
        }
    }

    public static void update()
    {
        ReservationModel reservationModel = new ReservationModel();
        PasengerModel pasengerModel = new PasengerModel();
        PlaneModel planeModel = new PlaneModel();

        String reservationList = listAllReservations();

        int idUpdated = Integer.parseInt( JOptionPane.showInputDialog(reservationList + "Enter Reservation ID to edit"));

        Reservacion reservation = reservationModel.findById(idUpdated);

        if (reservation == null)
        {
            JOptionPane.showMessageDialog(null, "Unknown Reservation");
        }
        else
        {
            Date reservationDate = Date .valueOf(JOptionPane.showInputDialog("Input the reservation name or leave default name", reservation.getReservation_date()));
            String seat = JOptionPane.showInputDialog("Input the reservation surname or leave default", reservation.getSeat());
            int fk_id_passenger = Integer.parseInt(JOptionPane.showInputDialog("Input the Passenger ID or leave default", reservation.getFk_id_pasenger()));
            int fk_id_fly = Integer.parseInt(JOptionPane.showInputDialog("Input the Plane ID or leave default", reservation.getFk_id_fly()));

            Pasajero passengerFinded = pasengerModel.findById(fk_id_passenger);
            Vuelo planeFinded = planeModel.findById(fk_id_fly);

            reservation.setReservation_date(reservationDate);
            reservation.setSeat(seat);
            reservation.setFk_id_pasenger(fk_id_passenger);
            reservation.setFk_id_fly(fk_id_fly);

            reservation.setPassenger_name(passengerFinded.getName());
            reservation.setPassenger_surname(passengerFinded.getSurname());
            reservation.setPassenger_document(passengerFinded.getPasenger_document());

            reservation.setDestination(planeFinded.getDestiny());
            reservation.setOut_date(planeFinded.getOut_date());
            reservation.setOut_time(planeFinded.getOut_hour());

            reservationModel.update(reservation);
        }
    }
    
    public static void create(){

        ReservationModel reservationModel = new ReservationModel();
        PasengerModel pasengerModel = new PasengerModel();
        PlaneModel planeModel = new PlaneModel();

        Reservacion reservation = new Reservacion();

        Date reservationDate = Date.valueOf(JOptionPane.showInputDialog("Insert Reservation Date"));
        int fk_id_fly = Integer.parseInt(JOptionPane.showInputDialog(planeModel.list() + "\n Insert Plane ID"));

        if (!reservationModel.airplaneCapacity(fk_id_fly))
        {
            JOptionPane.showMessageDialog(null, "Exced the Airplane capacity");
            return;
        }

        String seat = JOptionPane.showInputDialog("Insert Seat Reservation");
        int fk_id_passenger = Integer.parseInt(JOptionPane.showInputDialog(pasengerModel.list() + "\n Insert Passenger ID"));

        reservation.setReservation_date(reservationDate);
        reservation.setFk_id_fly(fk_id_fly);
        reservation.setSeat(seat);
        reservation.setFk_id_pasenger(fk_id_passenger);

        Pasajero passengerFinded = pasengerModel.findById(fk_id_passenger);
        Vuelo planeFinded = planeModel.findById(fk_id_fly);

        reservation.setPassenger_name(passengerFinded.getName());
        reservation.setPassenger_surname(passengerFinded.getSurname());
        reservation.setPassenger_document(passengerFinded.getPasenger_document());

        reservation.setDestination(planeFinded.getDestiny());
        reservation.setOut_date(planeFinded.getOut_date());
        reservation.setOut_time(planeFinded.getOut_hour());

        reservation = (Reservacion) reservationModel.create(reservation);

        JOptionPane.showMessageDialog(null, reservation.toString());
    }

    public static void findAllReservationInPlane()
    {
        ReservationModel reservationModel = new ReservationModel();
        PlaneModel planeModel = new PlaneModel();

        String listReservations = "--- ALL PLANES FINDED --- \n";

        int id_plane = Integer.parseInt(JOptionPane.showInputDialog(planeModel.list() + "\nType the ID Plane name to search All Reservations"));

        for (Object reservation: reservationModel.findReservationsInPlane(id_plane))
        {
            Reservacion newReservation = (Reservacion) reservation;
            listReservations += newReservation.toString() + "\n";
        }

        JOptionPane.showMessageDialog(null ,listReservations);
    }
}

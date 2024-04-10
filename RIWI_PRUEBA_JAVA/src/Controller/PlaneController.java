package Controller;

import Entity.Avion;
import Entity.Vuelo;
import Entity.Vuelo;
import Model.AirplaneModel;
import Model.PlaneModel;
import Model.PlaneModel;

import javax.swing.JOptionPane;
import java.sql.Time;
import java.sql.Date;
import java.util.List;

public class PlaneController
{
    //Listar factorizado para cualquier objeto de lista
    public static String listAll(List<Object> objectList)
    {
        String list = "--- PLANES LIST --- \n";

        for (Object plane: objectList){
            Vuelo planeNew = (Vuelo) plane;
            list += planeNew.toString() + "\n";
        }

        return list;
    }

    public static String listAllPlans()
    {
        PlaneModel planeModel = new PlaneModel();

        String listPlanes = "PLANES LIST \n";

        for (Object plane: planeModel.list())
        {
            Vuelo planeNew = (Vuelo) plane;
            listPlanes += planeNew.toString() + "\n";
        }

        //Devuelve un string
        return listPlanes;
    }

    public static void listPlanes()
    {
        PlaneModel planeModel = new PlaneModel();
        AirplaneModel airplaneModel = new AirplaneModel();

        JOptionPane.showMessageDialog(null, listAll(planeModel.list()));
    }

    public static void delete()
    {
        PlaneModel planeModel = new PlaneModel();
        AirplaneModel airplaneModel = new AirplaneModel();

        String planeList = listAllPlans();

        int id = Integer.parseInt(JOptionPane.showInputDialog(planeList + "Input the Plane ID to delete"));

        //Buscamos primero si existe
        Vuelo plane = planeModel.findById(id);

        Avion airplane = airplaneModel.findById(plane.getFk_id_airplane());

        if (plane == null)
        {
            JOptionPane.showInputDialog(null,"Unknown Plane");
        }
        else
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are you sure to delete? -- > " + plane.toString());

            if (confirm == 1)
            {
                JOptionPane.showMessageDialog(null,"Stopped!");
            }
            else
            {
                planeModel.delete(plane);
                JOptionPane.showMessageDialog(null, "Deleted sucessfully! --> " + plane.toString() + airplane.toString());
            }
        }
    }

    public static void update()
    {
        PlaneModel planeModel = new PlaneModel();
        AirplaneModel airplaneModel = new AirplaneModel();

        String planeList = listAllPlans();

        int idUpdated = Integer.parseInt( JOptionPane.showInputDialog(planeList + "Enter Plane ID to edit"));

        Vuelo plane = planeModel.findById(idUpdated);

        if (plane == null)
        {
            JOptionPane.showMessageDialog(null, "Unknown Plane");
        }
        else
        {
            String destiny = JOptionPane.showInputDialog("Input the plane destiny or leave default", plane.getDestiny());
            Date out_date = Date.valueOf(JOptionPane.showInputDialog("Input the out date or leave default", plane.getOut_date()));
            Time out_hour = Time.valueOf(JOptionPane.showInputDialog("Input the out hour or leave default", plane.getOut_hour()));
            int fk_id_airplane = Integer.parseInt(JOptionPane.showInputDialog(airplaneModel.list() + "Input the Airplane ID or leave default", plane.getFk_id_airplane()));

            plane.setDestiny(destiny);
            plane.setOut_date(out_date);
            plane.setOut_hour(out_hour);
            plane.setFk_id_airplane(fk_id_airplane);

            planeModel.update(plane);
        }
    }

    public static void create(){

        PlaneModel planeModel = new PlaneModel();
        AirplaneModel airplaneModel = new AirplaneModel();

        Vuelo plane = new Vuelo();

        String destiny = JOptionPane.showInputDialog("Insert plane Destiny");
        Date out_date = Date.valueOf(JOptionPane.showInputDialog("Insert out Date 'YYYY-MM-DD'"));
        Time out_time = Time.valueOf(JOptionPane.showInputDialog("Insert out Time 'HH:MM:SS'"));
        int fk_id_airplane = Integer.parseInt(JOptionPane.showInputDialog(airplaneModel.list() + "Insert ID Airplane if exist"));

        plane.setDestiny(destiny);
        plane.setOut_date(out_date);
        plane.setOut_hour(out_time);
        plane.setFk_id_airplane(fk_id_airplane);

        //Pasamos el objeto a medico
        plane = (Vuelo) planeModel.create(plane);

        JOptionPane.showMessageDialog(null, plane.toString());
    }

    public static void findByDestinyName()
    {
        PlaneModel planeModel = new PlaneModel();
        AirplaneModel airplaneModel = new AirplaneModel();

        Vuelo plane = new Vuelo();

        String destiny = JOptionPane.showInputDialog("Input the Plane Destination to search");

        Vuelo airplaneReceived = planeModel.findByName(destiny);
        Avion planeReceived = airplaneModel.findById(airplaneReceived.getFk_id_airplane());

        if (airplaneReceived == null)
        {
            JOptionPane.showMessageDialog(null, "Destination not available");
        }
        else
        {
            plane.setId_fly(airplaneReceived.getId_fly());
            plane.setDestiny(airplaneReceived.getDestiny());
            plane.setOut_date(airplaneReceived.getOut_date());
            plane.setOut_hour(airplaneReceived.getOut_hour());
            plane.setFk_id_airplane(airplaneReceived.getFk_id_airplane());

            JOptionPane.showMessageDialog(null, plane.toString() + " " + planeReceived.toString());
        }
    }
}

package Controller;

import Entity.Avion;
import Model.AirplaneModel;

import javax.swing.JOptionPane;
import java.util.List;

public class AirplaneController 
{
    //Listar factorizado para cualquier objeto de lista
    public static String listAll(List<Object> objectList)
    {
        String list = "--- AIRPLANE LIST --- \n";

        for (Object airplane: objectList){
            Avion airplaneNew = (Avion) airplane;
            list += airplaneNew.toString() + "\n";
        }

        return list;
    }

    public static void listAirplanes()
    {
        AirplaneModel airplaneModel = new AirplaneModel();

        JOptionPane.showMessageDialog(null, listAll(airplaneModel.list()));
    }

    public static String lsitAllAirplanes()
    {
        AirplaneModel airplaneModel = new AirplaneModel();
        String listAirplanes = "AIRPLANE LIST \n";

        for (Object airplane: airplaneModel.list()){

            Avion airplaneNew = (Avion) airplane;
            listAirplanes += airplaneNew.toString() + "\n";
        }

        return listAirplanes;
    }

    public static void delete()
    {
        AirplaneModel airplaneModel = new AirplaneModel();

        String airplaneList = lsitAllAirplanes();

        int id = Integer.parseInt(JOptionPane.showInputDialog(airplaneList + "Input the Airplane ID to delete"));

        //Buscamos primero si existe
        Avion airplane = airplaneModel.findById(id);

        if (airplane == null)
        {
            JOptionPane.showInputDialog(null,"Unknown Airplane");
        }
        else
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are you sure to delete? -- > " + airplane.toString());

            if (confirm == 1)
            {
                JOptionPane.showMessageDialog(null,"Stopped!");
            }
            else
            {
                airplaneModel.delete(airplane);
                JOptionPane.showMessageDialog(null, "Deleted sucessfully! --> " + airplane.toString());
            }
        }
    }

    public static void update()
    {
        AirplaneModel airplaneModel = new AirplaneModel();

        String airplaneList = lsitAllAirplanes();

        int idUpdated = Integer.parseInt( JOptionPane.showInputDialog(airplaneList + "Enter Airplane ID to edit"));

        Avion airplane = airplaneModel.findById(idUpdated);

        if (airplane == null)
        {
            JOptionPane.showMessageDialog(null, "Unknown Airplane");
        }
        else
        {
            String model = JOptionPane.showInputDialog("Input the Airplane´s model or leave default", airplane.getModel());
            int capacity  = Integer.parseInt( JOptionPane.showInputDialog("Input the Airplane´s Capacity or leave default", airplane.getCapacity()));

            airplane.setModel(model);
            airplane.setCapacity(capacity);

            airplaneModel.update(airplane);
        }
    }

    public static void create(){

        AirplaneModel airplaneModel = new AirplaneModel();
        Avion airplane = new Avion();

        String model = JOptionPane.showInputDialog("Insert airplane Model");
        int capacity = Integer.parseInt(JOptionPane.showInputDialog("Insert airplane capacity"));

        airplane.setModel(model);
        airplane.setCapacity(capacity);

        //Pasamos el objeto a cita
        airplane = (Avion) airplaneModel.create(airplane);

        JOptionPane.showMessageDialog(null, airplane.toString());
    }

}

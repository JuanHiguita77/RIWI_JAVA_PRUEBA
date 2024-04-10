package Controller;

import Entity.Pasajero;
import Model.PasengerModel;

import javax.swing.JOptionPane;
import java.util.List;

public class PasengerController
{

    //Listar factorizado para cualquier objeto de lista
    public static String listAll(List<Object> objectList)
    {
        String list = "--- PASENGERS LIST --- \n";

        for (Object pasenger: objectList){
            Pasajero pasengerNew = (Pasajero) pasenger;
            list += pasengerNew.toString() + "\n";
        }

        return list;
    }
    //Metodo controlador principal para el menu
    public static void listPasengers()
    {
        PasengerModel pasengerModel = new PasengerModel();

        JOptionPane.showMessageDialog(null, listAll(pasengerModel.list()));
    }

    public static String listAllPasengers()
    {
        PasengerModel pasengerModel = new PasengerModel();
        String listPasengers = "PASENGERS LIST \n";

        for (Object pasenger: pasengerModel.list())
        {
            Pasajero pasengerNew = (Pasajero) pasenger;
            listPasengers += pasengerNew.toString() + "\n";
        }

        return listPasengers;
    }

    public static void delete()
    {
        PasengerModel pasengerModel = new PasengerModel();

        String pasengersList = listAllPasengers();

        int id = Integer.parseInt(JOptionPane.showInputDialog(pasengersList + "Input the Pasenger ID to delete"));

        //Buscamos primero si existe
        Pasajero pasenger = pasengerModel.findById(id);

        if (pasenger == null)
        {
            JOptionPane.showInputDialog(null,"Unknown Pasenger");
        }
        else
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are you sure to delete? -- > " + pasenger.toString());

            if (confirm == 1)
            {
                JOptionPane.showMessageDialog(null,"Stopped!");
            }
            else
            {
                pasengerModel.delete(pasenger);
                JOptionPane.showMessageDialog(null, "Deleted sucessfully! --> " + pasenger.toString());
            }
        }
    }

    public static void update()
    {
        PasengerModel pasengerModel = new PasengerModel();

        String pasengersList = listAllPasengers();

        int idUpdated = Integer.parseInt( JOptionPane.showInputDialog(pasengersList + "Enter Pasenger ID to edit"));

        Pasajero pasenger = pasengerModel.findById(idUpdated);

        if (pasenger == null)
        {
            JOptionPane.showMessageDialog(null, "Unknown Pasenger");
        }
        else
        {
            //Llenamos el objeto para enviarlo al modelo
            String name = JOptionPane.showInputDialog("Input the pasenger name or leave default name", pasenger.getName());
            String surname = JOptionPane.showInputDialog("Input the pasenger surname or leave default", pasenger.getSurname());
            String document = JOptionPane.showInputDialog("Input the pasenger document or leave default", pasenger.getPasenger_document());

            pasenger.setName(name);
            pasenger.setSurname(surname);
            pasenger.setPasenger_document(document);

            pasengerModel.update(pasenger);
        }
    }


    public static void create()
    {

        PasengerModel pasengerModel = new PasengerModel();
        Pasajero pasenger = new Pasajero();

        String name = JOptionPane.showInputDialog("Insert pasenger name");
        String surname = JOptionPane.showInputDialog("Insert pasenger surname");
        String document = JOptionPane.showInputDialog("Insert pasenger document");
        
        pasenger.setName(name);
        pasenger.setSurname(surname);
        pasenger.setPasenger_document(document);

        //Pasamos el objeto a tipo pasajero
        pasenger = (Pasajero) pasengerModel.create(pasenger);

        JOptionPane.showMessageDialog(null, pasenger.toString());
    }

    public static void findByName() 
    {
        PasengerModel pasengerModel = new PasengerModel();
        Pasajero pasenger = new Pasajero();

        String pasenger_name = JOptionPane.showInputDialog("Input the pasenger Name to search");

        Pasajero pasengerReceived = pasengerModel.findByName(pasenger_name);

        if (pasengerReceived == null) 
        {
            JOptionPane.showMessageDialog(null, "Name´s pasenger not Found");
        } 
        else 
        {
            pasenger.setId_pasenger(pasengerReceived.getId_pasenger());
            pasenger.setName(pasengerReceived.getName());
            pasenger.setSurname(pasengerReceived.getSurname());
            pasenger.setPasenger_document(pasengerReceived.getPasenger_document());

            JOptionPane.showMessageDialog(null, pasenger.toString());
        }
    }
}

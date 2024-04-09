package Controller;

import Entity.Empresa;
import Model.EmpresaModel;
import utils.Utils;

import javax.swing.JOptionPane;
import java.sql.Date;
import java.util.List;

public class EmpresaController
{

    public static EmpresaModel instanceEnterpriseModel()
    {
        return new EmpresaModel();
    }

    //Listar factorizado para cualquier objeto de lista
    public static String listAll(List<Object> objectList)
    {
        String list = "--- ENTERPRISES LIST --- \n";

        for (Object enterprise: objectList){
            Empresa enterpriseNew = (Empresa) enterprise;
            list += enterpriseNew.toString() + "\n";
        }

        return list;
    }

    public static void listEnterprises()
    {
        JOptionPane.showMessageDialog(null, listAll(instanceEnterpriseModel().list()));
    }

    public static void delete()
    {
        Object[] enterpriseOptions = Utils.listToarray(instanceEnterpriseModel().list());

        Empresa optionSelected = (Empresa) JOptionPane.showInputDialog(null,
                "Select Enterprise to Delete",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                enterpriseOptions,
                enterpriseOptions[0]);

        instanceEnterpriseModel().delete(optionSelected);
    }

    public static void update()
    {
        Object[] enterpriseOptions = Utils.listToarray(instanceEnterpriseModel().list());

        Empresa optionSelected = (Empresa) JOptionPane.showInputDialog(null,
                "Select Enterprise to Modify",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                enterpriseOptions,
                enterpriseOptions[0]);

        optionSelected.setName(JOptionPane.showInputDialog(null, "Input New Name or leave defult", optionSelected.getName()));
        optionSelected.setSector(JOptionPane.showInputDialog(null, "Input New Sector or leave defult", optionSelected.getSector()));
        optionSelected.setUbication(JOptionPane.showInputDialog(null, "Input New Ubication or leave defult", optionSelected.getUbication()));
        optionSelected.setContact(JOptionPane.showInputDialog(null, "Input New Contact or leave defult", optionSelected.getContact()));

        instanceEnterpriseModel().update(optionSelected);
    }


    public static void create()
    {
        String name = JOptionPane.showInputDialog("Insert enterprise Name");
        String sector = JOptionPane.showInputDialog("Insert enterprise Sector");
        String ubication = JOptionPane.showInputDialog("Insert enterprise Ubication");
        String contact = JOptionPane.showInputDialog("Insert enterprise Contact");

        instanceEnterpriseModel().create(new Empresa(name, sector, ubication, contact));
    }

    /*
    public static void findByDocument()
    {
        Empresa enterprise = new Empresa();

        String patient_document = JOptionPane.showInputDialog("Input the enterprise Document to search");

        Empresa patientReceived = instanceEnterpriseModel().findByDocument(patient_document);

        if (patientReceived == null)
        {
            JOptionPane.showMessageDialog(null, "Document´s Enterprise not Found");
        }
        else
        {
            enterprise.setId_paciente(patientReceived.getId_paciente());
            enterprise.setName(patientReceived.getName());
            enterprise.setSurname(patientReceived.getSurname());
            enterprise.setDate_birth(patientReceived.getDate_birth());
            enterprise.setDocument(patientReceived.getDocument());

            JOptionPane.showMessageDialog(null, enterprise.toString());
        }
    }*/
}

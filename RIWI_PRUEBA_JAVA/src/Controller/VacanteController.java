package Controller;

import Entity.Vacante;
import Entity.Coder;
import Entity.Empresa;
import Model.EmpresaModel;
import Model.VacanteModel;
import utils.Utils;

import javax.swing.JOptionPane;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class VacanteController 
{
    public static VacanteModel instanceModelVacant()
    {
        return new VacanteModel();
    }

    //Listar factorizado para cualquier objeto de lista
    public static String listAll(List<Object> objectList)
    {
        String list = "--- VACANTS LIST --- \n";

        for (Object vacant: objectList){
            Vacante vacantNew = (Vacante) vacant;
            list += vacantNew.toString() + "\n";
        }

        return list;
    }

    public static void listVacants()
    {
        JOptionPane.showMessageDialog(null, listAll(instanceModelVacant().list()));
    }

    public static void delete()
    {
        Object[] vacantList =  Utils.listToarray(instanceModelVacant().list());

        Vacante optionSelectedVacant = (Vacante) JOptionPane.showInputDialog(null,
                "Select a Vacant to Delete",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                vacantList,
                vacantList[0]);

        instanceModelVacant().delete(optionSelectedVacant);
    }

    public static void update()
    {
        Object[] vacantList = Utils.listToarray(VacanteController.instanceModelVacant().list());

        Vacante vacantSelected = (Vacante) JOptionPane.showInputDialog(null,
                "Select a Vacant to Update",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                vacantList,
                vacantList[0]);

        vacantSelected.setId_vacante(vacantSelected.getId_vacante());
        vacantSelected.setTitle( JOptionPane.showInputDialog(null, "Input New Title or leave Default", vacantSelected.getTitle()));
        vacantSelected.setDescription( JOptionPane.showInputDialog(null, "Input New Description or leave Default", vacantSelected.getDescription()));
        vacantSelected.setDuration( JOptionPane.showInputDialog(null, "Input New Duration or leave Default", vacantSelected.getDuration()));
        vacantSelected.setState( JOptionPane.showInputDialog(null, "Input New State 'ACTIVO' or 'INACTIVO' or leave Default", vacantSelected.getState()));
        vacantSelected.setTechnology( JOptionPane.showInputDialog(null, "Input New Technology or leave Default", vacantSelected.getTechnology()));
        vacantSelected.setClan( JOptionPane.showInputDialog(null, "Input New State Coder Clan or leave Default", vacantSelected.getClan()));

        instanceModelVacant().update(vacantSelected);
    }

    public static void create()
    {
        EmpresaController enterpriseController = new EmpresaController();

        Object[] enterprises = Utils.listToarray(enterpriseController.instanceEnterpriseModel().list());

         Empresa enterpriseSelected = (Empresa) JOptionPane.showInputDialog(null,
                "Select a Enterprise",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                enterprises,
                enterprises[0]);

        String title = JOptionPane.showInputDialog("Insert Title Vacant Title");
        String description = JOptionPane.showInputDialog("Insert vacant Description");
        String duration = JOptionPane.showInputDialog(null, "Insert Contract Duration");
        String state = JOptionPane.showInputDialog(null, "Insert Actual State 'ACTIVO' or 'INACTIVO'").toUpperCase();
        String techonology = JOptionPane.showInputDialog(null, "Insert Principal Technology");
        String clan = JOptionPane.showInputDialog(null, "Insert Actual Clan");

        instanceModelVacant().create(new Vacante(enterpriseSelected.getId_empresa() ,title, description, duration, state, techonology, clan));
    }

    public static void findByTitleAndTech()
    {
        String title = JOptionPane.showInputDialog(null, "Insert Vacant Title to search");
        String tech = JOptionPane.showInputDialog(null, "Insert principal vacant technology to search");

        String listVacants = "LIST VACANTS WITH " + title.toUpperCase() + "AND" + tech.toUpperCase() + " FINDED -- > \n";

        for (Object vacant: instanceModelVacant().findByTitleAndTech(title, tech))
        {
            Vacante vacantNew = (Vacante) vacant;
            listVacants += vacantNew.toString() + "\n";
        }

        JOptionPane.showMessageDialog(null ,listVacants);
    }

}

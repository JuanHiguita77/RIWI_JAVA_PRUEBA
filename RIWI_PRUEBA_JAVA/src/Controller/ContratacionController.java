package Controller;

import Entity.Coder;
import Entity.Contratacion;
import Entity.Empresa;
import Entity.Vacante;
import Model.ContratacionModel;
import utils.Utils;

import javax.swing.JOptionPane;
import javax.swing.plaf.multi.MultiToolTipUI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ContratacionController
{

    //Creamos la instancia del Modelo de donde sacaremos los metodos necesarios
    public static ContratacionModel instanceContractModel()
    {
        return new ContratacionModel();
    }

    //Listar factorizado para cualquier objeto de lista
    public static String listAll(List<Object> objectList)
    {
        String list = "--- CONTRACT LIST --- \n";

        for (Object contract: objectList){
            Contratacion contractNew = (Contratacion) contract;
            list += contractNew.toString() + "\n";
        }

        return list;
    }

    public static void listContracts()
    {
        JOptionPane.showMessageDialog(null, listAll(instanceContractModel().list()));
    }

    public static void delete()
    {
        Object[] contractsList = Utils.listToarray(instanceContractModel().list());

        Contratacion optionSelected = (Contratacion) JOptionPane.showInputDialog(null, 
                "Select a contract to delete", 
                "",
            JOptionPane.QUESTION_MESSAGE,
            null,
            contractsList,
            contractsList[0]);

        instanceContractModel().delete(optionSelected);
    }

    public static void update()
    {
        Object[] contractsList = Utils.listToarray(instanceContractModel().list());
        Object[] vacantList = Utils.listToarray(VacanteController.instanceModelVacant().list());
        Object[] codersList = Utils.listToarray(CoderController.instanceCoderModel().list());

        Contratacion optionSelected = (Contratacion) JOptionPane.showInputDialog(null,
                "Select a Contract To update",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                contractsList,
                contractsList[0]);

        Contratacion optionVacantSelected = (Contratacion) JOptionPane.showInputDialog(null,
                "Select a Vacant To Add",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                vacantList,
                vacantList[0]);

        Contratacion optionCoderSelected = (Contratacion) JOptionPane.showInputDialog(null,
                "Select a Coder To Add",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                codersList,
                codersList[0]);

        String state = JOptionPane.showInputDialog("Input the new State 'ACTIVO' or 'INACTIVO' or leave Default", optionSelected.getState());
        Double salary = Double.parseDouble(JOptionPane.showInputDialog("Input the new Descripton or leave Default", optionSelected.getSalary()));

        //Usando la instancia del modelo y los metodos, creamos el objeto que se espera pasandole los datos correspondientes
        instanceContractModel().update(new Contratacion(optionVacantSelected.getId_vacante(),optionCoderSelected.getId_coder(), state, salary));
    }

    public static void create()
    {
        Object[] codersList = Utils.listToarray(CoderController.instanceCoderModel().list());
        Object[] enterprises = Utils.listToarray(EmpresaController.instanceEnterpriseModel().list());

        List<Object> vacantsEnable = new ArrayList<>();

        for(Object vacantEnable: VacanteController.instanceModelVacant().list())
        {
            Vacante newVacant = (Vacante) vacantEnable;

            if ((newVacant.getState().equals("ACTIVO")))
            {
                vacantsEnable.add(newVacant);
            }
        }

        Object[] vacantList = Utils.listToarray(vacantsEnable);

        Vacante optionVacantSelected = (Vacante) JOptionPane.showInputDialog(null,
                "Select a Vacant To Add",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                vacantList,
                vacantList[0]);

        Coder optionCoderSelected = (Coder) JOptionPane.showInputDialog(null,
                "Select a Coder To Add",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                codersList,
                codersList[0]);

        if (!instanceContractModel().verifyOfert(optionVacantSelected.getDescription()))
        {
            JOptionPane.showInputDialog(null, "DONT HAVE THE REQUERIMENTS ");
        }
        else
        {

            //Se cambia el estado de la contratacion manual
            String state = JOptionPane.showInputDialog("Insert contract State 'ACTIVO' or 'INACTIVO'");
            double salary = Double.parseDouble(JOptionPane.showInputDialog("Insert Salary for contract"));

            optionVacantSelected.setState("INACTIVO");

            //Usando la instancia del modelo y los metodos, creamos el objeto que se espera pasandole los datos correspondientes
            instanceContractModel().create(new Contratacion(optionVacantSelected.getId_vacante(), optionCoderSelected.getId_coder(), state, salary));

            String enterpriseName = "", enterpriseUbication = "";

            for (Object enterprise: enterprises)
            {
                Empresa enterpriseNew = (Empresa) enterprise;

                if (enterpriseNew.getId_empresa() == optionVacantSelected.getId_empresa())
                {
                    enterpriseName = enterpriseNew.getName();
                    enterpriseUbication = enterpriseNew.getUbication();
                }
            }



            JOptionPane.showMessageDialog(null, "Vacante: " + optionVacantSelected.getTitle() + " -- Description: " + optionVacantSelected.getDescription() + "" +
                    "\n Enterprise: " + enterpriseName + " --- Enterprise Ubication: " + enterpriseUbication +
                    "\n Coder Name: " + optionCoderSelected.getName() + " --- Surnames: " + optionCoderSelected.getSurname() + " --- Document: " + optionCoderSelected.getDocument() +
                    "\n --- Technology: " + optionCoderSelected.getCv() + "  --- Salary: " + salary);
        }
    }
}

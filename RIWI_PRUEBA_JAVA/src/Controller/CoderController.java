package Controller;

import Entity.Coder;
import Model.CoderModel;
import utils.Utils;

import javax.swing.JOptionPane;
import java.util.List;

public class CoderController
{
    public static CoderModel instanceCoderModel()
    {
        return new CoderModel();
    }

    //Listar factorizado para cualquier objeto de lista
    public static String listAll(List<Object> objectList)
    {
        String list = "--- CODERS LIST --- \n";

        for (Object coder: objectList){
            Coder coderNew = (Coder) coder;
            list += coderNew.toString() + "\n";
        }

        return list;
    }

    public static void listCoders()
    {
        JOptionPane.showMessageDialog(null, listAll(instanceCoderModel().list()));
    }

    public static void delete()
    {
        Object[] coderSelected = Utils.listToarray(CoderController.instanceCoderModel().list());

        Coder optionSelected = (Coder) JOptionPane.showInputDialog(null,
                "Select a Coder to delete",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                coderSelected,
                coderSelected[0]);

        instanceCoderModel().delete(optionSelected);
    }

    public static void update()
    {
        //Listas pasadas a array para el JOptionPane
        Object[] coders = Utils.listToarray(CoderController.instanceCoderModel().list());

        Coder optionSelected = (Coder) JOptionPane.showInputDialog(null,
                "Select a Coder to Modify",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                coders,
                coders[0]);

        String name = JOptionPane.showInputDialog(null,"Input the coder name or leave default", optionSelected.getName());
        String surname = JOptionPane.showInputDialog(null, "Input the coder surname or leave default", optionSelected.getSurname());
        String document = JOptionPane.showInputDialog(null, "Input the coder Document or leave default", optionSelected.getDocument());
        int cohorte = Integer.parseInt(JOptionPane.showInputDialog(null, "Input the coder Cohorte or leave default", optionSelected.getCohorte()));
        String cv = JOptionPane.showInputDialog(null, "Input the cv or leave default", optionSelected.getCv());

        optionSelected.setName(name);
        optionSelected.setSurname(surname);
        optionSelected.setDocument(document);
        optionSelected.setCohorte(cohorte);
        optionSelected.setCv(cv);

        instanceCoderModel().update(optionSelected);
    }


    public static void create()
    {
        String name = JOptionPane.showInputDialog("Insert coder name");
        String surname = JOptionPane.showInputDialog("Insert coder surname");
        String document = JOptionPane.showInputDialog("Insert coder Document");
        int cohorte = Integer.parseInt(JOptionPane.showInputDialog("Insert coder Cohorte"));
        String cv = JOptionPane.showInputDialog("Upload the Coder´s cv");

        //Pasamos el objeto a medico
        instanceCoderModel().create(new Coder(name, surname, document, cohorte, cv));
    }


    public static void findByCohorte()
    {
        int cohorte = Integer.parseInt( JOptionPane.showInputDialog("Type the Cohorte number to search"));

        String listCodersCohorte = "LIST CODERS IN COHORTE #" + cohorte + " FINDED -- > \n";

        for (Object coder: instanceCoderModel().findByCohorte(cohorte))
        {
            Coder newCoder = (Coder) coder;
            listCodersCohorte += newCoder.toString() + "\n";
        }

        JOptionPane.showMessageDialog(null ,listCodersCohorte);
    }

    public static void findByClan()
    {
        String clan = JOptionPane.showInputDialog("Type the clan to search All Coders");

        String listCodersClan = "LIST CODERS IN ClAN " + clan + " FINDED -- > \n";

        for (Object coder: instanceCoderModel().findByClan(clan))
        {
            Coder newCoder = (Coder) coder;
            listCodersClan += newCoder.toString() + "\n";
        }

        JOptionPane.showMessageDialog(null ,listCodersClan);
    }

    public static void findBytech()
    {
        String tech = JOptionPane.showInputDialog("Type the clan to search All Coders");

        String listCodersTech = "LIST CODERS WITH " + tech + " FINDED -- > \n";

        for (Object coder: instanceCoderModel().findBytech(tech))
        {
            Coder newCoder = (Coder) coder;
            listCodersTech += newCoder.toString() + "\n";
        }

        JOptionPane.showMessageDialog(null ,listCodersTech);
    }
}

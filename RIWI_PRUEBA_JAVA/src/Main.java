

import Controller.CoderController;
import Controller.ContratacionController;
import Controller.VacanteController;
import Controller.EmpresaController;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args)
    {
        String optionPrincipalMenu;
        String coderOptionMenu;
        String vacantOptionMenu;
        String enterpriseOptionMenu;
        String contractOptionMenu;

        boolean exit = false;

        do
        {
            optionPrincipalMenu = JOptionPane.showInputDialog("""
                    1 - Coders Menu
                    2 - Enterprises Menu
                    3 - Vacant Menu
                    4 - Contracts Menu
                    5 - Exit
                    """);

            switch (optionPrincipalMenu)
            {
                case "1":
                    do
                    {
                        coderOptionMenu = JOptionPane.showInputDialog("""
                            1 - List All Coders
                            2 - Add New Coder
                            3 - Search Coder By Cohorte
                            4 - Search Coder By Clan
                            5 - Search Coder By Technology
                            6 - Update Coder
                            7 - Delete Coder
                            8 - Exit Coders Menu
                            """);

                        switch (coderOptionMenu)
                        {
                            case "1":
                                CoderController.listCoders();
                                break;

                            case "2":
                                CoderController.create();
                                break;

                            case "3":
                                CoderController.findByCohorte();
                                break;

                            case "4":
                                CoderController.findByClan();
                                break;

                            case "5":
                                CoderController.findBytech();
                                break;

                            case "6":
                                CoderController.update();
                                break;

                            case "7":
                                CoderController.delete();
                                break;
                        }
                    }while (!coderOptionMenu.equals("8"));

                break;

                case "2":
                    do
                    {
                        enterpriseOptionMenu = JOptionPane.showInputDialog("""
                                1 - List All Enterprises
                                2 - Add New Enterprise
                                3 - Update Enterprise
                                4 - Delete Enterprise
                                5 - Exit Enterprise Menu
                                """);

                        switch (enterpriseOptionMenu)
                        {
                            case "1":
                                EmpresaController.listEnterprises();
                                break;

                            case "2":
                                EmpresaController.create();
                                break;

                            case "3":
                                EmpresaController.update();
                                break;

                            case "4":
                                EmpresaController.delete();
                                break;

                        }
                    }while (!enterpriseOptionMenu.equals("5"));
                break;
                case "3":
                    do
                    {
                        vacantOptionMenu = JOptionPane.showInputDialog("""
                                    1 - List All Vacants
                                    2 - Add New Vacant
                                    3 - Update Vacant
                                    4 - Delete Vacant
                                    5 - Search Vacant By Title and Technology
                                    6 - Exit Vacant Menu
                                    """);

                        switch (vacantOptionMenu)
                        {
                            case "1":
                                VacanteController.listVacants();
                                break;

                            case "2":
                                VacanteController.create();
                                break;

                            case "3":
                                VacanteController.update();
                                break;

                            case "4":
                                VacanteController.delete();
                                break;

                            case "5":
                                VacanteController.findByTitleAndTech();
                                break;
                        }
                    }while (!vacantOptionMenu.equals("6"));
                    break;
                case "4":
                    do
                    {
                        contractOptionMenu = JOptionPane.showInputDialog("""
                                    1 - List All Contracts
                                    2 - Add New Contract
                                    3 - Update Contract
                                    4 - Delete Contract
                                    5 - Exit Contracts Menu
                                    """);

                        switch (contractOptionMenu)
                        {
                            case "1":
                                ContratacionController.listContracts();
                                break;

                            case "2":
                                ContratacionController.create();
                                break;

                            case "3":
                                ContratacionController.update();
                                break;

                            case "4":
                                ContratacionController.delete();
                                break;

                        }
                    }while (!contractOptionMenu.equals("5"));
                    break;
            }
        }while(!optionPrincipalMenu.equals("5"));
    }
}
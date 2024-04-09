package Entity;

import java.sql.Date;

public class Contratacion
{
    private int id_contratacion;
    private int id_vacante;
    private int id_coder;
    private Date aplication_date;
    private String state;
    private double salary;

    public Contratacion() {
    }

    public Contratacion(int id_vacante, int id_coder, String state, double salary) {
        this.id_vacante = id_vacante;
        this.id_coder = id_coder;
        this.state = state;
        this.salary = salary;
    }

    public int getId_contratacion() {
        return id_contratacion;
    }

    public void setId_contratacion(int id_contratacion) {
        this.id_contratacion = id_contratacion;
    }

    public int getId_vacante() {
        return id_vacante;
    }

    public void setId_vacante(int id_vacante) {
        this.id_vacante = id_vacante;
    }

    public int getId_coder() {
        return id_coder;
    }

    public void setId_coder(int id_coder) {
        this.id_coder = id_coder;
    }

    public Date getAplication_date() {
        return aplication_date;
    }

    public void setAplication_date(Date aplication_date) {
        this.aplication_date = aplication_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Contratacion{" +
                "id_contratacion=" + id_contratacion +
                ", id_vacante=" + id_vacante +
                ", id_coder=" + id_coder +
                ", aplication_date=" + aplication_date +
                ", state='" + state + '\'' +
                ", salary=" + salary;
    }
}

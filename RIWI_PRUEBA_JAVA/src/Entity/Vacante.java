package Entity;

import java.sql.Time;
import java.sql.Date;

public class Vacante
{
    private int id_vacante;
    private int id_empresa;
    private String title;
    private String description;
    private String duration;
    private String state;
    private String technology;
    private String clan;

    public Vacante() {
    }

    public Vacante(int id_empresa, String title, String description, String duration, String state, String technology, String clan) {
        this.id_empresa = id_empresa;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.state = state;
        this.technology = technology;
        this.clan = clan;
    }

    public int getId_vacante() {
        return id_vacante;
    }

    public void setId_vacante(int id_vacante) {
        this.id_vacante = id_vacante;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    @Override
    public String toString() {
        return "Vacante{" +
                "id_vacante=" + id_vacante +
                ", id_empresa=" + id_empresa +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", state='" + state + '\'' +
                ", technology='" + technology + '\'' +
                ", clan='" + clan + '\'' +
                '}';
    }
}



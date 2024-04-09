package Entity;

import java.sql.Date;

public class Empresa
{
    private int id_empresa;
    private String name;
    private String sector;
    private String ubication;
    private String contact;

    public Empresa() {
    }

    public Empresa(String name, String sector, String ubication, String contact) {
        this.name = name;
        this.sector = sector;
        this.ubication = ubication;
        this.contact = contact;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id_empresa=" + id_empresa +
                ", name='" + name + '\'' +
                ", sector='" + sector + '\'' +
                ", ubication='" + ubication + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}

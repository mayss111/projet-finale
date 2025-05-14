package contenu;

import gestioncours.contenttype;

public class ContenuCours {
    private int id;
    private String titre;
    private contenttype type;
    private String url;
    private  int idCours;

    public ContenuCours(int id, String titre, contenttype type, String url , int idCours) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.url = url;
        this.idCours= idCours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public contenttype getType() {
        return type;
    }

    public void setType(contenttype type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }
    // toString() utile pour debug
    @Override
    public String toString() {
        return titre + " [" + type + "] - " + url +idCours;
    }


}



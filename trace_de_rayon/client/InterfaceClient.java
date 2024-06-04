import java.util.List;

public interface InterfaceClient {
    /**
     * demande les proxys au service en parametres
     */
    public List<InterfaceClient> demanderProxy(Service s);
    /**
     * demande à sa liste de client de calculer une partie d'image
     */
    public void lancerCalcul();
    /**
     * Exécution d'un calcul
     */
    public Image realiserCalcul();
}

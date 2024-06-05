import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;


public interface InterfaceClient {
    /**
     * demande les proxys au service en parametres
     * @throws ServerNotActiveException 
     * @throws RemoteException 
     */
    public void demanderProxy(InterfaceService service) throws RemoteException, ServerNotActiveException;
     /**
     * demande à sa liste de client de calculer une partie d'image
     */
    public void lancerCalcul(int largeur, int hauteur) throws RemoteException, ServerNotActiveException;
}

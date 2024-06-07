import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;


public interface InterfaceClient {
     /**
     * demande à sa liste de client de calculer une partie d'image
     */
    public void lancerCalcul(int largeur, int hauteur, int numChunks) throws RemoteException, ServerNotActiveException;
}

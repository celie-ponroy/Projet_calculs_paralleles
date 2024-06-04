import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LancerService {
    public static void main (String args[]) throws RemoteException {
        InterfaceService service = new Service(); // Créer une instance de Compteur
        InterfaceService rd = (InterfaceService) UnicastRemoteObject.exportObject(service, 0); 
        //Un_port = un entier particulier ou 0 pour auto-assigné 

        Registry reg = LocateRegistry.getRegistry(1099); //Récupération/Création de l'annuaire
        reg.rebind("raytracing", rd);//Enregistrement de la référence sous le nom "CompteurDistant"
    }
}

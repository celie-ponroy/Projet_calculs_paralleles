import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.rmi.registry.LocateRegistry;

public class LancerServiceRayTracer {

    public static void main(String[] args) {
        String serveur = "localhost";
        int port = 1099;
        if (args.length > 0)
            serveur = args[0];
        if (args.length > 1)
            port = Integer.parseInt(args[1]);

        try {
            Registry reg = LocateRegistry.getRegistry(serveur, port);
            System.out.println("Connexion à l'annuaire réussie");
            try {
                InterfaceService distributeurNoeuds = (InterfaceService) reg.lookup("distributeurNoeuds");
                try {
                    ServiceScene sc = new ServiceScene();
                    ServiceRayTracer rc = (ServiceRayTracer) UnicastRemoteObject.exportObject(sc, 0);
                    distributeurNoeuds.enregistrerRayTracer(rc);
                    System.out.println("Client connecte au processus central");
                } catch (RemoteException e) {
                    System.out.println("Impossible d'enregistrer le client : " + e.getMessage());
                } catch (java.rmi.server.ServerNotActiveException e) {
                    System.out.println("Impossible de recuperer l'adresse du client : " + e.getMessage());
                }
            } catch (java.rmi.NotBoundException e) {
                System.out.println("Le service distant n'existe pas : " + e.getMessage());
            }
        } catch (RemoteException e) {
            System.out.println("Probleme de connexion à l'annuaire: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Usage : java LancerServiceRayTracer [adresse] [port]");
        }
    }
}
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;

public class LancerClient {
    public static void main(String[] args) throws RemoteException, ServerNotActiveException, NotBoundException {
        String fichier_description = "../simple.txt";
        int largeur = 512;
        int hauteur = 512;
        int numChunks = 1;
        if (args.length > 0) {
            fichier_description = args[0];
            if (args.length > 1) {
                largeur = Integer.parseInt(args[1]);
                if (args.length > 2)
                    hauteur = Integer.parseInt(args[2]);
                    if(args.length > 3)
                        numChunks = Integer.parseInt(args[3]);
            }
        } else {
            System.out.println("Raytracer : synthèse d'image par lancé de rayons (https://en.wikipedia.org/wiki/Ray_tracing_(graphics))\n\nUsage : java LancerRaytracer [fichier-scène] [largeur] [hauteur]\n\tfichier-scène : la description de la scène (par défaut simple.txt)\n\tlargeur : largeur de l'image calculée (par défaut 512)\n\thauteur : hauteur de l'image calculée (par défaut 512)\n");
        }


        Registry reg = LocateRegistry.getRegistry("localhost", 1099);
        InterfaceService service = (InterfaceService) reg.lookup("distributeurNoeuds");

        ArrayList<ServiceRayTracer> liste_tracer = service.demanderProxys();

        Client client = new Client(fichier_description, liste_tracer, service);

        client.lancerCalcul(largeur, hauteur, numChunks);
    }
}

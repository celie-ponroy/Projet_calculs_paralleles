import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.security.Provider.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Client implements InterfaceClient {
    private ArrayList<ServiceRayTracer> liste_tracer;
    private String fichier_description;
    private InterfaceService service;

    public Client(String fichier, ArrayList<ServiceRayTracer> liste_tracer, InterfaceService service) {
        this.liste_tracer = liste_tracer;
        this.fichier_description = fichier;
        this.service = service;
    }

    /**
     * demande à sa liste de client de calculer une partie d'image
     */
    @Override
    public void lancerCalcul(int largeur, int hauteur) throws RemoteException, ServerNotActiveException {
        Disp disp = new Disp("Raytracer", largeur, hauteur);
        Scene scene = new Scene(fichier_description, largeur, hauteur);
        int x0 = 0, y0 = 0;
        int l = largeur;
        int h = hauteur;
        if (liste_tracer.isEmpty()) {
            Image image = new RayTracer(l, h).compute(scene, x0, y0, l, h, 10, 1);
            disp.setImage(image, x0, y0);
        } else {
            // Calcul de l'image sur tout les noeuds de la liste a l'aide de Treads
            for (ServiceRayTracer rayTracer : liste_tracer) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Instant debut = Instant.now();
                        System.out.println("Calcul de l'image :\n - Coordonnées : " + 0 + "," + 0
                                + "\n - Taille " + largeur + "x" + hauteur);
                        Image image;
                        try {
                            image = rayTracer.genererImage(scene, 0, 0, largeur, hauteur);
                        } catch (Exception r) {
                            try {
                                service.supprimerProxy(rayTracer);
                                liste_tracer = service.demanderProxys();
                            } catch (RemoteException remoteException) {
                                System.out.println("Erreur lors de la connection au serveur");
                            } catch (ServerNotActiveException e) {
                                System.out.println("Erreur lors de la connection au serveur");
                            }
                            //relancer un thread
                            image = new RayTracer(largeur, hauteur).compute(scene, 0, 0, largeur, hauteur, 10, 1);
                        }
                        Instant fin = Instant.now();
                        long duree = Duration.between(debut, fin).toMillis();
                        System.out.println("Image calculée en :" + duree + " ms");
                        disp.setImage(image, x0, 0);
                        if (x0 != largeur){
                            x0 = x0 + l;
                        }  
                        else {
                            x0 = 0;
                            y0 += h;
                        }
                    }
                });
                thread.run();
            }
        }
    }

/*
            //divisions le l'image 
            int x0 = 0, y0 = 0;
            int l = largeur / liste_tracer.size();
            int h = hauteur / liste_tracer.size();
            for (RayTracer rayTracer : liste_tracer) {

                Instant debut = Instant.now();
                System.out.println("Calcul de l'image :\n - Coordonnées : " + x0 + "," + y0
                        + "\n - Taille " + largeur + "x" + hauteur);
                Image image;
                try {
                    image = rayTracer.compute(scene, x0, y0, l, h, 10, 1);
                } catch (Exception r) {
                    // donner le process pour que service l'enlève
                    service.supprimerProxy(rayTracer);
                    demanderProxy(service);
                    //relancer un thread
                    image = new RayTracer(l, h).compute(scene, x0, y0, l, h, 10, 1);
                }


                Instant fin = Instant.now();

                long duree = Duration.between(debut, fin).toMillis();

                System.out.println("Image calculée en :" + duree + " ms");

                disp.setImage(image, x0, y0);
                if (x0 != largeur)
                    x0 = x0 + l;
                else {
                    x0 = 0;
                    y0 += h;
                }
            }*/

        }
    }
}

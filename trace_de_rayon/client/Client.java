import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.security.Provider.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Client {
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
    public void lancerCalcul(int largeur, int hauteur, int numChunks) throws RemoteException, ServerNotActiveException {
        Disp disp = new Disp("Raytracer", largeur, hauteur);
        Scene scene = new Scene(fichier_description, largeur, hauteur);

        if (liste_tracer.isEmpty()) {
            Image image = new RayTracer(largeur, hauteur).compute(scene, 0, 0, largeur, hauteur, 10, 1);
            disp.setImage(image, 0, 0);
        } else {
            // Définir le nombre de découpes de l'image
            int numTracers = liste_tracer.size();
            int chunkWidth = largeur / numChunks;
            int chunkHeight = hauteur / numChunks;

            for (int chunkIndex = 0; chunkIndex < numChunks * numChunks; chunkIndex++) {
                ServiceRayTracer rayTracer = liste_tracer.get(chunkIndex % numTracers);
                final int x0 = (chunkIndex % numChunks) * chunkWidth;
                final int y0 = (chunkIndex / numChunks) * chunkHeight;

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Instant debut = Instant.now();
                        System.out.println("Calcul de l'image :\n - Coordonnées : " + x0 + "," + y0
                                + "\n - Taille " + chunkWidth + "x" + chunkHeight);
                        Image image;
                        try {
                            image = rayTracer.genererImage(scene, x0, y0, chunkWidth, chunkHeight);
                        } catch (Exception r) {
                            try {
                                service.supprimerProxy(rayTracer);
                                synchronized (liste_tracer) {
                                    liste_tracer = service.demanderProxys();
                                }
                            } catch (RemoteException remoteException) {
                                System.out.println("Erreur lors de la connexion au serveur");
                                return;
                            } catch (ServerNotActiveException e) {
                                System.out.println("Erreur lors de la connexion au serveur");
                                return;
                            }
                            // Relancer un thread si une exception se produit
                            image = new RayTracer(chunkWidth, chunkHeight).compute(scene, x0, y0, chunkWidth,
                                    chunkHeight, 10, 1);
                        }
                        Instant fin = Instant.now();
                        long duree = Duration.between(debut, fin).toMillis();
                        System.out.println("Image calculée en :" + duree + " ms");
                        disp.setImage(image, x0, y0);
                    }
                });
                thread.start();
            }
        }
    }

}

/*
 * //divisions le l'image
 * int x0 = 0, y0 = 0;
 * int l = largeur / liste_tracer.size();
 * int h = hauteur / liste_tracer.size();
 * for (RayTracer rayTracer : liste_tracer) {
 * 
 * Instant debut = Instant.now();
 * System.out.println("Calcul de l'image :\n - Coordonnées : " + x0 + "," + y0
 * + "\n - Taille " + largeur + "x" + hauteur);
 * Image image;
 * try {
 * image = rayTracer.compute(scene, x0, y0, l, h, 10, 1);
 * } catch (Exception r) {
 * // donner le process pour que service l'enlève
 * service.supprimerProxy(rayTracer);
 * demanderProxy(service);
 * //relancer un thread
 * image = new RayTracer(l, h).compute(scene, x0, y0, l, h, 10, 1);
 * }
 * 
 * 
 * Instant fin = Instant.now();
 * 
 * 
 * long duree = Duration.between(debut, fin).toMillis();
 * 
 * System.out.println("Image calculée en :" + duree + " ms");
 * 
 * disp.setImage(image, x0, y0);
 * if (x0 != largeur)
 * x0 = x0 + l;
 * else {
 * x0 = 0;
 * y0 += h;
 * }
 * }
 */

    
    


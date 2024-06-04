import java.rmi.RemoteException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import raytracer.Disp;
import raytracer.Image;
import raytracer.RayTracer;
import raytracer.Scene;

public class Client implements InterfaceClient {
    private List<RayTracer> liste_tracer;
    private String fichier_description;
    private int largeur, hauteur;
    private Service service ;

    Client(String fichier, int larg, int haut){
        liste_tracer = new ArrayList<RayTracer>();
        if(fichier.isEmpty()){
            fichier_description ="simple.txt";
        }
        else{
            fichier_description = fichier;
        }
        if (larg > 0) 
            largeur = larg;
        if (haut > 0)
            hauteur = haut;
        
    }
    @Override
    public void demanderProxy(Service s) {
        service = s;
        this.liste_tracer = s.demanderProxys();
    }


    /**
     * demande à sa liste de client de calculer une partie d'image
     */
    
     @Override
    public void lancerCalcul(int taille){
        Disp disp = new Disp("Raytracer", largeur, hauteur);
        Scene scene = new Scene(fichier_description, largeur, hauteur);
        //divisions le l'image 
        int x0 = 0, y0 = 0;
        int l = taille / liste_tracer.size();
        int h = taille /  liste_tracer.size();
        for (RayTracer rayTracer : liste_tracer) {

            Instant debut = Instant.now();
            System.out.println("Calcul de l'image :\n - Coordonnées : " + x0 + "," + y0
                    + "\n - Taille " + largeur + "x" + hauteur);
            try{
                Image image = rayTracer.compute(scene, x0, y0, l, h, 10, 1);
            }catch(RemoteException r){
                // donner le process pour que service l'enlève
                service.supprimerRayTracer(rayTracer);
                demanderProxy(service);
                //relancer un thread
                Image image = new RayTracer(l, h).compute(scene, x0, y0, l, h, 10, 1);
            }
            
    
            Instant fin = Instant.now();
    
            long duree = Duration.between(debut, fin).toMillis();
    
            System.out.println("Image calculée en :" + duree + " ms");
    
            disp.setImage(image, x0, y0);
        }
        
    }
}

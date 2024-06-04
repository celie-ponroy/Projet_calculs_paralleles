import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
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
    private Service service ;

    Client(String fichier){
        liste_tracer = new ArrayList<RayTracer>();
        if(fichier.isEmpty()){
            fichier_description ="simple.txt";
        }
        else{
            fichier_description = fichier;
        }
        
    }
    @Override
    public void demanderProxy(InterfaceService service2) throws RemoteException, ServerNotActiveException {
        service = (Service) service2;
        this.liste_tracer = service2.demanderProxys();
    }


    /**
     * demande à sa liste de client de calculer une partie d'image
     */
    
     @Override
    public void lancerCalcul(int largeur, int hauteur){
        Disp disp = new Disp("Raytracer", largeur, hauteur);
        Scene scene = new Scene(fichier_description, largeur, hauteur);
        if(liste_tracer.size()==0){
            int x0 = 0, y0 = 0;
            int l = largeur;
            int h = hauteur;
            Image image = new RayTracer(l, h).compute(scene, x0, y0, l, h, 10, 1);
            disp.setImage(image, x0, y0);
        }else{
            //divisions le l'image 
            int x0 = 0, y0 = 0;
            int l = largeur / liste_tracer.size();
            int h = hauteur /  liste_tracer.size();
            for (RayTracer rayTracer : liste_tracer) {

                Instant debut = Instant.now();
                System.out.println("Calcul de l'image :\n - Coordonnées : " + x0 + "," + y0
                        + "\n - Taille " + largeur + "x" + hauteur);
                Image image;
                try{
                    image = rayTracer.compute(scene, x0, y0, l, h, 10, 1);
                }catch(RemoteException r){
                    // donner le process pour que service l'enlève
                    service.supprimerRayTracer(rayTracer);
                    demanderProxy(service);
                    //relancer un thread
                    image = new RayTracer(l, h).compute(scene, x0, y0, l, h, 10, 1);
                }
                

                Instant fin = Instant.now();

                long duree = Duration.between(debut, fin).toMillis();

                System.out.println("Image calculée en :" + duree + " ms");

                disp.setImage(image, x0, y0);
                if(x0 != largeur)
                    x0= x0 +l;
                else{
                    x0 = 0;
                    y0 += h;
                }
            }

        }
    }   
}

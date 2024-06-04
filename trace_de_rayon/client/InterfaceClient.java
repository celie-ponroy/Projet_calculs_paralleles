import java.time.Duration;
import java.time.Instant;
import java.util.List;

import raytracer.Disp;
import raytracer.Image;
import raytracer.RayTracer;
import raytracer.Scene;

public interface InterfaceClient {
    /**
     * demande les proxys au service en parametres
     */
    public void demanderProxy(Service s);
     /**
     * demande à sa liste de client de calculer une partie d'image
     */
    public void lancerCalcul(int taille);
}

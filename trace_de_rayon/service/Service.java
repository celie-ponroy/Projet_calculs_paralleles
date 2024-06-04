
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;

import raytracer.RayTracer;

public class Service implements InterfaceService {
    List<RayTracer> listraytracer;

    Service(){
        this.listraytracer = new ArrayList<>();
    }

    public void enregistrerRayTracer(RayTracer c) throws RemoteException, ServerNotActiveException {
        listraytracer.add(c);
    }

    public List<RayTracer> demanderProxys(){
        return this.listraytracer;
    }
    public void supprimerRayTracer(RayTracer rayTracer){
        listraytracer.remove(rayTracer);
    }
    
}

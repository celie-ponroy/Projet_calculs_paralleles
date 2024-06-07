import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;

public class Service implements InterfaceService {
    ArrayList<ServiceRayTracer> listraytracer;

    public Service() {
        this.listraytracer = new ArrayList<>();
    }

    public void enregistrerRayTracer(ServiceRayTracer c) throws RemoteException, ServerNotActiveException {
        listraytracer.add(c);
    }

    public ArrayList<ServiceRayTracer> demanderProxys() {
        return this.listraytracer;
    }

    public void supprimerProxy(ServiceRayTracer rayTracer) {
        listraytracer.remove(rayTracer);
    }

}

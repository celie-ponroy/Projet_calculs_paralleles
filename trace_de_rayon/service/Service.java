
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import raytracer.RayTracer;

public class Service implements InterfaceService {
    List<Client> listraytracer;

    public void enregistrerClient(RayTracer c) throws RemoteException, ServerNotActiveException {
        listraytracer.add(c);
    }
    public void envoyerProxy() throws RemoteException, ServerNotActiveException{

    }
    
}

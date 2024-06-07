import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;

public interface InterfaceService extends Remote {
    public void enregistrerRayTracer(ServiceRayTracer c) throws RemoteException, ServerNotActiveException;

    public ArrayList<ServiceRayTracer> demanderProxys() throws RemoteException, ServerNotActiveException;

    public void supprimerProxy(ServiceRayTracer rayTracer) throws RemoteException, ServerNotActiveException;
}
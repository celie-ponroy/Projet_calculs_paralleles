import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;


public interface InterfaceService extends Remote{
   public void enregistrerRayTracer(RayTracer c) throws RemoteException, ServerNotActiveException;
   public List<RayTracer> demanderProxys()throws RemoteException, ServerNotActiveException;
  public void supprimerRayTracer(RayTracer rayTracer)throws RemoteException, ServerNotActiveException;
}
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface InterfaceService extends Remote{
   public void methode() throws RemoteException, ServerNotActiveException;
}
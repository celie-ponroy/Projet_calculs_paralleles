import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LancerService {
    public static void main(String[] args) throws RemoteException {
        Service service = new Service();
        InterfaceService rd = (InterfaceService) UnicastRemoteObject.exportObject(service, 0);

        Registry reg = LocateRegistry.getRegistry(1099);
        reg.rebind("distributeurNoeuds", rd);
    }
}

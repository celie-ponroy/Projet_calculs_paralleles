import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceRayTracer extends Remote {
    public Image genererImage(Scene c, int x0, int y0, int w, int h) throws RemoteException;
}

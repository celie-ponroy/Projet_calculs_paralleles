import java.awt.*;
import java.rmi.RemoteException;

import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;

public class ServiceScene implements ServiceRayTracer {
    @Override
    public Image genererImage(Scene c, int x0, int y0, int w, int h) throws RemoteException {
        String host = "";
        try {
            host = RemoteServer.getClientHost();
            System.out.println("Demande de calcul d'image de la part de : " + host);
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
        return c.compute(x0, y0, w, h);
    }
}
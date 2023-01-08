package pl.Filipe.Patricia.Sandra.Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GetRemoteServerListServidorInterface extends Remote {
    String REGISTRY_BIND_NAME = "SHOW_SERVICE_";
    ArrayList<HeardBeat> getServers() throws RemoteException;
    void addObserver(GetRemoteServerListClientInterface cliRef) throws RemoteException;
    void removeObserver(GetRemoteServerListClientInterface cliRef) throws RemoteException;

}

package pl.Filipe.Patricia.Sandra.Servidor;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GetRemoteServerListClientInterface extends Remote {

     void printServers(ArrayList<HeardBeat> listaServidores)throws RemoteException;
     void rececaoClientUDP(InetAddress ip, int port) throws RemoteException;
     void loginClient(String nome) throws RemoteException;
     void logoutClient(String nome) throws RemoteException;
     void rececaoClientTCP(InetAddress ip, int port) throws RemoteException;
     void perdaLigacaoTCP(String nome) throws RemoteException;

}

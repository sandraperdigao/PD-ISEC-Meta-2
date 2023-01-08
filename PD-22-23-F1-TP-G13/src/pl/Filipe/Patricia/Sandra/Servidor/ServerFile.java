package pl.Filipe.Patricia.Sandra.Servidor;

import pl.Filipe.Patricia.Sandra.Servidor.Thread.ServerThreadReceiveBeat;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerFile extends UnicastRemoteObject implements GetRemoteServerListServidorInterface {

    private ServerThreadReceiveBeat serverThreadReceiveBeat;
    private int portUDP;

    private final HashSet<GetRemoteServerListClientInterface> clientes;

    public ServerFile(ServerThreadReceiveBeat serverThreadReceiveBeat, int i, HashSet<GetRemoteServerListClientInterface> clientes) throws RemoteException {
        this.portUDP = i;
        this.serverThreadReceiveBeat = serverThreadReceiveBeat;
        this.clientes = clientes;

    }

    public ServerFile(int i) throws RemoteException {
        this.portUDP = i;
        clientes = new HashSet<>();

    }

    @Override
    public ArrayList<HeardBeat> getServers() throws RemoteException {
        return serverThreadReceiveBeat.getListaServidores();
    }

    @Override
    public void addObserver(GetRemoteServerListClientInterface cliRef) throws RemoteException {
        synchronized (clientes){
            clientes.add(cliRef);
            System.out.println("Cliente size: " + clientes.size());
        }

    }

    @Override
    public void removeObserver(GetRemoteServerListClientInterface cliRef) throws RemoteException {
        synchronized (clientes){
            clientes.remove(cliRef);
        }
    }

    public void rececaoClientUDPRMI(InetAddress socketAddress, int port) throws RemoteException {
       for (GetRemoteServerListClientInterface cliente: clientes) {
           synchronized (clientes){
               cliente.rececaoClientUDP(socketAddress, port);
           }
        }
    }
    public void loginCliente(String nome) throws RemoteException {
        for (GetRemoteServerListClientInterface cliente: clientes) {
            synchronized (clientes){
                cliente.loginClient(nome);

            }
        }
    }

    public void logoutCliente(String nome) throws RemoteException {
        for (GetRemoteServerListClientInterface cliente: clientes) {
            synchronized (clientes){
                cliente.logoutClient(nome);
            }
        }
    }
    public void rececaoClientTCP(InetAddress ip, int port) throws RemoteException{
        for (GetRemoteServerListClientInterface cliente: clientes) {
            synchronized (clientes){
            cliente.rececaoClientTCP(ip, port);
            }
        }
    }

    public void perdaLigacaoTCP(String nome) throws RemoteException{
        for (GetRemoteServerListClientInterface cliente: clientes) {
            synchronized (clientes){
                cliente.perdaLigacaoTCP(nome);
            }
        }
    }


    public  void rmi(ServerThreadReceiveBeat serverThreadReceiveBeat) throws RemoteException {
        String nomeRegistry = REGISTRY_BIND_NAME + portUDP;
        System.out.println("nome Rmi " + nomeRegistry);
        //fazer try catch se der excecao ExportException faz get
        Registry r;
        try{
             r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }catch (ExportException e){
             r = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
        }

        r.rebind(nomeRegistry, new ServerFile(serverThreadReceiveBeat, portUDP, clientes));
    }


}
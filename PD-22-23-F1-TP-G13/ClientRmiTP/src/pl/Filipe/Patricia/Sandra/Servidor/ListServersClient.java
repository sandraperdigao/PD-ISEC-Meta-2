package pl.Filipe.Patricia.Sandra.Servidor;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ListServersClient extends UnicastRemoteObject implements GetRemoteServerListClientInterface {

    public ListServersClient() throws RemoteException {
    }

    @Override
    public void printServers(ArrayList<HeardBeat> listaServidores) {
        for (HeardBeat hb : listaServidores) {
            System.out.println(hb);
        }
    }

    @Override
    public void rececaoClientUDP(InetAddress ip, int port) throws RemoteException {
        System.out.println("recebou um cliente com o IP: " + ip + " e porto UDP: " + port);
    }
    @Override
    public void loginClient(String nome) throws RemoteException{
        System.out.println("Houve um login de um cliente com o nome: " + nome);
    }
    @Override
    public void logoutClient(String nome) throws RemoteException{
        System.out.println("Houve um logout de um cliente com o nome: " + nome);

    }

    @Override
    public void perdaLigacaoTCP(String nome) throws RemoteException {
        System.out.println("O cliente: " + nome + " perdeu a ligação TCP");
    }

    @Override
    public void rececaoClientTCP(InetAddress ip, int port) throws RemoteException{
        System.out.println("recebou um cliente com o IP: " + ip + " e porto TCP:" + port);
    }

    public int menu(){
        int aux;
        aux = PDInput.chooseOption("Escolha uma opção", "Obter a lista de servidores",
                "Adicionar observer no servidor",
                "Remover observer no servidor",
                "Sair da aplicação");

        return aux;
    }

    public void fechaCliente() throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(this, true);

    }

    private void listServers(ArrayList<HeardBeat> servers, int arg){
        for (HeardBeat hb: servers) {
            if(hb.getPortRMI() != arg)
            System.out.println(hb);
        }


    }

    public static void main(String[] args) throws IOException, NotBoundException {
        if (args.length != 2)
        {
            System.out.println("The RMI Registry IP or port is missing from the command line arguments.");
            return;
        }
        String nomeRMI = GetRemoteServerListServidorInterface.REGISTRY_BIND_NAME + args[1];
        System.out.println(nomeRMI);

        Registry r = LocateRegistry.getRegistry(args[0], Registry.REGISTRY_PORT);


        GetRemoteServerListServidorInterface remoteRef = (GetRemoteServerListServidorInterface)
                r.lookup(nomeRMI);

        ListServersClient fileClient = new ListServersClient();
        int res;
        while((res = fileClient.menu())!= 4){
            switch (res){
                case 1 -> fileClient.listServers(remoteRef.getServers(), Integer.parseInt(args[1]));
                case 2 -> remoteRef.addObserver(fileClient);
                case 3 -> remoteRef.removeObserver(fileClient);
            }
        }
        fileClient.fechaCliente();
    }
}

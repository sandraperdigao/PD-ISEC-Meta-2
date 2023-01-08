package pl.Filipe.Patricia.Sandra.Servidor;

import java.rmi.RemoteException;

public class Servidor {
    public static void main(String[] args) throws RemoteException {
        if(args.length < 1){
            System.out.println("Número de argumentos da linha de comandos errado.");
            return;
        }
        ServidorUI servidorUI = new ServidorUI();
        servidorUI.run(args);
    }

}

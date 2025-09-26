package com.github.britooo.looca.api;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.rede.RedeInterfaceGroup;
import com.github.britooo.looca.api.group.rede.RedeParametros;
import oshi.SystemInfo;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Looca looca = new Looca();

        System.out.println("-----------------------");
        System.out.println(looca.getMemoria());
        System.out.println("-----------------------");
        System.out.println(looca.getProcessador());
        System.out.println("-----------------------");

        List<RedeInterface> dados = looca.getRede().getGrupoDeInterfaces().getInterfaces();

        for (RedeInterface dado : dados) {
            System.out.println(dados.get(0));
            break;
        }

        System.out.println("-----------------------");

        List<DispositivoUsb> dispositivos = looca.getDispositivosUsbGrupo().getDispositivosUsbConectados();

        for (DispositivoUsb dispositivo : dispositivos) {
            System.out.println(dispositivo);
        }

    }
}

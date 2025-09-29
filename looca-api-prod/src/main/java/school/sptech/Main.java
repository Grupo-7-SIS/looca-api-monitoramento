package school.sptech;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import school.sptech.controller.LoocaCapturaController;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Looca looca = new Looca();
        LoocaCapturaController loocaDados = new LoocaCapturaController();
        loocaDados.dadosNetworking();
    }
}
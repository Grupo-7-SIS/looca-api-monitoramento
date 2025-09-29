package school.sptech.controller;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;

import java.util.List;

public class LoocaCapturaController {

    public void dadosNetworking() {
        Looca looca = new Looca();
        List<RedeInterface> dados = looca.getRede().getGrupoDeInterfaces().getInterfaces();
        for (RedeInterface dado : dados) {
            System.out.println(dado);
        }
    }
}

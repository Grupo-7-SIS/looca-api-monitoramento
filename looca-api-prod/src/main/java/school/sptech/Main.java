package school.sptech;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Looca looca = new Looca();

        List<RedeInterface> dados = looca.getRede().getGrupoDeInterfaces().getInterfaces();

        for (RedeInterface dado : dados) {
            System.out.println(dado);
        }
    }
}
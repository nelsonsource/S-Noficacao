package ujc.notificacao.system.sistema_notificacao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ujc.notificacao.system.sistema_notificacao.entity.Atendente;
import ujc.notificacao.system.sistema_notificacao.repository.AtendenteRepository;

@SpringBootTest
public class AtendenteTest {
    @Autowired
    AtendenteRepository atendenteRepository;

    @Test
    void salvar(){
        Atendente atendente=new Atendente();

        atendente.setCodigoAtendente("1312");
        atendente.setNome("Alfredo");
        atendente.setApelido("Machono");
        atendente.setEmail("edymachono@gmail.com");
        atendente.setTelefone("847056097");
        atendenteRepository.save(atendente);
    }
}

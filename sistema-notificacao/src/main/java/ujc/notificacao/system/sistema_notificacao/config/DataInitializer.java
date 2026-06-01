package ujc.notificacao.system.sistema_notificacao.config;

import ujc.notificacao.system.sistema_notificacao.entity.*;
import ujc.notificacao.system.sistema_notificacao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EstudanteRepository estudanteRepository;
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=========================================");
        System.out.println("DataInitializer EXECUTANDO...");
        System.out.println("=========================================");
        
        // Criar ADMIN
        if (!usuarioRepository.existsByEmail("admin@admin.com")) {
            Funcionario adminFunc = new Funcionario();
            adminFunc.setEmail("admin@admin.com");
            adminFunc.setNome("Administrador");
            adminFunc.setApelido("Sistema");
            adminFunc.setGenero("MASCULINO");
            adminFunc.setCurso("TI");
            funcionarioRepository.save(adminFunc);
            
            Usuario admin = new Usuario();
            admin.setEmail("admin@admin.com");
            admin.setCodigo("ADM001");
            admin.setSenha(encoder.encode("admin123"));
            admin.setPerfil(Perfil.ADMIN);
            admin.setFuncionarioId(adminFunc.getId());
            usuarioRepository.save(admin);
            
            System.out.println("✅ ADMIN criado - Email: admin@admin.com | Senha: admin123");
        }

        // Criar SECRETARIA
        if (!usuarioRepository.existsByEmail("secretaria@email.com")) {
            Funcionario secFunc = new Funcionario();
            secFunc.setEmail("secretaria@email.com");
            secFunc.setNome("Secretaria");
            secFunc.setApelido("Académica");
            secFunc.setGenero("FEMININO");
            secFunc.setCurso("Administração");
            funcionarioRepository.save(secFunc);
            
            Usuario secretaria = new Usuario();
            secretaria.setEmail("secretaria@email.com");
            secretaria.setCodigo("SEC001");
            secretaria.setSenha(encoder.encode("secretaria123"));
            secretaria.setPerfil(Perfil.SECRETARIA);
            secretaria.setFuncionarioId(secFunc.getId());
            usuarioRepository.save(secretaria);
            
            System.out.println("✅ SECRETARIA criado - Email: secretaria@email.com | Senha: secretaria123");
        }

        // Criar ESTUDANTE
        if (!usuarioRepository.existsByCodigo("2400001")) {
            Estudante alunoEst = new Estudante();
            alunoEst.setEmail("joao.silva@email.com");
            alunoEst.setNome("João");
            alunoEst.setApelido("Silva");
            alunoEst.setNumeroEstudante("20240001");
            alunoEst.setGenero("MASCULINO");
            alunoEst.setCurso("Engenharia Informática");
            alunoEst.setAnoIngresso(2024);
            estudanteRepository.save(alunoEst);
            
            Usuario aluno = new Usuario();
            aluno.setEmail("joao.silva@email.com");
            aluno.setCodigo("2400001");
            aluno.setSenha(encoder.encode("joao123"));
            aluno.setPerfil(Perfil.ALUNO);
            aluno.setEstudanteId(alunoEst.getId());
            usuarioRepository.save(aluno);
            
            System.out.println("✅ ESTUDANTE criado - Código: 2400001 | Senha: joao123");
        }
        
        System.out.println("=========================================");
        System.out.println("DataInitializer FINALIZADO!");
        System.out.println("=========================================");
    }
}
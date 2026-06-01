package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.entity.Funcionario;
import ujc.notificacao.system.sistema_notificacao.repository.FuncionarioRepository;
import ujc.notificacao.system.sistema_notificacao.dto.request.FuncionarioRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.FuncionarioResponseDTO;
import ujc.notificacao.system.sistema_notificacao.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Criar novo funcionário
    @Transactional
    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO dto) {
        // Validações
        if (!ValidationUtils.isValidNome(dto.getNome())) {
            throw new RuntimeException("Nome inválido (mínimo 3 caracteres)");
        }
        if (!ValidationUtils.isValidEmail(dto.getEmail())) {
            throw new RuntimeException("Email inválido");
        }
        if (dto.getTelefone() != null && !ValidationUtils.isValidTelefone(dto.getTelefone())) {
            throw new RuntimeException("Telefone inválido (deve conter 9 dígitos começando com 8 ou 9)");
        }
        
        // Verificar email duplicado
        if (funcionarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + dto.getEmail());
        }
        
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setApelido(dto.getApelido());
        funcionario.setGenero(dto.getGenero());
        funcionario.setCurso(dto.getCurso());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setEmail(dto.getEmail());
        
        Funcionario saved = funcionarioRepository.save(funcionario);
        return new FuncionarioResponseDTO(saved);
    }

    // Listar todos os funcionários
    public List<FuncionarioResponseDTO> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(FuncionarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar funcionário por ID
    public FuncionarioResponseDTO buscarPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        return new FuncionarioResponseDTO(funcionario);
    }

    // Buscar por email
    public FuncionarioResponseDTO buscarPorEmail(String email) {
        Funcionario funcionario = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com email: " + email));
        return new FuncionarioResponseDTO(funcionario);
    }

    // Buscar por nome
    public List<FuncionarioResponseDTO> buscarPorNome(String nome) {
        if (!ValidationUtils.isValidNome(nome)) {
            throw new RuntimeException("Nome deve ter pelo menos 3 caracteres");
        }
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(FuncionarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por curso
    public List<FuncionarioResponseDTO> buscarPorCurso(String curso) {
        return funcionarioRepository.findByCurso(curso)
                .stream()
                .map(FuncionarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Atualizar funcionário
    @Transactional
    public FuncionarioResponseDTO atualizarFuncionario(Long id, FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        
        if (!ValidationUtils.isValidNome(dto.getNome())) {
            throw new RuntimeException("Nome inválido");
        }
        
        if (!funcionario.getEmail().equals(dto.getEmail()) && 
            funcionarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + dto.getEmail());
        }
        
        funcionario.setNome(dto.getNome());
        funcionario.setApelido(dto.getApelido());
        funcionario.setGenero(dto.getGenero());
        funcionario.setCurso(dto.getCurso());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setEmail(dto.getEmail());
        
        Funcionario updated = funcionarioRepository.save(funcionario);
        return new FuncionarioResponseDTO(updated);
    }

    // Deletar funcionário
    @Transactional
    public void deletarFuncionario(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }
        funcionarioRepository.deleteById(id);
    }
}
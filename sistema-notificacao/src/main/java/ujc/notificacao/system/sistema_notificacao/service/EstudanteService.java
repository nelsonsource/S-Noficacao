package ujc.notificacao.system.sistema_notificacao.service;

import ujc.notificacao.system.sistema_notificacao.entity.Estudante;
import ujc.notificacao.system.sistema_notificacao.repository.EstudanteRepository;
import ujc.notificacao.system.sistema_notificacao.dto.request.EstudanteRequestDTO;
import ujc.notificacao.system.sistema_notificacao.dto.response.EstudanteResponseDTO;
import ujc.notificacao.system.sistema_notificacao.dto.list.EstudanteListDTO;
import ujc.notificacao.system.sistema_notificacao.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudanteService {

    @Autowired
    private EstudanteRepository estudanteRepository;

    // Criar novo estudante
    @Transactional
    public EstudanteResponseDTO criarEstudante(EstudanteRequestDTO dto) {
        // Validações
        if (!ValidationUtils.isValidNome(dto.getNome())) {
            throw new RuntimeException("Nome inválido (mínimo 3 caracteres)");
        }
        if (!ValidationUtils.isValidEmail(dto.getEmail())) {
            throw new RuntimeException("Email inválido");
        }
        if (!ValidationUtils.isValidNumeroEstudante(dto.getNumeroEstudante())) {
            throw new RuntimeException("Número de estudante inválido (deve conter 8-12 dígitos)");
        }
        if (!ValidationUtils.isValidAno(dto.getAnoIngresso())) {
            throw new RuntimeException("Ano de ingresso inválido");
        }
        
        // Verificar duplicados
        if (estudanteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + dto.getEmail());
        }
        if (estudanteRepository.existsByNumeroEstudante(dto.getNumeroEstudante())) {
            throw new RuntimeException("Número de estudante já cadastrado: " + dto.getNumeroEstudante());
        }
        
        // Converter DTO para entidade
        Estudante estudante = new Estudante();
        estudante.setNome(dto.getNome());
        estudante.setApelido(dto.getApelido());
        estudante.setNumeroEstudante(dto.getNumeroEstudante());
        estudante.setGenero(dto.getGenero());
        estudante.setCurso(dto.getCurso());
        estudante.setAnoIngresso(dto.getAnoIngresso());
        estudante.setEmail(dto.getEmail());
        
        // Salvar
        Estudante saved = estudanteRepository.save(estudante);
        return new EstudanteResponseDTO(saved);
    }

    // Listar todos os estudantes
    public List<EstudanteListDTO> listarTodos() {
        return estudanteRepository.findAll()
                .stream()
                .map(EstudanteListDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar estudante por ID
    public EstudanteResponseDTO buscarPorId(Long id) {
        Estudante estudante = estudanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado com ID: " + id));
        return new EstudanteResponseDTO(estudante);
    }

    // Buscar por número de estudante
    public EstudanteResponseDTO buscarPorNumeroEstudante(String numeroEstudante) {
        Estudante estudante = estudanteRepository.findByNumeroEstudante(numeroEstudante)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado com número: " + numeroEstudante));
        return new EstudanteResponseDTO(estudante);
    }

    // Buscar por nome
    public List<EstudanteListDTO> buscarPorNome(String nome) {
        if (!ValidationUtils.isValidNome(nome)) {
            throw new RuntimeException("Nome deve ter pelo menos 3 caracteres");
        }
        return estudanteRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(EstudanteListDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por curso
    public List<EstudanteListDTO> buscarPorCurso(String curso) {
        return estudanteRepository.findByCurso(curso)
                .stream()
                .map(EstudanteListDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar por termo geral
    public List<EstudanteListDTO> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            throw new RuntimeException("Termo de busca não pode ser vazio");
        }
        return estudanteRepository.buscarPorTermo(termo)
                .stream()
                .map(EstudanteListDTO::new)
                .collect(Collectors.toList());
    }

    // Atualizar estudante
    @Transactional
    public EstudanteResponseDTO atualizarEstudante(Long id, EstudanteRequestDTO dto) {
        Estudante estudante = estudanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado com ID: " + id));
        
        // Validações
        if (!ValidationUtils.isValidNome(dto.getNome())) {
            throw new RuntimeException("Nome inválido");
        }
        if (!ValidationUtils.isValidEmail(dto.getEmail())) {
            throw new RuntimeException("Email inválido");
        }
        
        // Verificar email duplicado (se mudou)
        if (!estudante.getEmail().equals(dto.getEmail()) && 
            estudanteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado por outro estudante: " + dto.getEmail());
        }
        
        // Verificar número duplicado (se mudou)
        if (!estudante.getNumeroEstudante().equals(dto.getNumeroEstudante()) && 
            estudanteRepository.existsByNumeroEstudante(dto.getNumeroEstudante())) {
            throw new RuntimeException("Número de estudante já cadastrado: " + dto.getNumeroEstudante());
        }
        
        // Atualizar dados
        estudante.setNome(dto.getNome());
        estudante.setApelido(dto.getApelido());
        estudante.setNumeroEstudante(dto.getNumeroEstudante());
        estudante.setGenero(dto.getGenero());
        estudante.setCurso(dto.getCurso());
        estudante.setAnoIngresso(dto.getAnoIngresso());
        estudante.setEmail(dto.getEmail());
        
        Estudante updated = estudanteRepository.save(estudante);
        return new EstudanteResponseDTO(updated);
    }

    // Deletar estudante
    @Transactional
    public void deletarEstudante(Long id) {
        if (!estudanteRepository.existsById(id)) {
            throw new RuntimeException("Estudante não encontrado com ID: " + id);
        }
        estudanteRepository.deleteById(id);
    }

    // Contar estudantes por curso
    public Long contarPorCurso(String curso) {
        return estudanteRepository.countByCurso(curso);
    }
}
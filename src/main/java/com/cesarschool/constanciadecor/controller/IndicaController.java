package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.IndicaDAO;
import com.cesarschool.constanciadecor.DAO.ClienteDAO;
import com.cesarschool.constanciadecor.model.Indica;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/indicacoes")
public class IndicaController {

    private final IndicaDAO dao = new IndicaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Indica i) {
        try {
            if (clienteDAO.getClienteByCpf(i.getCpfIndicador()) == null ||
                    clienteDAO.getClienteByCpf(i.getCpfIndicado()) == null) {
                return ResponseEntity.badRequest().body("Ambos os clientes devem estar cadastrados.");
            }

            if (dao.existeIndicacao(i.getCpfIndicador(), i.getCpfIndicado())) {
                return ResponseEntity.badRequest().body("Essa indicação já existe.");
            }

            dao.adicionar(i);
            return ResponseEntity.status(201).body("Indicação registrada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Indica>> listar() {
        try {
            // Retornar apenas os indicados (opcionalmente, pode criar um DTO só com esse campo)
            List<Indica> todas = dao.listarTodos();

            // Se quiser retornar apenas os indicados no JSON:
            List<Indica> apenasIndicados = todas.stream()
                    .map(i -> new Indica(null, i.getCpfIndicado())) // cpfIndicador = null
                    .collect(Collectors.toList());

            return ResponseEntity.ok(apenasIndicados);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> remover(@RequestBody Indica i) {
        try {
            dao.remover(i);
            return ResponseEntity.ok("Indicação removida com sucesso.");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao remover: " + e.getMessage());
        }
    }
}

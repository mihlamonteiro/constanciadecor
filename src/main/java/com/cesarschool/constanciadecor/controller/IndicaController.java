package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.ClienteDAO;
import com.cesarschool.constanciadecor.DAO.IndicaDAO;
import com.cesarschool.constanciadecor.model.Indica;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/indicacoes")
public class IndicaController {

    private final IndicaDAO dao = new IndicaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Indica indicacao) {
        try {
            if (indicacao.getCpfIndicador().equals(indicacao.getCpfIndicado())) {
                return ResponseEntity.badRequest().body("Um cliente não pode se autoindicar.");
            }

            if (clienteDAO.getClienteByCpf(indicacao.getCpfIndicador()) == null ||
                    clienteDAO.getClienteByCpf(indicacao.getCpfIndicado()) == null) {
                return ResponseEntity.badRequest().body("CPF do indicador ou indicado não encontrado.");
            }

            dao.adicionarIndicacao(indicacao);
            return ResponseEntity.status(201).body("Indicação registrada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao registrar indicação: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> remover(@RequestBody Indica indicacao) {
        try {
            dao.removerIndicacao(indicacao);
            return ResponseEntity.ok("Indicação removida com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao remover indicação: " + e.getMessage());
        }
    }

    @GetMapping("/{cpfIndicador}")
    public ResponseEntity<List<Indica>> listar(@PathVariable String cpfIndicador) {
        try {
            return ResponseEntity.ok(dao.listarIndicacoesPorCliente(cpfIndicador));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

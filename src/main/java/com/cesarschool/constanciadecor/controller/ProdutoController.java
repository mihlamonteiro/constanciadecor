package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.AdministradorAromasDAO;
import com.cesarschool.constanciadecor.DAO.ProdutoDAO;
import com.cesarschool.constanciadecor.model.Produto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoDAO dao = new ProdutoDAO();
    private final AdministradorAromasDAO administradorDAO = new AdministradorAromasDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Produto produto) {
        try {
            // Verifique o CPF do administrador que está sendo passado
            System.out.println("Verificando administrador com CPF: " + produto.getCpfAdministrador());

            // Verifica se o administrador existe no banco
            if (produto.getCpfAdministrador() == null || administradorDAO.getAdministradorByCpf(produto.getCpfAdministrador()) == null) {
                return ResponseEntity.badRequest().body("Administrador responsável não encontrado.");
            }

            // Se o administrador for encontrado, adiciona o produto
            dao.addProduto(produto);
            return ResponseEntity.status(201).body("Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> adicionarProdutoComImagem(
            @RequestPart("produto") Produto produto,
            @RequestPart("imagem") MultipartFile imagem) {

        try {
            String pasta = "uploads/";
            String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
            Path destino = Paths.get(pasta + nomeArquivo);

            Files.createDirectories(destino.getParent());
            Files.copy(imagem.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            produto.setNomeImagem(nomeArquivo);
            dao.addProduto(produto);

            return ResponseEntity.status(201).body("Produto cadastrado com imagem!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar imagem: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{codigo}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> editarProdutoComImagem(
            @PathVariable int codigo,
            @RequestPart("produto") Produto produto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        try {
            if (imagem != null && !imagem.isEmpty()) {
                String pasta = "uploads/";
                String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                Path destino = Paths.get(pasta + nomeArquivo);
                Files.createDirectories(destino.getParent());
                Files.copy(imagem.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
                produto.setNomeImagem(nomeArquivo);
            }
            dao.updateProduto(codigo, produto);
            return ResponseEntity.ok("Produto editado com imagem!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao editar produto com imagem: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        try {
            return ResponseEntity.ok(dao.getAllProdutos());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable int codigo) {
        try {
            Produto produto = dao.getProdutoByCodigo(codigo);
            return (produto != null) ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<String> editar(@PathVariable int codigo, @RequestBody Produto produto) {
        try {
            dao.updateProduto(codigo, produto);
            return ResponseEntity.ok("Produto editado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao editar produto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> deletar(@PathVariable int codigo) {
        try {
            dao.deleteProduto(codigo);
            return ResponseEntity.ok("Produto excluído com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir produto: " + e.getMessage());
        }
    }
}

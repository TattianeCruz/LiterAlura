package br.com.alura.literalura.repository;


import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.DadosAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("Select a FROM autores a WHERE a.anoFalec > :year AND a.anoNasc <= :year")
    List<Autor> findAutoresAliveInYear(int year);
}

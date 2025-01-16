package br.com.alura.literalura.dto;

import br.com.alura.literalura.model.DadosLivro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public record ApiResult(@JsonAlias("results") List<DadosLivro> dadosLivro) {
}

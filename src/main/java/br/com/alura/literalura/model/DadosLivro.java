package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public record DadosLivro(@JsonAlias("title") String title,
                         @JsonAlias("authors") List<DadosAutor> dadosAutorList,
                         @JsonAlias("languages") List<String> linguagemList,
                         @JsonAlias("download_count") Integer downloadCount) {
}

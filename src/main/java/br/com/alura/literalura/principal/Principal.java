package br.com.alura.literalura.principal;

import br.com.alura.literalura.dto.ApiResult;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner livroScn = new Scanner(System.in);
    private Scanner escolhaScn = new Scanner(System.in);
    private Scanner linguagemScn = new Scanner(System.in);
    private final String ENDERECOAPI = "https://gutendex.com/books/?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }


//    private AutorRepository autorRepository;


    public void exibeMenu() {
        int escolha = -1;

        while (escolha != 0) {
            System.out.println("---------------------------------");
            System.out.println("Escolha o número de sua opção:");
            System.out.println("1- buscar livro pelo título");
            System.out.println("2- listar livros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos em um determinado ano");
            System.out.println("5- listar livros em um determinado idioma");
            System.out.println("0- sair");

            escolha = escolhaScn.nextInt();

            switch (escolha) {
                case 1:
                    System.out.println("Digite o nome do livro:");
                    String titulo = livroScn.nextLine();
                    buscarLivro(titulo);
                    break;
                case 2:
                    ListarLivros();
                    break;
                case 3:
                    ListarAutores();
                    break;
                case 4:
                    ListarAutoresNasc();
                    break;
                case 5:
                    ListarLivrosEmLinguagem();
                    break;
                case 0:
                    System.out.println("Saindo. Obrigado por usar o programa!");
                    break;
                default:
                    System.out.println("Ainda não implementado");
                    break;
            }
        }

    }


    public void buscarLivro(String titulo) {
        String urlFinal = ENDERECOAPI + titulo.replace(" ", "%20");

        var json = consumoApi.obterDados(urlFinal);
        ConverteDados conversor = new ConverteDados();

        ApiResult apiResult = conversor.obterDados(json, ApiResult.class);
        Optional<DadosLivro> dadosLivro = apiResult.dadosLivro().stream().findFirst();

        if (dadosLivro.isPresent()) {
            Livro livro = new Livro(dadosLivro.get());

            SalvarLivro(livro);
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado na API");
        }
    }

    public void SalvarAutor(Livro livro) {

        Autor autor = livro.getAutor();

        Optional<Autor> autorDb = autorRepository.findByNomeContainingIgnoreCase(autor.getNome());


        if (autorDb.isEmpty()) {
            autorRepository.save(autor);
        } else {
            livro.setAutor(autorDb.get());
        }
    }

    public void SalvarLivro(Livro livro) {
        Optional<Livro> livroSearch = livroRepository.findByTituloContainingIgnoreCase(livro.getTitulo());

        if (livroSearch.isEmpty()) {
            SalvarAutor(livro);
            livroRepository.save(livro);
        } else {
            System.out.println("Livro já existente no sistema");
        }
    }

    private void ListarLivros() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro foi registrado até o momento.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void ListarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum livro foi registrado até o momento.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void ListarAutoresNasc() {
        Scanner anoScn = new Scanner(System.in);
        System.out.println("Qual o ano?");

        int ano = anoScn.nextInt();

        List<Autor> autores = autorRepository.findAutoresAliveInYear(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado.");
        } else {
            System.out.println("Autores vivos em um determinado ano:");
            autores.forEach(System.out::println);
            System.out.println("============== TOTAL DE AUTORES VIVOS ==============");
            System.out.println("Total de autores vivos: " + autores.size());
            System.out.println("====================================================");
        }
    }

    private void ListarLivrosEmLinguagem() {
        System.out.println("""
                Códigos:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        System.out.println("Digite o código do idioma desejado: ");
        String languageCode = linguagemScn.nextLine();

        List<Livro> livros = livroRepository.findByLanguageContainingIgnoreCase(languageCode);

        if (livros.isEmpty()){
            System.out.println("Não foram encontrados livros no idioma mencionado.");
        }else{
            livros.forEach(System.out::println);
            System.out.println("Total de livros encontrados: " + livros.size());
        }

    }

}

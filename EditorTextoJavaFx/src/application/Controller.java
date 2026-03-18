package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller {

    @FXML
    public TextArea textArea;
    public File arquivoAtual;
    private double fontSize = 14; // Tamanho inicial da fonte

    @FXML
    public void initialize() {
        // Adiciona o evento de Scroll para aumentar/diminuir o zoom
        textArea.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {
                if (event.getDeltaY() > 0) {
                    fontSize += 1;
                } else if (event.getDeltaY() < 0 && fontSize > 5) {
                    fontSize -= 1;
                }
                atualizarEstiloFonte();
                event.consume(); // Impede o scroll normal da página enquanto dá zoom
            }
        });
    }

    private void atualizarEstiloFonte() {
        // Mantém as cores atuais e só altera o tamanho da fonte
        String currentStyle = textArea.getStyle();
        textArea.setStyle(currentStyle + "; -fx-font-size: " + fontSize + "px;");
    }

    public void salvarArquivo() {
        if (arquivoAtual != null) {
            escreverArquivo(arquivoAtual, textArea.getText());
        } else {
            salvarArquivoComo();
        }
    }

    public void escreverArquivo(File file, String conteudo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(conteudo);
        } catch (Exception e) {
            exibirAlerta("Erro", "Não foi possível salvar o arquivo.");
        }
    }

    public void salvarArquivoComo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Arquivo");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Arquivos de texto (*.txt)", "*.txt"));
        
        File file = fileChooser.showSaveDialog(textArea.getScene().getWindow());

        if (file != null) {
            arquivoAtual = file;
            escreverArquivo(arquivoAtual, textArea.getText());
        }
    }

    public void abrirArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Arquivos de texto (*.txt)", "*.txt"));
        
        File file = fileChooser.showOpenDialog(textArea.getScene().getWindow());

        if (file != null) {
            arquivoAtual = file;
            textArea.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    textArea.appendText(linha + "\n");
                }
            } catch (Exception e) {
                exibirAlerta("Erro", "Erro ao ler o arquivo.");
            }
        }
    }

    public void novoArquivo() {
        textArea.clear();
        arquivoAtual = null;
    }

    public void sair() {
        javafx.application.Platform.exit();
    }

    public void copiar() {
        textArea.copy();
    }

    public void colar() {
        textArea.paste();
    }

    public void selecionarTudo() {
        textArea.selectAll();
    }

    public void modoEscuro() {
        textArea.setStyle("-fx-control-inner-background: #2b2b2b; -fx-text-fill: white; -fx-font-size: " + fontSize + "px;");
    }

    public void modoClaro() {
        textArea.setStyle("-fx-control-inner-background: white; -fx-text-fill: black; -fx-font-size: " + fontSize + "px;");
    }

    public void exibirInformacoes() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sobre o Editor");
        alert.setHeaderText("Editor de Texto JavaFX");
        alert.setContentText("Use CTRL + Scroll do mouse para aumentar ou diminuir o zoom do texto.");
        alert.showAndWait();
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
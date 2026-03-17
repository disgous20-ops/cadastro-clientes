package Relatorios;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import controller.Conexao;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class RelatorioClientesPDF {

    public static void gerarRelatorio() {
        
        // 1. Definição do local e nome do arquivo
        String caminhoPasta = "C:\\relatorio";
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nomeArquivo = "Relatorio Clientes -" + timestamp + ".pdf";
        File arquivoFinal = new File(caminhoPasta, nomeArquivo);

        Document document = new Document();

        try {
            // 2. Garante que a pasta C:\relatorio existe no Windows
            File pasta = new File(caminhoPasta);
            if (!pasta.exists()) {
                pasta.mkdirs(); 
            }

            // 3. Inicializa o PDF no caminho específico
            PdfWriter.getInstance(document, new FileOutputStream(arquivoFinal));
            document.open();

            // Cabeçalho do documento (Sem data no texto, conforme solicitado)
            document.add(new Paragraph("RELATÓRIO DE CLIENTES"));
            document.add(new Paragraph(" "));

            // 4. Conexão e Query (Removido o campo 'nome')
            Connection conn = Conexao.getInstacia().abrirConexao();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nome, cpfCnpj, email, telefone FROM CLIENTE");

            // 5. Criação da tabela com 3 colunas (CPF, Email, Telefone)
            PdfPTable tabela = new PdfPTable(4);
            tabela.setWidthPercentage(100); // Ocupa a largura total da página

            // Cabeçalhos da Tabela
            tabela.addCell(new PdfPCell(new Phrase("Nome")));
            tabela.addCell(new PdfPCell(new Phrase("CPF/CNPJ")));
            tabela.addCell(new PdfPCell(new Phrase("Email")));
            tabela.addCell(new PdfPCell(new Phrase("Telefone")));

            // 6. Preenchimento dos dados
            while (rs.next()) {
            	tabela.addCell(rs.getString("nome"));
                tabela.addCell(rs.getString("cpfCnpj"));
                tabela.addCell(rs.getString("email"));
                tabela.addCell(rs.getString("telefone"));
            }

            document.add(tabela);
            document.close();

            System.out.println("Relatório gerado com sucesso em: " + arquivoFinal.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso - "+ arquivoFinal.getAbsolutePath());
            // Opcional: Abrir o arquivo automaticamente após gerar (apenas Windows)
            // java.awt.Desktop.getDesktop().open(arquivoFinal);

        } catch (Exception e) {
            System.err.println("Erro ao gerar o relatório: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Relatório não gerado");
            e.printStackTrace();
        }
    }
}
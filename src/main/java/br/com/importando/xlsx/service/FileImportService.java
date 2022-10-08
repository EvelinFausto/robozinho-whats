package br.com.importando.xlsx.service;

import br.com.importando.xlsx.dto.ContatoDto;
import br.com.importando.xlsx.mapper.ContatoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class FileImportService {


    public List<ContatoDto> readWorksheet() {
        FileInputStream fisPlanilha = null;
        String name = "";
        String phone = "";
        List<ContatoDto> contatos = new ArrayList<>();
        Integer linha = 0;
        try {
            //pegando planilha
            File planilha = new File("C:\\meus\\planilha\\contatos.xlsx");
            fisPlanilha = new FileInputStream(planilha);
            XSSFWorkbook workbook = new XSSFWorkbook(fisPlanilha);
            //aba 1 da planilha
            XSSFSheet sheet = workbook.getSheetAt(0);
            int countRow = sheet.getPhysicalNumberOfRows();
            Iterator<Row> rowIterator = sheet.iterator();

            //varremos todas as linhas
            while (linha < countRow) {
                //linha
                Row row = rowIterator.next();
                if (row.getCell(0) != null && row.getCell(1) != null) {
                    if (row.getCell(0).getCellType().equals(CellType.STRING)) {
                        name = row.getCell(0).getStringCellValue();
                        name = buildName(name);
                    } else {
                        name = String.valueOf(row.getCell(0).getNumericCellValue());
                        name = buildName(name);
                    }
                    if (row.getCell(1).getCellType().equals(CellType.STRING)) {
                        phone = row.getCell(1).getStringCellValue();
                    } else {
                        row.getCell(1).setCellType(CellType.STRING);
                        phone = (row.getCell(1).getStringCellValue());
                    }
                    if (!(name.equals("0.0") || phone.equals("0.0") || phone.equals("") || name.equals("0.0 0.0"))) {
                        contatos.add(createNewContact(name, phone));
                    }
                } else {
                    log.error("linha invalida", linha);
                }
                linha++;
            }
            return contatos;
        } catch (IOException ex) {
            log.error("Não foi possível montar a lista", ex);
        }
        return null;
    }

    private ContatoDto createNewContact(String name, String phone) {
        return ContatoMapper.createContato(phone, name);
    }

    private String buildName(String nomeCompleto) {
        String[] split = nomeCompleto.split(" ");
        String ultimoNome = firtLetterUper(split[split.length - 1]);

        String primeiroNome = firtLetterUper(nomeCompleto.split(" ")[0]);
        return primeiroNome + " " + ultimoNome;
    }

    private String firtLetterUper(String word) {
        String wordLower = word.toLowerCase();
        return wordLower.substring(0, 1).toUpperCase().concat(wordLower.substring(1));
    }
}
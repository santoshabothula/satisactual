package com.datawise.satisactual;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@ActiveProfiles("local")
class SatisactualApplicationTests {

    @Test
    void context() {}

    public static void main(String a[]) throws IOException {
        File file = ResourceUtils.getFile("classpath:entity.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet worksheet = workbook.getSheetAt(0);

        String tableName = "mst_prod_certificates";
        String entityName = "";
        String entity = "";
        String id = "";

        File entityFile = ResourceUtils.getFile("classpath:entity.txt");
        String entityFileStr = new String(Files.readAllBytes(entityFile.toPath()));
        String entityFileStrTemp = "";
        String entityIdFileStrTemp = "";
        String dtoFileStrTemp = "";

        File entityId = ResourceUtils.getFile("classpath:entityId.txt");
        String entityIdStr = new String(Files.readAllBytes(entityId.toPath()));

        File dto = ResourceUtils.getFile("classpath:dto.txt");
        String dtoStr = new String(Files.readAllBytes(dto.toPath()));

        File repository = ResourceUtils.getFile("classpath:repository.txt");
        String repositoryStr = new String(Files.readAllBytes(repository.toPath()));

        String columnTemplate = "\n@Column(name = \"{column_name}\")\n" +
                "private {datatype} {variable};\n";

        String dtoTemplate = "\n\t@JsonProperty(\"{column_name}\")\n" +
                "    private {datatype} {variable};\n";

        for(int i=0;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);
            String name = row.getCell(0).getStringCellValue();
            String datatype = row.getCell(1).getStringCellValue();
            String isPrimary = row.getCell(2).getStringCellValue();

            String column = columnTemplate.replace("{column_name}", name.trim());
            String dtoColumn = dtoTemplate.replace("{column_name}", name.trim());

            String type = "String";
            if (datatype.trim().contains("smallint")) {
                type = "Integer";
            } else if (datatype.trim().contains("date")) {
                type = "LocalDate";
            } else if (datatype.trim().contains("double")) {
                type = "Double";
            }
            column = column.replace("{datatype}", type);
            dtoColumn = dtoColumn.replace("{datatype}", type);

            String[] variable = name.split("_");
            StringBuilder var = new StringBuilder();
            if (variable[0].contains("txt") || variable[0].contains("num") || variable[0].contains("flg") || variable[0].contains("dat") || variable[0].contains("enu") || variable[0].contains("cod") || variable[0].contains("amt") || variable[0].contains("max")) {
                for(int j=1;j< variable.length;j++) {
                    String first = (j == 1) ? String.valueOf(variable[j].charAt(0)) : String.valueOf(variable[j].charAt(0)).toUpperCase();
                    String r = variable[j].substring(1);
                    var.append(first).append(r);
                }
            }

            if (isPrimary.equals("PRI")) {
                entityIdFileStrTemp += column.replace("{variable}", var.toString());
            } else {
                entityFileStrTemp += column.replace("{variable}", var.toString());
            }
            dtoFileStrTemp += dtoColumn.replace("{variable}", var.toString());
        }
        entityFileStr = entityFileStr.replace("<columns-list>", entityFileStrTemp);
        entityIdStr = entityIdStr.replace("<primary-key>", entityIdFileStrTemp);
        dtoStr = dtoStr.replace("<properties>", dtoFileStrTemp);

        entityName = tableName.replace("mst_", "");

        String[] entitySplit = entityName.split("_");
        entityName = "";
        for (int i=0;i<entitySplit.length;i++) {
            String first = String.valueOf(entitySplit[i].charAt(0)).toUpperCase();
            String r = entitySplit[i].substring(1);
            entityName += first + r;
        }

        boolean last = String.valueOf(entityName.charAt(entityName.length() - 1)).equalsIgnoreCase("s");
        entityName = entityName.substring(0, last ? entityName.length() - 1 : entityName.length());

        entityFileStr = entityFileStr.replace("<entity-name>", entityName);
        entityFileStr = entityFileStr.replace("<table-name>", tableName);
        entityIdStr = entityIdStr.replace("<entity-name>", entityName);
        dtoStr = dtoStr.replace("<entity-name>", entityName);

        repositoryStr = repositoryStr.replace("<entity-name>", entityName);

        Path path = Paths.get(entityName + "Entity.java");
        byte[] entityFileBytes = entityFileStr.getBytes();
        Files.write(path, entityFileBytes);

        path = Paths.get(entityName + "EmbeddedKey.java");
        byte[] entityIdFileBytes = entityIdStr.getBytes();
        Files.write(path, entityIdFileBytes);

        path = Paths.get(entityName + "DTO.java");
        byte[] dtoFileBytes = dtoStr.getBytes();
        Files.write(path, dtoFileBytes);

        path = Paths.get(entityName + "Repository.java");
        byte[] repositoryStrFileBytes = repositoryStr.getBytes();
        Files.write(path, repositoryStrFileBytes);

    }
}

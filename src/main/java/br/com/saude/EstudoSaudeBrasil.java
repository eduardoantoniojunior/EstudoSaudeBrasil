/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.saude;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.asc;

/**
 *
 * @author ejunior@beonup.intra
 */
public class EstudoSaudeBrasil {
    public static void main(String[] args) {
        try {
            Logger.getLogger("org.apache").setLevel(Level.WARN);
            SparkSession spark = SparkSession
                    .builder()
                    .appName("ExplorandoDados")
                    .master("local[*]")
                    .getOrCreate();
            
            Dataset<Row> imunizacao = spark.read()
                    .option("header", true)
                    .option("inferSchema", true)
                    .option("delimiter", ",")
                    .csv("/home/ejunior@beonup.intra/Estudos/Spark/Data/Imunizacao/br_ms_imunizacoes_municipio.csv");

            imunizacao.printSchema();
            imunizacao
                    .select("ano", "sigla_uf", "doses_total")
                    .where("sigla_uf = 'PI'")
                    .orderBy(asc("doses_total"))
                    .show();

            spark.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }   
    }
}

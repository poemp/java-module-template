package org.poem.db;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * @author poem
 */
public class JooqPgGen {
    /**
     * Jooq 自动化生成代码
     */
    public void gen(Properties props) {

        String driverClassName = props.getProperty("spring.datasource.driverClassName");
        String username = props.getProperty("spring.datasource.username");
        String url = props.getProperty("spring.datasource.url");
        String password = props.getProperty("spring.datasource.password");

        Jdbc jdbc = new Jdbc().withDriver(driverClassName)
                .withUrl(url)
                .withUser(username)
                .withPassword(password);
        Database database = new Database()
                .withName("org.jooq.meta.postgres.PostgresDatabase")
                .withExcludes("schema_version")
                .withSchemata(new Schema().withInputSchema("public"));
        Generator generator = new Generator()
                .withName("org.jooq.codegen.JavaGenerator")
                .withDatabase(database)
                .withGenerate(new Generate()
                )
                .withTarget(new Target()
                        .withPackageName("org.poem.jooq").withDirectory("vs-entity/src/main/scala"));
        Configuration conf = new Configuration()
                .withJdbc(jdbc)
                .withGenerator(generator);

        try {
            GenerationTool.generate(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties properties = yaml.getObject();
        JooqPgGen jg = new JooqPgGen();
        jg.gen(properties);
        System.out.println("JooqPgGenerator finished.");
    }

}

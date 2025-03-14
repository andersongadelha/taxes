package br.com.zup.taxes.infra;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "Bearer Authentication";
        String description = """
                Documentação dos endpoints de Taxes API.\n
                Para acessar endpoints autenticados, é necessário adicionar o token JWT no cabeçalho de autorização (Bearer Token).\n
                No Swagger, você pode usar o botão "Authorize" para inserir o token e autenticar automaticamente.
                """;
        return new OpenAPI()
                .info(new Info()
                        .title("Taxes API")
                        .version("1.0.0")
                        .description(description))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Adicione o token JWT aqui para acessar endpoints autenticados.")));
    }

}
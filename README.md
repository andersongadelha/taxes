# 📊 Taxes API

Bem-vindo à **Taxes API**! Este projeto é uma aplicação Spring Boot que fornece uma API para gerenciar taxas. Abaixo, você encontrará informações sobre a estrutura do projeto, como rodar a aplicação, configurar o banco de dados local e executar os testes com relatórios de cobertura de código usando o JaCoCo.

## 🗂️ Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

- **`br.com.zup.taxes`**: Contém a classe principal da aplicação.
- **`br.com.zup.taxes.controllers`**: Controladores REST para expor os endpoints da API.
- **`br.com.zup.taxes.exceptions`**: Contem as classes de exceções e GlobalExceptionHandler.
- **`br.com.zup.taxes.infra`**: Configurações gerais sobre infraestrutura da API.
- **`br.com.zup.taxes.services`**: Contém a lógica de negócios da aplicação.
- **`br.com.zup.taxes.repositories`**: Repositórios para acesso ao banco de dados.
- **`br.com.zup.taxes.models`**: Modelos de dados utilizados na aplicação.

## 🚀 Como Rodar o Projeto

Siga os passos abaixo para rodar a aplicação localmente:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/andersongadelha/taxes.git
   ```

2. **Configure o banco de dados local**:
    - Certifique-se de ter um banco de dados PostgreSQL rodando.
    - Atualize a conexão do seu banco local de desenvolvimento ou
    - Atualize o arquivo `application.yml` com as credenciais do banco de dados local:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/taxes
      spring.datasource.username=postgres
      spring.datasource.password=admin123
      ```

3. **Compile e rode a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

4. **Acesso à API**:
    - A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

5. **Acesso á documentação Swagger**:
   - A documentação swagger estará disponível em: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   - Registrar um usuário.
   - Fazer login com o usuário registrado na base e copiar o JWT retornado.
   - Autenticar pelo swagger usando o JWT no botão Authorize e colar o valor do JWT.

## 🧪 Como Rodar os Testes e Gerar Relatórios de Cobertura

O projeto utiliza o **JaCoCo** para medir a cobertura de código. Siga os passos abaixo para rodar os testes e gerar os relatórios:

1. **Execute os testes**:
   ```bash
   mvn clean verify
   ```

2. **Acesse o relatório**:
    - O relatório HTML será gerado no diretório `target/site/jacoco-merged-test-coverage-report/index.html`.
    - Abra o arquivo no navegador para visualizar a cobertura de código.

---
Feito com ❤️ por [Anderson Gadelha](https://github.com/andersongadelha)

## quotation-management 

### Descrição
Projeto consiste em uma aplicação que cadastar e consulta ações e cotações de mercado financeiro, realizando comunicação com outra api para validação das ações.

### Instruções
- Executar comando `mvnw package` para realizar o empacotamento do projeto em um arquivo jar
- Executar em ambiente wsl2 o comando `bash`, em seguida executar o comando `docker-compose up`

### Informações das dependências
- Projeto depende das imagens "mysql:8" e "lucasvilela/stock-manager"

### Informações do end-point
- A expecificação da REST API estão disponíveis na página: `/swagger-ui.html#/`

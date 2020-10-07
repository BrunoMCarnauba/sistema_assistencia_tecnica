# Sistema de assistência técnica

Sistema de assistência técnica desenvolvido para a disciplina de programação para web do curso de sistemas de informação. O sistema permite o cadastro e listagem de usuários, clientes, fabricantes, peças e ordens de serviços. Você pode conferir o funcionamento desse sistema assistindo a esse [video de demonstração](https://youtu.be/15bHucm3fF8).

## Descrição técnica

- Sistema web desenvolvido em Java com o framework web [JavaServer Faces 2.2.5](https://javaee.github.io/javaserverfaces-spec/) <br/>
- O sistema de gerenciamento de banco de dados utilizado foi o [PostgreSQL](https://www.postgresql.org/) com a aplicação gráfica [pgAdmin III](https://www.pgadmin.org/) <br/>
- Foi utilizada a biblioteca de componentes de interface [PrimeFaces 7.0](https://www.primefaces.org/)

### Preparando o ambiente

- É necessário ter o [Java Development Kit (JDK)](https://www.oracle.com/technetwork/pt/java/javase/downloads/index.html) instalado no computador. <br/>
- O desenvolvimento foi realizado com a IDE [Eclipse](https://www.eclipse.org/downloads/packages/release/2020-06/r/eclipse-ide-enterprise-java-developers) <br/>
- Para abrir o projeto no eclipse, vá em File - Open Projects from File System e selecione o diretório do projeto <br/>
O servidor utilizado para iniciar o sistema foi o [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi). A instalação foi feita da mesma maneira deste vídeo: https://youtu.be/G9PIpuMopvk <br/>
- Para adicionar o projeto ao servidor, vá na aba Servers, clique com o botão direito no Tomcat, escolha a opção "Add and Remove..." e mova o projeto da lista de disponíveis para a lista de configurados, na direita. <br/>
- Se certifique de que no eclipse ao clicar com botão direito no projeto e ir em propriedades, na aba Project Faces a opção JavaServer Faces esteja selecionada com a versão 2.2, e que na aba runtimes esteja marcado o Apache Tomcat que irá iniciar a aplicação. <br/>
Para criar o banco de dados, basta executar o script SQL chamado "Script_Banco". Ele irá criar o banco com o nome "sistema_assistencia_tecnica", as tabelas e inserir alguns dados iniciais, como cargos e o usuário administrador, que tem como seu login o email "admin@admin.com" e senha "admin". As configurações como usuário e senha do banco são colocadas na classe "src/factory/FabricaConexao.java".

### Iniciando o sistema web

Com o projeto adicionado ao servidor, para iniciá-lo na rede local vá na aba Servers do eclipse e clique no botão "Start the server" que é verde e tem um ícone referente a "Play". Com o servidor iniciado, pode ir no navegador e acessar ao sistema pelo endereço
```
http://localhost:8080/SistemaAssistenciaTecnica/
```

### Possíveis erros

- Se estiver apresentando o erro ["... cannot be resolved to a type"](https://stackoverflow.com/questions/19467397/eclipse-system-cannot-be-resolved/53787279#:~:text=To%20resolve%20this%20problem%20perform,will%20get%20displayed%20as%20%E2%80%9CJRE) indicado pelo eclipse em algumas linhas de código das classes java, então vá até as propriedades do projeto clicando com botão direito nele e indo em propriedades, procure por Java Build Path, na aba Libraries dê 2 cliques em JRE System Library e selecione a versão do java no campo Alternate JRE. <br/>
- Se a [aba "Servers" não estiver aparecendo](https://stackoverflow.com/questions/13039449/no-server-in-eclipse-trying-to-install-tomcat) na parte inferior do eclipse, basta ir no menu "Window -> Show View -> Other" localizado no topo, procurar por "Servers" e abri-lo. <br/>
- Se ao tentar iniciar o projeto der o erro de a [porta do Tomcat já está sendo usada](https://stackoverflow.com/questions/34253779/tomcat-server-error-port-8080-already-in-use), verifique qual(is) porta(s) ele disse que está em uso e vá na aba servers, dê 2 cliques no servidor que deu erro, na aba "overview" procure em "port number" pela(s) porta(s) que foi(ram) informada(s) e altere para outra. Por exemplo, se foi informado que a porta 8080 está em uso, altera o "port number" para 8081.

### Extra

- A definição de qual é a página inicial é configurada na tag "welcome-file" do arquivo de configuração do JSF, WebContent/WEB-INF/web.xml <br/>
- As [aulas de programação web com java](https://www.youtube.com/watch?v=Vb5Lk00UeI4&list=PL_GwGUsBlNycg0Ath1oYKCEPmaBkcehBD) do canal [Sérgio Roberto Delfino](https://www.youtube.com/channel/UCJdtabTp9TXaHxdYrAa2j0A) auxiliaram muito no desenvolvimento.

### O que falta implementar?

- Adicionar sistema de pagamento; <br/>
- Adicionar permissões de acesso para os cargos.
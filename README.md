<h1>API para Sistema de Avaliação de Créditos</h1>
<br>
<p>Uma empresa de empréstimo precisa criar um sistema de análise de solicitação de crédito. 
Sua tarefa será criar uma API REST SPRING BOOT E KOTLIN para a empresa fornecer aos seus clientes as seguintes funcionalidades:

<h3>Cliente (Customer):</h3>
<ul>Cadastrar:
    <li>Request: firstName, lastName, cpf, income, email, password, zipCode e street</li>
    <li>Response: String</li>
</ul>
<ul>Editar cadastro:
<li>Request: id, firstName, lastName, income, zipCode, street</li>
<li>Response: firstName, lastName, income, cpf, email, income, zipCode, street</li>
</ul>
<ul>Visualizar perfil:
<li>Request: id</li>
<li>Response: firstName, lastName, income, cpf, email, income, zipCode, street</li>
</ul>

<ul>Deletar cadastro:
<li>Request: id</li>
<li>Response: sem retorno</li>
</ul>

<h3>Solicitação de Empréstimo (Credit):</h3>
<ul>Cadastrar:
<li>Request: creditValue, dayFirstOfInstallment, numberOfInstallments e customerId</li>
<li>Response: String</li>
</ul>

<ul>Listar todas as solicitações de emprestimo de um cliente:
<li>Request: customerId</li>
<li>Response: creditCode, creditValue, numberOfInstallment</li>
</ul>

<ul>Visualizar um emprestimo:
<li>Request: customerId e creditCode</li>
<li>Response: creditCode, creditValue, numberOfInstallment, status, emailCustomer e incomeCustomer</li>
</ul>

<h1>Notas do desenvolvedor</h1>
<li>Estrutura empregada: 3 camadas</li>
<li>Framework: Springboot, Junit, Hibernate, JPA</li>
<li>Documentação: Swagger (/swagger-ui.html)</li>
<li>Banco de dados: H2 </li>
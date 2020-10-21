## Projeto para teste de ingresso no Bootcamp 2 da Zup.

## O que utilizei

- spring mvc
- quartz
- swagger
- argon2
- PostgreSql

## Sobre o desenvolvimento

No geral toda a aplicação está síncrona, menos o envio e e-mail.
Em relação a organização, decidi separar pelo o contexto, sendo:

- criar um nova conta,
- acesso (login)
- transferência

Para controle das url após a primeira etapa, utilizei uuid para gerenciar as propostas e esse foi um dos motivos de escolher o PostgreSql como banco de dados.

Na parte de transferência, optei por utilizar o quartz para gerenciar uma fila de jobs, onde o request da transferência sendo válido é inserido na fila para processar a transferência e ao final enviar um e-mail ao dono da conta e não bloqueando o retorno da requisição.

Para hash de senha utilizei argon2 e MailTrap para simular o envio de email.

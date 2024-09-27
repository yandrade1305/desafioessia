## Virtufile
![Badge Concluído](http://img.shields.io/static/v1?label=STATUS&message=CONCLUÍDO&color=GREEN&style=for-the-badge)
![Badge Java](http://img.shields.io/static/v1?label=JAVA&message=21.0.4&color=yellow&style=for-the-badge)
![Badge Spring](http://img.shields.io/static/v1?label=SPRING&message=4.0.0&color=GREEN&style=for-the-badge)
![Badge MySQL](https://img.shields.io/static/v1?label=MySQL&message=8.0.39&color=2596be&style=for-the-badge)
![Badge Angular](http://img.shields.io/static/v1?label=Angular&message=14.1.0&color=red&style=for-the-badge)

<p align="center">
  <img src="https://github.com/user-attachments/assets/c6860b54-b7de-4213-bd4e-e7411a79fe45" />
</p>

Repositório voltado ao desenvolvimento do <a href="https://docs.google.com/presentation/d/10LXS58S4mXKWY-ppl8_zigrJQS19povhkVIx1IrrTdo/edit#slide=id.g2dde4ec7cdc_0_15"> Desafio da empresa Essia </a>

## Índice 

* [Índice](#índice)
* [Descrição do Projeto](#descrição-do-projeto)
* [Como Rodar Localmente](#como-rodar-localmente)
* [Funcionalidades](#funcionalidades)
* [Métodos](#métodos)
* [Pessoas Desenvolvedoras do Projeto](#pessoas-desenvolvedoras)
* [Conclusão](#conclusão)

## Descrição do Projeto

Virtufile é uma aplicação Web onde você consegue gerir arquivos e diretórios. A aplicação foi desenvolvida usando Java e o frontend é em Angular. Além disso, o projeto possui testes unitários e está totalmente dockerizado.

## Como Rodar Localmente

Para rodar o prjeto localmente você precisa estar na pasta raiz do projeto e rodar o seguinte comando:

```console
docker-compose up --build
```
Então o serviços estarão disponiveis nas seguintes portas:

* Banco de Dados: 3306
* Backend: 8080
* Frontend: 4200

## Funcionalidades
As funcionalidades foram divididas em: criar, editar, excluir e listar tanto arquivos, quanto diretórios.

## Métodos
&emsp;&emsp; As requisições para a API devem seguir os padrões:

<center>
  
| Método   | Descrição                                             |
|:---------|-------------------------------------------------------|
| `GET`    | Retorna informações de um ou mais registros.          |
| `POST`   | Utilizado para criar unm novo registro.               |
| `PATCH`  | Atualiza dados de um registro ou altera sua situação. |
| `DELETE` | Remove um registro do sistema.                        |

</center>

### Criar Diretório
* Método HTTP
  * POST
* API Endpoint
  * /api/directory/create
* Request (application/json)
  ```json
    {
        "name": "Documentos",
        "parentId": 1  
    }
  ```
* Response 200 (application/json)
  ```json
    {
        "id": 2,
        "name": "Documentos" ,
        "files": [],
        "subdirectories": [] 
    }
  ```

### Editar Diretório
* Método HTTP
  * POST
* API Endpoint
  * api/directory/update/{id}
* Request (application/json)
  ```json
  {
      "id": 2,
      "name": "Imagens" ,
      "files": [1],
      "subdirectories": [2] 
  }
  ```
* Response 200 (application/json)
 ```json
    {
        "id": 2,
        "name": "Imagens" ,
        "files": [{
            "id": 1,
            "name": "imagem1.png"
        }],
        "subdirectories": [
          {
            "id": 2,
            "name": "Documentos",
            "subdirectories": [],
            "files": [
              {
                "id": 3,
                "name": "projeto.docx"
              }
            ]
          }
        ] 
    }
  ```

### Excluir Diretórios
* Método HTTP
  * DELETE
* API Endpoint
  * api/directory/delete/{id}
* Response 200 (application/json)
  * O body da resposta é vazio

### Listar Diretórios
* Método HTTP
  * GET
* API Endpoint
  * api/directory/get

```json
[
  {
    "id": 1,
    "name": "Raiz",
    "files": [],
    "subdirectories": [
      {
        "id": 2,
        "name": "Documentos",
        "subdirectories": [
          {
            "id": 4,
            "name": "Trabalho",
            "subdirectories": [],
            "files": [
              {
                "id": 3,
                "name": "projeto.docx"
              }
            ]
          }
        ],
        "files": [
          {
            "id": 1,
            "name": "arquivo1.txt"
          }
        ]
      },
      {
        "id": 3,
        "name": "Imagens",
        "subdirectories": [],
        "files": [
          {
            "id": 2,
            "name": "imagem1.png"
          }
        ]
      }
    ]
  },
  {
    "id": 6,
    "name": "My Heart",
    "files": [
      {
        "id": 5,
        "name": "declaracao.txt"
      }
    ],
    "subdirectories": []
  }
]
```

### Criar Arquivo
* Método HTTP
  * POST
* API Endpoint
  * /api/file/create
* Request (application/json)
  ```json
    {
        "name": "arquivo.txt",
        "directoryId": 1  
    }
  ```
* Response 200 (application/json)
  ```json
    {
        "id": 2,
        "name": "arquivo.txt",
        "directoryName": "Raiz" 
    }
  ```

### Editar Arquivo
* Método HTTP
  * POST
* API Endpoint
  * api/file/update/{id}
* Request (application/json)
  ```json
  {
      "id": 2,
      "name": "arquivoatualizado.txt",
      "directoryId": 2  
  }
  ```
* Response 200 (application/json)
 ```json
    {
       "id": 2,
       "name": "arquivoatualizado.txt",
       "directoryName": "Documentos" 
    }
  ```

### Excluir Arquivos
* Método HTTP
  * DELETE
* API Endpoint
  * api/file/delete/{id}
* Response 200 (application/json)
  * O body da resposta é vazio

### Listar Arquivos
* Método HTTP
  * GET
* API Endpoint
  * api/file/get

```json
[
  {
    "id": 1,
    "name": "arquivo1.txt",
    "directoryName": "Documentos"
  },
  {
    "id": 2,
    "name": "imagem1.png",
    "directoryName": "Imagens"
  },
  {
    "id": 3,
    "name": "projeto.docx",
    "directoryName": "Trabalho"
  }
]
```


### Pessoas Desenvolvedoras

[<p align="center"><img src="https://avatars.githubusercontent.com/u/48693812?s=400&u=e3b46f180b450fc7e0bdc65bbbf68e4a77f8d121&v=4" width=115 ><br><sub>Yan Andrade de Sena</sub>](https://github.com/yandrade1305)</p>

### Conclusão

Este desafio foi de longe um dos mais legais de se desenvolver, porque foi um projeto que eu consegui aplicar tudo que aprendi nas minhas experiências profissionais e acadêmicas. Consegui desenvolver os testes unitários criando um container para o banco de dados usando os TestContainers e também dockerizando a aplicação inteira. Duas coisas que eu nunca tinha feito do zero e obtido sucesso, e nesse projeto eu consegui. Aprendi que se você nunca desistir e sempre tentar alcançar seus objetivos, uma hora você chega lá. O projeto em si eu sinto que conseguiria ter realizado ele em 3 dias se eu me dedicasse exclusivamente a ele, mas fico feliz com o resultado de ter entregado ele completo e dentro do prazo.

Muito obrigado Essia por me darem esta oportunidade onde pude me desafiar e desenvolver um excelente projeto!

Obs: Sintam-se á vontade para conhecer e ver outros projetos meus no meu Github!
